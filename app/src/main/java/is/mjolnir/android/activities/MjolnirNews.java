package is.mjolnir.android.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.net.URL;
import java.util.ArrayList;

import is.mjolnir.android.BuildConfig;
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

    private ListView newsList;
    private TextView newsText;
    // Binding data
    ArrayAdapter adapter;
    ArrayList<String> headlines;
    ArrayList<String> links;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mjolnir_news);

        newsText = (TextView) findViewById(R.id.newsText);
        newsList = (ListView) findViewById(R.id.newsList);

        headlines = new ArrayList();
        links = new ArrayList();

        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, headlines);

        newsList.setAdapter(adapter);
        newsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Uri uri = Uri.parse(links.get(position));
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        new Thread() {
            public void run() {
                try {


                    URL url = new URL("http://www.mjolnir.is/feed");
                    RssFeed feed = RssReader.read(url);

                    ArrayList<RssItem> rssItems = feed.getRssItems();
                    String s = "";
                    for (RssItem rssItem : rssItems) {
                        String headline = String.format("%s\n%s", rssItem.getPubDate(), rssItem.getTitle());
                        headlines.add(headline);
                        links.add(rssItem.getLink());
                        /*
                        Log.d("RSS Reader", rssItem.getTitle());
                        s += rssItem.getPubDate() + "\n";
                        s += rssItem.getTitle() + "\n";
                        s += rssItem.getDescription() + "\n";
                        s += rssItem.getLink() + "\n";
                        s += rssItem.getContent() + "\n";
                        s += rssItem.getFeed() + "\n";
                        s += "\n";
                        */
                        //newsText.setText(newsText.getText().toString() + rssItem.getTitle() + "\n");
                    }
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
        setTitle("");
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
