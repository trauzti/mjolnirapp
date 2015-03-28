package is.mjolnir.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class DayScheduleActivity extends ActionBarActivity {

    public final String TAG = DayScheduleActivity.class.getName();

    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_schedule);
        lv = (ListView) findViewById(R.id.list);
        Intent i = getIntent();
        final int day = i.getIntExtra("day", 0);
        Log.d(TAG, "day=" + day);
        //Toast.makeText(this, "day=" + day, Toast.LENGTH_LONG).show();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        String days[] = {"","mánudagur", "þriðjudagur", "miðvikudagur", "fimmtudagur", "föstudagur", "laugardagur", "sunnudagur"};
        actionBar.setTitle(days[day]);

        List<Item> items = new ArrayList<Item>();
        items.add(new Header("SALUR 1"));
        for (CT ct: Timetable.getClassesForWeekday(day, 1)) {
            items.add(new ListItem(ct.time, ct.name));
        }
        items.add(new Header("SALUR 2"));
        for (CT ct: Timetable.getClassesForWeekday(day, 2)) {
            items.add(new ListItem(ct.time, ct.name));
        }

        items.add(new Header("SALUR 3"));
        for (CT ct: Timetable.getClassesForWeekday(day, 3)) {
            items.add(new ListItem(ct.time, ct.name));
        }



        TwoTextArrayAdapter adapter = new TwoTextArrayAdapter(this, items);
        lv.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_day_schedule, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_customize) {
            // TODO: Slide up
            startActivity(new Intent(DayScheduleActivity.this, CustomizeClasses.class));
            return true;
        }

        if (id == android.R.id.home) {
            //  The action bar will
            // automatically handle clicks on the Home/Up button, so long
            // as you specify a parent activity in AndroidManifest.xml.
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        finish();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
    }
}
