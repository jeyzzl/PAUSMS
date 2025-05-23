<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="mentCarrera" scope="page" class="aca.mentores.MentCarrera"/>
<%	String sModulo			= request.getParameter("moduloId");
	String sCarpeta     	= request.getParameter("carpeta");
	String carreraId		= request.getParameter("carreraId");
	String periodoId		= (String) session.getAttribute("ciclo");
	String codigoEmpleado	= (String) session.getAttribute("codigoEmpleado");
	String accion			= request.getParameter("Accion");
	
	if(accion == null)
		accion = "0";
	
	if(accion.equals("1")){
		mentCarrera.setPeriodoId(periodoId);
		mentCarrera.setCarreraId(carreraId);
		mentCarrera.setMentorId(codigoEmpleado);
		if(!mentCarrera.existeReg(conEnoc)){
			if(mentCarrera.insertReg(conEnoc)){
%>
	<table style="margin: 0 auto;">
		<tr>
			<td><font size="3" color="blue">Data Saved!</font></td>
		</tr>
	</table>
	<meta http-equiv='REFRESH' content='1;URL=mentor.jsp?carrera=<%=carreraId %>&periodo=<%=periodoId %>'>
<%
			}
		}else{
%>
	<table style="margin: 0 auto;">
		<tr>
			<td><font size="3" color="red">This mentor is already assigned to the degree!</font></td>
		</tr>
	</table>
<%
		}
	}
	
%>

<form id="forma" name="forma" action="agrega_mentor.jsp?Accion=1&moduloId=<%=sModulo %>&carpeta=<%=sCarpeta %>&carreraId=<%=carreraId %>&periodoId=<%=periodoId %>" method="post">
	<table id="table" class="table table-sm table-bordered">
		<tr>
			<td align="center"><font size="4"><b>Select professor as a degree mentor [<%=carreraId %>]<br />in the cycle [<%=periodoId %>]</b></font></td>
		</tr>
		<tr>
			<td align="center"><b>Name:</b> <%= aca.vista.MaestrosUtil.getNombreMaestro(conEnoc,codigoEmpleado,"NOMBRE") %></td>
		</tr>
		<tr>
			<td align="center"><input type="submit" value="Save" /></td>
		</tr>
	</table>
</form>

<%@ include file= "../../cierra_enoc.jsp" %> 