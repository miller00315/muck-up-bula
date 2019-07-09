package br.com.miller.muckup.menuPrincipal.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.miller.muckup.menuPrincipal.views.fragments.SearchFragment;
import br.com.miller.muckup.menuPrincipal.views.fragments.OffersFragment;
import br.com.miller.muckup.menuPrincipal.views.fragments.PerfilFragment;
import br.com.miller.muckup.menuPrincipal.views.fragments.StoresFragment;

public class TabPagerAdapter extends FragmentPagerAdapter {

    private int tabCount;

    private Context context;

    public TabPagerAdapter(FragmentManager fm, int numOfTabs, Context context) {
        super(fm);

        tabCount = numOfTabs;
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i){

            case 0:
                return Fragment.instantiate(context, SearchFragment.class.getName());

            case 1:
                return Fragment.instantiate(context, OffersFragment.class.getName());

            case 2:
                return Fragment.instantiate(context, StoresFragment.class.getName());

            case 3:
                return Fragment.instantiate(context, PerfilFragment.class.getName());

                default:
                    return null;
        }
    }



    @Override
    public int getCount() {
        return tabCount;
    }
}
