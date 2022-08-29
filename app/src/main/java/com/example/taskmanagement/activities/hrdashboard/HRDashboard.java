package com.example.taskmanagement.activities.hrdashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.taskmanagement.R;
import com.example.taskmanagement.adapter.PageAdapter;
import com.example.taskmanagement.adapter.PageAdapter2;
import com.example.taskmanagement.databinding.ActivityHrdashboardBinding;
import com.google.android.material.tabs.TabLayout;

public class HRDashboard extends AppCompatActivity {

    ActivityHrdashboardBinding binding;
    PageAdapter2 adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding= DataBindingUtil.setContentView(this,R.layout.activity_hrdashboard);

        FragmentManager manager = getSupportFragmentManager();
        adapter = new PageAdapter2(manager, getLifecycle());
        binding.viewPager.setAdapter(adapter);

        SharedPreferences sharedPreferences=getSharedPreferences("db", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("k","HR");
        editor.apply();
        editor.commit();

        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("WorkPlace"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Employ"));

//
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //Load the tab using pageadapter
                binding.viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0 || tab.getPosition()==1) {
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//
//        //Scroll r swipe fragment
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                binding.tabLayout.selectTab(binding.tabLayout.getTabAt(position));
            }
        });

    }
}