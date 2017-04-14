package fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.applicationtest2.R;

import java.util.ArrayList;
import java.util.List;

import Adapter.CommentAdapter;
import bean.Comment;
import bean.ResponseList;
import retrofit2.Call;
import retrofit2.Callback;
import util.RetrofitUtil;

import static com.makeramen.roundedimageview.RoundedDrawable.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class ArticleCommentFragment extends Fragment {


    private View view;
    private ListView listViewComment;
    private List<Comment> commentList ;
    private String content, title;
    private int articleId;
    private CommentAdapter adapter;

    @Override
    public void setArguments(Bundle bundle) {
        super.setArguments(bundle);
        articleId = bundle.getInt("articleId");
        content = bundle.getString("content");
        title = bundle.getString("title");
    }

    public ArticleCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.from(getActivity()).inflate(R.layout.fragment_article_comment, container, false);

        initList();
        listViewComment = (ListView) view.findViewById(R.id.listViewComment);
        adapter = new CommentAdapter(getActivity(), R.layout.item_comment, commentList);
        listViewComment.setAdapter(adapter);
        return view;
    }

    private void initList() {
          commentList = new ArrayList<>();
//        for(int i=0; i<10; i++) {
//            Comment listItem = new Comment(0,i,"name" + i,"time"+i, "comment"+i, "head1", i%2==0?false:true );
//            commentList.add(listItem);
//        }
        Call<ResponseList<Comment>> call = RetrofitUtil.service.getAllComment(articleId);
        call.enqueue(new Callback<ResponseList<Comment>>() {
            @Override
            public void onResponse(Call<ResponseList<Comment>> call, retrofit2.Response<ResponseList<Comment>> response) {
                if(!response.body().isStatus()){
                    return ;
                }
                for (Comment item:response.body().getData()) {
                    commentList.add(item);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseList<Comment>> call, Throwable t) {
                Toast.makeText(getActivity(), "访问失败"+t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

}
