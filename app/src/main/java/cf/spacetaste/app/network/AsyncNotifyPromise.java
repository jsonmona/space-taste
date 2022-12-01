package cf.spacetaste.app.network;

import androidx.annotation.UiThread;

public interface AsyncNotifyPromise {
    @UiThread
    void onResult(boolean success);
}
