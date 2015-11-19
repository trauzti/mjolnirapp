package is.mjolnir.android.api;

import is.mjolnir.android.models.timetable.TimetableResponse;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by traustis on 3/31/15.
 */
public interface MjolnirTimetableApiService {

    @GET("/trauzti/mjolnir-timetable/master/timetable.json")
    void getTimeTable(Callback<TimetableResponse> cb);
    // https://raw.githubusercontent.com/trauzti/mjolnir-timetable/master/timetable.json
}
