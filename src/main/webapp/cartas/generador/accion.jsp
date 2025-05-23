<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="AlumConstancia" scope="page" class="aca.alumno.AlumConstancia"/>
<jsp:useBean id="AlumConstanciaU" scope="page" class="aca.alumno.AlumConstanciaUtil"/>
<jsp:useBean id="empU" scope="page" class="aca.emp.EmpleadoUtil"/>
<jsp:useBean id="Fecha" scope="page" class="aca.util.Fecha" />


<%

	String usuario				= (String)session.getAttribute("codigoPersonal");
	String constanciaId         = request.getParameter("constanciaId")==null?"":request.getParameter("constanciaId");
	
	String msj					= "";
	String accion 				= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	
	if(accion.equals("1")){//Guardar
		
		if(constanciaId.equals("")){
			AlumConstancia.setConstanciaId(AlumConstanciaU.maximoReg(conEnoc));	
			constanciaId = AlumConstancia.getConstanciaId();
		}else{
			AlumConstancia.setConstanciaId(constanciaId);
		}
			
		AlumConstancia.setConstanciaNombre(request.getParameter("nombre"));
		AlumConstancia.setTipo(request.getParameter("tipo"));
		AlumConstancia.setConstancia(request.getParameter("constancia"));

		
		String USUARIOS = "-"+usuario+"-";
		String[] usuarios = request.getParameterValues("usuarios")==null?new String[0]:request.getParameterValues("usuarios");
		for(String usr : usuarios){
			USUARIOS += usr+"-";
		}
		
		AlumConstancia.setUsuarios(USUARIOS);
		
		
		if( AlumConstanciaU.existeReg(conEnoc, constanciaId) ){
			if(AlumConstanciaU.updateReg(conEnoc, AlumConstancia)){
	       		msj = "<div class='alert alert-success'>Se modificó correctamente</div>";
	       		
			}else{
				msj = "<div class='alert alert-danger'>Ocurrió un error al modificar</div>"; 
			}
		}else{
			if(AlumConstanciaU.insertReg(conEnoc, AlumConstancia)){
				msj = "<div class='alert alert-success'><spring:message code='aca.SeGuardoCorrectamente'/></div>";
			}else{
				msj = "<div class='alert alert-danger'>Ocurrió un error al guardar</div>";
			}
		}
	}

	
	if(!constanciaId.equals("")){
		AlumConstanciaU.mapeaRegId(conEnoc, constanciaId);
	}

	ArrayList<String> empleados = empU.getListEmpleadosNombre(conEnoc, "ORDER BY CLAVE");
%>

<div class="container-fluid">
	
	<h2>
	<%
		if(constanciaId.equals("")){		
	%>
			Añadir
	<%
		}else{
	%> 
			Editar
	<%
		}
	%>
	</h2>
	
	<%=msj %>
	
	<div class="alert alert-info">
		<a href="constancias" class="btn btn-primary">
			<i class="fas fa-arrow-left"></i>
		</a>
		
	<%
		if(!constanciaId.equals("")){		
	%>
		<a href="accion" class="btn btn-primary">
			<i class="icon-plus icon-white"></i> Nuevo
		</a>
	<%
		}
	%>
	</div>
	
	<form action="" method="post" name="forma">
		<input type="hidden" name="Accion" value="1" />
		<input type="hidden" name="constanciaId" value="<%=constanciaId%>" />
	
		<p>
			<label for="nombre"><spring:message code="aca.Nombre"/>:</label>
			<input class="input-xxlarge" type="text" id="nombre" name="nombre" value="<%=AlumConstancia.getConstanciaNombre() %>" />
		</p>
		
		<p>
			<label for="usuarios">Usuarios:</label>
			<select name="usuarios" id="usuarios" multiple class="chosen" style="width:95%;">
				<%for(String emp : empleados){%>
					<%
						String clave 	= emp.split("@@")[0];
						String nombre 	= emp.split("@@")[1];
					%>
					<option <%if(AlumConstancia.getUsuarios().contains("-"+clave+"-")){out.print("selected");} %> value="<%=clave %>" <%if(clave.equals(usuario)){out.print("disabled");} %>><%=clave%> | <%=nombre %></option>
				<%}%>
			</select>
		</p>
		
		<p>
			<label for="tipo"><spring:message code="aca.Tipo"/>:</label>
			<select name="tipo" id="tipo">
				<option value="NINGUNO" <%if(AlumConstancia.getTipo().equals("NINGUNO")){out.print("selected");} %>><spring:message code='aca.Ninguno'/></option>
				<option value="ESTUDIOS" <%if(AlumConstancia.getTipo().equals("ESTUDIOS")){out.print("selected");} %>>Estudios</option>
				<option value="CONDICIONADA" <%if(AlumConstancia.getTipo().equals("CONDICIONADA")){out.print("selected");} %>>Condicionada</option>
			</select>
		</p>
		
		<p>
		
			<label for="constancia">Constancia:</label>
			
			<div class="alert alert-info">
		  	
		  		<h5>Variables Personales:</h5>
		  		
		  		<span title="La foto del alumno">#Foto</span>
		  		&nbsp;
		  		<span title="Ejemplo: 1120562">#Matricula</span>
		  		&nbsp;
		  		<span title="Ejemplo: Juan">#Nombre</span>
		  		&nbsp;
		  		<span title="Ejemplo: Perez">#Paterno</span>
		  		&nbsp;
		  		<span title="Ejemplo: Martinez">#Materno</span>
		  		&nbsp;
		  		<span title="Ejemplo: Externo">#Residencia</span>
		  		&nbsp;
		  		<span title="Ejemplo: MUXA870212HNECXN00">#Curp</span>
		  		<span title="Dependiendo el genero pone la letra: 'a' si es mujer u 'o' si es hombre, puede ser utilizado por ejemplo cuando se hace referencia a 'alumno' o 'alumna'">#LetraGenero</span>
		  		
		  		<h5>Variables de Procedencia:</h5>
		  		
		  		<span title="Ejemplo: Mexicana">#Nacionalidad</span>
		  		&nbsp;
		  		<span title="Ejemplo: México">#Pais</span>
		  		&nbsp;
		  		<span title="Ejemplo: Nuevo León">#Estado</span>
		  		&nbsp;
		  		<span title="Ejemplo: Montemorelos">#Ciudad</span>
		  		
		  		<h5>Variables Academicas:</h5>
		  		  		
		  		<span title="Ejemplo: ISC2010*">#Plan</span>
		  		&nbsp;
		  		<span title="Ejemplo: Facultad de Ingeniería y Tecnología">#Facultad</span>
		  		&nbsp;
		  		<span title="Ejemplo: Ingeniería en Sistemas Computacionales">#Carrera</span>
		  		&nbsp;
		  		<span title="Ejemplo: cuarto">#Semestre</span>
		  		
		  		<h5>Variables de Fecha:</h5>
		  		
		  		<span title="Ejemplo: <%=aca.util.Fecha.getHoy() %>">#Fecha</span>
		  		&nbsp;
		  		<span title="Ejemplo: <%=aca.util.NumberToLetter.convertirLetras( Integer.parseInt(Fecha.getDia(aca.util.Fecha.getHoy())) )%>">#Dia</span>
		  		&nbsp;
		  		<span title="Ejemplo: <%=Fecha.getMesNombre( aca.util.Fecha.getHoy() ) %>">#Mes</span>
		  		&nbsp;
		  		<span title="Ejemplo: <%=aca.util.NumberToLetter.convertirLetras( Integer.parseInt(Fecha.getYear(aca.util.Fecha.getHoy())) ) %>">#Year</span>
		  		&nbsp;
		  		<span title="Ejemplo: <%=Fecha.getDia(aca.util.Fecha.getHoy()) %>">#DiaNumero</span>
		  		&nbsp;
		  		<span title="Ejemplo: <%=Fecha.getMes(aca.util.Fecha.getHoy()) %>">#MesNumero</span>
		  		&nbsp;
		  		<span title="Ejemplo: <%=Fecha.getYear(aca.util.Fecha.getHoy()) %>">#YearNumero</span>
		  		&nbsp;
		  		<span title="Ejemplo: <%=aca.util.Fecha.getPeriodoActual() %>">#PeriodoActual</span>
		  		
		  		<h5>Variables de Vacaciones:</h5>
		  		
		  		<span title="Ejemplo: 20">#UltimoDiaDeClases</span>
		  		&nbsp;
		  		<span title="Ejemplo: mayo">#UltimoMesDeClases</span>
		  		&nbsp;
		  		<span title="Ejemplo: 2014">#UltimoYearDeClases</span>
		  		&nbsp;
		  		<span title="Ejemplo: 21">#PrimerDiaDeVacaciones</span>
		  		&nbsp;
		  		<span title="Ejemplo: mayo">#PrimerMesDeVacaciones</span>
		  		&nbsp;
		  		<span title="Ejemplo: 2014">#PrimerYearDeVacaciones</span>
		  		&nbsp;
		  		<span title="Ejemplo: 18">#UltimoDiaDeVacaciones</span>
		  		&nbsp;
		  		<span title="Ejemplo: agosto">#UltimoMesDeVacaciones</span>
		  		&nbsp;
		  		<span title="Ejemplo: 2014">#UltimoYearDeVacaciones</span>
		  		&nbsp;
		  		<span title="Ejemplo: 19">#UltimoDiaDeVacacionesSiguiente</span>
		  		&nbsp;
		  		
		  		
		  	</div>	
			
			<!-- This <div> holds alert messages to be display in the sample page. -->
			<div id="alerts">
				<noscript>
					<p>
						<strong>CKEditor requires JavaScript to run</strong>. In a browser with no JavaScript
						support, like yours, you should still see the contents (HTML data) and you should
						be able to edit it normally, without a rich editor interface.
					</p>
				</noscript>
			</div>
			
			<textarea cols="500" id="constancia" name="constancia" rows="10">
				<% if(AlumConstancia.getConstancia().equals("")){ %>
						<p style="text-align:right">&nbsp;</p> <p style="text-align:right"><span style="font-size:14px"><strong>ASUNTO: CONSTANCIA</strong></span></p> <p style="text-align:right">&nbsp;</p> <p>#Foto</p> <p style="text-align:right">&nbsp;</p> <p><span style="font-size:14px">A QUIEN CORRESPONDA</span></p> <p>&nbsp;</p> <p style="text-align:justify">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.<span style="font-size:14px">&nbsp;</span></p> <p style="text-align:justify">&nbsp;</p> <p style="text-align:center"><span style="font-size:20px"><strong>#Nombre #Paterno #Materno</strong></span></p> <p style="text-align:center">&nbsp;</p> <p style="text-align:justify">Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore <strong>#Fecha</strong>. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. <strong>#Plan #Facultad #Carrera</strong>.&nbsp;Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.<span style="font-size:14px">.</span></p> <p style="text-align:justify">&nbsp;</p> <p style="text-align:justify"><span style="font-size:14px">Atentamente</span></p> <p style="text-align:justify">&nbsp;</p> <p style="text-align:justify">&nbsp;</p> <p style="text-align:justify"><span style="font-size:14px">Prof. Juan Perez</span><br /> &nbsp;</p> <p>&nbsp;</p>
				<% }else{%>
						<%=AlumConstancia.getConstancia() %>
				<% } %>
			</textarea>
		
		</p>
	</form>
		
	<div class="alert alert-info">
		<button onclick="guardar();" class="btn btn-primary btn-large">
			<i class="fas fa-check"></i>
			<%
				if(constanciaId.equals("")){		
			%>
					Guardar
			<%
				}else{
			%>
					Editar
			<%
				}
			%>
		</button>
	</div>
	
</div>	


<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<script type="text/javascript" src="../../js/chosen/chosen.jquery.js"></script>
<script> 
		jQuery(".chosen").chosen(); 
</script>

<script src="../../js/ckeditor/ckeditor.js"></script>

<script>

	function guardar(){
		if(document.forma.nombre.value != "" && document.forma.constancia.value !=""){
			document.forma.submit();
		}else{
			alert("Todos los campos son requeridos");
		}
	}

	// Replace the <textarea id="constancia"> with an CKEditor instance.
	CKEDITOR.replace( 'constancia', {
		on: {
			// Check for availability of corresponding plugins.
			pluginsLoaded: function( evt ) {
				var doc = CKEDITOR.document, ed = evt.editor;
				if ( !ed.getCommand( 'bold' ) )
					doc.getById( 'exec-bold' ).hide();
				if ( !ed.getCommand( 'link' ) )
					doc.getById( 'exec-link' ).hide();
			}
		},
		toolbar :
			[
				{ name: 'document', items : [ 'Source','-','DocProps','Preview','Print','-','Templates' ] },
				{ name: 'clipboard', items : [ 'Cut','Copy','Paste','PasteText','PasteFromWord','-','Undo','Redo' ] },
				{ name: 'editing', items : [ 'Find','Replace','-','SelectAll','-','SpellChecker', 'Scayt' ] },
				{ name: 'insert', items : [ 'Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak' ] },
				{ name: 'links', items : [ 'Link','Unlink' ] },
				'/',
				{ name: 'tools', items : [ 'Maximize','-' ] },
				{ name: 'styles', items : [ 'Styles','Format','Font','FontSize' ] },
				{ name: 'colors', items : [ 'TextColor','BGColor' ] },
				{ name: 'basicstyles', items : [ 'Bold','Italic','Underline','Strike','Subscript','Superscript','-','RemoveFormat' ] },
				{ name: 'paragraph', items : [ 'NumberedList','BulletedList','-','Outdent','Indent','-','Blockquote',
				'-','JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock','-','BidiLtr','BidiRtl' ] }
				
			],
	});
	
	CKEDITOR.config.height = 500;

	
</script>



<%@ include file= "../../cierra_enoc.jsp" %>