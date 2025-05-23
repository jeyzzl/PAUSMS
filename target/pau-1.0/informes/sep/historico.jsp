<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.catalogo.spring.CatEstado"%>
<%@ page import= "aca.vista.spring.Estadistica"%>
<%@ page import= "aca.plan.spring.MapaPlan"%>
<%@ page import= "aca.carga.spring.CargaAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%		
	String cargas 		= request.getParameter("alumnos");	
	String planAlum		= "";
	String sBgcolor		= "";	
	String carrera		= "x"; 
	String carreraNombre= "x";		
	String planSe		= "";	
		 
	String codigoTemp	= "x";
	
	int cont			= 0;
	int total			= 0;
	int sem				= 0;
	int semestre		= 0;
	int grado 			= 0;
	
	String status		= "";
	String genero 		= "";
	String estilo		= "";
	
	List<Estadistica> lisEstadistica 				= (List<Estadistica>)request.getAttribute("lisEstadistica");
	
	HashMap<String,MapaPlan> mapaPlanes				= (HashMap<String,MapaPlan>)request.getAttribute("mapaPlanes");
	HashMap<String, CatFacultad> mapaFacultades		= (HashMap<String,CatFacultad>)request.getAttribute("mapaFacultades");
	HashMap<String, CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String, CatPais> mapaPaises				= (HashMap<String,CatPais>)request.getAttribute("mapaPaises");	
	HashMap<String, CatEstado> mapaEstados			= (HashMap<String,CatEstado>)request.getAttribute("mapaEstados");	
	HashMap<String, CatModalidad> mapaModalidades	= (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidades");	
	HashMap<String, String> mapaAlumPlanes			= (HashMap<String,String>)request.getAttribute("mapaAlumPlanes");
	HashMap<String, String> mapaGradosyCiclos		= (HashMap<String,String>)request.getAttribute("mapaGradosyCiclos");
	HashMap<String, String> mapaEdades				= (HashMap<String,String>)request.getAttribute("mapaEdades");
	HashMap<String,CargaAlumno> mapaCargasAlumno	= (HashMap<String,CargaAlumno>)request.getAttribute("mapaCargasAlumno");
%>
<head>

</head>
<body>
<div class="container-fluid">
	<h2>Alumnos Inscritos en los periodos: <%=cargas%></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="reporte"><spring:message code="aca.Regresar"/></a>
	</div>
	<table style="margin: 0 auto;  width:98%"  class="table table-condensed">
		<tr>	
			<th align="center" >Facultad</th>	    
			<th align="center" >Carrera</th>
			<th align="center" >Clave SEMSYS</th>
			<th align="center" >Plan SE</th>
			<th align="center" >Plan UM</th>
			<th align="center" ><spring:message code="aca.Ciclo"/> SE</th>
			<th align="center" ><spring:message code="aca.Ciclo"/></th>
			<th align="center" >CURP</th>
			<th align="center" ><spring:message code="aca.Nombre"/></th>
			<th align="center" >A. Paterno</th>
			<th align="center" >A. Materno</th>
			<th align="center" >G&eacute;nero</th>
			<th align="center" >Matr&iacute;cula</th>
			<th align="center" >Edad</th>
			<th align="center" >Id Pais</th>
			<th align="center" >Pais</th>
			<th align="center" >Id Estado</th>
			<th align="center" >Estado</th>
			<th align="center" >Modalidad</th>
		</tr>
<%	
	if(lisEstadistica.size() > 0){
		
		for (Estadistica estad : lisEstadistica){
			
			if(mapaCargasAlumno.containsKey(estad.getCodigoPersonal()+estad.getCargaId()+estad.getBloqueId())){
				String compara = mapaCargasAlumno.get(estad.getCodigoPersonal()+estad.getCargaId()+estad.getBloqueId()).getPlanId();
				if(!compara.equals(estad.getPlanId())){
					estad.setPlanId(compara);
					if(mapaPlanes.containsKey(compara)){
						estad.setCarreraId(mapaPlanes.get(compara).getCarreraId());
					}
				}
			}
			
			String carreraSe = "-";
			if (mapaPlanes.containsKey(estad.getPlanId())){
				carreraSe = mapaPlanes.get(estad.getPlanId()).getCarreraSe();
			}
			
			String clave = "-";
			if (mapaPlanes.containsKey(estad.getPlanId())){
				clave = mapaPlanes.get(estad.getPlanId()).getSemsys();
			}
			
			if (mapaCarreras.containsKey(estad.getCarreraId())){
				estad.setFacultadId(mapaCarreras.get(estad.getCarreraId()).getFacultadId());
			}
			
			// filtra los alumnos repetidos
			if (!codigoTemp.equals(estad.getCodigoPersonal())){
				codigoTemp = estad.getCodigoPersonal();
				
				cont++;
				if ((cont % 2) == 1 ) sBgcolor = sColor; else sBgcolor = "";	
			
				planAlum 	= estad.getPlanId();

				if (mapaGradosyCiclos.containsKey(estad.getCargaId()+estad.getCodigoPersonal())){
  					String[] dato = mapaGradosyCiclos.get(estad.getCargaId()+estad.getCodigoPersonal()).split(",");
  					grado 		= Integer.parseInt(dato[0]);						 
  					semestre	= Integer.parseInt(dato[1]); 
  				}
				
				String facultadNombre 	= "-";
				if (mapaFacultades.containsKey(estad.getFacultadId())){
					facultadNombre 		=  mapaFacultades.get(estad.getFacultadId()).getNombreFacultad();
				}
				
				estilo = "";
				if (!planAlum.equals(estad.getPlanId())){
					estilo = "style='color:red;font-weight:bold;'";
				}
				
				if(semestre != 1)
					status = "REINGRESO";
				else
					status = "NUEVO INGRESO";
				
				if (!estad.getCarreraId().equals(carrera)){
					if (!carrera.equals("x")){
						total += cont-1;
					}
					carrera 		= estad.getCarreraId();
					
					carreraNombre = "";
					if (mapaCarreras.containsKey(estad.getCarreraId())){
						carreraNombre 	= mapaCarreras.get(estad.getCarreraId()).getNombreCarrera();
					}
										
					cont = 1;
				}
				
				String paisNombre = "-";
				if(mapaPaises.containsKey(estad.getPaisId())){
					paisNombre = mapaPaises.get(estad.getPaisId()).getNombrePais();
					
				}
				String estadoNombre = "-";
				if(mapaEstados.containsKey(estad.getPaisId()+estad.getEstadoId())){
					estadoNombre = mapaEstados.get(estad.getPaisId()+estad.getEstadoId()).getNombreEstado();
				}
				
				if(mapaPlanes.containsKey(planAlum)){
					planSe = mapaPlanes.get(planAlum).getPlanSE();
				}
				
				String cicloSep = "0";
				if (mapaAlumPlanes.containsKey(estad.getCodigoPersonal()+planAlum)){
					cicloSep = mapaAlumPlanes.get(estad.getCodigoPersonal()+planAlum);
				}			
				
				String nombreModalidad 	= "-";
				if (mapaModalidades.containsKey(estad.getModalidadId())){
					nombreModalidad = mapaModalidades.get(estad.getModalidadId()).getNombreModalidad();
				}
				
				String edadAlumno = "0";
				if (mapaEdades.containsKey(estad.getCodigoPersonal())){
					edadAlumno = mapaEdades.get(estad.getCodigoPersonal());
				}
%>
		 <tr class="tr2" <%=sBgcolor%>>
		 	<td align="center"><%=facultadNombre%></td>
			<td align="center"><%=carreraSe %></td>
			<td align="center" <%=estilo%>><%=clave%></td>
			<td align="center" <%=estilo%>><%=planSe%></td>
			<td align="center" <%=estilo%>><%=planAlum%></td>
			<td align="center"><%=cicloSep %></td>
			<td align="center"><%=semestre %></td>			
			<td align="left"><%=estad.getCurp()%></td>
			<td align="left"><%=estad.getNombre()%></td>
			<td align="left"><%=estad.getApellidoPaterno()%></td>
			<td align="left"><%=estad.getApellidoMaterno()%></td>
			<td align="left"><%=estad.getSexo().equals("M")?"H":"M"%></td>
			<td align="left"><%=estad.getCodigoPersonal()%></td>
			<td align="left"><%=edadAlumno%></td>
			<td align="left"><%=estad.getPaisId()%></td>
			<td align="left"><%=paisNombre%></td>
			<td align="left"><%=estad.getEstadoId()%></td>
			<td align="left"><%=estadoNombre%></td>
			<td align="left"><%=nombreModalidad%></td>
		</tr>
<%			} //cierra filtro de alumno repetidos
		} // Cierre del for
		total+=cont;
%>
		<tr>
			<td colspan="16" align="center">
				<font face="Arial" color="blue" size="2">
					<strong>Total Inscritos: <%= total %></strong>
				</font>
			</td>
		</tr>		
<%	//Condicion cuando listor = 0
	}else{	%>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td colspan="16" align="center">
			<font face="Arial" color="blue" size="3">
				<strong>No Hay Alumnos inscritos en esta carga...!!</strong>
			</font>
		</td>
	</tr>
<%	}%>
	</table>
	</div>
</body>