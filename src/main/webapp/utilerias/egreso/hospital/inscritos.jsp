<%@ include file= "../../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../../seguro.jsp" %>
<%@ include file= "../../../body.jsp" %>
<%@ include file="../../../idioma.jsp"%>

<jsp:useBean id="Insc" scope="page" class="aca.vista.InscritosUtil"/>

<%

	ArrayList lisIns 		= Insc.getListAll(conEnoc,"ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
%>

<table id="noayuda" width="99%"  align="center" class="tabla">
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<%	
	
	for (int i=0; i< lisIns.size(); i++){
		aca.vista.Inscritos ins	= (aca.vista.Inscritos) lisIns.get(i);
%>
<tr>
	<td> INSERT INTO ALUMNOSINSCRITOS(MATRICULA, NOMBRE, APATERNO, AMATERNO, GENERO, MODALIDAD, CARRERA) VALUES('<%=ins.getCodigoPersonal() %>', '<%=ins.getNombre() %>', '<%=ins.getApellidoPaterno() %>', '<%=ins.getApellidoMaterno() %>', '<%=ins.getSexo() %>', '<%=aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, ins.getModalidadId()) %>', '<%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, ins.getCarreraId()) %>');</td>
</tr>
<%		
	}
%>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
<tr>
<td align="center"><b>FIN DEL LISTADO</b></td>
</tr>
</table>

<%@ include file= "../../../cierra_enoc.jsp" %>