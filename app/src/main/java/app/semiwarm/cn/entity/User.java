package app.semiwarm.cn.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户类
 * Created by alibct on 2017/3/5.
 */

public class User implements Serializable {
    private Long userId; // 用户ID
    private String userName; // 用户名称
    private String userAccount; // 用户账号
    private String loginPassword; // 密码
    private Boolean status; // 账号是否可用->默认可用
    private Date createAt;// 用户创建时间

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userAccount='" + userAccount + '\'' +
                ", loginPassword='" + loginPassword + '\'' +
                ", status=" + status +
                ", createAt=" + createAt +
                '}';
    }
}
