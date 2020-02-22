<%@ page contentType="text/html;charset=utf-8" language="java" %>
<!-- HTML5文档-->
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
<head>
    <!-- 指定字符集 -->
    <meta charset="utf-8">
    <!-- 使用Edge最新的浏览器的渲染方式 -->
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <!-- viewport视口：网页可以根据设置的宽度自动进行适配，在浏览器的内部虚拟一个容器，容器的宽度与设备的宽度相同。
    width: 默认宽度与设备的宽度相同
    initial-scale: 初始的缩放比，为1:1 -->
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>添加用户</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <%-- 实现表单校验
            1.获取form对象
            2.姓名和年龄的onblur校验
            3.最后提交时的总体校验
     --%>
    <script>
        window.onload = function() {

            var form = document.getElementById("form");
            form.onsubmit = function () {
                return checkUser() && checkAge();
            }

            document.getElementById("name").onblur = checkUser;
            document.getElementById("age").onblur = checkAge;
        }

        function checkUser() {
            var name = document.getElementById("name").value;
            var reg = /^\w{6,12}$/;
            var flag = reg.test(name);
            var userinf = document.getElementById("checkuser");
            if(flag){
                userinf.innerHTML = "挺好";
            }else{
                userinf.innerHTML = "差点意思";
            }

            return flag;

        }
        function checkAge() {
            var age = document.getElementById("age").value;
            var reg = /^\d{1,3}$/;
            var flag = reg.test(age);
            var ageinf = document.getElementById("checkage");
            if(flag){
                ageinf.innerHTML = "挺好";
            }else{
                ageinf.innerHTML = "你太大了";
            }
            return flag;
        }



    </script>
</head>
<body>
<div class="container">
    <center><h3>添加联系人页面</h3></center>
    <form action="${pageContext.request.contextPath}/addUserServlet" method="post" id="form">
        <div class="form-group">
            <label for="name">姓名：</label>
            <input type="text" class="form-control" id="name" name="name" placeholder="请输入姓名">
            <span id = "checkuser"></span>
        </div>

        <div class="form-group">
            <label>性别：</label>
            <input type="radio" name="gender" value="男" checked="checked"/>男
            <input type="radio" name="gender" value="女"/>女
        </div>

        <div class="form-group">
            <label for="age">年龄：</label>
            <input type="text" class="form-control" id="age" name="age" placeholder="请输入年龄">
            <span id = "checkage"></span>
        </div>

        <div class="form-group">
            <label for="home">籍贯：</label>
            <select name="home" class="form-control" id="home">
                <option value="广东">广东</option>
                <option value="广西">广西</option>
                <option value="湖南">湖南</option>
            </select>
        </div>

        <div class="form-group">
            <label for="qq">QQ：</label>
            <input type="text" class="form-control" name="qq" id="qq" placeholder="请输入QQ号码"/>
        </div>

        <div class="form-group">
            <label for="mail">Email：</label>
            <input type="text" class="form-control" name="mail" id="mail" placeholder="请输入邮箱地址"/>
        </div>

        <div class="form-group" style="text-align: center">
            <input class="btn btn-primary" type="submit" value="提交" />
            <input class="btn btn-default" type="reset" value="重置" />
            <input class="btn btn-default" type="button" value="返回" />
        </div>
    </form>
</div>
</body>
</html>