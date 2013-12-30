package com.op.cookit.shoplist;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class ShopListActivity extends Fragment implements OnCrossListener {

    protected CrossView cross;
    protected ListView list;

    private ShopList shopList;
    private SimpleAdapter sAdapter;
    ArrayList<Map<String, Object>> productsMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shoplist, container, false);

        // Retrieve the TextView widget that will display results.
        //mResults = (TextView)v.findViewById(R.id.results);

        // This allows us to later extend the text buffer.
        //mResults.setText(mResults.getText(), TextView.BufferType.EDITABLE);

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.fragment_shoplist);
//!!!!!!
        this.cross = (CrossView) this.getActivity().findViewById(R.id.crossview);
        this.list = (ListView) this.getActivity().findViewById(android.R.id.list);

//        this.registerForContextMenu(list);
 //       cross.addOnCrossListener(this);

    }


    public void onStart() {
        super.onStart();
        productsMap = new ArrayList<Map<String, Object>>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // connect up with database
        shopList = AppBase.shopListRest.getShopList(1);
        List<Product> productList = //new ArrayList<Product>();//
         shopList.getProductList();

        for (Product product : productList) {
            Map<String, Object> list2 = new HashMap<String, Object>();
            list2.put("content", product);
            productsMap.add(list2);
        }
        sAdapter = new ShopListAdapter(this.getActivity(), productsMap, R.layout.item_todo,
                new String[]{"content"}, new int[]{android.R.id.content});
        sAdapter.setViewBinder(new CrossBinder());
  //      list.setAdapter(sAdapter);

    //    list.getChildAt(0);

        Log.d("","log");
    }

    public void onStop() {
        super.onStop();
    }


    public void onCross(int position, boolean crossed) {
        Log.e(AppBase.TAG, "onCross");
        int viewIndex = position - list.getFirstVisiblePosition();
        if (viewIndex < shopList.getProductList().size() && viewIndex >= 0) {
            Product product = shopList.getProductList().get(viewIndex);

            if (product.getCrossed().booleanValue() != crossed) {
                AppBase.shopListRest.crossProduct(1, product);

                if (product.getCrossed()) {
                    MediaPlayer mPlayer = MediaPlayer.create(this.getActivity(), R.raw.cross);
                    mPlayer.setLooping(false);
                    mPlayer.start();
                }

              //  list.refreshDrawableState();
                list.refreshDrawableState();
                sAdapter.notifyDataSetChanged();
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        // show menu to create new todo entry
        MenuItem add = menu.add(R.string.todo_add);
        add.setIcon(android.R.drawable.ic_menu_add);
        add.setOnMenuItemClickListener(new OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // prompt user for new todo entry
                LayoutInflater inflater = (LayoutInflater) ShopListActivity.this.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.dia_newitem, null);

                new AlertDialog.Builder(ShopListActivity.this.getActivity())
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
                                    AppBase.shopListRest.addProduct(1, product);

                                    Map<String, Object> list2 = new HashMap<String, Object>();
                                    list2.put("content", product);
                                    productsMap.add(list2);
                                    sAdapter.notifyDataSetChanged();
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