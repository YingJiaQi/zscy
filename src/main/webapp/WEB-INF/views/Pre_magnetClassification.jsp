<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ page import="java.io.*,java.util.* " %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   <!--移动设置优先 -->
    <meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">

    <title>智晟磁业</title>
    <meta name="description" content="Source code generated using layoutit.com">
    <meta name="author" content="LayoutIt!">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
	<script src="${pageContext.request.contextPath }/static/js/jquery-1.11.3.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/picHandle.js"></script>
	<!-- 导入分页js -->
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/webPagination.js"></script>
	<!-- 图片轮播 -->
	<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/unslider/js/unslider-min.js"></script>
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
	//用于保存点击的类目名
	var onclickCategoryName = null;
	var indexParam = null;
		$(function(){
			/* 图片轮播 */
			$('.banner').unslider({
				speed: 500,               //  The speed to animate each slide (in milliseconds)
				delay: 3000,              //  The delay between slide animations (in milliseconds)
				complete: function() {},  //  A function that gets called after every slide animation
				keys: true,               //  Enable keyboard (left, right) arrow shortcuts
				dots: true,               //  Display dot navigation
				fluid: false              //  Support responsive design. May break non-responsive designs
			});
			 $('.unslider-arrow').click(function() {
			        var fn = this.className.split(' ')[1];

			        //  Either do unslider.data('unslider').next() or .prev() depending on the className
			        unslider.data('unslider')[fn]();
			 });
			
			/* 获取后台页面传递的参数，然后赋值给js变量 */
			<%
				String indexParamss = request.getAttribute("indexParam")+"";
			    // indexParamss = new String(indexParamss.getBytes("iso-8859-1"),"utf-8");
			%>
			indexParam = <%=indexParamss %>;
			//alert(indexParam)
			if(indexParam != null){
				var compareString = "五金类,电机类,皮套类,喇叭磁铁,玩具类,真空模机类,LED磁铁,其它";
				if(compareString.indexOf(indexParam) >0){
					//调用磁铁用途
					getDataWithUsed(indexParam);
				}else{
					//调用磁铁分类
					getDetailCategory(indexParam);
				}
				
			}
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
							var optionHtml = "<li style='font-size:1.1em;'><a href='javascript:void(0);' onclick='getDetailCategory(this)'>"+data.datas[i].categoryName+"</a></li>"
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
			var pageSize = 32;
			var curr = 1;
			if(typeof(obj) == "object"){
				onclickCategoryName = $(obj).text();
				//onclickCategoryName = categoryNames[categoryIndex];
			}else if(typeof(obj) == "string"){
				onclickCategoryName = obj;
			}else{
				curr = obj;
			}
			$("#categoryTitlePanel").html(onclickCategoryName );//从数组中获取类目并放入相应位置
			var dataVo = {categoryName:onclickCategoryName ,pageIndex: curr,pageSize: pageSize,parentModule:'磁性制品'}
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
		    				var initialData = data.datas[i].image;
	    					var initialArray = initialData.split("?");
	    					var secArray = initialArray[1].split("&");
	    					var latestData = initialArray[0]+"/"+secArray[0];
		    				var optionHtml = "<div  sourceId = '"+data.datas[i].id+"' style='cursor:pointer'  onclick='commodityDetail(this)' class='col-md-3'><img alt='Image Preview' src='"+hostIpAddress+latestData+"' class='img-responsive img-thumbnail'>"+
							"<div class='caption'><h6>"+data.datas[i].title+"</h6></div></div>";
							$("#magnetListPanel").append(optionHtml);
						}
		    			var pages = Math.ceil(data.total/pageSize); //得到总页数
		    			if(pages >1){
		    				//大于1页时再显示分页
			    			var leftRight = 3;//当前页左右显示的页码
			    			var showPageLocation = $('#commodity_pagination');//显示页码的位置，dom的id标记
			    			//分页
			    			pagination(pages,curr, leftRight ,showPageLocation, "getDetailCategory");
		    			}
					} else {
						var optionHtml = "<div style='width:100px;height:50px;margin:5px 10px;padding:3px 3px 3px 15px;font-size:1.2em;line-height:1.3'>暂无数据</div>"
						$("#magnetListPanel").append(optionHtml);
					}
		    	 }
		     });
		}
		//按用途查询数据,分页也会调用这个方法，所以要判断参数类型来区分
		function getDataWithUsed(obj){
			$("#magnetListPanel").empty();
			$("#magnetListPanel").append("<br/>");
			$("#categoryTitlePanel").html(obj);
			var pageSize = 32;
			var curr = 1;
			if(typeof(obj) != "string"){
				//分页调用
				curr = obj;
			}else{
				//点击类目调用,onclickCategoryName是全局变量定义在方法外
				onclickCategoryName = obj;
			}
			var dataVo = {categoryName:onclickCategoryName,pageIndex: curr,pageSize: pageSize, parentModule:'磁性制品'};
			$.ajax({
		    	 type:'post',
		    	 url:'${pageContext.request.contextPath}/PreWebContentManager/getMagnetDataByUsedName',
		    	 data:JSON.stringify(dataVo),
		    	 dataType : 'json',
		    	 contentType : "application/json;charset=utf-8",
		    	 success : function (data){
		    		 
		    		if(data.success == "true"){	
		    			
		    			for(var i = 0; i< data.datas.length ;i++){
		    				var initialData = data.datas[i].image;
	    					var initialArray = initialData.split("?");
	    					var secArray = initialArray[1].split("&");
	    					var latestData = initialArray[0]+"/"+secArray[0];
		    				var optionHtml = "<div   sourceId = '"+data.datas[i].id+"'  style='cursor:pointer'   onclick='commodityDetail(this)'  class='col-md-3'><img alt='"+hostIpAddress+latestData+"' class='img-responsive img-thumbnail'>"+
							"<div class='caption'><h6>"+data.datas[i].title+"</h6></div></div>";
							$("#magnetListPanel").append(optionHtml);
						}
		    			
		    			var pages = Math.ceil(data.total/pageSize); //得到总页数
		    			if(pages >1){
		    				//大于1页时再显示分页
			    			var leftRight = 3;//当前页左右显示的页码
			    			var showPageLocation = $('#commodity_pagination');//显示页码的位置，dom的id标记
			    			//分页
			    			pagination(pages,curr, leftRight ,showPageLocation, "getDataWithUsed");
		    			}
					} else {
						var optionHtml = "<div style='width:100px;height:50px;margin:5px 10px;padding:3px 3px 3px 15px;font-size:1.2em;line-height:1.3'>暂无数据</div>"
						$("#magnetListPanel").append(optionHtml);
					}
		    	 }
		     });
		}
		function commodityDetail(obj){
			$("#alertPanel").remove();
			//根据ID查找资源
			var dataVo = {id:$(obj).attr("sourceId")};
			$.ajax({
		    	 type:'post',
		    	 url:'${pageContext.request.contextPath}/PreWebContentManager/getCommodityDetailByID',
		    	 data:JSON.stringify(dataVo),
		    	 dataType : 'json',
		    	 contentType : "application/json;charset=utf-8",
		    	 success : function (data){
		    		 if(data.success == "true"){
		    			 var initialArray = data.commodityMain.image.split("?");
	 					var secArray = initialArray[1].split("&");
	 					//var latestData = initialArray[0]+"/"+secArray[0];
	 					var picArr = [];
	 					for(var j=0;j<secArray.length-1;j++){
	 						picArr[j] = hostIpAddress + initialArray[0]+"/"+secArray[j];
	 					}
			    		 var windowPanel = "<div id='alertPanel' style='background:white;width:100%;height:100%;z-index:999;position:fixed;top:0;left:0;overflow:auto;'>"+
				    			"<p><button class='btn' style='float:right;margin:10px 30px 20px 10px' onclick='closedWindowPanel(this)'>关闭</button></p>"+
				    			"<br/><br/><br/><br/><br/>"+
				    			"<div class='row'>"+
				    				"<div class='col-md-2'></div>"+
				    				"<div class='col-md-8'>"+
				    					"<div class='row' height:500px'>"+
				    						"<div class='col-md-6' id='picsShowPanels' >"+
				    						"</div>"+
				    						"<div class='col-md-6'  id='basicInfos'></div>"+
				    					"</div><br/><br/><hr/>"+
				    					"<div class='row'  id='specification'>"+
				    					"<h3>规格参数</h3><br/></div><br/><br/><hr/>"+
				    					"<div class='row'  id='commodityDesc'>"+
				    					"<h3>产品详情</h3><br/></div>"+
				    				"</div>"+
				    				"<div class='col-md-2'></div>"+
				    			"</div>"+
				    		"</div>";
			    		 $(document.body).append(windowPanel);
			    		 //调用方法，显示图片
			    		 picDisplayCommodity(picArr,$("#picsShowPanels"));
			    		 //显示产品基本信息
			    		 $("#basicInfos").empty();
					    var basicInfo = "<table class='table'>"+
					    		  "<thead>"+
					    		    "<tr>"+
					    		      "<th>"+data.commodityMain.title+"</th>"+
					    		    "</tr>"+
					    		  "</thead>"+
					    		  "<tbody>"+
					    		   "<tr>"+
					    		      "<td>卖点:&nbsp;&nbsp;"+data.commodityMain.sellPoint+"</td>"+
					    		    "</tr>"+
					    		    "<tr>"+
					    		   	   "<td>价格:&nbsp;&nbsp;"+data.commodityMain.price+"&nbsp;<b style='color:red'>元</b></td>"+
					    		    "</tr>"+
					    		    "<tr>"+
					    		   	   "<td>数量:&nbsp;&nbsp;"+data.commodityMain.num+"</td>"+
					    		    "</tr>"+
					    		    "<tr>"+
					    		   	   "<td>可作用途:&nbsp;&nbsp;"+data.commodityMain.usedType+"</td>"+
					    		    "</tr>"+
					    		    "<tr>"+
					    		   	   "<td>所属分类:&nbsp;&nbsp;"+data.commodityMain.categoryName+"</td>"+
					    		    "</tr>"+
					    		  "</tbody>"+
					    		"</table>";
					    	$("#basicInfos").append(basicInfo);
					    	var specificationHtml = "";
					    	for(var k=0;k< data.commoditySpec.length;k++){
					    		var kk = "<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+data.commoditySpec[k].specificationName+"：</span>"+
				    		      "<span style='margin-right:90px'>&nbsp;&nbsp;"+data.commoditySpec[k].specificationContent+"</span>";
					    		specificationHtml += kk;
					    	} 
					    	$("#specification").append(specificationHtml);
					    	$("#commodityDesc").append(data.commodityDesc.content);
		    		 }else{
		    			 var windowPanel = "<div style='background:white;width:100%;height:100%;z-index:999;position:fixed;top:0;left:0;overflow:auto;'>"+
			    			"<p><button class='btn' style='float:right;margin:10px 30px 20px 10px' onclick='closedWindowPanel(this)'>关闭</button></p>"+
			    			"<br/><br/><br/><br/><br/><h1 style='padding:10% 15% 15% 32%'>获取产品失败，请联系管理员</h1>"+
			    		"</div>";
		    			 $(document.body).append(windowPanel);
		    		 }
		    	 }
			})
		    
		}
		function closedWindowPanel(target){
			$(target).parent().parent().hide();
		}
	</script>
	<style type="text/css">
		.banner { position: relative; overflow: auto; }
    	.banner li { list-style: none; }
        .banner ul li { float: left; }
	</style>
  </head>
  
  <body>
  <%-- 	<c:set var="indexParam" scope="session" value="${indexParam}"/> --%>
  	 <c:if test="${!empty indexParam}">
  	 	
  	 </c:if>
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
								  <li  class="active"><a href="${pageContext.request.contextPath }/MagnetClassification/getList">磁性制品</a></li>
								  <li><a href="${pageContext.request.contextPath }/PreWebContentManager/Pre_buyPrice">采购报价</a></li>
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
				<%-- <img alt="Image Preview" src="${pageContext.request.contextPath }/static/image/ctfl_big.png" class="img-responsive"> --%>
				<div class="banner">
				    <ul>
				        <li>This is a slide.</li>
				        <li>This is another slide.</li>
				        <li>This is a final slide.</li>
				    </ul>
				</div>
				<a href="#" class="unslider-arrow prev">Previous slide</a>
				<a href="#" class="unslider-arrow next">Next slide</a>
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
								<li><a href="javascript:void(0);" onclick="getDataWithUsed('五金类')">五金类</a></li>
								<li><a href="javascript:void(0);" onclick="getDataWithUsed('电机类')">电机类</a></li>
								<li><a href="javascript:void(0);" onclick="getDataWithUsed('喇叭磁铁')">喇叭磁铁</a></li>
								<li><a href="javascript:void(0);" onclick="getDataWithUsed('玩具类')">玩具类</a></li>
								<li><a href="javascript:void(0);" onclick="getDataWithUsed('皮套类')">皮套类</a></li>
								<li><a href="javascript:void(0);" onclick="getDataWithUsed('真空膜机类')">真空膜机类</a></li>
								<li><a href="javascript:void(0);" onclick="getDataWithUsed('LED磁铁')">LED磁铁</a></li>
								<li><a href="javascript:void(0);" onclick="getDataWithUsed('其它')">其它</a></li>
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
						<ul id="commodity_pagination" style="list-style-type:none;"></ul>
					</div>
				</div>
			</div>
			<div class="col-md-2"></div>
		</div>
	</div>
  </body>
</html>
