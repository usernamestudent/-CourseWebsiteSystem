/**
 * 
 */
function login(){
	var userId = $("#userId").val();
	var password = $("#password").val();
	if(userId != "" && password != ""){
		$.ajax({
			type:"post",
			url:"../../ObjectServlet?method=user",
			async:true,
			data:{
				"type":"select",
				"id":userId,
				"password":password
			},
			success:function(data){
				sessionStorage.clear();
				sessionStorage.setItem('key', data);
				self.location = "../html/index.html";
			},
			error:function(data){
				alert("密码错误");
			}
		})
	}
}