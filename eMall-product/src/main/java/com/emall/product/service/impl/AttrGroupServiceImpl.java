package com.emall.product.service.impl;

import com.mysql.cj.util.StringUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.emall.common.utils.PageUtils;
import com.emall.common.utils.Query;

import com.emall.product.dao.AttrGroupDao;
import com.emall.product.entity.AttrGroupEntity;
import com.emall.product.service.AttrGroupService;


@Service("attrGroupService")
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupDao, AttrGroupEntity> implements AttrGroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrGroupEntity> page = this.page(
                new Query<AttrGroupEntity>().getPage(params),
                new QueryWrapper<AttrGroupEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long catelogId) {
        if(catelogId == 0) {
            return this.queryPage(params);
        } else {
            // select * from pms_attr_group where catelog_id=? and (attr_group_id=key or attr_group_name like key)
            String key = (String) params.get("key");
            QueryWrapper<AttrGroupEntity> wrapper = new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId);
            if(!StringUtils.isNullOrEmpty("key")) {
                wrapper.and(obj -> {
                    obj.eq("attr_group_id", key).or().like("attr_group_name", key);
                });
            }
            IPage<AttrGroupEntity> page = this.page(
                    new Query<AttrGroupEntity>().getPage(params),
                    wrapper
            );
            return new PageUtils(page);
        }
    }

}