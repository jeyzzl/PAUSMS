<%@page import="java.util.ArrayList"%>
<%@page import="aca.saii.spring.SaiiPeriodo"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script>
</script>
<%
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	SaiiPeriodo periodo		= (SaiiPeriodo)request.getAttribute("periodo");
	
	if(mensaje.equals("0")){
		mensaje = "¡Registro actualizado!";
	}else if (mensaje.equals("1")){
		mensaje = "¡Creado exitosamente!";		
	}else if (mensaje.equals("2")){
		mensaje = "¡Registro eliminado!";
	}
	
	ArrayList<SaiiPeriodo> lisPeriodo = (ArrayList<SaiiPeriodo>)request.getAttribute("lisPeriodo");
%>
<body>
<div class="container-fluid">
	<h2>Periodos SAII</h2>
	<div class="alert alert-info">
		&nbsp;
		<form name="frmPeriodo" action="guardar" method="post">
  			Clave:
  			<input id="PeriodoId" type="text" name="PeriodoId" class="input input-small" value="<%=periodo.getPeriodoId()%>">
  			&nbsp; &nbsp;
  			Periodo:
  			<input id="Nombre" type="text" name="Nombre" value="<%=periodo.getPeriodoNombre()%>">
  			&nbsp; &nbsp;
  			Estado:
  			<select id="Estado" name="Estado">
  				<option value="A" <%=periodo.getEstado().equals("A")?"selected":""%>>Activo</option>
  				<option value="I" <%=periodo.getEstado().equals("I")?"selected":""%>>Inactivo</option>
  			</select>
  			&nbsp; &nbsp;
  			Fecha:
  			<input id="Fecha" type="text" name="Fecha" data-date-format="dd/mm/yyyy" value="<%=periodo.getFecha()%>">
  			&nbsp; &nbsp;  			
  			<input type="submit" value="Guardar">
		</form>
		
	</div>
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<% 	}%>	
	<table class="table table-bordered">
	<thead class="table-info">
		<tr>
			<th>Op.</th>
			<th>#</th>
			<th>Clave</th>
			<th>Periodo</th>
			<th>Fecha</th>
			<th>Estado</th>
		</tr>
	</thead>
	<tbody>
<%
	int row = 0;
	for(SaiiPeriodo per : lisPeriodo){
		row++;		
%>
		<tr>
			<td>
			<a href="listado?PeriodoId=<%=per.getPeriodoId()%>"><i class="fas fa-edit"></i></a>&nbsp;			
			<a href="borrar?PeriodoId=<%=per.getPeriodoId()%>" onclick="return confirm('¿Desea borrar este registro? ');"><i class="fas fa-trash-alt"></i></a>
			</td>
			<td><%=row %></td>
			<td><%=per.getPeriodoId() %></td>
			<td><%=per.getPeriodoNombre() %></td>
			<td><%=per.getFecha() %></td>
			<td><%=per.getEstado().equals("A")?"Activo":"Inactivo"%></td>
		</tr>
<% 
	}
%>
	</tbody>
	</table>
</div>
</body>
<script>
	jQuery('#Fecha').datepicker();
</script>