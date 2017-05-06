package app.semiwarm.cn.service;

import java.util.List;

import app.semiwarm.cn.entity.SubCategory;
import app.semiwarm.cn.http.BaseResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * 子类目服务
 * Created by alibct on 2017/5/6.
 */

public interface SubCategoryService {
    /**
     * 根据类目Id获取子类目列表
     *
     * @param categoryId 类目ID
     * @return 子类目列表
     */
    @GET("subCategories/categoryId/{categoryId}")
    Observable<BaseResponse<List<SubCategory>>> getSubCategoriesByCategoryId(@Path("categoryId") Integer categoryId);
}
