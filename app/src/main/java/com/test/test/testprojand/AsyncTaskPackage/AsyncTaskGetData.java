package com.test.test.testprojand.AsyncTaskPackage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.test.test.testprojand.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class AsyncTaskGetData extends AsyncTask<HashMap, String, HashMap> {

    private Context mContext;
    private AsyncInterface mListener;
    private ProgressDialog progressDialog;

    private HttpURLConnection httpURLConnection;
    private BufferedReader bufferedReader;

    String errorMessage;

    public AsyncTaskGetData(Context context, AsyncInterface mListener) {
        mContext = context;
        this.mListener = mListener;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(this.mContext, R.style.MyAlertDialogStyle);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    protected HashMap doInBackground(HashMap... params) {
        Boolean isSuccess = false;
        HashMap hashMap = new HashMap<>();
        System.out.println(Objects.requireNonNull(params[0].get("URL")).toString());
        try {
            URL url = new URL(params[0].get("URL").toString());
            System.out.println("params : " + params.length);
            System.out.println("url : " + url);
            httpURLConnection.setFollowRedirects(false);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            if (params[0].get("setConnectTimeout") == null) { httpURLConnection.setConnectTimeout(30000);
            } else { httpURLConnection.setConnectTimeout(Integer.valueOf(params[0].get("setConnectTimeout").toString()));
            }

            if (params.length > 1) { doAddRequestProperty(params[1]); }

            httpURLConnection.connect();
            System.out.println("response Code : " + httpURLConnection.getResponseCode());
            if (httpURLConnection.getResponseCode() == 200) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                isSuccess = true;
            }

            if (bufferedReader != null) {
                hashMap.put("result", doStringBufferAppend(bufferedReader).toString());
                hashMap.put("isSuccess", isSuccess);
            } else {
                hashMap.put("result", "");
                hashMap.put("isSuccess", isSuccess);
            }

            return hashMap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("Error MalformedURLException : " + e);
            errorMessage = String.valueOf(e);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error IOException : " + e);
            errorMessage = String.valueOf(e);
        }

        hashMap.put("result", errorMessage);
        hashMap.put("isSuccess", isSuccess);

        return hashMap;
    }

    @Override
    protected void onPostExecute(HashMap result) {
        if (progressDialog.isShowing()) {
            if (mListener != null)  mListener.AsyncResult(result.get("result"), (Boolean) result.get("isSuccess"));
            progressDialog.dismiss();
        } else {
            System.out.println("Something Wrong with progress Dialog");
        }
    }

    public void doAddRequestProperty (HashMap thisHashMap) {
        if (thisHashMap.keySet().size() > 1) {
            Set<String> keys = thisHashMap.keySet();
            for (String strKeys : keys) {
                httpURLConnection.addRequestProperty(strKeys,thisHashMap.get(strKeys).toString());
            }
        }
    }

    public StringBuffer doStringBufferAppend(BufferedReader bufferedReader) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        String strLine = "";
        while ((strLine = bufferedReader.readLine()) != null) {
            stringBuffer.append(strLine+"\n");
            Log.d("Response: ", "> " + strLine);
        }
        return stringBuffer;
    }

}
