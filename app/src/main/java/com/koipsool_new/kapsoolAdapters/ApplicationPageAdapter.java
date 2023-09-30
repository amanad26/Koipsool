package com.koipsool_new.kapsoolAdapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.koipsool_new.model.MyAppliedJobModel;
import com.koipsool_new.ui.pharma.AccepetdApplicationFragment;
import com.koipsool_new.ui.pharma.AllApplicationFragment;
import com.koipsool_new.ui.pharma.RejectedApplicationFragment;

import java.util.List;

public class ApplicationPageAdapter extends FragmentStateAdapter {

    List<MyAppliedJobModel.MyAppliedData> list ;

    public ApplicationPageAdapter(@NonNull FragmentActivity fragmentActivity, List<MyAppliedJobModel.MyAppliedData> list) {
        super(fragmentActivity);
        this.list = list ;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
      if(position == 0) return  new AllApplicationFragment(list);
      else if(position == 1) return  new AccepetdApplicationFragment(list);
      else return new RejectedApplicationFragment(list);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
