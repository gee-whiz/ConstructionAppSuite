package com.example.codetribe1.constructionappsuite;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class GeorgeFragment extends android.support.v4.app.Fragment {


    private OnFragmentInteractionListener mListener;


    public GeorgeFragment() {
        // Required empty public constructor
    }

    public static GeorgeFragment newInstance(int page) {
        GeorgeFragment fragment = new GeorgeFragment();
        Bundle args = new Bundle();
        args.putInt("shsh", page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_george, container, false);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
