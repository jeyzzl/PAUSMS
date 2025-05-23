<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="alumResp" scope="page" class="aca.edo.EdoAlumnoResp" />
<jsp:useBean id="alumRespU" scope="page" class="aca.edo.EdoAlumnoRespUtil" />
<jsp:useBean id="Maestros" scope="page" class="aca.vista.Maestros" />
<jsp:useBean id="edo" scope="page" class="aca.edo.Edo"/>
<jsp:useBean id="EdoU" scope="page" class="aca.edo.EdoUtil"/>

<% 	
	java.text.DecimalFormat getformato= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String periodo		= request.getParameter("periodo");
	String edoId		= request.getParameter("edo");
	
	edo = EdoU.mapeaRegId(conEnoc, edoId);
	String cargas 		= aca.edo.EdoUtil.getCargasEvaluadas(conEnoc,edoId);	
	
	String fac			= "x";
	String facTemp		= "x";
	
	String numCursos	= "";
	String promedio		= "";
	int numAlum	=0, numAlumEval=0, numAlumFaltan=0;	
	double participacion = 0;
	
	// Arbol que contiene el numero de cursos por maestro
	java.util.TreeMap<String,String> mapCursos	= new java.util.TreeMap<String,String>();
	mapCursos 	= alumRespU.getMapCursos(conEnoc,edoId,cargas);
	
	// Arbol que contiene el total de alumnos por maestro  
	java.util.TreeMap<String,String> mapAlumnos = new java.util.TreeMap<String,String>();
	mapAlumnos		= alumRespU.getMapAlumnos(conEnoc,edoId,cargas);	
	
	// Arbol que contiene el total de alumnos que han evaluado a un maestro  
	java.util.TreeMap<String,String> mapEvaluados = new java.util.TreeMap<String,String>();
	mapEvaluados	= alumRespU.getMapEvaluados(conEnoc,edoId,cargas);
	
	// Arbol que contiene el promedio por maestro  
	java.util.TreeMap<String,String> mapPromedio = alumRespU.getMapPromedio(conEnoc,edoId);	
	
	// Listado de maestros
	ArrayList<aca.vista.Maestros> lisMaestro	= new ArrayList<aca.vista.Maestros>();
	lisMaestro	= aca.vista.MaestrosUtil.getListMaestroEvalDocente(conEnoc, edoId, "ORDER BY EMP_FAC_BASE(CODIGO_PERSONAL), CODIGO_PERSONAL");	
%>
<body>
	<table style="width:60%"  align="center">
		<tr>
		  <td align="center"><font size="3"><strong><%= aca.edo.EdoPeriodoUtil.getPeriodoNombre(conEnoc, periodo) %> - 		
		  <%=edo.getTipo().equals("E")?"Opinion Est.":edo.getTipo().equals("A")?"Autoevaluaci&oacute;n":"" %> - <%=edo.getNombre()%></strong></font>
		  </td>
		</tr>
		<tr>
		  <td align="center"><font size="2"><strong>Promedio Institucional = <%=aca.edo.EdoAlumnoRespUtil.getPromedioEvaluacion(conEnoc, edoId)%></strong></font></td>
		</tr>
		<tr>
			<td align="center">
				<a href="maestroDetalle?periodo=<%=periodo%>&edo=<%=edoId %>" class="btn btn-primary">Ver reporte detallado</a>
			</td>
		</tr>
	</table>	
	<table style="margin: 0 auto;  width:80%" class="table table-fullcondensed">
	  <tr>
		<td align="center" colspan="9"><a class="btn btn-primary" href="evaluacion"><font size="2">&lsaquo;&lsaquo; Regresar</font></a></td>
	  </tr>
	  <tr>
		<td colspan="9">&nbsp;</td>
	  </tr>
	  <tr>
	    <th><spring:message code="aca.Numero"/></th>
		<th><spring:message code="aca.Clave"/></th>
		<th><spring:message code="aca.Maestro"/></th>
		<th><spring:message code="aca.Promedio"/></th>
		<th>#Materias</th>
		<th>#Alumnos</th>
		<th>Contestaron</th>
		<th>Faltan</th>
		<th>% Part.</th>
	  </tr>
<%		
		for (int i=0; i< lisMaestro.size(); i++){
			aca.vista.Maestros profe = (aca.vista.Maestros) lisMaestro.get(i);
			
			numCursos="0"; promedio="0"; numAlum=0; numAlumEval=0;
			
			// Obtiene los datos almacenados en los TreeMap
			if ( mapCursos.containsKey(profe.getCodigoPersonal())) numCursos = mapCursos.get(profe.getCodigoPersonal());
			if ( mapAlumnos.containsKey(profe.getCodigoPersonal())) numAlum = Integer.parseInt(mapAlumnos.get(profe.getCodigoPersonal()));
			if ( mapEvaluados.containsKey(profe.getCodigoPersonal())) numAlumEval = Integer.parseInt(mapEvaluados.get(profe.getCodigoPersonal()));
			if ( mapPromedio.containsKey(profe.getCodigoPersonal())) promedio = mapPromedio.get(profe.getCodigoPersonal());
			
			fac = aca.hca.HcaMaestroUtil.getEmpFacBase(conEnoc, profe.getCodigoPersonal());
			
			if ( !facTemp.equals(fac) ){
				facTemp = fac;
%>
	  <tr class="th2"><td colspan="9"><%=fac%>: <%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, fac)%></td></tr>
<%	
			}			
			numAlumFaltan	= numAlum-numAlumEval;//Integer.parseInt(aca.edo.EdoAlumnoResp.getAlumFaltantesProf(conEnoc, edoId, profe.getCodigoPersonal()));
			if (numAlum >0)
				participacion 	= Double.valueOf((double)numAlumEval/numAlum*100);
			else
				participacion = 0;
			// aca.edo.Edo.getCursosProf(conEnoc,edoId,profe.getCodigoPersonal())
			// aca.edo.EdoAlumnoResp.getPromedioMaestro(conEnoc,edoId,profe.getCodigoPersonal())
%>
 	  <tr <%=(i%2 != 0)?sColor:"" %>>
 	    <td align="center"><%=i+1%></td>
	    <td align="center"><%=profe.getCodigoPersonal()%></td>
	    <td><%=profe.getApellidoPaterno()%> <%=profe.getApellidoMaterno()%> <%=profe.getNombre()%></td>
	    <td align="right"><%= promedio %></td>
	    <td align="right"><%= numCursos %></td>
	    <td align="right"><%= numAlum %></td>
	    <td align="right"><%= numAlumEval %></td>
	    <td align="right"><%= numAlumFaltan %></td>
	    <td align="right"><%= getformato.format(participacion) %></td>
	  </tr>	
<%		} %>	
	</table>
</body>
<%
	if (mapCursos!=null) mapCursos.clear();
	if (mapAlumnos!=null) mapAlumnos.clear();
	if (mapEvaluados!=null) mapEvaluados.clear();
	if (mapPromedio!=null) mapPromedio.clear();
%>
<%@ include file= "../../cierra_enoc.jsp" %>