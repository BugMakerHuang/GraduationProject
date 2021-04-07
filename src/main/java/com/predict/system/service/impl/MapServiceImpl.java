package com.predict.system.service.impl;

import com.predict.system.entity.Country;
import com.predict.system.mapper.CountryMapper;
import com.predict.system.service.MapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ZhenYu.Ben
 */
@Slf4j
@Service
public class MapServiceImpl implements MapService {
    @Autowired
    private CountryMapper countryMapper;
    static {
        log.info("进入地图配置接口,请等待.............");
    }
    @Override
    public Country searchCountry(String id) {
        return null;
    }

    @Override
    public Integer insertCountry(Country country) {
        //判断内容是否为空
        if(country == null){
            return null;
        }
        //插入数据
        Integer num = countryMapper.insertCountry(country);
        return num;
    }

    @Override
    public Integer insertCountryList(List<Country> countryList) {
        return null;
    }
}
