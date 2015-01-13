package com.example.codetribe1.constructionappsuite;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class StaffListFragment extends android.support.v4.app.Fragment {


    public StaffListFragment() {
        // Required empty public constructor
    }

    public static StaffListFragment newInstance(int Page) {
        StaffListFragment fragment = new StaffListFragment();
        Bundle args = new Bundle();
        args.putInt("someint", Page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_staff_list, container, false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
