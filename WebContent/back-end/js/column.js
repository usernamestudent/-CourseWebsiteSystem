function display() {
	 var htmlobj=$.ajax({url:"columnShow.html",async:false});
	 $("#myDiv").html(htmlobj.responseText);
	 showColumn();
}

//全选
function checkAll() {
	$("input[name='id']").each(function() {
		if (this.checked) {
			this.checked = false;
		} else {
			this.checked = true;
		}
	});
}

function judgeAll(){
	var falg = true;
	$("input[name='id']").each(function() {
		if(this.checked == false){
			falg = false;
		}
	});

	if(falg){
		$("#checkall").prop("checked", falg);
	}else{
		$("#checkall").prop("checked", falg);
	}
}

//批量删除
function DelSelect() {
	var Checkbox = false;
	$("input[name='id']").each(function() {
		if (this.checked == true) {
			Checkbox = true;
		}
	});
	if (Checkbox) {
		var t = confirm("您确认要删除选中的内容吗？");
		if (t == false)
			return false;
		$("#listform").submit();
	} else {
		alert("请选择您要删除的内容!");
		return false;
	}
}

function addHtml() {
	var htmlobj=$.ajax({url:"columnAdd.html",async:false});
	 $("#myDiv").html(htmlobj.responseText);
}

function back() {
	display();
}
function showColumn(){
	$.ajax({
		type:"post",
		url:"../../ObjectServlet?method=part",
		async:true,
		data:{
			"type":"select",
		},
		success:function(data){
			var arry = JSON.parse(data);
			var flag = document.createDocumentFragment();
			for (var i = 0; i < arry.length; i++) {
				var tr = document.createElement("tr"); 
				var str = "";
				str += '<td><input type="checkbox" name="id" onclick="judgeAll()" value="'+ arry[i].id + '" /></td>';
				str += '<td>' + arry[i].columnName + '</td>';
				if (arry[i].fatherId == "") {
					str += '<td>无</td>';
				} else {
					str += '<td>' + arry[i].fatherId + '</td>';
				}
				str += '<td><div class="button-group"><a class="button border-red" href="javascript:void(0)"'; 
				str +=	' onclick="del(this, '+ arry[i].id +')"><span class="icon-trash-o"></span>删除</a></div></td>';
				tr.innerHTML = str;
				flag.appendChild(tr);
			}
			document.getElementById("tbody").appendChild(flag);
		},
		error:function(data){

		}
	})
}

function addColumn() {
	var title = $("#title").val();
	var column = $('#column option:selected').val();
	
	if(title != "" && column != ""){
		$.ajax({
			type:"post",
			url:"../../ObjectServlet?method=part",
			async:true,
			data:{
				"type":"add",
				"title":title,
				"column_name":column,
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
