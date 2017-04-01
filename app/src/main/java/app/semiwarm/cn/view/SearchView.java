package app.semiwarm.cn.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import app.semiwarm.cn.R;
import app.semiwarm.cn.activity.SearchActivity;

/**
 * 自定义搜索框
 * Created by alibct on 2017/3/3.
 */

public class SearchView extends LinearLayout {

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.view_search, this);
        LinearLayout searchView = (LinearLayout) view.findViewById(R.id.ll_search_view);
        searchView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                getContext().startActivity(intent);
            }
        });
    }
}
