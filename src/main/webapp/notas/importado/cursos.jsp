<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.kardex.spring.KrdxCursoImp"%>
<%@ page import= "aca.catalogo.spring.CatTipoCal"%>
<%@ page import="aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

 <link rel="stylesheet" href="../../css/style.css" />
<script>
	function Eliminar(folio){
		if(confirm("Are you sure to delete the record?")==true){
			document.location.href="borrar?Folio="+folio;
		}	
	}
</script>
<%
	//variables
	String codigoAlumno	 	= (String)session.getAttribute("codigoAlumno");

    String nombreAlumno	 	= (String)request.getAttribute("nombreAlumno");	
	String planId 		 	= (String)request.getAttribute("planId");
	String nombreCarrera 	= (String)request.getAttribute("nombreCarrera");
	String carreraId	 	= (String)request.getAttribute("carreraId");
	String nota 			= "0";
	
	List<CatTipoCal> lisTipos				= (List<CatTipoCal>)request.getAttribute("lisTipos");
	List<KrdxCursoImp> lisImportados		= (List<KrdxCursoImp>)request.getAttribute("lisImportados");
	HashMap<String,String> mapaEmpleados	= (HashMap<String,String>)request.getAttribute("mapaEmpleados");
	HashMap<String,MapaCurso> mapaCursos	= (HashMap<String,MapaCurso>)request.getAttribute("mapaCursos");
	HashMap<String, String> mapaGradePoint	= (HashMap<String,String>) request.getAttribute("mapaGradePoint");
%>
<html>
<head>
	<title><spring:message code='aca.DocumentoSinTitulo'/></title>
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<div class="container-fluid">
	<h2>Imported Subjects <small class="text-muted fs-5"> (<b><%=codigoAlumno%></b> <%=nombreAlumno%> - <i><%=planId%></i> - <%=nombreCarrera%>)</small></h2>
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="accion"><spring:message code='aca.Añadir'/> <spring:message code='aca.Materia'/> <i class="fas fa-plus"></i></a>&nbsp;&nbsp;
		<input type="text" class="form-control search-query" placeholder="Search..." id="buscar" style="width:200px;">	
		&nbsp;&nbsp;
		<a class="btn btn-primary" href="alumnos" title="List of students"><i class="fas fa-users"></i></a>
	</div>	
 	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		  <tr>
		    <th width="3%"><h5><spring:message code="aca.Numero"/></h5></th>
		    <th width="5%"><h5>Op.</h5></th>
		    <th width="5%"><h5><spring:message code="aca.Plan"/></h5></th>
		    <th width="5%"><h5>Semester</h5></th>
		    <th width="5%"><h5>Sub. ID</h5></th>
		    <th width="30%"><h5>Subject <spring:message code="aca.Nombre"/></h5></th>
		    <th width="6%"><h5><spring:message code="aca.Fecha"/></h5></th>
		    <th width="4%"><h5><spring:message code="aca.Nota"/></h5></th>
		    <th width="4%"><h5>Grade</h5></th>		    		    
		    <th width="3%"><h5><spring:message code="aca.Tipo"/></h5></th>
		    <th width="25%"><h5>Observations</h5></th>
		    <th width="10%"><h5><spring:message code='aca.Usuario'/></h5></th>				    
		  </tr>
	</thead>
	<tbody>
<%	
	for(int i=0; i < lisImportados.size(); i ++){
		KrdxCursoImp cursoImport = (KrdxCursoImp) lisImportados.get(i);		
		
		String notaConva	= cursoImport.getNotaConva();
		nota 				= String.valueOf(cursoImport.getNota()); 
		
		String nombre = "Does not exist Employee";
		if(mapaEmpleados.containsKey(cursoImport.getUsuario())){							
			nombre = mapaEmpleados.get(cursoImport.getUsuario());
		}
		
		String ciclo 		= "0";
		String cursoNombre 	= "-";
		String clave 		= "-";
		String planCurso	= "-";		
		if (mapaCursos.containsKey(cursoImport.getCursoId())){			
			ciclo 		= mapaCursos.get(cursoImport.getCursoId()).getCiclo();
			cursoNombre = mapaCursos.get(cursoImport.getCursoId()).getNombreCurso();
			clave 		= mapaCursos.get(cursoImport.getCursoId()).getCursoClave();
			planCurso 	= mapaCursos.get(cursoImport.getCursoId()).getPlanId();
		}	
		
		String gradePointNombre = "";
		String gradePointValor	= "";
		if (mapaGradePoint.containsKey(nota)){
			gradePointNombre 	= mapaGradePoint.get(nota);
			gradePointValor 	= gradePointNombre.split(";")[1];
			gradePointNombre	= gradePointNombre.split(";")[0];
		}
		String typeCal ="";
		for(int j= 0; j<lisTipos.size(); j++){
			CatTipoCal tipocalf	= (CatTipoCal) lisTipos.get(j);
			if(tipocalf.getTipoCalId().equals(cursoImport.getTipoCalId())){
				typeCal = tipocalf.getNombreTipoCal();	
			}	
		}		
	%>  
	  <tr class="tr2">
	    <td align="center"><span><%=i+1%></span></td>
	    <td align="center">
	    	<a href="curso_update?Folio=<%=cursoImport.getFolio()%>"><i class="fas fa-edit"></i></a>&nbsp;
	    	<a href="javascript:Eliminar(<%=cursoImport.getFolio()%>);"><i class="fas fa-trash-alt" style="color:red"></i></a>
	    </td>
	    <td><span><%=planCurso%></span></td>
	    <td align="center"><span><%=ciclo%></span></td>
	    <td><span><%=clave%></span></td>
	    <td><span><%=cursoNombre%></span></td>
	    <td align="center"><span><%=cursoImport.getFCreada()%></span></td>
	    <td align="center"><span><%=nota%></span></td>
	    <td align="center"><span><%=gradePointNombre%></span></td>	    
	    <td align="center"><span><%=typeCal%></span></td>
	    <td><span><%=cursoImport.getObservaciones()%></span></td>
	    <td><span title="<%=nombre%>" ><%=cursoImport.getUsuario()%></span></td>
	  </tr>
<%
	}
%>
	</tbody>
	</table>
</div>
</body>
</html>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>