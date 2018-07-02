function display() {
	var htmlobj=$.ajax({url:"columnShow.html",async:false});
	$("#myDiv").html(htmlobj.responseText);
	showColumn();
}

function addHtml() {
	var htmlobj=$.ajax({url:"columnAdd.html",async:false});
	$("#myDiv").html(htmlobj.responseText);
	$("#submit").on("click", function() {
		addColumn();
	})
}

function back() {
	display();
}

var all = "";
function showColumn(){
	$.ajax({
		type:"post",
		url:"../../ObjectServlet?method=part",
		async:true,
		data:{
			"type":"select",
		},
		success:function(data){
			all = data;
			var arry = JSON.parse(data);
			var flag = document.createDocumentFragment();
			for (var i = 0; i < arry.length; i++) {
				var tr = document.createElement("tr"); 
				var str = "";
				str += '<td><input type="checkbox" name="id" onclick="judgeAll()" value="'+ arry[i].id + '" /></td>';
				str += '<td>' + arry[i].columnName + '</td>';
				if (arry[i].fatherColumn == "") {
					str += '<td>无</td>';
				} else {
					str += '<td>' + arry[i].fatherColumn + '</td>';
				}
				str += '<td><div class="button-group"><a class="button border-main"';
				str += ' onclick="modify('+ i +')"><span class="icon-edit"></span> 修改</a>';
				str += '<a class="button border-red" href="javascript:void(0)"'; 
				str +=	' onclick="del(this, '+ arry[i].id +')"><span class="icon-trash-o"></span> 删除</a></div></td>';
				tr.innerHTML = str;
				flag.appendChild(tr);
			}
			document.getElementById("tbody").appendChild(flag);
			exhibition();
		},
		error:function(data){

		}
	})
}

function addColumn(){
	var column = $("#column").val();
	var fatherColumn = $('#fatherColumn option:selected').val();
	if(column != "" && fatherColumn != ""){
		$.ajax({
			type:"post",
			url:"../../ObjectServlet?method=part",
			async:true,
			data:{
				"type":"add",
				"column_name":column,
				"father_column":fatherColumn,
			},
			success:function(data){
				alert("添加成功");
			},
			error:function(data){
				alert("添加成功");
			}
		})
	}
}

function modify(i) {
	var htmlobj=$.ajax({url:"columnAdd.html",async:false});
	$("#myDiv").html(htmlobj.responseText);
	var arry = JSON.parse(all);
	$("#column").val(arry[i].columnName);
	$("#fatherColumn").val(arry[i].fatherColumn);
	var id = arry[i].id;
	$("#submit").on("click", function() {
		update(id);
	})
}

function update(id) {
	var column = $("#column").val();
	var fatherColumn = $('#fatherColumn option:selected').val();
	if(column != "" && fatherColumn != ""){
		$.ajax({
			type:"post",
			url:"../../ObjectUpdateServlet?method=part",
			async:true,
			data:{
				"id": id,
				"column_name":column,
				"father_column":fatherColumn
			},
			success:function(data){
				alert("修改成功")
			},
			error:function(data){

			}
		})
	}
}

//单个删除
function del(row, id) {
	if (confirm("您确定要删除吗?")) {
		$.ajax({
			type:"post",
			url:"../../ObjectServlet?method=part",
			async:true,
			data:{
				"type":"delete",
				"id": id
			},
			success:function(data){
				$(row).parent().parent().parent().remove();
			},
			error:function(data){

			}
		})
	}
}

//批量删除
function DelSelect() {
	var index = 0;
	var WnoArray = [];
	var rowArray = [];
	$("input[name='id']").each(function() {
		if(this.checked == true){
			WnoArray.push(this.value);
			rowArray.push(index);
		}
		index++;
	});
	if (WnoArray.length == 0) {
		alert("选择你需要删除的项");
	} else {
		if (confirm("您确定要删除吗?")) {
			$.ajax({
				url:"../../ObjectdeleteServlet?method=part",
				type: "post",
				data:{
					id: ""+WnoArray,
				},
				success: function() {
					for(var i = 0; i < rowArray.length; i++){
						document.getElementById('tbody').deleteRow(rowArray[i] - i);
					}
					$("#checkall").prop("checked", false);
				},
			});
		}
	}
} 