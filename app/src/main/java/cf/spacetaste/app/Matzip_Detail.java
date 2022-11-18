package cf.spacetaste.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Matzip_Detail extends AppCompatActivity {
    private Uri uri;
    private ImageView imageView;
    private Button goReviewbtn;
    private AlertDialog.Builder builder;
    private EditText addReview_ettext;
    private RatingBar detail_rateTaste,detail_ratePrice,detail_ratePolite,detail_rateClean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapzip_detail);

        goReviewbtn = findViewById(R.id.goReviewbtn);
        goReviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Matzip_Detail.this, AddReview.class );
                startActivity( intent );
            }
        });
    }

}