package com.example.telegram.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.example.telegram.models.User;
import com.example.telegram.utils.BitmapUtils;

public class UserEntity extends SQLiteOpenHelper {
    public static final String TableName = "UserTable";
    public static final String Email = "Email";
    public static final String Image = "Image";
    public static final String Status = "Status";

    public UserEntity(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "Create table if not exists " + TableName + "("
                + Email + " Text Primary key, "
                + Image + " Text, "
                + Status + " Status)";
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + TableName);
        onCreate(db);
    }

    public User getUserByEmail(String email) {
         String sql = "Select * from " + TableName + " where Email =" +  "'" + email +"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql,null);
        if(cursor!=null)
            while (cursor.moveToNext())
            {
                String email_ = cursor.getString(0);
                String image = cursor.getString(1);
                boolean status_ = false;
                return  new User(email_,image,status_);
            }
        return  null;
    }

    public void addUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Email, user.getEmail());
        value.put(Image, user.getImage());
        value.put(Status, user.getStatus() ? 1 : 0);
        db.insert(TableName,null,value);
        db.close();
    }

    public void updateUser(User user)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(Email, user.getEmail());
        value.put(Image, user.getImage());
        value.put(Status, user.getStatus() ? 1 : 0);
        db.insert(TableName,null,value);
        db.update(TableName, value,Email + "=?",
                new String[]{String.valueOf(Email)});
        db.close();
    }


}
