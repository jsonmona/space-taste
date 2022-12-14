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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cf.spacetaste.app.data.MatzipCreateRequest;

public class Nav3_Fragment extends Fragment {
    private View view;
    private Uri uri;
    private ImageView imageView;
    private Button selectImageBtn,btnHashTag,addbtn;
    private List<String> SelectedHashTag;
    private AlertDialog dialog;
    private AlertDialog.Builder builder;
    private EditText et_name,et_addr,et_addrDetail;
    private String bcode;

    public Nav3_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_navi3, container, false);

        et_name = view.findViewById(R.id.et_name);
        et_addrDetail = view.findViewById(R.id.et_addrDetail);

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
                //?????? ?????? ?????? ???????????? ??????
                Intent intent = new Intent(getActivity(), Nav3_Fragment_addr.class);
                getSearchResult.launch(intent);
            }
        });
        addbtn = view.findViewById(R.id.addbtn);
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et_name.getText().toString();
                String baseAddress = et_addr.getText().toString();
                String detailAddress = et_addrDetail.getText().toString();

                if(name.equals("")||bcode.equals("")||baseAddress.equals("")||detailAddress.equals("")||SelectedHashTag.equals("")){
                    builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("??? ??? ?????? ??????????????????")
                            .setNegativeButton("??????",null)
                            .create();
                    dialog.show();
                } else {
                    MatzipCreateRequest req = new MatzipCreateRequest(name,bcode,baseAddress,detailAddress,SelectedHashTag,uri);
                    AppState.getInstance(getActivity()).createMatzip(req,((success, result) -> {
                        if (success) {
                            Toast.makeText(getActivity(), "?????????????????????. ???????????? ????????? ???????????????", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(), AddReview.class);
                            intent.putExtra("matzipInfo", result);
                            getSearchResult.launch(intent);
                        } else {
                            Toast.makeText(getActivity(), "?????? ????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }));
                }
            }
        });

        return view;
    }
    private final ActivityResultLauncher<Intent> getSearchResult =registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                //Nav3_Fraagment_addr???????????? ?????? ?????? ???????????? ????????????..(setResult??? ??????)
                if (result.getResultCode() == getActivity().RESULT_OK){
                    if(result.getData() != null){
                        String address = result.getData().getStringExtra("address");
                        bcode = result.getData().getStringExtra("bcode");
                        et_addr.setText(address);
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
        builder.setTitle("??????????????? ?????? ??????????????????");

        //???????????????
        builder.setMultiChoiceItems(R.array.HashTag, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                //????????? ????????? ??????
                String[] items = getResources().getStringArray(R.array.HashTag);
                //????????? ????????? ??????
                if(b){
                    SelectedHashTag.add(items[i]);
                }else if(SelectedHashTag.contains(items[i])){
                    SelectedHashTag.remove(items[i]);
                }
            }
        });

        //OK?????????
        builder.setPositiveButton("??????",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int i) {
                String final_selection ="";

                for(String item : SelectedHashTag){
                    final_selection = final_selection+ "\n" + item;
                }

                Toast.makeText(getActivity(), "????????? ???????????????" + final_selection, Toast.LENGTH_SHORT).show();
            }
        });
        //???????????????
        builder.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}