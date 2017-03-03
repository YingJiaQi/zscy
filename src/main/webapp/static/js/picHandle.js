/**
 * 含有缩略图的图片展示，一般用于商品的展示
 * @author yingjq
 * @param location 图片存储位置，是个dom对象，高400px;宽400px
 * @param picArr 图片数组，里面的图片必须是在浏览器中能访问的
 * 
 */
var picNumLocal = null;
var picArrLocal = [];
function picDisplayCommodity(picArr, location){
	picArrLocal = picArr;
	picNumLocal = picArr.length;
	var picLis = "";
	if(picNumLocal <= 5){
		picLis += "<ul style='list-style-type:none;margin-left:7px' id='smallPics'>";
		for(var i=0;i<picNumLocal;i++){
			picLis += "<li  style='list-style-type:none;float:left;height:70px;width:70px;border:1px solid orange;margin-right:2px;cursor:pointer;' onclick='clickSinglePic(this)'><img src='"+picArr[i]+"' height='70px' width='70px'/></li>";
		}
		picLis += "</ul>";
	}else{
		picLis += "<div style='position:absolute;z-index:999;margin-top:12px;cursor:pointer' onclick='toLeft();'><img src='/ZSCY/static/image/arrowhead_left.png'  width='60px' height='60px' style='opacity:0.5;'/></div>"+
			"<ul style='list-style-type:none;margin-left:7px' id='smallPics'>";
		for(var i=0;i<picNumLocal;i++){
			picLis += "<li  style='list-style-type:none;float:left;height:70px;width:70px;border:1px solid orange;cursor:pointer;margin-right:2px' onclick='clickSinglePic(this)'><img src='"+picArr[i]+"' height='70px' width='70px'/></li>";
		}
		picLis += "</ul><div style='position:absolute;margin-left:390px;z-index:999;cursor:pointer;' onclick='toRight();'><img src='/ZSCY/static/image/arrowhead_right.png'  width='60px' height='60px' style='opacity:0.5;'/></div>";
	}
	var optionHtml = "<div class='row' style='heigth:400px;width:100%;padding:12px;border:1px solid orange'><img id='bigPicPanel' src='"+picArr[0]+"' width='400px' height='400px'/></div>"+
						"<div class='row' style='height:70px;width:100%;overflow:hidden;margin-left:-25px;margin-top:6px' id='smallPicsParent'>"+picLis
						"</div>";
	$(location).append(optionHtml);
}
function toLeft(){
	$("#smallPics li").eq(0).before($("#smallPics li").eq(picNumLocal-1));
	var hrefpic = $("#smallPics li:eq(0) img").attr("src");
	$("#bigPicPanel").attr("src",hrefpic);
}
function toRight(){
	if($("#smallPics li").eq(0).next()){
		$("#smallPics li").eq(picNumLocal-1).after($("#smallPics li").eq(0)); 
	}
	var hrefpic = $("#smallPics li:eq(0) img").attr("src");
	$("#bigPicPanel").attr("src",hrefpic);
	
}
function clickSinglePic(obj){
	var pic = $(obj).html();
	var start = pic.indexOf("src=")+5;
	var end = pic.indexOf("height")-2;
	pic = pic.substring(start, end);
	if(pic.indexOf("width") >0){//火狐浏览器
		end = pic.indexOf("width")-2;
		pic = pic.substring(0, end);
	}
	$("#bigPicPanel").attr("src",pic);
}
/**
 * 首页大图轮播，可自动轮转
 * @author yingjq
 * @param carousels_picArr 图片数组,里面的图片必须是在浏览器中能访问的
 * @param carousels_domObject 显示位置，dom对象
 */
//需要展示的图片数量
var y_picLength = null;
//当前展示第几个图片，保存图片序号
var y_currPicOrder = null;
function picCarousels(carousels_picArr, carousels_domObject){
	var cacheHtml = "";
	if(carousels_picArr != null){
		y_picLength = carousels_picArr.length;
		var picLis = "";
		for(var i=0;i<y_picLength;i++){
			picLis += "<li style='list-style-type:none;display :none;'><img  src='"+carousels_picArr[i]+"' alt=''  height='500px'  width='100%'  ></li>";
		}
		cacheHtml = "<div id='pictureCarousels'>"+
						"<ul>"+picLis+
						"</ul>"+
						"<a href='javascript:void(0);' onclick='carousels_toLeft();' class='prev' style='position:absolute;top:40%;left:3%;'><img  id='al' src='http://127.0.0.1:8080/ZSCY/static/image/arrowhead_left.png' alt='prev' width='30' height='80'></a>"+
						"<a href='javascript:void(0);' onclick='carousels_toRight();' class='next' style='position:absolute;top:40%;left:94%;'><img  id='ar' src='http://127.0.0.1:8080/ZSCY/static/image/arrowhead_right.png' alt='next' width='30' height='80'></a>"+
					"</div>";
	}else{
		cacheHtml = "<div id='pictureCarousels'>"+
						"<ul>"+
							"<li style='list-style-type:none;display :none;'><img  src='http://127.0.0.1:8080/ZSCY/static/image/01.jpg' alt=''  height='500px'  width='100%'  ></li>"+
							"<li style='list-style-type:none;display :none;'><img  src='http://127.0.0.1:8080/ZSCY/static/image/02.jpg' alt=''  height='500px'  width='100%'  ></li>"+
							"<li style='list-style-type:none;display :none;'><img  src='http://127.0.0.1:8080/ZSCY/static/image/03.jpg' alt=''  height='500px'  width='100%'  ></li>"+
							"<li style='list-style-type:none;display :none;'><img  src='http://127.0.0.1:8080/ZSCY/static/image/04.jpg' alt=''  height='500px'  width='100%'  ></li>"+
							"<li style='list-style-type:none;display :none;'><img  src='http://127.0.0.1:8080/ZSCY/static/image/05.jpg' alt=''  height='500px'  width='100%'  ></li>"+
						"</ul>"+
						"<a href='javascript:void(0);' onclick='carousels_toLeft();' class='prev' style='position:absolute;top:40%;left:6%;'><img  id='al' src='http://127.0.0.1:8080/ZSCY/static/image/arrowhead_left.png' alt='prev' width='30' height='80'></a>"+
						"<a href='javascript:void(0);' onclick='carousels_toRight();' class='next' style='position:absolute;top:40%;left:80%;'><img  id='ar' src='http://127.0.0.1:8080/ZSCY/static/image/arrowhead_right.png' alt='next' width='30' height='80'></a>"+
					"</div>";
	}
	$(carousels_domObject).append(cacheHtml);
	//先隐藏所有图片
	$("#pictureCarousels ul li").css("display","none");
	$("#pictureCarousels ul li:eq(0)").css("display","block");
	y_currPicOrder = 0;
	if(y_picLength == null){
		y_picLength = $("#pictureCarousels ul li").length;
	}
	var percent = 50;
	//alert(y_picLength)
	//计算percent的初始位置
	for(var j=0;j<y_picLength;j++){
		if((i+1)%2 == 0){
			percent -= 2;
		}else{
			percent -= 1;
		}
	}
	for(var i=0 ;i<y_picLength;i++){
		percent += 3;
		var htmls = "<span orders='"+i+"' style='position:absolute;top:90%;left:"+percent+"%;height:15px;width:15px;border:2px solid white;border-radius:15px;cursor:pointer' onclick='clickCircle(this)'></span>"
		$("#pictureCarousels").append(htmls);
	}
	$("#pictureCarousels span:eq(0)").css("background-color","white");
	//图片自动轮换
	setInterval(picLoopAuto, 6000);
}
function picLoopAuto(){
		carousels_toRight();
}
function clickCircle(obj){
	
	for(var i=0;i<y_picLength;i++){
  		$("#pictureCarousels span:eq("+i+")").css("background-color","transparent");
	}
	$(obj).css("background-color","white");
	$("#pictureCarousels ul li").css("display","none");
	var order = $(obj).attr("orders");
	$("#pictureCarousels ul li:eq("+order+")").css("display","block");
	y_currPicOrder = order;
}
function carousels_toLeft(){
	//图片当前位置减1
	//先初始化圆圈样式
	for(var i=0;i<y_picLength;i++){
  		$("#pictureCarousels span:eq("+i+")").css("background-color","transparent");
	}
	if(y_currPicOrder ==0){
		y_currPicOrder = y_picLength;
	}
	y_currPicOrder --;
	//给上一个圆圈加白色背景
	$("#pictureCarousels span:eq("+y_currPicOrder+")").css("background-color","white");
	//先隐藏所有图片
	$("#pictureCarousels ul li").css("display","none");
	//显示上一个图片
	$("#pictureCarousels ul li:eq("+y_currPicOrder+")").css("display","block");
	
}
function carousels_toRight(){
	//图片当前位置加1
	//先初始化圆圈样式
	for(var i=0;i<y_picLength;i++){
  		$("#pictureCarousels span:eq("+i+")").css("background-color","transparent");
	}
	if(y_currPicOrder ==(y_picLength-1)){
		y_currPicOrder = -1;
	}
	y_currPicOrder ++;
	//给下一个圆圈加白色背景
	$("#pictureCarousels span:eq("+y_currPicOrder+")").css("background-color","white");
	//先隐藏所有图片
	$("#pictureCarousels ul li").css("display","none");
	//显示下一个图片
	$("#pictureCarousels ul li:eq("+y_currPicOrder+")").css("display","block");
	
}