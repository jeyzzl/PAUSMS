<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.trabajo.spring.TrabAlum"%>
<%@ page import="aca.trabajo.spring.TrabDepartamento"%>
<%@ page import="aca.trabajo.spring.TrabCategoria"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Borrar(DeptId) {
		if(confirm("<spring:message code="aca.JSEliminar"/> " ) == true) {
			document.location.href = "borrarDepartamento?DeptId="+DeptId;
		} 
	}
	
	function Grabar(){		
		document.frmdept.submit();
		
	}
	
	function cambioDepartamento(){
		console.log("in");
		var periodoId 	= document.getElementById("periodoId").value;
		var deptId 		= document.getElementById("deptId").value;
		var catId 		= document.getElementById("catId").value;
		var horas 		= document.getElementById("Horas").value;
		var nuevoDept 	= document.getElementById("NuevoDept").value;
		
		location.href = "cambiarDept?PeriodoId="+periodoId+"&DeptId="+deptId+"&CatId="+catId+"&horas="+horas+"&NuevoDept="+nuevoDept;
	}
</script>
<%
	String codigoAlumno 	= (String)session.getAttribute("codigoAlumno");

	String nuevoDept		= (String)request.getAttribute("nuevoDept");

	TrabAlum alumno 		= (TrabAlum) request.getAttribute("alumno");
	String nombreAlumno 	= (String)request.getAttribute("nombreAlumno");
	String nombreDept 		= (String)request.getAttribute("nombreDept");
	String nombreCat 		= (String)request.getAttribute("nombreCat");
	String nombrePeriodo 	= (String)request.getAttribute("nombrePeriodo");
	
	List<TrabDepartamento> lisDepartamentos 	= (List<TrabDepartamento>)request.getAttribute("lisDepartamentos");
	List<TrabCategoria> lisCategorias 			= (List<TrabCategoria>)request.getAttribute("lisCategorias");
%>
<body>
<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<div class="container-fluid">
	<h2>Department Change</h2><small class="text-muted fs-5">( <%=codigoAlumno %> - <%=nombreAlumno%> )</small></h2>	
	
	<form action="grabarNuevoDept" method="post" name="frmdept" target="_self">
	<input type="hidden" name="periodoId" id="periodoId" value = <%=alumno.getPeriodoId() %>>
	<input type="hidden" name="deptId" id="deptId" value = <%=alumno.getDeptId() %>>
	<input type="hidden" name="catId" id="catId" value = <%=alumno.getCatId() %>>
	
	<div class="alert alert-info">
		<a href="periodoAlum" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	
	<div class="row container-fluid">
	<div class="span2 col">
		<label for="PeriodoId"><strong>Period</strong></label>
		<input class="input input-mini form-control" name="PeriodoId" type="text" id="PeriodoId" maxlength="3" value="<%=nombrePeriodo%>" readonly>
		<br>
		<label for="DeptId"><strong>Department</strong></label>
		<input class="input input-xlarge form-control" name="DeptId" type="text" id="DeptId" value="<%=nombreDept%>" maxlength="50" readonly>
		<br>
		<label for="CatId">Category</strong></label>
		<input class="input input-xlarge form-control" name="CatId" type="text" id="CatId" value="<%=nombreCat%>" maxlength="50" readonly>
		<br>
		<label for="Horas">Hours</strong></label>
		<input class="input input-xlarge form-control" name="Horas" type="text" id="Horas" value="<%=alumno.getHoras()%>" maxlength="50" readonly>
	    <br><br>	
	    </div>
	    <div class="span2 col">
	
		
		<br><br><br><br>
		<label for="NuevoDept"><strong>Department</strong></label><br>
		<select name="NuevoDept" id = "NuevoDept" class="form-select chosen" onchange="javascript:cambioDepartamento();" style="width:400px" required>
	    	<option value="0">Choose</option>
<%		for (TrabDepartamento dept : lisDepartamentos){ %>
			<option value="<%=dept.getDeptId()%>" <%=dept.getDeptId().equals(nuevoDept)?"selected":""%>><%=dept.getEstado()%>-<%=dept.getDeptId()%> - <%=dept.getNombre()%></option>
<%		} %>	    	
	    </select>
		<br>
		<label for="NuevoCat">Category</strong></label><br>
		<select name="NuevoCat" id = "NuevoCat" class="form-select chosen" style="width:400px" required>
	    	<option value="0">Choose</option>
<%		int i = 0;	for (TrabCategoria cat : lisCategorias){ %>
			<option value="<%=cat.getCategoriaId()%>" <%=i==0?"selected":""%>><%=cat.getEstado()%>-<%=cat.getCategoriaId()%> - <%=cat	.getNombreCategoria()%></option>
<%		i++;} %>	    	
	    </select>
		
		
	    	
	    </div>
	</div>
	
	
	
	
	<div class="alert alert-info">
		<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;
	</div>
	</form>
</div>

<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();	
</script>
</body>