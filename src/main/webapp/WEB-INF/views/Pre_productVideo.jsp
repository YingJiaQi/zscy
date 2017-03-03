<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.* " %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>智晟磁业</title>
     <!--移动设置优先 -->
    <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
	<meta name="author" content="LayoutIt!">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	<script src="${pageContext.request.contextPath }/static/js/jquery-1.11.3.js"></script>
    <script src="${pageContext.request.contextPath }/static/js/bootstrap/js/bootstrap.min.js"></script>
    <link href="${pageContext.request.contextPath }/static/js/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath }/static/js/bootstrap/css/style.css" rel="stylesheet">
    <!--[if lt IE 9]>
	  <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
	  <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
<script type="text/javascript">
<%  
Properties pro = new Properties();   
 String realpath = request.getRealPath("/WEB-INF/classes");   
 try{    
     //读取配置文件  
     FileInputStream in = new FileInputStream(realpath+"/program.properties");   
     pro.load(in);   
 }catch(FileNotFoundException e){   
     out.println(e);   
 }catch(IOException e){out.println(e);}   

//通过key获取配置文件  
String path = "'"+pro.getProperty("hostIpAddress")+"'";  
%>  
//主机IP地址
var hostIpAddress =<%=path%>;
	
	$(function () {
		$.ajax({
	    	 type:'post',
	    	 url:'${pageContext.request.contextPath}/PreWebContentManager/getProductVideoData',
	    	 dataType : 'json',
	    	 contentType : "application/json;charset=utf-8",
	    	 success : function (data){
	    		if (data.success == "true") {
	    			
	    			for(var i = 0; i< data.datas.length ;i++){
	    				if(data.datas[i].viewCount == 1){
	    					var optionHtml = "<div class='col-md-6'>"+
						    					"<video src='"+hostIpAddress+data.datas[i].sourceUrl+"' controls='controls' width='460px' >"+
						    					"您的浏览器不支持 video 标签。"+
						    					"</video>"+
						    					"<div class='caption'>"+
						    						"<h6 style='padding:5px'>"+data.datas[i].sourceTitle+"</h6><img src='${pageContext.request.contextPath }/static/image/hot.png' width='30px' height='30px' style='margin-top:-8px;margin-left:20px'/>"+
						    					"</div>"+
						        			"</div>";
	    					$("#videoPanel").append(optionHtml);
	    				}else{
	    					var optionHtml = "<div class='col-md-6'>"+
						    					"<video src='"+hostIpAddress+data.datas[i].sourceUrl+"' controls='controls' width='460px' >"+
						    					"您的浏览器不支持 video 标签。"+
						    					"</video>"+
						    					"<div class='caption'>"+
						    						"<h6 style='padding:5px'>"+data.datas[i].sourceTitle+"</h6>"+
						    					"</div>"+
						        			"</div>";
							$("#videoPanel").append(optionHtml);
	    				}
					}
				} else {
					var optionHtml = "<div style='width:100px;height:50px;margin:5px 10px;padding:3px 3px 3px 15px;font-size:1.1em;line-height:1.3'>暂无数据</div>"
					$("#showDataPanal").append(optionHtml);
				}
	    	 }
	     });
	});
</script>
  </head>
  
  <body>
     <div class="container-fluid">
			<div class="row">
				<div class="col-md-12" style="height:5px;background-color:#0066cc">
				</div>
			</div>
		<nav class="navbar navbar-default"  style="background-color:white">
				<div class="row">
					<div class="col-md-2"></div>
					<div class="col-md-8 col-xs-12">
						<!-- logo -->
						<div class="navbar-header" style="margin-top:10px"> 
							<img alt="logo Preview" src="${pageContext.request.contextPath }/static/image/zm_logo.png" width="90%" height="90%"  class="img-responsive">
						</div>
						<div class="collapse  navbar-collapse " style="margin-top:23px;margin-left:10px">
								<ul class="nav nav-pills">
								  <li><a href="${pageContext.request.contextPath }">首页</a></li>
								  <li  class="active"><a href="${pageContext.request.contextPath }/productVideo/toProductJsp">生产视频</a></li>
								  <!-- <li><a href="#">强力磁铁</a></li> -->
								  <li><a href="${pageContext.request.contextPath }/MagnetClassification/getList">磁性制品</a></li>
								  <li><a href="${pageContext.request.contextPath }/PreWebContentManager/Pre_buyPrice">采购报价</a></li>
								  <li><a href="${pageContext.request.contextPath }/MessageInfo/newsCentor">新闻中心</a></li>
								  <li><a href="${pageContext.request.contextPath }/MessageInfo/aboutUs">关于我们</a></li>
								</ul>
						</div>
							
					</div>
					<div class="col-md-2"></div>
				</div>	
	</nav>
    <div class="row">
    	<div class="col-md-2"></div>
    	<div class="col-md-8">
    		<div class="row" style="height:3px;background-color:#1a54b1"></div>
    		<div class="row text-left" style="height:35px;background-color:#dce4f3"><button class="btn" style="background-color:#dce4f3">&nbsp;&nbsp;生产视频</button></div>
    		<br/>
    		<div class="row" id="videoPanel">
    <%-- 			<div class="col-md-3">
    				<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
					<div class="caption">
						<h6>圆形带孔磁铁</h6>
					</div>
    			</div> --%>
    		</div>
    	</div>
    	<div class="col-md-2"></div>
    </div>
   </div>
  </body>
</html>
