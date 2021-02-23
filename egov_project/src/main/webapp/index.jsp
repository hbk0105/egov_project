<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <jsp:forward page="/egovSampleList.do"/> --%>
<html>
<head>
<title>h2</title>
<meta name="_csrf" content="${_csrf.token}">
<meta name="_csrf_header" content="${_csrf.headerName}">
<script
  src="https://code.jquery.com/jquery-1.12.4.js"
  integrity="sha256-Qw82+bXyGq6MydymqBxNPYTaUXXq7c8v3CwiYwLLNXU="
  crossorigin="anonymous"></script>
<script type="text/javascript">

	$(function() {
			ajaxCall();
	});
	
	function ajaxCall(){
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		var name = "나의 이름은..alert(1);";
		
		var jsonData = {"nome":"<script>alert('xss');"+$('#test').val()};
		
		var ss = "<script>alert('xss');"+$('#test').val();
		
		
		$.ajax({
			type: 'post',
			url:'/updateUser.do',
			data: {'data' : JSON.stringify(jsonData)}, // String -> json 형태로 변환
			beforeSend : function(xhr)
            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				xhr.setRequestHeader(header, token);
            },
			//dataType: 'json', // success 시 받아올 데이터 형
			success: function(data){
				console.log(data);
				$('#aaa').html(data);
				alert(1);
				//console.log(data.name);
			},
			error:function(xhr,status,error){
				console.log('error:'+error);
			}
		});
	}
	
</script>

</head>
<body>
	${errMsg }

	<h2>hello world</h2>
	<input type="text" id="test" value="</script>" />
	<h3 id="aaa"></h3>
</body>
</html>