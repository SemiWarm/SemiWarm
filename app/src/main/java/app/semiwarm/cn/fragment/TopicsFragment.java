package app.semiwarm.cn.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.semiwarm.cn.R;
import butterknife.ButterKnife;

/**
 * 专题
 * A simple {@link Fragment} subclass.
 */
public class TopicsFragment extends Fragment {


    public TopicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_topics, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

}
