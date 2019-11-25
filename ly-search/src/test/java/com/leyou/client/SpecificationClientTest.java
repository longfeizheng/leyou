package com.leyou.client;

import com.leyou.item.pojo.SpecParam;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by zhenglongfei 2019-11-22.
 *
 * @VERSION 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SpecificationClientTest {

    @Autowired
    private SpecificationClient specificationClient;


    @Test
    public void testQuerySpecParam() {
        List<SpecParam> specParams = this.specificationClient.querySpecParam(1l, 1l, true, true);
        specParams.forEach(e -> System.out.println(e));
    }
}
