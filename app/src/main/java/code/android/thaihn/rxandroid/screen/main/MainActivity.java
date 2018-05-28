package code.android.thaihn.rxandroid.screen.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import code.android.thaihn.rxandroid.BaseActivity;
import code.android.thaihn.rxandroid.R;
import code.android.thaihn.rxandroid.data.model.Collection;

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
        mPresenter.loadData();
    }

    private void observableDemo() {
//        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
//                e.onNext(0);
//                e.on
//                e.onComplete();
//            }
//        });

//        Observable<String> observable1 = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> e) throws Exception {
//                e.onNext(getData("https://api-v2.soundcloud.com/charts?kind=top&genre=soundcloud%3Agenres%3Apop&client_id=a7Ucuq0KY8Ksn8WzBG6wj4x6pcId6BpU"));
//                e.onComplete();
//            }
//        });
//
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(String s) {
//                System.out.println("TTT" + s);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        };

//        observable1.subscribe(observer);

//        Observer<Integer> observer = new Observer<Integer>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                System.out.println("TTT" + "onSubscribe");
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                System.out.println("TTT" + "onNext " + integer);
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                System.out.println("TTT" + "onError ");
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("TTT" + "Complete");
//            }
//        };
//        observable.subscribe(observer);
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
