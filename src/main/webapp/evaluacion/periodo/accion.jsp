<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Periodo" scope="page" class="aca.edo.EdoPeriodo"/>
<jsp:useBean id="PeriodoU" scope="page" class="aca.edo.EdoPeriodoUtil"/>

<script type="text/javascript">
	
	function Nuevo(){
		var fecha = new Date();
		var dia = fecha.getDate();
		var año = fecha.getYear();
		var mes = fecha.getMonth() + 1;
		
		if (año<2000) año = año + 1900;
		document.frmPeriodo.PeriodoId.value 	= "";
		document.frmPeriodo.PeriodoNombre.value = "";				
		document.frmPeriodo.fInicio.value 		= "";
		document.frmPeriodo.fFinal.value 		= "";
		document.frmPeriodo.Estado.value		= "0";
		document.frmPeriodo.Accion.value		= "1";
		document.frmPeriodo.submit();
	}
	
	function Grabar(){
		if(document.frmPeriodo.PeriodoId.value!="" && document.frmPeriodo.PeriodoNombre!=""){
			document.frmPeriodo.Accion.value="2";
			document.frmPeriodo.submit();
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Modificar(){
		document.frmPeriodo.Accion.value="3";
		document.frmPeriodo.submit();
	}
	
	function Borrar( ){
		if(document.frmPeriodo.PeriodoId.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.frmPeriodo.Accion.value="4";
				document.frmPeriodo.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmPeriodo.PeriodoId.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmPeriodo.Accion.value="5";
		document.frmPeriodo.submit();		
	}
	
</script>
<%
	// Declaracion de variables
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String sResultado	= "";
	String fecha 		= "";
	String date			= "";
	String mm			= "";
	String dd			= "";
	String FInicio		= request.getParameter("date-1-dd")+"/"+request.getParameter("date-1-mm")+"/"+request.getParameter("date-1");
	String FFinal		= request.getParameter("date-2-dd")+"/"+request.getParameter("date-2-mm")+"/"+request.getParameter("date-2");	
	if ( nAccion != 1 )
		Periodo.setPeriodoId(request.getParameter("PeriodoId"));
		
	// Operaciones a realizar en la pantalla
	switch (nAccion){
		case 1: { // Nuevo
			fecha 		= aca.util.Fecha.getHoy();
			Periodo.setFInicio(fecha);			
			sResultado	= "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar
			Periodo.setPeriodoNombre(request.getParameter("PeriodoNombre"));			
			Periodo.setPeriodoId(request.getParameter("PeriodoId"));			
			Periodo.setFInicio(request.getParameter("fInicio"));
			Periodo.setFFinal(request.getParameter("fFinal"));			
			Periodo.setEstado(request.getParameter("Estado"));
			
			if (PeriodoU.existeReg(conEnoc, Periodo.getPeriodoId()) == false){
				//System.out.println("Antes del insert Reg... en el jsp");
				if (PeriodoU.insertReg(conEnoc, Periodo)){
					sResultado = "Grabado: "+Periodo.getPeriodoId();					
				}else{
					sResultado = "No Grabó: "+Periodo.getPeriodoId();
				}
			}else{
				if (PeriodoU.updateReg(conEnoc, Periodo)){
					sResultado = "Modificado: "+Periodo.getPeriodoId();					
				}else{
					sResultado = "No Cambió: "+Periodo.getPeriodoId();
				}
			}
			
			break;
		}
		case 4: { // Borrar
			if (PeriodoU.existeReg(conEnoc, Periodo.getPeriodoId()) == true){
				if (PeriodoU.deleteReg(conEnoc, Periodo.getPeriodoId())){
					sResultado = "Borrado: "+Periodo.getPeriodoId();					
				}else{
					sResultado = "No Borró: "+Periodo.getPeriodoId();
				}	
			}else{
				sResultado = "No existe: "+Periodo.getPeriodoId();
			}
			break;
		}
		case 5: { // Consultar			
			if (PeriodoU.existeReg(conEnoc, Periodo.getPeriodoId()) == true){
				Periodo.mapeaRegId(conEnoc, request.getParameter("PeriodoId"));				
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+Periodo.getPeriodoId();
			}	
			break;
		}
	}	
%>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<div class="container-fluid">
	<h2>Periodos de evaluación<small class="text-muted fs-5"> ( <%=sResultado%> ) </small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="periodo"><spring:message code="aca.Regresar"/></a>
	</div>
	<form action="accion" method="post" name="frmPeriodo" target="_self">
		<input type="hidden" name="Accion">
		<div class="row">
			<div class="col-3">
				<label for="PeriodoId"><spring:message code="aca.Clave"/>:</label>			
				<input name="PeriodoId" type="text" class="form-control" id="PeriodoId" size="6" maxlength="6" value="<%=Periodo.getPeriodoId()%>">
				<br>
				<label for="PeriodoNombre"><spring:message code="aca.Nombre"/>:</label>					
				<input name="PeriodoNombre" type="text" class="form-control" id="PeriodoNombre" size="30" maxlength="30" value="<%=Periodo.getPeriodoNombre()%>">
				<br>
				<label for="fInicio">Fecha. Inicio(DD/MM/YYYY):</label>
				<input type="text" class="form-control" id="fInicio" data-date-format="dd/mm/yyyy" name="fInicio" value="<%=Periodo.getFInicio() %>" maxlength="10" />	        	
				<br>
				<label for="fFinal"><spring:message code="aca.FechaFinal"/>(DD/MM/YYYY):</label>					
				<input type="text" class="form-control" id="fFinal" data-date-format="dd/mm/yyyy" name="fFinal" value="<%=Periodo.getFFinal() %>" maxlength="10" />	          	
				<br>
				<label for="Estado"><spring:message code="aca.Estado"/>:</label>
				<select name="Estado" class="form-select">
			<%  if(Periodo.getEstado().equals("A")){%>
	                <option value='A' selected><spring:message code='aca.Activo'/></option>
	                <option value='I' ><spring:message code='aca.Inactivo'/></option>
            <%  }else{ %>
	                <option value='A'><spring:message code='aca.Activo'/></option>
	                <option value='I' selected><spring:message code='aca.Inactivo'/></option>
            <%  } %>
              </select>
              <br>             
			</div>
		</div>
		<br>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> &nbsp;<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp; 
			<a class="btn btn-primary" href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a>			  
			<input name="FInicio" type="hidden" value="<%=FInicio%>">
			<input name="FFinal" type="hidden" value="<%=FFinal%>">		
		</div>
	</form>
</div>
<script>
	jQuery('#fInicio').datepicker();
	jQuery('#fFinal').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>