package Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.applicationtest2.R;

import java.util.List;

import bean.ForumItem;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 殇痕 on 2017/4/22.
 */

public class ForumListAdapter extends ArrayAdapter<ForumItem> {
    private int resourceId;

    public ForumListAdapter(Context context, int resource, List<ForumItem> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder holder;
        ForumItem forumItem = getItem(position);
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        }
        holder.txtTitle.setText(forumItem.getTitle());
        holder.txtContent.setText(forumItem.getContent());
        holder.txtTime.setText(forumItem.getTime());
        holder.contentImageShow.setImageResource(forumItem.getUrl());
        return view;
    }

    static class ViewHolder {
        @BindView(R.id.txtTitle)
        TextView txtTitle;
        @BindView(R.id.txtContent)
        TextView txtContent;
        @BindView(R.id.txtTime)
        TextView txtTime;
        @BindView(R.id.contentImageShow)
        ImageView contentImageShow;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
