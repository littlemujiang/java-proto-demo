package com;

import com.controler.PageSelect;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by chenxl7 on 2017/8/30.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PageSelect.class)
public class PageSelectTest {

    @Autowired
    PageSelect pageSelect;


    @Test
    public void test(){


//        String cursor = String.valueOf(System.currentTimeMillis());

        pageSelect.getSelectResult(30);

        System.out.println("~~~~ Next Pages");

        pageSelect.getSelectResult(30);

    }


}
