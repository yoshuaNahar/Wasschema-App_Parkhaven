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
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/alert-box.css" />
<!-- Stylesheet for prikbord message modal -->
<link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css" />
<style>
 <c:if test="${requestScope.laundryRoom == 2}">
 #blue, .progress-bar-info {background-color: #69d2e7;}
 #green, .progress-bar-success {background-color: #588c73;}
 #yellow, .progress-bar-warning {background-color: #f39c12;}
 #red, .progress-bar-danger {background-color: #f2ae72;}
 </c:if>
 <c:if test="${requestScope.laundryRoom == 3}">
 #blue, .progress-bar-info {background-color: #24a8ac;}
 #green, .progress-bar-success {background-color: #ffa200;}
 #yellow, .progress-bar-warning {background-color: #9b539c;}
 #red, .progress-bar-danger {background-color: #eb65a0;}
 </c:if>
</style>
</head>

<body>
  <nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand mainTitle" href="${pageContext.request.contextPath}/index.010"> <img id="menu_img"
            src="${pageContext.request.contextPath}/resources/img/parkhaven-logo.png" /><span id="menu_span">Wasschema</span>
        </a>
      </div>

      <div class="navbar-form navbar-right">
        <c:if test="${not empty sessionScope.user.email}">
          <div id="login_information">
            Logged in as
            <c:out value="${sessionScope.user.email}" />
          </div>
          <c:if test="${sessionScope.user.admin}">
            <form id="adminForm" name="adminButton" class="form-signin" action="admin.010" method="get">
              <button type="submit" class="btn btn-succes">Admin page</button>
            </form>
          </c:if>
          <form id="logoutForm" name="logoutButton" class="form-signin" action="index.010" method="post">
            <button id="logoutButton" type="submit" class="btn btn-default">Log Out</button>
          </form>
        </c:if>
      </div>
    </div>
  </nav>

  <div class="container-fluid">
    <div class="row">
      <div class="col-xs-2 sidebar">
        <ul class="nav nav-sidebar">
          <li class="active dashBoard_margin"><a class="dashBoard_boldness">Dashboard <span class="shrink_glyph glyphicon glyphicon-home"></span><span
              class="sr-only">(current)</span></a></li>
          <li>
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">

              <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingFour">
                  <h4 class="panel-title">
                    <a id="left_menu_header" class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour"
                      aria-expanded="false" aria-controls="collapseFour"> Settings<span
                      class="dashboard_glyph shrink_glyph glyphicon glyphicon-cog"></span>
                    </a>
                  </h4>
                </div>
                <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                  <div class="panel-body">
                    <a class="settings_link" data-toggle="modal" data-target="#modify_account_housenumber_modal">Change housenumber</a><hr class="smaller_margin"></hr>
                    <a class="settings_link" data-toggle="modal" data-target="#modify_account_password_modal">Change password</a><hr class="smaller_margin"></hr>
                    <a class="settings_link" data-toggle="modal" data-target="#delete_account_modal">Delete account</a>
                  </div>
                </div>
              </div>

              <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingTwo">
                  <h4 class="panel-title">
                    <a id="left_menu_header" class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo"
                      aria-expanded="true" aria-controls="collapseTwo">Choose Laundry Room <img id="laundryroom_img"
                        src="${pageContext.request.contextPath}/resources/img/laundrymachine.svg" />
                    </a>
                  </h4>
                </div>
                <div id="collapseTwo" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingTwo">
                  <div class="panel-body">
                    <c:if test="${requestScope.laundryRoom != 1 && requestScope.laundryRoom != 2 && requestScope.laundryRoom != 3}">
                      <a class="settings_link" href="?week=${requestScope.week}&laundryRoom=1">Laundry room A</a><hr class="smaller_margin"></hr>
                      <a class="settings_link" href="?week=${requestScope.week}&laundryRoom=2">Laundry room B</a><hr class="smaller_margin"></hr>
                      <a class="settings_link" href="?week=${requestScope.week}&laundryRoom=3">Laundry room C</a>
                    </c:if>
                    <c:if test="${requestScope.laundryRoom == 1}">
                      <a class="settings_link">Laundry room A <span class="label" id="default_blue">(Current)</span></a><hr class="smaller_margin"></hr>
                      <a class="settings_link" href="?week=${requestScope.week}&laundryRoom=2">Laundry room B</a><hr class="smaller_margin"></hr>
                      <a class="settings_link" href="?week=${requestScope.week}&laundryRoom=3">Laundry room C</a>
                    </c:if>
                    <c:if test="${requestScope.laundryRoom == 2}">
                      <a class="settings_link" href="?week=${requestScope.week}&laundryRoom=1">Laundry room A</a><hr class="smaller_margin"></hr>
                      <a class="settings_link">Laundry room B <span class="label" id="default_blue">(Current)</span></a><hr class="smaller_margin"></hr>
                      <a class="settings_link" href="?week=${requestScope.week}&laundryRoom=3">Laundry room C</a>
                    </c:if>
                    <c:if test="${requestScope.laundryRoom == 3}">
                      <a class="settings_link" href="?week=${requestScope.week}&laundryRoom=1">Laundry room A</a><hr class="smaller_margin"></hr>
                      <a class="settings_link" href="?week=${requestScope.week}&laundryRoom=2">Laundry room B</a><hr class="smaller_margin"></hr>
                      <a class="settings_link">Laundry room C <span class="label" id="default_blue">(Current)</span></a>
                    </c:if>
                  </div>
                </div>
              </div>
            </div>
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
              <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingOne">
                  <h4 class="panel-title">
                    <img src="${pageContext.request.contextPath}/resources/img/prikbord.png" data-toggle="modal" data-target="#prikbord_modal"
                      id="prikbord_header" />
                  </h4>
                </div>
                <div id="collapseOne" class="panel-collapse collapse in">
                  <div id="prikbord_body" class="panel-body">

                    <c:forEach var="message" items="${prikbord_messages}">
                      <h4 class="prikbord_message panel-title">${message.value.titleOutput}</h4>
               	     	${message.value.bodyOutput}
               	     	<c:out value="${message.value.userEmail}"></c:out>

                      <c:if test="${message.value.userId == sessionScope.user.id || sessionScope.user.admin}">
                        <button id="save_message_id" data-title="${message.value.titleOutput}" data-id="${message.key}" type="button"
                          data-toggle="modal" data-target="#delete_message_modal" class="btn btn-default btn-xs">Remove message</button>
                      </c:if>

                      <hr class="prikbord_message" />
                    </c:forEach>
                  </div>
                </div>
              </div>
            </div>
          </li>
        </ul>

      </div>


  <div id="alert_div" class="alert alert-success" style="position:absolute; top: 6%; left: 40%">
    <a href="" class="close" data-dismiss="alert" aria-label="close"><span aria-hidden="true">&times;</span></a>
    <span id="errorMessage"></span>
  </div>


      <div class="col-xs-10 col-xs-offset-2 main">

<c:if test="${requestScope.laundryRoom == 1 || requestScope.laundryRoom == 2 || requestScope.laundryRoom == 3}">
        <nav aria-label="week_pagination" id="week">
          <ul class="pager">
            <c:if test="${requestScope.week != 'next'}">
              <li class="disabled label"><a id="active_week">Current Week</a></li>
              <li class="label"><a id="available_week" href="?week=next&laundryRoom=${requestScope.laundryRoom}">Next Week <span
                  class="shrink_glyph glyphicon glyphicon-arrow-right"></span>
              </a></li>
            </c:if>
            <c:if test="${requestScope.week == 'next'}">
              <li class="label"><a id="available_week" href="?week=current&laundryRoom=${requestScope.laundryRoom}"><span
                  class="shrink_glyph glyphicon glyphicon-arrow-left"></span> Current Week</a></li>
              <li class="label disabled"><a id="active_week">Next Week</a></li>
            </c:if>
          </ul>
        </nav>

        <div class="vertical-line"></div>

        <nav aria-label="week_pagination" id="dryers">
          <ul class="pager">
            <li class="label"><a class="go_to_dryers" id="available_week">Dryers <span class="shrink_glyph glyphicon glyphicon-arrow-down"></span></a></li>
          </ul>
        </nav>


        <div id="overview">
          <h2 class="page-header">
             Week <small>${get_overview}</small>
          </h2>
        </div>

        <div class="legend">
          <c:if test="${requestScope.laundryRoom == 1}">
            <small><span class="label" id="blue">Laundrymachine A1</span> <b>-</b> <span class="label" id="yellow">Laundrymachine A2</span></small>
          </c:if>
          <c:if test="${requestScope.laundryRoom == 2}">
            <small><span class="label" id="blue">Laundrymachine B1</span> <b>-</b> <span class="label" id="yellow">Laundrymachine B2</span></small>
          </c:if>
          <c:if test="${requestScope.laundryRoom == 3}">
            <small><span class="label" id="blue">Laundrymachine C1</span> <b>-</b> <span class="label" id="yellow">Laundrymachine C2</span></small>
          </c:if>
        </div>


        <div class="table-responsive">
          <table id="laundry_machines_table" class="table table-striped">
            <thead>
              <tr>
                <th id="timeHeader">Time</th>
                <th>Monday</th>
                <th>Tuesday</th>
                <th>Wednesday</th>
                <th>Thursday</th>
                <th>Friday</th>
                <th>Saturday</th>
                <th>Sunday</th>
              </tr>
            </thead>
            
            <tbody data-machineType="wash">
              <tr>
                <td><c:out value="${time[0]}"></c:out></td>
                <c:forEach var="i" begin="0" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-info">
                          <span class="coverProgressNumber">${huis_nummer1[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-warning">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[1]}"></c:out></td>
                <c:forEach var="i" begin="1" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-info">
                          <span class="coverProgressNumber">${huis_nummer1[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-warning">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[2]}"></c:out></td>
                <c:forEach var="i" begin="2" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-info">
                          <span class="coverProgressNumber">${huis_nummer1[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-warning">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>
            
              <tr>
                <td><c:out value="${time[3]}"></c:out></td>
                <c:forEach var="i" begin="3" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-info">
                          <span class="coverProgressNumber">${huis_nummer1[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-warning">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-default" style="width:50%"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[4]}"></c:out></td>
                <c:forEach var="i" begin="4" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-info">
                          <span class="coverProgressNumber">${huis_nummer1[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-warning">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[5]}"></c:out></td>
                <c:forEach var="i" begin="5" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-info">
                          <span class="coverProgressNumber">${huis_nummer1[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-warning">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[6]}"></c:out></td>
                <c:forEach var="i" begin="6" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-info">
                          <span class="coverProgressNumber">${huis_nummer1[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-warning">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[7]}"></c:out></td>
                <c:forEach var="i" begin="7" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-info">
                          <span class="coverProgressNumber">${huis_nummer1[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-warning">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
             </tr>

              <tr>
                <td><c:out value="${time[8]}"></c:out></td>
                <c:forEach var="i" begin="8" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-info">
                          <span class="coverProgressNumber">${huis_nummer1[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-warning">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[9]}"></c:out></td>
                <c:forEach var="i" begin="9" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-info">
                          <span class="coverProgressNumber">${huis_nummer1[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-warning">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[10]}"></c:out></td>
                <c:forEach var="i" begin="10" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-info">
                          <span class="coverProgressNumber">${huis_nummer1[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-warning">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[11]}"></c:out></td>
                <c:forEach var="i" begin="11" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-info">
                          <span class="coverProgressNumber">${huis_nummer1[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-warning">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[12]}"></c:out></td>
                <c:forEach var="i" begin="12" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-info">
                          <span class="coverProgressNumber">${huis_nummer1[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer1[i]}'>
                        <div data-machine="1" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-warning">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="2" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              <tr>
            </tbody>
          </table>
        </div>

        <div class="legend">
          <c:if test="${requestScope.laundryRoom == 1}">
            <small><span class="label" id="green">Dryer A3</span> <b>-</b> <span class="label" id="red">Dryer A4</span></small>
          </c:if>
          <c:if test="${requestScope.laundryRoom == 2}">
            <small><span class="label" id="green">Dryer B3</span> <b>-</b> <span class="label" id="red">Dryer B4</span></small>
          </c:if>
          <c:if test="${requestScope.laundryRoom == 3}">
            <small><span class="label" id="green">Laundrymachine C3</span> <b>-</b> <span class="label" id="red">Laundrymachine C4</span></small>
          </c:if>
        </div>

        <div class="table-responsive">
          <table id="dryers_table" class="table table-striped">
            <thead>
              <tr>
                <th id="timeHeader">Time</th>
                <th>Monday</th>
                <th>Tuesday</th>
                <th>Wednesday</th>
                <th>Thursday</th>
                <th>Friday</th>
                <th>Saturday</th>
                <th>Sunday</th>
              </tr>
            </thead>
            <tbody data-machineType="dry">

              <tr>
                <td><c:out value="${time[0]}"></c:out></td>
                <c:forEach var="i" begin="0" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-success">
                          <span class="coverProgressNumber">${huis_nummer3[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-danger">
                          <span class="coverProgressNumber">${huis_nummer4[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[1]}"></c:out></td>
                <c:forEach var="i" begin="1" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-success">
                          <span class="coverProgressNumber">${huis_nummer3[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-danger">
                          <span class="coverProgressNumber">${huis_nummer4[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[2]}"></c:out></td>
                <c:forEach var="i" begin="2" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-success">
                          <span class="coverProgressNumber">${huis_nummer3[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default">
                          <span class="coverProgressNumber">${huis_nummer4[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[3]}"></c:out></td>
                <c:forEach var="i" begin="3" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-success">
                          <span class="coverProgressNumber">${huis_nummer3[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-danger">
                          <span class="coverProgressNumber">${huis_nummer4[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[4]}"></c:out></td>
                <c:forEach var="i" begin="4" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-success">
                          <span class="coverProgressNumber">${huis_nummer3[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-danger">
                          <span class="coverProgressNumber">${huis_nummer4[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[5]}"></c:out></td>
                <c:forEach var="i" begin="5" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-success">
                          <span class="coverProgressNumber">${huis_nummer3[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-danger">
                          <span class="coverProgressNumber">${huis_nummer4[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[6]}"></c:out></td>
                <c:forEach var="i" begin="6" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-success">
                          <span class="coverProgressNumber">${huis_nummer3[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-danger">
                          <span class="coverProgressNumber">${huis_nummer4[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[7]}"></c:out></td>
                <c:forEach var="i" begin="7" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-success">
                          <span class="coverProgressNumber">${huis_nummer3[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-danger">
                          <span class="coverProgressNumber">${huis_nummer4[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[8]}"></c:out></td>
                <c:forEach var="i" begin="8" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-success">
                          <span class="coverProgressNumber">${huis_nummer3[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-danger">
                          <span class="coverProgressNumber">${huis_nummer4[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[9]}"></c:out></td>
                <c:forEach var="i" begin="9" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-success">
                          <span class="coverProgressNumber">${huis_nummer3[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-danger">
                          <span class="coverProgressNumber">${huis_nummer4[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[10]}"></c:out></td>
                <c:forEach var="i" begin="10" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-success">
                          <span class="coverProgressNumber">${huis_nummer3[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-danger">
                          <span class="coverProgressNumber">${huis_nummer4[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[11]}"></c:out></td>
                <c:forEach var="i" begin="11" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-success">
                          <span class="coverProgressNumber">${huis_nummer3[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer2[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-danger">
                          <span class="coverProgressNumber">${huis_nummer2[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer2[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>

              <tr>
                <td><c:out value="${time[12]}"></c:out></td>
                <c:forEach var="i" begin="12" end="90" step="13" varStatus="loop">
                  <td>
                    <div data-id="${i}" class="progress">
                      <c:if test='${not empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-success">
                          <span class="coverProgressNumber">${huis_nummer3[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer3[i]}'>
                        <div data-machine="3" class="progress-bar progress-bar-default"></div>
                      </c:if>

                      <c:if test='${not empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-danger">
                          <span class="coverProgressNumber">${huis_nummer4[i]}</span>
                        </div>
                      </c:if>
                      <c:if test='${empty huis_nummer4[i]}'>
                        <div data-machine="4" class="progress-bar progress-bar-default"></div>
                      </c:if>
                    </div>
                  </td>
                </c:forEach>
              </tr>
            </tbody>
          </table>
        </div>
   </c:if>
      </div>
    </div>
  </div>

  <!-- CHANGE USER ACCOUNT HUISNUMMER MODAL -->
  <div class="modal fade" id="modify_account_housenumber_modal" role="dialog" data-controls-modal="modify_account_housenumber_modal"
    data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="center_modal_top_close_button close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header form-signin-heading">Change Housenumber</h4>
        </div>
        <form name="modify_account_housenumber" class="form-signin" action="index.010" method="post">
          <div class="modal-body">
            <div class="row">
              <div class="col-xs-9 col-xs-offset-1">
                <input name="houseNumber" type="text" class="form-control settings_modal" placeholder="New Housenumber" required /> <span
                  class="required_asterisk shrink_glyph glyphicon glyphicon-question-sign" data-toggle="tooltip" data-placement="right"
                  title="You will need to log back in after this change."></span>
                <input name="to_servlet" value="changeHouseNumber"
                  style="display: none;" required>
                <input value="${requestScope.week}" name="week" type="text" style="display: none;"><input value="${requestScope.laundryRoom}" name="laundryRoom" type="number" style="display: none;">
                  
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-primary" type="submit">
              Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span>
            </button>
            <button class="btn btn-default" type="button" onclick="hideBlueBlackground()" data-dismiss="modal">
              Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- CHANGE USER PASSWORD MODAL -->
  <div class="modal fade" id="modify_account_password_modal" role="dialog" data-controls-modal="modify_account_password_modal" data-backdrop="static"
    data-keyboard="false">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="center_modal_top_close_button close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header form-signin-heading">Change Password</h4>
        </div>
        <form name="modify_account_password" class="form-signin" action="index.010" method="post">
          <div class="modal-body">
            <div class="row">
              <div class="col-xs-9 col-xs-offset-1">
                <input id="password" name="password" type="password" class="form-control settings_modal" placeholder="New Password" required /> <br>
                <input id="confirm_password" type="password" class="form-control" placeholder="Retype Password" pattern="" required title="Password not identical"/>
                <input name="to_servlet" value="changePassword" style="display: none;" required>
                <input value="${requestScope.week}" name="week" type="text" style="display: none;"><input value="${requestScope.laundryRoom}" name="laundryRoom" type="number" style="display: none;">
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-primary" type="submit">
              Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span>
            </button>
            <button class="btn btn-default" type="button" onclick="hideBlueBlackground()" data-dismiss="modal">
              Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span>
            </button>
          </div>
        </form>
      </div>
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
        <form name="delete_account" class="form-signin" action="index.010" method="post">
          <div class="modal-body">
            <div class="row">
              <div class="col-xs-9 col-xs-offset-1">
                <input name="password" type="password" class="form-control settings_modal" placeholder="Type password" required />
                <input name="to_servlet" value="deleteAccount" style="display: none;" required>
                <input value="${requestScope.week}" name="week" type="text" style="display: none;"><input value="${requestScope.laundryRoom}" name="laundryRoom" type="number" style="display: none;">
              </div>
            </div>
          </div>
          <div class="modal-footer">
            <button class="btn btn-primary" type="submit">
              Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span>
            </button>
            <button class="btn btn-default" type="button" onclick="hideBlueBlackground()" data-dismiss="modal">
              Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <!-- Place Appointment Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="center_modal_top_close_button close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header form-signin-heading">Insert Reservation</h4>
        </div>

        <form name="appointment_form" class="form-signin" action="index.010" method="post">
          <div class="modal-body inputSpread">
            <div class="row">
              <div id="remove_background_color_insert_date_modal" class="col-xs-4">
                <h5>Day:</h5>
              </div>
              <div class="col-xs-8 .col-xs-offset-4">

                <select name="day" class="form-control pull-right day_class">
                  <c:if test="${requestScope.week != 'next'}">
                    <c:forEach items="${date}" var="entry" varStatus="loop" begin="0" end="6">
                      <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                  </c:if>

                  <c:if test="${requestScope.week == 'next'}">
                    <c:forEach items="${date}" var="entry" varStatus="loop" begin="7" end="13">
                      <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                  </c:if>
                </select>

              </div>
            </div>

            <div class="row">
              <div id="remove_background_color_insert_date_modal" class="col-xs-4">
                <h5>Time:</h5>
              </div>
              <div class="col-xs-8 .col-xs-offset-4">

                <select name="time" class="form-control pull-right time_class">
                  <c:forEach items="${time}" var="entry">
                    <option value="${entry.key}">${entry.value}</option>
                  </c:forEach>
                </select>

              </div>
            </div>

            <div class="row">
              <div id="remove_background_color_insert_date_modal" class="col-xs-4">
                <h5>Machine:</h5>
              </div>
              <div class="col-xs-8 .col-xs-offset-4">

                <select name="machine" class="form-control machine_class">
                  <c:if test="${requestScope.laundryRoom != 2 && requestScope.laundryRoom != 3}">
                    <c:forEach items="${wasmachine}" var="entry" varStatus="loop" begin="0" end="3">
                      <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                  </c:if>
                  <c:if test="${requestScope.laundryRoom == 2}">
                    <c:forEach items="${wasmachine}" var="entry" varStatus="loop" begin="4" end="7">
                      <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                  </c:if>
                  <c:if test="${requestScope.laundryRoom == 3}">
                    <c:forEach items="${wasmachine}" var="entry" varStatus="loop" begin="8" end="11">
                      <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                  </c:if>
                </select>

              </div>
            </div>

            <div class="row">
              <div id="remove_background_color_insert_date_modal" class="col-xs-4">
                <h5>Number:</h5>
              </div>
              <div class="col-xs-8 .col-xs-offset-4">
                <input value="${sessionScope.user.houseNumber}" name="huisnummer" type="text" class="form-control pull-right readonly"
                  placeholder="Not Logged In!" readonly>
              </div>
            </div>
            
            <div class="row">
              <div id="remove_background_color_insert_date_modal" class="col-xs-4">
                <h5>Remaining:</h5>
              </div>
              <div class="col-xs-8 .col-xs-offset-4">
              <input id="remaining" value="" name="wash_counter" type="text" class="form-control pull-right readonly"
                  placeholder="Not Logged In!" readonly>
              </div>
            </div>

            <input value="${sessionScope.user.id}" name="id" type="number" style="display: none;" required> <input name="to_servlet"
              value="addAppointment" style="display: none;">
              <input class="machineType" value="" name="machinetype" type="text" style="display: none;">
            <input value="${requestScope.week}" name="week" type="text" style="display: none;"><input value="${requestScope.laundryRoom}" name="laundryRoom" type="number" style="display: none;">
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">
              Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span>
            </button>
            <button type="button" class="btn btn-default" data-dismiss="modal">
              Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span>
            </button>
          </div>
        </form>

      </div>
    </div>
  </div>



  <div class="modal fade" id="delete_appointment_modal" role="dialog" data-controls-modal="delete_appointment_modal" data-backdrop="static"
    data-keyboard="false">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header" class="form-signin-heading">Remove Reservation</h4>
        </div>

        <form name="delete_appointment_form" class="form-signin" action="index.010" method="post">

          <div class="modal-body inputSpread">
            <div class="row">
              <div id="remove_background_color_insert_date_modal" class="col-xs-4">
                <h5>Day:</h5>
              </div>
              <div class="col-xs-8 .col-xs-offset-4">

                <select name="day" class="form-control pull-right day_class">
                  <c:if test="${requestScope.week != 'next'}">
                    <c:forEach items="${date}" var="entry" varStatus="loop" begin="0" end="6">
                      <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                  </c:if>

                  <c:if test="${requestScope.week == 'next'}">
                    <c:forEach items="${date}" var="entry" varStatus="loop" begin="7" end="13">
                      <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                  </c:if>
                </select>

              </div>
            </div>

            <div class="row">
              <div id="remove_background_color_insert_date_modal" class="col-xs-4">
                <h5>Time:</h5>
              </div>
              <div class="col-xs-8 .col-xs-offset-4">

                <select name="time" class="time_class form-control pull-right">
                  <c:forEach items="${time}" var="entry">
                    <option value="${entry.key}">${entry.value}</option>
                  </c:forEach>
                </select>

              </div>
            </div>

            <div class="row">
              <div id="remove_background_color_insert_date_modal" class="col-xs-4">
                <h5>Machine:</h5>
              </div>
              <div class="col-xs-8 .col-xs-offset-4">

                <select name="machine" class="machine_class form-control">
                  <c:if test="${requestScope.laundryRoom == 1}">
                    <c:forEach items="${wasmachine}" var="entry" varStatus="loop" begin="0" end="3">
                      <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                  </c:if>
                  <c:if test="${requestScope.laundryRoom == 2}">
                    <c:forEach items="${wasmachine}" var="entry" varStatus="loop" begin="4" end="7">
                      <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                  </c:if>
                  <c:if test="${requestScope.laundryRoom == 3}">
                    <c:forEach items="${wasmachine}" var="entry" varStatus="loop" begin="8" end="11">
                      <option value="${entry.key}">${entry.value}</option>
                    </c:forEach>
                  </c:if>

                </select>

              </div>
            </div>

            <div class="row">
              <div id="remove_background_color_insert_date_modal" class="col-xs-4">
                <h5>Number:</h5>
              </div>
              <div class="col-xs-8 .col-xs-offset-4">
                <input value="${sessionScope.user.houseNumber}" name="huisnummer" type="text" class="form-control pull-right readonly"
                  placeholder="Not Logged In!" readonly>
              </div>
            </div>

            <input value="${sessionScope.user.id}" name="id" type="number" style="display: none;" required> <input name="to_servlet"
              value="removeAppointment" style="display: none;">
              
            <input class="machineType" name="machinetype" type="text" style="display: none;">
            <input value="${requestScope.week}" name="week" type="text" style="display: none;"><input value="${requestScope.laundryRoom}" name="laundryRoom" type="number" style="display: none;">

          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">
              Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span>
            </button>
            <button type="button" class="btn btn-default" data-dismiss="modal">
              Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span>
            </button>
          </div>
        </form>

      </div>
    </div>
  </div>


  <div class="modal fade" id="delete_message_modal" role="dialog" data-controls-modal="delete_message_modal" data-backdrop="static"
    data-keyboard="false">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header" class="form-signin-heading">Remove Message</h4>
        </div>
        <form name="delete_message_form" class="form-prikbord" action="index.010" method="post">
          <div class="modal-body">
            <div class="row">
              <div class="col-xs-12">
                <h5>
                  Are you sure you want to remove your message with title: <br> <b><span id="messageTitle"></span></b>
                </h5>
                <input value="${sessionScope.user.id}" name="userId" type="number" style="display: none;" required>
                <input id="messageId" name="id" value="" style="display: none;" required>
              </div>
            </div>
          </div>
          <input name="to_servlet" value="removeMessage" style="display: none;">
          <input value="${requestScope.week}" name="week" type="text" style="display: none;"><input value="${requestScope.laundryRoom}" name="laundryRoom" type="number" style="display: none;">
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">
              Delete <span class="shrink_glyph glyphicon glyphicon-ok"></span>
            </button>
            <button type="button" class="btn btn-default" data-dismiss="modal">
              Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span>
            </button>
          </div>
        </form>
      </div>
    </div>
  </div>


  <div class="modal fade" id="prikbord_modal" role="dialog" aria-labelledby="myModalLabel" data-controls-modal="prikbord_modal" data-backdrop="static"
    data-keyboard="false">
    <div class="modal-dialog">
      <div class="modal-content">

        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header" class="form-signin-heading">Create Message</h4>
        </div>

        <form name="prikbord_form" class="form-prikbord" action="index.010" method="post">

          <div class="modal-body inputSpread">
            <div class="row">
              <div class="col-xs-2">
                <h5>Title:</h5>
              </div>
              <div class="col-xs-10 .col-xs-offset-2">
                <input type="text" name="titleInput" class="form-control pull-right">
              </div>
            </div>

            <br />

            <div class="row">
              <div class="col-xs-2">
                <h5>Inhoud:</h5>
              </div>
              <div class="col-xs-10 .col-xs-offset-2">
                <textarea name="bodyInput" rows="10" cols="80" id="MyID"></textarea>
              </div>
            </div>

            <div class="row">
              <div class="col-xs-2">
                <h5>Sender:</h5>
              </div>
              <div class="col-xs-10 .col-xs-offset-2">
                <input value="${sessionScope.user.email}" id="space_for_span_prikbord_input" name="userEmail" type="text"
                  class="form-control pull-right readonly" placeholder="Not Logged In!" readonly> <span
                  class="shrink_glyph glyphicon glyphicon-question-sign" data-toggle="tooltip" data-placement="left"
                  title="Your message has to be approved before it will be visible!"></span>
              </div>
            </div>

            <input value="${sessionScope.user.id}" name="userId" type="number" style="display: none;" required> <input name="to_servlet"
              value="createMessage" style="display: none;">
            <input value="${requestScope.week}" name="week" type="text" style="display: none;"><input value="${requestScope.laundryRoom}" name="laundryRoom" type="number" style="display: none;">

          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary">
              Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span>
            </button>
            <button type="button" class="btn btn-default" data-dismiss="modal">
              Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span>
            </button>
          </div>
        </form>

      </div>
    </div>
  </div>



  <div id="blue_background" style="display: none;"></div>

  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>
  <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

  <script>
			$("[data-toggle=popover]").popover();

			$("#delete_appointment_modal").draggable({
				handle : ".modal-content"
			});

			$("#myModal").draggable({
				handle : ".modal-content"
			});

			$(document).ready(function() {
				$("#signup_button").click(function() {
					$("#blue_background").css("display", "initial");
				});
			});

			function hideBlueBlackground() {
				$("#blue_background").css("display", "none");
			};

			$(function() {
				$('[data-toggle="tooltip"]').tooltip()
			});
		</script>

<script>
$(function() {
	var getUrlParameter = function getUrlParameter(sParam) {
	    var sPageURL = decodeURIComponent(window.location.search.substring(1)),
	        sURLVariables = sPageURL.split('&'),
	        sParameterName,
	        i;
	    for (i = 0; i < sURLVariables.length; i++) {
	        sParameterName = sURLVariables[i].split('=');
	        if (sParameterName[0] === sParam) {
	            return sParameterName[1] === undefined ? true : sParameterName[1];
	        }
	    }
	};

  // toggle alert message
  var hasMessage = getUrlParameter("message");
  if (hasMessage) {
	  $('#errorMessage').text(hasMessage);
    $('#alert_div').animate({
      height : "toggle",
       opacity : "toggle"
     }, "slow").delay(10000);
     $('#alert_div').animate({
       height : "toggle",
       opacity : "toggle"
     }, "slow");
   }
   $('.close').click(function() {
     $('#alert_div').animate({
       height : "toggle",
       opacity : "toggle"
     }, "slow");
   });
});
</script>

  <script>
			$(".go_to_dryers").click(function() {
				$('html, body').animate({
					scrollTop : $("#dryers_table").offset().top
				}, 'slow');
			});
		</script>

  <!-- Script for prikbord -->
  <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
  <script>
			var simplemde = new SimpleMDE(
					{
						element : document.getElementById("MyID"),
						placeholder : "NOTE: Write <br> instead of pressing enter to go to the next line!",
						spellChecker : false,
						status : false,
						styleSelectedText : false,
						showIcons : [ "bold", "italic", "strikethrough",
								"quote", "unordered-list", "ordered-list" ],
						hideIcons : [ "heading", "image", "side-by-side",
								"fullscreen", "guide", "horizontal-rule" ]
					});
		</script>



<script>
  $(document).ready(function() {
    var dbTimesArray = new Array();
    <c:forEach items="${time}" var="entry">
    dbTimesArray.push("${entry.value}");
    </c:forEach>

    var now = new Date();
    var now_DayIndex = now.getDay() - 1;
    if (now_DayIndex == -1) { // if (Day of the week == Sunday)
      now_DayIndex = 6;
    }

    var nowPlus5Min = new Date(now.getTime() + (5 * 60000));
    var nowTimePlus5Min = nowPlus5Min.getHours() + ':' + nowPlus5Min.getMinutes() + ":" + nowPlus5Min.getSeconds();

    var nowPlus30Min = new Date(now.getTime() + (30 * 60000));
    var nowTimePlus30Min = nowPlus30Min.getHours() + ':' + nowPlus30Min.getMinutes() + ":" + nowPlus30Min.getSeconds();
    
    var thisWeek = ${requestScope.week != 'next'};

    var yourHousenumber = '${sessionScope.user.houseNumber}';
    
    var placeHoverEffectClass = function() {
        if (thisWeek) {
          var i = $(this).parent().closest('div').data('id');
          var day = Math.floor(i / 13);
          var timeIndex = i % 13;
          var nowOrWithin5min_Time = Date.parse("1/1/1970 " + dbTimesArray[timeIndex]) >= Date.parse("1/1/1970 " + nowTimePlus5Min);
          var nowOrWithin30min_Time = Date.parse("1/1/1970 " + dbTimesArray[timeIndex]) >= Date.parse("1/1/1970 " + nowTimePlus30Min);

          if (!$(this).children('span').length) { // Span doesnt exists (no appointment there)
              if (day > now_DayIndex || (day == now_DayIndex && nowOrWithin5min_Time)) { // Future Date OR (day == today && passed current Time)
                $(this).addClass('hoverEffect');
                $(this).attr('data-toggle', 'modal');
                $(this).attr('data-target', '#myModal');
              } else {
            	  $(this).addClass('passed_date');
              }
            } else if ($(this).children('span').text() == yourHousenumber) { // Span exists and has your housenumber
              if (day > now_DayIndex || (day == now_DayIndex && nowOrWithin30min_Time)) { // Future Date OR (day == today && passed current Time)
          	    $(this).append('<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>');
              }
            }
        } else {
          if (!$(this).children('span').length) { // Span doesnt exists (no appointment there)
            $(this).addClass('hoverEffect');
            $(this).attr('data-toggle', 'modal');
            $(this).attr('data-target', '#myModal');
          } else if ($(this).children('span').text() == yourHousenumber) { // Span exists and has your housenumber
              $(this).append('<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>');
            }
          }
        }

	  $("#laundry_machines_table div div").each(placeHoverEffectClass);
	  $("#dryers_table div div").each(placeHoverEffectClass);
  });

  var placeValues = function() {
	    var machineType = $(this).parents("tbody").data('machinetype');
	    
	    var week = 'next';
	    <c:if test="${requestScope.week != 'next'}">
	      week = 'current';
	    </c:if>
	    var userWashesMap = ${sessionScope.wash_counter};
	    

      var i = $(this).parent().closest('div').data('id');
      var machine = $(this).data('machine');
      var day = Math.floor(i / 13);
      var time = (i % 13);
      
      var thisWeek = ${requestScope.week != 'next'};
      var laundryRoom = ${requestScope.laundryRoom};
      
      if (laundryRoom == 2) {
    	  machine += 4;
      } else if (laundryRoom == 3) {
    	  machine += 8;
      }

      if (!thisWeek) {
    	  day += 7;
      }

      $(".time_class").val(time);
      $(".day_class").val(day + 1);
      $(".machine_class").val(machine);
      $(".machineType").val(machineType);
      
      $("#remaining").val(3 - userWashesMap[week + machineType]);
    }

  $("#laundry_machines_table div div").click(placeValues);
  $("#dryers_table div div").click(placeValues);

  <!-- Save id for delete message modal -->
  $("#save_message_id").click(function() {
        var myMessageId = $(this).data('id');
        $("#messageId").val(myMessageId);

        var myMessageTitle = $(this).data('title');
        document.getElementById("messageTitle").innerHTML = myMessageTitle;
  });
  
  $('#password').focusout(function() {
	  var pass = $('#password').val();
	  $('#confirm_password').attr("pattern", pass);
  });
</script>
</body>
</html>
