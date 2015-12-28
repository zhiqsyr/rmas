<%@page contentType="text/html;charset=UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body>
</body>
<script type="text/javascript">  
function jump(){  
    if (parent != null) {
    	parent.location.href="<%=request.getContextPath()%>/zul/system/user_login.zul";
    } else {
    	window.location.href="<%=request.getContextPath()%>/zul/system/user_login.zul";
    }
}  
jump();  
</script>
</html>