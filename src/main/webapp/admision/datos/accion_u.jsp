<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.alumno.spring.AlumUbicacion"%>
<%@page import="aca.catalogo.spring.CatReligion"%>
<%@page import="aca.catalogo.spring.CatRecogida"%>
<%@page import="aca.catalogo.spring.CatPais"%>
<%@page import="aca.catalogo.spring.CatEstado"%>
<%@page import="aca.catalogo.spring.CatCiudad"%>
<%@page import="aca.log.spring.LogAlumno"%>

 <%@ include file= "id.jsp" %>
 <%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type = "text/javascript">	
	function Grabar(){
		if(document.frmubicacion.CodigoPersonal.value!=""){			
			document.frmubicacion.submit();
		}else{
			alert(" Fill in all fields with *");
		}
	}
		
	function Borrar( ){		
		if(confirm("You are sure to delete the record?")==true){
			document.location.href="borrarUbicacion";		
		}		
	}
</script>
<% 
	// Declaracion de variables
	String usuario 						= (String)session.getAttribute("codigoPersonal");	
	String codigoAlumno					= (String)session.getAttribute("codigoAlumno");
	
	AlumUbicacion alumUbicacion			= (AlumUbicacion)request.getAttribute("alumUbicacion");
	LogAlumno logAlumno					= (LogAlumno)request.getAttribute("logAlumno");
	boolean existeUbicacion				= (boolean)request.getAttribute("existeUbicacion");
	String maxUpdate					= (String)request.getAttribute("maxUpdate");
	String cotejado						= (String)request.getAttribute("cotejado");
	String nombreAlumno					= (String)request.getAttribute("nombreAlumno");
	String usuarioActualizo				= (String)request.getAttribute("usuarioActualizo");
	String mensaje						= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	List<CatReligion> lisReligiones		= (List<CatReligion>)request.getAttribute("lisReligiones");
	List<CatPais> lisPaises				= (List<CatPais>)request.getAttribute("lisPaises");
	List<CatEstado> lisEstados			= (List<CatEstado>)request.getAttribute("lisEstados");
	List<CatCiudad> lisCiudades			= (List<CatCiudad>)request.getAttribute("lisCiudades");	
	List<CatEstado> lisPEstados			= (List<CatEstado>)request.getAttribute("lisEstados");
	List<CatCiudad> lisPCiudades		= (List<CatCiudad>)request.getAttribute("lisPCiudades");	
	List<CatEstado> lisMEstados			= (List<CatEstado>)request.getAttribute("lisMEstados");
	List<CatCiudad> lisMCiudades		= (List<CatCiudad>)request.getAttribute("lisMCiudades");	
	List<CatRecogida> lisRecogidas		= (List<CatRecogida>)request.getAttribute("lisRecogidas");				
%>
<div class="container-fluid">
	<h2>Guardian Data <small class="text-muted fs-5">( Student: <%=codigoAlumno%> - <%=nombreAlumno%> )</small></h2>
	<div class="alert alert-info">
		<a href="alumno" title="Return"><i class="fas fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="alumno" title="Personal Data"><i class="fas fa-user fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_u" title="Guardian Data"><i class="fas fa-globe fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_a" title="Academic Data"><i class="fas fa-book fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_e" title="Background Data"><i class="fas fa-file-signature fa-lg"></i></a>&nbsp;&nbsp;&nbsp;&nbsp;
		<a href="accion_o" title="Bank Data"><i class="fas fa-credit-card fa-lg"></i></a>
	</div>
<%	if (!mensaje.equals("-")){ %>	
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>	
	<form action="grabarUbicacion" method="post" name="frmubicacion" target="_self">
	<input type="hidden" name="CodigoPersonal" value="<%=codigoAlumno %>">	
	<table class="table table-sm">
	<tr> 
    	<td><strong><font color="#000099">Father</font></strong></td>
        <td>
<%		if ( cotejado.equals("S") ){ 
        	out.print( alumUbicacion.getpNombre() );
 %> 	<input type="hidden" name="PNombre" value="<%=alumUbicacion.getpNombre()%>" class="form-control text" style="width:25rem;">
 <%
        }else{
%>				              		
        <input name="PNombre" type="text" class="form-control text" id="PNombre" size="40" maxlength="60" value="<%=alumUbicacion.getpNombre()==null?"-":alumUbicacion.getpNombre()%>" tabindex="1"  style="width:25rem;">	
<%      } %>      	
        </td>
    	<td><strong><font color="#000099">Mother</font></strong></td>
        <td>
<%				
              	if (cotejado.equals("S")){
              		out.print( alumUbicacion.getmNombre() );
              		%> <input type="hidden" name="MNombre" value="<%=alumUbicacion.getmNombre()%>"  style="width:25rem;"><%
              	}else{%>                       	
			<input name="MNombre" type="text" class="text form-control" id="MNombre" value="<%=alumUbicacion.getmNombre()==null?"-":alumUbicacion.getmNombre()%>" size="40" maxlength="60" tabindex="10"  style="width:25rem;">            	
<%             	} %>
        </td>
	</tr>
	<tr> 
    	<td><strong>Father's ID</strong></td>
		<td><input name="mPadre" type="text" class="form-control text" id="mPadre" value="<%=alumUbicacion.getCodigoPadre()==null?"-":alumUbicacion.getCodigoPadre()%>" size="10" maxlength="7" readonly  style="width:25rem;" ></td>
       	<td><strong>Mother's ID</strong></td>
    	<td><input name="mMadre" type="text" class="text form-control" id="mMadre" value="<%=alumUbicacion.getCodigoMadre()==null?"-":alumUbicacion.getCodigoMadre()%>" size="10" maxlength="7" readonly  style="width:25rem;"></td>
	</tr>
	<tr> 
        <td><strong>Denomination</strong></td>
        <td>
        	<select name="PReligion" id="PReligion" tabindex="2" class="form-select" style="width:25rem;">
      <%
				for( CatReligion religion : lisReligiones){					
					if (religion.getReligionId().equals(alumUbicacion.getpReligion())){
						out.print(" <option value='"+religion.getReligionId()+"' Selected>"+ religion.getNombreReligion()+"</option>");
					}else{
						out.print(" <option value='"+religion.getReligionId()+"'>"+ religion.getNombreReligion()+"</option>");
					}				
				}				
	  %>
         	</select> 
		</td>
		<td><strong>Denomination</strong></td>
        <td>
        	<select name="MReligion" id="MReligion" tabindex="11" class="form-select" style="width:25rem;">
                <%				
				for( CatReligion religion : lisReligiones){
					if (religion.getReligionId().equals(alumUbicacion.getmReligion())){
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
        <td><strong>Nationality</strong></td>
        <td>
        	<select name="PNacionalidad" id="select2" tabindex="3" class="form-select" style="width:25rem;">
                <%				
				for( CatPais pais : lisPaises){					
					if (pais.getPaisId().equals(alumUbicacion.getpNacionalidad())){
						out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNacionalidad()+"</option>");
					}else{
						out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNacionalidad()+"</option>");
					}				
				}				
			  %>
            </select>
		</td>
        <td><strong>Nationality</strong></td>
        <td>
        	<select name="MNacionalidad" id="select4" tabindex="12" class="form-select" style="width:25rem;">
                <%				
				for( CatPais pais : lisPaises){					
					if (pais.getPaisId().equals(alumUbicacion.getmNacionalidad())){
						out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNacionalidad()+"</option>");
					}else{
						out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNacionalidad()+"</option>");
					}				
				}				
			  %>
            </select>
        </td>
	</tr>
	<tr>
        <td><strong>Father's Country</strong></td>
        <td>
        	<select name="PPais" id="PPais" tabindex="4" class="form-select" style="width:25rem;" onchange="javascript:refreshPEstados();">
            <% for( CatPais pais : lisPaises){	%>
				<option value="<%=pais.getPaisId()%>" <%=pais.getPaisId().equals(alumUbicacion.getpPais())?"selected":""%>><%=pais.getNombrePais()%></option>
			<% } %>
            </select>
		</td>
        <td><strong>Mother's Country</strong></td>
        <td>
        	<select name="MPais" id="MPais" tabindex="5" class="form-select" style="width:25rem;" onchange="javascript:refreshMEstados();">
            <% for( CatPais pais : lisPaises){	%>
				<option value="<%=pais.getPaisId()%>" <%=pais.getPaisId().equals(alumUbicacion.getmPais())?"selected":""%>><%=pais.getNombrePais()%></option>
			<% } %>
            </select>
		</td>
	</tr>
	<tr>
        <td><strong>Father's Province</strong></td>
        <td>
        	<select name="PEstado" id="PEstado" tabindex="6" class="form-select" style="width:25rem;" onchange="javascript:refreshPCiudades();">
            <% for( CatEstado estado : lisPEstados){	%>
				<option value="<%=estado.getEstadoId()%>" <%=estado.getEstadoId().equals(alumUbicacion.getpEstado())?"selected":""%>><%=estado.getNombreEstado()%></option>
			<% } %>
            </select>
		</td>
        <td><strong>Mother's Province</strong></td>
        <td>
        	<select name="MEstado" id="MEstado" tabindex="7" class="form-select" style="width:25rem;" onchange="javascript:refreshMCiudades();">
            <% for( CatEstado estado : lisMEstados){	%>
				<option value="<%=estado.getEstadoId()%>" <%=estado.getEstadoId().equals(alumUbicacion.getmEstado())?"selected":""%>><%=estado.getNombreEstado()%></option>
			<% } %>
            </select>
		</td>
	</tr>
	<tr>
        <td><strong>Father's District/Village</strong></td>
        <td>
        	<select name="PCiudad" id="PCiudad" tabindex="8" class="form-select" style="width:25rem;">
            <% for( CatCiudad ciudad : lisPCiudades){	%>
				<option value="<%=ciudad.getCiudadId()%>" <%=ciudad.getCiudadId().equals(alumUbicacion.getpCiudad())?"selected":""%>><%=ciudad.getNombreCiudad()%></option>
			<% } %>
            </select>
		</td>
        <td><strong>Mother's District/Village</strong></td>
        <td>
        	<select name="MCiudad" id="MCiudad" tabindex="9" class="form-select" style="width:25rem;">
            <% for( CatCiudad ciudad : lisMCiudades){	%>
				<option value="<%=ciudad.getCiudadId()%>" <%=ciudad.getCiudadId().equals(alumUbicacion.getmCiudad())?"selected":""%>><%=ciudad.getNombreCiudad()%></option>
			<% } %>
            </select>
		</td>
	</tr>
	<tr>
        <td><strong>Father's Origin Ward</strong></td>
        <td><input name="POrigen" type="text" class="form-control text" id="POrigen" value="<%=alumUbicacion.getpOrigen()==null?"-":alumUbicacion.getpOrigen()%>" style="width:25rem;"></td>
        <td><strong>Father's Origin Ward</strong></td>
        <td><input name="MOrigen" type="text" class=" form-control text" id="MOrigen" value="<%=alumUbicacion.getmOrigen()==null?"-":alumUbicacion.getmOrigen()%>" style="width:25rem;"></td>
	</tr>
    <tr> 
		<td><strong><font color="#000099">Mentor</font></strong></td>
        <td><input name="TNombre" type="text" class="text form-control" id="PNombre3" size="40" maxlength="60" value="<%=alumUbicacion.gettNombre()==null?"-":alumUbicacion.gettNombre()%>" style="width:25rem;"></td>  
        <td><strong><font color="#000099">Mentor's Phone</font></strong></td>
        <td><input name="TCelular" type="text" class="text form-control" id="TCelular" size="40" maxlength="30" value="<%=alumUbicacion.gettCelular()==null?"-":alumUbicacion.gettCelular()%>" style="width:25rem;"></td>
	</tr>
	<tr> 
        <td><strong>Address</strong></td>
        <td><input name="TDireccion" type="text" class="form-control text" id="TDireccion" value="<%=alumUbicacion.gettDireccion()==null?"-":alumUbicacion.gettDireccion()%>" size="40" maxlength="60"  style="width:25rem;"></td>
        <td><strong>Neighborhood</strong></td>
        <td><input name="TColonia" type="text" class=" form-control text" id="TColonia" value="<%=alumUbicacion.gettColonia()==null?"-":alumUbicacion.gettColonia()%>" size="20" maxlength="30"  style="width:25rem;"></td>
	</tr>
	<tr> 
        <td><strong>Phone</strong></td>
        <td><input name="TTelefono" type="text" class="text form-control" id="TTelefono" value="<%=alumUbicacion.gettTelefono()==null?"-":alumUbicacion.gettTelefono()%>" size="20" maxlength="20"  style="width:25rem;"></td>
        <td width="12%"><strong>ZIP Code</strong></td>
        <td><input name="TCodigo" type="text" class="text form-control" id="TCodigo" value="<%=alumUbicacion.gettCodigo()==null?"-":alumUbicacion.gettCodigo()%>" size="10" maxlength="10"  style="width:25rem;"></td>
	</tr>
	<tr>
        <td><strong>P.O. Box</strong></td> 
        <td width="41%"><input name="TApartado" type="text" class="text form-control" id="TApartado" value="<%=alumUbicacion.gettApartado()==null?"-":alumUbicacion.gettApartado()%>" size="20" maxlength="20" readonly  style="width:25rem;"></td>
        <td><strong><spring:message code="aca.Email"/></strong></td>
        <td><input name="TEmail" type="text" class="text form-control" id="TEmail" value="<%=alumUbicacion.gettEmail()==null?"-":alumUbicacion.gettEmail()%>" size="40" maxlength="50"  style="width:25rem;"></td>
	</tr>
	<tr>
        <td><strong>Country</strong></td>
        <td>
        	<select name="TPais" id="TPais" onchange="javascript:refreshEstados();" class="form-select" style="width:25rem;">
            <%				
				for( CatPais pais : lisPaises){					
					if (pais.getPaisId().equals(alumUbicacion.gettPais())){
						out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNombrePais()+"</option>");
					}else{
						out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNombrePais()+"</option>");
					}				
				}				
			%>
            </select>
		</td>
        <td><strong>Preferred Contact Method</strong></td>
        <td><select id="TComunica" name="TComunica" class="form-select" style="width:25rem;">
       	 		<option value="0"  SELECTED  >Select preferred method</option>
       	  		<% String comunica = alumUbicacion.gettComunica()==null?"":alumUbicacion.gettComunica(); %>
         		<option value="C" <%=comunica.equals("C")?"Selected":"" %>>Letter</option>
         		<option value="E" <%=comunica.equals("E")?"Selected":"" %>>E-mail</option>
         		<option value="T" <%=comunica.equals("T")?"Selected":"" %>>Text Message</option>
         		<option value="L" <%=comunica.equals("L")?"Selected":"" %>>Phone Call</option>
       			</select>
        </td>
	</tr>
	<tr>
		<td><strong>Province</strong></td>
        <td>
        	<select name="TEstado" id="TEstado"  onChange= "javascript:refreshCiudades();" class="form-select" style="width:25rem;">
                <%			
				for( CatEstado estado : lisEstados){					
					if (estado.getEstadoId().equals(alumUbicacion.gettEstado())){
						out.print(" <option value='"+estado.getEstadoId()+"' Selected>"+ estado.getNombreEstado()+"</option>");
					}else{
						out.print(" <option value='"+estado.getEstadoId()+"'>"+ estado.getNombreEstado()+"</option>");
					}				
				}				
			  %>
              </select>
        </td>
        <td><strong>Pickup Location</strong></td>
        <td> 
			<select name="RecogidaId" id="RecogidaId" class="form-select" style="width:25rem;">
				<option value="0" <%=alumUbicacion.getRecogidaId().equals("0")?"selected":""%> >None</option>
<%			
				for( CatRecogida recogida : lisRecogidas){	
%>									
				<option value="<%=recogida.getRecogidaId()%>" <%=alumUbicacion.getRecogidaId().equals(recogida.getRecogidaId())?"selected":""%>><%=recogida.getNombre()%></option>						
<%				}	%>
			</select>
        </td>
	</tr>
	<tr>
        <td><strong>District/Village</strong></td>
        <td> <select name="TCiudad" id="TCiudad" class="form-select" style="width:25rem;">
              <%			
				for( CatCiudad ciudad : lisCiudades){										
					if (ciudad.getCiudadId().equals(alumUbicacion.gettCiudad())){
						out.print(" <option value='"+ciudad.getCiudadId()+"' Selected>"+ ciudad.getNombreCiudad()+"</option>");						
					}else{
						out.print(" <option value='"+ciudad.getCiudadId()+"'>"+ ciudad.getNombreCiudad()+"</option>");
					}				
				}			
			  %>
              </select>
        </td>	
	</tr>
	</table>
	<div class="alert alert-info">
		<a href="javascript:Grabar()" class="btn btn-primary">Save</a>&nbsp;
<%	if(existeUbicacion){ %>
		<a href="javascript:Borrar()" class="btn btn-primary">Delete</a>&nbsp;&nbsp;
	<%	if (!maxUpdate.equals("0")){ %>
		<span style="font-size:10px">Updated: <%=logAlumno.getFecha()%> by <%=usuarioActualizo%></span>
<%		}else{
			out.print("<span style='font-size:10px'> Not updated </span>");
		}
	}else{
		out.print("Student not registered");
	}
%>	</div>
	</form>
</div>
<script>

	function refreshEstados(){		
		
		jQuery('#TEstado').html('<option>Loading</option>');
		
		var paisId = document.frmubicacion.TPais.value;	
		
		jQuery.get('getEstados?paisId='+paisId, function(data){
			jQuery("#TEstado").html(data);
			refreshCiudades();
		});
		
	}
	
	function refreshCiudades(){
		jQuery('#TCiudad').html('<option>Loading</option>');
		
		var paisId = document.frmubicacion.TPais.value;
		var estadoId = document.frmubicacion.TEstado.value;
		
		jQuery.get('getCiudades?paisId='+paisId+'&estadoId='+estadoId, function(data){
			jQuery("#TCiudad").html(data);
		});
	}

	function refreshPEstados(){		
		
		jQuery('#PEstado').html('<option>Loading</option>');
		
		var paisId = document.frmubicacion.PPais.value;	
		
		jQuery.get('getEstados?paisId='+paisId, function(data){
			jQuery("#PEstado").html(data);
			refreshCiudades();
		});
		
	}
	
	function refreshPCiudades(){
		jQuery('#PCiudad').html('<option>Loading</option>');
		
		var paisId = document.frmubicacion.PPais.value;
		var estadoId = document.frmubicacion.PEstado.value;
		
		jQuery.get('getCiudades?paisId='+paisId+'&estadoId='+estadoId, function(data){
			jQuery("#PCiudad").html(data);
		});
	}

	function refreshMEstados(){		
		
		jQuery('#MEstado').html('<option>Loading</option>');
		
		var paisId = document.frmubicacion.MPais.value;	
		
		jQuery.get('getEstados?paisId='+paisId, function(data){
			jQuery("#MEstado").html(data);
			refreshCiudades();
		});
		
	}
	
	function refreshMCiudades(){
		jQuery('#MCiudad').html('<option>Loading</option>');
		
		var paisId = document.frmubicacion.MPais.value;
		var estadoId = document.frmubicacion.MEstado.value;
		
		jQuery.get('getCiudades?paisId='+paisId+'&estadoId='+estadoId, function(data){
			jQuery("#MCiudad").html(data);
		});
	}
	
</script>