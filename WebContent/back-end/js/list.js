/*
 * 
 */
window.onload = function() {
	var htmlobj=$.ajax({url:"articleShow.html",async:false});
	$("#myDiv").html(htmlobj.responseText);
	
	//展示文章
	$.ajax({
		type:"post",
		url:"../../ObjectServlet?method=article",
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
				str += '<td style="text-align: left; padding-left: 20px;"><input type="checkbox" name="id" value="'+ arry[i].id + '" /></td>';
				str += '<td>' + arry[i].title + '</td>';
				str += '<td>' + arry[i].author + '</td>';
				str += '<td>' + arry[i].columnName + '</td>';
				str += '<td>' + arry[i].createTime + '</td>';
				str += '<td><div class="button-group"><a class="button border-green"'; 
				str +=	' onclick="view('+ arry[i] +')"><span class="icon-search"></span> 查看</a>';
				str += '<a class="button border-main"';
				str += ' onclick="modify('+ arry[i] +')"><span class="icon-edit"></span> 修改</a>'
				str += '<a class="button border-red"'; 
				str +=	' onclick="del(this, '+ arry[i].id +')"><span class="icon-trash-o"></span> 删除</a></div></td>';
				tr.innerHTML = str;
				flag.appendChild(tr);
			}
			document.getElementById("tbody").appendChild(flag);
		},
		error:function(data){
			alert("添加成功");
		}
	})
}
//增加内容		
function add() {
	var htmlobj=$.ajax({url:"articleAdd.html",async:false});
	$("#myDiv").html(htmlobj.responseText);
}

function addArticle() {
	var title = $("#title").val();
	var column = $('#column option:selected').val();
	var note = $("#note").val();
	var content = $("#content").val();
	var s_title = $("#s_title").val();
	var s_keywords = $("#s_keywords").val();
	var s_desc = $("#s_desc").val();
	var date = $("#datetime").val();
	var data = JSON.parse(sessionStorage.getItem('key'));
	var author = data[0].name;
	if(column != null || data != null || author != null){
		$.ajax({
			type:"post",
			url:"../../ObjectServlet?method=article",
			async:true,
			data:{
				"type":"add",
				"title":title,
				"author":author,
				"column_name":column,
				"create_time":date,
				"s_title":s_title,
				"s_keywords":s_keywords,
				"s_desc":s_desc,
				"note":note,
				"content":content,
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

function modify(id) {
	
}

//搜索
function changesearch() {
}

//单个删除
function del(id, mid, iscid) {
	if (confirm("您确定要删除吗?")) {

	}
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


function back() {
	var htmlobj=$.ajax({url:"articleShow.html",async:false});
	$("#myDiv").html(htmlobj.responseText);
}