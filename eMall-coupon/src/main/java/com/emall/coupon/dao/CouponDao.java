package com.emall.coupon.dao;

import com.emall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author rongjian
 * @email rongjianxiao@gmail.com
 * @date 2023-05-06 18:26:12
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
