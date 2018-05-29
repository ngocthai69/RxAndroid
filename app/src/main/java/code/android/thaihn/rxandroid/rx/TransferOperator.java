package code.android.thaihn.rxandroid.rx;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import code.android.thaihn.rxandroid.data.model.Introduce;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class TransferOperator {

    public static final String TAG = TransferOperator.class.getSimpleName();

    public static void demoMap() {
        createObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Introduce, Introduce>() {
                    @Override
                    public Introduce apply(Introduce introduce) throws Exception {
                        introduce.setName(introduce.getName().toUpperCase());
                        introduce.setAddress("AAA");
                        return introduce;
                    }
                })
                .subscribe(new Observer<Introduce>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: Map ");
                    }

                    @Override
                    public void onNext(Introduce introduce) {
                        Log.i(TAG, "onNext: Map " + introduce.getName() + ", "
                                + introduce.getGender() + ", " + introduce.getAddress());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: Map " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: Map");
                    }
                });
    }

    public static void demoFlatMap() {
        createObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<Introduce, ObservableSource<Introduce>>() {
                    @Override
                    public ObservableSource<Introduce> apply(Introduce introduce) throws Exception {
                        return getAddressObservable(introduce);
                    }
                })
                .subscribe(new Observer<Introduce>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: FlatMap");
                    }

                    @Override
                    public void onNext(Introduce introduce) {
                        Log.i(TAG, "onNext: FlatMap " + introduce.getName() + ", "
                                + introduce.getGender() + ", " + introduce.getAddress());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: FlatMap");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: FlatMap");
                    }
                });
    }

    public static void demoConcatMap() {
        createObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .concatMap(new Function<Introduce, ObservableSource<Introduce>>() {
                    @Override
                    public ObservableSource<Introduce> apply(Introduce introduce) throws Exception {
                        return getAddressObservable(introduce);
                    }
                })
                .subscribe(new Observer<Introduce>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: ConcatMap");
                    }

                    @Override
                    public void onNext(Introduce introduce) {
                        Log.i(TAG, "onNext: ConcatMap " + introduce.getName() + ", "
                                + introduce.getGender() + ", " + introduce.getAddress());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: ConcatMap");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ConcatMap");
                    }
                });
    }

    private static Observable<Introduce> getAddressObservable(final Introduce introduce) {

        final String[] addresses = new String[]{
                "1600 Amphitheatre Parkway, Mountain View, CA 94043",
                "2300 Traverwood Dr. Ann Arbor, MI 48105",
                "500 W 2nd St Suite 2900 Austin, TX 78701",
                "355 Main Street Cambridge, MA 02142"
        };

        return Observable
                .create(new ObservableOnSubscribe<Introduce>() {
                    @Override
                    public void subscribe(ObservableEmitter<Introduce> emitter) throws Exception {
                        if (!emitter.isDisposed()) {
                            introduce.setAddress(addresses[new Random().nextInt(2) + 0]);


                            // Generate network latency of random duration
                            int sleepTime = new Random().nextInt(1000) + 500;

                            Thread.sleep(sleepTime);
                            emitter.onNext(introduce);
                            emitter.onComplete();
                        }
                    }
                }).subscribeOn(Schedulers.io());
    }

    private static Observable<Introduce> createObservable() {
        String[] names = new String[]{"mark", "john", "trump", "obama"};

        final List<Introduce> introduces = new ArrayList<>();
        for (String name : names) {
            Introduce introduce = new Introduce();
            introduce.setName(name);
            introduce.setEmail(name + "@gmail.com");
            introduce.setGender("male");
            introduces.add(introduce);
        }

        return Observable.create(new ObservableOnSubscribe<Introduce>() {
            @Override
            public void subscribe(ObservableEmitter<Introduce> emitter) throws Exception {
                for (Introduce introduce : introduces) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(introduce);
                    }
                }
                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        }).subscribeOn(Schedulers.io());
    }

}
