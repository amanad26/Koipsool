package com.koipsool_new.ui.pharma;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.koipsool_new.databinding.FragmentAccepetdApplicationBinding;

import com.koipsool_new.kapsoolAdapters.ApplicantsAdapter;
import com.koipsool_new.model.MyAppliedJobModel;

import java.util.ArrayList;
import java.util.List;

public class AccepetdApplicationFragment extends Fragment {

    List<MyAppliedJobModel.MyAppliedData> list= new ArrayList<>();
    private FragmentAccepetdApplicationBinding binding;
    private Activity activity;

    public AccepetdApplicationFragment(List<MyAppliedJobModel.MyAppliedData> list) {

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getApplyStatus().equalsIgnoreCase("accepted")) {
                this.list.add(list.get(i));
            }
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAccepetdApplicationBinding.inflate(getLayoutInflater());

        activity = requireActivity();
        binding.recycelrView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recycelrView.setAdapter(new ApplicantsAdapter(activity, list,1));

        return binding.getRoot();
    }
}