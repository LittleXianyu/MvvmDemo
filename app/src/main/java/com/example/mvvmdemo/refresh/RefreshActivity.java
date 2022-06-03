package com.example.mvvmdemo.refresh;

import static androidx.fragment.app.FragmentStatePagerAdapter.BEHAVIOR_SET_USER_VISIBLE_HINT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.mvvmdemo.R;
import com.example.mvvmdemo.list.InfoListAdapter;
import com.example.mvvmdemo.list.ItemModel;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

public class RefreshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.pager) ;
        viewPager.setAdapter(new DemoCollectionPagerAdapter(getSupportFragmentManager()));

        tabLayout.setupWithViewPager(viewPager);
    }
    public class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

        public DemoCollectionPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }


        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            // Our object is just an integer :-P
            args.putInt(DemoObjectFragment.ARG_OBJECT, position + 1);
            fragment.setArguments(args);
            return fragment;
        }

    }

}