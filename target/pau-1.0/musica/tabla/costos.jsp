<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="PeriodoU" scope="page" class="aca.musica.MusiPeriodoUtil"/>
<jsp:useBean id="CobroU" scope="page" class="aca.musica.CobroUtil"/>

<head>
<script type="text/javascript">
	function recarga(){
		document.forma.submit();
	}
	
	function Borrar( PeriodoId, CuentaId ){
		if(confirm("Estas seguro de eliminar el registro: "+PeriodoId+"- "+CuentaId)==true){
	  		document.location="accion?Accion=4&PeriodoId="+PeriodoId+"&CuentaId="+CuentaId;
	  	}
	}
</script>
</head>
<%	
	// String periodoId 		= (String) session.getAttribute("periodo");
	java.text.DecimalFormat getformato= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String periodoId 		= request.getParameter("PeriodoId");
	if (periodoId==null || periodoId.equals("null") || periodoId.equals("")){
		periodoId 			= aca.musica.MusiPeriodo.getPeriodoActual(conEnoc);
	}
	String bgColor			= "";
	
	ArrayList lisPeriodos	= PeriodoU.getListAll(conEnoc," ORDER BY ENOC.MUSI_PERIODO.F_INICIO DESC, PERIODO_ID");
	ArrayList lisCobros		= CobroU.getListPeriodo(conEnoc,periodoId," ORDER BY ENOC.MUSI_CTATIPO(CUENTA_ID), ENOC.MUSI_CTA(CUENTA_ID)");
%>
<div class="container-fluid">
<h1>Costos de Enseñanza y Renta de Instrumentos</h1>
<form name="forma">
<div class="alert alert-info">
 	<b>Periodo: </b>
		  <select onchange='javascript:recarga()' name="PeriodoId">
<%
	for (int i=0; i< lisPeriodos.size(); i++){
		aca.musica.MusiPeriodo periodo = (aca.musica.MusiPeriodo) lisPeriodos.get(i);
%>
			<option <%if( periodoId.equals(periodo.getPeriodoId() ))out.print(" Selected ");%> value="<%= periodo.getPeriodoId() %>">[<%= periodo.getPeriodoId() %>] <%= periodo.getPeriodoNombre() %></option>
<%
	}
%>
		  </select>&nbsp;&nbsp;
		  <a class="btn btn-primary" href="accion?PeriodoId=<%=periodoId%>&Accion=1"><spring:message code='aca.Añadir'/></a>
</div>
</form>
<body>
<table style="width:100%" id="noayuda" class="table table-fullcondensed table-striped">	
	<tr>
		<th width="5%"><spring:message code="aca.Operacion"/></th>
	  	<th width="5%"><spring:message code="aca.Numero"/></th>
	  	<th width="5%"><spring:message code="aca.Periodo"/></th>
	  	<th width="40%"><spring:message code="aca.Cuenta"/></th>
	    <th width="10%"><spring:message code='aca.Cantidad'/></th>
	    <th width="30%"><spring:message code="aca.Comentario"/></th>
	    <th width="5%"><spring:message code="aca.Clases"/></th>
	</tr>
<%
	for (int i=0; i< lisCobros.size(); i++){
		aca.musica.MusiPeriodoCobro cobros = (aca.musica.MusiPeriodoCobro) lisCobros.get(i);

%>				
	<tr>
  		<td width="5%">
  			<i title="Editar" class="fas fa-edit" onclick="location.href='accion?Accion=5&PeriodoId=<%=periodoId%>&CuentaId=<%=cobros.getCuentaId()%>';"></i>
	    	<i title="Eliminar" class="fas fa-trash-alt" onclick="Borrar('<%=cobros.getPeriodoId()%>','<%=cobros.getCuentaId()%>');"></i>
		</td>
    	<td width="5%">
	  		<%=i+1%>
		</td>
	    <td width="5%"><%=cobros.getPeriodoId()%></td>
	    <td width="40%"><%=cobros.getCuentaId()%> - <%=aca.musica.MusiCuenta.getCuentaNombre(conEnoc,cobros.getCuentaId())%></td>
		<td width="10%"><%=getformato.format( Double.valueOf(cobros.getCantidad()).doubleValue())%></td>
		<td width="30%"><%=cobros.getComentario()%></td>
		<td width="5%"><%=cobros.getClases()%></td>
  	</tr>  
<%  
	}
%>
</table>
</body>
<%
	lisCobros 	= null;
%>
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>