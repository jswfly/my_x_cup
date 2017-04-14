package com.example.applicationtest2;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import fragment.CategoryFragment;
import fragment.GiftFragment;
import fragment.HomeFragment;
import fragment.ProfileFragment;
import fragment.RankFragment;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private TextView txtTop;
    private RadioGroup rgTab;
    private RadioButton rbHome, rbgift, rbcategory, rbprofile, rbRank;
    private HomeFragment homeFragment;
    private RankFragment rankFragment;
    private GiftFragment giftFragment;
    private CategoryFragment categoryFragment;
    private ProfileFragment profileFragment;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        manager = getSupportFragmentManager();
        if(savedInstanceState == null){
            homeFragment = new HomeFragment();
            manager.beginTransaction().add(R.id.fragment_container, homeFragment).commit();
        }
        bindView();
    }

    private void bindView() {
        txtTop = (TextView) findViewById(R.id.txtTop);
        rgTab = (RadioGroup) findViewById(R.id.rgMenu);

        rbHome = (RadioButton) findViewById(R.id.rbHome);
        rbRank = (RadioButton) findViewById(R.id.rbRank);
        rbgift = (RadioButton) findViewById(R.id.rbGift);
        rbcategory = (RadioButton) findViewById(R.id.rbCategory);
        rbprofile = (RadioButton) findViewById(R.id.rbProfile);
        rgTab.setOnCheckedChangeListener(this);
        rbHome.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction ft = manager.beginTransaction();
        hideAllFragment(ft);
        switch(checkedId){
            case R.id.rbHome:
                if(homeFragment==null){
                    homeFragment = new HomeFragment();
                    ft.add(R.id.fragment_container,homeFragment);
                }else{
                    ft.show(homeFragment);
                }
                txtTop.setText(getResources().getString(R.string.home));
                break;
            case R.id.rbRank:
                if(rankFragment==null){
                    rankFragment = new RankFragment();
                    ft.add(R.id.fragment_container,rankFragment);
                }else{
                    ft.show(rankFragment);
                }
                txtTop.setText(getResources().getString(R.string.rank));
                break;
            case R.id.rbGift:
                if(giftFragment==null){
                    giftFragment = new GiftFragment();
                    ft.add(R.id.fragment_container,giftFragment);
                }else{
                    ft.show(giftFragment);
                }
                txtTop.setText(getResources().getString(R.string.gift));
                break;
            case R.id.rbCategory:
                if(categoryFragment==null){
                    categoryFragment = new CategoryFragment();
                    ft.add(R.id.fragment_container,categoryFragment);
                }else{
                    ft.show(categoryFragment);
                }
                txtTop.setText(getResources().getString(R.string.category));
                break;
            case R.id.rbProfile:
                if(profileFragment==null){
                    profileFragment = new ProfileFragment();
                    ft.add(R.id.fragment_container,profileFragment);
                }else{
                    ft.show(profileFragment);
                }
                txtTop.setText(getResources().getString(R.string.profile));
                break;
        }
        ft.commit();

    }

    public void hideAllFragment(FragmentTransaction ft){
        if(homeFragment!=null){
            ft.hide(homeFragment);
        }
        if(rankFragment!=null){
            ft.hide(rankFragment);
        }
        if(giftFragment!=null){
            ft.hide(giftFragment);
        }
        if(categoryFragment!=null){
            ft.hide(categoryFragment);
        }
        if(profileFragment!=null){
            ft.hide(profileFragment);
        }
    }

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出应用", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                //强制退出
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
