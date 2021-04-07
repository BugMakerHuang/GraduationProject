package com.predict.system.service;

import com.predict.system.entity.Country;

import java.util.List;

/**
 * @author ZhenYu.Ben
 */
public interface MapService {
    /**
     * 通过id查询地图信息
     * @param id        国家id
     * @return          国家数据
     */
    Country searchCountry(String id);
    /**
     * 插入国家数据
     * @param country   国家信息
     * @return          整型数据
     */
    Integer insertCountry(Country country);
    /**
     * 批量插入国家数据
     * @param countryList  国家列表
     * @return             整型数据
     */
    Integer insertCountryList(List<Country> countryList);
}
