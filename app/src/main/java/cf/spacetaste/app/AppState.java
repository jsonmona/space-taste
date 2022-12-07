package cf.spacetaste.app;

import android.content.Context;
import androidx.annotation.NonNull;
import cf.spacetaste.app.data.AuthResponse;
import cf.spacetaste.app.data.MatzipCreateRequest;
import cf.spacetaste.app.data.MatzipInfo;
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

    public void login(String kakaoAccessToken, AsyncResultPromise<AuthResponse> cb) {
        remoteService.checkUserAuth(kakaoAccessToken, cb);
    }

    public void logout() {
        remoteService.logout();
    }

    public void createMatzip(MatzipCreateRequest req, AsyncResultPromise<MatzipInfo> cb) {
        req.validate();
        remoteService.createMatzip(req, cb);
    }

    public void listMatzipPhotos(MatzipInfo matzip, AsyncResultPromise<List<String>> cb) {
        remoteService.listMatzipPhotos(matzip, cb);
    }

    public void listMatzipReviews(MatzipInfo matzip, AsyncResultPromise<List<ReviewInfoDTO>> cb) {
        remoteService.listReviewOfMatzip(matzip, cb);
    }

    public void searchMatzip(List<String> tags, String term, AsyncResultPromise<List<MatzipInfo>> cb) {
        remoteService.searchMatzip(tags, term, cb);
    }

    public void listServiceArea(AsyncResultPromise<List<AddressInfoDTO>> cb) {
        remoteService.listServiceArea(cb);
    }

    public void postReview(MatzipInfo matzip, CreateReviewRequestDTO review, AsyncNotifyPromise cb) {
        remoteService.postReview(matzip, review, cb);
    }

    public void getUserInfo(AsyncResultPromise<UserInfoDTO> cb) {
        remoteService.getUserInfo(cb);
    }

    /** 내가 리뷰한 맛집 리스트를 가져옴 (최근 리뷰한 순서) */
    public void searchByReviewedUser(AsyncResultPromise<List<MatzipInfo>> cb) {
        remoteService.searchByReviewedUser(cb);
    }

    /**
     * 사용자의 지역을 변경함
     * @param activeArea 활동범위. null일 수 있음.
     * @param livingArea 생활범위. null일 수 있음.
     * @param cb 콜백
     */
    public void changeUserArea(AddressInfoDTO activeArea, AddressInfoDTO livingArea, AsyncNotifyPromise cb) {
        remoteService.changeUserArea(activeArea, livingArea, cb);
    }

    /**
     * 제일 최근 리뷰를 한번에 가져오는 함수
     * [맛집0, 맛집1, 맛집2]를 입력으로 넣으면 [맛집0의 제일 최근 리뷰, 맛집1의 제일 최근 리뷰, 맛집2의 제일 최근 리뷰] 가 나옴
     * 오류가 발생하거나 리뷰가 아예 없는 경우에는 해당 자리에 null이 들어감
     *
     * @param matzip 최근 리뷰를 알고싶은 맛집의 목록
     * @param cb 콜백
     */
    public void getLastReviewOfMatzipBatched(List<MatzipInfo> matzip, AsyncResultPromise<List<ReviewInfoDTO>> cb) {
        remoteService.getLastReviewOfMatzipBatched(matzip, cb);
    }

    public void getRandomTags(AsyncResultPromise<List<String>> cb) {
        remoteService.getRandomTags(cb);
    }

    public void getMainPhotoOfTag(String tag, AsyncResultPromise<String> cb) {
        remoteService.getMainPhotoOfTag(tag, cb);
    }
}
