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

import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.op.cookit.AppBase;
import com.op.cookit.R;
import com.op.cookit.model.Product;

public class CrossBinder implements SimpleAdapter.ViewBinder {

    public boolean setViewValue(View view, final Object product, String testValue) {
        Log.d(AppBase.TAG, "CrossBinder");
        switch (view.getId()) {
            case android.R.id.content:
                // binding to parent container should set the crossed value
                ImageView icon = (ImageView) view.findViewById(android.R.id.icon);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                text1.setText(((Product) product).getName());
                ImageView delicon = (ImageView) view.findViewById(R.id.imgdel);




                boolean crossed = ((Product) product).getCrossed();
                if (crossed) {
                    //icon.setImageState(new int[] { android.R.attr.state_checked }, true);
                    icon.setImageResource(android.R.drawable.checkbox_on_background);
                    text1.setPaintFlags(text1.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    text1.setTextColor(Color.GRAY);
                } else {
                    icon.setImageResource(android.R.drawable.checkbox_off_background);
                    //icon.setImageState(new int[] { }, true);
                    text1.setPaintFlags(text1.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    text1.setTextColor(Color.BLACK);
                }
                return true;

        }
        return false;
    }

}