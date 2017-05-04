package fragment;


import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.applicationtest2.MainActivity;
import com.example.applicationtest2.R;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DesignFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.btnImport)
    Button btnImport;
    Unbinder unbinder;
    private ImageButton up, down, left, right, btn;
    private Button material, color, shape, size, handleShape, cover, spoon, pattern;
    private ImageView show;
    //上右左下
    private int[][] resource = {{R.mipmap.cup1_3, R.mipmap.cup1_4, R.mipmap.cup1_1, R.mipmap.cup1_2}};
    private int row = 0;

    public DesignFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.from(getContext()).inflate(R.layout.fragment_design, container, false);
        up = (ImageButton) view.findViewById(R.id.up);
        down = (ImageButton) view.findViewById(R.id.down);
        left = (ImageButton) view.findViewById(R.id.left);
        right = (ImageButton) view.findViewById(R.id.right);
        show = (ImageView) view.findViewById(R.id.show);
        material = (Button) view.findViewById(R.id.material);
        color = (Button) view.findViewById(R.id.color);
        shape = (Button) view.findViewById(R.id.shape);
        size = (Button) view.findViewById(R.id.size);
        pattern = (Button) view.findViewById(R.id.pattern);

        material.setOnClickListener(this);
        color.setOnClickListener(this);
        shape.setOnClickListener(this);
        size.setOnClickListener(this);
        pattern.setOnClickListener(this);

        up.setOnClickListener(this);
        down.setOnClickListener(this);
        left.setOnClickListener(this);
        right.setOnClickListener(this);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void showDialog(String dialogName, final String[] listStr, final int check, final Button btn) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_view, null);
        dialogBuilder.setView(dialogView);

        //dialog标题
        TextView txtDialog = (TextView) dialogView.findViewById(R.id.txtDialog);
        txtDialog.setText(dialogName);
        Button close = (Button) dialogView.findViewById(R.id.close);


        LoopView loopView = (LoopView) dialogView.findViewById(R.id.loopView);
        final ArrayList<String> list = new ArrayList<>();
        for (String str : listStr) {
            list.add(str);
        }
        // 滚动监听
        loopView.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                if (check == R.id.material) {
                    row = 0;
                    show.setImageResource(resource[row][0]);
                }
                btn.setText(listStr[index]);
                Toast.makeText(getActivity(), listStr[index], Toast.LENGTH_SHORT).show();
            }
        });
        // 设置原始数据
        loopView.setItems(list);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Resources r = getResources();
        switch (v.getId()) {
            case R.id.up:
                show.setImageResource(resource[row][0]);
                break;
            case R.id.down:
                show.setImageResource(resource[row][1]);
                break;
            case R.id.left:
                show.setImageResource(resource[row][2]);
                break;
            case R.id.right:
                show.setImageResource(resource[row][3]);
                break;
            case R.id.material:
                showDialog(r.getString(R.string.glassMaterial), r.getStringArray(R.array.material), R.id.material, material);
                break;
            case R.id.color:
                showDialog(r.getString(R.string.glassColor), r.getStringArray(R.array.color), R.id.color, color);
                break;
            case R.id.shape:
                showDialog(r.getString(R.string.glassShape), r.getStringArray(R.array.shape), R.id.shape, shape);
                break;
            case R.id.size:
                showDialog(r.getString(R.string.glassSize), r.getStringArray(R.array.size), R.id.size, size);
                break;
            case R.id.pattern:
                showDialog(r.getString(R.string.glassPattern), r.getStringArray(R.array.pattern), R.id.pattern, pattern);
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.btnImport)
    public void onViewClicked() {
        ((MainActivity)getActivity()).toCategory();
    }
}
