package com.lofstschool.mymoneytrackeroct17;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MainPagerAdapter extends FragmentPagerAdapter {


    private final static int PAGE_EXPENSES = 0;
    private final static int PAGE_INCOMES = 1;
    private final static int PAGE_BALANC = 2;


    private  String[] titles;

    public  MainPagerAdapter (FragmentManager fm, Resources resources){
        super (fm);

        titles = resources.getStringArray(R.array.tabs_titles);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case PAGE_EXPENSES:
                return ItemsFragment.createItemsFragment(ItemsFragment.TYPE_EXPENSE);
            case PAGE_INCOMES:
                return ItemsFragment.createItemsFragment(ItemsFragment.TYPE_INCOME);
            case PAGE_BALANC:
                BalanceFragment fragment = new BalanceFragment();
                return fragment;

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles [position];
    }
}
