package entity;

import annotations.Table;
import annotations.Column;

@Table(name="article", Name="文章")
public class Article {
	@Column(isId=true, name="article_id", ChineseName="文章号")
	private Integer articleId;
	@Column(name="title", ChineseName="标题")
	private String title;
	@Column(name="author", ChineseName="作者")
	private String author;
	@Column(name="column_name", ChineseName="栏目名称")
	private String columnName;
	@Column(name="create_time", ChineseName="创建时间")
	private String createTime;
	@Column(name="isPass", ChineseName="状态")
	private Integer isPass;//0表示false, 1表示true
	
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
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
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public Integer isPass() {
		return isPass;
	}
	public void setPass(Integer isPass) {
		this.isPass = isPass;
	}
}
