<%@ page language="java" contentType="text/html; charset=utf-8"
pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>11111111</title>
        <script type="text/javascript" src="/resource/jquery-1.9.1/jquery-1.9.1.min.js"></script>
        <script type="text/javascript">
            $(function() {
            	var mmp={"name": "薛利飞","classID":"1"};
             	var obj = JSON.stringify(mmp);
  //          	var obj = code;
                $("#tj").click(function() {
                	 alert("111");
                    $.ajax({
                        type: "POST",
                        beforeSend: function (xhr) {
                            xhr.setRequestHeader("third_session", "1111111111111");
                        },
                        url: "http://localhost:8080/resource/user/add.do",
                        data:{code:"admin"},
                       	dataType:'json',
                       	data:obj,
                      //  processData: false,  // 告诉jQuery不要去处理发送的数据
      				  	contentType: "application/json;charset=utf-8",  
                        success: function(data) {
                            alert(data)
                        },
                        error: function(json) {
                        	alert(obj);
                            alert("failed");
                            return false;
                        }
                    });
                });
            });
        </script>
    </head>

    <body>

        <br>
        <input type="button" value="getDwbRmirrorReqH" id="tj" />
        <br>

    </body>

</html>