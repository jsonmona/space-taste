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
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Nav3 extends AppCompatActivity {
    private Uri uri;
    private ImageView imageView;
    private Button selectImageBtn,btnHashTag;
    private List<String> SelectedHashTag;
    private AlertDialog.Builder builder;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private EditText et_addr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav3);

        selectImageBtn = findViewById(R.id.selectImageBtn);
        imageView = findViewById(R.id.add_image);
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent);
            }
        });

        btnHashTag = findViewById(R.id.btnHashTag);
        btnHashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        et_addr = (EditText) findViewById(R.id.et_addr);
        Button btn_search = (Button) findViewById(R.id.button);

        if (btn_search != null) {
            btn_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent i = new Intent(Nav3.this, Nav3_addr.class);
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }
            });
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
    public void showDialog(){
        SelectedHashTag = new ArrayList<>();
        builder = new AlertDialog.Builder(Nav3.this);
        builder.setTitle("해시태그를 모두 선택해주세요");

        //클릭이벤트
        builder.setMultiChoiceItems(R.array.HashTag, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                //데이터 리스트 담기
                String[] items = getResources().getStringArray(R.array.HashTag);
               //선택된 아이템 담기
               if(b){
                   SelectedHashTag.add(items[i]);
               }else if(SelectedHashTag.contains(items[i])){
                   SelectedHashTag.remove(items[i]);
               }
            }
        });

        //OK이벤트
        builder.setPositiveButton("확인",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String final_selection ="";

                for(String item : SelectedHashTag){
                    final_selection = final_selection+ "\n" + item;
                }

                Toast.makeText(getApplicationContext(), "선택된 해시태드는" + final_selection, Toast.LENGTH_SHORT).show();
            }
        });
        //취소이벤트
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        et_addr.setText(data);
                    }
                }
                break;
        }
    }
}

