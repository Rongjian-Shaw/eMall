package com.emall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.emall.common.utils.PageUtils;
import com.emall.product.entity.SpuCommentEntity;

import java.util.Map;

/**
 * 商品评价
 *
 * @author rongjian
 * @email rongjianxiao@gmail.com
 * @date 2023-05-28 16:40:22
 */
public interface SpuCommentService extends IService<SpuCommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

