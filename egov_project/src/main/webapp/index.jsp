<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%-- <jsp:forward page="/egovSampleList.do"/> --%>
<html>
<head>
<title>h2</title>
</head>
<body>
	${errMsg }

	<h2>hello world</h2>


	<form action="/connect/facebook" method="post" id="facebook-form">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		<input type="hidden" name="scope" value="public_profile, email"/>
		<button type="submit">Sign In with Facebook</button>
	</form>

	



</body>
</html>