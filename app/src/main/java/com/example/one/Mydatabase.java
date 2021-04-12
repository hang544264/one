package com.example.one;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Mydatabase extends SQLiteOpenHelper {

    public static final String CREATE_BOOK ="create table Book("
            +"id integer primary key autoincrement,"
            +"author text,"
            +"price real,"
            +"pages integer,"
            +"name text)";

    public static final String  CREATE_CATEGORY = "create table Category(" +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer)";

    Context context;

    public Mydatabase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

//  创建数据库时会调用，我们需要在里面创建我们的数据表。
//  onCreate只会调用一次，如果数据库已经存在，那么打开数据库是不会回调onCreate的。
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(context,"Create Database",Toast.LENGTH_SHORT).show();
    }

//    升级数据库时会调用，我们可以在这里面做升级操作。
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
    }

//    onOpen: 打开数据库时会调用到。和onCreate有所区别的是，onCreate只在创建数据库时会调用，而onOpen在每次打开数据库时都会调用。
//    onConfigure:配置数据库连接。比如设置外键约束等。

}
