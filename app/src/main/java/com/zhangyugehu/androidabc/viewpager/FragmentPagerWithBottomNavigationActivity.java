package com.zhangyugehu.androidabc.viewpager;

import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.Toast;

import com.zhangyugehu.androidabc.R;
import com.zhangyugehu.androidabc.viewpager.fragment.ContactFragment;
import com.zhangyugehu.androidabc.viewpager.fragment.DiscoverFragment;
import com.zhangyugehu.androidabc.viewpager.fragment.MainFragment;
import com.zhangyugehu.androidabc.viewpager.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentPagerWithBottomNavigationActivity extends FragmentActivity {

    @BindView(R.id.fragment_view_pager) ViewPager mViewPager;
    @BindView(R.id.bottom_navigation_view) BottomNavigationView mBottomNavigationView;
    private FragmentManager mFragmentManager;
    private List<Fragment> mFragments;
    private MainFragment mainFragment;
    private ContactFragment contactFragment;
    private DiscoverFragment discoverFragment;
    private MineFragment mineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_pager);
        ButterKnife.bind(this);
        mFragments = new ArrayList<>();
        mFragmentManager = getSupportFragmentManager();
        mainFragment = MainFragment.newInstance();
        contactFragment = ContactFragment.newInstance();
        discoverFragment = DiscoverFragment.newInstance();
        mineFragment = MineFragment.newInstance();
        mFragments.add(mainFragment);
        mFragments.add(contactFragment);
        mFragments.add(discoverFragment);
        mFragments.add(mineFragment);

        mViewPager.setOnTouchListener((view, motionEvent)-> true);
        mViewPager.setAdapter(new FragmentPagerAdapter(mFragmentManager) {
            @Override
            public Fragment getItem(int i) {
                return mFragments.get(i);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()){
                case R.id.menu_main:
                    mViewPager.setCurrentItem(0, false);
                    break;
                case R.id.menu_contact:
                    mViewPager.setCurrentItem(1, false);
                    break;
                case R.id.menu_discover:
                    mViewPager.setCurrentItem(2, false);
                    break;
                case R.id.menu_mine:
                    mViewPager.setCurrentItem(3, false);
                    break;
            }
            return true;
        });

        mBottomNavigationView.setOnNavigationItemReselectedListener(menuItem ->
                Toast.makeText(FragmentPagerWithBottomNavigationActivity.this, menuItem.getTitle() + " released", Toast.LENGTH_SHORT).show());

    }
}
