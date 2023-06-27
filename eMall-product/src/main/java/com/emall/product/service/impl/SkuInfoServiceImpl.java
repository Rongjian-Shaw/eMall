package com.emall.product.service.impl;

import com.emall.product.entity.SkuImagesEntity;
import com.emall.product.entity.SpuInfoDescEntity;
import com.emall.product.entity.SpuInfoEntity;
import com.emall.product.service.*;
import com.emall.product.vo.*;
import com.mysql.cj.util.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.emall.common.utils.PageUtils;
import com.emall.common.utils.Query;

import com.emall.product.dao.SkuInfoDao;
import com.emall.product.entity.SkuInfoEntity;


@Service("skuInfoService")
public class SkuInfoServiceImpl extends ServiceImpl<SkuInfoDao, SkuInfoEntity> implements SkuInfoService {

    @Autowired
    private SkuImagesService skuImagesService;

    @Autowired
    private SpuInfoDescService spuInfoDescService;

    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    private ThreadPoolExecutor executor;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                new QueryWrapper<SkuInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuInfo(List<Skus> skus, SpuInfoEntity spuInfoEntity) {
        skus.forEach(sku -> {
            String defaultImg = "";
            for (Images image : sku.getImages()) {
                if(image.getDefaultImg() == 1) {
                    defaultImg = image.getImgUrl();
                    break;
                }
            }
            SkuInfoEntity skuInfoEntity = new SkuInfoEntity();
            BeanUtils.copyProperties(sku, skuInfoEntity);
            skuInfoEntity.setBrandId(spuInfoEntity.getBrandId());
            skuInfoEntity.setCatelogId(spuInfoEntity.getCatalogId());
            skuInfoEntity.setSaleCount(0L);
            skuInfoEntity.setSpuId(spuInfoEntity.getId());
            skuInfoEntity.setSkuDefaultImg(defaultImg);
            this.save(skuInfoEntity);
        });
    }

    @Override
    public PageUtils queryPageByCondition(Map<String, Object> params) {
        QueryWrapper<SkuInfoEntity> skuInfoEntityQueryWrapper = new QueryWrapper<>();
        String key = (String) params.get("key");
        if(!StringUtils.isNullOrEmpty(key)){
            skuInfoEntityQueryWrapper.and(wrapper -> {
                wrapper.eq("sku_id", key).or().like("sku_name", key);
            });
        }
        String catelogId = (String) params.get("catelogId");
        if(!StringUtils.isNullOrEmpty(catelogId) && !"0".equalsIgnoreCase(catelogId)){
            skuInfoEntityQueryWrapper.eq("catelog_id", catelogId);
        }
        String brandId = (String) params.get("brandId");
        if(!StringUtils.isNullOrEmpty(brandId) && !"0".equalsIgnoreCase(brandId)){
            skuInfoEntityQueryWrapper.eq("brand_id", brandId);
        }
        String min = (String) params.get("min");
        if(!StringUtils.isNullOrEmpty(min)){
             skuInfoEntityQueryWrapper.ge("price", min);
        }
        String max = (String) params.get("max");
        if(!StringUtils.isNullOrEmpty(max)){
            BigDecimal bigDecimal = new BigDecimal(max);
            try {
                if(bigDecimal.compareTo(new BigDecimal("0")) == 1) {
                    skuInfoEntityQueryWrapper.le("price", max);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        IPage<SkuInfoEntity> page = this.page(
                new Query<SkuInfoEntity>().getPage(params),
                skuInfoEntityQueryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public List<SkuInfoEntity> getSkusBySpuId(Long spuId) {
        List<SkuInfoEntity> skuInfoEntities = this.list(new QueryWrapper<SkuInfoEntity>().eq("spu_id", spuId));

        return skuInfoEntities;
    }

    @Override
    public SkuItemVo item(Long skuId) {
        SkuItemVo skuItemVo = new SkuItemVo();

        CompletableFuture<SkuInfoEntity> infoFuture = CompletableFuture.supplyAsync(() -> {
            //1、sku基本信息的获取  pms_sku_info
            SkuInfoEntity skuInfoEntity = this.getById(skuId);
            skuItemVo.setInfo(skuInfoEntity);
            return skuInfoEntity;
        }, executor);

        CompletableFuture<Void> saleAttrFuture = infoFuture.thenAcceptAsync((res) -> {
            //3、获取spu的销售属性组合
            List<SkuItemSaleAttrVo> saleAttrVos = skuSaleAttrValueService.getSaleAttrBySpuId(res.getSpuId());
            skuItemVo.setSaleAttr(saleAttrVos);
        }, executor);

        CompletableFuture<Void> descFuture = infoFuture.thenAcceptAsync((res) -> {
            //4、获取spu的介绍    pms_spu_info_desc
            SpuInfoDescEntity spuInfoDescEntity = spuInfoDescService.getById(res.getSpuId());
            skuItemVo.setDesc(spuInfoDescEntity);
        }, executor);

        CompletableFuture<Void> baseAttrFuture = infoFuture.thenAcceptAsync((res) -> {
            //5、获取spu的规格参数信息
            List<SpuItemAttrGroupVo> attrGroupVos = attrGroupService.getAttrGroupWithAttrsBySpuId(res.getSpuId(), res.getCatelogId());
            skuItemVo.setGroupAttrs(attrGroupVos);
        }, executor);

        //2、sku的图片信息    pms_sku_images
        CompletableFuture<Void> imageFuture = CompletableFuture.runAsync(() -> {
            List<SkuImagesEntity> imagesEntities = skuImagesService.getImagesBySkuId(skuId);
            skuItemVo.setImages(imagesEntities);
        }, executor);

        //等到所有任务都完成
        CompletableFuture.allOf(saleAttrFuture, descFuture, baseAttrFuture, imageFuture);
        return skuItemVo;
    }

}