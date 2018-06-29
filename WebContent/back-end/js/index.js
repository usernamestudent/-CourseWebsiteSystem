/**
 * 
 */
function showName(){
	var data = JSON.parse(sessionStorage.getItem('key'));
	$("#text").html("欢迎您，" + data[0].name);
}
function back(){
	window.location.href = "../html/login.html";
	sessionStorage.clear();
}