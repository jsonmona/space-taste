package cf.spacetaste.app;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class MainActivity extends AppCompatActivity {
    private View loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loginButton = findViewById(R.id.btnLogin);

        String keyHash = Utility.INSTANCE.getKeyHash(this);
        System.out.println("keyHash: " + keyHash);

        Function2<OAuthToken, Throwable, Unit> callback = (token, error) -> {
            if (error != null) {
                error.printStackTrace();
            }
            else if (token != null) {
                String accessToken = token.getAccessToken();
                AppState.getInstance(this).checkUserAuth(accessToken, (success, result) -> {
                    if (success) {System.out.println(result.isNewUser());}
                    else {
                        Toast.makeText(this,"네트워크에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        };

        loginButton.setOnClickListener(view -> UserApiClient.getInstance().loginWithKakaoAccount(MainActivity.this, callback));
    }
}