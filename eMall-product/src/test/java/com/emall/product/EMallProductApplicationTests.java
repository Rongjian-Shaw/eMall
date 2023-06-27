package com.emall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.emall.product.dao.AttrGroupDao;
import com.emall.product.entity.BrandEntity;
import com.emall.product.service.BrandService;
import com.emall.product.service.CategoryService;
import com.emall.product.service.SkuSaleAttrValueService;
import com.emall.product.vo.SkuItemSaleAttrVo;
import com.emall.product.vo.SpuItemAttrGroupVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EMallProductApplicationTests {
    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    SkuSaleAttrValueService skuSaleAttrValueService;

    @Autowired
    AttrGroupDao attrGroupDao;

    @Test
    public void testGetCatelogPath(){
        Long[] catelogPath = categoryService.generateCatelogPath(225L);
        log.info("testGetCatelogPath: {}", Arrays.asList(catelogPath));
    }

    @Test
    public void contextLoads() {
        BrandEntity brandEntity = new BrandEntity();

//        brandEntity.setName("Xiaomi");
//        brandService.save(brandEntity);
//        brandEntity.setBrandId(1L);
//        brandEntity.setDescript("iphone");
//        brandService.updateById(brandEntity);
//        System.out.println("Save successfully");
        List<BrandEntity> list = brandService.list(new QueryWrapper<BrandEntity>().eq("brand_id", 1L));
        list.forEach((item)->{
            System.out.println(item);
        });
    }

    @Test
    public void testRedis() {
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("a", "hello world" + UUID.randomUUID().toString());
        String a = ops.get("a");
        System.out.println("a is " + a);
    }

    @Test
    public void testRedisson() {
        System.out.println(redissonClient);
    }

    @Test
    public void skuSaleAttrValueService() {
        List<SkuItemSaleAttrVo> saleAttrBySpuId = skuSaleAttrValueService.getSaleAttrBySpuId(13L);
        System.out.println(saleAttrBySpuId);
    }

    @Test
    public void testattrGroupDao() {
        List<SpuItemAttrGroupVo> attrGroupWithAttrsBySpuId = attrGroupDao.getAttrGroupWithAttrsBySpuId(13L, 225L);
        System.out.println(attrGroupWithAttrsBySpuId);
    }

}

class StringCollection {
    List<String> stringsCollection;
    public void addString(String string) {}

    public List<String> getStringsCollection() {
        return null;
    }
}