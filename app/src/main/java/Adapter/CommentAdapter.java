package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.applicationtest2.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import bean.Comment;
import util.ImageLoaderUtil;

/**
 * Created by 殇痕 on 2017/3/30.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {
    private int resorceId;
    private DisplayImageOptions optionHead;
    public CommentAdapter(Context context,int textViewResourceId, List<Comment> object) {
        super(context,textViewResourceId, object);
        resorceId = textViewResourceId;
        optionHead = ImageLoaderUtil.buildOptions(R.mipmap.head_holder_default);
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        View view ;
        final Comment listItem = getItem(position);
        final ViewHolder holder;
        if(convertView == null){
            view = View.inflate(getContext(), resorceId, null);
            holder = new ViewHolder();
            holder.imgHead = (RoundedImageView) view.findViewById(R.id.imgHead);
            holder.rbPraise = (RadioButton) view.findViewById(R.id.rbPraise);
            holder.txtComment = (TextView) view.findViewById(R.id.txtComment);
            holder.txtCustomerName = (TextView) view.findViewById(R.id.txtCustomerName);
            holder.txtTime = (TextView) view.findViewById(R.id.txtTime);
            view.setTag(holder);
        }else{
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        ImageLoader.getInstance().displayImage(listItem.getHeadImg(),
                holder.imgHead, optionHead);
        holder.txtCustomerName.setText(listItem.getCustomerName());
        holder.txtTime.setText(listItem.getTime());
        holder.txtComment.setText(listItem.getComment());
        holder.rbPraise.setText(String.valueOf(listItem.getPraiseNumber()));
//            Drawable drawable = getContext().getResources().getDrawable(R.mipmap.icon_praise_red);
//            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight()); //设置边界
//            holder.rbPraise.setCompoundDrawables(null, null, drawable, null);
        if(listItem.isHasPraise()){
            holder.rbPraise.setChecked(true);
        }
        //添加点赞事件
        holder.rbPraise.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                holder.rbPraise.setText(String.valueOf(listItem.getPraiseNumber()+1));
            }
        });

        return view;
    }

    class ViewHolder{
        RoundedImageView imgHead;
        TextView txtCustomerName;
        TextView txtTime;
        TextView txtComment;
        RadioButton rbPraise;
    }
}
