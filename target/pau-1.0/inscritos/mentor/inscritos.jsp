<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>

<%@ page import= "aca.catalogo.spring.CatPeriodo"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatPais"%>
<%@ page import= "aca.catalogo.spring.CatEstado"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.catalogo.spring.CatReligion"%>
<%@ page import= "aca.acceso.spring.Acceso"%>
<%@ page import= "aca.vista.spring.Inscritos"%>
<%@ page import= "aca.mentores.spring.MentAlumno"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
</script>
<%	
	java.text.DecimalFormat getFormato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String codigo			= (String) session.getAttribute("codigoPersonal");	
	String modalidades		= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
	String cargas			= (String)request.getAttribute("cargas");
	Acceso acceso 			= (Acceso) request.getAttribute("acceso");
	boolean tieneAcceso		= (boolean) request.getAttribute("tieneAcceso");
	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	List<Inscritos> lisInscritos					= (List<Inscritos>) request.getAttribute("lisInscritos");
	
	HashMap<String,CatFacultad> mapaFacultades 		= (HashMap<String,CatFacultad>) request.getAttribute("mapaFacultades");
	HashMap<String,CatCarrera> mapaCarreras 		= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	HashMap<String,CatPais> mapaPaises 				= (HashMap<String,CatPais>) request.getAttribute("mapaPaises");
	HashMap<String,CatModalidad> mapaModalidades 	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	HashMap<String,CatReligion> mapaReligiones 		= (HashMap<String,CatReligion>) request.getAttribute("mapaReligiones");
	HashMap<String,CatEstado> mapaEstados 			= (HashMap<String,CatEstado>) request.getAttribute("mapaEstados");
	HashMap<String, String> mapaGrados				= (HashMap<String,String>) request.getAttribute("mapaGrados");
	HashMap<String, String> mapaEdades				= (HashMap<String,String>) request.getAttribute("mapaEdades");	
	HashMap<String, MentAlumno> mapaMentores		= (HashMap<String,MentAlumno>) request.getAttribute("mapaMentores");
	HashMap<String, String> mapaMaestros			= (HashMap<String,String>) request.getAttribute("mapaMaestros");	
/*	
	if ((String)session.getAttribute("cargas") == null){
		session.setAttribute("cargas", aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()));
	}	
	if(cargas.equals(""))cargas="''";	
*/		
	String facultadId 		= "-";
	String facultadTemp		= "X";
	String sCarrera 		= "X";
	String sCarreraTemp		= "X";
	String grado 			= "0";
	String ciclo			= "0";
	
	int cont 				= 1;
	float edad				= 0f;
	int nInscritos 			= 0; 
	int nInternos			= 0, nExternos 		= 0, nHombres = 0, nMujeres = 0;
	int inscritosFac 		= 0, internosFac = 0, externosFac = 0, hombresFac = 0, mujeresFac = 0;
	
	String codigoTemp		= "";
	boolean otraFac 		= false;	
	
	String lisModo[] 		= modalidades.replace("'", "").split(",");	
			
	if(tieneAcceso==true){		
		if(modalidades.equals(""))modalidades="' '";
		if(cargas.equals(""))cargas="' '";	
%>
<head>		
	<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
</head>	

<div class="container-fluid">
	<h2>Inscritos por Facultad (Mentores)</h2>
	<form id="forma" name="forma" action="inscritos" method="post">
	<div class="alert alert-info">
		<b>Cargas:</b> <%= cargas.replace("'", "")%>&nbsp;<a href="cargas" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
		<b>Modalidades:</b>
<%
		for(String mod:lisModo){				
			String nombreModalidad = "-";
			if(mapaModalidades.containsKey(mod)){ nombreModalidad = mapaModalidades.get(mod).getNombreModalidad(); }
			out.print("["+nombreModalidad+"] ");	
		}		
%>
		<a href="modalidades" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;		
	</div>
	<div class="alert alert-info">		 
		Fecha Inicio: <input id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		Fecha Final: <input id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>		 
	</div>
<%		
		int row = 0;
		for(Inscritos insc : lisInscritos){
			
			if (!insc.getCodigoPersonal().equals(codigoTemp)){
				codigoTemp = insc.getCodigoPersonal();
				
				String carreraNombre	= "-";
				String facultadNombre	= "-";
				if (mapaCarreras.containsKey(insc.getCarreraId())){
					facultadId 			= mapaCarreras.get(insc.getCarreraId()).getFacultadId();
					carreraNombre		= mapaCarreras.get(insc.getCarreraId()).getNombreCarrera();
					if (mapaFacultades.containsKey(facultadId)){
						facultadNombre 	= mapaFacultades.get(facultadId).getNombreFacultad();
					}
				}
				
				String modalidadNombre = "-";
				if (mapaModalidades.containsKey(insc.getModalidadId())){
					modalidadNombre = mapaModalidades.get(insc.getModalidadId()).getNombreModalidad();
				}
				
				String paisNombre = "-";
				if (mapaPaises.containsKey(insc.getPaisId())){
					paisNombre = mapaPaises.get(insc.getPaisId()).getNombrePais();
				}
				
				String nacionNombre = "-";
				if (mapaPaises.containsKey(insc.getNacionalidad())){
					nacionNombre = mapaPaises.get(insc.getNacionalidad()).getNombrePais();
				}
				
				String estadoNombre = "-";
				if (mapaEstados.containsKey(insc.getPaisId()+insc.getEstadoId())){
					estadoNombre = mapaEstados.get(insc.getPaisId()+insc.getEstadoId()).getNombreEstado();
				}
				
				String religionNombre = "-";
				if (mapaReligiones.containsKey(insc.getReligionId())){
					religionNombre = mapaReligiones.get(insc.getReligionId()).getNombreReligion();
				}
				
				// Busca el grado y el ciclo del alumno				
	 			grado ="0"; ciclo = "0";  				
	 			if (mapaGrados.containsKey(insc.getCargaId()+insc.getCodigoPersonal())){
	 				
	 				String[] dato = mapaGrados.get(insc.getCargaId()+insc.getCodigoPersonal()).split(",");
	 				grado 	= dato[0];
	 				ciclo	= dato[1]; 
	 			}
	 			
	 			String mentorId			= "";
	 			String mentorNombre		= "";
	 			if (mapaMentores.containsKey(insc.getCodigoPersonal())){
	 				mentorId			= mapaMentores.get(insc.getCodigoPersonal()).getMentorId();
	 				if (mapaMaestros.containsKey(mentorId)){
	 					mentorNombre = mapaMaestros.get(mentorId);
	 				}
	 			}
				if( acceso.getAccesos().indexOf(insc.getCarreraId())!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){				
					
				    otraFac = false;
					nInscritos++;
					if (insc.getResidenciaId().equals("I")){ nInternos++; }else{ nExternos++; }										 
					if(!facultadId.equals(facultadTemp)){
						otraFac = true;
						if (!facultadTemp.equals("X")){
%>					<table>
						<tr>
							<th colspan="4"><font size="1">Total Programa: <%=inscritosFac %></font></th>
						    <th colspan="3"><font size="1">Internos: <%=internosFac %></font></th>
						    <th colspan="3"><font size="1">Externos: <%=externosFac %></font></th>
						    <th colspan="3"><font size="1">Hombres: <%=hombresFac %></font></th>
						    <th colspan="4"><font size="1">Mujeres: <%=mujeresFac %></font></th>
						</tr>
					</table>
<%					
						} // fin de if (!facultadId.equals("X"))
						facultadTemp = facultadId;
%>	 	
					<br>
					<div class="alert alert-success">
					  <h3><%=facultadNombre%></h3>  
					</div>
<%
					}//fin del if de facultades diferentes
					if(!sCarrera.equals(insc.getCarreraId())){
	   					sCarrera = insc.getCarreraId(); 
%>

<%
						if(row != 0 && otraFac==false){
%>	
					<tr>
						<th colspan="4"><font size="1">Total Programa: <%=inscritosFac %></font></th>
						<th colspan="3"><font size="1">Internos: <%=internosFac %></font></th>
						<th colspan="3"><font size="1">Externos: <%=externosFac %></font></th>
						<th colspan="3"><font size="1">Hombres: <%=hombresFac %></font></th>
						<th colspan="4"><font size="1">Mujeres: <%=mujeresFac %></font></th>
					</tr>
					</table>
<%
						}
						inscritosFac = 0;
						internosFac = 0;
						externosFac = 0;
						hombresFac = 0;
						mujeresFac = 0;
%>
	<table style="width:100%"  class="table table-sm table-bordered table-striped">
  	<tr> 
    	<td colspan="17"><h4><%=carreraNombre%></h4></td>
  	</tr>
  	<tr class="table-info"> 
    	<th width="2%" height="18" align="center"><b><spring:message code="aca.Numero"/></b></th>
		<th width="4%" height="18" align="center"><b><spring:message code="aca.Plan"/></b></th>
    	<th width="5%" height="18" align="center"><b><spring:message code="aca.Matricula"/></b></th>
    	<th width="15%" height="18" align="center"><b><spring:message code="aca.Nombre"/></b></th>
    	<th width="3%" height="18" align="center" title="Fecha Inscripcion"><b>F.Ins</b></th>
    	<th width="3%" height="18" align="center" title="Residencias"><b>Re.</b></th>
    	<th width="3%" height="18" class="center" title="Dormitorio"><b>Do.</b></th>
    	<th width="3%" height="18" align="center" title="Genero"><b>Ge.</b></th>
		<th width="3%" height="18" align="center"><b><spring:message code="aca.Edad"/></b></th>
		<th width="7%" height="18" align="center"><b><spring:message code="aca.Modalidad"/></b></th>
		<th width="4%" height="18" align="center" title="Grado"><b>Gr.</th>
		<th width="4%" height="18" align="center"><b>Sem.</b></th>	
		<th width="5%" height="18" align="center"><b>Nacionalidad</b></th>
		<th width="5%" height="18" align="center"><b><spring:message code="aca.Pais"/></b></th>
		<th width="5%" height="18" align="center"><b><spring:message code="aca.Estado"/></b></th>
		<th width="14%" height="18" align="center"><b>Religión</b></th>
		<th width="16%" height="18" align="center"><b>Mentor</b></th>
  	</tr>
<%	        			cont = 1;
          			}//fin del if de carreras diferentes
					if (mapaEdades.containsKey(insc.getCodigoPersonal())){
						edad = 	Float.valueOf(mapaEdades.get(insc.getCodigoPersonal()));
					}
          			
          			// if (edad>=19 && edad <=29){
	          			sCarreraTemp= insc.getCarreraId().substring(0,3);         				
          				inscritosFac++;
          				if(insc.getResidenciaId().equals("I"))
          					internosFac++;
          				else
          					externosFac++;
          				if(insc.getSexo().equals("M")){
          					hombresFac++;
          					nHombres++;
          				}else{
          					mujeresFac++;
          					nMujeres++;
          				}
%>
	<tr> 
    	<td><font size="1">&nbsp;</font><%=cont%></td>
		<td><font size="1"><%=insc.getPlanId()%></font></td>
    	<td align="center"><font size="1"><%=insc.getCodigoPersonal()%></font></td>
    	<td><font size="1">&nbsp;<%=insc.getApellidoPaterno()%>&nbsp;<%=insc.getApellidoMaterno()%>&nbsp;<%=insc.getNombre()%></font></td>
    	<td align="center"><font size="1"><%=insc.getfInscripcion()%></font></td>
    	<td class="center"><font size="1"><%=insc.getResidenciaId().equals("E")?"Externo":"Interno"%></font></td>
    	<td class="center"><font size="1"><%=insc.getDormitorio()%></font></td>
    	<td class="center"><font size="1"><%=insc.getSexo().equals("M")?"Hombre":"Mujer"%></font></td>
		<td class="center"><font size="1"><%=getFormato.format(edad)%></font></td>
		<td class="center"><font size="1"><%=modalidadNombre%></font></td>
		<td class="center"><font size="1"><%=grado%></font></td>
		<td class="center"><font size="1"><%=ciclo%></font></td>	
		<td class="center"><font size="1"><%=nacionNombre%></font></td>
		<td align="center"><font size="1"><%=paisNombre%></font></td>
		<td align="center"><font size="1"><%=estadoNombre%></font></td>
		<td align="center"><font size="1"><%=religionNombre%></font></td>
		<td align="left"><font size="1"><%=mentorId%>, <%=mentorNombre%></font></td>	
  	</tr>
<%     					cont++;
          			// } // Edad entre 19 y 29
				} //fin del alumno academico
			}	// fin de codigo diferente
			row++;
 		} // fin del for 		
%>
	<tr>
		<th colspan="4"><font size="1">Total Programa: <%=inscritosFac %></font></th>
	    <th colspan="3"><font size="1">Internos: <%=internosFac %></font></th>
	    <th colspan="3"><font size="1">Externos: <%=externosFac %></font></th>
	    <th colspan="3"><font size="1">Hombres: <%=hombresFac %></font></th>
	    <th colspan="4"><font size="1">Mujeres: <%=mujeresFac %></font></th>
	</tr>
	<tr>
		<td colspan="17">&nbsp;</td>
	</tr>
	<tr> 
		<th colspan="4"><font size="1">Inscritos: <%=nInscritos%></font></th>
		<th colspan="3"><font size="1">Internos: <%=nInternos%></font></th>
		<th colspan="3"><font size="1">Externos: <%=nExternos%></font></th>
		<th colspan="3"><font size="1">Hombres: <%=nHombres %></font></th>
		<th colspan="4"><font size="1">Mujeres: <%=nMujeres %></font></th>
	</tr>
	</table>
	</form>
<%	}else{	%>
	<div><b><font color="#000099">No tiene Acceso..!</font> </b> </font></div>
<%	}//fin del if acceso
%>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>