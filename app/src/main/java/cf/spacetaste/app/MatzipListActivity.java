package cf.spacetaste.app;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Arrays;

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

        list = (ArrayList) getIntent().getSerializableExtra("matzipList");

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
//            tagList.add("#활기찬");
//            tagList.add("#왁자지껄한");
        }
    }

    public static ArrayList<Matzip> MakeExample(ArrayList<Matzip> list) {
//        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울특별시 성동구 성수동2가 성덕정15길 6-9", "1F (경도상가 1층)"
//                ,"큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
//        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383","1F (경도상가 1층)"
//                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 0.87f, new ArrayList<String>()));
//        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383", "1F (경도상가 1층)"
//                ,"큼지막한 알곱창이 푸짐하게 들어가있어",  R.drawable.gukbap, 3.87f, new ArrayList<String>()));
//        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383" , "1F (경도상가 1층)"
//                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 4.87f, new ArrayList<String>()));
//        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울특별시 성동구 성수동2가 성덕정15길 6-9", "1F (경도상가 1층)"
//                ,"큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
//        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383","1F (경도상가 1층)"
//                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 0.87f, new ArrayList<String>()));
//        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383", "1F (경도상가 1층)"
//                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
//        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383" , "1F (경도상가 1층)"
//                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 4.87f, new ArrayList<String>()));
//        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울특별시 성동구 성수동2가 성덕정15길 6-9", "1F (경도상가 1층)"
//                ,"큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요\"", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
//        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383","1F (경도상가 1층)"
//                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 0.87f, new ArrayList<String>()));
//        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383", "1F (경도상가 1층)"
//                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
//        list.add(new Matzip(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383" , "1F (경도상가 1층)"
//                ,"큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 4.87f, new ArrayList<String>()));
        list.add(new Matzip(R.drawable.matzip1, "꼼다비뛰드", "서울특별시 강남구 강남대로110길 62", "지하 1층"
                , "빵지 순례 중 대박을 찾았다!", R.drawable.chunsik, 4.3f, new ArrayList<String>(Arrays.asList("#카페", "#대화하기 좋은", "양식"))));

        list.add(new Matzip(R.drawable.matzip2, "도이칠란드 박", "서울특별시 성북구 솔샘로6길 30-15", "1층"
                , "이왕이면 오리지널 보다 매운맛 킬바사를 추천드립니다", R.drawable.chunsik, 4.7f, new ArrayList<String>(Arrays.asList("#양식"))));

        list.add(new Matzip(R.drawable.matzip6, "뽀르께노 스페니쉬비스트로", "서울특별시 성북구 동소문로6길 4-21", "1F"
                , "스페인 요리인 것도 유니크하고, 안 와볼 수가 없는 맛집", R.drawable.chunsik, 4.6f, new ArrayList<String>(Arrays.asList("#양식", "#양이 많은"))));

        list.add(new Matzip(R.drawable.matzip9, "살살솔트앤샐러드", "서울특별시 성북구 성북로6길 20", "1F"
                , "완전 예쁜 인스타 감성 맛집..", R.drawable.chunsik, 4.4f, new ArrayList<String>(Arrays.asList("#양식", "#아침식사"))));

        list.add(new Matzip(R.drawable.matzip5, "치티", "서울특별시 성북구 성북로5길 12", "101호"
                , "비오는 날이 잘 어울리는 생면 파스타 집", R.drawable.chunsik, 4.7f, new ArrayList<String>(Arrays.asList("#양식", "#조용한", "#혼밥하기 좋은"))));

        list.add(new Matzip(R.drawable.matzip2, "도이칠란드 박", "서울특별시 성북구 솔샘로6길 30-15", "1층"
                , "프로그래밍이 맛있게 잘 나와요~", R.drawable.chunsik, 4.7f, new ArrayList<String>(Arrays.asList("양식"))));

        list.add(new Matzip(R.drawable.matzip2, "도이칠란드 박", "서울특별시 성북구 솔샘로6길 30-15", "1층"
                , "프로그래밍이 맛있게 잘 나와요~", R.drawable.chunsik, 4.7f, new ArrayList<String>(Arrays.asList("양식"))));

        list.add(new Matzip(R.drawable.matzip2, "도이칠란드 박", "서울특별시 성북구 솔샘로6길 30-15", "1층"
                , "프로그래밍이 맛있게 잘 나와요~", R.drawable.chunsik, 4.7f, new ArrayList<String>(Arrays.asList("양식"))));
        list.add(new Matzip(R.drawable.matzip2, "도이칠란드 박", "서울특별시 성북구 솔샘로6길 30-15", "1층"
                , "프로그래밍이 맛있게 잘 나와요~", R.drawable.chunsik, 4.7f, new ArrayList<String>(Arrays.asList("양식"))));


        return list;
    }
}