<%@ page import= "java.security.MessageDigest" %>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>

<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Alumno" scope="page" class="aca.musica.MusiAlumno"/>
<jsp:useBean id="InstitucionU" scope="page" class="aca.musica.MusiInstitucionUtil"/>
<jsp:useBean id="Padres" scope="page" class="aca.musica.MusiPadres"/>
<jsp:useBean id="PadresU" scope="page" class="aca.musica.MusiPadresUtil"/>
<jsp:useBean id="CatReligionDao" scope="page" class="aca.catalogo.CatReligionDao"/>
<jsp:useBean id="Movimiento" scope="page" class="aca.musica.MusiMovimiento"/>
<jsp:useBean id="Ubicacion" scope="page" class="aca.alumno.AlumUbicacion"/>
<jsp:useBean id="UbicacionU" scope="page" class="aca.alumno.UbicacionUtil"/>

<head>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script type="text/javascript">

	function Nuevo(){
		document.frmPersonal.Codigo.value			= "0";
		document.frmPersonal.Nombre.value			= "";
		document.frmPersonal.ApellidoPaterno.value 	= "";
		document.frmPersonal.ApellidoMaterno.value 	= "";
		document.frmPersonal.FechaNac.value			= "";
		document.frmPersonal.Genero.value			= "";
		document.frmPersonal.InstitucionId.value	= "6";
		document.frmPersonal.Seguro.value			= "N";
		document.frmPersonal.Tutor.value			= "";
		document.frmPersonal.Telefono.value			= "1";
		document.frmPersonal.Celular.value			= "1";
		document.frmPersonal.Email.value			= "1";
		document.frmPersonal.CodigoUM.value			= "0";
		document.frmPersonal.Comentario.value		= "";
		document.frmPersonal.Empleado.value		    = "";
		document.frmPersonal.ReligionId.value		= "";
		document.frmPersonal.CodigoUsuario.value	= "";
		document.frmPersonal.CiudadId.value			= "";
		document.frmPersonal.EstadoId.value			= "";
		document.frmPersonal.PiasId.value			= "";
		document.frmPersonal.Nacionalidad.value		= "";
		document.frmPersonal.TelTrabajo.value		= "";
		document.frmPersonal.Estado.value			= "";
		document.frmPersonal.Accion.value			= "1";		
		document.frmPersonal.submit();		
	}
	
	function Grabar(){
		if( document.frmPersonal.Nombre!="" && document.frmPersonal.ApellidoPaterno.value!="" && document.frmPersonal.ApellidoMaterno.value!=""
			&& document.frmPersonal.FechaNac.value!="" && document.frmPersonal.Empleado.value!="" ){
			document.frmPersonal.Accion.value	= "2";		
			document.frmPersonal.submit();			
		}else{
			alert("Complete todos los campos!");
		}
	}	
	
	function Borrar( ){
		if(document.frmPersonal.Codigo.value!=""){
			if(confirm("¿Estás seguro de eliminar el registro?")==true){
	  			document.frmPersonal.Accion.value="4";
				document.frmPersonal.submit();
			}			
		}else{
			alert("Escriba el Codigo!");
			document.frmPersonal.CodigoId.focus(); 
	  	}
	}
	
	function Consultar(){
		document.frmPersonal.Accion.value="5";
		document.frmPersonal.submit();		
	}
	
	function GrabarPadres( ){
		document.forma.accion.value = "1";
		document.forma.submit();
	}
	
	function camara(){
		location.href="tomarfoto";
	}
	
	function refreshEstados(){
		
		jQuery('#EstadoId').html('<option>Actualizando</option>');
		
		var paisId = document.frmPersonal.PaisId.value;
		
		jQuery.get('getEstados?paisId='+paisId, function(data){
			jQuery("#EstadoId").html(data);
			refreshCiudades();
		});
		
	}
	
	function refreshCiudades(){
		jQuery('#CiudadId').html('<option>Actualizando</option>');
		
		var paisId = document.frmPersonal.PaisId.value;
		var estadoId = document.frmPersonal.EstadoId.value;
		
		jQuery.get('getCiudades?paisId='+paisId+'&estadoId='+estadoId, function(data){
			jQuery("#CiudadId").html(data);
		});
	}
	
</script>
</script>

<%
	// Declaracion de variables
	String codigoId			= (String) session.getAttribute("CodigoId");
	String codigo 			= request.getParameter("Codigo");
	String codigoPersonal 	= (String) session.getAttribute("codigoPersonal");
	String activar 			= "NO"; //Variable para activar o desactivar los combos de Institucion y Seguro
	String nomInstitucion 		= (String)session.getAttribute("institucion");
	
	aca.catalogo.PaisUtil paisU = new aca.catalogo.PaisUtil();
	ArrayList<aca.catalogo.CatPais> lisPais	 	= paisU.getListAll(conEnoc,"ORDER BY 2");
	
	ArrayList<aca.catalogo.CatEstado> lisEstado		= new ArrayList<aca.catalogo.CatEstado>();
	
	if(codigoPersonal.equals("9800308") || codigoPersonal.equals("9800739")){
		activar = "SI";
	}
	
	if (codigo!=null){
		codigoId = codigo;
		session.setAttribute("CodigoId",codigoId);
	}	
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");		
	int numAccion			= Integer.parseInt(accion);	
	String year				= request.getParameter("Year")==null?aca.util.Fecha.getHoy().substring(6,10):request.getParameter("Year");
	int i 					= 0;
	String resultado		= "";
	String inst				= "";	 
	
	if (numAccion==1){
		codigoId = Alumno.maximoReg(conEnoc, year.substring(2,4));
	}else{		
		Alumno.setCodigoId(codigoId);
		Alumno.mapeaRegId(conEnoc,codigoId);
	}

	// Operaciones a realizar en la pantalla
	switch (numAccion){
		case 1: { // Nuevo
			resultado = "Llene el formulario correctamente ..!!";
			Alumno.setCodigoId(Alumno.maximoReg(conEnoc, year.substring(2,4)));
			Alumno.setInstitucionId("6");
			Alumno.setSeguro("N");		
			break;
		}		
		case 2: { // Grabar
			
			if (request.getParameter("Codigo")==null || request.getParameter("Codigo").equals("0"))
				Alumno.setCodigoId(Alumno.maximoReg(conEnoc, year.substring(2,4)));
			else
				Alumno.setCodigoId(request.getParameter("Codigo"));
		
			if (request.getParameter("InstitucionId")==null || request.getParameter("InstitucionId").equals("")){
				inst = "6";
			}else{
				inst = request.getParameter("InstitucionId");
			}
			
			//Alumno.getCodigoId(Alumno.getCodigoId()));
			Alumno.setNombre(request.getParameter("Nombre"));
			Alumno.setApellidoPaterno(request.getParameter("ApellidoPaterno"));
			Alumno.setApellidoMaterno(request.getParameter("ApellidoMaterno"));
			Alumno.setFechaNac(request.getParameter("FechaNac"));
			Alumno.setGenero(request.getParameter("Genero"));
			Alumno.setInstitucionId(inst);
			Alumno.setSeguro(request.getParameter("Seguro")==null?"N":request.getParameter("Seguro"));
			Alumno.setTutor(request.getParameter("Tutor"));
			Alumno.setTelefono(request.getParameter("Telefono"));
			Alumno.setCelular(request.getParameter("Celular"));
			Alumno.setEmail(request.getParameter("Email"));
			Alumno.setCodigoUM(request.getParameter("CodigoUM"));
			Alumno.setComentario(request.getParameter("Comentario"));
			Alumno.setEmpleado(request.getParameter("Empleado"));
			Alumno.setReligionId(request.getParameter("ReligionId"));
			Alumno.setCodigoUsuario(request.getParameter("CodigoUsuario"));
			Alumno.setCiudadId(request.getParameter("CiudadId"));
			Alumno.setEstadoId(request.getParameter("EstadoId"));
			Alumno.setPaisId(request.getParameter("PaisId"));
			Alumno.setNacionalidad(request.getParameter("Nacionalidad"));
			Alumno.setTelTrabajo(request.getParameter("TelTrabajo"));
			Alumno.setEstado(request.getParameter("Estado"));
			
			if (Alumno.existeReg(conEnoc) == false){
				if (Alumno.insertReg(conEnoc)){
					resultado = "Grabado: "+Alumno.getCodigoId();			
				}else{
					resultado = "No Grabó: "+Alumno.getCodigoId();
				}
			}else{
				if (Alumno.updateReg(conEnoc)){
					resultado = "Modificado: "+Alumno.getCodigoId();					
				}else{
					resultado = "No se modificó: "+Alumno.getCodigoId();
				}				
			}
			
			break;
		}
       
		case 4: { // Borrar
			if (Alumno.existeReg(conEnoc) == true){
				if(Movimiento.existeAlumno(conEnoc, Alumno.getCodigoId()) == false){
					if (Alumno.deleteReg(conEnoc)){
						resultado = "Borrado: "+Alumno.getCodigoId();						
					}else{
						resultado = "No se borró: "+Alumno.getCodigoId();
				    }
				}else{
					resultado = "No se puede borrar porque el alumno "+Alumno.getCodigoId() + " tiene movimiento";
				}
			break;
			}
		}
		
		case 5: { // Consultar			
			if (Alumno.existeReg(conEnoc) == true){
				Alumno.mapeaRegId(conEnoc, request.getParameter("CodigoId"));
				resultado = "Consulta";
			}else{
				resultado = "No existe: "+Alumno.getCodigoId();
			}	
			break;			
		}		
	}
	
	ArrayList lisInstitucion = InstitucionU.getListAll(conEnoc,"ORDER BY 1");
	ArrayList lisReligion 	 = CatReligionDao.getListAll(conEnoc, "ORDER BY 1");
	
	boolean tieneFoto 			= false; 
	
	// Verifica si existe la imagen	
	String dirFoto = application.getRealPath("/WEB-INF/fotos/"+codigoId+".jpg");
	java.io.File foto = new java.io.File(dirFoto);
	if (foto.exists()){
		tieneFoto = true;
	}	
%>
<div class="container-fluid">
<h1>Datos del Alumno</h1>
<form action="datos" method="post" name="frmPersonal" target="_self">
<input type="hidden" name="Accion">
<div class="alert alert-info">
	<tr><td class="titulo"><%=nomInstitucion%>, A.C.</td><tr>
	<tr><td align="center" style="font-size:11pt; font-weight:bold;">Conservatorio</td><tr>
	<tr><td align="center"><a class="btn btn-primary" href="buscar">Buscar Alumno</a></td><tr>
</div>
<table style="width:70%" class="table table-fullcondensed">
	<tr>
    	<th align="center">Datos Personales</th>
    </tr>
    <tr>
    	<td>
	  		<table style="width:100%" >
	  		<tr>
	  			<td><strong><spring:message code="aca.Matricula"/>:</strong></td>
	  			<td>
		  			<input name="Codigo" type="text" class="text" id="Codigo" size="7" maxlength="5" value="<%=Alumno.getCodigoId()==null ? "0" : Alumno.getCodigoId()%>">
		  			<select name="Year">
			  			<option value="2007" <% if (year.equals("2007")) out.print("selected"); %>>2007</option>
						<option value="2008" <% if (year.equals("2008")) out.print("selected"); %>>2008</option>
						<option value="2009" <% if (year.equals("2009")) out.print("selected"); %>>2009</option>
						<option value="2010" <% if (year.equals("2010")) out.print("selected"); %>>2010</option>
						<option value="2011" <% if (year.equals("2011")) out.print("selected"); %>>2011</option>
						<option value="2012" <% if (year.equals("2012")) out.print("selected"); %>>2012</option>
						<option value="2013" <% if (year.equals("2013")) out.print("selected"); %>>2013</option>
						<option value="2014" <% if (year.equals("2014")) out.print("selected"); %>>2014</option>
						<option value="2015" <% if (year.equals("2015")) out.print("selected"); %>>2015</option>
						<option value="2016" <% if (year.equals("2016")) out.print("selected"); %>>2016</option>
						<option value="2017" <% if (year.equals("2017")) out.print("selected"); %>>2017</option>
						<option value="2018" <% if (year.equals("2018")) out.print("selected"); %>>2018</option>
					</select>
				</td>
				<td rowspan='13' align='center' valign="top">
		  	  		<img src="foto?id=<%=codigoId%>.jpg" width="150"><br><br>
		  	  		<input class="btn btn-primary" type='button' value='Tomar Foto' onclick='camara()'><br>
		  	  		<a class="btn btn-primary" href="subir">Subir Foto</a>
<% 	if (tieneFoto){%>
	              	&nbsp; &nbsp;
	              	<a class="btn btn-danger" href="borrar"><spring:message code='aca.Borrar'/></a>
<% 	}%>
				</td>
			</tr>
			<tr>
				<td><strong><spring:message code="aca.Nombre"/>:</strong></td>
				<td><input name="Nombre" type="text" class="text" id="Nombre" size="40" maxlength="70" value="<%=Alumno.getNombre()%>"></td>
			</tr>
			<tr>
				<td><strong>A.Paterno:</strong></td>
				<td><input name="ApellidoPaterno" type="text" class="text" id="ApellidoPaterno" size="70" maxlength="40" value="<%=Alumno.getApellidoPaterno()%>"></td>
			</tr>
			<tr>
	      		<td><strong>A.Materno:</strong></td>
          		<td><input name="ApellidoMaterno" type="text" class="text" id="ApellidoMaterno" size="70" maxlength="40" value="<%=Alumno.getApellidoMaterno()%>"></td>
          	</tr>
          	<tr>
          		<td><strong><spring:message code="aca.Fecha"/>:</strong></td>
          		<td><input name="FechaNac" type="text" class="text" id="FechaNac" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=Alumno.getFechaNac()%>">(DD/MM/AAAA)</td>
          	</tr>
          	<tr>
          		<td><strong>Sexo:</strong></td>
          		<td>
          			<select name="Genero">
           				<option <% if (Alumno.getSeguro().equals("M")) out.print("selected");%> value="M">Masculino</option>
           				<option <% if (Alumno.getSeguro().equals("F")) out.print("selected");%> value="F">Femenino</option>		 	
           			</select>
          		</td>
          	</tr>
          	<tr>
          		<td><strong><spring:message code="aca.Religion"/>:</strong></td>
          		<td>
          		<select name="ReligionId" id="ReligionId">
<%
	for (int j=0; j<lisReligion.size(); j++){
		aca.catalogo.CatReligion religion = (aca.catalogo.CatReligion) lisReligion.get(j);
%>
				<option <%if( Alumno.getReligionId().equals(religion.getReligionId()))out.print(" Selected ");%> value="<%= religion.getReligionId() %>"><%= religion.getNombreReligion() %></option>
<%	}%>
            	</select>
            	</td>
            </tr>
            <tr>
            	<td><strong><spring:message code="aca.Institucion"/>:</strong></td>
            	<td>
			<% if(activar.equals("SI")){ %>
					<select name="InstitucionId">
			<%
	for (int j=0; j<lisInstitucion.size(); j++){
		aca.musica.MusiInstitucion institucion = (aca.musica.MusiInstitucion) lisInstitucion.get(j);
			%>
					<option <%if( Alumno.getInstitucionId().equals(institucion.getInstitucionId()))out.print(" Selected ");%> value="<%= institucion.getInstitucionId() %>"><%= institucion.getInstitucionNombre()%></option>
			<% } %>
            		</select>	
          <% }else{%>
          			<input name="inst" type="text" class="text" size="35" value="<%=aca.musica.MusiInstitucion.getNombreInstitucion(conEnoc, Alumno.getInstitucionId()) %>" readonly>
          			<input name="InstitucionId" id="InstitucionId" type="hidden" class="text" size="35" value="<%= Alumno.getInstitucionId() %>" >
          <% } %>
          		</td>
          	</tr>
          	<tr>
          		<td><strong><spring:message code="aca.Seguro"/>:</strong></td>
          		<td>
           <%if(activar.equals("SI")){ %>
           			<select name="Seguro">
           				<option <% if (Alumno.getSeguro().equals("S")) out.print("selected");%> value="S"><spring:message code='aca.Si'/></option>
           				<option <% if (Alumno.getSeguro().equals("N")) out.print("selected");%> value="N"><spring:message code='aca.No'/></option>		 	
           			</select>
		   <% }else{
		   		String seguro = Alumno.getSeguro().equals("S")?"Si":"No";
		   %>
          			<input name="seg" type="text" class="text" size="1" value="<%=seguro %>" readonly>
         			<input name="Seguro" id="Seguro" type="hidden" class="text" size="35" value="<%= Alumno.getSeguro() %>" >
           <%} %>
           		</td>
           	</tr>
           	<tr>
           		<td><strong><spring:message code="aca.Tutor"/>:</strong></td>
           		<td><input name="Tutor" type="text" class="text" id="Tutor" size="40" maxlength="100" value="<%=Alumno.getTutor()%>"></td>
           	</tr>
           	<tr>
           		<td><strong><spring:message code='aca.Telefono'/>:</strong></td>
           		<td><input name="Telefono" type="text" class="text" id="Telefono" size="40" maxlength="30" value="<%=Alumno.getTelefono()%>"></td>
           	</tr>
           	<tr>
           		<td><strong><spring:message code="aca.Cel"/>ular:</strong></td>
           		<td><input name="Celular" type="text" class="text" id="Celular" size="40" maxlength="50" value="<%=Alumno.getCelular()%>"></td>
           	</tr>
           	<tr>
           		<td><strong>Email:</strong></td>
           		<td><input name="Email" type="text" class="text" id="Email" size="40" maxlength="70" value="<%=Alumno.getEmail()%>"></td>
           	</tr>
           	<tr>
           		<td><strong><spring:message code="aca.Codigo"/>UM</strong></td>
           		<td><input name="CodigoUM" type="text" class="text" id="CodigoUM" size="8" maxlength="7" value="<%=Alumno.getCodigoUM()%>" readonly></td>
           	</tr>
           	<tr>
           		<td><strong><spring:message code="aca.Comentario"/></strong></td>
           		<td><input name="Comentario" type="text" class="text" id="Comentario" size="40" maxlength="100" value="<%=Alumno.getComentario()%>"></td>
           	</tr>
           	<tr>
           		<td><strong>N° Nomina</strong></td>
           		<td><input name="Empleado" type="text" class="text" id="Empleado" size="8" maxlength="7" value="<%=Alumno.getEmpleado()%>"></td>
           	</tr>
           	<tr>
           		<td><strong>C. Usuario</strong></td>
           		<td><input name="CodigoUsuario" type="text" class="text" id="CodigoUsuario" size="8" maxlength="7" value="<%=Alumno.getEmpleado()%>"></td>
           	</tr>
           	<tr>
           		<td><strong>Pa&iacute;s:</strong></td>
           		<td>
           			<select name="PaisId" id="PaisId" onchange="javascript:refreshEstados();">
	                	<%
							for( i=0;i<lisPais.size();i++){
								aca.catalogo.CatPais pais = (aca.catalogo.CatPais) lisPais.get(i);
								if (pais.getPaisId().equals(Alumno.getPaisId())){
									out.print(" <option value='"+pais.getPaisId()+"' Selected>"+ pais.getNombrePais()+"</option>");
								}else{
									out.print(" <option value='"+pais.getPaisId()+"'>"+ pais.getNombrePais()+"</option>");
								}
							}
				  		%>
            		</select>
				</td>
			</tr>
			<tr>
				<td><strong><spring:message code="aca.Estado"/>:</strong></td>
				<td>
					<select name="EstadoId" id="EstadoId" onchange="javascript:refreshCiudades();">
						<%
							aca.catalogo.EstadoUtil estadoU = new aca.catalogo.EstadoUtil();
							lisEstado = estadoU.getLista(conEnoc,Alumno.getPaisId(),"ORDER BY 1,3");
							for( i=0;i<lisEstado.size();i++){
								aca.catalogo.CatEstado estado = (aca.catalogo.CatEstado) lisEstado.get(i);
								if (estado.getEstadoId().equals(Alumno.getEstadoId())){
									out.print(" <option value='"+estado.getEstadoId()+"' Selected>"+ estado.getNombreEstado()+"</option>");
								}else{
									out.print(" <option value='"+estado.getEstadoId()+"'>"+ estado.getNombreEstado()+"</option>");
								}
							}
							lisEstado 		= null;				
							estadoU	= null;
				  		%>
              		</select>
              	</td>
            </tr>
            <tr>
            	<td><strong>Ciudad:</strong></td>
            	<td>
            		<select name="CiudadId" id="CiudadId">
		                <%			  	
							aca.catalogo.CiudadUtil ciudadU = new aca.catalogo.CiudadUtil();
							ArrayList<aca.catalogo.CatCiudad> lisCiudad = ciudadU.getLista(conEnoc,Alumno.getPaisId(),Alumno.getEstadoId(),"ORDER BY 4");
							for( i=0;i<lisCiudad.size();i++){
								aca.catalogo.CatCiudad ciudad = (aca.catalogo.CatCiudad) lisCiudad.get(i);					
								if (ciudad.getCiudadId().equals(Alumno.getCiudadId())){
									out.print(" <option value='"+ciudad.getCiudadId()+"' Selected>"+ ciudad.getNombreCiudad()+"</option>");						
								}else{
									out.print(" <option value='"+ciudad.getCiudadId()+"'>"+ ciudad.getNombreCiudad()+"</option>");
								}
							}
						%>
					</select>
				</td>
			</tr>
			<tr>
				<td><strong><spring:message code="aca.Nacionalidad"/>:</strong></td>
				<td>
					<select name="Nacionalidad" id="Nacionalidad" tabindex="12">
		                <%				
							for( i=0;i<lisPais.size();i++){
								aca.catalogo.CatPais pais = (aca.catalogo.CatPais) lisPais.get(i);
								if (pais.getPaisId().equals(Alumno.getNacionalidad())){
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
				<td><strong>Tel. Trabajo</strong></td>
				<td><input name="TelTrabajo" type="text" class="text" id="TelTrabajo" size="12" value="<%=Alumno.getTelTrabajo()%>"></td>
			</tr>
			<tr>
				<td><strong>Tipo</strong></td>
				<td>
					<select name="Estado" id="Estado">
			  			<option value="P" <% if (Alumno.getEstado().equals("P")) out.print("selected"); %>>1. En proceso</option>
						<option value="S" <% if (Alumno.getEstado().equals("S")) out.print("selected"); %>>2. Solicitante</option>
					</select>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center"><%=resultado%></td>
			</tr>
			<tr>
				<th colspan="3" style="text-align:center;">
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
<%//------------------------------------------------->>>>>>>>>>>>>>>>>>>>>>>> Seccion de Papa y Mama<<<<<<<<<<<<<<<<<<<<<<<<<<------------------ 

	
if(Alumno.existeReg(conEnoc)){
	
	Padres.mapeaRegId(conEnoc,codigoId);
	
	String accion2 			= request.getParameter("accion")==null?"0":request.getParameter("accion");
	String sResultado 		= "";
	if(accion2.equals("1")){
		Padres.setCodigoId(Alumno.getCodigoId());
		Padres.setPadNombre(request.getParameter("padNombre").equals("")?"-":request.getParameter("padNombre"));
		Padres.setPadPaterno(request.getParameter("padPaterno").equals("")?"-":request.getParameter("padPaterno"));
		Padres.setPadMaterno(request.getParameter("padMaterno").equals("")?"-":request.getParameter("padMaterno"));
		Padres.setPadDireccion(request.getParameter("padDireccion").equals("")?"-":request.getParameter("padDireccion"));
		Padres.setPadCorreo(request.getParameter("padCorreo").equals("")?"-":request.getParameter("padCorreo"));
		Padres.setPadOcupacion(request.getParameter("padOcupacion").equals("")?"-":request.getParameter("padOcupacion"));
		Padres.setPadTelcasa(request.getParameter("padTelcasa").equals("")?"-":request.getParameter("padTelcasa"));
		Padres.setPadTeltrabajo(request.getParameter("padTeltrabajo").equals("")?"-":request.getParameter("padTeltrabajo"));
		Padres.setPadTelcelular(request.getParameter("padTelcelular").equals("")?"-":request.getParameter("padTelcelular"));
		Padres.setMadNombre(request.getParameter("madNombre").equals("")?"-":request.getParameter("madNombre"));
		Padres.setMadPaterno(request.getParameter("madPaterno").equals("")?"-":request.getParameter("madPaterno"));
		Padres.setMadMaterno(request.getParameter("madMaterno").equals("")?"-":request.getParameter("madMaterno"));
		Padres.setMadOcupacion(request.getParameter("madOcupacion").equals("")?"-":request.getParameter("madOcupacion"));
		Padres.setMadDireccion(request.getParameter("madDireccion").equals("")?"-":request.getParameter("madDireccion"));
		Padres.setMadCorreo(request.getParameter("madCorreo").equals("")?"-":request.getParameter("madCorreo"));
		Padres.setMadTelcasa(request.getParameter("madTelcasa").equals("")?"-":request.getParameter("madTelcasa"));
		Padres.setMadTeltrabajo(request.getParameter("madTeltrabajo").equals("")?"-":request.getParameter("madTeltrabajo"));
		Padres.setMadTelcelular(request.getParameter("madTelcelular").equals("")?"-":request.getParameter("madTelcelular"));
		Padres.setPadVive(request.getParameter("padVive"));
		Padres.setMadVive(request.getParameter("madVive"));
		Padres.setCodigoUsuario(request.getParameter("codigoUsuario"));
		Padres.setPadReligionId(request.getParameter("padReligionId"));
		Padres.setMadReligionId(request.getParameter("madReligionId"));
		
		if(Padres.existeReg(conEnoc, codigoId)==false){//Grabar
			if (Padres.insertReg(conEnoc)){
				sResultado = "Grabado";				
			}else{
				sResultado = "No Grabó";
			}
		}else{//Modificar
			if (Padres.updateReg(conEnoc)){
				sResultado = "Modificado";				
			}else{
				sResultado = "No Modificó";
			}	
		}
	}
%>
<h1>Datos del Alumno</h1>
<form action="datos" method="post" name="forma" target="_self">
<input type="hidden" name="accion">
<table style="width:70%">
	<tr>
		<td>
			<table style="width:80%" class="table table-fullcondensed"   bordercolor="#000000">
		  	<tr>
		   		<th align="center"><font size="2">Datos del Padre</font></th>
		  	</tr>
		  	<tr>
		    	<td>
			  		<table style="width:100%" >
			  	  		<tr> 
		          			<td width="30%"><strong>C&oacute;digo:</strong></td>
		            		<td width="76%"><input name="codigo" type="text" id="codigo" size="5" maxlength="5" value="<%=Alumno.getCodigoId() %>" readonly>
		          		</tr>
			  	  		<tr>
				  			<td align="right" colspan="2" onchange="Combo()"><strong>Vive:</strong>
				  				<select id="padVive" name="padVive">
				  					<option value="S" <%=Padres.getPadVive().equals("S")?" Selected":""%>><spring:message code='aca.Si'/></option>
				  					<option value="N" <%=Padres.getPadVive().equals("N")?" Selected":""%>><spring:message code='aca.No'/></option>
				  				</select>
				  			</td>
				  		</tr>
				  		<tr> 
		            		<td width="30%"><strong><spring:message code="aca.Nombre"/>:</strong></td>
		            		<td width="76%"><input name="padNombre" type="text" id="padNombre" size="30" maxlength="50" value="<%=Padres.getPadNombre() %>">
		            	</tr>
		            	<tr>
		            		<td width="30%"><strong>A. Paterno:</strong></td>
		            		<td width="76%"><input name="padPaterno" type="text" id="padPaterno" size="30" maxlength="50" value="<%=Padres.getPadPaterno() %>">
		            	</tr>
		            	<tr>
		            		<td width="30%"><strong>A. Materno:</strong></td>
		            		<td width="76%"><input name="padMaterno" type="text" id="padMaterno" size="30" maxlength="50" value="<%=Padres.getPadMaterno() %>">
		            	</tr>
		            	<tr>
		            		<td width="30%"><strong>Ocupación:</strong></td>
		            		<td width="76%"><input name="padOcupacion" type="text" id="padOcupacion" size="30" maxlength="50" value="<%=Padres.getPadOcupacion() %>">
		            	</tr>
		            	<tr>
		            		<td width="30%"><strong>Dirección:</strong></td>
		            		<td width="76%"><input name="padDireccion" type="text" id="padDireccion" size="30" maxlength="150" value="<%=Padres.getPadDireccion() %>">
		            	</tr>
		          		<tr> 
		            		<td width="30%"><strong><spring:message code="aca.Correo"/>:</strong></td>
		            		<td width="76%"><input name="padCorreo" type="text" id="padCorreo" size="30" maxlength="100" value="<%=Padres.getPadCorreo() %>">
		          		</tr>
		          		<tr>
		            		<td width="30%"><strong>Tel. Casa:</strong></td>
		            		<td width="76%"><input name="padTelcasa" type="text" id="padTelcasa" size="15" maxlength="70" value="<%=Padres.getPadTelcasa() %>">
		          		</tr>
		          		<tr> 
		            		<td width="30%"><strong>Tel. Trabajo:</strong></td>
		            		<td width="76%"><input name="padTeltrabajo" type="text" id="padTeltrabajo" size="15" maxlength="70" value="<%=Padres.getPadTeltrabajo() %>">
		         	 	</tr>
		          		<tr> 
		            		<td width="30%"><strong><spring:message code="aca.Cel"/>ular:</strong></td>
		            		<td width="76%"><input name="padTelcelular" type="text" id="padTelcelular" size="15" maxlength="70" value="<%=Padres.getPadTelcelular() %>">
		          		</tr>
		         		<tr>
	      					<td><strong><spring:message code="aca.Religion"/>:</strong></td>
          					<td>
            					<select name="padReligionId" id="padReligionId">
<%
	for (int j=0; j<lisReligion.size(); j++){
		aca.catalogo.CatReligion religion = (aca.catalogo.CatReligion) lisReligion.get(j);
%>
									<option <%if( Padres.getPadReligionId().equals(religion.getReligionId()))out.print(" Selected ");%> value="<%= religion.getReligionId() %>"><%= religion.getNombreReligion() %></option>
<%	}%>
            					</select>		    
		  					</td>
						</tr>
		 				<tr> 
		 					<td width="30%"><strong>C. Usuario:</strong></td>
		    				<td width="76%"><input name="codigoUsuario" type="text" id="codigoUsuario" size="8" maxlength="8" value="<%=Padres.getCodigoUsuario()%>">
		 				</tr>
		 			</table>
				</td>
			</tr>
			</table>
		</td>
		
		<td valign="bottom">
			<table>
				<tr>
					<td align="center" colspan="2"><input class="btn btn-primary" type="button" value="Grabar" id="grabar" onclick="GrabarPadres()" /></td>
				</tr>
		 		<tr>
            		<td colspan="2" align="center"><%=sResultado%></td>
          		</tr>
			</table>
		</td>
		<td>
			<table style="width:80%" class="table table-fullcondensed"   bordercolor="#000000">
  				<tr>
   					<th align="center"><font size="2">Datos de la Madre</font></th>
  				</tr>
  				<tr>
    				<td>
	  					<table style="width:100%" >
				  	 		<tr>
				   				<td width="30%"><strong>C&oacute;digo:</strong></td>
					    		<td width="76%"><input name="codigo" type="text" id="codigo" size="5" maxlength="5" value="<%=Padres.getCodigoId() %>" readonly>
				   			</tr>
				  	   		<tr>
								<td align="right" colspan="2" onchange="Combo()"><strong>Vive:</strong>
							  		<select id="madVive" name="madVive">
								 		<option value="S" <%=Padres.getMadVive().equals("S")?" Selected":""%>><spring:message code='aca.Si'/></option>
								 		<option value="N" <%=Padres.getMadVive().equals("N")?" Selected":""%>><spring:message code='aca.No'/></option>
							  		</select>
								</td>
					  		</tr> 
			           		<tr> 
					            <td width="30%"><strong><spring:message code="aca.Nombre"/>:</strong></td>
					            <td width="76%"><input name="madNombre" type="text" id="madNombre" size="30" maxlength="50" value="<%=Padres.getMadNombre() %>">
					        </tr>
					        <tr> 
					            <td width="30%"><strong>A. Paterno:</strong></td>
					            <td width="76%"><input name="madPaterno" type="text" id="madPaterno" size="30" maxlength="50" value="<%=Padres.getMadPaterno() %>">
					        </tr>
					        <tr>
					       		<td width="30%"><strong>A. Materno:</strong></td>
					            <td width="76%"><input name="madMaterno" type="text" id="madMaterno" size="30" maxlength="50" value="<%=Padres.getMadMaterno() %>">				
					        </tr>
					        <tr>
					        	<td width="30%"><strong>Ocupación:</strong></td>
					            <td width="76%"><input name="madOcupacion" type="text" id="madOcupacion" size="30" maxlength="50" value="<%=Padres.getMadOcupacion() %>">
					        </tr>
					        <tr>
					        	<td width="30%"><strong>Dirección:</strong></td>
					            <td width="76%"><input name="madDireccion" type="text" id="madDireccion" size="30" maxlength="150" value="<%=Padres.getMadDireccion() %>">
					        </tr>
					        <tr>
					        	<td width="30%"><strong><spring:message code="aca.Correo"/>:</strong></td>
					            <td width="76%"><input name="madCorreo" type="text" id="madCorreo" size="30" maxlength="100" value="<%=Padres.getMadCorreo() %>">
					        </tr>
					        <tr>
					        	<td width="30%"><strong>Tel. Casa:</strong></td>
					            <td width="76%"><input name="madTelcasa" type="text" id="madTelcasa" size="15" maxlength="70" value="<%=Padres.getMadTelcasa() %>">
					        </tr>
					        <tr>
					        	<td width="30%"><strong>Tel. Trabajo:</strong></td>
					            <td width="76%"><input name="madTeltrabajo" type="text" id="madTeltrabajo" size="15" maxlength="70" value="<%=Padres.getMadTeltrabajo() %>">
					        </tr>
					        <tr> 
					        	<td width="30%"><strong><spring:message code="aca.Cel"/>ular:</strong></td>
					            <td width="76%"><input name="madTelcelular" type="text" id="madTelcelular" size="15" maxlength="70" value="<%=Padres.getMadTelcelular() %>">
					        </tr>
					       	<tr>
				      			<td><strong><spring:message code="aca.Religion"/>:</strong></td>
			          			<td>
			            			<select name="madReligionId" id="madReligionId">
<%
	for (int j=0; j<lisReligion.size(); j++){
		aca.catalogo.CatReligion religion = (aca.catalogo.CatReligion) lisReligion.get(j);
%>
										<option <%if( Padres.getMadReligionId().equals(religion.getReligionId()))out.print(" Selected ");%> value="<%= religion.getReligionId() %>"><%= religion.getNombreReligion() %></option>
<%	}%>              
            						</select>		    
		  						</td>
							</tr>
		 					<tr> 
		 						<td width="30%"><strong>C. Usuario:</strong></td>
		    					<td width="76%"><input name="codigoUsuario" type="text" id="codigoUsuario" size="8" maxlength="8" value="<%=Padres.getCodigoUsuario()%>">
		 					</tr>
        				</table>
					</td>
  				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
<%}//fin de if de padres %>
</div>
</body>
</html>
<script>
	jQuery('#FechaNac').datepicker();
</script>
<%@ include file= "../../cierra_enoc.jsp" %>