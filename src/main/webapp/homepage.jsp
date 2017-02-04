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
  <!-- Stylesheet for prikbord message modal -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css" />
</head>

<body>
  <nav class="navbar navbar-default navbar-fixed-top">
    <div class="container-fluid">
      <div class="navbar-header">
        <a class="navbar-brand mainTitle" href="${pageContext.request.contextPath}"><img id="menu_img" src="${pageContext.request.contextPath}/resources/img/parkhaven-logo.png"/><span id="menu_span">Wasschema</span></a>
      </div>

      <div class="navbar-form navbar-right">
        <c:if test="${empty sessionScope.user.email}">
	      <form id="login_form" name="login_form" action="" method="post">
            <div class="form-group">
              <input name="email" type="email" class="form-control" placeholder="Email" required/>
              <input name="password" type="password" class="form-control" placeholder="Password" required/>
            </div>
            <input name="to_servlet" value="loginForm" style="display: none;"/>
            <button type="submit" class="btn btn-default">Log in</button>
          </form>

          <button id="signup_button" data-toggle="modal" data-target="#signup_modal" class="btn btn-default">Sign up</button>
        </c:if>

        <c:if test="${not empty sessionScope.user.email}">
          <div id="login_information">Logged in as <c:out value="${sessionScope.user.email}"/></div>
			<c:if test="${sessionScope.user.admin}">
			  <form id="adminForm" name="adminButton" class="form-signin" action="admin.010" method="post">
				<button type="submit" class="btn btn-succes" >Admin page</button>	
			  </form>
			</c:if>
   		    <form id="logoutForm" name="logoutButton" class="form-signin" action="" method="post">
              <button type="submit" class="btn btn-default" >Log Out</button>
			</form>
		</c:if>
      </div>
    
    </div>
  </nav>


  <div class="container-fluid">
    <div class="row">
      <div class="col-xs-2 sidebar">
        <ul class="nav nav-sidebar">
          <li class="active dashBoard_margin"><a class="dashBoard_boldness">Dashboard <span class="shrink_glyph glyphicon glyphicon-home"></span><span class="sr-only">(current)</span></a></li>

          <li>
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">

           	  <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingFour">
                  <h4 class="panel-title">
                  <c:if test="${empty sessionScope.user.email}">
                    <a id="left_menu_header" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="true" aria-controls="collapseFour">
                      Settings<span class="dashboard_glyph shrink_glyph glyphicon glyphicon-cog"></span>
                    </a>
                  </c:if>
   		          <c:if test="${not empty sessionScope.user.email}">
                    <a id="left_menu_header" class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                      Settings<span class="dashboard_glyph shrink_glyph glyphicon glyphicon-cog"></span>
                    </a>   		          
                  </c:if>
                  </h4>
                </div>

              <c:if test="${empty sessionScope.user.email}">
                <div id="collapseFour" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingFour">
                  <div class="panel-body">
				    <span>- <a class="settings_link" data-toggle="modal" data-target="#forgot_password_modal">Forgot password.</a></span><br>
			      </div>
                </div>
              </c:if>
			  <c:if test="${not empty sessionScope.user.email}">
			    <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                  <div class="panel-body">
					<span>- <a class="settings_link" data-toggle="modal" data-target="#modify_account_housenumber_modal">Change housenumber.</a></span><br>
					<span>- <a class="settings_link" data-toggle="modal" data-target="#modify_account_password_modal">Change password.</a></span><br>
  					<span>- <a class="settings_link" data-toggle="modal" data-target="#delete_account_modal">Delete account.</a></span>		
 				  </div>
                </div>		  
			  </c:if>
           	</div>

              <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingThree">
                  <h4 class="panel-title">
                    <a id="left_menu_header" class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                      Info<span class="dashboard_glyph shrink_glyph glyphicon glyphicon-info-sign"></span>
                    </a>
                  </h4>
                </div>
                <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                  <div class="panel-body">
					There is a limit of three washes per week.
			        <c:if test="${not empty sessionScope.user.email}">
              	      <br>
              	      Washes placed this week: ${sessionScope.was_counter[0]}
               	      <br>
               	      Washes placed next week: ${sessionScope.was_counter[1]}
					</c:if>

					<hr> 
                	Total page hits: ${requestScope.hitcounter}
                	<br>
					Total washes placed: ${requestScope.totalwashcounter}
                   </div>
                 </div>
               </div>
             </div>
             
            <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
               <div class="panel panel-default">
                 <div class="panel-heading" role="tab" id="headingOne">
                   <h4 class="panel-title">
                     <img src="${pageContext.request.contextPath}/resources/img/prikbord.png" data-toggle="modal" data-target="#prikbord_modal" id="prikbord_header"/>
                   </h4>
                 </div>
                 <div id="collapseOne" class="panel-collapse collapse in">
                   <div id="prikbord_body" class="panel-body">
                     
               	     <c:forEach var="message" items="${prikbord_messages}">
               	     	<h4 class="prikbord_message panel-title">${message.value.titleOutput}</h4>
               	     	${message.value.bodyOutput}
               	     	<c:out value="${message.value.userEmail}"></c:out>
               	     	
               	     	<c:if test="${message.value.userId == sessionScope.user.id || sessionScope.user.admin}">
                            <button id="save_message_id" data-title="${message.value.titleOutput}" data-id="${message.key}" type="button" data-toggle="modal" data-target="#delete_message_modal" class="btn btn-default btn-xs">Remove message</button>
               	     	</c:if>
               	     	
               	     	<hr class="prikbord_message"/>
               	     </c:forEach>
                   </div>
                 </div>
               </div>
               </div>
           </li>
         </ul>

         <button class="btn btn-primary btn-md" data-toggle="modal" data-target="#myModal">Datum Invoeren <span class="shrink_glyph glyphicon glyphicon-edit"></span></button>
       </div>


     <div class="col-xs-10 col-xs-offset-2 main">
	   		<nav aria-label="week_pagination" id="week">
				<ul class="pager">
					<c:if test="${requestScope.week != 'next'}">
						<li class="disabled label"><a id="active_week">Current Week</a></li>
						<li class="label"><a id="available_week" href="?week=next&wasruimte=${requestScope.wasruimte}">Next Week <span class="shrink_glyph glyphicon glyphicon-arrow-right"></span></a></li>
					</c:if>
					<c:if test="${requestScope.week == 'next'}">
						<li class="label"><a id="available_week" href="?week=current&wasruimte=${requestScope.wasruimte}"><span class="shrink_glyph glyphicon glyphicon-arrow-left"></span> Current Week</a></li>
						<li class="label disabled"><a id="active_week">Next Week</a></li>
					</c:if>
				</ul>
			</nav>


    	<div id="wasruimte_all">
  		<nav aria-label="week_pagination" id="">
			<ul class="pager">
				<c:if test="${requestScope.wasruimte != 'that'}">
					<li class="disabled label"><a id="active_week">Wasruimte #351</a></li>
					<li class="label"><a id="available_week" href="?week=${requestScope.week}&wasruimte=that">Wasruimte #115</a></li>
				</c:if>
				<c:if test="${requestScope.wasruimte == 'that'}">
					<li class="label"><a id="available_week" href="?week=${requestScope.week}&wasruimte=this">Wasruimte #351</a></li>
					<li class="disabled label"><a id="active_week">Wasruimte #115</a></li>
				</c:if>
			</ul>
		</nav>
		
   			<nav aria-label="week_pagination" 
   						<c:if test="${requestScope.wasruimte != 'that'}">id="under_wasruimte"</c:if>
   						<c:if test="${requestScope.wasruimte == 'that'}">id="under_other_wasruimte"</c:if>
   			>	
				<ul class="pager">
					<li class="label"><a class="go_to_drogers" id="available_week">Drogers <span class="shrink_glyph glyphicon glyphicon-arrow-down"></span></a></li>
				</ul>
			</nav>
	</div>
	
	
       	 <div id="overview">
           <h2 class="page-header">Overview <small>/ Week ${get_overview}</small></h2>
         </div>

         <div class="table-responsive">
           <table class="table table-striped">
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
             <tbody>
             
               <tr>
               	 <td><c:out value="${time[0]}"></c:out></td>               
                 <c:forEach var="i" begin="0" end="90" step="13" varStatus="loop">
                 
                 	<td>
	                  <div class="progress">
	                  	<c:if test='${not empty huis_nummer1[i]}'>
							<div class="progress-bar progress-bar-info" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer1[i]}</span>
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer1[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-warning" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-warning" style="width:0%">
						  	</div>
						  </c:if>
					  </div>
                   	</td>
                 </c:forEach>
               </tr>

               <tr>
               	 <td><c:out value="${time[1]}"></c:out></td>               
                 <c:forEach var="i" begin="1" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer1[i]}'>
							<div class="progress-bar progress-bar-info" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer1[i]}</span>
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer1[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-warning" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-warning" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

               <tr>
               	 <td><c:out value="${time[2]}"></c:out></td>               
                 <c:forEach var="i" begin="2" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer1[i]}'>
							<div class="progress-bar progress-bar-info" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer1[i]}</span>
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer1[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-warning" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-warning" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

               <tr>
               	 <td><c:out value="${time[3]}"></c:out></td>               
                 <c:forEach var="i" begin="3" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer1[i]}'>
							<div class="progress-bar progress-bar-info" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer1[i]}</span>
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer1[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-warning" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-warning" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

               <tr>
               	 <td><c:out value="${time[4]}"></c:out></td>               
                 <c:forEach var="i" begin="4" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer1[i]}'>
							<div class="progress-bar progress-bar-info" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer1[i]}</span>
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer1[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-warning" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-warning" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>
               
               <tr>
               	 <td><c:out value="${time[5]}"></c:out></td>               
                 <c:forEach var="i" begin="5" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer1[i]}'>
							<div class="progress-bar progress-bar-info" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer1[i]}</span>
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer1[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-warning" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-warning" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

               <tr>
               	 <td><c:out value="${time[6]}"></c:out></td>               
                 <c:forEach var="i" begin="6" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer1[i]}'>
							<div class="progress-bar progress-bar-info" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer1[i]}</span>
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer1[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-warning" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-warning" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

                <tr>
               	 <td><c:out value="${time[7]}"></c:out></td>               
                 <c:forEach var="i" begin="7" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer1[i]}'>
							<div class="progress-bar progress-bar-info" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer1[i]}</span>
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer1[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-warning" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-warning" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

                <tr>
               	 <td><c:out value="${time[8]}"></c:out></td>               
                 <c:forEach var="i" begin="8" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer1[i]}'>
							<div class="progress-bar progress-bar-info" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer1[i]}</span>
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer1[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-warning" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-warning" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

                <tr>
               	 <td><c:out value="${time[9]}"></c:out></td>               
                 <c:forEach var="i" begin="9" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer1[i]}'>
							<div class="progress-bar progress-bar-info" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer1[i]}</span>
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer1[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-warning" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-warning" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>
               
               <tr>
               	 <td><c:out value="${time[10]}"></c:out></td>               
                 <c:forEach var="i" begin="10" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer1[i]}'>
							<div class="progress-bar progress-bar-info" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer1[i]}</span>
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer1[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-warning" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-warning" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>
               
               <tr>
               	 <td><c:out value="${time[11]}"></c:out></td>               
                 <c:forEach var="i" begin="11" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer1[i]}'>
							<div class="progress-bar progress-bar-info" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer1[i]}</span>
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer1[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-warning" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-warning" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>
               
                <tr>
               	 <td><c:out value="${time[12]}"></c:out></td>               
                 <c:forEach var="i" begin="12" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer1[i]}'>
							<div class="progress-bar progress-bar-info" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer1[i]}</span>
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer1[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-warning" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-warning" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>
               
             </tbody>
           </table>

<div class="legend">
		<c:if test="${requestScope.wasruimte != 'that'}">
			<small><span class="label" id="blue">Wasmachine C1</span> <b>-</b> <span class="label" id="yellow">Wasmachine C2</span> <b>-</b> <span class="label" id="green">Droger D1</span> <b>-</b> <span class="label" id="red">Droger D2</span></small>
		</c:if>
		<c:if test="${requestScope.wasruimte == 'that'}">
			<small><span class="label" id="blue">Wasmachine C3</span> <b>-</b> <span class="label" id="yellow">Wasmachine C4</span> <b>-</b> <span class="label" id="green">Droger D3</span> <b>-</b> <span class="label" id="red">Droger D4</span></small>
		</c:if>
</div>
		</div>

         <div class="table-responsive">
           <table id="drogers" class="table table-striped">
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
             <tbody>
             
               <tr>
               	 <td><c:out value="${time[0]}"></c:out></td>               
                 <c:forEach var="i" begin="0" end="90" step="13" varStatus="loop">
                 
                 	<td>
	                  <div class="progress">
	                  	<c:if test='${not empty huis_nummer3[i]}'>
							<div class="progress-bar progress-bar-success" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer3[i]}</span>
							  <c:if test="${huis_nummer3[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer3[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer4[i]}'>
	 					  	<div class="progress-bar progress-bar-danger" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer4[i]}</span>
	 					  		<c:if test="${huis_nummer4[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer4[i]}'>
						  	<div class="progress-bar progress-bar-danger" style="width:0%">
						  	</div>
						  </c:if>
					  </div>
                   	</td>
                 </c:forEach>
               </tr>

               <tr>
               	 <td><c:out value="${time[1]}"></c:out></td>               
                 <c:forEach var="i" begin="1" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer3[i]}'>
							<div class="progress-bar progress-bar-success" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer3[i]}</span>
							  <c:if test="${huis_nummer3[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer3[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer4[i]}'>
	 					  	<div class="progress-bar progress-bar-danger" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer4[i]}</span>
	 					  		<c:if test="${huis_nummer4[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer4[i]}'>
						  	<div class="progress-bar progress-bar-danger" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

               <tr>
               	 <td><c:out value="${time[2]}"></c:out></td>               
                 <c:forEach var="i" begin="2" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer3[i]}'>
							<div class="progress-bar progress-bar-success" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer3[i]}</span>
							  <c:if test="${huis_nummer3[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer3[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer4[i]}'>
	 					  	<div class="progress-bar progress-bar-danger" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer4[i]}</span>
	 					  		<c:if test="${huis_nummer4[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer4[i]}'>
						  	<div class="progress-bar progress-bar-danger" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

               <tr>
               	 <td><c:out value="${time[3]}"></c:out></td>               
                 <c:forEach var="i" begin="3" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer3[i]}'>
							<div class="progress-bar progress-bar-success" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer3[i]}</span>
							  <c:if test="${huis_nummer3[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer3[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer4[i]}'>
	 					  	<div class="progress-bar progress-bar-danger" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer4[i]}</span>
	 					  		<c:if test="${huis_nummer4[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer4[i]}'>
						  	<div class="progress-bar progress-bar-danger" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

               <tr>
               	 <td><c:out value="${time[4]}"></c:out></td>               
                 <c:forEach var="i" begin="4" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer3[i]}'>
							<div class="progress-bar progress-bar-success" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer3[i]}</span>
							  <c:if test="${huis_nummer3[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer3[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer4[i]}'>
	 					  	<div class="progress-bar progress-bar-danger" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer4[i]}</span>
	 					  		<c:if test="${huis_nummer4[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer4[i]}'>
						  	<div class="progress-bar progress-bar-danger" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>
               
               <tr>
               	 <td><c:out value="${time[5]}"></c:out></td>               
                 <c:forEach var="i" begin="5" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer3[i]}'>
							<div class="progress-bar progress-bar-success" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer3[i]}</span>
							  <c:if test="${huis_nummer3[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer3[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer4[i]}'>
	 					  	<div class="progress-bar progress-bar-danger" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer4[i]}</span>
	 					  		<c:if test="${huis_nummer4[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer4[i]}'>
						  	<div class="progress-bar progress-bar-danger" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

               <tr>
               	 <td><c:out value="${time[6]}"></c:out></td>               
                 <c:forEach var="i" begin="6" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer3[i]}'>
							<div class="progress-bar progress-bar-success" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer3[i]}</span>
							  <c:if test="${huis_nummer3[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer3[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer4[i]}'>
	 					  	<div class="progress-bar progress-bar-danger" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer4[i]}</span>
	 					  		<c:if test="${huis_nummer4[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer4[i]}'>
						  	<div class="progress-bar progress-bar-danger" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

                <tr>
               	 <td><c:out value="${time[7]}"></c:out></td>               
                 <c:forEach var="i" begin="7" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer3[i]}'>
							<div class="progress-bar progress-bar-success" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer3[i]}</span>
							  <c:if test="${huis_nummer3[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer3[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer4[i]}'>
	 					  	<div class="progress-bar progress-bar-danger" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer4[i]}</span>
	 					  		<c:if test="${huis_nummer4[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer4[i]}'>
						  	<div class="progress-bar progress-bar-danger" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

                <tr>
               	 <td><c:out value="${time[8]}"></c:out></td>               
                 <c:forEach var="i" begin="8" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer3[i]}'>
							<div class="progress-bar progress-bar-success" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer3[i]}</span>
							  <c:if test="${huis_nummer3[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer3[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer4[i]}'>
	 					  	<div class="progress-bar progress-bar-danger" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer4[i]}</span>
	 					  		<c:if test="${huis_nummer4[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer4[i]}'>
						  	<div class="progress-bar progress-bar-danger" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>

                <tr>
               	 <td><c:out value="${time[9]}"></c:out></td>               
                 <c:forEach var="i" begin="9" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer3[i]}'>
							<div class="progress-bar progress-bar-success" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer3[i]}</span>
							  <c:if test="${huis_nummer3[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer3[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer4[i]}'>
	 					  	<div class="progress-bar progress-bar-danger" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer4[i]}</span>
	 					  		<c:if test="${huis_nummer4[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer4[i]}'>
						  	<div class="progress-bar progress-bar-danger" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>
               
               <tr>
               	 <td><c:out value="${time[10]}"></c:out></td>               
                 <c:forEach var="i" begin="10" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer3[i]}'>
							<div class="progress-bar progress-bar-success" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer3[i]}</span>
							  <c:if test="${huis_nummer3[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer3[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer4[i]}'>
	 					  	<div class="progress-bar progress-bar-danger" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer4[i]}</span>
	 					  		<c:if test="${huis_nummer4[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer4[i]}'>
						  	<div class="progress-bar progress-bar-danger" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>
               
               <tr>
               	 <td><c:out value="${time[11]}"></c:out></td>               
                 <c:forEach var="i" begin="11" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer3[i]}'>
							<div class="progress-bar progress-bar-success" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer3[i]}</span>
							  <c:if test="${huis_nummer3[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer3[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer2[i]}'>
	 					  	<div class="progress-bar progress-bar-danger" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer2[i]}</span>
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer2[i]}'>
						  	<div class="progress-bar progress-bar-danger" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>
               
                <tr>
               	 <td><c:out value="${time[12]}"></c:out></td>               
                 <c:forEach var="i" begin="12" end="90" step="13" varStatus="loop">
                 	<td>
	                  <div class="progress">             	
	                  	<c:if test='${not empty huis_nummer3[i]}'>
							<div class="progress-bar progress-bar-success" style="width:50%">
							  <span class="coverProgressNumber">${huis_nummer3[i]}</span>
							  <c:if test="${huis_nummer3[i] eq sessionScope.user.houseNumber}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  </c:if>
		 					</div>
						</c:if>
    				    <c:if test='${empty huis_nummer3[i]}'>
    				    	<div class="progress-bar progress-bar-default" style="width:50%">
		 					</div>
    				    </c:if>

	 					  <c:if test='${not empty huis_nummer4[i]}'>
	 					  	<div class="progress-bar progress-bar-danger" style="width:50%">
	 					  		<span class="coverProgressNumber">${huis_nummer4[i]}</span>
	 					  		<c:if test="${huis_nummer4[i] eq sessionScope.user.houseNumber}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="shrink_glyph remove_appointment glyphicon glyphicon-trash"></button>
							  	</c:if>
	 					  	</div>
	 					  </c:if>
						  <c:if test='${empty huis_nummer4[i]}'>
						  	<div class="progress-bar progress-bar-danger" style="width:0%">
						  	</div>
						  </c:if>
					   </div>
                   	</td>
                 </c:forEach>
               <tr>
               
             </tbody>
           </table>

<div class="legend">
		<c:if test="${requestScope.wasruimte != 'that'}">
			<small><span class="label" id="blue">Wasmachine C1</span> <b>-</b> <span class="label" id="yellow">Wasmachine C2</span> <b>-</b> <span class="label" id="green">Droger D1</span> <b>-</b> <span class="label" id="red">Droger D2</span></small>
		</c:if>
		<c:if test="${requestScope.wasruimte == 'that'}">
			<small><span class="label" id="blue">Wasmachine C3</span> <b>-</b> <span class="label" id="yellow">Wasmachine C4</span> <b>-</b> <span class="label" id="green">Droger D3</span> <b>-</b> <span class="label" id="red">Droger D4</span></small>
		</c:if>
</div>
        </div>
      </div>
    </div>
  </div>

  <!-- Signup Modal -->
  <div class="modal fade" id="signup_modal" role="dialog" data-controls-modal="signup_modal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
      <div class="modal-content" id="register_form">

        <div class="modal-header">
          <button onclick="hideBlueBlackground()" type="button" class="center_modal_top_close_button close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header form-signin-heading">Registreren</h4>
        </div>

        <form name="index" class="form-signin" action="" method="post">
          <div class="modal-body">
            <div class="row">
	  	      <div class="col-xs-9 col-xs-offset-1">
				<input name="email" type="email" class="form-control" placeholder="Email Address" required autofocus/><span class="required_asterisk shrink_glyph glyphicon glyphicon-asterisk"></span>
				<input name="firstname" type="text" class="non_required_input form-control" placeholder="First Name"/>
				<input name="lastname" type="text" class="non_required_input form-control" placeholder="Last Name"/>
				<input name="password" type="password" class="form-control" placeholder="Password" required/><span class="required_asterisk shrink_glyph glyphicon glyphicon-asterisk"></span>
				<input type="password" class="form-control" placeholder="Repeat Password" required/><span class="required_asterisk shrink_glyph glyphicon glyphicon-asterisk"></span>
				<input name="huisnummer" type="text" class="form-control" placeholder="House Number" required/><span class="required_asterisk shrink_glyph glyphicon glyphicon-asterisk"></span>
				<input name="sharedcode" type="text" class="form-control" placeholder="Shared Code" required/><span class="required_asterisk shrink_glyph glyphicon glyphicon-asterisk"></span>
						<span id="shared_code_info" class="shrink_glyph glyphicon glyphicon-question-sign" data-toggle="tooltip" data-placement="left" title="Shared code can be found in the laundry rooms!"></span>

				<input name="to_servlet" value="signupForm" style="display: none;" required>
	  	      </div>
            </div>
          </div>
          <div class="modal-footer">
	  		<button class="btn btn-primary" type="submit">Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span></button>
            <button class="btn btn-default" type="button" onclick="hideBlueBlackground()" data-dismiss="modal">Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span></button>
	  	  </div>
        </form>

      </div>
    </div>
  </div>

	<!-- FORGOT PASSWORD MODAL -->
  <div class="modal fade" id="forgot_password_modal" role="dialog" data-controls-modal="forgot_password_modal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="center_modal_top_close_button close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header form-signin-heading">Forgot Password</h4>
        </div>
        <form name="forgot_password_form" class="form-signin" action="" method="post">
          <div class="modal-body">
            <div class="row">
	  	      <div class="col-xs-9 col-xs-offset-1">
				<input name="email" type="email" class="form-control settings_modal" placeholder="Email Address" required/>
						<span class="required_asterisk shrink_glyph glyphicon glyphicon-question-sign" data-toggle="tooltip" data-placement="right" title="An email will be send, containing your new password!"></span>
				<input name="to_servlet" value="forgotPasswordForm" style="display: none;" required>
	  	      </div>
            </div>
          </div>
          <div class="modal-footer">
	  		<button class="btn btn-primary" type="submit">Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span></button>
            <button class="btn btn-default" type="button" onclick="hideBlueBlackground()" data-dismiss="modal">Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span></button>
	  	  </div>
        </form>
      </div>
    </div>
  </div>

<!-- CHANGE USER ACCOUNT HUISNUMMER MODAL -->
  <div class="modal fade" id="modify_account_housenumber_modal" role="dialog" data-controls-modal="modify_account_housenumber_modal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="center_modal_top_close_button close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header form-signin-heading">Change Housenumber</h4>
        </div>
        <form name="modify_account_housenumber" class="form-signin" action="" method="post">
          <div class="modal-body">
            <div class="row">
	  	      <div class="col-xs-9 col-xs-offset-1">
				<input name="huisnummer" type="text" class="form-control settings_modal" placeholder="New Housenumber" required/>
						<span class="required_asterisk shrink_glyph glyphicon glyphicon-question-sign" data-toggle="tooltip" data-placement="right" title="You will need to log back in after this change."></span>
				<input name="to_servlet" value="changeHuisnummerForm" style="display: none;" required>
	  	      </div>
            </div>
          </div>
          <div class="modal-footer">
	  		<button class="btn btn-primary" type="submit">Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span></button>
            <button class="btn btn-default" type="button" onclick="hideBlueBlackground()" data-dismiss="modal">Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span></button>
	  	  </div>
        </form>
      </div>
    </div>
  </div>

<!-- CHANGE USER ACCOUNT PASSWORD MODAL -->
  <div class="modal fade" id="modify_account_password_modal" role="dialog" data-controls-modal="modify_account_password_modal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="center_modal_top_close_button close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header form-signin-heading">Change Password</h4>
        </div>
        <form name="modify_account_password" class="form-signin" action="" method="post">
          <div class="modal-body">
            <div class="row">
	  	      <div class="col-xs-9 col-xs-offset-1">
				<input name="password" type="password" class="form-control settings_modal" placeholder="New Password" required/>
				<br>
				<input name="password" type="password" class="form-control" placeholder="Retype Password"/>
				<input name="to_servlet" value="changePasswordForm" style="display: none;" required>
	  	      </div>
            </div>
          </div>
          <div class="modal-footer">
	  		<button class="btn btn-primary" type="submit">Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span></button>
            <button class="btn btn-default" type="button" onclick="hideBlueBlackground()" data-dismiss="modal">Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span></button>
	  	  </div>
        </form>
      </div>
    </div>
  </div>

<!-- DELETE USER ACCOUNT MODAL -->
  <div class="modal fade" id="delete_account_modal" role="dialog" data-controls-modal="delete_account_modal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="center_modal_top_close_button close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header form-signin-heading">Delete Account</h4>
        </div>
        <form name="delete_account" class="form-signin" action="" method="post">
          <div class="modal-body">
            <div class="row">
	  	      <div class="col-xs-9 col-xs-offset-1">
				<input name="password" type="password" class="form-control settings_modal" placeholder="Type password" required/>
				<input name="to_servlet" value="deleteAccountForm" style="display: none;" required>
	  	      </div>
            </div>
          </div>
          <div class="modal-footer">
	  		<button class="btn btn-primary" type="submit">Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span></button>
            <button class="btn btn-default" type="button" onclick="hideBlueBlackground()" data-dismiss="modal">Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span></button>
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
          <h4 class="center_modal_header form-signin-heading">Insert Date</h4>
        </div>

        <form name="appointment_form" class="form-signin" action="" method="post">
          <div class="modal-body inputSpread">
            <div class="row">
              <div id="remove_background_color_insert_date_modal" class="col-xs-4"><h5>Dag:</h5></div>
              <div class="col-xs-8 .col-xs-offset-4">
              
                <select name="day" class="form-control pull-right">
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
  			  <div id="remove_background_color_insert_date_modal" class="col-xs-4"><h5>Tijd:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">
  			  
                <select name="time" class="form-control pull-right">
			  	  <c:forEach items="${time}" var="entry">
			  	  	<option value="${entry.key}">${entry.value}</option>
			  	  </c:forEach>
				</select>

		      </div>
  			</div>
  			
  			<div class="row">
  		      <div id="remove_background_color_insert_date_modal" class="col-xs-4"><h5>Wasmachine:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">

		  	    <select name="machine" class="form-control">
		  	     <c:if test="${requestScope.wasruimte != 'that'}">
				  <c:forEach items="${wasmachine}" var="entry" varStatus="loop" begin="0" end="3">
				  	<option value="${entry.key}">${entry.value}</option>
				  </c:forEach>
				  </c:if>
				 
				 <c:if test="${requestScope.wasruimte == 'that'}">
				  <c:forEach items="${wasmachine}" var="entry" varStatus="loop" begin="4" end="7">
				  	<option value="${entry.key}">${entry.value}</option>
				  </c:forEach>
				  </c:if>
		  	    </select>

  		      </div>
  			</div>

  			<div class="row">
  		      <div id="remove_background_color_insert_date_modal" class="col-xs-4"><h5>Huisnummer:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">
  			    <input value="${sessionScope.user.houseNumber}" name="huisnummer" type="text" class="form-control pull-right readonly" placeholder="Not Logged In!" readonly>
  		      </div>
  			</div>
		    <input value="${sessionScope.user.id}" name="id" type="number" style="display: none;" required>		
        	<input name="to_servlet" value="appointmentForm" style="display: none;">
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary" >Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span></button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span></button>
          </div>
        </form>

      </div>
    </div>
  </div>
  
  

<div class="modal fade" id="delete_appointment_modal" role="dialog" data-controls-modal="delete_appointment_modal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header" class="form-signin-heading">Remove Appointent</h4>
        </div>
        
        <form name="delete_appointment_form" class="form-signin" action="" method="post">
        
          <div class="modal-body inputSpread">
          <div class="row">
        
  		      <div class="col-xs-12"><h5>Set the correct details of the appointment you want to remove.</h5></div>
  		      </div>
  		      
            <div class="row">
              <div id="remove_background_color_insert_date_modal" class="col-xs-4"><h5>Dag:</h5></div>
              <div class="col-xs-8 .col-xs-offset-4">
              
                <select name="day" class="form-control pull-right">
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
  			  <div id="remove_background_color_insert_date_modal" class="col-xs-4"><h5>Tijd:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">
  			  
                <select name="time" class="form-control pull-right">
			  	  <c:forEach items="${time}" var="entry">
			  	  	<option value="${entry.key}">${entry.value}</option>
			  	  </c:forEach>
				</select>

		      </div>
  			</div>
  			
  			<div class="row">
  		      <div id="remove_background_color_insert_date_modal" class="col-xs-4"><h5>Wasmachine:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">

		  	    <select name="machine" class="form-control">		  	    
		  	     <c:if test="${requestScope.wasruimte != 'that'}">
				  <c:forEach items="${wasmachine}" var="entry" varStatus="loop" begin="0" end="3">
				  	<option value="${entry.key}">${entry.value}</option>
				  </c:forEach>
				  </c:if>
				 
				 <c:if test="${requestScope.wasruimte == 'that'}">
				  <c:forEach items="${wasmachine}" var="entry" varStatus="loop" begin="4" end="7">
				  	<option value="${entry.key}">${entry.value}</option>
				  </c:forEach>
				  </c:if>

		  	    </select>

  		      </div>
  			</div>

  			<div class="row">
  		      <div id="remove_background_color_insert_date_modal" class="col-xs-4"><h5>Huisnummer:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">
  			    <input value="${sessionScope.user.houseNumber}" name="huisnummer" type="text" class="form-control pull-right readonly" placeholder="Not Logged In!" readonly>
  		      </div>
  			</div>

		    <input value="${sessionScope.user.id}" name="id" type="number" style="display: none;" required>		
        	<input name="to_servlet" value="removeAppointmentForm" style="display: none;">
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary" >Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span></button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span></button>
          </div>
        </form>
      
        </div>
    </div>
    </div>
    

  <div class="modal fade" id="delete_message_modal" role="dialog" data-controls-modal="delete_message_modal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header" class="form-signin-heading">Remove Message</h4>
        </div>
        <form name="delete_message_form" class="form-prikbord" action="" method="post">
          <div class="modal-body">
            <div class="row">
  		      <div class="col-xs-12"><h5>Are you sure you want to remove your message with title: <br><b><span id="messageTitle"></span></b></h5>
		        <input value="${sessionScope.user.id}" name="user_id" type="number" style="display: none;" required>		
  			    <input id="messageId" name="message_id" value="" style="display: none;" required>
  		      </div>
  		    </div>
  		  </div>
  		  <input name="to_servlet" value="removeMessageForm" style="display: none;">
         <div class="modal-footer">
            <button type="submit" class="btn btn-primary" >Delete <span class="shrink_glyph glyphicon glyphicon-ok"></span></button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span></button>
          </div>
        </form>
      </div>
    </div>
  </div>
  

  <div class="modal fade" id="prikbord_modal" role="dialog" aria-labelledby="myModalLabel" data-controls-modal="prikbord_modal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
      <div class="modal-content">
      
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="center_modal_header" class="form-signin-heading">Create Message</h4>
        </div>

        <form name="prikbord_form" class="form-prikbord" action="" method="post">
        
          <div class="modal-body inputSpread">
            <div class="row">
              <div class="col-xs-2"><h5>Title: </h5></div>
              <div class="col-xs-10 .col-xs-offset-2">
	  		  	  <input type="text" name="title" class="form-control pull-right">
	  		  </div>
        	</div>
        	
			<br/>
            
            <div class="row">
              <div class="col-xs-2"><h5>Inhoud: </h5></div>
              <div class="col-xs-10 .col-xs-offset-2">
					<textarea name="body" rows="10" cols="80" id="MyID"></textarea>
	  		  </div>
        	</div>
        	
  			<div class="row">
  		      <div class="col-xs-2"><h5>Sender:</h5></div>
  			  <div class="col-xs-10 .col-xs-offset-2">
  			    <input value="${sessionScope.user.email}" id="space_for_span_prikbord_input" name="userEmail" type="text" class="form-control pull-right readonly" placeholder="Not Logged In!" readonly>
  			    	<span class="shrink_glyph glyphicon glyphicon-question-sign" data-toggle="tooltip" data-placement="left" title="Your message has to be approved before it will be visible!"></span>
  		      </div>
  			</div>
 
		    <input value="${sessionScope.user.id}" name="id" type="number" style="display: none;" required>		
        	<input name="to_servlet" value="createMessageForm" style="display: none;">
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary" >Accept <span class="shrink_glyph glyphicon glyphicon-ok"></span></button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel <span class="shrink_glyph glyphicon glyphicon-remove"></span></button>
          </div>
        </form>

      </div>
    </div>
  </div>
  
  

  <div id="blue_background" style="display: none;"></div>

  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

  <script>
    <!-- Save id for delete message modal -->
    $(document).on("click", "#save_message_id", function () {
      var myMessageId = $(this).data('id');
      $("#messageId").val(myMessageId);

      var myMessageTitle = $(this).data('title');
      document.getElementById("messageTitle").innerHTML = myMessageTitle; 
    });
  </script>

  <script>
    $("[data-toggle=popover]").popover();

    $("#delete_appointment_modal").draggable({
      handle: ".modal-content"
    });

    $("#myModal").draggable({
      handle: ".modal-content"
    });

    $(document).ready(function() {
	  $("#signup_button").click(function() {
	    $("#blue_background").css("display", "initial");
	  });
	});

    function hideBlueBlackground() {
      $("#blue_background").css("display", "none");    
    };

    $(function () {
      $('[data-toggle="tooltip"]').tooltip()
    })
  </script>

  <!-- Showing Error Messages! -->
  <script>
    myVar = "${errorMessage}";
   	if(myVar != "") {
   	  alert(myVar);
   	}
  </script>

  <script>
    $(".go_to_drogers").click(function() {
      $('html, body').animate({
        scrollTop: $("#drogers").offset().top}, 'slow');
    });
  </script>

  <!-- Script for prikbord -->
  <script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
  <script>
  var simplemde = new SimpleMDE({
    element: document.getElementById("MyID"),
    placeholder: "NOTE: Write <br> instead of pressing enter to go to the next line!",
    spellChecker: false,
    status: false,
    styleSelectedText: false,
    showIcons: ["bold", "italic", "strikethrough", "quote", "unordered-list", "ordered-list"],
    hideIcons: ["heading", "image", "side-by-side", "fullscreen", "guide", "horizontal-rule"]});
  </script>

</body>
</html>
