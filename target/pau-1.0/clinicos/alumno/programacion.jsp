<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.rotaciones.spring.RotHospital"%>
<%@ page import= "aca.rotaciones.spring.RotProgramacion"%>
<%@ page import= "aca.rotaciones.spring.RotEspecialidad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../idioma.jsp" %>
<head>
<%	
	List<RotProgramacion> lisProgramaciones 			= (List<RotProgramacion>)request.getAttribute("lisProgramaciones");
	HashMap<String,String> mapaAlumnos 					= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,RotEspecialidad> mapaEspecialidades	= (HashMap<String,RotEspecialidad>)request.getAttribute("mapaEspecialidades");
	HashMap<String,String> mapaHospitales				= (HashMap<String,String>)request.getAttribute("mapaHospitales");
%>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
</head>
<body>
<%
	if(lisProgramaciones.size()==0){
%>
		<table>
			<tr>
				<td style="text-align:center;">
					<h2>No Hay Plazas Disponibles</h2>
				</td>
			</tr>
		</table>
<%
	}else{
%>
<div class="container-fluid">
	<h1>Registro de plazas</h1>
	<div class="alert alert-info">
		<input type="text" class="form-control search-query" placeholder="Buscar" id="buscar" style="width:200px">
	</div>
	<table id="table" class="table table-sm">
    <thead>
	<tr> 
		<th><h3>#</h3></th>
		<th><h3>&nbsp;</h3></th>
		<th><h3><spring:message code="aca.Matricula"/></h3></th>
		<th><h3><spring:message code="aca.Nombre"/></h3></th>
		<th><h3><spring:message code="aca.FechaInicio"/></h3></th>
		<th><h3><spring:message code='aca.FechaFinal'/></h3></th>
		<th><h3><spring:message code="aca.Estado"/></h3></th>
		<th><h3>Especialidad</h3></th>
		<th><h3>Hospital</h3></th>
	</tr>
	</thead>
	<tbody>
	<%
	int cont = 1;
	for(RotProgramacion prog : lisProgramaciones){ 
		String estado = prog.getCodigoPersonal()==null?"Vacia":"Ocupada";
		 
		String hide = "";
		if(estado.equals("Vacia")){
			hide = "style='display:none;'";
		}
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(prog.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(prog.getCodigoPersonal());
		}
		
		String especialidadNombre = "-";
		if (mapaEspecialidades.containsKey(prog.getEspecialidadId())){
			especialidadNombre = mapaEspecialidades.get(prog.getEspecialidadId()).getEspecialidadNombre();
		}
		
		String hospitalNombre = "-";
		if (mapaHospitales.containsKey(prog.getHospitalId())){
			hospitalNombre = mapaHospitales.get(prog.getHospitalId());
		}
	%>
		<tr>
			<td align="center"><%=cont%></td>
			<td align="center">				
<!-- 				<i title="Asignar Alumno" class="fas fa-user addUser"></i> -->
<%-- 				<i title="Desasignar Alumno" <%=hide%> class="fas fa-minus delUser"></i> --%>
<!-- 				<i title="Eliminar Plaza" class="fas fa-trash-alt delPlaza"></i> -->
				<input type="hidden" value="<%=prog.getProgramacionId()%>"/>
			</td>
			<td align="center">
				<span class="matricula"><%=prog.getCodigoPersonal()==null?"&nbsp;":prog.getCodigoPersonal() %></span>
			</td>
			<td>
				<span class="nombre"><%=alumnoNombre%></span>
			</td>
			<td align="center"><%=prog.getfInicio() %></td>
			<td align="center"><%=prog.getfFinal() %></td>
			<td align="center">
				<span class="estado"><%=estado %></span>
			</td>
			<td><%=especialidadNombre%></td>
			<td><%=hospitalNombre%></td>
		</tr>
	<%
		cont++;
	} %>
	</tbody>
	</table>	
<script>
	function grabar(){
		if(document.forma.cantidad.value!=""){
			document.forma.Accion.value = "3";
			document.forma.submit();
		}else{
			alert("La cantidad es requerida");
			document.forma.cantidad.focus();
		}
	}
	
	(function($){
		//Agregar plazas
		var popup = $('.popupPlazas');
		var bg = $('.bgPlazas');
		
		$('.add').on('click', function(){
			show();
		});
		function show(){
			bg.show();
			popup.css({
				'left': $(window).width()/2 - popup.width()/2,
				'top' : $(window).height()/2 - popup.height()/2 - 40
			});
			popup.fadeToggle(200, function(){
				document.getElementById('cantidad').focus();
			});
		}
		$('.cancel').on('click', close);
		bg.on('click', close);
		function close(){
			popup.fadeToggle();
			bg.fadeToggle();
		}
		
		jQuery('#dp2').datepicker();
		
		
		//Anadir Alumnos a la plaza
		var programacionId = "";
		var popupAl = $('.popupAlumno');
		var bgAl = $('.bgAlumno');
		var matricula;
		var nombre;
		var estado;
		
		$('.addUser').on('click', function(){
			
			matricula = $(this).parent().parent().children('td').find('.matricula');
			nombre = $(this).parent().parent().children('td').find('.nombre');
			estado = $(this).parent().parent().children('td').find('.estado');
			
			programacionId = $(this).siblings('input').val();
			jQuery('#alumno').val($.trim( matricula.html().replace('&nbsp;','') ));
			showAl();
		});
		function showAl(){
			bgAl.show();
			popupAl.css({
				'left': $(window).width()/2 - popupAl.width()/2,
				'top' : $(window).height()/2 - popupAl.height()/2 - 40
			});
			popupAl.fadeToggle(200, function(){
				document.getElementById('alumno').focus();
			});
		}
		$('.cancelAl').on('click', closeAl);
		bgAl.on('click', closeAl);
		function closeAl(){
			popupAl.fadeToggle();
			bgAl.fadeToggle();
		}
		//Grabar alumno
		$('.grabarAl').on('click', function(){
			var codigoPersonal = jQuery('#alumno').val();
			if(codigoPersonal.length == 7){
				$.get('existeAlumno?codigoPersonal='+codigoPersonal, function(r){
					if($.trim(r) == 'existe'){
						$.get('setAlumno?programacionId='+programacionId+'&codigoPersonal='+codigoPersonal, function(res){
							if($.trim(res) != 'error'){
								matricula.html(codigoPersonal);
								nombre.html(res);
								estado.html("Ocupada");
								console.log(estado.parent());
								estado.parent().parent().find('.delUser').show();
								closeAl();
							}else{
								alert('ocurrio un error al asignar el alumno');
							}
						});
					}else{
						alert('este numero de matricula no existe');
					}
				});
			}else{
				alert('La matricual debe ser de 7 caracteres');
			}
		});
		//Eliminar Rotacion
		$('.delPlaza').on('click', function(){
			var $this = $(this);
			
			$.get('existePago?programacionId='+$this.siblings('input').val(), function(re){
				if($.trim(re) == 'borrar'){
					
					if(confirm('¿Esta seguro que desea eliminar esta plaza?') == true){
						$.get('eliminarProgramacion?programacionId='+$this.siblings('input').val(), function(r){
							if($.trim(r) == 'borro'){
								$this.parent().parent().remove();
								console.log($this.parent().parent());
							}else{
								alert('ocurrio un error al borrar la plaza');
							}
						});
					}
					
				}else{
					alert('No se puede borrar esta plaza porque el alumno de esta plaza ya tiene pago');
				}
				
			});
			
		});
		//Eliminar alumno de la rotacion
		$('#table').on('click', '.delUser', function(){
			$this = $(this);
			
			$.get('existePago?programacionId='+$this.siblings('input').val(), function(re){
				if($.trim(re) == 'borrar'){
					
					if(confirm('¿Esta seguro que desea borrar el alumno de esta plaza?') == true){
						matricula = $this.parent().parent().children('td').find('.matricula');
						nombre = $this.parent().parent().children('td').find('.nombre');
						estado = $this.parent().parent().children('td').find('.estado');
						
						$.get('eliminarAlumno?programacionId='+$this.siblings('input').val(), function(r){
							if($.trim(r) == 'borro'){
								$this.hide();
								matricula.html('&nbsp;');
								nombre.html('&nbsp;');
								estado.html('Vacia');
							}else{
								alert('ocurrio un error al eliminar al alumno');
							}
						});
					}
					
				}else{
					alert('No se puede desasignar este alumno porque el alumno ya tiene pago');
				}
			});
		});
	})(jQuery);
</script>
<script type="text/javascript" src="../../js/search.js"></script>
<script>
	jQuery('#buscar').focus().search({table:jQuery("#table")});
</script>
<%}//end if de no hay plazas disponibles %>
</div>
</body>