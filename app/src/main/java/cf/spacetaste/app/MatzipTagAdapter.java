package cf.spacetaste.app;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import cf.spacetaste.app.databinding.Navi1ItemBinding;

public class MatzipTagAdapter extends RecyclerView.Adapter<MatzipTagAdapter.ViewHolder> {

    private List<String> matzipTagList;
    private OnItemClickListner itemClickListner;
    private Context context;

    public MatzipTagAdapter(Context context) {
        this.context = context;
        this.matzipTagList = new ArrayList<>();
        AppState.getInstance(this.context.getApplicationContext()).getRandomTags((success, result) -> {
            if (success) {
                this.matzipTagList = result;
                notifyDataSetChanged();
                Log.d("debug", "서버로부터 받아온 태그의 개수: " + result.size());
            } else {
                // 네트워크 오류, 서버 오류, 기타등등
                Log.d("debug", "error");
                Toast.makeText(context.getApplicationContext(), "ERROR!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface OnItemClickListner {
        void onItemClicked(int position, String data);
    }

    public void setOnItemClickedListner(OnItemClickListner listner) {
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



    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        Navi1ItemBinding binding = Navi1ItemBinding.inflate(LayoutInflater.from(viewGroup.getContext()), viewGroup, false);
        MatzipTagAdapter.ViewHolder viewHolder = new MatzipTagAdapter.ViewHolder(binding);
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListner.onItemClicked(viewHolder.getAdapterPosition(), matzipTagList.get(viewHolder.getAdapterPosition()));
            }
        });
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        Log.d("debug", matzipTagList.get(position));
        viewHolder.getBinding().tag.setText("#" + matzipTagList.get(position));
        AppState.getInstance(this.context.getApplicationContext()).getMainPhotoOfTag(matzipTagList.get(position), (success, result) -> {
            if (success) {
                Glide.with(context.getApplicationContext()).load(result).into(viewHolder.getBinding().matzipImage);
            } else {
                Log.d("debug", "사진이 없네용");
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return matzipTagList.size();
    }
}
