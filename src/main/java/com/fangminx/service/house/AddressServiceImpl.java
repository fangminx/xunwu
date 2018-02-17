package com.fangminx.service.house;

import com.fangminx.entity.SupportAddress;
import com.fangminx.repository.SupportAddressRepository;
import com.fangminx.service.IAddressService;
import com.fangminx.service.ServiceMultiResult;
import com.fangminx.web.controller.house.SupportAddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService{

    @Autowired
    private SupportAddressRepository supportAddressRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ServiceMultiResult<SupportAddressDTO> findAllCities() {
        List<SupportAddress> supportAddressList = supportAddressRepository.findAllByLevel(SupportAddress.Level.CITY.getValue());
        List<SupportAddressDTO> supportAddressDTOList = new ArrayList<>();
        for(SupportAddress supportAddress : supportAddressList){
            SupportAddressDTO target = modelMapper.map(supportAddress,SupportAddressDTO.class);
            supportAddressDTOList.add(target);
        }
        return new ServiceMultiResult<>(supportAddressDTOList.size(),supportAddressDTOList);
    }
}
