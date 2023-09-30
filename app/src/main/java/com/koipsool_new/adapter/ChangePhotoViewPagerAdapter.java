package com.koipsool_new.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.koipsool_new.ChangePhotoBasicDetaiFragment;
import com.koipsool_new.ResumeFragment;

public class ChangePhotoViewPagerAdapter extends FragmentStateAdapter {

    public ChangePhotoViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0)
            return new ChangePhotoBasicDetaiFragment();
        else return new ResumeFragment();
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
