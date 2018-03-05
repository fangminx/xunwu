package com.fangminx.service.search;

import com.fangminx.ApplicationTests;
import com.fangminx.service.ISearchService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class SearchServiceTests  extends ApplicationTests{

    @Autowired
    private ISearchService searchService;

    @Test
    public void testIndex(){
        Long targetHouseId = 15L;
        boolean success = searchService.index(targetHouseId);
        Assert.assertTrue(success);
    }

    @Test
    public void testRemove(){
        Long targetHouseId = 15L;
        searchService.remove(targetHouseId);
    }

}
