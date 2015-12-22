package com.example.manjunathnb.appstats.database;

import com.example.manjunathnb.appstats.listener.StatSqliteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by manjunathnb on 7/7/2015.
 */
public class DBManager {

	private static DBManager dbManager;
	private SQLiteDatabase sqlDB;

	private DBManager(Context context) {
		mStatSqliteHelper =  new StatSqliteHelper(context);
	}

	public static DBManager getInstance(Context context) {
		synchronized (DBManager.class){
			if(dbManager == null ) {
				Log.d("manju :DBManager", "creating DB manager instance");
				dbManager =  new DBManager(context);
			}
		}
		return dbManager;
	}

	private StatSqliteHelper mStatSqliteHelper;

	public void getWritableDB() {
		sqlDB =  mStatSqliteHelper.getWritableDatabase();
	}

	public void getReadableDB() {
		sqlDB =  mStatSqliteHelper.getReadableDatabase();
	}

	public void beginTransaction() {
		sqlDB.beginTransaction();
	}

	public void endTransaction() {
		sqlDB.endTransaction();
	}

	public long insert(String tableName, ContentValues values) {
		return sqlDB.insert(tableName, null, values );
	}

	public Cursor query(String tableName, String[] projection, String selection, String[] selectionArgs,
			String groupBy, String having,String orderBy) {
		return sqlDB.query(tableName, projection, selection, selectionArgs, groupBy, having, orderBy) ;
	}

	public int update (String tableName, ContentValues cv , String whereClause , String [] whereArgs){
		return sqlDB.update(tableName,cv, whereClause , whereArgs );
	}
}
