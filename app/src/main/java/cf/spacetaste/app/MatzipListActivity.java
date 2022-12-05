package cf.spacetaste.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import cf.spacetaste.app.data.MatzipInfo;
import cf.spacetaste.app.databinding.ActivityMatzipListBinding;

public class MatzipListActivity extends AppCompatActivity {

    private ActivityMatzipListBinding binding;
    private MatzipListAdapter adapter;
    private ArrayList<MatzipInfo> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatzipListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = (ArrayList) getIntent().getSerializableExtra("matzipList");
        Log.d("debug", "list.size(): " + list.size());

        adapter = new MatzipListAdapter(list, getApplicationContext());
        adapter.setOnItemClickedListner(new MatzipListAdapter.OnItemClickListner() {
            @Override
            public void onItemClicked(int position, String data) {
                Toast.makeText(getApplicationContext(), data + ": " + position, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Matzip_Detail.class);
                intent.putExtra("matzipInfo",list.get(position));
                startActivity(intent);
            }
        });

        LinearLayoutManager linear = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linear);
        binding.recyclerView.setAdapter(adapter);
    }
}