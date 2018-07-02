package entity;

import java.sql.Date;

import annotations.Column;
import annotations.Table;

@Table(name="course", Name="课程")
public class Course {
	@Column(isId=true, name="id", ChineseName="课程Id", type="Integer")
	private Integer id;
	@Column(name="name", ChineseName="课程名")
	private String name;
	@Column(name="detail", ChineseName="课程简介")
	private String detail;
	@Column(name="image", ChineseName="课程图片")
	private String image;
	@Column(name="video", ChineseName="课程视频")
	private String video;
	@Column(name="create_time", ChineseName="创建时间", type="Date")
	private Date createTime;
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
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getVideo() {
		return video;
	}
	public void setVideo(String video) {
		this.video = video;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
