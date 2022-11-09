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
        et_addr.setFocusable(false);
        et_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("주소설정페이지", "주소입력창 클릭");
                int status = NetworkStatus.getConnectivityStatus(getActivity().getApplicationContext());
                if(status == NetworkStatus.TYPE_MOBILE || status == NetworkStatus.TYPE_WIFI) {
                    Log.i("주소설정페이지", "주소입력창 클릭");
                    Intent i = new Intent(getActivity().getApplicationContext(), Nav3_Fragment_addr.class);
                    // 주소결과
                    startActivityForResult(i, SEARCH_ADDRESS_ACTIVITY);
                }else {
                    Toast.makeText(getActivity().getApplicationContext(), "인터넷 연결을 확인해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        Log.i("test", "onActivityResult");

        switch (requestCode) {
            case SEARCH_ADDRESS_ACTIVITY:
                if (resultCode == getActivity().RESULT_OK) {
                    String data = intent.getExtras().getString("data");
                    if (data != null) {
                        Log.i("test", "data:" + data);
                        et_addr.setText(data);
                    }
                }
                break;
        }
    }

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
    public static class NetworkStatus {
        public static final int TYPE_WIFI = 1;
        public static final int TYPE_MOBILE = 2;
        public static final int TYPE_NOT_CONNECTED = 3;

        public static int getConnectivityStatus(Context context){ //해당 context의 서비스를 사용하기위해서 context객체를 받는다.
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            if(networkInfo != null){
                int type = networkInfo.getType();
                if(type == ConnectivityManager.TYPE_MOBILE){//쓰리지나 LTE로 연결된것(모바일을 뜻한다.)
                    return TYPE_MOBILE;
                }else if(type == ConnectivityManager.TYPE_WIFI){//와이파이 연결된것
                    return TYPE_WIFI;
                }
            }
            return TYPE_NOT_CONNECTED;  //연결이 되지않은 상태
        }
    }

}