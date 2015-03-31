package is.mjolnir.android.models.instagram;

import java.util.List;

/**
 * Created by traustis on 3/31/15.
 */
public class InstagramResponse {
    public List<InstagramImage> data;
    public InstagramPagination pagination;
}

/*
{
    "data": [
        ...
    ],
    "meta": {
        "code": 200
    },
    "pagination": {
        "deprecation_warning": "next_max_id and min_id are deprecated for this endpoint; use min_tag_id and max_tag_id instead",
        "min_tag_id": "1427664706518357",
        "next_max_id": "1427479939279439",
        "next_max_tag_id": "1427479939279439",
        "next_min_id": "1427664706518357",
        "next_url": "https://api.instagram.com/v1/tags/mjolnirmma/media/recent?client_id=ebcc90c8e531481f99e39fbccfe6b9e1&max_tag_id=1427479939279439"
    }
}
 */