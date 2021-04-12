package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SqliteDatebasebStore extends AppCompatActivity {

    Button btn_new,btn_add,btn_mod,btn_delete,btn_inq;
    Mydatabase mydatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_datebaseb_store);
        initViews();
    }

    private void initViews() {
//        context 数据库名 null 数据库的版本号 用于对数据库进行升级操作0
        mydatabase = new Mydatabase(SqliteDatebasebStore.this,"Database",null,2);
        btn_new = findViewById(R.id.btn_new);
        btn_add = findViewById(R.id.btn_add);
        btn_delete =findViewById(R.id.btn_delete);
        btn_mod = findViewById(R.id.btn_mod);
        btn_inq = findViewById(R.id.btn_inq);
        btn_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mydatabase.getWritableDatabase();
            }
        });

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                先获取SQLiteDatabase对象,在使用ContentValues对要添加的数据进行组装
                SQLiteDatabase db = mydatabase.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name","移动互联1902");
                values.put("author","19级");
                values.put("pages",100);
                values.put("price",20.2);
//                SQLiteDatabase提供的inset()方法接受三个参数：
//                表名，
//                在未指定添加数据的情况下给某些可为空的列自动赋值null，一般用不到这个功能，直接传入null即可
//                ContentValues对象,他提供一系列put()方法重载,向ContentValues中添加数据,只需要将表中的每个列名以及相应的待添加数据传入即可
                db.insert("book",null,values);
            }
        });

        btn_mod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                在修改数据按钮的点击事件里面构建了一个ContentValues对象，
//                给它指定一组数据，把价格这一列的数据更新成10.2。
//                再调用SQLiteDatabase的update()方法执行更新操作。
                SQLiteDatabase db = mydatabase.getWritableDatabase();
                ContentValues contentValues = new ContentValues();
                contentValues.put("price",10.2);
                contentValues.put("name","移动互联1901");
//              SQLiteDatabase提供了update()方法用于对数据进行修改更新，这个方法接收4个参数。
//              参数1：表名
//              参数2：ContentValues对象，组装更新数据
//              参数3、4：用于约束更新某一行或某几行中的数据，默认更新所有行
                db.update("book",contentValues,"name=?",new String[]{"移动互联1902"});
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mydatabase.getWritableDatabase();
//              SQLiteDatabase提供delete()方法，用于删除数据，这个方法接收3个参数。
//              参数1：表名
//              参数2、3用于约束删除某一行或某几行的数据，默认就是删除所有行。
                db.delete("book","id>?",new String[]{"1"});
            }
        });

//        getWritableDatabsae 可读可写
        btn_inq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = mydatabase.getWritableDatabase();
//                SQLiteDatabase提供query()方法用于对数据进行查询。
                Cursor cursor = db.query("book",null,null,null,null,null,null);
                StringBuilder stringBuilder = new StringBuilder();
//                query()方法返回Cursor对象，调用Cursor对象的moveToFirst ()方法将数据的指针移动到第一行的位置，
//                然后进入一个循环，遍历查询到的每一行数据。
//                在循环中通过Cursor的getColumnIndex ()方法获取某一列在表中对应的位置索引，
//                将这个索引传人到相应的取值方法中，得到从数据库中读取的数据。
//                通过Toast方式显示查询的数据内容。最后调用close()方法关闭Cursor。
                if (cursor.moveToFirst()){
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        stringBuilder.append(name+"\t"+author+"\t"+pages+"\t"+price+"\t"+"\n");
                    }while (cursor.moveToNext());
                    Toast.makeText(SqliteDatebasebStore.this,stringBuilder.toString(),Toast.LENGTH_SHORT).show();
                }
                cursor.close();
            }
        });
    }
}
