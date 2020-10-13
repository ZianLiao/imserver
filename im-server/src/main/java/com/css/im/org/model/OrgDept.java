package com.css.im.org.model;

import com.css.im.sys.model.SysUser;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class OrgDept implements Serializable {
    /**
     * 主键
     *
     * @mbg.generated
     */
    private String deptId;

    /**
     * 名称
     *
     * @mbg.generated
     */
    private String deptName;

    /**
     * 父id
     *
     * @mbg.generated
     */
    private String fatherId;

    /**
     * 编码
     *
     * @mbg.generated
     */
    private String deptCode;

    /**
     * 创建时间
     *
     * @mbg.generated
     */
    private String createTime;

    /**
     * 状态
     *
     * @mbg.generated
     */
    private Short status;

    /**
     * 创建人id
     *
     * @mbg.generated
     */
    private String creater;

    /**
     * 备注
     *
     * @mbg.generated
     */
    private String remark;

    /**
     * 排序
     *
     * @mbg.generated
     */
    private Integer orderNum;

    public List<OrgDept> subDepts = new ArrayList<OrgDept>();

    private List<SysUser> members = new ArrayList<>();

    public List<SysUser> getMembers() {
        return members;
    }

    public void setMembers(List<SysUser> members) {
        this.members = members;
    }

    private static final long serialVersionUID = 1L;

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId == null ? null : fatherId.trim();
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", deptId=").append(deptId);
        sb.append(", deptName=").append(deptName);
        sb.append(", fatherId=").append(fatherId);
        sb.append(", deptCode=").append(deptCode);
        sb.append(", createTime=").append(createTime);
        sb.append(", status=").append(status);
        sb.append(", creater=").append(creater);
        sb.append(", remark=").append(remark);
        sb.append(", orderNum=").append(orderNum);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}