<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}

.container .row div {
	/* position:relative;
				 float:left; */
	
}

font {
	color: #666;
	font-size: 22px;
	font-weight: normal;
	padding-right: 17px;
}
</style>
<script type="text/javascript">
	$(function(){
		//验证码的改变
		$("#checkImg").click(function(){
			/* this.src = "${pageContext.request.contextPath}/checkImgServlet?method=getMa&r="+new Date().getTime();
			alert(this.src); */
			$(this).prop("src","${pageContext.request.contextPath}/checkImgServlet?r=1"+new Date().getTime());
		});
		//验证用户名是否符合
		/* $("#username").blur(function(){
			//alert("xx");
			var username = $(this).prop("value");
			//alert(username);
			$.get(
					"${pageContext.request.contextPath}/checknameServlet",
					{"username":username},
					function(data){
						//alert(data.boo);
						if(data.boo=='true'){
							//alert("true");
							//$("#nameInfo").html("<font color:'green'>用户名可用.</font>");
							$("#nameInfoY").empty();
							$("#nameInfoN").empty();
							$("#nameInfoY").text("用户名可用.");
						}else{
							//$("#nameInfo").html("<font color:'red'>用户名不可用!</font>")
							$("#nameInfoY").empty();
							$("#nameInfoN").empty();
							$("#nameInfoN").text("用户名不可用!");
						}
					},
					"json"
			); 
		});*/
	});

</script>
</head>
<body >

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>


	<div class="container"
		style="width: 100%; height: 460px; background: #FF2C4C url('images/loginbg.jpg') no-repeat;">
		<div class="row">
			<div class="col-md-7">
				<!--<img src="./image/login.jpg" width="500" height="330" alt="会员登录" title="会员登录">-->
			</div>

			<div class="col-md-5">
				<div
					style="width: 440px; border: 1px solid #E7E7E7; padding: 20px 0 20px 30px; border-radius: 5px; margin-top: 60px; background: #fff;">
					<font>会员登录</font>USER LOGIN
					<div><span style="color:red">${loginInfo}</span></div>
					<form class="form-horizontal" action="${pageContext.request.contextPath}/loginServlet" method="get">
						<div class="form-group">
							<label for="username" class="col-sm-2 control-label">用户名</label>
							<div class="col-sm-6">
								<input type="text" class="form-control" id="username" name="username"
									placeholder="请输入用户名">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
							<div class="col-sm-6">
								<input type="password" class="form-control" id="inputPassword3" name="password"
									placeholder="请输入密码">
							</div>
						</div>
						<div class="form-group">
							<label for="inputPassword3" class="col-sm-2 control-label">验证码</label>
							<div class="col-sm-3">
								<input type="text" class="form-control" id="inputPassword3" name="checkImg"
									placeholder="请输入验证码">
							</div>
							<div class="col-sm-3">
								<img src="${pageContext.request.contextPath}/checkImgServlet" id="checkImg"/>
							</div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<span style="color: red">${checkImgInfo}</span>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<div class="checkbox">
									<label> <input type="checkbox" value="auto" name="autoLogin"> 自动登录
									</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label> <input
										type="checkbox"> 记住用户名
									</label>
								</div>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<input type="submit" width="100" value="登录" name="submit"
									style="background: url('./images/login.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>