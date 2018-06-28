package entity;

import annotations.Column;
import annotations.Table;

@Table(name="coursegroup", Name="课程组")
public class CourseGroup {
	@Column(isId=true, name="id", ChineseName="课程组号")
	private Integer id;
	@Column(name="column_name", ChineseName="课程组成员")
	private String columnName;
	@Column(name="isHead", ChineseName="是否为负责人")
	private Integer isHead;//0表示false,1表示true
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Integer isHead() {
		return isHead;
	}
	public void setHead(Integer isHead) {
		this.isHead = isHead;
	}
}
