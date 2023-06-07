package com.emall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.emall.common.utils.PageUtils;
import com.emall.product.entity.SkuInfoEntity;
import com.emall.product.entity.SpuInfoEntity;
import com.emall.product.vo.Skus;

import java.util.List;
import java.util.Map;

/**
 * sku信息
 *
 * @author rongjian
 * @email rongjianxiao@gmail.com
 * @date 2023-05-05 16:54:47
 */
public interface SkuInfoService extends IService<SkuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuInfo(List<Skus> skus, SpuInfoEntity spuInfoEntity);

    PageUtils queryPageByCondition(Map<String, Object> params);
}

