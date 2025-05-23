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
	int numPreguntas	= aca.edo.EdoAlumnoPregUtil.getNumPreguntas(conEnoc, edoId, "O");
	
	String fac			= "x";
	String facTemp		= "x";
	String nombreFac	= "";
	
	String numCursos	= "";
	String promedio		= "";
	String promPreg	= "";
	int numAlum	=0, numAlumEval=0, numAlumFaltan=0;	
	double participacion = 0;
	
	// Arbol que contiene el numero de cursos por maestro
	java.util.TreeMap<String,String> mapCursos	= alumRespU.getMapCursos(conEnoc,edoId,cargas);
		
	// Arbol que contiene el total de alumnos por maestro  
	java.util.TreeMap<String,String> mapAlumnos = alumRespU.getMapAlumnos(conEnoc,edoId,cargas);	
		
	// Arbol que contiene el total de alumnos que han evaluado a un maestro  
	java.util.TreeMap<String,String> mapEvaluados = alumRespU.getMapEvaluados(conEnoc,edoId,cargas);
	
	// Arbol que contiene el promedio por maestro  
	java.util.TreeMap<String,String> mapPromedio =  alumRespU.getMapPromedio(conEnoc,edoId);	
	
	// Mapa para traer el promedio por pregunta de cada maestro	
	java.util.HashMap <String, String> mapPromPreg = alumRespU.getPromedio(conEnoc, cargas);	
	
	// Listado de maestros
	ArrayList<aca.vista.Maestros> lisMaestro	= aca.vista.MaestrosUtil.getListMaestroEvalDocente(conEnoc, edoId, "ORDER BY EMP_FAC_BASE(CODIGO_PERSONAL), CODIGO_PERSONAL");	
%>
<body>
<div class="container-fluid">
<h2>
	<%= aca.edo.EdoPeriodoUtil.getPeriodoNombre(conEnoc, periodo) %> - <%=edo.getTipo().equals("E")?"Opinion Est.":edo.getTipo().equals("A")?"Autoevaluaci&oacute;n":"" %> - <%=edo.getNombre()%>
	<small class="text-muted fs-4">( Promedio Institucional = <%=alumRespU.getPromedioEvaluacion(conEnoc, edoId)%> )</small>
</h2>
<div class="alert alert-info">
	<a class="btn btn-primary" href="evaluacion?periodoId=<%=periodo%>&edo=<%=edoId %>">Regresar</a>&nbsp;
	<a href="maestroDetalle?periodo=<%=periodo%>&edo=<%=edoId %>" class="btn btn-primary">Ver reporte detallado</a>
</div>
	<table style="margin: 0 auto; "" class="table table-striped table-bordered">  
	  <tr class="table-info">
	    <th><spring:message code="aca.Numero"/></th>
		<th><spring:message code="aca.Clave"/></th>
		<th><spring:message code="aca.Maestro"/></th>
		<th>Prom.</th>
		<th>#Mate.</th>
		<th>#Alum.</th>
		<th>Eval.</th>
		<th>Faltan</th>
		<th>% Part.</th>
<%
	for (int z=0; z<numPreguntas; z++){
		out.println("<th>"+(z+1)+"</th>");
	}
%>		
	  </tr>
<%		
		for (int i=0; i< lisMaestro.size(); i++){
			aca.vista.Maestros profe = (aca.vista.Maestros) lisMaestro.get(i);
			numCursos="0"; promedio="0"; numAlum=0; numAlumEval=0;
			
			// Obtiene los datos almacenados en los TreeMap
			if ( mapCursos.containsKey(profe.getCodigoPersonal())) numCursos = mapCursos.get(profe.getCodigoPersonal());
			if ( mapAlumnos.containsKey(profe.getCodigoPersonal())) numAlum = Integer.parseInt(mapAlumnos.get(profe.getCodigoPersonal()));
			if ( mapEvaluados.containsKey(profe.getCodigoPersonal())) numAlumEval = Integer.parseInt(mapEvaluados.get(profe.getCodigoPersonal()));
			if ( mapPromedio.containsKey(profe.getCodigoPersonal())){
				promedio = mapPromedio.get(profe.getCodigoPersonal());				
			}	
			/*if ( mapPromPreg.containsKey(profe.getCodigoPersonal())){
				promPreg = mapPromPreg.get(profe.getCodigoPersonal());
			}else{
				promPreg="0";
			}*/
			
			fac = aca.hca.HcaMaestroUtil.getEmpFacBase(conEnoc, profe.getCodigoPersonal());
			
			if ( !facTemp.equals(fac) ){
				facTemp = fac;
				if (!fac.equals("xxx")){ 
					nombreFac = aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, fac);
				}else{
					nombreFac = "-";
				}
%>
	  <tr class="th2"><td colspan="40"><%=fac.equals("xxx")?"000":fac%>: <%=nombreFac%></td></tr>
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
 	
 	    <td align="center"><%=i+1%></td>
	    <td align="center"><%=profe.getCodigoPersonal()%></td>
	    <td><%=profe.getApellidoPaterno()%> <%=profe.getApellidoMaterno()%> <%=profe.getNombre()%></td>
	    <td align="right"><%= promedio %></td>
	    <td align="right"><%= numCursos %></td>
	    <td align="right"><%= numAlum %></td>
	    <td align="right"><%= numAlumEval %></td>
	    <td align="right"><%= numAlumFaltan %></td>
	    <td align="right"><%= getformato.format(participacion) %></td>
<%
	for (int z=0; z<numPreguntas; z++){
		if ( mapPromPreg.containsKey(profe.getCodigoPersonal()+Integer.toString(z+1))){
			promPreg = mapPromPreg.get(profe.getCodigoPersonal()+Integer.toString(z+1));
		}else{
			promPreg="0";
		}
		
		out.println("<td>"+(promPreg)+"</td>");
	}
%>    
	  </tr>	
<%		} %>	
	</table>
	</div>
</body>
<%
	if (mapCursos!=null) mapCursos.clear();
	if (mapAlumnos!=null) mapAlumnos.clear();
	if (mapEvaluados!=null) mapEvaluados.clear();
	if (mapPromedio!=null) mapPromedio.clear();
%>
<%@ include file= "../../cierra_enoc.jsp" %>