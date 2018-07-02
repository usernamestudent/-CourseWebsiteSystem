
function back() {
	showArticle();
}

var all = "";
function showArticle(){
	$.ajax({
		type:"post",
		url:"../../ObjectServlet?method=article",
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
				str += '<td>' + arry[i].title + '</td>';
				str += '<td>' + arry[i].author + '</td>';
				str += '<td>' + arry[i].approver + '</td>';
				if (arry[i].isPass == "1") {
					str += '<td>是</td>';
				} else {
					str += '<td>否</td>';
				}
				str += '<td><div class="button-group"><a class="button border-main"';
				str += ' onclick="modify('+ i +')"><span class="icon-edit"></span> 修改</a></div></td>';
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

function modify(i) {
	var data = JSON.parse(all);
	var arry = JSON.stringify(data[i]);
	sessionStorage.setItem('data', arry);
	location.href = "../html/articleAuditUpdate.html";
}

function updateShow(){
	var data = sessionStorage.getItem('data');
	var user = sessionStorage.getItem('key');
	var account = JSON.parse(user);
	var arr = JSON.parse(data);
	$("#title").val(arr.title);
	$("#author").val(arr.author);
	$("#approver").val(account[0].name);
	$("input").attr("disabled", "true");
	$("input").css("background","white");
	$("input").css("border","none");
}


function updateAudit(){
	var user = sessionStorage.getItem('key');
	var account = JSON.parse(user);
	var data = sessionStorage.getItem('data');
	var isPass = $('#column option:selected').val();
	var arry = JSON.parse(data);
	var id = arry.id;
	var approver = account[0].name;
	if(isPass != "2"){
		$.ajax({
			type:"post",
			url:"../../ObjectUpdateServlet?method=article",
			async:false,
			data:{
				"id": id,
				"approver":approver,
				"isPass":isPass
			},
			success:function(data){
				location.href = "../html/articleAudit.html";
				sessionStorage.removeItem('data');
			},
			error:function(data){

			}
		})
	}
}


function up(){
	location.href = "../html/articleAudit.html";
}
