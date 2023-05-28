package com.emall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.emall.common.utils.PageUtils;
import com.emall.product.entity.SpuImagesEntity;

import java.util.Map;

/**
 * spu图片
 *
 * @author rongjian
 * @email rongjianxiao@gmail.com
 * @date 2023-05-28 16:40:22
 */
public interface SpuImagesService extends IService<SpuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

