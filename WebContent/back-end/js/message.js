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
			 alert(data)
		 },
		 error:function(data){
			 
		 }
	})
}