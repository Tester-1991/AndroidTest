package com.shiyan.app.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ITransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.shiyan.app.R;
import com.shiyan.app.dbflow.AppDatabase;
import com.shiyan.app.dbflow.Category;
import com.shiyan.app.dbflow.Comment;
import com.shiyan.app.dbflow.Introduction;
import com.shiyan.app.dbflow.News;
import com.shiyan.app.dbflow.News_Category;
import com.shiyan.app.dbflow.News_Category_Table;
import com.shiyan.app.dbflow.News_Table;
import com.shiyan.app.dbflow.UserData;
import com.shiyan.app.dbflow.UserData_Table;

import java.util.ArrayList;
import java.util.List;

public class DBFlowActivity extends BaseActivity {

    private Button btn_add;

    private Button btn_toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbflow);

        btn_add = findViewById(R.id.btn_add);

        btn_toast = findViewById(R.id.btn_toast);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManyToManyByQuery();
            }
        });

        btn_toast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("hello");
            }
        });
    }

    private void ManyToManyByAdd() {

        News news = new News();
        news.title = "标题一";
        news.content = "标题一内容";
        news.insert();

        for(int i = 0;i < 2;i++){
            Category category = new Category();
            category.name = "属性" + i;
            category.insert();

            News_Category news_category = new News_Category();
            news_category.setCategory(category);
            news_category.setNews(news);
            news_category.save();
        }
    }

    private void ManyToManyByQuery(){
        List<News_Category> news_categories = SQLite.select().from(News_Category.class).queryList();

        for(News_Category news_category:news_categories){
            Log.e("news_category",news_category.getCategory().name);
            Log.e("news_category",news_category.getNews().title);
        }
    }


    public void add() {
        UserData userData = new UserData();
        userData.age = 18;
        userData.name = "shiyan";
        userData.sex = true;

        userData.insert();
    }

    /**
     * 批量添加(非事物处理)
     */
    public void addAll() {
        for (int i = 0; i < 1000 * 10; i++) {
            UserData userData = new UserData();
            userData.age = 18;
            userData.name = "shiyan";
            userData.sex = true;

            userData.async().save();
        }
    }

    /**
     * 批量添加(事物处理)
     */
    public void addAllByTransaction() {
        Transaction transaction = FlowManager.getDatabase(AppDatabase.class).beginTransactionAsync(new ITransaction() {
            @Override
            public void execute(DatabaseWrapper databaseWrapper) {
                for (int i = 0; i < 10000 * 10; i++) {
                    UserData userData = new UserData();
                    userData.age = 18;
                    userData.name = "shiyan";
                    userData.sex = true;

                    userData.save();
                }
            }
        }).build();

        transaction.execute();
    }

    private void update() {
        UserData userData = SQLite.select().from(UserData.class)
                .where(UserData_Table.age.eq(18))
                .querySingle();

        userData.sex = false;

        userData.update();
    }

    private void update2() {
        SQLite.update(UserData.class)
                .set(UserData_Table.name.eq("123"))
                .where(UserData_Table.age.eq(18))
                .executeUpdateDelete();
    }

    public void queryAll() {
        List<UserData> datas = SQLite.select().from(UserData.class)
                .queryList();
        for (int i = 0; i < datas.size(); i++) {
            LogUtils.eTag("name", datas.get(i).name + i);
        }
    }

    public void queryAll2() {
        CursorResult<UserData> results = SQLite.select().from(UserData.class)
                .queryResults();

        for (int i = 0; i < results.getCount(); i++) {
            LogUtils.eTag("name", results.getItem(i).name + i);
        }
    }

    /**
     * 一对一添加数据
     */
    public void oneToOneByAdd() {
        News news = new News();

        news.title = "标题一";

        news.content = "标题一的内容";

        news.introduction = new Introduction();

        news.introduction.digest = "标题一摘要";

        news.introduction.guide = "标题一介绍";

        news.introduction.id = news.id;

        news.introduction.insert();

        news.insert();
    }

    public void oneToOneByDelete() {
        News news = SQLite.select().from(News.class).where(News_Table.title.eq("标题一")).querySingle();

        news.introduction.delete();

        news.delete();
    }


    /**
     * 一对多添加数据
     */
    public void oneToManyByAdd() {
        News news = new News();

        news.title = "标题一";

        news.content = "标题一的内容";

        //添加简介表
        news.introduction = new Introduction();

        news.introduction.digest = "标题一摘要";

        news.introduction.guide = "标题一介绍";

        news.introduction.id = news.id;

        news.introduction.insert();

        news.insert();

        //添加评论表
        news.comments = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Comment comment = new Comment();
            comment.news = new News();
            comment.news.id = news.id;
            comment.content = "评论内容" + i;
            comment.insert();
            news.comments.add(comment);
        }
    }

    public void oneToManyByQuery() {
        News news = SQLite.select().from(News.class).where(News_Table.title.eq("标题一")).querySingle();

        for (int i = 0; i < news.getComments().size(); i++) {
            LogUtils.eTag("oneToManyByQuery", news.getComments().get(i).content);
        }
    }
}
