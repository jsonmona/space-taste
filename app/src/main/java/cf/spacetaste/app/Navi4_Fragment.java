package cf.spacetaste.app;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

import cf.spacetaste.common.AddressInfoDTO;

public class Navi4_Fragment extends Fragment {

    private TextView txtUserName, txtInfo, txtRegist, infoAcRange, infoLiRange;
    private ImageView imgUser;
    private RecyclerView userList;
    private Button btnIdentify;

    private String formatLocation(AddressInfoDTO x) {
        return x.getSiGunGu() + " " + x.getEupMyeonDong() + " " + x.getDongRi();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup View = (ViewGroup) inflater.inflate(R.layout.activity_info, container, false);

        btnIdentify = View.findViewById(R.id.btnIdentify);
        btnIdentify.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), NeighborhoodActivity.class);
            startActivity(intent);
        });

        txtUserName = View.findViewById(R.id.txtUserName);
        txtInfo = View.findViewById(R.id.txtInfo);
        txtRegist = View.findViewById(R.id.txtRegist);
        infoAcRange = View.findViewById(R.id.infoAcRange);
        infoLiRange = View.findViewById(R.id.infoLiRange);
        imgUser = View.findViewById(R.id.imgUser);
        imgUser.setClipToOutline(true);
        AppState.getInstance(getActivity()).getUserInfo((success, result) -> {
            if (success) {
                txtUserName.setText(result.getUsername());
                txtInfo.setText(result.getUsername() + "님의 정보");
                txtRegist.setText(result.getUsername() + "님이 리뷰한 맛집");
                if (result.getActiveArea() != null)
                    infoAcRange.setText(formatLocation(result.getActiveArea()));
                if (result.getLivingArea() != null)
                    infoLiRange.setText(formatLocation(result.getLivingArea()));
                Glide.with(getActivity()).load(result.getProfilePhotoUrl()).into(imgUser);
                System.out.println("result.getProfilePhotoUrl(): " + result.getProfilePhotoUrl());
            } else {
                // 네트워크 오류, 서버 오류, 기타등등
                Toast.makeText(getActivity(), "회원정보를 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        userList = View.findViewById(R.id.userList);
        AppState.getInstance(getActivity()).searchByReviewedUser((success, result) -> {
            if (success) {
                // result 활용해 처리
                MatzipListAdapter adapter = new MatzipListAdapter(result, getActivity().getApplicationContext());
                LinearLayoutManager linear = new LinearLayoutManager(getActivity().getApplicationContext());
                adapter.setOnItemClickedListner((position, data) -> {
                    Toast.makeText(getActivity(), data + ": " + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), Matzip_Detail.class);
                    intent.putExtra("matzipInfo", result.get(position));
                    startActivity(intent);
                });
                userList.setLayoutManager(linear);
                userList.setAdapter(adapter);
            } else {
                // 네트워크 오류, 서버 오류, 기타등등
                Toast.makeText(getActivity(), "ERROR!", Toast.LENGTH_SHORT).show();
            }
        });

        return View;
    }
}