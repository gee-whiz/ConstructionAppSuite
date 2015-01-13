package com.example.codetribe1.constructionappsuite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BeneficiaryFragment extends android.support.v4.app.Fragment {


    public BeneficiaryFragment() {
        // Required empty public constructor
    }

    public static BeneficiaryFragment newInstance(int Page) {
        BeneficiaryFragment fragment = new BeneficiaryFragment();
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
        return inflater.inflate(R.layout.fragment_beneficiary, container, false);
    }


}
