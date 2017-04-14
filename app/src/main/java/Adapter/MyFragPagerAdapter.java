package Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 殇痕 on 2017/3/30.
 */

public class MyFragPagerAdapter extends FragmentPagerAdapter{

    private List<Fragment> fmList;
    public MyFragPagerAdapter(FragmentManager fm){
        super(fm);
    }

    public MyFragPagerAdapter(FragmentManager fm,List<Fragment> fmList){
        super(fm);
        this.fmList = fmList;
    }
    @Override
    public Fragment getItem(int position) {
        return fmList.get(position);
    }

    @Override
    public int getCount() {
        return fmList.size();
    }
}
