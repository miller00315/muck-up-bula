package br.com.miller.muckup.store.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import java.util.ArrayList;

import br.com.miller.muckup.store.fragments.OpinionFragment;
import br.com.miller.muckup.store.fragments.DepartamentFragment;
import br.com.miller.muckup.store.fragments.HomeStoreFragment;

public class TabPagerStoreAdapter extends FragmentPagerAdapter {

    private int tabCount;

    private Context context;

    private Bundle bundle;

    private ArrayList<String> fragmentsAdapters;

    public TabPagerStoreAdapter(FragmentManager fm, int numTabs, Context context, Bundle bundle) {
        super(fm);

        tabCount = numTabs;

        this.context = context;

        this.bundle = bundle;

        fragmentsAdapters = new ArrayList<>();

        fragmentsAdapters.add(HomeStoreFragment.class.getName());
        fragmentsAdapters.add(DepartamentFragment.class.getName());
        fragmentsAdapters.add(OpinionFragment.class.getName());

    }


    @Override
    public Fragment getItem(int i) {

        Log.w("test", String.valueOf(i));

        return Fragment.instantiate(context, fragmentsAdapters.get(i), bundle);

    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
