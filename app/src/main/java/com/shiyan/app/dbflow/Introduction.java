package com.shiyan.app.dbflow;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * 作者: created by shiyan on 2018/10/12
 **/
@Table(database = AppDatabase.class,name = "Introduction")
public class Introduction extends BaseModel {

    @PrimaryKey
    public int id;

    @Column
    public String guide;

    @Column
    public String digest;
}
