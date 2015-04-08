package is.mjolnir.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;

import is.mjolnir.android.R;
import nl.matshofman.saxrssreader.RssFeed;
import nl.matshofman.saxrssreader.RssItem;
import nl.matshofman.saxrssreader.RssReader;

public class MjolnirNews extends ActionBarActivity {
    /*
    http://www.mjolnir.is/feed
    http://developer.android.com/reference/android/webkit/WebView.html
    https://androidresearch.wordpress.com/2012/01/21/creating-a-simple-rss-application-in-android/
     */

    TextView newsText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mjolnir_news);

        newsText = (TextView) findViewById(R.id.newsText);

        new Thread() {
            public void run() {
                try {


                    URL url = new URL("http://www.mjolnir.is/feed");
                    RssFeed feed = RssReader.read(url);

                    ArrayList<RssItem> rssItems = feed.getRssItems();
                    String s = "";
                    for (RssItem rssItem : rssItems) {
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
                    final String ss = s;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            newsText.setText(ss);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
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
