package app.semiwarm.cn.service.observable;

import java.util.List;

import app.semiwarm.cn.entity.Category;
import app.semiwarm.cn.http.BaseResponse;
import app.semiwarm.cn.http.ObservableLoader;
import app.semiwarm.cn.http.RetrofitServiceManager;
import app.semiwarm.cn.service.CategoryService;
import rx.Observable;

/**
 * 类目服务类观察者类
 * Created by alibct on 2017/4/20.
 */

public class CategoryServiceObservable extends ObservableLoader {

    private CategoryService mCategoryService;

    public CategoryServiceObservable() {
        mCategoryService = RetrofitServiceManager.getInstance().create(CategoryService.class);
    }

    public Observable<BaseResponse<List<Category>>> getAllCategories() {
        return observable(mCategoryService.getAllCategories());
    }
}
