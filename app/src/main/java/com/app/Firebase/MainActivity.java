package com.app.Firebase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String Artist_Name = "artistname";
    public static final String Artist_Id = "artistid";
    EditText editTextName;
    Button addButton;
    Spinner spinnerGeneres;
    DatabaseReference databaseArtist;
    ListView listViewArtist;
    ArrayList<Artist> newartist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseArtist = FirebaseDatabase.getInstance().getReference("artist");
        newartist = new ArrayList<>();
        editTextName = findViewById(R.id.editTextName);
        addButton = findViewById(R.id.addAtrist);
        spinnerGeneres = findViewById(R.id.spinnerGenere);
        listViewArtist = findViewById(R.id.listViewArtist);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArtist();
            }
        });

        listViewArtist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Artist artists = newartist.get(i);
                Intent intent = new Intent(getApplicationContext(), AddTrackActivity.class);
                intent.putExtra(Artist_Id, artists.artistId);
                intent.putExtra(Artist_Name, artists.getArtistName());
                startActivity(intent);
            }
        });
        listViewArtist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Artist artist = newartist.get(i);
                showUpdateDeleteDialog(artist.getArtistId(), artist.getArtistName());
                return true;
            }
        });


    }

    @Override
    protected void onStart() {

        super.onStart();
        databaseArtist.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                newartist.clear();
                for (DataSnapshot artistSnapshort : dataSnapshot.getChildren()) {
                    Artist artist = artistSnapshort.getValue(Artist.class);
                    newartist.add(artist);


                }
                ArtistList adapter = new ArtistList(MainActivity.this, newartist);
                listViewArtist.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void showUpdateDeleteDialog(final String artistId, String artistName) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText editTextName = dialogView.findViewById(R.id.editTextName);
        final Spinner spinnerGenre = dialogView.findViewById(R.id.spinnerGenres);
        final Button buttonUpdate = dialogView.findViewById(R.id.buttonUpdateArtist);
        final Button buttonDelete = dialogView.findViewById(R.id.buttonDeleteArtist);

        dialogBuilder.setTitle(artistName);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String genre = spinnerGenre.getSelectedItem().toString();
                if (!TextUtils.isEmpty(name)) {
                    updateArtist(artistId, name, genre);
                    b.dismiss();
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteArtist(artistId);
                b.dismiss();
            }
        });


    }

    private boolean updateArtist(String id, String name, String genre) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("artist").child(id);

        //updating artist
        Artist artist = new Artist(id, name, genre);
        dR.setValue(artist);
        Toast.makeText(getApplicationContext(), "Artist Updated", Toast.LENGTH_LONG).show();
        return true;
    }

    private boolean deleteArtist(String id) {
        //getting the specified artist reference
        DatabaseReference dR = FirebaseDatabase.getInstance().getReference("artist").child(id);

        //removing artist
        dR.removeValue();

        //getting the tracks reference for the specified artist
        DatabaseReference drTracks = FirebaseDatabase.getInstance().getReference("tracks").child(id);

        //removing all tracks
        drTracks.removeValue();
        Toast.makeText(getApplicationContext(), "Artist Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    private void addArtist() {
        String name = editTextName.getText().toString().trim();
        String genere = spinnerGeneres.getSelectedItem().toString().trim();

        if (!TextUtils.isEmpty(name)) {

            String id = databaseArtist.push().getKey();
            Artist art = new Artist(id, name, genere);
            databaseArtist.child(id).setValue(art);
            Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "you should enter a name", Toast.LENGTH_SHORT).show();

        }
    }
}

