<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<!-- 登录 注册 购物车... -->
<div class="container-fluid">
	<div class="col-md-4">
		<img src="img/logo2.png" />
	</div>
	<div class="col-md-5">
		<img src="img/header.png" />
	</div>
	<div class="col-md-3" style="padding-top: 20px">
		<ol class="list-inline">
			<c:if test="${user==null }">
				<li><a href="login.jsp">登录</a></li>
				<li><a href="register.jsp">注册</a></li>
			</c:if>
			<c:if test="${user!=null }">
				欢迎您:<li><a href="login.jsp">${user.username}</a></li>
				<li><a href="register.jsp">退出</a></li>
			</c:if>
			<li><a href="cart.jsp">购物车</a></li>
			<li><a href="order_list.jsp">我的订单</a></li>
		</ol>
	</div>
</div>

<!-- 导航条 -->
<div class="container-fluid">
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<!-- Brand and toggle get grouped for better mobile display -->
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#bs-example-navbar-collapse-1"
					aria-expanded="false">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">首页</a>
			</div>

			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1">
				<ul class="nav navbar-nav">
					<li class="active"><a href="product_list.htm">手机数码<span
							class="sr-only">(current)</span></a></li>
					<li><a href="#">电脑办公</a></li>
					<li><a href="#">电脑办公</a></li>
					<li><a href="#">电脑办公</a></li>
				</ul>
				<form class="navbar-form navbar-right" role="search" action="${pageContext.request.contextPath}/searchProductInfoByPnameServlet" method="post">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="Search" id="aja" name="pname">
					</div>
					<div id="showDiv"style="width: 170px;background: #fff;display: none;position:absolute;border:1px solid #ccc;z-index:1000;">
					
					</div>
					<button type="submit" class="btn btn-default">Submit</button>
				</form>
			</div>
			<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.3.min.js"></script>
			<script type="text/javascript">
			function clickFn(obj){
				//点击后选中这个信息，消失showdiv
				var name = $(obj).text();
				$("#aja").prop("value",name);
				$("#showDiv").css("display","none");
			}
			function overFn(obj){
				$(obj).css("background","#DBEAF9");
			}
			function outFn(obj){
				$(obj).css("background","#fff");
			}
				$(function(){
					//alert("xx");
					/* $("#aja").blur(function(){
						$("#showDiv").css("display","none");
					}); */
					$("#aja").keyup(function(){
						//alert("x");
						//$("#showDiv").css("display","block");
						var pname = $(this).prop("value");
						//alert(pname);
						 $.ajax({
								url:"${pageContext.request.contextPath}/searchProductNameServlet",
								type:"get",
								data:{"pname":pname},
								dataType:"json",
								success:function(data){
									var show = "";
									 if(data.length>0){
										for(var i = 0;i<data.length;i++){
											show += "<div style='padding:5px; cursor:pointer' onclick='clickFn(this)' onmouseover='overFn(this)' onmouseout='outFn(this)'>"+data[i]+"</div>";
										}
										$("#showDiv").html(show);
										$("#showDiv").css("display","block");
									} 
								}
								}); 
						
					});
				});
			</script>
		</div>
	</nav>
</div>