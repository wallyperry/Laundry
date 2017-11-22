package ren.perry.laundry.mvp.model;

import java.util.List;

import ren.perry.laundry.api.ApiEngine;
import ren.perry.laundry.bean.ListBean;
import ren.perry.laundry.mvp.contract.MainContract;
import ren.perry.mvplibrary.rx.RxSchedulers;
import rx.Observable;

/**
 * @author perry
 * @date 2017/11/22
 * WeChat 917351143
 */

public class MainModel implements MainContract.Model {
    @Override
    public Observable<List<ListBean>> getData(int page, int size) {
        return ApiEngine.getInstance()
                .getApiService()
                .getData(page, size)
                .compose(RxSchedulers.switchThread());
    }
}
