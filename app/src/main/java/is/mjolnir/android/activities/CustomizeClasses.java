package is.mjolnir.android.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import is.mjolnir.android.R;
import is.mjolnir.android.lists.InteractiveArrayAdapter;
import is.mjolnir.android.models.Timetable;


public class CustomizeClasses extends ActionBarActivity {

    public static final String TAG = CustomizeClasses.class.getName();
    private ListView lv;
    private InteractiveArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate savedInstanceState ==" + savedInstanceState);
        Timetable.ensureRejectedMapNotNull(this);

        setContentView(R.layout.activity_customize_classes);
        lv = (ListView) findViewById(R.id.list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Velja sýndan tíma");

        adapter = new InteractiveArrayAdapter(this, Timetable.getAllClasses());


        lv.setAdapter(adapter);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_customize_classes, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            Timetable.saveRejectedClasses(this);
            onBackPressed();
            return true;
        }

        if (id == R.id.action_select_all) {
            for (String rejectedKey : Timetable.rejectedMap.keySet()) {
                Timetable.rejectedMap.put(rejectedKey, false);
            }
            Timetable.needToRefreshRejectedClasses = true;
            adapter.notifyDataSetChanged();
            return true;
        }

        if (id == R.id.action_select_none) {
            for (String rejectedKey : Timetable.rejectedMap.keySet()) {
                Timetable.rejectedMap.put(rejectedKey, true);
            }
            Timetable.needToRefreshRejectedClasses = true;
            adapter.notifyDataSetChanged();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        Timetable.saveRejectedClasses(this);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}
