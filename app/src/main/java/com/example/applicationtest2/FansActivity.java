package com.example.applicationtest2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import fragment.FansFragment;

public class FansActivity extends FragmentActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    private ImageButton back;
    private FansFragment fansFragment0, fansFragment1, fansFragment2;
    private int position;
    private RadioGroup rgStatus;

    public static void actionStart(Context context, int state){
        Intent intent = new Intent(context, FansActivity.class);
        intent.putExtra("state", state);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fans);

        bindView();
        initFragment();

    }

    private void bindView() {
        rgStatus = (RadioGroup) findViewById(R.id.rgStatus);
        back = (ImageButton) findViewById(R.id.fansBack);

        position = getIntent().getIntExtra("state", 0);
        ((RadioButton)rgStatus.getChildAt(position)).setChecked(true);
        rgStatus.setOnCheckedChangeListener(this);
        back.setOnClickListener(this);
    }

    private void initFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        hideAllFragment(ft);
        Bundle bundle = new Bundle();
        switch (position){
            case 0:
                if(fansFragment0==null) {
                    fansFragment0 = new FansFragment();
                    bundle.putInt("state",0);
                    fansFragment0.setArguments(bundle);
                    ft.add(R.id.fragment_container,fansFragment0);
                }else{
                    ft.show(fansFragment0);
                }
                break;
            case 1:
                if(fansFragment1==null) {
                    fansFragment1 = new FansFragment();
                    bundle.putInt("state",1);
                    fansFragment1.setArguments(bundle);
                    ft.add(R.id.fragment_container,fansFragment1);
                }else{
                    ft.show(fansFragment1);
                }
                break;
            case 2:
                if(fansFragment2==null) {
                    fansFragment2 = new FansFragment();
                    bundle.putInt("state",2);
                    fansFragment2.setArguments(bundle);
                    ft.add(R.id.fragment_container,fansFragment2);
                }else{
                    ft.show(fansFragment2);
                }
                break;
        }
        ft.commit();
    }

    private void hideAllFragment(FragmentTransaction ft){
        if(fansFragment0!=null){
            ft.hide(fansFragment0);
        }
        if(fansFragment1!=null){
            ft.hide(fansFragment1);
        }
        if(fansFragment2!=null){
            ft.hide(fansFragment2);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fansBack:
                finish();
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group,int checkedId) {
        position=checkedId;
        Toast.makeText(FansActivity.this,"checkid"+checkedId,Toast.LENGTH_SHORT).show();
        initFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }



}
