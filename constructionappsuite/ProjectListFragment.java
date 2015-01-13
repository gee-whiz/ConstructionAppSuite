package com.example.codetribe1.constructionappsuite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class ProjectListFragment extends android.support.v4.app.Fragment {


    public ProjectListFragment() {
        // Required empty public constructor
    }

    public static ProjectListFragment newInstance(int Page) {
        ProjectListFragment fragment = new ProjectListFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", Page);
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

        View view = inflater.inflate(R.layout.fragment_project_list, container, false);
        return view;
    }


}
