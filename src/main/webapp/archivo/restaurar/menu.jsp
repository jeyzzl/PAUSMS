<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>

<body>
	<div class="container-fluid">
		<h1>Reports</h1>
		<div class="row-fluid">
            <ul class="list-group">			
            	<li class="list-group-item"><a href="documentos.jsp"><i class="fas fa-arrow-circle-right"></i>Document images</a></li>
              	<li class="list-group-item"><a href="generales.jsp"><i class="fas fa-arrow-circle-right"></i>Unclassified images</a></li>
              	<li class="list-group-item"><a href="alumnos.jsp"><i class="fas fa-arrow-circle-right"></i>Student files</a></li>             
              	<li class="list-group-item"><a href="maestros.jsp"><i class="fas fa-arrow-circle-right"></i>Teacher files</a></li>
            </ul>
		</div>
	</div>
</body>
</html>
<style>
	body{
		background:white;
	}
</style>