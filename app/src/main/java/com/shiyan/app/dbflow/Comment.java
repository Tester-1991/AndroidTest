package com.shiyan.app.dbflow;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * 作者: created by shiyan on 2018/10/12
 **/
@Table(database = AppDatabase.class, name = "Comment")
public class Comment extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public int commentId;

    @ForeignKey(stubbedRelationship = true,saveForeignKeyModel = false)
    public News news;

    @Column
    public String content;
}
