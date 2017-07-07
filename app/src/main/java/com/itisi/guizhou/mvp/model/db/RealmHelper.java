package com.itisi.guizhou.mvp.model.db;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * **********************
 * 功 能:Realm 数据库 操作
 * 创建人:itisi
 * 邮  箱:itisivip@qq.com
 * 创建时间:2017/7/5 17:27
 * 修改人:itisi
 * 修改时间: 2017/7/5 17:27
 * 修改内容:itisi
 * *********************
 */

public class RealmHelper implements DBHelper {
    /**
     * 数据库 名称
     */
    private static final String DB_NAME = "myRealm.realm";

    private Realm mRealm;

    @Inject
    public RealmHelper() {
        mRealm = Realm.getInstance(new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name(DB_NAME)
                .build());

    }

    @Override
    public boolean queryNewsId(int id) {
        return false;
    }
}
