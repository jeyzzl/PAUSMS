<%@page import="aca.por.spring.PorRegistro"%>
<%@page import="aca.vista.spring.Usuarios"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.por.spring.PorHora"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>


<script type="text/javascript">	
		
</script>
<%
	//String usuario 		= (String) session.getAttribute("usuario");
	String codigoEmpleado 	= (String) session.getAttribute("codigoEmpleado");
	String nombreEmpleado	= (String) request.getAttribute("nombreEmpleado");	
	
	HashMap<String,String> mapMaestros	= (HashMap<String,String>) request.getAttribute("mapMaestros");
	HashMap<String,PorHora> mapaHoras	= (HashMap<String,PorHora>) request.getAttribute("mapaHoras");
	HashMap<String,String> mapaSalones	= (HashMap<String,String>) request.getAttribute("mapaSalones");
	
	ArrayList<PorRegistro> lisEquipos	= (ArrayList<PorRegistro>)request.getAttribute("lisEquipos");
%>
<div class="container-fluid">
	<h2>Revision de portafolio <small class="text-muted fs-4">( <%=codigoEmpleado%> - <%=nombreEmpleado%> )</small></h2>
	<div class="alert alert-info">	
	<form name="frmEquipo" action="grabar" method="post">
		<input type="hidden" name="Estado" value="A"/>
		<select name="EquipoId" id="EquipoId" class="input input-medium">
			<option value="1">Comisión 1</option>
			<option value="2">Comisión 2</option>
			<option value="3">Comisión 3</option>
			<option value="4">Comisión 4</option>
			<option value="5">Comisión 5</option>
		</select>&nbsp;		
		<a href="javascript:Grabar()" class="btn btn-primary">Grabar</a>
	</form>
	</div> 
	
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
		<tr>
			<th width="2%"></th>
			<th>#</th>
			<th>Equipo</th>
			<th>Empleado</th>
			<th>Horario</th>
			<th>Estado</th>	
			<th>Opción</th>
		</tr>
	</thead>
		<tr>
<%
	int row = 0;
	for (PorRegistro equipo : lisEquipos){
		row++;
		String nombreMaestro = "";
		if(mapMaestros.containsKey(equipo.getCodigoPersonal()))
		{
			nombreMaestro = mapMaestros.get(equipo.getCodigoPersonal());
		}
		
		String horario	= "NO REGISTRADO";
		String dia 		= "-"; 
		String salon	= "-";
		if (mapaHoras.containsKey(equipo.getCodigoPersonal())){
			PorHora hora = mapaHoras.get(equipo.getCodigoPersonal());
			
			if (hora.getDia().equals("1")) dia = "Domingo";
			if (hora.getDia().equals("2")) dia = "Lunes";
			if (hora.getDia().equals("3")) dia = "Martes";
			if (hora.getDia().equals("4")) dia = "Miércoles";
			if (hora.getDia().equals("5")) dia = "Jueves";
			if (hora.getDia().equals("6")) dia = "Viernes";
			if (hora.getDia().equals("7")) dia = "Sábado";
			
			if (mapaSalones.containsKey(hora.getSalonId())){
				salon = mapaSalones.get(hora.getSalonId());
			}
			horario = dia+": "+salon+": "+hora.getHora();
		}		
%>
		<tr>
			<td>
<% 
	if(!mapaHoras.containsKey(equipo.getCodigoPersonal())){
%>
			<a href="javascript:Borrar('<%=equipo.getCodigoPersonal()%>')"><i class="fas fa-times fa-1x"></i></a>
<% 
	}
%>		
			</td>
			<td><%=row%></td>
			<td><span class="badge bg-success"><%=equipo.getEquipoId()%></span></td>
			<td><%=equipo.getCodigoPersonal() +" "+nombreMaestro%></td>
			<td><%=horario%></td>
			<td><%=equipo.getEstado().equals("A")?"Abierto":"Confirmado"%></td>
			<td>
				<a href="javascript:GrabarEstado('<%=equipo.getCodigoPersonal()%>','<%=equipo.getEquipoId()%>','C')"><span class="badge bg-warning">Cerrar</span></a>&nbsp;&nbsp;
				<a href="javascript:GrabarEstado('<%=equipo.getCodigoPersonal()%>','<%=equipo.getEquipoId()%>','A')"><span class="badge bg-info">Abrir</span></a>
			</td>
		<tr>
<%		
	}	
%>	
	</table>
<script type="text/javascript">
	function Grabar() {
		document.frmEquipo.submit();
	}
	
	function Borrar(codigoPersonal){
		if (confirm("¿Estás seguro de borrar?")){
			document.location.href="borrar?CodigoPersonal="+codigoPersonal;
		}
	}
	
	function GrabarEstado(codigoPersonal, equipo, estado){
		document.location.href="grabar?CodigoPersonal="+codigoPersonal+"&EquipoId="+equipo+"&Estado="+estado;
	}
</script>
</div>