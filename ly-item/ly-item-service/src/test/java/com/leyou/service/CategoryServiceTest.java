package com.leyou.service;


import com.leyou.item.pojo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by zhenglongfei 2019-11-14.
 *
 * @VERSION 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryServiceTest {


    @Autowired
    private CategoryService categoryService;

    @Test
    public void testQueryListByParent() throws Exception {
        List<Category> categories = categoryService.queryListByParent(0l);
        System.out.println(categories.size());
    }
}