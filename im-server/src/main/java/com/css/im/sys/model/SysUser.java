package com.css.im.sys.model;

import com.css.im.sys.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SysUser implements UserDetails, Serializable {
    /**
     * 主键
     *
     * @mbg.generated
     */
    private String userId;

    /**
     * 名字
     *
     * @mbg.generated
     */
    private String name;

    /**
     * 账户名称
     *
     * @mbg.generated
     */
    private String username;

    /**
     * 密码
     *
     * @mbg.generated
     */
    private String password;

    /**
     * 头像
     *
     * @mbg.generated
     */
    private String photo;

    /**
     * 状态
     *
     * @mbg.generated
     */
    private Short status;

    /**
     * 保存时间
     *
     * @mbg.generated
     */
    private String createTime;

    /**
     * 最后一次登录时间
     *
     * @mbg.generated
     */
    private String lastActiveTime;

    /**
     * 邮箱
     *
     * @mbg.generated
     */
    private String email;

    /**
     * 生日
     *
     * @mbg.generated
     */
    private String birthday;

    /**
     * 性别
     *
     * @mbg.generated
     */
    private Short sex;

    /**
     * 电话号码
     *
     * @mbg.generated
     */
    private String phone;

    /**
     * 序号
     *
     * @mbg.generated
     */
    private Integer orderNum;

    /**
     * 更新时间
     *
     * @mbg.generated
     */
    private String updateTime;

    /**
     * 创建人
     *
     * @mbg.generated
     */
    private String creater;

    /**
     * 手机号
     *
     * @mbg.generated
     */
    private String mobile;

    /**
     * 办公室
     *
     * @mbg.generated
     */
    private String office;

    /**
     * 个性签名
     *
     * @mbg.generated
     */
    private String sign;

    /**
     * 分机号
     *
     * @mbg.generated
     */
    private String extensionNum;

    /**
     * 职务
     *
     * @mbg.generated
     */
    private String duty;

    /**
     * 内网邮箱
     *
     * @mbg.generated
     */
    private String mail;

    /**
     * 校验token
     *
     * @mbg.generated
     */
    private String token;

    /**
     * 描述
     *
     * @mbg.generated
     */
    private String description;

    private String deptId;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    private static final long serialVersionUID = 1L;

    private List<SysMenu> menus = new ArrayList<SysMenu>();

    public List<SysMenu> getMenus() {
        return menus;
    }

    public void setMenus(List<SysMenu> menus) {
        this.menus = menus;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.status ==null || this.status.shortValue() != Status.UserStatus.Locked.status();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.status ==null || this.status.shortValue() == Status.UserStatus.Normal.status();
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.menus;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public String getLastActiveTime() {
        return lastActiveTime;
    }

    public void setLastActiveTime(String lastActiveTime) {
        this.lastActiveTime = lastActiveTime == null ? null : lastActiveTime.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday == null ? null : birthday.trim();
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office == null ? null : office.trim();
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign == null ? null : sign.trim();
    }

    public String getExtensionNum() {
        return extensionNum;
    }

    public void setExtensionNum(String extensionNum) {
        this.extensionNum = extensionNum == null ? null : extensionNum.trim();
    }

    public String getDuty() {
        return duty;
    }

    public void setDuty(String duty) {
        this.duty = duty == null ? null : duty.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", userId=").append(userId);
        sb.append(", name=").append(name);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", photo=").append(photo);
        sb.append(", status=").append(status);
        sb.append(", createTime=").append(createTime);
        sb.append(", lastActiveTime=").append(lastActiveTime);
        sb.append(", email=").append(email);
        sb.append(", birthday=").append(birthday);
        sb.append(", sex=").append(sex);
        sb.append(", phone=").append(phone);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", creater=").append(creater);
        sb.append(", mobile=").append(mobile);
        sb.append(", office=").append(office);
        sb.append(", sign=").append(sign);
        sb.append(", extensionNum=").append(extensionNum);
        sb.append(", duty=").append(duty);
        sb.append(", mail=").append(mail);
        sb.append(", token=").append(token);
        sb.append(", description=").append(description);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}