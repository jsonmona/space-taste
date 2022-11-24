package cf.spacetaste.app;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import cf.spacetaste.app.databinding.Navi2FragmentBinding;

public class Navi2_Fragment extends Fragment {

    private Navi2FragmentBinding binding;
    private ArrayList<MatzipList> list;
    private MatzipListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = Navi2FragmentBinding.inflate(inflater, container, false);
        MapView mapView = new MapView(getActivity());
        binding.map.addView(mapView);

        list = new ArrayList<>();
        list.add(new MatzipList(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383" , "1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 4.87f, new ArrayList<String>()));
        list.add(new MatzipList(R.drawable.gukbap, "화목순대국", "서울특별시 성동구 성수동2가 성덕정15길 6-9", "1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요\"", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
        list.add(new MatzipList(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383","1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 0.87f, new ArrayList<String>()));
        list.add(new MatzipList(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383", "1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
        list.add(new MatzipList(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383" , "1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 4.87f, new ArrayList<String>()));

        adapter = new MatzipListAdapter(list, getActivity().getApplicationContext());
        LinearLayoutManager linear = new LinearLayoutManager(getActivity().getApplicationContext());
        binding.recyclerView.setLayoutManager(linear);
        binding.recyclerView.setAdapter(adapter);
        return binding.getRoot();
    }
}