<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<%
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
%>
<body>
	<div class="container-fluid">
		<h2>Reports</h2>
		<div class="row-fluid" style="width:49%">
            <ul class="list-group">              
              <li class="list-group-item"><a href="fecha"><i class="fas fa-arrow-circle-right"></i> Enrolled Students</a></li>
              <li class="list-group-item"><a href="listado"><i class="fas fa-arrow-circle-right"></i> Students with unassigned images</a></li>
              <li class="list-group-item"><a href="doc_existe"><i class="fas fa-arrow-circle-right"></i> Students per Document</a></li>              
              <li class="list-group-item"><a href="alumnoSinDoc"><i class="fas fa-arrow-circle-right"></i> Students without documents</a></li>              
              <li class="list-group-item"><a href="alumnoConRevalida"><i class="fas fa-arrow-circle-right"></i> Students with revalidation</a></li>              
              <li class="list-group-item"><a href="listadoIncorrectos"><i class="fas fa-arrow-circle-right"></i> Students with incorrect documents</a></li>
              <li class="list-group-item"><a href="servicio"><i class="fas fa-arrow-circle-right"></i> Students with social service or Bachelor's degree</a></li>
<%	if (codigoPersonal.equals("9800308")){%>                            
              <li class="list-group-item"><a href="buscarImagenes"><i class="fas fa-arrow-circle-right"></i> Identify images(*In-development*)</a></li>
<%	} %>              
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