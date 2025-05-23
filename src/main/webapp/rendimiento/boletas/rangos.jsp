<%@page import="java.util.List"%>
<%@page import="aca.carga.spring.Carga"%>
<%@page import="aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<!-- inicio de estructura -->
<%
	String cargaId 					= (String)request.getAttribute("cargaId");
	String carreraId 				= (String)request.getAttribute("carreraId");
	String rangoIni 				= (String)request.getAttribute("rangoIni");
	String rangoFin 				= (String)request.getAttribute("rangoFin");
	List<Carga> lisCargas 			= (List<Carga>) request.getAttribute("lisCargas");
	List<CatCarrera> lisCarreras 	= (List<CatCarrera>) request.getAttribute("lisCarreras");	
%>
<div class="container-fluid">
<h2>Impresion de Boletas de Calificaciones</h2>
<form name="frmRangos" action="rangos" method="post">
<div class="alert alert-info d-flex align-items-center">
	<strong>Cargas:</strong>&nbsp;
    <select id="CargaId" name="CargaId" class="form-select chosen" style="width:350px;" onchange="javascript:document.frmRangos.submit()">
<%	for (Carga carga:lisCargas){ %>
		<option value="<%=carga.getCargaId()%>" <%=carga.getCargaId().equals(cargaId)?"selected":""%>><%=carga.getCargaId()+" - "+carga.getNombreCarga()%></option>
<%	}%>
    </select>   
    &nbsp;<strong>Rango/Matriculas:</strong>
    <input class="form-control" name="RangoIni" type="text" class="text" value="<%=rangoIni%>" size="10" maxlength="7" style="width:100px;">
    al &nbsp; 
    <input class="form-control" name="RangoFin" type="text" class="text" value="<%=rangoFin%>" size="10" maxlength="7" style="width:100px;">    
</div>
<div class="alert alert-info d-flex align-items-center">
	<strong>Elegir Carrera:</strong>&nbsp;
   	<select id="CarreraId" name="CarreraId" class="form-select chosen" style="width:550px;" onchange="javascript:document.frmRangos.submit()">
<%	for (CatCarrera carrera : lisCarreras){ %>
			<option value="<%=carrera.getCarreraId()%>" <%=carrera.getCarreraId().equals(carreraId)?"selected":""%>><%=carrera.getNombreCarrera()%></option>
<%	} %>
    </select>    
    &nbsp;&nbsp; 
    <a href="boletaPdf?CargaId=<%=cargaId%>&CarreraId=<%=carreraId%>&RangoIni=<%=rangoIni%>&RangoFin=<%=rangoFin%>" class="btn btn-primary">Boletas Pdf</a>
</div>
</form>
</div>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();	
</script>