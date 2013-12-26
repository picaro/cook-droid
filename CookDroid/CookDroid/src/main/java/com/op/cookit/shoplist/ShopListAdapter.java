package com.op.cookit.shoplist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.op.cookit.R;
import com.op.cookit.model.Product;


/**
 * Created by alexander.pastukhov on 12/25/13.
 */
public class ShopListAdapter extends ArrayAdapter<Product> {
    private final Context context;
    private final Product[] values;

    public ShopListAdapter(Context context, Product[] values) {
        super(context, R.layout.item_todo, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_todo, parent, false);
        TextView textView = (TextView) rowView.findViewById(android.R.id.text1);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values[position].getName());
        // Change the icon for Windows and iPhone
        //String s = values[position];
//        if (s.startsWith("iPhone")) {
//            imageView.setImageResource(R.drawable.no);
//        } else {
//            imageView.setImageResource(R.drawable.ok);
//        }

        return rowView;
    }
}