package cf.spacetaste.app;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import cf.spacetaste.app.databinding.Navi1FragmentBinding;

public class Navi1_Fragment extends Fragment {

    private Navi1FragmentBinding binding;
    private RecyclerView recyclerView;
    private MatzipTagAdapter adapter;
    private ArrayList<MatzipTag> list;
    private MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        list = new ArrayList<>();
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        list.add(new MatzipTag("#태그", R.drawable.examplefood));
        adapter = new MatzipTagAdapter(list);

        adapter.setOnItemClickedListner(new MatzipListAdapter.OnItemClickListner() {
            @Override
            public void onItemClicked(int position, String data) {
                Toast.makeText(getContext(), position + data, Toast.LENGTH_SHORT).show();
                ((MainActivity)getActivity()).showMatzipList();
            }
        });

        binding = Navi1FragmentBinding.inflate(inflater, container, false);
        recyclerView = binding.recyclerView;
        GridLayoutManager grid = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(grid);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new Spacing(30, 10));

        return binding.getRoot();
    }

    // binding 메모리 누수 방지
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}

class MatzipTag {
    String tag;
    Integer thumbnail;

    public MatzipTag(String tag, Integer thumbnail) {
        this.tag = tag;
        this.thumbnail = thumbnail;
    }

    public MatzipTag(String tag) {
        this(tag, null);
    }
}

class Spacing extends RecyclerView.ItemDecoration {

    private int spacing;
    private int topSpacing;

    public Spacing(int spacing, int topSpacing) {
        this.spacing = spacing;
        this.topSpacing = topSpacing;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // Column Index
        int index = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        // Item 포지션
        int position = parent.getChildLayoutPosition(view);
        //좌측 Spacing 절반
        if (index == 0) {
            outRect.right = spacing;
            outRect.left = spacing * 2;
        } else {
            outRect.right = spacing * 2;
            outRect.left = spacing;
        }

        if (position < 2) {
            outRect.top = topSpacing;
        } else {
            outRect.top = 0;
        }
    }
}