package com.example.android.assignment_1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import static com.example.android.assignment_1.utils.Utils.getFileByName;
import static com.example.android.assignment_1.utils.Utils.getListFromFiles;



public class TitlesFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    public TitlesFragment() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_titles, container, false);
        ListView ls=(ListView)view.findViewById(R.id.list_frg);

        //Using Files
        ls.setAdapter(new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,
                getListFromFiles(getContext())));
        ls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                //Using Files
                mListener.onFragmentInteraction(getFileByName(getContext(), getListFromFiles(getContext())[i]));
            }
        });
        return view;
    }
    public void onButtonPressed(String uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(String uri);
    }
}
