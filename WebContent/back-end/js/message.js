/**
 * 
 */

var all = "";
function allMessage(){
	$.ajax({
		type:"post",
		url:"../../ObjectServlet?method=message",
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
				str += '<td>' + arry[i].name + '</td>';
				str += '<td>' + arry[i].phone + '</td>';
				str += '<td>' + arry[i].email + '</td>';
				str += '<td>' + arry[i].note + '</td>';
				str += '<td>' + arry[i].time + '</td>';
				str += '<td><div class="button-group"><a class="button border-green"'; 
				str +=	' onclick="view('+ i +')"><span class="icon-search"></span> 查看</a>';
				str += '<a class="button border-red" href="javascript:void(0)"'; 
				str +=	' onclick="del(this, '+ arry[i].id +')"><span class="icon-trash-o"></span>删除</a></div></td>';
				tr.innerHTML = str;
				flag.appendChild(tr);
				
				exhibition();
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

function checkAll() {
	if($('#checkall').is(':checked')){
		$("input[name='id']").each(function() {
			this.checked = true;
		});
	}else{
		$("input[name='id']").each(function() {
			this.checked = false;
		});
	}
}

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
				url:"../../ObjectdeleteServlet?method=message",
				type: "post",
				data:{
					id: ""+WnoArray,
				},
				success: function() {
					alert("删除成功");
					for(var i = 0; i < rowArray.length; i++){
						document.getElementById('tbody').deleteRow(rowArray[i] - i);
					}
					$("#checkall").prop("checked", false);
				},
			});
		}
	}
} 

function view(i){
	var data = JSON.parse(all);
	var arry = JSON.stringify(data[i]);
	sessionStorage.setItem('data', arry);
	location.href = "../html/messageShow.html";
}

function show(){
	var data = sessionStorage.getItem('data');
	sessionStorage.removeItem('data');
	var arr = JSON.parse(data);
	$("#name").val(arr.name);
	$("#phone").val(arr.phone);
	$("#email").val(arr.email);
	$("#content").val(arr.content);
	$("#note").val(arr.note);
	$("#reply").val(arr.reply);
	$("#time").val(arr.time);
	$("input,textarea").attr("disabled", "true");
	$("input,textarea").css("background","white");
	$("input,textarea").css("border","none");
}

function back(){
	location.href = "../html/message.html";
}