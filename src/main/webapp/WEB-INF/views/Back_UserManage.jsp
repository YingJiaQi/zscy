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
	src="${pageContext.request.contextPath }/static/js/jquery-1.11.3.js"></script>
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
	var columns = [ [{
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
			},	{
				field : 'id',
				title : 'ID',
				width : 250,
				align : 'left',
			},
			{
				field : 'userCode',
				title : '用户编号',
				width : 100,
				align : 'center',
			},
			{
				field : 'loginTimes',
				title : '登录次数',
				width : 100,
				align : 'center',
			},
			{
				field : 'userPassword',
				title : '密码',
				width : 132,
				align : 'center',
			},
			{
				field : 'userName',
				title : '用户名',
				width : 132,
				align : 'center',
			},
			{
				field : 'userGender',
				title : '性别',
				width : 50,
				align : 'center',
				formatter : function(data, row, index) {
					if (data == '1') {
						return "男";
					} else {
						return "女";
					}
				}
			},
			{
				field : 'userPhone',
				title : '手机号',
				width : 160,
				align : 'center'
			},
			{
				field : 'userEmail',
				title : '邮箱',
				width : 100,
				align : 'center'
			},
			{
				field : 'birthday',
				title : '生日',
				width : 180,
				align : 'center'
			},
			{
				field : 'lastLoginTime',
				title : '最后登录的时间',
				width : 200,
				align : 'center',
		
			},{
				field : 'isDeleted',
				title : '是否已删除',
				width : '50',
				align : 'center'
				
			},
			{
				field : 'createTime',
				title : '创建时间',
				width : 132,
				align : 'center'
			},
			{
				field : 'updateTime',
				title : '更新时间',
				width : 132,
				align : 'center'
			} ] ];
	$(function() {
		$('#grid').datagrid({
			fit : true,
			border : false,
			rownumbers : true,
			animate : true,
			singleSelect : true,
			striped : true,
			pageList : [ 10, 20, 50 ],
			pagination : true,
			url : "${pageContext.request.contextPath}/user/getUserList",
			columns : columns,
			loadMsg : '数据加载中...',
			frozenColumns : [ [ {
				field : 'id',
				title : 'ID',
				width : 250,
				align : 'left',
			} ] ],
			onDblClickRow : doDblClickRow
		});
		$("#updateWindow").window({
			width : 680,
			height : 500,
			modal : true
		});
		$("#addWindow").window({
			width : 700,
			height : 500,
			modal : true
		});
	//   点击保存 提交表单!
	      $("#save").click(function(){
	    	    	  if ($("#updateRecordForm").form("validate")) {
	    					var data = $("#updateRecordForm").serialize();
	    					$.ajax({
	    						type : 'post',
	    						url : "${pageContext.request.contextPath }/visit/updateVisitRecord",
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
	      $("#saveVR").click(function() {
					if ($("#addRecordForm").form("validate")) {
						var data = $("#addRecordForm").serialize();
						$.ajax({
							type : 'post',
							url : "${pageContext.request.contextPath }/visit/addVisitRecord",
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

	})
	function doSearch() {
		var isDel = 0;
		if ($('#yesOrNot').combobox('getText') == '已删除') {
			isDel = 1;
		}
		if ($('#yesOrNot').combobox('getText') == '未删除') {
			isDel = 0;
		}
		if($('#yesOrNot').combobox('getText') == 'All'){
			isDel = 10;
		}
		$("#grid").datagrid("load", {
			"startTime" : $("#startDate").val(),
			"endTime" : $("#endDate").val(),
			"id" : $("input[name='id']").val(),
			"key" : $("input[name='userCode']").val(),
			"key2" : $("input[name='loginTimes']").val(),
			"key3" : $("input[name='userName']").val(),
			"isDeleted" : isDel
		});
	}
	function edit(id, index) {
		var dataVo = {
				id : id
			};
			$.ajax({
						type : 'post',
						url : '${pageContext.request.contextPath}/user/getUserById',
						data : JSON.stringify(dataVo),
						dataType : 'json',
						contentType : "application/json; charset=utf-8",
						success : function(data) {
							if (data.flag) {
								$('#updateWindow').window("open");
								$("input[name='id']").val(data.result.id);
								$("input[name='userCode']").val(data.result.userCode);
								$("input[name='userPassword']").val(data.result.userPassword);
								$("input[name='birthday']").val(data.result.birthday);
								$("input[name='lastLoginTime']").val(data.result.lastLoginTime);
								$("input[name='userPassword']").val(data.result.userPassword);
								$("input[name='userName']").val(data.result.userName);
								$("#loginTimes").numberbox('setValue', data.result.loginTimes);
								$("input[name='userEmail']").val(data.result.userEmail);
								$("#userPhone").numberbox('setValue', data.result.userPhone);
								$("input[name='isDeleted']").val(data.result.isDeleted);
								$("input[name='createTime']").val(data.result.createTime);
								
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
											url : '${pageContext.request.contextPath}/visit/deleteVisitRecordById',
											data : JSON.stringify(dataVo),
											dataType : 'json',
											contentType : "application/json; charset=utf-8",
											success : function(data) {
												if (data.payload) {
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
	//双击修改内容
	function doDblClickRow(rowIndex, rowData){
		$('#updateWindow').window("open");
		$("#updateUserForm").form("load", rowData);
	}
	function addData(){
		$('#addWindow').window("open");
		$("#addUserForm")[0].reset();
	}
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
				name="id" class="easyui-textbox" style="width: 100px">
			&nbsp; <span>用户编号:</span><input name="userCode"
				class="easyui-textbox" style="width: 100px"> &nbsp; <span>登录次数(>=):</span><input
				name="loginTimes" class="easyui-textbox" style="width: 70px">
			&nbsp; <span>用户名:</span><input name="userName" class="easyui-textbox"
				style="width: 100px"><br/><div style="height:8px"></div> <span style="margin-left:60px">是否已删除:</span> <select id="yesOrNot"
				class="easyui-combobox" style="width: 80px;">
				<option value="all">All</option>
				<option value="yes">已删除</option>
				<option value="no" selected>未删除</option>
			</select>&nbsp;&nbsp; <a href="javascript:void(0);" onclick="doSearch()"
				class="easyui-linkbutton" style="margin-left: 30px">search</a>&nbsp;&nbsp;
			<a href="javascript:void(0);" onclick="export()"
				class="easyui-linkbutton" style="margin-left:80px;margin-right:10px">导出</a>
				<a href="javascript:void(0);" onclick="import()"
				class="easyui-linkbutton">导入</a><a href="javascript:void(0);" onclick="addData()"
				class="easyui-linkbutton" style="float:right;margin-right:20px">add</a>
		</form>
	</div>
	<div data-options="region:'center'"
		style="padding: 6px; background: #eee;">
		<table id="grid"></table>
	</div>
	<!-- 修改窗口 -->
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
			<form id="updateUserForm"
				action="#">
				<table class="table-edit" width="95%" align="center">
					<tr class="title" style="font-size: 15px">
						<th colspan="2"
							style="font-size: 18px; height: 40px; padding-left: 25px;">用户信息
							<input type="hidden" name="id" id="Id" />
							<input type="hidden" name="createTime"/>
							<input type="hidden" name="isDeleted"/>
						</th>
					</tr>
					<tr></tr>
					<tr>
						<th style="height: 35px">用户编号</th>
						<th><input type="text" name="userCode" class="easyui-validatebox" required="true" validType="equals[32]" invalidMessage="必填32个字符"/></th>
						<th>登录次数</th>
						<th><input id="loginTimes" type="text" name="loginTimes" class="easyui-validatebox" required='true' validType="equals[32]" invalidMessage="必填32个字符"/></th>
					</tr>
						<tr>
						<th style="height: 35px">用户生日 </th>
						<th><input name="birthday" class="Wdate"
							type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="height: 15px" /></th>
						<th>用户最后登录时间</th>
						<th><input name="lastLoginTime" class="Wdate"
							type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="height: 15px" /></th>
					</tr>
						<tr>
						<th style="height: 35px">用户密码 </th>
						<th><input type="text" name="userPassword" class="easyui-validatebox" required="true"/></th>
						<th>用户姓名</th>
						<th><input type="text" name="userName" class="easyui-validatebox" required="true"/></th>
					</tr>
						<tr>
						<th style="height: 35px">用户邮箱 </th> 
						<th><input type="text" name="userEmail" class="easyui-textbox" required="true" validType='email'  missingMessage="邮箱不能为空" invalidMessage="请输入正确的邮箱"/></th>
						<th>用户手机号</th>
						<th><input id="userPhone" type="text" name="userPhone" class="easyui-textbox" required="true" validType="phoneNum" invalidMessage="必填11个数字"/></th>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<!-- 添加窗口 -->
	<div class="easyui-window" title="add window" id="addWindow" collapsible="false" minimizable="false" maximizable="true"
		closed="true" style="top: 20px; left: 200px">
		<div region="north" style="height: 31px; overflow: hidden;"
			split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="saveVR" icon="icon-save" href="#" class="easyui-linkbutton"
					plain="true" style="color: green; font-size: 15px">&nbsp;<b>submit</b></a>
			</div>
		</div>

		<div region="center" style="overflow: auto; padding: 6px;"
			border="false">
			<form id="addRecordForm"
				action="#">
				<table class="table-edit" width="95%" align="center">
					<tr class="title" style="font-size: 15px">
						<th colspan="2"
							style="font-size: 18px; height: 40px; padding-left: 25px;">拜访记录信息
						</th>
					</tr>
					<tr></tr>
					<tr>
						<th style="height: 35px">计划ID</th>
						<th><input type="text" name="plan_id" class="easyui-validatebox" required="true" validType="equals[32]" invalidMessage="必填32个字符"/></th>
						<th>客户ID</th>
						<th><input type="text" name="customer_id" class="easyui-validatebox" required='true' validType="equals[32]" invalidMessage="必填32个字符"/></th>
					</tr>
						<tr>
						<th style="height: 35px">开始拜访时间 </th>
						<th><input name="start_visit_time" class="Wdate"
							type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="height: 15px" /></th>
						<th>完成拜访时间 </th>
						<th><input name="complete_visit_time" class="Wdate"
							type="text" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="height: 15px" /></th>
					</tr>
						<tr>
						<th style="height: 35px">拜访反馈</th>
						<th><input type="text" name="visit_feedback" class="easyui-validatebox" required="true"/></th>
						<th>自我评价</th>
						<th><input type="text" name="self_assessment" class="easyui-validatebox" required="true"/></th>
					</tr>
						<tr>
						<th style="height: 35px">拜访时长 </th>
						<th><input type="text" name="visit_interval" class="easyui-numberbox" required="true"/></th>
						<th>用户ID</th>
						<th><input type="text" name="owner_id" class="easyui-validatebox" required="true" validType="equals[32]" invalidMessage="必填32个字符"/></th>
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
			},
			 phoneNum: { //验证手机号    
                 validator: function(value, param){  
                  return /^1[3-8]+\d{9}$/.test(value);  
                 },     
                 message: '请输入正确的手机号码。'    
             },  
              
             telNum:{ //既验证手机号，又验证座机号  
               validator: function(value, param){  
                   return /(^(0[0-9]{2,3}\-)?([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$)|(^((\d3)|(\d{3}\-))?(1[358]\d{9})$)/.test(value);  
                  },     
                  message: '请输入正确的电话号码。'  
             }    
         });  
	</script>
</body>

</html>