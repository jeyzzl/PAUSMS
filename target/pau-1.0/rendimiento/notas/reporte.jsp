<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="aca.carga.spring.Carga" %>
<%@ page import="aca.catalogo.spring.CatCarrera" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	String cargaId					= (String) request.getAttribute("cargaId");
	String carreraId				= (String) request.getAttribute("carreraId");
	List<Carga> lisCargas 			= (List<Carga>) request.getAttribute("lisCargas");
	List<CatCarrera> lisCarreras	= (List<CatCarrera>) request.getAttribute("lisCarreras");
%>
<div class="container-fluid">
	<h1>Concentrado de Calificaciones por Carga Academica</h1>
	<form name="frmReporte" method="post" action="reporte">
	<div class="alert alert-info">
		<strong>Cargas:</strong>&nbsp;&nbsp;
    	<select name="CargaId" id="CargaId" onchange="document.frmReporte.submit();" style="width:350px">
<%	for (Carga carga : lisCargas ){%>
		<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%=carga.getCargaId()%> - <%=carga.getNombreCarga()%></option>
<%	}%>
     	</select>
     	&nbsp;&nbsp;
        <strong>Carreras: </strong>      
        &nbsp;&nbsp;
	    <select name="CarreraId" id="CarreraId" onchange="document.frmReporte.submit();" style="width:450px">
<%	for (CatCarrera carrera : lisCarreras){%>
		<option value="<%=carrera.getCarreraId()%>" <%=carrera.getCarreraId().equals(carreraId)?"selected":""%>><%=carrera.getCarreraId()%> - <%=carrera.getNombreCarrera()%></option>
<%	}%>
        </select>
        &nbsp;&nbsp;
        <a href="alumnos?CargaId=<%=cargaId%>&CarreraId=<%=carreraId%>" class="btn btn-primary">Buscar</a>
	</div>
	</form>
</div>
<script type="text/javascript">
	function Refrescar(){
		
	}
</script>	  
<!-- fin de estructura -->