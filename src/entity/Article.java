package entity;

import java.sql.Date;

import annotations.Column;
import annotations.Table;

@Table(name="article", Name="文章")
public class Article {
	@Column(isId=true, name="id", ChineseName="文章号", type="Integer")
	private Integer id;
	@Column(name="title", ChineseName="标题")
	private String title;
	@Column(name="author", ChineseName="作者")
	private String author;
	@Column(name="column_name", ChineseName="栏目名称")
	private String columnName;
	@Column(name="create_time", ChineseName="创建时间", type="Date")
	private Date createTime;
	@Column(name="isPass", ChineseName="状态", type="Integer")
	private Integer isPass;//0表示false, 1表示true
	@Column(name="s_title", ChineseName="关键字标题")
	private String s_title;
	@Column(name="s_keywords", ChineseName="内容关键字")
	private String s_keywords;
	@Column(name="s_desc", ChineseName="关键字描述")
	private String s_desc;
	@Column(name="note", ChineseName="描述")
	private String note;
	@Column(name="content", ChineseName="内容")
	private String content;
	@Column(name="approver", ChineseName="审核人")
	private String approver;
	@Column(name="image", ChineseName="图片")
	private String image;
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Integer getIsPass() {
		return isPass;
	}
	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer isPass() {
		return isPass;
	}
	public void setPass(Integer isPass) {
		this.isPass = isPass;
	}
	public String getS_title() {
		return s_title;
	}
	public void setS_title(String s_title) {
		this.s_title = s_title;
	}
	public String getS_keywords() {
		return s_keywords;
	}
	public void setS_keywords(String s_keywords) {
		this.s_keywords = s_keywords;
	}
	public String getS_desc() {
		return s_desc;
	}
	public void setS_desc(String s_desc) {
		this.s_desc = s_desc;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getContent() {
		return content;
	}
}