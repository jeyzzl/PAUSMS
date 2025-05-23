<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.*"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.acceso.spring.Acceso"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.catalogo.spring.CatPeriodo"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>
<%@page import="aca.catalogo.spring.CatModalidad"%>
<%@page import="aca.vista.spring.CargaAcademica"%>
<%@page import="aca.hca.spring.HcaMaestro"%>
<%@page import="aca.hca.spring.HcaActividad"%>
<%@page import="aca.hca.spring.HcaTipo"%>
<%@page import="aca.hca.spring.HcaMaestroActividad"%>
<%@page import="aca.hca.spring.HcaRango"%>
<%@page import="aca.alumno.spring.AlumPlan"%>
<%@page import="aca.plan.spring.MapaCurso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
	DecimalFormat getformato	= new DecimalFormat("#####0.00;(#####0.00)");

	String codigoEmpleado 		= (String)session.getAttribute("codigoPersonal");
	String cargaId				= (String)request.getAttribute("cargaId");
	String periodoId			= (String)request.getAttribute("periodoId");
	String nombreMaestro		= (String)request.getAttribute("nombreMaestro");
	String semanas				= (String)request.getAttribute("semanas");
	String accion				= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	HcaMaestro hcaMaestro		= (HcaMaestro)request.getAttribute("hcaMaestro");
	Acceso acceso 				= (Acceso)request.getAttribute("acceso");	
	
	float totalSemestral		= 0F;
	float totalSemestralFinal	= 0F;	
	double promSemanal			= 0;	
	
	List<CatPeriodo> lisPeriodos 				= (List<CatPeriodo>)request.getAttribute("lisPeriodos");
	List<Carga> lisCargas		 				= (List<Carga>)request.getAttribute("lisCargas");
	List<CargaAcademica> lisCursos 				= (List<CargaAcademica>)request.getAttribute("lisCursos");
	List<HcaRango> lisRangos 					= (List<HcaRango>)request.getAttribute("lisRangos");
	List<HcaMaestroActividad> lisActividades 	= (List<HcaMaestroActividad>)request.getAttribute("lisActividades");
	
	HashMap<String,CatModalidad> mapaModalidades 	= (HashMap<String,CatModalidad>)request.getAttribute("mapaModalidades");
	HashMap<String,CatCarrera> mapaCarreras	 		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,MapaCurso> mapaCursos 			= (HashMap<String,MapaCurso>)request.getAttribute("mapaCursos");
	HashMap<String,HcaActividad> mapaActividades	= (HashMap<String,HcaActividad>)request.getAttribute("mapaActividades");
	HashMap<String,HcaTipo> mapaTipos				= (HashMap<String,HcaTipo>)request.getAttribute("mapaTipos");
	
	if((acceso.getAccesos().compareTo(hcaMaestro.getCarreraId()) != -1 && !hcaMaestro.getCarreraId().equals("")) || 
		acceso.getAdministrador().equals("S") || acceso.getSupervisor().equals("S")){	
%>
<body>	
<div class="container-fluid">
	<h3><spring:message code="portal.maestro.cargaDocente.Maestro"/>: <%=codigoEmpleado %> -- <%=nombreMaestro%></h3>	
	<form id="forma1" name="forma1" action="carga_docente" method="post">
	<div class="alert alert-info d-flex align-items-center">
		<a href="cursos" class="btn btn-primary btn-sm"><spring:message code="portal.maestro.cargaDocente.Regresar"/></a>
		&nbsp;&nbsp;
		<select id="PeriodoId" name="PeriodoId" class="form-select" style="width:250px" onchange="document.forma1.submit();">
<%		for(CatPeriodo periodo : lisPeriodos){ %>			
			<option value="<%=periodo.getPeriodoId()%>"<%=periodoId.equals(periodo.getPeriodoId())?" Selected":"" %>><%=periodo.getPeriodoId()%> - <%=periodo.getNombre()%></option>
<%		} %>
		</select>
	</div>
<%		
		for(Carga carga : lisCargas){									
			totalSemestral = 0F;
			totalSemestralFinal = 0F;
%>
	<h4><%=carga.getNombreCarga() %></h4>
	<table class="table table-sm">
	<tr class="alert alert-success"><th colspan="10"><spring:message code="portal.maestro.cargaDocente.Docencia"/></th></tr>
	<tr>
		<th align="center" width="35%"><b><spring:message code="aca.Nombre"/> <spring:message code="portal.maestro.cargaDocente.DeLaMateria"/></b></th>
		<th align="center" width="15%"><b><spring:message code="portal.maestro.cargaDocente.Nivel"/></b></th>
		<th align="center" width="7%"><b><spring:message code="aca.Modalidad"/></b></th>
		<th align="center" width="7%"><b><spring:message code="portal.maestro.cargaDocente.NAlum"/></b></th>
		<th align="center" width="5%"><b><spring:message code="portal.maestro.cargaDocente.Valor"/></b></th>
		<th align="center" width="5%"class="ayuda <%=idJsp %> 001"><font color='#ffffff'><b>FS</b></th>
		<th align="center" width="7%"><b><spring:message code="portal.maestro.cargaDocente.Semanas"/></b></th>
		<th align="center" width="8%"><b><spring:message code="portal.maestro.cargaDocente.TotalSemanal"/></b></th>
		<th align="center" width="8%"><b><spring:message code="portal.maestro.cargaDocente.TotalSemestral"/></b></th>
	</tr>
<%
		int contador = 0;
		for( CargaAcademica cargaAcademica : lisCursos){
			
			String nivelId 		= "0";
			String carreraCorto = "-";
			if (mapaCarreras.containsKey(cargaAcademica.getCarreraId())){
				nivelId 		= mapaCarreras.get(cargaAcademica.getCarreraId()).getNivelId(); 
				carreraCorto 	= mapaCarreras.get(cargaAcademica.getCarreraId()).getNombreCorto();
			}
			
			String modalidadNombre = "-";
			if (mapaModalidades.containsKey(cargaAcademica.getModalidadId())){
				modalidadNombre = mapaModalidades.get(cargaAcademica.getModalidadId()).getNombreModalidad();				
			}
			
			if (carga.getCargaId().equals(cargaAcademica.getCargaId())){
				
				// Busca el valor en la lista de rangos
				String valor = "0";
				for(HcaRango rango : lisRangos){
					if (rango.getNivelId().equals(nivelId) && rango.getModalidadId().equals(cargaAcademica.getModalidadId()) 
						&& Integer.parseInt(cargaAcademica.getNumAlum())>= Integer.parseInt(rango.getRangoIni()) &&  Integer.parseInt(cargaAcademica.getNumAlum())<= Integer.parseInt(rango.getRangoFin()) ){
						valor = rango.getValor();
					}
				}
				String frecuencias = "0";
				if (mapaCursos.containsKey(cargaAcademica.getCursoId())){
					if (mapaCursos.get(cargaAcademica.getCursoId()).getHt().equals("0")) 
						frecuencias = mapaCursos.get(cargaAcademica.getCursoId()).getHfd(); 
					else 
						frecuencias = mapaCursos.get(cargaAcademica.getCursoId()).getHt(); 
				}
				
				if(cargaAcademica.getValeucas().equals("S")){
					totalSemestral += ((Float.parseFloat(valor) * Float.parseFloat(frecuencias)) * Float.parseFloat(cargaAcademica.getSemanas()));
					if((Float.parseFloat(valor) * Float.parseFloat(frecuencias)) > 0){						
						contador++;
					}
%>
	<tr>
		<td><%=cargaAcademica.getNombreCurso()%></td>        
		<td><%=carreraCorto%></td>
		<td align="center"><%=modalidadNombre%></td>
		<td align="center">
			<%=cargaAcademica.getNumAlum() %>
		</td>
		<td align="center"><%=valor %></td>
		<td align="center"><%=frecuencias%></td>
		<td align="center"><%=cargaAcademica.getSemanas() %></td>
		<td align="center"><%=cargaAcademica.getValeucas().equals("S")?(Float.parseFloat(valor) * Float.parseFloat(frecuencias)):"-" %></td>
		<td align="center"><%=cargaAcademica.getValeucas().equals("S")?((Float.parseFloat(valor) * Float.parseFloat(frecuencias)) * Float.parseFloat(cargaAcademica.getSemanas())):"-" %></td>
	</tr>
<%
				}
			}
		}
%>
		<tr>
			<td colspan="7" align="right"><b><spring:message code="aca.Total"/></b></td>			
			<td align="center"><b><%=getformato.format(totalSemestral) %></b></td>
		</tr>
<%
		totalSemestralFinal += totalSemestral;
		totalSemestral = 0F;
%>
	</table>
	</form>
	<table class="table table-sm" style="width:100%">
	<tr class="alert alert-success">
		<th><spring:message code="portal.maestro.cargaDocente.ActividadesDocente"/></th>
	</tr>
	</table>
	<form id="forma3" name="forma3" action="docente" method="post">	
	<table class="table table-sm" style="margin: 0 auto; width:100%">
<%			
			String tipoId = "";
			double totActividad 	= 0;			
			int row = 0;
			for(HcaMaestroActividad hcaMaestroActividad : lisActividades){
				if (hcaMaestroActividad.getCargaId().equals(carga.getCargaId())){					
					row++;
					HcaActividad hcaActividad = new HcaActividad();
					if (mapaActividades.containsKey(hcaMaestroActividad.getActividadId())){
						hcaActividad = mapaActividades.get(hcaMaestroActividad.getActividadId());
					}
					
					String tipoNombre = "-";
					if(!tipoId.equals(hcaActividad.getTipoId())){
						tipoId = hcaActividad.getTipoId();
						if (mapaTipos.containsKey(tipoId)){
							tipoNombre = mapaTipos.get(tipoId).getTipoNombre();
						}
						if(row != 0){
%>
					<tr>						
						<td colspan="5" align="right"><b>Total:</b></td>
						<td align="center"><b><%=getformato.format(totalSemestral) %></b></td>
					</tr>
<%
						totalSemestralFinal += totalSemestral;						
						totalSemestral = 0F;						
						}
						contador = 0;
%>
					<tr>
						<td colspan="6">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="6"><font><b><%=tipoNombre%></b></font></td>
					</tr>
					<tr>
						<th width="5%">&nbsp;</th>
						<th align="center" width="60%"><b><spring:message code="aca.Nombre"/></b></th>
						<th align="center" width="10%"><b><spring:message code="portal.maestro.cargaDocente.Valor"/></b></font></th>
						<th align="center" width="5%"><b><spring:message code="aca.Frec"/></b></th>
						<th align="center" width="10%"><b><spring:message code="portal.maestro.cargaDocente.Semanas"/></b></th>
						<th align="center" width="10%"><b><spring:message code="portal.maestro.cargaDocente.TotHrs"/></b></th>
					</tr>
<%
					}					
					contador++;
					totActividad = Double.valueOf(hcaActividad.getValor()) * Double.valueOf(hcaMaestroActividad.getSemanas()) * Double.valueOf(hcaMaestroActividad.getHoras());
%>
					<tr>
						<td align="center">							
						</td>
						<td><%=hcaActividad.getActividadNombre() %></td>
						<td align="center"><%=hcaActividad.getValor() %></td>
						<td align="center"><%=hcaMaestroActividad.getHoras() %></td> <!-- ------------Frecuencia -->
						<td align="center"><%=hcaMaestroActividad.getSemanas() %></td>
						<td align="center"><%=Float.parseFloat(hcaActividad.getValor()) * Float.parseFloat(hcaMaestroActividad.getSemanas()) %></td>
					</tr>
<%
					totalSemestral += totActividad;	
				}
			}
%>
					<tr>
						<td colspan="5" align="right">Total:</td>						
						<td align="center"><b><%=getformato.format(totalSemestral) %></b></td>
					</tr>					
<%
			totalSemestralFinal += totalSemestral;
			promSemanal = totalSemestralFinal / Double.valueOf(semanas);		
%>					
					<tr>
					  <td style="font-size:11pt;"colspan="6" align="center" class="ayuda mensaje Horas= <%=getformato.format(totalSemestralFinal)%>  Semanas= <%=semanas%>">
					    <b><spring:message code="portal.maestro.cargaDocente.PromHorasSemana"/>: </b><%=getformato.format(promSemanal) %>&nbsp;&nbsp;&nbsp;&nbsp;
						<b><spring:message code="portal.maestro.cargaDocente.TotalHoras"/>:</b> <%=getformato.format(totalSemestralFinal) %>
					  </td>
					</tr>					
	</table>		
	</form>
<%
			totalSemestralFinal = 0;
		}
	}else{		
%>
	<table style="margin: 0 auto;">
	<tr>
		<td align="center">
			<a href="javascript:buscar('buscar')"><b>[ <spring:message code="portal.maestro.cargaDocente.BuscarEmpleado"/> ]</b></a>
		</td>
	</tr>
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="center"><b><spring:message code="portal.maestro.cargaDocente.MensajeUno"/> <%=codigoEmpleado %></b></td>
	</tr>
	</table>
<%	} %>
</div>	
</body>