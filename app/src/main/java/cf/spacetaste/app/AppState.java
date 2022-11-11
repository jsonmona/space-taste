package cf.spacetaste.app;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import cf.spacetaste.app.data.MatzipInfo;
import cf.spacetaste.app.network.AsyncResultPromise;
import cf.spacetaste.app.network.RemoteService;

import java.util.ArrayList;
import java.util.List;

public class AppState {
    private static volatile AppState instance;

    private Context context;
    private RemoteService remoteService;

    private AppState(@NonNull Context context) {
        this.context = context.getApplicationContext();
        this.remoteService = new RemoteService(context);
    }

    public static AppState getInstance(Context context) {
        if (instance == null) {
            synchronized (AppState.class) {
                if (instance == null)
                    instance = new AppState(context);
            }
        }
        return instance;
    }

    public void createMatzip(String name, String baseAddress, String detailAddress, List<String> hashtags, Uri photo, AsyncResultPromise<MatzipInfo> cb) {
        if (name == null || baseAddress == null || detailAddress == null)
            throw new NullPointerException("name and addresses should be not null");

        if (hashtags == null)
            hashtags = new ArrayList<>();

        remoteService.createMatzip(name, baseAddress, detailAddress, hashtags, photo, cb);
    }
}
