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
import code.android.thaihn.rxandroid.rx.TransferOperator;

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
        // TODO: Load in here
//        mPresenter.loadData();
        mPresenter.loadDataWithHttpConnection();
        TransferOperator.demoFlatMap();
        TransferOperator.demoConcatMap();
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
