<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Inscritos" scope="page" class="aca.vista.InscritosUtil"/>
<jsp:useBean id="Ubicacion" scope="page" class="aca.alumno.AlumUbicacion"/>
<jsp:useBean id="AlumU" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="UbicacionU" scope="page" class="aca.alumno.UbicacionUtil"/>
<jsp:useBean id="CiudadU" scope="page" class="aca.catalogo.CiudadUtil"/>
<jsp:useBean id="EstadoU" scope="page" class="aca.catalogo.EstadoUtil"/>
<jsp:useBean id="CarrearU" scope="page" class="aca.catalogo.CatCarreraUtil"/>
<jsp:useBean id="FacultadU" scope="page" class="aca.catalogo.CatFacultadUtil"/>
<jsp:useBean id="ActualizaU" scope="page" class="aca.alumno.AlumActualizaUtil"/>
<% 
	ArrayList<aca.vista.Inscritos> lisAlumnos		= Inscritos.getListUbicacion(conEnoc, "ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, CODIGO_PERSONAL");
	String sFacultad 		= "X";
	String sBgcolor			= "";
	
	//Map alumnos inscritos
	java.util.HashMap<String, aca.alumno.AlumPersonal>	mapAlum			= AlumU.getMapInscritos(conEnoc);
	
	//Map nombre de pais
	java.util.HashMap<String, aca.catalogo.CatPais> 	mapPais			= aca.catalogo.PaisUtil.getMapAll(conEnoc, "");
	
	//Map Ubicacion
	java.util.HashMap<String, aca.alumno.AlumUbicacion> mapUbicacion	= UbicacionU.mapInscritos(conEnoc);
	
	//Map nombre de Ciudad
	java.util.HashMap<String, aca.catalogo.CatCiudad>	mapNomCiudad	= CiudadU.getMapAll(conEnoc, "");
	
	//Map nombre de Estado
	java.util.HashMap<String, aca.catalogo.CatEstado>	mapNomEstado	= EstadoU.getMapAll(conEnoc, "");
	
	//Map Carrera
	java.util.HashMap<String, aca.catalogo.CatCarrera>	mapCarrera		= CarrearU.getMapAll(conEnoc, "");
	
	//Map Facultad
	java.util.HashMap<String, aca.catalogo.CatFacultad>	mapFacultad		= FacultadU.getMapFacultad(conEnoc, "");
	
	//Map Facultad
	java.util.HashMap<String, String>					mapActualiza	= ActualizaU.mapAll(conEnoc);
%>
<div class="container-fluid">
	<h2><spring:message code="aca.DomicilioPermanenteDeAlumnos"/></h2>
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary">Return</a>&nbsp;&nbsp;
	</div>
<%	
	 
	for(int i=0; i<lisAlumnos.size();i++){
		aca.vista.Inscritos alumno = (aca.vista.Inscritos) lisAlumnos.get(i);
		
		String comunica  	= "-";
		String telefono	 	= "-";
		String celular	 	= "-";
		String apartado	 	= "-";
		String direccion 	= "-";
		String ubicacion 	= "-";
		String ciudad	 	= "-";
		String nomCiudad 	= "-";	
		String estado	 	= "-";
		String nomEstado 	= "-";
		String pais		 	= "-";
		String nomPais 	 	= "-";
		String nomAlum   	= "-";
		String facultadId	= "-";
		String nomFacu		= "-";
		String actualiza	= "-";
		
		if(mapUbicacion.containsKey(alumno.getCodigoPersonal())){
			comunica	= mapUbicacion.get(alumno.getCodigoPersonal()).gettComunica();
			telefono	= mapUbicacion.get(alumno.getCodigoPersonal()).getTTelefono();
			celular		= mapUbicacion.get(alumno.getCodigoPersonal()).gettCelular();
			apartado	= mapUbicacion.get(alumno.getCodigoPersonal()).getTApartado();			
			direccion	= mapUbicacion.get(alumno.getCodigoPersonal()).getTDireccion();	
			ubicacion	= mapUbicacion.get(alumno.getCodigoPersonal()).getTCodigo();
			ciudad 		= mapUbicacion.get(alumno.getCodigoPersonal()).getTCiudad();
			estado		= mapUbicacion.get(alumno.getCodigoPersonal()).getTEstado();
			pais		= mapUbicacion.get(alumno.getCodigoPersonal()).getTPais();
			nomAlum		= mapUbicacion.get(alumno.getCodigoPersonal()).getTNombre();
			
			
			if(mapNomCiudad.containsKey(pais+estado+ciudad)){
				nomCiudad = mapNomCiudad.get(pais+estado+ciudad).getNombreCiudad();				
			}
			if(mapNomEstado.containsKey(pais+estado)){
				nomEstado = mapNomEstado.get(pais+estado).getNombreEstado();			
			}
			
			if(mapPais.containsKey(pais)){
				nomPais   = mapPais.get(pais).getNombrePais();
			}
			
			if(mapCarrera.containsKey(alumno.getCarreraId())){
				facultadId = mapCarrera.get(alumno.getCarreraId()).getFacultadId();
			}
		}
	
		if(mapFacultad.containsKey(facultadId)){
			nomFacu    = mapFacultad.get(facultadId).getNombreFacultad();
		}
		
		if (comunica==null) comunica = "L";
		
		if(comunica.equals("C")){
			comunica = "Carta";
		}else if(comunica.equals("L")){
			comunica = "Celular";
		}else if(comunica.equals("T")){
			comunica = "Tel�fono";
		}else{
			comunica = "E-mail";
		}
		
		if(!sFacultad.equals(facultadId)){
			sFacultad = facultadId;
%>
	<table id="noayuda" width="100%"  height="23">
	<tr>
		<td colspan="12"><b><font size="5"><%=nomFacu%> School</font></b></td>
	</tr>
	</table>
	<table class="table table-striped table-bordered" >
	<tr class="table-info">
	  <th width="5%"><spring:message code="aca.Numero"/></th>
	  <th width="5%"><spring:message code="aca.Matricula"/></th>
	  <th width="30%"><spring:message code="aca.Nombre"/></th>
	  <th width="10%"><spring:message code="aca.Pais"/></th>
	  <th width="10%"><spring:message code="aca.Estado"/></th>
	  <th width="10%"><spring:message code="aca.Ciudad"/></th>
	  <th width="5%"><spring:message code="aca.C.P."/></th>
	  <th width="20%"><spring:message code="aca.Direccion"/></th>
	  <th width="8%%"><spring:message code="aca.Apartado"/></th>
	  <th width="10%"><spring:message code="aca.Celular"/></th>
	  <th width="10%"><spring:message code="aca.Telefono"/></th>
	  <th width="10%">Contact Method</th>
	  <th width="3%">Updated</th>
	</tr>
<%  
		}
		if(mapActualiza.containsKey(alumno.getCodigoPersonal())){
			actualiza = mapActualiza.get(alumno.getCodigoPersonal());
		}
		//if ((i % 2) == 0 ) sBgcolor = sColor; else sBgcolor = "";
		//actualiza = aca.alumno.AlumActualiza.getEstado(conEnoc, alumno.getCodigoPersonal());
%>
	<tr>
    	<td><%= i+1 %></td>
    	<td><%= alumno.getCodigoPersonal() %></td>
    	<td><%= nomAlum %></td>
    	<td><%= nomPais%></td>
    	<td><%= nomEstado %></td>
    	<td><%= nomCiudad %></td>
    	<td><%= ubicacion%></td>
    	<td><%= direccion%></td>
    	<td><%= apartado==null?"-":apartado%></td> <!--check condition-->
    	<td><%= celular %></td>
    	<td><%= telefono %></td>
    	<td><%= comunica %></td>
	    <td align="center"><%=actualiza%></td>
  	</tr>
<% 			
	}
%>
	</table>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>