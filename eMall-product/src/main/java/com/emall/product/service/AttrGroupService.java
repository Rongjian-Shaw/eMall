package com.emall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.emall.common.utils.PageUtils;
import com.emall.product.entity.AttrGroupEntity;

import java.util.Map;

/**
 * 属性分组
 *
 * @author rongjian
 * @email rongjianxiao@gmail.com
 * @date 2023-05-05 16:54:47
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catelogId);
}

