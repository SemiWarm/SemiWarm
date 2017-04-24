package app.semiwarm.cn.service;

import java.util.List;

import app.semiwarm.cn.entity.Category;
import app.semiwarm.cn.http.BaseResponse;
import retrofit2.http.GET;
import rx.Observable;

/**
 * 类目服务
 * Created by alibct on 2017/4/19.
 */

public interface CategoryService {
    @GET("categories")
    Observable<BaseResponse<List<Category>>> getAllCategories();
}
