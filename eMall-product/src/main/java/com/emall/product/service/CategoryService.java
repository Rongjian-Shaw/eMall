package com.emall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.emall.common.utils.PageUtils;
import com.emall.product.entity.CategoryEntity;

import java.util.List;
import java.util.Map;

/**
 * 商品三级分类
 *
 * @author rongjian
 * @email rongjianxiao@gmail.com
 * @date 2023-05-05 16:54:47
 */
public interface CategoryService extends IService<CategoryEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<CategoryEntity> listWithTree();
}

