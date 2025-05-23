<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.disciplina.spring.CondAlumno"%>
<%@page import="aca.disciplina.spring.CondReporte"%>
<%@page import="aca.mentores.spring.MentAlumno"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<script type="text/javascript">
	function Borrar( codigo, periodo, folio){
		if(confirm("Are you sure you want to delete this record: "+folio)==true){
	  		document.location="grabar?Accion=4&codigoPersonal="+codigo+"&Periodo="+periodo+"&folio="+folio;
	  	}
	}
</script>
<%
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno			= request.getParameter("codigoAlumno")==null?"0":request.getParameter("codigoAlumno");
	String folio				= request.getParameter("folio")==null?"0":request.getParameter("folio");
	String cargaId				= request.getParameter("CargaId")==null?"0":request.getParameter("CargaId");
	String periodoId			= (String) request.getAttribute("periodoId");
	String alumnoNombre			= (String) request.getAttribute("alumnoNombre");

	
	List<Carga> lisCargaAlumnos			 	= (List<Carga>) request.getAttribute("lisCargaAlumnos");
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>) request.getAttribute("lisPeriodos");	
	
	HashMap<String,String> mapaMaestros			= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String,String> mapaMateriasPorCarga	= (HashMap<String,String>) request.getAttribute("mapaMateriasPorCarga");
%>
<div class="container-fluid">
	<h2>Loads<small class="text-muted fs-5"> ( <%=codigoAlumno%> - <%=alumnoNombre%> )</small></h2>
	<form id="forma" name="forma" action="unidad" method="post">
	<div class="alert alert-info">	
    	<a class="btn btn-primary" href="portal?PeriodoId=<%=periodoId%>"><spring:message code='aca.Regresar'/></a>&nbsp;&nbsp;
	</div>  
	<table class="table table-sm">
  	<tr> 
    	<th width="3%" height="21">#</th>
    	<th width="3%" height="21">&nbsp;</th>
    	<th width="7%">Load ID</th>
    	<th width="60%">Load Name</th>
    	<th width="24%" class="text-center">#Subjects</th>
  	</tr>
<%	
	for(int i=0; i<lisCargaAlumnos.size(); i++){
		
		Carga carga 	= (Carga)lisCargaAlumnos.get(i);				
		
		String numMaterias = "0";
		if(mapaMateriasPorCarga.containsKey(carga.getCargaId()) ){
			numMaterias=mapaMateriasPorCarga.get(carga.getCargaId());
		}
		String cargaIcono	= "";
		if (cargaId.equals(carga.getCargaId())){
			cargaIcono	= "<i class='fas fa-check-circle' ></i>";
		}
%>
	<tr> 
    	<td><%=i+1%></td>
    	<td><%=cargaIcono%></td>
    	<td><a href="modificarCarga?cargaId=<%=carga.getCargaId()%>&codigoAlumno=<%=codigoAlumno%>&folio=<%=folio%>"><%=carga.getCargaId()%></a></td>
    	<td><%=carga.getNombreCarga() %></td>    	
   		<td align="center"><%=numMaterias%></td>       	
  	</tr>
  
<%
	}
	
%>
	</table>
	</form>
</div>
