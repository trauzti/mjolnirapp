package is.mjolnir.android.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import is.mjolnir.android.R;
import is.mjolnir.android.lists.PicassoAdapter;

public abstract class PicassoSampleActivity extends ActionBarActivity {
    private ToggleButton showHide;
    private FrameLayout sampleContent;
    private ActionBar actionBar;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.picasso_sample_activity);
        sampleContent = (FrameLayout) findViewById(R.id.sample_content);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        final ListView activityList = (ListView) findViewById(R.id.activity_list);
        final PicassoAdapter adapter = new PicassoAdapter(this);
        activityList.setAdapter(adapter);
        activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //adapter.getItem(position).launch(PicassoSampleActivity.this, position);
            }
        });

        //showHide = (ToggleButton) findViewById(R.id.faux_action_bar_control);
        //showHide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
        //    @Override public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
        //        activityList.setVisibility(checked ? VISIBLE : GONE);
        //    }
        //});
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Picasso.with(this).cancelTag(this);
    }

    @Override public void setContentView(int layoutResID) {
        getLayoutInflater().inflate(layoutResID, sampleContent);
    }

    @Override public void setContentView(View view) {
        sampleContent.addView(view);
    }

    @Override public void setContentView(View view, ViewGroup.LayoutParams params) {
        sampleContent.addView(view, params);
    }
}