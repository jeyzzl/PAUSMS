<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@page import="aca.exa.spring.ExaDireccion"%>
<%@page import="aca.exa.spring.ExaPais"%>
<%@page import="aca.exa.spring.ExaEstado"%>
<%@page import="aca.exa.spring.ExaTelefono"%>
<%@page import="aca.exa.spring.ExaCorreo"%>
<%@page import="aca.exa.spring.ExaEgreso"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<% 	
	String carreraId = (String) request.getAttribute("paisId"); 

	List<String> getCarreras	= (List<String>) request.getAttribute("getCarreras"); 
	List<ExaEgreso> lisEgresos 	= (List<ExaEgreso>) request.getAttribute("lisEgresos"); 

	HashMap<String, ExaDireccion> mapDireccion 	= (HashMap<String, ExaDireccion>) request.getAttribute("mapDireccion");
	HashMap<String, ExaPais> mapaPaises 		= (HashMap<String, ExaPais>) request.getAttribute("mapaPaises");	
	HashMap<String, ExaEstado> mapaEstados 		= (HashMap<String, ExaEstado>) request.getAttribute("mapaEstados");
	HashMap<String, CatCarrera> mapaCarreras 	= (HashMap<String, CatCarrera>) request.getAttribute("mapaCarreras");
	
	String carreraNombre 	= "", pais = "", estado = "", ciudad = "", direccion = "", cp ="";
	String facNombre 		= "";
	String carreraTabla 	= "";
	
	HashMap<String, ExaTelefono> mapaTelefono 	= (HashMap<String, ExaTelefono>) request.getAttribute("mapaTelefono");
	HashMap<String, ExaCorreo> mapaCorreo 		= (HashMap<String, ExaCorreo>) request.getAttribute("mapaCorreo");
	HashMap<String, AlumPersonal> mapaEgreso 	= (HashMap<String, AlumPersonal>) request.getAttribute("mapaEgreso");
%>
<div class="container-fluid">
	<h2>Reporte por Carreras</h2>
	<form id="formaCarrera" name="formaCarrera" action="carreras">
	<div class="alert alert-info d-flex align-items-center">		
		<a class="btn btn-primary" href="menu"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<select name="CarreraId" id="CarreraId" class="form-select" onchange="document.formaCarrera.submit();" style="width:500px;">
			<option value = "Selecciona" selected> Selecciona la Carrera </option>
		<%for(String carrera : getCarreras){
			if (mapaCarreras.containsKey(carrera)){
				CatCarrera carr = mapaCarreras.get(carrera); 
				carreraNombre = carr.getNombreCarrera();
			}else{
				carreraNombre = "¡No existe!";
			}	
			%>
			<option  value="<%=carrera%>" <%=carrera.equals(carreraId)?"Selected":""%>><%=carreraNombre%></option>	
		<%}%>			
		</select>				
	</div>
	</form>	
	<table  class="table table-striped table-bordered table-sm table-fontsmall"  align="center" >
		<%	
			if (mapaCarreras.containsKey(carreraId)){
				CatCarrera carr = mapaCarreras.get(carreraId); 
				carreraTabla = carr.getNombreCarrera();
			}else{
				carreraTabla = "Todas";
			}	%>
				
		<tr><th colspan="10" ><%=carreraTabla %></th></tr>
		<tr>
			<th width="3%"><spring:message code="aca.Numero"/></td>
			<th width="1%"><spring:message code="aca.Matricula"/></th>
			<th width="25%"><spring:message code="aca.Nombre"/></th>
			<th width="10%"><spring:message code="aca.Pais"/></th>
			<th width="10%"><spring:message code="aca.Estado"/></th>
			<th width="10%"><spring:message code="aca.Ciudad"/></th>
			<th width="20%"><spring:message code='aca.Direccion'/></th>
			<th width="1%">CP</th>
			<th><spring:message code="aca.Correo"/></th>
			<th><spring:message code="aca.Telefono"/></th>
<%  

	int con = 1;
	for(ExaEgreso egreso : lisEgresos){
		
		if(mapaCarreras.containsKey(egreso.getCarreraId())){
			CatCarrera carr = mapaCarreras.get(egreso.getCarreraId()); 
			carreraNombre = carr.getNombreCarrera();
		}

		ExaTelefono exaTelefono = mapaTelefono.containsKey(egreso.getMatricula())?mapaTelefono.get(egreso.getMatricula()):new ExaTelefono();
		ExaCorreo exaCorreo = mapaCorreo.containsKey(egreso.getMatricula())?mapaCorreo.get(egreso.getMatricula()):new ExaCorreo();
		
		if (mapDireccion.containsKey(egreso.getMatricula())){
			ExaDireccion dir = mapDireccion.get(egreso.getMatricula());
			pais 		= dir.getPaisId();			
			estado 		= dir.getEstadoId();
			ciudad		= dir.getCiudad();
			direccion 	= dir.getDireccion();
			cp 			= dir.getCp();

			// Obtiene los nombres del pais y estado en los hash map
			if (mapaPaises.containsKey(pais)) pais = mapaPaises.get(pais).getPaisNombre();
			if (mapaEstados.containsKey(dir.getPaisId()+estado)) estado = mapaEstados.get(dir.getPaisId()+estado).getNombreEstado();
		}else{
			pais = "-"; estado = "-"; ciudad= "-"; direccion = "-"; cp= "-";
		}
		
		AlumPersonal alumPersonal = new AlumPersonal();
		if(mapaEgreso.containsKey(egreso.getMatricula())){
			alumPersonal = mapaEgreso.get(egreso.getMatricula());
		}
%>		
		<tr>
		  	<td><%= con++ %></td>
		  	<td><%= egreso.getMatricula() %></td>
		  	<td><%= alumPersonal.getNombreLegal()  %></td>
		  	<td><%=	pais%></td>
		  	<td><%=	estado%></td>
		  	<td><%=	ciudad%></td>
		  	<td><%=	direccion%></td>
		  	<td><%=	cp%></td>
			<td><%=	exaCorreo.getCorreo().equals("")?"-":exaCorreo.getCorreo()%></td>
			<td><%=	exaTelefono.getTelefono().equals("")?"-":exaTelefono.getTelefono()%></td>
		</tr>
<% 	
	}
%>
	</table>
</div>