package com.example.codetribe1.constructionappsuite;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ProjectStatustTypeListFragment extends android.support.v4.app.Fragment {


    public ProjectStatustTypeListFragment() {
        // Required empty public constructor
    }

    public static ProjectStatustTypeListFragment newInstance(int Page) {
        ProjectStatustTypeListFragment fragment = new ProjectStatustTypeListFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", Page);
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
        return inflater.inflate(R.layout.fragment_project_statust_type_list, container, false);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
