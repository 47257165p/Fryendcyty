package fryendcyty.fryendcyty;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerFragment extends Fragment {

    public PlayerFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_player, container, false);

        if (getActivity().getIntent().getExtras().getString("type").equals("photo"))
        {
            ImageView iV = (ImageView) rootView.findViewById(R.id.iV);
            iV.setVisibility(View.VISIBLE);
            iV.setImageURI(Uri.parse(getActivity().getIntent().getExtras().getString("path")));
        }
        else
        {
            VideoView vV = (VideoView) rootView.findViewById(R.id.vV);
            vV.setVisibility(View.VISIBLE);
            vV.setVideoPath(getActivity().getIntent().getExtras().getString("path"));
            vV.start();
        }
        return rootView;
    }
}
