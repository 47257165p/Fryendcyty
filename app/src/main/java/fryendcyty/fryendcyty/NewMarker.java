package fryendcyty.fryendcyty;


import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.Date;


public class NewMarker extends Fragment implements LocationListener
{
    Location loc;
    boolean tookPhoto = false;
    boolean tookVideo = false;
    boolean tookSound = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.new_marker_tab, container, false);
        Button bTVideo = (Button) rootView.findViewById(R.id.bTVideo);
        Button bTPhoto = (Button) rootView.findViewById(R.id.bTPhoto);
        Button bTSound = (Button) rootView.findViewById(R.id.bTSound);
        Button bTUpload = (Button) rootView.findViewById(R.id.bTUpload);

        LocationManager manager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);

        Firebase.setAndroidContext(getContext());

        final Firebase ref = new Firebase("https://fryencyty.firebaseio.com/");

        final Firebase markers = ref.child("markers");

        bTVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCamera = new Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA);
                tookVideo = true;
                startActivity(openCamera);
            }
        });

        bTPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCamera = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
                tookPhoto = true;
                startActivity(openCamera);
            }
        });

        bTSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openRecorder = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
                tookSound = true;
                startActivity(openRecorder);
            }
        });

        bTUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!tookSound && !tookPhoto && !tookVideo)
                {
                    Toast.makeText(getContext(), "No photo, video or sound taken.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (tookVideo)
                    {
                        tookVideo = false;
                        Firebase marker = markers.push();
                        createMarker("video", videoPath(), loc.getLatitude(), loc.getLongitude(), new Date(), marker);
                        Toast.makeText(getContext(), "Video have been added.", Toast.LENGTH_SHORT).show();
                    }
                    if (tookPhoto)
                    {
                        tookPhoto = false;
                        Firebase marker = markers.push();
                        createMarker("photo", photoPath(), loc.getLatitude(), loc.getLongitude(), new Date(), marker);
                        Toast.makeText(getContext(), "Photo have been added.", Toast.LENGTH_SHORT).show();
                    }
                    if (tookSound)
                    {
                        tookSound = false;
                        Firebase marker = markers.push();
                        createMarker("sound", soundPath(), loc.getLatitude(), loc.getLongitude(), new Date(), marker);
                        Toast.makeText(getContext(), "Voice record have been added.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return rootView;
    }

    public void createMarker (String type, String path, double latitude, double longitude, Date date, Firebase marker)
    {
        SharedData sharedData = new SharedData(getActivity().getIntent().getExtras().getString("name"), type, path, latitude, longitude, date);
        marker.setValue(sharedData);
    }
    @Override
    public void onLocationChanged(Location location) {
        loc = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public String videoPath()
    {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getActivity().managedQuery(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
        cursor.moveToLast();
        String videoPath =  cursor.getString(column_index_data);

        return videoPath;
    }

    public String photoPath()
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToLast();
        String imagePath =  cursor.getString(column_index_data);

        return imagePath;
    }

    public String soundPath()
    {
        String[] projection = { MediaStore.Audio.Media.DATA };
        Cursor cursor = getActivity().managedQuery(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToLast();
        String soundPath =  cursor.getString(column_index_data);

        return soundPath;
    }
}