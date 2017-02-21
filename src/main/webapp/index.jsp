<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
	<!--移动设置优先 -->
    <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">

    <title>智晟磁业</title>
    <meta name="description" content="Source code generated using layoutit.com">
    <meta name="author" content="LayoutIt!">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	<!-- 新 Bootstrap 核心 CSS 文件 -->
	<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap.min.css">
	
	<!-- 可选的Bootstrap主题文件（一般不用引入） -->
	<link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.0/css/bootstrap-theme.min.css">
	
	<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="http://cdn.bootcss.com/jquery/1.11.1/jquery.min.js"></script>
	
	<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
	<script src="http://cdn.bootcss.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
      <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
	<style type="text/css">
	#ownClassfication li{
		margin-bottom:2.5px
	}
	</style>
	<script type="text/javascript">
	$(function () {
		$('#myTab li:eq(0) a').tab('show');
	});
	</script>
  </head>
  <body>

    <div class="container-fluid">
			<div class="row">
				<div class="col-md-12" style="height:5px;background-color:#0066cc">
				</div>
			</div>
		<nav class="navbar navbar-default">
				<div class="row">
					<div class="col-md-2"></div>
					<div class="col-md-8 col-xs-12">
						<!-- logo -->
						<div class="navbar-header" style="margin-top:10px"> 
							<img alt="logo Preview" src="${pageContext.request.contextPath }/static/image/zm_logo.png" width="90%" height="90%"  class="img-responsive">
						</div>
						<div class="collapse  navbar-collapse " style="margin-top:23px;margin-left:10px">
								<ul class="nav nav-pills">
								  <li class="active"><a href="#">首页</a></li>
								  <li><a href="${pageContext.request.contextPath }/productVideo/toProductJsp">生产视频</a></li>
								  <li><a href="#">强力磁铁</a></li>
								  <li><a href="${pageContext.request.contextPath }/MagnetClassification/getList">磁性制品</a></li>
								  <li><a href="#">采购报价</a></li>
								  <li><a href="#">新闻中心</a></li>
								  <li><a href="${pageContext.request.contextPath }/MessageInfo/aboutUs">关于我们</a></li>
								</ul>
						</div>
							
					</div>
					<div class="col-md-2"></div>
				</div>	
			<div class="row">
				<div class="col-md-12">
					<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/zs_bigImg.jpg" width="100%" height="100%">
				</div>
			</div>
	</nav>
	
	<div class="row">
		<div class="col-md-2">
		</div>
		<div class="col-md-8">
				<h3>磁铁分类</h3>
					<div class="row" style="margin-top:-10px;padding-left:15px">
						<div class="pull-left" style="width:12%">
							<h5>Classification</h5>
						</div>
						<div  class="pull-left"  style="width:88%">
							<hr style="height:3px;border:none;border-top:1px solid #555555"/>
						</div>
					</div>
			<div class="row">
				<div class="col-md-3">
					<div class="thumbnail">
						<img alt="圆形磁铁图片" src="${pageContext.request.contextPath }/static/image/zs_ctfl_yxImg.png">
						<div class="caption">
							<p><span class="label label-danger">圆形磁铁分类</span></p>
							<p><a href="#">圆片磁铁</a></p>
							<p><a href="#">圆环磁铁</a></p>
							<p><a href="#">圆柱磁铁</a></p>
							<p><a href="#">圆形沉头孔磁铁</a></p>
						</div>
					</div>
				</div>
				<div class="col-md-3">
					<div class="thumbnail">
						<img alt="圆形磁铁图片" src="${pageContext.request.contextPath }/static/image/zs_ctfl_fxImg.png">
						<div class="caption">
							<p><span class="label label-danger">方形磁铁分类</span></p>
							<p><a href="#">方形磁铁</a></p>
							<p><a href="#">长方形磁铁</a></p>
							<p><a href="#">方形沉头孔磁铁</a></p>
							<p><a href="#" style="visibility:hidden">other</a></p>
						</div>
					</div>
				</div>
				<div class="col-md-3">
					<div class="thumbnail">
						<img alt="圆形磁铁图片" src="${pageContext.request.contextPath }/static/image/zs_ctfl_yixImg.png">
						<div class="caption">
							<p><span class="label label-danger">异形磁铁分类</span></p>
							<p><a href="#">异型磁铁</a></p>
							<p><a href="#">梯形磁铁</a></p>
							<p><a href="#">瓦形磁铁</a></p>
							<p><a href="#">大小头磁铁</a></p>
						</div>
					</div>
				</div>
				<div class="col-md-3">
					<div class="thumbnail">
						<img alt="圆形磁铁图片" src="${pageContext.request.contextPath }/static/image/zs_ctfl_other.png">
						<div class="caption">
							<p><span class="label label-danger">按用途分类</span></p>
							<ul class="list-inline" id="ownClassfication">
							  <li><a herf="#"  class="label label-primary">五金类</a></li>
							  <li><a herf="#"  class="label label-primary">电机类</a></li>
							  <li><a herf="#"  class="label label-primary">皮套类</a></li>
							  <li><a herf="#"  class="label label-primary">喇叭磁铁</a></li>
							   <li><a herf="#"  class="label label-primary">玩具类</a></li>
							  <li><a herf="#"  class="label label-primary">真空模机类</a></li>
							  <li><a herf="#"  class="label label-primary">LED磁铁</a></li>
							  <p><a href="#" style="visibility:hidden">other</a></p>
							</ul>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="col-md-2">
		</div>
	</div>
	<div class="row">
				<div class="col-md-2">
				</div>
				<div class="col-md-8">
					<h3>
						头条推荐
					</h3>
					<div class="row" style="margin-top:-10px;padding-left:15px">
						<div class="pull-left" style="width:15%">
							<h5>Recommendation</h5>
						</div>
						<div  class="pull-left"  style="width:85%">
							<hr style="height:3px;border:none;border-top:1px solid #555555"/>
						</div>
					</div>
					</div>
				</div>
				<div class="col-md-2">
				</div>
			</div>
	<div class="row" style="background-color:#d8d8d8;padding:30px">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-2">
					
				</div>
				<div class="col-md-8">
						<div class="row">
						<div class="col-md-6">
							<img alt="img"  src="${pageContext.request.contextPath }/static/image/zs_tttj_pic.png"  width="376px" height="273px" >
						</div>
						<div class="col-md-3">
							<p><span class="label label-danger">热门信息</span></p>
							<p><a href="#">异型磁铁</a></p>
							<p><a href="#">梯形磁铁</a></p>
							<p><a href="#">瓦形磁铁</a></p>
							<p><a href="#">大小头磁铁</a></p>
						</div>
						<div class="col-md-3">
							<p><span class="label btn-info">最新信息</span></p>
							<p><a href="#">异型磁铁</a></p>
							<p><a href="#">梯形磁铁</a></p>
							<p><a href="#">瓦形磁铁</a></p>
							<p><a href="#">大小头磁铁</a></p>
						</div>
					</div>
					</div>
				</div>
				<div class="col-md-2">
					
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-2">
		</div>
		<div class="col-md-8">
			<h3>
				产品中心
			</h3>
			<div class="row" style="margin-top:-10px">
				<div class="col-md-1">
					<h5>Products</h5>
				</div>
				<div class="col-md-11">
					<hr style="height:1px;border:none;border-top:1px solid #555555;"/>
				</div>
			</div>
			<ul id="myTab" class="nav nav-tabs">
			<li class="active"><a href="#fxct" data-toggle="tab">方形磁铁</a></li>
			<li><a href="#yxct" data-toggle="tab">圆形磁铁</a></li>
			<li><a href="#yixingct" data-toggle="tab">异型磁铁</a></li>
			<li><a href="#cxzp" data-toggle="tab">磁性制品</a></li>
			</ul>
		</div>
		<div class="col-md-2">
		</div>
	</div>
	<div id="myTabContent" class="tab-content">
		<div class="tab-pane fade in active" id="fxct">
			<div class="row">
				<div class="col-md-2">
				</div>
				<div class="col-md-8">
					<div class="row">
						<div class="col-md-3">
							<div class="thumbnail">
								<img alt="圆形磁铁图片" src="${pageContext.request.contextPath }/static/image/zs_ctfl_yxImg.png">
								<div class="caption">
									<p><span class="label label-danger">圆形磁铁分类</span></p>
									<p><a href="#">圆片磁铁</a></p>
									<p><a href="#">圆环磁铁</a></p>
									<p><a href="#">圆柱磁铁</a></p>
									<p><a href="#">圆形沉头孔磁铁</a></p>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="thumbnail">
								<img alt="圆形磁铁图片" src="${pageContext.request.contextPath }/static/image/zs_ctfl_fxImg.png">
								<div class="caption">
									<p><span class="label label-danger">方形磁铁分类</span></p>
									<p><a href="#">方形磁铁</a></p>
									<p><a href="#">长方形磁铁</a></p>
									<p><a href="#">方形沉头孔磁铁</a></p>
									<p><a href="#" style="visibility:hidden">other</a></p>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="thumbnail">
								<img alt="圆形磁铁图片" src="${pageContext.request.contextPath }/static/image/zs_ctfl_yixImg.png">
								<div class="caption">
									<p><span class="label label-danger">异形磁铁分类</span></p>
									<p><a href="#">异型磁铁</a></p>
									<p><a href="#">梯形磁铁</a></p>
									<p><a href="#">瓦形磁铁</a></p>
									<p><a href="#">大小头磁铁</a></p>
								</div>
							</div>
						</div>
						<div class="col-md-3">
							<div class="thumbnail">
								<img alt="圆形磁铁图片" src="${pageContext.request.contextPath }/static/image/zs_ctfl_other.png">
								<div class="caption">
									<p><span class="label label-danger">按用途分类</span></p>
									<ul class="list-inline" id="ownClassfication">
									  <li><a herf="#"  class="label label-primary">五金类</a></li>
									  <li><a herf="#"  class="label label-primary">电机类</a></li>
									  <li><a herf="#"  class="label label-primary">皮套类</a></li>
									  <li><a herf="#"  class="label label-primary">喇叭磁铁</a></li>
									   <li><a herf="#"  class="label label-primary">玩具类</a></li>
									  <li><a herf="#"  class="label label-primary">真空模机类</a></li>
									  <li><a herf="#"  class="label label-primary">LED磁铁</a></li>
									  <p><a href="#" style="visibility:hidden">other</a></p>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-2">
				</div>
			</div>
		</div>
		<div class="tab-pane fade" id="yxct">
			<div class="row">
				<div class="col-md-2">
				</div>
				<div class="col-md-8">
					<p>圆形磁铁</p>
				</div>
				<div class="col-md-2">
				</div>
			</div>
		</div>
		<div class="tab-pane fade" id="yixingct">
			<div class="row">
				<div class="col-md-2">
				</div>
				<div class="col-md-8">
					<p>异型磁铁</p>
				</div>
				<div class="col-md-2">
				</div>
			</div>
		</div>
		<div class="tab-pane fade" id="cxzp">
			<div class="row">
				<div class="col-md-2">
				</div>
				<div class="col-md-8">
					<p>磁性制品</p>
				</div>
				<div class="col-md-2">
				</div>
			</div>
		</div>
	</div>
	<div class="row"  style="background-color:#d8d8d8;padding:15px 0px">
		<div class="col-md-12">
			<div class="row">
				<div class="col-md-2">
				</div>
				<div class="col-md-8">
					<h3>
						我们的优势
					</h3>
					<div class="row" style="margin-top:-10px">
						<div class="col-md-1">
							<h5>Advantage</h5>
						</div>
						<div class="col-md-11">
							<hr style="height:1px;border:none;border-top:1px solid #555555;"/>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							
								<div  class="row">
									<div class="col-md-6">
										<div class="thumbnail">
											<img alt="Bootstrap Thumbnail First" src="http://lorempixel.com/output/people-q-c-600-200-1.jpg">
										</div>
									</div>
									<div class="col-md-6">
										<div class="caption">
											<h3>
												Thumbnail label
											</h3>
											<p>
												Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
											</p>
										</div>
									</div>
								</div>
						</div>
						<div class="col-md-6">
							
								<div  class="row">
									<div class="col-md-6">
										<div class="thumbnail">
											<img alt="Bootstrap Thumbnail First" src="http://lorempixel.com/output/people-q-c-600-200-1.jpg">
										</div>
									</div>
									<div class="col-md-6">
										<div class="caption">
											<h3>
												Thumbnail label
											</h3>
											<p>
												Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
											</p>
										</div>
									</div>
								</div>
						</div>
					</div>
					<div class="row">
						<div class="col-md-6">
							
								<div  class="row">
									<div class="col-md-6">
										<div class="thumbnail">
											<img alt="Bootstrap Thumbnail First" src="http://lorempixel.com/output/people-q-c-600-200-1.jpg">
										</div>
									</div>
									<div class="col-md-6">
										<div class="caption">
											<h3>
												Thumbnail label
											</h3>
											<p>
												Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
											</p>
										</div>
									</div>
								</div>
						</div>
						<div class="col-md-6">
							
								<div  class="row">
									<div class="col-md-6">
										<div class="thumbnail">
											<img alt="Bootstrap Thumbnail First" src="http://lorempixel.com/output/people-q-c-600-200-1.jpg">
										</div>
									</div>
									<div class="col-md-6">
										<div class="caption">
											<h3>
												Thumbnail label
											</h3>
											<p>
												Cras justo odio, dapibus ac facilisis in, egestas eget quam. Donec id elit non mi porta gravida at eget metus. Nullam id dolor id nibh ultricies vehicula ut id elit.
											</p>
										</div>
									</div>
								</div>
						</div>
					</div>
				</div>
				<div class="col-md-2">
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12" style="background-color:#302d27;color:white">
			<div class="row">
				<div class="col-md-2">
				</div>
				<div class="col-md-8">
					<div class="row">
						<div class="col-md-6">
							<ol class="list-unstyled">
								<li>
									Lorem ipsum dolor sit amet
								</li>
								<li>
									Consectetur adipiscing elit
								</li>
								<li>
									Integer molestie lorem at massa
								</li>
								<li>
									Facilisis in pretium nisl aliquet
								</li>
								<li>
									Nulla volutpat aliquam velit
								</li>
								<li>
									Faucibus porta lacus fringilla vel
								</li>
								<li>
									Aenean sit amet erat nunc
								</li>
								<li>
									Eget porttitor lorem
								</li>
							</ol>
						</div>
						<div class="col-md-6">
						</div>
					</div>
				</div>
				<div class="col-md-2">
				</div>
			</div>
		</div>
	</div>

  
  </body>
</html>