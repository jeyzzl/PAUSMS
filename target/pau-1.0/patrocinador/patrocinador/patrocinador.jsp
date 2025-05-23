<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatPatrocinador"%>
<%@ page import="aca.carga.spring.Carga"%>
<%@ page import="aca.alumno.spring.AlumPatrocinador"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" >
	function grabar(){
		var porcentaje = document.frmPatrocinador.Porcentaje.value;
		if(document.frmPatrocinador.PatrocinadorId.value=="0"){
			alert("Select a valid Sponsor")
		}else if(document.frmPatrocinador.CargaId.value=="0"){
			alert("Select a valid Load")
			
		}//else if(!isNan(porcentaje) || porcentaje < 0 || porcentaje > 100){
			//alert("Please select a valid number between 0 and 100")
		//}
		else{
			document.frmPatrocinador.submit();
		}
		
	}
	
	function Borrar(CargaId, PatrocinadorId) {
		if (confirm("<spring:message code="aca.JSEliminar"/>") == true) {
			document.location = "borrar?CargaId="+CargaId+"&PatrocinadorId="+PatrocinadorId;
		}
	}
	
</script>

<%
	String codigoAlumno									= (String) session.getAttribute("codigoAlumno");
	String nombreAlumno									= (String) request.getAttribute("nombreAlumno");
	String mensaje										= (String)request.getParameter("mensaje")==null?"-":(String)request.getParameter("mensaje");
	
	List<Carga> lisCargas 								= (List<Carga>)request.getAttribute("lisCargas");
	List<CatPatrocinador> lisPatrocinadores 			= (List<CatPatrocinador>)request.getAttribute("lisPatrocinadores");
	List<AlumPatrocinador> lisAlumPatrocinadores 		= (List<AlumPatrocinador>)request.getAttribute("lisAlumPatrocinadores");
	
	HashMap<String, Carga> mapCargas					= (HashMap<String, Carga>)request.getAttribute("mapCargas");
	HashMap<String, CatPatrocinador> mapPatrocinadores	= (HashMap<String, CatPatrocinador>)request.getAttribute("mapPatrocinadores");
	
%>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<div class="container-fluid">
<h2>Assign Sponsor <small class="text-muted fs-5">( <%=codigoAlumno %> - <%=nombreAlumno%> )</small></h2>	
	<form action="grabar" name="frmPatrocinador" method="post">	
<%
	if(mensaje.equals("1")){
%>
	<div class="alert alert-info">
		Error: The student is already assigned to this Load and Sponsor !
	</div>
<%
	}else if (mensaje.equals("2")){
%>
<div class="alert alert-info">
		New Sponsor has been assigned for student !
	</div>
<%
	}else if (mensaje.equals("3")){
%>
<div class="alert alert-info">
		There has been an error saving the Sponsor !
	</div>
<%
	}else if(mensaje.equals("4")){
%>
	<div class="alert alert-info">
		Register has been deleted !
	</div>
<%
	}else if (mensaje.equals("5")){
%>
<div class="alert alert-info">
		There was an Error deleting this Register !
	</div>
<%
	}else if (mensaje.equals("6")){
%>
<div class="alert alert-info">
		This Register does not exist :/ !
	</div>
<%
	}
%>

	<div class="alert alert-info d-flex align-items-center">
	Load:&nbsp;
		<select name="CargaId" id="CargaId" style="width:300px;" class="form-select">
		<option value="0" selected>Select a Load</option>
<%
	for(Carga carga: lisCargas){
%>
			<option value="<%=carga.getCargaId()%>"><%=carga.getNombreCarga() %></option>
<%
	}
%>
		</select> &nbsp; &nbsp;
	Sponsor:&nbsp;
		<select name="PatrocinadorId" id="PatrocinadorId" class="chosen" style="width:300px;" class="form-select">
		<option value="0" selected>Select a Sponsor</option>
<%
	for(CatPatrocinador pat: lisPatrocinadores){
%>
			<option value="<%=pat.getPatrocinadorId()%>"><%=pat.getNombrePatrocinador() %></option>
<%
	}
%>
		</select> &nbsp; &nbsp;
		Amount:&nbsp;
		<input type="text" name="Cantidad" id="Cantidad" class="form-control" style="width:100px" value>
		<input type="hidden" name="Porcentaje" id="Porcentaje" value="0">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<button type="submit" class="btn btn-primary">Assign</button>
	</div>
	</form>
	
	<table id="table" class="table table-sm table-bordered">
		<thead class="table-info">
		<tr>
			<th width="2%">#</th>
			<th width="3%">Op.</th>
			<th width="15%">Carga</th>
			<th width="15%"><spring:message code="aca.Alumno"/></th>
			<th width="15%">Sponsor</th>
			<th width="15%">Details</th>		
			<th width="10%">Address</th>	
			<th width="10%">Phone Number</th>	
			<th width="10%">Amount</th>											
		</tr>
		</thead>
<%
int count = 1;
for(AlumPatrocinador alumPat: lisAlumPatrocinadores){
	
	Carga carga 		= new Carga();
	CatPatrocinador pat = new CatPatrocinador();
	
	if(mapCargas.containsKey(alumPat.getCargaId())){
		carga = mapCargas.get(alumPat.getCargaId());
	}
	
	if(mapPatrocinadores.containsKey(alumPat.getPatrocinadorId())){
		pat = mapPatrocinadores.get(alumPat.getPatrocinadorId());
	}
	
	
%>
	<tr>
		<td><%=count %></td>
		<td><a class="fas fa-trash-alt text-danger" href="javascript:Borrar('<%=alumPat.getCargaId()%>', '<%=alumPat.getPatrocinadorId()%>')"></a>	</td>
		<td><%=carga.getNombreCarga()%></td>
		<td><%=codigoAlumno%> |  <%=nombreAlumno%></td>
		<td><%=pat.getNombrePatrocinador()%></td>
		<td><%=pat.getDetalles()%></td>
		<td><%=pat.getDireccion()%></td>
		<td><%=pat.getTelefono() %></td>
		<td><%=alumPat.getCantidad() %></td>
	</tr>
<%
	count++;
}
%>
	</table>
</div>

<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
	jQuery(".chosen").chosen();
	
	function autorizar(ejercicioId,deptoId,informeId){
		document.location.href="autorizar?EjercicioId="+ejercicioId+"&DeptoId="+deptoId+"&InformeId="+informeId;
	}
		
	function desautorizar(ejercicioId,deptoId,informeId){
		document.location.href="desautorizar?EjercicioId="+ejercicioId+"&DeptoId="+deptoId+"&InformeId="+informeId;		
	}
</script>