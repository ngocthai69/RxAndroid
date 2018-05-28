package code.android.thaihn.rxandroid.service;

import code.android.thaihn.rxandroid.data.model.SongResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SongApi {

    @GET("charts")
    Observable<SongResponse> getListSong(@Query("kind") String kind,
                                         @Query("genre") String genre,
                                         @Query("client_id") String id,
                                         @Query("limit") int limit,
                                         @Query("offset") int offset
    );
}
