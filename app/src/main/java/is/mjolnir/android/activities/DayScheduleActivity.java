package is.mjolnir.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import is.mjolnir.android.R;
import is.mjolnir.android.lists.Header;
import is.mjolnir.android.lists.Item;
import is.mjolnir.android.lists.ListItem;
import is.mjolnir.android.lists.TwoTextArrayAdapter;
import is.mjolnir.android.models.ClassAndTime;
import is.mjolnir.android.models.Timetable;


public class DayScheduleActivity extends ActionBarActivity {

    public final String TAG = DayScheduleActivity.class.getName();
    private List<Item> items = new ArrayList<Item>();
    private TwoTextArrayAdapter adapter;
    private int day = 0;
    private String dayName = "";


    private ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_schedule);
        Timetable.ensureRejectedMapNotNull(this);

        lv = (ListView) findViewById(R.id.list);
        Intent i = getIntent();
        day = i.getIntExtra("day", 0);
        dayName = i.getStringExtra("dayName");

        //Log.d(TAG, "day=" + day);
        //Toast.makeText(this, "day=" + day, Toast.LENGTH_LONG).show();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(dayName);



        adapter = new TwoTextArrayAdapter(this, items);
        lv.setAdapter(adapter);
        setItems();
    }


    public void setItems() {
        items.clear();
        items.add(new Header("SALUR 1"));
        for (ClassAndTime classAndTime : Timetable.getClassesForWeekday(day, 1)) {
            if (!Timetable.rejectedMap.get(classAndTime.name)) {
                items.add(new ListItem(classAndTime.time, classAndTime.name));
            }
        }
        items.add(new Header("SALUR 2"));
        for (ClassAndTime classAndTime : Timetable.getClassesForWeekday(day, 2)) {
            if (!Timetable.rejectedMap.get(classAndTime.name)) {
                items.add(new ListItem(classAndTime.time, classAndTime.name));
            }
        }

        items.add(new Header("SALUR 3"));
        for (ClassAndTime classAndTime : Timetable.getClassesForWeekday(day, 3)) {
            if (!Timetable.rejectedMap.get(classAndTime.name)) {
                items.add(new ListItem(classAndTime.time, classAndTime.name));
            }
        }
        adapter.notifyDataSetChanged();
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
        super.onBackPressed();
        overridePendingTransition(R.anim.anim_slide_in_right,
                R.anim.anim_slide_out_right);
        //        finish();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (Timetable.needToRefreshRejectedClasses) {
            Timetable.needToRefreshRejectedClasses = false;
            //Log.d(TAG, "Refreshing rejected classes");
            setItems();
        }
    }
}
