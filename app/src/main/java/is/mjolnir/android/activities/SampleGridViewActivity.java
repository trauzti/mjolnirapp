package is.mjolnir.android.activities;

import android.os.Bundle;
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
        setTitle("#mjolnirmma");
    }

}