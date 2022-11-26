package cf.spacetaste.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import cf.spacetaste.app.databinding.Navi1ItemBinding;

public class MatzipTagAdapter extends RecyclerView.Adapter<MatzipTagAdapter.ViewHolder> {

    private ArrayList<MatzipTag> matzipTagList;
    private MatzipListAdapter.OnItemClickListner itemClickListner;

    public interface OnItemClickListner {
        void onItemClicked(int position, String data);
    }

    public void setOnItemClickedListner(MatzipListAdapter.OnItemClickListner listner) {
        this.itemClickListner = listner;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
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
                itemClickListner.onItemClicked(viewHolder.getAdapterPosition(),"position");
            }
        });
        return viewHolder;
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
