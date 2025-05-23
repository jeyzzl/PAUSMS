<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.plan.spring.MapaNuevoPlan"%>
<%@ page import= "aca.plan.spring.MapaCurso"%>
<%@ page import= "aca.area.spring.Area"%>
<%@ page import= "aca.catalogo.spring.CatTipoCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">
	function Grabar(){
		
		var plan 		= document.formaAccion.planId.value;		
		var cursoId 	= document.formaAccion.cursoId.value;
		var clave  		= document.formaAccion.Clave.value;
		var nombre		= document.formaAccion.Nombre.value;
		var ciclo 		= document.formaAccion.Ciclo.value;		
		var creditos	= document.formaAccion.Creditos.value;
		var ht 			= document.formaAccion.HT.value;
		var hp			= document.formaAccion.HP.value;
		var hi			= document.formaAccion.HI.value;
		var nota 		= document.formaAccion.NotaAprobatoria.value;		
		var linea 		= document.formaAccion.EnLinea.value;
		var obligatorio = document.formaAccion.obligatorio.value;
		var completo	= document.formaAccion.completo.value;
		var hh			= document.formaAccion.HH.value;
		var hfd			= document.formaAccion.HFD.value;
		var hss			= document.formaAccion.HSS.value;
		var has			= document.formaAccion.HAS.value;
		var horario		= document.formaAccion.Horario.value;
		var area		= document.formaAccion.Area.value;
					
		if(plan==""){			
			alert("<spring:message code='mapa.plan.JSPlan'/>");
		}else{			
			if(clave==""){
				alert("<spring:message code='mapa.materia.IngreseClaveMateria'/>");
				document.formaAccion.Clave.focus();
			}else{
				if(cursoId.length!=15){
					alert("<spring:message code='mapa.materia.CursoIdCaracteres'/>");
				}else{
					if(nombre==""){
						alert("<spring:message code='mapa.materia.IngreseNombreMateria'/>");
						document.formaAccion.Nombre.focus();
					}else{
						if(ciclo==""){
							alert("<spring:message code='mapa.materia.IngreseCicloMateria'/>");
							document.formaAccion.Ciclo.focus();
						}else{
							if(creditos==""){
								alert("<spring:message code='mapa.materia.IngreseCreditosMateria'/>");
								document.formaAccion.Creditos.focus();									
							}else{
								if(ht==""){
									alert("<spring:message code='mapa.materia.IngreseHorasTeoricasMateria'/>");
									document.formaAccion.HT.focus();
								}else{
									if(hp==""){
										alert("<spring:message code='mapa.materia.IngreseHorasPracticasMateria'/>");
										document.formaAccion.HP.focus();
									}else{
										if(hi==""){
											alert("<spring:message code='mapa.materia.IngreseHorasIndependientesMateria'/>");
											document.formaAccion.HI.focus();
										}else{
											if(nota==""){
												alert("<spring:message code='mapa.materia.IngreseNotaAprobatoriaMateria'/>");
												document.formaAccion.NotaAprobatoria.focus();
											}else{
												if(hh==""){
													alert("<spring:message code='mapa.materia.IngreseHorasHorarioMateria'/>");
													document.formaAccion.HH.focus();
												}else{
													if(hfd==""){
														alert("<spring:message code='mapa.materia.IngreseHorasFrenteDocenteMateria'/>");
														document.formaAccion.HFD.focus();
													}else{
														if(hss==""){
															alert("<spring:message code='mapa.materia.IngreseHorasSincronicasMateria'/>");
															document.formaAccion.HSS.focus();
														}else{
															if(has==""){
																alert("<spring:message code='mapa.materia.IngreseHorasAsincronicasMateria'/>");
																document.formaAccion.HAS.focus();
															}else{
																document.formaAccion.Accion.value="1";
																document.formaAccion.submit();
															}
														}
													}
												}
											}
										}
									}	
								}
							}
						}
					}
				}
			}
		}
	}
</script>
<%	
	String enLinea				= request.getParameter("EnLinea")==null?"N":request.getParameter("EnLinea");
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String planId 				= (String)request.getAttribute("planId");
	String cursoId 				= (String)request.getAttribute("cursoId");
	String planNombre			= (String)request.getAttribute("planNombre");	
	boolean existe 				= (boolean)request.getAttribute("existe");
	String resultado 			= "";
	
	// Mapa de los planes
	HashMap<String, MapaNuevoPlan> mapaPlan = (HashMap<String, MapaNuevoPlan>)request.getAttribute("mapaPlan");
	
	List<Area> listaArea 					= (List<Area>)request.getAttribute("listaArea");
	List<CatTipoCurso> listaCurso	 		= (List<CatTipoCurso>)request.getAttribute("listaCurso");	
	MapaCurso mapaCurso						= (MapaCurso)request.getAttribute("mapaCurso");
	
	if (existe==false) mapaCurso.setCursoId(planId+"SUB-000");
%>
<div class="container-fluid">
	<h2><th><spring:message code="aca.EditarMateria"/><small class="text-muted fs-6"> (<%=planId%>- <%=planNombre%>)</small></th></h2>
	<form action="grabarAccion" method="post" name="formaAccion" target="_self">
	<input type="hidden" name="Accion">
	<input type="hidden" name="planId" value="<%=planId%>">
	<div class="alert alert-info">
		<a class="btn btn-primary" href="materia?planId=<%=planId%>"><spring:message code="aca.Regresar"/></a>&nbsp; <%=mensaje.equals("-")?"":mensaje%>
	</div>			
	<div class="row">	
		<div class="col-3">			
			<label><b>Subject ID:</b></label>
			<input name="cursoId" type="text" class="form-control" id="cursoId" value="<%=mapaCurso.getCursoId()%>" size="25" maxlength="15" readonly>
			<br><br>
			<label><b>Subject <spring:message code="aca.Clave"/>:</b></label>
			<input name="Clave" type="text" class="form-control" id="Clave" value="<%=mapaCurso.getCursoClave()%>" size="10" maxlength="8">
			<br><br>
			<label><b>Subject <spring:message code="aca.Nombre"/>:</b></label>
			<input name="Nombre" type="text" class="form-control" id="Nombre" value="<%=mapaCurso.getNombreCurso()%>" size="33" maxlength="120">
			<br><br>												
			<label><b><spring:message code="aca.Area"/>:</b></label>
			<select class="form-select"   name="Area" id="Area">	
			<%	for(Area area : listaArea){
					if(mapaCurso.getAreaId().equals(area.getAreaId())){
						out.println("<option value="+area.getAreaId()+" selected>"+area.getAreaNombre()+"</option>");
					}else{
						out.println("<option value="+area.getAreaId()+">"+area.getAreaNombre()+"</option>");
					}
				}%>
			</select>
			<br><br>
			<label><b>Cycle:</b></label>
			<input name="Ciclo" type="text" class="form-control" id="Ciclo" value="<%=mapaCurso.getCiclo()%>" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;" size="4" maxlength="2">
			<br><br>
			<label><b>Credits:</b></label>
			<input name="Creditos" type="text" class="form-control" id="Creditos" value="<%=mapaCurso.getCreditos()%>" onKeypress="if ((window.event.keyCode<48 || window.event.keyCode>57) && window.event.keyCode!=46) window.event.returnValue = false;" size="4" maxlength="5">
			<br><br>				
			<label><b><spring:message code="mapa.materia.NotaAprobatoria"/>:</b></label>
			<input name="NotaAprobatoria" type="text" class="form-control" id="NotaAprobatoria" value="<%=mapaCurso.getNotaAprobatoria() %>" onKeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" size="5" maxlength="3">
			<br><br>
			<label class="text-center"><b><spring:message code="mapa.materia.RequierePracticas"/>:</b></label>
			<select class="form-select" name="Laboratorio" id="Laboratorio" style="width:450px;">
				<option value="N" <%if(mapaCurso.getLaboratorio().equals("N")){%>selected<%}%>><spring:message code="aca.No"/></option>
				<option value="S" <%if(mapaCurso.getLaboratorio().equals("S")){%>selected<%}%>><spring:message code="aca.Si"/></option>
			</select>
			
		</div>
		<div class="col-3">			
			<label><b><spring:message code="mapa.materia.HorasTeoricas"/> (TH):</b></label>
			<input name="HT" type="text" class="form-control" id="HT" value="<%=mapaCurso.getHt()%>" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;"  size="4" maxlength="2">
			<br><br>
			<label><b><spring:message code="mapa.materia.HorasPracticas"/> (PH):</b></label>
			<input name="HP" type="text" class="form-control" id="HP" value="<%=mapaCurso.getHp()%>" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;" size="4" maxlength="2">
			<br><br>
			<label><b><spring:message code="mapa.materia.HorasIndependientes"/> (IH):</b></label>
			<input name="HI" type="text" class="form-control" id="HI" value="<%=mapaCurso.getHi()%>" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;" size="4" maxlength="2">
			<br><br>
			<label><b><spring:message code="mapa.materia.HorasHorario"/>  (RH):</b></label>
			<input name="HH" type="text" class="form-control" id="HH" value="<%=mapaCurso.getHh()%>" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;" size="4" maxlength="2">
			<br><br>				
			<label><b><spring:message code="mapa.materia.HorasFrenteDocente"/>  (IPH):</b></label>
			<input name="HFD" type="text" class="form-control" id="HFD" value="<%=mapaCurso.getHfd()%>" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;"  size="4" maxlength="2">
			<br><br>
			<label><b><spring:message code="mapa.materia.HorasSincornas"/>  (SH):</b></label>
			<input name="HSS" type="text" class="form-control" id="HSS" value="<%=mapaCurso.getHss()%>" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;" size="4" maxlength="2" readonly>
			<br><br>
			<label><b><spring:message code="mapa.materia.HorasAsincronas"/>  (AH):</b></label>
			<input name="HAS" type="text" class="form-control" id="HAS" value="<%=mapaCurso.getHas()%>" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;" size="4" maxlength="2" readonly>
			<br><br>
			<label><b>Order:</b></label>
			<input name="Orden" type="text" class="form-control" id="Orden" value="<%=mapaCurso.getOrden() %>" onKeypress="if (event.keyCode < 48 || event.keyCode > 57) event.returnValue = false;" size="5" maxlength="3">
		</div>
			
		<div class="col-3">
			<label><b>Subject Type:</b></label>
			<select name="TipoCursoId" id="TipoCursoId" class="form-select">
				
			<%	for(CatTipoCurso tcurso : listaCurso){
					if(mapaCurso.getTipoCursoId().equals(tcurso.getTipoCursoId())){
						out.println("<option value="+tcurso.getTipoCursoId()+" selected>"+tcurso.getNombreTipoCurso()+"</option>");
					}
					else{
						out.println("<option value="+tcurso.getTipoCursoId()+">"+tcurso.getNombreTipoCurso()+"</option>");
					}				
				}%>
            </select>
            <br><br>
			<label><b>Online:</b></label>
			<select class="form-select" name="EnLinea" id="EnLinea">
				<option value="N" <%if(mapaCurso.getOnLine().equals("N")){%>selected<%}%>><spring:message code="aca.No"/></option>
				<option value="S" <%if(mapaCurso.getOnLine().equals("S")){%>selected<%}%>><spring:message code="aca.Si"/></option>
			</select>
			<br><br>
			<label><b><spring:message code="aca.Status"/>:</b></label>
			<select class="form-select" name="Estado" id="Estado">
				<option value="1" <%if(mapaCurso.getEstado().equals("1")){%>selected<%}%>><spring:message code='aca.Activo'/></option>
				<option value="0" <%if(mapaCurso.getEstado().equals("0")){%>selected<%}%>><spring:message code='aca.Inactivo'/></option>
			</select>
			<br><br>
			<label><b>Required:</b></label>
			<select class="form-select" name="obligatorio" id="obligatorio">
				<option value="N" <%if(mapaCurso.getObligatorio().equals("N")){%>selected<%}%>><spring:message code="aca.No"/></option>
				<option value="S" <%if(mapaCurso.getObligatorio().equals("S")){%>selected<%}%>><spring:message code="aca.Si"/></option>
			</select>
			<br><br>
			<label><b>Completed:</b></label>
			<select class="form-select" name="completo" id="completo">
				<option value="N" <%if(mapaCurso.getCompleto().equals("N")){%>selected<%}%>><spring:message code="aca.No"/></option>
				<option value="S" <%if(mapaCurso.getCompleto().equals("S")){%>selected<%}%>><spring:message code="aca.Si"/></option>
			</select>
			<br><br>								
			<label><b><spring:message code="mapa.materia.RequiereHorario"/>:</b></label>
			<select class="form-select" name="Horario" id="Horario">
				<option value="N" <%if(mapaCurso.getHorario().equals("N")){%>selected<%}%>><spring:message code="aca.No"/></option>
				<option value="S" <%if(mapaCurso.getHorario().equals("S")){%>selected<%}%>><spring:message code="aca.Si"/></option>
			</select>
			<br><br>								
			<label><b><spring:message code="mapa.materia.RequiereSalon"/>:</b></label>
			<select class="form-select" name="Salon" id="Salon">
				<option value="N" <%if(mapaCurso.getSalon().equals("N")){%>selected<%}%>><spring:message code="aca.No"/></option>
				<option value="S" <%if(mapaCurso.getSalon().equals("S")){%>selected<%}%>><spring:message code="aca.Si"/></option>
			</select>
			
		</div>

	</div>
	<br>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="accion?planId=<%=planId%>"><spring:message code='aca.Nuevo'/></a>&nbsp;
		<a class="btn btn-primary" href="javascript:Grabar();"><spring:message code="aca.Grabar"/></a> &nbsp;
	</div>
	</form>
</div>	