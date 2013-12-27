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

import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.op.cookit.AppBase;
import com.op.cookit.model.Product;

public class CrossBinder implements SimpleAdapter.ViewBinder {

    public boolean setViewValue(View view, Object product, String testValue) {
        Log.d(AppBase.TAG, "CrossBinder");
        switch (view.getId()) {
            case android.R.id.content:
                // binding to parent container should set the crossed value
                ImageView icon = (ImageView) view.findViewById(android.R.id.icon);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                text1.setText(((Product) product).getName());

                boolean crossed = ((Product) product).getCrossed();
                if (crossed) {
                    icon.setImageState(new int[] { android.R.attr.state_checked }, true);
                    text1.setPaintFlags(text1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    icon.setImageState(new int[] { }, true);
                    text1.setPaintFlags(text1.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                }
                return true;

        }
        return false;
    }

}