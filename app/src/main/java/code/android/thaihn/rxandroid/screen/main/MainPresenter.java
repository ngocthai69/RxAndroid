package code.android.thaihn.rxandroid.screen.main;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import code.android.thaihn.rxandroid.data.model.SongResponse;
import code.android.thaihn.rxandroid.service.SongApi;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    @Override
    public void loadData() {
        SongApi songApi = new Retrofit.Builder()
                .baseUrl("https://api-v2.soundcloud.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(SongApi.class);
        Observable<SongResponse> getData = songApi.getListSong(
                "top",
                "soundcloud%3Agenres%3Apop",
                "a7Ucuq0KY8Ksn8WzBG6wj4x6pcId6BpU",
                20,
                20);
        getData.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SongResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        System.out.println("TTT" + "onSubscribe");
                    }

                    @Override
                    public void onNext(SongResponse songResponse) {
                        mView.showList(songResponse.getCollection());
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.showError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("TTT" + "onComplete");
                    }
                });
    }

    @Override
    public void setView(MainContract.View view) {
        this.mView = view;
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }
}
