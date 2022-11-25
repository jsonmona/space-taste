package cf.spacetaste.app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;

import cf.spacetaste.app.databinding.Navi2FragmentBinding;

public class Navi2_Fragment extends Fragment {

    private Navi2FragmentBinding binding;
    private ArrayList<MatzipList> list;
    private MatzipListAdapter adapter;
    private final String TAG = "Navi2";
    // 위치 권한
    private final ActivityResultLauncher<String[]> locationPermissionRequest = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                Boolean fineLocationGranted = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                Boolean coarseLocationGranted = result.get(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        binding = Navi2FragmentBinding.inflate(inflater, container, false);
        MapView mapView = new MapView(getActivity());
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37, 127), true);
        mapView.setBackground(Drawable.createFromPath("@drawable/layout_shape"));

        binding.map.addView(mapView);
        binding.searchMatzip.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.d("debug", "enter");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                    Toast.makeText(getContext(), "클릭", Toast.LENGTH_SHORT).show();
                    return true;
                }

                return false;
            }
        });

        list = new ArrayList<>();
        list.add(new MatzipList(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383", "1F (경도상가 1층)"
                , "큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 4.87f, new ArrayList<String>()));
        list.add(new MatzipList(R.drawable.gukbap, "화목순대국", "서울특별시 성동구 성수동2가 성덕정15길 6-9", "1F (경도상가 1층)"
                , "큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요큼지막한 알곱창이 푸짐하게 들어가있어요\"", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
        list.add(new MatzipList(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383", "1F (경도상가 1층)"
                , "큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 0.87f, new ArrayList<String>()));
        list.add(new MatzipList(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383", "1F (경도상가 1층)"
                , "큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 3.87f, new ArrayList<String>()));
        list.add(new MatzipList(R.drawable.gukbap, "화목순대국", "서울 영등포구 여의대방로 383", "1F (경도상가 1층)"
                , "큼지막한 알곱창이 푸짐하게 들어가있어요", R.drawable.gukbap, 4.87f, new ArrayList<String>()));

        adapter = new MatzipListAdapter(list, getActivity().getApplicationContext());
        LinearLayoutManager linear = new LinearLayoutManager(getActivity().getApplicationContext());
        binding.recyclerView.setLayoutManager(linear);
        binding.recyclerView.setAdapter(adapter);

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        });

        final LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        binding.locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "위치 권한이 허용되어야 사용할 수 있는 기능입니다", Toast.LENGTH_SHORT).show();
                } else {
                    // 현재 위치 받아오기
                    Location location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    MapPoint currentLocation = MapPoint.mapPointWithGeoCoord(location.getLatitude(), location.getLongitude());

                    MapPOIItem marker = new MapPOIItem(); // 마커 생성
                    marker.setItemName("현재 위치");
                    marker.setMapPoint(currentLocation);
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
                    mapView.addPOIItem(marker);
                    mapView.setMapCenterPoint(currentLocation, true);
                }
            }
        });

        return binding.getRoot();
    }
}