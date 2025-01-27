<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration</title>
<link
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"
	rel="stylesheet">
<style>
body {
	display: flex;
	justify-content: center;
	align-items: center;
	height: 100vh;
	background-color: #f1f2f2;
	margin: 0;
	font-family: Arial, sans-serif;
}

.container {
	background-color: #dcdbe1;
	padding: 30px;
	border-radius: 8px;
	box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
	width: 350px;
}

h2 {
	text-align: center;
	color: #080808;
	margin-bottom: 30px;
}

label {
	display: block;
	margin-bottom: 5px;
	color: #303032;
}

input {
	width: 100%;
	padding: 10px;
	margin-bottom: 15px;
	border: 1px solid #303032;
	border-radius: 4px;
	font-size: 16px;
}

button {
	width: 100%;
	padding: 10px;
	background-color: #080808;
	border: none;
	border-radius: 4px;
	color: white;
	font-size: 16px;
	cursor: pointer;
}

button:hover {
	background-color: #303032;
}

.error-message {
	color: #a94442; /* Цвет ошибки */
	margin-bottom: 10px;
	text-align: center;
}

a {
	display: block;
	text-align: center;
	margin-top: 10px;
	text-decoration: none;
	color: #080808;
}

a:hover {
	text-decoration: underline;
}
</style>
</head>
<body>

	<div class="container mt-5">
		<h2>User Registration</h2>
		<form action="MyController" method="post">
			<input type="hidden" name="command" value="do_registration" />

			<div class="error-message">
				<c:if test="${not empty requestScope.error}">
					<c:out value="${requestScope.error}" />
				</c:if>
			</div>


			<label for="name">Name</label> <input type="text"
				class="form-control" id="name" name="name" required> <label
				for="email">Email</label> <input type="email" class="form-control"
				id="email" name="email" required> <label for="password">Password</label>
			<input type="password" class="form-control" id="password"
				name="password" required>

			<button type="submit">Register</button>
		</form>

		<a href="MyController?command=go_to_index_page">Already have an
			account? Log in</a>
	</div>

</body>
</html>
