package com.emall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.emall.common.utils.PageUtils;
import com.emall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author rongjian
 * @email rongjianxiao@gmail.com
 * @date 2023-05-07 11:33:21
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

