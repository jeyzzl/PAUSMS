<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Cuenta" scope="page" class="aca.musica.MusiCuenta"/>
<jsp:useBean id="tipoU" scope="page" class="aca.musica.MusiTipoCtaUtil"/>
<jsp:useBean id="InstrumentoU" scope="page" class="aca.musica.MusiInstrumentoUtil"/>

<script type="text/javascript">
	
	function Nuevo()	{		
		document.frmCuenta.CuentaId.value 		= " ";
		document.frmCuenta.CuentaNombre.value 	= " ";
		document.frmCuenta.Tipo.value 	= " ";
		document.frmCuenta.Accion.value="1";
		document.frmCuenta.submit();		
	}
	
	function Grabar(){		
		if(document.frmCuenta.CuentaId.value != "" && document.frmCuenta.CuentaNombre.value != "" && document.frmCuenta.Tipo.value != "" ){			
			document.frmCuenta.Accion.value = "2";
			document.frmCuenta.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Borrar( ){
		if(document.frmCuenta.CuentaId.value!=""){
			if(confirm("Estas seguro de eliminar el registro!") == true){
	  			document.frmCuenta.Accion.value = "4";
				document.frmCuenta.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmCuenta.CuentaId.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmCuenta.Accion.value="5";
		document.frmCuenta.submit();		
	}
	
</script>
<%
	// Declaracion de variables	
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String sResultado		= "";
	
	if ( nAccion == 1 )
		Cuenta.setCuentaId(Cuenta.maximoReg(conEnoc));
	else
		Cuenta.setCuentaId(request.getParameter("CuentaId"));
		
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar la cuenta
			Cuenta.setCuentaNombre(request.getParameter("CuentaNombre"));
			Cuenta.setTipo(request.getParameter("Tipo"));
			Cuenta.setInstrumentoId(request.getParameter("Instrumento"));
			if (Cuenta.existeReg(conEnoc) == false){
				if (Cuenta.insertReg(conEnoc)){
					sResultado = "Grabado: "+Cuenta.getCuentaId();					
				}else{
					sResultado = "No Grabó: "+Cuenta.getCuentaId();
				}
			}else{
				if (Cuenta.updateReg(conEnoc)){
					sResultado = "Modificado: "+Cuenta.getCuentaId();
				}else{
					sResultado = "No Cambió: "+Cuenta.getCuentaId();
				}
			}
			
			break;
		}		
		case 4: { // Borrar
			if (Cuenta.existeReg(conEnoc) == true){
				if ( aca.musica.MusiMovimiento.numMovtosCuenta(conEnoc, Cuenta.getCuentaId()) == 0 ){
					if (Cuenta.deleteReg(conEnoc)){
						sResultado = "Borrado: "+Cuenta.getCuentaId();						
					}else{
						sResultado = "No Borró: "+Cuenta.getCuentaId();
					}
				}else{
					sResultado = "No es posible borrar la cuenta "+Cuenta.getCuentaId()+" porque tiene movimientos registrados.!";
				}				
			}else{
				sResultado = "No existe: "+Cuenta.getCuentaId();
			}
			break;
		}
		case 5: { // Consultar
			if (Cuenta.existeReg(conEnoc) == true){
				Cuenta.mapeaRegId(conEnoc, request.getParameter("CuentaId"));
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+Cuenta.getCuentaId();
			}
			break;
		}
	}	
	ArrayList<aca.musica.MusiTipoCta> lisTipo	= tipoU.getListAll(conEnoc,"TIPOCTA_ID");
	ArrayList<aca.musica.MusiInstrumento> lisInstrumento = InstrumentoU.getListAll(conEnoc, "ORDER BY 1");
%>
<html>
<body>
<div class="container-fluid">
	<h2>Catalogo de Cuentas</h2>
	<form action="accion" method="post" name="frmCuenta" target="_self">
	<input type="hidden" name="Accion"> 
	<div class="alert alert-info">
		 <a class="btn btn-primary" href="cuenta"><spring:message code="aca.Regresar"/></a>  
	</div>
<table style="width:50%" class="table table-nohover">
  <tr>
    <td>
	  <table style="width:100%" >
        <tr> 
          <td width="15%"><strong><spring:message code="aca.Clave"/>:</strong></td>
          <td width="76%"><input name="CuentaId" type="text" class="text" id="CuentaId" size="3" maxlength="3" value="<%=Cuenta.getCuentaId()%>" readonly></td>			
        </tr>
        <tr> 
          <td><strong><spring:message code="aca.Nombre"/>:</strong></td>
          <td><input name="CuentaNombre" type="text" class="text" id="CuentaNombre" size="40" maxlength="40" value="<%=Cuenta.getCuentaNombre()%>"></td>
        </tr>
        <tr>
	      <td><strong><spring:message code="aca.Tipo"/>:</strong></td>
          <td>
			<select name="Tipo" id="Tipo">
<%
	for (int j=0; j<lisTipo.size(); j++){
		aca.musica.MusiTipoCta tipoCta = (aca.musica.MusiTipoCta) lisTipo.get(j);
%>
			<option <%if( Cuenta.getTipo().equals(tipoCta.getTipoCtaId())) out.print(" Selected ");%> value="<%= tipoCta.getTipoCtaId() %>"><%= tipoCta.getTipoCtaNombre() %></option>
<%	}%>
            </select>
		  </td>
		  </tr>
		  <tr>
		  <td><strong>Instrumento:</strong></td>
          <td>
			<select name="Instrumento" id="Instrumento">
<%
	for (int j=0; j<lisInstrumento.size(); j++){
		aca.musica.MusiInstrumento ins = (aca.musica.MusiInstrumento) lisInstrumento.get(j);
%>
			<option <%if( Cuenta.getInstrumentoId().equals(ins.getInstrumentoId())) out.print(" Selected ");%> value="<%= ins.getInstrumentoId() %>"><%= ins.getInstrumentoNombre() %></option>
<%	}%>
            </select>
		  </td>
		</tr>        
        <tr> 
          <td colspan="2" style="text-align:left;"><%=sResultado%></td>
        </tr>
        <tr>
          <th colspan="2" style="text-align:left;"> 
		  <a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> &nbsp;
		  <a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp;		   
		  <a class="btn btn-primary" href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a> &nbsp;
		  <a class="btn btn-primary" href="javascript:Consultar()"><spring:message code='aca.Consultar'/></a>
		  </th>
        </tr>
      </table>
	</td>
  </tr>
</table>
</form>
</div>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>