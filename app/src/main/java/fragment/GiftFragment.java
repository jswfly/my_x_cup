package fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.applicationtest2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class GiftFragment extends Fragment {

    private Context context;
    public GiftFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gift, container, false);
//        RollPagerView rollViewPager = (RollPagerView) view.findViewById(R.id.rollView2);
//        rollViewPager.setHintView(new ColorPointHintView(getActivity(),
//                getResources().getColor(R.color.red),
//                getResources().getColor(R.color.gray2)));
//        rollViewPager.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Toast.makeText(getActivity(),"Item "+position+" clicked",Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        LoopPagerAdapter headerAdapter = new LoopPagerAdapter(rollViewPager) {
//            private int []imgs = new int[]{
//                    R.mipmap.img1,
//                    R.mipmap.img2,
//                    R.mipmap.img3,
//                    R.mipmap.img4
//            };
//            @Override
//            public View getView(ViewGroup container, int position) {
//                ImageView view = new ImageView(getContext());
//                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
//                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
//                view.setImageResource(imgs[position]);
//                return view;
//            }
//
//            @Override
//            public int getRealCount() {
//                return imgs.length;
//            }
//        };
//        rollViewPager.setAdapter(headerAdapter);
        return view;
    }

}
