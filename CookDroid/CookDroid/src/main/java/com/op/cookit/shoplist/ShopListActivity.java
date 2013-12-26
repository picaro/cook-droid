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
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.op.cookit.AppBase;
import com.op.cookit.R;
import com.op.cookit.model.Product;
import com.op.cookit.model.ShopList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShopListActivity extends Activity implements OnCrossListener {

    protected CrossView cross;
    protected ListView list;

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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // connect up with database
        ShopList shopList = AppBase.shopListRest.getShopList(1);
        List<Product> productList = shopList.getProductList();

        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        for (Product product : productList) {
            Map<String,Object> list2 = new HashMap<String,Object>();
            list2.put("content",product);
            list2.put("text1",product.getName());
            data.add(list2);
        }
        //String[] aaa = list2.toArray(new String[list2.size()]);

        // build adapter to show todo cursor
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item_todo,
                new String[]{"content"}, new int[]{android.R.id.content});
        //SimpleAdapter adapter = new ShopListAdapter(this.getApplicationContext(), aaa);
//				R.layout.item_todo, cursor, new String[] { db.FIELD_LIST_TITLE,
//						db.FIELD_LIST_CREATED, db.FIELD_LIST_CROSSED },
//				new int[] { android.R.id.text1, android.R.id.text2,
//						android.R.id.content });
        sAdapter.setViewBinder(new CrossBinder());
        list.setAdapter(sAdapter);

    }

    public void onStop() {
        super.onStop();
    }

    public void onCross(int position, boolean crossed) {

        // someone crossed an item, so we should update the database
        int id;
        boolean current;
//			id = cursor.getInt(COL_ID);
//			current = Boolean.valueOf(cursor.getString(COL_CROSSED));
//		}

        // skip this process if database already correct
        //if(current == crossed) return;

//		db.crossEntry(id, crossed);
//		cursor.requery();

        // and refresh the child view for any changes
        int viewIndex = position - list.getFirstVisiblePosition();
        View child = list.getChildAt(viewIndex);
        if (child != null) {
            child.setVisibility(0);
            child.invalidate();
        }

        Log.e("a","CROSS");
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // show menu to create new todo entry
        MenuItem add = menu.add(R.string.todo_add);
        add.setIcon(android.R.drawable.ic_menu_add);
        add.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // prompt user for new todo entry
                LayoutInflater inflater = (LayoutInflater) ShopListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.dia_newitem, null);

                new AlertDialog.Builder(ShopListActivity.this)
                        .setView(view)
                        .setTitle(R.string.todo_add_title)
                        .setPositiveButton(R.string.todo_add_pos, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // create new list from name entered
                                String title = ((TextView) view.findViewById(android.R.id.text1)).getText().toString();
                                if (title.length() > 0) {
                                    Product product = new Product();
                                    product.setName(title);
                                    product.setShoplistid(1);
                                    AppBase.shopListRest.addProduct(1,product);
//							db.createEntry(null, title, 0);
//							cursor.requery();
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
        final boolean current = false;
//		synchronized(cursor) {
//			cursor.moveToPosition(position);
//			id = cursor.getInt(COL_ID);
//			current = Boolean.valueOf(cursor.getString(COL_CROSSED));
//		}

        MenuItem crossoff = menu.add(R.string.todo_cross);
        crossoff.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                onCross(position, !current);
                return true;
            }
        });

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        MenuItem delete = menu.add(R.string.todo_delete);
        delete.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AppBase.shopListRest.deleteProduct(1, 1);//??? id
                list.invalidateViews();
                return true;
            }
        });

    }

}