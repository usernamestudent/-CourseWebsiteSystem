/**
 * 
 */
function receiveData(){
	var data = JSON.parse(sessionStorage.getItem('key'));
	$("#account").text(data[0].id);
}

function update(){
	var oldPassword = $("#mpass").val();
	var password = $("#password").val();
	var passwords = $("#passwords").val();
	var data = JSON.parse(sessionStorage.getItem('key'));
	if(oldPassword != null && password != null && password == passwords){
		$.ajax({
			type:"post",
			 url:"../../ObjectUpdateServlet?method=user",
			 async:true,
			 data:{
				 "id":data[0].id,
				 "password":password
			 },
			 success:function(data){
				 alert(data);
				 if(data == "true"){
					 alert(data);
				 }
			 },
			 error:function(data){
				 
			 }
		})
	}
}