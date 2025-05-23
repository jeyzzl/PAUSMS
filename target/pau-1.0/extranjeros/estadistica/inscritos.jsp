<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.catalogo.spring.CatEstado"%>
<%@ page import= "aca.catalogo.spring.CatReligion"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.catalogo.spring.CatTipoAlumno"%>
<%@ page import= "aca.vista.spring.Estadistica"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	String modalidades 	= (String) session.getAttribute("modalidadesReportes");

	List<Estadistica> lisExtranjeros 				= (List<Estadistica>) request.getAttribute("lisExtranjeros");
	HashMap<String,CatModalidad> mapaModalidades	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String,CatCarrera> mapaCarreras			= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,CatPais> mapaPaises 				= (HashMap<String,CatPais>) request.getAttribute("mapaPaises");
	HashMap<String,CatReligion> mapaReligion		= (HashMap<String,CatReligion>) request.getAttribute("mapaReligion");
	HashMap<String,CatEstado> mapaEstados		  	= (HashMap<String,CatEstado>) request.getAttribute("mapaEstados");
 	HashMap<String,CatTipoAlumno> mapaTipos 	  	= (HashMap<String,CatTipoAlumno>) request.getAttribute("mapaTipos");
	int totInscritos 		= 0;	
%>

<div class="container-fluid">
	<h3>Alumnos Extranjeros Inscritos</h3>	
	<div class="alert alert-info">
		<a href="elegir" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>&nbsp;&nbsp;	 
		<a class="btn btn-primary" href="inscritos">Mostrar</a>
	</div>	
	<table class="table table-bordered  table-striped" id="noayuda">  
	<tr> 
	    <th width="3%" height="22" align="center"><font size="2"><spring:message code="aca.Numero"/></font></th>    
		<th width="5%" align="center"><b><font size="2"><spring:message code="aca.Matricula"/></font></b></th>
	    <th width="19%" align="center"><b><font size="2" ><spring:message code="aca.Nombre"/></font></b></th>
		<th width="4%" align="center"><b><font size="2"><spring:message code="aca.Carga"/></font></b></th>
		<th width="27%" align="center"><b><font size="2">Carrera</font></b></th>
	    <th width="4%" align="center"><b><font size="2">Resi.</font></b></th>	
	    <th width="4%" align="center"><b><font size="2">Sexo.</font></b></th>	
	    <th width="15%" align="center"><b><font size="2">Religi&oacute;n</font></b></th>
	    <th width="6%" align="center"><b><font size="2"><spring:message code="aca.Nacionalidad"/></font></b></th>
		<th width="6%" align="center"><b><font size="2"><spring:message code="aca.Pais"/></font></b></th>
		<th width="9%" align="center"><b><font size="2"><spring:message code="aca.Estado"/></font></b></th>	
		<th width="4%" align="center"><b><font size="2">Clas.fin.</font></b></th>
	</tr>
<%
	String codigoPersonalTmp 	= "";
	for(Estadistica estadistica : lisExtranjeros){				
		if(!estadistica.getCodigoPersonal().equals(codigoPersonalTmp)){
			codigoPersonalTmp 	= estadistica.getCodigoPersonal();
			totInscritos++;
			
			String carreraNombre = "-";
			if (mapaCarreras.containsKey(estadistica.getCarreraId())){
				carreraNombre = mapaCarreras.get(estadistica.getCarreraId()).getNombreCarrera();
			}
			
			String religionNombre = "-";
			if (mapaReligion.containsKey(estadistica.getReligionId())){
				religionNombre = mapaReligion.get(estadistica.getReligionId()).getNombreReligion();
			}
			
			String paisNombre = "-";
			if (mapaPaises.containsKey(estadistica.getPaisId())){
				paisNombre = mapaPaises.get(estadistica.getPaisId()).getNombrePais();
			}
			
			String nacionalidadNombre = "-";
			if (mapaPaises.containsKey(estadistica.getNacionalidad())){
				nacionalidadNombre = mapaPaises.get(estadistica.getNacionalidad()).getNombrePais();
			}
			
			String estadoNombre = "-";
			if (mapaEstados.containsKey(estadistica.getPaisId()+estadistica.getEstadoId())){
				estadoNombre = mapaEstados.get(estadistica.getPaisId()+estadistica.getEstadoId()).getNombreEstado();
			}
			
			String tipoAlumno = "-";
			if (mapaTipos.containsKey(estadistica.getTipoAlumnoId())){
				tipoAlumno = mapaTipos.get(estadistica.getTipoAlumnoId()).getNombreTipo() ;
			}
			
			
%>  		
	<tr > 
	    <td width="3%" align="center"><font size="1"><%=totInscritos%></font></td>
	    <td width="5%" align="center"><font size="1"><%=estadistica.getCodigoPersonal()%></font></td>
	    <td width="19%"><font size="1">&nbsp;<%=estadistica.getNombre()%> <%=estadistica.getApellidoPaterno()%> <%=estadistica.getApellidoMaterno()%></font></td>
		<td width="4%"><font size="1">&nbsp;<%=estadistica.getCargaId()%></font></td>
	    <td width="27%" align="lefht"><font size="1"><%=carreraNombre%></font></td>	
	    <td width="4%" align="lefht"><font size="1"><%=estadistica.getResidenciaId().equals("E")?"Externo":"Interno"%></font></td>
	    <td width="4%" align="lefht"><font size="1"><%=estadistica.getSexo().equals("F")?"Mujer":"Hombre"%></font></td>
	    <td width="15%" align="lefht"><font size="1"><%=religionNombre%></font></td>
	    <td width="6%" align="lefht"><font size="1"><%=nacionalidadNombre%></font></td>
		<td width="6%" align="lefht"><font size="1"><%=paisNombre%></font></td>
		<td width="9%" align="lefht"><font size="1"><%=estadoNombre%></font></td>
		<td width="4%" align="lefht"><font size="1"><%=tipoAlumno%></font></td>
	</tr>
<%  
		} // fin del while
	} 
%>
	</table> 
	<br>
	<div class="alert alert-info">Total de Alumnos: <%=totInscritos%></div>
</div>