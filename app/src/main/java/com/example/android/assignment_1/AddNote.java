package com.example.android.assignment_1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.stetho.Stetho;

import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddNote extends AppCompatActivity implements TitlesFragment.OnFragmentInteractionListener {


    FragmentTransaction transaction;

    // User Session Manager Class
    UserSessionManagement session;
    ActivityTracker activityTracker;
    private String logInUser;

    @BindView(R.id.AddNote_button)
    Button addNote_button;
    @BindView(R.id.AddNote_textView)
    TextView addNote_textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        Stetho.initializeWithDefaults(this);
        ButterKnife.bind(this);

        // Session class instance
        session = new UserSessionManagement(getApplicationContext());
        logInUser = session.getUserDetails();
        activityTracker = new ActivityTracker(getApplicationContext(), logInUser);
        activityTracker.updateActivity(logInUser+" moved to Add Note Page!");

        addNote_button.setOnClickListener(new Lsnr());

        transaction=getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_container,new TitlesFragment());
        transaction.commit();

        if(findViewById(R.id.fragment_container_details)!=null){
            transaction=getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_container_details,new DetailsFragment());
            transaction.commit();
        }
    }


    class Lsnr implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            final View thisView = view;
            View viewGrp = getLayoutInflater().inflate(R.layout.costum_dialog_layout,
                    (ViewGroup) findViewById(R.id.activity_addnote), false);

            final EditText noteTitle = (EditText) viewGrp.findViewById(R.id.AddNote_custom_dialog_title);
            final EditText noteBody = (EditText) viewGrp.findViewById(R.id.AddNote_custom_dialog_body);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(AddNote.this)
                    .setTitle("Take a note").setView(viewGrp)

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            addNote_textView.setText(noteTitle.getText());

                            //Using Files
                            try {
                                FileOutputStream outputStream = openFileOutput(noteTitle.getText().toString(), MODE_APPEND);
                                outputStream.write(noteBody.getText().toString().getBytes());
                                outputStream.close();
                                Snackbar.make(thisView, "File Saved", Snackbar.LENGTH_SHORT).show();
                                activityTracker.updateActivity(logInUser+" added note" + noteTitle.getText().toString());
                            } catch (Exception e) {
                                Log.e("ERROR", e.getMessage());
                            }
                        }
                    });
            alertBuilder.show();
        }
    }

    @Override
    public void onFragmentInteraction(String uri) {
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new TitlesFragment());
        transaction.commit();
        if (findViewById(R.id.fragment_container_details) != null) {
            transaction = getSupportFragmentManager().beginTransaction();
            DetailsFragment df = new DetailsFragment();
            Bundle b = new Bundle();
            b.putString("KEY", uri);
            df.setArguments(b);
            transaction.add(R.id.fragment_container_details, df);
            transaction.commit();
        } else {
            Intent intent = new Intent(AddNote.this, AddNote2.class);
            intent.putExtra("MSG", uri);
            startActivity(intent);
        }

    }
}
