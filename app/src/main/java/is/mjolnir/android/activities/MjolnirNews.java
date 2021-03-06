package is.mjolnir.android.activities;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.net.URL;
import java.util.ArrayList;

import is.mjolnir.android.BuildConfig;
import is.mjolnir.android.R;
import is.mjolnir.android.lists.MjolnirNewsAdapter;
import is.mjolnir.android.views.BackgroundSetter;
import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;

public class MjolnirNews extends ActionBarActivity {
    /*
    http://www.mjolnir.is/feed
    http://developer.android.com/reference/android/webkit/WebView.html
    https://androidresearch.wordpress.com/2012/01/21/creating-a-simple-rss-application-in-android/
     */

    private ListView newsList;
    private TextView newsText;
    private MjolnirNewsAdapter adapter;
    private ArrayList<RssItem> rssItems = new ArrayList<>();
    private Drawable pressed;
    private Button btnNews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mjolnir_news);
        ActionBar actionBar = getSupportActionBar();
        setTitle("");
        if (actionBar != null) {
            actionBar.hide();
        }


        pressed = getResources().getDrawable(R.drawable.btn_mjolnir_navigation_pressed);
        btnNews = (Button) findViewById(R.id.newsButton);
        BackgroundSetter.setBackground(btnNews, pressed);

        newsText = (TextView) findViewById(R.id.newsText);
        newsList = (ListView) findViewById(R.id.newsList);

        adapter = new MjolnirNewsAdapter(this, rssItems);


        /* Not working so I moved this into MjolnirNewsAdapter
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(rssItems.get(position).getLink());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        */
        newsList.setAdapter(adapter);

        new Thread() {
            public void run() {
                try {
                    URL url = new URL("http://www.mjolnir.is/feed");
                    RssFeed feed = RssReader.read(url);

                    String s = "";
                    rssItems.clear();
                    rssItems.addAll(feed.getRssItems());

                    /*
                    for (RssItem rssItem : rssItems) {
                        String headline = String.format("%s\n%s", rssItem.getPubDate(), rssItem.getTitle());
                        headlines.add(headline);
                        links.add(rssItem.getLink());
                        Log.d("RSS Reader", rssItem.getTitle());
                        s += rssItem.getPubDate() + "\n";
                        s += rssItem.getTitle() + "\n";
                        s += rssItem.getDescription() + "\n";
                        s += rssItem.getLink() + "\n";
                        s += rssItem.getContent() + "\n";
                        s += rssItem.getFeed() + "\n";
                        s += "\n";
                        //newsText.setText(newsText.getText().toString() + rssItem.getTitle() + "\n");
                    }
                    */

                    final String ss = s;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //newsText.setText(ss);

                            adapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    if (BuildConfig.REPORT_TO_CRASHLYTICS) {
                        Crashlytics.logException(e);
                    } else {
                        e.printStackTrace();
                    }
                }


            }
        }.start();
        /*
        PojoXml pojoXml = PojoXmlFactory.createPojoXml();

        try {
            InputStream is = new URL("http://www.mjolnir.is/feed").openConnection().getInputStream();
        } catch (Exception e) {

            StringWriter writer = new StringWriter();
            IOUtils.copy(is, writer, "UTF-8");
            String newsXml = writer.toString();
            NewsWrapper newsWrapper = (NewsWrapper) pojoXml.getPojo(newsXml,NewsWrapper.class);

        }
        */
    }

    /*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mjolnir_news, menu);
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


    public void openSchedule(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openInstagramFeed(View view) {
        Intent intent = new Intent(this, InstaGridView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    public void openNewsFeed(View view) {
    }

}
