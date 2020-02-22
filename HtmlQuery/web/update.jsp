<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- 网页使用的语言 -->
<html lang="zh-CN">
    <head>
        <!-- 指定字符集 -->
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatibl e" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>修改用户</title>

        <link href="css/bootstrap.min.css" rel="stylesheet">
        <script src="js/jquery-2.1.0.min.js"></script>
        <script src="js/bootstrap.min.js"></script>
        
    </head>
    <body>
        <div class="container" style="width: 400px;">
        <h3 style="text-align: center;">修改联系人</h3>
        <form action="${pageContext.request.contextPath}/updateUserServlet" method="post">
          <div class="form-group">
            <label for="name">姓名：</label>
            <input type="text" class="form-control" id="name"  value="${finduser.name}" name="name"  readonly="readonly"  />
          </div>
            <input type="hidden" name="id" value="${finduser.id}" />
          <div class="form-group">
              <c:if test ="${finduser.gender =='男'}">
            <label>性别：</label>
              <input type="radio" name="gender" value="男"  checked="checked" />男
                <input type="radio" name="gender" value="女"  />女
               </c:if>
              <c:if test="${finuser.gender == '女'}">
              <input type="radio" name="gender" value="男"  />男
              <input type="radio" name="gender" value="女" checked="checked" />女
              </c:if>
              <c:if test ="${finduser.gender !='男' && finduser.gender !='女'}">
                  <label>性别：</label>
                  <input type="radio" name="gender" value="男" />男
                  <input type="radio" name="gender" value="女"  />女
              </c:if>
          </div>

          <div class="form-group">
            <label for="age">年龄：</label>
            <input type="text" class="form-control" id="age" value="${finduser.age}" name="age" />
          </div>

          <div class="form-group">
            <label for="home">籍贯：</label>
             <select name="home"  id="home" class="form-control" >
                 <c:if test ="${finduser.home =='广东'}">
                     <option value="广东" selected>广东</option>
                     <option value="广西">广西</option>
                     <option value="山西">山西</option>
                 </c:if>
                 <c:if test ="${finduser.home =='广西'}">
                 <option value="广东" >广东</option>
                 <option value="广西" selected>广西</option>
                 <option value="山西">山西</option>
                </c:if>
                 <c:if test ="${finduser.home =='山西'}">
                 <option value="广东">广东</option>
                 <option value="广西">广西</option>
                 <option value="山西" selected>山西</option>
                 </c:if>
                 <c:if test ="${finduser.home == null}">
<%--java中string的默认初始值为null，其实表单校验，应该把这种情况排除，而且数据库也可以设计成此项不能为空，然后能避免不符合格式的数据写入--%>
                     <option value="广东" >广东</option>
                     <option value="广西">广西</option>
                     <option value="山西">山西</option>
                 </c:if>

             </select>
          </div>

          <div class="form-group">
            <label for="qq">QQ：</label>
            <input type="text" class="form-control" id="qq" name="qq" value="${finduser.qq}"/>
          </div>

          <div class="form-group">
            <label for="mail">Email：</label>
            <input type="text" class="form-control" id = "mail" name="mail" value="${finduser.mail}"/>
          </div>

             <div class="form-group" style="text-align: center">
                <input class="btn btn-primary" type="submit" value="提交" />
                <input class="btn btn-default" type="reset" value="重置" />
                <input class="btn btn-default" type="button" value="返回"/>
             </div>
        </form>
        </div>
    </body>
</html>