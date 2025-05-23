<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.cred.spring.CredEmpleado"%>
<%@ page import="aca.vista.spring.Maestros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String mesActual 		= aca.util.Fecha.getMesActual(aca.util.Fecha.getHoy()); 
	String dia				= request.getParameter("Dia")==null?"00":request.getParameter("Dia");
	String mes				= request.getParameter("Mes")==null?mesActual:request.getParameter("Mes");	
	if (dia.equals("0")) dia = "0"+dia;
	
	List<Maestros> lisEmpleados 					= (List<Maestros>)request.getAttribute("lisEmpleados");
	HashMap<String,CredEmpleado> mapaPuestos 		= (HashMap<String,CredEmpleado>)request.getAttribute("mapaPuestos");
	aca.util.Fecha fecha	= new aca.util.Fecha();
%>
<div class="container-fluid">
	<h2>Cumplea&ntilde;os de los empleados activos</h2>
	<form action="mes" method="post" name="mes" id="noayuda">
	<div class="alert alert-info d-flex align-items-center">
		<input type="hidden" name="Accion" value="2">
  		Mes:    
    	<select class="form-select" style="width:120px" name="Mes" id="Mes">
	    	<option value="01" <%=mes.equals("01")?"selected":""%>>Enero</option>	
			<option value="02" <%=mes.equals("02")?"selected":""%>>Febrero</option>			  
	        <option value="03" <%=mes.equals("03")?"selected":""%>>Marzo</option>	
			<option value="04" <%=mes.equals("04")?"selected":""%>>Abril</option>
			<option value="05" <%=mes.equals("05")?"selected":""%>>Mayo</option>
			<option value="06" <%=mes.equals("06")?"selected":""%>>Junio</option>       
			<option value="07" <%=mes.equals("07")?"selected":""%>>Julio</option>	
			<option value="08" <%=mes.equals("08")?"selected":""%>>Agosto</option>	
			<option value="09" <%=mes.equals("09")?"selected":""%>>Septiembre</option>	
			<option value="10" <%=mes.equals("10")?"selected":""%>>Octubre</option>
			<option value="11" <%=mes.equals("11")?"selected":""%>>Noviembre</option>
			<option value="12" <%=mes.equals("12")?"selected":""%>>Diciembre</option>	
	    </select>&nbsp;
	    Dia: 
	    <input  name="Dia" type="text" class="form-control" id="Dia" value="<%=dia%>" style="width:120px" size="3" maxlength="3">&nbsp;
	    (Dia 00 reporta todo el mes) &nbsp;
	    <input class="btn btn-primary" name="Buscar" type="submit" id="Buscar" value="Buscar">
    </div>
	</form>	
	<table style="width:90%" class="table table-bordered ">
  	<tr class="table-info">     
		<th width="5%"><spring:message code="aca.Numero"/></th>
		<th width="10%"><spring:message code="aca.Nomina"/></th>
    	<th width="35%"><spring:message code="aca.Nombre"/></th>
    	<th width="20%">Departamento</th>
    	<th width="20%">Puesto</th>
    	<th width="20%">Estado</th>
  	</tr>
<%
	int row=0;
	String diaTemp = "0";
	for (Maestros empleado : lisEmpleados){
		
		String depto = "-";
		if (mapaPuestos.containsKey(empleado.getCodigoPersonal())){
			depto = mapaPuestos.get(empleado.getCodigoPersonal()).getDepartamento();
		}
		
		String puesto = "-";
		if (mapaPuestos.containsKey(empleado.getCodigoPersonal())){
			puesto = mapaPuestos.get(empleado.getCodigoPersonal()).getPuesto();
		}
		
		if ( !fecha.getDia(empleado.getfNacimiento()).equals(diaTemp)){
			diaTemp = fecha.getDia( empleado.getfNacimiento() );
%>
	<tr><td colspan="4">&nbsp;</td></tr>
	<tr class="th2"><td colspan="4" align="center">D í a : &nbsp; <%=diaTemp%></td>
<%		}%>  
  <tr> 
    <td><%=++row%></td>
    <td><%=empleado.getCodigoPersonal()%></td>
    <td><%=empleado.getNombre()+","+empleado.getApellidoPaterno()+" "+empleado.getApellidoMaterno()%></td>
    <td><%=depto%></td>
    <td><%=puesto%></td>
    <td><%=empleado.getEstado().equals("A")?"Activo":"Jubilado"%></td>
  </tr>
<%	}%>  
</table>
</div>