<%@page import="aca.edo.Edo"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %> 
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="maestroU" class="aca.hca.HcaMaestroUtil" scope="page"/>
<jsp:useBean id="carreraU" class="aca.catalogo.CatCarreraUtil" scope="page"/>
<jsp:useBean id="edoU" scope="page" class="aca.edo.EdoUtil"/>
<head>
	<script type="text/javascript">
		function ElegirEvaluacion(){
			document.frmDocente.Accion.value="0";
			document.frmDocente.submit();
		}	
	</script>
</head>		
<%
	String codigoPersonal	= (String) session.getAttribute("codigoPersonal");
	String edoId 			= request.getParameter("EdoId")==null?"0":request.getParameter("EdoId");
	String facultad 		= request.getParameter("facultad");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	String facultadId 		= "X";
	String carreraId		= "X";	
	int cont = 0;
	
	ArrayList<aca.catalogo.CatCarrera> lisCarrera 	= carreraU.getListCarrera(conEnoc,facultad,"ORDER BY NOMBRE_CARRERA");
	ArrayList<aca.hca.HcaMaestro> lisMaestro 		= maestroU.getListFacultad(conEnoc, facultad," ORDER BY ENOC.CARRERA_NIVEL(CARRERA_ID), ENOC.NOMBRE_CARRERA(CARRERA_ID), ENOC.EMP_APELLIDO(CODIGO_PERSONAL)");
	ArrayList<aca.edo.Edo> lisEdo 					= edoU.getListTipo(conEnoc,"P", " ORDER BY PERIODO_ID");
	
	// Elige la primer evaluación de la lista en caso de no existir una evalauacion en el parámetro.
	if (edoId.equals("0")&&lisEdo.size()>0) edoId = lisEdo.get(0).getEdoId();
	
	java.util.HashMap<String,String> mapPares	= aca.edo.EdoParUtil.mapMaestroPar(conEnoc, edoId);		
%>
<body>
	<div class="container-fluid">
	<h2>Evaluación de Pares<small class="text-muted fs-4"> ( [<%=codigoPersonal%>] <%= aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, codigoPersonal, "NOMBRE") %> )</small></h2>
	<form name="frmDocente" action="asignaPar">
	<input type="hidden" name="Accion">
	<input type="hidden" name="facultad" value="<%=facultad %>">
	<div class="alert alert-info d-flex align-items-center">
		<a href="par" class="btn btn-primary"><spring:message code="aca.Regresar"/></a>&nbsp;
				<b>Evaluación:</b>&nbsp;
				<select id="EdoId" name="EdoId" class="form-control" style="width:200px" onchange="javascript:ElegirEvaluacion();">
<%
	for(int i = 0; i < lisEdo.size(); i++){
		aca.edo.Edo edo = (aca.edo.Edo) lisEdo.get(i);
%>
					<option value="<%=edo.getEdoId()%>"<%=edo.getPeriodoId().equals(edoId)?" Selected":"" %>><%=edo.getNombre()%></option>
<%	} %>
				</select>								
	</div>
	</form>
<table  class="table table-sm  table-bordered">

<%	for (int i=0; i<lisMaestro.size(); i++){
		aca.hca.HcaMaestro docente = lisMaestro.get(i);

		if(!carreraId.equals(docente.getCarreraId())){
			carreraId = docente.getCarreraId();%>
		  	<tr>
		    	<td colspan="6"><font size="5"><b>Carrera :&nbsp;<%=docente.getCarreraId()%> -&nbsp;<%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc,docente.getCarreraId())%></b></font></td>
			</tr>
			<tr class="table-info"> 
				<th width="10%">Código</th>
				<th width="40%"><spring:message code="aca.Maestro"/></th>
				<th width="40%">Evaluado por:</th>
			</tr>
  		<%	cont = 0;
		}  
		cont++;
		String maestros = "";
		if (mapPares.containsKey(edoId+docente.getCodigoPersonal())) maestros = (String)mapPares.get(edoId+docente.getCodigoPersonal());
%>
		<tr> 
			<td align="center"><%=docente.getCodigoPersonal()%></td>
			<td><a href="agrega?EdoId=<%=edoId%>&CodigoMaestro=<%=docente.getCodigoPersonal()%>&facultad=<%=facultad%>"><%= aca.vista.MaestrosUtil.getNombreMaestro(conEnoc,docente.getCodigoPersonal(),"APELLIDO")%></a></td>
			<td align="center"><%=maestros%></td>
		</tr>
<%	}
	lisMaestro	= null;
%>	
	<tr><td colspan="5" class="end"></td></tr>
</table>	
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>