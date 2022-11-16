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
import android.widget.ImageView;
import android.widget.RatingBar;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;


public class AddReview extends AppCompatActivity{
    private Uri uri;
    private ImageView imageView;
    private Button addReviewImageBtn,cancelButton,okButton;
    private AlertDialog.Builder builder;
    private EditText addReview_ettext;
    private RatingBar rateTaste,ratePrice,ratePolite,rateClean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addreview);

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

        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent( AddReview.this, Detail.class ); 여기 상세리뷰만들고 수정
//                startActivity( intent );
            }
        });
        okButton = findViewById(R.id.okButton);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DB에 추가하는 부분 추후 추가
                builder = new AlertDialog.Builder(AddReview.this);
                AlertDialog dialog = builder.setMessage("리뷰작성이 완료되었습니다.")
                        .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
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
