<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="nl-NL">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta name="description" content="Wasrooster van de studentenflat Parkhaven in Rotterdam" />
<meta name="keywords" content="" />

<title>Parkhaven - Was Schema</title>

<link rel="shortcut icon" href="${pageContext.request.contextPath}/resources/img/favicon.ico" type="image/x-icon">
<link rel="icon" href="${pageContext.request.contextPath}/resources/img/favicon.ico" type="image/x-icon">

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrapEdited.css" />
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dashboard.css" />
</head>

<body>
  <nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
          <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand mainTitle" href="/WasSchema/index.010">Parkhaven - Was Schema</a>
      </div>

      <div id="navbar" class="navbar-collapse collapse">
        <div class="navbar-form navbar-right">
          <div id="login_information">Logged in as <c:out value="${sessionScope.user.email}" /></div>
        </div>
      </div>

    </div>
  </nav>

  <div class="col-sm-9 col-md-10 main">
    <div class="row">
      <h4 class="page-header">Pending Messages:</h4>
      <c:forEach var="message" items="${prikbord_messages}">
        <h4>${message.value.titleOutput}</h4>
        ${message.value.bodyOutput} <c:out value="${message.value.userEmail}"></c:out>
        <form name="accept_prikbord_message_form" class="form-signin" action="admin.010" method="post">
          <input name="id" value="${message.value.id}" style="display: none;" required>
          <input name="to_servlet" value="acceptMessage" style="display: none;">
          <button type="submit" name="messageAccepted" value="true" class="btn btn-succes btn-xs">
            Approve Message <span class="glyphicon glyphicon-ok"></span>
          </button>
          <button type="submit" name="messageAccepted" value="false" class="btn btn-succes btn-xs">
            Decline Message <span class="glyphicon glyphicon-remove"></span>
          </button>
        </form>
        <hr></hr>
      </c:forEach>
    </div>
  </div>

  <div class="col-sm-8 col-md-8 main">
    <div class="row">
      <h4 class="page-header">Users:</h4>
      <form id="searchUserForm" action="admin.010" method="post">
        <input name="to_servlet" value="getAllUsers" style="display: none;">
        <input type="submit" name="submit" value="Search" />
      </form>
      <br>
      <table class="table table-striped">
        <c:forEach items="${requestScope.users}" var="entry" varStatus="loop">
          <tr data-id="${entry.key}">
            <td>${entry.value.id}</td>
            <td>${entry.value.firstName}</td>
            <td>${entry.value.lastName}</td>
            <td>${entry.value.email}</td>
            <td>${entry.value.houseNumber}</td>
            <td style="display: none;" >${entry.value.password}</td>
            <td><button class="deleteButton" value="${entry.key}" data-toggle="modal" data-target="#delete_account_modal">Delete</button><td>
          </tr>
        </c:forEach>
    </table>
    </div>
  </div>

  <!-- DELETE USER ACCOUNT MODAL -->
  <div class="modal fade" id="delete_account_modal" role="dialog" data-controls-modal="delete_account_modal" data-backdrop="static"
    data-keyboard="false">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="center_modal_top_close_button close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header form-signin-heading">Delete Account</h4>
        </div>
        <form name="delete_account" class="form-signin" action="admin.010" method="post">
          <div class="modal-body">
            <div class="row">
              <div class="col-xs-9 col-xs-offset-1">
                <input id="id" type="text" name="id" class="form-control settings_modal" readonly />                              
                <input id="email" name="email" type="text" class="form-control settings_modal" readonly />
                <input id="password" style="display: none;" name="password" type="text" class="form-control settings_modal" readonly />
                <input id="housenumber" name="houseNumber" type="text" class="form-control settings_modal" readonly />
                <input name="to_servlet" value="deleteSelectedUser" style="display: none;" required>
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-primary" type="submit">
              Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span>
            </button>
            <button class="btn btn-default" type="button" data-dismiss="modal">
              Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

  <script>
    $(document).ready(function() {
      $(".deleteButton").click(function() {
    	  var id = $(this).val();
    	  console.log(id);
    	  var topTr = $(this).parent().parent();
        $("#id").val(topTr.find(":nth-child(1)").html());
        $("#email").val(topTr.find(":nth-child(4)").html());
        $("#password").val(topTr.find(":nth-child(6)").html());
        $("#housenumber").val(topTr.find(":nth-child(5)").html());
      });
    });
  </script>

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