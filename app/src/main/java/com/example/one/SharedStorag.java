package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/*    SharedPreferences存储
      SharedPreferences是使用键值对的方式来存储数据
      支持不同的数据类型存储
*/
public class SharedStorag extends AppCompatActivity {

    Button btn_save,btn_read;
    TextView txt_headline;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_storag);
        intViews();
    }

    private void intViews() {
        btn_read = findViewById(R.id.btn_read);
        btn_save = this.findViewById(R.id.btn_save);
        txt_headline = findViewById(R.id.txt_headline);
        img_back = findViewById(R.id.img_back);
        txt_headline.setText("SharedPreferences存储");
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });
        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Read();
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Read() {
        SharedPreferences sharedPreferences = getSharedPreferences("SpData",MODE_PRIVATE);
        String name = sharedPreferences.getString("name","");
        int age = sharedPreferences.getInt("age",0);
        String major = sharedPreferences.getString("major","");
        Boolean married = sharedPreferences.getBoolean("married",false);

        String msg = name+age+major+married;
        Log.d("123", "Read: "+msg);
        Toast.makeText(SharedStorag.this,msg,Toast.LENGTH_LONG).show();
    }

    private void Save() {
        SharedPreferences.Editor editor = (SharedPreferences.Editor)getSharedPreferences("SpData",MODE_PRIVATE).edit();
//        SharedPreferences.Editor editor = (SharedPreferences.Editor) SharedpreferencesStorag.this.getSharedPreferences("SpData",MODE_PRIVATE);
        editor.putString("name","Li");
        editor.putInt("age",20);
        editor.putString("major","移动互联应用技术");
        editor.putBoolean("married",false);
        editor.commit();
    }
}

