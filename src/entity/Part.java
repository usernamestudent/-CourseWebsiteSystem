package entity;

import annotations.Column;
import annotations.Table;

@Table(name="part", Name="À¸Ä¿")
public class Part {
	@Column(isId=true, name="id", ChineseName="À¸Ä¿Id", type="Integer")
	private Integer id;
	@Column(name="column_name", ChineseName="À¸Ä¿Ãû")
	private String columnName;
	@Column(name="father_column", ChineseName="¸¸À¸Ä¿")
	private String fatherColumn;
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
	public String getfatherColumn() {
		return fatherColumn;
	}
	public void setfatherColumn(String fatherColumn) {
		this.fatherColumn = fatherColumn;
	}
}
