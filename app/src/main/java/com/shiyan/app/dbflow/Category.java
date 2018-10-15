package com.shiyan.app.dbflow;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * 作者: created by shiyan on 2018/10/15
 **/
@Table(database = AppDatabase.class,name = "Category")
public class Category extends BaseModel{

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String name;
}
