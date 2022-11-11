package cf.spacetaste.app;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import cf.spacetaste.app.databinding.Navi1ItemBinding;

public class MatzipTagAdapter extends RecyclerView.Adapter<MatzipTagAdapter.ViewHolder> {

    private ArrayList<MatzipTag> matzipTagList;

    public interface OnItemClickListner {
        void onItemClicked(int position, String data);
    }

    private MatzipListAdapter.OnItemClickListner itemClickListner;

    public void setOnItemClickedListner(MatzipListAdapter.OnItemClickListner listner) {
        itemClickListner = listner;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private Navi1ItemBinding binding;

        public ViewHolder(Navi1ItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public Navi1ItemBinding getBinding() {
            return binding;
        }
    }

    public MatzipTagAdapter(ArrayList<MatzipTag> matzipTagList) {
        this.matzipTagList = matzipTagList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        Navi1ItemBinding binding = Navi1ItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        MatzipTagAdapter.ViewHolder viewHolder = new MatzipTagAdapter.ViewHolder(binding);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = "";
                int position = viewHolder.getAdapterPosition();
                System.out.println(position);
                if (position != RecyclerView.NO_POSITION) {
                    data = "wow";
                }
                itemClickListner.onItemClicked(position, data);
            }
        });
        return new ViewHolder(binding);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        viewHolder.getBinding().tag.setText(matzipTagList.get(position).tag);
        viewHolder.getBinding().matzipImage.setImageResource(matzipTagList.get(position).thumbnail);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return matzipTagList.size();
    }
}
