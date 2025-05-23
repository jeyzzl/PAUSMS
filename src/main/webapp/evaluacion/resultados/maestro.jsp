<%@page import="aca.edo.EdoPeriodo"%>
<%@page import="aca.edo.Edo"%>
<%@page import="aca.hca.HcaMaestro"%>
<%@page import="aca.edo.EdoAlumnoRespUtil"%>
<%@page import="aca.edo.EdoAutoResp"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="edoPeriodo" scope="page" class="aca.edo.EdoPeriodo" />
<jsp:useBean id="edoPeriodoU" scope="page" class="aca.edo.EdoPeriodoUtil" />
<jsp:useBean id="edo" scope="page" class="aca.edo.Edo" />
<jsp:useBean id="edoU" scope="page" class="aca.edo.EdoUtil" />
<jsp:useBean id="hcaMaestro" scope="page" class="aca.hca.HcaMaestro" />
<jsp:useBean id="hcaMaestroU" scope="page" class="aca.hca.HcaMaestroUtil" />
<%
	String facultadId				= request.getParameter("facultad");
	String periodoId				= request.getParameter("periodoId")==null?"0":request.getParameter("periodoId");
	
	String nombrefacultad 			= aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, facultadId);
	
	int edoSize						= 0;
	int alumnoSize					= 0;
	int autoSize					= 0;
	
	// Lista de periodos
	ArrayList<EdoPeriodo> lisPeriodo 	= edoPeriodoU.getListAll(conEnoc, " ORDER BY ENOC.EDO_PERIODO.F_INICIO DESC");
	
	// Lista de maestros
	ArrayList<HcaMaestro> lisMaestro 	= hcaMaestroU.getListFacultad(conEnoc, facultadId, " ORDER BY ENOC.EMP_APELLIDO(CODIGO_PERSONAL)");
%>
<body>
<div class="container-fluid">
	<h2>Resultados de la Evaluación <small class="text-muted fs-4">( <%=nombrefacultad%> )</small></h2>
	<form id="forma" name="forma" action="maestro?facultad=<%=facultadId %>" method="post">	
	<div class="alert alert-info d-flex justify-content-start">
		<a href="facultad" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
		<select id="periodoId" name="periodoId" class="form-select" style="width:300px" onchange="document.forma.submit();">
<%
	if(periodoId.equals("0") && lisPeriodo.size() > 0){ periodoId = ((EdoPeriodo) lisPeriodo.get(0)).getPeriodoId(); }
	for(int i = 0; i < lisPeriodo.size(); i++){
		edoPeriodo = (EdoPeriodo) lisPeriodo.get(i);
%>
			<option value="<%=edoPeriodo.getPeriodoId() %>"<%=edoPeriodo.getPeriodoId().equals(periodoId)?" Selected":"" %>><%=edoPeriodo.getPeriodoNombre() %></option>
<%	} %>
		</select>
	</div>		
	</form>
	<table  class="table table-sm table-striped table-bordered">
		<tr class="table-info">
			<th>N° Nomina</th>
			<th><spring:message code="aca.Nombre"/></th>
			<th>Evaluacion</th>
			<th>Porcentaje</th>
		</tr>
<%
	ArrayList<Edo> lisEdo = edoU.getListPeriodo(conEnoc, periodoId, " ORDER BY ENOC.EDO.F_INICIO, NOMBRE");
	edoSize = lisEdo.size();
	for(int i = 0; i < lisMaestro.size(); i++){
		hcaMaestro = (HcaMaestro) lisMaestro.get(i);
		float alumno	= 0f;
		//float auto		= 0f;
		for(int j = 0; j < edoSize; j++){
			edo = (Edo) lisEdo.get(j);
			String tmpAlumno	= EdoAlumnoRespUtil.getPromedioMaestro(conEnoc, edo.getEdoId(), hcaMaestro.getCodigoPersonal());
			//String tmpAuto		= EdoAutoResp.getPromedio(conEnoc, edo.getEdoId(), hcaMaestro.getCodigoPersonal());
			
			//System.out.println("EdoAutoResp.getPromedio(conEnoc, "+edo.getEdoId()+", "+hcaMaestro.getCodigoPersonal()+")");
			alumno	+= Float.parseFloat(tmpAlumno);
			//auto	+= Float.parseFloat(tmpAuto);
			String tipo = edo.getTipo();
%>
	
			<td align="center"><%=(j==0)?(hcaMaestro.getCodigoPersonal()):"&nbsp;" %></td>
			<td><%=(j==0)?( aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, hcaMaestro.getCodigoPersonal(), "APELLIDO")):"&nbsp;" %></td>
			<td>
				<%=tipo.equals("E")?"Opinion Est.":tipo.equals("A")?"Autoevaluaci&oacute;n":"" %>
				 - 
				<%=edo.getNombre() %>
			</td>
			<td align="center">
<%
				if(tipo.equals("E")){
%>
					<%=tmpAlumno %>
<%
				}
%>
			</td>
		</tr>
<%
			if(edoSize > 1 && j == (edoSize-1)){
%>
		
			<td style="border-bottom: dotted 1px gray;">&nbsp;</td>
			<td style="border-bottom: dotted 1px gray;">&nbsp;</td>
			<td style="border-bottom: dotted 1px gray;" align="right"><b><spring:message code="aca.Total"/></b></td>
			<td style="border-bottom: dotted 1px gray;" align="center"><b><%=(alumno)/edoSize %></b></td>
		</tr>
<%
			}
		}
	}
%>
	</table>
</div>	
</body>
<%@ include file= "../../cierra_enoc.jsp" %>