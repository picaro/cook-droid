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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class CrossBinder implements SimpleAdapter.ViewBinder {

    public boolean setViewValue(View view, Object person, String testValue) {
        Log.e("cook","CrossBinder");
        switch (view.getId()) {
            case android.R.id.content:
                // binding to parent container should set the crossed value
                ImageView icon = (ImageView) view.findViewById(android.R.id.icon);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1),
                        text2 = (TextView) view.findViewById(android.R.id.text2);

                // read crossed status and set text flags for strikethrough
                boolean crossed = true;//Boolean.valueOf(testValue);
                if (crossed) {
                    icon.setImageState(new int[]{android.R.attr.state_checked}, true);
                    text1.setPaintFlags(text1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    text2.setPaintFlags(text2.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    icon.setImageState(new int[]{}, true);
                    text1.setPaintFlags(text1.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    text2.setPaintFlags(text2.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
                return true;

        }

        // otherwise fall through to other binding methods
        return false;

    }

}
