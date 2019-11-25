package com.leyou.client;

import com.leyou.item.api.CategoryApi;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * Created by zhenglongfei 2019-11-22.
 *
 * @VERSION 1.0
 */
@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryApi {
}
