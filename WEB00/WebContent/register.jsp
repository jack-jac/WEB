<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head></head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员注册</title>
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

font {
	color: #3164af;
	font-size: 18px;
	font-weight: normal;
	padding: 0 10px;
}
</style>
<script type="text/javascript">
	$(function(){
		//动态改变验证码
		$("#checkImg").click(function(){
			$(this).prop("src","${pageContext.request.contextPath}/checkImgServlet?r="+new Date().getTime());
		});
		//检验用户名
		$("#username").blur(function(){
			var username = $(this).prop("value");
			if(username==""||username==null){
				$("#nameInfoY").empty();
				$("#nameInfoN").text("用户名不能为空!");
			}else{
				$.get(
						"${pageContext.request.contextPath}/checknameServlet",
						{"username":username},
						function(data){
							//alert(data);
							if(data.boo=='true'){
								$("#nameInfoN").empty();
								$("#nameInfoY").text("用户名可用。");
							}else{
								$("#nameInfoY").empty();
								$("#nameInfoN").text("用户名不可用!");
							}
									},
						"json"
					);
				}
		});
		//校验密码是否符合
		$("#password").blur(function(){
			//先获得文本信息
			var password = $(this).prop("value");
			//判断密码是否符合，小于6个
			if(password.length<6){
				$("#passwordInfoY").empty();
				$("#passwordInfoN").text("密码长度至少为六位!");
			}else{
				$("#passwordInfoN").empty();
				$("#passwordInfoY").text("密码符合.");
			}
		});
		//校验确认密码是否符合
		$("#confirmpwd").blur(function(){
			//获得上下密码对比
			var password = $("#password").prop("value");
			var confirmpwd = $(this).prop("value");
			if(password==confirmpwd){
				$("#confirmpwdInfoN").empty();
				$("#confirmpwdInfoY").text("上下密码一样.");
			}else{
				$("#confirmpwdInfoY").empty();
				$("#confirmpwdInfoN").text("上下密码不一样!");
			}
		});
		//检验email是否符合
		$("#email").blur(function(){
			//获取email信息
			var email = $(this).prop("value");
			
			if(email.indexOf("@")!=-1 && email.indexOf(".com")!=-1){
				//符合要求
				$("#emailInfoN").empty();
				$("#emailInfoY").text("符合.");
				
			}else{
				$("#emailInfoY").empty();
				$("#emailInfoN").text("不符合!");
			}
		});
		//检验生日birthday,设置类型为date就是符合生日的形式
		  /* $("#birthday").blur(function(){
			var birthday = $(this).prop("value");
			alert(birthday);
		});   */
		//给注册按钮绑定事件，看是否符合要求，动态提交
		 $("#test").click(function(){
			//alert("submit");
			var nameInfoY = $("#nameInfoY").text();
			var passwordInfoY = $("#passwordInfoY").text();
			var confirmpwdInfoY = $("#confirmpwdInfoY").text();
			var emailInfoY = $("#emailInfoY").text();
			var name = $("#name").prop("value");
			var birthday = $("#birthday").prop("value");
			if(nameInfoY!="" && passwordInfoY!="" && confirmpwdInfoY!="" && emailInfoY!="" && name!="" && birthday!=""){
				$("form:eq(1)").submit();
				//$("form:first").submit();
				//document.form[0].submit();
				//alert("xx");
			}else{
				$("#123InfoN").text("请确认每个信息都被选中过，并正确!");
			}
			
			
		}); 
		//使用事件判断是否是能成功
		/* function test(){
			alert("xxx");
			var nameInfoY = $("#nameInfoY").text();
			var passwordInfoY = $("#passwordInfoY").text();
			var confirmpwdInfoY = $("#confirmpwdInfoY").text();
			var emailInfoY = $("#emailInfoY").text();
			var name = $("#name").prop("value");
			var birthday = $("#birthday").prop("value");
			if(nameInfoY!="" && passwordInfoY!="" && confirmpwdInfoY!="" && emailInfoY!="" && name!="" && birthday!=""){
				alert("xxx");
				return false;
				//$("form:first").submit();
				//document.form[0].submit();
				//alert("xx");
			}else{
				alert("xxx");
				$("#123InfoN").text("请确认每个信息都被选中过，并正确!");
				return false;
			}
		} */
	});
	
</script>
</head>
<body >

	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container"
		style="width: 100%; background: url('image/regist_bg.jpg');">
		<div class="row">
			<div class="col-md-2"></div>
			<div class="col-md-8"
				style="background: #fff; padding: 40px 80px; margin: 30px; border: 7px solid #ccc;">
				<font>会员注册</font>USER REGISTER
				<form class="form-horizontal" style="margin-top: 5px;" action="${pageContext.request.contextPath}/registerServlet" method="post">
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="username" name = "username"
								placeholder="请输入用户名">
						</div><span id="nameInfoN" style="color:red"></span><span id="nameInfoY" style="color:green"></span>
					</div>
					<div class="form-group">
						<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="password" name = "password"
								placeholder="请输入密码">
						</div><span id="passwordInfoN" style="color:red"></span><span id="passwordInfoY" style="color:green"></span>
					</div>
					<div class="form-group">
						<label for="confirmpwd" class="col-sm-2 control-label">确认密码</label>
						<div class="col-sm-6">
							<input type="password" class="form-control" id="confirmpwd"
								placeholder="请输入确认密码">
						</div><span id="confirmpwdInfoN" style="color:red"></span><span id="confirmpwdInfoY" style="color:green"></span>
					</div>
					<div class="form-group">
						<label for="inputEmail3" class="col-sm-2 control-label">Email</label>
						<div class="col-sm-6">
							<input type="email" class="form-control" id="email" name = "email"
								placeholder="Email">
						</div><span id="emailInfoN" style="color:red"></span><span id="emailInfoY" style="color:green"></span>
					</div>
					<div class="form-group">
						<label for="usercaption" class="col-sm-2 control-label">姓名</label>
						<div class="col-sm-6">
							<input type="text" class="form-control" id="name" name = "name"
								placeholder="请输入姓名">
						</div>
					</div>
					<div class="form-group opt">
						<label for="inlineRadio1" class="col-sm-2 control-label">性别</label>
						<div class="col-sm-6">
							<label class="radio-inline"> <input type="radio"
								name="sex" id="inlineRadio1" value="男" checked="checked">
								男
							</label> <label class="radio-inline"> <input type="radio"
								name="sex" id="inlineRadio2" value="女">
								女
							</label>
						</div>
					</div>
					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">出生日期</label>
						<div class="col-sm-6">
							<input type="date" class="form-control" name = "birthday" id = "birthday" value="1998-03-04">
						</div>
					</div>

					<div class="form-group">
						<label for="date" class="col-sm-2 control-label">验证码</label>
						<div class="col-sm-3">
							<input type="text" class="form-control" name="checkImg">

						</div>
						<div class="col-sm-2">
							<img src="${pageContext.request.contextPath}/checkImgServlet" id="checkImg"/>
						</div>&nbsp;&nbsp;&nbsp;
						<span style="color: red">${checkImgInfo}</span>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							
							 <input type="button" width="100" value="注册" id="test"
								style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;">
							<!-- <button type="button" width="100" value="注册" id="123"
								style="background: url('./images/register.gif') no-repeat scroll 0 0 rgba(0, 0, 0, 0); height: 35px; width: 100px; color: white;"></button> -->
							<!-- <a >
								<button type="submit" width="100" id="123">
								</button>
							</a> -->
						</div>
					</div><span id="123InfoN" style="color:red"></span>
				</form>
			</div>

			<div class="col-md-2"></div>

		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>

</body>
</html>




