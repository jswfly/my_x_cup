package com.example.applicationtest2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ForumDetailActivity extends AppCompatActivity {


    private static final String TAG = "ForumDetailActivity";
    @BindView(R.id.imageShow)
    ImageView imageShow;
    @BindView(R.id.txtTitle)
    TextView txtTitle;
    @BindView(R.id.txtTime)
    TextView txtTime;
    @BindView(R.id.headImage)
    RoundedImageView headImage;
    @BindView(R.id.txtUsername)
    TextView txtUsername;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.txtContent)
    TextView txtContent;
    @BindView(R.id.faBtnShare)
    FloatingActionButton faBtnShare;
    @BindView(R.id.faBtnShareCollection)
    FloatingActionButton faBtnShareCollection;
    @BindView(R.id.faBtnComment)
    FloatingActionButton faBtnComment;


    public static void startAction(Context context, int _headImg, String _username, String _title, String _time, String _content, ArrayList<Integer> _drawables){
        Bundle _bundle = new Bundle();
        Intent intent = new Intent(context, ForumDetailActivity.class);
        Log.d(TAG, "startAction: "+_headImg);
        _bundle.putInt("headImg", _headImg);
        _bundle.putString("username", _username);
        _bundle.putString("title", _title);
        _bundle.putString("time", _time);
        _bundle.putString("content", _content);
        _bundle.putIntegerArrayList("drawables", _drawables);
        intent.putExtras(_bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum_detail);
        ButterKnife.bind(this);
//        toolbar.setSubtitle("This is Title");
//        toolbar.setNavigationIcon(R.mipmap.back_red);
//        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Bundle bundle = getIntent().getExtras();
        headImage.setImageResource(bundle.getInt("headImg", R.mipmap.head1));
        imageShow.setImageResource(bundle.getIntegerArrayList("drawables").get(0));
        txtUsername.setText(bundle.getString("username"));
        txtTitle.setText(bundle.getString("title"));
        txtContent.setText(bundle.getString("content"));
        txtTime.setText(bundle.getString("time"));
    }

    @OnClick({R.id.imageShow, R.id.headImage, R.id.faBtnShare, R.id.faBtnShareCollection, R.id.faBtnComment})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageShow:
                ShowImageActivity.startAction(this, imageShow, false, getIntent().getExtras().getIntegerArrayList("drawables"));
                break;
            case R.id.headImage:
                Toast.makeText(this, "头像Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.faBtnShare:
                Toast.makeText(this, "分享Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.faBtnShareCollection:
                Toast.makeText(this, "收藏Clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.faBtnComment:
                Toast.makeText(this, "评论Clicked", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
