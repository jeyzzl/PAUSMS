<%@ page import="java.util.*"%>
<%@ page import="java.text.DecimalFormat"%>

<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.financiero.spring.SaldosAlumnos"%>
<%@page import="aca.catalogo.spring.CatFacultad"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatReligion"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatTipoAlumno"%>
<%@page import="aca.alumno.spring.AlumAcademico"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.vista.spring.Inscritos"%>
<%@page import="aca.financiero.spring.FinSaldo"%>
<%@page import="aca.carga.spring.CargaAlumno"%>

<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Mostrar(){		
		var x = document.getElementById("loading");
  	  	if (x.style.display === "none") {
 	    	x.style.display = "block";
  	  	} else {
  	    	x.style.display = "none";
  	  	}
		document.forma.submit();
	}
</script>	
<%	
	DecimalFormat frmDecimal= new DecimalFormat("###,##0.00; -###,##0.00");
	DecimalFormat frmEntero	= new DecimalFormat("###,##0; -###,##0");

	String codigo 			= (String) request.getAttribute("codigo");
	String fechaIni 		= (String) request.getAttribute("fechaIni");
	String fechaFin 		= (String) request.getAttribute("fechaFin");
	String accion 			= (String) request.getAttribute("accion");
	Acceso acceso 			= (Acceso) request.getAttribute("acceso");
	boolean tieneAcceso 	= (boolean) request.getAttribute("tieneAcceso");
	String cargas			= (String) request.getAttribute("cargas");
	String modalidades		= (String) request.getAttribute("modalidades");
	String fInscripcion		= (String) request.getAttribute("fInscripcion");
	String conSaldo			= (String) request.getAttribute("conSaldo");
	
	List<CatModalidad> lisModalidad 			= (List<CatModalidad>) request.getAttribute("lisModalidad");
	List<Carga> lisCarga 						= (List<Carga>) request.getAttribute("lisCarga");
	//HashMap<String, SaldosAlumnos> listaSaldos 	= (HashMap<String, SaldosAlumnos>) request.getAttribute("listaSaldos");
	HashMap<String, CatFacultad> mapFacultad 	= (HashMap<String, CatFacultad>) request.getAttribute("mapFacultad");
	HashMap<String, CatCarrera> mapCarrera 		= (HashMap<String, CatCarrera>) request.getAttribute("mapCarrera");
	HashMap<String, CatReligion> mapReligion 	= (HashMap<String, CatReligion>) request.getAttribute("mapReligion");
	HashMap<String, CatModalidad> mapModalidad 	= (HashMap<String, CatModalidad>) request.getAttribute("mapModalidad");
	HashMap<String, CatPais> mapPais 			= (HashMap<String, CatPais>) request.getAttribute("mapPais");
	HashMap<String, CatEstado> mapEstado 		= (HashMap<String, CatEstado>) request.getAttribute("mapEstado");
	HashMap<String, CatTipoAlumno> mapTipo 		= (HashMap<String, CatTipoAlumno>) request.getAttribute("mapTipo");
	HashMap<String, AlumAcademico> mapAcademico = (HashMap<String, AlumAcademico>) request.getAttribute("mapAcademico");
	HashMap<String, AlumPlan> mapAlumPlan 		= (HashMap<String, AlumPlan>) request.getAttribute("mapAlumPlan");
	HashMap<String, String> mapEdad 			= (HashMap<String, String>) request.getAttribute("mapEdad");
	HashMap<String, AlumPersonal> mapAlumno 	= (HashMap<String, AlumPersonal>) request.getAttribute("mapAlumno");
	HashMap<String, String> mapCreditos 		= (HashMap<String, String>) request.getAttribute("mapCreditos");
	HashMap<String, String> mapGradoyCiclo 		= (HashMap<String, String>) request.getAttribute("mapGradoyCiclo");
	HashMap<String, CargaAlumno> mapaCargasAlumno 	= (HashMap<String,CargaAlumno>) request.getAttribute("mapaCargasAlumno");
	String[] lisModo 							= (String[]) request.getAttribute("lisModo");
	List<Inscritos> lisInsc 					= (List<Inscritos>) request.getAttribute("lisInsc");
	
	HashMap<String, String> mapaFormal 			= (HashMap<String, String>) request.getAttribute("mapaFormal");
	HashMap<String, String> mapaCimum 			= (HashMap<String, String>) request.getAttribute("mapaCimum");
	HashMap<String, String> mapaIdiomas 		= (HashMap<String, String>) request.getAttribute("mapaIdiomas");
	HashMap<String, FinSaldo> mapaSaldos 		= (HashMap<String, FinSaldo>) request.getAttribute("mapaSaldos");
	HashMap<String, String> mapaUsoImagen		= (HashMap<String, String>) request.getAttribute("mapaUsoImagen");
	
	int totalPorSemestre[] = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	String sFacultad 		= "X";
	String sFacTem			= "";
	String sCarrera 		= "X";
	String sCarreraTemp		= "X";	
	String sGrupo			= "";	
	String religion			= "";
	String modalidad		= "";
	String pais				= "";
	String estado			= "";
	String nacionalidad		= "";
	String grado 			= "";
	String ciclo			= "";
	String tipo				= "";
	
	DecimalFormat dmf	= new DecimalFormat("###,##0.00; (###,##0.00)");
	
	int cont 				= 1;	
	int nInscritos 			= 0; 
	int nInternos			= 0, nExternos 		= 0, nHombres = 0, nMujeres = 0, nMexicanos = 0, nExtranjeros = 0, nAdventistas = 0, nNoAdventistas = 0;
	int inscritosFac 		= 0, internosFac = 0, externosFac = 0, hombresFac = 0,	mujeresFac = 0, mexicanos = 0, extranjeros = 0, adventistas = 0, noAdventistas = 0;
	int numCred=0;
	int totalCreditos=0;
	
	String codigoTemp		= "";
	boolean otraFac 		= false;	
	
	if(tieneAcceso){
		
		if(modalidades.equals("")) modalidades="' '";
		if(cargas.equals(""))cargas="' '";
%>
<head>		
	<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />
</head>
<div class="container-fluid">
	<h2>Enrolled by School</h2>
	<form id="forma" name="forma" action="inscritos?Accion=1" method="post">
	<div class="alert alert-info">
		<b>Loads:</b> <%= cargas.replace("'", "")%>&nbsp;<a href="cargasActivas" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;&nbsp;
		<b>Modalities:</b>
<%
		for(String mod:lisModo){
			String nombreModalidad = "-";
			if(mapModalidad.containsKey(mod)){
				nombreModalidad = mapModalidad.get(mod).getNombreModalidad();
			}
			out.print("["+nombreModalidad+"] ");	
		}		
%>
		<a href="modalidades" class="btn btn-primary btn-sm"><i class="fas fa-pencil-alt"></i></a>&nbsp;	
		<b>Balances:</b>	
		<select name="ConSaldo">
			<option value="S" <%=conSaldo.equals("S")?"selected":""%>>Yes</option>
			<option value="N" <%=conSaldo.equals("N")?"selected":""%>>No</option>
		</select>
	</div>
	<div id="loading" style="display: none;">
		<button class="btn btn-primary" type="button" disabled>
	  		<span class="spinner-border spinner-border-sm" role="status"></span>
	  		Searching...
		</button><br><br>
 	</div>
	<div class="alert alert-info">		 
		Start date: <input data-format="hh:mm:ss" id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		<span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
		End date: <input data-format="hh:mm:ss" id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10" style="width:120px;"/>&nbsp;&nbsp;
		<span class="add-on"><i data-time-icon="icon-time" data-date-icon="icon-calendar"></i></span>
		<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>		 
	</div>	
<%	
		int i = 0;
		for(Inscritos insc : lisInsc){			
			if (!insc.getCodigoPersonal().equals(codigoTemp)){
				codigoTemp = insc.getCodigoPersonal();
				if( acceso.getAccesos().indexOf(insc.getCarreraId())!= -1 || acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S") ){
					
				    otraFac = false;
					nInscritos++;
					if (insc.getResidenciaId().equals("I")){ nInternos++; }else{ nExternos++; }
					
					// Busca la facultad y la carrera
					String nombreCarrera = "";
					if (mapCarrera.containsKey( insc.getCarreraId() )){
						sFacTem 		= mapCarrera.get( insc.getCarreraId() ).getFacultadId();
						nombreCarrera 	= mapCarrera.get( insc.getCarreraId() ).getNombreCarrera();
					}					
					
					String estadoCivil 	= "";
					String curp 		= "";
					if (mapAlumno.containsKey( insc.getCodigoPersonal() )){
						AlumPersonal alumno = (AlumPersonal) mapAlumno.get( insc.getCodigoPersonal());
						curp = alumno.getCurp();
						
						estadoCivil = alumno.getEstadoCivil();
          				if(estadoCivil.equals("S"))estadoCivil="Single";
          				if(estadoCivil.equals("C"))estadoCivil="Married";
          				if(estadoCivil.equals("D"))estadoCivil="Divorced";
          				if(estadoCivil.equals("V"))estadoCivil="Widow";
          				
					}else{
						curp = " ";
					}
					
					double edad = 0;
					if (mapEdad.containsKey(insc.getCodigoPersonal())){
						edad = Double.parseDouble(mapEdad.get( insc.getCodigoPersonal() ));
					}
					
					String saldoTotal 	= "";
					String saldoVencido =  "";
					FinSaldo finSaldo = new FinSaldo();
					
					if (mapaSaldos.containsKey(insc.getCodigoPersonal())){
						finSaldo = mapaSaldos.get(insc.getCodigoPersonal());
						saldoTotal = dmf.format(Float.parseFloat(finSaldo.getSaldoSP()))+" Db";
						saldoVencido =  dmf.format(Float.parseFloat(finSaldo.getSaldoVencido()))+" Db";
						if(Float.parseFloat(finSaldo.getSaldoSP()) < 0){
							saldoTotal = dmf.format(Float.parseFloat(finSaldo.getSaldoSP().substring(1)))+" Cr";
						}
					}
					
					String usoImagen = "";
					if(mapaUsoImagen.containsKey(insc.getCodigoPersonal())){
						usoImagen = mapaUsoImagen.get(insc.getCodigoPersonal());
					}
					 
					if(!sFacultad.equals(sFacTem)){					
						otraFac = true;						 
						if (!sFacultad.equals("X")){
%>						
							<tr>
								<th colspan="4"><font size="1">Total by Program: <%=inscritosFac %></font></th>
								<th colspan="24">
								<font size="1">
									[ Boarding: <%=internosFac %> ]&nbsp;&nbsp;
									[ Off-campus: <%=externosFac%> ]&nbsp;&nbsp;
									[ Female: <%=mujeresFac%> ]&nbsp;&nbsp;
									[ Male: <%=hombresFac%> ]&nbsp;&nbsp;
									[ Nationals: <%=mexicanos%> ]&nbsp;&nbsp;
									[ Foreigners: <%=extranjeros%> ]&nbsp;&nbsp;
									[ SDA: <%=adventistas%> ]&nbsp;&nbsp;
									[ NON SDA: <%=noAdventistas%> ]&nbsp;&nbsp;
									[ Credits: <%=numCred%> ]									
									<%
										for (int x=1; x<15; x++){
											out.print("<b>"+x+"="+totalPorSemestre[x]+"</b>,"); 
										} 
									%>								
								</font>
								</th>							
							</tr>						
						</table>
<%					
						} // fin de if (!sFacultad.equals("X"))
							
						for(int x=0; x<15; x++){
   							totalPorSemestre[x] = 0;
   						}	
		    			sFacultad = sFacTem;
						String nombreFacultad = "";
						if (mapFacultad.containsKey(sFacultad)){
							nombreFacultad = mapFacultad.get( sFacultad ).getNombreFacultad();
						}
%>	 	
							<table width="100%" height="23">
							  <tr>
							    <td class="alert alert-success"><h3><%=nombreFacultad%><small class="text-muted fs-5">( <a href="tarjeta?facultad=<%=sFacultad%>&cargas=<%=cargas%>&modalidades=<%=modalidades%>">Pictures (school <%=sFacultad%>)</a> )</small></td>
							  </tr>							  
							</table>
<%
					}//fin del if de facultades diferentes
					if(!sCarrera.equals(insc.getCarreraId())){
	   					sCarrera = insc.getCarreraId();	   					
	   					
	   					if(i != 0 && otraFac==false){
%>	
		   					<table>
							<tr>
								<th colspan="4"><font size="1">Total by Program: <%=inscritosFac %></font></th>
								<th colspan="20">
									<font size="2">
									[ Boarding: <%=internosFac %> ]&nbsp;&nbsp;
									[ Off-campus: <%=externosFac%> ]&nbsp;&nbsp;
									[ Female: <%=mujeresFac%> ]&nbsp;&nbsp;
									[ Male: <%=hombresFac%> ]&nbsp;&nbsp;
									[ Nationals: <%=mexicanos%> ]&nbsp;&nbsp;
									[ Foreigners: <%=extranjeros%> ]&nbsp;&nbsp;
									[ SDA: <%=adventistas%> ]&nbsp;&nbsp;
									[ NON SDA: <%=noAdventistas%> ]&nbsp;&nbsp;
									[ Credits: <%=numCred%> ]
									&nbsp;&nbsp;
									Semester&nbsp;
									<%
										for (int x=1; x<15; x++){
											out.print("<b>"+x+"="+totalPorSemestre[x]+"</b>, "); 
										} 
									%>																		
									</font>
								</th>													
							</table>
	<%
							}
	   						for(int x=0; x<15; x++){
	   							totalPorSemestre[x] = 0;
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
							numCred = 0;
						%><br>
		<table style="width:100%"  class="table table-sm table-bordered">	
			<tr> 
			    <td colspan="30"><h5><b>Program: <%=nombreCarrera%></b></h5></td>
			</tr>
			<tr class="table-info"> 
			    <th align="center"><spring:message code="aca.Numero"/></th>
				<th align="center"><spring:message code="aca.Plan"/></th>
			    <th align="center"><spring:message code="aca.Matricula"/></th>
			    <th align="center"><spring:message code="aca.Nombre"/></th>
			    <th align="center"><spring:message code="aca.Paterno"/></th>
			    <th align="center"><spring:message code="aca.Materno"/></th>
			    <th align="center" title="Enrollment Date">Enr. Date</th>
			    <th align="center">Use Picture</th>
			    <th align="center" title="Residency">Resi.</th>
			    <th align="center" title="Gender">Gender</th>
				<th align="center"><spring:message code="aca.Edad"/></th>
				<th align="center"><spring:message code="aca.Modalidad"/></th>
			    <th align="center">Location</th>
				<th align="center"><spring:message code="aca.Grado"/></th>
				<th align="center">Sem.</th>				
				<th align="center">Gpo.</th>				
				<th align="center"><spring:message code="aca.Tipo"/></th>
				<th align="center">Country</th>
				<th align="center"><spring:message code="aca.Estado"/></th>
				<th align="center">Nationality</th>
				<th align="center">Religion</th>
				<th align="center">GOB ID</th>
				<th align="center">B.D.</th>
				<th align="center">Marital St.</th>
				<th align="center" title="Total balance">T. Balance</th>
				<th align="center" title="Due Balance">D. Balance.</th>
				<th align="center" title="Credits">Crd.</th>
				<th align="center" title="Credits">Study <br>formal</th>
				<th align="center" title="Credits">Study <br> cimum</th>
				<th align="center" title="Credits">Study <br> languages</th>
			</tr>
	<%	        			cont = 1;
	          			}//fin del if de carreras diferentes
						
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
          				
          				// Obtiene el nombre de la religion
          				religion = "";
          				if (mapReligion.containsKey(insc.getReligionId())){
          					CatReligion rel = mapReligion.get(insc.getReligionId());
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
          					CatModalidad mod = mapModalidad.get(insc.getModalidadId());
          					modalidad = mod.getNombreModalidad();
          				}
          				
          				// Obtiene el nombre del pais y el número de extranjeros
          				pais = "";
          				if (mapPais.containsKey(insc.getPaisId())){
          					CatPais p = mapPais.get(insc.getPaisId());
          					pais 	= p.getNombrePais();
          				}
          				
          				if(insc.getPaisId().equals("91")){
          					mexicanos++;
          					nMexicanos++;
          				}
          				else{
          					extranjeros++;
          					nExtranjeros++;
          				}
          				
          				//Obtiene el estado
          				estado = "-";
          				if(mapEstado.containsKey(insc.getPaisId()+insc.getEstadoId())){
    						estado = mapEstado.get(insc.getPaisId()+insc.getEstadoId()).getNombreEstado();
    					}
          				
          				//Obtiene la nacionalidad
          				nacionalidad = "";
          				if (mapPais.containsKey(insc.getNacionalidad())){
          					CatPais n = mapPais.get(insc.getNacionalidad());
          					nacionalidad 	= n.getNacionalidad();
          				}
          				
          				// Obtiene el grado y ciclo del alumno en el plan
          				grado ="0"; ciclo = "0";
          				
          				if (mapGradoyCiclo.containsKey(insc.getCargaId()+insc.getCodigoPersonal()+insc.getBloqueId())){          					
          					String[] dato = mapGradoyCiclo.get(insc.getCargaId()+insc.getCodigoPersonal()+insc.getBloqueId()).split(",");
          					grado 	= dato[0];
          					ciclo	= dato[1]; 
          				}
          				
          				totalPorSemestre[Integer.parseInt(ciclo)] = totalPorSemestre[Integer.parseInt(ciclo)]+1;
          				// Obtiene el nombre del tipo de alumno 
          				tipo = "";
          				if (mapAcademico.containsKey(insc.getCodigoPersonal())){
          					AlumAcademico alumAcademico = mapAcademico.get(insc.getCodigoPersonal());   					
          					if (mapTipo.containsKey(alumAcademico.getTipoAlumno())){
              					CatTipoAlumno tipoAlumno = mapTipo.get(alumAcademico.getTipoAlumno());
              					tipo = tipoAlumno.getNombreTipo(); 
              				}          					
          				}    
          				
          				String creditos = "0";
          				if (mapCreditos.containsKey(insc.getCodigoPersonal()+insc.getCargaId()+insc.getModalidadId())){
          					creditos = mapCreditos.get(insc.getCodigoPersonal()+insc.getCargaId()+insc.getModalidadId());
          					numCred = numCred+(Integer.parseInt(creditos));
          					totalCreditos=totalCreditos+Integer.parseInt(creditos);          					
          				}     
          				
          				String asistencia = "Home/Virtual";
          				CargaAlumno alumno = new CargaAlumno();
    					if (mapaCargasAlumno.containsKey(insc.getCodigoPersonal()+insc.getCargaId()+insc.getBloqueId())){
    						alumno = mapaCargasAlumno.get(insc.getCodigoPersonal()+insc.getCargaId()+insc.getBloqueId());
    						if(alumno.getModo().equals("P")){
    							asistencia = "On Campus";
    						}
    						
    					}
    					
	%>
			<tr> 
			    <td><font size="1">&nbsp;</font><%=cont%></td>
				<td><font size="1"><%=insc.getPlanId()%></font></td>
			    <td align="center"><font size="1"><%=insc.getCodigoPersonal()%></font></td>
			    <td><%=insc.getNombre()%></td>
			    <td><%=insc.getApellidoPaterno()%></td>
			    <td><%=insc.getApellidoMaterno()%></td>
			    <td align="center"><font size="1"><%=insc.getfInscripcion()%></font></td>
			    <td><%=usoImagen.equals("S")?"Si":"No"%></td>
			    <td align="center"><font size="1"><%=insc.getResidenciaId()%></font></td>
			    <td align="center"><font size="1"><%=insc.getSexo()%></font></td>
				<td align="center"><font size="1"><%=frmEntero.format(edad)%></font></td>
				<td align="center"><font size="1"><%=modalidad%></font></td>
				<td align="center"><font size="1"><%=asistencia%></font></td>
				<td align="center"><font size="1"><%=grado%></font></td>
				<td align="center"><font size="1"><%=ciclo%></font></td>			
				<td align="center"><font size="1"><%=alumno.getGrupo()%></font></td>			
				<td align="center"><font size="1"><%=tipo%></font></td>
				<td align="center"><font size="1"><%=pais%></font></td>
				<td align="center"><font size="1"><%=estado%></font></td>
				<td align="center"><font size="1"><%=nacionalidad%></font></td>
				<td align="center"><font size="1"><%=religion%></font></td>
				<td align="center"><font size="1"><%=curp %></font></td>
				<td align="center"><font size="1"><%=insc.getfNacimiento() %></font></td>
				<td align="center"><font size="1"><%=estadoCivil %></font></td>
						<%								
							double saldo 	= Double.parseDouble(insc.getSaldo());														
							double deudaHoy = 0;							
							/*
							if(listaSaldos.containsKey(insc.getCodigoPersonal())){
								SaldosAlumnos saldoAlumno = listaSaldos.get(insc.getCodigoPersonal());								
								deudaHoy	= Double.parseDouble(saldoAlumno.getSVencido());																						 
							}
							*/
							String color = "#AE2113";
							if(saldo>=0) color = "green";
							%>
<%-- 				<td class="right"><font size="1" color=<%=color%>><%=frmDecimal.format(saldo)%></font></td> --%>
				<td class="right"><font size="1" color=<%=color%>><%=saldoTotal%></font></td>
								
						<%	color = "#AE2113";
							if(deudaHoy>=0) color = "green";
							
						%>
<%-- 				<td class="right"><font size="1" color=<%=color%>><%=frmDecimal.format(deudaHoy)%></font></td> --%>
				<td class="right"><font size="1" color=<%=color%>><%=saldoVencido%></font></td>
				<td class="right"><font size="1"><%=creditos%></font></td>
				<td class="right"><font size="1"><%=mapaFormal.containsKey(codigoTemp) ? "Yes" : "No"%></font></td>
				<td class="right"><font size="1"><%=mapaCimum.containsKey(codigoTemp) ? "Yes" : "No"%></font></td>
				<td class="right"><font size="1"><%=mapaIdiomas.containsKey(codigoTemp) ? "Yes" : "No"%></font></td>
			</tr>
	<%   				cont++;	          			
				} //fin del alumno academico
				i++;
			}	// fin de codigo diferente
	 	} // fin del for
	 	lisInsc= null;
	%>
			<tr>
				<th colspan="4"><font size="1">Total by Program: <%=inscritosFac %></font></th>
				<th colspan="24">
					<font size="1">
					[ Boarding: <%=internosFac %> ]&nbsp;&nbsp;
					[ Off-campus: <%=externosFac%> ]&nbsp;&nbsp;
					[ Female: <%=mujeresFac%> ]&nbsp;&nbsp;
					[ Male: <%=hombresFac%> ]&nbsp;&nbsp;
					[ Nationals: <%=mexicanos%> ]&nbsp;&nbsp;
					[ Foreigners: <%=extranjeros%> ]&nbsp;&nbsp;
					[ SDA: <%=adventistas%> ]&nbsp;&nbsp;
					[ NON SDA: <%=noAdventistas%> ]&nbsp;&nbsp;
					[ Credits: <%=numCred%> ]
					</font>
				</th>							
			</tr>		
			<tr>
				<td colspan="23">&nbsp;</td>
			</tr>
			<tr> 
				<th colspan="4"><font size="2">Total Enrolled: <%=nInscritos %></font></th>
				<th colspan="24">
					<font size="2">
						[ Boarding: <%=internosFac %> ]&nbsp;&nbsp;
						[ Off-campus: <%=externosFac%> ]&nbsp;&nbsp;
						[ Nationals: <%=mexicanos%> ]&nbsp;&nbsp;
						[ Foreigners: <%=extranjeros%> ]&nbsp;&nbsp;
						[ SDA: <%=adventistas%> ]&nbsp;&nbsp;
						[ NON SDA: <%=noAdventistas%> ]&nbsp;&nbsp;
						[ Credits: <%=numCred%> ]]
					</font>
				</th>		
			</tr>	
		</table>
	</form>
</div>
<div align="center"><font size="3" face="Times New Roman, Times, serif">
<b><font color="#000099"></font> </b> </font></div>
<%	}else{	%>
<b><font color="#000099">Has no access...!</font></b>
<%	}//fin del if acceso
%>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();
</script>