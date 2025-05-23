<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="reporte" scope="page" class="aca.disciplina.CondReporte"/>
<jsp:useBean id="reporteU" scope="page" class="aca.disciplina.CondReporteUtil"/>

<script type="text/javascript">
	
	function Nuevo(){		
		document.frmreporte.IdReporte.value	= " ";
		document.frmreporte.fNombre.value	= " ";
		document.frmreporte.Tipo.value		= " ";
		document.frmreporte.Accion.value	="1";
		document.frmreporte.submit();		
	}
	
	function Grabar(){
		if(document.frmreporte.IdReporte.value!="" && document.frmreporte.fNombre.value!=""  && document.frmreporte.Tipo.value!=""){			
			document.frmreporte.Accion.value="2";
			document.frmreporte.submit();			
		}else{
			alert("Fill out the entire form");
		}
	}
	
	function Modificar(){
		document.frmreporte.Accion.value="3";
		document.frmreporte.submit();
	}
	
	function Borrar( ){
		if(document.frmreporte.IdReporte.value!=""){
			if(confirm("Are you sure you want to delete this records?")==true){
	  			document.frmreporte.Accion.value="4";
				document.frmreporte.submit();
			}			
		}else{
			alert("Input the ID");
			document.frmreporte.IdReporte.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmreporte.Accion.value="5";
		document.frmreporte.submit();		
	}
	
</script>

<%	String sResultado		= "";
	String sTipo			= request.getParameter("Tipo");
	int nAccion				= Integer.parseInt(request.getParameter("Accion"));
	
	if ( nAccion == 1 )	{ reporte.setIdReporte(reporteU.maximoReg(conEnoc));
	} else { 
		reporte.setIdReporte(request.getParameter("IdReporte"));
		reporte.mapeaRegId(conEnoc, reporte.getIdReporte());
	}
	
	if(nAccion != 1 && nAccion != 4) { sTipo = reporte.getTipo(); } else { sTipo = "N"; }
	
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			//sResultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar
			reporte.setNombre(request.getParameter("fNombre"));
			reporte.setTipo(request.getParameter("Tipo"));
			if (reporteU.existeReg(conEnoc, request.getParameter("IdReporte")) == false){
				if (reporteU.insertReg(conEnoc, reporte)){
					sResultado = "Saved: "+reporte.getIdReporte();					
				}else{
					sResultado = "Error saving: "+reporte.getIdReporte();
				}
			}else{
				if (reporteU.updateReg(conEnoc, reporte)){
					sTipo = reporte.getTipo();
					sResultado = "Updated: "+reporte.getIdReporte();					
				}else{
					sResultado = "Error updating: "+reporte.getIdReporte();
				}
			}
			break;
		}
		case 4: { // Borrar
			if (reporteU.existeReg(conEnoc, request.getParameter("IdReporte")) == true){
				if (reporteU.deleteReg(conEnoc, request.getParameter("IdReporte"))){
					sResultado = "Deleted: "+reporte.getIdReporte();					
					reporte.setIdReporte(reporteU.maximoReg(conEnoc));
					reporte.setNombre("");
				}else{
					sResultado = "Error deleting: "+reporte.getIdReporte();
				}	
			}else{
				sResultado = "Not found: "+reporte.getIdReporte();
			}
			break;
		}
		case 5: { // Consultar
			if (reporteU.existeReg(conEnoc, request.getParameter("IdReporte")) == true){
				reporte.mapeaRegId(conEnoc, reporte.getIdReporte());
				sResultado = "Query";
			}else{
				sResultado = "Not found: "+reporte.getIdReporte(); 
			}	
			break;			
		}
	}
%>
<div class="container-fluid">
	<h2>Report Type Catalog</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="reportes"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="frmreporte" method="post" action="grabar">
		<input type="hidden" name="Accion">
		<div class="row">
			<div class="span3">
				<label for="IdReporte">Id:</label>			
				<input name="IdReporte" type="text" class="text form-control" style="width:50px" id="IdReporte" value="<%=reporte.getIdReporte()%>" size="2" maxlength="2" >
				<br><br>
				<label for="fNombre"><spring:message code="aca.Nombre"/>:</label>			
				<input name="fNombre" type="text" class="text form-control" style="width:500px" value="<%=reporte.getNombre()%>" size="42" maxlength="50">
				<br><br>
				<label for="fNombre"><spring:message code="aca.Tipo"/></label>	
				<select name="Tipo" id="Tipo" class="form-select" style="width:100px">
<%
				if(sTipo.equals("N")){
%>		
					<option value="D">Misconduct</option>
					<option value="C">Praise</option>
					<option value="N" selected>Other</option>
<%
				}else if (sTipo.equals("C")){
%>
					<option value="D">Misconduct</option>
					<option value="C" selected>Praise</option>
					<option value="N">Other</option>	
<%
				}else{
%>		
					<option value="D" selected>Misconduct</option>
			        <option value="C">Praise</option>
					<option value="N">Other</option>
<%		
				}
%> 
        </select>		
			</div>
		</div>
		<br>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> 
            &nbsp;<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a>
            &nbsp; <a class="btn btn-primary" href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a>
		</div>
	</form>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>