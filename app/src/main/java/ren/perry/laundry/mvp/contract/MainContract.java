package ren.perry.laundry.mvp.contract;

import java.util.List;

import ren.perry.laundry.bean.ListBean;
import ren.perry.mvplibrary.base.BaseModel;
import ren.perry.mvplibrary.base.BasePresenter;
import ren.perry.mvplibrary.base.BaseView;
import ren.perry.mvplibrary.net.ApiException;
import rx.Observable;

/**
 * @author perry
 * @date 2017/11/22
 * WeChat 917351143
 */

public interface MainContract {
    interface View extends BaseView {
        void onListSuccess(List<ListBean> beans);

        void onListError(ApiException.ResponseException e);

    }

    interface Model extends BaseModel {
        Observable<List<ListBean>> getData(int page, int size);
    }

    abstract class Presenter extends BasePresenter<View, Model> {
        public abstract void getData(int page, int size);

    }
}
