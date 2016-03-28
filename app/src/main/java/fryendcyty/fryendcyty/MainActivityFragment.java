package fryendcyty.fryendcyty;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        EditText eTUsernameMain = (EditText) rootView.findViewById(R.id.eTUsernameMain);
        EditText eTPasswordMain = (EditText) rootView.findViewById(R.id.eTPasswordMain);
        Button bTMain = (Button) rootView.findViewById(R.id.bTMain);
        TextView tVSignUpMain = (TextView) rootView.findViewById(R.id.tVSignUpMain);

        bTMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        tVSignUpMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createUser = new Intent(getContext(), CreateUser.class);
                startActivityForResult(createUser, 1);
            }
        });

        return rootView;
    }
}
