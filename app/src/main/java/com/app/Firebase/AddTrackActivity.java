package com.app.Firebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AddTrackActivity extends AppCompatActivity {
    Button buttonAddTrack;
    EditText editTextTrackNames;
    SeekBar seekBarRating;
    TextView textViewRating, textViewArtist;
    ListView listViewTracks;

    DatabaseReference databaseTracks;

    List<Track> tracks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);
        textViewArtist = findViewById(R.id.TextViewArtistName);
        editTextTrackNames = findViewById(R.id.editTextTrackName);
        seekBarRating = findViewById(R.id.seekbar);
        listViewTracks = findViewById(R.id.listViewArtist);
        buttonAddTrack = findViewById(R.id.addTrack);
        tracks = new ArrayList<>();

        Intent i = getIntent();
        String id = i.getStringExtra(MainActivity.Artist_Id);
        String Name = i.getStringExtra(MainActivity.Artist_Name);
        textViewArtist.setText(Name);
       /* seekBarRating.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textViewRating.setText(String.valueOf(i));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });*/

        databaseTracks = FirebaseDatabase.getInstance().getReference("tracks").child((i.getStringExtra(MainActivity.Artist_Id)));
        buttonAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrack();
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tracks.clear();
                for (DataSnapshot tracksnapshort : dataSnapshot.getChildren()) {
                    Track trackss = tracksnapshort.getValue(Track.class);
                    tracks.add(trackss);

                }
                TrackList adapter = new TrackList(AddTrackActivity.this, tracks);
                listViewTracks.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void saveTrack() {
        String trackName = editTextTrackNames.getText().toString().trim();
        int rating = seekBarRating.getProgress();
        if (!TextUtils.isEmpty(trackName)) {
            String id = databaseTracks.push().getKey();
            Track tracks = new Track(id, trackName, rating);
            databaseTracks.child(id).setValue(tracks);
            Toast.makeText(this, "Track saved", Toast.LENGTH_LONG).show();

        } else {
            Toast.makeText(this, "Please enter track name", Toast.LENGTH_LONG).show();
        }
    }
}

