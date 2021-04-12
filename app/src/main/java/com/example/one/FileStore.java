package com.example.one;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/*
 *    用文件存储的方式存取数据
 *    数据以文件方式存储到设备的内部，默认位置为/data/data/com.example.one/files/data
 *    文件被其所创建的app私有，当创建的app被卸载时，文件也随之被删除
 *    FileOutputstream fos = openFileOutput(String name, int mode);
 *    文件的操作模式为MODE_PRIVATE和MODE_APPEND
 *    Filelnputstream fis = openFileInput(String name);
 */

public class FileStore extends AppCompatActivity {

    EditText edit_data;
    Button btn_input,btn_output,btn_delete,btn_shared;
    TextView txt_headline;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intViews();
    }

    private void intViews() {
        edit_data = findViewById(R.id.edit_data);
        btn_input = findViewById(R.id.btn_input);
        btn_output = findViewById(R.id.btn_output);
        btn_delete = findViewById(R.id.btn_delete);
        txt_headline = findViewById(R.id.txt_headline);
        img_back = findViewById(R.id.img_back);
        btn_shared = findViewById(R.id.btn_shared);
        btn_shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(FileStore.this,SharedStorag.class);
                startActivity(intent);
            }
        });
        btn_output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = edit_data.getText().toString();
                try {
                    Save(data);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Dele();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//  数据删除
    private void Dele() throws FileNotFoundException {
        FileInputStream fileInput = openFileInput("data");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInput));
        File file = new File(getFilesDir(),bufferedReader.getClass().toString());
        if (file.exists()){
            Toast.makeText(FileStore.this,"删除成功",Toast.LENGTH_SHORT).show();
            file.delete();
        }
    }

//        获取数据
    private void Get() throws IOException {
//        openFileInput用于打开输出流
//        使用openFileInput（）方法获得FileInputStream
        FileInputStream fileInput = openFileInput("data");
//        通过fikeInput对象使用Java流的方式从文件中读取数据
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInput));
        String data;
        StringBuilder stringBuilder =new StringBuilder();
        while ((data = bufferedReader.readLine()) != null){
            stringBuilder.append(data);
        }

        if (bufferedReader != null){
            bufferedReader.close();
        }

        if (fileInput != null){
            fileInput.close();
        }

        Toast.makeText(FileStore.this,stringBuilder,Toast.LENGTH_SHORT).show();
    }

//    存储数据
    private void Save(String data) throws IOException {
//        onpenFileOutout用于打开输出流
//        使用openFileOutput（）方法获得fileOutputStream对象
//        MODE_PRIVTE 会覆盖文件
//        MODE_APPEND 追加内容
        FileOutputStream fileOutput = openFileOutput("data",MODE_APPEND);
//        通过fileOutput对象使用Java流的方式将数据写入到文件中
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutput));


        FileInputStream fileInputStream = openFileInput("data");
        BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
        if (reader.readLine() != null){
            bufferedWriter.write("\n"+data);
        }else {
            bufferedWriter.write(data);
        }

        if (bufferedWriter != null){
            bufferedWriter.close();
        }

        if (fileOutput != null){
            fileOutput.close();
        }
    }
}
