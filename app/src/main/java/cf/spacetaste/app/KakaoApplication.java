package cf.spacetaste.app;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this, "37f11d1931bb444ca09d4cc0a73a9124");
    }
}
