package cf.spacetaste.app;

import android.content.Context;
import androidx.annotation.NonNull;
import cf.spacetaste.app.data.MatzipCreateRequest;
import cf.spacetaste.app.data.MatzipInfo;
import cf.spacetaste.app.network.AsyncResultPromise;
import cf.spacetaste.app.network.RemoteService;

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

    public void createMatzip(MatzipCreateRequest req, AsyncResultPromise<MatzipInfo> cb) {
        req.validate();

        remoteService.createMatzip(req, cb);
    }
}
