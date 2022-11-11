package cf.spacetaste.app;

import android.app.Application;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        KakaoSdk.init(this, "08bc0801cb37209a15534af8488e5f94");
    }
}
