package com.leyou.user.service;

import com.leyou.LyUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zhenglongfei 2019-11-25.
 *
 * @VERSION 1.0
 */
@SpringBootTest(classes = LyUserService.class)
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void checkData() {
        Boolean flag = this.userService.checkData("huge", 1);
        System.out.println(flag);
    }
}
