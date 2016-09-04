<html>
<body>

	<h2>Hello World!</h2>

	<div>
		Tomcat Version:
		<%=application.getServerInfo()%><br> Servlet Specification
		Version :
		<%=application.getMajorVersion()%>.<%=application.getMinorVersion()%><br>
		JSP version :
		<%=JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion()%><br>
	</div>

</body>
</html>
