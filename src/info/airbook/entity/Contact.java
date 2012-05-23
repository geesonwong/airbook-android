package info.airbook.entity;

import java.util.Date;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 联系人数据
 * 
 * 
 */
public class Contact implements Parcelable {

	private Integer id;
	private String name;
	private String baseEmail;
	private String basePhone;
	private String firstName;
	private String lastName;
	private String photoPath;
	private Date createTime;
	private String remark;
	private int type;
	private String qq;
	private String homePage;
	private String addr;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBaseEmail() {
		return baseEmail;
	}

	public void setBaseEmail(String baseEmail) {
		this.baseEmail = baseEmail;
	}

	public String getBasePhone() {
		return basePhone;
	}

	public void setBasePhone(String basePhone) {
		this.basePhone = basePhone;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
	}
}
