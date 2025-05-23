<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.graduacion.spring.AlumEvento"%>
<%@page import="aca.graduacion.spring.AlumEgreso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
		
	function cambiaEvento(){		
  		document.forma.submit();  		
	}
</script>

<%
	String eventoId 					= (String)request.getAttribute("eventoId");
	List<AlumEvento> lisEventos 		= (List<AlumEvento>) request.getAttribute("lisEventos");
	List<CatPais> lisPaises 			= (List<CatPais>) request.getAttribute("lisPaises");
	List<AlumPersonal> lisAlumnos 		= (List<AlumPersonal>) request.getAttribute("lisAlumnos");	
	HashMap<String,String> mapaPaises	= (HashMap<String,String>) request.getAttribute("mapaPaises");	
%>
<div class="container-fluid">
	<h2>Graduates by Country</h2>
	<form name="forma" action="graduandos" method="post">
	<div class="alert alert-info d-flex align-items-center">
		Event:
		<select name="EventoId" class="form-select" onchange="javascript:cambiaEvento()" style="width:520px;">
<%	for (AlumEvento evento: lisEventos) { %>
			<option value="<%=evento.getEventoId()%>" <%=evento.getEventoId().equals(eventoId)?"Selected":"" %>>
				<%=evento.getEventoNombre()%> - [<%=evento.getFecha()%>]
			</option>
<%	} %>
		</select>
		&nbsp;
		<%-- <a href="estados?EventoId=<%=eventoId%>" class="btn btn-success">M�xico</a> --%>
	</div>
	</form>
	<table style="width:100%" class="table table-sm table-bordered">	  	
<%	
  	int row = 0;  	
  	for(CatPais pais : lisPaises){
  		row++;
  		String totalAlum  = "0";
  		if(mapaPaises.containsKey(pais.getPaisId()) ){
  			totalAlum = mapaPaises.get(pais.getPaisId());
  		}  	
%>
	<thead>
	<tr>
		<td width="3%"><h5><span class="badge bg-dark"><%=row%></span></h5></td>
		<td colspan="7"><h5><%=pais.getNombrePais()%></h5></td>	
	</tr>		
	<tr class="table-info">
    	<th>&nbsp;</th>
    	<th>#</th>
    	<th>Student ID</th>
    	<th>Name</th>    
    	<th>Gender</th>
    	<th class="right">Age</th>
  	</tr>
  	</thead>
  	<tbody>
 <%	
  		int rowAlumno = 0;  	
  		for(AlumPersonal alumno : lisAlumnos){
  			if ( alumno.getPaisId().equals(pais.getPaisId())){
  				rowAlumno++;
%>
	<tr align="left">
		<td>&nbsp;</td>
		<td><%=rowAlumno%></td>
		<td><%=alumno.getCodigoPersonal()%></td>
		<td><%=alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()+" "+alumno.getNombre()%></td>
		<td><%=alumno.getSexo().equals("F")?"Female":"Male"%></td>
		<td class="right"><%=aca.util.Fecha.edad(alumno.getFNacimiento(), aca.util.Fecha.getHoy())%></td>
	</tr>
<%  				
  			}
  		}
%>
	</tbody>
<%  		
  	}
%>
	</table>
</div>