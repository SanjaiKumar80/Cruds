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
public class ArtistList extends ArrayAdapter<Artist> {

    private Activity context;
    private List<Artist> artistList;

    public ArtistList(Activity context, List<Artist> artistList) {
        super(context, R.layout.listlayout, artistList);
        this.context = context;
        this.artistList = artistList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();
        View listViewItem = inflator.inflate(R.layout.listlayout, null, true);

        TextView textviewname = listViewItem.findViewById(R.id.textViewName);
        TextView textviewgenere = listViewItem.findViewById(R.id.textViewGenere);
        Artist artist = artistList.get(position);
        textviewname.setText(artist.artistName);
        textviewgenere.setText(artist.artistGenre);
        return listViewItem;
    }
}
