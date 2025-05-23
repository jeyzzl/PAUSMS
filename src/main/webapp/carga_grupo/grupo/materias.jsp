<%@ page import= "java.util.List"%>
<%@ page import= "java.util.ArrayList"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.vista.spring.CargaAcademica"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<head></head>
<%
	String codigoPersonal	 = (String) session.getAttribute("codigoPersonal");
	String cargaId 			 = request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");	
	String planId 			 = request.getParameter("PlanId")==null?"0":request.getParameter("PlanId");	
	
	List<MapaCurso> lisMaterias		 				= (List<MapaCurso>)request.getAttribute("lisMaterias");
	List<CargaAcademica> lisOfertadas				= (List<CargaAcademica>)request.getAttribute("lisOfertadas");	
	HashMap<String,String> mapaRegistradas			= (HashMap<String,String>) request.getAttribute("mapaRegistradas");
	HashMap<String,CatModalidad> mapaModalidades	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
%>
<body>
<div class="container-fluid">
	<h2>Subject List <small class="text-muted fs-5">( Load: <%=cargaId%>&nbsp; Plan: <%=planId%> )</small></h2>		
	<div class="alert alert-info d-flex align-items-center"> 
		<a class="btn btn-primary" href="elegir?Carga=<%=cargaId%>"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<input type="text" class="form-control search-query d-flex"  placeholder="Buscar" id="buscar" style="width:200px;">
	</div>			
	<table id="tableGrupo" class="table table-sm table-striped table-bordered">
	<thead>		     
	<tr>
		<th><spring:message code="aca.Numero"/></th>
		<th>Code</th>
		<th>Name</th>	
		<th><spring:message code="aca.Crd"/>.</th>
		<th>Hr</th>	
		<th>#Reg.</th>
		<th>Modalities</th>
		<th>Lecturer</th>
	</tr>
	</thead>
	<tbody>			
<%	
	int row = 0;
	String ciclo= "0";
	for (MapaCurso  mapaCurso : lisMaterias){
		row++;
		String total = "0";
		if (mapaRegistradas.containsKey(mapaCurso.getCursoId())){
			total = mapaRegistradas.get(mapaCurso.getCursoId());
		}
		if (!ciclo.equals(mapaCurso.getCiclo())){
			ciclo = mapaCurso.getCiclo();
			out.print("<tr class='table-dark'><td colspan='8'><h5>Semester:"+ciclo+"</h5></td></tr>");
		}
		List<String> lisMod 		= new ArrayList<String>();
		List<String> lisMaestros 	= new ArrayList<String>();
		String modalidades 	= "-";
		String maestros 	= "-";
		String creditos 	= "";
		for (CargaAcademica oferta : lisOfertadas){
			if (mapaCurso.getCursoId().equals(oferta.getCursoId())){
				
				if (!lisMod.contains(oferta.getModalidadId())){
					lisMod.add(oferta.getModalidadId());
					if (mapaModalidades.containsKey(oferta.getModalidadId())){
						if (modalidades.equals("-")) modalidades = mapaModalidades.get(oferta.getModalidadId()).getNombreModalidad(); else modalidades += " - "+mapaModalidades.get(oferta.getModalidadId()).getNombreModalidad(); 
					}					 
				}
				if (!lisMaestros.contains(oferta.getCodigoPersonal())){
					lisMaestros.add(oferta.getCodigoPersonal());
					if (maestros.equals("-")) maestros = oferta.getNombre(); else maestros += " - "+oferta.getNombre();
				}
			}
			creditos = oferta.getCreditos();
		}
%>  
		<tr> 
			<td><%=row%></td>					
			<td><%=mapaCurso.getCursoId() %></td>
			<td><%=mapaCurso.getNombreCurso()%></td>	
			<td><%=creditos %></td>
			<td><%="<span class='badge bg-dark rounded-pill'>"+mapaCurso.getHh()+"</span>"%></td>					
			<td><span class="badge <%=total.equals("0")?"bg-warning":"bg-dark"%> rounded-pill"><%=total%></span></td>
			<td><%=modalidades%></td>
			<td><%=maestros%></td>
		</tr>
<%	} %>
	<tbody>						
	</table>	
</div>
</body>
<script type="text/javascript" src="../../js/search.js"></script>
<script type="text/javascript">
	jQuery('#buscar').focus().search({table:jQuery("#tableGrupo")});
</script>