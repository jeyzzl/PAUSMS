<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Maestro" scope="page" class="aca.musica.MusiMaestro"/>
<jsp:useBean id="MaestroU" scope="page" class="aca.musica.MusiMaestroUtil"/>
<head>
  	<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
	<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
  <script type="text/javascript">
	
	function nuevo()	{		
		document.frmAlta.Nombre.value 		   = " ";
		document.frmAlta.FNacimiento.value 	   = " ";
		document.frmAlta.Paterno.value 	   	   = " ";
		document.frmAlta.Materno.value  	   = " ";
		document.frmAlta.Telefono.value 	   = " ";
		document.frmAlta.Celular.value 	       = " ";
		document.frmAlta.Correo.value 		   = " ";
		document.frmAlta.MaestroId.value 	   = " ";
		document.frmAlta.Accion.value="1";
		document.frmAlta.submit();		
	}
	
	function grabar(){
		if(document.frmAlta.Nombre.value!="" && document.frmAlta.FNacimiento.value!="" && document.frmAlta.Paterno.value!=""
		 && document.frmAlta.Materno.value!="" && document.frmAlta.Telefono.value!="" && document.frmAlta.Correo.value!=""  
		 && document.frmAlta.MaestroId.value!="" && document.frmAlta.Celular.value!=""){			
			document.frmAlta.Accion.value="2";
			document.frmAlta.submit();			
		}else{
			alert("Complete el formulario ..! ");
		}
	}
	
	function borrar( ){
		if(document.frmAlta.MaestroId.value!=""){
			if(confirm("Estás seguro de eliminar el registro!")==true){
	  			document.frmAlta.Accion.value="3";
				document.frmAlta.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.frmAlta.MaestroId.focus(); 
	  	}
	}

		
</script>
  
</head>
<% 
	String accion 			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");		
	int numAccion			= Integer.parseInt(accion);	
    String sResultado        = "";
    
	if ( numAccion==1){	
		Maestro.setMaestroId(Maestro.maximoReg(conEnoc));
	}else{
		Maestro.setMaestroId(request.getParameter("MaestroId"));
	}
	
	ArrayList lisMaestro = null;
	
//operaciones a realizar	
    switch (numAccion){

	    case 1: { // Nuevo			
	    	sResultado = "Llene el formulario correctamente ..¡¡";
				break;
		}		
		
		case 2: { // Grabar
			Maestro.setMaestroId(request.getParameter("MaestroId"));
			Maestro.setNombre(request.getParameter("Nombre"));
			Maestro.setApellidoMaterno(request.getParameter("Materno"));
			Maestro.setApellidoPaterno(request.getParameter("Paterno"));
			Maestro.setCorreo(request.getParameter("Correo"));
			Maestro.setFNacimiento(request.getParameter("FNacimiento"));
			Maestro.setTelefono(request.getParameter("Telefono"));
			Maestro.setCelular(request.getParameter("Celular"));
			if (Maestro.existeReg(conEnoc) == false){
				if (Maestro.insertReg(conEnoc)){
					sResultado = "Grabado: "+Maestro.getMaestroId();					
				}else{
					sResultado = "No Grabó: "+Maestro.getMaestroId();
				}
			}else{				
				if (Maestro.updateReg(conEnoc)){
					sResultado = "Modificado: "+Maestro.getMaestroId();					
				}else{
					sResultado = "No Cambió: "+Maestro.getMaestroId();
				}
			}
			
			break;			
		}
		case 3:{//borrar
			
			if (Maestro.existeReg(conEnoc) == true){
				if (Maestro.deleteReg(conEnoc)){
					sResultado = "Borrado: "+Maestro.getMaestroId();					
				}else{
					sResultado = "No Borró: "+Maestro.getMaestroId();
				}	
			}else{
					sResultado = "No existe: "+Maestro.getMaestroId();
			}
			break;
		}
		
		case 4:{ //Consultar
			
			if (Maestro.existeReg(conEnoc) == true){
				Maestro.mapeaRegId(conEnoc,Maestro.getMaestroId());
			}else{
				sResultado = "No existe: "+Maestro.getMaestroId();
			}
			break;
		}
		
						
			
	}  
	lisMaestro = MaestroU.getListAll(conEnoc, "ORDER BY MAESTRO_ID");

%>
<body>
<div class="container-fluid">
<h1>Alta Maestro</h1>
<form action="maestro" method="post" name="frmAlta" target="_self">
<input type="hidden" name="Accion">
<table style="width:70%" class="table table-condensed">
  <tr>
    <th colspan="4" align="center">Datos Personales</th>
  </tr>
  <tr>
    <td><strong>MaestroId:</strong></td>
    <td>
      <input name="MaestroId" type="text" class="text" id="MaestroId" size="3" maxlength="2" value="<%= Maestro.getMaestroId() %>">
    </td>
    <td><strong><spring:message code="aca.Fecha"/>:</strong></td>
    <td>
	  <input name="FNacimiento" type="text" class="text" id="FNacimiento" data-date-format="dd/mm/yyyy" size="12" maxlength="10" onfocus="focusFecha(this);" value="<%= Maestro.getFNacimiento() %>">
      (DD/MM/AAAA)
    </td>
  </tr>
  <tr>
    <td><strong><spring:message code="aca.Nombre"/>:</strong></td>
    <td>
      <input name="Nombre" type="text" class="text" id="Nombre" size="10" maxlength="50" value="<%= Maestro.getNombre()%>">
    </td>
    <td><strong><spring:message code="aca.Tel"/>éfono:</strong></td>
    <td>
      <input name="Telefono" type="text" class="text" id="Telefono" size="10" maxlength="70" value="<%=Maestro.getTelefono()%>">
    </td>
  </tr>
  <tr>
    <td><strong>A.Paterno:</strong></td>
    <td>
      <input name="Paterno" type="text" class="text" id="Paterno" size="15" maxlength="50" value="<%=Maestro.getApellidoPaterno() %>">
    </td>
    <td><strong><spring:message code="aca.Cel"/>ular:</strong></td>
    <td>
      <input name="Celular" type="text" class="text" id="Celular" size="10" maxlength="70" value="<%=Maestro.getCelular()%>">
    </td>
  </tr>
  <tr>
    <td><strong>A.Materno:</strong></td>
    <td>
      <input name="Materno" type="text" class="text" id="Materno" size="15" maxlength="50" value="<%=Maestro.getApellidoMaterno()%>">
    </td>
    <td><strong><spring:message code="aca.Correo"/>:</strong></td>
    <td>
      <input name="Correo" type="text" class="text" id="Correo" size="15" maxlength="100" value="<%= Maestro.getCorreo()%>">
    </td>
  </tr>
  <tr><td style="text-align:center;"><%= sResultado %></td></tr>
  <tr>
    <th colspan="4" style="text-align:center;">
      <a class="btn btn-primary" href="javascript:nuevo()"><spring:message code='aca.Nuevo'/></a>&nbsp;&nbsp;
      <a class="btn btn-primary" href="javascript:grabar()"><spring:message code="aca.Grabar"/></a>&nbsp;&nbsp;
      <a class="btn btn-primary" href="javascript:borrar()"><spring:message code='aca.Borrar'/></a>
    </th>
  </tr>
</table>
<table style="width:50%">
  <tr><td align="center" colspan="3" style="font-size:10pt"><b>Listado de Mestros del Conservatorio</b></td></tr>
</table>  
<table style="width:50%" class="table table-condensed">  
  <tr>
     <th width="5%"><spring:message code="aca.Operacion"/></th>
     <th width="5%">Id</th>
     <th width="20%"><spring:message code="aca.Nombre"/></th>
     <th width="10%"><spring:message code="aca.Fecha"/></th>
     <th width="10%">Teléfono</th>
     <th width="10%">Celular</th>
     <th width="20%">Correo</th>
  </tr>  
<%  for(int i=0; i<lisMaestro.size(); i++){
		aca.musica.MusiMaestro maestro = (aca.musica.MusiMaestro) lisMaestro.get(i);
%>
  <tr class="tr2">
    <td align="center"> 
    <a class="fas fa-edit" href="maestro?Accion=4&MaestroId=<%= maestro.getMaestroId() %>"> 
      </a> <a class="fas fa-trash-alt" href="maestro?Accion=3&MaestroId=<%= maestro.getMaestroId()%>">
      </a> </td>
    <td align="left"><%= maestro.getMaestroId()%></td>
    <td align="left"><%= maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()%></td>
    <td align="left"><%= maestro.getFNacimiento()%></td>
    <td align="left"><%= maestro.getTelefono() %></td>
    <td align="left"><%= maestro.getCelular() %></td>
    <td align="left"><%= maestro.getCorreo()%></td>
  </tr>   
<% }%>  
</table>
</form>
</body>
<script>
	jQuery('#FNacimiento').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>