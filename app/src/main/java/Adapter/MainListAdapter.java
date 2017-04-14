package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.applicationtest2.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bean.ModelList;
import util.ImageLoaderUtil;

public class MainListAdapter extends ArrayAdapter<ModelList> implements View.OnClickListener{

    private int resourceId;
    private DisplayImageOptions optionHead;
    private DisplayImageOptions optionShow;

    //注：所有listview的item共用同一个
    private Callback mCallback;

    @Override
    public void onClick(View v) {
        //将点击事件传递出来
        mCallback.insideClick(Integer.valueOf(v.getTag().toString()), v);
    }

    /**
     * 自定义接口，用于回调按钮点击事件到Activity
     * 用于回调
     */
    public interface Callback {
        void insideClick(int position, View v);
    }

    public MainListAdapter(Context context,int textViewResourceId,List<ModelList> objects,Callback callback) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        mCallback = callback;
//        optionHead = DisplayOptionsUtil.buildHeadOptions();
//        optionShow = DisplayOptionsUtil.buildArticalShowOptions();
        optionHead = ImageLoaderUtil.buildOptions(R.mipmap.head_holder_default);
        optionShow = ImageLoaderUtil.buildOptions(R.mipmap.image_holder_default);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModelList listitem = getItem(position);
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = View.inflate(getContext(), resourceId, null);
            holder = new ViewHolder();

                holder.imgHead = (ImageView) view.findViewById(R.id.imgHead);
                holder.txtAuthor = (TextView) view.findViewById(R.id.txtAuthor);
                holder.txtAuthorMotto = (TextView) view.findViewById(R.id.txtAuthorMotto);
                holder.linAuthor = (LinearLayout) view.findViewById(R.id.linAuthor);

                holder.imgArticleShow = (ImageView) view.findViewById(R.id.imgArticleShow);
                holder.txtTitle = (TextView) view.findViewById(R.id.txtArticleTitle);
                holder.txtIntroduction = (TextView) view.findViewById(R.id.txtIntroduction);
                holder.linArticle = (LinearLayout) view.findViewById(R.id.linArticle);

            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }

//        holder.imgHead.setImageResource(listitem.getImgHead());
        /**
         * imageUrl 图片的Url地址 imageView 承载图片的ImageView控件 options
         * DisplayImageOptions配置文件
         */
        ImageLoader.getInstance().displayImage(listitem.getOwnermessage().getHeadimgurl(),
                holder.imgHead, optionHead);
//        ImageLoader.getInstance().displayImage(RetrofitUtil.getUrl()+"images/imageHead/"+listitem.getImgHead()+".jpg",
//                holder.imgHead, optionHead);
        holder.txtAuthor.setText(listitem.getOwnermessage().getCustomername());
        holder.txtAuthorMotto.setText(listitem.getOwnermessage().getMotto());
        //注册点击事件
        //把点击的位置信息也要打包
        holder.linAuthor.setOnClickListener(this);
        holder.linAuthor.setTag(position);

//        holder.imgArticleShow.setImageResource(listitem.getImgArticleShow());
        ImageLoader.getInstance().displayImage(listitem.getModelidimg().get(0).getImgurl(),
                holder.imgArticleShow, optionShow);
        holder.txtTitle.setText(listitem.getTitle());
        holder.txtIntroduction.setText(listitem.getContent());
        //注册点击事件
        //把点击的位置信息也要打包
        holder.linArticle.setOnClickListener(this);
        holder.linArticle.setTag(position);
        return view;
    }

    class ViewHolder{
        ImageView imgHead;
        TextView txtAuthor;
        TextView txtAuthorMotto;
        ImageView imgArticleShow;
        TextView txtTitle;
        TextView txtIntroduction;
        LinearLayout linAuthor;
        LinearLayout linArticle;
    }




}
