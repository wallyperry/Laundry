package ren.perry.laundry.bean;

import lombok.Data;

/**
 * @author perry
 * @date 2017/11/22
 * WeChat 917351143
 */
@SuppressWarnings("SpellCheckingInspection")
@Data
public class ListBean {
    /**
     * id : 205870318
     * authorId : 654666
     * authorName : 生活洗衣百科
     * title : 洗衣店长：您说洗皮衣价格贵，我把十三步工艺告诉您！
     * picUrl : http://5b0988e595225.cdn.sohucs.com/images/20171122/bf92bdeb1848460db18da029ced01c16.jpeg
     * publicTime : 1511280000000
     * url : //www.sohu.com/a/205870318_654666
     */

    private int id;
    private int authorId;
    private String authorName;
    private String title;
    private String picUrl;
    private long publicTime;
    private String url;
}
