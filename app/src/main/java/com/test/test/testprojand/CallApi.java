package com.test.test.testprojand;

import android.app.Activity;
import android.content.Context;

import com.test.test.testprojand.AsyncTaskPackage.AsyncTaskGetData;
import com.test.test.testprojand.RecyclerViewPackage.ModelRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

import static com.test.test.testprojand.HttpUtil.setCommonHttp;
import static com.test.test.testprojand.HttpUtil.setRequestProperty;

public class CallApi {

    public static void ThisPopularSearch(String strType , Context context , int amount, Context listContext) {
        AsyncTaskGetData asyncTaskGetData = new AsyncTaskGetData(context, (result, isSuccess) -> {
            if (isSuccess) {
                try {
                    Object object = new JSONTokener(result.toString()).nextValue();
                    GlobData.posofitem = GlobData.modelRecyclerViews.size();

                    GlobData.modelRecyclerViews = new ArrayList<>();
                    GlobData.strArticles = strType;

                    for (int i = 0; i < ((JSONArray)((JSONObject)object).get("results")).length(); i++) {
                        GlobData.modelRecyclerViews.add(new ModelRecyclerView(
                                ((JSONObject)((JSONArray)((JSONObject)object).get("results")).get(i)).get("title").toString(),
                                ((JSONObject)((JSONArray)((JSONObject)object).get("results")).get(i)).get("published_date").toString() ));
                    }

                    if (listContext != null) {
                        ((ListActivity) listContext).onNotifyDataChange(GlobData.modelRecyclerViews);
                        return;
                    }

                    Utils.ChangeActivityWithFinish(context, ListActivity.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        asyncTaskGetData.execute(setCommonHttp(GlobData.strURL+"/mostpopular/v2/"+strType+"/"+amount+".json?&api-key="+ context.getResources().getString(R.string.api_key)), setRequestProperty());
    }
}
