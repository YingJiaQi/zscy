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
<div style='background:white;width:100%;height:100%;z-index:999;position:fixed;top:0;left:0;overflow:auto;'>
	<p><button class='btn' style='float:right;margin:10px 30px 20px 10px' onclick='closedWindowPanel(this)'>关闭</button></p>
	<br/><br/><br/><br/><br/>
	<div class="row">
		<div class="col-md-2"></div>
		<div class="col-md-8">
			<div class="row" style="border:1px solid red;height:300px">
				<div class="col-md-6" style="border:1px solid black"></div>
				<div class="col-md-6" style="border:1px solid black"></div>
			</div>
			<div class="row" style="border:1px solid black;height:500px">
				<div class="col-md-4" style="border:1px solid red"></div>
				<div class="col-md-8" style="border:1px solid red"></div>
			</div>
		</div>
		<div class="col-md-2"></div>
	</div>
</div>