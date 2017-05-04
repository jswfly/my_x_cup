package com.example.applicationtest2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import Adapter.MainBodyAdapter;
import bean.ModelList;
import bean.Response;
import bean.User;
import retrofit2.Call;
import retrofit2.Callback;
import util.ImageLoaderUtil;
import util.RetrofitUtil;

import static com.makeramen.roundedimageview.RoundedDrawable.TAG;

public class AuthorActivity extends Activity implements AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {

    private List<ModelList> modelList;
    private ListView listWorks;
    private ImageView imgBack;
    private RelativeLayout relTitle;
    private TextView authorName;
    private ImageButton back;
    private Bundle bundle;
    private MainBodyAdapter adapter;
    private CheckBox like;
    private int userId = -1;
    private TextView txtWorks, txtFans;
    public AuthorActivity() {
    }

    public static void actionStart(Context context, int _customerId, String _customerName, String _motto, String _headImg){
        Intent intent = new Intent(context, AuthorActivity.class);
        Bundle _bundle = new Bundle();
        _bundle.putInt("customerId", _customerId);
        _bundle.putString("customerName", _customerName);
        _bundle.putString("motto", _motto);
        _bundle.putString("headImg", _headImg);
        intent.putExtras(_bundle);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_author);
        bundle = this.getIntent().getExtras();

        SharedPreferences pref = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        if(pref.getBoolean("hasLogin", false)){
            userId = pref.getInt("userId", -1);
        }


//        Toast.makeText(this, bundle.getString("author")+bundle.getString("motto"), Toast.LENGTH_SHORT).show();


        modelList = new ArrayList<>();

        listWorks = (ListView) findViewById(R.id.listAuthorWorks);
        adapter = new MainBodyAdapter(AuthorActivity.this, R.layout.part_item_main_body, modelList);

        View viewHeader = LayoutInflater.from(AuthorActivity.this).inflate(R.layout.header_author_info, null);

        //添加头部

        listWorks.addHeaderView(viewHeader);
        listWorks.setAdapter(adapter);
        initHeader();

        listWorks.setOnItemClickListener(this);
        //添加标题动画效果
        initTitle();
        back = (ImageButton) findViewById(R.id.authorBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        like.setOnCheckedChangeListener(this);

        initListDate();
    }

    private void initTitle() {
        imgBack = (ImageView) findViewById(R.id.authorBack);
        relTitle = (RelativeLayout) findViewById(R.id.RelTitle);
        authorName = (TextView) findViewById(R.id.authorName);
        like = (CheckBox) findViewById(R.id.like);

        relTitle.getBackground().setAlpha(0);
        authorName.setTextColor(Color.argb(0,110,110,110));

        listWorks.setOnScrollListener(new AbsListView.OnScrollListener() {

            private int alpha=0;
            private int lastpoint=0;
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState){

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                Log.d("555555", firstVisibleItem+","+visibleItemCount+","+totalItemCount);
                if(firstVisibleItem==0) {
                    //获取位置信息，!null判断用来防止第一次未实例化（防止程序崩溃）
                    View v1 = listWorks.getChildAt(0);
                    int scrollTop = (v1 == null) ? 0 : -v1.getTop();


                    if(scrollTop>150 && alpha<=245 && scrollTop>lastpoint){//向下滑动
                        relTitle.getBackground().setAlpha(alpha);
                        authorName.setTextColor(Color.argb(alpha,110,110,110));
                        alpha=alpha+10;
                    }else if(scrollTop<400 && alpha>=10 && lastpoint>scrollTop){//向上滑动
                        relTitle.getBackground().setAlpha(alpha);
                        authorName.setTextColor(Color.argb(alpha,110,110,110));
                        alpha=alpha-10;
                    }
                    //重置透明度
                    if(scrollTop>400){
                        alpha=255;
                        relTitle.getBackground().setAlpha(alpha);
                        authorName.setTextColor(Color.argb(alpha,110,110,110));
                    }else if(scrollTop<150){
                        alpha=0;
                        relTitle.getBackground().setAlpha(alpha);
                        authorName.setTextColor(Color.argb(alpha,110,110,110));
                    }
                    //设置返回键颜色
                    if(scrollTop>300) {
                        imgBack.setBackgroundResource(R.mipmap.back_black);
                    }else {
                        imgBack.setBackgroundResource(R.mipmap.back_pink);
                    }

                    lastpoint = scrollTop;
                }
            }
        });
    }

    private void initListDate() {
//        MainList item = new MainList(R.mipmap.show1, "#内有福利#白色情人节如何“宠幸”男友?快戳进来！", "handsome, 今晚我有个礼物送你哦");
//        mainlist.add(item);
//        item = new MainList(R.mipmap.show2, "想为爸妈尽些孝心，就送这些实用的东西♪(^∇^*)", "今晚我有个礼物送你哦");
//        mainlist.add(item);
//        item = new MainList(R.mipmap.show3, "清凉夏日常被着它总没错，相信我你不会吃亏！", "清新自然的绿色环境，会给人充满力量的正能量。夏季天气炎热");
//        mainlist.add(item);
//        item = new MainList(R.mipmap.show4, "就差你了！快进来", "handsome, 今晚我有个礼物送你哦");
//        mainlist.add(item);
//        item = new MainList(R.mipmap.show5, "二次元老司机带队啦！快上车！！！", "最近喜欢上的姑娘是个B站老司机、日本百事通、次元中毒患者，害怕有二次元壁？");
//        mainlist.add(item);
//        item = new MainList(R.mipmap.show6, "送给男票们千万不要错过，他会喜欢的", "你有一份礼物请查收！今晚我就是你的人了(✿◡‿◡)");
//        mainlist.add(item);
        txtWorks = (TextView) findViewById(R.id.txtAuthorWorks) ;
        txtFans = (TextView) findViewById(R.id.txtAuthorFans);

//        Log.d(TAG,"initListDate: "+bundle.getInt("customerId")+","+userId);

        Call<Response<User>> call = RetrofitUtil.service.getCustomMessage(userId, bundle.getInt("customerId", -1));
        call.enqueue(new Callback<Response<User>>() {
            @Override
            public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                if(!response.body().isStatus()){
                    return ;
                }
                final User data = response.body().getData();
                if(data.isFollowed()) {
                    like.setChecked(true);
//                    Log.d("qwjheisdjhfioh", "11111111111111");
                }
//                Log.d(TAG,"onResponse: "+data.getCustomerid()+data.getCustomername()+data.getModelcnt()+data.getFanscnt());
                txtWorks.setText(String.valueOf(data.getModelcnt()));
                txtFans.setText(String.valueOf(data.getFanscnt()));


                for (ModelList item: data.getModelsmessage()) {
                    ModelList modelItem = new ModelList(item.getModelid(), item.getTitle(), item.getContent(),item.getModelurl(),
                            item.getModelidimg());
                    modelList.add(item);
//                    Log.d(TAG,"onResponse: "+item.getModelidimg().get(0).getImgurl());
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Response<User>> call, Throwable t) {
                Toast.makeText(AuthorActivity.this, "访问失败"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private void initHeader() {
        String authorName = bundle.getString("author");
        String authorMotto = bundle.getString("motto");
        String headimg = bundle.getString("headImg");
//        Toast.makeText(this, authorMotto+authorName+String.valueOf(headimg), Toast.LENGTH_SHORT).show();

        TextView titleAuthorName = (TextView) findViewById(R.id.authorName);

        RoundedImageView imgAuthorHead = (RoundedImageView) findViewById(R.id.imgAuthorHead);
        ImageView imgBackgrounnd = (ImageView) findViewById(R.id.imgAuthorBackground);
        TextView txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        TextView txtAuthorMotto = (TextView) findViewById(R.id.txtAuthorMotto);

        titleAuthorName.setText(authorName);
        txtAuthor.setText(authorName);
        //添加头像
        DisplayImageOptions options = ImageLoaderUtil.buildOptions(R.mipmap.head_holder_default);
        ImageLoader.getInstance().displayImage(headimg,
                imgAuthorHead, options);
//        imgAuthorHead.setImageResource(headimg);
        txtAuthorMotto.setText(authorMotto);
//        txtWorks.setText(String.valueOf((int)(Math.random()*100)));
//        txtFans.setText(String.valueOf((int)(Math.random()*1000)));

        try {
//            Bitmap img1 = ImageLoader.getInstance().getMemoryCache().get(MemoryCacheUtils.generateKey(headimg, new ImageSize(200, 400)));
//            Bitmap img1 = BitmapFactory.decodeResource(getResources(), headimg);
//            Bitmap img1 = ImageLoader.getInstance().getMemoryCache().get(headimg);
//            //缩放并显示
//            Bitmap newImg = BlurUtil.doBlur(img1, 2, 2);
//            img1.recycle();
//            imgBackgrounnd.setImageBitmap(newImg);
            //添加头像模糊效果

            ImageLoader.getInstance().displayImage(headimg,
                    imgBackgrounnd, options);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("error:", "wrong");
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(AuthorActivity.this, "position", Toast.LENGTH_SHORT).show();
        if(position!=0) {
            ModelList modelItem = this.modelList.get(position);
            ArticleActivity.startAction(this, modelItem.getModelid(), modelItem.getTitle(), modelItem.getContent());
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView,final boolean isChecked) {
        switch (buttonView.getId()){
            case R.id.like:
                int method = 0;
                if(isChecked){
                    method = 1;
                }else{
                    method = 0;
                }
                Call<Response<String>> call = RetrofitUtil.service.insertLike(bundle.getInt("customerId"), userId, method);
                call.enqueue(new Callback<Response<String>>() {
                    @Override
                    public void onResponse(Call<Response<String>> call, retrofit2.Response<Response<String>> response) {
                        if(!response.body().isStatus()){
                            Toast.makeText(AuthorActivity.this, "关注修改失败", Toast.LENGTH_SHORT).show();
                            return ;
                        }
                        if(isChecked){
                            Toast.makeText(AuthorActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AuthorActivity.this, "关注取消成功", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Response<String>> call, Throwable t) {
                        Toast.makeText(AuthorActivity.this, "访问失败"+t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }
                });
                break;
        }
    }
}
