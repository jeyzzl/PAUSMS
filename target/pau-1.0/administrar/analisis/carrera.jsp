<%@ page import="java.text.*"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.acceso.spring.Acceso"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%
	DecimalFormat getformato = new DecimalFormat("##0.00;(##0.00)");

	String codigoUsuario 	= (String) session.getAttribute("codigoPersonal");
	String cargaId 			= request.getParameter("CargaId")==null?(String)session.getAttribute("cargaId"):request.getParameter("CargaId");
	String carreraId 		= request.getParameter("CarreraId")==null?"00000":request.getParameter("CarreraId");
	String accion 			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");
	
	int nAccion = Integer.parseInt(accion);
	
	if (request.getParameter("CargaId")!=null){
		session.setAttribute("cargaId", request.getParameter("CargaId"));
	}

	List<Carga> lisCargas 			= (List<Carga>)request.getAttribute("lisCargas");	
	List<CatCarrera> lisCarreras 	= (List<CatCarrera>)request.getAttribute("lisCarreras");
	
	HashMap<String,CatFacultad> mapaFacultades  = (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,String> mapaInscritos	 	= (HashMap<String,String>)request.getAttribute("mapaInscritos");
	
	String facTemp 		= "x";	

	Acceso acceso = (Acceso)request.getAttribute("acceso");	
%>
<div class="container-fluid">
	<h2>Análisis por Alumnos</h2>
	<form name="forma" action="carrera" method='post'>
		<div class="alert alert-info d-flex align-items-center">
			<a href="menu" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;
			<strong>Carga: [ <%=cargaId%> ] </strong>
			<select name="CargaId" class="form-select" onchange="document.forma.submit()" style="width:400px;">
	<%			
					for (Carga carga :lisCargas) {		
	%>
				<option value="<%=carga.getCargaId()%>" <%=cargaId.equals(carga.getCargaId())?" selected":""%>>
				<%=carga.getCargaId()%> - <%=carga.getNombreCarga()%>
				</option>
	<%
					}
	%>
			</select>
			&nbsp; &nbsp;
			<input type="text" class="input-medium search-query form-control" placeholder="Buscar" id="buscar" style="width:200px">
		</div>	
	</form>
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info">
	<tr>
		<th>#</th>
		<th>Facultad</th>
		<th>Clave</th>
		<th>Carrera</th>
		<th class="right">Inscritos</th>
	</tr>
	</thead>	
<%
	int row=0;
	for(CatCarrera car : lisCarreras) {		
		if ((acceso.getAccesos().indexOf(car.getCarreraId()) != -1) || (acceso.getAdministrador().equals("S")) || (acceso.getSupervisor().equals("S"))) {
			
			row++;
			
			String facultadNombre = "-";
		 	if(mapaFacultades.containsKey(car.getFacultadId())){
		 		facultadNombre = mapaFacultades.get(car.getFacultadId()).getNombreCorto();
		 	}
				
			String carreraNombre = "-";
		 	if(mapaCarreras.containsKey(car.getCarreraId())){
		 		carreraNombre = mapaCarreras.get(car.getCarreraId()).getNombreCarrera();
		 	}	 
		 	
		 	String inscritos = "0";
		 	if(mapaInscritos.containsKey(car.getCarreraId())){
		 		inscritos = mapaInscritos.get(car.getCarreraId());
		 	}
%>	<tr>
		<td width="5%"><b><%=row%></b></td>
		<td class="left" width="10%">
			<b><%=facultadNombre%></b>
		</td>		
		<td class="left" width="10%">
			<a href="alumnos?CarreraId=<%=car.getCarreraId()%>"><b><%=car.getCarreraId()%></b></a>
		</td>
		<td>	
			<a href="alumnos?CarreraId=<%=car.getCarreraId()%>"><b><%=carreraNombre%></b></a>
		</td>
		<td class="right"><b><%=inscritos%></b></td>		
	</tr>
<%			
		} // Si tiene privilegios de ver la carrera 
	} //fin del for
%>
	</table>	
</div>
<script src="../../js/search.js"></script>
<script>
	$('#buscar').search();
</script>