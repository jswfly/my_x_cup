package com.example.applicationtest2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.jude.rollviewpager.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import Adapter.MyFragPagerAdapter;
import bean.Response;
import bean.TabListItem;
import fragment.ArticleCommentFragment;
import fragment.ArticleMainFragment;
import retrofit2.Call;
import retrofit2.Callback;
import util.RetrofitUtil;
import view.ClearEditText;

import static com.makeramen.roundedimageview.RoundedDrawable.TAG;

public class ArticleActivity extends FragmentActivity implements View.OnClickListener, OnItemClickListener, ViewPager.OnPageChangeListener {

    private RadioGroup rgArticle;
    private ViewPager vpArticleViewPager;
    private List<Fragment> listFragment;
    private MyFragPagerAdapter fAdapter;
    private ImageView tabLine;
    private int[] imgWidth = new int[10];
    private int screenWidth;
    private ClearEditText myComment;
    private CheckBox toComment;
    private Button sendMsg;
    private Bundle bundle;
    /**
     * len代表hsvHomeTab滚动的位置
     * mCurrentPageIndex当前的page位置
     */
    private int mCurrentPageIndex;


    public static void startAction(Context context, int _articleId, String _title, String _content){
        Bundle _bundle = new Bundle();
        Intent intent = new Intent(context, ArticleActivity.class);
        _bundle.putInt("articleId", _articleId);
        _bundle.putString("title", _title);
        _bundle.putString("content", _content);
        intent.putExtras(_bundle);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_article);
        bundle = getIntent().getExtras();
        initView();


    }



    private void initView() {
        ImageButton btnBack = (ImageButton) findViewById(R.id.articleBack);
        btnBack.setOnClickListener(this);

        rgArticle = (RadioGroup) findViewById(R.id.rgArticle);

        initPage();
        initRadioGroup();
        initLine();

        rgArticle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group,int checkedId) {
                vpArticleViewPager.setCurrentItem(checkedId);
            }
        });
        rgArticle.check(0);


        toComment = (CheckBox) findViewById(R.id.toComment);
        Drawable drawable=getResources().getDrawable(R.mipmap.icon_sign);
        drawable.setBounds(0,0,40,40);
        toComment.setCompoundDrawables(drawable,null,null,null);
        toComment.setOnClickListener(this);

        sendMsg = (Button) findViewById(R.id.sendMsg);
        myComment = (ClearEditText) findViewById(R.id.myComment);
        sendMsg.setOnClickListener(this);
    }


    private void initPage() {
        vpArticleViewPager = (ViewPager) findViewById(R.id.vpArticlePager);
        listFragment = new ArrayList<Fragment>();

        /**
         * 创建ArticleMainFragment页和ArticleCommentFragment页
         */
        ArticleMainFragment articelMainFragment = new ArticleMainFragment();
        ArticleCommentFragment articleCommentFragment = new ArticleCommentFragment();
        articelMainFragment.setArguments(bundle);
        articleCommentFragment.setArguments(bundle);
        listFragment.add(articelMainFragment);
        listFragment.add(articleCommentFragment);

        fAdapter = new MyFragPagerAdapter(getSupportFragmentManager(),listFragment);

        vpArticleViewPager.setAdapter(fAdapter);

        vpArticleViewPager.addOnPageChangeListener(this);
    }

    private void initRadioGroup() {

        List<TabListItem> tabList;tabList = new ArrayList<TabListItem>();
        tabList.add(new TabListItem("内容",0));
        tabList.add(new TabListItem("评论",1));
        for(int i=0;i<tabList.size();i++){
            RadioButton rb=(RadioButton) LayoutInflater.from(this).
                    inflate(R.layout.radiobutton_tab_item, null);
            //设置rb的id
            rb.setId(i);
            //获取对应id的tab名字
            String ss = tabList.get(i).getItemName() ;
            rb.setText(ss);
            rb.setTextSize(16);
            //把rb添加到rgHome中
            RadioGroup.LayoutParams params=new
                    RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.MATCH_PARENT);
            rgArticle.addView(rb, params);
        }
    }

    /**
     * 获取下划线的长度
     */
    private void initLine() {
        tabLine = (ImageView) findViewById(R.id.imgLine);
//        Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        display.getMetrics(outMetrics);
//        mScreet = outMetrics.widthPixels / 4;
        /**
         * 获取控件控件的宽度，因为在create中不能获取，所以要监听
         */

        final int size = rgArticle.getChildCount();

        if(size>=0) {
            final RadioButton rb = (RadioButton) rgArticle.getChildAt(0);
            ViewTreeObserver vto2 = rb.getViewTreeObserver();
            vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    for (int i = 0; i <size ; i++) {

                        RadioButton rb1 = (RadioButton) rgArticle.getChildAt(i);
                        imgWidth[i] = rb1.getMeasuredWidth();
//                        Log.d(TAG, "initLine: "+imgWidth[i]);
                    }
                    ViewGroup.LayoutParams lp = tabLine.getLayoutParams();
                    lp.width = imgWidth[0];
                    tabLine.setLayoutParams(lp);

                    screenWidth = getResources().getDisplayMetrics().widthPixels ;
                    rb.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.articleBack:
                finish();
                break;
            case R.id.toComment:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(!toComment.isChecked()){
                    showInput();
                }else {
                    hideInput();
                }
                break;
            case R.id.sendMsg:
                SharedPreferences pref = this.getSharedPreferences("userInfo", MODE_PRIVATE);
                int userId = pref.getInt("userId", -1);
                if(!pref.getBoolean("hasLogin",false)){
                    Toast.makeText(this, "尚未登陆，不能发表评论", Toast.LENGTH_SHORT).show();
                    break;
                }
                if(userId==-1){
                    Toast.makeText(this, "本地用户id读取错误", Toast.LENGTH_SHORT).show();
                    break;
                }
                Call<Response<String>> call = RetrofitUtil.service.insertComment(userId, bundle.getInt("articleId"), myComment.getText().toString());
                call.enqueue(new Callback<Response<String>>() {
                    @Override
                    public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                        if(!response.body().isStatus()){
                            Toast.makeText(ArticleActivity.this, "发布失败", Toast.LENGTH_SHORT).show();
                            return ;
                        }
                        Toast.makeText(ArticleActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                        hideInput();
                    }

                    @Override
                    public void onFailure(Call<Response<String>> call, Throwable t) {
                        Toast.makeText(ArticleActivity.this, "发布失败"+t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }
                });
        }
    }

    @Override
    public void onBackPressed() {
        if(toComment.isChecked()) {
            hideInput();
        }else{
            finish();
        }
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this,"Item " + position + " clicked",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    @Override
    public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels) {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tabLine.getLayoutParams();
        if(mCurrentPageIndex == position) {
            RadioButton rb=(RadioButton)rgArticle.getChildAt(position);
            lp.leftMargin = (int) (positionOffset * imgWidth[position] +rb.getLeft()+rgArticle.getLeft());
        } else{
            RadioButton rb=(RadioButton)rgArticle.getChildAt(position+1);
            lp.leftMargin = (int)((positionOffset-1)*imgWidth[position] + rb.getLeft()+rgArticle.getLeft());
        }

        lp.width = (int) (imgWidth[position]*(1-positionOffset)+imgWidth[position+1]*positionOffset);
        tabLine.setLayoutParams(lp);
    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPageIndex = position;
        RadioButton rb=(RadioButton)rgArticle.getChildAt(position);
        rb.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    public void sendChangePage(int position){
        vpArticleViewPager.setCurrentItem(position);
    }

    private void showInput(){
        myComment.setVisibility(View.VISIBLE);
        sendMsg.setVisibility(View.VISIBLE);
        myComment.findFocus();
        //强制显示键盘
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(myComment,InputMethodManager.SHOW_FORCED);
        toComment.setText("取  消");
    }

    private void  hideInput(){
        myComment.clearFocus();
        myComment.setVisibility(View.GONE);
        sendMsg.setVisibility(View.GONE);
        toComment.setText("评一下");
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(myComment.getWindowToken(), 0); //强制隐藏键盘
    }
}
