package is.mjolnir.android.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import is.mjolnir.android.R;
import is.mjolnir.android.lists.InstaGridViewAdapter;
import is.mjolnir.android.lists.PicassoScrollListener;
import is.mjolnir.android.views.HeaderGridView;

public class InstaGridView extends PicassoSampleActivity {

    public static final String TAG = InstaGridView.class.getName();


    HeaderGridView gv;
    InstaGridViewAdapter adapter;
    private RelativeLayout rlRootOfGridView;
    private Button bHashTagMjolnirMMA, bAtMjolnirMMA;
    LinearLayout llHeader;
    Drawable pressed, unPressed;


    final boolean USE_HEADER = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sample_gridview_activity);
        Log.d(TAG, "onCreate savedInstanceState ==" + savedInstanceState);
        rlRootOfGridView = (RelativeLayout) findViewById(R.id.rootOfGridView);

        pressed = getResources().getDrawable(R.drawable.btn_mjolnir_navigation_pressed);
        unPressed = getResources().getDrawable(R.drawable.btn_mjolnir_navigation);

        gv = (HeaderGridView) findViewById(R.id.grid_view);
        if (adapter == null) {
            adapter = new InstaGridViewAdapter(this);
        }
        gv.setOnScrollListener(new PicassoScrollListener(this));
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(InstaGridView.this, ImageDetailActivity.class);
                int correctPosition = position;
                if (USE_HEADER) {
                    int numColumns = 3;
                    if (Build.VERSION.SDK_INT >= 11) {
                        numColumns = gv.getNumColumns();
                    }
                    correctPosition -= numColumns;
                }
                intent.putExtra("position", correctPosition );
                startActivity(intent);
            }
        });

        if (USE_HEADER) {
            LayoutInflater inflater = getLayoutInflater();
            llHeader = (LinearLayout) inflater.inflate(R.layout.header_instagram_navigation, rlRootOfGridView, false);
            bHashTagMjolnirMMA = (Button) llHeader.findViewById(R.id.hashTagMjolnirMMA);
            bAtMjolnirMMA = (Button) llHeader.findViewById(R.id.atMjolnirMMA);
            gv.addHeaderView(llHeader);
            hashTagMjolnirMMA(null);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            // TODO: select #mjolnirmma or @mjolnirmma here
            // OR hide the actionbar
        }
        setTitle("#mjolnirmma");
        gv.setAdapter(adapter);

    }

    public void openSchedule(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openInstagramFeed(View view) {

    }

    public void openNewsFeed(View view) {
        Intent intent = new Intent(this, MjolnirNews.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }



    public void hashTagMjolnirMMA(View view) {
        setBackground(bHashTagMjolnirMMA, pressed);
        setBackground(bAtMjolnirMMA, unPressed);
    }

    public void atMjolnirMMA(View view) {
        Toast toast = Toast.makeText(this, "Coming soon!", Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        TextView toastText = (TextView) toastView.findViewById(android.R.id.message);
        toastText.setBackgroundColor(getResources().getColor(R.color.mjolnirtransparent));
        toast.show();
        if (false) {
            setBackground(bAtMjolnirMMA, pressed);
            setBackground(bHashTagMjolnirMMA, unPressed);
        }
    }

    public static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= 16) {
            view.setBackground(drawable);
        } else {
            view.setBackgroundDrawable(drawable);
        }
    }
}