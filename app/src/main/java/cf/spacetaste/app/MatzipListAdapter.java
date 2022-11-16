package cf.spacetaste.app;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
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
        this.context = context;
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
        viewHolder.getBinding().address1.setText(matzipList.get(position).matzipAddress1);
        viewHolder.getBinding().address2.setText(matzipList.get(position).matzipAddress2);
        viewHolder.getBinding().userProfile.setImageResource(matzipList.get(position).userProfile);
        viewHolder.getBinding().review.setText(matzipList.get(position).review);

        for (int i = 0; i < matzipList.get(position).tagList.size(); i++) {
            TextView textView = new TextView(context.getApplicationContext());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
            textView.setText(matzipList.get(position).tagList.get(i));
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
