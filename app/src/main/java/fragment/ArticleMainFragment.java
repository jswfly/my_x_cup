package fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicationtest2.ArticleActivity;
import com.example.applicationtest2.R;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bean.ModelImg;
import bean.Response;
import bean.User;
import retrofit2.Call;
import retrofit2.Callback;
import util.ImageLoaderUtil;
import util.RetrofitUtil;

import static com.makeramen.roundedimageview.RoundedDrawable.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleMainFragment extends Fragment {

    private View view;
    private TextView txtTitle, txtContent, txtCustomerName, txtTime, txtComment;
    private RoundedImageView imgHead;
    private RadioButton rbPraise;
    private String content, title;
    private int articleId;
    private List<ModelImg> modelImgs;
    private LinearLayout choiceness;
    private int choicenessCNumber=0;

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
        articleId = bundle.getInt("articleId");
        content = bundle.getString("content");
        title = bundle.getString("title");
    }

    public ArticleMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.from(getActivity()).inflate(R.layout.fragment_article_main_fregemnt, container, false);



//        RadioButton rbPraise = (RadioButton) view.findViewById(R.id.rbPraise);
//        Drawable drawable=getResources().getDrawable(R.mipmap.icon_praise_grey);
//        drawable.setBounds(0,0,20,20);
//        rbPraise.setCompoundDrawables(null,null,drawable,null);
        initArticle();

        Button totalComment = (Button) view.findViewById(R.id.totalComment);
        totalComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArticleActivity activity = (ArticleActivity) getActivity();
                activity.sendChangePage(1);
            }
        });

        return view;
    }

    private void initArticle() {
        txtTitle = (TextView) view.findViewById(R.id.txtTitle);
        txtContent = (TextView) view.findViewById(R.id.txtContent);
        txtCustomerName = (TextView) view.findViewById(R.id.txtCustomerName);
        txtTime = (TextView) view.findViewById(R.id.txtTime);
        txtComment = (TextView) view.findViewById(R.id.txtComment);

        imgHead = (RoundedImageView) view.findViewById(R.id.imgHead);
        rbPraise = (RadioButton) view.findViewById(R.id.rbPraise);
        choiceness = (LinearLayout) view.findViewById(R.id.choiceness);

        rbPraise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbPraise.setText(String.valueOf(choicenessCNumber+1));
            }
        });

        txtTitle.setText(title);
        txtComment.setText(content);

        Call<Response<User>> call = RetrofitUtil.service.getArticleMessage(articleId);
        call.enqueue(new Callback<Response<User>>() {
            @Override
            public void onResponse(Call<Response<User>> call, retrofit2.Response<Response<User>> response) {
                if(!response.body().isStatus()){
                    return ;
                }
                User data = response.body().getData();



                modelImgs = data.getModelidimg();
                //插入轮播图
                initRollView();

                //如果没有评论就删除
                if(data.getComments().size()!=0) {
                    choicenessCNumber = data.getComments().get(0).getPraiseNumber();
                    choiceness.setVisibility(View.VISIBLE);
                    DisplayImageOptions options = ImageLoaderUtil.buildOptions(R.mipmap.head_holder_default);
                    ImageLoader.getInstance().displayImage(data.getComments().get(0).getHeadImg(),
                            imgHead,options);
                    txtCustomerName.setText(data.getComments().get(0).getCustomerName());
                    txtTime.setText(data.getComments().get(0).getTime());
                    txtComment.setText(data.getComments().get(0).getComment());
                    txtContent.setText(content);
                    rbPraise.setText(String.valueOf(data.getComments().get(0).getPraiseNumber()));
                }else{
                    choiceness.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<Response<User>> call, Throwable t) {
                Toast.makeText(getActivity(), "访问失败"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });

    }

    private void initRollView(){
        RollPagerView rollViewPager = (RollPagerView) view.findViewById(R.id.articleRollView);
        rollViewPager.setHintView(new ColorPointHintView(getActivity(),Color.RED, getResources().getColor(R.color.gray5)));
        rollViewPager.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), "Item" + position + "clicked", Toast.LENGTH_SHORT).show();
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
//            private int[] imgs = new int[]{
//                    R.mipmap.img1,
//                    R.mipmap.img2,
//                    R.mipmap.img3,
//                    R.mipmap.img4
//            };

            @Override
            public View getView(ViewGroup container, int position) {
                ImageView view = new ImageView(getContext());
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                view.setImageResource(imgs[position]);
                DisplayImageOptions options = ImageLoaderUtil.buildOptions(R.mipmap.head_holder_default);
                ImageLoader.getInstance().displayImage(modelImgs.get(position).getImgurl(),
                        view, options);
                return view;
            }

            @Override
            public int getRealCount() {
                return modelImgs.size();
            }
        };
        rollViewPager.setAdapter(headerAdapter);
    }

}
