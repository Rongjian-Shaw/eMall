package com.emall.product.service.impl;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.emall.common.utils.PageUtils;
import com.emall.common.utils.Query;

import com.emall.product.dao.CategoryDao;
import com.emall.product.entity.CategoryEntity;
import com.emall.product.service.CategoryService;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 1. Get all catagory
        List<CategoryEntity> entities = baseMapper.selectList(null);

        // 2. wrap it to parent-child tree structure
        // 2.1 Get all first-level catagory
        List<CategoryEntity> level1Menus = entities.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == 0)
                .map((menu) -> {
                    menu.setChildren(getChildrens(menu, entities));
                    return menu;
                })
                .sorted((menu1, menu2) ->
                        (menu1.getSort()==null?0: menu1.getSort()) - (menu2.getSort()==null?0: menu2.getSort()))
                .collect(Collectors.toList());

        return level1Menus;
    }

    // Get all children catagory recursily
    private List<CategoryEntity> getChildrens(CategoryEntity root, List<CategoryEntity> all) {
        List<CategoryEntity> children = all.stream()
                .filter(categoryEntity -> categoryEntity.getParentCid() == root.getCatId())
                .map(categoryEntity -> {
                    categoryEntity.setChildren(getChildrens(categoryEntity, all));
                    return categoryEntity;
                })
                .sorted((menu1, menu2) ->
                        (menu1.getSort()==null?0: menu1.getSort()) - (menu2.getSort()==null?0: menu2.getSort()))
                .collect(Collectors.toList());

        return children;
    }

}