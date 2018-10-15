package com.shiyan.app.dbflow;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * 作者: created by shiyan on 2018/10/12
 **/
@Table(database = AppDatabase.class, name = "News")
@ManyToMany(referencedTable = Category.class)
public class News extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public int id;

    @Column
    public String title;

    @Column
    public String content;

    @ForeignKey(stubbedRelationship = true)
    public Introduction introduction;

    public List<Comment> comments;


    @OneToMany(methods = {OneToMany.Method.ALL}, variableName = "comments")
    public List<Comment> getComments() {
        if (comments == null || comments.isEmpty()) {
            comments = SQLite.select().from(Comment.class).where(Comment_Table.news_id.eq(id)).queryList();
        }
        return comments;
    }
}
