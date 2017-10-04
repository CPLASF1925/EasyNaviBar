package com.capton.enbdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.capton.enb.DisplayUtil;
import com.capton.enb.EasyNaviBar;

import java.util.ArrayList;
import java.util.List;

public class DemoActivity extends AppCompatActivity {


    EasyNaviBar naviBar;
    Settings settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        settings= (Settings) getIntent().getSerializableExtra("setting");

        initFrament();

        naviBar= (EasyNaviBar) findViewById(R.id.navibar);
        String titles[]=new String[]{"应用","日程","设置","云盘"};
        int icons[]=new int[]{R.drawable.ic_dashboard_black_36dp,
                R.drawable.ic_event_note_black_36dp,
                R.drawable.ic_phonelink_setup_black_36dp,
                R.drawable.ic_cloud_circle_black_36dp};
        int checkIcons[]=new int[]{R.drawable.ic_dashboard_green_36dp,
                R.drawable.ic_event_note_amber_500_36dp,
                R.drawable.ic_phonelink_setup_green_36dp,
                R.drawable.ic_cloud_circle_light_blue_600_36dp};
        naviBar.setTabData(titles, icons, checkIcons);
        naviBar.setOnTabClickListener(new EasyNaviBar.OnTabClickListener() {
            @Override
            public void onTabClick(int position) {
                showFragment(position);
                naviBar.setBadgeShown(position,false);
            }
        });

        naviBar.setWidth(DisplayUtil.px2dip(this,settings.getWidth()));
        naviBar.setHeight(DisplayUtil.px2dip(this,settings.getHeight()));
        naviBar.setIconWidth(settings.getIconWidth());
        naviBar.setIconHeight(settings.getIconHeight());
        naviBar.setTextSize(settings.getTextSize());
        naviBar.setTextColor(settings.getTextColor());
        naviBar.setCheckTextColor(settings.getCheckTextColor());
        naviBar.setTextMarginTop(settings.getTextMarginTop());
        naviBar.setTextMarginBottom(settings.getTextMarginBottom());
        naviBar.setIconMarginTop(settings.getIconMarginTop());
        naviBar.setIconMarginBottom(settings.getIconMarginBottom());
        naviBar.setTextMarginTop(settings.getTextMarginTop());
        naviBar.setTextMarginBottom(settings.getTextMarginBottom());
        naviBar.showDivider(settings.showDivider);
        naviBar.setDividerColor(settings.getDividerColor());

        naviBar.setBadgeMessage(2);

    }

    List<Fragment> fragments=new ArrayList<>();
    public void initFrament(){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        for (int i = 0; i <4; i++) {
            BlankFragment blankFragment=new BlankFragment();
            Bundle bundle=new Bundle();
            bundle.putInt("index",i);
            blankFragment.setArguments(bundle);
            transaction.add(R.id.container,blankFragment);
            transaction.hide(blankFragment);
            fragments.add(blankFragment);
        }
        transaction.show(fragments.get(0));
        transaction.commitNow();
    }

    public void showFragment(int position){
        FragmentManager manager=getSupportFragmentManager();
        FragmentTransaction transaction=manager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            transaction.hide(fragments.get(i));
        }
        transaction.show(fragments.get(position));
        transaction.commitNow();
    }
    public void finish(View view){
        finish();
    }

}
