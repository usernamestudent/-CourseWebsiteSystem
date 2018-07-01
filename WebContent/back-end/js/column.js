/**
 * 
 */
window.onload = function() {
	 var htmlobj=$.ajax({url:"columnShow.html",async:false});
	 $("#myDiv").html(htmlobj.responseText);
	 showColumn();
}
function del(id){
	if(confirm("您确定要删除吗?")){
		
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

function addHtml() {
	var htmlobj=$.ajax({url:"columnAdd.html",async:false});
	 $("#myDiv").html(htmlobj.responseText);
}

function back() {
	var htmlobj=$.ajax({url:"columnShow.html",async:false});
	 $("#myDiv").html(htmlobj.responseText);
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
				str += '<td><input type="checkbox" name="id" value="'+ arry[i].id + '" /></td>';
				str += '<td>' + arry[i].columnName + '</td>';
				str += '<td>' + arry[i].fatherId + '</td>';
//				str += '<td>' + arry[i].email + '</td>';
//				str += '<td>' + arry[i].note + '</td>';
//				str += '<td>' + arry[i].content + '</td>';
//				str += '<td>' + arry[i].time + '</td>';
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