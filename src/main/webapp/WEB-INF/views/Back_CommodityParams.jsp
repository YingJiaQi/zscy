<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
<title>智晟</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/jquery-1.11.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/common.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/json2.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/static/js/utils.js"></script>
<!-- 导入easyui类库 -->
<link id="easyuiTheme" rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/static/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/static/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath }/static/css/default.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/static/js/easyui/jquery.easyui.min.js"></script>
<script
	src="${pageContext.request.contextPath }/static/js/easyui/locale/easyui-lang-zh_CN.js"
	type="text/javascript"></script>
	
  <script type="text/javascript">
//用于存放关联文件的id,在删除关联时使用
	var docIds = new Array();
	//用于记录删除的关联文件id
	var docName = null;
	//阻止重复点击类目
	var categoryid = "";
	  $(function(){
/* 		  document.onkeydown = function() {
				if(event.keyCode == 9) { //如果是其它键，换上相应在ascii 码即可。
					return false; //非常重要
				}
			} */
		  $("body").css({visibility:"visible"});
		  $('#tree').tree({
	          url:'${pageContext.request.contextPath }/category/getCatagoryList',
	          animate:true,
	          checkbox:true,
	          onlyLeafCheck:true,
	          onClick:function(node){
	        	  getSelectedNodeData(node);
	        	//将选择的节点传送到右侧docList中
	        	  if(node.id != 1){
	        		  //不是根节点
		        	  $("#asscoiatList").removeClass("commonNode").addClass("chooseNode");
					  $("#articalList").removeClass("chooseNode").addClass("commonNode");
		        	  $("#associatedList").css("display","block");
		        	  $("#documentList").css("display","none");
		        	  if(categoryid != node.id){
		        		  $("#showAssociatedList").empty();
			        	//用于控制对应关联库的显示
						  var dataVo = {id : node.id};
				 			 $.ajax({
				 					type : 'post',
				 					url : '${pageContext.request.contextPath}/category/getAssociatedListById',
				 					data : JSON.stringify(dataVo),
				 					dataType : 'json',
				 					contentType : "application/json; charset=utf-8",
				 					success : function(data) {
										if (data.success == "true") {
											//$("#showAssociatedList").empty();
											if(data.docList != null && data.docList != ""){
												$("#showTip").css("display","none");
												$("#showAssociatedList").css("display","block");
					   							for(var i = 0; i< data.docList.length ;i++){
					   								docIds[i] = String(data.docList[i].id);
					   								var optionHtml = "<li title='"+data.docList[i].cateforySpecificationName+"' style='width:60px;height:20px;float:left;border:1px solid green;list-style-type:none;margin:5px 10px;padding:6px 2px 2px 30px;overflow:hidden;font-size:10px;line-height:1.3;letter-spacing:2px;position:relative'><div style='height:60px; overflow:auto;'>"+data.docList[i].cateforySpecificationName+"</div><a onclick='deleteAssociated("+i+");' href='javascript:void(0);' style='position:absolute;top:2px;left:2px;text-decoration:none;font-size:1.3em'><b>删</b></a></li>"
													$("#showAssociatedList").append(optionHtml);
					   							}
											}else{
												$("#showTip").css("display","block");
												$("#showAssociatedList").empty();
											}
										} else {
											$.messager.alert('更新失败',data.msg,"error");
										}
									}
				 				});
				 			 //防止重复提交
				 			categoryid = node.id;
		        	  }
		        	}
	          },
	          onDblClick:function(node){
	        	  $(this).tree('beginEdit', node.target);
	          },
	          onAfterEdit:saveNode,
	          onContextMenu: function(e, node){  
	                        e.preventDefault();
	                        if(node.id == 1){
	                        	//root
	                        	 $('#rootNode').menu('show', {  
	 	                            left: e.pageX,  
	 	                            top: e.pageY  
	 	                        });
	                        }else{
		                        $('#parentLeafNode').menu('show', {  
		                            left: e.pageX,  
		                            top: e.pageY  
		                        });
	                        }
	                    }  
	          
	        });
		  $("#updateWindow").window({
				width: 300,
				height: 160,
			});
		  $("#updateWindow").window({
			  onBeforeClose:function(){
		    	  $("#mask").hide();
		       }
			});
		  $("#saveUpdateData").click(function() {
				// var node = $('#tree').tree('getSelected');
				 var data = $("#updateDataForm").serialize();
				 //data = data + "&folderParenId="+node.id;
				 var params = conveterParamsToJson(data);
				if ($("#updateDataForm").form("validate")) {
					$.ajax({
						type : 'post',
						url : "${pageContext.request.contextPath }/CommoditySpecification/updateCommoditySpecificationById",
						data : JSON.stringify(params),
						dataType : 'json',
						contentType : "application/json; charset=utf-8",
						success : function(data) {
							if (data.success == "true") {
								myFrame.window.flushData();
								$.messager.alert('添加成功',data.msg,"info");
							} else {
								$.messager.alert('添加失败',data.msg,"error");
							}
							$('#updateDataForm')[0].reset();
						}
					});
					} else {
						return;
					}
				$("#mask").hide();
				$("#updateWindow").window("close");
			});
	  })
	  function saveNode(node){
    	 if(node.text == "" || node.text == null){
    		 $.messager.alert("警告","节点名不能为空","warn");
    		 $('#tree').tree('reload');
    		 return;
    	 }
    	 if(node.text.length > 16){
    		 $.messager.alert("警告","节点名不能大于16个字符","warn");
    		 $('#tree').tree('reload');
    		 return;
    	 }
    	/*  var parentId = $('#tree').tree('getParent', node.target);
    	 if(parentId == null){
    		 parentIds = 0;
    	 }else{
    		 parentIds = parentId.id;
    	 } */
    	 var dataVo = {
    				text : node.text,
    				id : node.id,
    				parentId : 1,
    				foldType : node.attributes
    			};
    			$.ajax({
    				type : 'post',
    				url : '${pageContext.request.contextPath}/category/modifyNode',
    				data : JSON.stringify(dataVo),
    				dataType : 'json',
    				contentType : "application/json; charset=utf-8",
    				success : function(data) {
    					if (data.flag) {
    						 $('#tree').tree('reload');
     		     			$("#tree").tree('collapse',node.target);
    						/*  $("#path").text(""); 
 							$("#articalList").removeClass("commonNode").addClass("chooseNode");
 							$("#asscoiatList").removeClass("chooseNode").addClass("commonNode");
 							$("#associatedList").css("display","none");
 							$("#documentList").css("display","block");
    						 */
     		     			$.messager.alert("成功",data.msg,"info");
    					} else {
    						$('#tree').tree('expand',node.target);  
    						$.messager.alert('失败', data.msg,"error");
    					}
    				}
    			});
     }
	  function addNode(){
	    	 var node = $('#tree').tree('getSelected');
	    	 $('#tree').tree('append',{
	                parent: (node?node.target:null),
	                data:
	                [
	                    {
	                    	   text:'newNode',
	                           id:2,//用于标记添加，更新时id不为2
	                           attributes:"newNode",
	                           state: 'open',
	                           checked:true
	                    } 
	                ]
	            });
	     }
	  function removeNoded(){
	         var nodes = $('#tree').tree('getChecked');
	         var ids = '';
	         for(var i=0; i<nodes.length; i++){
	                if (ids != '') ids += ',';
	                ids += nodes[i].id;
	                 //$('#tree').tree('remove',nodes[i].target);
	         }
	         if(ids != null && ids != ""){
	        	 
	         var dataVo = {
	 				ids : ids
	 			};
	 			$.ajax({
	 				type : 'post',
	 				url : '${pageContext.request.contextPath}/category/removeNode',
	 				data : JSON.stringify(dataVo),
	 				dataType : 'json',
	 				contentType : "application/json; charset=utf-8",
	 				success : function(data) {
	 					if (data.flag) {
	 						 $('#tree').tree('reload');
	 						$.messager.alert("删除成功",data.msg,"info");
	 						} else {
	 							$.messager.alert('删除失败', data.msg,"error");
	 						}
	 					}
	 				});
	         }else{
	        	 $.messager.alert("警告","请点选叶子结点前的复选框，可以选择多个","warn");
	         }
	     }
	  function updateCatagory(id){
		  var dataVo = {
					id: id
				};
				$.ajax({
					type: 'post',
					url: '${pageContext.request.contextPath}/CommoditySpecification/getCommoditySpecificationById',
					data: JSON.stringify(dataVo),
					dataType: 'json',
					contentType: "application/json; charset=utf-8",
					success: function(data) {
						if(data.success == "true") {
							$("#mask").show();
							$('#updateWindow').window("open");
							
							$("input[name='id']").val(data.result.id);
							$("input[name='specificationName']").val(data.result.specificationName);
							$("input[name='createTime']").val(data.result.createTime);
							$("input[name='isDel']").val(data.result.isDel);
						} else {
							$.messager.alert('提示', data.msg);
						}
					}
				});
	  }
	     //用于子框架调用，获取当前节点的id和名称
	     function getSelectedNodeData(node){
		    var leaf = $('#tree').tree('isLeaf', node.target);
	    	 //已关联页面中删除的关联文件的id
	    	 //var docId = docName;
	    	 var map="";
	    	 if(leaf){
		    	map={
		    			    nodeName:node.text,
		    			    nodeId:node.id,
		    			    //docId:docId,
		    			    //parentNode:''
		    			};
	    	 }else{
	    		 if(node != "undefined" && node != '' && node != null){
		    		 map={
		    				 //文件夹，不带参数刷新
		    				 	 nodeName:'',
			    			    nodeId:'',
			    			    //docId:'',
			    			   // parentNode:node.text
			    			};
	        	 }
	    	 }
		    //父调用子页面文件
		    myFrame.window.checkChooseNode(map);
	     }
	     //子页面调用 ，保存关联操作
	     function associatedSubmitDOs(docData,nodeName,nodeId){
	    	 var arr = new Array();
	    		for(var i =0; i< docData.length;i++){
	    			//必须设置它为局部变量，否则会出现多条重复数据
	    			var map = {};
	    			map["id"]=docData[i].id;
	    			map["title"]=docData[i].specificationName;
	    			arr[i] = map;
	    		}
	    		if(nodeName == null || nodeId == null || nodeName == '' || nodeId == ''){
	    			//alert(nodeName)
	    			$.messager.alert("警告","请先选择叶子节点","error");
	    		}else if(arr.length >=0){
	    			if(nodeId == 2){
	    				$.messager.alert("警告","新添加节点，必须修改名称后才可以关联文件","error");
	    				return;
	    			}
	    			var dataVo = {
	    					nodeName : nodeName,
	    					nodeId : nodeId,
	    					dataList : arr
	    			}
	    			$.ajax({
	    				type : 'post',
	    				url : "${pageContext.request.contextPath }/category/addAssociate",
	    				data : JSON.stringify(dataVo),
	    				dataType : 'json',
	    				contentType : "application/json; charset=utf-8",
	    				success : function(data) {
	    					if (data.success == "true") {
	    						//$.messager.alert('关联成功',data.msg,"info");
	    						addOnclickWithNode();
	    						myFrame.window.flushData();
	    					} else {
	    						$.messager.alert('关联失败',data.msg,"error");
	    						myFrame.window.flushData();
	    					}
	    				}
	    			});
	    		}
	     }
	   //给节点绑定单击事件
	     function addOnclickWithNode(){
	    	 var node = $('#tree').tree('getSelected');
	    	//用于控制对应关联库的显示
			  var dataVo = {id : node.id};
	 			 $.ajax({
	 					type : 'post',
	 					url : '${pageContext.request.contextPath}/category/getAssociatedListById',
	 					data : JSON.stringify(dataVo),
	 					dataType : 'json',
	 					contentType : "application/json; charset=utf-8",
	 					success : function(data) {
							if (data.success == "true") {
								 $("#showAssociatedList").empty();
								if(data.docList != null && data.docList != ""){
									$("#showTip").css("display","none");
									$("#showAssociatedList").css("display","block");
		   							for(var i = 0; i< data.docList.length ;i++){
		   								docIds[i] = String(data.docList[i].id);
		   								var optionHtml = "<li title='"+data.docList[i].cateforySpecificationName+"' style='width:60px;height:20px;float:left;border:1px solid green;list-style-type:none;margin:5px 10px;padding:6px 2px 2px 30px;overflow:hidden;font-size:10px;line-height:1.3;letter-spacing:2px;position:relative'><div style='height:60px; overflow:auto;'>"+data.docList[i].cateforySpecificationName+"</div><a onclick='deleteAssociated("+i+");' href='javascript:void(0);' style='position:absolute;top:2px;left:2px;text-decoration:none;font-size:1.3em'><b>删</b></a></li>"
										$("#showAssociatedList").append(optionHtml);
		   							}
								}else{
									$("#showTip").css("display","block");
									$("#showAssociatedList").empty();
								}
							} else {
								$.messager.alert('更新失败',data.msg,"error");
							}
						}
	 				});
	 			$("#asscoiatList").removeClass("commonNode").addClass("chooseNode");
				  $("#articalList").removeClass("chooseNode").addClass("commonNode");
	        	  $("#associatedList").css("display","block");
	        	  $("#documentList").css("display","none");
	     }
	//删除关联参数
	 function deleteAssociated(i){
    	
    	 var specificationId = docIds[i];
    	 var node = $('#tree').tree('getSelected');
	     var dataVo = { specificationId : specificationId, categoryId : node.id};
	     $.ajax({
	    	 type:'post',
	    	 url:'${pageContext.request.contextPath}/category/delAssociate',
	    	 data:JSON.stringify(dataVo),
	    	 dataType : 'json',
	    	 contentType : "application/json;charset=utf-8",
	    	 success : function (data){
	    		if (data.success == "true") { 
	    			//docName = docId;
	    			$("#showAssociatedList li").remove("li[title ='"+data.specificationName+"']");
					$.messager.alert("成功",data.msg,'info');
					if ( $("#showAssociatedList li").length == 0 ) { 
						$("#showTip").css("display","block");
						$("#showAssociatedList").empty();
						}else{
							$("#showTip").css("display","none");
						}  
				} else {
					$.messager.alert('失败', data.msg,'error');
				}
	    	 }
	     });
     }
  </script>
  </head>
<body class="easyui-layout"  style="visibility:hidden;" >
    <div data-options="region:'west',title:'目录结构',split:true" style="width:266px;">
		<ul id="tree" style="margin:16px"></ul>
		<div id="parentLeafNode" class="easyui-menu" style="width: 120px;height:50px">
	        <div onclick="removeNoded()" iconcls="icon-remove" style="height:20px">删除节点</div>
       		<!-- <div onclick="updateNode()" iconcls="icon-edit" style="height:20px">修改节点</div> -->
    	</div>
    	<div id="rootNode" class="easyui-menu" style="width: 120px;height:30px">
	        <div onclick="addNode()" iconcls="icon-add" style="height:20px">添加节点</div>
    	</div>
	</div> 
	<div data-options="region:'center',title:'文档列表'" style="padding:2px 16px 2px 60px;">
		<br/><br/> 
		<%@ include file="Back_CommodityAssociatedRight.jsp" %>
	</div> 
	<!-- 添加窗口 -->
<div id="updateWindow" class="easyui-window" title="更新数据" collapsible="false" minimizable="false" maximizable="true" resizable="false" closed="true" style="top: 100px; left: 430px;z-index:9999">
	<div region="center" style="overflow: auto; padding: 6px;margin:20px 0" border="false">
				<form id="updateDataForm" action="#">
					<table class="table-edit" width="80%" align="center">
						<tr>
							<th style="height: 40px">参数名称
								<input type="hidden" name="id" id="Id" />
								<input type="hidden" name="isDel" />
								<input type="hidden" name="createTime" />
							</th>
							<th><input type="text" style="width: 156px;" name="specificationName" class="easyui-validatebox" data-options="required:true,validType:'multiple[\'length[1,100]\',\'RegeMatch\']'" invalidMessage="字符最少1个，最多100个或存在非法字符" /></th>
						</tr>
					</table>
				</form>
			</div>
			<div class="datagrid-toolbar">
				<a id="saveUpdateData" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" style="color: green; font-size: 15px;margin-left:100px">&nbsp;<b>提交</b></a>
			</div>
</div>
	<div id="mask" style="display:none"></div>
  </body>
</html>
