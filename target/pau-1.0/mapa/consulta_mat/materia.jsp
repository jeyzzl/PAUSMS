<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatTipoCurso"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.plan.spring.MapaCursoPre"%>
<%@ page import= "aca.plan.spring.MapaNuevoPlan"%>
<%@ page import= "aca.plan.spring.MapaNuevoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<script type="text/javascript">
	function Borrar(cursoId ){
		var planId = document.plan.planId.value;
		if(confirm("Are you sure you want to delete the record?: "+cursoId)==true){
	  		document.location="accion.jsp?Accion=4&cursoId="+cursoId+"&planId="+planId;
	  	}
	}
</script>
<%
	java.text.DecimalFormat getformato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String planId			= request.getParameter("planId");
	String facultad			= request.getParameter("facultad");
	String versionPlan		= (String) request.getAttribute("versionPlan");
	String planOrigen		= (String) request.getAttribute("planOrigen");
	
	String creditos			= "";
	String ht				= "";
	String hp				= "";
	String hh				= "";
	String hfd				= "";
	String fCreada			= "";
	String notaAprobatoria 	= "";
	String estado			= "";
	
	int  nCiclo				= 0;
	int cont				= 0;
	String sBgcolor	= "";
	
	List<MapaCurso> lisCursos						= (List<MapaCurso>)request.getAttribute("lisCursos");
	List<MapaCursoPre> lisPrerrequisitos			= (List<MapaCursoPre>)request.getAttribute("lisPrerrequisitos");
	
	HashMap<String,MapaCurso> mapaCursos			= (HashMap<String,MapaCurso>)request.getAttribute("mapaCursos");
	HashMap<String,MapaNuevoPlan> mapaNuevosPlanes	= (HashMap<String,MapaNuevoPlan>)request.getAttribute("mapaNuevosPlanes");	
	HashMap<String,MapaNuevoCurso> mapaNuevosCursos	= (HashMap<String,MapaNuevoCurso>)request.getAttribute("mapaNuevosCursos");
	HashMap<String,CatTipoCurso> mapaTipos			= (HashMap<String,CatTipoCurso>)request.getAttribute("mapaTipos");	
	HashMap<String,String> mapaReprobados			= (HashMap<String,String>)request.getAttribute("mapaReprobados");
	HashMap<String,String> mapaRegistrados			= (HashMap<String,String>)request.getAttribute("mapaRegistrados");
	HashMap<String,String> mapaPromedioPorMateria 	= (HashMap<String, String>)request.getAttribute("mapaPromedioPorMateria");
%>
<div class="container-fluid">
	<h2>List of subjects for: <%=planId%>&nbsp;</h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="listado?facultad=<%=facultad%>"><spring:message code="aca.Regresar"/></a>
		&nbsp; &nbsp;
		<input type="text" class="form-control search-query" placeholder="Search" id="buscar" style="width:200px;">
		&nbsp; &nbsp;		
<!-- 		<strong> -->
<%-- <% 	if(planId.contains("2010")){%> --%>
<%-- 	   	<a class="btn btn-info" href="muestraPlanPdf?planId=<%=planId%>&versionId=<%=versionPlan%>">PDF/Plan</a> --%>
<%-- <% 	}else{%> --%>
<%-- 	   	<a class="btn btn-info" href="muestraPlanPdfNuevo?planId=<%=planId%>&versionId=<%=versionPlan%>">PDF/Plan</a> --%>
<%-- <% 	}%> --%>
<!-- 		</strong> -->
	</div>
  	<form name="plan">
	  	<input name="planId" type="hidden" value="<%=planId%>">
 	</form>
	<table id="table" class="table table-bordered table-sm">
   	<thead class="table-info">
 	<tr> 
	    <th><h5><spring:message code="aca.Op"/></h5></th>
	    <th><h5><spring:message code="aca.Numero"/></h5></th>
	    <th><h5><spring:message code="aca.Ciclo"/></h5></th>
	    <th><h5><spring:message code="aca.Clave"/></h5></th>
	    <th><h5><spring:message code="aca.Nombre"/></h5></th>
	    <th><h5 title="Alumnos registrados">Reg. St.</h5></th>
	    <th><h5 title="Número de reprobados">Failed St.</h5></th>
		<th><h5><spring:message code="aca.Prom"/> Mark</h5></th>
	    <th><h5><spring:message code='aca.Prerrequisito'/>s</h5></th>
	    <th><h5><spring:message code='aca.Creditos'/></h5></th>
	    <th><h5>HT</h5></th>
	    <th><h5>HP</h5></th>
	    <th><h5>HH</h5></th>
	    <th><h5>HFD</h5></th>
	    <th><h5><spring:message code="aca.Fecha"/> created</h5></th>
	    <th><h5>Pass. Grade</h5></th>
	    <th><h5>Subj. Type</h5></th>
	    <th><h5>Timetable</h5></th>							    
	    <th><h5>Classroom</h5></th>		
  	</tr>
	</thead>
	<tbody>
<%
	for (MapaCurso curso : lisCursos){
		
		nCiclo = Integer.parseInt(curso.getCiclo());
		
		cont++;
		
		if(curso.getCreditos()!=null){
			creditos = curso.getCreditos();
		}
		if(curso.getHt()!=null){
			ht = curso.getHt();
		}
		if(curso.getHp()!=null){
			hp = curso.getHp();
		}
		if(curso.getHh()!=null){
			hh = curso.getHh();
		}
		if(curso.getHfd()!=null){
			hfd = curso.getHfd();
		}
		if(curso.getfCreada()!=null){
			fCreada	= curso.getfCreada();
		}
		if(curso.getNotaAprobatoria()!=null){
			notaAprobatoria = curso.getNotaAprobatoria();
		}
		if(curso.getEstado()!=null){
			estado = curso.getEstado();
		}
		
		// Exception para buscar las materias del programa de musica de la UM virtual
		String planNuevo		= planOrigen;
		String nuevoCursoId 	= "0";
		String version 			= "0";
		String maestros 		= "";					
		if (mapaNuevosCursos.containsKey(curso.getCursoNuevo())){
			nuevoCursoId 		= mapaNuevosCursos.get(curso.getCursoNuevo()).getCursoId();
			version				= mapaNuevosCursos.get(curso.getCursoNuevo()).getVersionId();
			maestros 			= mapaNuevosCursos.get(curso.getCursoNuevo()).getCodigoMaestro();
			planNuevo 			= mapaNuevosCursos.get(curso.getCursoNuevo()).getPlanId();
		}
		
		if(maestros.length()>1){
			maestros = maestros.substring(1,maestros.length()-1).replaceAll("-", ",");
		}
		
		String tipoNombre = "-";
		if(mapaTipos.containsKey(curso.getTipoCursoId())){
			tipoNombre = mapaTipos.get(curso.getTipoCursoId()).getNombreTipoCurso();
		}

		String promedio = "0";
		String colorPromedio = "<span class='badge bg-secondary rounded-pill'>0</span>";
		if(mapaPromedioPorMateria.containsKey(curso.getCursoId())){
			promedio = mapaPromedioPorMateria.get(curso.getCursoId());
			if (Float.valueOf(promedio)<Float.valueOf(curso.getNotaAprobatoria())){
				colorPromedio = "<span class='badge bg-danger rounded-pill'>"+getformato.format(Float.valueOf(promedio))+"</span>";
			}else if (Float.valueOf(promedio) < 85){
				colorPromedio = "<span class='badge bg-warning rounded-pill'>"+getformato.format(Float.valueOf(promedio))+"</span>";
			}else{
				colorPromedio = "<span class='badge bg-success rounded-pill'>"+getformato.format(Float.valueOf(promedio))+"</span>";
			}
		}
		
		boolean tieneAlumnos = false;
		String numRegistrados = "<span class='badge bg-warning rounded-pill'>0</span>";
		if (mapaRegistrados.containsKey(curso.getCursoId())){
			numRegistrados = "<span class='badge bg-dark rounded-pill'>"+mapaRegistrados.get(curso.getCursoId())+"</span>";
			tieneAlumnos = true;
		}
%>
  		<tr> 
  			<td align="center">
<% 		if(!nuevoCursoId.equals("0")){%>   			
<%			if(mapaNuevosPlanes.containsKey(planNuevo)){
				if(mapaNuevosPlanes.get(planNuevo).getYear().equals("2017")){ %> 
  						<a title="<%=maestros%>" href="muestraMateriaPdfNuevo?planId=<%=planNuevo%>&versionId=<%=version%>&cursoId=<%=nuevoCursoId%>"><i class="fas fa-file-pdf" style="color:black"></i></a>
<% 							}else{%>
  						<a title="<%=maestros%>" href="muestraMateriaPdf?planId=<%=planNuevo%>&versionId=<%=version%>&cursoId=<%=curso.getCursoNuevo()%>"><i class="fas fa-file-pdf" style="color:black"></i></a>
<% 							}
			}else{
				out.print("Not found:"+planNuevo);
			}
			
		}else{
			out.print("<i class='fas fa-exclamation-circle' style='color:orange'></i>");
		}

		int repro 			= 0;
		String colorRepro	= "bg-dark";
		if(mapaReprobados.containsKey(curso.getCursoId())){
			repro 		= Integer.parseInt(mapaReprobados.get(curso.getCursoId()));
			if (repro>=1) colorRepro 	= "bg-warning"; 
		}		
%>
   			</td>
		    <td align="center"><%=cont%></td>
		    <td align="center"><%=nCiclo%></td>
		    <td title="<%=curso.getCursoId()%>"><%=curso.getCursoClave() %></td>
		    <td><a href="prerrequisito?idCurso=<%=curso.getCursoId()%>&ciclo=<%=nCiclo%>&planId=<%=planId%>&facultad=<%=facultad%>&paso=1"><%=curso.getNombreCurso()%></a></td>
		    <td align="center">
		    	<a href="listaAlumnos?CursoId=<%=curso.getCursoId()%>&PlanId=<%=planId%>&facultad=<%=facultad%>"><%=numRegistrados%></a>
		    </td>
		    <td align="center" title="Alumnos reprobados"><a href="listaReprobados?CursoId=<%=curso.getCursoId()%>&PlanId=<%=planId%>&facultad=<%=facultad%>"><span class="badge <%=colorRepro%> rounded-pill"><%=repro%></span></a></td>
			<td align="center"><%=colorPromedio%></td>
		    <td align="center">
<%
        String prerrequisitos = " ";
        for (MapaCursoPre pre : lisPrerrequisitos){
        	
        	// Si es el curso actual, es decir si tiene prerrequisitos
        	if (pre.getCursoId().equals(curso.getCursoId())){
        		
        		// busca el curso clave de la materia que es prerrequisito del curso actual
        		if (mapaCursos.containsKey(pre.getCursoIdPre())){			        			
        			prerrequisitos += mapaCursos.get(pre.getCursoIdPre()).getCursoClave()+", ";
        		}			        		
        	}
        }
        if (prerrequisitos.equals(" ")) prerrequisitos = "-"; 
        out.print(prerrequisitos);%>
    		</td>
		    <td align="center"><%=creditos%></td>
		    <td align="center"><%=ht%></td>
		    <td align="center"><%=hp%></td>
		    <td align="center"><%=hh%></td>
		    <td align="center"><%=hfd%></td>
		    <td align="center"><%=fCreada%></td>
		    <td align="center"><%=notaAprobatoria%></td>
		    <td align="center"><%=tipoNombre%></td>
		    <td class="text-center"><%=curso.getHorario().equals("S")?"<span class='badge bg-success'><i class='fas fa-check'></i></span>":"<span class='badge bg-secondary'><i class='fas fa-times'></i></span>"%></td>
   			<td class="text-center"><%=curso.getSalon().equals("S")?"<span class='badge bg-success'><i class='fas fa-check'></i></span>":"<span class='badge bg-secondary'><i class='fas fa-times'></i></span>"%></td>
 			</tr>
<%	
			 creditos			= "";
			 ht					= "";
			 hp					= "";
			 fCreada			= "";
			 notaAprobatoria 	= "";
			 estado				= "";
		}				
%>
		</tbody>
	</table>			
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>