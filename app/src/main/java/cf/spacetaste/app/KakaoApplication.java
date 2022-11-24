package cf.spacetaste.app;

import android.app.Application;

import android.util.Log;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.common.util.Utility;

public class KakaoApplication extends Application {

    private final String TAG = "Application";

    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this, "37f11d1931bb444ca09d4cc0a73a9124");
        Log.d(TAG, "Key hash: " + Utility.INSTANCE.getKeyHash(this));
    }
}
