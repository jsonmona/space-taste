package cf.spacetaste.app;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == PopActivity.RESULT_OK) {
                    Intent inIntent = result.getData();
                    boolean resultEnroll = inIntent.getBooleanExtra("allow", false);
                    if (resultEnroll) {
                        Toast.makeText(getApplicationContext(), "동네인증페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
                        Intent Intent = new Intent(getApplicationContext(),NeighborhoodActivity.class);
                        startActivity(Intent);
                    } else {
                        Toast.makeText(getApplicationContext(), "메인페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
                        Intent Intent = new Intent(getApplicationContext(),HomeActivity.class);
                        startActivity(Intent);
                    }
                }
            });
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
                AppState.getInstance(this).login(accessToken, (success, result) -> {
                    if (success) {
                        System.out.println(result.isNewUser());
                        if (result.isNewUser()) {
                            Toast.makeText(this, "새로운 회원입니다.", Toast.LENGTH_SHORT).show();
                            Intent intentAllow = new Intent(getApplicationContext(), PopActivity.class);
                            activityResultLauncher.launch(intentAllow); // 의사에 따라 갈 페이지가 달라짐
                        } else {
                            Toast.makeText(this, "기존 회원입니다.", Toast.LENGTH_SHORT).show();
                            Intent Intent = new Intent(getApplicationContext(),HomeActivity.class);
                            startActivity(Intent);
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
//            UserApiClient.getInstance().loginWithKakaoAccount(this, callback);
            // 아래는 임시코드
            boolean isNew = true; // 임의로 바꿔서 테스트 진행
            if (isNew){
                Toast.makeText(this, "새로운 회원입니다.", Toast.LENGTH_SHORT).show();
                Intent intentAllow = new Intent(getApplicationContext(), PopActivity.class);
                activityResultLauncher.launch(intentAllow); // 의사에 따라 갈 페이지가 달라짐
            }else {
                Toast.makeText(this, "기존 회원입니다.", Toast.LENGTH_SHORT).show();
                Intent Intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(Intent);
            }
        });
    }
}
