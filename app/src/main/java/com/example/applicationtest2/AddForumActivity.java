package com.example.applicationtest2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.lidong.photopicker.PhotoPickerActivity;
import com.lidong.photopicker.PhotoPreviewActivity;
import com.lidong.photopicker.SelectModel;
import com.lidong.photopicker.intent.PhotoPickerIntent;
import com.lidong.photopicker.intent.PhotoPreviewIntent;

import org.json.JSONArray;

import java.util.ArrayList;

import Adapter.AddForumImageGridAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import util.UpLoadImageUtil;

public class AddForumActivity extends Activity {


    private static final int REQUEST_CAMERA_CODE = 10;
    private static final int REQUEST_PREVIEW_CODE = 20;
    @BindView(R.id.back)
    ImageButton back;
    @BindView(R.id.publishForum)
    Button publishForum;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.txtLabel)
    LinearLayout txtLabel;
    @BindView(R.id.gvImages)
    GridView gvImages;
    @BindView(R.id.topic_main_layout)
    LinearLayout topicMainLayout;

    private AddForumImageGridAdapter gridAdapter;

    private ArrayList<String> imagePaths = new ArrayList<>();
//    private ImageCaptureManager captureManager; // 相机拍照处理类


    public static void startAction(Context context){
        Bundle _bundle = new Bundle();
        Intent intent = new Intent(context, AddForumActivity.class);
        intent.putExtras(_bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_forum);
        ButterKnife.bind(this);

        int cols = getResources().getDisplayMetrics().widthPixels / getResources().getDisplayMetrics().densityDpi;
        cols = cols < 3 ? 3 : cols;
        gvImages.setNumColumns(cols);

        // preview
        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String imgs = (String) parent.getItemAtPosition(position);
                if (imagePaths.contains("add")) {
                    imagePaths.remove("add");
                }
                if ("add".equals(imgs)) {

                    PhotoPickerIntent intent = new PhotoPickerIntent(AddForumActivity.this);
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(true); // 是否显示拍照
                    intent.setMaxTotal(6); // 最多选择照片数量，默认为6
                    intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
                    startActivityForResult(intent, REQUEST_CAMERA_CODE);
                } else {
                    //预览照片
                    PhotoPreviewIntent intent = new PhotoPreviewIntent(AddForumActivity.this);
                    intent.setCurrentItem(position);
                    intent.setPhotoPaths(imagePaths);
                    startActivityForResult(intent, REQUEST_PREVIEW_CODE);
                }
            }
        });
        //添加gridview的add图片
        imagePaths.add("add");
        gridAdapter = new AddForumImageGridAdapter(this, imagePaths);
        gvImages.setAdapter(gridAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    ArrayList<String> list = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
//                    Log.d(TAG, "list: " + "list = [" + list.size());
                    loadAdpater(list);
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    ArrayList<String> ListExtra = data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT);
//                    Log.d(TAG, "ListExtra: " + "ListExtra = [" + ListExtra.size());
                    loadAdpater(ListExtra);
                    break;
            }
        }
    }

    private void loadAdpater(ArrayList<String> paths) {
        if (imagePaths != null && imagePaths.size() > 0) {
            imagePaths.clear();
        }
        if (paths.contains("add")) {
            paths.remove("add");
        }
        paths.add("add");
        imagePaths.addAll(paths);

        //改变gridiew的高度
        LinearLayout.LayoutParams lp;
        if (imagePaths.size() > 3) {
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, gvImages.getChildAt(0).getMeasuredHeight() * 2);
        } else {
            lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        gvImages.setLayoutParams(lp);
        gridAdapter = new AddForumImageGridAdapter(this, imagePaths);
        gvImages.setAdapter(gridAdapter);

        try {
            JSONArray obj = new JSONArray(imagePaths);
            Log.e("--", obj.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.back, R.id.publishForum})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.publishForum:
                if(TextUtils.isEmpty(content.getText())){
                    Toast.makeText(this, "内容不能为空！！！", Toast.LENGTH_SHORT).show();
                    return ;
                }
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        UpLoadImageUtil.uploadMany(AddForumActivity.this, imagePaths, content.getText().toString().trim());
                    }
                }.start();
                break;
        }
    }
}
