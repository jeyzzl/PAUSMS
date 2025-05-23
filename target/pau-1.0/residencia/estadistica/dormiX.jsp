<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.vista.spring.Inscritos"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Acceso(num){
		document.form1.Accion.value = num;
		document.form1.submit();
	}
	
	function Mostrar(){		
		document.forma.submit();
	}
</script>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
<% 	
	String modalidades 				= (String) session.getAttribute("modalidadesReportes");	
	String fechaIni					= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin					= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	
	List<Inscritos> lisAlumnos					= (List<Inscritos>)request.getAttribute("lisAlumnos");
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
%>
<div class="container-fluid">
	<h2>Dormitory 1</h2>
	<form name="forma" action="dormiX" method="post">
		<div class="alert alert-info">
			<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
			Start Date: <input data-format="hh:mm:ss" id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10"/>&nbsp;&nbsp;
			<span class="add-on">
     		 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   			 </span>
			End Date: <input data-format="hh:mm:ss" id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10"/>&nbsp;&nbsp;
			<span class="add-on">
     		 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   			 </span>
			<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>	
		</div>
	</form>	
	<table style="width:77%; margin:0 auto">
	  <tr> 
	    <td colspan="5" class="titulo3">Total Dormitory Students:&nbsp;[&nbsp;<%=lisAlumnos.size()%>&nbsp;]</td>
	  </tr>
	</table> 
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">	 
	  <tr> 
	    <th width="5%"><div align="center"><spring:message code="aca.Matricula"/></div></th>
	    <th width="10%"><div align="center"><spring:message code="aca.Matricula"/></div></th>
	    <th width="40"><div align="center"><spring:message code="aca.Alumno"/></div></th>
	    <th width="10"><div align="center">Gender</div></th>
	    <th width="35"><div align="center"><spring:message code="aca.Carrera"/></div></th>
	  </tr> 
	 </thead> 
<%
	int row = 0; 
	for(Inscritos inscrito : lisAlumnos ){
		row++;
		String carreraNombre = "-";
		if (mapaCarreras.containsKey(inscrito.getCarreraId())){
			carreraNombre = mapaCarreras.get(inscrito.getCarreraId()).getNombreCarrera();
		}
%>  
	  <tr>
	    <td width="5%"><div align="center"><%= row %></div></td>
	    <td width="10%"><div align="center"><%=inscrito.getCodigoPersonal() %></div></td>
	    <td width="40"><div align="left"><%=inscrito.getApellidoPaterno()%> <%=inscrito.getApellidoMaterno()%>, <%=inscrito.getNombre()%></div></td>
	    <td width="10"><div align="center"><%=inscrito.getSexo()%></div></td>
	    <td width="35"><div align="left"><%=carreraNombre%></div></td>
	  </tr>		
<%	
	}
%>
	</table> 
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
</script>