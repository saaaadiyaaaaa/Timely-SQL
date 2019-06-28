package com.example.sqltodo.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.sqltodo.R;

public class FragmentArticles extends Fragment {

    CardView art1, art2, art3, art4, art5, art6;

    private final String TAG = "FRAGMENT_ARTICLES";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( R.layout.fragment_articles, container, false );

        art1 = view.findViewById( R.id.art1 );
        art2 = view.findViewById( R.id.art2 );
        art3 = view.findViewById( R.id.art3 );

        art1.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.buzzfeed.com/ariannarebolini/22-tweets-about-procrastination-that-will-make-you-laugh-out"
                )));

            }
        } );

        art2.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.buzzfeed.com/emmamcanaw/things-that-may-actually-help-you-stop-procrastinating" )));
            }
        } );

        art3.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.inc.com/lolly-daskal/10-smart-tips-to-prevent-distractions-and-sharpen-your-focus.html" )));
            }
        } );

        return view;
    }
}
