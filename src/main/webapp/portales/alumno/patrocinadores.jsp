<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.text.*"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.trabajo.spring.TrabAlum"%>
<%@ page import="aca.trabajo.spring.TrabPeriodo"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@ include file="../alumno/menu.jsp"%>
<script type="text/javascript">
	function cambioPeriodo(){
		var periodoId = document.getElementById("PeriodoId").value;
		document.frmPeriodos.submit();
	}
</script>
<head></head>
<%
	DecimalFormat frmDecimal 	= new DecimalFormat("###,##0.00;(###,##0.00)");

	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String periodoId 			= (String) request.getAttribute("periodoId");

	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");
	TrabAlum trabAlum = (TrabAlum) request.getAttribute("trabAlum");

	HashMap<String,String> mapaCategorias = (HashMap<String,String>) request.getAttribute("mapaCategorias");
	HashMap<String,String> mapaDepartamentos = (HashMap<String,String>) request.getAttribute("mapaDepartamentos");
	List<TrabPeriodo> lisPeriodos = (List<TrabPeriodo>) request.getAttribute("lisPeriodos");
	List<TrabAlum> lisTrabajos = (List<TrabAlum>) request.getAttribute("lisTrabajos");
%>
<body>
<div class="container-fluid mt-1">	
	<div class="alert alert-success">
		<a href="previos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
		<span class="badge rounded-pill bg-dark">1D</span> Registration Stations - CTP	
		<small class="text-muted fs-6"> ( <%=codigoAlumno%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoMaterno()%> <%=alumPersonal.getApellidoPaterno()%>)</small>
	</div>	
<%	if(!lisPeriodos.isEmpty() && lisPeriodos != null){%>
	<form name="frmPeriodos" action="patrocinadores" mthod="post">
	<div class="alert alert-info">
		<select class="form-select" style="width:25rem;" id="PeriodoId" name="PeriodoId" onChange="javascript:cambioPeriodo()">
<%
		for(TrabPeriodo periodo : lisPeriodos){		
%>
			<option value="<%=periodo.getPeriodoId()%>" <%=periodoId.equals(periodo.getPeriodoId())?"selected":""%>><%=periodo.getNombrePeriodo() %></option>
<%
		}
%>
		</select>
	</div>
	</form>
	<table class="table table-sm table-bordered">
	<thead class="table-dark">
		<tr> 
	    	<th width="%">No.</th>
	        <th width="%">Department</th>
	        <th width="%">Category</th>
	        <th width="%">Assigned Hours</th>
	        <th width="%">Status</th>
		</tr>
	</thead>
	<tbody>
<%
	int row=0;

	for (TrabAlum trabajo : lisTrabajos){		
		String nombreDepartamento = "";
		if(mapaDepartamentos.containsKey(trabajo.getDeptId())){
			nombreDepartamento = mapaDepartamentos.get(trabajo.getDeptId());
		}

		String nombreCategoria = "";
		if(mapaCategorias.containsKey(trabajo.getCatId())){
			nombreCategoria = mapaCategorias.get(trabajo.getCatId());
		}
%>
        <tr> 
           <td><%=row++%></td>
           <td><%=nombreDepartamento%></td>
           <td><%=nombreCategoria%></td>
           <td><%=trabajo.getHoras()%></td> 
           <td><%=trabajo.getEstado().equals("A")?"Active":"Inactive"%></td> 
        </tr>
<%	}%>
	</tbody>		      
	</table>
<% }else{%>
    <div class="alert alert-warning">
        There are no Workline / CTP details registered. Please wait until their assigned or contact your advisor.
    </div>
<% }%>
</div>
</body>