package app.semiwarm.cn.service.observable;

import java.util.List;

import app.semiwarm.cn.entity.SubCategory;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.http.ObservableLoader;
import app.semiwarm.cn.http.RetrofitServiceManager;
import app.semiwarm.cn.service.SubCategoryService;
import rx.Observable;

/**
 * SubCategoryServiceObservable
 * Created by alibct on 2017/5/6.
 */

public class SubCategoryServiceObservable extends ObservableLoader {

    private SubCategoryService mSubCategoryService;

    public SubCategoryServiceObservable() {
        mSubCategoryService = RetrofitServiceManager.getInstance().create(SubCategoryService.class);
    }

    public Observable<BaseResponse<List<SubCategory>>> getSubCategoriesByCategoryId(Integer categoryId) {
        return observable(mSubCategoryService.getSubCategoriesByCategoryId(categoryId));
    }
}
