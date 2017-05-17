package app.semiwarm.cn.service;

import java.util.List;

import app.semiwarm.cn.entity.Goods;
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
}
