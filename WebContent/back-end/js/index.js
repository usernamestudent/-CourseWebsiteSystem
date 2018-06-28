/**
 * 
 */
function showName(){
	var  url = window.location.href;
	var name = decodeURI(url).split("=")[1];
	$("#text").html("欢迎您，" + name)
}