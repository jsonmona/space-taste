package cf.spacetaste.app;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import cf.spacetaste.app.data.MatzipCreateRequest;
import cf.spacetaste.app.data.MatzipInfo;
import cf.spacetaste.app.network.AsyncResultPromise;
import cf.spacetaste.app.network.RemoteService;

public class AppState {
    private static final String TAG = AppState.class.getSimpleName();

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

        //remoteService.createMatzip(req, cb);
        Log.w(TAG, "STUB createMatzip("+ req +")");
        MatzipInfo mockedResult = new MatzipInfo();
        mockedResult.setMatzipId(123);
        mockedResult.setHashtags(req.getHashtags());
        mockedResult.setBaseAddress(req.getBaseAddress());
        mockedResult.setDetailAddress(req.getDetailAddress());
        cb.onResult(true, mockedResult);
    }
}
