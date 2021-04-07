package com.predict.system.mapper;

import com.predict.system.entity.Country;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author ZhenYu.Ben
 */
@Mapper
public interface CountryMapper {
    /**
     * 插入语句
     * @param country 国家信息
     * @return        数字
     */
    @Insert("INSERT INTO COUNTRY VALUES (#{id},#{name},#population)")
    Integer insertCountry(Country country);

    /**
     * 搜索
     * @param id  国家ID
     * @return    国家数据
     */
    @Select("SELECT * FROM COUNTRY WHERE id = #{id}")
    Country searchCountry(String id);
}
