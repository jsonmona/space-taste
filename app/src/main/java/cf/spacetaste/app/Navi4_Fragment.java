package cf.spacetaste.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

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
                ArrayList<Matzip> list = Matzip.MakeExample(new ArrayList<Matzip>());

                MatzipListAdapter adapter = new MatzipListAdapter(list, getActivity().getApplicationContext());
                LinearLayoutManager linear = new LinearLayoutManager(getActivity().getApplicationContext());
                userList.setLayoutManager(linear);
                userList.setAdapter(adapter);
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Matzip> list = Matzip.MakeExample(new ArrayList<Matzip>());

                MatzipListAdapter adapter = new MatzipListAdapter(list, getActivity().getApplicationContext());
                LinearLayoutManager linear = new LinearLayoutManager(getActivity().getApplicationContext());
                userList.setLayoutManager(linear);
                userList.setAdapter(adapter);
            }
        });

        return View;
    }
}
