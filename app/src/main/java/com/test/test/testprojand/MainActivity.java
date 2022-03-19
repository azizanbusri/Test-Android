package com.test.test.testprojand;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.test.test.testprojand.AsyncTaskPackage.AsyncInterface;
import com.test.test.testprojand.AsyncTaskPackage.AsyncTaskGetData;
import com.test.test.testprojand.RecyclerViewPackage.ModelRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import static com.test.test.testprojand.CallApi.ThisPopularSearch;
import static com.test.test.testprojand.HttpUtil.setCommonHttp;
import static com.test.test.testprojand.HttpUtil.setRequestProperty;

public class MainActivity extends AppCompatActivity {

    CardView cardView1;
    CardView cardView2;
    CardView cardView3;
    CardView cardView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView1 = findViewById(R.id.cardView1);
        cardView2 = findViewById(R.id.cardView2);
        cardView3 = findViewById(R.id.cardView3);
        cardView4 = findViewById(R.id.cardView4);

        SetCardViewListener();

        Utils.SetActionBarDetail(MainActivity.this, "NYT");

    }


    //region set listener
    public void SetCardViewListener(){
        cardView1.setOnClickListener(view -> SearchDialog());

        cardView2.setOnClickListener(view -> ThisPopularSearch("viewed", MainActivity.this, 7, null));

        cardView3.setOnClickListener(view -> ThisPopularSearch( "shared", MainActivity.this, 7, null));

        cardView4.setOnClickListener(view -> ThisPopularSearch( "emailed", MainActivity.this, 7, null));
    }
    //endregion

    //region custom alert dialog

    public void SearchDialog () {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater = (LayoutInflater)MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view2 = inflater.inflate(R.layout.alertlayout, null);
        EditText txtArticles = view2.findViewById(R.id.si_SignIn);
        alertDialog.setView(view2);
        alertDialog.setPositiveButton("OK", null);
        TextView txtTitle = view2.findViewById(R.id.lblMessageTitle);
        txtTitle.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.purple_700));

        txtTitle.setText(getResources().getString(R.string.search_articles));
        alertDialog.setView(view2);

        alertDialog.setPositiveButton("OK", null);
        alertDialog.setNegativeButton("Cancel", null);

        final AlertDialog mAlertDialog = alertDialog.create();
        mAlertDialog.setCanceledOnTouchOutside(false);
        mAlertDialog.setCancelable(false);

        mAlertDialog.setOnShowListener(dialog -> {
            Button b = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            b.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            b.setOnClickListener(view -> {
                // TODO Do something

                if(txtArticles.getText().toString().equals("")) {
                    Toast toast = Toast.makeText(MainActivity.this, "Please enter your keyword", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.TOP| Gravity.CENTER, 0, 0);
                    toast.show();
                } else {
                    mAlertDialog.dismiss();
                    AsyncTaskGetData asyncTaskGetData = new AsyncTaskGetData(MainActivity.this, (result, isSuccess) -> {
                        if (isSuccess) {
                            try {
                                Object object = new JSONTokener(result.toString()).nextValue();
                                GlobData.modelRecyclerViews = new ArrayList<>();

                                for (int i = 0 ; i < ((JSONArray)((JSONObject)((JSONObject)object).get("response")).get("docs")).length(); i++) {
                                    GlobData.modelRecyclerViews.add(new ModelRecyclerView(
                                            ((JSONObject)((JSONObject)((JSONArray)((JSONObject)((JSONObject)object).get("response")).get("docs")).get(i)).get("headline")).get("main").toString(),
                                            DateConversion(((JSONObject)((JSONArray)((JSONObject)((JSONObject)object).get("response")).get("docs")).get(i)).get("pub_date").toString())));
                                }
                                Utils.ChangeActivityWithFinish(MainActivity.this, ListActivity.class);
                            } catch (JSONException | ParseException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    asyncTaskGetData.execute(setCommonHttp(GlobData.strURL+"/search/v2/articlesearch.json?q="+txtArticles.getText().toString()+"&api-key="+getResources().getString(R.string.api_key)), setRequestProperty());
                }

            });

            Button bn = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            bn.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            bn.setTextColor(getResources().getColor(R.color.material_on_surface_disabled, getTheme()));
            bn.setOnClickListener(view -> {
                // TODO Do something
                mAlertDialog.dismiss();
            });
        });

        mAlertDialog.show();
    }

    //endregion custom alert dialog

    public String DateConversion(String strDate) throws ParseException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter currentFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ");
            DateTimeFormatter convertedOutputFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
            LocalDateTime starting = LocalDateTime.parse(strDate, currentFormatter);
            return starting.format(convertedOutputFormatter);
        } else {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.US);
            Date currentdate=sdf.parse(strDate);
            SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            return sdf2.format(currentdate);
        }
    }

    //region Popular Function Region

    //endregion



}