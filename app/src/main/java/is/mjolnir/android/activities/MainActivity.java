package is.mjolnir.android.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Calendar;

import io.fabric.sdk.android.Fabric;
import is.mjolnir.android.BuildConfig;
import is.mjolnir.android.R;
import is.mjolnir.android.api.MjolnirTimetableApiService;
import is.mjolnir.android.models.Timetable;
import is.mjolnir.android.models.timetable.TimetableResponse;
import is.mjolnir.android.views.BackgroundSetter;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;


public class MainActivity extends ActionBarActivity {

    public final String TAG = MainActivity.class.getName();

    private Drawable pressed;
    private Button btnSchedule;
    private MjolnirTimetableApiService timetableApiService;
    private RestAdapter restAdapter;


    /*
      http://www.rainbowbreeze.it/navigationbar-in-style-iphone-uitabbarcontroller-per-android/

      https://api.instagram.com/v1/tags/mjolnirmma/media/recent?client_id=ebcc90c8e531481f99e39fbccfe6b9e1
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate savedInstanceState ==" + savedInstanceState);

        if (BuildConfig.REPORT_TO_CRASHLYTICS) {
            Fabric.with(this, new Crashlytics());
        }
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Timetable.loadRejectedClasses(this);

        int[] buttonIds = {R.id.day1, R.id.day2, R.id.day3, R.id.day4, R.id.day5, R.id.day6, R.id.day7};
        final int[] dayIds = {Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};
        final String[] dayNames = {"Mánudagur", "Þriðjudagur", "Miðvikudagur", "Fimmtudagur", "Föstudagur", "Laugardagur", "Sunnudagur"};

        for (int i = 0; i < buttonIds.length; i++) {
            final int buttonId = buttonIds[i];
            final int day = dayIds[i];
            final String dayName = dayNames[i];

            findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(MainActivity.this, String.format("Day #%d clicked", buttonId), Toast.LENGTH_LONG);
                    Intent intent = new Intent(MainActivity.this, DayScheduleActivity.class);
                    intent.putExtra("day", day);
                    intent.putExtra("dayName", dayName);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_left,
                            R.anim.anim_slide_out_left);
                }
            });


        }

        pressed = getResources().getDrawable(R.drawable.btn_mjolnir_navigation_pressed);
        btnSchedule = (Button) findViewById(R.id.scheduleButton);
        BackgroundSetter.setBackground(btnSchedule, pressed);

        findViewById(R.id.daytoday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, String.format("Day today"), Toast.LENGTH_LONG);

                Intent intent = new Intent(MainActivity.this, DayScheduleActivity.class);

                Calendar c = Calendar.getInstance();
                int today = c.get(Calendar.DAY_OF_WEEK);
                int index = 0;
                for (; index<dayIds.length; index++) {
                    if (today == dayIds[index]) {
                        break;
                    }
                }
                intent.putExtra("dayName", dayNames[index]);

                intent.putExtra("day", today);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);
            }
        });


        RestAdapter.LogLevel logLevel = RestAdapter.LogLevel.NONE;

        Gson gson = new GsonBuilder()
                .create();

        if (BuildConfig.DEBUG) {
            logLevel = RestAdapter.LogLevel.BASIC;
            //logLevel = RestAdapter.LogLevel.FULL;
        }

        restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://raw.githubusercontent.com")
                .setConverter(new GsonConverter(gson))
                .setLogLevel(logLevel)
                .build();

        timetableApiService = restAdapter.create(MjolnirTimetableApiService.class);
        loadTimetable();



    }

    public void saveJSONTimetableToPrefs(String json) {
        SharedPreferences keyValues = getApplicationContext().getSharedPreferences("mjolnir", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = keyValues.edit();
        editor.putString( "timetable", json );
        editor.commit();
        Log.d(TAG, "saveJSONTimetableToPrefs success");
        //Log.d(TAG, "json="+json);
    }

    public String getJSONTimetableFromPrefs() {
        SharedPreferences keyValues = getApplicationContext().getSharedPreferences("mjolnir", Context.MODE_PRIVATE);
        String json = keyValues.getString("timetable", null);
        if (json == null) {
            Log.d(TAG, "getJSONTimetableFromPrefs failure");
        } else {
            Log.d(TAG, "getJSONTimetableFromPrefs success");
        }
        //Log.d(TAG, "json="+json);
        return json;
    }

    public String getJSONTimetableFromAssets() {
        try {
            InputStream is = getApplicationContext().getAssets().open("timetable.json");
            String json = stringFromInputStream(is);
            Log.d(TAG, "getJSONTimetableFromAssets success");
            //Log.d(TAG, "json="+json);
            return json;
        }  catch(IOException e){
            Log.e(TAG, "Error reading json from device", e);
        }
        Log.d(TAG, "getJSONTimetableFromAssets failure");
        return null;
    }

    public void loadTimetable() {
        timetableApiService.getTimeTable(new Callback<TimetableResponse>() {
            @Override
            public void success(TimetableResponse timetableResponse, Response response) {
                Log.d(TAG, "timetableApiService.getTimetable success");
                Timetable.timetableResponse = timetableResponse;
                Timetable.loadRejectedClasses(MainActivity.this);
                MainActivity.this.saveJSONTimetableToPrefs(response.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "timetableApiService.getTimetable failure");
                String json = MainActivity.this.getJSONTimetableFromPrefs();
                if (json == null) {
                    json = MainActivity.this.getJSONTimetableFromAssets();
                    MainActivity.this.saveJSONTimetableToPrefs(json);
                }
                Gson gson = new Gson();
                TimetableResponse timetableResponse = gson.fromJson(json, TimetableResponse.class);
                Timetable.timetableResponse = timetableResponse;
                Timetable.loadRejectedClasses(MainActivity.this);

            }
        });

    }


    private String stringFromInputStream(InputStream paramInputStream) throws IOException {
        StringWriter localStringWriter = new StringWriter();
        char[] arrayOfChar = new char[1024];
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream, "UTF-8"));
        while (true) {
            int i = localBufferedReader.read(arrayOfChar);
            if (i == -1)
                break;
            localStringWriter.write(arrayOfChar, 0, i);
        }
        return localStringWriter.toString();
    }

    public void openSchedule(View view) {
    }

    public void openInstagramFeed(View view) {
        Intent intent = new Intent(this, InstaGridView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openNewsFeed(View view) {
        Intent intent = new Intent(this, MjolnirNews.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


}
