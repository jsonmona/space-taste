package cf.spacetaste.app;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;

import cf.spacetaste.app.data.MatzipInfo;
import cf.spacetaste.app.data.MatzipReview;
import cf.spacetaste.common.ReviewInfoDTO;


public class AddReview extends AppCompatActivity{
    private Uri uri;
    private ImageView imageView;
    private ImageButton reveiwBackbtn;
    private Button addReviewImageBtn,okButton;
    private AlertDialog.Builder builder;
    private EditText addReview_ettext;
    private RatingBar rateTaste,ratePrice,ratePolite,rateClean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addreview);

        addReview_ettext = findViewById(R.id.addReview_ettext);
        //저장할때 불러와야하니 가져옴.

        addReviewImageBtn = findViewById(R.id.addReviewImageBtn);
        imageView = findViewById(R.id.addReview_image);
        addReviewImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent);
            }
        });
        rateTaste = findViewById(R.id.rateTaste);
        ratePrice = findViewById(R.id.ratePrice);
        ratePolite = findViewById(R.id.ratePolite);
        rateClean = findViewById(R.id.rateClean);

        rateTaste.setOnRatingBarChangeListener(new Listener());
        ratePrice.setOnRatingBarChangeListener(new Listener());
        ratePolite.setOnRatingBarChangeListener(new Listener());
        rateClean.setOnRatingBarChangeListener(new Listener());

        reveiwBackbtn = findViewById(R.id.reveiw_backbtn);
        reveiwBackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( AddReview.this, Matzip_Detail.class );
                startActivity( intent );
            }
        });
        okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String detail = addReview_ettext.getText().toString();
                int scoreTaste = rateTaste.getNumStars();
                int scorePrice = ratePrice.getNumStars();
                int scoreKindness = ratePolite.getNumStars();
                int scoreClean = rateClean.getNumStars();
                if(addReview_ettext.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddReview.this,R.style.MyAlertDialogStyle);
                    AlertDialog dialog = builder.setMessage("짧은 한마디를 입력해주세요")
                            .setNegativeButton("확인",null)
                            .create();
                    dialog.show();
                } else {
                    MatzipInfo matzip = new MatzipInfo(0,"","","",[],"",0);
                    ReviewInfoDTO review = new ReviewInfoDTO(0,0,0, scoreTaste, scorePrice, scoreKindness, scoreClean, detail);
                    AppState.getInstance(AddReview.this).postReview(matzip,review,((success) -> {
                        if (success) {
                            builder = new AlertDialog.Builder(AddReview.this);
                            AlertDialog dialog = builder.setMessage("리뷰 작성이 완료되었습니다.")
                                    .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent( AddReview.this, Matzip_Detail.class );
                                            startActivity( intent );
                                        }
                                    })

                                    .create();
                            dialog.show();
                        } else {
                            Toast.makeText(AddReview.this, "리뷰 추가에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }));
                }
            }
        });
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DB에 추가하는 부분 추후 추가
                builder = new AlertDialog.Builder(AddReview.this);
                AlertDialog dialog = builder.setMessage("리뷰작성이 완료되었습니다.")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent( AddReview.this, Matzip_Detail.class );
                                startActivity( intent );
                            }
                        })

                        .create();
                dialog.show();
            }
        });
    }

    class Listener implements RatingBar.OnRatingBarChangeListener {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            rateTaste.setRating(rating);
            ratePrice.setRating(rating);
            ratePolite.setRating(rating);
            rateClean.setRating(rating);
        }
    }

    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if( result.getResultCode() == RESULT_OK && result.getData() != null){
                        uri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imageView.setImageBitmap(bitmap);
                        }catch (FileNotFoundException e){
                            e.printStackTrace();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            });
}