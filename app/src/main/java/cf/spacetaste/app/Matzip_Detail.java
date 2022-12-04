package cf.spacetaste.app;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import cf.spacetaste.app.data.MatzipInfo;
import cf.spacetaste.app.data.MatzipReview;

public class Matzip_Detail extends AppCompatActivity {
    private Uri uri;
    private TextView detail_name, detail_addr, detail_ethashtag;
    private ImageView detail_pic1, detail_pic2, detail_pic3, detail_pic4, detail_pic5, detail_pic6;
    private ImageButton detailBackbtn;
    private Button goReviewbtn;
    private AlertDialog.Builder builder;
    private EditText addReview_ettext;
    private RatingBar detail_rateTaste, detail_ratePrice, detail_ratePolite, detail_rateClean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapzip_detail);

        Intent inIntent = getIntent();
        MatzipInfo matzipInfo = (MatzipInfo) inIntent.getSerializableExtra("matzipInfo");

        AppState.getInstance(getApplicationContext()).listMatzipReviews(matzipInfo, (success, result) -> {
            if (result.size() != 0){

            } else {
            }
        });

        detail_pic1 = findViewById(R.id.detail_pic1);
        detail_pic2 = findViewById(R.id.detail_pic2);
        detail_pic3 = findViewById(R.id.detail_pic3);
        detail_pic4 = findViewById(R.id.detail_pic4);
        detail_pic5 = findViewById(R.id.detail_pic5);
        detail_pic6 = findViewById(R.id.detail_pic6);

        detail_name = findViewById(R.id.detail_name);
        detail_name.setText(matzipInfo.getName());

        detail_addr = findViewById(R.id.detail_addr);
        detail_addr.setText(matzipInfo.getBaseAddress() + " " + matzipInfo.getDetailAddress());

        detail_ethashtag = findViewById(R.id.detail_ethashtag);
        String hashTags = "";
        for (int i = 0; i < matzipInfo.getHashtags().size(); i++) {
            hashTags += "#" + matzipInfo.getHashtags().get(i) + " ";
        }
        detail_ethashtag.setText(hashTags);

        detail_rateTaste = findViewById(R.id.detail_rateTaste);
        detail_ratePrice = findViewById(R.id.detail_ratePrice);
        detail_ratePolite = findViewById(R.id.detail_ratePolite);
        detail_rateClean = findViewById(R.id.detail_rateClean);


        detailBackbtn = findViewById(R.id.detail_backbtn);
        detailBackbtn.setOnClickListener(view -> finish());

        goReviewbtn = findViewById(R.id.goReviewbtn);
        goReviewbtn.setOnClickListener(view -> {
            Intent intent = new Intent(Matzip_Detail.this, AddReview.class);
            startActivity(intent);
        });
    }

}
