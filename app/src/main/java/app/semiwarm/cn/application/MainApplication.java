package app.semiwarm.cn.application;

import android.app.Application;

import org.greenrobot.greendao.database.Database;

import app.semiwarm.cn.entity.DaoMaster;
import app.semiwarm.cn.entity.DaoSession;

/**
 * 主进程
 * Created by alibct on 2017/5/25.
 */

public class MainApplication extends Application {

    private static MainApplication instance;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "CartGoods-db", null);
        Database db = helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public static MainApplication getInstance() {
        return instance;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
