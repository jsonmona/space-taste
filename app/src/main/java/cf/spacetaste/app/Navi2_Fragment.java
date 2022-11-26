package cf.spacetaste.app;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import cf.spacetaste.app.databinding.Navi2FragmentBinding;

public class Navi2_Fragment extends Fragment {

    private Navi2FragmentBinding binding;
    private MatzipListAdapter adapter;
    private final String TAG = "Navi2";
    private Location location;
    private final ActivityResultLauncher<String[]> locationPermissionRequest = registerForActivityResult(
            new ActivityResultContracts.RequestMultiplePermissions(),
            result -> {
                result.get(Manifest.permission.ACCESS_FINE_LOCATION);
                result.get(Manifest.permission.ACCESS_COARSE_LOCATION);
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
//        MapView mapView = new MapView(getActivity());
//        binding.map.addView(mapView);
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

        ArrayList<Matzip> list = Matzip.MakeExample(new ArrayList<Matzip>());

        adapter = new MatzipListAdapter(list, getActivity().getApplicationContext());
        LinearLayoutManager linear = new LinearLayoutManager(getActivity().getApplicationContext());
        binding.recyclerView.setLayoutManager(linear);
        binding.recyclerView.setAdapter(adapter);

        locationPermissionRequest.launch(new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        });

        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        LocationListener listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location l) {
                location = l;
                Log.d(TAG, location.getLatitude() + ", " + location.getLongitude());
            }
        };

//        binding.locationButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(getContext(), "이 기능을 사용하려면 위치 접근 권한이 필요합니다", Toast.LENGTH_SHORT).show();
//                } else if (!lm.isProviderEnabled(lm.GPS_PROVIDER)) {
//                        Toast.makeText(getContext(), "이 기능을 사용하려면 위치 기능을 켜야합니다", Toast.LENGTH_SHORT).show();
//                        // GPS 설정 화면으로 이동
//                        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivity(gpsOptionsIntent);
//                } else {
//                    // 이 둘은 마지막으로 저장된 위치정보를 가져오는 것이기 때문에, 위치 기능을 껐다 키고 바로 앱을 실행하면 null 값이 되버림
//                    Location location = lm.getLastKnownLocation(lm.NETWORK_PROVIDER);
//
//                    if (location == null) {
//                        lm.requestLocationUpdates(lm.NETWORK_PROVIDER, 1000, 0, listener); // minTimeMs가 왜 커질수록 빨라지지..?
//                        Toast.makeText(getContext(), "위치 정보를 받아오는 중입니다. 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//
//                    // 현재 위치 갱신
//                    MapPoint currentLocation = MapPoint.mapPointWithGeoCoord(location.getLatitude(), location.getLongitude());
//                    lm.removeUpdates(listener);
//
//                    MapPOIItem marker = new MapPOIItem(); // 마커 생성
//                    marker.setItemName("현재 위치");
//                    marker.setMapPoint(currentLocation);
//                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
//                    mapView.addPOIItem(marker);
//                    mapView.setMapCenterPoint(currentLocation, true);
//                }
//            }
//        });

        return binding.getRoot();
    }

    // binding 메모리 누수 방지
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}