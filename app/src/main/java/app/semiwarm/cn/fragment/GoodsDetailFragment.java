package app.semiwarm.cn.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import app.semiwarm.cn.R;
import app.semiwarm.cn.entity.Goods;
import app.semiwarm.cn.view.VerticalWebView;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 商品详情Fragment
 * A simple {@link Fragment} subclass.
 */
public class GoodsDetailFragment extends BaseFragment {

    @BindView(R.id.vwv_goods_detail)
    VerticalWebView mGoodsDetailWebView;
    @BindView(R.id.progressbar)
    ProgressBar mProgressBar;

    private boolean hasInited = false;

    public GoodsDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goods_detail, container, false);
        ButterKnife.bind(this, view);
        // 获取Goods
        Goods goods = (Goods) getArguments().getSerializable("Goods");

        if (null != goods && null != mGoodsDetailWebView) {
            hasInited = true;
            mProgressBar.setVisibility(View.GONE);
            mGoodsDetailWebView.loadDataWithBaseURL(null, goods.getGoodsHtmlDetail(), "text/html", "utf-8", null);
            WebSettings settings = mGoodsDetailWebView.getSettings();
            settings.setJavaScriptEnabled(true);
            mGoodsDetailWebView.setWebViewClient(new GoodsDetailWebViewClient());
        }
        return view;
    }

    @Override
    public void goTop() {
        mGoodsDetailWebView.goTop();
    }


    private class GoodsDetailWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            //  html加载完成之后，调用js的方法
            imgReset();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        private void imgReset() {
            mGoodsDetailWebView.loadUrl("javascript:(function(){"
                    + "var objs = document.getElementsByTagName('img'); "
                    + "for(var i=0;i<objs.length;i++)  " + "{"
                    + "var img = objs[i];   "
                    + "    img.style.width = '100%';   "
                    + "    img.style.height = 'auto';   "
                    + "}" + "})()");
        }
    }
}
