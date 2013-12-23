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

import android.database.Cursor;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.op.cookit.R;

public class CrossBinder implements SimpleCursorAdapter.ViewBinder {

	public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
		
		switch(view.getId()) {
		case android.R.id.content:
			// binding to parent container should set the crossed value
			ImageView icon = (ImageView)view.findViewById(android.R.id.icon);
			TextView text1 = (TextView)view.findViewById(android.R.id.text1),
				text2 = (TextView)view.findViewById(android.R.id.text2);
			
			// read crossed status and set text flags for strikethrough
			boolean crossed = Boolean.valueOf(cursor.getString(columnIndex));
			if(crossed) {
				icon.setImageState(new int[] { android.R.attr.state_checked }, true);
				text1.setPaintFlags(text1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
				text2.setPaintFlags(text2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
			} else {
				icon.setImageState(new int[] { }, true);
				text1.setPaintFlags(text1.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
				text2.setPaintFlags(text2.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
			}
			return true;
			
		case android.R.id.text2:
			// binding to second textview should format time nicely
			long created = cursor.getLong(columnIndex);
			long now = System.currentTimeMillis() / 1000;
			
			int minutes = (int)((now - created) / 60);
			String nice = view.getContext().getString(R.string.bind_minutes, minutes);
			if(minutes >= 60) {
				int hours = (minutes / 60);
				nice = view.getContext().getString(R.string.bind_hours, hours);
				if(hours >= 24) {
					int days = (hours / 24);
					nice = view.getContext().getString(R.string.bind_days, days);
				}
			}
			
			((TextView)view).setText(nice);
			
			return true;
		}
		
		// otherwise fall through to other binding methods
		return false;
		
	}

}
