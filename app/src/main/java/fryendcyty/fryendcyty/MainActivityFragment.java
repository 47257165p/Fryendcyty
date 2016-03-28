package fryendcyty.fryendcyty;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private static EditText eTUsernameMain;
    private static EditText eTPasswordMain;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        eTUsernameMain = (EditText) rootView.findViewById(R.id.eTUsernameMain);
        eTPasswordMain = (EditText) rootView.findViewById(R.id.eTPasswordMain);
        Button bTMain = (Button) rootView.findViewById(R.id.bTMain);
        TextView tVSignUpMain = (TextView) rootView.findViewById(R.id.tVSignUpMain);

        bTMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authFirebase();
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

    public void authFirebase ()
    {
        if (eTUsernameMain.getText().toString().equals("") ||
                eTPasswordMain.toString().equals(""))
        {
            new AlertDialog.Builder(getContext())
                    .setTitle("    INFORMATION")
                    .setMessage("Please introduce username and password.")
                    .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {}
                    })
                    .setIcon(R.drawable.ic_information)
                    .show();
        }
        else
        {
            Firebase.setAndroidContext(getContext());
            final Firebase ref = new Firebase("https://fryencyty.firebaseio.com/");
            ref.authWithPassword(eTUsernameMain.getText().toString(), eTPasswordMain.getText().toString(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("provider", authData.getProvider());
                    map.put("email", eTUsernameMain.getText().toString());
                    ref.child("users").child(authData.getUid()).setValue(map);
                    Intent tabActivity = new Intent(getContext(), tabActivity.class);
                    tabActivity.putExtra("name", eTUsernameMain.getText().toString());
                    startActivity(tabActivity);
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("    INFORMATION")
                            .setMessage("Error login in, try again. (" + firebaseError.getMessage() + ")")
                            .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(R.drawable.ic_information)
                            .show();
                }
            });
        }
    }
}
