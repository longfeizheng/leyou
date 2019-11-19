package com.leyou.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * Created by zhenglongfei 2019-11-14.
 *
 * @VERSION 1.0
 */
@org.apache.ibatis.annotations.Mapper
public interface CategoryMapper extends Mapper<Category> {

    /**
     * 根据品牌id查询商品分类
     *
     * @param bid
     * @return
     */
    @Select("SELECT * FROM tb_category WHERE id IN (SELECT category_id FROM tb_category_brand WHERE brand_id = #{bid})")
    List<Category> queryByBrandId(Long bid);
}
