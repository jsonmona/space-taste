package cf.spacetaste.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    FrameLayout main_ly;
    Fragment[] fragments = new Fragment[4];
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // 네비바 프래그먼트 넣기
        init(); //객체 정의
        SettingListener(); //리스너 등록

        //맨 처음 시작할 탭 설정
        bottomNavigationView.setSelectedItemId(R.id.navbar1);
    }

    private void init() {
        main_ly = findViewById(R.id.main_ly);
        bottomNavigationView = findViewById(R.id.bottomNavi);
    }

    private void SettingListener() {
        //선택 리스너 등록
        bottomNavigationView.setOnItemSelectedListener(new TabSelectedListener());
    }

    void showMatzipList() {
        Intent intent = new Intent(HomeActivity.this, MatzipListActivity.class);
        startActivity(intent);
    }

    private void addFragment(int naviPosition) {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_ly, fragments[naviPosition])
                .setReorderingAllowed(true)
                .commit();
    }

    private void showFragment(int naviPosition) {
        getSupportFragmentManager().beginTransaction()
                .show(fragments[naviPosition])
                .setReorderingAllowed(true)
                .commit();
    }

    private void hideFragment(int naviPosition) {
        for (int i = 0; i < 3; i++) {
            naviPosition = (naviPosition + 1) % 4;
            if (fragments[naviPosition] != null) {
                getSupportFragmentManager().beginTransaction()
                        .hide(fragments[naviPosition])
                        .setReorderingAllowed(true)
                        .commit();
            }
        }
    }

    class TabSelectedListener implements BottomNavigationView.OnItemSelectedListener{
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.navbar1:
                    if (fragments[0] == null) {
                        fragments[0] = new Navi1_Fragment();
                        addFragment(0);
                    } else {
                        showFragment(0);
                    }

                    hideFragment(0);
                    return true;

                case R.id.navbar2:
                    if (fragments[1] == null) {
                        fragments[1] = new Navi2_Fragment();
                        addFragment(1);
                    } else {
                        showFragment(1);
                    }

                    hideFragment(1);
                    return true;

                case R.id.navbar3:
                    if (fragments[2] == null) {
                        fragments[2] = new Nav3_Fragment();
                        addFragment(2);
                    } else {
                        showFragment(2);
                    }

                    hideFragment(2);
                    return true;

                case R.id.navbar4:
                    if (fragments[3] == null) {
                        fragments[3] = new Navi4_Fragment();
                        addFragment(3);
                    } else {
                        showFragment(3);
                    }

                    hideFragment(3);
                    return true;
            }

            return false;
        }
    }
}