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


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.MenuItem.OnMenuItemClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.op.cookit.R;

public class TodoActivity extends Activity implements OnCrossListener {
	
	protected CrossView cross;
	protected ListView list;
	
	protected TodoDatabase db;
	protected Cursor cursor;
	protected int COL_ID, COL_CROSSED;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shoplist);

		this.cross = (CrossView) this.findViewById(R.id.crossview);
		this.list = (ListView) this.findViewById(android.R.id.list);
		
		this.registerForContextMenu(list);
		cross.addOnCrossListener(this);
		
	}
	

	public void onStart() {
		super.onStart();
		
		// connect up with database
		this.db = new TodoDatabase(this);
		this.cursor = db.todoCursor(); 
		this.COL_ID = cursor.getColumnIndexOrThrow(db.FIELD_ID);
		this.COL_CROSSED = cursor.getColumnIndexOrThrow(db.FIELD_LIST_CROSSED);
		
		// build adapter to show todo cursor
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				R.layout.item_todo, cursor, new String[] { db.FIELD_LIST_TITLE,
						db.FIELD_LIST_CREATED, db.FIELD_LIST_CROSSED },
				new int[] { android.R.id.text1, android.R.id.text2,
						android.R.id.content });
		adapter.setViewBinder(new CrossBinder());

		list.setAdapter(adapter); 

	}
	
	public void onStop() {
		super.onStop();
		
		// clean up any cursor and database connections
		cursor.close();
		db.close();
		
	}
	
	public void onCross(int position, boolean crossed) {
		
		// someone crossed an item, so we should update the database
		int id;
		boolean current;
		synchronized(cursor) {
			cursor.moveToPosition((int)position);
			id = cursor.getInt(COL_ID);
			current = Boolean.valueOf(cursor.getString(COL_CROSSED));
		}
		
		// skip this process if database already correct
		if(current == crossed) return;

		db.crossEntry(id, crossed);
		cursor.requery();

		// and refresh the child view for any changes
		int viewIndex = position - list.getFirstVisiblePosition();
		View child = list.getChildAt(viewIndex);
		if(child != null) child.invalidate();

	}
	
	public boolean onCreateOptionsMenu(Menu menu) {
		
		// show menu to create new todo entry
		MenuItem add = menu.add(R.string.todo_add);
		add.setIcon(android.R.drawable.ic_menu_add);
		add.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				
				// prompt user for new todo entry
				LayoutInflater inflater = (LayoutInflater)TodoActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				final View view = inflater.inflate(R.layout.dia_newitem, null);
				
				new AlertDialog.Builder(TodoActivity.this)
				.setView(view)
				.setTitle(R.string.todo_add_title)
				.setPositiveButton(R.string.todo_add_pos, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// create new list from name entered
						String title = ((TextView)view.findViewById(android.R.id.text1)).getText().toString();
						if(title.length() > 0) {
							db.createEntry(null, title, 0);
							cursor.requery();
							list.invalidateViews();
						}
					}
				})
				.setNegativeButton(R.string.todo_add_neg, null).create().show();
				
				return true;
			}
		});
		
		return true;
		
	}
	
	public synchronized void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
		
		// pop up menu to ask about crossing or deleting
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		final int position = info.position;
		
		final int id;
		final boolean current;
		synchronized(cursor) {
			cursor.moveToPosition(position);
			id = cursor.getInt(COL_ID);
			current = Boolean.valueOf(cursor.getString(COL_CROSSED));
		}
		
		MenuItem crossoff = menu.add(R.string.todo_cross);
		crossoff.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				onCross(position, !current);
				return true;
			}
		});
		
		MenuItem delete = menu.add(R.string.todo_delete);
		delete.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public boolean onMenuItemClick(MenuItem item) {
				db.deleteEntry(id);
				cursor.requery();
				list.invalidateViews();
				return true;
			}
		});
		
	}

}