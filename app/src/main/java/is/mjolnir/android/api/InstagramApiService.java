package is.mjolnir.android.api;

import is.mjolnir.android.models.instagram.InstagramResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by traustis on 3/31/15.
 */
public interface InstagramApiService {

    // https://api.instagram.com/v1/tags/mjolnirmma/media/recent?client_id=ebcc90c8e531481f99e39fbccfe6b9e1

    @GET("/v1/tags/mjolnirmma/media/recent")
    void getFirstImages(@Query("client_id") String clientId, Callback<InstagramResponse> cb);


    @GET("/v1/tags/mjolnirmma/media/recent")
    void getMoreImages(@Query("client_id") String clientId, @Query("max_tag_id") String next_max_tag_id, Callback<InstagramResponse> cb);
}
