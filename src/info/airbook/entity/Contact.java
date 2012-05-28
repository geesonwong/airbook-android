package info.airbook.entity;

import java.io.Serializable;

/**
 * 联系人数据
 * 
 * 
 */
public class Contact implements Serializable {

	private static final long serialVersionUID = 2735631431392429220L;
	private String id;
	private String name;
	private String baseEmail;
	private String basePhone;
	private String firstName;
	private String lastName;
	private String photoPath;
	private String createTime;
	private String comment;
	private int type;
	private String qq;
	private String homePage;
	private String addr;
	private String tags;
	private String pigeohole;
	private int state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getPigeohole() {
		return pigeohole;
	}

	public void setPigeohole(String pigeohole) {
		this.pigeohole = pigeohole;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

}
