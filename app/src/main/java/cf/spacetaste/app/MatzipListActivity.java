package cf.spacetaste.app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import cf.spacetaste.app.databinding.ActivityMatzipListBinding;

public class MatzipListActivity extends AppCompatActivity {

    private ActivityMatzipListBinding binding;
    private MatzipListAdapter adapter;
    private ArrayList<Matzip> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMatzipListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        list = Matzip.MakeExample(new ArrayList<Matzip>());

        adapter = new MatzipListAdapter(list, getApplicationContext());
        adapter.setOnItemClickedListner(new MatzipListAdapter.OnItemClickListner() {
            @Override
            public void onItemClicked(int position, String data) {
                Toast.makeText(getApplicationContext(), "Position:" + position + ", Data:" + data, Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayoutManager linear = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linear);
        binding.recyclerView.setAdapter(adapter);
    }
}

class Matzip {

    int matzipImage;
    String matzipName;
    String matzipAddress1;
    String matzipAddress2;
    int userProfile;
    String review;
    float matzipRating;
    ArrayList<String> tagList;

    public Matzip(int matzipImage, String matzipName, String matzipAddress1, String matzipAddress2, String review, int userProfile, float matzipRating, ArrayList<String> tagList) {
        this.matzipImage = matzipImage;
        this.matzipName = matzipName;
        this.matzipAddress1 = matzipAddress1;
        this.matzipAddress2 = matzipAddress2;
        this.userProfile = userProfile;
        this.review = "“" + review + "”";
        this.matzipRating = matzipRating;
        this.tagList = tagList;

        if (tagList.isEmpty()) {
            tagList.add("#한식");
            tagList.add("#활기찬");
            tagList.add("#왁자지껄한");
        }
    }

    public static ArrayList<Matzip> MakeExample(ArrayList<Matzip> list) {
        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울특별시 성동구 성수동2가 성덕정15길 6-9", "1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383","1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 0.87f, new ArrayList<String>()));
        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383", "1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어",  R.drawable.gukbap, 3.87f, new ArrayList<String>()));
        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383" , "1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 4.87f, new ArrayList<String>()));
        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울특별시 성동구 성수동2가 성덕정15길 6-9", "1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383","1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 0.87f, new ArrayList<String>()));
        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383", "1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383" , "1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 4.87f, new ArrayList<String>()));
        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울특별시 성동구 성수동2가 성덕정15길 6-9", "1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요\"", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383","1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 0.87f, new ArrayList<String>()));
        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383", "1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383" , "1F (경도상가 1층)"
                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 4.87f, new ArrayList<String>()));

        return list;
    }
}