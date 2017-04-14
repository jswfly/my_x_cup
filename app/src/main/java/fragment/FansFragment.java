package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.applicationtest2.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.MainHeadAdapter;
import bean.MainList;
import util.MyListener;
import view.PullToRefreshLayout;
import view.PullableListView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FansFragment extends Fragment implements AdapterView.OnItemClickListener {

    private List<MainList> mainList;
    private PullableListView listView;
    private MainHeadAdapter adapter;
    private PullToRefreshLayout fansRefresh;
    private int position;
    public FansFragment() {
        // Required empty public constructor
    }

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
        position = bundle.getInt("state", 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.from(getActivity()).inflate(R.layout.fragment_fans, container, false);
        //设置空的刷新5000ms
        fansRefresh = ((PullToRefreshLayout) view.findViewById(R.id.fansRefresh));
        fansRefresh.setOnRefreshListener(new MyListener());

        mainList = new ArrayList<>();
        initListDate();
        listView = (PullableListView) view.findViewById(R.id.listFans);
        adapter = new MainHeadAdapter(getActivity(), R.layout.part_item_main_head,  mainList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return view;
    }

    private void initListDate() {
        MainList item = new MainList("小礼君", "资深买买买达人", R.mipmap.head1);
        mainList.add(item);
        item = new MainList("叫我小公举", "居家小物收集爱好者", R.mipmap.head2);
        mainList.add(item);
        item = new MainList("上上签", "迷信生活的叛逆者", R.mipmap.head3);
        mainList.add(item);
        item = new MainList("朵朵酱", "不为无聊之事，何以遣有涯之生", R.mipmap.head4);
        mainList.add(item);
        item = new MainList("淘局长", "礼物界的老司机", R.mipmap.head5);
        mainList.add(item);
        item = new MainList("爱因钱烧", "带你跟风带你飞的无洒水烧钱君", R.mipmap.head6);
        mainList.add(item);
    }



    @Override
    public void onItemClick(AdapterView<?> parent,View view,int position,long id) {
//        AuthorActivity.actionStart(getActivity(),mainList.get(position).getAuthor(),
//                mainList.get(position).getAuthorMotto(),
//                mainList.get(position).getImgHead());
//        Toast.makeText(this, "hello world!!", Toast.LENGTH_SHORT).show();
    }

}
