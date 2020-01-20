package com.worldofuiux.fashionprofileuikit.Map;

import android.app.Application;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.NaverMapOptions;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.util.MarkerIcons;
import com.worldofuiux.fashionprofileuikit.R;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        MapFragment mapFragment = (MapFragment)getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance(new NaverMapOptions().camera(new CameraPosition(
                    NaverMap.DEFAULT_CAMERA_POSITION.target, NaverMap.DEFAULT_CAMERA_POSITION.zoom, 0, 0)));
            getSupportFragmentManager().beginTransaction().add(R.id.map_fragment, mapFragment).commit();
        }
        mapFragment.getMapAsync(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Marker marker = new Marker();
        marker.setPosition(new LatLng(35.186578, 126.839739));
        marker.setMap(naverMap);

        marker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Toast.makeText(getApplicationContext(),"신가부영 1268세대 광주광역시 광산구 수등로 287",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Marker markerWithCustomIcon = new Marker();
        markerWithCustomIcon.setPosition(new LatLng(35.164851, 126.81039));
        markerWithCustomIcon.setIcon(MarkerIcons.BLACK);
        markerWithCustomIcon.setMap(naverMap);

        markerWithCustomIcon.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Toast.makeText(getApplicationContext(),"하남금호타운 1500세대 광주광역시 광산구 월곡산정로 80",Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        Marker markerWithCustomIcon2 = new Marker();
        markerWithCustomIcon2.setPosition(new LatLng(35.184858, 126.81321));
        markerWithCustomIcon2.setIcon(MarkerIcons.RED);
        markerWithCustomIcon2.setAngle(315);
        markerWithCustomIcon2.setMap(naverMap);
        markerWithCustomIcon2.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Toast.makeText(getApplicationContext(),"수완중흥에스클래스3단지 209세대 광주광역시 광산구 풍영로145번길 18",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Marker markerWithCustomIcon3 = new Marker();
        markerWithCustomIcon3.setPosition(new LatLng(35.131407, 126.785178));
        markerWithCustomIcon3.setIcon(MarkerIcons.RED);
        markerWithCustomIcon3.setAngle(315);
        markerWithCustomIcon3.setMap(naverMap);
        markerWithCustomIcon3.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Toast.makeText(getApplicationContext(),"광주광역시 광산구 평동로1120번길12",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Marker markerWithCustomIcon4 = new Marker();
        markerWithCustomIcon4.setPosition(new LatLng(35.219871, 126.852212));
        markerWithCustomIcon4.setIcon(MarkerIcons.BLACK);
        markerWithCustomIcon4.setMap(naverMap);

        markerWithCustomIcon4.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Toast.makeText(getApplicationContext(),"광주광역시 광산구 앰코로 21",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Marker marker5 = new Marker();
        marker5.setPosition(new LatLng(35.181469, 126.79683));
        marker5.setMap(naverMap);

        marker5.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Toast.makeText(getApplicationContext(),"광주광역시 광산구 단전둘레길 15\t",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Marker marker6 = new Marker();
        marker6.setPosition(new LatLng(35.17792, 126.806528));
        marker6.setMap(naverMap);

        marker6.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Toast.makeText(getApplicationContext(),"광주광역시 광산구 용아로400번길 30",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Marker marker7 = new Marker();
        marker7.setPosition(new LatLng(35.143069, 126.799056));
        marker7.setMap(naverMap);

        marker7.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Toast.makeText(getApplicationContext(),"광주광역시 광산구 광산로89번길27-7",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Marker marker8 = new Marker();
        marker8.setPosition(new LatLng(35.13627, 126.786528));
        marker8.setMap(naverMap);

        marker8.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Toast.makeText(getApplicationContext(),"광주광역시 광산구 평동로1101번길 40",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Marker markerWithCustomIcon9 = new Marker();
        markerWithCustomIcon9.setPosition(new LatLng(35.2058, 126.828829));
        markerWithCustomIcon9.setIcon(MarkerIcons.RED);
        markerWithCustomIcon9.setAngle(315);
        markerWithCustomIcon9.setMap(naverMap);
        markerWithCustomIcon9.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Toast.makeText(getApplicationContext(),"광주광역시 광산구 상완길 299",Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        Marker markerWithCustomIcon10 = new Marker();
        markerWithCustomIcon10.setPosition(new LatLng(35.220772, 126.844426));
        markerWithCustomIcon10.setIcon(MarkerIcons.RED);
        markerWithCustomIcon10.setAngle(315);
        markerWithCustomIcon10.setMap(naverMap);
        markerWithCustomIcon10.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay) {
                Toast.makeText(getApplicationContext(),"광주광역시 광산구 첨단중앙로170번길 87",Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}