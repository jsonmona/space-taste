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

import cf.spacetaste.app.databinding.ActivityReviewListItemBinding;
import cf.spacetaste.common.ReviewInfoDTO;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {

    private List<ReviewInfoDTO> reviewList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ActivityReviewListItemBinding binding;

        public ViewHolder(ActivityReviewListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public ActivityReviewListItemBinding getBinding() {
            return binding;
        }
    }

    public ReviewListAdapter(List reviewList, Context context) {
        this.reviewList = reviewList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        ActivityReviewListItemBinding binding = ActivityReviewListItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        ReviewListAdapter.ViewHolder viewHolder = new ReviewListAdapter.ViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Glide.with(context.getApplicationContext()).load(reviewList.get(position).getProfileUrl()).into(viewHolder.getBinding().userImage);
        viewHolder.getBinding().userName.setText(reviewList.get(position).getNickname());
        viewHolder.getBinding().userReview.setText("“" + reviewList.get(position).getDetail() + "”");
        float userRatingAvg = (reviewList.get(position).getScoreTaste()
                + reviewList.get(position).getScoreKindness()
                + reviewList.get(position).getScoreClean()
                + reviewList.get(position).getScorePrice()) / 4;
        viewHolder.getBinding().userRating.setText(String.format("%.1f", userRatingAvg));
        viewHolder.getBinding().userStar.setRating(userRatingAvg);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}
