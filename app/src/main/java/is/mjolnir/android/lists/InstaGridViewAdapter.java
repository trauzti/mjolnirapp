package is.mjolnir.android.lists;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.List;

import is.mjolnir.android.BuildConfig;
import is.mjolnir.android.R;
import is.mjolnir.android.api.InstagramApiService;
import is.mjolnir.android.models.InstagramCache;
import is.mjolnir.android.models.instagram.InstagramImage;
import is.mjolnir.android.models.instagram.InstagramResponse;
import is.mjolnir.android.views.SquaredImageView;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;

import static android.widget.ImageView.ScaleType.CENTER_CROP;

public final class InstaGridViewAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<String> urls = InstagramCache.instagramURLsLowRes;

    public static final int STATUS_TOO_MANY_REQUESTS = 429;

    private boolean mLoadingMore = true;
    private InstagramApiService instagramApiService;
    private RestAdapter restAdapter;
    private String next_max_tag_id = null;
    private Callback<InstagramResponse> instagramCallback;

    public InstaGridViewAdapter(Context context) {
        mContext = context;
        RestAdapter.LogLevel logLevel = RestAdapter.LogLevel.NONE;

        Gson gson = new GsonBuilder()
                .create();

        if (BuildConfig.DEBUG) {
            logLevel = RestAdapter.LogLevel.BASIC;
            //logLevel = RestAdapter.LogLevel.FULL;
        }

        restAdapter = new RestAdapter.Builder()
                .setEndpoint("https://api.instagram.com")
                .setConverter(new GsonConverter(gson))
                .setLogLevel(logLevel)
                .build();

        instagramCallback = new Callback<InstagramResponse>() {
            @Override
            public void success(InstagramResponse instagramResponse, Response response) {
                if (instagramResponse == null || instagramResponse.data == null || instagramResponse.data.size() == 0) {
                    //tvInstagramResults.setText("Ekkert fannst");
                    showToast("Ekkert fannst");
                }


                for (InstagramImage instagramImage : instagramResponse.data) {
                    try {
                        String lowResolution = instagramImage.images.low_resolution.url;

                        //tvInstagramResults.setText(tvInstagramResults.getText().toString() + "\n" + standardResolution);

                        InstagramCache.instagramURLsLowRes.add(lowResolution);
                        InstagramCache.instagramImages.add(instagramImage);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                next_max_tag_id = instagramResponse.pagination.next_max_tag_id;
                mLoadingMore = false;


                notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                if (error != null) {
                    Response reseponse = error.getResponse();
                    if (reseponse != null && reseponse.getStatus() == STATUS_TOO_MANY_REQUESTS) {
                        showToast("Kvótinn er búinn. Bíddu í <60 mín.");
                    } else {
                        showToast("Villa kom upp");
                    }
                } else {
                    showToast("Villa kom upp");
                }
            }
        };

        instagramApiService = restAdapter.create(InstagramApiService.class);
        InstagramCache.instagramImages.clear();
        InstagramCache.instagramURLsLowRes.clear();

        instagramApiService.getFirstImages("ebcc90c8e531481f99e39fbccfe6b9e1", instagramCallback);

    }

    @Override public View getView(int position, View convertView, ViewGroup parent) {
        SquaredImageView view = (SquaredImageView) convertView;
        if (view == null) {
            view = new SquaredImageView(mContext);
            view.setScaleType(CENTER_CROP);
        }

        // Get the image URL for the current position.
        String url = getItem(position);

        // Trigger the download of the URL asynchronously into the image view.
        Picasso.with(mContext) //
                .load(url) //
                //.placeholder(R.drawable.placeholder) //
                .error(R.drawable.mjolnirlogo) //
                .fit() //
                .tag(mContext) //
                .into(view);

        return view;
    }

    public void showToast(String msg) {
        Toast toast = Toast.makeText(mContext, msg, Toast.LENGTH_SHORT);
        View toastView = toast.getView();
        TextView toastText = (TextView) toastView.findViewById(android.R.id.message);
        toastText.setBackgroundColor(mContext.getResources().getColor(R.color.mjolnirtransparent));
        toast.show();
    }

    @Override public int getCount() {
        return urls.size();
    }

    @Override public String getItem(int position) {
        if (!mLoadingMore && position == urls.size() -1) {
            mLoadingMore = true;
            if (next_max_tag_id != null) {
                instagramApiService.getMoreImages("ebcc90c8e531481f99e39fbccfe6b9e1", next_max_tag_id, instagramCallback);
            }

        }
        return urls.get(position);
    }

    @Override public long getItemId(int position) {
        return position;
    }
}