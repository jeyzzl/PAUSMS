<%@page import="java.util.List"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.alumno.spring.AlumUbicacion"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatCiudad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>	
</head>
<%
	String mensaje 				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje"); 
	AlumPersonal datos 			= (AlumPersonal)request.getAttribute("datos");
	AlumUbicacion ubicacion 	= (AlumUbicacion)request.getAttribute("ubicacion");	
	
	List<CatPais> lisPaises 		= (List<CatPais>)request.getAttribute("lisPaises");	
	List<CatEstado> lisEstados 		= (List<CatEstado>)request.getAttribute("lisEstados");	
	List<CatCiudad> lisCiudades 	= (List<CatCiudad>)request.getAttribute("lisCiudades");	
	
	String genero 					= datos.getSexo().equals("M")?"H":"M";
	String curpAlumno 				= datos.getCurp()==null?"------------------":datos.getCurp();
	boolean validaCurp				= aca.alumno.AlumUtil.validarCurp(curpAlumno);	
	String errorCurp				= aca.alumno.spring.AlumPersonalDao.validaDatosDeCurp(curpAlumno, datos.getNombre(), datos.getApellidoPaterno(), datos.getApellidoMaterno(), datos.getFNacimiento(), genero, datos.getPaisId(), datos.getEstadoId());
	String paisNombre				= (String)request.getAttribute("paisNombre");
	String estadoNombre				= (String)request.getAttribute("estadoNombre");
	String ciudadNombre				= (String)request.getAttribute("ciudadNombre");
	String nacionNombre				= (String)request.getAttribute("nacionNombre");	
%>
<div class="container-fluid">
<h2>Verify Student</h2>
<div class="alert alert-info d-flex align-items-center"></div>
	<form action="grabar" method="post" name="frmpersonal" target="_self">    
	<table class="table table-sm table-bordered">
    <tr> 
    	<td width="15%"><strong><spring:message code="aca.Clave"/>:<b><font color="#AE2113"> *</font></b></strong></td>
        <td width="32%">
  			<input name="CodigoPersonal" type="hidden" class="form-control" value="<%=datos.getCodigoPersonal()%>"> 
        	<input name="Cotejado" type="hidden" value="<%=datos.getCotejado()%>">
           	<strong>[<%=datos.getCodigoPersonal()%>] - Verified: <%=datos.getCotejado().equals("S")?"SI":"NO" %></strong></td>
		<td><strong>Country:</strong></td>
        <td><%
            if(datos.getCotejado().equals("S") ){
            	out.print(paisNombre);
            }else {%>            
        	<select name="PaisId" id="PaisId" class="form-control" onchange="javascript:refreshEstados();">
            <%
            for(CatPais pais : lisPaises){
            	if (pais.getPaisId().equals(datos.getPaisId())){
					out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNombrePais()+"</option>");
				}else{
					out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNombrePais()+"</option>");
				}
            }%>
            </select>
            <%}%></td>
	</tr>
    <tr> 
    	<td><strong><spring:message code="aca.Nombre"/>:<b><font color="#AE2113"> *</font></b></strong></td>
        <td><%if(datos.getCotejado().equals("N")){%>
			<input name="Nombre" type="text" id="Nombre" class="form-control" size="<%=datos.getNombre().length()+11%>" maxlength="40" value="<%=datos.getNombre()%>" tabindex="2">
				<%}else{ out.print(datos.getNombre());%>
			<input name="Nombre" type="hidden" id="Nombre" class="form-control" value="<%=datos.getNombre()%>">
				<%}%>
		</td>
        <td><strong><spring:message code="aca.Estado"/>:</strong></td>
        <td><% 
            if(datos.getCotejado().equals("S")){
            	out.print(estadoNombre);
            }else{%>
			<select name="EstadoId" id="EstadoId" class="form-control" onChange="javascript:refreshCiudades();">
			<%
            for(CatEstado estado : lisEstados){
            	if (estado.getEstadoId().equals(datos.getEstadoId()) && estado.getPaisId().equals(datos.getPaisId())){
					out.print(" <option value='"+estado.getEstadoId()+"' Selected>"+ estado.getNombreEstado()+"</option>");
				}else{
					out.print(" <option value='"+estado.getEstadoId()+"'>"+ estado.getNombreEstado()+"</option>");
				}
        	 }
			%> 
            </select>
            <%}%>
		</td>
	</tr>
    <tr> 
    	<td><strong>Surname:<b><font color="#AE2113"> *</font></b></strong></td>
        <td><%if(datos.getCotejado().equals("N")){%>
			<input name="ApellidoPaterno" type="text" class="form-control" id="ApellidoPaterno" size="<%=datos.getApellidoPaterno().length()+11%>" maxlength="40" value="<%=datos.getApellidoPaterno()%>" tabindex="3">
				<%}else{ out.print(datos.getApellidoPaterno()); }%>
		</td>				
        <td><strong>Village:</strong></td>
        <td><%
            if(datos.getCotejado().equals("S")){
            	out.print(ciudadNombre);
            }else{%>
			<select name="CiudadId" id="CiudadId" class="form-control">
			<%
	            for(CatCiudad ciudad : lisCiudades){
	            	if (ciudad.getCiudadId().equals(datos.getCiudadId()) && ciudad.getEstadoId().equals(datos.getEstadoId()) && ciudad.getPaisId().equals(datos.getPaisId())){
						out.print(" <option value='"+ciudad.getCiudadId()+"' Selected>"+ ciudad.getNombreCiudad()+"</option>");
					}else{
						out.print(" <option value='"+ciudad.getCiudadId()+"'>"+ ciudad.getNombreCiudad()+"</option>");
					}
	         	}
			%> 
            </select>
            <%}%>
		</td>
	</tr>
    <tr> 
    	<td><strong>Maiden Name:<b><font color="#AE2113"> *</font></b></strong></td>
        <td><%if(datos.getCotejado().equals("N")){%>
			<input name="ApellidoMaterno" type="text" class="form-control" id="ApellidoMaterno" size="<%=datos.getApellidoMaterno().length()+11%>" maxlength="40" value="<%=datos.getApellidoMaterno().equals("null") ? "" : datos.getApellidoMaterno()%>" tabindex="4">
				<%}else{ out.print(datos.getApellidoMaterno()); }%>
		</td>
        <td><strong><spring:message code="aca.Nacionalidad"/>:</strong></td>
        <td><%
            if(datos.getCotejado().equals("S")){
            	out.print(nacionNombre);
            }else{%>
            <select id="Nacionalidad" name="Nacionalidad" class="form-control"> 
            <%
            	for(CatPais nacion : lisPaises){
            		if (nacion.getPaisId().equals(datos.getNacionalidad())){
    					out.print(" <option value='"+nacion.getPaisId()+"' Selected>"+ nacion.getNombrePais()+"</option>");
    				}else{
    					out.print(" <option value='"+nacion.getPaisId()+"'>"+ nacion.getNombrePais()+"</option>");
    				}
            	}
            %>
            </select>
            <%}%>
		</td>
	</tr>
    <tr> 
   		<td><strong>Legal <spring:message code="aca.Nombre"/> :</strong></td>
    	<td>
       	<input name="NombreLegal" size="<%=datos.getNombreLegal()==null ? 40 : datos.getNombreLegal().length()+13%>" class="form-control" <%if(!datos.getCotejado().equals("S"))out.print("type=\"text\"");else out.print("type=\"hidden\"");%> value="<%=datos.getNombreLegal()==null ? datos.getNombre()+" "+datos.getApellidoPaterno()+" "+datos.getApellidoMaterno(): datos.getNombreLegal()%>" tabindex="5">
				<%if(datos.getCotejado().equals("S"))out.print(datos.getNombreLegal());%>
		</td>
    	<td width="12%"><strong>GOB ID: </strong></td>
    	<td><%
				if (!datos.getCotejado().equals("S")){
			%>
	   	<input name="Curp" type="text" id="Curp" class="form-control" size="<%=datos.getCurp()==null? 1 : datos.getCurp().length()+11%>" maxlength="18" value="<%=(datos.getCurp()==null || datos.getCurp().equals("null"))?"-":datos.getCurp()%>" disabled>&nbsp;&nbsp;
			<%	}else{
					out.print(datos.getCurp());
				}
				if (validaCurp && errorCurp.equals("11111")){
					out.print("&nbsp;<img src='../../imagenes/activa.png' height='15px'");
				} else{
					out.print("&nbsp;<i class='fas fa-times text-danger'></i>&nbsp;&nbsp;[");
					for(int z=0; z < errorCurp.length(); z++){
						if (errorCurp.substring(z,z+1).equals("1")){
							out.print("<i class='fas fa-times'></i> ");
						}else{
							out.print("<i class='fas fa-trash'></i>");
						}
						
					}
					out.print("]");
				}
			%>
		</td>
    </tr>
	<tr> 
    	<td><strong>Date of Birth:<b><font color="#AE2113"> *</font></b></strong></td>
        <td>
		    <%
				if (!datos.getCotejado().equals("S")){
			%>
			          	<input name="FNacimiento" type="text" id="FNacimiento" class="form-control" size="12" maxlength="10" onfocus="focusFecha(this);" value="<%=datos.getFNacimiento()%>">
			           	<img class="button" alt="calendario" id="fotoFecha" src="../../imagenes/calendario.gif" onClick="javascript:showCalendar(this, document.getElementById('FNacimiento'), 'dd/mm/yyyy',null,1,-1,-1);"> (DD/MM/AAAA)
			<%
				}else{ out.print(datos.getFNacimiento()); }
			%>
        </td>
        <td width="12%"><strong><spring:message code="aca.Nombre"/> of the Father: </strong></td>
        <td width="41%">
            	<%if(datos.getCotejado().equals("N")){%>
			<input name="NombrePadre" type="text" id="NombrePadre" class="form-control" size="<%=ubicacion.getpNombre().length()+11%>" maxlength="40" value="<%=ubicacion.getpNombre()%>" tabindex="3">
				<%}else{ out.print(ubicacion.getpNombre()); }%>
    	</td>
	</tr>
    <tr> 
    	<td><strong>Gender:<b><font color="#AE2113"> *</font></b></strong></td>
        <td><%	if (!datos.getCotejado().equals("S")){ %>
		  		<select name="Sexo" id="Sexo" class="form-control">
	<%		if(datos.getSexo().equals("M")){ %>
					<option value='M' selected>Male</option>
				    <option value='F' >Female</option>
	<%	  	}else{%>
		    		<option value='M'>Male</option>
		    		<option value='F' selected>Female</option>
		  		</select>  
	<% 		}
		}else{
			if(datos.getSexo().equals("M")) 
				out.println("Male");
			else
				out.print("Female");
		}
	%>	
		</td>
        <td><strong><spring:message code="aca.Nombre"/> of the Mother:</strong></td>
        <td>
            	<%if(datos.getCotejado().equals("N")){%>
				<input name="NombreMadre" type="text" id="NombreMadre" class="form-control" size="<%=ubicacion.getmNombre().length()+11%>" maxlength="40" value="<%=ubicacion.getmNombre()%>" tabindex="3">
				<%}else{ out.print(ubicacion.getmNombre()); }%>
        </td>
	</tr>
    <tr> 
    	<td colspan="4">&nbsp;</td>
    </tr>
		<% 		if (!mensaje.equals("-")){%>          
    <tr><td colspan="4" align="center" style="border: dotted 1px gray;"><%=mensaje%></td></tr>
        <% 		}%>  
    <tr class="alert alert-info">
    	<th colspan="4" style="text-align:center;"> 
			<% 	
				if (datos.getCotejado().equals("N")){
			%>
 				<a href="javascript:Grabar()" class="btn btn-primary" tabindex="14">Verify</a>	 
			<%				}else{ %>
				<a href="javascript:SinCotejar()" class="btn btn-primary" tabindex="14">Remove verification</a>	 
			<%}								
			%>
    	</th>
    </tr>
    </table>
	</form>
</div>
<script>
	jQuery('#FNacimiento').datepicker();
	
	function Grabar(){
		if(document.frmpersonal.Cotejado.value==="S"){
			document.frmpersonal.submit();			
		}else{			
			if(document.frmpersonal.Nombre.value!=""
				&& document.frmpersonal.ApellidoPaterno.value!="" && document.frmpersonal.ApellidoMaterno.value!=""
				&& document.frmpersonal.FNacimiento.value!="" && document.frmpersonal.Sexo.value!="" ){					
					document.frmpersonal.submit();
			}else{				
				alert("Complete the required fields");
			}		
		}
	}

	function SinCotejar(){
		document.location.href = "quitarCotejado";
	}
	
	function refreshEstados(){		
		jQuery('#EstadoId').html('<option>Updating</option>');		
		var paisId = document.frmpersonal.PaisId.value;		
		jQuery.get('getEstados?paisId='+paisId, function(data){
			jQuery("#EstadoId").html(data);
			refreshCuidades();
		});		
	}	
	
	function refreshCiudades(){
		jQuery('#CiudadId').html('<option>Updating</option>');		
		var paisId = document.frmpersonal.PaisId.value;
		var estadoId = document.frmpersonal.EstadoId.value;		
		jQuery.get('getCiudades?paisId='+paisId+'&estadoId='+estadoId, function(data){
			jQuery("#CiudadId").html(data);
		});
	}
</script>