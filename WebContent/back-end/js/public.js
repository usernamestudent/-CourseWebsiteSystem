//搜索
function changesearch() {
	var info = $("#search").val();
	var tab = document.getElementById("tbody");
	var rows = tab.rows;
	var serachArray = [];
	var Wholeinfo = "";

	for (var i = 0; i < rows.length; i++) {//找到table的每行信息满足搜索找到指定所在的行
		Wholeinfo = "";
		rows[i].style.display = '';
		for (var j = 0; j < rows[i].cells.length; j++) {
			Wholeinfo += rows[i].cells[j].innerText;//每行的信息连接成字符串
		}
		if (Wholeinfo.indexOf(info) == -1) {//与搜索内容比较
			serachArray.push(i);
		}
	}

	for (var i = 0; i < serachArray.length; i++) {//隐藏不满足搜索的行
		rows[serachArray[i]].style.display = 'none';
	}
}


//全选
function checkAll() {
	var flag = $("#checkall").is(':checked');
	$("input[name='id']").each(function() {
		this.checked = flag;
	})
}

//单选
function judgeAll(){
	var falg = true;
	$("input[name='id']").each(function() {
		if(this.checked == false){
			falg = false;
		}
	});

	if(falg){
		$("#checkall").prop("checked", falg);
	}else{
		$("#checkall").prop("checked", falg);
	}
}


//分页

var pageSize = 5;    //每页显示的记录条数
var curPage = 1;        //当前页
var len;            //总行数 、总的多少数据
var pages;            //总页数

function exhibition(){  
	len = $("#tbody tr").length;//总的多少数据
	document.getElementById("data").innerText = "总共有"+ len +"条记录";
	pages = len % pageSize == 0 ? len/pageSize : Math.floor(len/pageSize) + 1;//根据记录条数，计算页数

	setData();
}


function setData() {
	$("#page").html(curPage);
	var begin = (curPage - 1) * pageSize;// 起始记录号
	var end = begin + pageSize - 1;    // 末尾记录号


	if(end > len ) end=len;

	$("#tbody tr").hide(); 
	$("#tbody tr").each(function(i){    // 然后，通过条件判断决定本行是否恢复显示
		if(i >= begin && i <= end)//显示begin<=x<=end的记录
			$(this).show();
	});
}

function previous() {
	if(curPage == 1){
		return;
	}
	curPage -= 1;
	setData();
}

function next() {
	if(curPage == pages){
		return;
	}
	curPage += 1;
	setData();
}

function changeSize () {
	var size = parseInt($("#pageSize").val());
	if(size > 0) {
		pageSize = size;
	}
	else {
		pageSize = 1;
	}
	pages = len % pageSize == 0 ? len / pageSize : Math.floor(len/pageSize) + 1;
	setData();
}
