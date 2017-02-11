<%@ page language="java" contentType="text/html; charset=UTF-8"     pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
	<br/><br/>
<script type="text/javascript">
function toDocList(){
	$("#articalList").removeClass("commonNode").addClass("chooseNode");
	$("#asscoiatList").removeClass("chooseNode").addClass("commonNode");
	$("#associatedList").css("display","none");
	$("#documentList").css("display","block");
}
</script>
    <div id="showTip">
    	<br><br><br><br><br><br><br><br><br><br><br><br><br><br>
    		<div style="text-align:center;">
    			<font size="4" color="gray">
    				<b>没有发现任何关联参数，<br>可以从参数列表中添加关联
    					<a onclick="toDocList()"><input type="button" value="添加" /></a>
    				</b>
    			</font>
    		</div>
    </div>
    <div >
    	<ul id="showAssociatedList" style="margin-top:15px"></ul>
    </div>
