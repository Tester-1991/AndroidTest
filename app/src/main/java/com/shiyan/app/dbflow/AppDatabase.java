package com.shiyan.app.dbflow;

import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.Migration;
import com.raizlabs.android.dbflow.sql.SQLiteType;
import com.raizlabs.android.dbflow.sql.migration.AlterTableMigration;

/**
 * 作者: created by shiyan on 2018/10/10
 **/

@Database(version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "AppDatabase";

    public static final int VERSION = 4;


    /**
     * 数据库升级迁移
     */
    @Migration(version = AppDatabase.VERSION,database = AppDatabase.class)
    public static class AddEmailToUserMigration extends AlterTableMigration<UserData>{

        public AddEmailToUserMigration(Class<UserData> table) {
            super(table);
        }

        @Override
        public void onPreMigrate() {
            super.onPreMigrate();

//            addColumn(SQLiteType.TEXT,"email");
        }
    }
}
