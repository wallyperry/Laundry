package ren.perry.laundry.mvp.presenter;

import java.util.List;

import ren.perry.laundry.bean.ListBean;
import ren.perry.laundry.mvp.contract.MainContract;
import ren.perry.laundry.mvp.model.MainModel;
import ren.perry.mvplibrary.net.ApiException;
import ren.perry.mvplibrary.rx.BaseSubscriber;

/**
 * @author perry
 * @date 2017/11/22
 * WeChat 917351143
 */

public class MainPresenter extends MainContract.Presenter {

    public MainPresenter(MainContract.View view) {
        mView = view;
        mModel = new MainModel();
    }

    @Override
    public void getData(int page, int size) {
        addSubscribe(mModel.getData(page, size)
                .subscribe(new BaseSubscriber<List<ListBean>>() {
                    @Override
                    public void onError(ApiException.ResponseException e) {
                        mView.onListError(e);
                    }

                    @Override
                    public void onNext(List<ListBean> listBeans) {
                        mView.onListSuccess(listBeans);
                    }
                }));
    }
}
