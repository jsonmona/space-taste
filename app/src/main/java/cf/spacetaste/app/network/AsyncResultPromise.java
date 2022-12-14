package cf.spacetaste.app.network;

import androidx.annotation.UiThread;

public interface AsyncResultPromise<T> {
    @UiThread
    void onResult(boolean success, T result);
}
