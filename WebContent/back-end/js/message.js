/**
 * 
 */
function allMessage(){
	$.ajax({
		type:"post",
		url:"../../ObjectServlet?method=message",
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
				str += '<td>' + arry[i].name + '</td>';
				str += '<td>' + arry[i].phone + '</td>';
				str += '<td>' + arry[i].email + '</td>';
				str += '<td>' + arry[i].note + '</td>';
				str += '<td>' + arry[i].content + '</td>';
				str += '<td>' + arry[i].time + '</td>';
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

function del(row,id) {
	if (confirm("您确定要删除吗?")) {
		$.ajax({
			type:"post",
			url:"../../ObjectServlet?method=message",
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

$("#checkall").click(function() {
	$("input[name='id']").each(function() {
		if (this.checked) {
			this.checked = false;
		} else {
			this.checked = true;
		}
	});
})

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
	} else {
		alert("请选择您要删除的内容!");
		return false;
	}
}