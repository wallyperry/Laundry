package ren.perry.laundry.ui;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import ren.perry.laundry.R;
import ren.perry.laundry.adapter.MainAdapter;
import ren.perry.laundry.bean.ListBean;
import ren.perry.laundry.mvp.presenter.MainPresenter;
import ren.perry.laundry.mvp.contract.MainContract;
import ren.perry.laundry.utils.UiUtils;
import ren.perry.mvplibrary.base.BaseActivity;
import ren.perry.mvplibrary.net.ApiException;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.refreshLayout)
    SwipeRefreshLayout refreshLayout;
    @Bind(R.id.fabTop)
    FloatingActionButton fabTop;

    private int pageCount = 1;
    private final int pageSize = 30;
    private boolean isLoadMore = false;
    private int lastPosition;
    private final int visibleFabPosition = 12;
    private MainAdapter rvAdapter;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        initToolBar();
        initRefreshLayout();
        initRecyclerView();
    }

    private void fetchData() {
        mPresenter.getData(pageCount, pageSize);
    }

    private void initToolBar() {
        setSupportActionBar(toolbar);
        setTitle(UiUtils.getString(R.string.app_name));
    }

    private void initRefreshLayout() {
        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimary, R.color.colorPrimary);
        refreshLayout.setOnRefreshListener(() -> {
            pageCount = 1;
            fetchData();
        });
    }

    private void initRecyclerView() {
        rvAdapter = new MainAdapter();
        rvAdapter.bindToRecyclerView(recyclerView);
        rvAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            ListBean bean = (ListBean) adapter.getData().get(position);
            switch (view.getId()) {
                case R.id.cv:
                    new FinestWebView.Builder(this)
                            .statusBarColorRes(R.color.colorPrimary_c)
                            .toolbarColorRes(R.color.colorPrimary)
                            .titleColorRes(R.color.white)
                            .progressBarColorRes(R.color.white)
                            .swipeRefreshColorRes(R.color.colorPrimary)
                            .urlColorRes(R.color.white)
                            .iconDefaultColorRes(R.color.white)
                            .webViewJavaScriptEnabled(true)
                            .webViewBuiltInZoomControls(true)
                            .webViewDisplayZoomControls(false)
                            .webViewSupportZoom(true)
                            .webViewDomStorageEnabled(true)
                            .webViewDatabaseEnabled(true)
                            .webViewLoadWithOverviewMode(true)
                            .webViewUseWideViewPort(true)
                            .show("http:" + bean.getUrl());
                    break;
            }

        });
        rvAdapter.setEnableLoadMore(true);
        rvAdapter.setOnLoadMoreListener(() -> {
            pageCount++;
            isLoadMore = true;
            fetchData();
        }, recyclerView);
        rvAdapter.disableLoadMoreIfNotFullPage(recyclerView);

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int position = lm.findLastVisibleItemPosition();
                if (lastPosition < position && lastPosition >= visibleFabPosition) {
                    fabTop.show();
                }
                if (lastPosition > position && fabTop.isShown()) {
                    fabTop.hide();
                }
                lastPosition = position;
            }
        });
        rvAdapter.setEmptyView(R.layout.view_rv_loading);
        fetchData();
    }

    @Override
    protected MainPresenter onCreatePresenter() {
        return new MainPresenter(this);
    }

    @OnClick(R.id.fabTop)
    public void onViewClicked() {
        recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onListSuccess(List<ListBean> bean) {
        refreshLayout.setRefreshing(false);
        if (isLoadMore) {
            isLoadMore = false;
            if (bean.size() >= 1) {
                rvAdapter.addData(bean);
                rvAdapter.loadMoreComplete();
            }
        } else {
            if (bean.size() < 1) {
                rvAdapter.setEmptyView(R.layout.view_rv_empty);
            } else {
                rvAdapter.setNewData(bean);
            }
        }
        if (bean.size() != pageSize) {
            rvAdapter.loadMoreEnd();
        }
    }

    @Override
    public void onListError(ApiException.ResponseException e) {
        refreshLayout.setRefreshing(false);
        if (isLoadMore) {
            isLoadMore = false;
            rvAdapter.loadMoreFail();
        } else {
            rvAdapter.setEmptyView(UiUtils.getView(R.layout.view_rv_fail));
            ((TextView) UiUtils.getView(R.layout.view_rv_fail).findViewById(R.id.tvMsg)).setText(e.message);
        }
    }
}
