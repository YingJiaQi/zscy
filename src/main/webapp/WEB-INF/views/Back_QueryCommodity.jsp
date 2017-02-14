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
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/static/js/editor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/static/js/editor/ueditor.all.min.js"> </script>
<!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
<!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/static/js/editor/lang/zh-cn/zh-cn.js"></script>
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
		$("#updateWindow").window({
			width: 900,
			height: 500,
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
		//更新商品  点击保存 提交表单!
		$("#saveCommodity").click(function() {
			
			if ($("#updateCommodityForm").form("validate")) {
				var data = $("#updateCommodityForm").serialize();
				var category = $('#category').combobox('getText');
				var aa = UE.getEditor('editor').getContent();
				var imageUrls = UE.getEditor('imageEditor').getContent();
				aa = aa.replace(/=/g, "|f|");
				imageUrls = imageUrls.replace(/=/g, "|f|");
				var arr = data.split("&");
				var datas = '';
				for(var i=0;i<arr.length;i++){
						datas += arr[i]+"&";
				} 
				datas = datas + "commodityDesc="+aa+"&"+"category_id="+category+"&"+"imageUrls="+imageUrls;
				//alert(datas)
				$.ajax({
					type : 'post',
					url : "${pageContext.request.contextPath }/Commodity/addCommodity",
					data : JSON.stringify(conveterParamsToJson(datas)),
					dataType : 'json',
					contentType : "application/json; charset=utf-8",
					success : function(data) {
						if (data.flag) {
							$("#updateCommodityForm")[0].reset();
							editor.execCommand("cleardoc");
							image.execCommand("cleardoc");
							$('#updateWindow').window("close");
							$.messager.alert('更新成功',data.msg,"info");
						} else {
							$('#updateWindow').window("close");
							$.messager.alert('更新失败',data.msg,"error");
						}
					}
				});
				} else {
					return;
				}
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
			url: '${pageContext.request.contextPath}/Commodity/getCommodityWithUpdateById',
			data: JSON.stringify(dataVo),
			dataType: 'json',
			contentType: "application/json; charset=utf-8",
			success: function(data) {
				if(data != null) {
					$('#updateWindow').window("open");
					var data = $("#category").combobox('getData');
				    if (data.length > 0) {
				        	for(var i =0 ;i<data.length;i++){
				        		//alert(data[i].text +"=="+temp)
				            	if(data[i].text=="ALL"){
				        		//alert(data[i].id);
				        		$("#category").combobox('select', data[i].id);
				            	}
				            }
				     }
				} else {
					$.messager.alert('提示', "更新失败");
				}
			}
		});
	}
	//添加类目时，相应添加规格描述
	function addSpecification(rec){
		var dataVo = {id:rec.id};
		$.ajax({
			type : 'post',
			url : "${pageContext.request.contextPath }/category/getAssociatedListById",
			data : JSON.stringify(dataVo),
			dataType : 'json',
			contentType : "application/json; charset=utf-8",
			success : function(data) {
				if (data.success == "true") {
					$("#specificationLocation").css("display","block");
					var optionHtmlh = "<div><hr/>规格参数配置<br/><h3 style='color:green'>"+data.docList[0].categoryName+"</h3></div>";
					$("#specificationLocation").append(optionHtmlh);
					for(var i = 0; i< data.docList.length ;i++){
						var optionHtml = "<div style='height:30px; overflow:auto;' title='"+data.docList[0].categoryName+"'>"+data.docList[i].cateforySpecificationName+"<input type='text' name='"+data.docList[0].categoryName+"_"+data.docList[i].cateforySpecificationName+"'/></div>"
						$("#specificationLocation").append(optionHtml);
					}
				} else {
					$.messager.alert('失败',"类目参数获取失败");
				}
			}
		});
	}
	//减少类目时，相应去除规格描述
	function delSpecification(re){
		//alert($("h3:contains('"+re.text+"')").html());
		$("h3:contains('"+re.text+"')").parent().remove();
		$("div[title='"+re.text+"']").remove();
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
	<div class="easyui-window" title="更新" id="updateWindow" collapsible="false" minimizable="false" maximizable="true" resizable="false" closed="true" style="top: 20px; left: 200px">
		<div region="north" style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="saveCommodity" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" style="color: green; font-size: 15px">&nbsp;<b>提交</b></a>
			</div>
		</div>
		<div region="center" style="overflow: auto; padding: 10px;" border="false">
			<form id="updateCommodityForm" action="#">
				<table class="table-edit" width="96%" align="center">
					<tr>
						<th style="width:100px;">商品类目:</th>
						<th>
							<input class="easyui-combobox" id="category"  multiple="true" data-options="valueField:'id',textField:'text',url:'${pageContext.request.contextPath }/category/getCategoryListWithCommodity',editable:false,onSelect: function(rec){addSpecification(rec);},onUnselect: function(re){delSpecification(re);}" />
	            			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            			<input type="checkbox" name="hotCommodity" value="1"/>热门商品
	            		</th>
					</tr>
					<tr>
						<th style="height: 40px">商品标题:</th>
						<th><input type="text" name="title" class="easyui-validatebox" data-options="required:true,validType:'multiple[\'length[3,100]\',\'RegeMatch\']'"  invalidMessage="字符最少3个，最多100个或存在非法字符" /></th>
					</tr>
					<tr>
						<th style="height: 40px">商品卖点:</th>
						<th><input type="text" name="sellPoint" class="easyui-validatebox" data-options="required:true,validType:'multiple[\'length[3,100]\',\'RegeMatch\']'"  invalidMessage="字符最少3个，最多100个或存在非法字符"/></th>
					</tr>
					<tr>
						<th style="height: 40px">商品价格:</th>
						<th><input type="text" name="priceView" class="easyui-numberbox" data-options="min:1,max:99999999,precision:2,required:true" /></th>
					</tr>
					<tr>
						<th style="height: 40px">库存数量:</th>
						<th><input type="text"  class="easyui-numberbox"  name="num" data-options="min:1,max:99999999,precision:0,required:true"/></th>
					</tr>
					<tr>
						<th style="height: 40px">条形码:</th>
						<th><input type="text" name="barcode" class="easyui-validatebox" data-options="validType:'length[1,30]'"  invalidMessage="1-30个字符"/></th>
					</tr>
					<tr>
						<th style="height: 40px">商品图片:</th>
						<th> 
							<!-- <a href="javascript:void(0)" onclick="upImage();">上传图片</a>
	                 		<input type="hidden" name="image"/> -->
	                 		<div style="width:800px">
								<script  type="text/plain" name="image" id="imageEditor"></script>
							</div>
	                 	</th>
					</tr>
					<tr>
						<th style="height: 40px">商品描述:</th>
						<td>
							<div style="width:800px">
								<script  type="text/plain" name="commodityDesc" id="editor"></script>
							</div>
	            		</td>
					</tr>
				</table>
				<div id="specificationLocation" style="display:none;margin-left:50px"></div>
			</form>
		</div>
	</div>
	<!-- 商品更新窗口   end -->
	<script type="text/javascript">
		var editor =new UE.ui.Editor({
			 //默认的编辑区域高度  
		    initialFrameHeight:500  
		});
		editor.render("editor");
		//var image =new UE.ui.Editor();
		var image= new UE.ui.Editor({  
		    //这里可以选择自己需要的工具按钮名称,此处仅选择如下五个  
		    toolbars:[['insertimage', 'Undo', 'Redo']],  
		    //focus时自动清空初始化时的内容  
		    autoClearinitialContent:false,  
		    //关闭字数统计  
		    wordCount:false, 
		    //不可以编辑
		   // readonly:true,
		    //关闭elementPath
		    elementPathEnabled:false,
		    //禁用浮动
		    autoFloatEnabled: false,
		    //默认的编辑区域高度  
		    initialFrameHeight:260  
		    //更多其他参数，请参考ueditor.config.js中的配置项  
		});  
		image.render("imageEditor");
		function isFocus(){    
		    if(image.isFocus()){
		    	image.blur();
		    }
		}
	</script>
</body>
</html>