package com.koipsool_new.ui.pharma;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koipsool_new.databinding.FragmentAllApplicationBinding;
import com.koipsool_new.kapsoolAdapters.ApplicantsAdapter;
import com.koipsool_new.model.MyAppliedJobModel;

import java.util.ArrayList;
import java.util.List;

public class AllApplicationFragment extends Fragment {

    private List<MyAppliedJobModel.MyAppliedData> list = new ArrayList<>();
    private FragmentAllApplicationBinding binding;
    private Activity activity ;

    public AllApplicationFragment(List<MyAppliedJobModel.MyAppliedData> list) {
        // Required empty public constructor
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getApplyStatus().equalsIgnoreCase("applied") || list.get(i).getApplyStatus().equalsIgnoreCase("view")) {
                this.list.add(list.get(i));
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAllApplicationBinding.inflate(getLayoutInflater());

        activity = requireActivity() ;

        binding.recycelrView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recycelrView.setAdapter(new ApplicantsAdapter(activity, list,0));

        return binding.getRoot();
    }
}