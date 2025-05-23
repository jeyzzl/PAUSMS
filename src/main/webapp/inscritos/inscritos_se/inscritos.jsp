<%@page import="aca.util.Fecha"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.*"%>
<%@page import="aca.carga.Carga"%>
<%@page import="aca.catalogo.CatModalidad"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="carga" scope="page" class="aca.carga.Carga" />
<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil" />
<jsp:useBean id="catModalidad" scope="page" class="aca.catalogo.CatModalidad" />
<jsp:useBean id="catModalidadU" scope="page" class="aca.catalogo.ModalidadUtil" />
<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<jsp:useBean id="inscritosU" scope="page" class="aca.vista.InscritosUtil"/>
<jsp:useBean id="alumPlanU" scope="page" class="aca.alumno.PlanUtil"/>
<jsp:useBean id="academicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="religionDao" scope="page" class="aca.catalogo.CatReligionDao"/>
<jsp:useBean id="modalidadU" scope="page" class="aca.catalogo.ModalidadUtil"/>
<jsp:useBean id="paisU" scope="page" class="aca.catalogo.PaisUtil"/>
<jsp:useBean id="estadoU" scope="page" class="aca.catalogo.EstadoUtil"/>
<jsp:useBean id="tipoU" scope="page" class="aca.catalogo.TipoAlumnoUtil"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
</script>

<style>
	.table th{
		font-size: 11px;
	}
</style>
<%	
	DecimalFormat frmDecimal= new DecimalFormat("###,##0.00; -###,##0.00");

	String codigo			= (String) session.getAttribute("codigoPersonal");	
	String sCarga			= request.getParameter("f_carga");	
	
	String sFacultad 		= "X";
	String sFacTem			= "";
	String sCarrera 		= "X";
	String sCarreraTemp		= "X";	
	String sGrupo			= "";
	String curp 			= "";
	String religion			= "";
	String modalidad		= "";
	String pais				= "";
	String estado			= "";
	String grado 			= "";
	String ciclo			= "";
	String tipo				= "";
	
	int cont 				= 1;
	int edad				= 0;
	int nInscritos 			= 0; 
	int nInternos			= 0, nExternos 		= 0, nHombres = 0, nMujeres = 0,
								nMexicanos = 0, nExtranjeros = 0, nAdventistas = 0, nNoAdventistas = 0;;
	int inscritosFac 		= 0, internosFac = 0, externosFac = 0, hombresFac = 0,
								mujeresFac = 0, mexicanos = 0, extranjeros = 0, adventistas = 0, noAdventistas = 0;
	ArrayList lisInsc		= new ArrayList();
	String codigoTemp		= "";
	boolean otraFac 		= false;
	
	long timeInicio=0, timeFinal = 0;	
	timeInicio = System.currentTimeMillis();	
	HashMap<String, aca.financiero.SaldosAlumnos> listaSaldos 	= aca.financiero.SaldosAlumnosUtil.getListInscritos(conEnoc, "");	
	HashMap<String, aca.alumno.AlumAcademico> mapAcademico 		= academicoU.getMapAcademico(conEnoc);
	HashMap<String, aca.alumno.AlumPlan> mapAlumPlan 			= alumPlanU.getMapInscritos(conEnoc,"");
	HashMap<String, aca.catalogo.CatReligion> mapReligion 		= religionDao.getMapAll(conEnoc,"");	
	HashMap<String, aca.catalogo.CatModalidad> mapModalidad 	= modalidadU.getMapAll(conEnoc,"");
	HashMap<String, aca.catalogo.CatPais> mapPais 				= paisU.getMapAll(conEnoc,"");
	HashMap<String, aca.catalogo.CatEstado> mapEstado			= estadoU.getMapPaisEstado(conEnoc,"");
	HashMap<String, aca.catalogo.CatTipoAlumno> mapTipo 		= tipoU.getMapAll(conEnoc,"");
	
	timeFinal = System.currentTimeMillis();
	//System.out.println("Tiempo de ejecución:"+(timeFinal-timeInicio));
	
	aca.alumno.AlumPersonal alumno = new aca.alumno.AlumPersonal();	
	acceso = AccesoU.mapeaRegId(conEnoc, codigo);	
	if(AccesoU.existeReg(conEnoc, codigo)==true){		
		
		String cargas						= (String)session.getAttribute("cargasActivas") == null?aca.carga.CargaUtil.getCargasActivas(conEnoc, aca.util.Fecha.getHoy()):session.getAttribute("cargasActivas").toString();
		String modalidades					= (String)session.getAttribute("modalidades") == null?"'1'":session.getAttribute("modalidades").toString();
		String fInscripcion					= request.getParameter("fecha")==null?Fecha.getHoy():request.getParameter("fecha");
		ArrayList<CatModalidad> lisModalidad = catModalidadU.getListAll(conEnoc, "ORDER BY MODALIDAD_ID");
		ArrayList<Carga> lisCarga = cargaU.getListPorFecha(conEnoc, fInscripcion, "ORDER BY CARGA_ID");
		
		String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
		String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
		String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		String periodoId 		= aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc);
		
		if (accion.equals("1")){		
			session.setAttribute("fechaIni", fechaIni);
			session.setAttribute("fechaFin", fechaFin);
		} 
		
		String lisModo[] 		= modalidades.replace("'", "").split(",");
		
		HashMap<String, String> mapGradoyCiclo						= aca.alumno.EstadoUtil.mapGradoyCiclo(conEnoc, cargas, "I");

		
		if(modalidades.equals(""))modalidades="' '";
		if(cargas.equals(""))cargas="' '";
%>
<head>		
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
</head>	
<div class="container-fluid">
	<h2>Inscritos para S.E.</h2>
	<form id="forma" name="forma" action="inscritos?Accion=1" method="post">
		<div class="alert alert-info">
			<b>Cargas:</b> <%= cargas.replace("'", "")%>&nbsp;<a href="cargasActivas" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
			<b>Modalidades:</b>
<%
			for(String mod:lisModo){
				String nombreModalidad = aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, mod);
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
		lisInsc	= inscritosU.getListAllUM(conEnoc, " WHERE CARGA_ID IN("+cargas+") AND MODALIDAD_ID IN ("+modalidades+")"+
					" AND F_INSCRIPCION BETWEEN TO_DATE('"+fechaIni+"','DD/MM/YYYY') AND TO_DATE('"+fechaFin+"','DD/MM/YYYY')"+
					" ORDER BY ENOC.FACULTAD(CARRERA_ID), CARRERA_ID, MODALIDAD_ID, APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");		
		for(int i=0; i<lisInsc.size(); i++){			
			aca.vista.Inscritos insc = (aca.vista.Inscritos) lisInsc.get(i);
			if (!insc.getCodigoPersonal().equals(codigoTemp)){
				codigoTemp = insc.getCodigoPersonal();
				if( acceso.getAccesos().indexOf(insc.getCarreraId())!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
					
					alumno = AlumUtil.mapeaRegId(conEnoc,insc.getCodigoPersonal());
				    otraFac = false;
					nInscritos++;
					if (insc.getResidenciaId().equals("I")){ nInternos++; }else{ nExternos++; }
					sFacTem = aca.catalogo.CatCarreraUtil.getFacultadId(conEnoc, insc.getCarreraId());
					curp = aca.alumno.AlumUtil.getCurp(conEnoc,insc.getCodigoPersonal()); 
					if(!sFacultad.equals(sFacTem)){						
						otraFac = true;
						if (!sFacultad.equals("X")){
%>						
						<tr class="table-info">
							<th ><font size="1">Total Programa: <%=inscritosFac %></font></th>
							<th ><font size="1">Internos: <%=internosFac %></font></th>
							<th ><font size="1">Externos: <%=externosFac %></font></th>
							<th ><font size="1">Hombres: <%=hombresFac %></font></th>
							<th ><font size="1">Mujeres: <%=mujeresFac %></font></th>
							<th ><font size="1">Mexicanos: <%=mexicanos %></font></th>
							<th ><font size="1">Extranjeros: <%=extranjeros %></font></th>
							<th ><font size="1">Adventistas: <%=adventistas %></font></th>
							<th ><font size="1">No Adventistas: <%=noAdventistas %></font></th>
							<th >&nbsp;</th>
							<th >&nbsp;</th>
							<th >&nbsp;</th>
						</tr>
					</table>
<%					
						} // fin de if (!sFacultad.equals("X"))
		    			sFacultad = sFacTem;
%>	 	
						<table id="noayuda" width="90%"  height="23">				  
						  <tr>
						    <td align="center">&nbsp;</td>
						  </tr>
						  <tr>
						    <td align="center" class="titulo"><h4><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,sFacultad)%></h4></td>
						  </tr>						  
						</table>
<%
					}//fin del if de facultades diferentes
					if(!sCarrera.equals(insc.getCarreraId())){
	   					sCarrera = insc.getCarreraId();
	   					
	   					if(i != 0 && otraFac==false){%>	
						  <tr class="table-info">
							<th ><font size="1">Total Programa: <%=inscritosFac %></font></th>
							<th ><font size="1">Internos: <%=internosFac %></font></th>
							<th ><font size="1">Externos: <%=externosFac %></font></th>
							<th ><font size="1">Hombres: <%=hombresFac %></font></th>
							<th ><font size="1">Mujeres: <%=mujeresFac %></font></th>
							<th ><font size="1">Mexicanos: <%=mexicanos %></font></th>
							<th ><font size="1">Extranjeros: <%=extranjeros %></font></th>
							<th ><font size="1">Adventistas: <%=adventistas %></font></th>
							<th ><font size="1">No Adventistas: <%=noAdventistas %></font></th>
							<th >&nbsp;</th>
							<th >&nbsp;</th>
							<th >&nbsp;</th>
						  </tr>
						</table>
<%
						}
						inscritosFac = 0;
						internosFac = 0;
						externosFac = 0;
						hombresFac = 0;
						mujeresFac = 0;
						mexicanos = 0;
						extranjeros = 0;
						adventistas = 0;
						noAdventistas = 0;
					%><br>
					<table style="width:100%"  class="table table-sm table-bordered table-fontsmall">
					  <tr> 
					    <td class="tabla3" colspan="20"><h5>Programa: <%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, sCarrera)%></h5></td>
					  </tr>
					  <tr class="table-info">  
					    <th align="center"><spring:message code="aca.Numero"/></th>
						<th align="center"><spring:message code="aca.Plan"/></th>
					    <th align="center"><spring:message code="aca.Matricula"/></th>
					    <th align="center"><spring:message code="aca.Nombre"/></th>
					    <th align="center">Genero</th>
						<th align="center"><spring:message code="aca.Edad"/></th>
						<th align="center"><spring:message code="aca.Grado"/></th>
						<th align="center">Sem.</th>
						<th align="center"><spring:message code="aca.Modalidad"/></th>
						<th align="center">Pais</th>
						<th align="center">Estado</th>
						<th align="center">E. Civil</th>			
					  </tr>
<%	        			cont = 1;
          			}//fin del if de carreras diferentes          			
          			edad = aca.alumno.AlumUtil.getEdad(conEnoc, insc.getCodigoPersonal());
          			// if (edad>=19 && edad <=29){
	          			sCarreraTemp= insc.getCarreraId().substring(0,3);
    	      			if(sCarreraTemp.equals("107")){ 
        	  				sGrupo = aca.vista.Estadistica.getGrupo(conEnoc,insc.getCodigoPersonal());
          					if(sGrupo.equals("X")) sGrupo= "I";
          				}else{ sGrupo = ""; }          				
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
          			
          				String estadoCivil = alumno.getEstadoCivil();
          				if(estadoCivil.equals("S"))estadoCivil="Solter"+(alumno.getSexo().equals("M")?"o":"a");
          				if(estadoCivil.equals("C"))estadoCivil="Casad"+(alumno.getSexo().equals("M")?"o":"a");
          				if(estadoCivil.equals("D"))estadoCivil="Divorciad"+(alumno.getSexo().equals("M")?"o":"a");
          				if(estadoCivil.equals("V"))estadoCivil="Viud"+(alumno.getSexo().equals("M")?"o":"a");
          				
          				// Obtiene el nombre de la religion
          				religion = "";
          				if (mapReligion.containsKey(insc.getReligionId())){
          					aca.catalogo.CatReligion rel = mapReligion.get(insc.getReligionId());
          					religion = rel.getNombreCorto();
          				}
          				if(insc.getReligionId().equals("1")){
          					adventistas++;
          					nAdventistas++;
          				}
          				else{
          					noAdventistas++;
          					nNoAdventistas++;
          				}
          				
          				// Obtiene el nombre de la modalidad
          				modalidad = "";
          				if (mapModalidad.containsKey(insc.getModalidadId())){
          					aca.catalogo.CatModalidad mod = mapModalidad.get(insc.getModalidadId());
          					modalidad = mod.getNombreModalidad();
          				}
          				
          				// Obtiene el nombre del pais
          				pais = "";
          				if (mapPais.containsKey(insc.getPaisId())){
          					aca.catalogo.CatPais p = mapPais.get(insc.getPaisId());
          					pais 	= p.getNombrePais();
          				}
          				
          				// Obtiene el nombre del estado
          				estado = "";
          				if (mapEstado.containsKey(insc.getPaisId()+insc.getEstadoId())){
          					aca.catalogo.CatEstado e = mapEstado.get(insc.getPaisId()+insc.getEstadoId());
          					estado 	= e.getNombreEstado();
          				}
          				
          				if(insc.getPaisId().equals("91")){
          					mexicanos++;
          					nMexicanos++;
          				}
          				else{
          					extranjeros++;
          					nExtranjeros++;
          				}
          				
          				
          				// Busca el grado y el ciclo del alumno				
          				grado ="0"; ciclo = "0";  				
          				if (mapGradoyCiclo.containsKey(insc.getCargaId()+insc.getCodigoPersonal())){
          					
          					String[] dato = mapGradoyCiclo.get(insc.getCargaId()+insc.getCodigoPersonal()).split(",");
          					grado 	= dato[0];
          					ciclo	= dato[1]; 
          				}          				
          				
          				// Obtiene el nombre del tipo de alumno 
          				tipo = "";
          				if (mapAcademico.containsKey(insc.getCodigoPersonal())){
          					aca.alumno.AlumAcademico alumAcademico = mapAcademico.get(insc.getCodigoPersonal());   					
          					if (mapTipo.containsKey(alumAcademico.getTipoAlumno())){
              					aca.catalogo.CatTipoAlumno tipoAlumno = mapTipo.get(alumAcademico.getTipoAlumno());
              					tipo = tipoAlumno.getNombreTipo(); 
              				}          					
          				}  
          				
          				String humano = "";
          				if(insc.getSexo().equals("F")){
          					humano = "Mujer";
          				}else{
          					humano = "Hombre";
          				}
%>
						  <tr> 
						    <td><font size="1">&nbsp;</font><%=cont%></td>
							<td><font size="1"><%=insc.getPlanId()%></font></td>
						    <td align="center"><font size="1"><%=insc.getCodigoPersonal()%></font></td>
						    <td><font size="1">&nbsp;<%=insc.getApellidoPaterno()%>&nbsp;<%=insc.getApellidoMaterno()%>&nbsp;<%=insc.getNombre()%></font></td>
						    <td align="center"><font size="1"><%=humano%></font></td>
							<td align="center"><font size="1"><%=edad%></font></td>
							<td align="center"><font size="1"><%=grado%></font></td>
							<td align="center"><font size="1"><%=ciclo%></font></td>
							<td align="center"><font size="1"><%=modalidad%></font></td>
							<td align="center"><font size="1"><%=pais%></font></td>
							<td align="center"><font size="1"><%=estado%></font></td>
							<td align="center"><font size="1"><%=estadoCivil %></font></td>								
						<%								
							double saldo 	= Double.parseDouble(insc.getSaldo());														
							double deudaHoy = 0;							
							
							if(listaSaldos.containsKey(insc.getCodigoPersonal())){
								aca.financiero.SaldosAlumnos saldoAlumno = listaSaldos.get(insc.getCodigoPersonal());								
								deudaHoy	= Double.parseDouble(saldoAlumno.getSVencido());																						 
							}
							
							String color = "#AE2113";
							if(saldo>=0) color = "green";
							%>	
						<%	color = "#AE2113";
							if(deudaHoy>=0) color = "green";
						%>						
						  </tr>
<%     					cont++;
          			// } // Edad entre 19 y 29
				} //fin del alumno academico
			}	// fin de codigo diferente
 		} // fin del for
 		lisInsc= null;
%>
			<tr>
				<th ><font size="1">Total Programa: <%=inscritosFac %></font></th>
				<th ><font size="1">Internos: <%=internosFac %></font></th>
				<th ><font size="1">Externos: <%=externosFac %></font></th>
				<th ><font size="1">Hombres: <%=hombresFac %></font></th>
				<th ><font size="1">Mujeres: <%=mujeresFac %></font></th>
				<th ><font size="1">Mexicanos: <%=mexicanos %></font></th>
				<th ><font size="1">Extranjeros: <%=extranjeros %></font></th>
				<th ><font size="1">Adventistas: <%=adventistas %></font></th>
				<th ><font size="1">No Adventistas: <%=noAdventistas %></font></th>
			</tr>
			<tr> 
				<th ><font size="1">Inscritos: <%=nInscritos %></font></th>
				<th ><font size="1">Internos: <%=nInternos %></font></th>
				<th ><font size="1">Externos: <%=nExternos %></font></th>
				<th ><font size="1">Hombres: <%=nHombres %></font></th>
				<th ><font size="1">Mujeres: <%=nMujeres %></font></th>
				<th ><font size="1">Mexicanos: <%=nMexicanos %></font></th>
				<th ><font size="1">Extranjeros: <%=nExtranjeros %></font></th>
				<th ><font size="1">Adventistas: <%=nAdventistas %></font></th>
				<th ><font size="1">No Adventistas: <%=nNoAdventistas %></font></th>
			</tr>
		</table>
	</form>
<div align="center"><font size="3" face="Times New Roman, Times, serif"> <br>
<b><font color="#000099"></font> </b> </font></div>
<%	}else{	%>
<br>
<b><font color="#000099">No tiene Acceso..!</font> </b> </font></div>
<%	}//fin del if acceso
%>
</form>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>
<%@ include file= "../../cierra_enoc.jsp" %>