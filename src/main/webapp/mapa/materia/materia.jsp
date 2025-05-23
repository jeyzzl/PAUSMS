<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.catalogo.spring.CatTipoCurso"%>
<%@ page import= "aca.plan.spring.MapaCursoPre"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../css/style.css" />
<script>
	function Borrar(cursoId){
		var planId = document.frmMateria.PlanId.value;
		if(confirm("Are you sure you want to delete the record?: "+cursoId)==true){
	  		document.location.href = "borrarCurso?cursoId="+cursoId+"&planId="+planId;
	  	}
	}
	function EditarDatos(planId){
		document.location.href = "materia?Accion=1&planId="+planId;
	}
	
	function GrabarDatos(){
		document.frmMateria.submit();
	}
	
	function EliminarCursoNuevo(cursoId,planId,cursoNuevo){
		if(confirm("Are you sure you want to remove the linkage with "+cursoNuevo)==true){
	  		document.location.href = "eliminarCursoNuevo?CursoId="+cursoId+"&planId="+planId;
	  	}
	}
</script>
<%
	String planId				= request.getParameter("planId")==null?"0":request.getParameter("planId");
	String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String facultad				= (String) session.getAttribute("fac");	
	String nombrePlan			= (String) request.getAttribute("nombrePlan");	
	String mensaje				= (String) request.getAttribute("mensaje");
	String planOrigen			= (String) request.getAttribute("planOrigen");
	
	List<MapaCurso> lisCursos 					= (List<MapaCurso>) request.getAttribute("lisCursos");
	List<MapaCursoPre> lisPrerrequisitos		= (List<MapaCursoPre>)request.getAttribute("lisPrerrequisitos");
	
	HashMap<String,MapaCurso> mapaCursos			= (HashMap<String,MapaCurso>)request.getAttribute("mapaCursos");
	HashMap<String, CatTipoCurso> mapaTipos			= (HashMap<String, CatTipoCurso>) request.getAttribute("mapaTipos");
	HashMap<String, String> mapaUsados				= (HashMap<String, String>) request.getAttribute("mapaUsados");	
	HashMap<String,String> mapaCursosPorPlan		= (HashMap<String,String>) request.getAttribute("mapaCursosPorPlan");
	
	boolean tieneMaterias 		= (boolean) request.getAttribute("tieneMaterias");	
	int  ciclo					= 0;
%>
<body>
<div class="container-fluid">
	<h3>Subjects for <small class="text-secondary"><b><%=planId%></b> - <i><%=nombrePlan%></small></i></h3>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="listado?planId=<%=planId%>&facultad=<%=facultad%>"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<a class="btn btn-success" href="accion?planId=<%=planId%>"><i class="fas fa-plus"></i> <spring:message code="aca.Anadir"/> Subject</a>&nbsp;&nbsp;
<% 	if (accion.equals("0")){%>		
		<a class="btn btn-success" href="javascript:EditarDatos('<%=planId%>')">Edit Cycles</a>&nbsp;&nbsp;
<% 	}else{%>
		<a class="btn btn-primary" href="javascript:GrabarDatos()">Save</a>&nbsp;&nbsp;
<% 	}%>				
<% 	if (!mapaCursosPorPlan.containsKey(planId) && tieneMaterias){%>
		<a class="btn btn-warning" href="traspasar?PlanId=<%=planId%>"><spring:message code="aca.Traspasar"/></a>&nbsp;&nbsp;
<%	}%>
		<input type="text" class="form-control search-query" placeholder="<spring:message code='aca.Buscar'/> Subject" id="buscar" style="width:200px;">		
	</div>
<% 	if (mensaje.equals("2")){%>
	<div class="alert alert-danger">
		<spring:message code="mapa.materia.MensajeSalonesSinHorario"/>
	</div>
<%	}%>		
	<form name="frmMateria" method="post" action="grabarDatos">
	<input name="PlanId" type="hidden" value="<%=planId%>">
		  		
 	<table id="table" class="table table-bordered table-sm table-striped">
    <thead>
		<tr class="table-info"> 
    		<th width="3%"><h3><spring:message code="aca.Op"/></h3></th>
		    <th width="3%"><h3><spring:message code="aca.Numero"/></h3></th>
		    <th width="4%"><h3>Cycle</h3></th>
		    <th width="4%"><h3>Order</h3></th>
		    <th width="5%"><h3>Sub. <spring:message code="aca.Clave"/></h3></th>
		    <th width="30%"><h3>Subject <spring:message code="aca.Nombre"/></h3></th>
		    <th width="5%"><h3>Prerequisites</h3></th>
		    <th width="5%"><h3>Credits</h3></th>
		    <th width="5%"><h3>Created</h3></th>
		    <th width="7%"><h3>Type</h3></th>
		    <th width="3%"><h3>Req.</h3></th>
		    <th width="5%"><h3>Timetabled</h3></th>
		    <th width="5%"><h3><spring:message code="aca.Salon"/></h3></th>
		</tr>
	</thead>
	<tbody>
<%	
	int row	= 0;
	for(MapaCurso curso : lisCursos){					
		row++;
		ciclo = Integer.parseInt(curso.getCiclo());
		String tipoNombre = "-";
		if (mapaTipos.containsKey(curso.getTipoCursoId())){
			tipoNombre = mapaTipos.get(curso.getTipoCursoId()).getNombreTipoCurso();				
		}
		String colorCiclo = "";
		if (ciclo%2==1) colorCiclo = "<span class='badge bg-dark'>"+ciclo+"</span>"; else colorCiclo = "<span class='badge bg-secondary'>"+ciclo+"</span>";							
%>
  		<tr> 
			<td style="text-align: center">
				<a href="accion?Accion=2&planId=<%=curso.getPlanId()%>&cursoId=<%=curso.getCursoId()%>&facultad=<%=facultad%>" class="fas fa-edit" title="<spring:message code='aca.Modificar'/>"></a>
<%		if (mapaUsados.containsKey(curso.getCursoId())==false){	%>			
				&nbsp;<a href="javascript:Borrar('<%=curso.getCursoId()%>')" class="fas fa-trash-alt" title="<spring:message code='aca.Eliminar'/>"></a>
<%		}%>			
			</td>
	    	<td align="center"><%=row%></td>
	    	<td align="center">
<% 		if (accion.equals("0")){%>	    	
	    		<%=colorCiclo%>
<%		}else{%>
			<input name="Ciclo-<%=curso.getCursoId()%>" type="text" class="form-control" value="<%=curso.getCiclo()%>">
<%		}%>	    		
	    	</td>
	    	<td align="center"><%=curso.getOrden()%></td>
	    	<td align="center"><%=curso.getCursoClave()%></td>
	    	<td><a href="prerrequisito?idCurso=<%=curso.getCursoId()%>&ciclo=<%=ciclo%>&planId=<%=planId%>&facultad=<%=facultad%>&paso=1"><%=curso.getNombreCurso()%></a></td>		    								    
		  	<td align="center">
<%
		String info = "";
        String prerrequisitos = " ";
        for (MapaCursoPre pre : lisPrerrequisitos){
        	// Si es el curso actual, es decir si tiene prerrequisitos
        	if (pre.getCursoId().equals(curso.getCursoId())){
        		// busca el curso clave de la materia que es prerrequisito del curso actual
        		if (mapaCursos.containsKey(pre.getCursoIdPre())){			        			
        			prerrequisitos += mapaCursos.get(pre.getCursoIdPre()).getCursoClave()+", ";
		        	info += " ("+mapaCursos.get(pre.getCursoIdPre()).getCursoClave()+"-"+mapaCursos.get(pre.getCursoIdPre()).getNombreCurso()+") -";
        		}			        		
        	}
        }
        
           int cantidadElimina = 2;
        if(prerrequisitos.endsWith(", ")){
            int m = Math.max(0, prerrequisitos.length() - cantidadElimina); 
        	prerrequisitos = prerrequisitos.substring(0, prerrequisitos.length()-cantidadElimina);
        }

        if(info.endsWith(" -")){
            int m = Math.max(0, info.length() - cantidadElimina); 
            info = info.substring(0, info.length()-cantidadElimina);
        }
        
        if (prerrequisitos.equals(" ")) prerrequisitos = "-";
%>
				<span data-bs-toggle="tooltip" data-placement="top" title="<%=info%>"><%=prerrequisitos%></span>
       			</td>
			<td align="center"><%=curso.getCreditos()==null?"0":curso.getCreditos()%></td>
			<td align="center"><%=curso.getfCreada()==null?aca.util.Fecha.getHoy():curso.getfCreada()%></td>
			<td align="center">
<%		if(curso.getTipoCursoId().equals("2")){ %>
			<a href="optativa?Plan=<%=planId%>&Facultad=<%=facultad%>&Semestre=<%=ciclo%>&Curso=<%=curso.getCursoId()%>"><%=tipoNombre%></a>
<%		}else{%>
			<%=tipoNombre%>
<%		} %>
   			</td>
   			<td><%=curso.getObligatorio().equals("S")?"<span class='badge bg-success'>YES</span>":"<span class='badge bg-secondary'>NO</span>"%></td>
<!--    			<td>				    		 -->
<%--    				<input class="todos ciclo<%=curso.getCiclo()%>" type="checkbox" name="<%=curso.getCursoId()%>" value="S" <%=curso.getLaboratorio().equals("S")?"checked":""%>>				    	 --%>
<!--    			</td> -->
   			<td>
   				<input type="checkbox" name="Hora<%=curso.getCursoId()%>" value="S" <%=curso.getHorario().equals("S")?"checked":""%>>
   			</td>
   			<td>
   				<input type="checkbox" name="Salon<%=curso.getCursoId()%>" value="S" <%=curso.getSalon().equals("S")?"checked":""%>>
   			</td>
		</tr>
<%	} %>  
	</tbody>
	</table>	
	</form>
</div>
<script src="../../js/search.js"></script>
<script type="text/javascript">
	$('#buscar').search();	
	$(function () {
		$('[data-toggle="tooltip"]').tooltip()
	})
</script>
</body>