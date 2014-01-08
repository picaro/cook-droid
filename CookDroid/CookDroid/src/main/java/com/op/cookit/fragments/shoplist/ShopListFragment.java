package com.op.cookit.fragments.shoplist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.op.cookit.AppBase;
import com.op.cookit.R;
import com.op.cookit.model.Product;
import com.op.cookit.model.ShopList;
import com.op.cookit.syncadapter.ProductsContentProvider;
import com.op.cookit.syncadapter.SyncUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShopListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> , OnCrossListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    protected CrossView cross;
    protected ListView list;

    private ShopList shopList;
    private SimpleAdapter sAdapter;
    ArrayList<Map<String, Object>> productsMap;

    /**
     * Cursor adapter for controlling ListView results.
     */
    private SimpleCursorAdapter mAdapter;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Create account, if needed
        SyncUtils.CreateSyncAccount(activity);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopListFragment newInstance(String param1, String param2) {
        ShopListFragment fragment = new ShopListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public ShopListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_shoplist,
                container, false);

        this.cross = (CrossView) view.findViewById(R.id.crossview);
        this.list = (ListView) view.findViewById(android.R.id.list);

        setHasOptionsMenu(true);
        this.registerForContextMenu(list);
        cross.addOnCrossListener(this);
        return view;

    }

    @Override
    public void onCreateOptionsMenu(Menu menu,MenuInflater menuInflater) {

        // show menu to create new todo entry
        MenuItem add = menu.add(R.string.todo_add);
        add.setIcon(android.R.drawable.ic_menu_add);
        add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                // prompt user for new todo entry
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View view = inflater.inflate(R.layout.dia_newitem, null);

                new AlertDialog.Builder(getActivity())
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
                                    AppBase.clientRest.addProduct(1, product);

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

    //    return true;

    }

    public void onStart() {
        super.onStart();
        productsMap = new ArrayList<Map<String, Object>>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // connect up with database
        shopList = AppBase.clientRest.getShopList(1);
        List<Product> productList = shopList.getProductList();

        for (Product product : productList) {
            Map<String, Object> list2 = new HashMap<String, Object>();
            list2.put("content", product);
            productsMap.add(list2);
        }
        sAdapter = new ShopListAdapter(this.getActivity(), productsMap, R.layout.item_todo,
                new String[]{"content"}, new int[]{android.R.id.content});
        sAdapter.setViewBinder(new CrossBinder());
        list.setAdapter(sAdapter);

        list.getChildAt(0);

        Log.d("","log");
    }

    public void onCross(int position, boolean crossed) {
        Log.e(AppBase.TAG, "onCross");
        int viewIndex = position - list.getFirstVisiblePosition();
        if (viewIndex < shopList.getProductList().size() && viewIndex >= 0) {
            Product product = shopList.getProductList().get(viewIndex);

            if (product.getCrossed().booleanValue() != crossed) {
                AppBase.clientRest.crossProduct(1, product);

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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // We only have one loader, so we can ignore the value of i.
        // (It'll be '0', as set in onCreate().)

        Log.e(AppBase.TAG, "onCreateLoader");


        return new CursorLoader(getActivity(),  // Context
                ProductsContentProvider.CONTENT_URI, // URI
                null,                // Projection
                null,                           // Selection
                null,                           // Selection args
                null); // Sort

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.changeCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.changeCursor(null);
    }


}
