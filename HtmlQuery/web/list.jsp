<%-- 用户查询显示的主页面 --%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    <title>用户信息管理系统</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="js/bootstrap.min.js"></script>
    <script>
        function deleteUser(id){
            if(confirm("您确定要删除吗")){
                location.href ="${pageContext.request.contextPath}/deleteServlet?id="+id;
            }
        }

        window.onload = function () {
            document.getElementById("delSelect").onclick = function(){
                var flag = false;
                var elements = document.getElementsByName("uid");//document是浏览器window对象的一部分，所以分页后，全选只能选中本页的数据，不能选中其他页的数据
                if(confirm("您确定要删除吗？")) {
                    for (var i = 0; i < elements.length; i++) {
                        if (elements[i].checked) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        document.getElementById("form").submit();
                    }
                }

            }

            document.getElementById("firstCheckbox").onclick = function(){
                var elements = document.getElementsByName("uid");
                for (var i = 0; i < elements.length; i++) {
                    elements[i].checked = this.checked;
                }
            }

        }

    </script>
    <style type="text/css">
        td, th {
            text-align: center;
        }
    </style>
</head>
<body>
<div class="container">
    <h3 style="text-align: center">用户信息列表</h3>
    <div style="float:left ; margin-left: 5px;margin-bottom: 5px">
        <form class="form-inline" action="${pageContext.request.contextPath}/pageControlServlet">
            <div class="form-group">
                <label for="exampleInputName2">姓名</label>
                <input type="text" class="form-control" id="exampleInputName2" value="${map.name[0]}" name="name">
            </div>
            <div class="form-group">
                <label for="exampleInputEmail2">籍贯</label>
                <input type="text" class="form-control" id="exampleInputEmail2" value="${map.home[0]}"  name="home">
            </div>
            <div class="form-group">
                <label for="exampleInputEmail3">邮箱</label>
                <input type="text" class="form-control" id="exampleInputEmail3" value="${map.mail[0]}"  name="mail">
            </div>
            <button type="submit" class="btn btn-default">查询</button>
        </form>
    </div>
    <div style="float: right ;margin-right: 5px" >
        <a class="btn btn-primary" href="${pageContext.request.contextPath}/add.jsp">添加联系人</a>
        <a class="btn btn-primary" href="javascript:void(0);" id="delSelect">删除选中</a>
    </div>
    <form action="${pageContext.request.contextPath}/delSelectedServlet" method="post" id="form">
    <table border="1" class="table table-bordered table-hover">
        <tr class="success">
            <th><input type="checkbox" id="firstCheckbox"></th>
            <th>编号</th>
            <th>姓名</th>
            <th>性别</th>
            <th>年龄</th>
            <th>籍贯</th>
            <th>QQ</th>
            <th>邮箱</th>
            <th>操作</th>
        </tr>
            <c:forEach items="${pb.list}" var="user" varStatus="s">
        <tr>
                <td><input type="checkbox" name="uid" value="${user.id}"></td>
                <td>${s.index}</td>
                <td>${user.name}</td>
                <td>${user.gender}</td>
                <td>${user.age}</td>
                <td>${user.home}</td>
                <td>${user.qq}</td>
                <td>${user.mail}</td>
                <td><a class="btn btn-default btn-sm" href="${pageContext.request.contextPath}/findUserServlet?id=${user.id}">修改</a>&nbsp;<a class="btn btn-default btn-sm" href="javascript:deleteUser(${user.id})">删除</a></td>
        </tr>
            </c:forEach>
    </table>
    </form>

    <nav aria-label="Page navigation">
        <ul class="pagination">
            <c:if test="${pb.currentPage == 1}">
                <li class="disabled">
            </c:if>
            <c:if test="${pb.currentPage != 1}">
            <li >
            </c:if>
                <a href="${pageContext.request.contextPath}/pageControlServlet?currentPage=${pb.currentPage - 1}&rows=2&name=${map.name[0]}&mail=${map.mail[0]}&home=${map.home[0]}" aria-label="Previous" >
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>

            <c:forEach var="i" begin="1" end="${pb.pages}">
                <c:if test="${pb.currentPage == i}">
                    <li class="active" >
                        <a href="${pageContext.request.contextPath}/pageControlServlet?currentPage=${i}&rows=2&name=${map.name[0]}&mail=${map.mail[0]}&home=${map.home[0]}">
                                ${i}
                        </a>
                    </li>
                </c:if>
                <c:if test="${pb.currentPage != i}" >
                    <li>
                        <a href="${pageContext.request.contextPath}/pageControlServlet?currentPage=${i}&rows=2&name=${map.name[0]}&mail=${map.mail[0]}&home=${map.home[0]}">
                                ${i}
                        </a>
                    </li>
                </c:if>
            </c:forEach>
            <c:if test="${(pb.pages - pb.currentPage) == 0}">
            <li class="disabled">
                </c:if>
                <c:if test="${(pb.pages - pb.currentPage) != 0}">
            <li >
                </c:if>
                <a href="${pageContext.request.contextPath}/pageControlServlet?currentPage=${pb.currentPage+1}&rows=2&name=${map.name[0]}&mail=${map.mail[0]}&home=${map.home[0]}" aria-label="Next">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
            <span style="font-size: 25px;">共${pb.allRows}条记录，共${pb.pages}页</span>
        </ul>
    </nav>

</div>
</body>
</html>