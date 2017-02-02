<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="nl-NL">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<meta name="description" content="" />
<meta name="author" content="" />
<meta name="keywords" content="" />

<title>Parkhaven - Was Schema</title>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap2.css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/dashboard.css" />
</head>

<body>

	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand mainTitle" href="/WasSchema/">Parkhaven - Was
					Schema</a>
			</div>

			<div id="navbar" class="navbar-collapse collapse">
				<div class="navbar-form navbar-right">

					<c:if test="${empty sessionScope.user.email}">
						<form id="loginForm" name="loginForm" action="" method="post">
							<div class="form-group">
								<input name="email" type="email" class="form-control"
									id="exampleInputEmail3" placeholder="Email" required> <input
									name="password" type="password" class="form-control"
									id="exampleInputPassword3" placeholder="Password" required>
							</div>
							<input name="signin" value="1" style="display: none;" />
							<button type="submit" class="btn btn-default">Log in</button>
						</form>

						<button id="testingShow" data-toggle="modal"
							data-target="#myRegisterModel" class="btn btn-default">Sign
							up</button>
					</c:if>
					<c:if test="${not empty sessionScope.user.email}">
						<div id="login_information">
							Logged in as
							<c:out value="${sessionScope.user.email}" />
						</div>
						<form id="logoutForm" name="logoutButton" class="form-signin"
							action="" method="post">
							<button type="submit" class="btn btn-default">Log Out</button>
						</form>
					</c:if>
				</div>
			</div>

		</div>
	</nav>


	<div class="col-sm-9 col-md-10 main">
		<div class="row">
			${sessionScope.user.email}
			<h4 class="page-header">Pending Messages:</h4>
			<c:forEach var="message" items="${prikbord_messages}">
				<h4>${message.value.titleOutput}</h4>
				${message.value.bodyOutput}
				<c:out value="${message.value.gebruikerMail}"></c:out>
				<form name="accept_prikbord_message_form" class="form-signin" action="admin.010" method="post">
					<input name="message_id" value="${message.value.id}" style="display: none;" required>
					<button type="submit" name="accept_prikbord_message" value="true" class="btn btn-succes btn-xs">Approve Message <span class="glyphicon glyphicon-ok"></span></button>
					<button type="submit" name="accept_prikbord_message" value="false" class="btn btn-succes btn-xs">Decline Message <span class="glyphicon glyphicon-remove"></span></button>
				</form>
				<hr></hr>		
			</c:forEach>
		</div>
	</div>



	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

	<script>
		$("[data-toggle=popover]").popover();

		$("#myModal").draggable({
			handle : ".modal-content"
		});

		$("#testingShowDiv").hide();

		$(document).ready(function() {
			$("#testingShow").click(function() {
				$("#testingShowDiv").show();
			});
		});

		function myFunction() {
			$("#testingShowDiv").hide();
		};

		$('#myRegisterModal').modal({
			backdrop : 'static',
			keyboard : false
		})
	</script>

	<!-- Showing Error Messages! -->
	<script>
		myVar = "${errorMessage}";
		if (myVar != "") {
			alert(myVar);
		}
	</script>

</body>
</html>