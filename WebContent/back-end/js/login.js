/**
 * 
 */
function login(){
	var userId = $("#userId").val();
	var password = $("#password").val();
	if(userId != "" || password != ""){
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
				var ruselt = JSON.parse(data);
				var name = ruselt[0].name;
				window.location.href = encodeURI("../html/index.html?name=" + name) ;
			},
			error:function(data){
				alert("error");
			}
		})
	}
}