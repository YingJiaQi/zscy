<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>eHealthRep</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/utils.js"></script>
<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/js/easyui/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/easyui/jquery.easyui.min.js"></script>
<script src="${pageContext.request.contextPath }/static/js/easyui/locale/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/datajs/WdatePicker.js"></script>
<script type="text/javascript">
//根据ID查找的商品
var commodityData = null;

var columns = [
				[{
					field: 'id',
					title: 'ID',
					width: 150,
					align: 'left',
				}, {
					field: 'title',
					title: '商品标题',
					width: 160,
					align: 'center',
				}, {
					field: 'categoryName',
					title: '所属类目',
					width: 80,
					align: 'center',
				},{
					field: 'sell_point',
					title: '商品卖点',
					width: 260,
					align: 'center',
				},{
					field: 'price',
					title: '商品价格',
					width: 50,
					align: 'center',
				}, {
					field: 'num',
					title: '商品数量',
					width: 50,
					align: 'center',
				},{
					field: 'hot',
					title: '热门商品',
					width: 50,
					align: 'center',
					formatter: function(data, rows, index) {
						if(data == "1") {
							return "<b style='color:red'>是</b>";
						} else {
							return "不是";
						}
					}
				},{
					field: 'status',
					title: '商品状态',
					width: 50,
					align: 'center',
					formatter: function(data, rows, index) {
						if(data == "1") {
							return "<b style='color:green'>已上架</b>";
						} else if(data == "2"){
							return "<b style='color:orange'>待上架</b>";
						}else {
							return "<b style='color:red'>已删除</b>";
						}
					}
				},{
					field: 'barcode',
					title: '商品条形码',
					width: 100,
					align: 'center',
				},{
					field: 'image',
					title: '商品图片地址',
					width: 130,
					align: 'center',
				},{
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
								"&nbsp;&nbsp;<a href=\"javascript:void(0);\" onclick=\"deleteIndexFrequency('" +
								row.id +
								"'," +
								index +
								")\" class=\"easyui-linkbutton deleteNewsNotic\" plain=\"true\"  style=\"text-decoration:none;font-size:13px;height:100%;width:50%\"><b>删除</b></a>";
							return opHtml;
						}
					},
				}]
			];
	$(function(){
		$("body").css("visibility","visible");
		$("#addWindow").window({
			maximizable: true,
			modal: true
		});
		$('#grid').datagrid({
			fit: true,
			border: false,
			rownumbers: true,
			animate: true,
			singleSelect: true,
			striped: true,
			pageList: [10, 20, 50],
			pagination: true,
			url: "${pageContext.request.contextPath }/Commodity/getCommodityList",
			columns: columns,
			loadMsg: '数据加载中...',
			onDblClickRow: doDblClickRow,
			onLoadSuccess: function(data) {
				if(data.total == 0) {
					$("#centerSys").css("display", "none");
					$("#showSys").css("display", "block");
				} else {
					$("#showSys").css("display", "none");
					$("#centerSys").css("display", "block");
				}
			},

		});
	})
	function doDblClickRow(rowIndex, field, value){
		
	}
function doSearch() {
		
		$("#grid").datagrid("load", {
				"startTime" : $("#startDate").val(),
				"endTime" : $("#endDate").val(),
	 		"key" : $("#category").combobox('getText'),
			"key2" : $("input[name='commodityName']").val(),
			"key3" : $("#commodityStatus").combobox('getValue')
		});

	}
	function deleteIndexFrequency(id, index) {
		jQuery.messager
			.confirm(
				'提示:',
				'你确认要删除吗?',
				function(event) {
					if(event) {
						var dataVo = {
							id: id
						};
						$
							.ajax({
								type: 'post',
								url: '${pageContext.request.contextPath}/Commodity/deleteCommodityById',
								data: JSON.stringify(dataVo),
								dataType: 'json',
								contentType: "application/json; charset=utf-8",
								success: function(data) {
									if(data.success == "true") {
										$('#grid').datagrid('reload');
										$.messager.alert('提示', data.msg, "info");
									} else {
										$.messager.alert('提示', data.msg, "error");
									}
								}
							});
					} else {
						return;
					}
				});
	}
	function edit(id, index) {
		var dataVo = {
			id: id
		};
		$.ajax({
			type: 'post',
			url: '${pageContext.request.contextPath}/Commodity/getCommodityById',
			data: JSON.stringify(dataVo),
			dataType: 'json',
			contentType: "application/json; charset=utf-8",
			success: function(data) {
				if(data != null) {
					alert(data.id)
					$('#updateWindow').window("open");
					commodityData = data;
				} else {
					$.messager.alert('提示', "更新失败");
				}
			}
		});
	}
</script>
<style type="text/css">
		.buttonStyle {
				padding: 4px 16px;
				margin-left: 30px;
				background-color: #F0F0F0;
				border-radius: 11px;
				font-size: 13px;
				cursor: pointer;
				color: black
			}
</style>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div style="display:none;color:red;position:absolute;top:50%;left:40%;font-size:2em" id="showSys"><b>没有查到相关数据</b></div>

	<div data-options="region:'north'" style="padding: 6px; background: #7F99BE; border: false">
		<form id="tb" method="post">
			创建时间<input editable="false" id="startDate" class="Wdate" type="text" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'endDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" style="width:126px!important; height: 15px" /> 
			~<input editable="false" id="endDate" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:true})" style="width:126px!important; height: 15px" />
			&nbsp; 
			<span>商品类目:</span>
			<input class="easyui-combobox" id="category"  multiple="true" data-options="valueField:'id',textField:'text',url:'${pageContext.request.contextPath }/category/getCategoryListWithCommodity',editable:false"/>
			&nbsp; 
			<span>商品名称:</span>
			<input id="commodityName" name="commodityName"/>
			&nbsp;
			<span>商品状态:</span>
				<select id="commodityStatus" class="easyui-combobox" style="width: 80px;" data-options="editable:false">
					<option value="1">上架商品</option>
					<option value="2">待上架商品</option>
					<option value="3">已删除</option>
					<option value="all" selected>所有商品</option>
			</select>
			<span onclick="doSearch()" class="buttonStyle">搜索</span>&nbsp;
		</form>
	</div>
	<div data-options="region:'center'" style="padding: 6px; background: #eee;" id="centerSys">
		<table id="grid"></table>
	</div>
	<!-- 商品更新窗口   start -->
<%--   	<div class="easyui-window" title="更新商品" id="updateWindow"  closed="true" draggable="false" style="top: 0; left: 0">
		<jsp:include page="./Back_AddCommodity.jsp">
			<jsp:param name="commodity" value="commodityData"/>
		</jsp:include>
	</div> --%>
	<!-- 商品更新窗口   end -->
</body>
</html>