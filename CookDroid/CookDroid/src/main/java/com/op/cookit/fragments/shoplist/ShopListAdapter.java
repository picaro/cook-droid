package com.op.cookit.fragments.shoplist;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.op.cookit.AppBase;
import com.op.cookit.MainActivity;
import com.op.cookit.R;
import com.op.cookit.model.Product;
import com.op.cookit.syncadapter.ProductsContentProvider;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by alexander.pastukhov on 12/25/13.
 */
public class ShopListAdapter extends SimpleAdapter {

    private List<? extends Map<String, ?>> data;
    private Context context;
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */
    public ShopListAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.context = context;
        this.data = data;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.e("", "view:" + position);

        View view = super.getView(position, convertView, parent);
        ImageView delicon = (ImageView) view.findViewById(R.id.imgdel);
        final Product product = (Product) ((HashMap) getItem(position)).get("content");
        delicon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.e(AppBase.TAG, "delview:" + position);
                Log.e(AppBase.TAG, "-----" + product.getId());
                //AppBase.clientRest.deleteProduct(1, product.getId());
                Integer deleted =  ((MainActivity)context).getContentResolver().delete( ProductsContentProvider.CONTENT_URI, "id = ?",new String[]{"" + product.getId()});
                data.remove(getItem(position));

                notifyDataSetChanged();
            }
        });

        ImageView icon = (ImageView) view.findViewById(android.R.id.icon);
        icon.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Log.e("", "cross:" + position);
                AppBase.clientRest.crossProduct(1, product);
                //data.remove(getItem(position));
                if (product.getCrossed()) {
                    MediaPlayer mPlayer = MediaPlayer.create(view.getContext(), R.raw.cross);
                    mPlayer.setLooping(false);
                    mPlayer.start();
                }
                notifyDataSetChanged();
            }
        });

        return view;
    }
}