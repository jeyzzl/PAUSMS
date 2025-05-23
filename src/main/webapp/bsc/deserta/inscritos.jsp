<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.*"%>

<jsp:useBean id="acceso" scope="page" class="aca.acceso.Acceso"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil"/>
<jsp:useBean id="inscritosU" scope="page" class="aca.vista.InscritosUtil"/>
<jsp:useBean id="alumPlanU" scope="page" class="aca.alumno.PlanUtil"/>
<jsp:useBean id="academicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="religionDao" scope="page" class="aca.catalogo.CatReligionDao"/>
<jsp:useBean id="modalidadU" scope="page" class="aca.catalogo.ModalidadUtil"/>
<jsp:useBean id="paisU" scope="page" class="aca.catalogo.PaisUtil"/>
<jsp:useBean id="tipoU" scope="page" class="aca.catalogo.TipoAlumnoUtil"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>

<%	
	DecimalFormat frmDecimal= new DecimalFormat("###,##0.00; -###,##0.00");

	String codigo			= (String) session.getAttribute("codigoPersonal");	
	String sCarga			= request.getParameter("f_carga");	
	
	String sFacultad 		= "X";
	String sFacTem			= "";
	String sCarrera 		= "X";
	String sCarreraTemp		= "X";
	String sBgcolor			= "";
	String modalidad		= "";
	String pais				= "";
	String grado 			= "";
	String ciclo			= "";
	
	int cont 				= 1;
	int nInscritos 			= 0; 
	int nInternos			= 0, nExternos 		= 0, nHombres = 0, nMujeres = 0;
	int inscritosFac 		= 0, internosFac = 0, externosFac = 0, hombresFac = 0, mujeresFac = 0;
	String codigoTemp		= "";
	boolean otraFac 		= false;
	
	ArrayList<aca.vista.Inscritos> lisInsc		= new ArrayList<aca.vista.Inscritos>();
	
	HashMap<String, aca.financiero.SaldosAlumnos> listaSaldos 	= aca.financiero.SaldosAlumnosUtil.getListInscritos(conEnoc, "");	
	HashMap<String, aca.alumno.AlumAcademico> mapAcademico 		= academicoU.getMapAcademico(conEnoc);
	HashMap<String, aca.alumno.AlumPlan> mapAlumPlan 			= aca.alumno.PlanUtil.getMapInscritos(conEnoc,"");
	HashMap<String, aca.catalogo.CatReligion> mapReligion 		= religionDao.getMapAll(conEnoc,"");	
	HashMap<String, aca.catalogo.CatModalidad> mapModalidad 	= aca.catalogo.ModalidadUtil.getMapAll(conEnoc,"");
	HashMap<String, aca.catalogo.CatPais> mapPais 				= aca.catalogo.PaisUtil.getMapAll(conEnoc,"");
	HashMap<String, aca.catalogo.CatTipoAlumno> mapTipo 		= aca.catalogo.TipoAlumnoUtil.getMapAll(conEnoc,"");
	
	aca.alumno.AlumPersonal alumno = new aca.alumno.AlumPersonal();	
	acceso = AccesoU.mapeaRegId(conEnoc, codigo);
	
	ArrayList<String> lisAlumnosLic = aca.alumno.AlumEgresoUtil.getListGradMat(conEnoc, 14, "ORDER BY CODIGO_PERSONAL");
	ArrayList<String> lisAlumnosPrepa = aca.alumno.AlumEgresoUtil.getListGradMat(conEnoc, 15, "ORDER BY CODIGO_PERSONAL");
	
	if(AccesoU.existeReg(conEnoc, codigo)){
		lisInsc	= inscritosU.getListDesercion(conEnoc, "ORDER BY 17, 25, 20, 5,6,4");
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
					if(!sFacultad.equals(sFacTem)){						
						otraFac = true;
						if (!sFacultad.equals("X")){
%>
					<table>
						<tr>
							<th colspan="3"><font size="1">Total Programa: <%=inscritosFac %></font></th>
						    <th colspan="3"><font size="1">Internos: <%=internosFac %></font></th>
						    <th colspan="3"><font size="1">Externos: <%=externosFac %></font></th>
						    <th colspan="5"><font size="1">Hombres: <%=hombresFac %></font></th>
						    <th colspan="5"><font size="1">Mujeres: <%=mujeresFac %></font></th>
						</tr>
					</table>
<%					
						} // fin de if (!sFacultad.equals("X"))
		    			sFacultad = sFacTem;
%>
						<div class="container-fluid">
						<table id="noayuda" style="width:90%; margin: 0 auto; height:23px;">
						  <tr>
						    <td align="center" class="titulo"><b><font  size="3"><%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc,sFacultad)%></font></b></td>
						  </tr>
						  <tr>
						  	<td align="center"><b><a href="tarjeta?facultad=<%=sFacultad%>">Fotografías (facultad <%=sFacultad%>)</a></b></td>
						  </tr>
						</table>
<%
					}//fin del if de facultades diferentes
					if(!sCarrera.equals(insc.getCarreraId())){
	   					sCarrera = insc.getCarreraId();
	   					
	   					if(i != 0 && otraFac==false){%>	
	   					<table>
						  <tr>
							<th colspan="3"><font size="1">Total Programa: <%=inscritosFac %></font></th>
							<th colspan="3"><font size="1">Internos: <%=internosFac %></font></th>
							<th colspan="4"><font size="1">Externos: <%=externosFac %></font></th>
							<th colspan="3"><font size="1">Hombres: <%=hombresFac %></font></th>
							<th colspan="3"><font size="1">Mujeres: <%=mujeresFac %></font></th>
						  </tr>
						</table>
<%
						}
						inscritosFac = 0;
						internosFac = 0;
						externosFac = 0;
						hombresFac = 0;
						mujeresFac = 0;
					%><br>
					<table class="table table-bordered">
					<thead>
					  <tr> 
					    <td class="tabla3" colspan="15"><b><font  size="2" face="Arial Narrow, Arial">Programa:</font><font  size="2"> 
					      <%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, sCarrera)%></font></b></td>
					  </tr>
					</thead>
					<thead class="table-info">
					  <tr> 
					    <th align="center"><spring:message code="aca.Numero"/></th>
						<th align="center"><spring:message code="aca.Plan"/></th>
					    <th align="center"><b><spring:message code="aca.Matricula"/></b></th>
					    <th align="center"><b>Apellidos</b></th>
					    <th align="center"><b><spring:message code="aca.Nombre"/></b></th>
					    <th align="center"><b>Resi.</b></th>
					    <th align="center"><b>Genero</b></th>
						<th align="center"><b><spring:message code="aca.Modalidad"/></b></th>
						<th align="center"><b><spring:message code="aca.Grado"/></b></th>
						<th align="center"><b>Sem.</b></th>
						<th align="center"><b>Nacionalidad</b></th>
						<th align="center" title="Saldo Total"><b>Sal.Tot.</b></th>
						<th align="center" title="Saldo vencido a la fecha"><b>Sal.Venc.</b></th>
						<th align="center"><b>Gradua</b></th>
						<th align="center"><b>Internado</b></th>
						<th align="center"><b>Regresa</b></th>				
					  </tr>
					</thead>
<%	        			cont = 1;
          			}//fin del if de carreras diferentes          		
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
          			
          				// Obtiene el nombre de la modalidad
          				modalidad = "";
          				if (mapModalidad.containsKey(insc.getModalidadId())){
          					aca.catalogo.CatModalidad mod = mapModalidad.get(insc.getModalidadId());
          					modalidad = mod.getNombreModalidad();
          				}
          				
          				// Obtiene el nombre dela modalidad
          				pais = "";
          				if (mapPais.containsKey(insc.getNacionalidad())){
          					aca.catalogo.CatPais p = mapPais.get(insc.getNacionalidad());
          					pais 	= p.getNacionalidad();
          				}
          				
          				// Obtiene el grado y ciclo del alumno en el plan
          				grado ="0"; ciclo = "0";
          				if (mapAlumPlan.containsKey(insc.getCodigoPersonal()+insc.getPlanId())){
          					aca.alumno.AlumPlan alumPlan = mapAlumPlan.get(insc.getCodigoPersonal()+insc.getPlanId());
          					grado 	= alumPlan.getGrado();
          					ciclo	= alumPlan.getCiclo(); 
          				}
%>
						  <tr class="tr2" <%=sBgcolor%>> 
						    <td><font size="1">&nbsp;</font><%=cont%></td>
							<td><font size="1"><%=insc.getPlanId()%></font></td>
						    <td align="center"><font size="1"><%=insc.getCodigoPersonal()%></font></td>
						    <td><font size="1">&nbsp;<%=insc.getApellidoPaterno()%>&nbsp;<%=insc.getApellidoMaterno()%></font>
						    <td><font size="1">&nbsp;<%=insc.getNombre()%></font></td>
						    <td align="center"><font size="1"><%=insc.getResidenciaId()%></font></td>
						    <td align="center"><font size="1"><%=insc.getSexo()%></font></td>
							<td align="center"><font size="1"><%=modalidad%></font></td>
							<td align="center"><font size="1"><%=insc.getCargaId().equals("XXXXXX") ? "0" : grado%></font></td>
							<td align="center"><font size="1"><%=insc.getCargaId().equals("XXXXXX") ? "0" :ciclo%></font></td>
							<td align="center"><font size="1"><%=pais%></font></td>
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
							<td align="right"><font size="1" color=<%=color%>><%=frmDecimal.format(saldo)%></font></td>
							
						<%	color = "#AE2113";
							if(deudaHoy>=0) color = "green";
							
							String grad = "No";
							if(sFacultad.equals("107")){
								grad = lisAlumnosPrepa.contains(insc.getCodigoPersonal()) ? "Si" : "No";
							}
							else{
								grad = lisAlumnosLic.contains(insc.getCodigoPersonal()) ? "Si" : "No";
							}
						%>
							<td align="right"><font size="1" color=<%=color%>><%=frmDecimal.format(deudaHoy)%></font></td>	
							<td align="center"><font size="1"><%=grad %></font></td>
							<td align="center"><font size="1">&nbsp;</font></td>
							<td align="center"><font size="1">&nbsp;</font></td>
						  </tr>
<%     					cont++;
          			// } // Edad entre 19 y 29
				} //fin del alumno academico
			}	// fin de codigo diferente
 		} // fin del for
 		lisInsc= null;
%>
	<tr>
		<th colspan="3"><font size="1">Total Programa: <%=inscritosFac %></font></th>
	    <th colspan="3"><font size="1">Internos: <%=internosFac %></font></th>
	    <th colspan="3"><font size="1">Externos: <%=externosFac %></font></th>
	    <th colspan="5"><font size="1">Hombres: <%=hombresFac %></font></th>
	    <th colspan="5"><font size="1">Mujeres: <%=mujeresFac %></font></th>
	</tr>
	<tr>
		<td colspan="5">&nbsp;</td>
	</tr>
	<tr> 
		<th colspan="3"><font size="1">Inscritos: <%=nInscritos%></font></th>
		<th colspan="3"><font size="1">Internos: <%=nInternos%></font></th>
		<th colspan="3"><font size="1">Externos: <%=nExternos%></font></th>
		<th colspan="5"><font size="1">Hombres: <%=nHombres %></font></th>
		<th colspan="5"><font size="1">Mujeres: <%=nMujeres %></font></th>
	</tr>
</table>
<div align="center"><font size="3" face="Times New Roman, Times, serif"> <br>
<b><font color="#000099"></font> </b> </font></div>
<%	}else{	%>
<br>
</div>
<b><font color="#000099">No tiene Acceso..!</font> </b> 
<%	}//fin del if acceso
%>
<%@ include file= "../../cierra_enoc.jsp" %>