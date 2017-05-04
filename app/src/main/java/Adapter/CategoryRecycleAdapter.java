package Adapter;

import android.content.res.Resources;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.applicationtest2.R;

import java.util.ArrayList;

/**
 * Created by 殇痕 on 2017/4/15.
 *
 */

public class CategoryRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Integer> dataList = new ArrayList<>();
    private SparseArray<String> titleList = new SparseArray<>();
    private Resources res;
    private int [] height;
    public static enum ITEM_TYPE {
        TITLE_TYPE,
        MY_TYPE
    }
    //声明一个这个接口的变量
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    //1.在Adapter中定义如下接口,模拟ListView的OnItemClickListener：
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    public void replaceAll(ArrayList<Integer> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        height = new int[dataList.size()];
        /**
         * 初始化高度
         */
        for(int i=0; i<dataList.size(); i++){
            height[i] = (int)(250/*+Math.random()*200*/);
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE.TITLE_TYPE.ordinal()){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_menu_title_item,parent,false);
            TitleHolder titleViewHolder = new TitleHolder(view);
            return titleViewHolder;

        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recycleview_item,parent,false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_recycleview_item, parent, false);
//        ViewHolder holder = new ViewHolder(view);
//        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (dataList.get(position) != null) {
            if(isTitle(position)){
                TitleHolder titleHolder = (TitleHolder) holder;
                titleHolder.title.setText(titleList.get(position));
                return;
            }
            //修正position的正确位子
            for(int i = 0; i < titleList.size(); i++){
                int key = titleList.keyAt(i);
//                Log.d(TAG, "onBindViewHolder: "+key);
                if(position > titleList.keyAt(titleList.size() - 1)){
                    position -= titleList.size();
                    break;
                }else if(position > key && position < titleList.keyAt(i + 1)){
                    position -= (i+1);
                    break;
                }
            }

            MyViewHolder myViewHolder = (MyViewHolder) holder;
//            String text = (String) dataList.get(position);

            int width = myViewHolder.categoryItem.getContext().getResources().getDisplayMetrics().widthPixels;
            ViewGroup.LayoutParams params =  myViewHolder.categoryItem.getLayoutParams();
            //设置图片的相对于屏幕的宽高比
            params.width = width / 3;
            params.height = height[position];
            myViewHolder.categoryItem.setLayoutParams(params);
            res =  myViewHolder.categoryItem.getContext().getResources();

            Glide.with(holder.itemView.getContext())
                    .load(dataList.get(position))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)   //缓存所有版本的图像（默认行为）
                    .placeholder(R.mipmap.image_holder_default)          //等待时显示的图片
                    .error(R.mipmap.image_holder_default)                //错误时显示的图片
                    .crossFade()                                //交叉渐入渐出
                    .into(myViewHolder.categoryItem);
//            ((MyViewHolder) holder).categoryItem.setImageResource(dataList.get(position));

            holder.itemView.setTag(position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(isTitle(position)){
            return ITEM_TYPE.TITLE_TYPE.ordinal();
        }
        return ITEM_TYPE.MY_TYPE.ordinal();
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        //如果是title就占据3个单元格(重点)
        GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if(isTitle(position)){
                    return 3;
                }
                return 1;
            }
        });
    }

    public class TitleHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public TitleHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.categoryTitle);
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView categoryItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            categoryItem = (ImageView) itemView.findViewById(R.id.categoryItem);
            categoryItem.setOnClickListener(this);
        }

        //将点击事件转移给外面的调用者：
        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                //注意这里使用getTag方法获取数据
                mOnItemClickListener.onItemClick(v, (Integer) itemView.getTag());
            }
        }
    }

    private boolean isTitle(int position){
        return titleList.get(position) != null;
    }
    public void addTitle(int position,String title){
        titleList.put(position,title);
    }
}


