<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.carga.spring.CargaBloque"%>
<%@ page import= "aca.baja.spring.BajaAlumno"%>
<%@ page import= "aca.alumno.spring.AlumEstado"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<script>
	function refrescar(){
		document.forma.submit();
	}	
</script>
<%	
	String codigoPersonal			= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");
	String alumnoNomnbre			= (String) request.getAttribute("alumnoNombre");
	String cargaId					= (String) request.getAttribute("cargaId");	
	String bloqueId					= (String) request.getAttribute("bloqueId");
	String carrera					= (String) request.getAttribute("carrera");
	boolean existe					= (boolean) request.getAttribute("existe");
	
	BajaAlumno bajaAlumno			= (BajaAlumno) request.getAttribute("bajaAlumno");
	AlumEstado alumEstado			= (AlumEstado) request.getAttribute("alumEstado");
		
	String mensaje					= request.getParameter("Mensaje") == null ? "-" : request.getParameter("Mensaje");
	
	List<Carga> lisCargas 			= (List<Carga>)request.getAttribute("lisCargas");
	List<CargaBloque> lisBloques 	= (List<CargaBloque>)request.getAttribute("lisBloques");
%>
<head>	
</head>
<body bgcolor="#FFFFFF">
<div class="container-fluid" >
	<h3>Solicitud de baja <small class="text-muted fs-5"> ( Alumno: <%=codigoAlumno%> - <%=alumnoNomnbre%> )</small></h3>
	<form id="forma" name="forma" action="guardarSolicitud" method="post">
	<div class="alert alert-info">&nbsp;Carga: 
		<select name="CargaId" style="width:350px;" onchange='javascript:refrescar()'>
<%	for(Carga carga : lisCargas){ %>
			<option <%=cargaId.equals(carga.getCargaId())?" selected ":""%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>
<%	} %>
		</select>&nbsp;&nbsp;Bloque: 	
		<select name="BloqueId" style="width:350px;" onchange='javascript:refrescar()'>
<%	for(CargaBloque bloque : lisBloques){ %>
			<option <%=bloqueId.equals(bloque.getBloqueId())?" selected ":""%> value="<%=bloque.getBloqueId()%>">[<%=bloque.getBloqueId() %>] <%=bloque.getNombreBloque()%></option>
<%	} %>
		</select>		
	</div>
	<div class="alert alert-success">DATOS REGISTRADOS EN EL PERIODO:&nbsp; &nbsp; &nbsp; &nbsp;Carrera: <%=carrera%> &nbsp; &nbsp; Plan: <%=alumEstado.getPlanId()%> &nbsp; &nbsp;Fecha de inscripción: <%=alumEstado.getFecha()%></div>
<%	if(!mensaje.equals("-")){%>
	<div class="alert alert-danger" role="alert">
		<%=mensaje%>
	</div>
<%	} %>		
		<h4>Razon:</h4><br>
		<select name="RazonId" style="width:350px;">
			<option <%=bajaAlumno.getRazonId().equals("1")?"selected":""%> value="1">Cambio de carrera</option>
			<option <%=bajaAlumno.getRazonId().equals("2")?"selected":""%> value="2">Cambio de institución</option>
			<option <%=bajaAlumno.getRazonId().equals("3")?"selected":""%> value="3">Enfermedad</option>
			<option <%=bajaAlumno.getRazonId().equals("4")?"selected":""%> value="4">Problemas familiares</option>
			<option <%=bajaAlumno.getRazonId().equals("5")?"selected":""%> value="5">Problemas disciplinarios</option>
			<option <%=bajaAlumno.getRazonId().equals("6")?"selected":""%> value="6">Otro</option>
		</select><br><br>
		<h4>Comentario:</h4><br>
		<textarea rows="3" cols="120" name="Comentario"><%=bajaAlumno.getComentario()%></textarea>
		<br><br>
		<div class="alert alert-info">
			<input type="submit" class="btn btn-primary" value="Guardar">&nbsp;
<% 		if (existe){ %>
			<a href="formato?BajaId=<%=bajaAlumno.getBajaId()%>"  class="btn btn-success">Formato</a>
<%		}%>			

		</div>		
	</form>
</div>
</body>