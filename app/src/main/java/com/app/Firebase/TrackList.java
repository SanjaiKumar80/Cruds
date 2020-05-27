package com.app.Firebase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * CREATED BY SANJAIKUMAR On 27-05-2020
 */
public class TrackList extends ArrayAdapter<Track> {

    private Activity context;
    private List<Track> tracks;

    public TrackList(Activity context, List<Track> tracks) {
        super(context, R.layout.tracklist, tracks);
        this.context = context;
        this.tracks = tracks;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();
        View listViewItem = inflator.inflate(R.layout.tracklist, null, true);

        TextView textviewname = listViewItem.findViewById(R.id.textracksName);
        TextView textViewRating = listViewItem.findViewById(R.id.textViewRating);
        Track track = tracks.get(position);
        textviewname.setText(track.getTrackName());
        textViewRating.setText(String.valueOf(track.getTrackRating()));
        return listViewItem;
    }
}





