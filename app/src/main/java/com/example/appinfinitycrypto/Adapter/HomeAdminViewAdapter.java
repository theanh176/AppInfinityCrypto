package com.example.appinfinitycrypto.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appinfinitycrypto.Fragment.HomeAdminFragment;
import com.example.appinfinitycrypto.Fragment.ListAdminFragment;
import com.example.appinfinitycrypto.Fragment.ListUserFragment;

public class HomeAdminViewAdapter extends FragmentStatePagerAdapter {

    public HomeAdminViewAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeAdminFragment();
            case 1:
                return new ListAdminFragment();
            case 2:
                return new ListUserFragment();
            default:
                return new HomeAdminFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Home";
            case 1:
                return "List Admin";
            case 2:
                return "List User";
            default:
                return "Home";
        }
    }
}
