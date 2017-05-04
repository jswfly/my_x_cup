package com.example.applicationtest2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import view.TouchImageView;

public class ShowImageActivity extends Activity {

    private static final String TAG = "ShowImageActivity";
    @BindView(R.id.showImageBigger)
    ViewPager mViewPager;
    @BindView(R.id.showImageNumerator)
    TextView numerator;
    @BindView(R.id.showImageDenominator)
    TextView denominator;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.activity_show_image)
    RelativeLayout activityShowImage;
    @BindView(R.id.btnExport)
    ImageButton btnExport;
    private ArrayList<Integer> mImgs = new ArrayList<>();
//    private ImageView[] mImageViews = new ImageView[mImgs.length];


    public static void startAction(Context context, View view, boolean _canExport, ArrayList<Integer> _drawables) {
        Bundle _bundle = new Bundle();
        Intent intent = new Intent(context, ShowImageActivity.class);
        _bundle.putBoolean("canExport", _canExport);
        _bundle.putIntegerArrayList("drawables", _drawables);
        intent.putExtras(_bundle);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view,
                0, 0, //拉伸开始的坐标
                view.getMeasuredWidth(), view.getMeasuredHeight());//初始的宽高
        context.startActivity(intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_image);
        ButterKnife.bind(this);
        mImgs.addAll(getIntent().getExtras().getIntegerArrayList("drawables"));

        if(!getIntent().getExtras().getBoolean("canExport")){
            btnExport.setVisibility(View.GONE);
        }
        denominator.setText("/" + mImgs.size());
        mViewPager.setAdapter(new PagerAdapter() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                TouchImageView img = new TouchImageView(container.getContext());
                img.setImageResource(mImgs.get(position));
//                img.setTransitionName("transition_show");
                container.addView(img, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                return img;
            }

            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView((View) object);
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return mImgs.size();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                numerator.setText(String.valueOf(i + 1));
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }
}
