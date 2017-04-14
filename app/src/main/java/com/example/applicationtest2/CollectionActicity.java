package com.example.applicationtest2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import Adapter.MainBodyAdapterCopy;
import bean.MainList;
import util.MyListener;
import view.PullToRefreshLayout;
import view.PullableListView;

public class CollectionActicity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener{


    private List<MainList> mainList;
    private PullableListView listView;
    private MainBodyAdapterCopy adapter;
    private PullToRefreshLayout collectionRefresh;
    private ImageButton back;

    public static void startAction(Context context){
        Intent intent = new Intent(context, CollectionActicity.class);
        context.startActivity(intent);
    }
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_collection_acticity);

       collectionRefresh = ((PullToRefreshLayout) findViewById(R.id.collectionRefresh));
       collectionRefresh.setOnRefreshListener(new MyListener());


       initListDate();

       listView = (PullableListView) findViewById(R.id.listCollection);
       adapter = new MainBodyAdapterCopy(CollectionActicity.this, R.layout.part_item_main_body,  mainList);
       listView.setAdapter(adapter);
       listView.setOnItemClickListener(this);

       back = (ImageButton) findViewById(R.id.collectionBack);
       back.setOnClickListener(this);

    }

    private void initListDate() {
        mainList = new ArrayList<>();
        MainList item = new MainList(R.mipmap.show1, "#内有福利#白色情人节如何“宠幸”男友?快戳进来！", "handsome, 今晚我有个礼物送你哦");
        mainList.add(item);
        item = new MainList(R.mipmap.show2, "想为爸妈尽些孝心，就送这些实用的东西♪(^∇^*)", "今晚我有个礼物送你哦");
        mainList.add(item);
        item = new MainList(R.mipmap.show3, "清凉夏日常被着它总没错，相信我你不会吃亏！", "清新自然的绿色环境，会给人充满力量的正能量。夏季天气炎热");
        mainList.add(item);
        item = new MainList(R.mipmap.show4, "就差你了！快进来", "handsome, 今晚我有个礼物送你哦");
        mainList.add(item);
        item = new MainList(R.mipmap.show5, "二次元老司机带队啦！快上车！！！", "最近喜欢上的姑娘是个B站老司机、日本百事通、次元中毒患者，害怕有二次元壁？");
        mainList.add(item);
        item = new MainList(R.mipmap.show6, "送给男票们千万不要错过，他会喜欢的", "你有一份礼物请查收！今晚我就是你的人了(✿◡‿◡)");
        mainList.add(item);
    }



    @Override
    public void onItemClick(AdapterView<?> parent,View view,int position,long id) {
//        ArticleActivity.startAction(this, modelList.get(position).getModelid(), modelList.get(position).getTitle(),modelList.get(position).getContent());
//        Toast.makeText(this, "hello world!!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.collectionBack:
                finish();
        }
    }
}
