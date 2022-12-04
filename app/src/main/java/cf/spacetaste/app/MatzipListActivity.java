package cf.spacetaste.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        adapter.setOnItemClickedListner((position, data) -> {
            Toast.makeText(getApplicationContext(), data + ": " + position, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),Matzip_Detail.class);
            intent.putExtra("matzip",list.get(position));
            startActivity(intent);
        });

        LinearLayoutManager linear = new LinearLayoutManager(this);
        binding.recyclerView.setLayoutManager(linear);
        binding.recyclerView.setAdapter(adapter);
    }
}

//class MatzipInfo {
//
//    int matzipImage;
//    String matzipName;
//    String baseAddress;
//    String detailAdress;
//    int userProfile;
//    String review;
//    float matzipRating;
//    ArrayList<String> tagList;
//
//    public MatzipInfo(int matzipImage, String matzipName, String baseAddress, String detailAdress, String review, int userProfile, float matzipRating, ArrayList<String> tagList) {
//        this.matzipImage = matzipImage;
//        this.matzipName = matzipName;
//        this.baseAddress = baseAddress;
//        this.detailAdress = detailAdress;
//        this.userProfile = userProfile;
//        this.review = "“" + review + "”";
//        this.matzipRating = matzipRating;
//        this.tagList = tagList;
//    }
//
//    public String getName() {
//        return this.matzipName;
//    }
//
//    public String getStar() {
//        return null;
//    }
//
//    public String getBaseAddress() {
//        return baseAddress;
//    }
//
//    public String getDetailAdress() {
//        return detailAdress;
//    }
//
//    public static List<MatzipInfo> MakeExample(List<MatzipInfo> list) {
//        list.add(new MatzipInfo(R.drawable.matzip1, "꼼다비뛰드", "서울특별시 강남구 강남대로110길 62", "지하 1층"
//                , "빵지 순례 중 대박을 찾았다!", R.drawable.chunsik, 4.3f, new ArrayList<String>(Arrays.asList("#카페", "#대화하기 좋은", "양식"))));
//
//        list.add(new MatzipInfo(R.drawable.matzip2, "도이칠란드 박", "서울특별시 성북구 솔샘로6길 30-15", "1층"
//                , "이왕이면 오리지널 보다 매운맛 킬바사를 추천드립니다", R.drawable.chunsik, 4.7f, new ArrayList<String>(Arrays.asList("#양식"))));
//
//        list.add(new MatzipInfo(R.drawable.matzip6, "뽀르께노 스페니쉬비스트로", "서울특별시 성북구 동소문로6길 4-21", "1F"
//                , "스페인 요리인 것도 유니크하고, 안 와볼 수가 없는 맛집", R.drawable.chunsik, 4.6f, new ArrayList<String>(Arrays.asList("#양식", "#양이 많은"))));
//
//        list.add(new MatzipInfo(R.drawable.matzip9, "살살솔트앤샐러드", "서울특별시 성북구 성북로6길 20", "1F"
//                , "완전 예쁜 인스타 감성 맛집..", R.drawable.chunsik, 4.4f, new ArrayList<String>(Arrays.asList("#양식", "#아침식사"))));
//
//        list.add(new MatzipInfo(R.drawable.matzip5, "치티", "서울특별시 성북구 성북로5길 12", "101호"
//                , "비오는 날이 잘 어울리는 생면 파스타 집", R.drawable.chunsik, 4.7f, new ArrayList<String>(Arrays.asList("#양식", "#조용한", "#혼밥하기 좋은"))));
//
//        list.add(new MatzipInfo(R.drawable.matzip2, "도이칠란드 박", "서울특별시 성북구 솔샘로6길 30-15", "1층"
//                , "프로그래밍이 맛있게 잘 나와요~", R.drawable.chunsik, 4.7f, new ArrayList<String>(Arrays.asList("양식"))));
//
//        list.add(new MatzipInfo(R.drawable.matzip2, "도이칠란드 박", "서울특별시 성북구 솔샘로6길 30-15", "1층"
//                , "프로그래밍이 맛있게 잘 나와요~", R.drawable.chunsik, 4.7f, new ArrayList<String>(Arrays.asList("양식"))));
//
//        return list;
//    }
//}