package is.mjolnir.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import is.mjolnir.android.R;
import is.mjolnir.android.lists.SampleGridViewAdapter;
import is.mjolnir.android.lists.SampleScrollListener;

public class SampleGridViewActivity extends PicassoSampleActivity {



    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_gridview_activity);

        GridView gv = (GridView) findViewById(R.id.grid_view);
        gv.setAdapter(new SampleGridViewAdapter(this));
        gv.setOnScrollListener(new SampleScrollListener(this));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SampleGridViewActivity.this, ImageDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        setTitle("#mjolnirmma");
    }

}