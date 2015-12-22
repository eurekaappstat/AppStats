package com.example.manjunathnb.appstats;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.manjunathnb.appstats.database.AppStatConstants;
import com.example.manjunathnb.appstats.database.DBManager;
import com.example.manjunathnb.appstats.listener.StatSqliteHelper;

public class ListActivity extends android.app.ListActivity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// start monitoring service
		startService(new Intent(this , AppMoniterService.class));

		DBManager db = DBManager.getInstance(this);
		db.getReadableDB();
		Cursor cursor = db.query(AppStatConstants.APP_INFO_TABLE, mProjection, null, null, null, null, null);

		ListAdapter listAdapter = new MyCursorAdapter(this, cursor,
				ListAdapter.IGNORE_ITEM_VIEW_TYPE);


		setListAdapter(listAdapter);
	}

	String[] mProjection = new String[] {
			StatSqliteHelper.ID, //0
			StatSqliteHelper.APP_PACKAGE, //1
			StatSqliteHelper.LAST_APP_LAUNCHED_TIME, //2
			StatSqliteHelper.LAST_APP_EXITED_TIME //3
	};

	static class MyCursorAdapter extends CursorAdapter {

		public MyCursorAdapter(Context context, Cursor c, int flags) {
			super(context, c, flags);
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			String packageName = cursor.getString(1);
			long startTime = cursor.getLong(2);
			long endTime = cursor.getLong(3);
			long tDiffInsec = ((endTime - startTime) /1000 );

			TextView tv1 = (TextView)view.findViewById(R.id.text1);
			TextView tv2 = (TextView)view.findViewById(R.id.text2);

			tv1.setText(packageName);
			if(tDiffInsec >= 0) {
				tv2.setText(Long.toString(tDiffInsec));
			}
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			return LayoutInflater.from(context).inflate(R.layout.item_list_view, parent, false);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
