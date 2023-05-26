package com.emall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.emall.product.entity.BrandEntity;
import com.emall.product.service.BrandService;
import com.emall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class EMallProductApplicationTests {
    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

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



}
