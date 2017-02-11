//对象转json
function conveterParamsToJson(paramsAndValues) {  
	paramsAndValues = paramsAndValues.replace(/\+/g," ");   //
	paramsAndValues = decodeURIComponent(paramsAndValues);
	
    var jsonObj = {};  
    var param = paramsAndValues.split("&");  
    for ( var i = 0; param != null && i < param.length; i++) {  
        var para = param[i].split("=");  
        jsonObj[para[0]] = para[1];  
    }  
  
    return jsonObj;  
} 

//数组是否包含有个值
function isCon(arr, val){
	for(var i=0; i<arr.length; i++){
		if(arr[i] == val)
			return true;
	}
	return false;
}

//是否全屏
var inFullScreen = false; // flag to show when full screen

document.onkeydown=function(event){ 
	var e = event || window.event || arguments.callee.caller.arguments[0]; 
	if(e && e.keyCode==27){ // 按 Esc 
	//要做的事情 
		if(inFullScreen){
			if(isIE = navigator.userAgent.indexOf("MSIE")!=-1){
				var WsShell = new ActiveXObject('WScript.Shell');
				WsShell.SendKeys('{F11}'); 
			}
			$('#fullScreen').html('全屏');
			inFullScreen = false;
    	}
	} 
}
//全屏显示
function fillScreen(){
	var docElm = document.documentElement;
	//W3C 
	if (docElm.requestFullscreen) { 
	  docElm.requestFullscreen(); 
	}//FireFox 
	else if (docElm.mozRequestFullScreen) { 
	  docElm.mozRequestFullScreen(); 
	}//Chrome等 
	else if (docElm.webkitRequestFullScreen) { 
	  docElm.webkitRequestFullScreen(); 
	}//IE11
	else if (docElm.msRequestFullscreen) {
		docElm.msRequestFullscreen();
	}// IE
	else if(isIE = navigator.userAgent.indexOf("MSIE")!=-1){
		window.open(location.href,'','width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+',top=0,left=0,resizable=yes,status=yes,menubar=no,scrollbars=yes'); 
/*		var WsShell = undefined;
		try{
			WsShell = new ActiveXObject('WScript.Shell');
	    }catch(e){
	    	alert(IEFullScreen);
	    	return false;
	    }
		WsShell.SendKeys('{F11}'); */
	}
	inFullScreen = true;
	return true;
}
//退出全屏
function reset() {
    if (document.exitFullscreen) {
      document.exitFullscreen();
    }
    else if (document.msExitFullscreen) {
      document.msExitFullscreen();
    }
    else if (document.mozCancelFullScreen) {
      document.mozCancelFullScreen();
    }
    else if (document.webkitCancelFullScreen) {
      document.webkitCancelFullScreen();
    }// IE
    else if(isIE = navigator.userAgent.indexOf("MSIE")!=-1){
		var WsShell = new ActiveXObject('WScript.Shell');
		WsShell.SendKeys('{F11}'); 
	}
	inFullScreen = false;
	return true;
  }

function ajaxLoading(){
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:$(window).height()}).appendTo("body");
    $("<div class=\"datagrid-mask-msg\"></div>").html("Loading...").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});
 }
 function ajaxLoadEnd(){
     $(".datagrid-mask").remove();
     $(".datagrid-mask-msg").remove();            
}
 //解决TypeError:$.browser is undefined报错
 (function(jQuery){  
	 
	 if(jQuery.browser) return;  
	  
	 jQuery.browser = {};  
	 jQuery.browser.mozilla = false;  
	 jQuery.browser.webkit = false;  
	 jQuery.browser.opera = false;  
	 jQuery.browser.msie = false;  
	  
	 var nAgt = navigator.userAgent;  
	 jQuery.browser.name = navigator.appName;  
	 jQuery.browser.fullVersion = ''+parseFloat(navigator.appVersion);  
	 jQuery.browser.majorVersion = parseInt(navigator.appVersion,10);  
	 var nameOffset,verOffset,ix;  
	  
	 // In Opera, the true version is after "Opera" or after "Version"  
	 if ((verOffset=nAgt.indexOf("Opera"))!=-1) {  
	 jQuery.browser.opera = true;  
	 jQuery.browser.name = "Opera";  
	 jQuery.browser.fullVersion = nAgt.substring(verOffset+6);  
	 if ((verOffset=nAgt.indexOf("Version"))!=-1)  
	 jQuery.browser.fullVersion = nAgt.substring(verOffset+8);  
	 }  
	 // In MSIE, the true version is after "MSIE" in userAgent  
	 else if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {  
	 jQuery.browser.msie = true;  
	 jQuery.browser.name = "Microsoft Internet Explorer";  
	 jQuery.browser.fullVersion = nAgt.substring(verOffset+5);  
	 }  
	 // In Chrome, the true version is after "Chrome"  
	 else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) {  
	 jQuery.browser.webkit = true;  
	 jQuery.browser.name = "Chrome";  
	 jQuery.browser.fullVersion = nAgt.substring(verOffset+7);  
	 }  
	 // In Safari, the true version is after "Safari" or after "Version"  
	 else if ((verOffset=nAgt.indexOf("Safari"))!=-1) {  
	 jQuery.browser.webkit = true;  
	 jQuery.browser.name = "Safari";  
	 jQuery.browser.fullVersion = nAgt.substring(verOffset+7);  
	 if ((verOffset=nAgt.indexOf("Version"))!=-1)  
	 jQuery.browser.fullVersion = nAgt.substring(verOffset+8);  
	 }  
	 // In Firefox, the true version is after "Firefox"  
	 else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) {  
	 jQuery.browser.mozilla = true;  
	 jQuery.browser.name = "Firefox";  
	 jQuery.browser.fullVersion = nAgt.substring(verOffset+8);  
	 }  
	 // In most other browsers, "name/version" is at the end of userAgent  
	 else if ( (nameOffset=nAgt.lastIndexOf(' ')+1) <  
	 (verOffset=nAgt.lastIndexOf('/')) )  
	 {  
	 jQuery.browser.name = nAgt.substring(nameOffset,verOffset);  
	 jQuery.browser.fullVersion = nAgt.substring(verOffset+1);  
	 if (jQuery.browser.name.toLowerCase()==jQuery.browser.name.toUpperCase()) {  
	 jQuery.browser.name = navigator.appName;  
	 }  
	 }  
	 // trim the fullVersion string at semicolon/space if present  
	 if ((ix=jQuery.browser.fullVersion.indexOf(";"))!=-1)  
	 jQuery.browser.fullVersion=jQuery.browser.fullVersion.substring(0,ix);  
	 if ((ix=jQuery.browser.fullVersion.indexOf(" "))!=-1)  
	 jQuery.browser.fullVersion=jQuery.browser.fullVersion.substring(0,ix);  
	  
	 jQuery.browser.majorVersion = parseInt(''+jQuery.browser.fullVersion,10);  
	 if (isNaN(jQuery.browser.majorVersion)) {  
	 jQuery.browser.fullVersion = ''+parseFloat(navigator.appVersion);  
	 jQuery.browser.majorVersion = parseInt(navigator.appVersion,10);  
	 }  
	 jQuery.browser.version = jQuery.browser.majorVersion;  
	 })(jQuery);
