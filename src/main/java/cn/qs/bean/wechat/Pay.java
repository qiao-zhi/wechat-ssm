package cn.qs.bean.wechat;

import java.util.Date;

import javax.persistence.Entity;

import cn.qs.bean.AbstractSequenceEntity;

/**
 * 缴费信息
 * 
 * @author QiaoLiQiang
 * @time 2019年12月11日下午10:25:30
 */
@Entity
public class Pay extends AbstractSequenceEntity {

	/**
	 * 幼儿园ID
	 */
	private Integer kindergartenId;

	private Integer userId;

	private Integer username;

	/**
	 * 学期
	 */
	private String semester;

	/**
	 * 缴费日期
	 */
	private Date payDate;

	private String parentName;

	private String parentPhone;

	private String childrenPhone;

	/**
	 * 年级
	 */
	private String grade;

	/**
	 * 班级
	 */
	private String classNum;

	/**
	 * 地址
	 */
	private String address;

	/**
	 * 收费金额
	 */
	private Float payAmount;

	/**
	 * 本次缴费生成的二维码
	 */
	private String qrcodePath;

	public Integer getKindergartenId() {
		return kindergartenId;
	}

	public void setKindergartenId(Integer kindergartenId) {
		this.kindergartenId = kindergartenId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getUsername() {
		return username;
	}

	public void setUsername(Integer username) {
		this.username = username;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public Date getPayDate() {
		return payDate;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getParentPhone() {
		return parentPhone;
	}

	public void setParentPhone(String parentPhone) {
		this.parentPhone = parentPhone;
	}

	public String getChildrenPhone() {
		return childrenPhone;
	}

	public void setChildrenPhone(String childrenPhone) {
		this.childrenPhone = childrenPhone;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getClassNum() {
		return classNum;
	}

	public void setClassNum(String classNum) {
		this.classNum = classNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Float getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(Float payAmount) {
		this.payAmount = payAmount;
	}

	public String getQrcodePath() {
		return qrcodePath;
	}

	public void setQrcodePath(String qrcodePath) {
		this.qrcodePath = qrcodePath;
	}

}
