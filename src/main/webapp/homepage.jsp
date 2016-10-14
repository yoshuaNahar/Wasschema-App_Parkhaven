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

	       <form id="loginForm" name="loginForm" action="controller" method="post">
             <div class="form-group">            
               <input name="email" type="email" class="form-control" id="exampleInputEmail3" placeholder="Email" required>
               <input name="password" type="password" class="form-control" id="exampleInputPassword3" placeholder="Password" required>
             </div>
             <input name="signin" value="1" style="display: none;"/>
             <button type="submit" class="btn btn-default">Log in</button>
           </form>

           <button id="testingShow" data-toggle="modal" data-target="#myRegisterModel" class="btn btn-default">Sign In</button>

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
                	   ${errorMessage}Anim pariatur cliche reprehenderit, enim eiusmod high life accusamus terry richardson ad squid.
                	   3 wolf moon officia aute, non cupidatat skateboard dolor brunch. Food truck quinoa nesciunt laborum
                 	   eiusmod. Brunch 3 wolf moon tempor, sunt aliqua put a bird on it squid single-origin coffee nulla
                 	   assumenda shoreditch et. Nihil anim keffiyeh helvetica, craft beer labore wes anderson cred nesciunt
                  	   sapiente ea proident. Ad vegan excepteur butcher vice lomo. Leggings occaecat craft beer farm-to-table,
                   	   raw denim aesthetic synth nesciunt you
                 	   probably haven't heard of them accusamus labore sustainable VHSx.
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
               	     <h6>Huisnummer: ${memberHuisnummer}</h6>
                     <p>Contact: +31 636493686<br>Email: yosh.nahar@gmail.com</p>
                     <p>If there is no emergency just mail me your problem or question. For disussions use the Prikbord on the main site!</p>
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
                     <img src="${pageContext.request.contextPath}/resources/img/prikbordnieuw.png" id="prikbordImg"/>
               	     <h4>KvD-Borrel</h4>
                     <p>Datum: 4 maart, 19:00 <br>
                        De dong #420 <br>
                        Free Booze and Hapjes!!!</p>
                     <hr>
                     <h4>Eten Bestellen</h4>
                     <p>*in t antiliaans* 1 Euro hopi lekkere broodjes!!! <br>
                        Tijdstip: 12:00 - 21:00 / Huisnummer 441</p>
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
                 <td>06:00</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[0] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[0] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[0]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[0] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[0] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[0]}</span>
	 					 </div>
					   </div>
			     </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width: 50%">
						   <span class="coverProgressNumber">230</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[15] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[15] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[15]}</span>
	 					 </div>
					   </div>
				 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[30] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[30] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[30]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[30] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[30] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[30]}</span>
	 					 </div>
					   </div>
				 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[45] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[45] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[45]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[45] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[45] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[45]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[60] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[60] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[60]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[60] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[60] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[60]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[75] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[75] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[75]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[75] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[75] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[75]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[90] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[90] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[90]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[90] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[90] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[90]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>07:30</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width: 50%">
						   <span class="coverProgressNumber">69</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:50%">
						   <span class="coverProgressNumber">420</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[16] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[16] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[16]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[16] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[16] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[16]}</span>
	 					 </div>
					   </div>
			     </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[31] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[31] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[31]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[31] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[31] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[31]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[46] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[46] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[61]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[46] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[46] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[46]}</span>
	 					 </div>
					   </div>
			  </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[61] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[61] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[61]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[61] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[61] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[61]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[76] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[76] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[76]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[76] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[76] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[76]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[91] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[91] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[91]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:50%">
						   <span class="coverProgressNumber">91</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>09:00</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[2] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[2] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[2]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[2] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[2] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[2]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[17] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[17] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[17]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[17] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[17] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[17]}</span>
	 					 </div>
					   </div>
			  </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[32] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[32] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[32]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[32] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[32] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[32]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[47] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[47] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[47]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[47] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[47] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[47]}</span>
	 					 </div>
					   </div>
			  </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[62] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[62] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[62]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[62] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[62] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[62]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[77] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[77] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[77]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[77] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[77] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[77]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[92] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[92] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[92]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[92] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[92] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[92]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>10:30</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[3] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[3] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[3]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[3] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[3] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[3]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[18] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[18] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[18]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[18] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[18] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[18]}</span>
	 					 </div>
					   </div>
			  </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[33] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[33] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[33]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[33] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[33] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[33]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[48] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[48] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[48]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[48] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[48] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[48]}</span>
	 					 </div>
					   </div>
			  </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[63] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[63] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[63]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[63] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[63] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[63]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[78] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[78] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[78]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[78] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[78] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[78]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[93] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[93] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[93]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[93] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[93] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[93]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>12:00</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[4] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[4] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[4]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[4] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[4] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[4]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[19] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[19] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[19]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[19] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[19] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[19]}</span>
	 					 </div>
					   </div>
			  </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[34] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[34] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[34]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[34] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[34] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[34]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[49] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[49] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[49]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[49] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[49] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[49]}</span>
	 					 </div>
					   </div>
			  </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[64] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[64] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[64]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[64] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[64] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[64]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[79] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[79] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[79]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[79] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[79] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[79]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[94] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[94] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[94]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[94] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[94] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[94]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>13:30</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[5] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[5] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[5]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[5] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[5] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[5]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[20] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[20] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[20]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[20] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[20] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[20]}</span>
	 					 </div>
					   </div>
			  </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[35] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[35] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[35]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[35] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[35] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[35]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[50] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[50] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[50]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[50] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[50] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[50]}</span>
	 					 </div>
					   </div>
			  </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[65] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[65] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[65]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[65] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[65] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[65]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[80] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[80] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[80]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[80] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[80] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[80]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[95] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[95] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[95]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[95] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[95] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[95]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>15:00</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[6] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[6] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[6]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[6] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[6] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[6]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[21] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[21] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[21]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[21] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[21] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[21]}</span>
	 					 </div>
					   </div>
			  </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[36] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[36] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[36]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[36] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[36] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[36]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[51] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[51] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[51]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[51] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[51] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[51]}</span>
	 					 </div>
					   </div>
			  </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[66] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[66] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[66]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[66] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[66] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[66]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[81] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[81] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[81]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[81] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[81] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[81]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[96] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[96] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[96]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[96] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[96] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[96]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>16:30</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[7] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[7] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[7]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[7] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[7] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[7]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[22] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[22] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[22]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[22] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[22] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[22]}</span>
	 					 </div>
					   </div>
			  </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[37] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[37] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[37]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[37] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[37] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[37]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[52] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[52] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[52]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[52] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[52] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[52]}</span>
	 					 </div>
					   </div>
			  </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[67] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[67] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[67]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[67] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[67] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[67]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[82] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[82] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[82]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[82] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[82] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[82]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[97] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[97] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[97]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[97] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[97] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[97]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>18:00</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[8] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[8] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[8]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[8] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[8] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[8]}</span>
	 					 </div>
					   </div>
			     </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[23] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[23] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[23]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[23] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[23] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[23]}</span>
	 					 </div>
					   </div>
				 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[38] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[38] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[38]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[38] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[38] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[38]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[53] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[53] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[53]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[53] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[53] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[53]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[68] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[68] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[68]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[68] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[68] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[68]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[83] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[83] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[83]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[83] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[83] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[83]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[98] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[98] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[98]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[98] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[98] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[98]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>19:30</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[9] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[9] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[9]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[9] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[9] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[9]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[24] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[24] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[24]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[24] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[24] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[24]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[39] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[39] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[39]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[39] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[39] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[39]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[54] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[54] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[54]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[54] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[54] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[54]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[69] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[69] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[69]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[69] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[69] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[69]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[84] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[84] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[84]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[84] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[84] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[84]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[99] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[99] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[99]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[99] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[99] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[99]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>21:00</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[10] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[10] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[10]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[10] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[10] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[10]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[25] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[25] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[25]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[25] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[25] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[25]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[40] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[40] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[40]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[40] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[40] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[40]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[55] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[55] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[55]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[55] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[55] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[55]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[70] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[70] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[70]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[70] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[70] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[70]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[85] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[85] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[85]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[85] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[85] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[85]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[100] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[100] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[100]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[100] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[100] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[100]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>22:30</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[11] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[11] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[11]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[11] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[11] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[11]}</span>
	 					 </div>
					   </div>
			     </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[26] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[26] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[26]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[26] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[26] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[26]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[41] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[41] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[41]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[41] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[41] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[41]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[56] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[56] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[56]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[56] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[56] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[56]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[71] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[71] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[71]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[71] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[71] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[71]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[86] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[86] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[86]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[86] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[86] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[86]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[101] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[101] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[101]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[101] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[101] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[101]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>00:00</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[12] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[12] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[12]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[12] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[12] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[12]}</span>
	 					 </div>
					   </div>
			     </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[27] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[27] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[27]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[27] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[27] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[27]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[32] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[32] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[32]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[32] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[32] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[32]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[57] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[57] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[57]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[57] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[57] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[57]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[72] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[72] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[72]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[72] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[72] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[72]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[87] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[87] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[87]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[87] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[87] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[87]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[102] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[102] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[102]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[102] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[102] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[102]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>01:30</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[13] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[13] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[13]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[13] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[13] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[13]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[28] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[28] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[28]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[28] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[28] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[28]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[43] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[43] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[43]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[43] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[43] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[43]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[58] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[58] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[58]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[58] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[58] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[58]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[73] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[73] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[73]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[73] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[73] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[73]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[88] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[88] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[88]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[88] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[88] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[88]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[103] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[103] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[103]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[103] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[103] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[103]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

               <tr>
                 <td>03:00</td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[14] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[14] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[14]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[14] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[14] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[14]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[29] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[29] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[29]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[29] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[29] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[29]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[44] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[44] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[44]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[44] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[44] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[44]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[74] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[74] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[74]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[74] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[74] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[74]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[59] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[59] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[59]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[59] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[59] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[59]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[89] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[89] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[89]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[89] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[89] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[89]}</span>
	 					 </div>
					   </div>
                 </td>
                 <td>
	                   <div class="progress">
	 					 <div class="progress-bar progress-bar-info" style="width:
	 					   <c:if test='${huisnummer1[104] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer1[104] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer1[104]}</span>
	 					 </div>
						 <div class="progress-bar progress-bar-warning" style="width:
						   <c:if test='${huisnummer2[104] == 0}'><c:out value='0%'/></c:if>
	 					   <c:if test='${huisnummer2[104] != 0}'><c:out value='50%'/></c:if>">
						   <span class="coverProgressNumber">${huisnummer2[104]}</span>
	 					 </div>
					   </div>
                 </td>
               </tr>

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
          <h4 class="form-signin-heading">Registreren</h4>
        </div>

        <form name="index" class="form-signin" action="controller" method="post">
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

        <form name="appointmentForm" class="form-signin" action="controller" method="post">
          <div class="modal-body inputSpread">
            <div class="row">
              <div class="col-xs-4"><h5>Dag:</h5></div>
              <div class="col-xs-8 .col-xs-offset-4">
                <select name="day" class="form-control pull-right">
			      <option value="0">${getDays.daysPresent[0]}</option>
			  	  <option value="15">${getDays.daysPresent[1]}</option>
			  	  <option value="30">${getDays.daysPresent[2]}</option>
			  	  <option value="45">${getDays.daysPresent[3]}</option>
			  	  <option value="60">${getDays.daysPresent[4]}</option>
			  	  <option value="75">${getDays.daysPresent[5]}</option>
			  	  <option value="90">${getDays.daysPresent[6]}</option>			  		
				</select>
	  		  </div>
        	</div>
  			<div class="row">
  			  <div class="col-xs-4"><h5>Tijd:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">
                <select name="time" class="form-control pull-right">
			  	  <option value="1">6:00</option>
			  	  <option value="2">7:30</option>
			  	  <option value="3">9:00</option>
			  	  <option value="4">10:30</option>
			  	  <option value="5">12:00</option>
			  	  <option value="6">13:30</option>
			  	  <option value="7">15:00</option>
			  	  <option value="8">16:30</option>
			  	  <option value="9">18:00</option>
			  	  <option value="10">19:30</option>
			  	  <option value="11">21:00</option>
			  	  <option value="12">22:30</option>
			  	  <option value="13">00:00</option>
			  	  <option value="14">01:30</option>
			  	  <option value="15">03:00</option>
				</select>
		      </div>
  			</div>
  			<div class="row">
  		      <div class="col-xs-4"><h5>Wasmachine:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">
		  	    <select name="machine" class="form-control">
		  	      <option value="1">C1</option>
		  	      <option value="2">C2</option>
		  	      <option value="3">B1</option>
		  	      <option value="4">B2</option>
		  	    </select>
  		      </div>
  			</div>
  			<div class="row">
  		      <div class="col-xs-4"><h5>Huisnummer:</h5></div>
  			  <div class="col-xs-8 .col-xs-offset-4">
  			    <input value="${memberHuisnummer}" name="huisnummer" type="text" class="form-control pull-right readonly" placeholder="Not Logged In!" required>
  		      </div>
  			</div>
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


  <div id="testingShowDiv"></div>

  <!-- Bootstrap core JavaScript -->
  <!-- Placed at the end of the document so the pages load faster -->
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
  <script src="https://code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
  <script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>

  <script>
	$("[data-toggle=popover]").popover();

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

<!-- This is for the readOnly and Required Appointment input field, You cant have readOnly and Required for an input field -->
  <script>
	$(".readonly").keydown(function(e){
		e.preventDefault();
	});
  </script>

</body>
</html>
