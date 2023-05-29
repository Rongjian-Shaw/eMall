package com.emall.coupon.service.impl;

import com.emall.common.to.MemberPrice;
import com.emall.common.to.SkuReductionTo;
import com.emall.coupon.entity.MemberPriceEntity;
import com.emall.coupon.entity.SkuLadderEntity;
import com.emall.coupon.service.MemberPriceService;
import com.emall.coupon.service.SkuLadderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.emall.common.utils.PageUtils;
import com.emall.common.utils.Query;

import com.emall.coupon.dao.SkuFullReductionDao;
import com.emall.coupon.entity.SkuFullReductionEntity;
import com.emall.coupon.service.SkuFullReductionService;


@Service("skuFullReductionService")
public class SkuFullReductionServiceImpl extends ServiceImpl<SkuFullReductionDao, SkuFullReductionEntity> implements SkuFullReductionService {

    @Autowired
    SkuLadderService skuLadderService;

    @Autowired
    MemberPriceService memberPriceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SkuFullReductionEntity> page = this.page(
                new Query<SkuFullReductionEntity>().getPage(params),
                new QueryWrapper<SkuFullReductionEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveSkuReduction(SkuReductionTo skuReductionTo) {
        if(skuReductionTo.getFullCount() > 0) {
            // sms_sku_ladder
            SkuLadderEntity skuLadderEntity = new SkuLadderEntity();
            skuLadderEntity.setSkuId(skuReductionTo.getSkuId());
            skuLadderEntity.setFullCount(skuReductionTo.getFullCount());
            skuLadderEntity.setDiscount(skuReductionTo.getDiscount());
            skuLadderEntity.setAddOther(skuReductionTo.getCountStatus());
            skuLadderService.save(skuLadderEntity);
        }


        if(skuReductionTo.getFullPrice().compareTo(new BigDecimal(0)) == 1) {
            // sms_sku_full_reduction
            SkuFullReductionEntity skuFullReductionEntity = new SkuFullReductionEntity();
            BeanUtils.copyProperties(skuReductionTo, skuFullReductionEntity);
            this.save(skuFullReductionEntity);
        }


        // sms_member_price
        List<MemberPrice> memberPrices = skuReductionTo.getMemberPrice();
        if(memberPrices != null || memberPrices.size() > 0) {
            List<MemberPriceEntity> memberPriceEntities = memberPrices.stream().map(memberPrice -> {
                MemberPriceEntity memberPriceEntity = new MemberPriceEntity();
                memberPriceEntity.setSkuId(skuReductionTo.getSkuId());
                memberPriceEntity.setMemberLevelId(memberPrice.getId());
                memberPriceEntity.setMemberLevelName(memberPrice.getName());
                memberPriceEntity.setMemberPrice(memberPrice.getPrice());
                memberPriceEntity.setAddOther(1);
                return memberPriceEntity;
            }).filter(memberPriceEntity -> {
                return memberPriceEntity.getMemberPrice().compareTo(new BigDecimal(0)) == 1;
            }).collect(Collectors.toList());
            memberPriceService.saveBatch(memberPriceEntities);
        }

    }

}