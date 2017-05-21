package app.semiwarm.cn.service;

import java.util.List;

import app.semiwarm.cn.entity.Goods;
import app.semiwarm.cn.entity.GoodsSpecParam;
import app.semiwarm.cn.http.BaseResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * GoodsService
 * Created by alibct on 2017/5/17.
 */

public interface GoodsService {
    @GET("search/{searchText}/goods")
    Observable<BaseResponse<List<Goods>>> searchGoods(@Path("searchText") String searchText);

    @GET("goodsId/{id}/goods")
    Observable<BaseResponse<Goods>> getGoodsById(@Path("id") Long id);

    @GET("goodsId/{id}/specParams")
    Observable<BaseResponse<List<GoodsSpecParam>>> getGoodsSpecParamsByGoodsId(@Path("id") Long id);

    @GET("subCategoryId/{id}/goods")
    Observable<BaseResponse<List<Goods>>> getAllGoodsBySubCategoryId(@Path("id") Integer id);
}
