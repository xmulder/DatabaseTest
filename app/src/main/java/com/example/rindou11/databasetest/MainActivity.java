package com.example.rindou11.databasetest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button button_jianli;
    private Button button_add_data;
    private Button button_update_data;
    private Button button_delete_data;
    private Button button_query_data;

    private MyDatabaseTest myDatabaseTest;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //只要将第4个参数version的值该大,便可以挑用MyDatabaseTest类中的onUpgrade方法
        myDatabaseTest=new MyDatabaseTest(this,"BookStore.db",null,2);

        button_jianli=(Button)findViewById(R.id.button_jianli_button);
        button_jianli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDatabaseTest.getWritableDatabase();

            }
        });

        button_add_data=(Button)findViewById(R.id.button_add_data);
        button_add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=myDatabaseTest.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("name","The Da Vinci Code");
                values.put("author","Bill");
                values.put("pages",555);
                values.put("price",15.55);
                db.insert("Book",null,values);
                values.clear();

                values.put("category_name","ART");
                values.put("category_code","I");
                db.insert("Category",null,values);
                values.clear();

                values.put("category_name","SEX ART");
                values.put("category_code","II");
                db.insert("Category",null,values);
                values.clear();
                Toast.makeText(MainActivity.this,"Add Category successful.",Toast.LENGTH_SHORT).show();

            }
        });

        button_update_data=(Button)findViewById(R.id.button_update_data);
        button_update_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=myDatabaseTest.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("price",5.00);
                values.put("pages",666);
                db.update("Book",values,"name=?",new String[]{"The Da Vinci Code"});
                db.update("Book",values,"name=?",new String[]{"The Da Vinci Code"});
                values.clear();
                Toast.makeText(MainActivity.this,"Change Category successful.",Toast.LENGTH_SHORT).show();

            }
        });

        button_delete_data=(Button)findViewById(R.id.button_delete_button);
        button_delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=myDatabaseTest.getWritableDatabase();
                db.delete("Category","category_name=?",new String[]{"SEX ART"});
                Toast.makeText(MainActivity.this,"Delete successful.",Toast.LENGTH_SHORT).show();
            }
        });

        button_query_data=(Button)findViewById(R.id.button_query_button);
        button_query_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db=myDatabaseTest.getWritableDatabase();
                Cursor cursor=db.query("Book",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do{
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        String author=cursor.getString(cursor.getColumnIndex("author"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.d("MainActivity","book name is"+name);
                    }while (cursor.moveToNext());
                }
                cursor.close();
            }
        });



    }

}
