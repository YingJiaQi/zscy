<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib  prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!-- 导入jquery核心类库 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/utils.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css" 	href="${pageContext.request.contextPath }/static/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath }/static/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath }/static/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath }/static/css/default.css">
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"	src="${pageContext.request.contextPath }/static/js/easyui/ext/jquery.cookie.js"></script>
<script	type="text/javascript"	src="${pageContext.request.contextPath }/static/js/easyui/locale/easyui-lang-zh_CN.js"></script>
<title>Insert title here</title>

<script type="text/javascript">
	//当前节点名称
	var nodeName = null;
	//当前节点id
	var nodeId = null;
	//保存当前节点ID，防止重复向后台发送请求
	var notAllowRepeatSubmit = null;
	//保存文档id,防止重复向后台发关请求
	var notAllowRepeatSubmitWithDocid = null;
	//初始加载获取所有当前节点关联的文件ID
	initChooseRowsId = "";
	//是否改变了关联
	flag = false;
	//由于页面启动时它会刷新数据，所选择的关联项确是一次一次刷的
	tab = 0;
	
	var columns = [ [{
		field : 'index',
		checkbox : true,
	}, {
		field : 'id',
		title : 'ID',
		width : 230,
		align : 'center',
	}, {
		field : 'specificationName',
		title : '参数名称',
		width : 150,
		align : 'center',
	}, {
		field : 'createTime',
		title : '创建时间',
		width : 230,
		align : 'center',
		hidden : true
	}, {
		field : 'updateTime',
		title : '更新时间',
		width : 130,
		align : 'center',
	},{
		field : 'operation',
		title : '操作',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			var opHtml = "<a href=\"javascript:void(0);\" onclick=\"edit('"
					+ row.id
					+ "',"
					+ index
					+ ")\" class=\"easyui-linkbutton\"  plain=\"true\" style=\"text-decoration:none;font-size:12px;height:100%;width:50%\"><b>更新</b></a>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"deleteIndexFrequencys('"
					+ row.id
					+ "',"
					+ index
					+ ")\" class=\"easyui-linkbutton deleteNewsNotic\" plain=\"true\"  style=\"text-decoration:none;font-size:13px;height:100%;width:50%\"><b>删除</b></a>";
			return opHtml;
		}
	}] ];
$(function(){
	$("body").css({visibility:"visible"});
	$('.buttonStyle').hover(function(){$(this).removeClass('buttonStyle').addClass('overButtonStyle')},function(){$(this).removeClass('overButtonStyle').addClass('buttonStyle')})
	$('#grid').datagrid({
		fit : true,
		border : false,
		rownumbers : true,
		animate: true,
		nowrap:true,
		striped : true,
		pageSize : 10000,
		pageList : [ 10, 20, 50, 10000 ],
		pagination : true,
		//pagination : false,
		url : "${pageContext.request.contextPath}/CommoditySpecification/getMenuList",
		columns : columns,
		loadMsg:'数据加载中...',
		onUncheck : function(rowIndex,rowData){
			judgeAssociated("uncheck");
		},
		onCheck : function(rowIndex,rowData){
			judgeAssociated("check");
		},
		onLoadSuccess: function (data) {
			//初始化数据
			//是否改变了关联
			flag = false;
			//由于页面启动时它会刷新数据，所选择的关联项确是一次一次刷的
			tab = 0;
			//alert("初始化数据 "+initChooseRowsId);
	            if (data.total == 0) {
	            	$("#centerShow").css("display","none");
	            	$("#showInfo").css("display","block")
	            }
	            else {
	            	//对应相应目录，使之勾选上
	            	$.each(data.rows, function(index, item){
	            		if(item.checked){
	            			$('#grid').datagrid('checkRow', index);
	            		}
	            	});
	            	
	            	$("#showInfo").css("display","none")
	            	$("#centerShow").css("display","block");
	            }
	            $(".datagrid-header-check").html("");
	        },
	});
	//初始化清空搜索框内容
	/* $("#folderType").combobox('select', '');
	$("#order").find("option[text='请选择']").attr("selected",true);
	$("#key").val(""); */
	
});
function flushData(){
	$('#grid').datagrid('reload');
}
//用于被父类调用，判断用户有没有对关联信息作出修改，返回TRUE说明作出修改，
function dataHandle(){
	//重新选择节点后，必须将flag初始化为false
	return flag;
}
function judgeAssociated(f){
	
	if(f == "check"){
		tab++;
	}
	if(f == "uncheck"){
		tab--;
	}
	var n = 0;
	//与初始化关联不匹配的数据，若有值，说明作了关联新选择
	var m = 0;
	//用于记录已经匹配的索引
	var k = "";
	var arrs = initChooseRowsId.split("|");
	
	if(arrs != null && arrs != ""){
		//n为初始化关联文件个数
		n = arrs.length;
	}
	//alert(n +"=="+tab)
	if(n == tab){
		var arr = $('#grid').datagrid('getChecked');
		for(var i=0 ;i<arr.length;i++){
			//若存在
			//alert("initChooseRowsId.indexOf(arr[i].id) >0 " +initChooseRowsId.indexOf(arr[i].id))
			if(initChooseRowsId.indexOf(arr[i].id) >=0 ){
			//	alert(1)
				n--;
			}else{
				flag = true;
			}
		}
		//如果n不等于0，说明用户对初始选择的关联文件作了修改
		if(n != 0 || m !=0){
			//说明做了修改
			flag=true;
		}else{
			flag=false;
		}
		//alert("n=="+n+"m=="+m+"flag=="+flag)
	}else{
		//数量不相等，一定修改了关联
		flag=true;
	}
}
//删除
function deleteIndexFrequencys(id, index){
	var dataVo = {
			id : id,
		};
	$.ajax({
		type : 'post',
		url  : '${pageContext.request.contextPath}/CommoditySpecification/deleteCommoditySpecificationById',
		data : JSON.stringify(dataVo),
		dataType : 'json',
		contentType : "application/json; charset=utf-8",
		success : function (data){
			if(data.success == "true"){
				$('#grid').datagrid('reload');
				$.messager.alert("成功",data.msg);
			}else{
				$.messager.alert("失败",data.msg);
			}
		}
	})
}
//父页面调用,实时跟踪用户选择的节点
function checkChooseNode(map){
	var docId;
	var parentNode;
	if(map != null && map != ""){
		
		nodeName = map['nodeName'];
		nodeId = map['nodeId'];
		//docId = map['docId'];
		//parentNode = map['parentNode'];
	}
	if(notAllowRepeatSubmitWithDocid != docId && docId != null && docId != ""){
	//	alert("带参数向后台发送请求，去掉节点刚刚删除的关联文件notAllowRepeatSubmitWithDocid="+notAllowRepeatSubmitWithDocid+"-------docId="+docId)
		//带参数向后台发送请求,去掉节点刚刚删除的关联文件勾选框
		$('#grid').datagrid({  	
			  queryParams: {  
				  nodeID: nodeId,
			   // docId : docId
			  }  
			});  
	}
	notAllowRepeatSubmitWithDocid = docId;
	notAllowRepeatSubmit = nodeId;
}
//更新
function edit(id ,index){
	parent.window.updateCatagory(id);
}
function docmentListFlash(){
	//搜索框赋初值
	$("#key").val("");
	$("#order").val("请选择");
	$('#grid').datagrid({  	
		  queryParams: {  		
		    nodeID: nodeId
		  }  
		});
	//获取选择的关联文件IDS
	$.ajax({
			type : 'post',
			url : '${pageContext.request.contextPath}/document/getAssociatedFileIds',
			data : JSON.stringify({id:nodeId}),
			dataType : 'json',
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				initChooseRowsId = data.msg;
				//初始化数据
				//是否改变了关联
				flag = false;
				//由于页面启动时它会刷新数据，所选择的关联项确是一次一次刷的
				tab = 0;
				//alert("初始化数据 "+initChooseRowsId);
			}
	});
}
function associatedSubmitDO(){
	var docData = $('#grid').datagrid('getChecked');
	parent.window.associatedSubmitDOs(docData,nodeName,nodeId);
}
</script>
<style type="text/css">
.overButtonStyle {
padding:4px 16px;
	margin-left:30px;
	border-radius:8px;
	font-size:12px;
	cursor: pointer;
	background-color:#7F99BE;
	color:white;
	padding:4px 12px;
	
}
.buttonStyle {
	padding:4px 12px;
	margin-left:30px;
	background-color:#F0F0F0;
	border-radius:8px;
	font-size:12px;
	cursor: pointer;
	color:black
}
body{
	font-size:13px;
}
.datagrid .datagrid-pager {
 	display:none
}
</style>
</head>
<body  class="easyui-layout"  style="visibility:hidden;">
<div style="display:none;color:red;position:absolute;top:50%;left:40%;font-size:2em" id="showInfo"><b>没有查到相关数据</b></div>
<div data-options="region:'north',border:false" id="searchCondition" style="font-size:14px;padding:10px 0 10px 0;">
	<form id="searchForm" method="post">
		标题关键字:<input type="text" name="key" id="key" style="width:80px;"/>
		排序:<select name="order" id="order">
				<option selected  value="">请选择</option>
				<option value="1">更新时间由早到晚</option>
				<option value="2">更新时间由晚到早</option>
				<option value="3">文件由大到小</option>
				<option value="4">文件由小到大</option>
			</select>
			&nbsp;<span  onclick="doSearch()" class="buttonStyle">搜索</span><span onclick="associatedSubmitDO();" class="buttonStyle" style="float:right;cursor:pointer;">保存关联</span>
	</form>
</div>
<div region="center" style="padding: 1px 6px; background: #eee;"  id="centerShow" >
		<table id="grid"></table>
</div>
</body>
</html>