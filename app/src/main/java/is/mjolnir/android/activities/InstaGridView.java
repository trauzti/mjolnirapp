package is.mjolnir.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import is.mjolnir.android.R;
import is.mjolnir.android.lists.InstaGridViewAdapter;
import is.mjolnir.android.lists.PicassoScrollListener;

public class InstaGridView extends PicassoSampleActivity {

    public static final String TAG = InstaGridView.class.getName();


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sample_gridview_activity);
        Log.d(TAG, "onCreate savedInstanceState ==" + savedInstanceState);


        GridView gv = (GridView) findViewById(R.id.grid_view);
        gv.setAdapter(new InstaGridViewAdapter(this));
        gv.setOnScrollListener(new PicassoScrollListener(this));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InstaGridView.this, ImageDetailActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });
        setTitle("#mjolnirmma");
    }

}