package cf.spacetaste.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cf.spacetaste.app.data.MatzipInfo;

public class Matzip_Detail extends AppCompatActivity {
    private Uri uri;
    private TextView detail_name, detail_addr, detail_ethashtag, txtImgMore;
    private ArrayList<ImageView> detail_pics = new ArrayList();
    private ImageButton detailBackbtn;
    private Button goReviewbtn;
    private AlertDialog.Builder builder;
    private EditText addReview_ettext;
    private TextView num1, num2, num3, num4;
    private RatingBar detail_rateTaste, detail_ratePrice, detail_ratePolite, detail_rateClean;
    private TableRow secongImgRow;
    private RecyclerView reviewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapzip_detail);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent inIntent = getIntent();
        MatzipInfo matzipInfo = (MatzipInfo) inIntent.getSerializableExtra("matzipInfo");

        reviewList = findViewById(R.id.reviewList);
        AppState.getInstance(getApplicationContext()).listMatzipReviews(matzipInfo, (success, result) -> {
            if (success) {
                if (result.size() != 0) {
                    // result 활용해 처리
                    ReviewListAdapter adapter = new ReviewListAdapter(result, getApplicationContext());
                    LinearLayoutManager linear = new LinearLayoutManager(getApplicationContext());
                    reviewList.setLayoutManager(linear);
                    reviewList.setAdapter(adapter);
                } else {
                    Toast.makeText(getApplicationContext(), "리뷰가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            } else {
                // 네트워크 오류, 서버 오류, 기타등등
                Toast.makeText(getApplicationContext(), "ERROR!", Toast.LENGTH_SHORT).show();
            }
        });

        secongImgRow = findViewById(R.id.secongImgRow);
        detail_pics.add(findViewById(R.id.detail_pic1));
        detail_pics.add(findViewById(R.id.detail_pic2));
        detail_pics.add(findViewById(R.id.detail_pic3));
        detail_pics.add(findViewById(R.id.detail_pic4));
        detail_pics.add(findViewById(R.id.detail_pic5));
        detail_pics.add(findViewById(R.id.detail_pic6));
        txtImgMore = findViewById(R.id.txtImgMore);
        AppState.getInstance(getApplicationContext()).listMatzipPhotos(matzipInfo, (success, result) -> {
            System.out.println("result: " + result);
            if (result != null) {
                int end_idx = 6;
                if (result.size() < end_idx)
                    end_idx = result.size();
                for (int i = 0; i < end_idx; i++)
                    Glide.with(getApplicationContext()).load(result.get(i)).into(detail_pics.get(i));
                if (3 < result.size()) {
                    secongImgRow.setVisibility(View.VISIBLE);
                    if (6 < result.size()) {
                        detail_pics.get(5).setAlpha((float) 0.4);
                        txtImgMore.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


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

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        detail_rateTaste = findViewById(R.id.detail_rateTaste);
        detail_ratePrice = findViewById(R.id.detail_ratePrice);
        detail_ratePolite = findViewById(R.id.detail_ratePolite);
        detail_rateClean = findViewById(R.id.detail_rateClean);

        if (matzipInfo.getStar() != null) {
            num1.setText(String.format("%.1f", matzipInfo.getStar().getScoreTaste()));
            num2.setText(String.format("%.1f", matzipInfo.getStar().getScorePrice()));
            num3.setText(String.format("%.1f", matzipInfo.getStar().getScoreKindness()));
            num4.setText(String.format("%.1f", matzipInfo.getStar().getScoreClean()));
        }
        if (matzipInfo.getStar() != null) {
            detail_rateTaste.setRating(matzipInfo.getStar().getScoreTaste());
            detail_ratePrice.setRating(matzipInfo.getStar().getScorePrice());
            detail_ratePolite.setRating(matzipInfo.getStar().getScoreKindness());
            detail_rateClean.setRating(matzipInfo.getStar().getScoreClean());
        }

        goReviewbtn = findViewById(R.id.goReviewbtn);
        goReviewbtn.setOnClickListener(view -> {
            Intent intent = new Intent(Matzip_Detail.this, AddReview.class);
            intent.putExtra("matzipInfo", matzipInfo);
            startActivity(intent);
        });
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}

