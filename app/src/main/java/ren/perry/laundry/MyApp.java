package ren.perry.laundry;

import ren.perry.mvplibrary.BaseMvpApplication;

/**
 * @author perry
 * @date 2017/11/22
 * WeChat 917351143
 */

public class MyApp extends BaseMvpApplication {
    private static MyApp mContext;

    @Override
    public void setTimeout(int connect, int read, int write) {
        super.setTimeout(15, 12, 12);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mContext == null) {
            mContext = this;
        }
    }

    public static MyApp getContext() {
        return mContext;
    }
}
