package cf.spacetaste.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PopActivity extends AppCompatActivity {
    Button btnYes, btnNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pop);
        Intent inIntent = getIntent();

        btnYes = (Button) findViewById(R.id.btnYes);
        btnYes.setOnClickListener(view -> {
            Intent ouIntent = new Intent(getApplicationContext(), NeighborhoodActivity.class);
            ouIntent.putExtra("allow", true);
            setResult(RESULT_OK, ouIntent);
            finish();
        });

        btnNo = (Button) findViewById(R.id.btnNo);
        btnNo.setOnClickListener(view -> {
            Intent ouIntent = new Intent(getApplicationContext(), NeighborhoodActivity.class);
            ouIntent.putExtra("allow", false);
            setResult(RESULT_OK, ouIntent);
            finish();
        });
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if( event.getAction() == MotionEvent.ACTION_OUTSIDE ) {
            return false;
        }
        return true;
    }

}
