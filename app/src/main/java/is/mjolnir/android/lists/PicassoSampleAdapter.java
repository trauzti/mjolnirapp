package is.mjolnir.android.lists;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import is.mjolnir.android.R;
import is.mjolnir.android.activities.ImageDetailActivity;
import is.mjolnir.android.activities.SampleGridViewActivity;

public final class PicassoSampleAdapter extends BaseAdapter {

    private static final int NOTIFICATION_ID = 666;

    public enum Sample {
        GRID_VIEW("Image Grid View", SampleGridViewActivity.class),
        SHOW_NOTIFICATION("Sample Notification", null);

        private final Class<? extends Activity> activityClass;
        private final String name;

        Sample(String name, Class<? extends Activity> activityClass) {
            this.activityClass = activityClass;
            this.name = name;
        }

        public void launch(Activity activity, int position) {
            Intent intent = new Intent(activity, ImageDetailActivity.class);
            intent.putExtra("position", position);
            activity.startActivity(intent);
            activity.finish();
        }
    }

    private final LayoutInflater inflater;

    public PicassoSampleAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override public int getCount() {
        return Sample.values().length;
    }

    @Override public Sample getItem(int position) {
        return Sample.values()[position];
    }

    @Override public long getItemId(int position) {
        return position;
    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) convertView;
        if (view == null) {
            view = (TextView) inflater.inflate(R.layout.picasso_sample_activity_item, parent, false);
        }

        view.setText(getItem(position).name);

        return view;
    }
}
