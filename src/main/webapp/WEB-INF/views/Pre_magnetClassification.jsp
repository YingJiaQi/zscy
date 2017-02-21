<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <!--移动设置优先 -->
    <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">

    <title>智晟磁业</title>
    <meta name="description" content="Source code generated using layoutit.com">
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
								  <li  class="active"><a href="${pageContext.request.contextPath }/MagnetClassification/getList">磁性制品</a></li>
								  <li><a href="#">采购报价</a></li>
								  <li><a href="${pageContext.request.contextPath }/MessageInfo/newsCentor">新闻中心</a></li>
								  <li><a href="${pageContext.request.contextPath }/MessageInfo/aboutUs">关于我们</a></li>
								</ul>
						</div>
							
					</div>
					<div class="col-md-2"></div>
				</div>	
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8">
				<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/ctfl_big.png" class="img-responsive">
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
							<h4 style="background-color:#1a54b1;padding:5px;color:white">&nbsp;&nbsp;按形状分类</h4>
							<ul class="list-unstyled classification">
								<li>圆环</li>
								<li>圆柱</li>
								<li>圆环</li>
								<li>圆柱</li>
								<li>圆环</li>
								<li>圆柱</li>
								<li>圆环</li>
								<li>圆柱</li>
								<li>圆环</li>
							</ul>
							<h4 style="background-color:#1a54b1;padding:5px;color:white">&nbsp;&nbsp;按用途分类</h4>
							<ul class="list-unstyled classification">
								<li>五金类</li>
								<li>电机类</li>
								<li>喇叭磁铁</li>
								<li>玩具类</li>
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
								<span class="btn pull-left">磁铁分类</span>
								<span class="btn pull-right">当前位置:</span>
							</div>
						</div>
						<div class="row">
							<br/>
							<div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div>
							<div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div>
							<div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div>
							<div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div>
							<div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div>
							<div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div>
							<div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div>
							<div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div>
							<div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div>
							<div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div>
							<div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div>
							<div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div>
							<div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
  </body>
</html>
