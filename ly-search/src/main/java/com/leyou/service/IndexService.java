package com.leyou.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.leyou.client.CategoryClient;
import com.leyou.client.GoodsClient;
import com.leyou.client.SpecificationClient;
import com.leyou.commom.pojo.JsonUtils;
import com.leyou.commom.pojo.PageResult;
import com.leyou.item.bo.SpuBo;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.pojo.Goods;
import com.leyou.pojo.SearchRequest;
import com.leyou.repository.GoodsRepository;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhenglongfei 2019-11-22.
 *
 * @VERSION 1.0
 */
@Service
public class IndexService {
    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsRepository repository;


    public Goods buildGoods(SpuBo spu) {
        //
        Long id = spu.getId();
        //准备数据
        //sku集合
        List<Sku> skus = this.goodsClient.querySkuBySpuId(id);

        //spuDetail
        SpuDetail spuDetail = this.goodsClient.querySpuDetailById(id);

        //商品分类名称
        List<String> names = this.categoryClient.queryNameByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));

        //查询通用规格参数
        List<SpecParam> genericParas = this.specificationClient.querySpecParam(null, spu.getCid3(), true, true);

        //处理sku
        //把商品价格取出单独存放，便于展示
        List<Long> prices = new ArrayList<>();

        List<Map<String, Object>> skuList = new ArrayList<>();

        for (Sku sku : skus) {
            prices.add(sku.getPrice());
            Map<String, Object> skuMap = new HashMap<>();
            skuMap.put("id", sku.getId());
            skuMap.put("title", sku.getPrice());
            skuMap.put("image", StringUtils.isBlank(sku.getImages()) ? "" : sku.getImages().split(",")[0]);
            skuMap.put("price", sku.getPrice());
            skuList.add(skuMap);
        }

        //处理规格参数
        Map<Long, String> genericMap = JsonUtils.parseMap(spuDetail.getSpecifications(), Long.class, String.class);


        Map<Long, List<String>> specialMap = JsonUtils.nativeRead(spuDetail.getSpecTemplate(), new TypeReference<Map<Long, List<String>>>() {
        });

        //处理规格参数显示问题，默认显示id+值，处理后显示id对应的名称+值
        Map<String, Object> specs = new HashMap<>();

        for (SpecParam param : genericParas) {

            //通用参数
            String value = genericMap.get(param.getId());
            if (param.getNumeric()) {
                //数值类型需要加分段
                value = this.chooseSegment(value, param);
            }
            specs.put(param.getName(), value);
        }

        Goods goods = new Goods();
        goods.setId(spu.getId());
        //搜索条件拼接：这里如果要加品牌，可以再写个BrandClient，根据id查品牌
        goods.setAll(spu.getTitle() + " " + StringUtils.join(names, " "));
        goods.setSubTitle(spu.getSubTitle());
        goods.setBrandId(spu.getBrandId());
        goods.setCid1(spu.getCid1());
        goods.setCid2(spu.getCid2());
        goods.setCid3(spu.getCid3());
        goods.setCreateTime(spu.getCreateTime());
        goods.setPrice(prices);
        goods.setSkus(JsonUtils.serialize(skuList));
        goods.setSpecs(specs);

        return goods;
    }

    private String chooseSegment(String value, SpecParam p) {
        double val = NumberUtils.toDouble(value);
        String result = "其它";
        // 保存数值段
        for (String segment : p.getSegments().split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            double begin = NumberUtils.toDouble(segs[0]);
            double end = Double.MAX_VALUE;
            if (segs.length == 2) {
                end = NumberUtils.toDouble(segs[1]);
            }
            // 判断是否在范围内
            if (val >= begin && val < end) {
                if (segs.length == 1) {
                    result = segs[0] + p.getUnit() + "以上";
                } else if (begin == 0) {
                    result = segs[1] + p.getUnit() + "以下";
                } else {
                    result = segment + p.getUnit();
                }
                break;
            }
        }
        return result;
    }

    public PageResult<Goods> search(SearchRequest request) {
        String key = request.getKey();
        if (StringUtils.isBlank(key)) {
            // 如果用户没搜索条件，我们可以给默认的，或者返回null
            return null;
        }

        Integer page = request.getPage() - 1;// page 从0开始
        Integer size = request.getSize();

        // 1、创建查询构建器
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 2、查询
        // 2.1、对结果进行筛选
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{"id", "skus", "subTitle"}, null));
        // 2.2、基本查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("all", key));

        // 2.3、分页
        queryBuilder.withPageable(PageRequest.of(page, size));

        // 3、返回结果
        Page<Goods> result = this.repository.search(queryBuilder.build());

        // 4、解析结果
        long total = result.getTotalElements();
        long totalPage = (total + size - 1) / size;
        return new PageResult<>(total, totalPage, result.getContent());
    }
}
