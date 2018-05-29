package code.android.thaihn.rxandroid.screen.main;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import code.android.thaihn.rxandroid.data.interator.GetDataInterator;
import code.android.thaihn.rxandroid.data.model.SongResponse;
import code.android.thaihn.rxandroid.service.SongApi;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;

    @Override
    public void loadDataWithHttpConnection() {
        getDataWithRxHttpConnection("https://api-v2.soundcloud.com/charts?kind=top&genre=soundcloud%3Agenres%3Apop&client_id=a7Ucuq0KY8Ksn8WzBG6wj4x6pcId6BpU&limit=20&offset=20");
    }

    @Override
    public void loadData() {
        getDataWithRxRetrofit();
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

    private void getDataWithRxHttpConnection(final String url) {
        Observable<SongResponse> observable = Observable.create(new ObservableOnSubscribe<SongResponse>() {
            @Override
            public void subscribe(ObservableEmitter<SongResponse> emitter) throws Exception {
                try {
                    GetDataInterator getDataInterator = new GetDataInterator();
                    String result = getDataInterator.getContentFromUrl(url);
                    SongResponse songResponse = new Gson().fromJson(result, SongResponse.class);
                    emitter.onNext(songResponse);
                    emitter.onComplete();
                } catch (Exception e) {
                    e.printStackTrace();
                    emitter.onError(e);
                }

            }
        });

        Observer<SongResponse> observer = new Observer<SongResponse>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(SongResponse songResponse) {
                // Show list
//                mView.showList(songResponse.getCollection());
            }

            @Override
            public void onError(Throwable e) {
                // Show error
                mView.showError(e.getMessage());
            }

            @Override
            public void onComplete() {
            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    private void getDataWithRxRetrofit() {
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

}
