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
	String planId			= (String) request.getAttribute("planId");
	boolean existe			= (boolean) request.getAttribute("existe");
	boolean btnGrabar		= request.getParameter("BtnGrabar") == null ? true : false;
	
	TitProfesional profesional	= (TitProfesional) request.getAttribute("profesional");
	TitAlumno titAlumno			= (TitAlumno) request.getAttribute("titAlumno");
%>
<html>
<body>
<div class="container-fluid">
	<h2>Profesional<small class="text-muted fs-4"> ( <%=codigoAlumno%> - <%=nombreAlumno%> - <%=planId%> )</small></h2>
	<div class="alert alert-info">		
		<a class="btn btn-primary" href="titulacion"><i class="fas fa-arrow-left"></i></a>&nbsp;
	</div>
	<form id="frmProfesional" name="frmProfesional" method="post" action="grabarProfesional">
		<input type="hidden" name="Folio" id="Folio" value="<%=profesional.getFolio()%>"/>
		<div class="row">
			<div class="span3">
				<fieldset>
					<label for="Folio">Folio: <span class="badge bg-dark"><%=profesional.getFolio()%></span> </label>
					<%if (!existe) out.print("¡ Sin Grabar !");%>
				</fieldset>
				<br>
				<fieldset>
					<label for="Curp">CURP</label>
					<input type="text" class="input input-large form-control" maxlength="18" name="Curp" id="Curp" value="<%=profesional.getCurp()%>" required/>
				</fieldset>
				<br>
				<fieldset>
					<label for="Nombre">Nombre</label>
					<input type="text" class="input input-large form-control" name="Nombre" id="Nombre" value="<%=profesional.getNombre()%>" required/>
				</fieldset>
				<br>
				<fieldset>
					<label for="PrimerApellido">Primer apellido</label>
					<input type="text" class="input input-large form-control" name="PrimerApellido" id="PrimerApellido" value="<%=profesional.getPrimerApellido()%>" required/>
				</fieldset>
				<br>
				<fieldset>
					<label for="SegundoApellido">Segundo apellido</label>
					<input type="text" class="input input-large form-control" name="SegundoApellido" id="SegundoApellido" value="<%=profesional.getSegundoApellido()==null?"":profesional.getSegundoApellido()%>" required/>
				</fieldset>
				<br>
				<fieldset>
					<label for="Correo">Correo electronico</label>
					<input type="text" class="input input-large form-control" name="Correo" id="Correo" value="<%=profesional.getCorreoElectronico()%>" required/>
				</fieldset>
			</div>
		</div>
		<br>
<%	if (btnGrabar){	%>
		<div class="alert alert-info">
<%		if(titAlumno.getEstado().equals("A")){%>
			<a onclick="javascript:Grabar();" class="btn btn-primary"><i class="icon-ok icon-white"></i> Grabar</a>
<%			if (existe){%>
			&nbsp;&nbsp;<a onclick="javascript:Borrar('<%=folio%>');" class="btn btn-primary"><i class="icon-ok icon-white"></i>Borrar</a>
<%			}
		}
	}
%>		
	</div>
	</form>
</div>
</body>
<script type="text/javascript">
	function Grabar() {
		document.frmProfesional.submit();
	}
	
	function Borrar(folio) {
		if (confirm("¿ Estás seguro de borrar los datos profesionales ?")){
			document.location.href = "borrarProfesional?Folio="+folio;
		}	
	}
</script>
</html>