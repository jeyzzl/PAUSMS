<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Cobro" scope="page" class="aca.musica.MusiPeriodoCobro"/>
<jsp:useBean id="CuentaU" scope="page" class="aca.musica.MusiCuentaUtil"/>
<jsp:useBean id="InstrumentoU" scope="page" class="aca.musica.MusiInstrumentoUtil"/>
<jsp:useBean id="MaestroU" scope="page" class="aca.musica.MusiMaestroUtil"/>

<head>
<script type="text/javascript">
	
	function Nuevo(){		
		document.frmcarga.CuentaId.value 	= "";
		document.frmcarga.Cantidad.value	= "0";
		document.frmcarga.Comentario.value	= "-";
		document.frmcarga.Accion.value		= "1";
		document.frmcarga.submit();
	}
	
	function Grabar(){
		if(document.frmcarga.CuentaId.value!=""){
			document.frmcarga.Accion.value="2";
			document.frmcarga.submit();
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Borrar( ){
		if(document.frmcarga.CuentaId.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.frmcarga.Accion.value="4";
				document.frmcarga.submit();
			}			
		}else{
			alert("Selecciona la Cuenta !");
			document.frmcarga.CuentaId.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmcarga.Accion.value="5";
		document.frmcarga.submit();		
	}
	
</script>
</head>
<%
	// Declaracion de variables
	String periodoId		= request.getParameter("PeriodoId");
	String maestroId		= request.getParameter("MaestroId")==null?"X":request.getParameter("MaestroId");
	String cuentaId			= request.getParameter("CuentaId")==null?"X":request.getParameter("CuentaId");
	int nAccion				= Integer.parseInt(request.getParameter("Accion"));
	String color 			= "";
	String tipo				= "";
	String resultado 		= "";
	
	if ( nAccion != 1 ){	
		Cobro.setPeriodoId(periodoId);
		Cobro.setCuentaId(cuentaId);
	}

	// Operaciones a realizar en la pantalla
	switch (nAccion){
		case 1: { // Nuevo						
			resultado	= "Llene el formulario correctamente ..¡¡";
			break;
		}
		case 2: { // Grabar
			Cobro.setPeriodoId(periodoId);
			Cobro.setCuentaId(cuentaId);
			Cobro.setCantidad(request.getParameter("Cantidad")==null?"0":request.getParameter("Cantidad"));
			Cobro.setComentario(request.getParameter("Comentario"));
			
			if (Cobro.existeReg(conEnoc) == false){	
				if (Cobro.insertReg(conEnoc)){
					resultado = "Grabado: "+Cobro.getCuentaId();
				}else{
					resultado = "Error al grabar..! : "+Cobro.getCuentaId();
				}
			}else{
				if (Cobro.updateReg(conEnoc)){
					resultado = "Modificado: "+Cobro.getCuentaId();					
				}else{
					resultado = "Error al modificar: "+Cobro.getCuentaId();
				}
				
			}
			
			break;
		}
		case 4: { // Borrar
			if (Cobro.existeReg(conEnoc) == true){
				if (Cobro.deleteReg(conEnoc)){
					resultado = "Borrado: "+Cobro.getCuentaId();					
				}else{
					resultado = "No Borró: "+Cobro.getCuentaId();
				}	
			}else{
				resultado = "No existe: "+Cobro.getCuentaId();
			}
			break;
		}
		case 5: { // Consultar
			if (Cobro.existeReg(conEnoc) == true){
				Cobro.mapeaRegId(conEnoc, periodoId, cuentaId);
				resultado = "Consulta";
			}else{
				resultado = "No existe: "+Cobro.getCuentaId();
			}	
			break;
		}
	}
	
	ArrayList lisCuenta		= CuentaU.getListAll(conEnoc,"ORDER BY TIPO, CUENTA_ID");		
%>
<body>
<div class="container-fluid">
	<h2>Captura de Costos de Enseñanza (<td align="center"><font size="3">Periodo: <%= aca.musica.MusiPeriodo.getPeriodoNombre(conEnoc, periodoId) %></font></td>)</h2>
	<form action="accion" method="post" name="frmcarga" target="_self">
	<input type="hidden" name="PeriodoId" value="<%=periodoId%>">
	<input type="hidden" name="Accion"> 
	<div class="alert alert-info">
		<a class="btn btn-primary" href="costos?PeriodoId=<%=periodoId%>"><spring:message code="aca.Regresar"/></a>
	</div>
<table style="width:50%" class="table">  
  <tr>
    <td>
	  <table style="width:100%" >
          <tr> 
            <td width="15%"><strong>Cuenta:</strong></td>
            <td width="76%">			
			  <select name="CuentaId">
<%
	for (int i=0; i< lisCuenta.size(); i++){
		aca.musica.MusiCuenta cuenta = (aca.musica.MusiCuenta) lisCuenta.get(i);
		if ( !tipo.equals(cuenta.getTipo()) ){
			if (color.equals("#CCCCCC")) color = "white"; else color = "#CCCCCC";
			tipo = cuenta.getTipo();
		}
%>
			<option style="background-color:<%=color%>;"<%if( cuentaId.equals( cuenta.getCuentaId() )) out.print(" Selected ");%> value="<%= cuenta.getCuentaId() %>">[<%= cuenta.getCuentaId() %>] <%= cuenta.getCuentaNombre() %></option>
<%	} %>
		  </select>
			</td>			
          </tr>
          <tr> 
            <td><strong>Cantidad:</strong></td>
            <td><input name="Cantidad" type="text" class="text" id="Cantidad" size="5" maxlength="7" value="<%=Cobro.getCantidad()%>"></td>
          </tr>          
		  <tr> 
            <td><strong><spring:message code="aca.Comentario"/>:</strong></td>
            <td><input name="Comentario" type="text" class="text" id="Comentario" size="30" maxlength="70" value="<%=Cobro.getComentario() %>"></td>
          </tr>	  
          <tr> 
            <td colspan="2"  style="text-align:left;"><%=resultado%></td>
          </tr>
          <tr>
            <th colspan="2" style="text-align:left;">
			  <a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> &nbsp;
			  <a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp;
			  <a class="btn btn-primary" href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a> &nbsp;
			</th>
          </tr>
        </table>
	</td>
  </tr>
</table>
</form>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>