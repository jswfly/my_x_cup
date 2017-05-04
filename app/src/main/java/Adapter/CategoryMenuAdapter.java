package Adapter;

import android.content.res.Resources;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.applicationtest2.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by 殇痕 on 2017/4/16.
 *
 */

public class CategoryMenuAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private ArrayList<String> dataList = new ArrayList<>();
    private SparseArray<String> titleList = new SparseArray<>();
    private boolean []checkList;
    private Resources res;
    private boolean isReset = false;

    public static enum ITEM_TYPE {
        TITLE_TYPE,
        MY_TYPE
    }

    public void replaceAll(ArrayList<String> list) {
        dataList.clear();
        if (list != null && list.size() > 0) {
            dataList.addAll(list);
        }
        checkList = new boolean[dataList.size()];
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ITEM_TYPE.TITLE_TYPE.ordinal()){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_menu_title_item,parent,false);
            TitleHolder titleViewHolder = new TitleHolder(view);
            return titleViewHolder;
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_menu_item,parent,false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(isTitle(position)){
            TitleHolder titleHolder = (TitleHolder) holder;
            titleHolder.title.setText(titleList.get(position));
            return;
        }
        //修正position的正确位子
        for(int i = 0; i < titleList.size(); i++){
            int key = titleList.keyAt(i);
//            Log.d(TAG, "onBindViewHolder: "+key);
            if(position > titleList.keyAt(titleList.size() - 1)){
                position -= titleList.size();
                break;
            }else if(position > key && position < titleList.keyAt(i + 1)){
                position -= (i+1);
                break;
            }
        }

        MyViewHolder myViewHolder = (MyViewHolder) holder;
//            int width = myViewHolder.menuCheckBox.getContext().getResources().getDisplayMetrics().widthPixels;
//            ViewGroup.LayoutParams params =  myViewHolder.menuCheckBox.getLayoutParams();
//            //设置图片的相对于屏幕的宽高比
//            params.width = width / 3;
//            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//            myViewHolder.menuCheckBox.setLayoutParams(params);
//            Log.d(TAG, "onBindViewHolder: "+text+myViewHolder.menuCheckBox.getId());
        myViewHolder.menuCheckBox.setChecked(checkList[position]);
        myViewHolder.itemView.setTag(position);
        myViewHolder.menuCheckBox.setText(dataList.get(position));
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
        return dataList != null ? dataList.size()+titleList.size() : 0;
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

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public CheckBox menuCheckBox;

        public MyViewHolder(final View itemView) {
            super(itemView);
            menuCheckBox = (CheckBox) itemView.findViewById(R.id.menuCheckBox);
            menuCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked) {
                        checkList[(int) itemView.getTag()] = true;
                    }else{
                        checkList[(int) itemView.getTag()] = false;
                    }
                }
            });
        }
    }

    private boolean isTitle(int position){
        return titleList.get(position) != null;
    }
    public void addTitle(int position,String title){
        titleList.put(position,title);
    }
    public boolean[] getCheckList(){
        return checkList;
    }
    public void reset(){
        Arrays.fill(checkList, false);
        notifyDataSetChanged();
    }
}
