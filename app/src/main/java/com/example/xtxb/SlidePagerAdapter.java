package com.example.xtxb;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class SlidePagerAdapter extends FragmentPagerAdapter {

    public SlidePagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new FragmentOne(); // 第一个界面的Fragment
        } else if (position == 1) {
            return new FragmentTwo(); // 第二个界面的Fragment
        } else {
            return new FragmentThree(); // 第三个界面的Fragment
        }
    }

    @Override
    public int getCount() {
        return 3; // 总共有三个界面
    }
}
