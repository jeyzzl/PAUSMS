<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@page import="aca.conva.spring.ConvHistorial"%>
<%@page import="aca.conva.spring.ConvEvento"%>
<%@page import="aca.vista.spring.Maestros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumnoUtil"  class="aca.alumno.AlumUtil" scope="page"/>

<script type="text/javascript">
	
</script><head><link href="css/pa.css" rel="STYLESHEET" type="text/css">
</head>
<STYLE TYPE="text/css">
.tabbox
	{
		background: #eeeeee;
		border-left: 0pt gray solid;
		border-right: 0pt gray solid;
		border-bottom: 1pt gray solid;
	}
</STYLE>
<%
	String convalidacionId  				= request.getParameter("convalidacionId")==null?"-":request.getParameter("convalidacionId");
	ConvEvento convEvento  					= (ConvEvento)request.getAttribute("convEvento");
	String nombreAlumno 					= (String)request.getAttribute("nombreAlumno");
	List<ConvHistorial> lisConv 			= (List<ConvHistorial>)request.getAttribute("lisConv");
	HashMap<String, Maestros> mapaMaestros	= (HashMap<String, Maestros>)request.getAttribute("mapaMaestros");
%>
<body marginwidth="0" marginheight="0" leftmargin="0" topmargin="0" background="../../imagenes/back.gif">
<div class="container-fluid">
	<h2>Convalidacion del Alumno 
	<small class="text-muted fs-4">(&nbsp;
		<%=convEvento.getCodigoPersonal() %> - 
		<%=nombreAlumno%> - 
		<%=convEvento.getPlanId() %> )
	</small>
	</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="solicitud"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="forma" action="" method="post" id="noayuda">	
	<table  class="table table-fullcondensed">		
	<thead>	
		<tr>
			<th align="center"><spring:message code="aca.Numero"/></th>
			<th align="center"><spring:message code="aca.Fecha"/></th>
			<th align="center"><spring:message code='aca.Usuario'/></th>												
			<th align="center"><spring:message code="aca.Estado"/></th>
		</tr>
	</thead>
	<tbody>	
<%
	int row = 0;
	for(ConvHistorial convHistorial : lisConv){
		row++;
		String estado = convHistorial.getEstado();
		String nombreUsuario = "-";
		if(mapaMaestros.containsKey(convHistorial.getUsuario())){
			nombreUsuario = mapaMaestros.get(convHistorial.getUsuario()).getNombre()+" "+mapaMaestros.get(convHistorial.getUsuario()).getApellidoPaterno()+" "+mapaMaestros.get(convHistorial.getUsuario()).getApellidoMaterno();
		}
%>
		<tr class="tr2">
			<td align="center"><%=row%></td>
			<td><%=convHistorial.getFecha() %></td>
			<td>[<%=convHistorial.getUsuario()%>] &nbsp; <%=nombreUsuario%></td>
			<td>
			<%
				if(estado.equals("S"))
					out.print("Solicitud");
				else if(estado.equals("P"))
					out.print("Predictamen");
				else if(estado.equals("C"))
					out.print("Confirmada");
				else if(estado.equals("A"))
					out.print("Tramite");
				else if(estado.equals("T"))
					out.print("Terminada");
				else if(estado.equals("X"))
					out.print("Cancelada");
				else if(estado.equals("R"))
					out.print("Registrada");
			%>
			</td>
		</tr>
<%	} %>
			
	</tbody>	
  	</table>
	</form>
</div>	