package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.applicationtest2.R;

import java.util.ArrayList;

/**
 * Created by 殇痕 on 2017/4/24.
 */

public class AddForumImageGridAdapter extends BaseAdapter {
    private ArrayList<String> listUrls;
    private LayoutInflater inflater;
    private Context context;

    public AddForumImageGridAdapter(Context context, ArrayList<String> listUrls) {
        this.context = context;
        this.listUrls = listUrls;
        if (listUrls.size() == 7) {
            listUrls.remove(listUrls.size() - 1);
        }
    }

    public int getCount() {
        return listUrls.size();
    }

    @Override
    public String getItem(int position) {
        return listUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_add_image, parent, false);
            holder.image = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final String path = listUrls.get(position);
        if (path.equals("add")) {
            holder.image.setImageResource(R.mipmap.btn_add_shows);
        } else {
            Glide.with(context)
                    .load(path)
                    .placeholder(R.mipmap.default_error)
                    .error(R.mipmap.default_error)
                    .centerCrop()
                    .crossFade()
                    .into(holder.image);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView image;
    }
}
