package com.example.customer.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.customer.Screen.Cart;
import com.example.customer.Screen.HomeProduct;
import com.example.customer.Screen.Me;
import com.example.customer.Screen.MessageUser;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new HomeProduct();
            case 1:
                return new MessageUser();
            case 2:
                return new Cart();
            case 3:
                return new Me();
            default:
                return new HomeProduct();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
