package com.emall.search.service.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import com.alibaba.fastjson.JSON;
import com.emall.common.es.SkuEsModel;
import com.emall.search.constant.EsConstant;
import com.emall.search.service.ProductSaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductSaveServiceImpl implements ProductSaveService {
    @Autowired
    ElasticsearchClient elasticsearchClient;
    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
        //1.在es中建立索引，建立映射关系（doc/json/product-mapping.json）

        //2. 在ES中保存这些数据
        BulkRequest.Builder br = new BulkRequest.Builder();
        for (SkuEsModel skuEsModel : skuEsModels) {
            //构造保存请求
            br.operations(op -> op
                    .index(idx -> idx
                            .index(EsConstant.PRODUCT_INDEX)
                            .id(skuEsModel.getSkuId().toString())
                            .document(skuEsModel)
                    )
            );
        }


        BulkResponse bulk = elasticsearchClient.bulk(br.build());

        //TODO 如果批量错误
        boolean hasFailures = bulk.errors();

        List<String> collect = bulk.items().stream().map(item -> {
            return item.id();
        }).collect(Collectors.toList());

        log.info("商品上架完成：{}",collect);

        return hasFailures;
    }
}
