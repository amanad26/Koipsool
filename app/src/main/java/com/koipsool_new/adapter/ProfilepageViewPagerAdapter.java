package com.koipsool_new.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.koipsool_new.ui.pharma.BasicDetailsFragment;
import com.koipsool_new.ui.pharma.BillingDetailFragment;

public class ProfilepageViewPagerAdapter extends FragmentStateAdapter {

    public ProfilepageViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new BasicDetailsFragment();
        else return new BillingDetailFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}

