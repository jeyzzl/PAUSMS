<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>

<%@page import="aca.alumno.spring.AlumAgenda"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<script>
	function Actualiza(){		 
		document.forma.submit();		
	}

	function Grabar(){
		document.forma.Accion.value="1";		 
		document.forma.submit();		
	}

	function Quitar(carreraId,entregado){
		if (confirm("Are you sure to delete the agenda label given to students?")){
			document.location.href="quitar?CarreraId="+carreraId+"&Entregado="+entregado;
		}	
	}
	
	function GrabarAlumno(codigoAlumno,carreraId,entregado){				 
		document.location.href="grabar?CodigoAlumno="+codigoAlumno+"&CarreraId="+carreraId+"&Entregado="+entregado;		
	}

	function QuitarAlumno(codigoAlumno,carreraId){	 
		document.location.href="grabar?CodigoAlumno="+codigoAlumno+"&CarreraId="+carreraId+"&Entregado=0";		
	}	
</script>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<%	
	String entregado		= request.getParameter("Entregado")==null?"0":request.getParameter("Entregado");
	String carreraId		= request.getParameter("CarreraId")==null?"0":request.getParameter("CarreraId");

	List<AlumAgenda> lisAlumnos 		= (List<AlumAgenda>)request.getAttribute("lisAlumnos");
	List<CatCarrera> lisCarreras		= (List<CatCarrera>)request.getAttribute("lisCarreras");
	HashMap<String,String> mapaAlumnos 	= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	
	HashMap<String,String> mapaCarreraAlumno 	= (HashMap<String,String>)request.getAttribute("mapaCarreraAlumno");
	HashMap<String,CatFacultad> mapaFacultades 	= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");	
%>
<div class="container-fluid">
	<h2>Agendas</h2>
	<form name="forma" action="listado" target="_self">
	<input name="Accion" type="hidden" value="0"/>	
	<div class="alert alert-info d-flex align-items-center">
		<a href="actualizar?CarreraId=<%=carreraId%>" class="btn btn-success">Update</a>&nbsp;&nbsp;
		<select name="CarreraId" class="form-select chosen" style="width:420px;" onchange="javascript:Actualiza()">
			<option value="0" <%=carreraId.equals("0")?"selected":""%>>All</option>
<%		for (CatCarrera carrera : lisCarreras){
			String facultadCorto = "-";
			if (mapaFacultades.containsKey(carrera.getFacultadId())) facultadCorto = mapaFacultades.get(carrera.getFacultadId()).getNombreCorto(); 
%>
			<option value="<%=carrera.getCarreraId()%>" <%=carreraId.equals(carrera.getCarreraId())?"selected":""%>><%=facultadCorto%> - <%=carrera.getNombreCarrera()%></option>
<%		} %>			
		</select>
		&nbsp;&nbsp;
		<select name="Entregado" class="form-select" style="width:120px;" onchange="javascript:Actualiza()">
			<option value="0" <%=entregado.equals("0")?"selected":""%>>New</option>
			<option value="1" <%=entregado.equals("1")?"selected":""%>>Delivery 1</option>
			<option value="2" <%=entregado.equals("2")?"selected":""%>>Delivery 2</option>
			<option value="3" <%=entregado.equals("3")?"selected":""%>>Delivery 3</option>
			<option value="4" <%=entregado.equals("4")?"selected":""%>>Delivery 4</option>
		</select>
		&nbsp;&nbsp;
		<a href="javascript:Grabar();" class="btn btn-primary">Save</a>
		&nbsp;&nbsp;
		<a href="javascript:Quitar('<%=carreraId%>','<%=entregado%>')" class="btn btn-danger">Delete</a>
	</div>
	<table id="table" class="table table-sm table-bordered">
  	<tr>
  		<th class="text-center">Op.</th>
    	<th class="text-center">#</th>
    	<th class="text-center">ID</th>
    	<th>Name</th>
    	<th class="text-center">Delivered</th>
  	    <th class="text-start">School</th>
    	<th class="text-start">Degree</th>
    	<th class="text-center">Date</th>
    </tr>	
<%
	int row=0;
	for (AlumAgenda agenda : lisAlumnos){
		row++;	
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(agenda.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(agenda.getCodigoPersonal());
		}
		String carreraAlumno = "-";
		if(mapaCarreraAlumno.containsKey(agenda.getCodigoPersonal())){
			carreraAlumno	= mapaCarreraAlumno.get(agenda.getCodigoPersonal());
		}
		String nombreCarrera 	= "-";
		String facultadId = "-";
		if(mapaCarreras.containsKey(carreraAlumno)){
			nombreCarrera 	= mapaCarreras.get(carreraAlumno).getNombreCarrera();
			facultadId		= mapaCarreras.get(carreraAlumno).getFacultadId();	
		}
		String nombreFacultad   = "-";
		if(mapaFacultades.containsKey(facultadId)){
			nombreFacultad	= mapaFacultades.get(facultadId).getNombreCorto();
		}
		
		String colorEntregado = "<span class='badge bg-warning rounded-pill'>NO<span>";
		if (agenda.getEntregado().equals("1")) colorEntregado = "<span class='badge bg-dark rounded-pill'>"+agenda.getEntregado()+"<span>";
		if (agenda.getEntregado().equals("2")) colorEntregado = "<span class='badge bg-info rounded-pill'>"+agenda.getEntregado()+"<span>";
		if (agenda.getEntregado().equals("3")) colorEntregado = "<span class='badge bg-success rounded-pill'>"+agenda.getEntregado()+"<span>";
		if (agenda.getEntregado().equals("4")) colorEntregado = "<span class='badge bg-danger rounded-pill'>"+agenda.getEntregado()+"<span>";
%>    
	<tr>
		<td class="text-center">			
			<a href="javascript:GrabarAlumno('<%=agenda.getCodigoPersonal()%>','<%=carreraId%>','<%=entregado%>')"><i class="far fa-check-circle"></i></a>&nbsp;
			<a href="javascript:QuitarAlumno('<%=agenda.getCodigoPersonal()%>','<%=carreraId%>')"><i class="fas fa-undo-alt"></i></a>
		</td>
    	<td class="text-center"><%=row%></td>
    	<td class="text-center"><%=agenda.getCodigoPersonal()%></td>
    	<td><%=alumnoNombre%></td>
    	<td class="text-center"><%=colorEntregado%></td>
	    <td class="text-start"><%=nombreFacultad%></td>	
    	<td class="text-start"><%=nombreCarrera %></td>
    	<td class="text-center"><%=agenda.getFecha()%></td>
    </tr>
<%	} %>
	</table>
	</form>	
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();
</script>