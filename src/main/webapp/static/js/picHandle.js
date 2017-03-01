/**
 * @author yingjq
 * @param location 图片存储位置，是个dom对象
 * @param picArr 图片数组
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