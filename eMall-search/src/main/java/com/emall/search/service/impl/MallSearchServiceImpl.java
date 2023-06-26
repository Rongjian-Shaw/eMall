package com.emall.search.service.impl;

import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.alibaba.fastjson.JSON;
import com.emall.search.service.MallSearchService;
import com.emall.search.vo.SearchParam;
import com.emall.search.vo.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MallSearchServiceImpl implements MallSearchService {

    @Override
    public SearchResult search(SearchParam param) {

        //1、动态构建出查询需要的DSL语句
        SearchResult result = null;

//        //1、准备检索请求
//        SearchRequest searchRequest = buildSearchRequest(param);
//
//        try {
//            //2、执行检索请求
//            SearchResponse response = esRestClient.search(searchRequest, GulimallElasticSearchConfig.COMMON_OPTIONS);
//
//            //3、分析响应数据，封装成我们需要的格式
//            result = buildSearchResult(response,param);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        return result;
    }
}
