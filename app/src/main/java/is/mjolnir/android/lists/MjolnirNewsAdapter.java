package is.mjolnir.android.lists;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

import is.mjolnir.android.R;
import nl.matshofman.saxrssreader.RssItem;

public class MjolnirNewsAdapter extends ArrayAdapter<RssItem> {
    public static final String TAG = MjolnirNewsAdapter.class.getName();
    private final Activity context;
    private final ArrayList<RssItem> rssItems;
    SimpleDateFormat dateFormat;

    static class ViewHolder {
        public TextView date, title;
        public WebView mjolnirWebView;
    }

    public MjolnirNewsAdapter(Activity context, ArrayList<RssItem> rssItems) {
        super(context, R.layout.news_item, rssItems);
        this.context = context;
        this.rssItems = rssItems;
        dateFormat = new SimpleDateFormat("d. MMM, yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        // reuse views
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.news_item, null);
            // configure view holder
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.date = (TextView) rowView.findViewById(R.id.list_content1);
            viewHolder.title = (TextView) rowView.findViewById(R.id.list_content2);

            viewHolder.mjolnirWebView = (WebView) rowView.findViewById(R.id.mjolnirNewsWebView);

            rowView.setTag(viewHolder);
        }

        // fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();
        RssItem rssItem = rssItems.get(position);
        String date = dateFormat.format(rssItem.getPubDate()).toUpperCase();
        String title = rssItem.getTitle();
        String html = rssItem.getDescription();

        holder.date.setText(date);
        holder.title.setText(title);
        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(rssItems.get(position).getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                context.startActivity(intent);
            }
        });
                /*

        Log.d(TAG, "feed = " + rssItem.getFeed());
        Log.d(TAG, "content = " + rssItem.getContent());
        Log.d(TAG, "description = " + rssItem.getDescription());
        04-08 19:47:13.315    5590-5590/is.mjolnir.android D/is.mjolnir.android.lists.MjolnirNewsAdapter﹕ feed = nl.matshofman.saxrssreader.RssFeed@53031218
04-08 19:47:13.939    5590-5590/is.mjolnir.android D/is.mjolnir.android.lists.MjolnirNewsAdapter﹕ content = null
04-08 19:47:15.515    5590-5590/is.mjolnir.android D/is.mjolnir.android.lists.MjolnirNewsAdapter﹕ description = <p><img src="http://www.mjolnir.is/static/news/thumb_egill-hk-ko.jpg" alt="Egill rotar Matt Hodgson" /></p>Birgir og Egill unnu báðir MMA bardaga sína í Doncaster í gærkvöldi á innan við mínútu samtals! Diego tapaði sínum fyrsta K1 bardaga gegn heimamanni á klofnum dómaraúrskurði.
         */

        holder.mjolnirWebView.loadData(html, "text/html; charset=utf-8", null);

        return rowView;
    }
}