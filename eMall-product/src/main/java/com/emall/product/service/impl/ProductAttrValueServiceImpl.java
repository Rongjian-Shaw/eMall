package com.emall.product.service.impl;

import com.emall.product.entity.AttrEntity;
import com.emall.product.service.AttrService;
import com.emall.product.vo.BaseAttrs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.emall.common.utils.PageUtils;
import com.emall.common.utils.Query;

import com.emall.product.dao.ProductAttrValueDao;
import com.emall.product.entity.ProductAttrValueEntity;
import com.emall.product.service.ProductAttrValueService;


@Service("productAttrValueService")
public class ProductAttrValueServiceImpl extends ServiceImpl<ProductAttrValueDao, ProductAttrValueEntity> implements ProductAttrValueService {

    @Autowired
    AttrService attrService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ProductAttrValueEntity> page = this.page(
                new Query<ProductAttrValueEntity>().getPage(params),
                new QueryWrapper<ProductAttrValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveProductAttrByBaseAttrs(List<BaseAttrs> baseAttrs, Long spuId) {
        List<ProductAttrValueEntity> productAttrValueEntities = baseAttrs.stream().map(baseAttr -> {
            ProductAttrValueEntity productAttrValueEntity = new ProductAttrValueEntity();
            productAttrValueEntity.setAttrId(baseAttr.getAttrId());
            productAttrValueEntity.setAttrValue(baseAttr.getAttrValues());
            productAttrValueEntity.setQuickShow(baseAttr.getShowDesc());
            AttrEntity attrEntity = attrService.getById(baseAttr.getAttrId());
            if (attrEntity != null) {
                productAttrValueEntity.setAttrName(attrEntity.getAttrName());
            }
            productAttrValueEntity.setSpuId(spuId);

            return productAttrValueEntity;
        }).collect(Collectors.toList());

        this.saveBatch(productAttrValueEntities);
    }

}