package fragment;


import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;

import com.example.applicationtest2.R;
import com.example.applicationtest2.SearchActivity;
import com.example.applicationtest2.ShowImageActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Adapter.CategoryMenuAdapter;
import Adapter.CategoryRecycleAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.imgBtnSearch)
    ImageButton imgBtnSearch;
    @BindView(R.id.btnMenu)
    Button btnMenu;
    @BindView(R.id.categoryRecyclerView)
    RecyclerView recyclerView;
    Unbinder unbinder;

    private static int cup[][]={{R.mipmap.cup1_1, R.mipmap.cup1_2, R.mipmap.cup1_3, R.mipmap.cup1_4},
            {R.mipmap.cup2_1, R.mipmap.cup2_2, R.mipmap.cup2_3, R.mipmap.cup2_4},
            {R.mipmap.cup3_1, R.mipmap.cup3_2, R.mipmap.cup3_3, R.mipmap.cup3_4},
            {R.mipmap.cup4_1, R.mipmap.cup4_2, R.mipmap.cup4_3, R.mipmap.cup4_4},
            {R.mipmap.cup5_1, R.mipmap.cup5_2, R.mipmap.cup5_3, R.mipmap.cup5_4},
            {R.mipmap.cup6_1, R.mipmap.cup6_2},
            {R.mipmap.cup7_1, R.mipmap.cup7_2},
            {R.mipmap.cup8_1, R.mipmap.cup8_2},
            {R.mipmap.cup9_1, R.mipmap.cup9_2},
            {R.mipmap.cup10_1, R.mipmap.cup10_2, R.mipmap.cup10_3, R.mipmap.cup10_4},
            {R.mipmap.cup11_1, R.mipmap.cup11_2, R.mipmap.cup11_3, R.mipmap.cup11_4},
            {R.mipmap.cup12_1, R.mipmap.cup12_2, R.mipmap.cup12_3, R.mipmap.cup12_4},
            {R.mipmap.cup13_1, R.mipmap.cup13_2, R.mipmap.cup13_3, R.mipmap.cup13_4},
            {R.mipmap.cup14_1, R.mipmap.cup14_2, R.mipmap.cup14_3, R.mipmap.cup14_4},
            {R.mipmap.cup15_1, R.mipmap.cup15_2, R.mipmap.cup15_3, R.mipmap.cup15_4},
            {R.mipmap.cup16_1, R.mipmap.cup16_2, R.mipmap.cup16_3, R.mipmap.cup16_4},
            {R.mipmap.cup17_1, R.mipmap.cup17_2, R.mipmap.cup17_3, R.mipmap.cup17_4},
            {R.mipmap.cup18_1, R.mipmap.cup18_2, R.mipmap.cup18_3, R.mipmap.cup18_4},
            {R.mipmap.cup19_1, R.mipmap.cup19_2, R.mipmap.cup19_3, R.mipmap.cup19_4},
            {R.mipmap.cup20_1, R.mipmap.cup20_2, R.mipmap.cup20_3, R.mipmap.cup20_4}};


    private PopupWindow mPopupWindow;
    private View popupView;
    private float alpha = 1.0f;
    private Button btnSure, btnReset;
    private CategoryMenuAdapter menuAdapter;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    backgroundAlpha((float) msg.obj);
                    break;
            }
        }
    };

    public CategoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.from(getContext()).inflate(R.layout.fragment_category, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        CategoryRecycleAdapter adapter = new CategoryRecycleAdapter();
        recyclerView.setAdapter(adapter);
        adapter.replaceAll(getData());
//        adapter.addTitle(0, "11111111");
//        adapter.addTitle(10, "222222222");
        adapter.setOnItemClickListener(new CategoryRecycleAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

//                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view, "transition_show");


//                ActivityOptionsCompat options =
//                        ActivityOptionsCompat.makeScaleUpAnimation(view, //The View that the new activity is animating from
//                                (int)view.getMeasuredWidth()/2, (int)view.getMeasuredHeight()/2, //拉伸开始的坐标
//                                0, 0);//拉伸开始的区域大小，这里用（0，0）表示从无到全屏
                ArrayList<Integer> array = new ArrayList<Integer>();
                for(int i=0; i<cup[position].length; i++){
                    array.add(cup[position][i]);
                }
                ShowImageActivity.startAction(getActivity(), view, true, array);

//                    getActivity().overridePendingTransition(R.anim.slide_bottom_in,android.R.anim.fade_out);
            }
        });

        bindPopupWindow();


        return view;
    }

    /**
     * 绑定右侧弹出窗
     */
    private void bindPopupWindow() {
        popupView = LayoutInflater.from(getContext()).inflate(R.layout.category_popupwindow, null, false);

        bindMenu();

        mPopupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
        //下面三行的作用是点击空白处的时候PopupWindow会消失
        mPopupWindow.setTouchable(true);
        mPopupWindow.setOutsideTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));

        mPopupWindow.setFocusable(true);
        mPopupWindow.setAnimationStyle(R.style.anim_popupwindow_test);
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                mPopupWindow.dismiss();
                cutAlpha();
            }
        });

        btnReset = (Button) popupView.findViewById(R.id.btnReset);
        btnSure = (Button) popupView.findViewById(R.id.btnSure);

        btnReset.setOnClickListener(this);
        btnSure.setOnClickListener(this);
    }

    public ArrayList<Integer> getData() {
        ArrayList<Integer> list = new ArrayList<>();
//        for (String url : ImageUtil.imageUrls) {
//            list.add(url);
//        }
        for(int i=0; i<cup.length; i++){
            list.add(cup[i][0]);
        }
        return list;
    }

    private void bindMenu() {
        List menuList = new ArrayList();
        RecyclerView menu = (RecyclerView) popupView.findViewById(R.id
                .categoryMenu);
        menu.setHasFixedSize(true);
        menu.setLayoutManager(new GridLayoutManager(getContext(), 3));
        menuAdapter = new CategoryMenuAdapter();
        menu.setAdapter(menuAdapter);
        menuAdapter.replaceAll(getMenuData());

    }


    private ArrayList<String> getMenuData() {
        ArrayList<String> list = new ArrayList<>();
        int i = 0;
        menuAdapter.addTitle(list.size() + i++, getResources().getString(R.string.glassMaterial));
        Collections.addAll(list, getResources().getStringArray(R.array.material));

        menuAdapter.addTitle(list.size() + i++, getResources().getString(R.string.glassColor));
        Collections.addAll(list, getResources().getStringArray(R.array.color));

        menuAdapter.addTitle(list.size() + i++, getResources().getString(R.string.glassShape));
        Collections.addAll(list, getResources().getStringArray(R.array.shape));

        menuAdapter.addTitle(list.size() + i++, getResources().getString(R.string.glassSize));
        Collections.addAll(list, getResources().getStringArray(R.array.size));

        menuAdapter.addTitle(list.size() + i, getResources().getString(R.string.glassPattern));
        Collections.addAll(list, getResources().getStringArray(R.array.pattern));
        return list;
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }


    private void addAlpha() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (alpha > 0.5f) {
                    try {
                        //4是根据弹出动画时间和减少的透明度计算
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    //每次减少0.01，精度越高，变暗的效果越流畅
                    alpha -= 0.01f;
                    msg.obj = alpha;
                    mHandler.sendMessage(msg);
                }
            }

        }).start();
    }

    public void cutAlpha() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //此处while的条件alpha不能<= 否则会出现黑屏
                while (alpha < 1f) {
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                            Log.d("HeadPortrait","alpha:"+alpha);
                    Message msg = mHandler.obtainMessage();
                    msg.what = 1;
                    alpha += 0.01f;
                    msg.obj = alpha;
                    mHandler.sendMessage(msg);
                }
            }

        }).start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReset:
                menuAdapter.reset();
                break;
            case R.id.btnSure:
//                int j =0;
//                for(boolean c:menuAdapter.getCheckList()){
//                    Log.d(TAG, "onClick: "+c+j++);
//                }
                mPopupWindow.dismiss();
                break;

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.imgBtnSearch, R.id.btnMenu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imgBtnSearch:
                SearchActivity.startAction(getActivity());
                break;
            case R.id.btnMenu:
                mPopupWindow.showAtLocation(btnMenu, Gravity.RIGHT, 0, 0);
                addAlpha();
                break;
        }
    }
}
