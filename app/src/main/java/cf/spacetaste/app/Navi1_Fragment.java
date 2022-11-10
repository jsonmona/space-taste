package cf.spacetaste.app;

import android.graphics.Rect;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import cf.spacetaste.app.databinding.Navi1FragmentBinding;

public class Navi1_Fragment extends Fragment {

    private Navi1FragmentBinding binding;
    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private ArrayList<MatzipTag> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // onCreateView에서는 layout inflat만 하고, 다른 초기 작업은 onViewCreated에서 하는게 좋음.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = Navi1FragmentBinding.inflate(getLayoutInflater(), container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        int someInt = requireArguments().getInt("init");
        list = new ArrayList<>();
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));
        list.add(new MatzipTag("#돈까스", R.drawable.examplefood));




        recyclerView = binding.recyclerView;
        adapter = new CustomAdapter(list);
        GridLayoutManager grid = new GridLayoutManager(getActivity(), 2);
        recyclerView.setLayoutManager(grid);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new Spacing(30, 10));
    }

    // binding 메모리 누수 방지
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        binding = null;
    }
}

class Matzip {

    String matzipName;
    int matzipImage;
    String matzipLocation;
    String matzipMostRecentReview;
    float matzipStars;

    public Matzip(String matzipName, int matzipImage, String matzipLocation, String matzipMostRecentReview, float matzipStars) {
        this.matzipName = matzipName;
        this.matzipImage = matzipImage;
        this.matzipLocation = matzipLocation;
        this.matzipMostRecentReview = matzipMostRecentReview;
        this.matzipStars = matzipStars;
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

        // 상단 탑 Spacing 맨 위에 포지션 0, 1은 Spacing을 안줌
        if (position < 2) {
            outRect.top = topSpacing;
        } else {
            outRect.top = 0;
        }
    }
}