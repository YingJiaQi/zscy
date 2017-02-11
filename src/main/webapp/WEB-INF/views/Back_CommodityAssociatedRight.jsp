<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style type="text/css">
.chooseNode{
	cursor:pointer;
	background-color:#7F99BE;
	border:1px solid #D3D3D3;
	padding:6px 10px;
	color:white;
	font-weight:bold;
	margin-right:30px;
	border-bottom:6px solid #7F99BE;
}
.commonNode{
	cursor:pointer;
	background-color:#F0F0F0;
	border:1px solid #D3D3D3;
	padding:6px 10px;
	margin-right:30px;
}
.chooseNodeSmall{
	cursor:pointer;
	background-color:#7F99BE;
	border:1px solid #D3D3D3;
	padding:4px 7px;
	color:white;
	font-weight:bold;
	font-size:13px;
	margin-left:30px;
}
.uploadType {
	text-decoration:none;
	cursor:pointer;
	background-color:#F0F0F0;
	border:1px solid #D3D3D3;
	padding:3px 5px;
	margin-right:10px;
	float:right;
}
#mask{
	width: 100%;
	height: 200%;
	position: absolute;
	background-color: #000;
	left: 0;
	opacity:0.5;
	top: 0;
	z-index: 2;
}
</style>
<script type="text/javascript">
	$(function(){
		$("#asscoiatList").click(function(){
			var flag = myFrame.window.dataHandle();
			if(!flag){
				var node = $('#tree').tree('getSelected');
				if(node != null && node != ''){
					var leaf = $('#tree').tree('isLeaf', node.target);
					if(leaf){
						$(this).removeClass("commonNode").addClass("chooseNode");
						$("#articalList").removeClass("chooseNode").addClass("commonNode");
						$("#documentList").css("display","none");
						$("#associatedList").css("display","block");
					}else{
						$.messager.alert("警告","请先选择叶子节点","warn");
					}
				}else{
					//alert(2)
					$.messager.alert("警告","请先选择叶子节点","warn");
				}
			}else{
				$.messager.alert("警告","已修改关联文件，请点击保存关联或恢复原关联","warn");
			}
			
			
		});
		$("#articalList").click(function(){
			//调用子页面的方法，刷新doclist
			myFrame.window.docmentListFlash();
			$(this).removeClass("commonNode").addClass("chooseNode");
			$("#asscoiatList").removeClass("chooseNode").addClass("commonNode");
			$("#associatedList").css("display","none");
			$("#documentList").css("display","block");
		});
		$("#addWindow").window({
			width: 300,
			height: 160,
		});
		 $("#addWindow").window({
		       onBeforeClose:function(){ 
		    	   $("#mask").hide();
		       }
		   });
		 $("#saveAddData").click(function() {
			// var node = $('#tree').tree('getSelected');
			 var data = $("#addDataForm").serialize();
			 //data = data + "&folderParenId="+node.id;
			 var data1 = conveterParamsToJson(data);
			if ($("#addDataForm").form("validate")) {
				$.ajax({
					type : 'post',
					url : "${pageContext.request.contextPath }/CommoditySpecification/addCommoditySpecification",
					data : JSON.stringify(data1),
					dataType : 'json',
					contentType : "application/json; charset=utf-8",
					success : function(data) {
						if (data.flag) {
							//调用子页面的方法，刷新doclist
							myFrame.window.flushData()
							$.messager.alert('添加成功',data.msg,"info");
						} else {
							$.messager.alert('添加失败',data.msg,"error");
						}
						$('#addDataForm')[0].reset();
					}
				});
				} else {
					return;
				}
			$("#mask").hide();
			$("#addWindow").window("close");
		});
	});
	function addData(){
		$("#mask").show();
		$("#addWindow").window("open");
	}
	var easyuiPanelOnMove = function(left, top) { /* 防止超出浏览器边界 */
		var parentObj = $(this).panel('panel').parent();
		if(left < 0) {
			$(this).window('move', {
				left: 1
			});
		}
		if(top < 0) {
			$(this).window('move', {
				top: 1
			});
		}
		var width = $(this).panel('options').width;
		var height = $(this).panel('options').height;
		var right = left + width;
		var buttom = top + height;
		var parentWidth = parentObj.width();
		var parentHeight = parentObj.height();
		if(parentObj.css("overflow") == "hidden") {
			if(left > parentWidth - width) {
				$(this).window('move', {
					"left": parentWidth - width

				});
			}
			if(top > parentHeight - height) {
				$(this).window('move', {
					"top": parentHeight - height
				});
			}
		}
	}
	$.fn.panel.defaults.onMove = easyuiPanelOnMove;
	$.fn.window.defaults.onMove = easyuiPanelOnMove;
	$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
</script>
<div id="top" style="margin-bottom:6px"><span id="asscoiatList" class="commonNode">已关联</span><span id="articalList" class="chooseNode">参数列表</span>
	<span class="chooseNodeSmall" onclick="addData();" style="margin-left:80%">添加参数</span>
</div>
<hr style="height:3px;background-color:#3D6A92;width:720px;float:left">
<div id="documentList" style="height:410px;">
	<iframe name="myFrame" frameborder="no" src="${pageContext.request.contextPath}/category/specificationList"  style="width:710px;height: 100%;" ></iframe>
</div>
<div id="associatedList" style="display:none;height:410px">
	<%@ include file="Back_L_AssociatedList.jsp" %>
</div>
<!-- 添加窗口 -->
<div id="addWindow" class="easyui-window" title="添加数据" collapsible="false" minimizable="false" maximizable="true" resizable="false" closed="true" style="top: 130px; left: 430px">
	<div region="center" style="overflow: auto; padding: 6px;margin:20px 0" border="false">
				<form id="addDataForm" action="#">
					<table class="table-edit" width="80%" align="center">
						<tr>
							<th style="height: 40px">参数名称</th>
							<th><input type="text" style="width: 156px;" name="specificationName" class="easyui-validatebox" data-options="required:true,validType:'multiple[\'length[1,100]\',\'RegeMatch\']'" invalidMessage="字符最少1个，最多100个或存在非法字符" /></th>
						</tr>
					</table>
				</form>
			</div>
			<div class="datagrid-toolbar">
				<a id="saveAddData" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" style="color: green; font-size: 15px;margin-left:100px">&nbsp;<b>提交</b></a>
			</div>
</div>
