package fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.applicationtest2.R;
import com.example.applicationtest2.SearchActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class HomeFragment extends Fragment /*implements ViewPager.OnPageChangeListener*/ {

    private static final String TAG = "HomeFragment";
    private Context context;
    private View view;
    private Unbinder unbinder;

    @BindView(R.id.fragmentTitle)
    TextView fragmentTitle;
    @BindView(R.id.containPage)
    FrameLayout containPage;
    @BindView(R.id.imgBtnSearch)
    ImageButton imgBtnSearch;

//    private HorizontalScrollView hsvHomeTab;
//    private RadioGroup rgHome;
//    private ViewPager vpHomeViewPager;
//    private MyFragPagerAdapter fAdapter;
//    private List<Fragment> listFragment;
//    private List<TabListItem> tabList;
//    private int[] imgWidth = new int[20];
//    private ImageView tabLine;
//    private static int screenWidth;
//    private static final String TAG = "HomeFragment";
    /**
     * mCurrentPageIndex当前的page位置
     */
    private int mCurrentPageIndex;

    public HomeFragment() {
        // Required empty public constructor
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);
        unbinder = ButterKnife.bind(this, view);
//------------------------------------------
        HomePageFragment page = new HomePageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("type", "精选");
        bundle.putInt("page", 0);
        page.setArguments(bundle);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.containPage, page);
        ft.show(page);
        ft.commit();
        //----------------------------------------------------------------
//        initView();
        return view;
    }


    /**
     * 获取下划线的长度
     */
  /*  private void initLine() {
        tabLine = (ImageView) view.findViewById(R.id.imgLine);
//        Display display = getActivity().getWindow().getWindowManager().getDefaultDisplay();
//        DisplayMetrics outMetrics = new DisplayMetrics();
//        display.getMetrics(outMetrics);
//        mScreet = outMetrics.widthPixels / 4;
        *//**
     * 获取控件控件的宽度，因为在create中不能获取，所以要监听
     *//*

        final int size = rgHome.getChildCount();

        if(size>=0) {
            final RadioButton rb = (RadioButton) rgHome.getChildAt(0);
            ViewTreeObserver vto2 = rb.getViewTreeObserver();
            vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    for (int i = 0; i <size ; i++) {

                        RadioButton rb1 = (RadioButton) rgHome.getChildAt(i);
                        imgWidth[i] = rb1.getMeasuredWidth();
//                        Log.d(TAG, "initLine: "+imgWidth[i]);
                    }
                    ViewGroup.LayoutParams lp = tabLine.getLayoutParams();
                    lp.width = imgWidth[0];
                    tabLine.setLayoutParams(lp);

                    screenWidth = getActivity().getResources().getDisplayMetrics().widthPixels ;
                    rb.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
        }
    }

    private void initView() {
        hsvHomeTab = (HorizontalScrollView) view.findViewById(R.id.hsvHomeTab);
        rgHome = (RadioGroup) view.findViewById(R.id.rgHome);

        tabList = new ArrayList<TabListItem>();
        tabList.add(new TabListItem("精选",0));
//        tabList.add(new TabListItem("送女友",1));
//        tabList.add(new TabListItem("送基友",2));
//        tabList.add(new TabListItem("送长辈",3));
//        tabList.add(new TabListItem("精选",4));
//        tabList.add(new TabListItem("送女友",5));
//        tabList.add(new TabListItem("送基友",6));
//        tabList.add(new TabListItem("送长辈",7));
//        tabList.add(new TabListItem("精选",8));
//        tabList.add(new TabListItem("送女友",9));
//        tabList.add(new TabListItem("送基友",10));
//        tabList.add(new TabListItem("送长辈",11));
        initViewTab();
        initLine();
        initPage();


        rgHome.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                vpHomeViewPager.setCurrentItem(checkedId);
//                Toast.makeText(getActivity(), "checkeid"+checkedId, Toast.LENGTH_SHORT).show();
            }
        });
        rgHome.check(0);

    }

    private void initPage() {
        vpHomeViewPager = (ViewPager) view.findViewById(R.id.vpHomePager);
        listFragment = new ArrayList<Fragment>();
        for(int i=0;i<tabList.size();i++){
            HomePageFragment frag=new HomePageFragment();
            Bundle bundle=new Bundle();
            bundle.putString("type", tabList.get(i).getItemName());
            bundle.putInt("page", tabList.get(i).getPage()) ;
            frag.setArguments(bundle);
            listFragment.add(frag);
        }
        fAdapter = new MyFragPagerAdapter(getChildFragmentManager(), listFragment);

        vpHomeViewPager.setAdapter(fAdapter);

        vpHomeViewPager.addOnPageChangeListener(this);

    }

    *//**
     * tabList所指定的item转换成radionButtom，
     * 然后添加到rgHome
     *//*
    private void initViewTab() {
        for(int i=0;i<tabList.size();i++){
            RadioButton rb=(RadioButton)LayoutInflater.from(getContext()).
                    inflate(R.layout.radiobutton_tab_item, null);
            //设置rb的id
            rb.setId(i);
            //获取对应id的tab名字
            String ss = tabList.get(i).getItemName() ;
            rb.setText(ss);
//            rb.setTextSize(16);
            //把rb添加到rgHome中
            RadioGroup.LayoutParams params=new
                    RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT,
                    RadioGroup.LayoutParams.MATCH_PARENT);
            rgHome.addView(rb, params);
        }
    }


    *//**
     * 三个方法的执行顺序为：用手指拖动翻页时，最先执行一遍onPageScrollStateChanged（1），
     * 然后不断执行onPageScrolled，放手指的时候，直接立即执行一次onPageScrollStateChanged（2），
     * 然后立即执行一次onPageSelected，然后再不断执行onPageScrollStateChanged，最后执行一次onPageScrollStateChanged（0）。
     *//*
    @Override
    public void onPageScrolled(int position,float positionOffset,int positionOffsetPixels) {
        //                Log.d(TAG, "onPageScrolled: "+mCurrentPageIndex+","+position+","+positionOffset+","+positionOffsetPixels);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) tabLine.getLayoutParams();
        if(mCurrentPageIndex == position) {
            RadioButton rb=(RadioButton)rgHome.getChildAt(position);
            lp.leftMargin = (int) (positionOffset * imgWidth[position] +rb.getLeft());
        } else{
            RadioButton rb=(RadioButton)rgHome.getChildAt(position+1);
            lp.leftMargin = (int)((positionOffset-1)*imgWidth[position] + rb.getLeft());
        }

        lp.width = (int) (imgWidth[position]*(1-positionOffset)+imgWidth[position+1]*positionOffset);
        tabLine.setLayoutParams(lp);
    }

    @Override
    public void onPageSelected(int position) {
        */

    /**
     * 滑动ViewPager时调整ScroollView的位置以便显示按钮
     *//*
        mCurrentPageIndex = position;
        RadioButton rb=(RadioButton)rgHome.getChildAt(position);
        rb.setChecked(true);
        //DisplayMetrics metrics=new DisplayMetrics();
        //getActivity().getWindow().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        //int screenWidth=metrics.widthPixels;
        int len=rb.getLeft()+imgWidth[position]/2-screenWidth/2;
//                Log.d(TAG, "len1: "+len);
//                if(len<0)
//                    len = 0;
//                else if(len>rgHome.getRight()-screenWidth) {
//                    len = rgHome.getRight()-screenWidth;
//                }

//                Log.d(TAG, "len2: "+len+","+rgHome.getRight()+","+scrollLen);
        hsvHomeTab.smoothScrollTo(len, 0);//滑动ScroollView
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }*/


    @OnClick(R.id.imgBtnSearch)
    public void onViewClicked() {
        Intent intent = new Intent(getActivity(), SearchActivity.class);
        startActivity(intent);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
