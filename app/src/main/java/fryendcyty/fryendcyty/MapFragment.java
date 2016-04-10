package fryendcyty.fryendcyty;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.clustering.RadiusMarkerClusterer;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ScaleBarOverlay;
import org.osmdroid.views.overlay.compass.CompassOverlay;
import org.osmdroid.views.overlay.compass.InternalCompassOrientationProvider;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

/**
 * Created by Alejandro on 30/03/2016.
 */
public class MapFragment extends Fragment {

    private MapView map;
    private IMapController mapController;
    private MyLocationNewOverlay ownLocation;
    private RadiusMarkerClusterer sharedMarker;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.map_tab, container, false);
        map = (MapView) rootView.findViewById(R.id.MVmap);
        configurateMap();
        setMarkersAndClusters();
        map.invalidate();
        return rootView;
    }

    private void configurateMap(){

        map.setTileSource(TileSourceFactory.MAPQUESTOSM);
        map.setTilesScaledToDpi(true);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        mapController = map.getController();
        mapController.setZoom(15);

        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        ownLocation = new MyLocationNewOverlay(getContext(), new GpsMyLocationProvider(getContext()), map);
        ownLocation.enableMyLocation();

        ownLocation.runOnFirstFix(new Runnable() {
            public void run() {
                mapController.animateTo(ownLocation
                        .getMyLocation());
            }
        });

        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(map);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(displayMetrics.widthPixels / 2, 10);
        CompassOverlay compassOverlay = new CompassOverlay(getContext(), new InternalCompassOrientationProvider(getContext()), map);
        compassOverlay.enableCompass();
        map.getOverlays().add(ownLocation);
        map.getOverlays().add(scaleBarOverlay);
        map.getOverlays().add(compassOverlay);
    }

    private void setMarkersAndClusters(){

        sharedMarker = new RadiusMarkerClusterer(getContext());
        map.getOverlays().add(sharedMarker);

        Bitmap clusterIcon = ((BitmapDrawable) getResources().getDrawable(R.drawable.map_marker)) != null ? ((BitmapDrawable) getResources().getDrawable(R.drawable.map_marker)).getBitmap() : null;
        sharedMarker.setIcon(clusterIcon);
        sharedMarker.setRadius(100);

        Firebase.setAndroidContext(getContext());

        final Firebase sharedData = new Firebase("https://fryencyty.firebaseio.com/").child("markers");

        sharedData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (isAdded()) {
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                        final SharedData sharedData = postSnapshot.getValue(SharedData.class);

                        Marker marker = new Marker(map);

                        GeoPoint point = new GeoPoint(sharedData.getLatitude(), sharedData.getLongitude());

                        marker.setPosition(point);

                        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                            @Override
                            public boolean onMarkerClick(Marker marker, MapView mapView) {
                                switch (sharedData.getType()) {
                                    case "video": {
                                        Intent videoActivity = new Intent(getContext(), Player.class);
                                        videoActivity.putExtra("type", "video");
                                        videoActivity.putExtra("path", sharedData.getDataPath());
                                        startActivity(videoActivity);
                                        break;
                                    }
                                    case "photo": {
                                        Intent photoActivity = new Intent(getContext(), Player.class);
                                        photoActivity.putExtra("type", "photo");
                                        photoActivity.putExtra("path", sharedData.getDataPath());
                                        startActivity(photoActivity);
                                        break;
                                    }
                                    case "sound": {
                                        MediaPlayer mp = new MediaPlayer();

                                        try {
                                            mp.setDataSource(sharedData.getDataPath());
                                            mp.prepare();
                                            mp.start();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    }
                                }
                                return false;
                            }
                        });
                        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                        marker.setIcon(getResources().getDrawable(R.drawable.map_marker));
                        marker.setTitle(sharedData.getTitle().split("@")[0]);
                        marker.setAlpha(0.6f);
                        sharedMarker.add(marker);
                    }
                    sharedMarker.invalidate();
                    map.invalidate();
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }

        });
    }
}
