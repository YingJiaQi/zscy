<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

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
	<style type="text/css">
		.classification li {
			height:35px;
			padding:4% 0 4% 20%;
			margin-top:6px;
			background-image: url(${pageContext.request.contextPath }/static/image/list_background.png);
		}
	</style>
	<script type="text/javascript">
	//用于存放新闻id,在用户点击后使用，向后台根据id查询新闻内容
	var newsIDs = new Array();
	var newsTitles = [];
	
	
		$(function () {
			$.ajax({
		    	 type:'post',
		    	 url:'${pageContext.request.contextPath}/PreWebContentManager/getByPriceData',
		    	 dataType : 'json',
		    	 contentType : "application/json;charset=utf-8",
		    	 success : function (data){
		    		if (data.success == "true") {
		    			
		    			for(var i = 0; i< data.datas.length ;i++){
		    				newsIDs[i] = String(data.datas[i].sourceContent);
		    				newsTitles[i] = String(data.datas[i].sourceTitle);
		    				if(data.datas[i].viewCount == 1){
		    					var optionHtml = "<div style='height:30px;overflow:hidden;padding:3px;font-size:1.0em;'><a href='javascript:void(0);' onclick='showDetailNews("+i+")' >&nbsp;&nbsp;&nbsp;新闻标题："+data.datas[i].sourceTitle+"------&nbsp;&nbsp;创建时间:"+data.datas[i].createTime+"</a><img src='${pageContext.request.contextPath }/static/image/hot.png' width='30px' height='30px' style='margin-top:-8px;margin-left:20px'/></div>"
								$("#showDataPanal").append(optionHtml);
		    				}else{
								var optionHtml = "<div style='height:30px;overflow:hidden;padding:3px;font-size:1.0em;'><a href='javascript:void(0);' onclick='showDetailNews("+i+")' >&nbsp;&nbsp;&nbsp;新闻标题："+data.datas[i].sourceTitle+"------&nbsp;&nbsp;创建时间:"+data.datas[i].createTime+"</a></div>"
								$("#showDataPanal").append(optionHtml);
		    				}
						}
					} else {
						var optionHtml = "<div style='width:100px;height:50px;margin:5px 10px;padding:3px 3px 3px 15px;font-size:1.1em;line-height:1.3'>暂无数据</div>"
						$("#showDataPanal").append(optionHtml);
					}
		    	 }
		     });
		});
		function showDetailNews(obj){
			$("#newsContentShow").empty();
			$("#newsTitlePanel").empty();
			$("#newsContentPanel").css("display","block");
			$("#newsContentShow").append(newsIDs[obj]);
			$("#newsTitlePanel").text(newsTitles[obj]);
		}
		function closePanel(){
			$("#newsContentPanel").css("display","none");
			$("#newsContentShow").empty();
			$("#newsTitlePanel").empty();
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
								 <!--  <li><a href="#">强力磁铁</a></li> -->
								  <li><a href="${pageContext.request.contextPath }/MagnetClassification/getList">磁性制品</a></li>
								  <li class="active"><a href="${pageContext.request.contextPath }/PreWebContentManager/Pre_buyPrice">采购报价</a></li>
								  <li ><a href="${pageContext.request.contextPath }/MessageInfo/newsCentor">新闻中心</a></li>
								  <li><a href="${pageContext.request.contextPath }/MessageInfo/aboutUs">关于我们</a></li>
								</ul>
						</div>
							
					</div>
					<div class="col-md-2"></div>
				</div>	
		</nav>
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8" id="showDataPanal">
				<div style="height:32px;background-color:#dce4f3;padding:0px;">
					<span class="btn pull-left">采购报价</span>
				</div><br/>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
	<div class="panel panel-success" id="newsContentPanel" style="display:none;height:100%">
	    <div class="panel-heading">
	        <h3 class="panel-title">
	            <b id="newsTitlePanel"></b><span class="btn btn-danger btn-md pull-right" onclick="closePanel()" style="margin-top:-8px">X</span>
	        </h3>
	    </div>
	    <div class="panel-body" id="newsContentShow">
	        面板内容
	    </div>
	</div>
  </body>
</html>
