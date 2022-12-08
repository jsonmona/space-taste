package cf.spacetaste.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.kakao.sdk.user.UserApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import cf.spacetaste.common.AddressInfoDTO;

public class NeighborhoodActivity extends AppCompatActivity {
    Button btnNeiCom;
    Spinner acDong, liDong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighborhood);
        acDong = findViewById(R.id.acDong);
        liDong = findViewById(R.id.liDong);

        ArrayList<String> items = new ArrayList<>();
        HashMap<String, AddressInfoDTO> mapper = new HashMap<>();

        AppState.getInstance(getApplicationContext()).listServiceArea((success, result) -> {
            if (success) {
                for (AddressInfoDTO element : result) {
                    String addressElement = element.getSiDo() + " " + element.getSiGunGu() + " " + element.getEupMyeonDong() + " " + element.getDongRi();
                    items.add(addressElement);
                    mapper.put(addressElement, element);
                }
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                acDong.setAdapter(adapter);
                liDong.setAdapter(adapter);
            } else {
                // 네트워크 오류, 서버 오류, 기타등등
                Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_SHORT).show();
            }
        });

//        acDong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                //텍스트 색 흰색으로 바꿔주기
//                ((TextView) adapterView.getChildAt(0)).setTextColor(Color.WHITE);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        btnNeiCom = (Button) findViewById(R.id.btnNeiCom);
        btnNeiCom.setOnClickListener(v -> {
            String acString = (String) acDong.getSelectedItem();
            String liString = (String) liDong.getSelectedItem();

            AddressInfoDTO activeArea = mapper.get(acString);
            AddressInfoDTO livingArea = mapper.get(liString);

            AppState.getInstance(getApplicationContext()).changeUserArea(activeArea, livingArea, (success) -> {
                if (success) {
                    Toast.makeText(getApplicationContext(), "동네인증이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                    Log.d("d", "onCreate: " + activeArea.getSiDo());
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error!.", Toast.LENGTH_SHORT).show();
                }

            });
            Intent Intent = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(Intent); // 메인페이지로 이동
        });
    }
}