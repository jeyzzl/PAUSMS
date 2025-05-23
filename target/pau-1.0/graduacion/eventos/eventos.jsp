<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.graduacion.spring.AlumEvento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String mensaje						= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	List<AlumEvento> lisEvento 			= (List<AlumEvento>) request.getAttribute("lisEvento");
	HashMap<String,String> mapaAlumnos	= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
%>
<head>
	<script type="text/javascript">
		function borrar(eventoId){
			if(confirm("¿Está seguro que desea eliminar el evento?")){
				document.location.href = 'borrarEvento?EventoId='+eventoId;
			}
		}		
	</script>
</head>
<body>
<div class="container-fluid">
	<h2>Eventos de graduaci&oacute;n</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="editar"><spring:message code='aca.Nuevo'/></a>
	</div>
<%	
	if (!mensaje.equals("-")){
		out.print("<div class='alert alert-info'>"+mensaje+"</div>");		
	}	
%>	
	<table class="table table-sm table-bordered table-striped">
	<thead class ="table-info">	 
	<tr>
  		<th width="5%"><spring:message code="aca.Operacion"/></th>
    	<th width="5%" class="text-center"><spring:message code="aca.Numero"/></th>
    	<th width="70%" class="text-start"><spring:message code="aca.Nombre"/> del Evento</th>
    	<th width="10%" class="text-center"><spring:message code="aca.Fecha"/></th>    
    	<th width="10%" class="text-center"><spring:message code="aca.Estado"/></th>
	</tr>
	</thead>
<%	
	int row = 0;
	for (AlumEvento ev : lisEvento){
		row++;
		
		String total = "0";
		if (mapaAlumnos.containsKey(ev.getEventoId())){
			total = mapaAlumnos.get(ev.getEventoId());
		}
%>
	<tr align="center">
		<td style="text-align: center">
			<a href="editar?EventoId=<%=ev.getEventoId()%>" title="<spring:message code="aca.Modificar"/>"><i class="fas fa-pencil-alt"></i></a>
<%		if(total.equals("0")){ %>			
			&nbsp;
			<a href="javascript:borrar('<%=ev.getEventoId()%>')" title="<spring:message code="aca.Eliminar"/>"><i class="fas fa-trash" ></i></a>
<%		} %>
		</td>
    	<td align="center"><%=row%></td>
    	<td align="left"><%=ev.getEventoNombre() %></td>
    	<td align="center"><%=ev.getFecha()%></td>
    	<td align="center"><%=ev.getEstado().equals("A")?"Activo":"Inactivo"%></td>
	</tr>	
<%	} %>
</table>
</div>
</body>