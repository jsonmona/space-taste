package cf.spacetaste.app;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import cf.spacetaste.app.data.AuthResponse;
import cf.spacetaste.app.data.MatzipCreateRequest;
import cf.spacetaste.app.data.MatzipInfo;
import cf.spacetaste.app.data.ReviewCreationInfo;
import cf.spacetaste.app.network.AsyncNotifyPromise;
import cf.spacetaste.app.network.AsyncResultPromise;
import cf.spacetaste.app.network.RemoteService;
import cf.spacetaste.common.AddressInfoDTO;
import cf.spacetaste.common.CreateReviewRequestDTO;
import cf.spacetaste.common.ReviewInfoDTO;
import cf.spacetaste.common.UserInfoDTO;

import java.util.List;

public class AppState {
    private static final String TAG = AppState.class.getSimpleName();

    private static volatile AppState instance;

    private Context context;
    private RemoteService remoteService;

    private AppState(@NonNull Context context) {
        this.context = context.getApplicationContext();
        this.remoteService = new RemoteService(context);
        loadToken();
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

    public boolean isLoggedIn() {
        return remoteService.isLoggedIn();
    }

    private void saveToken() {
        String token = remoteService.getToken();
        SharedPreferences sharedPref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("token", token);
        editor.apply();
    }

    private void loadToken() {
        SharedPreferences sharedPref = context.getSharedPreferences("auth", Context.MODE_PRIVATE);
        try {
            String val = sharedPref.getString("token", null);
            if (val != null)
                remoteService.setToken(val);
        } catch (ClassCastException e) {
            // ignore
        }
    }

    public void login(String kakaoAccessToken, AsyncResultPromise<AuthResponse> cb) {
        remoteService.checkUserAuth(kakaoAccessToken, new AsyncResultPromise<AuthResponse>() {
            @Override
            public void onResult(boolean success, AuthResponse result) {
                saveToken();
                cb.onResult(success, result);
            }
        });
    }

    public void logout() {
        remoteService.logout();
    }

    public void createMatzip(MatzipCreateRequest req, AsyncResultPromise<MatzipInfo> cb) {
        saveToken();
        req.validate();
        remoteService.createMatzip(req, cb);
    }

    public void listMatzipPhotos(MatzipInfo matzip, AsyncResultPromise<List<String>> cb) {
        saveToken();
        remoteService.listMatzipPhotos(matzip, cb);
    }

    public void listMatzipReviews(MatzipInfo matzip, AsyncResultPromise<List<ReviewInfoDTO>> cb) {
        saveToken();
        remoteService.listReviewOfMatzip(matzip, cb);
    }

    public void searchMatzip(List<String> tags, String term, AsyncResultPromise<List<MatzipInfo>> cb) {
        saveToken();
        remoteService.searchMatzip(tags, term, cb);
    }

    public void listServiceArea(AsyncResultPromise<List<AddressInfoDTO>> cb) {
        saveToken();
        remoteService.listServiceArea(cb);
    }

    public void postReview(MatzipInfo matzip, ReviewCreationInfo review, AsyncNotifyPromise cb) {
        saveToken();
        remoteService.postReview(matzip, review, cb);
    }

    public void getUserInfo(AsyncResultPromise<UserInfoDTO> cb) {
        saveToken();
        remoteService.getUserInfo(cb);
    }

    /** ?????? ????????? ?????? ???????????? ????????? (?????? ????????? ??????) */
    public void searchByReviewedUser(AsyncResultPromise<List<MatzipInfo>> cb) {
        saveToken();
        remoteService.searchByReviewedUser(cb);
    }

    /**
     * ???????????? ????????? ?????????
     * @param activeArea ????????????. null??? ??? ??????.
     * @param livingArea ????????????. null??? ??? ??????.
     * @param cb ??????
     */
    public void changeUserArea(AddressInfoDTO activeArea, AddressInfoDTO livingArea, AsyncNotifyPromise cb) {
        saveToken();
        remoteService.changeUserArea(activeArea, livingArea, cb);
    }

    /**
     * ?????? ?????? ????????? ????????? ???????????? ??????
     * [??????0, ??????1, ??????2]??? ???????????? ????????? [??????0??? ?????? ?????? ??????, ??????1??? ?????? ?????? ??????, ??????2??? ?????? ?????? ??????] ??? ??????
     * ????????? ??????????????? ????????? ?????? ?????? ???????????? ?????? ????????? null??? ?????????
     *
     * @param matzip ?????? ????????? ???????????? ????????? ??????
     * @param cb ??????
     */
    public void getLastReviewOfMatzipBatched(List<MatzipInfo> matzip, AsyncResultPromise<List<ReviewInfoDTO>> cb) {
        saveToken();
        remoteService.getLastReviewOfMatzipBatched(matzip, cb);
    }

    public void getRandomTags(AsyncResultPromise<List<String>> cb) {
        saveToken();
        remoteService.getRandomTags(cb);
    }

    public void getMainPhotoOfTag(String tag, AsyncResultPromise<String> cb) {
        saveToken();
        remoteService.getMainPhotoOfTag(tag, cb);
    }
}
