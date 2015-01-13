package com.example.codetribe1.constructionappsuite;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TaskFragment extends android.support.v4.app.Fragment {


    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(int Page) {
        TaskFragment fragment = new TaskFragment();
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
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
