package cf.spacetaste.app;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.List;

import cf.spacetaste.app.data.MatzipInfo;
import cf.spacetaste.app.databinding.ActivityMatzipListItemBinding;
import lombok.SneakyThrows;

public class MatzipListAdapter extends RecyclerView.Adapter<MatzipListAdapter.ViewHolder> {

    private List<MatzipInfo> matzipList;
    private Context context;
    private OnItemClickListner itemClickListner;

    public interface OnItemClickListner {
        void onItemClicked(int position, String data) throws IOException;
    }

    public void setOnItemClickedListner(OnItemClickListner listner) {
        this.itemClickListner = listner;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ActivityMatzipListItemBinding binding;

        public ViewHolder(ActivityMatzipListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ActivityMatzipListItemBinding getBinding() {
            return binding;
        }
    }

    public MatzipListAdapter(List matzipList, Context context) {
        this.matzipList = matzipList;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        ActivityMatzipListItemBinding binding = ActivityMatzipListItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        MatzipListAdapter.ViewHolder viewHolder = new MatzipListAdapter.ViewHolder(binding);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @SneakyThrows
            @Override
            public void onClick(View v) {
                itemClickListner.onItemClicked(viewHolder.getAdapterPosition(), null);
            }
        });
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        if (matzipList.get(position).getPhotoUrl() != null)
            Glide.with(context.getApplicationContext()).load(matzipList.get(position).getPhotoUrl()).into(viewHolder.getBinding().matzipImage);
        viewHolder.getBinding().number.setText((position + 1) + ". ");
        viewHolder.getBinding().matzipName.setText(matzipList.get(position).getName());
        if (matzipList.get(position).getStar() != null)
            viewHolder.getBinding().rating.setText(String.format("%.2f", matzipList.get(position).getStar().average()));
        if (matzipList.get(position).getStar() != null)
            viewHolder.getBinding().starRating.setRating(matzipList.get(position).getStar().average());
        viewHolder.getBinding().address1.setText(matzipList.get(position).getBaseAddress());
        viewHolder.getBinding().address2.setText(matzipList.get(position).getDetailAddress());
        AppState.getInstance(context.getApplicationContext()).listMatzipReviews(matzipList.get(position), (success, result) -> {
            if (result.size() != 0) {
                Glide.with(context.getApplicationContext()).load(result.get(0).getProfileUrl()).into(viewHolder.getBinding().userProfile);
                viewHolder.getBinding().review.setText(result.get(0).getDetail());
            } else {
                viewHolder.getBinding().review.setText("최근에 작성한 리뷰가 없습니다.");
            }
        });

        viewHolder.getBinding().tagLayout.removeAllViews();
        for (int i = 0; i < matzipList.get(position).getHashtags().size(); i++) {
            TextView textView = new TextView(context.getApplicationContext());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            textView.setText("#" + matzipList.get(position).getHashtags().get(i));
            textView.setGravity(Gravity.RIGHT);
            viewHolder.getBinding().tagLayout.addView(textView);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return matzipList.size();
    }
}
