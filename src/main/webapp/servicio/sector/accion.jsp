<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.ssoc.spring.SsocSector"%>

<script type="text/javascript">	
	function Grabar(){
		if(document.frmdocumento.SectorId!="" && document.frmdocumento.Sector_Nombre!=""){			
			document.frmdocumento.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
</script>
<%
	SsocSector sector			= (SsocSector)request.getAttribute("sector");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	if (mensaje.equals("1")) mensaje = "¡ Grabado !"; else if (mensaje.equals("2")) mensaje = "¡ Error al grabar !";
	%>


<body>
<div class="container-fluid">
	<h1>Añadir Sector</h1>
	<form action="grabar" method="post" name="frmDocumento">
		<input type="hidden" name="Accion">
		<div class="alert alert-info">
			<a href="sector" class="btn btn-primary"><spring:message code="aca.Regresar"/></a> 
		</div>
<%	if (!mensaje.equals("-")){%>	
		<div class="alert alert-info">
			<%=mensaje%>		
		</div>	
<%	} %>
		<div class="form-group">
			<label for="SectorId">Id:</label>
		    <input type="text" class="input input-mini" name="SectorId" id="SectorId" value="<%=sector.getSectorId()%>" required/>
		    <br><br>
		    <label for="SectorNombre" >Nombre:</label>
		    <input type="text" class="input input-xlarge" name="SectorNombre" id="SectorNombre" value="<%=sector.getSectorNombre()%>" required/>
		    <br><br> 
		</div>
		<div class="alert alert-info"> 
	        <a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>
   	
<%-- 		  <a href="javascript:Nuevo()" class="btn btn-primary"><spring:message code='aca.Nuevo'/></a> &nbsp; --%>
<%-- 		  <a href="javascript:Modificar()" class="btn btn-primary"><spring:message code='aca.Modificar'/></a> &nbsp;  --%>
<%-- 		  <a href="javascript:Borrar()" class="btn btn-danger"><spring:message code='aca.Borrar'/></a> &nbsp; --%>
<%-- 		  <a href="javascript:Consultar()" class="btn btn-primary"><spring:message code='aca.Consultar'/></a> --%>
 		</div>
	</form>	  
</div>
</body>