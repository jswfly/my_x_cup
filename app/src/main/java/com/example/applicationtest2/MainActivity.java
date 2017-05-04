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
import fragment.ForumFragment;
import fragment.HomeFragment;
import fragment.ProfileFragment;
import fragment.DesignFragment;

public class MainActivity extends FragmentActivity implements RadioGroup.OnCheckedChangeListener {

    private TextView txtTop;
    private RadioGroup rgTab;
    private RadioButton rbHome, rbForum, rbcategory, rbprofile, rbDesign;
    private HomeFragment homeFragment;
    private DesignFragment designFragment;
    private ForumFragment forumFragment;
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
//        txtTop = (TextView) findViewById(R.id.txtTop);
        rgTab = (RadioGroup) findViewById(R.id.rgMenu);

        rbHome = (RadioButton) findViewById(R.id.rbHome);
        rbDesign = (RadioButton) findViewById(R.id.rbDesign);
        rbForum = (RadioButton) findViewById(R.id.rbForum);
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
//                txtTop.setText(getResources().getString(R.string.home));
                break;
            case R.id.rbDesign:
                if(designFragment == null){
                    designFragment = new DesignFragment();
                    ft.add(R.id.fragment_container, designFragment);
                }else{
                    ft.show(designFragment);
                }
//                txtTop.setText(getResources().getString(R.string.rank));
                break;
            case R.id.rbForum:
                if(forumFragment == null){
                    forumFragment = new ForumFragment();
                    ft.add(R.id.fragment_container, forumFragment);
                }else{
                    ft.show(forumFragment);
                }
//                txtTop.setText(getResources().getString(R.string.gift));
                break;
            case R.id.rbCategory:
                if(categoryFragment==null){
                    categoryFragment = new CategoryFragment();
                    ft.add(R.id.fragment_container,categoryFragment);
                }else{
                    ft.show(categoryFragment);
                }
//                txtTop.setText(getResources().getString(R.string.category));
                break;
            case R.id.rbProfile:
                if(profileFragment==null){
                    profileFragment = new ProfileFragment();
                    ft.add(R.id.fragment_container,profileFragment);
                }else{
                    ft.show(profileFragment);
                }
//                txtTop.setText(getResources().getString(R.string.profile));
                break;
        }
        ft.commit();

    }

    public void hideAllFragment(FragmentTransaction ft){
        if(homeFragment!=null){
            ft.hide(homeFragment);
        }
        if(designFragment !=null){
            ft.hide(designFragment);
        }
        if(forumFragment !=null){
            ft.hide(forumFragment);
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

    public void toCategory(){
        FragmentTransaction ft = manager.beginTransaction();
        hideAllFragment(ft);
        if(categoryFragment==null){
            categoryFragment = new CategoryFragment();
            ft.add(R.id.fragment_container,categoryFragment);
        }else{
            ft.show(categoryFragment);
        }
        ft.commit();
        rbcategory.setChecked(true);
    }
}
