package Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applicationtest2.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bean.ModelList;
import util.ImageLoaderUtil;

/**
 * Created by 殇痕 on 2017/3/20.
 */

public class MainBodyAdapter extends ArrayAdapter<ModelList>{
    private int resourceId;
    private DisplayImageOptions optionShow;
    public MainBodyAdapter(Context context,int textViewResourceId,List<ModelList> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        optionShow = ImageLoaderUtil.buildOptions(R.mipmap.image_holder_default);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ModelList listitem = getItem(position);
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = View.inflate(getContext(), resourceId, null);
            holder = new ViewHolder();
            holder.imgArticleShow = (ImageView) view.findViewById(R.id.imgArticleShow);
            holder.txtTitle = (TextView) view.findViewById(R.id.txtArticleTitle);
            holder.txtIntroduction = (TextView) view.findViewById(R.id.txtIntroduction);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }


//        holder.imgArticleShow.setImageResource(listitem.getImgArticleShow());
        ImageLoader.getInstance().displayImage(listitem.getModelidimg().get(0).getImgurl(),
                holder.imgArticleShow, optionShow);
        holder.txtTitle.setText(listitem.getTitle());
        holder.txtIntroduction.setText(listitem.getContent());

        return view;
    }

    class ViewHolder{
        ImageView imgArticleShow;
        TextView txtTitle;
        TextView txtIntroduction;
    }
}
