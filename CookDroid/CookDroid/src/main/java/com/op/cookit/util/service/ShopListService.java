package com.op.cookit.util.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;

public class ShopListService extends IntentService {

	public static final String SERVICE_CALLBACK = "com.jeremyhaberman.restfulandroid.service.SERVICE_CALLBACK";

	public static final String ORIGINAL_INTENT_EXTRA = "com.jeremyhaberman.restfulandroid.service.ORIGINAL_INTENT_EXTRA";

	private static final int REQUEST_INVALID = -1;

	private ResultReceiver mCallback;

	private Intent mOriginalRequestIntent;

	public ShopListService() {
		super("ShopListService");
	}

	@Override
	protected void onHandleIntent(Intent requestIntent) {

		mOriginalRequestIntent = requestIntent;

		// Get request data from Intent
	//	String method = requestIntent.getStringExtra(ShopListService.METHOD_EXTRA);
	//	int resourceType = requestIntent.getIntExtra(ShopListService.RESOURCE_TYPE_EXTRA, -1);
		mCallback = requestIntent.getParcelableExtra(ShopListService.SERVICE_CALLBACK);

//		switch (resourceType) {
//		case RESOURCE_TYPE_PROFILE:
//
//			if (method.equalsIgnoreCase(METHOD_GET)) {
//				ProfileProcessor processor = new ProfileProcessor(getApplicationContext());
//				processor.getProfile(makeProfileProcessorCallback());
//			} else {
//				mCallback.send(REQUEST_INVALID, getOriginalIntentBundle());
//			}
//			break;
//
//		default:
//			mCallback.send(REQUEST_INVALID, getOriginalIntentBundle());
//			break;
//		}
        mCallback.send(REQUEST_INVALID, getOriginalIntentBundle());

	}

//	private TimelineProcessorCallback makeTimelineProcessorCallback() {
//		TimelineProcessorCallback callback = new TimelineProcessorCallback() {
//
//			@Override
//			public void send(int resultCode) {
//				if (mCallback != null) {
//					mCallback.send(resultCode, getOriginalIntentBundle());
//				}
//			}
//		};
//		return callback;
//	}

	protected Bundle getOriginalIntentBundle() {
		Bundle originalRequest = new Bundle();
		originalRequest.putParcelable(ORIGINAL_INTENT_EXTRA, mOriginalRequestIntent);
		return originalRequest;
	}
}
