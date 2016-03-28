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

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreateUserFragment extends Fragment {

    public CreateUserFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_user, container, false);
        final EditText eTEmailCreate = (EditText) rootView.findViewById(R.id.eTEmailCreate);
        final EditText eTPassword1Create = (EditText) rootView.findViewById(R.id.eTPassword1Create);
        final EditText eTPassword2Create = (EditText) rootView.findViewById(R.id.eTPassword2Create);
        Button bTSignCreate = (Button) rootView.findViewById(R.id.bTSignCreate);

        bTSignCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!eTEmailCreate.getText().toString().equals("")
                        && !eTPassword1Create.getText().toString().equals("")
                        && !eTPassword2Create.getText().toString().equals("")
                        && (eTPassword1Create.getText().toString().equals(eTPassword2Create.getText().toString())))
                {
                    Firebase.setAndroidContext(getContext());
                    Firebase ref = new Firebase("https://fryencyty.firebaseio.com/");
                    ref.createUser(eTEmailCreate.getText().toString(), eTPassword1Create.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                        @Override
                        public void onSuccess(Map<String, Object> stringObjectMap) {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("    INFORMATION")
                                    .setMessage("Congratulations, your account have been created.")
                                    .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent returnIntent = new Intent();
                                            getActivity().setResult(Activity.RESULT_CANCELED, returnIntent);
                                            getActivity().finish();
                                        }
                                    })
                                    .setIcon(R.drawable.ic_information)
                                    .show();
                        }

                        @Override
                        public void onError(FirebaseError firebaseError) {
                            new AlertDialog.Builder(getContext())
                                    .setTitle("    INFORMATION")
                                    .setMessage("Error creating your account, try again. (" + firebaseError.getMessage() + ")")
                                    .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .setIcon(R.drawable.ic_information)
                                    .show();
                        }
                    });
                }
                else {
                    new AlertDialog.Builder(getContext())
                            .setTitle("    INFORMATION")
                            .setMessage("Completing all fields is necesary.")
                            .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {}
                            })
                            .setIcon(R.drawable.ic_information)
                            .show();
                }
            }
        });

        return rootView;
    }
}
