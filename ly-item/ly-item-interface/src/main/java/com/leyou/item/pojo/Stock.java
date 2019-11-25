package com.leyou.item.pojo;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zhenglongfei 2019-11-19.
 *
 * @VERSION 1.0
 */
@Data
@Table(name = "tb_stock")
public class Stock {

    @Id
    private Long skuId;
    private Integer seckillStock;// 秒杀可用库存
    private Integer seckillTotal;// 已秒杀数量
    private Long stock;// 正常库存
}
