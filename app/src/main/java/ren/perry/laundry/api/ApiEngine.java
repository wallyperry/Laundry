package ren.perry.laundry.api;

import ren.perry.laundry.Constants;
import ren.perry.mvplibrary.net.NetClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author perry
 * @date 2017/11/22
 * WeChat 917351143
 */

public class ApiEngine {
    private volatile static ApiEngine apiEngine;

    private ApiEngine() {
    }

    public static ApiEngine getInstance() {
        if (apiEngine == null) {
            synchronized (ApiEngine.class) {
                if (apiEngine == null) {
                    apiEngine = new ApiEngine();
                }
            }
        }
        return apiEngine;
    }

    public ApiService getApiService() {
        return new Retrofit.Builder()
                .baseUrl(Constants.Api.BASE_URL)
                .client(NetClient.getInstance().getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(ApiService.class);
    }
}
