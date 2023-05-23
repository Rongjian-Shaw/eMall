package com.emall.product;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.emall.product.entity.BrandEntity;
import com.emall.product.service.BrandService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EMallProductApplicationTests {
    @Autowired
    BrandService brandService;

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
