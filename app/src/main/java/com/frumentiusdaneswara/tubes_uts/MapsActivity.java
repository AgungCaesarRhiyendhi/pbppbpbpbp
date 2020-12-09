package com.frumentiusdaneswara.tubes_uts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Feature;
import com.mapbox.geojson.FeatureCollection;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.style.layers.SymbolLayer;
import com.mapbox.mapboxsdk.style.sources.GeoJsonSource;

import java.util.ArrayList;
import java.util.List;

import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconAllowOverlap;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconIgnorePlacement;
import static com.mapbox.mapboxsdk.style.layers.PropertyFactory.iconImage;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String DESTINATION_SYMBOL_LAYER_ID = "destination-symbol-layer-id";
    private static final String DESTINATION_ICON_ID = "destination-icon-id";
    private static final String DESTINATION_SOURCE_ID = "destination-source-id";
    private static final int REQUEST_CODE_AUTOCOMPLETE = 1;
    private FloatingActionButton btnback;
    private PermissionsManager permissionsManager;
    private MapboxMap mapboxMap;
    private MapView mapView;
    private Point origin;
    private Kos kos;
    private double longitude;
    private double latitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        setContentView(R.layout.activity_maps);
        Window window = MapsActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(ContextCompat.getColor(MapsActivity.this, R.color.colorPrimary));

        kos = (Kos) getIntent().getSerializableExtra("kos");
        longitude = kos.getLongitude();
        latitude = kos.getLatitude();

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        btnback = findViewById(R.id.btn_back);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                data.putSerializable("kos", kos);
                finish();
            }
        });
    }


    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;

        List<Feature> symbolLayerIconFeatureList = new ArrayList<>();
        symbolLayerIconFeatureList.add(Feature.fromGeometry(
                Point.fromLngLat(longitude,latitude)));


        mapboxMap.setStyle(new Style.Builder().fromUri("mapbox://styles/mapbox/cjf4m44iw0uza2spb3q0a7s41")
                .withImage(DESTINATION_ICON_ID, BitmapFactory.decodeResource(
                        MapsActivity.this.getResources(), R.drawable.mapbox_marker_icon_default))
                .withSource(new GeoJsonSource(DESTINATION_SOURCE_ID, FeatureCollection.fromFeatures(symbolLayerIconFeatureList)))
                .withLayer(new SymbolLayer(DESTINATION_SYMBOL_LAYER_ID, DESTINATION_SOURCE_ID)
                        .withProperties(
                                iconImage(DESTINATION_ICON_ID),
                                iconAllowOverlap(true),
                                iconIgnorePlacement(true)
                        )
                ), new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {

            }
        });

        mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude))
                        .zoom(14)
                        .build()),4000);



    }


    @Override
    protected void onStart(){
        super.onStart();
        mapView.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory(){
        super.onLowMemory();
        mapView.onLowMemory();
    }


}