<%@ page import= "java.text.*"%>
<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatTipoCal"%>
<%@ page import= "aca.carga.spring.Carga"%>
<%@ page import= "aca.vista.spring.AlumnoCurso"%>
<%@ page import= "aca.vista.spring.Estadistica"%>
<%@ page import= "aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script>
	function grabaPeriodo(periodoId){
		document.location.href="listadoDetalle?cambioPeriodo=1&PeriodoId="+periodoId;
	}	
	function grabaCarga(cargaId){
 		document.location.href="listadoDetalle?cambioCarga=1&CargaId="+cargaId;
	}	
</script>

<!-- inicio de estructura -->
<%
	String codigoUsuario	= (String) session.getAttribute("codigoPersonal");

	String periodoId 		= (String)session.getAttribute("periodo");
	String cargaId 			= (String)session.getAttribute("cargaId");
	Acceso acceso 			= (Acceso)request.getAttribute("acceso");
	
	String cambioPeriodo 	= request.getParameter("cambioPeriodo")==null?"0":request.getParameter("cambioPeriodo");
	String cambioCarga 		= request.getParameter("cambioCarga")==null?"0":request.getParameter("cambioCarga");
	
	if (cambioPeriodo.equals("1")){
		periodoId = request.getParameter("PeriodoId");
		session.setAttribute("periodo", periodoId);
	}
	if (cambioCarga.equals("1")){
		cargaId = request.getParameter("CargaId");
		session.setAttribute("cargaId", cargaId);
	}
			
	String sBgcolor			= "";
	
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>) request.getAttribute("lisPeriodos");
	List<Carga> lisCargas 						= (List<Carga>) request.getAttribute("lisCargas");	
	List<Estadistica> lisReprobados 			= (List<Estadistica>) request.getAttribute("lisReprobados");	
	List<AlumnoCurso> lisCursos 				= (List<AlumnoCurso>) request.getAttribute("lisCursos");
	
	HashMap<String,String> mapaMaestros 		= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String,CatTipoCal> mapaTipos 		= (HashMap<String,CatTipoCal>) request.getAttribute("mapaTipos");
	HashMap<String,CatFacultad> mapaFacultades	= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	
	String codigoAlumno		= "X";	
	String facultad			= "X";	
	String carreraTemp		= "X";
	int nInscritos			= 0;
	int cont= 1, row=0;	
%>
<div class="container-fluid">
	<h2>Calificaciones por Alumno</h2>
	<form id="noayuda" name="forma" action="listadoDetalle" method='post'>
	<div class="alert alert-info">
		<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		&nbsp;
    	<spring:message code="aca.Periodo"/>: 
		<select onchange="javascript:grabaPeriodo(this.value);" name="PeriodoId" class="input input-medium">
<%		for(CatPeriodo periodo : lisPeriodos){ %>
			<option <%if(periodoId.equals(periodo.getPeriodoId()))out.print(" Selected ");%> value="<%=periodo.getPeriodoId()%>"><%=periodo.getNombre().replace("Periodo ", "")%></option>
<%		}%>
		</select>&nbsp;&nbsp;
		Carga: [ <%=cargaId%> ] 
    	<select name="CargaId" align="center" class="input input-xlarge" onchange="javascript:grabaCarga(this.value);">
<%		for( Carga carga: lisCargas){%>			
			<option value="<%=carga.getCargaId()%>" <%=cargaId.equals(carga.getCargaId())?"selected":""%>>
				<%=carga.getNombreCarga()%>
			</option>
<%		}%>		
		</select>
  	</div>
	</form>
	<table class="table table-bordered">
<%
	/* cierra_enoc */
	java.util.Iterator iter = lisReprobados.iterator();
	while(iter.hasNext()){
		Estadistica inscrito = (Estadistica)iter.next();
		
		codigoAlumno = inscrito.getCodigoPersonal();						
		//System.out.println("Paso 1:"+codigoAlumno+":"+acceso.getAdministrador() );		
		if( (acceso.getAccesos().indexOf(inscrito.getCarreraId()) != -1) || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
			nInscritos++;
			//System.out.println("Paso 2:"+codigoAlumno);
			String facultadNombre = "";
			if (mapaFacultades.containsKey(inscrito.getFacultadId())){
				facultadNombre = mapaFacultades.get(inscrito.getFacultadId()).getNombreFacultad();
			}
			
			String carreraNombre = "";
			if (mapaCarreras.containsKey(inscrito.getCarreraId())){
				carreraNombre = mapaCarreras.get(inscrito.getCarreraId()).getNombreCarrera();
			}
			
			if(!facultad.equals(inscrito.getFacultadId())){	
	   	 		facultad = inscrito.getFacultadId();	
%>	 	

	<thead>
	<tr>
    	<td align="center" class="titulo" colspan="7"><%=facultadNombre%></td>
  	</tr>
  	</thead>
<%  
       		}//fin del if de facultades diferentes
			if(!inscrito.getCarreraId().equals( carreraTemp)){
	   			carreraTemp = inscrito.getCarreraId();
%>
	<thead>
	<tr> 
    	<td height="28" class="titulo3" colspan="7">Programa: <%=carreraNombre%></td>
  	</tr>
  	</thead>
<%
          		cont = 1;
        	}//fin del if de carreras diferentes
%>
	<thead>
 	<tr><td colspan="7" align="center">&nbsp;</td></tr>				
  	</thead>
	<thead>
  	<tr> 
		<td width="4%" height="21"><b><%=cont%></b></td>
		<td colspan="6" align="left"><b>
	  	[<%=codigoAlumno%>] &nbsp; <%=inscrito.getNombre() %>,<%=inscrito.getApellidoPaterno()+" "+inscrito.getApellidoMaterno()%>&nbsp; &nbsp;
	  	<%=inscrito.getPlanId()%></b>
		</td>
  	</tr>  
  	</thead>
	<thead class="table-info">
 	<tr> 
    	<th align="center"><spring:message code="aca.Numero"/></th>
    	<th align="center"><spring:message code='aca.Creditos'/></th>
    	<th align="center"><spring:message code="aca.Materia"/></th>
  		<th align="center"><spring:message code="aca.Maestro"/></th>
    	<th align="center"><spring:message code="aca.Nota"/></th>
    	<th align="center"><spring:message code="aca.Extra"/></th>
    	<th align="center"><spring:message code="aca.Tipo"/></th>
  	</tr>
  	</thead>
<%
  			cont++; row=0;
			for  (int j=0; j<lisCursos.size(); j++){ 
				AlumnoCurso alumnoCurso = (AlumnoCurso) lisCursos.get(j);
				if ( alumnoCurso.getCodigoPersonal().equals(inscrito.getCodigoPersonal()) ){
					
					String maestroNombre = "";
					if (mapaMaestros.containsKey(alumnoCurso.getMaestro())){
						maestroNombre = mapaMaestros.get(alumnoCurso.getMaestro());
					}
					
					String tipoNombre = "";
					if (mapaTipos.containsKey(alumnoCurso.getTipoCalId())){
						tipoNombre = mapaTipos.get(alumnoCurso.getTipoCalId()).getNombreTipoCal();
					}
%>
						<tr> 
							<td align="center"><%=row+1%></td>
							<td align="left"><b><%=alumnoCurso.getCreditos()%></td>
							<td align="left"><%=alumnoCurso.getNombreCurso()%></td>
							<td align="left"><%=maestroNombre%></td>
							<td align="center"><%=alumnoCurso.getNota() %></td>
							<td align="center"><%=alumnoCurso.getNotaExtra() %></td>
							<td align="center"><%=tipoNombre%></td>
						</tr>
<%						row++;
				}
			}					
		} // fin de if esta autorizado a ver la carrera 
	}// fin del while
%>
	</table>
</div>