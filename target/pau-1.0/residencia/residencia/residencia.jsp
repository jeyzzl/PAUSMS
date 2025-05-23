<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.residencia.spring.ResDatos"%>
<%@page import="aca.residencia.spring.ResRazon"%>
<%@page import="aca.residencia.spring.ResComentario"%>
<%@page import="aca.internado.spring.IntDormitorio"%>
<%@page import="aca.alumno.spring.AlumDireccion"%>
<%@page import="aca.alumno.spring.AlumUbicacion"%>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css"/>
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<script type="text/javascript">
	 function valida(){ 
		var texto1	= document.frmExterno.Municipio.value;
		var texto2	= document.frmExterno.Calle.value;	
		var texto3	= document.frmExterno.Colonia.value;	
		var texto4	= document.frmExterno.Numero.value;
		var texto5	= document.frmExterno.Area.value;	
		var texto6	= document.frmExterno.Telefono.value;
		var texto7	= document.frmExterno.Nombre.value;	
		var texto8	= document.frmExterno.Apellidos.value;	
		var texto9	= document.frmExterno.Fecha.value;
		var razon	= document.frmExterno.Razon.value;
		var exter	= document.frmExterno.Externado.value;
		
		if(razon === '15' && exter === 'false'){
		  alert('This student is enrolled as face-to-face. The school dean must update the student record to Day Student.');	  
		}else if((texto1=="")||(texto2=="")||(texto3=="")||(texto4=="")||(texto5=="")||(texto6=="")||(texto7=="")||(texto8=="")||(texto9=="")){
		  alert('Provide all required data for the Day Student residency');	  
		}else{
			document.frmExterno.Accion.value = "1";
		  	document.frmExterno.submit();
		}	
	 }
	
 	function interno(){
		var texto1 = document.frmInterno.Dormitorio.value;
		if((texto1=="")&&(texto1!="X")){		  
		  alert('Please provide the dormitory bedroom No.');
		  return true;
		}else{
			document.frmInterno.Accion.value = "2";
		  	document.frmInterno.submit();
		}	
	 }	
	 
	function Ayuda(direccion){
		 direccion.style.visibility='visible';
	}
		
		
	function Desaparece(direccion){
        direccion.style.visibility='hidden'; 
    }		

</script>
<%	
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	String codigoPersonal		= (String) request.getAttribute("codigoPersonal");
	String codigoAlumno			= (String) request.getAttribute("codigoAlumno");	
	String residencia			= (String) request.getAttribute("residencia");
	String requerido			= (String) request.getAttribute("requerido");
	String usuario				= (String) request.getAttribute("usuario");
	String fecha				= (String) request.getAttribute("fecha");	
	String inscrito				= (String) request.getAttribute("inscrito");		
	String nombreAlumno 		= (String) request.getAttribute("nombreAlumno");
	String nombrePais 			= (String) request.getAttribute("nombrePais");
	String nombreCarrera 		= (String) request.getAttribute("nombreCarrera");
	String dormitorio 			= (String) request.getAttribute("dormitorio");
	String nombreCiudad 		= (String) request.getAttribute("nombreCiudad");
	String planId		 		= (String) request.getAttribute("planId");
	int numAccion 				= (int) request.getAttribute("numAccion");	
	int edad	 				= (int) request.getAttribute("edad");	
	boolean posibleExternado 	= (boolean) request.getAttribute("posibleExternado");
	boolean accesoCarrera 		= (boolean) request.getAttribute("accesoCarrera");
	
	ResDatos dato 						= (ResDatos) request.getAttribute("dato");
	ResComentario resComentario			= (ResComentario) request.getAttribute("resComentario");
	AlumDireccion direccion 			= (AlumDireccion) request.getAttribute("direccion");
	AlumUbicacion ubicacion 			= (AlumUbicacion) request.getAttribute("ubicacion");		
	List<ResRazon> lisRazon 			= (List<ResRazon>) request.getAttribute("lisRazon");
	List<IntDormitorio> lisDormitorios 	= (List<IntDormitorio>) request.getAttribute("lisDormitorios");
%>
<div class="container-fluid">
	<h2>Residence Data
	<small class="text-muted fs-6">
		( <%=codigoAlumno%> - <%=nombreAlumno%> -&nbsp;
		<%=nombrePais%> -&nbsp;
		<span title="<%=nombreCarrera%>"><%=planId%></span> -&nbsp;
		<strong><%=inscrito%></strong> -&nbsp;<strong><%=residencia.equals("E")?"Day Student":"Boarding Student"%></strong>&nbsp;
		)
	</small></h2>
	<div class="alert alert-info">	
<%	if(!codigoPersonal.equals("9800946") && !codigoPersonal.equals("9820389") && !codigoPersonal.equals("9800078")){%> 
	  	<a href="documentosExternos" class="btn btn-primary"><spring:message code="aca.Documento"/></a>&nbsp;&nbsp;	  	
	  	<a class="btn btn-primary" href="residencia?Accion=2"> Update to Boarding Student </a>&nbsp;&nbsp;	  	
<% 	}%> 
<% 	if(!codigoPersonal.equals("9800946") && !codigoPersonal.equals("9820389") && !codigoPersonal.equals("9800078")){%> 
	  	<a class="btn btn-primary" href="residencia?Accion=1"> Update to Day Student </a>&nbsp;&nbsp;	  	
<% 	}%> 
<% 	if((codigoPersonal.equals("9800946") || codigoPersonal.equals("9820389") || codigoPersonal.equals("9800078"))){
		if(edad < 16 && accesoCarrera){%>
	  		<a class="btn btn-primary" href="residencia?Accion=1"> Update to Day Student </a>&nbsp;&nbsp;	  	
<% 		}else{%> 
			This student does not meet the necessary requirements to qualify as a Day Student.
<% 		} 
 	}%> 
<% 	if(!codigoPersonal.equals("9800946") && !codigoPersonal.equals("9820389") && !codigoPersonal.equals("9800078")){%> 
	  	<a class="btn btn-primary" href="historial?codigoAlumno=<%=codigoAlumno%>"> History</a>
<% 	}%> 
	</div>
<%
	if (!mensaje.equals("-")){
		out.print("<div class='alert alert-info'>"+mensaje+"</div>");
	}
%>	
<%	//  Se muestra al inicio y cuando se cambia un alumno a Externo ..¡ 
	if ( ( numAccion == 0 && residencia.equals("E")) || numAccion == 1 ){ %>
	<form name="frmExterno" method="post" action="externo?CodigoAlumno=<%=codigoAlumno%>">	
  	<input name="Accion" type= "hidden">
  	<input name="Externado" type= "hidden" value="<%=posibleExternado%>">
	<table class="table table-condensed">
  	<tr><th colspan="4">Day Student data</th></tr>
    <tr> 
    	<td width="10%"><strong>Street:</strong></td>
      	<td width="43%">         
        	<input name="Calle" type="text" class="text" id="Calle" value="<%=dato.getCalle()%>" size="30" maxlength="30">        
      	</td>
      	<td width="20%"><strong>Tel. Area:</strong></td>
      	<td width="27%"> 
        	<input name="Area" type="text" class="text" id="Area" value="<%=dato.getTelArea()%>" size="10" maxlength="7">
      	</td>
    </tr>
	<tr> 
		<td height="21"><strong>House #:</strong></td>
	    <td> 
	       	<input name="Numero" type="text" class="text" id="Numero" value="<%=dato.getNumero()%>" size="5" maxlength="14">
	    </td>
	    <td><strong>Phone:</strong></td>
	    <td> 
	       	<input name="Telefono" type="text" class="text" id="Telefono" value="<%=dato.getTelNum()%>" size="13" maxlength="12">
	    </td>
	</tr>
	<tr> 
		<td><strong>Neighborhood:</strong></td>
	    <td> 
	    	<input name="Colonia" type="text" class="text" id="Colonia" value="<%=dato.getColonia()%>" size="30" maxlength="20">
	    </td>
	    <td><strong>Mentor Name:</strong></td>
	    <td>
	    	<input name="Nombre" type="text" class="text" id="Nombre" value="<%=dato.getNombreTut()%>" size="30" maxlength="20">
	    </td>
	</tr>
	<tr> 
		<td><strong>Village/Town:</strong></td>
	    <td> 
	    	<input name="Municipio" type="text" class="text" id="Municipio" value="<%=dato.getMpio()%>" size="30" maxlength="20">
	    </td>
	    <td><strong>Mentor Surname: </strong></td>
	    <td> 
	    	<input name="Apellidos" type="text" class="text" id="Apellidos" value="<%=dato.getApellidoTut()%>" size="30" maxlength="20">
	    </td>
	</tr>
	<tr> 
		<td height="22"><strong>Day Student reason:</strong></td>
	    <td> 
	    	<select name="Razon">
			
<%	
		for(ResRazon razon : lisRazon){
			if (razon.getRazon().equals(dato.getRazon())){
				out.print(" <option value='"+razon.getRazon()+"'");			
				out.print("Selected>"+ razon.getDescripcion()+"</option>");
			}else{
				out.print(" <option value='"+razon.getRazon()+"'");
				out.print(" >"+ razon.getDescripcion()+"</option>");
			}
		}
	%>
	        </select>       
	    </td>	        
	    <td><strong><spring:message code="aca.Usuario"/>:</strong></td>
	    <td><strong>[&nbsp;<%=usuario%>&nbsp;]</strong></td>
	</tr>	  
	<tr> 
		<td><strong>Date</strong></td>
	    <td><input name="Fecha" type="text" class="text" id="Fecha" data-date-format="dd/mm/yyyy" value="<%=fecha%>" size="11" maxlength="10"><strong> (DD/MM/YYYY)</strong></td>
	    <td><strong>Comment: </strong></td>
	    <td><input name="Comentario" type="text" class="text" size="30" maxlength="70" value="<%=resComentario.getComentario()%>"></td>
	</tr>
	<tr>
<% 		if(!codigoPersonal.equals("9800946") && !codigoPersonal.equals("9820389") && !codigoPersonal.equals("9800078")){%> 		
			<th colspan="4" style="text-align:left;"><font size="3"><a class="btn btn-primary" href="javascript:valida()"><strong>Save Day Student data</strong></a></font>&nbsp;&nbsp;
	    	<a class="btn btn-info" href="javascript:Ayuda(direccion)" >View <b>current</b> student data</a>
<%		}%>
<% 	if((codigoPersonal.equals("9800946") || codigoPersonal.equals("9820389") || codigoPersonal.equals("9800078"))){
 		if(edad < 16 && accesoCarrera){%>
			<th colspan="4" style="text-align:left;"><font size="3"><a class="btn btn-primary" href="javascript:valida()"><strong>Save Boarding Student data</strong></a></font>&nbsp;&nbsp;
<% 		} 
 	}%> 
	    </th>
	</tr>	
	</table>
	</form>
<%	
	} // fin de if ( ( numAccion == 0 && residencia.equals("Externo")) || numAccion == 1 )
	if ( (numAccion == 0 && ( residencia.equals("I") || residencia.equals("i"))) || numAccion == 2 ){   	
%>
	<form name="frmInterno" method="post" action="interno?codigoAlumno=<%=codigoAlumno%>">
	<input name="Accion" type="hidden">
	<table style="width:73%" class="table table-condensed">
	<tr><th>Boarding Data</th></tr>
  	<tr>
    	<td width="31%">  	    
        	<strong>Type: 
        	<select name="Requerido">       	
	        	<option value="S" <%=requerido.equals("S")?"selected":""%>>Permanent Dormitory residence</opption>
   	     		<option value="N" <%=requerido.equals("N")?"selected":""%>>Temporary Dormitory residence</opption>
        	</select>&nbsp;&nbsp; 
        	<strong>Dormitory:</strong> 
        	<select name="Dormitorio" id="Dormitorio">
<%
		for(IntDormitorio dorm : lisDormitorios){
%>
	        	<option value="<%=dorm.getDormitorioId() %>" <%=dormitorio.equals(dorm.getDormitorioId())?"selected":""%>><%=dorm.getNombre() %></opption>
<%
		}
%>
        	</select>&nbsp;&nbsp;
<%--         	<input name="Dormitorio" type="text" class="text" id="Dormitorio" size="1" maxlength="1" value="<%=dormitorio%>">&nbsp;&nbsp; --%>
        	<strong>Comment:</strong>
        	<input name="Comentario" type="text" class="text" size="80" maxlength="70" value="<%=resComentario.getComentario()%>">        
    	</td>
  	</tr>
  	<tr>  
		<th>
	  		<font size="3">
	  		<a class="btn btn-primary" href="javascript:interno()" style="text-align:left;"><strong>Save Boarding data</strong></a>
	  		</font>
		</th>
  	</tr>
	</table>
	</form>
<%	} %>
<%	if ((!residencia.equals("I") && !residencia.equals("i")) || numAccion==1) {%>
	<title>Visibility</title>
	<div id="direccion" style="visibility:hidden;" align="center">
		<form name="frmdireccion" method="post" action="externo?CodigoAlumno=<%=codigoAlumno%>">
			<input name="Accion" type= "hidden">
		</form>
		<table style="width:73%" class="table table-condensed">
		<tr>  
			<th colspan="4">Updated Address</th>
		</tr>
		<tr> 
	   		<td width="10%"><strong>Street:</strong></td>
	   	 	<td width="43%"><%= direccion.getCalle()%></td> 
	    	<td><strong>Phone Number:</strong></td>
	    	<td> <%=direccion.getTelefono()%></td>
		</tr>  
		<tr>
			<td width="20%"><strong>House Number</strong></td>
	    	<td width="27%"><%= direccion.getNumero()%></td>	  
	    	<td height="21"><strong>Town:</strong></td>
	    	<td><%=nombreCiudad%></td>
		</tr>
		<tr>
			<td height="21"><strong>Neighborhood:</strong></td>
	    	<td><%= direccion.getColonia()%></td>
	    	<td><strong><spring:message code="aca.Tutor"/>:</strong></td>
	    	<td><strong>&nbsp;<%= ubicacion.gettNombre() %>&nbsp;</strong></td>
		<tr align="center">
			<td colspan="4"  style="text-align:center" style="font-size: 9pt;"><a class="btn btn-danger" href="javascript:Desaparece(direccion)" >Close</a></td>
		</tr>		
		</table>
	</div>
<%	} %>
</div>
<script>
	var dormi = jQuery('#Dormitorio');

	function verificarNumero(){
		if(dormi.val() == '1' || dormi.val() == '2' || dormi.val() == '3' || dormi.val() == '4' || dormi.val() == '' ){
			
		}else{
			dormi.val('1');
			alert('Invalid dormitory number')
		}
	}
	
	jQuery('#Dormitorio').on('keyup', function(){
		verificarNumero();
	});
</script>
<script>
	jQuery('#Fecha').datepicker();
</script>
