package cf.spacetaste.app;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
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

import java.io.IOException;
import java.util.List;

import cf.spacetaste.app.data.MatzipInfo;
import cf.spacetaste.app.databinding.Navi2FragmentBinding;
import lombok.SneakyThrows;

public class Navi2_Fragment extends Fragment {

    private Navi2FragmentBinding binding;
    private MatzipListAdapter adapter;
    private List<MatzipInfo> matzipInfoList;
    private final String TAG = "Navi2";
    private Location location;
    private MapView mapView = null;
    private Mylisetner mylisetner = new Mylisetner();
    private Geocoder geocoder;
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
            mapView.setPOIItemEventListener(this.mylisetner);
            binding.map.addView(mapView);
        } catch(UnsatisfiedLinkError e) {
            // 안드로이드 스튜디오에서 "실행"으로 실행했을때는 타 아키텍처의 라이브러리가 포함되지 않아 오류 발생
            // Build > Build Bundle/APK > Build APK 로 생성된 APK를 설치하면 에뮬레이터에서도 잘 작동함
            // 따라서 이 코드는 실제 환경에서는 트리거되지 않음
            Log.w(TAG, "Failed to load native library", e);
        }

        // 도로명 주소 -> 위도, 경도 변환 라이브러리
        geocoder = new Geocoder(getContext());

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
                            if (result.isEmpty()) {
                                Toast.makeText(getActivity(), "검색 결과가 없습니다", Toast.LENGTH_SHORT).show();
                            } else {
                                matzipInfoList = result;
                                setMapView(matzipInfoList);
                            }
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
//        AppState.getInstance(getActivity()).searchMatzip(new ArrayList<>(Arrays.asList("한식")), "", (success, result) -> {
//            if (success) {
//                // result 활용해 처리
//                if (!result.isEmpty()) {
//                    this.matzipInfoList = result;
//                    setMapView(this.matzipInfoList);
//                }
//            } else {
//                // 네트워크 오류, 서버 오류, 기타등등
//                Toast.makeText(getActivity(), "ERROR!", Toast.LENGTH_SHORT).show();
//            }
//        });

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
                        Toast.makeText(getContext(), "에뮬레이터에서 사용 불가", Toast.LENGTH_SHORT).show();
                    } else {
                        // 현재 위치 갱신
                        MapPoint currentLocation = MapPoint.mapPointWithGeoCoord(location.getLatitude(), location.getLongitude());
                        lm.removeUpdates(listener);

                        MapPOIItem marker = new MapPOIItem(); // 마커 생성
                        marker.setItemName("현재 위치");
                        marker.setMapPoint(currentLocation);
                        mapView.addPOIItem(marker);
                        mapView.selectPOIItem(marker, true);
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

    // 어댑터 클릭 이벤트 세팅 등
    private void setMapView(List<MatzipInfo> matzipList) {
        adapter = new MatzipListAdapter(matzipList, getActivity().getApplicationContext());

        if (mapView == null) { // 에뮬레이터에선 클릭 이벤트 사용 불가
            adapter.setOnItemClickedListner(new MatzipListAdapter.OnItemClickListner() {
                @Override
                public void onItemClicked(int position, String data) throws IOException {
                    Toast.makeText(getContext(), "에뮬레이터에서 사용 불가", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            mapView.removeAllPOIItems();
            adapter.setOnItemClickedListner(new MatzipListAdapter.OnItemClickListner() {
                @Override
                public void onItemClicked(int position, String data) throws IOException {
                    moveAndMark(matzipList, position);
                }
            });
            moveAndMark(matzipList, 0);
        }

        LinearLayoutManager linear = new LinearLayoutManager(getActivity().getApplicationContext());
        binding.recyclerView.setLayoutManager(linear);
        binding.recyclerView.setAdapter(adapter);
    }

    @SneakyThrows
    private void moveAndMark(List<MatzipInfo> matzipList, int position) {
        List<Address> address = geocoder.getFromLocationName(matzipList.get(position).getBaseAddress(), 1);
        MapPoint matzipLocation = MapPoint.mapPointWithGeoCoord(address.get(0).getLatitude(), address.get(0).getLongitude());
        MapPOIItem marker = new MapPOIItem(); // 마커 생성
        marker.setItemName(matzipList.get(position).getName());
        marker.setMapPoint(matzipLocation);
        marker.setTag(position);
        mapView.addPOIItem(marker);
        mapView.selectPOIItem(marker, true);
        mapView.setMapCenterPoint(matzipLocation, true);
    }

    class Mylisetner implements MapView.POIItemEventListener {
        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {}

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {}

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
            if (!mapPOIItem.getItemName().equals("현재 위치")) {
                Toast.makeText(mapView.getContext(), mapPOIItem.getItemName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mapView.getContext(), Matzip_Detail.class);
                intent.putExtra("matzipInfo",  matzipInfoList.get(mapPOIItem.getTag()));
                startActivity(intent);
            }
        }

        @Override
        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {}
    }
}

