package cf.spacetaste.app;

import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cf.spacetaste.app.databinding.Navi1FragmentBinding;

public class Navi1_Fragment extends Fragment {

    private Navi1FragmentBinding binding;
    private RecyclerView recyclerView;
    private MatzipTagAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new MatzipTagAdapter(getContext());
        adapter.setOnItemClickedListner(new MatzipTagAdapter.OnItemClickListner() {
            @Override
            public void onItemClicked(int position, String data) {
                Log.d("debug", "Item has clicked.");

                // 서버에 tag 제출 후 list 반환
                AppState.getInstance(getActivity()).searchMatzip(new ArrayList<>(Arrays.asList(data)), data, (success, result) -> {
                    if (success) {
                        // result 활용해 처리
                        ((HomeActivity) getActivity()).showMatzipList(result);
                    } else {
                        // 네트워크 오류, 서버 오류, 기타등등
                        Toast.makeText(getActivity(), "ERROR!", Toast.LENGTH_SHORT).show();
                    }
                });
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