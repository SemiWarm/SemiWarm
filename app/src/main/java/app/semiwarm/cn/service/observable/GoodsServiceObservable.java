package app.semiwarm.cn.service.observable;

import java.util.List;

import app.semiwarm.cn.entity.Goods;
import app.semiwarm.cn.entity.GoodsSpecParam;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.http.ObservableLoader;
import app.semiwarm.cn.http.RetrofitServiceManager;
import app.semiwarm.cn.service.GoodsService;
import rx.Observable;

/**
 * GoodsServiceObservable
 * Created by alibct on 2017/5/17.
 */

public class GoodsServiceObservable extends ObservableLoader {

    private GoodsService mGoodsService;

    public GoodsServiceObservable() {
        mGoodsService = RetrofitServiceManager.getInstance().create(GoodsService.class);
    }

    public Observable<BaseResponse<List<Goods>>> searchGoods(String searchText) {
        return observable(mGoodsService.searchGoods(searchText));
    }

    public Observable<BaseResponse<Goods>> getGoodsById(Long id) {
        return observable(mGoodsService.getGoodsById(id));
    }

    public Observable<BaseResponse<List<GoodsSpecParam>>> getGoodsSpecParamsByGoodsId(Long id) {
        return observable(mGoodsService.getGoodsSpecParamsByGoodsId(id));
    }

    public Observable<BaseResponse<List<Goods>>> getAllGoodsBySubCategoryId(Integer id) {
        return observable(mGoodsService.getAllGoodsBySubCategoryId(id));
    }
}
