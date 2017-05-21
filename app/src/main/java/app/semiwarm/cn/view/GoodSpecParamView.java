package app.semiwarm.cn.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.semiwarm.cn.entity.Goods;

/**
 * GoodSpecParamView
 * Created by alibct on 2017/5/19.
 */

public class GoodSpecParamView extends LinearLayout {

    private ImageView mGoodsBannerImageView;
    private TextView mGoodsPriceTextView;
    private TextView mSelectedParamsTextView;
    private Goods mGoods;

    public GoodSpecParamView(Context context) {
        this(context, null);
    }

    public GoodSpecParamView(Context context, @Nullable AttributeSet attrs) {
        this(context, null, 0);
    }

    public GoodSpecParamView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

//    public void initView() {
//        if (null != mGoods) {
//            String[] banners = mGoods.getGoodsBanners().split(";");
//            Glide.with(getContext()).load(banners[0]).into(mGoodsBannerImageView);
//        }
//        mGoodsPriceTextView.setText("¥ " + mGoods.getGoodsPrice());
//        mSelectedParamsTextView.setText("请选择规格参数");
//        // 开始处理规格参数数据
//        // 根据商品id请求商品所有规格参数
//        GoodsServiceObservable goodsService = new GoodsServiceObservable();
//        goodsService.getGoodsSpecParamsByGoodsId(mGoods.getGoodsId()).subscribe(new Subscriber<BaseResponse<List<GoodsSpecParam>>>() {
//            @Override
//            public void onCompleted() {
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//
//            }
//
//            @Override
//            public void onNext(BaseResponse<List<GoodsSpecParam>> listBaseResponse) {
//                if (listBaseResponse.getSuccess() == 1) {
//                    // 第一步获取规格名称
//                    String[] specNames = listBaseResponse.getData().get(0).getSpecParamName().split(",");
//                    // 第二步创建HashMap用于存储format之后的数据
//                    HashMap<String, HashSet<String>> specNameAndValues = new HashMap<>();
//                    // 第三步根据规格名称创建规格参数集
//                    for (int i = 0; i < specNames.length; i++) {
//                        // 创建规格参数集
//                        HashSet<String> specValues = new HashSet<>();
//                        // 根据商品规格的数量获取所有规格值，然后进行重复过滤
//                        for (GoodsSpecParam param : listBaseResponse.getData()) {
//                            String[] tempValues = param.getSpecParamValue().split(",");
//                            specValues.add(tempValues[i]);
//                        }
//                        // 添加规格
//                        specNameAndValues.put(specNames[i], specValues);
//                    }
//                    Log.i("format:", specNameAndValues.toString());
//                    // 根据specNameAndValues的长度初始化布局的个数
//                    Log.i("specNameAndValuesSize:", "" + specNameAndValues.size());
//
////                    TextView specName = new TextView(getContext());
////                    specName.setText("颜色");
////                    specName.setTextColor(Color.parseColor("#424242"));
////                    specName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
////                    LayoutParams specNameLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
////                    specName.setLayoutParams(specNameLayoutParams);
////                    addView(specName);
//                    // 遍历HashMap
////                    Set<String> set = specNameAndValues.keySet();
////                    Iterator nameIterator = set.iterator();
////                    while (nameIterator.hasNext()) {
//                    // 初始化组容器
////                        LinearLayout specLayout = new LinearLayout(getContext());
////                        specLayout.setOrientation(LinearLayout.VERTICAL);
////                        LayoutParams specLayoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
////                        specLayoutParams.setMargins(10, 10, 10, 10);
////                        specLayout.setLayoutParams(specLayoutParams);
//                    // 初始化规格名称
////                        TextView specName = new TextView(getContext());
////                        String name = (String) nameIterator.next();
////                        specName.setText(name);
////                        specName.setTextColor(Color.parseColor("#424242"));
////                        specName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
////                        LayoutParams specNameLayoutParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
////                        specName.setLayoutParams(specNameLayoutParams);
////                        specLayout.addView(specName);
//                    // 初始化RadioGroup
////                        RadioGroup specValueGroup = new RadioGroup(getContext());
////                        specValueGroup.setOrientation(RadioGroup.HORIZONTAL);
////                        LayoutParams specValueLayout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
////                        specValueLayout.setMargins(0, 10, 0, 0);
////                        specValueGroup.setLayoutParams(specValueLayout);
////                        // 根据规格参数个数初始化RadioButton
////                        HashSet<String> values = specNameAndValues.get(name);
////                        Iterator valueIterator = values.iterator();
////                        int i = 0;
////                        while (valueIterator.hasNext()) {
////                            i++;
////                            RadioButton specValue = new RadioButton(getContext());
////                            // 设置radioButton的属性
////                            specValue.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
////                            specValue.setText((String) valueIterator.next());
////                            specValue.setTextColor(Color.parseColor("#424242"));
////                            // 隐藏radioButton前面的小圆圈
////                            specValue.setButtonDrawable(android.R.color.transparent);
////                            // 设置radioButton布局
////                            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
////                            layoutParams.gravity = Gravity.CENTER;
////                            if (i > 1) {
////                                layoutParams.setMargins(15, 0, 0, 0);
////                            }
////                            specValue.setLayoutParams(layoutParams);
////                            specValueGroup.addView(specValue);
////                        }
////                        specLayout.addView(specValueGroup);
//
////                        addView(specLayout);
////                    }
//                }
//            }
//        });
//    }

    public void setGoods(Goods goods) {
        mGoods = goods;
    }
}
