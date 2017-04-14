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

import bean.MainList;
import util.ImageLoaderUtil;

/**
 * Created by 殇痕 on 2017/4/1.
 */

public class MainHeadAdapter extends ArrayAdapter<MainList> {
    private int resourceId;
    private DisplayImageOptions optionHead;
    public MainHeadAdapter(Context context,int textViewResourceId,List<MainList> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        optionHead = ImageLoaderUtil.buildOptions(R.mipmap.head_holder_default);
    }

    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        MainList listitem = getItem(position);
        View view;
        ViewHolder holder;
        if (convertView == null) {
            view = View.inflate(getContext(), resourceId, null);
            holder = new ViewHolder();
            holder.imgHead = (ImageView) view.findViewById(R.id.imgHead);
            holder.txtAuthor = (TextView) view.findViewById(R.id.txtAuthor);
            holder.txtAuthorMotto = (TextView) view.findViewById(R.id.txtAuthorMotto);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }


//            holder.imgHead.setImageResource(listitem.getImgHead());
        ImageLoader.getInstance().displayImage("drawable://"+listitem.getImgHead(),
                holder.imgHead, optionHead);
        holder.txtAuthor.setText(listitem.getAuthor());
        holder.txtAuthorMotto.setText(listitem.getAuthorMotto());

        return view;
    }

    class ViewHolder{
        ImageView imgHead;
        TextView txtAuthor;
        TextView txtAuthorMotto;
    }
}
