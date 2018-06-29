/**
 * 
 */
function login(){
	var userId = $("#userId").val();
	var password = $("#password").val();
	if(userId != null || password != null){
		$.ajax({
			type:"post",
			url:"../../ObjectServlet?method=user",
			async:true,
			data:{
				"type":"select",
				"id":userId,
				"password":password,
			},
			success:function(data){
				sessionStorage.clear();
				sessionStorage.setItem('key', data);
				window.location.href = "../html/index.html";
			},
			error:function(data){
				alert("error");
			}
		})
	}
}