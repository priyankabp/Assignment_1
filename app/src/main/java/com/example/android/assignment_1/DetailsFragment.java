package com.example.android.assignment_1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {


    String str;

    public DetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
            str = getArguments().getString("KEY");
        if (getActivity().getIntent().getStringExtra("MSG") != null)
            str = getActivity().getIntent().getStringExtra("MSG");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        ((TextView) view.findViewById(R.id.fargmentDetails_textView)).setText(str);
        return view;
    }

}