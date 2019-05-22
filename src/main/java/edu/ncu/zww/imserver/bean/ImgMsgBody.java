package edu.ncu.zww.imserver.bean;

import java.io.Serializable;

public class ImgMsgBody implements Serializable {

    //缩略图文件的本地路径
    private String thumbPath;
    //缩略图远程地址
    private String thumbUrl;

    public String getThumbPath() {
        return thumbPath;
    }

    public void setThumbPath(String thumbPath) {
        this.thumbPath = thumbPath;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

}
