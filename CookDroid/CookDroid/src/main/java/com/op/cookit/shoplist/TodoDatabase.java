/*
	Copyright (C) 2008 Jeffrey Sharkey, http://jsharkey.org/
	
	This program is free software: you can redistribute it and/or modify
	it under the terms of the GNU General Public License as published by
	the Free Software Foundation, either version 3 of the License, or
	(at your option) any later version.
	
	This program is distributed in the hope that it will be useful,
	but WITHOUT ANY WARRANTY; without even the implied warranty of
	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
	GNU General Public License for more details.
	
	You should have received a copy of the GNU General Public License
	along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package com.op.cookit.shoplist;


import java.util.Random;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.op.cookit.R;


public class TodoDatabase extends SQLiteOpenHelper {
	
	public final static String DB_NAME = "todo";
	public final static int DB_VERSION = 5;
	
	public final static String TABLE_TODO = "todo";
	public final static String FIELD_ID = "_id";
	public final static String FIELD_LIST_TITLE = "title";
	public final static String FIELD_LIST_CREATED = "created";
	public final static String FIELD_LIST_CROSSED = "crossed";
	
	public final String[] examples;
	
	
	public TodoDatabase(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		examples = context.getResources().getStringArray(R.array.list_examples);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE " + TABLE_TODO
				+ " (" + FIELD_ID + " INTEGER PRIMARY KEY, "
				+ FIELD_LIST_TITLE + " TEXT, "
				+ FIELD_LIST_CREATED + " INTEGER, "
				+ FIELD_LIST_CROSSED + " TEXT)");

		// create some test entries, but pass specific database to prevent onCreate() loops
		Random random = new Random();
		for(String example : examples) {
			createEntry(db, example, random.nextInt(60 * 24 * 2));
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
		onCreate(db);
	}
	

	/**
	 * Create a new entry in the given database.
	 * @param db Database to create new entry in, or null to use default database
	 * @param title Title of new todo entry
	 * @return _id of newly inserted todo entry
	 */
	public long createEntry(SQLiteDatabase db, String title, int delayMinutes) {
		// pull default database if nothing specific was requested
		if(db == null) db = this.getWritableDatabase();
		long now = System.currentTimeMillis() / 1000;

		ContentValues saved = new ContentValues();
		saved.put(FIELD_LIST_TITLE, title);
		saved.put(FIELD_LIST_CREATED, now - (delayMinutes * 60));
		saved.put(FIELD_LIST_CROSSED, Boolean.toString(false));
		
		long row = db.insert(TABLE_TODO, null, saved);
		return row;
	}

	/**
	 * Set the crossed-out status of a specific todo entry.
	 * @param id _id of the todo entry
	 * @param crossed New crossed value to set
	 */
	public void crossEntry(int id, boolean crossed) {
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues saved = new ContentValues();
		saved.put(FIELD_LIST_CROSSED, Boolean.toString(crossed));
		
		db.update(TABLE_TODO, saved, FIELD_ID + " = ?", new String[] { Integer.toString(id) });
	}
	

	/**
	 * Delete the given todo entry from this database
	 * @param id _id of the todo entry
	 */
	public void deleteEntry(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_TODO, FIELD_ID + " = ?", new String[] { Integer.toString(id) });
	}
	

	/**
	 * Return a cursor over all the todo entries in this database.
	 * @return Cursor that contains all todo entries
	 */
	public Cursor todoCursor() {
		SQLiteDatabase db = this.getReadableDatabase();
		return db.query(TABLE_TODO, null, null, null, null, null, null);
	}
	

}
