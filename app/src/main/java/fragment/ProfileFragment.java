package fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicationtest2.CollectionActivity;
import com.example.applicationtest2.CustomServiceActivity;
import com.example.applicationtest2.FansActivity;
import com.example.applicationtest2.R;
import com.example.applicationtest2.ReviseUserInfoActivity;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import DB.CupDB;
import bean.Response;
import bean.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import util.ImageLoaderUtil;
import util.RetrofitUtil;
import view.ClearEditText;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "ProfileFragment";
    @BindView(R.id.reviseUserInfo)
    Button reviseUserInfo;
    Unbinder unbinder;
    private View view;
    private View loginView;
    private Button btnLR, btnLoginOff, collectin, customService;
    private ClearEditText username, password;
    private Context context;
    private TextView toRL, txtGzNumber, txtFansNumber;
    private LinearLayout linConcern, linFans, linVisitor;
    private TextView txtUserName;
    private String Telephone, Password;
    private RoundedImageView imgUserHead;
    //是否时登陆界面
    private boolean isLoginView = true;

    public ProfileFragment() {
        // Required empty public constructor
        context = getContext();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profile, container, false);

        bindProfile();
        bindLogin();
        SharedPreferences pref = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE);
//        Toast.makeText(getActivity(), pref.getString("username", "username"), Toast.LENGTH_SHORT).show();
        if (pref.getBoolean("hasLogin", false)) {
            loginView.setVisibility(View.GONE);
            Telephone = pref.getString("telephone", "");
            Password = pref.getString("password", "");
            initUserInfo();
        } else {
//            Toast.makeText(getActivity(), "本地登陆测试", Toast.LENGTH_SHORT).show();
            loginView.setVisibility(View.VISIBLE);

        }
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void bindProfile() {
        loginView = view.findViewById(R.id.loginView);
        btnLoginOff = (Button) view.findViewById(R.id.btnLoginOff);
        linConcern = (LinearLayout) view.findViewById(R.id.LinConcern);
        linFans = (LinearLayout) view.findViewById(R.id.LinFans);
        linVisitor = (LinearLayout) view.findViewById(R.id.LinVisitor);
        txtUserName = (TextView) view.findViewById(R.id.txtUsername_my);
        collectin = (Button) view.findViewById(R.id.collection);
        customService = (Button) view.findViewById(R.id.customService);
        txtGzNumber = (TextView) view.findViewById(R.id.txtGzNumber);
        txtFansNumber = (TextView) view.findViewById(R.id.txtFansNumber);
        imgUserHead = (RoundedImageView) view.findViewById(R.id.imgUserHead);


        linConcern.setOnClickListener(this);
        linFans.setOnClickListener(this);
        linVisitor.setOnClickListener(this);
        btnLoginOff.setOnClickListener(this);
        collectin.setOnClickListener(this);
        customService.setOnClickListener(this);
    }

    private void bindLogin() {
        username = (ClearEditText) view.findViewById(R.id.editUserName);
        password = (ClearEditText) view.findViewById(R.id.editPassword);
        toRL = (TextView) view.findViewById(R.id.to_register_login);
        btnLR = (Button) view.findViewById(R.id.btn_login_register);
        btnLR.setOnClickListener(this);
        toRL.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_login_register:
                if (TextUtils.isEmpty(username.getText().toString())) {
                    Toast.makeText(getActivity(), "用户名不能为空!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())) {
                    Toast.makeText(getActivity(), "密码不能为空!!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                CupDB db = CupDB.getInstance(getActivity());
                if (isLoginView) {
//                    if (db.CheckLogin(username.getText().toString(), password.getText().toString())) {
//                        Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
//                        SharedPreferences.Editor editor = getActivity().getSharedPreferences("userInfo",
//                                MODE_PRIVATE).edit() ;
//                        editor.putString("username", username.getText().toString()) ;
//                        editor.putBoolean("hasLogin", true) ;//用来确定是否登录
//                        editor.apply();
//                        loginView.setVisibility(View.GONE);
//                        txtUserName.setText(User);
//                    } else {
//                        Toast.makeText(getActivity(), "登陆失败", Toast.LENGTH_SHORT).show();
//                    }
                    Telephone = username.getText().toString();
                    Password = password.getText().toString();
                    initUserInfo();
                } else {
                    if (db.RegisterUser(username.getText().toString(), password.getText().toString())) {
                        btnLR.setText("登陆");
                        btnLR.setTextColor(Color.WHITE);
                        btnLR.setBackgroundColor(getResources().getColor(R.color.gray2));
                        toRL.setText("还没有账号？");
                        isLoginView = true;
                        Toast.makeText(getActivity(), "注册成功φ(゜▽゜*)♪", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "注册失败,用户名已存在/(ㄒoㄒ)/~~", Toast.LENGTH_SHORT).show();
                    }
                    /*AppService service = RetrofitUtil.getRetrofit().create(AppService.class);
                    Call<Response<User>> userCall = service.checkLoginRegister(username.getText().toString(), password.getText().toString(), username.getText().toString(), "register");

                    userCall.enqueue(new Callback<Response<User>>() {
                        @Override
                        public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                            int state = response.body().getStatus();
                            if(state==201){
                                btnLR.setText("登陆");
                                btnLR.setTextColor(Color.WHITE);
                                btnLR.setBackgroundColor(getResources().getColor(R.color.gray2));
                                toRL.setText("还没有账号？");
                                isLoginView = true;
                                Toast.makeText(getActivity(), "注册成功φ(゜▽゜*)♪", Toast.LENGTH_SHORT).show();
                            } else if(state==401){
                                Toast.makeText(getActivity(), "注册失败/(ㄒoㄒ)/~~", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Response<User>> call, Throwable t) {
                            Toast.makeText(getActivity(), "访问失败"+t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "访问失败 "+t.getMessage());
                        }
                    });*/
                }
                txtClean();
                break;
            case R.id.to_register_login:
                if (isLoginView) {
                    btnLR.setText("注册");
                    btnLR.setTextColor(Color.RED);
                    btnLR.setBackgroundColor(getResources().getColor(R.color.pink));
                    toRL.setText("已有账号，去登陆");
                    isLoginView = false;
                } else {
                    btnLR.setText("登陆");
                    btnLR.setTextColor(Color.WHITE);
                    btnLR.setBackgroundColor(getResources().getColor(R.color.gray2));
                    toRL.setText("还没有账号？");
                    isLoginView = true;
                }
                txtClean();
                break;
            case R.id.btnLoginOff:
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("userInfo", MODE_PRIVATE).edit();
                editor.putBoolean("hasLogin", false);
                editor.apply();
                isLoginView = true;
                loginView.setVisibility(View.VISIBLE);
                txtClean();
                break;
            case R.id.LinConcern:
                FansActivity.actionStart(getContext(), 0);
                break;
            case R.id.LinFans:
                FansActivity.actionStart(getContext(), 1);
                break;
            case R.id.LinVisitor:
                FansActivity.actionStart(getContext(), 2);
                break;
            case R.id.collection:
                CollectionActivity.startAction(getContext());
                break;
            case R.id.customService:
                CustomServiceActivity.startAction(getContext());
                break;
        }
    }

    private void txtClean() {
        Telephone = username.getText().toString();
        Password = password.getText().toString();
        username.setText("");
        password.setText("");
    }

    private void initUserInfo() {
        Call<Response<User>> userCall = RetrofitUtil.service.toLogin(Telephone, Password);

        userCall.enqueue(new Callback<Response<User>>() {
            @Override
            public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                User data = response.body().getData();
                boolean state = response.body().isStatus();
                if (state) {
//                    Toast.makeText(getActivity(), "登陆成功", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("userInfo",
                            MODE_PRIVATE).edit();
                    //用户id
                    editor.putInt("userId", data.getCustomerid());
                    //用户名
                    editor.putString("username", data.getCustomername());
                    //密码
                    editor.putString("password", Password);
                    //用来确定是否登录
                    editor.putBoolean("hasLogin", true);
                    //用户头像地址
                    editor.putString("headImgUrl", data.getHeadimgurl());
                    //用户motto
                    editor.putString("motto", data.getMotto());
                    //用户手机号
                    editor.putString("telephone", Telephone);
                    editor.apply();
                    loginView.setVisibility(View.GONE);
                    txtUserName.setText(data.getCustomername());
                    txtGzNumber.setText(String.valueOf(data.getFollowedcnt()));
                    txtFansNumber.setText(String.valueOf(data.getFanscnt()));
                    DisplayImageOptions options = ImageLoaderUtil.buildOptions(R.mipmap.head_holder_default);
                    ImageLoader.getInstance().displayImage(data.getHeadimgurl(),
                            imgUserHead, options);
//                                Log.d("login",data.getHeadimgurl());
                } else {
                    Toast.makeText(getActivity(), "登陆失败", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Response<User>> call, Throwable t) {
                Toast.makeText(getActivity(), "访问失败" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "访问失败 " + t.getMessage());
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.reviseUserInfo)
    public void onViewClicked() {
        ReviseUserInfoActivity.startAction(getActivity());
    }
}
