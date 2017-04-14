package fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicationtest2.ArticleActivity;
import com.example.applicationtest2.AuthorActivity;
import com.example.applicationtest2.R;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import Adapter.MainListAdapter;
import bean.ModelList;
import bean.ResponseList;
import retrofit2.Call;
import retrofit2.Callback;
import util.RetrofitUtil;
import view.PullToRefreshLayout;
import view.PullableListView;

import static com.makeramen.roundedimageview.RoundedDrawable.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends Fragment implements MainListAdapter.Callback, PullToRefreshLayout.OnRefreshListener {

    //    private TextView txt;
    private String type;
    private int page = 0;
    private List<ModelList> modelList;
    private PullableListView listview;
    private MainListAdapter adapter;
    private int offset = 0;

    public HomePageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);
//        txt = (TextView) view.findViewById(R.id.fpTxt);
//        txt.setText(type+String.valueOf(page));

        modelList = new ArrayList<>();
        initListDate();

        adapter = new MainListAdapter(getActivity(), R.layout.item_list_main_total, modelList, this);
        listview = (PullableListView) view.findViewById(R.id.listMain);

        //设置空的刷新5000ms
        PullToRefreshLayout mainFresh = ((PullToRefreshLayout) view.findViewById(R.id.mainFresh));
        mainFresh.setOnRefreshListener(this);


        if(page==0){
            initRollViewHeader(listview);

            LinearLayout header = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.header_home, null, false);
            listview.addHeaderView(header);

            for (int i=0 ; i<header.getChildCount();i++) {
                final TextView txt = (TextView) header.getChildAt(i);
                txt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(), txt.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        listview.setAdapter(adapter);
        return view;
    }

//    private void initRollViewPager(View view, PullableListView listview) {
//      //  RollPagerView rollViewPager = (RollPagerView) inflater.from(getContext()).inflate(R.layout.header_rollviewpager,null, false);
//
//    }

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
        type = bundle.getString("type");
        page = bundle.getInt("page");
    }

    private void initListDate() {
//        MainList item = new MainList("小礼君", "资深买买买达人", R.mipmap.head1,
//                R.mipmap.show1, "#内有福利#白色情人节如何“宠幸”男友?快戳进来！", "handsome, 今晚我有个礼物送你哦");
//        mainlist.add(item);
//        item = new MainList("叫我小公举", "居家小物收集爱好者", R.mipmap.head2,
//                R.mipmap.show2, "想为爸妈尽些孝心，就送这些实用的东西♪(^∇^*)", "今晚我有个礼物送你哦");
//        mainlist.add(item);
//        item = new MainList("上上签", "迷信生活的叛逆者", R.mipmap.head3,
//                R.mipmap.show3, "清凉夏日常被着它总没错，相信我你不会吃亏！", "清新自然的绿色环境，会给人充满力量的正能量。夏季天气炎热");
//        mainlist.add(item);
//        item = new MainList("朵朵酱", "不为无聊之事，何以遣有涯之生", R.mipmap.head4,
//                R.mipmap.show4, "就差你了！快进来", "handsome, 今晚我有个礼物送你哦");
//        mainlist.add(item);
//        item = new MainList("淘局长", "礼物界的老司机", R.mipmap.head5,
//                R.mipmap.show5, "二次元老司机带队啦！快上车！！！", "最近喜欢上的姑娘是个B站老司机、日本百事通、次元中毒患者，害怕有二次元壁？");
//        mainlist.add(item);
//        item = new MainList("爱因钱烧", "带你跟风带你飞的无洒水烧钱君", R.mipmap.head6,
//                R.mipmap.show6, "送给男票们千万不要错过，他会喜欢的", "你有一份礼物请查收！今晚我就是你的人了(✿◡‿◡)");
//        mainlist.add(item);
        Call<ResponseList<ModelList>> homeListCall = RetrofitUtil.service.getHomeList(offset);
        homeListCall.enqueue(new Callback<ResponseList<ModelList>>() {
            @Override
            public void onResponse(Call<ResponseList<ModelList>> call, retrofit2.Response<ResponseList<ModelList>> response) {
                List<ModelList> getmainlist = response.body().getData();
                if(getmainlist.size()==0){
                    offset--;
                }
                for (ModelList item: getmainlist) {

                    ModelList modelItem = new ModelList(item.getModelid(), item.getOwnerid(), item.getTitle(), item.getContent(),item.getModelurl(),
                            item.getCreatedAt(), item.getUpdatedAt(), item.getModelidimg(), item.getOwnermessage(), item.getCollectionCnts());
                    modelList.add(item);
//                    Log.d(TAG,"onResponse: "+item.getModelidimg().get(0).getImgurl());

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseList<ModelList>> call, Throwable t) {
                Toast.makeText(getActivity(), "访问失败"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });


    }

    private void initRollViewHeader(PullableListView listview){
        View rview = LayoutInflater.from(getContext()).inflate(R.layout.header_rollviewpager, null, false);
        RollPagerView rollViewPager = (RollPagerView) rview.findViewById(R.id.rollView);
        rollViewPager.setHintView(new ColorPointHintView(getActivity(),Color.RED, getResources().getColor(R.color.gray2)));
        rollViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), "Item " + position + " clicked", Toast.LENGTH_SHORT).show();
            }
        });
        rollViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        LoopPagerAdapter headerAdapter = new LoopPagerAdapter(rollViewPager) {
            private int[] imgs = new int[]{
                    R.mipmap.img1,
                    R.mipmap.img2,
                    R.mipmap.img3,
                    R.mipmap.img4
            };

            @Override
            public View getView(ViewGroup container, int position) {
                ImageView view = new ImageView(getContext());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setImageResource(imgs[position]);
                return view;
            }

            @Override
            public int getRealCount() {
                return imgs.length;
            }
        };
        rollViewPager.setAdapter(headerAdapter);
        listview.addHeaderView(rview);
    }

    @Override
    public void insideClick(int position, View v) {
        Intent intent;

        switch (v.getId()){
            case R.id.linAuthor:
                Bundle bundle = new Bundle();
//                Toast.makeText(getActivity(), "author "+position+"clicked ", Toast.LENGTH_SHORT).show();
//                intent = new Intent(getActivity(), AuthorActivity.class);
//                bundle.putString("author", mainlist.get(position).getAuthor());
//                bundle.putString("motto", mainlist.get(position).getAuthorMotto());
//                bundle.putInt("headimg", mainlist.get(position).getImgHead());
//                intent.putExtras(bundle);
//                startActivity(intent);
                AuthorActivity.actionStart(getActivity(),modelList.get(position).getOwnermessage().getCustomerid(),
                        modelList.get(position).getOwnermessage().getCustomername(),
                        modelList.get(position).getOwnermessage().getMotto(),
                        modelList.get(position).getOwnermessage().getHeadimgurl());
                break;
            case R.id.linArticle:
//                Bundle bundle1 = new Bundle();
//                Toast.makeText(getActivity(), "article "+position+"clicked ", Toast.LENGTH_SHORT).show();
//                intent = new Intent(getActivity(), ArticleActivity.class);
//                bundle1.putString("article", mainlist.get(position).getTitle());
//                intent.putExtras(bundle1);
//                startActivity(intent);
                ArticleActivity.startAction(getContext(), modelList.get(position).getModelid(), modelList.get(position).getTitle(),
                                    modelList.get(position).getContent());
                break;
        }
    }

    @Override
    public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
        // 下拉刷新操作
        new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                // 千万别忘了告诉控件刷新完毕了哦！
                pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
            }
        }.sendEmptyMessageDelayed(0, 2000);
    }


    @Override
    public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
        offset++;
        Call<ResponseList<ModelList>> homeListCall = RetrofitUtil.service.getHomeList(offset);
        homeListCall.enqueue(new Callback<ResponseList<ModelList>>() {
            @Override
            public void onResponse(Call<ResponseList<ModelList>> call, retrofit2.Response<ResponseList<ModelList>> response) {
                List<ModelList> getmainlist = response.body().getData();
                if(getmainlist.size()==0){
                    offset--;
                }
                for (ModelList item: getmainlist) {

                    ModelList modelItem = new ModelList(item.getModelid(), item.getOwnerid(), item.getTitle(), item.getContent(),item.getModelurl(),
                            item.getCreatedAt(), item.getUpdatedAt(), item.getModelidimg(), item.getOwnermessage(), item.getCollectionCnts());
                    modelList.add(item);
//                    Log.d(TAG,"onResponse: "+item.getModelidimg().get(0).getImgurl());
                }
                adapter.notifyDataSetChanged();
                new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        // 千万别忘了告诉控件刷新完毕了哦！
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }

            @Override
            public void onFailure(Call<ResponseList<ModelList>> call, Throwable t) {
                Toast.makeText(getActivity(), "访问失败"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
                new Handler()
                {
                    @Override
                    public void handleMessage(Message msg)
                    {
                        // 千万别忘了告诉控件刷新完毕了哦！
                        pullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    }
                }.sendEmptyMessageDelayed(0, 1000);
            }
        });

    }
}
