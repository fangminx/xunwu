package com.fangminx.service.search;

import com.fangminx.entity.House;
import com.fangminx.entity.HouseDetail;
import com.fangminx.entity.HouseTag;
import com.fangminx.repository.HouseDetailRepository;
import com.fangminx.repository.HouseRepository;
import com.fangminx.repository.HouseTagRepository;
import com.fangminx.service.ISearchService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SearchServiceImpl implements ISearchService{

    private static final String INDEX_NAME = "xunwu";

    private static final String INDEX_TYPE = "house";

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private HouseDetailRepository houseDetailRepository;

    @Autowired
    private HouseTagRepository houseTagRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TransportClient esClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean index(Long houseId) {
        House house = houseRepository.findOne(houseId);
        if(house == null){
            log.error("Index house {} dose not exist!", houseId);
            return false;
        }

        HouseIndexTemplate indexTemplate = new HouseIndexTemplate();
        modelMapper.map(house,indexTemplate);

        HouseDetail detail = houseDetailRepository.findByHouseId(houseId);
        if (detail == null){
            // TODO 异常情况
        }
        modelMapper.map(detail,indexTemplate);

        List<HouseTag> tags = houseTagRepository.findAllByHouseId(houseId);
        if(tags != null && !tags.isEmpty()){
            List<String> tagStrings = new ArrayList<>();
            tags.forEach(houseTag -> tagStrings.add(houseTag.getName()));
            indexTemplate.setTags(tagStrings);
        }

        SearchRequestBuilder requestBuilder = this.esClient.prepareSearch(INDEX_NAME).setTypes(INDEX_TYPE)
                .setQuery(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID,houseId));

        log.debug(requestBuilder.toString());
        SearchResponse searchResponse = requestBuilder.get();

        //create \ update \ delete & create
        boolean success;
        long totalHit = searchResponse.getHits().getTotalHits();
        if(totalHit == 0){
            success = create(indexTemplate);
        }else if(totalHit == 1){
            String esId = searchResponse.getHits().getAt(0).getId();
            success = update(esId,indexTemplate);
        }else {
            success = deleteAndCreate(totalHit,indexTemplate);
        }

        if(success){
            log.debug("Index success with house "+houseId);
        }
        return success;

    }

    private boolean create(HouseIndexTemplate indexTemplate){
        try {
            IndexResponse response = this.esClient.prepareIndex(INDEX_NAME,INDEX_TYPE)
                    .setSource(objectMapper.writeValueAsBytes(indexTemplate), XContentType.JSON)
                    .get();
            log.debug("Create index with house: "+ indexTemplate.getHouseId());
            if(response.status() == RestStatus.CREATED){
                return true;
            }else {
                return false;
            }
        } catch (JsonProcessingException e) {
            log.error("Error to index house "+indexTemplate.getHouseId(),e);
            return false;
        }
    }

    private boolean update(String esId,HouseIndexTemplate indexTemplate){
        try {
            UpdateResponse response = this.esClient.prepareUpdate(INDEX_NAME,INDEX_TYPE,esId)
                    .setDoc(objectMapper.writeValueAsBytes(indexTemplate), XContentType.JSON)
                    .get();
            log.debug("Update index with house: "+ indexTemplate.getHouseId());
            if(response.status() == RestStatus.OK){
                return true;
            }else {
                return false;
            }
        } catch (JsonProcessingException e) {
            log.error("Error to index house "+indexTemplate.getHouseId(),e);
            return false;
        }
    }

    private boolean deleteAndCreate(long totalHit, HouseIndexTemplate indexTemplate){
        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(esClient)
                .filter(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID,indexTemplate.getHouseId()))
                .source(INDEX_NAME);

        log.debug("Delete by query for house " + builder);

        BulkByScrollResponse response = builder.get();
        long deleted = response.getDeleted();
        if(deleted != totalHit){
            log.warn("Need delete {}, but {} was deleted!",totalHit,deleted);
            return false;
        } else {
            return create(indexTemplate);
        }

    }


    @Override
    public void remove(Long houseId) {
        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
                .newRequestBuilder(esClient)
                .filter(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID,houseId))
                .source(INDEX_NAME);

        log.debug("Delete by query for house " + builder);

        BulkByScrollResponse response = builder.get();
        long deleted = response.getDeleted();
        log.debug("Delete total " + deleted);
    }
}
