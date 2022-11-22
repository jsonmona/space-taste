package cf.spacetaste.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.kakao.sdk.user.UserApiClient;

public class NeighborhoodActivity extends AppCompatActivity {
    Button btnNeiCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighborhood);

        btnNeiCom = (Button) findViewById(R.id.btnNeiCom);
        btnNeiCom.setOnClickListener(v -> {
            // 거주지 저장하고
            Intent Intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(Intent); // 메인페이지로 이동
        });
    }
}