<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>智晟</title>
<!-- 导入jquery核心类库 -->
<script type="text/javascript"
	src="${pageContext.request.contextPath }/static/js/jquery-1.8.3.js"></script>
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
	// 初始化ztree菜单
	$(function() {
		
		tabClose();
		tabCloseEven();
		//生成新的左侧手风琴菜单
		createLeftDivChild();	

		$("#btnCancel").click(function() {
			$('#editPwdWindow').window('close');
		});

		$("#btnEp").click(function() {
			//  1:  获取新密码和重复密码  比对是否一致 
			var newPwd = $("#newPassword").val();
			var rePwd = $("#repassword").val();
			//  js 校验  是否一致     123   456       222     2 2 2 2 2
			if (newPwd == "" || newPwd.length<3||newPwd.length>10) {
				$.messager.alert("警告", "密码必须3-10位,必须填写", "warning");
				return;
			}
			//  正则 /^$/表示正则  match(不推荐)  规则test(string)  true/false
			//  true /fasle
			var reg = /\s+/;
			if (reg.test(newPwd)) {
				$.messager.alert("警告", "密码不能出现空格", "warning");
				return;
			}
			if (newPwd != rePwd) {
				$.messager.alert("错误", "两次密码不一致", "error");
				return;
			}
			//  发送ajax  给后台 
			$.post("${pageContext.request.contextPath}/", {
				"password" : newPwd
			}, function(data) {
				if (data.flag) {
					$.messager.alert("恭喜", "修改密码成功", "info");
					//  关闭遮罩窗体 
				} else {
					$.messager.alert("error", "服务器出错了...修改密码失败", "error");
				}
				$('#editPwdWindow').window('close');//  关闭窗体
			});

		});
	});
	/*
	 * 生成左边菜单的样式  (实现动态填充leftdiv)
	 */
	function createLeftDivChild() {
		//1,获取json
		$.ajax({
			url: '../sys/getMenu',
			type: "post",
			success: function(data) {
				//2.解析json 
				//21,首先解析父菜单 //没有page属性的就是父
				for(var i = 0; i < data.length; i++) {
					if(data[i].page == undefined) {
						var parhtml = '<div id="par_' + data[i].id + '" title="' + data[i].text + '"style="padding:5px;width:auto;fillSpace: true">'
						parhtml += '</div>'
						$("#leftdiv").append(parhtml);
					}
				}
				//子菜单解析 ,有page属性  ,并把子添加到父容器中
				for(var i = 0; i < data.length; i++) {
					if(data[i].children != undefined) {
						for(var j = 0; j < data[i].children.length; j++) {
							var pageName = data[i].children[j].text
							var pageUrl = data[i].children[j].attributes;
							//子节点设计s
							//var chtml = '<p style="cursor:pointer" pageName="' + pageName + '" pageUrl="' + pageUrl + '" onclick="openTabs(this)">' + data[i].name + '</p><p><br></p>'
							//padding 上 右 下 左
							var chtml = '<p style="cursor:pointer;height:20px;font-size:12px;padding:5px 0px 0px 5px;" pageName="' + pageName + '" pageUrl="' + pageUrl + '" onclick="openTabs(this)">' + '<img src="../static/image/clipboard.png" style="height:12px">' + pageName + '</p>'
	
							//子节点设计e
							$("#par_" + data[i].id).append(chtml);
						}
					}
				}
				$("#leftdiv").accordion() //启动手风琴样式
				$("#leftdiv").accordion('getSelected').panel('collapse') //默认关闭
			},
			error: function(data) {}
		});
	}
	//菜单点击,打开新的额tab
	function openTabs(ele) {
		var pageName = ele.getAttribute("pageName")
		var pageUrl = ele.getAttribute("pageUrl")
			//点击<p>背景色变色,其他<p>颜色还原为白色
		for(var i = 0; i < $("#leftdiv").find("p").length; i++) {
			//console.log($("#leftdiv").find("p")[i])
			$("#leftdiv").find("p")[i].setAttribute('style', 'cursor:pointer;height:20px;font-size:12px;padding:5px 0px 0px 5px;')
		}
		//被电击的<p>元素变色
		ele.setAttribute('style', 'cursor:pointer;height:20px;font-size:12px;padding:5px 0px 0px 5px;background-color:cornflowerblue')
			// 判断树菜单节点是否含有 page属性
		if($("#tabs").tabs('exists', pageName)) { // 判断tab是否存在
			$('#tabs').tabs('select', pageName); // 切换tab
		} else {
			if(pageName == "自定义表单") {
				window.open(pageUrl, "_blank");
			} else {
				// 开启一个新的tab页面
				var content = '<div style="width:100%;height:100%;overflow:hidden;">' +
					'<iframe src="' +
					pageUrl +
					'" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>';

				$('#tabs').tabs('add', {
					title: pageName,
					content: content,
					closable: true
				});
			}
		}
	}
	function onClick(event, treeId, treeNode, clickFlag) {
		// 判断树菜单节点是否含有 page属性
		if (treeNode.page != undefined && treeNode.page != "") {
			if ($("#tabs").tabs('exists', treeNode.name)) {// 判断tab是否存在
				$('#tabs').tabs('select', treeNode.name); // 切换tab
			} else {
				// 开启一个新的tab页面
				var content = '<div style="width:100%;height:100%;overflow:hidden;">'
						+ '<iframe src="'
						+ treeNode.page
						+ '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>';

				$('#tabs').tabs('add', {
					title : treeNode.name,
					content : content,
					closable : true
				});
			}
		}
	}

	/*******顶部特效 *******/
	/**
	 * 更换EasyUI主题的方法
	 * @param themeName
	 * 主题名称
	 */
	changeTheme = function(themeName) {
		var $easyuiTheme = $('#easyuiTheme');
		var url = $easyuiTheme.attr('href');
		var href = url.substring(0, url.indexOf('themes')) + 'themes/'
				+ themeName + '/easyui.css';
		$easyuiTheme.attr('href', href);
		var $iframe = $('iframe');
		if ($iframe.length > 0) {
			for (var i = 0; i < $iframe.length; i++) {
				var ifr = $iframe[i];
				$(ifr).contents().find('#easyuiTheme').attr('href', href);
			}
		}
	};
	// 退出登录
	function logoutFun() {
		$.messager
				.confirm(
						'系统提示',
						'您确定要退出本次登录吗?',
						function(isConfirm) {
							if (isConfirm) {
								location.href = '${pageContext.request.contextPath }/admin/index';
							}
						});
	}
	// 修改密码
	function editPassword() {
		$('#editPwdWindow').window('open');//  遮罩效果
	}
	function tabClose() {
		/* 双击关闭TAB选项卡 */
		$(".tabs-inner").dblclick(function() {
			var tab = $('#tabs').tabs('getSelected');
			var index = $('#tabs').tabs('getTabIndex',tab);
			$('#tabs').tabs('close',index);
		});
		/* 为选项卡绑定右键 */
		$(".tabs-inner").bind('contextmenu', function(e) {
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});

			var subtitle = $(this).children(".tabs-closable").text();

			$('#mm').data("currtab", subtitle);
			$('#tabs').tabs('select', subtitle);
			return false;
		});
	}
	// 绑定右键菜单事件
	function tabCloseEven() {
		// 刷新
		$('#mm-tabupdate').click(function() {
			var currTab = $('#tabs').tabs('getSelected');
			var url = currTab.panel('options').content;
			$('#tabs').tabs('update', {
				tab : currTab,
				options : {
					content : url
				}
			});
		});
		// 关闭当前
		$('#mm-tabclose').click(function() {
			var tab = $('#tabs').tabs('getSelected');
			var index = $('#tabs').tabs('getTabIndex',tab);
			$('#tabs').tabs('close',index);
		});
		// 全部关闭
		$('#mm-tabcloseall').click(function() {
			$('.tabs-inner span').each(function(i, n) {
				var t = $(n).text();
				$('#tabs').tabs('close', t);
			});
		});
		// 关闭除当前之外的TAB
		$('#mm-tabcloseother').click(function() {
			$('#mm-tabcloseright').click();
			$('#mm-tabcloseleft').click();
		});
		// 关闭当前右侧的TAB
		$('#mm-tabcloseright').click(function() {
			var nextall = $('.tabs-selected').nextAll();
			if (nextall.length == 0) {
				return false;
			}
			nextall.each(function(i, n) {
				var t = $('a:eq(0) span', $(n)).text();
				$('#tabs').tabs('close', t);
			});
			return false;
		});
		// 关闭当前左侧的TAB
		$('#mm-tabcloseleft').click(function() {
			var prevall = $('.tabs-selected').prevAll();
			if (prevall.length == 0) {
				return false;
			}
			prevall.each(function(i, n) {
				var t = $('a:eq(0) span', $(n)).text();
				$('#tabs').tabs('close', t);
			});
			return false;
		});

		// 退出
		$("#mm-exit").click(function() {
			$('#mm').menu('hide');
		});
	}
</script>
<style type="text/css">
</style>
</head>
<body class="easyui-layout">
			<% 
			response.setHeader("Cache-Control", "no-cache"); 
			response.setHeader("Cache-Control", "no-store"); 
			response.setDateHeader("Expires", 0); 
			response.setHeader("Pragma", "no-cache"); 
			%> 
	<div data-options="region:'north',border:false"
		style="height: 50px; padding: 10px; background-color: #7F99BE">
		<div>
			<img src="../static/image/logo.png"
				style="position: absolute; top: 2px" border="0">
		</div>
		<div style="position: absolute; right: 5px; bottom: 10px;">
		<c:if test="${user.userName == 'admin'}">
		[<strong>超级管理员</strong>]，
		</c:if>
		<c:if test="${user.userName != 'admin'}">
		[<strong>普通管理员</strong>]，
		</c:if>
		欢迎[${user.userName }]
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_pfMenu',iconCls:'icon-ok'">更换皮肤</a>
			<a href="javascript:void(0);" class="easyui-menubutton"
				data-options="menu:'#layout_north_kzmbMenu',iconCls:'icon-help'">控制面板</a>
		</div>
		<div id="layout_north_pfMenu" style="width: 120px; display: none;">
			<div onclick="changeTheme('default');">default</div>
			<div onclick="changeTheme('gray');">gray</div>
			<div onclick="changeTheme('black');">black</div>
			<div onclick="changeTheme('bootstrap');">bootstrap</div>
			<div onclick="changeTheme('metro');">metro</div>
		</div>
		<div id="layout_north_kzmbMenu" style="width: 100px; display: none;">
			<div onclick="editPassword();">修改密码</div>
			<div class="menu-sep"></div>
			<div onclick="logoutFun();">退出系统</div>
		</div>
	</div>
	<div data-options="region:'west',split:true,title:'菜单导航'" style="width: 180px">
		<div id="leftdiv" data-options="multiple:true,fit:true" style="width:auto;">
		</div>
	</div>
	<div data-options="region:'center'">
		<div id="tabs" fit="true" class="easyui-tabs tabs-inner" border="false">
			<div title="消息中心" id="subWarp"
				style="width: 100%; height: 100%; overflow: hidden">
				<%--这里显示公告栏、预警信息和代办事宜--%>
				<iframe src="${pageContext.request.contextPath}/toLogin/InfoCentor"
					style="width: 100%; height: 100%; border: 0;"></iframe>
			</div>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		style="height: 33px; padding: 8px; background-color: #D2E0F2;width:100%">
		<table style="width: 100%;">
			<tbody>
				<tr>
					<td style="width:100%">
						<div style="color: #999; font-size: 12pt; text-align: center">
							Copyright©</div>
					</td>
				</tr>
			</tbody>
		</table>
	</div>

	<!--修改密码窗口-->
	<div id="editPwdWindow" class="easyui-window" title="修改密码"
		collapsible="false" minimizable="false" modal="true" closed="true"
		resizable="false" maximizable="false" icon="icon-save"
		style="width: 400px; height: 260px; padding: 15px; background: #fafafa">

		<input id="Password" type="text" value=""
			style="height: 30px; width: 70%; padding-left: 26px; margin-left: 10%; margin-bottom: 8px; color: #c1c1c1; background: url(../static/image/oldpwd.png) no-repeat 12px 3px;"
			onclick="document.getElementById('Password').style.background='white'" />
		<input id="newPassword" type="text" value=""
			style="height: 30px; width: 70%; padding-left: 26px; margin-left: 10%; margin-bottom: 8px; color: #c1c1c1; background: url(../static/image/newpwd.png) no-repeat 12px 3px;"
			onclick="document.getElementById('newPassword').style.background='white'" />
		<input id="repassword" type="text" value=""
			style="height: 30px; width: 70%; padding-left: 26px; margin-left: 10%; margin-bottom: 26px; color: #c1c1c1; background: url(../static/image/repwd.png) no-repeat 12px 3px;"
			onclick="document.getElementById('repassword').style.background='white'" />

		<a id="btnEp" class="easyui-linkbutton" icon="icon-ok"
			href="javascript:void(0)" style="margin-left: 25%">确定</a>
		&nbsp;&nbsp;&nbsp; <a id="btnCancel" class="easyui-linkbutton"
			icon="icon-cancel" href="javascript:void(0)">取消</a>
	</div>
	<div id="mm" class="easyui-menu" style="width: 150px;">
		<div id="mm-tabupdate">刷新</div>
		<div class="menu-sep"></div>
		<div id="mm-tabclose">关闭</div>
		<div id="mm-tabcloseall">全部关闭</div>
		<div id="mm-tabcloseother">除此之外全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-tabcloseright">当前页右侧全部关闭</div>
		<div id="mm-tabcloseleft">当前页左侧全部关闭</div>
		<div class="menu-sep"></div>
		<div id="mm-exit">退出</div>
	</div>
</body>
</html>