package com.itisi.guizhou;

import com.itisi.guizhou.mvp.model.db.RealmHelper;

import org.junit.Test;

/**
 * **********************
 * 功 能:realm 数据库测试
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/4 10:25
 * 修改人:itisi
 * 修改时间: 2017/8/4 10:25
 * 修改内容:itisi
 * *********************
 */

public class RealmTest {
    @Test
    public void test_insert(){
        new RealmHelper().test_insert();
    }
}
