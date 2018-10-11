package com.shiyan.app.dbflow;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * 作者: created by shiyan on 2018/10/10
 **/
@Table(database = AppDatabase.class)
public class  UserData extends BaseModel{

    @PrimaryKey(autoincrement = true)
    public long id;

    /**
     * 姓名
     */
    @Column
    public String name;

    /**
     * 年龄
     */
    @Column
    public int age;

    /**
     * 性别
     */
    @Column
    public boolean sex;
}
