package cf.spacetaste.app;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import cf.spacetaste.app.databinding.ActivityMatzipListItemBinding;

public class MatzipListAdapter extends RecyclerView.Adapter<MatzipListAdapter.ViewHolder> {

    private ArrayList<MatzipList> matzipList;
    private Context context;

    public interface OnItemClickListner {
        void onItemClicked(int position, String data);
    }

    private OnItemClickListner itemClickListner;

    public void setOnItemClickedListner(OnItemClickListner listner) {
        itemClickListner = listner;
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

    public MatzipListAdapter(ArrayList<MatzipList> matzipList, Context context) {
        this.matzipList = matzipList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        ActivityMatzipListItemBinding binding = ActivityMatzipListItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        return new ViewHolder(binding);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getBinding().matzipImage.setImageResource(matzipList.get(position).matzipImage);
        viewHolder.getBinding().number.setText((position + 1) + ". ");
        viewHolder.getBinding().matzipName.setText(matzipList.get(position).matzipName);
        viewHolder.getBinding().rating.setText(matzipList.get(position).matzipRating + "");
        viewHolder.getBinding().starRating.setRating(matzipList.get(position).matzipRating);
        viewHolder.getBinding().address.setText(matzipList.get(position).matzipAddress);
        viewHolder.getBinding().userProfile.setImageResource(matzipList.get(position).userProfile);
        viewHolder.getBinding().review.setText(matzipList.get(position).review);

//        for (int i = 0; i < matzipList.get(position).tagList.size(); i++) {
//            TextView textView = new TextView(context);
//
//            DisplayMetrics dm = context.getResources().getDisplayMetrics();
//            textView.setText(matzipList.get(position).tagList.get(position));
//            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.WRAP_CONTENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT);
//            param.topMargin = Math.round(5 * dm.density);
//            textView.setLayoutParams(param);
//            textView.setTextSize(Math.round(12 * dm.density));
//            viewHolder.getBinding().tagLayout.addView(textView);
//        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return matzipList.size();
    }
}
