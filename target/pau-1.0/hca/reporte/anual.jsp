<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.carga.Carga"%>
<%@page import="aca.vista.CargaAcademica"%>
<%@page import="aca.hca.HcaActividad"%>
<%@page import="aca.hca.HcaMaestroActividad"%>
<%@page import="aca.hca.HcaRangoUtil"%>
<%@page import="aca.alumno.PlanUtil"%>
<%@page import="aca.plan.MapaCurso"%>
<%@page import="aca.util.Fecha"%>
<%@page import="aca.catalogo.CatModalidad"%>
<%@ page import = "java.text.*"%>

<%@page import="aca.vista.MaestrosUtil"%>
<jsp:useBean id="cargaU" class="aca.carga.CargaUtil" scope="page"/>
<jsp:useBean id="PeriodoU" class="aca.catalogo.CatPeriodoUtil" scope="page"/>
<jsp:useBean id="cargaAcaU" class="aca.vista.CargaAcaUtil" scope="page"/>
<jsp:useBean id="cargaAcademica" class="aca.vista.CargaAcademica" scope="page"/>
<jsp:useBean id="alumnoCursoU" class="aca.vista.AlumnoCursoUtil" scope="page"/>
<jsp:useBean id="cargaGrupo" class="aca.carga.CargaGrupo" scope="page"/>
<jsp:useBean id="hcaMaestro" class="aca.hca.HcaMaestro" scope="page"/>
<jsp:useBean id="acceso" class="aca.acceso.Acceso" scope="page"/>
<jsp:useBean id="AccesoU" scope="page" class="aca.acceso.AccesoUtil" />
<jsp:useBean id="hcaMaestroActividad" class="aca.hca.HcaMaestroActividad" scope="page"/>
<jsp:useBean id="hcaMAU" class="aca.hca.HcaMAUtil" scope="page"/>
<jsp:useBean id="hcaActividad" class="aca.hca.HcaActividad" scope="page"/>
<jsp:useBean id="hcaActividadU" class="aca.hca.HcaActividadUtil" scope="page"/>
<jsp:useBean id="hcaTipo" class="aca.hca.HcaTipo" scope="page"/>
<jsp:useBean id="accesoU"  class="aca.acceso.AccesoUtil" scope="page"/>
<jsp:useBean id="fecha"  class="aca.util.Fecha" scope="page"/>
<%
	DecimalFormat getformato		= new DecimalFormat("#####0.00;(#####0.00)");

	String codigoEmpleado 			= (String) session.getAttribute("codigoEmpleado");	
	String periodoId				= request.getParameter("PeriodoId")==null?aca.catalogo.CatPeriodoUtil.getPeriodo(conEnoc):request.getParameter("PeriodoId");	
	
	double totalSemestral			= 0;
	double totalSemestralFinal		= 0;	
	double promSemanal				= 0;	
	String semanas					= "0";
	
	// Lista de periodos
	ArrayList<aca.catalogo.CatPeriodo> lisPeriodos	= PeriodoU.getListAll(conEnoc,"ORDER BY PERIODO_ID");
	
	// Lista de cargas en las que imparte materias el maestro	
	ArrayList <aca.carga.Carga> lisCargas			= cargaU.getListMaestroPeriodo(conEnoc, codigoEmpleado, periodoId);	
%>
<head>
	<script type="text/javascript">
		function buscar(pag){
			abrirVentana("bem",400,400,200,500,"no","yes","yes","no","no",pag,false);
		}
		
		function abrirVentana(strName,iW,iH,TOP,LEFT,R,S,SC,T,TB,URL,modal){
			var sF="";
			if (navigator.appName=="Microsoft Internet Explorer" && modal){
				sF+=T?'unadorned:'+T+';':'';
				sF+=TB?'help:'+TB+';':'';
				sF+=S?'status:'+S+';':'';
				sF+=SC?'scroll:'+SC+';':'';
				sF+=R?'resizable:'+R+';':'';
				sF+=iW?'dialogWidth:'+iW+'px;':'';
				sF+=iH?'dialogHeight:'+(parseInt(iH)+(S?42:0))+'px;':'';
				sF+=TOP?'dialogTop:'+TOP+'px;':'';
				sF+=LEFT?'dialogLeft:'+LEFT+'px;':'';	
				return window.showModalDialog(URL,"",sF);
			}else{
				sF+=iW?'width='+iW+',':'';
				sF+=iH?'height='+iH+',':'';
				sF+=R?'resizable='+R+',':'';
				sF+=S?'status='+S+',':'';
				sF+=SC?'scrollbars='+SC+',':'';
				sF+=T?'titlebar='+T+',':'';
				sF+=TB?'toolbar='+TB+',':'';
				sF+=TB?'menubar='+TB+',':'';
				sF+=TOP?'top='+TOP+',':'';
				sF+=LEFT?'left='+LEFT+',':'';
				return window.open(URL,strName?strName:'',sF).focus()
			}
		}
	</script>
	<style type="text/css">
	input[type="checkbox"] {
	    transform:scale(2,2);
	    -ms-transform:scale(2,2); /* IE 9 */
	    -moz-transform:scale(2,2); /* Firefox */
	    -webkit-transform:scale(2,2); /* Safari and Chrome */
	    -o-transform:scale(2,2); /* Opera */
	}
</style>
</head>
<%
	hcaMaestro.mapeaRegId(conEnoc, codigoEmpleado);
	acceso = AccesoU.mapeaRegId(conEnoc, (String) session.getAttribute("codigoPersonal"));
	if((acceso.getAccesos().compareTo(hcaMaestro.getCarreraId()) != -1 && !hcaMaestro.getCarreraId().equals("")) || Boolean.parseBoolean(session.getAttribute("admin")+"") || accesoU.esSupervisor(conEnoc, (String) session.getAttribute("codigoPersonal"))){
%>
<div class="container-fluid">
	<form id="forma1" name="forma1" action="anual" method="post">	
	<h2> Contrato Docente <small class="text-muted fs-4">( <%=codigoEmpleado %> - <%= MaestrosUtil.getNombreMaestro(conEnoc,codigoEmpleado,"NOMBRE")%> )</small> </h2>
	<div class="alert alert-info d-flex aign-itemes-center" >
		<a href="../contrato/docente" class="btn btn-primary"><spring:message code='aca.Regresar'/></a>&nbsp;&nbsp;		
		<a href="javascript:buscar('buscar')" class="btn btn-primary"><i class="icon-white icon-search"></i> Empleados</a>&nbsp;&nbsp;
		<select id="PeriodoId" name="PeriodoId" class="form-select" style="width:200px" onchange="document.forma1.submit();">
<%
		for (int i=0; i< lisPeriodos.size(); i++){
			aca.catalogo.CatPeriodo per = (aca.catalogo.CatPeriodo) lisPeriodos.get(i);
%>
			<option value="<%= per.getPeriodoId() %>"<%=per.getPeriodoId().equals(periodoId)?" Selected":"" %>><%= per.getNombre() %></option>
<%
		}
%>
		</select>&nbsp;&nbsp;
	<%for(aca.carga.Carga carga : lisCargas){%>
	<%=carga.getNombreCarga() %> &nbsp;
			<input name="<%= carga.getCargaId() %>" type="checkbox" value="S" <% if (request.getParameter(carga.getCargaId())!=null) out.print("checked");%> >
			&nbsp;&nbsp;
	<%}	
	%>
		<input type="button" class="btn btn-primary" onclick="Grabar();" value="Filtrar" />
	
	</div>
	</form>
<%
		int cont = 0;
		for(int j = 0; j < lisCargas.size(); j++){
			aca.carga.Carga carga = (aca.carga.Carga) lisCargas.get(j);
			ArrayList<aca.vista.CargaAcademica> lisCursos = cargaAcaU.getListaMaestro(conEnoc, carga.getCargaId(), codigoEmpleado, "ORDER BY NOMBRE_CURSO");
			
			totalSemestral 		= 0F;
			totalSemestralFinal	= 0F;		
			if ( request.getParameter(carga.getCargaId())!=null ){
%>
	<div class="alert alert-info">
		<h3><%=carga.getNombreCarga() %><small class="text-muted fs-5">( No. Materias:<%=lisCursos.size()%>)</small></h3>
	</div>
	<div class="center">
 		<h3>Docencia</h3>
		<table class="table table-bordered table-condensed">
		<tr>
			<th align="center" width="35%"><spring:message code="aca.Nombre"/> de la materia</th>
			<th align="center" width="15%">Nivel</th>
			<th align="center" width="7%"><spring:message code="aca.Modalidad"/></th>
			<th align="center" width="7%">N° Alum</th>
			<th align="center" width="5%">Valor</th>
			<th align="center" width="5%"class="ayuda <%=idJsp %> 001">FS</th>
			<th align="center" width="7%">Semanas</th>
			<th align="center" width="8%">Total Semanal</th>
			<th align="center" width="8%">Total Semestral</th>
		</tr>
<%

			int contador = 0;
			for(int i = 0; i < lisCursos.size(); i++){
				
				cargaAcademica = (CargaAcademica) lisCursos.get(i);
				String modalidadMateria = "0";
				if (!cargaAcademica.getModalidadId().equals("1")&&!cargaAcademica.getModalidadId().equals("4")) 
					modalidadMateria = "5";
				else
					modalidadMateria = cargaAcademica.getModalidadId();
				
				String valor = HcaRangoUtil.getValor(conEnoc, PlanUtil.getCarreraNivel(conEnoc, cargaAcademica.getCarreraId()), modalidadMateria, cargaAcademica.getNumAlum());
				int fs = Integer.parseInt(aca.plan.CursoUtil.getFS(conEnoc, cargaAcademica.getCursoId()));
				if (fs==0) {
					fs = Integer.parseInt(aca.plan.CursoUtil.getHH(conEnoc, cargaAcademica.getCursoId()));
				}
				if(cargaAcademica.getValeucas().equals("S")){
					totalSemestral += ((Double.valueOf(valor) * fs) * Double.valueOf(cargaAcademica.getSemanas()));
					if((Double.valueOf(valor) * fs) > 0){						
						contador++;
					}
%>
		<tr>
			<td><%=cargaAcademica.getNombreCurso()%></td>
			<td><%=aca.catalogo.CatCarreraUtil.getNombreCorto(conEnoc,cargaAcademica.getCarreraId())%></td>
			<td align="center"><%=aca.catalogo.ModalidadUtil.getNombreModalidad(conEnoc, cargaAcademica.getModalidadId())%></td>
			<td style="text-align:right">
				<%=cargaAcademica.getNumAlum() %>
			</td>
			<td style="text-align:right"><%=valor %></td>
			<td style="text-align:right"><%=fs %></td>
			<td style="text-align:right"><%=cargaAcademica.getSemanas() %></td>
			<td style="text-align:right"><%=cargaAcademica.getValeucas().equals("S")?(Double.valueOf(valor) * fs):"-" %></td>
			<td style="text-align:right"><%=cargaAcademica.getValeucas().equals("S")?((Double.valueOf(valor) * fs) * Double.valueOf(cargaAcademica.getSemanas())):"-" %></td>
		</tr>
<%
				}
			}
%>
		<tr>
			<td colspan="8" align="right">
				<b>
				<%=contador==0?" ¡No hay materias capturadas! ":"Total "%>
				</b>				
			</td>			
			<td style="text-align:right"><strong><%=getformato.format(totalSemestral) %></strong></td>
		</tr>
<%
			totalSemestralFinal += totalSemestral;			
			totalSemestral = 0F;
%>
		</table>
	</div>	
		
	<div class="center">
		<h3>Actividades del Docente</h3>
		<form id="forma3" name="forma3" action="docente" method="post">
		<table class="table table-bordered table-condensed">
<%
			ArrayList lisMA = hcaMAU.getListMaestroCarga(conEnoc, codigoEmpleado, carga.getCargaId(), "ORDER BY HCA_ACTORDEN(ACTIVIDAD_ID)");
			String tipoId 			= "";
			double totActividad 	= 0;
			semanas = aca.carga.CargaUtil.getSemanas(conEnoc, carga.getCargaId());
			
			for(int i = 0; i < lisMA.size(); i++){				
				hcaMaestroActividad = (HcaMaestroActividad) lisMA.get(i);
				hcaActividad.mapeaRegId(conEnoc, hcaMaestroActividad.getActividadId());
				if(!tipoId.equals(hcaActividad.getTipoId())){
					tipoId = hcaActividad.getTipoId();
					hcaTipo.mapeaRegId(conEnoc, tipoId);
					if(i != 0){
%>
		<tr>
			<td colspan="4" style="text-align:right"><b>Total:</b>&nbsp;</td>
			<td style="text-align:right"><b><%=getformato.format(totalSemestral) %></b></td>
		</tr>
<%
						totalSemestralFinal += totalSemestral;						
						totalSemestral = 0F;						
					} 
					contador = 0;					
%>			
		<tr>
			<td colspan="6"><h4><strong><%=hcaTipo.getTipoNombre() %></strong></h4></td>
		</tr>
		<tr>
			<th align="center" width="45%"><spring:message code="aca.Nombre"/></th>
			<th align="center" width="10%">valor</th>
			<th align="center" width="10%"><spring:message code="aca.Frec"/></th>
			<th align="center" width="10%">Semanas</th>						
			<th align="center" width="15%">Tot. Hrs.</th>
		</tr>
<%
				}				
				contador++;
				totActividad = Double.valueOf(hcaActividad.getValor()) * Double.valueOf(hcaMaestroActividad.getSemanas()) * Double.valueOf(hcaMaestroActividad.getHoras());				
%>
			<tr>
				<td><%=hcaActividad.getActividadNombre() %></td>
				<td style="text-align:right"><%=hcaActividad.getValor() %></td>
				<td style="text-align:right"><%=hcaMaestroActividad.getHoras() %></td>
				<td style="text-align:right"><%=hcaMaestroActividad.getSemanas() %></td>
				<td style="text-align:right"><%=totActividad%></td>
			</tr>
<%
				totalSemestral += totActividad;
			}
%>
			<tr>
				<td colspan="4" style="text-align:right"><b>Total:</b>&nbsp;</td>						
				<td style="text-align:right"><b><%=getformato.format(totalSemestral) %></b></td>
			</tr>
<%			
			totalSemestralFinal += totalSemestral;
			promSemanal = totalSemestralFinal / Double.valueOf(semanas);
%>					
		</table>
		</form>
		<div class="alert alert-warning">
			<h4>Prom. hrs. Semana(Horas / semanas): <%=getformato.format(promSemanal) %>&nbsp;&nbsp;
			Total de Horas: <%=getformato.format(totalSemestralFinal) %></h4>
		</div>
	</div>
<%			totalSemestralFinal = 0;		
%>
	<br><br>
	<table class="table table-condensed">
<%
	String empCarreraBase= aca.hca.HcaMaestroUtil.getEmpCarreraBase(conEnoc,codigoEmpleado);
	String coordinador	= aca.catalogo.CatCarreraUtil.getCoordinador(conEnoc,empCarreraBase);
	String empFacBase	= aca.hca.HcaMaestroUtil.getEmpFacBase(conEnoc,codigoEmpleado);
	String director 	= aca.catalogo.CatFacultadUtil.Director(conEnoc,empFacBase);

	if(!codigoEmpleado.equals(director)){%>				
					<tr>					
						<td colspan="2" align="center"><div align="center">__________________________________<br><b><%=MaestrosUtil.getNombreMaestro(conEnoc, codigoEmpleado,"NOMBRE") %><br><spring:message code="aca.Maestro"/></b></div></td>						
						<td colspan="4" align="center">
<%}	if(!codigoEmpleado.equals(coordinador)){%>						
						<div align="center">__________________________________<br><b><%= MaestrosUtil.getNombreMaestro(conEnoc,coordinador,"NOMBRE")%><br><spring:message code="aca.Coordinador"/></b></div>
<% 	}else{%>
						<div align="center">__________________________________<br><b><%= MaestrosUtil.getNombreMaestro(conEnoc,director,"NOMBRE")%><br><spring:message code="aca.Director"/></b></div>
<% }%>
						</td>
	</table>
<% 		
			} // Si encuentra la carga
		} // Recorre las cargas
		
	}else{
%>
	<table style="margin: 0 auto;">
	<tr>
		<td align="center">
			<a href="javascript:buscar('buscar')"><b>[ Buscar Empleado ]</b></a>
		</td>
	</tr>	
	<tr>
		<td align="center"><b>No tiene acceso para ver al empleado <%=codigoEmpleado %></b></td>
	</tr>
	</table>
<%
	}
%>
</div>
<script>
	function Grabar(){		
		document.forma1.submit();
	}
</script>
<%@ include file= "../../cierra_enoc.jsp" %>