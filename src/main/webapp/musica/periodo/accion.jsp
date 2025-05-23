<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Periodo" scope="page" class="aca.musica.MusiPeriodo"/>
<head>
	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
	<script type="text/javascript">
		
		function NuevoPeriodo(){			
			document.frmPeriodo.PeriodoId.value 		= "";
			document.frmPeriodo.PeriodoNombre.value 	= "";	
			document.frmPeriodo.FInicio.value 			= "";
			document.frmPeriodo.FFinal.value 			= "";
			document.frmPeriodo.CicloEscolar.value		= "0";
			document.frmPeriodo.Estado.value			= "0";
			document.frmPeriodo.NumPagare.value			= "0";
			document.frmPeriodo.costoPeriodo.value		= "0";
			document.frmPeriodo.Accion.value			= "1";
			document.frmPeriodo.submit();
		}
		
		function GrabarPeriodo(){
			
			if( document.frmPeriodo.PeriodoId.value !="" && document.frmPeriodo.PeriodoNombre.value!="" && 
				document.frmPeriodo.FInicio.value!="" && document.frmPeriodo.FFinal.value !="" && 
				document.frmPeriodo.CicloEscolar.value != ""){
				document.frmPeriodo.Accion.value = "2";
				document.frmPeriodo.submit();
				
			}else{
				alert("Complete el formulario ..! ");
			}
		}		
		
		function BorrarPeriodo( ){
		
			if(document.frmPeriodo.PeriodoId.value!=""){
				if(confirm("Estas seguro de eliminar el registro!") == true){
		  			document.frmPeriodo.Accion.value = "4";
					document.frmPeriodo.submit();
				}			
			}else{
				alert("Escriba la Clave !");
				document.frmPeriodo.PeriodoId.focus(); 
		  	}
		}
		
		function ConsultarPeriodo(){
			document.frmPeriodo.Accion.value="5";
			document.frmPeriodo.submit();		
		}
		
	</script>
</head>
<%
	// Declaracion de variables
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String resultado	= "";
	String fecha 		= "";
	
	if ( nAccion != 1 )
		Periodo.setPeriodoId(request.getParameter("PeriodoId"));
		
	// Operaciones a realizar en la pantalla
	switch (nAccion){
	
		case 1: { // Nuevo
			fecha = aca.util.Fecha.getHoy();			
			Periodo.setFInicio(fecha);
			Periodo.setFFinal(fecha);
			Periodo.setCicloEscolar("0");
			Periodo.setEstado("0");	
			Periodo.setNumPagare("0");
			Periodo.setCostoPagare("0");
			resultado	= "Llene el formulario correctamente ..¡¡";
			break;
		}
		
		case 2: { // Grabar
			Periodo.setPeriodoId(request.getParameter("PeriodoId"));
			Periodo.setPeriodoNombre(request.getParameter("PeriodoNombre"));			
			Periodo.setFInicio(request.getParameter("FInicio"));
			Periodo.setFFinal(request.getParameter("FFinal"));
			Periodo.setCicloEscolar(request.getParameter("CicloEscolar"));
			Periodo.setEstado(request.getParameter("Estado"));
			Periodo.setNumPagare(request.getParameter("NumPagare"));
			Periodo.setCostoPagare(request.getParameter("costoPagare"));
			
			if (Periodo.existeReg(conEnoc) == false){
				if (Periodo.insertReg(conEnoc)){
					resultado = "Grabado: "+Periodo.getPeriodoId();					
				}else{
					resultado = "No Grabó: "+Periodo.getPeriodoId();
				}
			}else{
				if (Periodo.updateReg(conEnoc)){
					resultado = "Modificado: "+Periodo.getPeriodoId();					
				}else{
					resultado = "No Cambió: "+Periodo.getPeriodoId();
				}				
			}			
			break;
		}
		
		case 4: { // Borrar
			if (Periodo.existeReg(conEnoc) == true){
				if (Periodo.deleteReg(conEnoc)){
					resultado = "Borrado: "+Periodo.getPeriodoId();					
					response.sendRedirect("periodo");
				}else{
					resultado = "No Borró: "+Periodo.getPeriodoId();
				}	
			}else{
				resultado = "No existe: "+Periodo.getPeriodoId();
			}
			break;
		}
		case 5: { // Consultar			
			if (Periodo.existeReg(conEnoc) == true){
				Periodo.mapeaRegId(conEnoc, request.getParameter("PeriodoId"));				
				resultado = "Consulta";
			}else{
				resultado = "No existe: "+Periodo.getPeriodoId();
			}	
			break;
		}
	}	
%>
<body>
<div class="container-fluid">
	<h2>Catálogo de Periodos</h2>
	<form action="accion" method="post" name="frmPeriodo" target="_self">
	<input type="hidden" name="Accion"> 
	<div class="alert alert-info">
		 <a class="btn btn-primary" href="periodo"><spring:message code="aca.Regresar"/></a>
	</div>
<table style="width:50%" class="table">
  <tr>
    <td>
	  <table style="width:100%" >
          <tr> 
            <td width="15%"><strong><spring:message code="aca.Clave"/>:</strong></td>
            <td width="76%">
			<input name="PeriodoId" type="text" id="PeriodoId" size="8" maxlength="7" value="<%=Periodo.getPeriodoId()%>">
			</td>			
          </tr>
          <tr> 
            <td><strong><spring:message code="aca.Nombre"/>:</strong></td>
            <td><input name="PeriodoNombre" type="text" id="PeriodoNombre" size="30" maxlength="30" value="<%=Periodo.getPeriodoNombre()%>"></td>
          </tr>		  
		  <tr> 
            <td><strong><spring:message code="aca.FechaInicio"/>:</strong></td>
            <td>
			  <input name="FInicio" type="text" id="FInicio" data-date-format="dd/mm/yyyy" size="10" maxlength="10" value="<%=Periodo.getFInicio()%>">
              dd/mm/yyyy 
			</td>
          </tr>
		  <tr> 
            <td><strong><spring:message code="aca.FechaFinal"/>:</strong></td>
            <td>
			  <input name="FFinal" type="text" id="FFinal" data-date-format="dd/mm/yyyy" size="10" maxlength="10" value="<%=Periodo.getFFinal()%>">
              dd/mm/yyyy 
			</td> 
          </tr>
		  <tr> 
            <td><strong>Ciclo Escolar:</strong></td>
            <td><input name="CicloEscolar" type="text" id="CicloEscolar" size="4" maxlength="4" value="<%=Periodo.getCicloEscolar()%>"></td>
          </tr>
		  <tr> 
            <td><strong><spring:message code="aca.Estado"/>:</strong></td>
            <td><select name="Estado">
				<% if(Periodo.getEstado().equals("1")){%>
	                <option value='1' selected><spring:message code='aca.Activo'/></option>
	                <option value='0' ><spring:message code='aca.Inactivo'/></option>
	            <%}else{ %>
	                <option value='1'><spring:message code='aca.Activo'/></option>
	                <option value='0' selected><spring:message code='aca.Inactivo'/></option>
	            <%} %> 
              </select>
			</td>			
          </tr>
          <tr> 
            <td><strong>N° Pagar&eacute;:</strong></td>
            <td>
              <select name="NumPagare">            	
                <option value='1' <%= Periodo.getNumPagare().equals("1")?"Selected":" " %>>1</option>
                <option value='2' <%= Periodo.getNumPagare().equals("2")?"Selected":" " %>>2</option>
                <option value='3' <%= Periodo.getNumPagare().equals("3")?"Selected":" " %>>3</option>
            
              </select>
			</td>			
          </tr>
          
          <tr> 
            <td><strong><spring:message code="aca.Costo"/> Pagare:</strong></td>
            <td><input name="costoPagare" type="text" id="costoPagare" size="8" maxlength="8" value="<%=Periodo.getCostoPagare() %>"></td>
          </tr>	
          <tr> 
            <td colspan="2" align="center"><%=resultado%></td>
          </tr>
          <tr>
            <td colspan="2" style="text-align:left;">
            	<input class="btn btn-primary" type="button" value="Nuevo" id="Nuevo" onclick="javascript:NuevoPeriodo()" style="cursor:pointer" />
            	<input class="btn btn-primary" type="button" value="Grabar" id="Grabar" onclick="javascript:GrabarPeriodo()" style="cursor:pointer" />
            	<input class="btn btn-primary" type="button" value="Borrar" id="Borrar" onclick="javascript:BorrarPeriodo()" style="cursor:pointer" />
			</td>
          </tr>
        </table>
	</td>
  </tr>
</table>
</form>
</body>
<script>
	jQuery('#FInicio').datepicker();
	jQuery('#FFinal').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>