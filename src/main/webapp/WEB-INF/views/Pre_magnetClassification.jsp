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
	<script src="${pageContext.request.contextPath }/static/js/jquery-1.8.3.js"></script>
	<!-- 导入分页js -->
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/laypage/laypage.js"></script>
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
	//存储类目名
	var categoryNames = new Array();
		$(function(){
			/**
			*页面加载时向后台请求，分类数据
			*/
			$.ajax({
		    	 type:'post',
		    	 url:'${pageContext.request.contextPath}/PreWebContentManager/getMagnetClassficationData',
		    	 dataType : 'json',
		    	 contentType : "application/json;charset=utf-8",
		    	 success : function (data){
		    		if (data.success == "true") {
		    			for(var i = 0; i< data.datas.length ;i++){
		    				categoryNames[i] =  String(data.datas[i].categoryName);
							var optionHtml = "<li style='font-size:1.1em;'><a href='javascript:void(0);' onclick='getDetailCategory("+i+")'>"+data.datas[i].categoryName+"</a></li>"
							$("#categoryPanel").append(optionHtml);
						}
					} else {
						var optionHtml = "<div style='width:100px;height:50px;margin:5px 10px;padding:3px 3px 3px 15px;font-size:1.1em;line-height:1.3'>暂无数据</div>"
						$("#categoryPanel").append(optionHtml);
					}
		    	 }
		     });
		})
		function getDetailCategory(obj){
			$("#magnetListPanel").empty();
			$("#magnetListPanel").append("<br/>");
			$("#categoryTitlePanel").html(categoryNames[obj]);
			var dataVo = {categoryTitle:categoryNames[obj]}
			/**
			*根据分类名，到后台取出该分类下的所有数据
			*/
			$.ajax({
		    	 type:'post',
		    	 url:'${pageContext.request.contextPath}/PreWebContentManager/getMagnetClassficationDataByTitle',
		    	 data:JSON.stringify(dataVo),
		    	 dataType : 'json',
		    	 contentType : "application/json;charset=utf-8",
		    	 success : function (data){
		    		if (data.success == "true") {
		    			for(var i = 0; i< data.datas.length ;i++){
		    				var optionHtml = "<div class='col-md-3'><img alt='Image Preview' src='${pageContext.request.contextPath }/static/image/classification_yxct.png' class='img-responsive img-thumbnail'>"+
							"<div class='caption'><h6>"+data.datas[i].title+"</h6></div></div>";
							$("#magnetListPanel").append(optionHtml);
						}
					} else {
						var optionHtml = "<div style='width:100px;height:50px;margin:5px 10px;padding:3px 3px 3px 15px;font-size:1.2em;line-height:1.3'>暂无数据</div>"
						$("#magnetListPanel").append(optionHtml);
					}
		    	 }
		     });
		}
		//按用途查询数据
		function getDataWithUsed(obj){
			$("#magnetListPanel").empty();
			$("#magnetListPanel").append("<br/>");
			$("#categoryTitlePanel").html($(obj).html());
			var pageSize = 32;
			var dataVo = {usedFunction:$(obj).html(),pageIndex: 1,pageSize: pageSize };
			$.ajax({
		    	 type:'post',
		    	 url:'${pageContext.request.contextPath}/PreWebContentManager/getMagnetDataByUsedName',
		    	 data:JSON.stringify(dataVo),
		    	 dataType : 'json',
		    	 contentType : "application/json;charset=utf-8",
		    	 success : function (data){
		    		if(data.success == "true"){	
		    	        laypage({
				            cont: 'page1', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>  
				            pages: data.pageCount, //通过后台拿到的总页数  
				            curr: 1, //初始化当前页  
				            skin: '#429842',//皮肤颜色  
				            groups: 3, //连续显示分页数  
				            skip: true, //是否开启跳页  
				            first: '首页', //若不显示，设置false即可  
				            last: '尾页', //若不显示，设置false即可  
				            //prev: '<', //若不显示，设置false即可  
				            //next: '>', //若不显示，设置false即可  
				            jump: function (e) { //触发分页后的回调  
				            	var dataVos = {usedFunction:$(obj).html(),pageIndex: e.curr,pageSize: pageSize };
				            	$.ajax({
				   		    	 type:'post',
				   		    	 url:'${pageContext.request.contextPath}/PreWebContentManager/getMagnetDataByUsedName',
				   		    	 data:JSON.stringify(dataVos),
				   		    	 dataType : 'json',
				   		    	 contentType : "application/json;charset=utf-8",
				   		    	 success : function (data){
				   		    		 
					   		    	  	e.pages = e.last = data.pageCount; //重新获取总页数，一般不用写  
					                    //渲染  
					                    var view = document.getElementById('magnetListPanel'); //你也可以直接使用jquery  
					                    //解析数据  
					                    //var resultHtml = PackagData(res); 
					                    var resultHtml = "";
					                    for(var i = 0; i< data.datas.length ;i++){
				   		    				var optionHtml = "<div class='col-md-3'><img alt='Image Preview' src='${pageContext.request.contextPath }/static/image/classification_yxct.png' class='img-responsive img-thumbnail'>"+
				   							"<div class='caption'><h6>"+data.datas[i].title+"</h6></div></div>";
				   		    				resultHtml += optionHtml;
				   						}
					                    view.innerHTML = resultHtml;  
				   		    	 	}
				   		     	});
				            }  
				        }); 
					} else {
						var optionHtml = "<div style='width:100px;height:50px;margin:5px 10px;padding:3px 3px 3px 15px;font-size:1.2em;line-height:1.3'>暂无数据</div>"
						$("#magnetListPanel").append(optionHtml);
					}
		    	 }
		     });
		}
		function searchDataByPagination(obj) {  
			$("#magnetListPanel").empty();
			$("#magnetListPanel").append("<br/>");
			$("#categoryTitlePanel").html($(obj).html());
			var categoryNav  = "";
			var methodParam = "";//标记是根据分类查询还是根据用途查询
			if(typeof(obj) == "number"){
				/**
				*根据分类名，到后台取出该分类下的所有数据
				*/
				categoryNav = categoryNames[obj];
				methodParam = "1";
			}else{
				//按用途查询数据
				categoryNav = $(obj).html();
				methodParam = "2";
			}
		    var pageSize = 32;  
		  
		    //以下将以分页实例，$.getJSON("1","2","3"),1为请求地址，2为请求参数，3为回调函数
		    $.getJSON('${pageContext.request.contextPath}/PreWebContentManager/getMagnetClassficationDataByTitle', {  
		    	methodParam: methodParam,  
		        pageIndex: 1,  
		        pageSize: pageSize,  
		        categoryNav: categoryNav
		    },  
		    function (res) { //从第1页开始请求。返回的json格式可以任意定义  
		        laypage({
		            cont: 'page1', //容器。值支持id名、原生dom对象，jquery对象。【如该容器为】：<div id="page1"></div>  
		            pages: res.pageCount, //通过后台拿到的总页数  
		            curr: 1, //初始化当前页  
		            skin: '#429842',//皮肤颜色  
		            groups: 3, //连续显示分页数  
		            skip: true, //是否开启跳页  
		            first: '首页', //若不显示，设置false即可  
		            last: '尾页', //若不显示，设置false即可  
		            //prev: '<', //若不显示，设置false即可  
		            //next: '>', //若不显示，设置false即可  
		            jump: function (e) { //触发分页后的回调  
		                $.getJSON('/Mobile/AjaxHandler/QuestionAjax.aspx?action=GetRedisJoinMemberInformationById', {  
		                    type: 2,  
		                    ccId: ccId,  
		                    pageIndex: e.curr,//当前页  
		                    pageSize: pageSize,  
		                    saveKey: saveKey  
		                }, function (res) {  
		                    e.pages = e.last = res.pageCount; //重新获取总页数，一般不用写  
		                    //渲染  
		                    var view = document.getElementById('userTable'); //你也可以直接使用jquery  
		                    //解析数据  
		                    var resultHtml = PackagData(res);  
		                    view.innerHTML = resultHtml;  
		                });  
		            }  
		        });  
		    });  
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
							<ul class="list-unstyled classification" id="categoryPanel">
							</ul>
							<h4 style="background-color:#1a54b1;padding:5px;color:white">&nbsp;&nbsp;按用途分类</h4>
							<ul class="list-unstyled classification">
								<li><a href="javascript:void(0);" onclick="getDataWithUsed(this)">五金类</a></li>
								<li><a href="javascript:void(0);" onclick="getDataWithUsed(this)">电机类</a></li>
								<li><a href="javascript:void(0);" onclick="getDataWithUsed(this)">喇叭磁铁</a></li>
								<li><a href="javascript:void(0);" onclick="getDataWithUsed(this)">玩具类</a></li>
								<li><a href="javascript:void(0);" onclick="getDataWithUsed(this)">皮套类</a></li>
								<li><a href="javascript:void(0);" onclick="getDataWithUsed(this)">真空膜机类</a></li>
								<li><a href="javascript:void(0);" onclick="getDataWithUsed(this)">LED磁铁</a></li>
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
								<span class="btn pull-left" id="categoryTitlePanel">磁铁分类</span>
								<!-- <span class="btn pull-right">当前位置:</span> -->
							</div>
						</div>
						<div class="row" id="magnetListPanel">
							<%-- <div class="col-md-3">
								<img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/classification_yxct.png" class="img-responsive img-thumbnail">
								<div class="caption">
									<h6>圆形带孔磁铁</h6>
								</div>
							</div> --%>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
  </body>
</html>
