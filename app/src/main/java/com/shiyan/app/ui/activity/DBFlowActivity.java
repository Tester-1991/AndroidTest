package com.shiyan.app.ui.activity;

import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.raizlabs.android.dbflow.sql.language.CursorResult;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.shiyan.app.R;
import com.shiyan.app.dbflow.UserData;
import com.shiyan.app.dbflow.UserData_Table;

import java.util.List;

public class DBFlowActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbflow);


    }

    public void add(){
        UserData userData = new UserData();
        userData.age = 18;
        userData.name = "shiyan";
        userData.sex = true;

        userData.insert();
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

    public void queryAll(){
        List<UserData> datas = SQLite.select().from(UserData.class)
                .queryList();
        for(int i = 0;i<datas.size();i++){
            LogUtils.eTag("name",datas.get(i).name + i);
        }
    }

    public void queryAll2(){
        CursorResult<UserData> results = SQLite.select().from(UserData.class)
                .queryResults();

        for(int i = 0;i<results.getCount();i++){
            LogUtils.eTag("name",results.getItem(i).name + i);
        }
    }
}
