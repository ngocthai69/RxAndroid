package code.android.thaihn.rxandroid;

public interface BasePresenter<T> {

    void setView(T view);

    void onStart();

    void onStop();
}
