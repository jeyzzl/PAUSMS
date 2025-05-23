<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumPersonal"%>
<%@page import="aca.catalogo.spring.CatReligion"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatCiudad"%>
<%@page import="aca.catalogo.spring.CatCultural"%>
<%@page import="aca.catalogo.spring.CatRegion"%>
<%@page import="aca.log.spring.LogAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<% idJsp="070_p";%>
<%@ include file="../../idioma.jsp"%>
<head>
<%
	String usuario 					= (String)session.getAttribute("codigoPersonal");
	String codigoAlumno				= (String)session.getAttribute("codigoAlumno");
	String accion 					= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String mensaje 					= request.getParameter("Mensaje")==null?"0":request.getParameter("Mensaje");
	String ingreso					= (String)request.getAttribute("ingreso");
	String nacionalidadNombre		= (String)request.getAttribute("nacionalidadNombre");
	String paisNombre				= (String)request.getAttribute("paisNombre");
	String estadoNombre				= (String)request.getAttribute("estadoNombre");
	String ciudadNombre				= (String)request.getAttribute("ciudadNombre");
	String resPaisNombre			= (String)request.getAttribute("resPaisNombre");
	String resEstadoNombre			= (String)request.getAttribute("resEstadoNombre");
	String resCiudadNombre			= (String)request.getAttribute("resCiudadNombre");
	String culturalNombre			= (String)request.getAttribute("culturalNombre");
	String regionNombre				= (String)request.getAttribute("regionNombre");
	String usuarioActualizo			= (String)request.getAttribute("usuarioActualizo");
	String maxUpdate				= (String)request.getAttribute("maxUpdate");
	AlumPersonal alumPersonal		= (AlumPersonal)request.getAttribute("alumPersonal");
	LogAlumno logAlumno				= (LogAlumno)request.getAttribute("logAlumno");
	boolean existePersonal			= (boolean)request.getAttribute("existePersonal");	
	boolean borrar					= (boolean)request.getAttribute("borrarAlumno");
	String resultado				= "";	
	
	List<CatReligion> lisReligiones	= (List<CatReligion>)request.getAttribute("lisReligiones");
	List<CatPais> lisPaises			= (List<CatPais>)request.getAttribute("lisPaises");
	List<CatEstado> lisEstados		= (List<CatEstado>)request.getAttribute("lisEstados");
	List<CatCiudad> lisCiudades		= (List<CatCiudad>)request.getAttribute("lisCiudades");
	List<CatCultural> lisGrupos		= (List<CatCultural>)request.getAttribute("lisGrupos");
	List<CatRegion> lisRegiones		= (List<CatRegion>)request.getAttribute("lisRegiones");
	List<CatPais> lisResPaises		= (List<CatPais>)request.getAttribute("lisResPaises");
	List<CatEstado> lisResEstados	= (List<CatEstado>)request.getAttribute("lisResEstados");
	List<CatCiudad> lisResCiudades	= (List<CatCiudad>)request.getAttribute("lisResCiudades");
	
	String curpAlumno 				= alumPersonal.getCurp()==null?"------------------":alumPersonal.getCurp();
	String genero 					= alumPersonal.getSexo().equals("M")?"H":"M";
	boolean validaCurp				= aca.alumno.AlumUtil.validarCurp(curpAlumno);	
	String errorCurp				= aca.alumno.AlumUtil.validaDatosDeCurp(curpAlumno, alumPersonal.getNombre(), alumPersonal.getApellidoPaterno(), alumPersonal.getApellidoMaterno(), alumPersonal.getFNacimiento(), genero, alumPersonal.getPaisId(), alumPersonal.getEstadoId());
	
	String tipoAlumno = "";	
	if (codigoAlumno.substring(0,1).equals("0") || codigoAlumno.substring(0,1).equals("1")){
		tipoAlumno = "Regular";
	}else if(codigoAlumno.substring(0,1).equals("2")){
		tipoAlumno = "Other";
	}else{
		tipoAlumno = "Other";
	}		
	String clave 			= "";
	String codPers			= "";	
	
%>
</head>
<body>
<div class="container-fluid">
	<h2>Personal Data <small class="text-muted fs-5">( Student: <%=codigoAlumno%> <%=alumPersonal.getNombre()+" "+(alumPersonal.getApellidoMaterno().equals("-")?"":alumPersonal.getApellidoMaterno())+" "+alumPersonal.getApellidoPaterno()%> )</small></h2>
	<div class="alert alert-info">
		<a href="semejantes?Accion=0" title="Search Student"><i class="fas fa-search fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="alumno" title="Personal Data"><i class="fas fa-user fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_u" title="Provenance Data"><i class="fas fa-globe fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_a" title="Academic Data"><i class="fas fa-book fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_e" title="Background Data"><i class="fas fa-file-signature fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_o" title="Bank Data"><i class="fas fa-credit-card fa-lg"></i></a>
	</div>
	<form name="forma" action="grabarAlumno" method="post">
	<input type="hidden" name="Accion">
	<input type="hidden" name="Estado" id="Estado" value="A">
	<input type="hidden" name="Cotejado" id="Cotejado"  value="<%=alumPersonal.getCotejado()==null?"":alumPersonal.getCotejado() %>">
	<table class="table table-sm table-nohover" style="width:90%">  		
		<tr>
	  		<td colspan='2' rowspan="14">
	  			<img src="../../foto?Codigo=<%=alumPersonal.getCodigoPersonal()%>&Tipo=O" width="200px" title='<%=alumPersonal.getEstado().equals("A")?"Active":"Inactive"%>'/>
		  	  	<br>
		  	  	<strong title="<%=ingreso%>">
		  	  	Student ID <%=alumPersonal.getCodigoPersonal()%>
		  	  	</strong>
		  	  	<br>
		  	  	<h6><%=alumPersonal.getCotejado().equals("S")?"Verified data":"Unverified data"%></h6>
			</td>
	  	</tr>
	  	<tr>
			<td><strong>Gender<b><font color="#AE2113"> *</font></b></strong></td>
		  	<td>	  	  	         			  
	<%	if (!alumPersonal.getCotejado().equals("S")){ %>
		  		<select name="Sexo" id="Sexo">
	<%		if(alumPersonal.getSexo().equals("M")){ %>
					<option value='M' selected>Male</option>
				    <option value='F' >Female</option>
	<%	  	}else{%>
		    		<option value='M'>Male</option>
		    		<option value='F' selected>Female</option>
		  		</select>  
	<% 		}
		}else{
			if(alumPersonal.getSexo().equals("M")) 
				out.println("Male");
			else
				out.print("Female");
		}
	%>	
			</td>
		</tr>
	  	<tr>
		  	<td><strong>Civil Status</strong></td>
	        <td>
				<select name="EstadoCivil" id="EstadoCivil">
					<option value="S" <% if(alumPersonal.getEstadoCivil().equals("S"))out.print("selected"); %>>Single</option>
					<option value="C" <% if(alumPersonal.getEstadoCivil().equals("C"))out.print("selected"); %>>Married</option>
					<option value="D" <% if(alumPersonal.getEstadoCivil().equals("D"))out.print("selected"); %>>Divorced</option>
					<option value="V" <% if(alumPersonal.getEstadoCivil().equals("V"))out.print("selected"); %>>Widowed</option>
	            </select>
			</td>
		</tr>
	  	<tr>
	    	<td><strong>Denomination</strong></td>
	        <td>
				<select name="ReligionId" id="ReligionId">
	<%				
		for(CatReligion religion : lisReligiones){			
			if (religion.getReligionId().equals(alumPersonal.getReligionId())){
				out.print(" <option value='"+religion.getReligionId()+"' Selected>"+ religion.getNombreReligion()+"</option>");
			}else{
				out.print(" <option value='"+religion.getReligionId()+"'>"+ religion.getNombreReligion()+"</option>");
			}				
		}
	%>
	              </select>
			</td>
		</tr>
	  	<tr>
	    	<td><strong>Baptized</strong></td>
	        <td>
				<select name="Bautizado" id="Bautizado">
					<option value="S" <% if(alumPersonal.getBautizado().equals("S"))out.print("selected"); %>>YES</option>
					<option value="N" <% if(alumPersonal.getBautizado().equals("N"))out.print("selected"); %>>NO</option>
	              </select>
			</td>
		</tr>
		<tr>
			<td><strong>Date of Baptism</strong></td>
			<td>
				<input name="FBautizo" type="text" class="text" id="FBautizo" size="12" maxlength="10" onfocus="focusFecha(this);" value="<%=alumPersonal.getfBautizado()==null?"01/01/1950":alumPersonal.getfBautizado()%>">
	           	<img class="button" alt="calendario" id="fotoFecha" src="../../imagenes/calendario.gif" onClick="javascript:showCalendar(this, document.getElementById('FBautizo'), 'dd/mm/yyyy',null,1,-1,-1);"> (DD/MM/YYYY)
			</td>
		</tr>
		<tr>
	    	<td><strong>Email</strong></td>
	        <td><input name="Email" type="text" class="text" id="Email" size="<%=alumPersonal.getEmail()==null?"": alumPersonal.getEmail().length()+11%>" maxlength="50" value="<%=alumPersonal.getEmail()==null?"": alumPersonal.getEmail()%>"></td>
		</tr>
	    <tr> 
	    	<td><strong>Phone</strong></td>
	        <td><input name="Telefono" type="text" class="text" id="Telefono" size="<%=alumPersonal.getTelefono()==null?"":alumPersonal.getTelefono().length()%>" maxlength="20" value="<%=alumPersonal.getTelefono()==null?"":alumPersonal.getTelefono()%>"></td>
		</tr>
		<tr>
	    	<td><strong>Cultural Group</strong></td>
	        <td>
	<%			
			if(!alumPersonal.getCotejado().equals("S")){
	 %>
				<select name="CulturalId" id="CulturalId" onchange="javascript:refreshRegiones();">
	<%				
		for(CatCultural cultural : lisGrupos){	
			if (cultural.getCulturalId().equals(alumPersonal.getCulturalId())){
				out.print(" <option value='"+cultural.getCulturalId()+"' selected>"+cultural.getPrincipal()+" - "+ cultural.getNombreCultural()+"</option>");
			}else{
				out.print(" <option value='"+cultural.getCulturalId()+"'>"+cultural.getPrincipal()+" - "+ cultural.getNombreCultural()+"</option>");
			}				
		}
	%>
	<%        	
			}else{out.print(culturalNombre);}			
	%>
	              </select>
			</td>
			
		</tr>
		<tr>
	    	<td><strong>Provincial Group</strong></td>
	        <td>
	<%			
			if(!alumPersonal.getCotejado().equals("S")){
	 %>
	 			<select name="RegionId" id="RegionId">	
	<%
				for(CatRegion region : lisRegiones){					
					if (region.getRegionId().equals(alumPersonal.getRegionId())){
						out.print(" <option value='"+region.getRegionId()+"' selected>"+ region.getNombreRegion()+"</option>");
					}else{
						out.print(" <option value='"+region.getRegionId()+"'>"+ region.getNombreRegion()+"</option>");
					}				
				}
%>
<%        	
			}else{out.print(regionNombre);}			
%>
	            </select>
			</td>
		</tr>
		<tr>
	    	<td><strong>Home Country</strong></td>
	        <td>
	<%			
			if(!alumPersonal.getCotejado().equals("S")){
	 %>
	 			<select name="ResPaisId" id="ResPaisId" onchange="javascript:refreshResEstados();">	
	<%
				for(CatPais pais : lisResPaises){					
					if (pais.getPaisId().equals(alumPersonal.getResPaisId())){
						out.print(" <option value='"+pais.getPaisId()+"' selected>"+ pais.getNombrePais()+"</option>");
					}else{
						out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNombrePais()+"</option>");
					}				
				}
%>
<%        	
			}else{out.print(resPaisNombre);}			
%>
	            </select>
			</td>
		</tr>
		<tr>
	    	<td><strong>Home Province</strong></td>
	        <td>
	<%			
			if(!alumPersonal.getCotejado().equals("S")){
	 %>
	 			<select name="ResEstadoId" id="ResEstadoId" onChange="refreshResCuidades();">	
	<%
				for(CatEstado estado : lisResEstados){					
					if (estado.getEstadoId().equals(alumPersonal.getResEstadoId())){
						out.print(" <option value='"+estado.getEstadoId()+"' selected>"+ estado.getNombreEstado()+"</option>");
					}else{
						out.print(" <option value='"+estado.getEstadoId()+"'>"+ estado.getNombreEstado()+"</option>");
					}				
				}
%>
<%        	
			}else{out.print(resEstadoNombre);}			
%>
	            </select>
			</td>
		</tr>
		<tr>
	    	<td><strong>Home Village</strong></td>
	        <td>
	<%			
			if(!alumPersonal.getCotejado().equals("S")){
	 %>
	 			<select name="ResCiudadId" id="ResCiudadId">	
	<%
				for(CatCiudad ciudad : lisResCiudades){					
					if (ciudad.getCiudadId().equals(alumPersonal.getResCiudadId())){
						out.print(" <option value='"+ciudad.getCiudadId()+"' selected>"+ ciudad.getNombreCiudad()+"</option>");
					}else{
						out.print(" <option value='"+ciudad.getCiudadId()+"'>"+ ciudad.getNombreCiudad()+"</option>");
					}				
				}
%>
<%        	
			}else{out.print(resCiudadNombre);}			
%>
	            </select>
			</td>
		</tr>
		<tr>
		 	<td colspan="2"><i>Birth Data</i></td>
		</tr>		
	  	<tr> 
	 	  	<td><strong>&nbsp;&nbsp;&nbsp;Name<b><font color="#AE2113"> *</font></b></strong></td>
	        <td>
	        	<% if(alumPersonal.getCotejado().equals("N")){%>
				<input name="Nombre" type="text" class="text" id="Nombre" size="<%=alumPersonal.getNombre().length()+11%>" maxlength="40" value="<%=alumPersonal.getNombre()%>" tabindex="2">
				<% }else{ out.print(alumPersonal.getNombre()); %>
					<input name="Nombre" type="hidden" class="text" id="Nombre" value="<%=alumPersonal.getNombre()%>">
				<% }%>			
		 	</td>
	        <td><strong><spring:message code="aca.Nacionalidad"/></strong></td>
	        <td>           
	<%		
		if(!alumPersonal.getCotejado().equals("S")){
	%>
				<select name="Nacionalidad" id="Nacionalidad">
	<%		
			for(CatPais pais : lisPaises){				
				if (pais.getPaisId().equals(alumPersonal.getNacionalidad())){
					out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNacionalidad()+"</option>");
				}else{
					out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNacionalidad()+"</option>");
				}
			}
	%>
	        	</select>		
	<%		
		}else{out.print(nacionalidadNombre);}%>
			</td>	        
		</tr>
		<tr> 
	    	<td><strong>&nbsp;&nbsp;&nbsp;Maiden Name</strong></td>
	        <td>
	        	<% if(alumPersonal.getCotejado().equals("N")){%>
				<input name="ApellidoMaterno" type="text" class="text" id="ApellidoMaterno" size="<%=alumPersonal.getApellidoMaterno().length()+11%>" maxlength="40" value="<%=alumPersonal.getApellidoMaterno().equals("null")?"":alumPersonal.getApellidoMaterno()%>" tabindex="4">
				<% }else{ out.print(alumPersonal.getApellidoMaterno()); }%>
			</td>
			<td><strong>Country</strong></td>
	        <td>
	<%            
		if(!alumPersonal.getCotejado().equals("S")){
	%>
				<select name="PaisId" id="PaisId" onchange="javascript:refreshEstados();">
	<%		
			for(CatPais pais : lisPaises){				
				if (pais.getPaisId().equals(alumPersonal.getPaisId())){
					out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNombrePais()+"</option>");
				}else{
					out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNombrePais()+"</option>");
				}
			}
	%>
	        	</select>
	<%        	
			
		}else{out.print(paisNombre);}%>
			</td>		
		</tr>
	 	<tr> 
	    	<td><strong>&nbsp;&nbsp;&nbsp;Surname<b><font color="#AE2113"> *</font></b></strong></td>
	        <td>
		        <% if(alumPersonal.getCotejado().equals("N")){%>
				<input name="ApellidoPaterno" type="text" class="text" id="ApellidoPaterno" size="<%=alumPersonal.getApellidoPaterno().length()+11%>" maxlength="40" value="<%=alumPersonal.getApellidoPaterno()%>" tabindex="3">
				<% }else{ out.print(alumPersonal.getApellidoPaterno()); }%>
			</td>
			<td><strong><spring:message code="aca.Fecha"/> of Birth<b><font color="#AE2113"> *</font></b></strong></td>
	        <td>
	<%
		if (!alumPersonal.getCotejado().equals("S")){
	%>
	          	<input name="FNacimiento" type="text" class="text" id="FNacimiento" size="12" maxlength="10" onfocus="focusFecha(this);" value="<%=alumPersonal.getFNacimiento()%>">
	           	<img class="button" alt="calendario" id="fotoFecha" src="../../imagenes/calendario.gif" onClick="javascript:showCalendar(this, document.getElementById('FNacimiento'), 'dd/mm/yyyy',null,1,-1,-1);"> (DD/MM/YYYY)
	<%
		}else{ out.print(alumPersonal.getFNacimiento()); }%>
			</td>
		</tr>
		<tr>
			<td><strong>&nbsp;&nbsp;&nbsp;Legal Name</strong></td>
	        <td>			
				<input name="NombreLegal" size="<%=alumPersonal.getNombreLegal()==null ? 40 : alumPersonal.getNombreLegal().length()+13%>" class="text" <% if(!alumPersonal.getCotejado().equals("S"))out.print("type=\"text\"");else out.print("type=\"hidden\"");%> value="<%=alumPersonal.getNombreLegal()==null ? alumPersonal.getNombre()+" "+alumPersonal.getApellidoPaterno()+" "+alumPersonal.getApellidoMaterno(): alumPersonal.getNombreLegal()%>" tabindex="5">
					<%if(alumPersonal.getCotejado().equals("S"))out.print(alumPersonal.getNombreLegal());%>
			</td>
			<td><strong>Province/State</strong></td>
	        <td>  
	<%			  	
				
			if(!alumPersonal.getCotejado().equals("S")){
	%>
				 <select name="EstadoId" id="EstadoId"  onChange="refreshCuidades();">
	<% 			
				for(CatEstado estado : lisEstados){					
					if (estado.getEstadoId().equals(alumPersonal.getEstadoId())){
						out.print(" <option value='"+estado.getEstadoId()+"' Selected>"+ estado.getNombreEstado()+"</option>");
					}else{
						out.print(" <option value='"+estado.getEstadoId()+"'>"+ estado.getNombreEstado()+"</option>");
					}		
				}
	 %>
	              </select>
	<%	
			}else{out.print( estadoNombre);}%>
			</td>     
		</tr>
		<tr>
			<td><strong>&nbsp;&nbsp;&nbsp;GOB ID</strong></td>
	    	<td>
	<%
		if (!alumPersonal.getCotejado().equals("S")){
	%>
	           	<input name="Curp" type="text" class="text" id="Curp" maxlength="18" value="<%=(alumPersonal.getCurp()==null || alumPersonal.getCurp().equals("null"))?"-":alumPersonal.getCurp()%>">&nbsp;&nbsp;
	<%	}else{
			out.print(alumPersonal.getCurp());
		}
// 		if (validaCurp && errorCurp.equals("11111")){
// 			out.print("&nbsp;<img src='../../imagenes/activa.png' height='15px'");
// 		} else{
// 			out.print("&nbsp;<img src='../../imagenes/no.png'>&nbsp;&nbsp;[");
// 			for(int z=0; z < errorCurp.length(); z++){
// 				if (errorCurp.substring(z,z+1).equals("1")){
// 					out.print("<i class='fas fa-check-circle'></i> ");
// 				}else{
// 					out.print("<i class='fas fa-trash-alt'></i>");
// 				}
				
// 			}
// 			out.print("]");
// 		}
	%>
			</td>
			<td><strong>Village/City</strong></td>
	        <td>		 
	<%			
			if(!alumPersonal.getCotejado().equals("S")){
	 %>
	 			<select name="CiudadId" id="CiudadId">	
	<%
				for(CatCiudad ciudad : lisCiudades){					
					if (ciudad.getCiudadId().equals(alumPersonal.getCiudadId())){
						out.print(" <option value='"+ciudad.getCiudadId()+"' Selected>"+ ciudad.getNombreCiudad()+"</option>");
					}else{
						out.print(" <option value='"+ciudad.getCiudadId()+"'>"+ ciudad.getNombreCiudad()+"</option>");
					}				
				}
%>
				</select>
<%        	
			}else{out.print(ciudadNombre);}			
%>
			</td>
		</tr>
	</table>
	<div class="alert alert-info">
		<a href="datos" class="btn btn-primary">New Student</a>&nbsp;
	<%	if(!tipoAlumno.equals("Otro") && !accion.equals("2") && existePersonal){ %>
		<a href="javascript:Grabar()" class="btn btn-primary">Save</a>&nbsp; 
			<%if(borrar){ %>
				<a href="javascript:Borrar('<%=codigoAlumno%>')" class="btn btn-primary">Delete</a>
			<%}%>
	<%	} %>
	<%	if (!maxUpdate.equals("0")){%>
			&nbsp;<span style="font-size:10px">Updated <%=logAlumno.getFecha()%> by <%=usuarioActualizo%></span>
	<%	}else{
			out.print("<span style='font-size:10px'> Not updated </span>");
		}				
	%>
	<%=mensaje%>**
	</div>
</form>
<script>
	function Grabar(){		
		if(document.forma.Cotejado.value==="S"){			
			document.forma.Accion.value = "1";
			document.forma.submit();			
		}else{			
 			if(document.forma.Nombre.value!="" && document.forma.ApellidoPaterno.value!="" && document.forma.ApellidoMaterno.value!=""
 				&& document.forma.FNacimiento.value!="" && document.forma.Sexo.value!="" ){ 					
					document.forma.Accion.value = "1";
					document.forma.submit();
			}else{				
				alert("Fill out all the fields");
			}		
		}
	}
	
	function Borrar(matricula){
		if(confirm("Are you sure you want to delete this student?")){			
			document.location.href = "borrarPersonales?CodigoAlumno="+matricula;
		}
	}

	function refreshEstados(){		
		
		jQuery('#EstadoId').html('<option>Loading</option>');
		
		var paisId = document.forma.PaisId.value;	
		
		jQuery.get('getEstados?paisId='+paisId, function(data){
			jQuery("#EstadoId").html(data);
			refreshCuidades();
		});
	}

		function refreshResEstados(){		
		
		jQuery('#ResEstadoId').html('<option>Loading</option>');
		
		var paisId = document.forma.ResPaisId.value;	
		
		jQuery.get('getEstados?paisId='+paisId, function(data){
			jQuery("#ResEstadoId").html(data);
			refreshCuidades();
		});
	}
	
	function refreshCuidades(){
		jQuery('#CiudadId').html('<option>Loading</option>');
		
		var paisId = document.forma.PaisId.value;
		var estadoId = document.forma.EstadoId.value;
		
		jQuery.get('getCiudades?paisId='+paisId+'&estadoId='+estadoId, function(data){
			jQuery("#CiudadId").html(data);
		});
	}

		function refreshResCuidades(){
		jQuery('#ResCiudadId').html('<option>Loading</option>');
		
		var paisId = document.forma.ResPaisId.value;
		var estadoId = document.forma.ResEstadoId.value;
		
		jQuery.get('getCiudades?paisId='+paisId+'&estadoId='+estadoId, function(data){
			jQuery("#ResCiudadId").html(data);
		});
	}
	
	function refreshRegiones(){
		jQuery('#RegionId').html('<option>Loading</option>');
		
		var culturalId = document.forma.CulturalId.value;
		
		jQuery.get('getRegiones?culturalId='+culturalId, function(data){
			jQuery("#RegionId").html(data);
		});
	}
</script>
</div>	
</body>