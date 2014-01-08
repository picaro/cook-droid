package com.op.cookit.util.remote;

import android.os.AsyncTask;

import com.op.cookit.AppBase;
import com.op.cookit.model.inner.PersonLocal;

/**
 *
 */
public class SendOsInfoAsyncTask extends AsyncTask<PersonLocal, Void, Void> {

    private String mInfo;
    private String mResponse;

    public SendOsInfoAsyncTask(String info) {
        this.mInfo = info;
    }

    @Override
    protected Void doInBackground(PersonLocal... params) {
        ClientRest mRemoteClient = AppBase.clientRest;
        mResponse = mRemoteClient.sendDeviceInformation(params[0], mInfo);
        return null;
    }


}
