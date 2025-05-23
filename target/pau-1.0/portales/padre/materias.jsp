<%@page import="aca.util.mapaCurricular"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<%@page import="aca.acceso.Acceso"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="academico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean id="AcademicoU" scope="page" class="aca.alumno.AcademicoUtil"/>
<jsp:useBean id="estado" scope="page" class="aca.alumno.AlumEstado"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="CargaBloque" scope="page" class="aca.carga.CargaBloque"/>
<jsp:useBean id="CargaGrupo" scope="page" class="aca.carga.CargaGrupo"/>
<jsp:useBean id="cursoU" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="alumnoCurso" scope="page" class="aca.vista.AlumnoCurso"/>
<jsp:useBean id="AlumnoCursoU" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="carrera" scope="page" class="aca.catalogo.CarreraUtil"/>
<jsp:useBean id="tipoCal" scope="page" class="aca.catalogo.TipoCalUtil"/>
<jsp:useBean id="modalidad" scope="page" class="aca.catalogo.ModalidadUtil"/>
<jsp:useBean id="cancelaEstudio" scope="page" class="aca.alumno.CancelaEstudio"/>
<jsp:useBean id="cancelaEstudioU" scope="page" class="aca.alumno.CancelaEstudioUtil"/>
<jsp:useBean id="evaluacionUtil" scope="page" class="aca.kardex.EvaluacionUtil"/>
<jsp:useBean id="materiasEleg" scope="page" class="aca.plan.MapaCursoElegibleUtil"/>
<jsp:useBean id="edo" scope="page" class="aca.edo.Edo"/>
<jsp:useBean id="EdoU" scope="page" class="aca.edo.EdoUtil"/>
<jsp:useBean id="asistencia" scope="page" class="aca.carga.CargaGrupoAsistencia"/>
<jsp:useBean id="Programacion" scope="page" class="aca.carga.CargaGrupoProgramacionUtil"/>
<jsp:useBean id="programaL" scope="page" class="aca.carga.CargaGrupoProgramacion"/>
<jsp:useBean id="acu" scope="page" class="aca.vista.AlumnoCursoUtil"/>
<jsp:useBean id="MapaCursoU" scope="page" class="aca.plan.CursoUtil" />
<%
	// Poner los objetos de session para 	
	session.setAttribute("IngresoAlumno", "false");
	session.setAttribute("SaldoVencido", 0.0);		
%>

<%-- <jsp:include page="../menuPadre.jsp" /> --%>
<%@ include file= "../menuPadre.jsp" %>
<%
	ArrayList<String> materiasElegibles = materiasEleg.getMaterias(conEnoc, "ORDER BY 1");

	java.text.DecimalFormat getformato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String matricula 	= (String) session.getAttribute("codigoAlumno");
	String accesoPortal = (String)session.getAttribute("accesoPortal")==null?"OK":(String)session.getAttribute("accesoPortal");
	
	String seguroAcceso	= request.getParameter("seguro")==null?"Restringido":request.getParameter("seguro");
	String cargaId 		= request.getParameter("CargaId");
	String nombreMaestro= "";
	String sesionId		= session.getId();

	plan.mapeaRegId(conEnoc, matricula);
	String escala 		= plan.getEscala();
	
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String cursoCargaId			= request.getParameter("CursoCargaId");	
	String materia	 			= request.getParameter("Materia");
	
	if (cargaId==null) cargaId = aca.carga.CargaUtil.getMejorCarga(conEnoc,matricula);	
	
	// Variables para calcular el promedio
	int nota 			= 0;
	float creditos		= 0;
	int sumaCredXNota 	= 0; 
	int sumaCred		= 0;
	int row				= 0;
	int sumaNotas		= 0;
	double ponderado 	= 0;
	double promedio 	= 0;
	boolean promedia 	= false;
	boolean acceso 		= false;
	
	
	alumno = AlumUtil.mapeaRegId(conEnoc,matricula);
	academico = AcademicoU.mapeaRegId(conEnoc,matricula);
	CargaBloque.CargaActiva(conEnoc,(String)session.getAttribute("cargaId"));
	estado.mapeaRegId(conEnoc,matricula,CargaBloque.getCargaId(),CargaBloque.getBloqueId());
	String colorPortal = (String)session.getAttribute("colorPortal");
	if(colorPortal==null)colorPortal="";
	
	if ( accesoPortal.equals("OK") || seguroAcceso.equals("mercy")){
		acceso = true;
	} 

	String fInicio = aca.carga.CargaUtil.getFInicio(conEnoc, cargaId);
	
	aca.util.Fecha fecha = new aca.util.Fecha();	
	String fechaLimite = fecha.getDiaSiguiente(!fInicio.equals("")?fInicio:"01/01/2000", 19);//26 dias despues de la fecha de inicio de la carga
	
	boolean mostrar = false;
	
	int diasRestantes = aca.util.Fecha.getDiasEntreFechas(aca.util.Fecha.getHoy(), fechaLimite);
	
	if(diasRestantes>0)mostrar=true;
	
	String mod	= aca.alumno.AcademicoUtil.getModalidadId(conEnoc, matricula);
	
	ArrayList<aca.carga.CargaGrupoProgramacion> lisProgra 		= Programacion.getListMateria(conEnoc, cursoCargaId, " ORDER BY ORDEN, TO_CHAR(TO_DATE(FECHA,'DD/MM/YYYY'),'YYYY/MM/DD')");
	
	// Consulta la asistencia de los alumnos en cada clase
	HashMap<String, String> mapAsistencia 		= aca.carga.CargaGrupoAsistenciaUtil.mapAsistenciaClase(conEnoc, cursoCargaId);
	HashMap<String, String> mapAsistenciaTotal 	= aca.carga.CargaGrupoAsistenciaUtil.mapAsistenciaTotal(conEnoc, cursoCargaId);
	ArrayList<aca.vista.AlumnoCurso> lisAlumnos = acu.getListCurso(conEnoc, cursoCargaId, "ORDER BY ENOC.ALUM_APELLIDO(CODIGO_PERSONAL)");	
	
%>
<html>
	<head>
<link href="css/portalAlumno.css" rel="STYLESHEET" type="text/css">
		
		<style>
			.linea{ 
			  text-decoration:line-through;
			}
		</style>
		
	</head>
	
	<body onload='muestraPagina();'>
		<script>
			parent.tabs.document.body.style.backgroundColor=parent.contenidoP.document.bgColor;
		</script>

		<form name="frmmaterias" action="materias">
		<br>
			<table cellpadding="10" style="width:90%">
	  			<tr>
					<td>
						<table style="width:100%; margin:0 auto">
	    					<tr> 
	    						<td align="center">
    							<%	if(!fInicio.equals("")){%>
    									<a href="horario.jsp?CargaId=<%=cargaId%>"><img style="height:50px;margin-bottom:8px;" src="img/horario.png" title="Horario" class="button"/></a>
								<%	}%>
					    		</td>
							  	<td align="center"><br><spring:message code="aca.Carga"/>:
							    	<select name="CargaId" onChange="javascritp:frmmaterias.submit()">
									<%
										aca.carga.CargaUtil cargaU = new aca.carga.CargaUtil();
										ArrayList<aca.carga.Carga> lisCarga = cargaU.getListAlumno(conEnoc, matricula);
										String sCargaId="";
										int i=0;
										for( i=0;i<lisCarga.size();i++){
											aca.carga.Carga carga = (aca.carga.Carga) lisCarga.get(i);
											if (i==0) sCargaId = carga.getCargaId();
											if (carga.getCargaId().equals(cargaId)){
												out.print(" <option value='"+carga.getCargaId()+"' Selected>"+ carga.getNombreCarga()+"</option>");
											}else{
												out.print(" <option value='"+carga.getCargaId()+"'>"+ carga.getNombreCarga()+"</option>");
											}				
										}
									
										if (lisCarga.size()==1){
											cargaId = sCargaId;
										}
										lisCarga 	= null;
										cargaU		= null;
										ArrayList<aca.vista.AlumnoCurso> lisCursos = cursoU.getListAlumnoCarga(conEnoc,matricula, cargaId, "ORDER BY CICLO, NOMBRE_CURSO");
										
										if(lisCursos.size()>0) plan.mapeaRegId(conEnoc, matricula, ((aca.vista.AlumnoCurso)lisCursos.get(0)).getPlanId());
								 	%>
									</select>
									<br>
		  						</td>
		  						<td>&nbsp;</td>
							</tr>
							<tr>
					      		<td colspan="3">
									<table class="table table-nobordered table-condensed table-nohover" width="80%"   align="center">
							  			<tr valign="top">
											<th style="text-align:center;">
												<spring:message code="datosAlumno.portal.Titulo7"/>: [<%=matricula%>] 
												[<%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%>]  -- 
												[<%=plan.getPlanId()%>] [<%=aca.plan.PlanUtil.getNombrePlan(conEnoc, plan.getPlanId()) %>]
											</th>
							  			</tr>
						  			</table>
						  		</td>
							</tr>
		
		
							<tr valign="top">
								<%	boolean estudiosCancelados = false;
									if(lisCursos.size()>0) cancelaEstudioU.existeRegId(conEnoc, matricula, ((aca.vista.AlumnoCurso)lisCursos.get(0)).getPlanId());
									if(!estudiosCancelados || Boolean.parseBoolean(session.getAttribute("admin")+"")){
										if(estudiosCancelados){
											cancelaEstudio.mapeaRegId(conEnoc, matricula, ((aca.vista.AlumnoCurso)lisCursos.get(0)).getPlanId());
								%>
								  			<tr>
												<td colspan="3">
													<font size="3" color="red"><b><spring:message code="datosAlumno.portal.Nota13"/>:</b><br><%=cancelaEstudio.getComentario() %></font>
												</td>
								  			</tr>
								<%		}%>
										<tr valign="top">
											<td colspan="3" width="60%">
							  					<table style="margin: 0 auto;  width:80%"   class="table table-fullcondensed">
											        <tr>
									              		<th colspan="12" style="font-size:10pt;"><spring:message code="aca.Cursos"/></th>
											        </tr>
												<%
													String notaMateria 	= "";
													aca.kardex.EvaluacionUtil krdxEvaluacionUtil = new aca.kardex.EvaluacionUtil();			
													for(i=0; i<lisCursos.size(); i++){
														alumnoCurso = (aca.vista.AlumnoCurso) lisCursos.get(i);			
														nombreMaestro = aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, alumnoCurso.getMaestro(), "NOMBRE");			
														notaMateria = alumnoCurso.getNota();
														if (notaMateria.equals("0")){
															//notaMateria = krdxEvaluacionUtil.getAlumnoEficiencia(conEnoc, alumnoCurso.getCursoCargaId(), alumnoCurso.getCodigoPersonal());
															notaMateria = evaluacionUtil.getAlumnoEficiencia(conEnoc, alumnoCurso.getCursoCargaId(), alumnoCurso.getCodigoPersonal());
														}
														
														// Calculo del promedio
														promedia = false; nota = 0; creditos = 0;
														if (AlumnoCursoU.obtenTipoCurso(conEnoc,alumnoCurso.getCursoId())==1||AlumnoCursoU.obtenTipoCurso(conEnoc,alumnoCurso.getCursoId())==2
																||AlumnoCursoU.obtenTipoCurso(conEnoc,alumnoCurso.getCursoId())==7){				
															if (alumnoCurso.getTipoCalId().equals("1")||alumnoCurso.getTipoCalId().equals("2")||alumnoCurso.getTipoCalId().equals("4")){				
																row++; promedia = true;
																//Obtiene la nota a considerar en el cálculo del promedio 
																if ( (alumnoCurso.getNotaExtra()==null||alumnoCurso.getNotaExtra().equals("0") )){
																	if (alumnoCurso.getNota() != null) 
																		nota = Integer.parseInt(alumnoCurso.getNota()); 
																}else{
																	nota =  Integer.parseInt(alumnoCurso.getNotaExtra());
																}
																creditos = Float.parseFloat(alumnoCurso.getCreditos());
																
																// Promedio Ponderado
																sumaCredXNota 	+=  creditos * nota;
																sumaCred		+= creditos;
																
																// Media Aritmética
																sumaNotas 		+= nota;
															}
														}
														
														String colorBien 	= "#37781B";
														String colorMal 	= "#840000";
														String color23 		= "#437EC5";

														String tipoCalNombre 	= tipoCal.getNombre(conEnoc,alumnoCurso.getTipoCalId(),"");
														String atributosTDNota 	= "";
														String atributosTDIns 	= "";
																												
														aca.plan.MapaCurso MapaCurso = new aca.plan.MapaCurso();
														MapaCurso = MapaCursoU.mapeaRegId(conEnoc, alumnoCurso.getCursoId());
														Float notaAprobatoria = 0.0f;
														String notaT 	= "";
														String extraT 	= "";
														if(AlumnoCursoU.obtenTipoCurso(conEnoc,alumnoCurso.getCursoId())==1||AlumnoCursoU.obtenTipoCurso(conEnoc,alumnoCurso.getCursoId())==2
																||AlumnoCursoU.obtenTipoCurso(conEnoc,alumnoCurso.getCursoId())==7){
															notaT = Float.parseFloat(notaMateria)/10+"";
															notaAprobatoria = Float.parseFloat(MapaCurso.getNotaAprobatoria())/10;
															
															if(Float.parseFloat(notaT)<notaAprobatoria){
																atributosTDNota = "style='color:"+colorMal+";font-weight:bold;font-size: 130%;'";
															}
														}
														else{
															notaT = tipoCalNombre+"";
														}
														if(!alumnoCurso.getNotaExtra().equals("0") && alumnoCurso.getNotaExtra()!=null){
															if(escala.equals("10")){
																extraT = "Extra:["+Float.parseFloat(alumnoCurso.getNotaExtra())/10+" ]";
															}
															else{
																extraT = "Extra:["+alumnoCurso.getNotaExtra()+" ]";
															}
														}
														if(!extraT.equals("")){
															atributosTDNota = "style='color:"+color23+";font-weight:bold;font-size: 130%;'";
														}

														if(tipoCalNombre.equals("AC")){
															atributosTDIns = atributosTDNota = "style='color:"+colorBien+";font-weight:bold;font-size: 130%;'";
														}
														else if(tipoCalNombre.equals("NA")){
															atributosTDIns = atributosTDNota = "style='color:"+colorMal+";font-weight:bold;font-size: 130%;'";
														}
														else if(tipoCalNombre.equals("Inscrito")){
															atributosTDIns = "style='color:"+colorBien+";font-weight:bold;font-size: 130%;'";
														}
														else if(tipoCalNombre.equals("BA")){
															atributosTDIns = atributosTDNota = "class='linea'";
														}
													%>
									
														<tr onMouseOut=this.style.backgroundColor='' style='cursor:pointer;' onClick="document.location.href='detallecal?cargaId=<%=cargaId%>&materia=<%=alumnoCurso.getNombreCurso()%>&profesor=<%= nombreMaestro %>&matricula=<%=matricula%>&cursoCargaId=<%=alumnoCurso.getCursoCargaId()%>'">
						  									<td>&nbsp;</td>
						  									<td width="50%">
						  									
						  									<%
						  									String optativa = alumnoCurso.getOptativa();
						  									if((diasRestantes<=0)&&(!optativa.equals("-"))){ %>
						  										<b><%=alumnoCurso.getNombreCurso()%> (<%=optativa%>)</b>
						  									<%}else{ %>
						  										<b><%=alumnoCurso.getNombreCurso()%> </b>
						  									<%} %>
						  										<!-- comentario -->
						  										<%
						  											CargaGrupo.mapeaRegId(conEnoc, alumnoCurso.getCursoCargaId());
						  									
						  										%>
						  										<%
						  										if(CargaGrupo.getComentario()!=null && !CargaGrupo.getComentario().trim().equals("")){ 
						  										%>
						  											(<%=CargaGrupo.getComentario() %>)
						  											<!-- Elegir materias opcionales -->
						  										<%} %>
						  										<%
						  										if(mostrar && materiasElegibles.contains(alumnoCurso.getCursoId()) && alumnoCurso.getTipoCalId().equals("I")){ %>
						  											&nbsp;
						  											<a class="btn btn-primary" href="elegirMateria.jsp?cursoCargaId=<%=alumnoCurso.getCursoCargaId()%>&cursoId=<%=alumnoCurso.getCursoId()%>&materia=<%=alumnoCurso.getNombreCurso()%>&profesor=<%=nombreMaestro %>&cargaId=<%=cargaId%>">
						  												<i class="icon-random"></i> Cambiar de grupo <font color="blue">(Quedan <%=diasRestantes%> dias)</font>
						  											</a>
						  										<%} %>
						  										<!-- END Elegir materias opcionales -->						  							
																<li style="text-indent:20;">
																	<%=nombreMaestro%>
																	<%										
																		String evaldocente = aca.edo.EdoUtil.getEdoActual(conEnoc,"E", academico.getModalidadId());																		
																		if (evaldocente!="0"){
																			edo = EdoU.mapeaRegId(conEnoc, evaldocente);
																			
																			if( !aca.edo.EdoAlumnoRespUtil.yaContesto(conEnoc, edo.getEdoId(), alumnoCurso.getCodigoPersonal(), alumnoCurso.getCursoCargaId()) && 
																					((String)session.getAttribute("codigoPersonal")).equals((String)session.getAttribute("codigoAlumno")) &&
																					(edo.getModalidad().indexOf("-"+academico.getModalidadId()+"-") != -1 || edo.getModalidad().indexOf("-0-") != -1) &&
																					(edo.getCargas().indexOf("-"+ alumnoCurso.getCargaId() +"-") != -1 || edo.getCargas().indexOf("-0-") != -1)
																					){												
																	%>
																				<a href="evaluaMaestro.jsp?edo=<%=edo.getEdoId() %>&cursoCarga=<%=alumnoCurso.getCursoCargaId() %>">Evaluar al maestro</a>
																	<%
																			}											
																		}																			
																	%>
																</li>
																<li style="text-indent:20;">
																	<%=alumnoCurso.getCiclo()%>o. Sem de <%=carrera.getNombreCarrera(conEnoc,alumnoCurso.getCarreraId(),"0")%>&nbsp;
																	<b>Grupo: [<%= alumnoCurso.getGrupo()%>]</b>
																	<b>Asistencias: [P=  T=  A=]</b>										
					
																</li>
						  									</td>
														  	<td width="10%">
														  		Cr.[ <%=alumnoCurso.getCreditos()%> ]&nbsp;<% if (promedia==true) out.println("*");%>
													  		</td>
														  	<td width="20%" <%=atributosTDNota%>>
						  					 					Nota: [ <%=notaT%> ] <%=extraT%>
						  									</td>								  
														  	<td width="15%" align="center" <%=atributosTDIns%>>
														  		[<%=tipoCalNombre%>]
													  		</td>
														  	<td width="5%" align="center">
						  									<%	if (acceso){%>
						    										<a href="http://e42.um.edu.mx/autologin.aspx?session=<%=sesionId%>&curso_carga_id=<%=alumnoCurso.getCursoCargaId()%>" target="_blank">E42&nbsp;&nbsp;</a>
							 			 					<%	} %>  
						  									</td>
														</tr>
													<%
													}
													ponderado 	= Double.valueOf(String.valueOf(sumaCredXNota)).doubleValue()/ Double.valueOf(String.valueOf(sumaCred)).doubleValue(); 
													promedio 	= Double.valueOf(String.valueOf(sumaNotas)).doubleValue()/ Double.valueOf(String.valueOf(row)).doubleValue();		
													//System.out.println("Promedio:"+getformato.format(promedio)+" - Ponderado:"+getformato.format(ponderado));
												%>
													<tr><td colspan="12">&nbsp;</td></tr>
													<tr>
									              		<th colspan="12" style="text-align:center;" style="font-size:10pt;">		                  
									                  		<spring:message code="aca.Total"/> <spring:message code="aca.Creditos"/>: <b><%= aca.kardex.ActualUtil.numAlumCredMat(conEnoc,matricula,cargaId,"'I','1','2','3','4','5','6'") %></b> &nbsp; - &nbsp;
									                  		<spring:message code="aca.Media"/>: 
														<%	if(escala.equals("10")){%>
									                  			<b><%= getformato.format((promedio)/10)%></b>&nbsp; - &nbsp;
							                  			<%	}
									                  		else{%>
									                  			<b><%= getformato.format(promedio)%></b>&nbsp; - &nbsp;
								                  		<%  }%>
									                  		<spring:message code="datosAlumno.portal.MateriaPromedio"/>: 
														<%	if(escala.equals("10")){%>
													  			<b><%=getformato.format(ponderado/10)%></b>
							                  			<%	}
									                  		else{%>
									                  			<b><%=getformato.format(ponderado)%></b>
								                  		<%  }%>
									              		</th>
										        	</tr>
			           		  					</table>
    							</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
<%
	}else{
		cancelaEstudio.mapeaRegId(conEnoc, matricula, ((aca.vista.AlumnoCurso)lisCursos.get(0)).getPlanId());
%>
<table style="width:100%; margin:0 auto">
	<tr>
		<td align="center">
			<br><br><br>
			<font size="3" color="red">
				<b>Tus estudios han sido cancelados por el siguiente motivo:</b><br>
				<%=cancelaEstudio.getComentario() %>
			</font>
		</td>
	</tr>
</table>
<%
	}
%>


<script>
	$('.nav-tabs').find('.materias').addClass('active');
</script>
<%@ include file= "../../cierra_enoc.jsp" %>