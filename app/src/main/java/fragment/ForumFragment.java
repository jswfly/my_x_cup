package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;

import com.example.applicationtest2.AddForumActivity;
import com.example.applicationtest2.ForumDetailActivity;
import com.example.applicationtest2.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.ForumListAdapter;
import bean.ForumItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import util.MyListener;
import view.PullToRefreshLayout;
import view.PullableListView;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ForumFragment extends Fragment {

    @BindView(R.id.btnAdd)
    Button btnAdd;
    @BindView(R.id.listForum)
    PullableListView listViewForum;
    @BindView(R.id.forumFresh)
    PullToRefreshLayout forumFresh;
    Unbinder unbinder;

    private List<ForumItem> forumList = new ArrayList<>();
    private ForumListAdapter adapter;

    public ForumFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        unbinder = ButterKnife.bind(this, view);

        adapter = new ForumListAdapter(getActivity(), R.layout.item_forum, forumList);
        initListDate();
        listViewForum.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<Integer> array = new ArrayList<Integer>();
                array.add(forumList.get(position).getUrl());
                array.add(R.mipmap.imageshow2);
                array.add(R.mipmap.imageshow3);
                array.add(R.mipmap.imageshow4);
                Log.d(TAG, "onItemClick: "+forumList.get(position).getHeadImg());
                ForumDetailActivity.startAction(getActivity(), forumList.get(position).getHeadImg(), forumList.get(position).getUsername(),forumList.get(position).getTitle(),
                        forumList.get(position).getTime(), forumList.get(position).getContent(), array);
            }
        });

        forumFresh.setOnRefreshListener(new MyListener());
        listViewForum.setAdapter(adapter);
        return view;
    }

    private void initListDate() {
        forumList.add(new ForumItem(R.mipmap.head7, "勇敢的心", "你们看这个杯子怎么样？",
                "    水杯是我们日常用来盛装的液体的容器，通常都采用高度大于宽度的圆柱体造型，以便于手拿取并保留液体的温度，也有方形等形状的水杯。有的水杯还会带有握柄、把手，" +
                        "或带有额外的防烫、保温等功能结构。水杯一般体积较小，人们可以很方便的单手拿起，杯底较宽大，可以稳定放在桌子上。水杯体采用玻璃、陶瓷、塑料、金属等坚固、不溶于水的材料制作，" +
                        "并可以安全容纳多种可食用液体（如饮料、酒水等）。\n"+"    水杯通常是人们盛装液体的容器，平时可用来喝茶、喝水、喝咖啡、喝饮料等。水杯是一种大多数情况下用来盛载液体的器皿。" +
                        "通常用塑胶、玻璃、瓷或不锈钢制造，在餐厅打包饮料，则常用纸杯或胶杯盛载。杯子多呈圆柱形，上面开口，中空，以供盛物。",
                "2017/4/20 10:20:11", R.mipmap.imageshow1));
        forumList.add(new ForumItem(R.mipmap.head6, "小姐姐","刚做了一个被子的模型，大家都来看看", "水杯通常是人们盛装液体的容器", "2017/4/20 10:20:21", R.mipmap.forumimage2));
        forumList.add(new ForumItem(R.mipmap.head5, "你猜我是谁","设计师能设计一个这样的杯子吗？", "杯子多呈圆柱形，上面开口，中空，以供盛物。", "2017/4/20 10:20:11", R.mipmap.forumimage3));
        forumList.add(new ForumItem(R.mipmap.head4, "小礼物君","杯子？杯子！！", "人们可以很方便的单手拿起，杯底较宽大，可以稳定放在桌子上。", "2017/4/20 10:20:12", R.mipmap.forumimage4));
        forumList.add(new ForumItem(R.mipmap.head3, "叫我小公主","原创，勿喷", "通常用塑胶、玻璃、瓷或不锈钢制造，在餐厅打包饮料，则常用纸杯或胶杯盛载。", "2017/4/20 10:20:43" , R.mipmap.forumimage5));
        forumList.add(new ForumItem(R.mipmap.head2, "上上签","做个杯子练练手=——=", "昨天刚做的杯子，就是想要练练手", "2017/4/20 10:20:35", R.mipmap.forumimage6));
        forumList.add(new ForumItem(R.mipmap.head1, "淘局长","新手冒个泡＾_＾", "这个人很懒什么东西都没写==", "2017/4/19 11:40:12", R.mipmap.forumimage7));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnAdd)
    public void onViewClicked() {
        AddForumActivity.startAction(getActivity());
    }
}
