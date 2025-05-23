<%@page import="aca.edo.EdoPeriodo"%>
<%@page import="aca.edo.Edo"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="edoPeriodo" scope="page" class="aca.edo.EdoPeriodo" />
<jsp:useBean id="edoPeriodoU" scope="page" class="aca.edo.EdoPeriodoUtil" />
<jsp:useBean id="edo" scope="page" class="aca.edo.Edo" />
<jsp:useBean id="edoU" scope="page" class="aca.edo.EdoUtil" />
<%	
	String periodoId = request.getParameter("periodoId")==null ? edoPeriodoU.getListAll(conEnoc, " ORDER BY ENOC.EDO_PERIODO.F_INICIO DESC").get(0).getPeriodoId() : request.getParameter("periodoId");	
	
	// Lista de periodos
	ArrayList<EdoPeriodo> lisPeriodo = edoPeriodoU.getListAll(conEnoc, " ORDER BY ENOC.EDO_PERIODO.F_INICIO DESC");
	
%>
<body>
<div class="container-fluid">
<h1>Opinion Estudiantil</h1>
<form id="forma" name="forma" action="evaluacion" method="post">
<div class="alert alert-info d-flex align-items-center">
	<select id="periodoId" name="periodoId" class="form-control" style="width:250px" onchange="document.forma.submit();" style="width:300px;">
<%
	if(periodoId == null && lisPeriodo.size() > 0){
		periodoId = aca.edo.EdoPeriodoUtil.getActual(conEnoc,aca.util.Fecha.getHoy());
	}
	for(int i = 0; i < lisPeriodo.size(); i++){
		edoPeriodo = (EdoPeriodo) lisPeriodo.get(i);
%>		<option value="<%=edoPeriodo.getPeriodoId() %>"<%=edoPeriodo.getPeriodoId().equals(periodoId)?" Selected":"" %>><%=edoPeriodo.getPeriodoNombre() %></option>
<%	} %>
		</select>
</div>
</form>
  <table  class="table table-sm table-bordered">
  <tr class="table-info">
    <th><spring:message code="aca.Numero"/></th>
    <th>Evaluacion</th>
    <th>F. Inicio</th>
    <th>F. Final</th>
    <th><spring:message code="aca.Maestro"/></th>
    <th>Item</th>
  </tr>
<%
	ArrayList<Edo> lisEdo		= edoU.getListPeriodo(conEnoc, periodoId, " ORDER BY ENOC.EDO.F_INICIO, NOMBRE");
	for(int i = 0; i < lisEdo.size(); i++){		
		edo = (Edo) lisEdo.get(i);			
%>
 
	<td align="center"> <%=i+1%> </td>	
	<td>&nbsp;
	
	  <%=edo.getTipo().equals("E")?"Opinion Est.":edo.getTipo().equals("A")?"Autoevaluaci&oacute;n":"" %> - <%=edo.getNombre()%>
	
	</td>
	<td align="center"><%=edo.getFInicio()%></td>
	<td align="center"><%=edo.getFFinal()%></td>
	<td align="center">
	  <a href="maestro?periodo=<%=periodoId%>&edo=<%=edo.getEdoId()%>" title="Ver por Maestro">Ver</a>
	</td>
	<td align="center">
	  <a href="item?periodo=<%=periodoId%>&edo=<%=edo.getEdoId()%>" title="Ver por Item">Ver</a>
	</td>
  </tr>
<%	}  %>

  </table>
  </div>	
</body>
<%@ include file= "../../cierra_enoc.jsp" %>