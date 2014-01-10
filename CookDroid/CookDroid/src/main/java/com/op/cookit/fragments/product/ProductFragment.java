package com.op.cookit.fragments.product;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.op.cookit.AppBase;
import com.op.cookit.R;
import com.op.cookit.model.ShopList;
import com.op.cookit.util.SystemUiHider;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import jim.h.common.android.zxinglib.integrator.IntentIntegrator;
import jim.h.common.android.zxinglib.integrator.IntentResult;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class ProductFragment extends Fragment {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    private ProductFragment fragment = this;
    private View view;

    private Handler  handler = new Handler();
    private TextView txtScanResult;
	//RetreiveFeedTask rt = new RetreiveFeedTask();
    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    class RetreiveFeedTask extends AsyncTask<String, Void, String> {

        private Exception exception;
		
		public ShopList product;

        protected String doInBackground(String... urls) {
            //String url = "http://cookcloud.jelastic.neohost.net/rest/barcode/" + urls[0];
            String url = AppBase.BASE_REST_URL + "shoplist/1";
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
            try {
                Log.e(AppBase.TAG, ">>before");
                String result = (String)restTemplate.getForObject(url, String.class);
                Log.e(AppBase.TAG, ">>result" + result);
                product =  new Gson().fromJson(result, ShopList.class);
            	Log.d(">>", ""+ result + " prod:" + product);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return "";

        }

        protected void onPostExecute(String result)
        {
            if (product != null){
                txtScanResult.setText(product.toString());
            } else {
                txtScanResult.setText("not found");
            }
        }
    }
	
	private static final int UPDATE_IMAGE = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_product,
                container, false);


        txtScanResult = (TextView) view.findViewById(R.id.scan_result);
        txtScanResult.setText("");


        View btnScan = view.findViewById(R.id.scan_button);
        // Scan button
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // set the last parameter to true to open front light if available
                IntentIntegrator.initiateScan(ProductFragment.this.getActivity(), R.layout.capture,
                        R.id.viewfinder_view, R.id.preview_view, true);
            }
        });

        new RetreiveFeedTask().execute("");

        return view;
    }

    public static ProductFragment newInstance() {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case IntentIntegrator.REQUEST_CODE:
                IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode,
                        resultCode, data);
                if (scanResult == null) {
                    return;
                }
                final String result = scanResult.getContents();
                if (result != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
							
							
							new RetreiveFeedTask().execute(result);
                          //  txtScanResult.setText(result);
                        }
                    });
                }
                break;
            default:
        }
    }

}
