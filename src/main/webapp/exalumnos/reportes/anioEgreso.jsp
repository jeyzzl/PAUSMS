<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.exa.spring.ExaDireccion"%>
<%@page import="aca.exa.spring.ExaPais"%>
<%@page import="aca.exa.spring.ExaEgreso"%>
<%@page import="aca.exa.spring.ExaEstado"%>
<%@page import="aca.exa.spring.ExaCorreo"%>
<%@page import="aca.exa.spring.ExaTelefono"%>
<%@page import="aca.exa.spring.ExaTelefono"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	
	function Datos(codigoPersonal,year){		
		document.location.href="../exalumno/datos?codigoAlumno="+codigoPersonal+"&Year="+year;
	}

</script>
<% 
	String year			 		= request.getParameter("Year")==null?"Selecciona":request.getParameter("Year");	
	String carreraId			= request.getParameter("CarreraId")==null?"Selecciona":request.getParameter("CarreraId");	
	String carreraTemp			= "";
	String carreraNombre		= "";
	
	String pais					= "";
	String estado				= "";
	String ciudad				= "";
	String direccion			= "";
	String cp					= "";
	String correo 				= "";
	String tel	 				= "";
	int contador 				= 0;
	List<String> lisYears			= (List<String>) request.getAttribute("lisYears"); 
	List<String> lisCarreras		= (List<String>) request.getAttribute("lisCarreras");	
	List<ExaEgreso> lisEgresos		= (List<ExaEgreso>) request.getAttribute("lisEgresos");
		
	HashMap<String, ExaDireccion> mapaDireccion = (HashMap<String, ExaDireccion>) request.getAttribute("mapaDirecciones");
	HashMap<String, CatCarrera> mapaCarreras 	= (HashMap<String, CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String, ExaPais> mapaPaises 		= (HashMap<String, ExaPais>) request.getAttribute("mapaPaises");	
	HashMap<String, ExaEstado> mapaEstados 		= (HashMap<String, ExaEstado>) request.getAttribute("mapaEstados");
	HashMap<String, ExaCorreo> mapaCorreo 		= (HashMap<String, ExaCorreo>) request.getAttribute("mapaCorreo");
	HashMap<String, ExaTelefono> mapaTel 		= (HashMap<String, ExaTelefono>) request.getAttribute("mapaTel");	
	HashMap<String, AlumPersonal> mapaEgreso 	= (HashMap<String, AlumPersonal>) request.getAttribute("mapaEgreso");	
%>
<div class="container-fluid">
	<h1>Reporte por Año de Egreso</h1>
	<form id="frmEgreso" name="frmEgreso" action="anioEgreso" method="post">	
	<div class="alert alert-info d-flex align-items-center">
		<a class="btn btn-primary" href="menu"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<strong>Año: &nbsp; </strong>
		<select name="Year" id="Year"  class="form-select" style="width:180px;" onchange="document.frmEgreso.submit();">
			<option value = "Selecciona"  selected> Selecciona el año </option>
				<%
					for( int j=0;j<lisYears.size();j++){
						%>
						<option value = "<%= lisYears.get(j) %>" <%= lisYears.get(j).equals(year)?"selected":""%>><%=lisYears.get(j) %></option>
				<% 	
					}
				%>
		</select>&nbsp;
		<strong>Carrera:&nbsp; </strong>
		<select name="CarreraId" id="CarreraId" class="form-select" style="width:400px;" onchange="document.frmEgreso.submit();">
			<option value = "Selecciona" selected> Selecciona la Carrera </option>
				<%						
					for( int j=0;j<lisCarreras.size();j++){
						carreraTemp = (String)lisCarreras.get(j);
						if (mapaCarreras.containsKey(carreraTemp)){
							CatCarrera carr = mapaCarreras.get(carreraTemp); 
							carreraNombre = carr.getNombreCarrera();
						}else{
							carreraNombre = "¡No existe!";
						}					
							
						if (carreraTemp.equals(carreraId)){
							out.print(" <option value='"+carreraTemp+"' Selected>"+ carreraNombre +"</option>");
						}else{
							out.print(" <option value='"+carreraTemp+"'>"+ carreraNombre +"</option>");
						}
					}	
				%>
		</select>
	</div>
	</form>
<%  
	carreraTemp 	= "";
	carreraNombre 	= ""; 
	for(int i=0; i<lisEgresos.size();i++){
		ExaEgreso egreso = lisEgresos.get(i);
		if(!carreraTemp.equals(egreso.getCarreraId())){
			carreraTemp = egreso.getCarreraId();
			if (mapaCarreras.containsKey(carreraTemp)){
				CatCarrera carr = mapaCarreras.get(carreraTemp);
				carreraNombre = carr.getNombreCarrera();
			}else{
				carreraNombre = "¡No existe!";
			}
%>
	<table  class="table table-striped table-bordered table-condensed table-fontsmall">
	<tr><th colspan="11" ><%= carreraNombre %></th></tr>
	<tr>
		<th width="5%">No.</td>
		<th width="5%"><spring:message code="aca.Matricula"/></th>
		<th width="25%"><spring:message code="aca.Nombre"/></th>
		<th width="5%">Año</th>
		<th width="15%"><spring:message code="aca.Pais"/></th>
		<th width="15%"><spring:message code="aca.Estado"/></th>
		<th width="15%"><spring:message code="aca.Ciudad"/></th>
		<th width="20%"><spring:message code='aca.Direccion'/></th>
		<th width="11%">CP</th>
		<th width="20%"><spring:message code="aca.Correo"/></th>
		<th width="20%"><spring:message code="aca.Telefono"/></th>
<%  	
		}
		
		if(mapaCorreo.containsKey(egreso.getMatricula())){
			ExaCorreo corr = mapaCorreo.get(egreso.getMatricula());
			
			correo = corr.getCorreo();
		}else{
			correo = "";
		}
		
		if(mapaTel.containsKey(egreso.getMatricula())){
			ExaTelefono telefono = mapaTel.get(egreso.getMatricula());
			tel = telefono.getTelefono();
		}else{
			tel = "";
		}
		
		if (mapaDireccion.containsKey(egreso.getMatricula())){
			ExaDireccion dir = mapaDireccion.get(egreso.getMatricula());
			pais 		= dir.getPaisId();			
			estado 		= dir.getEstadoId();	 
			ciudad		= dir.getCiudad();
			direccion 	= dir.getDireccion();
			cp 			= dir.getCp();
			
			// Obtiene los nombres del pais y estado en los hash map
			if ( mapaPaises.containsKey(pais)){
				pais = mapaPaises.get(pais).getPaisNombre();
			}
			if (mapaEstados.containsKey(dir.getPaisId()+estado)){
				estado = mapaEstados.get(dir.getPaisId()+estado).getNombreEstado();
			}
			
		}else{
			pais = "-"; estado = "-"; ciudad= "-"; direccion = "-"; cp= "-";
		}
		String nombreEgresado = "";
		if (mapaEgreso.containsKey(egreso.getMatricula())){
			AlumPersonal alumno = new AlumPersonal();
			alumno = mapaEgreso.get(egreso.getMatricula());
			nombreEgresado = alumno.getNombre()+" "+alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno();
		}
%>		
	<tr>
	  <td><%= i+1 %></td>
	  <td><a href="javascript:Datos('<%= egreso.getMatricula() %>','<%=year%>')"><%= egreso.getMatricula() %></a></td>
	  <td><%=nombreEgresado%></td>
	  <td><%=egreso.getYear() %></td>
	  <td><%=pais%></td>
	  <td><%=estado%></td>
	  <td><%=ciudad%></td>
	  <td><%=direccion%></td>
	  <td><%=cp%></td>
	  <td><%=correo%></td>
	  <td><%=tel%></td>
	</tr>
<%	
	}	
%>
	</table>
</div>