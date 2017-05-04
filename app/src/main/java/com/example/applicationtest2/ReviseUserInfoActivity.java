package com.example.applicationtest2;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import view.ClearEditText;
import widget.PhotoPopupWindow;

public class ReviseUserInfoActivity extends Activity implements PhotoPopupWindow.OnDialogListener{

    @BindView(R.id.btnBack)
    Button btnBack;
    @BindView(R.id.editUserName)
    ClearEditText editUserName;
    @BindView(R.id.editTel)
    ClearEditText editTel;
    @BindView(R.id.editMotto)
    ClearEditText editMotto;
    @BindView(R.id.btnOK)
    Button btnOK;
    @BindView(R.id.headImg)
    RoundedImageView headImg;

    private PopupWindow mPopupWindow;
    private Uri photoUri;

    /***
     * 使用照相机拍照获取图片
     */
    public static final int SELECT_PIC_BY_CAMERA = 1;
    /***
     * 使用相册中的图片
     */
    public static final int SELECT_PIC_BY_LOCAL_PHOTO = 2;

    private static final int CUT_PHOTO = 3;

    public static void startAction(Context context){
        Bundle _bundle = new Bundle();
        Intent intent = new Intent(context, ReviseUserInfoActivity.class);
        intent.putExtras(_bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revise_user_info);
        ButterKnife.bind(this);

        SharedPreferences pref = this.getSharedPreferences("userInfo", MODE_PRIVATE);
        editUserName.setHint(pref.getString("username", "x_cup"));
        editMotto.setHint(pref.getString("motto", "this is a motto"));
        editTel.setHint(pref.getString("telephone", "17826870000"));
        Glide.with(this)
                .load(pref.getString("headImgUrl", ""))
                .diskCacheStrategy(DiskCacheStrategy.ALL)   //缓存所有版本的图像（默认行为）
                .placeholder(R.mipmap.image_holder_default)          //等待时显示的图片
                .error(R.mipmap.image_holder_default)                //错误时显示的图片
                .crossFade()                                //交叉渐入渐出
                .into(headImg);

        mPopupWindow = new PhotoPopupWindow(this, this);

    }

    @OnClick({R.id.btnBack, R.id.btnOK, R.id.headImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                finish();
                break;
            case R.id.btnOK:
                finish();
                break;
            case R.id.headImg:
                mPopupWindow.showAtLocation(headImg, Gravity.BOTTOM, 0, 0);
                break;
        }
    }


    @Override
    public void onCamera() {
// 执行拍照前，应该先判断SD卡是否存在
        String SDState = Environment.getExternalStorageState();
        if (!SDState.equals(Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "内存卡不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            photoUri = getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    new ContentValues());
            if (photoUri != null) {
                Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                i.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(i, SELECT_PIC_BY_CAMERA);

            } else {
                Toast.makeText(this, "发生意外，无法写入相册", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(this, "发生意外，无法写入相册", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocalPhoto() {
        // 从相册中取图片
        Intent choosePictureIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(choosePictureIntent, SELECT_PIC_BY_LOCAL_PHOTO);
    }

    @Override
    public void onCancel() {

    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_PIC_BY_CAMERA:
                    // 选择自拍结果
                    beginCrop(photoUri);
                    break;
                case SELECT_PIC_BY_LOCAL_PHOTO:
                    // 选择图库图片结果
                    beginCrop(intent.getData());
                    break;
                case CUT_PHOTO:
                    handleCrop(intent);
                    break;
            }

        }
    }

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    public void beginCrop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高，注意如果return-data=true情况下,其实得到的是缩略图，并不是真实拍摄的图片大小，
        // 而原因是拍照的图片太大，所以这个宽高当你设置很大的时候发现并不起作用，就是因为返回的原图是缩略图，但是作为头像还是够清晰了
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        //返回图片数据
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CUT_PHOTO);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param result
     */
    private void handleCrop(Intent result) {
        Bundle extras = result.getExtras();
        if (extras != null) {
            try{
                Bitmap bitmap = (Bitmap) extras.get("data");
                headImg.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
