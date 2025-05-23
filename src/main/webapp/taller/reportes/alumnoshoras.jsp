<%@page import="java.text.*" %>
<%@page import="java.util.List" %>
<%@page import="java.util.HashMap" %>
<%@page import="aca.bec.spring.BecInformeAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%	
	java.text.DecimalFormat getFormato		= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String idEjercicio 	= request.getParameter("EjercicioId")==null?"0":request.getParameter("EjercicioId");
	String idCcosto 	= request.getParameter("DeptoId")==null?"0":request.getParameter("DeptoId");
	String informeId 	= request.getParameter("InformeId")==null?"0":request.getParameter("InformeId");

	String informeNombre 					= (String) request.getAttribute("informeNombre");
	String deptoNombre 						= (String) request.getAttribute("deptoNombre");
	String mensaje 							= (String) request.getAttribute("mensaje");
	boolean admin 							= (boolean) request.getAttribute("admin");
	
	List<BecInformeAlumno> lisBecados		= (List<BecInformeAlumno>) request.getAttribute("lisBecados");
	HashMap<String,String> mapaAlumnos		= (HashMap<String,String>) request.getAttribute("mapaAlumnos");
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
	.align-right{
		text-align:right !important;
	}
</style>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />

<div class="container-fluid">
	<h3><%=informeNombre%> <small class="text-muted fs-5"><%=deptoNombre%></small></h3>
	<%=mensaje %>
	<div class="alert alert-info">
		<a href="informemensual?InformeId=<%= informeId %>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
		<tr>			
			<th>#</th>
			<th><spring:message code="aca.Matricula"/></th>
			<th><spring:message code="aca.Nombre"/></th>
			<th><spring:message code="aca.Fecha"/></th>
			<th>Horas</th>
			<th><spring:message code="aca.Estado"/></th>
			<th><spring:message code='aca.Usuario'/></th>
			<th>Autorizar</th>
			<th>Cancelar</th>	
		</tr>
	</thead>
<%
	int cont = 0;
	for(BecInformeAlumno alumno : lisBecados ){
		cont++;
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(alumno.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(alumno.getCodigoPersonal());
		}
		
		String estado = "Sin Estado";			
		if(alumno.getEstado().equals("1")){
			estado = "Enviado";
		}else if(alumno.getEstado().equals("2")){
			estado = "Autorizado";
		}else if(alumno.getEstado().equals("3")){
			estado = "Contabilizado";
		}
%>			
		<tr>						
			<td><%=cont %></td>			
			<td><%=alumno.getCodigoPersonal()%></td>
			<td><%=alumnoNombre%></td>
			<td><%=alumno.getFecha()%></td>
			<td class="right"><%=alumno.getHoras()%></td>
			<td><%=estado %></td>
			<td><%=alumno.getUsuario() %></td>
			<td>
<%		if (admin && ( alumno.getEstado().equals("1"))){ %>
			<a href="alumnoshoras?DeptoId=<%=idCcosto%>&InformeId=<%=informeId%>&EjercicioId=<%=idEjercicio%>&codigoAlumno=<%=alumno.getCodigoPersonal()%>&Accion=6" class="btn btn-primary btn-small">
			<i class="icon-ok icon-white"></i>Auto.
			</a>
<%		}else{ 
			out.print("-");
		}
%>			
			</td>
			<td>
<%		if (admin && ( alumno.getEstado().equals("1") || alumno.getEstado().equals("2"))){ %>			
			&nbsp; &nbsp;
			<a href="alumnoshoras?DeptoId=<%=idCcosto%>&InformeId=<%=informeId%>&EjercicioId=<%=idEjercicio%>&codigoAlumno=<%=alumno.getCodigoPersonal()%>&Accion=5" class="btn btn-danger btn-small">
			<i class="fas fa-trash-alt icon-white"></i>Estado
			</a>
			&nbsp; &nbsp;
			<a href="alumnoshoras?DeptoId=<%=idCcosto%>&InformeId=<%=informeId%>&EjercicioId=<%=idEjercicio%>&codigoAlumno=<%=alumno.getCodigoPersonal()%>&Accion=4" class="btn btn-danger btn-small">
			<i class="fas fa-trash-alt icon-white"></i>Horas
			</a>
<%
		}else{
			out.print("-");
		}
%>			
			</td>	
		</tr>				
<%	} %>		
	</table>	
<%
	if(admin){
%>	
		<div class="alert alert-info">
			<a href="alumnoshoras?DeptoId=<%=idCcosto %>&InformeId=<%=informeId %>&EjercicioId=<%=idEjercicio %>&Accion=2" class="btn btn-primary btn-large"><i class="fas fa-check"></i> Autorizar</a>
			<a href="alumnoshoras?DeptoId=<%=idCcosto %>&InformeId=<%=informeId %>&EjercicioId=<%=idEjercicio %>&Accion=3" class="btn btn-danger btn-large"><i class="fas fa-check"></i> Desautorizar</a>
		</div>
		
		<br /><br />
<%
	}
%>	
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
	jQuery(".chosen").chosen();
</script>