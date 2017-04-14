package DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by 殇痕 on 2017/3/21.
 */

public class CupDB{
    private static CupDB cupDB;
    private SQLiteDatabase db;
    public static final String DB_NAME = "cup";
    public int VERSION = 1;
    public CupDB(Context context){
        DBcupOpenHelpter dbHelpter = new DBcupOpenHelpter(context, DB_NAME, null, VERSION);
        db = dbHelpter.getWritableDatabase();
        Log.d("hello", "new database");
    }
    /*
	 * 保证全局范围内只有一个db实例。。
	 */
    public static synchronized CupDB getInstance(Context context){
        if(cupDB == null){
            cupDB = new CupDB(context);
        }
     return cupDB;
    }
    public boolean RegisterUser(String name, String password){
        int i=0 ;
        String sql = "insert into user(username, password)values(?,?)";
        ContentValues values = new ContentValues();
        values.put("username", name);
        values.put("password", password);
        Long rt = db.insert("user", null, values);
        if(rt>0){
            return true;
        }
        return  false;
    }
    public boolean CheckLogin(String username, String password){
        String sql = "select * from user where username=? and password=?";
        Cursor cursor = db.rawQuery(sql, new String[]{username, password});
        if(cursor.moveToFirst()){
            cursor.close();
            return true;
        }
        return false;


    }
}
