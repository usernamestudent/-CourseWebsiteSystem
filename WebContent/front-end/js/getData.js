window.onload = function() {
	var introduce = getInfo("课程简介");
	console.log(introduce);
	$("#contentIntroduce").html(introduce[0].content);
//	$("#contentPrincipal").html(getInfo("课程负责人"));
//	
//	var state = getInfo("课程动态");
//	var j = 0;
//	var flag = document.createDocumentFragment();
//	for (var i = state.length - 1; i > 0; i--) {
//		if (j == 5) {
//			break;
//		}
//		j++;
//		
//		var str = "";
//		var li = document.createElement("li"); 
//		str += '<img src="' + state[i].image + '" width="4" height="6"/>';
//		str += '<a href="#">' + state[i].title + '</a>' 
//		str += '<span class="time">' + state[i].createTime + '</span>'
//		li.innerHTML += str;
//		flag.appendChild(li);
//	}
//	document.getElementById("contentTrendsMid").appendChild(flag);
//	
//	var members = getInfo("课程组成员");
//	j = 0;
//	for (var i = members.length; i > 0; i--) {
//		if (j == 10) {
//			break;
//		}
//		j++;
//		
//		var str = "";
//		str += '<li class="trendsImg"><img src="../images/课程负责人.png"/></li>'
//	}
//	document.getElementById("carousel_ul").innerHTML = str;
	
	
}

function getInfo(columnName) {
	var arry = ""
	$.ajax({
		type:"post",
		url:"../../ObjectServlet?method=article",
		async:false,
		data:{
			"type":"select",
			"isPass": "1"
		},
		success:function(data){
			arry = JSON.parse(data);
		},
		error:function(data){

		}
	})
	return arry;
}