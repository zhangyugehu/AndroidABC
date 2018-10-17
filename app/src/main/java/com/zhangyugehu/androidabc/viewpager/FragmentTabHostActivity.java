package com.zhangyugehu.androidabc.viewpager;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.zhangyugehu.androidabc.R;
import com.zhangyugehu.androidabc.viewpager.fragment.ContactFragment;
import com.zhangyugehu.androidabc.viewpager.fragment.DiscoverFragment;
import com.zhangyugehu.androidabc.viewpager.fragment.MainFragment;
import com.zhangyugehu.androidabc.viewpager.fragment.MineFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentTabHostActivity extends FragmentActivity {

    @BindView(R.id.fragment_tab_host)
    FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tab_host);
        ButterKnife.bind(this);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.main_content);


        addTab(getString(R.string.main_fragment), getResources().getDrawable(R.drawable.ic_toys_black_24dp), MainFragment.class, null);
        addTab(getString(R.string.contact_fragment), getResources().getDrawable(R.drawable.ic_contacts_black_24dp), ContactFragment.class, null);
        addTab(getString(R.string.discover_fragment), getResources().getDrawable(R.drawable.ic_explore_black_24dp), DiscoverFragment.class, null);
        addTab(getString(R.string.mine_fragment), getResources().getDrawable(R.drawable.ic_account_circle_black_24dp), MineFragment.class, null);


        // 去除分割线
        mTabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);
    }

    private void addTab(String title, Drawable indicatorDrawable, Class<? extends Fragment> clazz, Bundle args){
        View itemPanel = LayoutInflater.from(this).inflate(R.layout.tab_item_panel, null, false);
        ImageView imageView = itemPanel.findViewById(R.id.imageView);
        TextView textView = itemPanel.findViewById(R.id.textView);
        textView.setText(title);
        imageView.setImageDrawable(indicatorDrawable);
        TabHost.TabSpec tabSpec = mTabHost.newTabSpec(title).setIndicator(itemPanel);
        mTabHost.addTab(tabSpec, clazz, args);
    }

}
