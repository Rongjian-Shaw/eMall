package com.emall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.emall.common.utils.PageUtils;
import com.emall.product.entity.SpuInfoEntity;
import com.emall.product.vo.SpuSaveVo;

import java.util.Map;

/**
 * spu信息
 *
 * @author rongjian
 * @email rongjianxiao@gmail.com
 * @date 2023-05-28 16:40:22
 */
public interface SpuInfoService extends IService<SpuInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveSpuInfo(SpuSaveVo spuSaveVo);

    PageUtils queryPageByCondition(Map<String, Object> params);

    /**
     * 商品上架
     * @param spuId
     */
    void up(Long spuId);
}

