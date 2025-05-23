<%@ page import="java.text.*"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabInforme"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>	
<%@ page import="aca.trabajo.spring.TrabCategoria"%>
<%@ page import="aca.trabajo.spring.TrabPeriodo"%>
<%@ page import="aca.trabajo.spring.TrabAlum"%>
<%@ page import="aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">
function Grabar(){		
	document.frmhoras.Accion.value="2";
	document.frmhoras.submit();					

}

</script>
<%
	String codigo				= (String) session.getAttribute("codigoPersonal");
	String periodoId 			= (String) request.getParameter("periodoId")==null?"1":request.getParameter("periodoId");
	String informeId 			= (String) request.getParameter("informeId")==null?"0":request.getParameter("informeId");
	String deptoId				= (String) request.getParameter("deptoId")==null?"1":request.getParameter("deptoId");
	String mensaje				= (String) request.getAttribute("Mensaje"); 
	String editar 				= (String) request.getParameter("editar")==null?"0":request.getParameter("editar");
	
	//TrabAlum alumno 			= (TrabInforme)request.getAttribute("alumno");
	List<TrabPeriodo> lisPeriodos							= (List<TrabPeriodo>)request.getAttribute("lisPeriodos");
	List<TrabInforme> lisInformes 							= (List<TrabInforme>)request.getAttribute("lisInformes");	
	List<TrabDepartamento> lisDeptos 						= (List<TrabDepartamento>)request.getAttribute("lisDeptos");	
	List<TrabCategoria> lisCategorias						= (List<TrabCategoria>)request.getAttribute("lisCategorias");
	List<TrabAlum> lisAlums									= (List<TrabAlum>)request.getAttribute("lisAlums");
	
	HashMap<String, String> mapaAlumNombre					= (HashMap<String, String>)request.getAttribute("mapaAlumNombre");
	HashMap<String, String> mapaCatNombre					= (HashMap<String, String>)request.getAttribute("mapaCatNombre");
	HashMap<String, String> mapaPeriodoNombre				= (HashMap<String, String>)request.getAttribute("mapaPeriodoNombre");
	HashMap<String, String> mapaAlumHoras					= (HashMap<String, String>)request.getAttribute("mapaAlumHoras");
	HashMap<String, String> mapaAlumHorasCompletadas		= (HashMap<String, String>)request.getAttribute("mapaAlumHorasCompletadas");
	HashMap<String, String> mapaInformeAlum					= (HashMap<String, String>)request.getAttribute("mapaInformeAlum");
	
	boolean editarActivo = (boolean)request.getAttribute("editarActivo");
	if(informeId.equals("0")){
		informeId = lisInformes.get(0).getInformeId();
	}
%>
<style>
	body{
		background:white;
	}
	.puestosAlum td, .puestosAlum th{
		background: white !important;
	}
	
	.puestosAlum th{
		color: black !important;
		border: 1px solid #DCDEDE !important;
	}
</style>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<div class="container-fluid">

	<h2>Weekly CTP Reports</h2>	

	<form action="informe" name="forma" method="get">		
	<div class="alert alert-info d-flex align-items-center">
		<select name="periodoId" id="periodoId" style="width:120px;" onchange="document.forma.submit()" class="form-select">
<%
	for(TrabPeriodo periodo: lisPeriodos){	
%>
			<option value="<%=periodo.getPeriodoId()%>" <% if(periodoId.equals(periodo.getPeriodoId()))out.print("selected"); %>><%=periodo.getNombrePeriodo()%></option>
<%
	}
%>	
		</select> &nbsp; &nbsp;
		<select name="informeId" id="informeId" style="width:320px;" onchange="document.forma.submit()" class="form-select">
<%
	for(TrabInforme informe: lisInformes){
%>
			<option value="<%=informe.getInformeId()%>" <%=informe.getInformeId().equals(informeId)?"selected":""%>><%=informe.getNombreInforme() %></option>
<%
	}
%>
		</select> &nbsp; &nbsp;
		<select name="deptoId" id="deptoId" class="chosen"  style="width:600px;" onchange="document.forma.submit()" class="form-select">
<%	for(TrabDepartamento depto: lisDeptos){%>
			<option value="<%=depto.getDeptId() %>" <% if(deptoId.equals(depto.getDeptId()))out.print("selected"); %>><%=depto.getDeptId() %> | <%=depto.getNombre() %></option>
<%	} %>
		</select>	
	</div>
	</form>
	<form name = "frmhoras" action ="informe?periodoId=<%=periodoId%>&informeId=<%=informeId%>&deptoId=<%=deptoId%>&editar=<%="0"%>" method = "post" target="_self">
	<input type="hidden" name="Accion">
			<table id="table" class="table table-sm table-bordered">
			<thead class="table-info">
				<tr>
					<th width="5%">#</th>
					<th width="15%">Period</th>
					<th width="25%"><spring:message code="aca.Alumno"/></th>
					<th width="25%">Category</th>
					<th width="10%">Assigned Hours </th>
					<th width="10%">Completed Hours</th>
					<th width="10%" class="text-center">
					<a class="btn btn-primary btn-sm" href="informe?periodoId=<%=periodoId%>&informeId=<%=informeId%>&deptoId=<%=deptoId%>&editar=<%="1"%>">
				  		Record Hours
				  	</a>
					</th>
				</tr>
			</thead>
<%
	int cont = 0;	
	for(TrabAlum alum: lisAlums){
		cont++;
		
		/* String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(puesto.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(puesto.getCodigoPersonal());
		} */
		String alumno = "";			
		if(alum.getMatricula() != null){
			alumno = alum.getMatricula() + " | " + mapaAlumNombre.get(alum.getMatricula());
		}
		
		String categoria = "";
		if(mapaCatNombre.containsKey(alum.getCatId())){
			categoria = mapaCatNombre.get(alum.getCatId());
		} 

		
		
	
%>
				<tr>
					<td><strong><%=cont%></strong></td>
					<td><%=mapaPeriodoNombre.get(periodoId)%></td>
					<td><%=alumno %></td>
					<td><%=categoria %></td>
					<td><%=alum.getHoras() %></td>
					<td><%=mapaAlumHorasCompletadas.get(alum.getMatricula())==null?"0":mapaAlumHorasCompletadas.get(alum.getMatricula())%></td>
					<td  width="25" class="text-center">
					<% if(editar.equals("1")){ %>
					<input name="informeId" type="hidden" value="<%=informeId%>">
					<% if(editarActivo){ %>
					<input id='ChkNota<%=cont%>' name="<%=alum.getMatricula()%>Hora"  type="text" class="text form-control" value="<%=mapaAlumHoras.get(alum.getMatricula())==null?"":mapaAlumHoras.get(alum.getMatricula())%>" size="3" maxlength="5">
					<% }else{ %>
					<input id='ChkNota<%=cont%>' name="<%=alum.getMatricula()%>Hora"  type="text" class="text form-control" value="<%=mapaAlumHoras.get(alum.getMatricula())==null?"":mapaAlumHoras.get(alum.getMatricula())%>" size="3" maxlength="5" readonly>
					<% } %>
					<% }else{%>
						<span><%=mapaAlumHoras.get(alum.getMatricula())==null?"0":mapaAlumHoras.get(alum.getMatricula()) %></span>
					<% } %>
					</td>

									
				</tr>
<%	} %>				
			</table>
	
	<a class="btn btn-primary" href="javascript:Grabar()">Save Hours</a>&nbsp;&nbsp;&nbsp;
	<br>
	<% if (!mensaje.equals("")){ %>
	<br>
	<div class = "alert alert-info"><%=mensaje%></div>
	<% } %>
	</form>
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