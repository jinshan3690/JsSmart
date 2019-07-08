package com.js.smart.common.app.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;


public class SimplePagerAdapter extends FragmentPagerAdapter {

    private String tabTitle[];
    private List<Fragment> frags;

    public SimplePagerAdapter(FragmentManager fm, List<Fragment> frags, String[] tabTitle) {
        super(fm);
        this.frags = frags;
        this.tabTitle = tabTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return frags.get(position);
    }

    @Override
    public int getCount() {
        return frags.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle[position];
    }
}