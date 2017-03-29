package app.semiwarm.cn.entity;

import java.io.Serializable;

/**
 * 轮播通知类
 * Created by alibct on 2017/3/29.
 */

public class RotationNotice implements Serializable{

    private Long noticeId;
    private String noticeTitle;
    private String noticeDesc;

    public RotationNotice(Long noticeId, String noticeTitle, String noticeDesc) {
        this.noticeId = noticeId;
        this.noticeTitle = noticeTitle;
        this.noticeDesc = noticeDesc;
    }

    public Long getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Long noticeId) {
        this.noticeId = noticeId;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeDesc() {
        return noticeDesc;
    }

    public void setNoticeDesc(String noticeDesc) {
        this.noticeDesc = noticeDesc;
    }

    @Override
    public String toString() {
        return "RotationNotice{" +
                "noticeId=" + noticeId +
                ", noticeTitle='" + noticeTitle + '\'' +
                ", noticeDesc='" + noticeDesc + '\'' +
                '}';
    }
}
