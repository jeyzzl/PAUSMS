<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.HashMap"%>

<jsp:useBean id="becaU" scope="page" class="aca.objeto.BecaUtil"/>
<jsp:useBean id="facultadU" scope="page" class="aca.catalogo.CatFacultadUtil"/>
<jsp:useBean id="carreraU" scope="page" class="aca.catalogo.CatCarreraUtil"/>

<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;(###,##0.00)");

	ArrayList<aca.objeto.Beca> alumnos = becaU.getList(conEnoc, "ORDER BY FACULTAD, CARRERA");
	HashMap<String, String> ensenianzas = becaU.getMapEnsenanza(conEnoc);
	
	HashMap<String, aca.catalogo.CatFacultad> facultades = facultadU.getMapFacultad(conEnoc, "");
	HashMap<String, aca.catalogo.CatCarrera> carreras = carreraU.getMapAll(conEnoc, "");
%>
<table style="margin: 0 auto;">
	<tr>
		<td><h2>Reporte de Becas</h2></td>
	</tr>
</table>
<table class="table table-condesed table-bordered table-striped table-fontsmall" align="center" width="80%">
<%
String facultadTmp 	= "";
String carreraTmp  	= "";
int row				= 0;

for(aca.objeto.Beca alumno: alumnos){ 
	if(!alumno.getFacultadId().equals(facultadTmp)){
		
%>
		</table>
		<table style="width:80%; margin:0 auto;" class="table" style="margin-bottom:0;">
		<tr>
			<td style="border:1px solid gray;letter-spacing:2px;">
				<h3><%=facultades.get(alumno.getFacultadId()).getNombreFacultad() %></h3>
			</td>
		</tr>
		</table>
		<table class="table table-condesed table-bordered table-striped table-fontsmall" align="center" width="80%">
<%
		facultadTmp = alumno.getFacultadId();
	}
	
	if(!alumno.getCarreraId().equals(carreraTmp)){ row=0;
%>
		<tr>
			<td colspan="8">
				<h5><%=carreras.get(alumno.getCarreraId()).getNombreCarrera() %></h5>
			</td>
		</tr>
		<tr>
			<th width="5%">#</th>
			<th width="5%"><spring:message code="aca.Matricula"/></th>
			<th width="25%"><spring:message code="aca.Nombre"/></th>
			<th width="5%"><spring:message code="aca.Carga"/></th>
			<th width="5%"><spring:message code="aca.Tipo"/></th>
			<th style="text-align: right;" width="10%"><spring:message code="aca.Beca"/></th>
			<th style="text-align: right;" width="10%">Enseñanza</th>
			<th style="text-align: right;" width="10%">%</th>
		</tr>
<%
		carreraTmp = alumno.getCarreraId();
	}
	row++;
	double beca 		= 0;
	double porcentaje	= 0;
	
	if(alumno.getTipoplaza().equals("CC")){
		beca = Double.parseDouble(alumno.getImportedos())+Double.parseDouble(alumno.getBeca())+Double.parseDouble(alumno.getBeca1())+Double.parseDouble(alumno.getBeca2())+Double.parseDouble(alumno.getBeca3());
	}else{
		if(alumno.getTipoBeca().equals("7")){
			beca = Double.parseDouble(alumno.getImporte())+Double.parseDouble(alumno.getBeca());
		}else{
			beca = Double.parseDouble(alumno.getImporte())+Double.parseDouble(alumno.getBeca())+Double.parseDouble(alumno.getBeca1())+Double.parseDouble(alumno.getBeca2())+Double.parseDouble(alumno.getBeca3());
		}
	}
	
	double ensenanza = 0;
	if(ensenianzas.containsKey(alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId())){
		ensenanza = Double.valueOf(ensenianzas.get(alumno.getCodigoPersonal()+alumno.getCargaId()+alumno.getBloqueId()));
	}
	porcentaje = beca/ensenanza*100;
%>	
	<tr>
		<td><%=row%></td>
		<td><%=alumno.getCodigoPersonal() %></td>
		<td><%=alumno.getNombre() %></td>
		<td><%=alumno.getCargaId() %></td>
		<td><%=alumno.getTipoplaza() %></td>
		<td style="text-align:right;"><%=getFormato.format(beca) %></td>
		<td style="text-align:right;"><%=getFormato.format(ensenanza)%></td>
		<td style="text-align:right;"><%=getFormato.format(porcentaje>100?100:porcentaje)%></td>
	</tr>
<%} %>
</table>
<%@ include file= "../../cierra_enoc.jsp" %>