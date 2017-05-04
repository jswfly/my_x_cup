package com.example.applicationtest2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends Activity {
    @BindView(R.id.btnCancel)
    Button btnCancel;

    @OnClick(R.id.btnCancel) void Cancel() {
        finish();
    }


    public static void startAction(Context context){
        Bundle _bundle = new Bundle();
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtras(_bundle);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
    }

}
