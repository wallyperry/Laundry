package ren.perry.laundry.api;

import java.util.List;

import ren.perry.laundry.bean.ListBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author perry
 * @date 2017/11/22
 * WeChat 917351143
 */

public interface ApiService {

    /**
     * 获取列表
     *
     * @param page 页数
     * @param size 每页数量
     * @return result
     */
    @GET("/public-api/authors/654666/articles")
    Observable<List<ListBean>> getData(
            @Query("page") int page,
            @Query("size") int size);
}
