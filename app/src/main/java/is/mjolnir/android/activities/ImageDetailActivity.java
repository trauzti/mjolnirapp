package is.mjolnir.android.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import is.mjolnir.android.R;
import is.mjolnir.android.models.InstagramCache;
import is.mjolnir.android.models.instagram.InstagramImage;
import is.mjolnir.android.views.SquaredImageView;

public class ImageDetailActivity extends ActionBarActivity {

    public static final String TAG = ImageDetailActivity.class.getName();
    private SquaredImageView imageView;
    private int position;
    private InstagramImage instagramImage;

    private TextView tvLabel, tvOwner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate savedInstanceState ==" + savedInstanceState);

        if (savedInstanceState != null) {
            if (InstagramCache.instagramImages.size() == 0) {
                finish();
                return;
            }

        }
        setContentView(R.layout.activity_image_detail);
        position = getIntent().getIntExtra("position", 0);
        tvLabel = (TextView) findViewById(R.id.imageDetailText);
        tvOwner = (TextView) findViewById(R.id.imageDetailOwner);
        setTitle("");


        instagramImage = InstagramCache.instagramImages.get(position);
        imageView = (SquaredImageView) findViewById(R.id.imageDetailView);
        String label = instagramImage.caption.text;
        String url = instagramImage.images.standard_resolution.url;
        String owner = instagramImage.caption.from.username;

        //String owner = instagramImage.type // TODO: fetch this



        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(this) //
                .load(url) //
                //.placeholder(R.drawable.placeholder) //
                .error(R.drawable.mjolnirlogo) //
                .fit() //
                .tag(this) //
                .into(imageView);

        tvLabel.setText(label);
        tvOwner.setText(owner);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");
        // TODO: Save the instagram images
    }


    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}
