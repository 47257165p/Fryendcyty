package fryendcyty.fryendcyty;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
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
    private MyLocationNewOverlay myLocation;
    //private RadiusMarkerClusterer noteMarker;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_tab, container, false);
        map = (MapView) view.findViewById(R.id.MVmap);
        //configurateMap();
        //setMarkersAndClusters();
        map.invalidate();
        return view;
    }

    /*private void setMarkersAndClusters(){

        noteMarker = new RadiusMarkerClusterer(getContext());
        map.getOverlays().add(noteMarker);

        //He hecho click derecho segunda opcion para que no pete la app si era null (cuando introducia una nota)
        Bitmap clusterIcon = ((BitmapDrawable) getResources().getDrawable(R.drawable.ic_clustermarker)) != null ? ((BitmapDrawable) getResources().getDrawable(R.drawable.ic_clustermarker)).getBitmap() : null;
        noteMarker.setIcon(clusterIcon);
        noteMarker.setRadius(100);

        //Le decimos a Firebase que este sera el contexto
        Firebase.setAndroidContext(getContext());

        //Creamos una referencia a nuestra bd de Firebase y a su hijo
        final Firebase notes = new Firebase("https://mapnote.firebaseio.com/").child("notes");

        notes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //Recorremos todas las notas que haya en ese momento
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    //Creamos un objeto nota de ese elemento
                    Note note = postSnapshot.getValue(Note.class);

                    Marker marker = new Marker(map);

                    //Crea un GeoPoint a partir de la latitud y longitud de la nota
                    GeoPoint point = new GeoPoint(note.getLatitud(), note.getLongitud());
                    //Le asignamos al marker esa posicion para que la señale
                    marker.setPosition(point);
                    //Modificamos sus atributos
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                    marker.setIcon(getResources().getDrawable(R.drawable.ic_notemarker));
                    marker.setTitle(note.getTitle());
                    marker.setAlpha(0.6f);
                    //Y que la añada
                    noteMarker.add(marker);
                }
                noteMarker.invalidate();
                map.invalidate();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }

        });
    }

    private void configurateMap(){

        //Configuraciones del mapa
        map.setTileSource(TileSourceFactory.MAPQUESTOSM);
        map.setTilesScaledToDpi(true);
        map.setBuiltInZoomControls(true);
        map.setMultiTouchControls(true);
        mapController = map.getController();
        mapController.setZoom(15);

        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        myLocation = new MyLocationNewOverlay(getContext(), new GpsMyLocationProvider(getContext()), map);
        myLocation.enableMyLocation();

        myLocation.runOnFirstFix(new Runnable() {
            public void run() {
                mapController.animateTo(myLocation
                        .getMyLocation());
            }
        });

        ScaleBarOverlay scaleBarOverlay = new ScaleBarOverlay(map);
        scaleBarOverlay.setCentred(true);
        scaleBarOverlay.setScaleBarOffset(displayMetrics.widthPixels / 2, 10);
        CompassOverlay compassOverlay = new CompassOverlay(getContext(), new InternalCompassOrientationProvider(getContext()), map);
        compassOverlay.enableCompass();
        map.getOverlays().add(myLocation);
        map.getOverlays().add(scaleBarOverlay);
        map.getOverlays().add(compassOverlay);
    }*/

}
