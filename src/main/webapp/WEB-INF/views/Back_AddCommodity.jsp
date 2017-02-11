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
<script type="text/javascript"
	src="${pageContext.request.contextPath }/static/js/easyui/jquery.easyui.min.js"></script>
<script
	src="${pageContext.request.contextPath }/static/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
	<link id="easyuiTheme" rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/static/js/easyui/themes/default/easyui.css">
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/static/js/editor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/static/js/editor/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath }/static/js/editor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript">
$(function(){
	// 先将body隐藏，再显示，不会出现页面刷新效果
	$("body").css({visibility:"visible"});
	//  点击保存 提交表单!
	$("#saveCommodity").click(function() {
		
		if ($("#addCommodityForm").form("validate")) {
			var data = $("#addCommodityForm").serialize();
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
						$("#addCommodityForm")[0].reset();
						editor.execCommand("cleardoc");
						image.execCommand("cleardoc");
						$.messager.alert('更新成功',data.msg,"info");
					} else {
						$.messager.alert('更新失败',data.msg,"error");
					}
				}
			});
			} else {
				return;
			}
	});
	//setInterval(isFocus, 100);
});
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
</style>
</head>
<body  class="easyui-layout" style="visibility:hidden;" >
		<div region="north" style="height: 31px; overflow: hidden;" split="false" border="false">
			<div class="datagrid-toolbar">
				<a id="saveCommodity" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" style="color: green; font-size: 15px">&nbsp;<b>提交</b></a>
			</div>
		</div>
		<div region="center" style="overflow: auto; padding: 10px;" border="false">
			<form id="addCommodityForm" action="#">
				<table class="table-edit" width="90%" align="center">
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
	                 		<div style="width:900px">
								<script  type="text/plain" name="image" id="imageEditor"></script>
							</div>
	                 	</th>
					</tr>
					<tr>
						<th style="height: 40px">商品描述:</th>
						<td>
							<div style="width:900px">
								<script  type="text/plain" name="commodityDesc" id="editor"></script>
							</div>
	            		</td>
					</tr>
				</table>
				<div id="specificationLocation" style="display:none;margin-left:50px"></div>
			</form>
		</div>
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