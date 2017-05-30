package app.semiwarm.cn.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flipboard.bottomsheet.commons.BottomSheetFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import app.semiwarm.cn.R;
import app.semiwarm.cn.entity.CartGoods;
import app.semiwarm.cn.entity.Goods;
import app.semiwarm.cn.entity.GoodsSpecParam;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.service.observable.GoodsServiceObservable;
import app.semiwarm.cn.utils.DrawableUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoodsSpecParamFragment extends BottomSheetFragment implements View.OnClickListener {

    @BindView(R.id.ll_root)
    LinearLayout mRootLinearLayout;
    @BindView(R.id.iv_goods_banner)
    ImageView mGoodsBannerImageView;
    @BindView(R.id.tv_goods_price)
    TextView mGoodsPriceTextView;
    @BindView(R.id.ll_selected_params)
    LinearLayout mSelectedParamsLinearLayout;

    @BindView(R.id.btn_minus)
    Button mMinusCountButton;
    @BindView(R.id.et_goods_count)
    EditText mGoodsCountEditText;
    @BindView(R.id.btn_plus)
    Button mPlusCountButton;

    private List<TextView> mTextViewList;

    private Goods mGoods;
    private String[] mBanners;

    public GoodsSpecParamFragment() {
        EventBus.getDefault().register(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_spec_param, container, false);
        ButterKnife.bind(this, view);
        mMinusCountButton.setOnClickListener(this);
        mPlusCountButton.setOnClickListener(this);
        mGoods = (Goods) getArguments().getSerializable("Goods");
        if (null != mGoods) {
            initView(mGoods);
        }
        return view;
    }

    public void initView(Goods goods) {
        if (null != goods) {
            mBanners = goods.getGoodsBanners().split(";");
            Glide.with(getContext()).load(mBanners[0]).into(mGoodsBannerImageView);
        }
        mGoodsPriceTextView.setText("¥ " + goods.getGoodsPrice());
        // 开始处理规格参数数据
        // 根据商品id请求商品所有规格参数
        GoodsServiceObservable goodsService = new GoodsServiceObservable();
        goodsService.getGoodsSpecParamsByGoodsId(goods.getGoodsId()).subscribe(new Subscriber<BaseResponse<List<GoodsSpecParam>>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(BaseResponse<List<GoodsSpecParam>> listBaseResponse) {
                if (listBaseResponse.getSuccess() == 1) {
                    // 第一步获取规格名称
                    final String[] specNames = listBaseResponse.getData().get(0).getSpecParamName().split(",");
                    // 根据specNames的个数创建TextView
                    mTextViewList = new ArrayList<>();
                    for (int i = 0; i < specNames.length; i++) {
                        TextView textView = new TextView(getContext());
                        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        if (i > 0) {
                            textViewLayoutParams.setMarginStart(10);
                        }
                        textView.setLayoutParams(textViewLayoutParams);
                        mSelectedParamsLinearLayout.addView(textView);
                        mTextViewList.add(textView);
                    }
                    // 第二步创建HashMap用于存储format之后的数据
                    final HashMap<String, HashSet<String>> specNameAndValues = new HashMap<>();
                    // 第三步根据规格名称创建规格参数集
                    for (int i = 0; i < specNames.length; i++) {
                        // 创建规格参数集
                        HashSet<String> specValues = new HashSet<>();
                        // 根据商品规格的数量获取所有规格值，然后进行重复过滤
                        for (GoodsSpecParam param : listBaseResponse.getData()) {
                            String[] tempValues = param.getSpecParamValue().split(",");
                            specValues.add(tempValues[i]);
                        }
                        // 添加规格
                        specNameAndValues.put(specNames[i], specValues);
                    }
                    Log.i("format:", specNameAndValues.toString());
                    // 根据specNameAndValues的长度初始化布局的个数
                    Log.i("specNameAndValuesSize:", "" + specNameAndValues.size());
                    // 遍历HashMap
                    Set<String> set = specNameAndValues.keySet();
                    Iterator nameIterator = set.iterator();
                    while (nameIterator.hasNext()) {
                        // 初始化组容器
                        LinearLayout specLayout = new LinearLayout(getContext());
                        specLayout.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams specLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        specLayoutParams.setMargins(30, 10, 10, 10);
                        specLayout.setLayoutParams(specLayoutParams);
                        // 初始化规格名称
                        final TextView specName = new TextView(getContext());
                        String name = (String) nameIterator.next();
                        specName.setText(name);
                        specName.setTextColor(Color.parseColor("#424242"));
                        specName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
                        LinearLayout.LayoutParams specNameLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        specName.setLayoutParams(specNameLayoutParams);
                        specLayout.addView(specName);
                        // 初始化RadioGroup
                        RadioGroup specValueGroup = new RadioGroup(getContext());
                        specValueGroup.setOrientation(RadioGroup.HORIZONTAL);
                        LinearLayout.LayoutParams specValueLayout = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        specValueLayout.setMargins(0, 10, 0, 0);
                        specValueGroup.setLayoutParams(specValueLayout);
                        // 根据规格参数个数初始化RadioButton
                        HashSet<String> values = specNameAndValues.get(name);
                        Iterator valueIterator = values.iterator();
                        int i = 0;
                        while (valueIterator.hasNext()) {
                            i++;
                            RadioButton specValue = new RadioButton(getContext());
                            // 设置radioButton的属性
                            specValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                            specValue.setText((String) valueIterator.next());
                            specValue.setTextColor(Color.parseColor("#424242"));
                            specValue.setPadding(10, 10, 10, 10);
                            specValue.setGravity(Gravity.CENTER);
                            specValue.setBackground(DrawableUtils.getSelector(DrawableUtils.getDrawable(Color.parseColor("#424242"), 5), DrawableUtils.getDrawable(Color.parseColor("#C62828"), 5)));
                            // 隐藏radioButton前面的小圆圈
                            specValue.setButtonDrawable(android.R.color.transparent);
                            // 设置radioButton布局
                            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(150, 80);
                            layoutParams.gravity = Gravity.CENTER;
                            if (i > 1) {
                                layoutParams.setMargins(15, 0, 0, 0);
                            }
                            specValue.setLayoutParams(layoutParams);
                            specValueGroup.addView(specValue);
                        }
                        specValueGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                                Log.i("CheckedId", "" + checkedId);
                                RadioButton checkedButton = (RadioButton) group.findViewById(checkedId);
                                Log.i("CheckedButton", checkedButton.getText().toString());
                                // 第i组的参数放在第i个TextView中
                                for (int i1 = 0; i1 < specNames.length; i1++) {
                                    if (specNameAndValues.get(specNames[i1]).contains(checkedButton.getText().toString())) {
                                        mTextViewList.get(i1).setText(checkedButton.getText().toString());
                                    }
                                }
                            }
                        });
                        specLayout.addView(specValueGroup);
                        mRootLinearLayout.addView(specLayout);
                    }
                }
            }
        });
    }

    @Subscribe
    public void onEvent(Integer code) {
        if (code == 1000) {
            // 接收到获取商品参数的请求，开始整理商品参数参数
            // 整理完成后开始向Activity发送数据
            CartGoods cartGoods = new CartGoods();
            cartGoods.setGoodsId(mGoods.getGoodsId());
            cartGoods.setGoodsBanner(mBanners[0]);
            cartGoods.setGoodsTitle(mGoods.getGoodsTitle());
            String goodsSpecParam = "";
            for (TextView textView : mTextViewList) {
                goodsSpecParam += textView.getText().toString() + " ";
            }
            cartGoods.setGoodsSpecParam(goodsSpecParam);
            cartGoods.setGoodsCount(Integer.valueOf(mGoodsCountEditText.getText().toString()));
            cartGoods.setGoodsPrice("¥" + mGoods.getGoodsPrice());
            cartGoods.setIsChecked(true);
            EventBus.getDefault().post(cartGoods);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_minus:
                if (Integer.valueOf(mGoodsCountEditText.getText().toString()) > 1) {
                    mGoodsCountEditText.setText(String.valueOf(Integer.valueOf(mGoodsCountEditText.getText().toString()) - 1));
                } else {
                    mGoodsCountEditText.setText(String.valueOf(1));
                }
                break;
            case R.id.btn_plus:
                // 这里本来需要获取该类别的商品个数的，但是为了简化写法直接加一，默认商品个数无限大
                mGoodsCountEditText.setText(String.valueOf(Integer.valueOf(mGoodsCountEditText.getText().toString()) + 1));
                break;
        }
    }
}
