package cf.spacetaste.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class Navi4_Fragment extends Fragment {

    private Button btnRegist, btnLike, btnBack;
    private RecyclerView userList;
    private FragmentuserLike fragmentuserLike;
    private FragmentuserRegist fragmentuserRegist;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup View = (ViewGroup) inflater.inflate(R.layout.activity_info, container, false);
        btnRegist = View.findViewById(R.id.btnRegist);
        btnLike = View.findViewById(R.id.btnLike);
        btnBack = View.findViewById(R.id.btnBack);
        userList = View.findViewById(R.id.userList);

        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.getInstance(getActivity()).searchMatzip(new ArrayList<>(Arrays.asList("한식")), "", (success, result) -> {
                    if (success) {
                        // result 활용해 처리
                        MatzipListAdapter adapter = new MatzipListAdapter(result, getActivity().getApplicationContext());
                        LinearLayoutManager linear = new LinearLayoutManager(getActivity().getApplicationContext());
                        userList.setLayoutManager(linear);
                        userList.setAdapter(adapter);
                    } else {
                        // 네트워크 오류, 서버 오류, 기타등등
                        Toast.makeText(getActivity(), "ERROR!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppState.getInstance(getActivity()).searchMatzip(new ArrayList<>(Arrays.asList("세계음식")), "", (success, result) -> {
                    if (success) {
                        // result 활용해 처리
                        MatzipListAdapter adapter = new MatzipListAdapter(result, getActivity().getApplicationContext());
                        LinearLayoutManager linear = new LinearLayoutManager(getActivity().getApplicationContext());
                        userList.setLayoutManager(linear);
                        userList.setAdapter(adapter);
                    } else {
                        // 네트워크 오류, 서버 오류, 기타등등
                        Toast.makeText(getActivity(), "ERROR!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        return View;
    }
}
