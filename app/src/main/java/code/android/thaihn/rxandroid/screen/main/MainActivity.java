package code.android.thaihn.rxandroid.screen.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import code.android.thaihn.rxandroid.BaseActivity;
import code.android.thaihn.rxandroid.R;
import code.android.thaihn.rxandroid.data.interator.GetDataInterator;
import code.android.thaihn.rxandroid.data.model.Collection;
import code.android.thaihn.rxandroid.data.model.SongResponse;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements MainContract.View,
        MainAdapter.OnClickListenerSong {

    private MainContract.Presenter mPresenter;
    private MainAdapter mMainAdapter;
    private RecyclerView mRecyclerView;

    @Override
    protected int getLayoutResources() {
        return R.layout.activity_main;
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        mRecyclerView = findViewById(R.id.recycle);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        configRecycle(this);
        mPresenter = new MainPresenter();
        mPresenter.setView(this);
//        mPresenter.loadData();
        getDataWithRx();
    }

    private void getDataWithRx() {
        Observable<SongResponse> observable = Observable.create(new ObservableOnSubscribe<SongResponse>() {
            @Override
            public void subscribe(ObservableEmitter<SongResponse> emitter) throws Exception {
                try {
                    GetDataInterator getDataInterator = new GetDataInterator();
                    String result = getDataInterator.getContentFromUrl("https://api-v2.soundcloud.com/charts?kind=top&genre=soundcloud%3Agenres%3Apop&client_id=a7Ucuq0KY8Ksn8WzBG6wj4x6pcId6BpU&limit=20&offset=20");
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
                showList(songResponse.getCollection());
            }

            @Override
            public void onError(Throwable e) {
                showError(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void showList(List<Collection> list) {
        mMainAdapter.addData(list);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, "Error " + message, Toast.LENGTH_SHORT).show();
    }

    private void configRecycle(Context context) {
        GridLayoutManager layoutManager = new GridLayoutManager(context, 2);
        mRecyclerView.setHasFixedSize(true);
        mMainAdapter = new MainAdapter(new ArrayList<Collection>());
        mMainAdapter.setOnClickListenerSong(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMainAdapter);
        mMainAdapter.notifyDataSetChanged();
    }

    @Override
    public void onMoreClicked(int position) {
        Toast.makeText(this, "fix more", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClicked(int position) {
        Toast.makeText(this, "fix item", Toast.LENGTH_SHORT).show();
    }
}
