<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="nl-NL">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
  <meta name="description" content="" />
  <meta name="author" content="" />
  <meta name="keywords" content="" />

  <title>Parkhaven - Was Schema</title>

  <!-- Bootstrap core CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap2.css" />
  <!-- Custom styles -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dashboard.css" />
  <!-- Stylesheet for Prikbord! -->
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.css" />
 
  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>


<body>

   <nav class="navbar navbar-default navbar-fixed-top">
     <div class="container-fluid">
       <div class="navbar-header">
         <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
           <span class="sr-only">Toggle navigation</span>
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
           <span class="icon-bar"></span>
         </button>
         <a class="navbar-brand mainTitle" href="#">Parkhaven - Was Schema</a>
       </div>

       <div id="navbar" class="navbar-collapse collapse">
	     <div class="navbar-form navbar-right">

<c:if test="${empty sessionScope.user.email}">
	       <form id="loginForm" name="loginForm" action="" method="post">
             <div class="form-group">            
               <input name="email" type="email" class="form-control" id="exampleInputEmail3" placeholder="Email" required>
               <input name="password" type="password" class="form-control" id="exampleInputPassword3" placeholder="Password" required>
             </div>
             <input name="signin" value="1" style="display: none;"/>
             <button type="submit" class="btn btn-default">Log in</button>
           </form>

           <button id="testingShow" data-toggle="modal" data-target="#myRegisterModel" class="btn btn-default">Sign up</button>
</c:if>
<c:if test="${not empty sessionScope.user.email}">
			<div id="login_information">Logged in as <c:out value="${sessionScope.user.email}" /></div>
			
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

     </div>
   </nav>


   <div class="container-fluid">
     <div class="row">
       <div class="col-sm-3 col-md-2 sidebar">
         <ul class="nav nav-sidebar">
           <li class="active dashBoard_margin"><a class="dashBoard_boldness">Dashboard <span class="glyphicon glyphicon-home"></span><span class="sr-only">(current)</span></a></li>

           <li>
             <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">

           	   <div class="panel panel-default">
                 <div class="panel-heading" role="tab" id="headingFour">
                   <h4 class="panel-title">
                     <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                       Settings <span class="glyphicon glyphicon-wrench"></span>
                     </a>
                   </h4>
                 </div>
                 <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                   <div class="panel-body">

				<form id="mailForm" name="mailForm" class="form-signin" action="" method="post">
					    <label class="form-check-inline">
							Receive a mail 15 min prior to wash date: <input id="mail_checkbox" name="mail_checkbox" onchange="$('#mailForm').submit();" type="checkbox" class="form-check-input" <c:if test="${sessionScope.user.mailEnabled}">checked</c:if> />
					    </label>
					    
					    <input value="${sessionScope.user.id}" name="id" type="number" style="display: none;" required>		
        				<input name="mail" value="1" type="number" style="display: none;">
				</form>
					                  	   <br>
					                  	   Other
                   </div>
                 </div>
           	   </div>

               <div class="panel panel-default">
                 <div class="panel-heading" role="tab" id="headingThree">
                   <h4 class="panel-title">
                   <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                     Help <span class="glyphicon glyphicon-earphone"></span>
                   </a>
                   </h4>
                 </div>
                 <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                   <div class="panel-body">
               	     <h6>Huisnummer: ${sessionScope.user.huisnummer}</h6>
               	     <h6>Hitcounter: ${requestScope.hitcounter}</h6>
               	     
               	     <c:set var="can_wash" value="${(sessionScope.wash_counter > 2)? false : true}" scope="session"/>
					 <h6>Can wash: ${can_wash}</h6>
               	     <h6>Wash times remaining: ${3 - sessionScope.wash_counter}</h6>
               	     <h6>Time: ${time[0]}, ${time[12]}</h6>
               	     <h6>Date: ${date[1]}, ${date[21]}</h6>
                     <p>Contact: +31 636493686<br>Email: yosh.nahar@gmail.com</p>
                     <p>Call or Email if unclear. For disussions use the Prikbord on the main site!</p>
                   </div>
                 </div>
               </div>

               <div class="panel panel-default">
                 <div class="panel-heading" role="tab" id="headingOne">
                   <h4 class="panel-title">
                   <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                     Prikbord <span class="glyphicon glyphicon-list"></span>
                   </a>
                   </h4>
                 </div>
                 <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                   <div class="prikbord panel-body">
                     <img src="${pageContext.request.contextPath}/resources/img/prikbordnieuw.png" data-toggle="modal" data-target="#prikbord_modal" id="prikbordImg"/>
               	     <c:forEach var="message" items="${prikbord_messages}">
               	     	<h4>${message.value.titleOutput}</h4>
               	     	${message.value.bodyOutput}
               	     	<c:out value="${message.value.gebruikerMail}"></c:out>
               	     	
               	     	<c:if test="${message.value.gebruikerId == sessionScope.user.id}">
                            <button id="save_message_id" data-title="${message.value.titleOutput}" data-id="${message.key}" type="button" data-toggle="modal" data-target="#delete_message_modal" class="btn btn-default btn-xs" >Remove message</button>
               	     	</c:if>
               	     	<hr></hr>
               	     </c:forEach>
                   </div>
                 </div>
               </div>

             </div>
           </li>
         </ul>

         <button class="btn btn-primary btn-md" data-toggle="modal" data-target="#myModal">Was Datum Invoeren <span class="glyphicon glyphicon-edit"></span></button>
       </div>

       <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
       	 <div id="bodyTitle">
           <h2 class="page-header">Overview <small>/ Week ${getDays.overviewDate}</small></h2>
         </div>
         <div >
			<nav aria-label="week_pagination">
				<ul class="pager">
					<li><a href="#" class="label"><span class="glyphicon glyphicon-arrow-left"></span> Previous</a></li>
					<li class="disabled"><a class="label">Current Week</a></li>
					<li><a href="#" class="label">Next <span class="glyphicon glyphicon-arrow-right"></span></a></li>
				</ul>
			</nav>
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
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.huisnummer}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.huisnummer}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.huisnummer}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.huisnummer}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.huisnummer}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.huisnummer}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.huisnummer}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.huisnummer}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.huisnummer}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.huisnummer}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.huisnummer}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.huisnummer}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.huisnummer}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.huisnummer}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.huisnummer}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.huisnummer}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.huisnummer}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.huisnummer}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.huisnummer}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.huisnummer}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.huisnummer}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.huisnummer}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.huisnummer}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.huisnummer}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
							  <c:if test="${huis_nummer1[i] eq sessionScope.user.huisnummer}">
							  <button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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
	 					  		<c:if test="${huis_nummer2[i] eq sessionScope.user.huisnummer}">
							  		<button type="button" data-toggle="modal" data-target="#delete_appointment_modal" class="remove_appointment glyphicon glyphicon-trash"></button>
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

          <small>*** <span id="blue">Wasmachine C1</span> / <span id="yellow">Wasmachine C2</span> / <span id="green">Droger D1</span> / <span id="red">Droger D2</span></small>

        </div>
      </div>
    </div>
  </div>

  <!-- Modal SIGNIN -->
  <div class="modal fade" id="myRegisterModel" role="dialog" aria-labelledby="myRegisterModelLabel" data-controls-modal="myRegisterModel" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog">
      <div class="modal-content" id="registerForm">

        <div class="modal-header">
          <button onclick="myFunction()" type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 id="center_h4" class="form-signin-heading">Registreren</h4>
        </div>

        <form name="index" class="form-signin" action="" method="post">
          <div class="modal-body">
            <div class="row">
	  	      <div class="col-xs-9 col-xs-offset-1">
				<input name="email" type="email" id="inputEmail" class="form-control" placeholder="Email Adres" required autofocus>
				
				<input name="firstname" type="text" class="form-control" placeholder="First Name" required>
				
				<input name="lastname" type="text" class="form-control" placeholder="Last Name" required>
				
				<input name="password" type="password" id="inputPassword" class="form-control" placeholder="Wachtwoord" required>
				
				<input type="password" id="inputPassword2" class="form-control" placeholder="Wachtwoord Herhalen" required>
				
				<input name="huisnummer" type="number" id="inputHuisnummer" class="form-control" placeholder="Huisnummer">

				<input name="sharedcode" type="text" class="form-control" placeholder="Shared Code">

				<input name="register" value="1" type="number" style="display: none;" required>
	  	      </div>
            </div>
          </div>
          <div class="modal-footer">
	  		<button class="btn btn-primary" type="submit">Akkoord</button>
            <button class="btn btn-default" type="button" onclick="myFunction()" data-dismiss="modal">Cancel</button>
	  	  </div>
        </form>
       
      </div>
    </div>
  </div>


  <!-- Modal APPOINTMENT -->
  <div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4>Insert Date</h4>
        </div>

        <form name="appointmentForm" class="form-signin" action="" method="post">
        
          <div class="modal-body inputSpread">
            <div class="row">
              <div class="col-xs-4"><h5>Dag:</h5></div>
              <div class="col-xs-8 .col-xs-offset-4">
              
                <select name="day" class="form-control pull-right">
                  <c:forEach items="${date}" var="entry">
			 	     <option value="${entry.key}">${entry.value}</option>
                  </c:forEach>
				</select>
	  		  
	  		  </div>
        	</div>

  			<div class="row">
  			  <div class="col-xs-4"><h5>Tijd:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">
  			  
                <select name="time" class="form-control pull-right">
			  	  <c:forEach items="${time}" var="entry">
			  	  	<option value="${entry.key}">${entry.value}</option>
			  	  </c:forEach>
				</select>

		      </div>
  			</div>
  			
  			<div class="row">
  		      <div class="col-xs-4"><h5>Wasmachine:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">

		  	    <select name="machine" class="form-control">
				  <c:forEach items="${wasmachine}" var="entry">
				  	<option value="${entry.key}">${entry.value}</option>
				  </c:forEach>
		  	    </select>

  		      </div>
  			</div>

  			<div class="row">
  		      <div class="col-xs-4"><h5>Huisnummer:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">
  			    <input value="${sessionScope.user.huisnummer}" name="huisnummer" type="text" class="form-control pull-right readonly" placeholder="Not Logged In!" readonly>
  		      </div>
  			</div>
		    <input value="${sessionScope.user.id}" name="id" type="number" style="display: none;" required>		
        	<input name="appointment" value="1" type="number" style="display: none;">
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary" >Accept <span class="glyphicon glyphicon-ok"></span></button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel <span class="glyphicon glyphicon-remove"></span></button>
          </div>
        </form>

      </div>
    </div>
  </div>
  
  

<div class="modal fade" id="delete_appointment_modal" role="dialog" aria-labelledby="myModalLabel" data-controls-modal="delete_appointment_modal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 id="center_h4" class="form-signin-heading">Remove Appointent</h4>
        </div>
        
        <form name="deleteAppointmentForm" class="form-signin" action="" method="post">
        
          <div class="modal-body inputSpread">
          <div class="row">
        
  		      <div class="col-xs-12"><h5>Set the correct details of the appointment you want to remove.</h5></div>
  		      </div>
  		      
            <div class="row">
              <div class="col-xs-4"><h5>Dag:</h5></div>
              <div class="col-xs-8 .col-xs-offset-4">
              
                <select name="day" class="form-control pull-right">
                  <c:forEach items="${date}" var="entry">
			 	     <option value="${entry.key}">${entry.value}</option>
                  </c:forEach>
				</select>
	  		  
	  		  </div>
        	</div>

  			<div class="row">
  			  <div class="col-xs-4"><h5>Tijd:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">
  			  
                <select name="time" class="form-control pull-right">
			  	  <c:forEach items="${time}" var="entry">
			  	  	<option value="${entry.key}">${entry.value}</option>
			  	  </c:forEach>
				</select>

		      </div>
  			</div>
  			
  			<div class="row">
  		      <div class="col-xs-4"><h5>Wasmachine:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">

		  	    <select name="machine" class="form-control">
				  <c:forEach items="${wasmachine}" var="entry">
				  	<option value="${entry.value}">${entry.value}</option>
				  </c:forEach>
		  	    </select>

  		      </div>
  			</div>

  			<div class="row">
  		      <div class="col-xs-4"><h5>Huisnummer:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">
  			    <input value="${sessionScope.user.huisnummer}" name="huisnummer" type="text" class="form-control pull-right readonly" placeholder="Not Logged In!" readonly>
  		      </div>
  			</div>
		    <input value="${sessionScope.user.id}" name="id" type="number" style="display: none;" required>		
        	<input name="remove_appointment" value="1" type="number" style="display: none;">
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary" >Accept <span class="glyphicon glyphicon-ok"></span></button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel <span class="glyphicon glyphicon-remove"></span></button>
          </div>
        </form>
      
        </div>
    </div>
    </div>
    

<div class="modal fade" id="delete_message_modal" role="dialog" aria-labelledby="myModalLabel" data-controls-modal="delete_message_modal" data-backdrop="static" data-keyboard="false">
    <div class="modal-dialog modal-sm">
      <div class="modal-content">
      
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 id="center_h4" class="form-signin-heading">Remove Message</h4>
        </div>
                        <form name="deleteMessageForm" class="form-prikbord" action="" method="post">
        
        <div class="modal-body">
                    <div class="row">
        
  		      <div class="col-xs-12"><h5>Are you sure you want to remove your message with title: <span id="messageTitle"></span></h5>
		    <input value="${sessionScope.user.id}" name="user_id" type="number" style="display: none;" required>		
  			    <input id="messageId" name="message_id" value="" required>
  		      </div>
  		      </div>
  			 </div>
  			         	<input name="delete_prikbord_message" value="1" type="number" style="display: none;">
  			 
         <div class="modal-footer">
            <button type="submit" class="btn btn-primary" >Delete <span class="glyphicon glyphicon-ok"></span></button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel <span class="glyphicon glyphicon-remove"></span></button>
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
          <h4 id="center_h4" class="form-signin-heading">Create Message</h4>
        </div>

        <form name="prikbordForm" class="form-prikbord" action="" method="post">
        
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
  			    <input value="${sessionScope.user.email}" name="userEmail" type="text" class="form-control pull-right readonly" placeholder="Not Logged In!" readonly>
  		      </div>
  			</div>
 
		    <input value="${sessionScope.user.id}" name="id" type="number" style="display: none;" required>		
        	<input name="prikbord" value="1" type="number" style="display: none;">
          </div>
          <div class="modal-footer">
            <button type="submit" class="btn btn-primary" >Accept <span class="glyphicon glyphicon-ok"></span></button>
            <button type="button" class="btn btn-default" data-dismiss="modal">Cancel <span class="glyphicon glyphicon-remove"></span></button>
          </div>
        </form>

      </div>
    </div>
  </div>
  
  

  <div id="testingShowDiv"></div>

  <!-- Bootstrap core JavaScript -->
  <!-- Placed at the end of the document so the pages load faster -->
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

	$("#testingShowDiv").hide();

	$(document).ready(function(){
	    $("#testingShow").click(function(){
	        $("#testingShowDiv").show();
	    });
	});

    function myFunction(){
    	$("#testingShowDiv").hide();    
    };

    $('#myRegisterModal').modal({
        backdrop: 'static',
        keyboard: false
    })
  </script>

<!-- Showing Error Messages! -->
  <script>
  	myVar = "${errorMessage}";
   	if(myVar != "") {
   		alert(myVar);
   	}
  </script>
<!-- Script for prikbord -->
<script src="https://cdn.jsdelivr.net/simplemde/latest/simplemde.min.js"></script>
<script>
var simplemde = new SimpleMDE({
	    element: document.getElementById("MyID"),
		placeholder: "NOTE: Write <br/> to continue on the next line, NOT the ENTER key!",
		spellChecker: false,
		status: false,
		styleSelectedText: false,
		showIcons: ["bold", "italic", "strikethrough", "quote", "unordered-list", "ordered-list"],
		hideIcons: ["heading", "image", "side-by-side", "fullscreen", "guide", "horizontal-rule"]});
</script>

</body>
</html>
