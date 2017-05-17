package app.semiwarm.cn.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.flipboard.bottomsheet.BottomSheetLayout;

import app.semiwarm.cn.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品信息Fragment
 * A simple {@link Fragment} subclass.
 */
public class GoodsInfoFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.bottomsheet)
    BottomSheetLayout mBottomSheetLayout;
    @BindView(R.id.ll_select_goods_spec)
    LinearLayout mSelectGoodsSpec;

    public GoodsInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_goods_info, container, false);
        ButterKnife.bind(this, view);
        mSelectGoodsSpec.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_goods_spec:
                String[] spec = new String[]{"颜色","尺寸"};
                View view = LayoutInflater.from(getContext()).inflate(R.layout.view_goods_spec, mBottomSheetLayout, false);
                // 需要动态加载规格参数信息
                LinearLayout goodsSpecRootLayout = (LinearLayout) view.findViewById(R.id.ll_goods_spec_root); // 获取布局根结点





                mBottomSheetLayout.showWithSheetView(view);
                break;
        }
    }
}
