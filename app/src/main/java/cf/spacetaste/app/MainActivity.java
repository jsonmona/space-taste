package cf.spacetaste.app;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public MainActivity() {
        super(R.layout.activity_main);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) { // 프래그먼트가 액티비티에 최초로 올려질 때. 이 이후론 savedInstancedState != null임

            Bundle bundle = new Bundle();
            bundle.putInt("init", 0);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true) // 원활한 트랙잭션(애니메이션)을 위해 필수
                    .add(R.id.fragment_container, Navi1_Fragment.class, bundle)
                    .commit();
        }
    }
}