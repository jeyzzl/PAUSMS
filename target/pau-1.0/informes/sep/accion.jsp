<%@page import="aca.alumno.PlanUtil"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.HashMap" %>

<jsp:useBean id="estadistica" scope="page" class="aca.vista.Estadistica"/>
<jsp:useBean id="estadisticaU" scope="page" class="aca.vista.EstadisticaUtil"/>
<%

	HashMap mapaSector 			= (HashMap)request.getAttribute("mapaSector");
	HashMap mapaSectorNombre 	= (HashMap)request.getAttribute("mapaSectorNombre");

	String alumnos 		= request.getParameter("alumnos");
	String opcion 		= request.getParameter("Opcion");
	
	String carga		= alumnos;	
	String Karga		= alumnos.substring(1,7);
	String planAlum		= "";
	String sBgcolor		= "";
	String facTmp		= "";
	String carrera		= "x"; 
	String carreraNombre= "x";
	//String columnas		= opcion.equals("1")?"11":"16";
	String condicion 	= " ";
	if (opcion.equals("1")||opcion.equals("2")){
		condicion 	= " AND SUBSTR(PLAN_ID,7,8) != 'SR' AND PLAN_ID != 'TEO2000*' AND CARRERA_ID NOT IN ('10209','10210')";
		//condicion 	= " AND SUBSTR(PLAN_ID,7,8) != 'SR' AND (FACULTAD_ID != '105' OR PLAN_ID = 'TEOL2010') AND CARRERA_ID NOT IN ('10209','10210')";
	}	 
	String codigoTemp	= "x";
	
	int cont			= 0;
	int total			= 0;
	int sem				= 0;
	int semestre		= 0;
	int grado 			= 0;
	
	String status		= "";
	String genero 		= "";
	
	boolean updateCiclo = false;
	boolean updateCicloPostgrado = false;	
	
	conEnoc.setAutoCommit(false);	
	if ( aca.alumno.PlanUtil.actualizaCiclo(conEnoc,carga) ){
		updateCiclo = true;
	}
	
	if ( aca.alumno.PlanUtil.actualizaCicloPostgrado(conEnoc,carga) ){
		updateCicloPostgrado = true;
	}
	
	if (updateCiclo || updateCicloPostgrado){
		if (aca.alumno.PlanUtil.actualizaGrado(conEnoc,carga)){  
			conEnoc.commit();
		}else{
			conEnoc.rollback();
		}
	}
	conEnoc.setAutoCommit(true);
	
	// Map de los paises
	java.util.HashMap<String, aca.catalogo.CatPais> mapPais = aca.catalogo.PaisUtil.getMapAll(conEnoc,"");
	
	// Map de los paises
	java.util.HashMap<String,String> mapEstado  = aca.catalogo.EstadoUtil.getMapEstado(conEnoc);
	
	//Map de edades
	java.util.HashMap<String,String> mapEdad  	= aca.alumno.AlumUtil.getMapEdad(conEnoc, carga);
	
	//Map de edades
	java.util.HashMap<String,aca.alumno.AlumPlan> mapPlan  	= PlanUtil.mapAlumPlanes(conEnoc,carga);
	
	//Map de modalidades
	java.util.HashMap<String,String> mapModalidad  	= aca.alumno.AcademicoUtil.getMapModalidad(conEnoc, " WHERE CODIGO_PERSONAL IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CARGA_ID IN ("+carga+") AND ESTADO = 'I')");
	
	// Map de Catalogo de modalidades
	java.util.HashMap<String, aca.catalogo.CatModalidad> mapCatMod 	= aca.catalogo.ModalidadUtil.getMapAll(conEnoc,"");

	ArrayList lisEstadistica = estadisticaU.getListInscritosSE(conEnoc, carga, condicion+" AND CODIGO_PERSONAL||CARGA_ID||PLAN_ID NOT IN (SELECT CODIGO_PERSONAL||CARGA_ID||PLAN_ID FROM ENOC.ALUM_SEQUITA) ORDER BY FACULTAD_ID, CARRERA_ID, ALUMNO_CICLO(CODIGO_PERSONAL,PLAN_ID),APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE");
	
	HashMap<String, String> mapGradoyCiclo			= aca.alumno.EstadoUtil.mapGradoyCiclo(conEnoc, carga, "I");
%>
<div class="container-fluid">
<h2>Alumnos Inscritos en los periodos: <%= carga %></h2>
<div class="alert alert-info">
	<a class="btn btn-primary" href="reporte"><spring:message code="aca.Regresar"/></a>
</div>
	<table style="margin: 0 auto;  width:98%"  >
<%
	if(lisEstadistica.size() > 0){
		for (int i=0; i< lisEstadistica.size(); i++){ 
			aca.vista.Estadistica estad = (aca.vista.Estadistica) lisEstadistica.get(i);
			
			// filtra los alumnos repetidos
			if (!codigoTemp.equals(estad.getCodigoPersonal())){
				codigoTemp = estad.getCodigoPersonal();
				
				cont++;
				if ((cont % 2) == 1 ) sBgcolor = sColor; else sBgcolor = "";
				
				planAlum 	= estad.getPlanId();
				grado 		= 0;
				semestre 	= 0;			
				if (mapPlan.containsKey(estad.getCodigoPersonal()+estad.getPlanId())){
				aca.alumno.AlumPlan plan = mapPlan.get(estad.getCodigoPersonal()+estad.getPlanId());					
					grado 		= Integer.parseInt(plan.getGrado());
					if (mapGradoyCiclo.containsKey(estad.getCargaId()+estad.getCodigoPersonal())){
	  					String[] dato = mapGradoyCiclo.get(estad.getCargaId()+estad.getCodigoPersonal()).split(",");
	  					grado 		= Integer.parseInt(dato[0]);						 
	  					semestre	= Integer.parseInt(dato[1]); 
	  				}
					
					semestre 	=Integer.parseInt(plan.getCiclo());
				}				
				genero 			= estad.getSexo().equals("F")?"M":"H";
				
				String pais 	= "";			 
				if (mapPais.containsKey(estad.getPaisId())){
    				aca.catalogo.CatPais p = mapPais.get(estad.getPaisId());
    				pais 		= p.getNombrePais();
    			}
				
				String estado 	= "";
				if (mapEstado.containsKey(estad.getPaisId()+estad.getEstadoId()))					
					estado = mapEstado.get(estad.getPaisId()+estad.getEstadoId());
				
				String edad = "0";
				if (mapEdad.containsKey(estad.getCodigoPersonal()))
					edad = mapEdad.get(estad.getCodigoPersonal());
				
				String modalidad = "0";
				if (mapModalidad.containsKey(estad.getCodigoPersonal()))
					modalidad = mapModalidad.get(estad.getCodigoPersonal());
				
				String modalidadNombre = "-";
  				if (mapCatMod.containsKey(modalidad)){
  					aca.catalogo.CatModalidad mod = mapCatMod.get(modalidad);
  					modalidadNombre = mod.getNombreModalidad();
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
					carreraNombre 	= aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, estad.getCarreraId());					
					cont = 1;
%>
		</table>
		<table style="margin: 0 auto;  width:98%"  class="table table-condensed">
<%-- 		<tr><td colspan="16" style="font-size:13pt"><%=carrera%>:<%= carreraNombre %><td><tr> --%>
		<tr>
			<th align="center" >Carrera</th>		    
			<th align="center" width="5%">TR</th>	
			<th align="center" width="5%"><spring:message code="aca.Ciclo"/></th>
			<th align="center" width="8%">CURP</th>
			<th align="center" width="30%"><spring:message code="aca.Nombre"/></th>
			<th align="center" width="15%">A. Paterno</th>
			<th align="center" width="15%">A. Materno</th>
			<th align="center" width="10%">Genero</th>
			<th align="center" width="10%"><spring:message code="aca.Numero"/></th>
			<th align="center" width="10%"><spring:message code="aca.Matricula"/></th>
			<th align="center" width="10%"><spring:message code="aca.Carga"/></th>
			<th align="center" align="center" width="10%"><spring:message code="aca.Plan"/></th>		
			<th align="center" width="5%"><spring:message code="aca.Grado"/></th>
			<th align="center" width="10%"><spring:message code="aca.Edad"/></th>
			<th align="center" width="10%"><spring:message code="aca.Status"/></th>
			<th align="center" width="10%">Id Pais</th>
			<th align="center" width="10%"><spring:message code="aca.Pais"/></th>
			<th align="center" width="10%">Id Estado</th>			
			<th align="center" width="10%"><spring:message code="aca.Estado"/></th>
			<th align="center" width="10%"><spring:message code="aca.Modalidad"/></th>
			<th align="center" width="10%">Sector</th>
		</tr>
<%				} %>
		 <tr class="tr2" <%=sBgcolor%>>
		 	<td align="center"><%=carreraNombre%></td>
			<td align="center">2</td>
			<td align="center"><%=semestre %></td>
			<td align="left"><%=estad.getCurp()%></td>
			<td align="left"><%=estad.getNombre()%></td>
			<td align="left"><%=estad.getApellidoPaterno()%></td>
			<td align="left"><%=estad.getApellidoMaterno()%></td>
			<td align="center"><%= genero %></td>
			<td align="center"><%= cont %></td>
			<td><%= estad.getCodigoPersonal() %></td>
			<td><%=estad.getCargaId() %></td>
			<td><%=estad.getPlanId() %></td>
			<td align="center"><%=grado %></td>			
			<td align="center"><%=edad%></td>
			<td align="center"><%=status%></td>	
			<td align="center"><%=estad.getEstadoId()%></td>		
			<td align="center"><%=pais%></td>
			<td align="center"><%=estad.getEstadoId()%></td>
			<td align="center"><%=estado%></td>
			<td align="center"><%=modalidadNombre%></td>
			<td align="center"><%=mapaSector.containsKey(estad.getCodigoPersonal())?mapaSectorNombre.get(mapaSector.get(estad.getCodigoPersonal())):"-"%></td>

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
<%@ include file= "../../cierra_enoc.jsp" %>