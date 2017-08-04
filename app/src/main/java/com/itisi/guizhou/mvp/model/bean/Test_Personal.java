package com.itisi.guizhou.mvp.model.bean;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * **********************
 * 功 能:必填必填必填必填必填必填
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/8/4 9:46
 * 修改人:itisi
 * 修改时间: 2017/8/4 9:46
 * 修改内容:itisi
 * *********************
 */

public class Test_Personal extends RealmObject {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @PrimaryKey
    private String id;
    private String name;
    private int age;

    public Test_Personal() {
    }

    public Test_Personal(String id, String name, int age) {
        this.id=id;
        this.name = name;
        this.age = age;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
