package com.koipsool_new.ui.pharma;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koipsool_new.databinding.FragmentRejectedApplicationBinding;
import com.koipsool_new.kapsoolAdapters.ApplicantsAdapter;
import com.koipsool_new.model.MyAppliedJobModel;

import java.util.ArrayList;
import java.util.List;


public class RejectedApplicationFragment extends Fragment {


    List<MyAppliedJobModel.MyAppliedData> list= new ArrayList<>();
    private FragmentRejectedApplicationBinding binding;
    private Activity activity;

    public RejectedApplicationFragment(List<MyAppliedJobModel.MyAppliedData> list) {
        // Required empty public constructor
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getApplyStatus().equalsIgnoreCase("rejected")) {
                this.list.add(list.get(i));
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRejectedApplicationBinding.inflate(getLayoutInflater());
        activity = requireActivity();
        binding.recycelrView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recycelrView.setAdapter(new ApplicantsAdapter(activity, list ,2));

        return binding.getRoot();
    }
}