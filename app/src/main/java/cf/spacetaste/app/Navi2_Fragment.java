package cf.spacetaste.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.ArrayList;
import java.util.Arrays;

import cf.spacetaste.app.databinding.Navi2FragmentBinding;

public class Navi2_Fragment extends Fragment {

    private Navi2FragmentBinding binding;
    private MatzipListAdapter adapter;
    private final String TAG = "Navi2";
    private Location location;
    private MapView mapView = null;
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

        try {
            System.loadLibrary("DaumMapEngineApi");
            mapView = new MapView(getActivity());
            binding.map.addView(mapView);
        } catch(UnsatisfiedLinkError e) {
            // 안드로이드 스튜디오에서 "실행"으로 실행했을때는 타 아키텍처의 라이브러리가 포함되지 않아 오류 발생
            // Build > Build Bundle/APK > Build APK 로 생성된 APK를 설치하면 에뮬레이터에서도 잘 작동함
            // 따라서 이 코드는 실제 환경에서는 트리거되지 않음
            Log.w(TAG, "Failed to load native library", e);
        }

        binding.searchMatzip.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
                    Log.d("debug", "enter");
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);

                    String word = String.valueOf(binding.searchMatzip.getText());

                    AppState.getInstance(getActivity()).searchMatzip(null, word, (success, result) -> {
                        if (success) {
                            // result 활용해 처리
                            adapter = new MatzipListAdapter(result, getActivity().getApplicationContext());
                            LinearLayoutManager linear = new LinearLayoutManager(getActivity().getApplicationContext());
                            binding.recyclerView.setLayoutManager(linear);
                            binding.recyclerView.setAdapter(adapter);
                        } else {
                            // 네트워크 오류, 서버 오류, 기타등등
                            Toast.makeText(getActivity(), "ERROR!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    return true;
                }

                return false;
            }
        });

        // 프래그먼트 생성시 보여질 맛집 리스트
        AppState.getInstance(getActivity()).searchMatzip(new ArrayList<>(Arrays.asList("한식")), "", (success, result) -> {
            if (success) {
                // result 활용해 처리
                adapter = new MatzipListAdapter(result, getActivity().getApplicationContext());
                LinearLayoutManager linear = new LinearLayoutManager(getActivity().getApplicationContext());
                binding.recyclerView.setLayoutManager(linear);
                binding.recyclerView.setAdapter(adapter);
            } else {
                // 네트워크 오류, 서버 오류, 기타등등
                Toast.makeText(getActivity(), "ERROR!", Toast.LENGTH_SHORT).show();
            }
        });

        // 위치권한 요청
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


        binding.locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(), "이 기능을 사용하려면 위치 접근 권한이 필요합니다", Toast.LENGTH_SHORT).show();
                } else if (!lm.isProviderEnabled(lm.GPS_PROVIDER)) {
                        Toast.makeText(getContext(), "이 기능을 사용하려면 위치 기능을 켜야합니다", Toast.LENGTH_SHORT).show();
                        // GPS 설정 화면으로 이동
                        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(gpsOptionsIntent);
                } else {
                    // 이 둘은 마지막으로 저장된 위치정보를 가져오는 것이기 때문에, 위치 기능을 껐다 키고 바로 앱을 실행하면 null 값이 되버림
                    Location location = lm.getLastKnownLocation(lm.NETWORK_PROVIDER);

                    if (location == null) {
                        lm.requestLocationUpdates(lm.NETWORK_PROVIDER, 1000, 0, listener); // minTimeMs가 왜 커질수록 빨라지지..?
                        Toast.makeText(getContext(), "위치 정보를 받아오는 중입니다. 다시 시도해 주세요", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // 에뮬레이터에서는 카카오맵 관련 기능 사용 불가
                    if (mapView == null) {
                        Toast.makeText(getContext(), "Error: 에뮬레이터에서 사용 불가", Toast.LENGTH_SHORT).show();
                    } else {
                        // 현재 위치 갱신
                        MapPoint currentLocation = MapPoint.mapPointWithGeoCoord(location.getLatitude(), location.getLongitude());
                        lm.removeUpdates(listener);

                        MapPOIItem marker = new MapPOIItem(); // 마커 생성
                        marker.setItemName("현재 위치");
                        marker.setMapPoint(currentLocation);
                        mapView.addPOIItem(marker);
                        mapView.setMapCenterPoint(currentLocation, true);
                    }
                }
            }
        });

        return binding.getRoot();
    }

    // binding 메모리 누수 방지
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}