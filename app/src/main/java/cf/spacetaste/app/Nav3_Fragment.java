package cf.spacetaste.app;

import android.app.Activity;
import android.app.usage.NetworkStats;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Nav3_Fragment extends Fragment {
    private View view;
    private Uri uri;
    private ImageView imageView;
    private Button selectImageBtn,btnHashTag;
    private List<String> SelectedHashTag;
    private AlertDialog.Builder builder;
    private static final int SEARCH_ADDRESS_ACTIVITY = 10000;
    private EditText et_addr;

    public Nav3_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_navi3, container, false);
        selectImageBtn = view.findViewById(R.id.selectImageBtn);
        imageView = view.findViewById(R.id.add_image);
        selectImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityResult.launch(intent);
            }
        });

        btnHashTag = view.findViewById(R.id.btnHashTag);
        btnHashTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        et_addr = (EditText) view.findViewById(R.id.et_addr);
        //block touch
        et_addr.setFocusable(false);
        et_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //주소 검색 웹뷰 화면으로 이동
                Intent intent = new Intent(getActivity(), Nav3_Fragment_addr.class);
                getSearchResult.launch(intent);
            }
        });

        return view;
    }
    private final ActivityResultLauncher<Intent> getSearchResult =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //Nav3_Fraagment_addr로부터의 결과 값이 이곳으로 전달된다..(setResult에 의해)
                if (result.getResultCode() == getActivity().RESULT_OK){
                    if(result.getData() != null){
                        String data = result.getData().getStringExtra("data");
                        et_addr.setText(data);
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> startActivityResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if( result.getResultCode() == getActivity().RESULT_OK && result.getData() != null){
                        uri = result.getData().getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
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
        builder = new AlertDialog.Builder(getActivity());
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

                Toast.makeText(getActivity(), "선택된 해시태그는" + final_selection, Toast.LENGTH_SHORT).show();
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

}