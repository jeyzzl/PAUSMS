<%@page import="aca.catalogo.spring.CatGradePoint"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<html>
<head>	
	<script type="text/javascript">	
		function Grabar() {
			if (document.frmGradePoint.GradePointId.value != ""
					&& document.frmGradePoint.Nombre != "") {				
				document.frmGradePoint.submit();
			} else {
				alert("<spring:message code="aca.JSCompletar"/> ");
			}
		}
	
	</script>
</head>
<%
	// Declaracion de variables	
	String gpId				= request.getParameter("GradePointId")==null?"0":request.getParameter("GradePointId");
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	CatGradePoint grade 	= (CatGradePoint) request.getAttribute("grade");
%>
<body>
	<div class="container-fluid">
	<h1><spring:message code="aca.GradePoint"/></h1>	
	<form action="grabar" method="post" name="frmGradePoint" target="_self">	
	<div class="alert alert-info">
		<a href="gpa" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>
	
	<div class="row container-fluid">
	
	<div class="span2 col">
		<label for="aca.Clave"><spring:message code="aca.Clave"/></label>
		<input name="GradePointId" class="input input-mini form-control" type="text" id="GradePointId" size="3" maxlength="3" required value="<%=grade.getGpId()%>" readonly>
		<br>
	
		<label for="aca.Nombre">Letter Grade</label>
		<input name="Nombre" type="text" class="input input-xlarge form-control" id="Nombre" size="40" maxlength="40" required value="<%=grade.getGpNombre()%>">
		<br>
		<label for="aca.Inicio">Minimum</label>
		<input name="Inicio" type="text" class="input input-xlarge form-control" id="Inicio" size="5" maxlength="5" required value="<%=grade.getInicio()%>">
		<br>	
		</div>
		<div class="span2 col">
		
		<label for="aca.Fin">Maximum</label>
		<input name="Fin" type="text" class="input input-xlarge form-control" id="Fin" size="5" maxlength="5" required value="<%=grade.getFin()%>">
		<br>
		<label for="aca.Puntos">GPA</label>	
		<input name="Puntos" type="text" class="input input-xlarge form-control" id="Puntos" size="4" maxlength="4" required value="<%=grade.getPuntos()%>">
		<br>
		<label for="aca.Titulo">Grade</label>	
		<input name="Titulo" type="text" class="input input-xlarge form-control" id="Titulo" size="30" maxlength="30" value="<%=grade.getTitulo()%>">
		<br>
		</div>
	</div>         
    <div class="alert alert-info">				
		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a> &nbsp;
	</div>	
	
	</form>
	</div>
</body>
</html>