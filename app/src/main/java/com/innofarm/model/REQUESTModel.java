package com.innofarm.model;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by dell on 2015/10/22.
 */
@Table(name = "REQUESTModel")
public class REQUESTModel {



    @Id
    public int  id;//请求时间事件
    @Column(column = "Content")
    public String Content;//内容
    @Column(column = "url")
    public String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }
}
