<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<jsp:useBean id="Instrumento" scope="page" class="aca.musica.MusiInstrumento"/>


<script type="text/javascript">
	
	function Nuevo()	{		
		document.frmInstrumento.InstrumentoId.value 	  = " ";
		document.frmInstrumento.InstrumentoNombre.value   = " ";
		document.frmInstrumento.Tipo.value 	              = " ";
		document.frmInstrumento.Accion.value              = "1";
		document.frmInstrumento.submit();		
	}
	
	function Grabar(){
		if(document.frmInstrumento.InstrumentoId.value!="" && document.frmInstrumento.InstrumentoNombre!="" && document.frmInstrumento.Tipo!="" ){			
			document.frmInstrumento.Accion.value="2";
			document.frmInstrumento.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function Borrar( ){
		if(document.frmInstrumento.InstrumentoId.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.frmInstrumento.Accion.value="4";
				document.frmInstrumento.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmInstrumento.InstrumentoId.focus(); 
	  	}
	}
	
</script>
<%
	// Declaracion de variables	
	int nAccion			= Integer.parseInt(request.getParameter("Accion"));
	String sResultado   = "";
	
	if ( nAccion == 1 )
		Instrumento.setInstrumentoId(Instrumento.maximoReg(conEnoc));
	else
		Instrumento.setInstrumentoId(request.getParameter("InstrumentoId"));
		
	// Operaciones a realizar en la pantalla	
	switch (nAccion){
		case 1: { // Nuevo			
			sResultado = "Llene el formulario correctamente ..¡¡";
			break;
		}		
		case 2: { // Grabar la cuenta
			Instrumento.setInstrumentoId(request.getParameter("InstrumentoId"));
			Instrumento.setInstrumentoNombre(request.getParameter("InstrumentoNombre"));
			Instrumento.setTipo(request.getParameter("Tipo"));
			if (Instrumento.existeReg(conEnoc) == false){
				if (Instrumento.insertReg(conEnoc)){
					sResultado = "Grabado: "+Instrumento.getInstrumentoId();					
				}else{
					sResultado = "No Grabó: "+Instrumento.getInstrumentoId();
				}
			}else{				
				if (Instrumento.updateReg(conEnoc)){
					sResultado = "Modificado: "+Instrumento.getInstrumentoId();					
				}else{
					sResultado = "No Cambió: "+Instrumento.getInstrumentoId();
				}
			}
			
			break;
		}		
		case 4: { // Borrar
			if (Instrumento.existeReg(conEnoc) == true){
					if (Instrumento.deleteReg(conEnoc)){
						sResultado = "Borrado: "+Instrumento.getInstrumentoId();						
					}else{
						sResultado = "No Borró: "+Instrumento.getInstrumentoId();
					}						
			}else{
				sResultado = "No existe: "+Instrumento.getInstrumentoId();
			}
			break;
		}
		case 5: { // Consultar
			if (Instrumento.existeReg(conEnoc) == true){
				Instrumento.mapeaRegId(conEnoc, request.getParameter("InstrumentoId"));
				sResultado = "Consulta";
			}else{
				sResultado = "No existe: "+Instrumento.getInstrumentoId();
			}
			break;
		}
	}	
	
%>
<html>
<body>
<div class="container-fluid">
	<h1>Catalogo de Cuentas</h1>
	<form action="accion" method="post" name="frmInstrumento" target="_self">
	<input type="hidden" name="Accion"> 
	<div class="alert alert-info">
		<a class="btn btn-primary" href="instrumento"><spring:message code="aca.Regresar"/></a>
	</div>
		<div class="form-group">
          	<label for="aca.Clave">Clave</label>
          	<input name="InstrumentoId" type="text" class="input input-mini" id="InstrumentoId" size="3" maxlength="2" required value="<%=Instrumento.getInstrumentoId()%>">			
        	<br><br> 
          	<label for="aca.Nombre">Nombre</label>
          	<input name="InstrumentoNombre" type="text" class="input input-xlarge" id="InstrumentoNombre" size="40" maxlength="40" required value="<%=Instrumento.getInstrumentoNombre()%>">
        	<br><br> 
	     	<label for="aca.Tipo">Tipo</label>
          	<input name="Tipo" type="text" class="text" id="Tipo" size="3" maxlength="1" value="<%=Instrumento.getTipo()%>">
          	<br><br> 
         	<%=sResultado%>
       	</div> 	
          	<div class="alert alert-info">
		  		<a class="btn btn-primary" href="javascript:Nuevo()"><spring:message code='aca.Nuevo'/></a> &nbsp;
		  		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp;		   
		  		<a class="btn btn-primary" href="javascript:Borrar()"><spring:message code='aca.Borrar'/></a> &nbsp;
		  	</div>
</form>
</div>
</body>
</html>
<%@ include file= "../../cierra_enoc.jsp" %>