<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@page import="aca.tit.spring.TitAlumno"%>
<%@page import="aca.tit.spring.TitProfesional"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%	
	String folio			= request.getParameter("Folio")==null?"0":request.getParameter("Folio");
	String codigoAlumno 	= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno		= (String) request.getAttribute("nombreAlumno");	
	boolean existe			= (boolean) request.getAttribute("existe");	
	
	TitAlumno titAlumno			= (TitAlumno) request.getAttribute("titAlumno");
%>
<html>
<body>
<div class="container-fluid">
	<h2>Titulo Físico<small class="text-muted fs-4"> ( <%=codigoAlumno%> - <%=nombreAlumno%> - <%=titAlumno.getPlanId()%> )</small></h2>
	<div class="alert alert-info">		
		<a class="btn btn-primary" href="titulacion"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
	<form id="frmFolio" name="frmFolio" method="post" action="grabarFolio">
	<input type="hidden" name="Folio" id="Folio" value="<%=titAlumno.getFolio()%>"/>
	<div class="row">
		<div class="span3">
			<fieldset>
				<label for="Folio">Folio: <span class="badge bg-dark"><%=titAlumno.getFolio()%></span> </label>
				<%if (!existe) out.print("¡ Sin Grabar !");%>
			</fieldset>
			<br>
			<fieldset>
				<label for="FolioFisico">Folio Fisico</label>
				<input type="text" class="input input-large form-control" maxlength="18" name="FolioFisico" id="FolioFisico" value="<%=titAlumno.getFolioFisico()%>" required/>
			</fieldset>
		</div>
	</div>
	<br>
	<div class="alert alert-info">
		<a onclick="javascript:Grabar();" class="btn btn-primary"><i class="icon-ok icon-white"></i>Grabar</a>	
	</div>
	</form>
</div>
</body>
<script type="text/javascript">
	function Grabar() {
		document.frmFolio.submit();
	}	
</script>
</html>