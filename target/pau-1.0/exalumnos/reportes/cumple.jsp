<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.exa.spring.ExaTelefono"%>
<%@page import="aca.exa.spring.ExaCorreo"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<% 	
	String dia				= (String) request.getAttribute("dia"); 
	String mes				= (String) request.getAttribute("mes"); 
	
	List<AlumPersonal> alumnos = (List<AlumPersonal>) request.getAttribute("alumnos"); 
	
	HashMap<String, ExaCorreo> correos 		= (HashMap<String, ExaCorreo>) request.getAttribute("correos");
	HashMap<String, ExaTelefono> telefonos 	= (HashMap<String, ExaTelefono>) request.getAttribute("telefonos");
	
	int nMes = Integer.parseInt(mes);
%>
<div class="container-fluid">
<h2>Reporte por Cumpleaños</h2>
<div class="alert alert-info d-flex align-items-center">
	<a class="btn btn-primary" href="menu"><spring:message code="aca.Regresar"/></a>
</div>
<form action="cumple" method="post">
<table style="width:30%" align="center" class="table table-bordered table-condensed table-nohover">
	<tr>
		<td colspan="2" style="text-align:center;">
			<input name="Dia" id="Dia" type="text" class="form-control" placeholder="Dia" value="<%=dia%>" />
			<br> <select class="form-control"name="Mes" id="Mes">	</br>
				  <option value="01" <%if(nMes==1)out.print("selected");%>>Enero</option>
				  <option value="02" <%if(nMes==2)out.print("selected");%>>Febrero</option>
				  <option value="03" <%if(nMes==3)out.print("selected");%>>Marzo</option>
				  <option value="04" <%if(nMes==4)out.print("selected");%>>Abril</option>
				  <option value="05" <%if(nMes==5)out.print("selected");%>>Mayo</option>
				  <option value="06" <%if(nMes==6)out.print("selected");%>>Junio</option>
				  <option value="07" <%if(nMes==7)out.print("selected");%>>Julio</option>
				  <option value="08" <%if(nMes==8)out.print("selected");%>>Agosto</option>
				  <option value="09" <%if(nMes==9)out.print("selected");%>>Septiembre</option>
				  <option value="10" <%if(nMes==10)out.print("selected");%>>Octubre</option>
				  <option value="11" <%if(nMes==11)out.print("selected");%>>Noviembre</option>
				  <option value="12" <%if(nMes==12)out.print("selected");%>>Diciembre</option>      
	        </select>
	        
			
			<br><button class="btn btn-primary">Filtrar</button></br>
		</td>
	</tr>
</table>
</form>

<table class="table table-condensed table-bordered table-striped" width="80%" align="center">
	<tr class="table-info">
		<th><spring:message code="aca.Matricula"/></th>
		<th><spring:message code="aca.Nombre"/></th>
		<th>Fecha Nacimiento</th>
		<th><spring:message code="aca.Correo"/></th>
		<th><spring:message code="aca.Telefono"/></th>
	</tr>	
<%
	for(AlumPersonal alumno: alumnos){
		
		String telefono = "&nbsp;";
		if(telefonos.containsKey(alumno.getCodigoPersonal())){
			telefono = telefonos.get(alumno.getCodigoPersonal()).getTelefono();
		}
%>
	<tr>
		<td><%=alumno.getCodigoPersonal() %></td>
		<td><%=alumno.getNombre() +" "+ alumno.getApellidoPaterno() +" "+ alumno.getApellidoMaterno()  %></td>
		<td><%=alumno.getFNacimiento() %></td>
		<td><%=correos.get(alumno.getCodigoPersonal()).getCorreo() %></td>
		<td><%=telefono %></td>
	</tr>
<%
	}
%>
</table>
</div>