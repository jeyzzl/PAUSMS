<%@page import="java.util.List"%>
<%@page import="aca.carga.spring.CargaEnLinea"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="lineaU" scope="page" class="aca.carga.CargaEnlineaUtil"/>
<jsp:useBean id="enlinea" scope="page" class="aca.carga.CargaEnlinea"/>

<head>
<script type="text/javascript">

	function Borrar( cargaId ){
		if(confirm("<spring:message code="aca.JSEliminar"/>")==true){
	  		document.location.href="borrar?CargaId="+cargaId;
	  	}
	}
	
</script>
<%
	List<CargaEnLinea> lisCargas	=  (List<CargaEnLinea>)request.getAttribute("lisCargas");
%>
</head>
<body>
<div class="container-fluid">
<h2><spring:message code="cargaGrupo.enLinea.PeriodoCargasEnLinea"/></h2>
<form action="carga" method="post" name="forma" target="_self">
<div class="alert alert-info">
	<a class="btn btn-primary" href="editar"><spring:message code="aca.Anadir"/></a>
</div>
	<table class="table table-bordered">
	<thead class="table-info">
		<tr>
		    <th width="5%"><spring:message code="aca.Operacion"/></th>
		    <th width="3%" align="center"><spring:message code="aca.Numero"/></th>
		    <th width="10%" align="center"><spring:message code="aca.Carga"/></th>
		    <th width="20%"><spring:message code="aca.Nombre"/></th>
		    <th width="30%"><spring:message code="aca.Descripcion"/></th>
		    <th width="10%" align="center"><spring:message code="cargasGrupo.enLinea.FInicio"/></th>
		    <th width="10%" align="center"><spring:message code="cargasGrupo.enLinea.FFinal"/></th>
		    <th width="5%" align="center"><spring:message code="aca.Status"/></th>
		    <th width="5%" align="center">Enrollment Proof</th>
	    </tr>
    </thead>
<%
	int i=0;
	for(CargaEnLinea enLinea : lisCargas){
		i++;
%>
	    <tr class="tr2">
	    <td align="center"> <a class="fas fa-edit" href="editar?CargaId=<%=enLinea.getCargaId()%>"></a>
	      <a class="fas fa-trash-alt" href="javascript:Borrar('<%=enLinea.getCargaId()%>')"></a>
	    </td>
	    <td align="center"><%=i%></td>
	    <td align="center"><%=enLinea.getCargaId() %></td>
	    <td><%=enLinea.getNombre() %></td>
	    <td><%=enLinea.getDescripcion() %></td>
	    <td align="center"><%=enLinea.getfInicio() %></td>
	    <td align="center"><%=enLinea.getfFinal() %></td>
	    <td align="center"><%=enLinea.getEstado().equals("A")?"Active":"Inactive"%></td>
	    <td align="center"><%=enLinea.getCarta().equals("S")?"Yes":"No"%></td>
	  </tr>
<%	}%>
	</table>
</form>
</div>
</body>