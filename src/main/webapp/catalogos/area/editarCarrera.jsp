<%@ page import= "java.util.List"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatNivel"%>
<%@ page import= "aca.vista.spring.Maestros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<script type="text/javascript">
	
	function Grabar(){
		if(document.frmcarrera.CarreraId.value!="" && document.frmcarrera.NivelId.value!="" && document.frmcarrera.Titulo.value!=""){
			if(document.frmcarrera.NombreCarrera.value!="" && document.frmcarrera.NombreCorto.value!=""){
			document.frmcarrera.Accion.value="2";
			document.frmcarrera.submit();			
			}else{
				alert("<spring:message code="aca.JSCompletar"/> 1");
			}
		}else{
			alert("<spring:message code="aca.JSCompletar"/> 2");
		}
	}	
</script>
<%	
	// Declaracion de variables	
	String facultadId			= (String)session.getAttribute("facultadId");
	CatCarrera carrera 			= (CatCarrera) request.getAttribute("carrera");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	List<CatNivel> lisNiveles	= (List<CatNivel>) request.getAttribute("lisNiveles");
	List<Maestros> lisMaestros	= (List<Maestros>) request.getAttribute("lisMaestros");
%>
<html>
<body>
<div class="container-fluid">
	<h2><spring:message code="catalogos.area.Titulo6"/></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="carrera?FacultadId=<%=facultadId%>&AreaId=<%=facultadId.substring(0,1)%>"><i class="fas fa-arrow-left"></i></a>
		&nbsp;&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>
	<form name="frmcarrera" action="grabarCarrera" method="post">
	<input type="hidden" name="Accion">	
	<div class="form-group">    
        <label for="aca.Clave"><spring:message code="aca.Clave"/></label>
        <input name="CarreraId" type="text" style="width:200px" class="input input-mini form-control" id="CarreraId" value="<%=carrera.getCarreraId()%>" readonly>
        <br> 
        <label for="catalogos.area.NivelId"><spring:message code="catalogos.area.NivelId"/></label>
        <select name="NivelId" class="form-select" style="width:200px">
   <% for (CatNivel nivel : lisNiveles){%>
   			<option value="<%=nivel.getNivelId()%>" <%=nivel.getNivelId().equals(carrera.getNivelId())?"selected":""%>><%=nivel.getNombreNivel()%></option>      
   <% }%>      	
        </select>         
        <br> 
        <label for="Titulo"><spring:message code="aca.Titulo"/></label>
        <input name="Titulo" type="text" class="input input-xlarge form-control" style="width:400px" id="Titulo" required value="<%=carrera.getTitulo()%>" size="40" maxlength="20">
        <br>
        <label for="NombreCarrera"><spring:message code="aca.Nombre"/></label>
        <input name="NombreCarrera" type="text" class="input input-xlarge form-control" style="width:400px" id="NombreCarrera" required value="<%=carrera.getNombreCarrera()%>" size="40" maxlength="80">
        <br>
       	<label for="NombreCorto"><spring:message code="aca.NombreCorto"/></label>
        <input name="NombreCorto" type="text" class="input input-xlarge form-control" style="width:400px" id="NombreCorto" required value="<%=carrera.getNombreCorto()%>" size="40" maxlength="15">
        <br>
        <%-- <label for="CcostoId"><spring:message code="catalogos.area.Costo"/></label>
        <input name="CcostoId" type="text" class="input input-xlarge form-control" style="width:200px" id="CcostoId" required value="<%=carrera.getCcostoId()%>" size="40" maxlength="20">
        <br> --%>
        <label for="CodigoPersonal"><spring:message code="aca.Codigo"/>:</label><br>
	    <select name="CodigoPersonal" class="form-select chosen" style="width:400px" required>
	    	<option value="0" <%=carrera.getCodigoPersonal().equals("1")?"selected":""%>>Choose</option>
<%		for (Maestros maestro : lisMaestros){ %>
			<option value="<%=maestro.getCodigoPersonal()%>" <%=maestro.getCodigoPersonal().equals(carrera.getCodigoPersonal())?"selected":""%>><%=maestro.getEstado()%>-<%=maestro.getCodigoPersonal()%> - <%=maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()+" "+maestro.getNombre()%></option>
<%		} %>	    	
	    </select>
		<br><br>
        <label for="GlSetNo">GLSETNO</label>
        <input name="GlSetNo" type="text" class="input input-xlarge form-control" style="width:200px" id="GlSetNo" value="<%=carrera.getGlsetno()%>" size="3" maxlength="3">
        <br>
        <label for="CostCentCd">COSTCENTCD</label>
        <input name="CostCentCd" type="text" class="input input-xlarge form-control" style="width:200px" id="CostCentCd" value="<%=carrera.getCostcentcd()%>" size="2" maxlength="2">
        <br>
        <label for="DscntPct">DSCNTPCT</label>
        <input name="DscntPct" type="text" class="input input-xlarge form-control" style="width:200px" id="DscntPct" value="<%=carrera.getDscntpct()%>" size="2" maxlength="2">
        <br>	       
	</div>  
	&nbsp;		
	<div class="alert alert-info">
		<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;
	</div>	
	</form>
<%
%>	
</div>
</body>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();	
</script>
</html>