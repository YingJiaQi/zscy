<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>eHealthRep</title>
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
	var columns = [ [
			{
				field : 'id',
				title : 'ID',
				width : 250,
				align : 'left',
			},
			{
				field : 'msg_title',
				title : '消息标题',
				width : 160,
				align : 'center',
			},
			{
				field : 'msg_content',
				title : '消息内容',
				width : 160,
				align : 'center',
			},
			{
				field : 'msg_type',
				title : '消息类型',
				width : 120,
				align : 'center',
				formatter : function(data, rows, index) {
					if (data == "1") {
						return "其他";
					} else {
						return "系统消息";
					}
				}
			},
			{
				field : 'msg_owner_id',
				title : '消息所有者ID',
				width : 250,
				align : 'center',
			},
			{
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
			},
			{
				field : 'create_time',
				title : '创建时间',
				width : 132,
				align : 'center'
			},
			{
				field : 'update_time',
				title : '更新时间',
				width : 132,
				align : 'center'
			},
			{
				field : 'operation',
				title : '操作',
				width : 120,
				align : 'center',
				formatter : function(data, row, index) {
					var opHtml = "<a href=\"javascript:void(0);\" onclick=\"edit('"
							+ row.id
							+ "',"
							+ index
							+ ")\" class=\"easyui-linkbutton\"  plain=\"true\" style=\"text-decoration:none;font-size:12px\"><b>&nbsp;U&nbsp;<b></a>"
							+ "&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"deleteIndexFrequency('"
							+ row.id
							+ "',"
							+ index
							+ ")\" class=\"easyui-linkbutton deleteNewsNotic\" plain=\"true\"  style=\"text-decoration:none;font-size:13px\"><b>&nbsp;D&nbsp;</b></a>";
					return opHtml;
				}
			} ] ];
	$(function() {

		$('#grid')
				.datagrid(
						{
							fit : true,
							border : false,
							rownumbers : true,
							animate : true,
							singleSelect : true,
							striped : true,
							pageList : [ 10, 20, 50 ],
							pagination : true,
							url : "${pageContext.request.contextPath}/systemMessage/getSystemMessageList",
							columns : columns,
							loadMsg : '数据加载中...',
							onDblClickRow : doDblClickRow

						});
		$("#updateWindow").window({
			width : 700,
			height : 500,
			modal : true
		});
		$("#addWindow").window({
			width : 700,
			height : 500,
			modal : true
		});
		//   点击保存 提交表单!
		$("#save").click(function() {
				if ($("#updateSysMsgForm").form("validate")) {
					var data = $("#updateSysMsgForm").serialize();
					$.ajax({
						type : 'post',
						url : "${pageContext.request.contextPath }/systemMessage/updateSysMsg",
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
		$("#saveSysMsg").click(function() {
			if ($("#addSysMsgForm").form("validate")) {
				var data = $("#addSysMsgForm").serialize();
				$.ajax({
					type : 'post',
					url : "${pageContext.request.contextPath }/systemMessage/addSysMsg",
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
	function doSearch() {
		var isDel = null;
		var msgType = null;
		if ($('#yesOrNot').combobox('getText') == '已删除') {
			isDel = 1;
		}
		if ($('#yesOrNot').combobox('getText') == '未删除') {
			isDel = 0;
		}
		if($('#yesOrNot').combobox('getText') == 'All'){
			isDel = null;
		}
		if($("#msgTypeId").combobox('getText') == '系统消息'){
			msgType = 0;
		}
		if($("#msgTypeId").combobox('getText') == '其它'){
			msgType = 1;
		}
		if($("#msgTypeId").combobox('getText') == 'All'){
			msgType = null;
		}
		$("#grid").datagrid("load", {
			"startTime" : $("#startDate").val(),
			"endTime" : $("#endDate").val(),
			"id" : $("input[name='Id']").val(),
			"msg_type" : msgType,
			"is_deleted" : isDel
		})
	}
	//双击修改内容
	function doDblClickRow(rowIndex, rowData) {
		$('#updateWindow').window("open");
		$("#updateSysMsgForm").form("load", rowData);
	}
	function edit(id, index) {
		var dataVo = {
				id : id
			};
			$.ajax({
						type : 'post',
						url : '${pageContext.request.contextPath}/systemMessage/getSystemMessageById',
						data : JSON.stringify(dataVo),
						dataType : 'json',
						contentType : "application/json; charset=utf-8",
						success : function(data) {
							if (data.flag) {
								$('#updateWindow').window("open");
								$("input[name='id']").val(data.result.id);
								$("input[name='msg_title']").val(data.result.msg_title);
								$("input[name='msg_content']").val(data.result.msg_content);
								$("#msg_type").numberbox('setValue', data.result.msg_type);
								$("input[name='msg_owner_id']").val(data.result.msg_owner_id);
								$("input[name='create_time']").val(data.result.create_time);
								$("input[name='is_deleted']").val(data.result.is_deleted);
							} else {
								$.messager.alert('提示', data.msg);
							}
						}
					});
	}
	function deleteIndexFrequency(id, index) {
		jQuery.messager
				.confirm(
						'提示:',
						'你确认要删除吗?',
						function(event) {
							if (event) {
								var dataVo = {
									id : id
								};
								$
										.ajax({
											type : 'post',
											url : '${pageContext.request.contextPath}/systemMessage/deleteSysMsgById',
											data : JSON.stringify(dataVo),
											dataType : 'json',
											contentType : "application/json; charset=utf-8",
											success : function(data) {
												if (data.result) {
													$('#grid').datagrid(
															'reload');
													$.messager.alert('提示',
															data.msg);
												} else {
													$.messager.alert('提示',
															data.msg);
												}
											}
										});
							} else {
								return;
							}
						});
	}
	function addData(){
		$('#addWindow').window("open");
		$("#addSysMsgForm")[0].reset();
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
				style="width: 145px; height: 15px" /> &nbsp;&nbsp; <span>ID:</span><input
				name="Id" class="easyui-textbox" style="width: 100px">
			&nbsp; <span>消息类型:</span><select id="msgTypeId"
				class="easyui-combobox" style="width: 80px"> 
					<option value="all" selected>All</option>
					<option value="0">系统消息</option>
					<option value="1">其它</option>
				</select>&nbsp; <span>是否已删除:</span>
			<select id="yesOrNot" class="easyui-combobox" style="width: 80px;">
				<option value="all">All</option>
				<option value="yes">已删除</option>
				<option value="no" selected>未删除</option>
			</select> <a href="javascript:void(0);" onclick="doSearch()"
				class="easyui-linkbutton" style="margin-left: 30px">search</a>&nbsp;&nbsp;
				<a href="javascript:void(0);" onclick="addData()"
				class="easyui-linkbutton" style="float:right;margin-right:20px">add</a>
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
			<form id="updateSysMsgForm"
				action="#">
				<table class="table-edit" width="95%" align="center">
					<tr class="title" style="font-size: 15px">
						<th colspan="2"
							style="font-size: 18px; height: 40px; padding-left: 25px;">更新系统消息
							<input type="hidden" name="id" id="Id" />
							<input type="hidden" name="is_deleted" />
							<input type="hidden" name="create_time" />
						</th>
					</tr>
				<tr></tr>
					<tr>
						<th style="height: 40px">消息标题</th>
						<th><input type="text" name="msg_title" class="easyui-validatebox" required="true" /></th>
						<th>消息内容</th>
						<th><input type="text" name="msg_content" class="easyui-validatebox" required="true" /></th>
					</tr>
					<tr>
						<th style="height: 40px">消息类型</th>
						<th><input type="text" name="msg_type" id="msg_type" class="easyui-numberbox" required="true"  min="0" max="1" missingMessage="必须填写0或1"/></th>
						<th>消息所有者ID</th>
						<th><input type="text" name="msg_owner_id" class="easyui-validatebox" required="true" validType="equals[32]" invalidMessage="必填32个字符"/></th>
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
				<a id="saveSysMsg" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true" style="color: green; font-size: 15px">&nbsp;<b>submit</b></a>
			</div>
		</div>

		<div region="center" style="overflow: auto; padding: 6px;"
			border="false">
			<form id="addSysMsgForm"
				action="#">
				<table class="table-edit" width="95%" align="center">
					<tr class="title" style="font-size: 15px">
						<th colspan="2"
							style="font-size: 18px; height: 40px; padding-left: 25px;">添加系统消息
						</th>
					</tr>
					<tr></tr>
					<tr>
						<th style="height: 40px">消息标题</th>
						<th><input type="text" name="msg_title" class="easyui-validatebox" required="true" /></th>
						<th>消息内容</th>
						<th><input type="text" name="msg_content" class="easyui-validatebox" required="true" /></th>
					</tr>
					<tr>
						<th style="height: 40px">消息类型</th>
						<th><input type="text" name="msg_type" class="easyui-numberbox" required="true"  min="0" max="1" missingMessage="必须填写0或1"/></th>
						<th>消息所有者ID</th>
						<th><input type="text" name="msg_owner_id" class="easyui-validatebox" required="true" validType="equals[32]" invalidMessage="必填32个字符"/></th>
					</tr>
				</table>
			</form>
		</div>
	</div>
		<script type="text/javascript">
		$.extend($.fn.validatebox.defaults.rules,{
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
				message:'长度必须为(2)个字符.'
			}
		})
	</script>
</body>

</html>