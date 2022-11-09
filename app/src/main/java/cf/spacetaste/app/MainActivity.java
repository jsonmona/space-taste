package cf.spacetaste.app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    FrameLayout main_ly;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init(); //객체 정의
        SettingListener(); //리스너 등록

        //맨 처음 시작할 탭 설정
//        bottomNavigationView.setSelectedItemId(R.id.home);
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
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_ly, new Nav3_Fragment())
                            .commit();
                    return true;
                }
            }

            return false;
        }
    }
}