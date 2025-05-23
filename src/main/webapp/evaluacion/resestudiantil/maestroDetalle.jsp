<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
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
	
	String fac			= "xxx";
	String facTemp		= "xxx";
	
	String numCursos	= "";
	String promedio		= "";
	int numAlum	=0, numAlumEval=0, numAlumFaltan=0;	
	double participacion = 0;
	
	// Arbol que contiene el total de alumnos por materia  
	java.util.TreeMap<String,String> mapAlumnos 	= alumRespU.getMapAlumnosMateria(conEnoc,edoId,cargas);	
	
	// Arbol que contiene el total de alumnos que han evaluado a un maestro por materia  
	java.util.TreeMap<String,String> mapEvaluados 	= alumRespU.getMapEvaluadosMaterial(conEnoc,edoId,cargas);
	
	// Arbol que contiene el promedio por materia  
	java.util.TreeMap<String,String> mapPromedio 	= alumRespU.getMapPromedioMateria(conEnoc,edoId);	
	
	// Listado de materias
	ArrayList<String> materias	= alumRespU.getCursos(conEnoc, cargas, edoId, "ORDER BY EMP_FAC_BASE(CODIGO_PERSONAL), CODIGO_PERSONAL");
%>
<body>
<div class="container-fluid">
<h2>
	<%= aca.edo.EdoPeriodoUtil.getPeriodoNombre(conEnoc, periodo) %> - 		
	<%=edo.getTipo().equals("E")?"Opinion Est.":edo.getTipo().equals("A")?"Autoevaluaci&oacute;n":"" %> - <%=edo.getNombre()%>
	<small class="text-muted fs-4">  ( Promedio Institucional = <%=aca.edo.EdoAlumnoRespUtil.getPromedioEvaluacion(conEnoc, edoId)%> )</small>
</h2>
<div class="alert alert-info">
	<a class="btn btn-primary" href="evaluacion?periodoId=<%=periodo%>">Regresar</a>&nbsp;
	<a href="maestro?periodo=<%=periodo%>&edo=<%=edoId %>" class="btn btn-primary">Reporte Concentrado</a>
</div>
	<table  class="table table-striped table-bordered">
	  <tr>
	    <th><spring:message code="aca.Numero"/></th>
		<th><spring:message code="aca.Clave"/></th>
		<th><spring:message code="aca.Maestro"/></th>
		<th><spring:message code="aca.Materia"/></th>
		<th><spring:message code="aca.Grupo"/></th>
		<th><spring:message code="aca.Promedio"/></th>
		<th>#Alumnos</th>
		<th>Contestaron</th>
		<th>Faltan</th>
		<th>% Part.</th>
	  </tr>
<%		
		for (int i=0; i< materias.size(); i++){
			String maestro = materias.get(i).split("@")[0];
			String materia = materias.get(i).split("@")[1];
			
			String nombreGrupo = alumRespU.nombreCurso(conEnoc, materia);
			
			numCursos="0"; promedio="0"; numAlum=0; numAlumEval=0;
			
			if ( mapAlumnos.containsKey(materia)) numAlum = Integer.parseInt(mapAlumnos.get(materia));
			if ( mapPromedio.containsKey(materia)) promedio = mapPromedio.get(materia);
			if ( mapEvaluados.containsKey(materia)) numAlumEval = Integer.parseInt(mapEvaluados.get(materia));
			
			fac = aca.hca.HcaMaestroUtil.getEmpFacBase(conEnoc, maestro);

			if (fac.equals("xxx") ){
				fac = "Facultad no registrada";
			}
			
			if ( !facTemp.equals(fac) ){
				facTemp = fac;
				if (!fac.equals("Facultad no registrada") ){
%>
	  <tr class="th2"><td colspan="9"><h1><%=fac%> : <%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, fac)%></</h1></td></tr>
<%	
				}else{
%>
	  <tr class="th2"><td colspan="9"><h1><%=fac%></</h1></td></tr>
<%	
				}
			}	
			
			numAlumFaltan	= numAlum-numAlumEval;//Integer.parseInt(aca.edo.EdoAlumnoResp.getAlumFaltantesProf(conEnoc, edoId, profe.getCodigoPersonal()));
			if (numAlum >0)
				participacion 	= Double.valueOf((double)numAlumEval/numAlum*100);
			else
				participacion = 0;
			
%>
 	  
 	    <td align="center"><%=i+1%></td>
	    <td align="center"><%=maestro%></td>
	    <td align="center"><%=aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, maestro, "NOMBRE") %></td>
	    <td align="center"><%=materia%></td>
	    <td align="center"><%=nombreGrupo%></td>
	    <td align="center"><%=promedio%></td>
	    <td align="center"><%=numAlum%></td>
	    <td align="center"><%=numAlumEval%></td>
	    <td align="right"><%= numAlumFaltan %></td>
	    <td align="right"><%= getformato.format(participacion) %></td>
	  </tr>	
<%		} %>	
	</table>
	</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>