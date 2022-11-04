package cf.spacetaste.app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Nav3.class);
                startActivity(intent);
            }
        });
    }


//    private BottomNavigationView.OnNavigationItemSelectedListener OnNavigationItemSelectedListener;{
//        OnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//            @Override
//            public boolean setOnNavigationItemSelectedListener(@Nullable MenuItem item){
//
//                switch (item.getItemId()){
//                    case R.id.navbar1:
//                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                        startActivity(intent);
//                    case R.id.navbar2:
//                        Intent intent2 = new Intent(MainActivity.this, MainActivity.class);
//                        startActivity(intent2);
//                    case R.id.navbar3:
//                        Intent intent3 = new Intent(MainActivity.this, Nav3.class);
//                        startActivity(intent3);
//                    case R.id.navbar4:
//                        Intent intent4 = new Intent(MainActivity.this, MainActivity.class);
//                        startActivity(intent4);
//                }
//            }
//        };
//    }
}