<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'Pre_AboutUs.jsp' starting page</title>
   <!--移动设置优先 -->
    <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
	<meta name="author" content="LayoutIt!">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	<script src="${pageContext.request.contextPath }/static/js/bootstrap/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath }/static/js/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath }/static/js/bootstrap/js/scripts.js"></script>
    <link href="${pageContext.request.contextPath }/static/js/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath }/static/js/bootstrap/css/style.css" rel="stylesheet">
    <!--[if lt IE 9]>
	  <script src="http://apps.bdimg.com/libs/html5shiv/3.7/html5shiv.min.js"></script>
	  <script src="http://apps.bdimg.com/libs/respond.js/1.4.2/respond.min.js"></script>
	<![endif]-->
	<style type="text/css">
		.classification li {
			height:35px;
			padding:4% 0 4% 20%;
			margin-top:6px;
			background-image: url(${pageContext.request.contextPath }/static/image/list_background.png);
		}
	</style>
<script type="text/javascript">
	$(function () {
		$('#myTab li:eq(0) a').tab('show');
	});
	function companyProfile(){
		var dataVo = {moduleName : "公司简介", modulePage : "关于我们"};
		$.ajax({
	    	 type:'post',
	    	 url:'${pageContext.request.contextPath}/webContentManager/getAboutUsCommponyProfile',
	    	 data:JSON.stringify(dataVo),
	    	 dataType : 'json',
	    	 contentType : "application/json;charset=utf-8",
	    	 success : function (data){
	    		if (data.success == "true") { 
	    			var dynamicData = data.datas;
	    			$("#showDataPanal").append(dynamicData);
				} else {
					
				}
	    	 }
	     });
		$("#companyProfile").css("display","block");
		$("#developmentHistory").css("display","none");
		$("#companyCulture").css("display","none");
		$("#touchUs").css("display","none");
	}
	function developmentHistory(){
		$("#companyProfile").css("display","none");
		$("#developmentHistory").css("display","block");
		$("#companyCulture").css("display","none");
		$("#touchUs").css("display","none");
	}
	function companyCulture(){
		$("#companyProfile").css("display","none");
		$("#developmentHistory").css("display","none");
		$("#companyCulture").css("display","block");
		$("#touchUs").css("display","none");
	}
	function touchUs(){
		$("#companyProfile").css("display","none");
		$("#developmentHistory").css("display","none");
		$("#companyCulture").css("display","none");
		$("#touchUs").css("display","block");
	}
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
								  <li><a href="${pageContext.request.contextPath }/productVideo/toProductJsp">生产视频</a></li>
								  <li><a href="#">强力磁铁</a></li>
								  <li><a href="${pageContext.request.contextPath }/MagnetClassification/getList">磁性制品</a></li>
								  <li><a href="#">采购报价</a></li>
								  <li><a href="#">新闻中心</a></li>
								  <li  class="active"><a href="${pageContext.request.contextPath }/MessageInfo/aboutUs">关于我们</a></li>
								</ul>
						</div>
							
					</div>
					<div class="col-md-2"></div>
				</div>	
	</nav>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<div class="row">
					<div class="col-md-3">
						<div class="caption">
							<h4 style="background-color:#1a54b1;padding:5px;color:white">&nbsp;&nbsp;关于我们</h4>
							<ul class="list-unstyled classification">
								<li><a href="#" onclick="companyProfile();">公司简介</a></li>
								<li><a href="#" onclick="developmentHistory();">发展历程</a></li>
								<li><a href="#" onclick="companyCulture();">公司文化</a></li>
								<li><a href="#" onclick="touchUs();">联系我们</a></li>
							</ul>
							<h4 style="background-color:#1a54b1;padding:5px;color:white">&nbsp;&nbsp;联系我们</h4>
							<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/contactUs.png" class="img-responsive">
							<table class="table table-hover table-condensed" style="font-size:13px">
								<tr>
									<th>手机:</th><th>2342345435435</th>
								</tr>
								<tr>
									<th>联系人:</th><th>魏经理</th>
								</tr>
								<tr>
									<th>电话:</th><th>0516—5435435</th>
								</tr>
								<tr>
									<th>QQ:</th><th>89898795435</th>
								</tr>
								<tr>
									<th>E-mail:</th><th>898987954@qq.com</th>
								</tr>
								<tr>
									<th>网址:</th><th>www.zs.com</th>
								</tr>
								<tr>
									<th>地址:</th><th>江苏省徐州市</th>
								</tr>
							</table>
						</div>
					</div>
					<div class="col-md-9"  style="padding-right:0px;overflow:hidden">
						<div class="caption">
							<div class="row" style="height:3px;background-color:#1a54b1;margin-top:10px;margin-left:1px">
							</div>
							<div style="height:32px;background-color:#dce4f3;padding:0px;">
								<span class="btn pull-left">公司简介</span>
							</div>
						</div>
						<div class="row" id="showDataPanal" style="padding:20px">
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
  </body>
</html>
