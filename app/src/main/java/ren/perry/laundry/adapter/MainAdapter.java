package ren.perry.laundry.adapter;

import android.annotation.SuppressLint;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.text.SimpleDateFormat;
import java.util.Date;

import ren.perry.laundry.R;
import ren.perry.laundry.bean.ListBean;
import ren.perry.library.GlideMan;

/**
 * @author perry
 * @date 2017/11/22
 * WeChat 917351143
 */

public class MainAdapter extends BaseQuickAdapter<ListBean, BaseViewHolder> {
    public MainAdapter() {
        super(R.layout.item_main_rv);
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        String timeStr = new SimpleDateFormat("yyyy-MM-dd")
                .format(new Date(item.getPublicTime()));

        helper.setText(R.id.tvTitle, item.getTitle())
                .setText(R.id.tvName, item.getAuthorName())
                .setText(R.id.tvTime, timeStr)
                .addOnClickListener(R.id.cv);

        new GlideMan.Builder()
                .load(item.getPicUrl())
                .loadingRes(R.mipmap.ic_launcher)
                .loadFailRes(R.mipmap.ic_launcher)
                .into(helper.getView(R.id.imageView));

    }
}
