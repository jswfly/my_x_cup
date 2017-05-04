package com.example.applicationtest2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import Adapter.CustomServiceAdapter;
import bean.Msg;
import view.ClearEditText;

public class CustomServiceActivity extends Activity implements View.OnClickListener{

    private ListView listView;
    private List<Msg> msgList;
    private CustomServiceAdapter adapter;
    private ImageButton back;
    private Button send;
    private ClearEditText content;

    public static void startAction(Context context){
        Intent intent = new Intent(context, CustomServiceActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_custom_service);

        listView = (ListView) findViewById(R.id.serviceList);
        msgList = new ArrayList<>();
        adapter = new CustomServiceAdapter(CustomServiceActivity.this, R.layout.item_custom_service, msgList);
        listView.setAdapter(adapter);

        back = (ImageButton) findViewById(R.id.serviceBack);
        send = (Button) findViewById(R.id.sendMsg);
        content = (ClearEditText) findViewById(R.id.myContent);
        back.setOnClickListener(this);
        send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.serviceBack:
                finish();
                break;
            case R.id.sendMsg:
                if(!TextUtils.isEmpty(content.getText())) {
                    Msg msg = new Msg(content.getText().toString(),Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyDataSetChanged();
                    listView.setSelection(msgList.size());
                    content.setText("");

                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                        try {
                            Thread.sleep(2000);
                            Msg msg = new Msg("客服系统正在建设中！", Msg.TYPE_RECEIVED);
                            msgList.add(msg);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                    listView.setSelection(msgList.size());
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        }
                    }).start();
                }
                break;
        }
    }
}
