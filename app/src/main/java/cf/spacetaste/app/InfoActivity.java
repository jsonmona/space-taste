package cf.spacetaste.app;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InfoActivity extends AppCompatActivity {

    private Button btnRegist, btnLike, btnBack;
    private FragmentuserLike fragmentuserLike;
    private FragmentuserRegist fragmentuserRegist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);





        // ******** Navi4_Fragment에서 작업해주세요 ******* //






//        fragmentuserRegist = new FragmentuserRegist();
//        fragmentuserLike = new FragmentuserLike();
//
//        //프래그먼트 매니저 획득
//        FragmentManager fragmentManager = getSupportFragmentManager();
//
//        //프래그먼트 Transaction 획득
//        //프래그먼트를 올리거나 교체하는 작업을 Transaction이라고 합니다.
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        //프래그먼트를 FrameLayout의 자식으로 등록해줍니다.
//        fragmentTransaction.add(R.id.userList, fragmentuserRegist);
//        //commit을 하면 자식으로 등록된 프래그먼트가 화면에 보여집니다.
//        fragmentTransaction.commit();

        btnRegist = findViewById(R.id.btnRegist);
        btnLike = findViewById(R.id.btnLike);
        btnBack = findViewById(R.id.btnBack);

//        btnRegist.setOnClickListener(view -> {
//            FragmentManager fm1 = getSupportFragmentManager();
//            FragmentTransaction ft1 = fragmentManager.beginTransaction();
//            ft1.replace(R.id.userList, fragmentuserRegist);
//            ft1.commit();
//        });
//
//        btnLike.setOnClickListener(view -> {
//            FragmentManager fm2 = getSupportFragmentManager();
//            FragmentTransaction ft2 = fragmentManager.beginTransaction();
//            ft2.replace(R.id.userList, fragmentuserLike);
//            ft2.commit();
//        });

        btnBack.setOnClickListener(view -> {
            finish();
        });

    }
}