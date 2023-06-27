package com.emall.product.service.impl;

import com.emall.product.vo.Skus;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.emall.common.utils.PageUtils;
import com.emall.common.utils.Query;

import com.emall.product.dao.SkuImagesDao;
import com.emall.product.entity.SkuImagesEntity;
import com.emall.product.service.SkuImagesService;


@Service("skuImagesService")
public class SkuImagesServiceImpl extends ServiceImpl<SkuImagesDao, SkuImagesEntity> implements SkuImagesService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuImagesEntity> page = this.page(
                new Query<SkuImagesEntity>().getPage(params),
                new QueryWrapper<SkuImagesEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuImages(List<Skus> skus) {
//        skus.forEach(sku -> {
//            sku.getImages().stream().map(img -> {
//                SkuImagesEntity skuImagesEntity = new SkuImagesEntity();
//                BeanUtils.copyProperties(img, skuImagesEntity);
//                skuImagesEntity.setSkuId();
//            })
//        });
    }

    @Override
    public List<SkuImagesEntity> getImagesBySkuId(Long skuId) {
        List<SkuImagesEntity> skuImagesEntities = this.baseMapper.selectList(new QueryWrapper<SkuImagesEntity>().eq("sku_id", skuId));
        return skuImagesEntities;
    }

}