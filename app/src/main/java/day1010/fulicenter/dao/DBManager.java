package day1010.fulicenter.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import day1010.fulicenter.I;
import day1010.fulicenter.bean.User;

/**
 * Created by Administrator on 2016/10/24.
 */
public class DBManager {
    private static DBManager dbManager = new DBManager();
    private static DBOpenHelper mHelper;

    void onInit(Context context){
        mHelper = new DBOpenHelper(context);
    }
    public static synchronized  DBManager getInstance(){
        return dbManager;
    }
    public synchronized  void closeDB(){
        if (mHelper != null){
            mHelper.closeDB();
        }
    }
    public synchronized boolean saveUser(User user){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UserDao.TABLE_COLUMN_NAME,user.getMuserName());
        values.put(UserDao.TABLE_COLUMN_NICK,user.getMuserNick());
        values.put(UserDao.TABLE_COLUMN_AVATAR_ID,user.getMavatarId());
        values.put(UserDao.TABLE_COLUMN_AVATAR_TYPE,user.getMavatarType());
        values.put(UserDao.TABLE_COLUMN_AVATAR_PATH,user.getMavatarPath());
        values.put(UserDao.TABLE_COLUMN_AVATAR_SUFFIX,user.getMavatarSuffix());
        values.put(UserDao.TABLE_COLUMN_AVATAR_LASTUPDATE_TIME,user.getMavatarLastUpdateTime());
        if (db.isOpen()){
            return db.replace(UserDao.TABLE_USER_NAME,null,values)!=-1;
        }
        return false;
    }
    public synchronized User getUser(String username){
     SQLiteDatabase db = mHelper.getReadableDatabase();
        String sql = "select * from " + UserDao.TABLE_USER_NAME + " where "
                + UserDao.TABLE_COLUMN_NAME + " =?";
        User user = null;
        Cursor cursor = db.rawQuery(sql,new String[]{username});
        if (cursor.moveToNext()){
            user = new User();
            user.setMuserName(username);
            user.setMuserNick(cursor.getString(cursor.getColumnIndex(UserDao.TABLE_COLUMN_NICK)));
            user.setMavatarId(cursor.getInt(cursor.getColumnIndex(UserDao.TABLE_COLUMN_AVATAR_ID)));
            user.setMavatarType(cursor.getInt(cursor.getColumnIndex(UserDao.TABLE_COLUMN_AVATAR_TYPE)));
            user.setMavatarPath(cursor.getString(cursor.getColumnIndex(UserDao.TABLE_COLUMN_AVATAR_PATH)));
            user.setMavatarSuffix(cursor.getString(cursor.getColumnIndex(UserDao.TABLE_COLUMN_AVATAR_SUFFIX)));
            user.setMavatarLastUpdateTime(cursor.getString(cursor.getColumnIndex(UserDao.TABLE_COLUMN_AVATAR_LASTUPDATE_TIME)));
            return user;
        }
        return user;
    }
    public synchronized boolean updateUser(User user){
        int result = -1;
        SQLiteDatabase db = mHelper.getWritableDatabase();
        String sql = UserDao.TABLE_COLUMN_NAME + "=?";
        ContentValues values = new ContentValues();
        values.put(UserDao.TABLE_COLUMN_NICK,user.getMuserNick());
        if (db.isOpen()){
            result = db.update(UserDao.TABLE_USER_NAME,values,sql,new String[]{user.getMuserName()});
        }
        return result > 0;

    }
}
