package com.example.rindou11.databasetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseTest extends SQLiteOpenHelper{

    //autoincrement表示id会进行自动增长.
    public static final String CREATE_BOOK="create table Book("+"id integer primary key AUTOINCREMENT,"+"author text,"+"price real,"+"pages integer,"+"name text)";
    public static final String CREATE_CATEGORY="create table Category("+"id integer primary key AUTOINCREMENT,"+"category_name text,"+"category_code text)";
    private Context mContext;

    //SQLiteOpenHelper的构造方法,接受4个参数
    public MyDatabaseTest(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext,"Database created.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop的字符串语句传入SQL的命令,如果表已存在,则删除旧表,然后调用上面的onCreate命令重新建立新表.
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);

    }
}
