package com.example.book.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.book.Screen.CartActivity;
import com.example.book.Screen.HomePageFragment;
import com.example.book.Screen.ProfileFragment;
import com.example.book.Screen.MessageFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomePageFragment();
            case 1:
                return new MessageFragment();
            case 2:
                return new CartActivity();
            case 3:
                return new ProfileFragment();
            default:
                return new HomePageFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
