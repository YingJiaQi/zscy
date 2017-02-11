<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>zmcy</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/static/js/jquery-1.8.3.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/utils.js"></script>
<!-- 导入easyui类库 -->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/static/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/static/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/static/js/easyui/ext/portal.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/static/css/default.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/static/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/static/js/easyui/ext/jquery.portal.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/static/js/easyui/ext/jquery.cookie.js"></script>
<script
	src="${pageContext.request.contextPath }/static/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/static/js/datajs/WdatePicker.js"></script>
<script type="text/javascript">
	var columns = [ [ {
		field : 'id',
		title : 'ID',
		width : 250,
		align : 'left',
	}, {
		field : 'news_title',
		title : '新闻公告标题',
		width : 160,
		align : 'center',
	}, {
		field : 'news_content',
		title : '新闻公告内容',
		width : 160,
		align : 'center',
	}, {
		field : 'news_type',
		title : '新闻公告类型',
		width : 120,
		align : 'center',
	}, {
		field : 'news_status',
		title : '新闻公告状态',
		width : 120,
		align : 'center',
	}, {
		field : 'publish_date',
		title : '新闻公告发布时间',
		width : 132,
		align : 'center',
	}, {
		field : 'is_deleted',
		title : '是否已删除',
		width : 120,
		align : 'center',
		formatter : function(data, row, index) {
			if (data == '1') {
				return "已删除";
			} else {
				return "未删除";
			}
		}
	}, {
		field : 'create_time',
		title : '创建时间',
		width : 132,
		align : 'center'
	}, {
		field : 'update_time',
		title : '更新时间',
		width : 132,
		align : 'center'
	},{
		field : 'operation',
		title : 'operation',
		width : 68,
		align : 'center',
		formatter : function(data, row, index) {
			var opHtml = "<a href=\"javascript:void(0);\" onclick=\"edit('"
					+ row.id
					+ "',"
					+ index
					+ ")\" class=\"easyui-linkbutton\"  plain=\"true\" style=\"text-decoration:none;font-size:12px;height:100%;width:50%\"><b>U</b></a>"
					+ "&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"deleteIndexFrequency('"
					+ row.id
					+ "',"
					+ index
					+ ")\" class=\"easyui-linkbutton deleteNewsNotic\" plain=\"true\"  style=\"text-decoration:none;font-size:13px;height:100%;width:50%\"><b>D</b></a>";
			return opHtml;
		}
	}] ];
	$(function() {

		$('#grid').datagrid({
			fit : true,
			border : false,
			rownumbers : true,
			animate: true,
			singleSelect:true,
			striped : true,
			pageList : [ 10, 20, 50 ],
			pagination : true,
			url : "${pageContext.request.contextPath}/newsManage/getNewsManageList",
			columns : columns,
			loadMsg:'数据加载中...',
			onDblClickRow : doDblClickRow
		});
		$("#updateWindow").window({
			width:700,
			height:500,
			modal:true
		});
		$("#addWindow").window({
			width:700,
			height:500,
			modal:true
		});
		//   点击保存 提交表单!
		$("#save").click(function() {
				if ($("#updateNewsForm").form("validate")) {
					var data = $("#updateNewsForm").serialize();
					$.ajax({
						type : 'post',
						url : "${pageContext.request.contextPath }/newsManage/updateNews",
						data : JSON.stringify(conveterParamsToJson(data)),
						dataType : 'json',
						contentType : "application/json; charset=utf-8",
						success : function(data) {
							if (data.result) {
								$("#grid").datagrid('reload');
								$.messager.alert('更新成功',data.msg,"info");
							} else {
								$.messager.alert('更新失败',data.msg,"error");
							}
						}
					});
					} else {
						return;
					}
				$('#updateWindow').window("close");

		});
		$("#saveNews").click(function() {
			if ($("#addNewsForm").form("validate")) {
				var data = $("#addNewsForm").serialize();
				$.ajax({
					type : 'post',
					url : "${pageContext.request.contextPath }/newsManage/addNews",
					data : JSON.stringify(conveterParamsToJson(data)),
					dataType : 'json',
					contentType : "application/json; charset=utf-8",
					success : function(data) {
						if (data.result) {
							$("#grid").datagrid('reload');
							$.messager.alert('添加成功',data.msg,"info");
						} else {
							$.messager.alert('添加失败',data.msg,"error");
						}
					}
				});
				} else {
					return;
				}
			$('#addWindow').window("close");

		});
		//自定义扩展方法，动态调整序号列的宽度
		$("#grid").datagrid({
			onLoadSuccess : function () {
			$(this).datagrid("fixRownumber");
			}
		});
	})
	//双击修改内容
	function doDblClickRow(rowIndex, rowData){
		$('#updateWindow').window("open");
		$("#updateNewsForm").form("load", rowData);
	}
	function edit(id, index) {
		var dataVo = {
				id : id
			};
			$.ajax({
						type : 'post',
						url : '${pageContext.request.contextPath}/newsManage/getNewsById',
						data : JSON.stringify(dataVo),
						dataType : 'json',
						contentType : "application/json; charset=utf-8",
						success : function(data) {
							if (data.flag) {
								$('#updateWindow').window("open");
								$("input[name='id']").val(data.result.id);
								$("input[name='news_title']").val(data.result.news_title);
								$("input[name='news_content']").val(data.result.news_content);
								$("input[name='news_type']").val(data.result.news_type);
								$("#news_status").numberbox('setValue', data.result.news_status);
								$("input[name='publish_date']").val(data.result.publish_date);
								$("input[name='is_deleted']").val(data.result.is_deleted);
								$("input[name='create_time']").val(data.result.create_time);
							} else {
								$.messager.alert('提示', data.msg);
							}
						}
					});
	}
	function deleteIndexFrequency(id, index) {
		jQuery.messager.confirm('提示:',	'你确认要删除吗?',	function(event) {
							if (event) {
								var dataVo = {
									id : id
								};
								$.ajax({
											type : 'post',
											url : '${pageContext.request.contextPath}/newsManage/deleteNewsById',
											data : JSON.stringify(dataVo),
											dataType : 'json',
											contentType : "application/json; charset=utf-8",
											success : function(data) {
												if (data.result) {
													$('#grid').datagrid('reload');
													$.messager.alert('提示',data.msg);
												} else {
													$.messager.alert('提示',	data.msg);
												}
											}
										});
							} else {
								return;
							}
						});
	}
	function doSearch() {
		var isDel = null;
		if ($('#yesOrNot').combobox('getText') == '已删除') {
			isDel = 1;
		}
		if ($('#yesOrNot').combobox('getText') == '未删除') {
			isDel = 0;
		}
		if($('#yesOrNot').combobox('getText') == 'All'){
			isDel = null;
		}
		$("#grid").datagrid("load", {
			"startTime" : $("#startDate").val(),
			"endTime" : $("#endDate").val(),
			"id" : $("input[name='Id']").val(),
			"content" : $("input[name='containInfo']").val(),
			"is_deleted" : isDel
		})
	}
	function addData(){
		$('#addWindow').window("open");
		$("#addNewsForm")[0].reset();
	}
	//自定义扩展方法，动态调整序号列的宽度
	$.extend($.fn.datagrid.methods, {  
	    fixRownumber : function (jq) {  
	        return jq.each(function () {  
	            var panel = $(this).datagrid("getPanel");  
	            var clone = $(".datagrid-cell-rownumber", panel).last().clone();  
	            clone.css({  
	                "position" : "absolute",  
	                left : -1000  
	            }).appendTo("body");  
	            var width = clone.width("auto").width();  
	            if (width > 25) {  
	                //多加5个像素,保持一点边距  
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).width(width + 5);  
	                $(this).datagrid("resize");  
	                //一些清理工作  
	                clone.remove();  
	                clone = null;  
	            } else {  
	                //还原成默认状态  
	                $(".datagrid-header-rownumber,.datagrid-cell-rownumber", panel).removeAttr("style");  
	            }  
	        });  
	    }  
	});  
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north'"
		style="padding: 6px; background: #7F99BE; border: false">
		<form id="tb" method="post">
			<b>start:</b> <input editable="false" id="startDate" class="Wdate"
				type="text"
				onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				style="width: 145px; height: 15px" /> <b>end:</b> <input
				editable="false" id="endDate" class="Wdate" type="text"
				onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})"
				style="width: 145px; height: 15px" />
			&nbsp;&nbsp; <span>ID:</span><input name="Id" class="easyui-textbox" style="width: 100px">
			&nbsp; <span>公告包含内容:</span><input name="containInfo" class="easyui-textbox" style="width: 100px"> 
			&nbsp; <span>是否已删除:</span> 
			<select id="yesOrNot" class="easyui-combobox" style="width: 80px;">
				<option value="all">All</option>
				<option value="yes">已删除</option>
				<option value="no" selected>未删除</option>
			</select> 
			<a href="javascript:void(0);" onclick="doSearch()" 	class="easyui-linkbutton" style="margin-left:30px">search</a>&nbsp;&nbsp; 
			<a href="javascript:void(0);" onclick="addData()" class="easyui-linkbutton" style="float:right;margin-right:20px">add</a>
		</form>
	</div>
	<div data-options="region:'center'"
		style="padding: 6px; background: #eee;">
		<table id="grid"></table>
	</div>
		<div class="easyui-window" title="update window" id="updateWindow" collapsible="false" minimizable="false" maximizable="true"
		closed="true" style="top: 20px; left: 200px">
		<div region="north" style="height: 31px; overflow: hidden;"
			split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true" style="color: green; font-size: 15px">&nbsp;<b>submit</b></a>
			</div>
		</div>

		<div region="center" style="overflow: auto; padding: 6px;"
			border="false">
			<form id="updateNewsForm" action="#">
				<table class="table-edit" width="95%" align="center">
					<tr class="title" style="font-size: 15px">
						<th colspan="2"
							style="font-size: 18px; height: 40px; padding-left: 25px;">消息管理更新
							<input type="hidden" name="id" />
							<input type="hidden" name="is_deleted" />
							<input type="hidden" name="create_time" />
						</th>
					</tr>
					<tr></tr>
					<tr>
						<th style="height: 35px">新闻公告标题</th>
						<th><input type="text" name="news_title" class="easyui-validatebox" required="true"/></th>
						<th>新闻公告内容</th>
						<th><input type="text" name="news_content" class="easyui-validatebox" required="true"/></th>
					</tr>
						<tr>
						<th style="height: 35px">新闻类型</th>
						<th><input type="text" name="news_type" class="easyui-validatebox" required="true"/></th>
						<th>新闻状态</th>
						<th><input type="text" name="news_status" id="news_status" class="easyui-numberbox" required='true'/></th>
					</tr>
					<tr>
						<th style="height: 35px">发布日期</th>
						<th><input	editable="false" name="publish_date" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="height: 15px"/> </th>
					</tr>
					</table>
			</form>
		</div>
	</div>
	<div class="easyui-window" title="add window" id="addWindow" collapsible="false" minimizable="false" maximizable="true"
		closed="true" style="top: 20px; left: 200px">
		<div region="north" style="height: 31px; overflow: hidden;"
			split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="saveNews" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true" style="color: green; font-size: 15px">&nbsp;<b>submit</b></a>
			</div>
		</div>

		<div region="center" style="overflow: auto; padding: 6px;"
			border="false">
			<form id="addNewsForm" action="#">
				<table class="table-edit" width="95%" align="center">
					<tr class="title" style="font-size: 15px">
						<th colspan="2"
							style="font-size: 18px; height: 40px; padding-left: 25px;">消息添加
							<input type="hidden" name="id" id="Id" />
						</th>
					</tr>
					<tr></tr>
					<tr>
						<th style="height: 35px">新闻公告标题</th>
						<th><input type="text" name="news_title" class="easyui-validatebox" required="true"/></th>
						<th>新闻公告内容</th>
						<th><input type="text" name="news_content" class="easyui-validatebox" required="true"/></th>
					</tr>
						<tr>
						<th style="height: 35px">新闻类型</th>
						<th><input type="text" name="news_type" class="easyui-validatebox" required="true"/></th>
						<th>新闻状态</th>
						<th><input type="text" name="news_status" class="easyui-numberbox" required='true'/></th>
					</tr>
					<tr>
						<th style="height: 35px">发布日期</th>
						<th><input	editable="false" name="publish_date" class="Wdate" type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="height: 15px"/> </th>
					</tr>
					</table>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		$.extend($fn.validatebox.defaults.rules,{
			minLength:{
				validator:function(value, param){
					return value.length >= param[0];
				},
				message:'请至少输入(2)个字符.'
			},
			equals:{
				validator:function(value, param){
					return value.length == param[0];
				},
				message:'必须填写(2)个字符.'
			}
		})
	</script>
</body>

</html>