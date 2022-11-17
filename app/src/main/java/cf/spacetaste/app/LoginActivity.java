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

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class LoginActivity extends AppCompatActivity {
    private View btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        String keyHash = Utility.INSTANCE.getKeyHash(this);
        System.out.println("keyHash: " + keyHash);

        Function2<OAuthToken, Throwable, Unit> callback = (token, error) -> {
            if (error != null) {
                error.printStackTrace();
            } else if (token != null) {
                String accessToken = token.getAccessToken();
                AppState.getInstance(this).checkUserAuth(accessToken, (success, result) -> {
                    if (success) {
                        System.out.println(result.isNewUser());
                        if (result.isNewUser()) {
                            Toast.makeText(this, "새로운 회원입니다.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "기존 회원입니다.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(this, "네트워크에 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
            return null;
        };

        btnLogin = (ImageButton) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(v -> {
//            UserApiClient.getInstance().loginWithKakaoAccount(LoginActivity.this, callback);
            Intent intent = new Intent(LoginActivity.this, NeighborhoodActivity.class);
            // 신규 회원이면 동네인증 의사 팝업, 인텐트 돌아오는 값(예, 아니오) 받아서 페이지 이동
            // 기존 회원이면 메인페이지로 이동
            startActivity(intent);
        });
    }
}
