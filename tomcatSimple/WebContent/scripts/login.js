function userLogin(){
	//测试绑定事件
	//alert("堵哪儿了");
	//获取参数
	var email = $("#email").val().trim();
	var password = $("#password").val().trim();
	//console.log(name+":"+password);
	$("#email_span").html("");
	$("#password_span").html("");
	//格式检查
	var ok = true;
    var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(email == ""){
		$("#email_span").html("email can not be null");
		ok = false;
	}else if(!emailReg.test(email)) {
        $("#email_span").html("please enter valid E_mail address");
        ok = false;
    }
	if(password == ""){
		$("#password_span").html("password can not be null");
		ok = false;
	}
	if(ok){
		//发送请求
		$.ajax({
			url : path + "/user/login.do",
			type : "post",
			data : {"email":email,"password":password},
			dataType : "json",
			success : function(result){
				//result是服务器返回的JSON结果
				if(result.status == 0){
					//将用户信息保存到cookie
					var userId = result.data.cn_user_id;
					addCookie("userId",userId,2);
					window.location.href = "profile.html";
				}else if(result.status == 1){
					$("#email_span").html(result.msg);
				}else if(result.status == 2){
					$("#password_span").html(result.msg);
				}
			},
			error : function(){
				alert("login failure!");
				}
			});
		}
};

function userRegister(){
	//测试绑定事件
	//alert("888");
	var email = $("#email").val().trim();
	var nickname = $("#nickname").val().trim();
	var password = $("#password").val().trim();
	var checkPassword = $("#check-password").val().trim();
	//alert(name + ":" + nickname + ":" + password +":" +checkPassword);
	$("#email_span").html("");
	$("#password_span").html("");
	$("#nickname_span").html("");
	$("#check-password_span").html("");
	
	//检查数据格式
	//对电子邮件的验证
	var ok = true;//表示参数状态

    var emailReg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
	if(email == ""){
		$("#email_span").html("email can not be null");
		ok = false;
	}else if(!emailReg.test(email)) {
        $("#email_span").html("please enter valid E_mail address");
        ok = false;
	}
	
	if(nickname == ""){
		$("#nickname_span").html("username can not be null");
		ok = false;
	}
	
	//检测密码：1-非空 2-不能小于6位
	if(password == ""){
		$("#password_span").html("password can not be null");
		ok = false;
	}else if(password.length < 6 && password.length > 0){
		$("#password_span").html("the length of password can not less than 6");
		ok = false;
	}
	
	//检测确认密码：1-非空 2-是否与密码一致
	if( password != checkPassword){
		$("#check-password_span").html("inconsistent password");
		ok = false;
	}
	
	if(ok){//数据校验通过，发送AJAX请求
		$.ajax({
			url:path + "/user/register.do",
			type : "post",
			data : {"email":email, "username":nickname,"password":password},
			datatype : "json",
			success : function(result){
				if(result.status == 0){
					alert(result.msg);
					//返回到登陆页面
					$("#back").click();
				}else if(result.status == 1){
					$("#email_span").html(result.msg);
				}
			},
			error : function(){
				alert("register failure！");
			}
		});
	}
};