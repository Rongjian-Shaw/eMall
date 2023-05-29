package com.emall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.emall.common.utils.PageUtils;
import com.emall.product.entity.SkuImagesEntity;
import com.emall.product.vo.Skus;

import java.util.List;
import java.util.Map;

/**
 * sku图片
 *
 * @author rongjian
 * @email rongjianxiao@gmail.com
 * @date 2023-05-05 16:54:47
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSkuImages(List<Skus> skus);
}

