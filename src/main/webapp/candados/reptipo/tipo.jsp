<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.candado.spring.CandTipo"%>
<%@page import="aca.candado.spring.CandAlumno"%>
<%@page import= "aca.candado.spring.Candado"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">		
	function cambiaPeriodo(){
  		document.location.href="tipo?Periodo="+document.forma.Periodo.value+"&Tipo="+document.forma.Tipo.value;
	}
	
	function cambiaTipo(){
  		document.location.href="tipo?Periodo="+document.forma.Periodo.value+"&Tipo="+document.forma.Tipo.value;
	}		
</script>
<%
	int cont               	= 0;	
	int i               	= 0;
	
	String codigoPersonal 	= (String) session.getAttribute("codigoPersonal");
	String tipoId			= (String) request.getAttribute("tipoId");
	String periodoId		= (String) request.getAttribute("periodoId");	
	
	List<CandTipo> lisTipos  		= (List<CandTipo>) request.getAttribute("lisTipos");	
	List<CandAlumno> lisCandAlum	= (List<CandAlumno>) request.getAttribute("lisCandAlum");
	List<CatPeriodo> lisPeriodos  	= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	
	HashMap<String, CatCarrera> mapaCarreras 		= (HashMap<String, CatCarrera>) request.getAttribute("mapaCarreras"); 
	HashMap<String, CatFacultad> mapaFacultades		= (HashMap<String, CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String, String> mapaInscritos 			= (HashMap<String, String>) request.getAttribute("mapaInscritos");
	HashMap<String, String> mapaCarrerasAlumnos		= (HashMap<String, String>)request.getAttribute("mapaCarrerasAlumnos");
	HashMap<String, Candado> mapaCandados			= (HashMap<String, Candado>)request.getAttribute("mapaCandados");
	HashMap<String, String> mapaAlumnos				= (HashMap<String, String>)request.getAttribute("mapaAlumnos");
%>
<body>
<div class="container-fluid">
	<h2>Students with Locks Report</h2>
	<form name="forma">
	<div class="alert alert-info">
		Period:&nbsp;
		<select class="form-select d-inline-flex" style="width: 15rem;" onchange='javascript:cambiaPeriodo()' name="Periodo">            
<%		
	for (int j=0; j< lisPeriodos.size(); j++){
		CatPeriodo per	= (CatPeriodo) lisPeriodos.get(j);
%>
			<option <%=periodoId.equals(per.getPeriodoId())?" Selected":""%> value="<%=per.getPeriodoId()%>"><%=per.getNombre()%></option>
<%
	}
%>
	  	</select>
		&nbsp;&nbsp;&nbsp;
		Category:&nbsp;
		<select class="form-select d-inline-flex" style="width: 15rem;" name="Tipo" onchange='javascript:cambiaTipo()'>
<%
//Comentado por matr�cula de Julio y Agosto 2020
/**
	for( CandTipo tipo : lisTipos){
		if (tipo.getTipoId().equals(tipoId)){
			out.print(" <option value='"+tipo.getTipoId()+"' Selected>"+ tipo.getNombreTipo()+"</option>");
		}else{
			out.print(" <option value='"+tipo.getTipoId()+"'>"+ tipo.getNombreTipo()+"</option>");
		}
	}
**/

//Se modific� por matr�cula de Julio y Agosto 2020
   	for( CandTipo tipo : lisTipos){
   		if(!tipo.getTipoId().equals("06")){
			if (tipo.getTipoId().equals(tipoId)){
				out.print(" <option value='"+tipo.getTipoId()+"' Selected>"+ tipo.getNombreTipo()+"</option>");
			}else{
				out.print(" <option value='"+tipo.getTipoId()+"'>"+ tipo.getNombreTipo()+"</option>");
			}
   		}
	}
%>					
		</select>
	</div>
	</form>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>	
			<th width="5%" align="center"><spring:message code="aca.Numero"/></th>
		    <th width="15%" align="center">School</th>
		    <th width="10%" align="center">Student ID</th>
		    <th width="15%" align="center"><spring:message code="aca.Nombre"/></th>
			<th width="15%" align="center">Lock</th>
		    <th width="7%" align="center">Date Active</th>
			<th width="10%" align="center">User</th>
			<th width="20%" align="center"><spring:message code="aca.Comentario"/></th>
			<th width="5%" align="center"><spring:message code="aca.Inscrito"/></th>
		</tr>
		</thead>
		<tbody>
<%			
	for(i=0; i<lisCandAlum.size(); i++){ 
		cont++;
		CandAlumno candAlum = (CandAlumno) lisCandAlum.get(i);
		String carreraId = "0";
		if (mapaCarrerasAlumnos.containsKey(candAlum.getCodigoPersonal())){
			carreraId = mapaCarrerasAlumnos.get(candAlum.getCodigoPersonal());
		}
		
	  	String facultadId = "";
	  	String facultad = "-";
	  	if(mapaCarreras.containsKey(carreraId)){
			facultadId = mapaCarreras.get(carreraId).getFacultadId();
		  	if(mapaFacultades.containsKey(facultadId)){
				facultad = mapaFacultades.get(facultadId).getNombreFacultad();
		  	}
	  	}	  
	  	String inscrito = "NO";
	  	if (mapaInscritos.containsKey(candAlum.getCodigoPersonal())){
			inscrito = "YES";
	  	}
	  	
	  	String alumno = "-";
	  	if (mapaAlumnos.containsKey(candAlum.getCodigoPersonal())){
			alumno = mapaAlumnos.get(candAlum.getCodigoPersonal());
	  	}
	  	
 	  	String nombreCandado = "-";
	  	if (mapaCandados.containsKey(candAlum.getCandadoId())){
	  		nombreCandado = mapaCandados.get(candAlum.getCandadoId()).getNombreCandado();
	  	}
%>				
		<tr>
			<td align="center"><%=cont%></td>
		  	<td align="left"><%=facultad%></td>
		  	<td align="center"><%=candAlum.getCodigoPersonal()%></td>
		  	<td align="left"><%=alumno%></td>    
		  	<td align="center"><%=nombreCandado%></td>  
		  	<td align="center"><%= candAlum.getfCreado() %></td>	    
		    <td align="center"><%= candAlum.getUsAlta() %></td>
		    <td align="left"><%= candAlum.getComentario()%></td>
		    <td align="center"><%= inscrito %></td>    
		</tr>	
<% 		
	}			
%>
		</tbody>
	</table>	 
</div>
</body>