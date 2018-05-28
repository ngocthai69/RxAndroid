package code.android.thaihn.rxandroid.screen.main;

import java.util.List;

import code.android.thaihn.rxandroid.BasePresenter;
import code.android.thaihn.rxandroid.BaseView;
import code.android.thaihn.rxandroid.data.model.Collection;
import code.android.thaihn.rxandroid.data.model.Track;

public interface MainContract {

    interface View extends BaseView {

        void showList(List<Collection> list);

        void showError(String message);
    }

    interface Presenter extends BasePresenter<View> {
        void loadData();
    }
}
