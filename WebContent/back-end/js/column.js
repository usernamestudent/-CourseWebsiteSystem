function display() {
	 var htmlobj=$.ajax({url:"columnShow.html",async:false});
	 $("#myDiv").html(htmlobj.responseText);
	 showColumn();
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

function addColumn(){
	var column = $("#column").val();
	var fatherId = $('#fatherId option:selected').val();
	if(column != "" && fatherId != ""){
		$.ajax({
			type:"post",
			url:"../../ObjectServlet?method=part",
			async:true,
			data:{
				"type":"add",
				"column_name":column,
				"father_id":fatherId,
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
