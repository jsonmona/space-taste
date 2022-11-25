package cf.spacetaste.app;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.util.Utility;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class HomeActivity extends AppCompatActivity {
    FrameLayout main_ly;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // 네비바 프래그먼트 넣기
        init(); //객체 정의
        SettingListener(); //리스너 등록

        //맨 처음 시작할 탭 설정
//        bottomNavigationView.setSelectedItemId(R.id.navbar1);
    }
    private void init() {
        main_ly = findViewById(R.id.main_ly);
        bottomNavigationView = findViewById(R.id.bottomNavi);
    }

    private void SettingListener() {
        //선택 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new TabSelectedListener());
    }
    class TabSelectedListener implements BottomNavigationView.OnNavigationItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navbar1: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_ly, new Nav3_Fragment())
                            .commit();
                    return true;
                }
                case R.id.navbar2: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_ly, new Nav3_Fragment())
                            .commit();
                    return true;
                }
                case R.id.navbar3: {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_ly, new Nav3_Fragment())
                            .commit();
                    return true;
                }
                case R.id.navbar4: {
                    // 내 정보 페이지 띄우기
                    Intent Intent = new Intent(getApplicationContext(), InfoActivity.class);
                    startActivity(Intent);
                    return true;
                }
            }

            return false;
        }
    }
}