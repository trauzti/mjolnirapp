package is.mjolnir.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity {

    public final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Timetable.loadRejectedClasses(this);

        int[] buttonIds = {R.id.day1, R.id.day2, R.id.day3, R.id.day4, R.id.day5, R.id.day6, R.id.day7};

        for (int i = 1; i <= buttonIds.length; i++) {
            final int buttonId = buttonIds[i-1];
            final int day = i;
            findViewById(buttonId).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(MainActivity.this, String.format("Day #%d clicked", buttonId), Toast.LENGTH_LONG);
                    Intent intent = new Intent(MainActivity.this, DayScheduleActivity.class);
                    intent.putExtra("day", day);
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_slide_in_left,
                            R.anim.anim_slide_out_left);
                }
            });


        }

        findViewById(R.id.daytoday).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this, String.format("Day today"), Toast.LENGTH_LONG);

                Intent intent = new Intent(MainActivity.this, DayScheduleActivity.class);

                Calendar c = Calendar.getInstance();
                int today = c.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY + 1;
                Log.d(TAG, "today= " + today);
                intent.putExtra("day", today);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_slide_in_left,
                        R.anim.anim_slide_out_left);            }
        });

    }


}
