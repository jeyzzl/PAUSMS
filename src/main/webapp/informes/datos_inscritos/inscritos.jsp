<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil" />
<jsp:useBean id="AlumPersonal" scope="page" class="aca.alumno.AlumPersonal" />
<jsp:useBean id="FesCcobroUtil" scope="page" class="aca.financiero.FesCcobroUtil" />
<jsp:useBean id="CatModalidad" scope="page" class="aca.catalogo.CatModalidad" />
<jsp:useBean id="catModalidadU" scope="page" class="aca.catalogo.ModalidadUtil" />
<jsp:useBean id="religionDao" scope="page" class="aca.catalogo.CatReligionDao"/>
<jsp:useBean id="carga" scope="page" class="aca.carga.Carga" />
<jsp:useBean id="cargaU" scope="page" class="aca.carga.CargaUtil" />

<%@page import="java.util.HashMap"%>

<script>
	function Mostrar(){					
		document.forma.submit();
	}
</script>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<%
	String fInscripcion			= request.getParameter("fecha")==null?aca.util.Fecha.getHoy():request.getParameter("fecha");
	String primerIng			= request.getParameter("primerIng")==null?"um":request.getParameter("primerIng");
	
	int nInternos				= 0;
	int nExternos				= 0;
	int nHombres				= 0;
	int nMujeres				= 0;
	
	String cargas				= "";
	String modalidades			= "";
	String modalidadesId		= "";
	String religion				= "";
	String condicion 			= ""; 
	
	
	ArrayList<aca.catalogo.CatModalidad> lisModalidad 		= catModalidadU.getListAll(conEnoc, "ORDER BY MODALIDAD_ID");
	ArrayList<aca.carga.Carga> lisCarga 					= cargaU.getListPorFecha(conEnoc, fInscripcion, "ORDER BY CARGA_ID");
	HashMap<String, aca.catalogo.CatReligion> mapReligion 	= religionDao.getMapAll(conEnoc,"");
	
	for(int i = 0; i < lisCarga.size(); i++){
		carga = (aca.carga.Carga) lisCarga.get(i);
		if(request.getParameter(carga.getCargaId()) != null){
					
				if(cargas.equals(""))
					cargas = "'"+carga.getCargaId()+"'";
				else
					cargas += ",'"+carga.getCargaId()+"'";	
				
		}
		
	}
	
	for(int i = 0; i < lisModalidad.size(); i++){
		CatModalidad = (aca.catalogo.CatModalidad) lisModalidad.get(i);
		if(request.getParameter("mod-"+CatModalidad.getModalidadId()) != null){
			
				if(modalidades.equals("")){
					modalidades = "'"+CatModalidad.getNombreModalidad()+"'";
					modalidadesId = "'"+CatModalidad.getModalidadId()+"'";
				}
				else{
					modalidades += ",'"+CatModalidad.getNombreModalidad()+"'";
					modalidadesId += ",'"+CatModalidad.getModalidadId()+"'";
				}
		}
	}
	
	if(modalidades.equals(""))modalidades="' '";
	if(cargas.equals(""))cargas="' '";
	if(modalidadesId.equals(""))modalidadesId="0";
	
	
	// Determina la condición de nuevo ingreso a la UM, Plan o ambos.
	if (primerIng.toUpperCase().equals("UM")){
		condicion = "FECHA = TO_DATE(ENOC.ALUM_INGRESO_UM(MATRICULA),'DD/MM/YYYY')";
	}else if (primerIng.toUpperCase().equals("PLAN") ){
		condicion = "FECHA = ENOC.ALUM_INGRESO_PLAN(MATRICULA, PLAN_ID) AND FECHA != ENOC.ALUM_INGRESO_UM(MATRICULA)";
	}else if (primerIng.toUpperCase().equals("PLANUM") ){
		condicion = "FECHA = ENOC.ALUM_INGRESO_PLAN(MATRICULA, PLAN_ID) ";
	}	
	
	ArrayList<aca.financiero.FesCcobro> listaFesCcobro = FesCcobroUtil.getListAll(conEnoc, "WHERE CARGA_ID IN ("+cargas+") AND "+condicion+" AND INSCRITO='S' AND MODALIDAD_ID IN ("+modalidadesId+") AND MATRICULA IN (SELECT CODIGO_PERSONAL FROM ENOC.ALUM_ESTADO WHERE CODIGO_PERSONAL=MATRICULA AND CARGA_ID = (FES_CCOBRO.CARGA_ID) AND ESTADO IN('I','3')) ORDER BY FACULTAD_ID, CARRERA_ID");
	HashMap<String, aca.alumno.AlumPersonal> mapaAlumPersonal = AlumUtil.getMapAllCargaAlumEstado(conEnoc, cargas.substring(1, cargas.length()-1));
%>
<div class="container-fluid">
	<h3>Alumnos Inscritos de Primer Ingreso</h3>
	<form name="forma" action="inscritos" method="post">	
	<div class="alert alert-info d-flex align-items-center">
		Fecha: <input type="text" class="form-control" id="fecha" data-date-format="dd/mm/yyyy" name="fecha" value="<%=fInscripcion %>" maxlength="10" size="12" style="width:120px;"/>
		(DD/MM/AAAA)&nbsp;
		Primer ingreso a: 
		<select name="primerIng" class="form-select" onChange="document.forma.submit();" style="width:140px;">
			<option value="um" <%=primerIng.equals("um")?"Selected":""%>>UM</option>
			<option value="plan" <%=primerIng.equals("plan")?"Selected":""%>><spring:message code="aca.Plan"/></option>
			<option value="planUM" <%=primerIng.equals("planUM")?"Selected":""%>>Plan y UM</option>
		</select>
	</div>
	<div class="row">
		<div class="col">
			<table class="table table-bordered">
				<thead class="table-info">
					<tr>
						<spring:message code="aca.Cargas"/>
					</tr>
				</thead>
					<tr><td><b><spring:message code="aca.Seleccionar"/>:</b></td>
					<tr>
					  <td>
					  	<a onclick="jQuery('.checkboxCarga').prop('checked', true)" class="btn btn-info"><spring:message code='aca.Todos'/></a> 
					  	<a class="btn btn-info" onclick="jQuery('.checkboxCarga').prop('checked', false)"><spring:message code='aca.Ninguno'/></a>
					  </td>
					</tr>
				<%
					String checkCarga = "";
					for(int i = 0; i < lisCarga.size(); i++){
						carga = (aca.carga.Carga) lisCarga.get(i);
						if ( cargas.indexOf("'"+carga.getCargaId()+"'") != -1 ) checkCarga = "checked"; else checkCarga = "";
						
				%>
						<tr>
							<td>
								<input class="checkboxCarga" type="checkbox" id="<%=carga.getCargaId() %>" name="<%=carga.getCargaId() %>" value="<%=carga.getCargaId() %>" <%=checkCarga%>/>
								<%=carga.getCargaId() %> | <b><%=carga.getNombreCarga() %></b> [<%=carga.getFInicio() %> - <%=carga.getFFinal() %>																					
							</td>
						</tr>
				<%	} %>
			</table>
		</div>
		<div class="col">
			<table class="table table-bordered">
				<thead class="table-info">
					<tr>
						<spring:message code="aca.Modalidades"/>
					</tr>
				</thead>
					<tr><td><b><spring:message code="aca.Seleccionar"/>:</b></td></tr>
					<tr>
					  <td>
					  	<a onclick="jQuery('.checkboxMod').prop('checked',true)" class="btn btn-info"><spring:message code='aca.Todos'/></a> 
					  	<a class="btn btn-info" onclick="jQuery('.checkboxMod').prop('checked', false)"><spring:message code='aca.Ninguno'/></a>
					  </td>
					</tr>
				<%
					String checkModalidad = "";
					for(int i = 0; i < lisModalidad.size(); i++){
						CatModalidad = (aca.catalogo.CatModalidad) lisModalidad.get(i);
						if ( modalidades.indexOf("'"+CatModalidad.getNombreModalidad()+"'") != -1 ) checkModalidad = "checked"; else checkModalidad = "";
						
				%>
						<tr>
							<td>
								<input class="checkboxMod" type="checkbox" id="mod-<%=CatModalidad.getModalidadId() %>" name="mod-<%=CatModalidad.getModalidadId() %>" value="<%=CatModalidad.getModalidadId() %>" <%=checkModalidad%>/>
								<%=CatModalidad.getNombreModalidad() %>
								<%%>
							</td>
						</tr>
				<%	} %>
			</table>
		</div><br>
		<input class="btn btn-primary" type="submit" value="Mostrar" onclick="javascript:Mostrar();"/><br>
	</div>
	</form>
  	<table class="table table-bordered">
<%		String facultadId 	= "";
		String carreraId 	= "";
		int cont = 0;
		int inscritosFac 	= 0;
		int internosFac 	= 0;
		int externosFac 	= 0;
		int hombresFac 		= 0;
		int mujeresFac 		= 0;
		
		int inscritosCar 	= 0;
		int internosCar 	= 0;
		int externosCar 	= 0;
		int hombresCar 		= 0;
		int mujeresCar 		= 0;
		
		boolean facu = false;
		for(int i=0; i<listaFesCcobro.size(); i++){
			aca.financiero.FesCcobro fesCcobro = (aca.financiero.FesCcobro)listaFesCcobro.get(i);
			String genero = mapaAlumPersonal.get(fesCcobro.getMatricula()).getSexo();
			facu=false;
			
			if(!facultadId.equals(fesCcobro.getFacultadId())){
				facultadId = fesCcobro.getFacultadId();
				
				if(i!=0){
					facu = true;
				%>
			  		<tr>
						<td colspan="10" height="1%">
							<table style="width:100%" class="table table-fontsmall table-bordered table-condensed">
	  							<tr>
							    	<th colspan="2">Inscritos: <%=inscritosCar%></th>
							    	<th colspan="1">Internos: <%=internosCar%></th>
							    	<th colspan="3">Externos: <%=externosCar%></th>
							    	<th colspan="1">Hombres: <%=hombresCar%></th>
							    	<th colspan="1">Mujeres: <%=mujeresCar%></th>
	  							</tr> 
	 							</table>
						</td>
		  			</tr>
					<tr> 
						<td colspan="10" height="1%">
							<table style="width:100%" class="table table-condensed">
	  							<tr>
							    	<th colspan="2">Inscritos: <%=inscritosFac%></th>
							    	<th colspan="1">Internos: <%=internosFac%></th>
							    	<th colspan="3">Externos: <%=externosFac%></th>
							    	<th colspan="1">Hombres: <%=hombresFac%></th>
							    	<th colspan="1">Mujeres: <%=mujeresFac%></th>
	  							</tr> 
  							</table>
						</td>
			  		</tr>
		</table>
	  		<%	
			  		inscritosCar=0;
					internosCar=0;
					externosCar=0;
					hombresCar=0;
					mujeresCar=0;
				}%>
		<div style="width:100%;text-align:center;margin:auto;"><h3><%=fesCcobro.getFacultad()%></h3></div>
		<table style="width:100%" class="table table-fullcondensed">
	  		<tr> 
		    	<th width="5%"><spring:message code="aca.Matricula"/></th>
		    	<th width="30%"><spring:message code="aca.Nombre"/></th>
		    	<th width="1%"><spring:message code="aca.Genero"/></th>
		    	<th width="1%"><spring:message code="aca.Residencia"/></th>
		    	<th width="10%"><spring:message code="aca.Tipo"/></th>
		    	<th width="10%">Religión</th>
		    	<th width="1%"><spring:message code="aca.Carga"/></th>
		    	<th width="1%"><spring:message code="aca.Modalidad"/></th>
		    	<th width="1%"><spring:message code="aca.Plan"/></th>
		    	<th width="1%"><spring:message code="aca.Ciclo"/></th>
		  	</tr>
		<%		inscritosFac	= 0;
				internosFac 	= 0;
				externosFac 	= 0;
				hombresFac 		= 0;
				mujeresFac 		= 0;
			}
			if(!carreraId.equals(fesCcobro.getCarreraId())){
				cont=1;
				carreraId = fesCcobro.getCarreraId();
				if(!facu && i!=0){
				%>
				<tr> 
					<td colspan="10" height="1%">
						<table style="width:100%" class="table table-fontsmall table-bordered table-condensed">
  							<tr>
						    	<th colspan="2">Inscritos: <%=inscritosCar%></th>
						    	<th colspan="1">Internos: <%=internosCar%></th>
						    	<th colspan="3">Externos: <%=externosCar%></th>
						    	<th colspan="1">Hombres: <%=hombresCar%></th>
						    	<th colspan="1">Mujeres: <%=mujeresCar%></th>
  							</tr> 
 							</table>
					</td>
		  		</tr>
				<%
					inscritosCar=0;
					internosCar=0;
					externosCar=0;
					hombresCar=0;
					mujeresCar=0;
				
				}
				%>
				<tr><td colspan="9"><h4><i><%=fesCcobro.getCarrera()%></i></h4></td></tr>
		<%	}
			
			// Obtiene el nombre de la religion
			AlumPersonal = AlumUtil.mapeaRegId(conEnoc,fesCcobro.getMatricula());
			religion = "";
			if (mapReligion.containsKey(AlumPersonal.getReligionId())){
				aca.catalogo.CatReligion rel = mapReligion.get(AlumPersonal.getReligionId());
				religion = rel.getNombreReligion();
			}
		%>
	  		<tr> 
			    <td style="text-align:center;"><font size="1"><%=fesCcobro.getMatricula()%></font></td>
			    <td><font size="1"><%=fesCcobro.getNombre()%></font></td>
			    <td style="text-align:center;"><font size="1"><%=genero%></font></td>
			    <td style="text-align:center;"><font size="1"><%=fesCcobro.getResidencia().equals("E")?"Externo":"Interno"%></font></td>
			    <td><font size="1"><%=fesCcobro.gettAlumno()%></font></td>
			    <td><font size="1"><%= religion %></font></td>
			    <td style="text-align:center;"><font size="1"><%=fesCcobro.getCargaId()%></font></td>
			    <td style="text-align:center;"><font size="1"><%=fesCcobro.getModalidad()%></font></td>
			    <td style="text-align:center;"><font size="1"><%=fesCcobro.getPlanId()%></font></td>
			    <td style="text-align:center;"><font size="1"><%=fesCcobro.getSemestre()%></font></td>
	  		</tr>
		<% 	if (fesCcobro.getResidencia().equals("I")){nInternos++;internosFac++;}else{nExternos++;externosFac++;}
			if (genero.equals("M")){nHombres++;hombresFac++;}else{nMujeres++;mujeresFac++;}
			inscritosFac++;
			
			if (fesCcobro.getResidencia().equals("I")){internosCar++;}else{externosCar++;}
			if (genero.equals("M")){hombresCar++;}else{mujeresCar++;}
			inscritosCar++;
			
			cont++;
		  }%>
		  <tr> 
				<td colspan="10" height="1%">
					<table style="width:100%" class="table table-fontsmall table-bordered table-condensed">
 						<tr>
					    	<th colspan="2">Inscritos: <%=inscritosCar%></th>
					    	<th colspan="2">Internos: <%=internosCar%></th>
					    	<th colspan="2">Externos: <%=externosCar%></th>
					    	<th colspan="2">Hombres: <%=hombresCar%></th>
					    	<th colspan="2">Mujeres: <%=mujeresCar%></th>
 						</tr> 
					</table>
				</td>
	  		</tr>
		  	<tr> 
		    	<th colspan="2">Inscritos: <%=inscritosFac%></th>
		    	<th colspan="2">Internos: <%=internosFac%></th>
		    	<th colspan="2">Externos: <%=externosFac%></th>
		    	<th colspan="2">Hombres: <%=hombresFac%></th>
		    	<th colspan="2">Mujeres: <%=mujeresFac%></th>
	  		</tr>
	    </table>
    <table class="table table-bordered">
	  <tr> 
	    <td colspan="15">
	    	<table style="width:100%" class="table table-condensed">
			  <tr> 
				    <th  colspan="1"><b>INSCRITOS:<%=listaFesCcobro.size()%></b></th>
				    <th  colspan="1"><b>INTERNOS: <%=nInternos%></b></th>
				    <th  colspan="1"><b>EXTERNOS: <%=nExternos%></b></th>
				    <th  colspan="1"><b>HOMBRES: <%=nHombres%></b></th>
				    <th  colspan="1"><b>MUJERES: <%=nMujeres%></b></th>
			  </tr>
		  </table>
	    </td>
	  </tr>
	</table>
</form>
</div>
<script>
	jQuery('#fecha').datepicker();
</script>
<%@ include file = "../../cierra_enoc.jsp"%>