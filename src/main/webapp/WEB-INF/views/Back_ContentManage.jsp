<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>zmcy</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/utils.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/css/default.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/easyui/ext/jquery.cookie.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/datajs/WdatePicker.js"></script>
<script type="text/javascript">
/* 资源列表初始化加载 列配置*/
var columns = [
		[{
			field: 'id',
			title: 'ID',
			width: 150,
			align: 'left',
		}, {
			field: 'sourceType',
			title: '资源类型',
			width: 80,
			align: 'center',
		}, {
			field: 'sourceTitle',
			title: '资源标题',
			width: 260,
			align: 'center',
		}, {
			field: 'createTime',
			title: '创建时间',
			width: 132,
			align: 'center'
		}, {
			field: 'updateTime',
			title: '更新时间',
			width: 132,
			align: 'center'
		}, {
			field: 'isDel',
			title: '操作',
			width: 100,
			align: 'center',
			formatter: function(data, row, index) {
				if(data == 1) {
					var opHtml_recovery = "<a href=\"javascript:void(0);\" onclick=\"recovery('" + row.id + "'," + index + ")\" class=\"easyui-linkbutton\"  plain=\"true\" style=\"text-decoration:none;font-size:12px;height:100%;width:50%\"><b>恢复</b></a>"
					return opHtml_recovery;
				} else {
					var opHtml = "<a href=\"javascript:void(0);\" onclick=\"edit('" + row.id + "'," +index +
						")\" class=\"easyui-linkbutton\"  plain=\"true\" style=\"text-decoration:none;font-size:12px;height:100%;width:50%\"><b>更新</b></a>" +
						"&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"deleteIndexFrequency('" +
						row.id +
						"'," +
						index +
						")\" class=\"easyui-linkbutton deleteNewsNotic\" plain=\"true\"  style=\"text-decoration:none;font-size:13px;height:100%;width:50%\"><b>删除</b></a>";
					return opHtml;
				}
			},
			hidden: true
		}]
	];
	$(function() {
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({
			visibility: "visible"
		});
		/* 资源列表   添加资源窗口参数配置*/
		$("#addWindow").window({
			width: 390,
			height: 280,
			modal: true
		});
		/* 资源列表   添加资源窗口关闭前执行*/
		 $("#addWindow").window({
		       onBeforeClose:function(){
		    	   $("#chooseType").css("display","block");
		    	   if($("#addVideo").css("display") == "block"){
		    		   $("#addVideo").css("display","none");
		    	   }
		    	   if($("#addArtical").css("display") == "block"){
		    		   $("#addArtical").css("display","none");
		    	   }
		       }
		   });
		/* 资源列表初始化加载 */
		$('#sourceGrid').datagrid({
			fit : true,
			border : false,
			rownumbers : true,
			animate: true,
			nowrap:true,
			striped : true,
			pageList : [ 10, 20, 50, 100 ],
			pagination : true,
			url : "${pageContext.request.contextPath}/webContentManager/getDataList",
			columns : columns,
			loadMsg:'数据加载中...',
			onUncheck : function(rowIndex,rowData){
				judgeAssociated("uncheck");
			},
			onCheck : function(rowIndex,rowData){
				judgeAssociated("check");
			},
			onLoadSuccess: function (data) {
				
			}
		});
	})
	/* 资源列表   判断是否修改关联 */
	function judgeAssociated(checkOrUncheck){
		alert(checkOrUncheck)
	}
	/* 资源列表   打开添加资源窗口*/
	function addData(){
		$("#addWindow").window("open");
	}
	/* 资源列表   搜索*/
	function doSearch(){
		alert("search")
	}
	/* 资源列表   保存关联*/
	function doAssociated(){
		alert("saveAssociated")
	}
	/* 资源列表   改变按钮样式 */
	function changeToCommon(obj){
		$(obj).removeClass('chooseNode').addClass('commonNode');
	}
	/* 资源列表   改变按钮样式 */
	function changeToChoosed(obj){
		$(obj).removeClass('commonNode').addClass('chooseNode');
	}
	/* 资源列表   添加资源*/
	function addSource(param){
		$("#chooseType").css("display","none");
		if(param == "video"){
			$("#addVideo").css("display","block");
		}else if(param == "artical"){
			$("#addArtical").css("display","block");
		}
	}
</script>
<style type="text/css">
.chooseNode{
	cursor:pointer;
	background-color:#7F99BE;
	border:1px solid #D3D3D3;
	padding:6px 10px;
	color:white;
	font-weight:bold;
	margin-right:30px;
}
.commonNode{
	cursor:pointer;
	background-color:#F0F0F0;
	border:1px solid #D3D3D3;
	padding:6px 10px;
	margin-right:30px;
}
</style>
</head>
<body class="easyui-layout" style="visibility:hidden;">


	<!-- 左侧前台页面功能显示区域   start -->
    <div data-options="region:'west',title:'前台页面功能',split:true" style="width:200px;">232</div>
    <!-- 左侧前台页面功能显示区域   end -->
    
   	<!-- 右侧资源区域，包含两个部分(与前台页面关联部分，资源列表)   start -->
    <div class="easyui-layout" data-options="region:'center',title:'资源管理'" style="padding:5px;background:#eee;">
    	<div data-options="region:'north',split:true" style="height:50px;">
    		<div style="margin:5px 0px 5px 30px">
    			<br/>
    			<span id="associatedButton" class="commonNode">已关联</span>
    			<span id="sourceListButton" class="chooseNode">资源列表</span>
				<span class="commonNode" onclick="addData();" style="float:right;margin-right:20px;margin-top:-7px">添加资源</span>
			</div>
    	</div>
	    <div data-options="region:'center'" style="padding:5px;background:#eee;">
	    	<!-- 资源列表  start -->
			<div id="sourceList">
				<span style="font-size:12px;font-weight:bold;">当前节点:&nbsp;<b id="path"></b></span>
				<br><br>
				类型:<select name="type" id="type">
						<option selected  value="">请选择</option>
						<option value="产品">产品</option>
						<option value="视频">视频</option>
						<option value="文档">文档</option>
					</select>&nbsp;
				标题关键字:<input type="text" name="titleKey" style="width:80px;"/>
				排序:<select name="order" id="order">
						<option selected  value="">请选择</option>
						<option value="1">更新时间由早到晚</option>
						<option value="2">更新时间由晚到早</option>
					</select>&nbsp;&nbsp;&nbsp;
				<span  onclick="doSearch()" class="commonNode">搜索</span>
				<span onclick="doAssociated();" class="commonNode" style="float:right;">保存关联</span>
				<br/><br/>
				<table id="sourceGrid"></table>
			</div>
			<!-- 资源列表  end -->
			
			<!-- 关联列表  start -->
			<div id="associatedList" style="display:none;">
			</div>
			<!-- 关联列表  end -->
	    </div>
    </div>
    <!-- 右侧资源区域，包含两个部分(与前台页面关联部分，资源列表)   end -->
    
    <!-- 资源添加窗口   start -->
    <div class="easyui-window" title="添加资源" id="addWindow"  closed="true" draggable="false" style="top: 100px; left: 390px">
    	<div id="chooseType" style="margin-top:100px;margin-left:110px">
	    	<span class="commonNode" onmouseover="changeToChoosed(this);" onmouseout="changeToCommon(this);" onclick="addSource('video');">添加视频</span>
	    	<span class="commonNode" onmouseover="changeToChoosed(this);" onmouseout="changeToCommon(this);" onclick="addSource('artical');">添加文档</span>
    	</div>
    	<div id="addVideo" style="display:none">添加视频</div>
    	<div id="addArtical" style="display:none">添加文档</div>
	</div>
	<!-- 资源添加窗口   end -->
</body>

</html>