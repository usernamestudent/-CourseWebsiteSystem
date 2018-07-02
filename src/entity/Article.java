package entity;

import java.sql.Date;

import annotations.Column;
import annotations.Table;

@Table(name="article", Name="����")
public class Article {
	@Column(isId=true, name="id", ChineseName="���º�", type="Integer")
	private Integer id;
	@Column(name="title", ChineseName="����")
	private String title;
	@Column(name="author", ChineseName="����")
	private String author;
	@Column(name="column_name", ChineseName="��Ŀ����")
	private String columnName;
	@Column(name="create_time", ChineseName="����ʱ��", type="Date")
	private Date createTime;
	@Column(name="isPass", ChineseName="״̬", type="Integer")
	private Integer isPass;//0��ʾfalse, 1��ʾtrue
	@Column(name="s_title", ChineseName="�ؼ��ֱ���")
	private String s_title;
	@Column(name="s_keywords", ChineseName="���ݹؼ���")
	private String s_keywords;
	@Column(name="s_desc", ChineseName="�ؼ�������")
	private String s_desc;
	@Column(name="note", ChineseName="����")
	private String note;
	@Column(name="content", ChineseName="����")
	private String content;
	@Column(name="approver", ChineseName="�����")
	private String approver;
	@Column(name="image", ChineseName="ͼƬ")
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