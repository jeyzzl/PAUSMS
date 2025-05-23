<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.baja.BajaAlumno"%>
<%@page import="aca.baja.BajaAlumpaso"%>
<%@page import="aca.baja.BajaAlumpasoUtil"%>
<jsp:useBean id="bajaAlumno" scope="page" class="aca.baja.BajaAlumno"/>
<jsp:useBean id="bajaAlumnoU" scope="page" class="aca.baja.BajaAlumnoUtil"/>
<jsp:useBean id="bajaAlumpaso" scope="page" class="aca.baja.BajaAlumpaso"/>
<jsp:useBean id="bajaAlumpasoU" scope="page" class="aca.baja.BajaAlumpasoUtil"/>
<head>	
	<script type="text/javascript">
		function autorizar(bajaId){
			if(confirm("Desea autorizar al alumno?")){
				location.href = "autorizacion?Accion=1&baja="+bajaId;
			}
		}
	</script>
</head>
<%
	String accion = request.getParameter("Accion");

	if(accion == null) accion = "0";
	
	if(accion.equals("1")){
		String bajaId = request.getParameter("baja");
		bajaAlumpaso = bajaAlumpasoU.mapeaRegId(conEnoc, bajaId, "1");
		bajaAlumpaso.setEstado("S");
		if(bajaAlumpasoU.updateReg(conEnoc, bajaAlumpaso)){
%>

<table id="noayuda">
	<tr><td><font color="blue" size="3"><b>El alumno se autoriz&oacute; con &eacute;xito!!!</b></font></td></tr>
</table>
<%
		}else{
%>
<table>
	<tr><td><font color="red" size="3"><b>Ocurri&oacute; un error al autorizar al alumno. Int&eacute;ntelo de nuevo</b></font></td></tr>
</table>
<%
		}
	}
%>
<div class="container-fluid">
<h1>Alumnos con solicitud de baja</h1> 		
	<table class="table table-bordered">
<%
	ArrayList<aca.baja.BajaAlumno> lisBaja = bajaAlumnoU.getListSolicitudes(conEnoc, "ORDER BY F_INICIO");
	if(lisBaja.size() > 0){
%>
	<thead class="table-info">
		<tr>
			<th><spring:message code="aca.Operacion"/></th>
			<th><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Nombre"/></th>
			<th><spring:message code="aca.Carrera"/></th>
			<th>N° Mat</th>
		</tr>
	</thead>
<%
		for(int i = 0; i < lisBaja.size(); i++){
			bajaAlumno = (BajaAlumno) lisBaja.get(i);
%>
		<tr>
			<td>
<%
			if(!BajaAlumpasoUtil.realizoPaso(conEnoc, bajaAlumno.getBajaId(), "1")){
%>
				<a class="btn btn-primary" href="#" onclick="autorizar('<%=bajaAlumno.getBajaId() %>');">Autorizar</a>
<%
			}else{
%>
							&nbsp;
<%
			}
%>
			</td>
			<td><%=bajaAlumno.getCodigoPersonal() %></td>
			<td><%=aca.alumno.AlumUtil.getNombreAlumno(conEnoc, bajaAlumno.getCodigoPersonal(), "NOMBRE") %></td>
			<td><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, aca.alumno.PlanUtil.getCarreraId(conEnoc, bajaAlumno.getCodigoPersonal())) %></td>
			<td align="center"><%=aca.kardex.KrdxCursoAct.numMatInscritas(conEnoc, bajaAlumno.getCodigoPersonal(), bajaAlumno.getCargaId()) %></td>
		</tr>
<%
		}
	}else{
%>
		<tr>
			<td>No existen alumnos con solicitud de baja</td>
		</tr>
<%
	}
%>
	</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>