<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.spring.Inscritos"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatCiudad"%>
<%@page import="aca.catalogo.spring.CatCiudad"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumUbicacion"%>
<%@page import="aca.alumno.spring.AlumActualiza"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>
<%			
	String facultad 		= "";
	String carrera			= "";
	String sBgcolor			= "";
	int cont 				= 0;
	
	List<Inscritos> lisInscritos					= (List<Inscritos>) request.getAttribute("lisInscritos"); 
	
	HashMap<String,AlumUbicacion> mapaUbicaciones 	= (HashMap<String,AlumUbicacion>) request.getAttribute("mapaUbicaciones");
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,CatPais> mapaPaises 			 	= (HashMap<String,CatPais>) request.getAttribute("mapaPaises");
	HashMap<String,CatEstado> mapaEstados 	 		= (HashMap<String,CatEstado>) request.getAttribute("mapaEstados");
	HashMap<String,CatCiudad> mapaCiudades 	 		= (HashMap<String,CatCiudad>) request.getAttribute("mapaCiudades");
	HashMap<String,AlumPersonal> mapaPersonales 	= (HashMap<String,AlumPersonal>) request.getAttribute("mapaPersonales");
	HashMap<String,AlumActualiza> mapaActualizados 	= (HashMap<String,AlumActualiza>) request.getAttribute("mapaActualizados");
%>

<div class="container-fluid">
	<h2>Listado de Direcciones</h2>
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>
	<table id="table" class="table table-sm table-bordered table-striped">
<%
	for(int i=0; i<lisInscritos.size(); i++){			
		Inscritos insc = (Inscritos) lisInscritos.get(i);
		
		String facultadAlumno = "";
		if(mapaCarreras.containsKey(insc.getCarreraId())){
			facultadAlumno = mapaCarreras.get(insc.getCarreraId()).getFacultadId();
		}
%>
<%
		if(!facultad.equals(facultadAlumno)){
%>
			<%
					String nomFacultad = "";
					if(mapaFacultades.containsKey(facultadAlumno)){
						nomFacultad = mapaFacultades.get(facultadAlumno).getNombreFacultad();
					}					
				%>
				<thead>	 
					<tr>
						<td align="center" colspan="10"><font size="5"><%=nomFacultad %></font></td>
					</tr>
				</thead>		
<% 			facultad=facultadAlumno;
		}
		
		if(!carrera.equals(insc.getCarreraId())){
%>
				<thead>	 
					<tr>
				<%
					String nomCarrera = "";
					if(mapaCarreras.containsKey(insc.getCarreraId())){
						nomCarrera = mapaCarreras.get(insc.getCarreraId()).getNombreCarrera();
					}
					
				%>
						<td colspan="10"><font size="2"><%=nomCarrera%></font></td>
					</tr>
				</thead>		
				<thead class="table-info">	 
					<tr>
						<th width="4%"><spring:message code="aca.Matricula"/></th>
						<th width="15%"><spring:message code="aca.Alumno"/></th>
						<th width="15%">Padre</th>
						<th width="15%"><spring:message code="aca.Direccion"/></th>
						<th width="10%">Colonia</th>
						<th width="9%"><spring:message code="aca.Ciudad"/></th>
						<th width="9%"><spring:message code="aca.Estado"/></th>
						<th width="9%"><spring:message code="aca.Pais"/></th>
						<th width="7%"><spring:message code="aca.Codigo"/> Postal</th>
						<th width="7%">Actualizado</th>
					</tr>
				</thead>
<%	
			carrera=insc.getCarreraId();
		}

		if(mapaUbicaciones.containsKey(insc.getCodigoPersonal())){
			AlumUbicacion ubicacion = mapaUbicaciones.get(insc.getCodigoPersonal());
			cont++;			
			
			String actualizado = "nunca";			
			if(mapaActualizados.containsKey(ubicacion.getCodigoPersonal())){				
				actualizado = mapaActualizados.get(ubicacion.getCodigoPersonal()).getFecha();
			}
			
			String ciudad = "";
			String llave = ubicacion.getTPais()+ubicacion.getTEstado()+ubicacion.getTCiudad();
			if(mapaCiudades.containsKey(llave)){
				ciudad = mapaCiudades.get(llave).getNombreCiudad();
			}
			
			String estado = "";
			String llaveE = ubicacion.getTPais()+ubicacion.getTEstado();
			if(mapaEstados.containsKey(llaveE)){
				estado = mapaEstados.get(llaveE).getNombreEstado();
			}
			
			String pais = "";
			if(mapaPaises.containsKey(ubicacion.getTPais())){
				pais = mapaPaises.get(ubicacion.getTPais()).getNombrePais();
			}
			
			String alumno= "";
			if(mapaPersonales.containsKey(ubicacion.getCodigoPersonal())){
				alumno = mapaPersonales.get(ubicacion.getCodigoPersonal()).getNombreLegal();
			}			
%>
				<tr> 
					<td align="center">
						<%=ubicacion.getCodigoPersonal()%>
					</td>
					<td>
						<%= alumno %>
					</td>
					<td>
						<%=ubicacion.getTNombre()%>
					</td>
					<td>
						<%=ubicacion.getTDireccion() %>
					</td>
					<td>
						<%=ubicacion.getTColonia() %>
					</td>
					<td>
						<%=ciudad%>
					</td>
					<td>
						<%=estado %>
					</td>
					<td>
						<%=pais%>
					</td>
					<td align="center">
						<%=ubicacion.getTCodigo() %>
					</td>
					<td align="center">
						<%=actualizado %>
					</td>
				</tr>	
<%		}%>
<% 	} // fin del for 	
%>
	</table>	
</div>