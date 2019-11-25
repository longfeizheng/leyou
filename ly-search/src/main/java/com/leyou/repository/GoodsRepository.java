package com.leyou.repository;

import com.leyou.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zhenglongfei 2019-11-22.
 *
 * @VERSION 1.0
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
