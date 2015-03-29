package is.mjolnir.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.crashlytics.android.Crashlytics;

import java.util.Calendar;

import io.fabric.sdk.android.Fabric;
import is.mjolnir.android.R;
import is.mjolnir.android.models.Timetable;


public class MainActivity extends ActionBarActivity {

    public final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Timetable.loadRejectedClasses(this);

        int[] buttonIds = {R.id.day1, R.id.day2, R.id.day3, R.id.day4, R.id.day5, R.id.day6, R.id.day7};
        final int[] dayIds = {Calendar.MONDAY, Calendar.TUESDAY, Calendar.WEDNESDAY, Calendar.THURSDAY, Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};
        final String[] dayNames = {"mánudagur", "þriðjudagur", "miðvikudagur", "fimmtudagur", "föstudagur", "laugardagur", "sunnudagur"};

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

    }


}
