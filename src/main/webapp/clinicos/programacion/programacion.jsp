<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.rotaciones.spring.RotHospital"%>
<%@ page import= "aca.rotaciones.spring.RotHospitalEspecialidad"%>
<%@ page import= "aca.rotaciones.spring.RotProgramacion"%>
<%@ page import= "aca.rotaciones.spring.RotEspecialidad"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguroJquery.jsf" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Fecha" scope="page" class="aca.util.Fecha"/>

<head>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="../../js/popup/popup.js" type="text/javascript"></script>

<script>
	function subirHospitalId(){
		location.href="programacion?Accion=1&HospitalId="+document.getElementById('HospitalId').value;
	}
	function subirEspecialidadId(){
		location.href="programacion?Accion=2&EspecialidadId="+document.getElementById('EspecialidadId').value;		
	}
	function crearLista(num){		
		var strParametros = "";
		jQuery("#table tr").each(function(i) {
			var row = jQuery(this);
			if(i!=0&&!row.is(":hidden")&&row.html().indexOf("Vacia")==-1){
				strParametros+=row.find(".progId").val()+"-";
			}
		});
		
		if(num==1) jQuery("#pdfPresentaciones").attr("href", "PDFT?Programaciones="+strParametros+"&Presentacion=1");
		else if(num==2) jQuery("#pdfEvaluaciones").attr("href", "PDFT?Programaciones="+strParametros+"&Evaluacion=1");
		else if(num==3) jQuery("#pdfCompletos").attr("href", "PDFT?Programaciones="+strParametros+"&Presentacion=1&Evaluacion=1");
	}
</script>
<%
	String hospitalId 		= (String)session.getAttribute("rotHospitalId");
	String especialidadId	= (String)request.getAttribute("especialidadId");	
	String accion 			= request.getParameter("Accion")==null?"":request.getParameter("Accion");
	String mensaje			= (String)request.getAttribute("mensaje");

	List<RotHospital> lisHospitales 					= (List<RotHospital>)request.getAttribute("lisHospitales");	
	List<RotHospitalEspecialidad> lisEspecialidades	 	= (List<RotHospitalEspecialidad>)request.getAttribute("lisEspecialidades");
	List<RotProgramacion> lisProgramaciones 			= (List<RotProgramacion>)request.getAttribute("lisProgramaciones");
	HashMap<String,String> mapaAlumnos 					= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,RotEspecialidad> mapaEspecialidades	= (HashMap<String,RotEspecialidad>)request.getAttribute("mapaEspecialidades");	
%>
<link rel="stylesheet" href="../../css/style.css" />
<style>	
	.tinytable .head:hover, .tinytable .desc:hover, .tinytable .asc:hover {background: gray;}
	h3{
		font-weight:300;
	}
	.icon-user, .icon-remove, .icon-minus{cursor:pointer;}
</style>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

</head>
<body>
<div class="container-fluid">
	<h2>Programación de Rotaciones</h2>
	<div class="alert alert-info d-flex align-items-center">
		Hospital: &nbsp;<select id="HospitalId" class="HospitalId" style="width:410px;" onchange="subirHospitalId();">
					<%for(RotHospital hospi: lisHospitales){ %>
						<option <%if(hospitalId.equals(hospi.getHospitalId()))out.print("selected");%> value="<%=hospi.getHospitalId() %>"><%=hospi.getHospitalNombre() %></option>
					<%} %>
				 </select>&nbsp;&nbsp;&nbsp;
		Especialidad: &nbsp;<select id="EspecialidadId" class="EspecialidadId" style="width:400px;" onchange="subirEspecialidadId();">
						<option <%if(especialidadId.equals("todas"))out.print("selected");%> value="todas">Todas</option>
						<%for(RotHospitalEspecialidad esp: lisEspecialidades){ %>
						<option <%if(especialidadId.equals(esp.getEspecialidadId()))out.print("selected");%> value="<%=esp.getEspecialidadId() %>"><%=mapaEspecialidades.get(esp.getEspecialidadId()).getEspecialidadNombre()%> &nbsp;&nbsp;&nbsp;(<%=mapaEspecialidades.get(esp.getEspecialidadId()).getSemanas()%>  Semanas)</option>
						<%} %>
					 </select>&nbsp;&nbsp;&nbsp;
		<%if(!especialidadId.equals("todas")){ %>
			<input class="add btn btn-primary" type="button" value="Añadir plazas" onclick="mostrarAgregarPlaza();"/>
		<%} %>
		<%if(!mensaje.equals("")){ %>
			<font color="#7E1E1F" size="3"><%=mensaje%></font>
		<%} %>
	</div>
	
	<!-- Agregar Plazas -->
	<form id="forma" name="forma" action="programacion" method="post">
		<input type="hidden" name="Accion" />
		<div class="alert alert-info d-flex align-items-center">			
			<label for="cantidad">Cantidad:</label>
			<input name="cantidad" id="cantidad" class="form-control" type="text" style="width:120px; maxlength="3" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;"/>
			&nbsp;&nbsp;&nbsp;
	        <label for="Semanas"><spring:message code="aca.FechaInicio"/>:</label>
	        <input name="fechaInicio" type="text" class="form-control" value="<%=aca.util.Fecha.getHoy()%>" data-date-format="dd/mm/yyyy" id="dp2" style="width:120px;">
	        &nbsp;&nbsp;&nbsp;
	        <input type="button" class="btn btn-primary btn-sm" onclick="grabar();" value="Grabar"/>
			<input type="button" class="btn btn-danger btn-sm cancel" value="Cancelar" onclick="ocultarAgregarPlaza();">
		</div>
	</form>	
	<table style="width:80%">
		<tr>
			<td>
				<div class="btn-group oculto pdf">
				  	<a class="btn btn-medium btn-info dropdown-toggle" data-bs-toggle="dropdown" href="#">PDF Todos<span class="caret"></span></a>
				  	<ul class="dropdown-menu" style="min-width:72px;">
			            <li><a class="dropdown-item" id="pdfPresentaciones"onClick="javascript:crearLista(1);" href="#" target="_blanck">Presentaciones</a></li>
			            <li><a class="dropdown-item" id="pdfEvaluaciones"onClick="javascriptcrearLista(2);" href="#" target="_blanck">Evaluaciones</a></li>
			            <li><a class="dropdown-item" id="pdfCompletos"onClick="javascriptcrearLista(3);" href="#" target="_blanck"><font color="green"><b>Completos</b></font></a></li>
				  	</ul>
				</div>
			</td>
		</tr>
	</table>
	
	<tr>
		<td>
	
	<div id="tableheader" style="border:1px solid #D4D4D4;height:46px;;">
	   <div class="search" style="background:transparent;">
	       <table class="">
		       	<tr class="table-info">
		       		<td><select style="border-radius: 5px;border:1px solid #D4D4D4;" id="columns" onchange="sorter.search('query')"></select></td>
		       		<td>&nbsp;</td>
		       		<td>
		       			<div style="border:1px solid #D4D4D4;border-radius:5px;width:185px;height:24px;background:url('../../imagenes/search-box.gif') no-repeat top left;">
		       				<table style="width:100%" height="100%">
		       			 		<tr>
		       			 			<td width="25px"></td>
		       			 			<td>
		       			 				<input style=" background-color:transparent;height:20px;width:158px;text-align: left;outline: none;border-width:0px;border-color:white;" 
		       			 					type="text" id="query" onkeyup="sorter.search('query')" />
		       			 			</td>
		       			 		</tr>
		       				</table>
		       			</div>
		       		</td>
		       	</tr>
	       </table>
	   </div>
	   <span class="details">
	   <div><spring:message code="aca.Registros"/> <span id="startrecord"></span>-<span id="endrecord"></span> <spring:message code="aca.De"/> <span id="totalrecords"></span></div>
	   <div><a href="javascript:sorter.reset()"><spring:message code="aca.Reiniciar"/></a>&nbsp;&nbsp;</div>
	   </span>
	</div>
	<table id="table" class="table table-bordered table-striped" >
	     <thead>
		 <tr class="table-info"> 
			<th><h3>#</h3></th>
			<th><h3>&nbsp;</h3></th>
			<th style="text-align: center"><h3><spring:message code="aca.Matricula"/></h3></th>
			<th><h3><spring:message code="aca.Nombre"/></h3></th>
			<th style="text-align: center"><h3><spring:message code="aca.FechaInicio"/></h3></th>
			<th style="text-align: center"><h3><spring:message code='aca.FechaFinal'/></h3></th>
			<th style="text-align: center"><h3><spring:message code="aca.Estado"/></h3></th>
			<th><h3>Especialidad</h3></th>
			<th><h3>&nbsp;</h3></th>
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
			
			String alumno = "0000000";
			if(mapaAlumnos.containsKey(prog.getCodigoPersonal())){
				alumno = mapaAlumnos.get(prog.getCodigoPersonal());
			}
			
			String especialidadNombre = "";
			if (mapaEspecialidades.containsKey(prog.getEspecialidadId())){
				especialidadNombre = mapaEspecialidades.get(prog.getEspecialidadId()).getEspecialidadNombre();
			}
		%>
			<tr>
				<td align="center"><%=cont%></td>
				<td align="center">
<!-- 					<i title="Asignar Alumno" class="icon-user"></i> -->
					<a href="agregarAlumno?programacionId=<%=prog.getProgramacionId()%>" title="Asignar Alumno" <i class="fas fa-user"></i></a>
					<i title="Desasignar Alumno" <%=hide%> class="icon-minus"></i>
					<i title="Eliminar plaza" class="fas fa-trash-alt QuitarPlaza"></i>
					<input type="hidden" class="progId" value="<%=prog.getProgramacionId()%>"/>
				</td>
				<td align="center">
					<span class="matricula"><%=prog.getCodigoPersonal()==null?"&nbsp;":prog.getCodigoPersonal() %></span>
				</td>
				<td>
					<span class="nombre"><%=alumno.equals("0000000")?"&nbsp;":alumno %></span>
				</td>
				<td align="center"><%=prog.getfInicio() %></td>
				<td align="center"><%=prog.getfFinal() %></td>
				<td align="center">
					<span class="estado"><%=estado %></span>
				</td>
				<td><%=especialidadNombre%></td>
				<td>
					<div class="btn-group oculto pdf" <%=hide%>>
					  	<a class="btn btn-sm btn-info dropdown-toggle" data-bs-toggle="dropdown" href="#">PDF*<span class="caret"></span></a>
					  	<ul class="dropdown-menu" style="min-width:72px;">
				            <li><a class="dropdown-item" href="PDFT?Programaciones=<%=prog.getProgramacionId()%>&Presentacion=1" target="_blanck">Presentación</a></li>
				            <li><a  class="dropdown-item"href="PDFT?Programaciones=<%=prog.getProgramacionId()%>&Evaluacion=1" target="_blanck">Evaluación</a></li>
				            <li><a  class="dropdown-item"  href="PDFT?Programaciones=<%=prog.getProgramacionId()%>&Presentacion=1&Evaluacion=1" target="_blanck"><font color="green"><b>Completo</b></font></a></li>
					  	</ul>
					</div>
					<div <%if(hide.equals(""))out.print("style='display:none;'"); %> class='space'>&nbsp;</div>
				</td>
			</tr>
		<%
			cont++;
		} %>
		</tbody>
	</table>
	<div id="tablefooter" style="border:1px solid #D4D4D4;border-top:0px solid gray;height:30px;background:#ecf2f6;">
	<div id="tablenav" style="position:relative;right:-8px;">
		<div>
	        <img src="../../css/images/first.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1,true)" />
	        <img src="../../css/images/previous.gif" width="16" height="16" alt="First Page" onclick="sorter.move(-1)" />
	        <img src="../../css/images/next.gif" width="16" height="16" alt="First Page" onclick="sorter.move(1)" />
	        <img src="../../css/images/last.gif" width="16" height="16" alt="Last Page" onclick="sorter.move(1,true)" />
	    </div>
	    <div style="position:relative;top:0px;">
	     	<select id="pagedropdown"></select>
		</div>
	    <div style="position:relative;top:6px;">
	      	<a href="javascript:sorter.showall()"><spring:message code="aca.MostrarTodosRegistros"/></a>
	    </div>
	</div>
	<div id="tablelocation" style="position:relative;right:11px;">
	    	<div>
	            <select onchange="sorter.size(this.value)">
	            <option value="5">5</option>
	                <option value="20">25</option>
	                <option value="100" selected="selected">100</option>
	                <option value="300">300</option>
	                <option value="500">500</option>
	                <option value="800">800</option>
	            </select>
	            <span><spring:message code="aca.EntradasPagina"/></span>
	        </div>
	        <div class="page"><spring:message code="aca.Pagina"/> <span id="currentpage"></span> <spring:message code="aca.De"/> <span id="totalpages"></span></div>
	</div>
	</div>
	
		</td>
	</tr>
	
	<script type="text/javascript" src="../../js/script.js"></script>
	<script type="text/javascript">
	var sorter = new TINY.table.sorter('sorter','table',{
		headclass:'head',
		ascclass:'asc',
		descclass:'desc',
		evenclass:'evenrow',
		oddclass:'oddrow',
		evenselclass:'evenselected',
		oddselclass:'oddselected',
		paginate:true,
		size:100,
		colddid:'columns',
		currentid:'currentpage',
		totalid:'totalpages',
		startingrecid:'startrecord',
		endingrecid:'endrecord',
		totalrecid:'totalrecords',
		hoverid:'selectedrow',
		pageddid:'pagedropdown',
		navid:'tablenav',
		sortcolumn:0,
		sortdir:0,
		init:true
	});
	</script>
	

<!-- Agregar Alumno a la plaza -->
<!-- <div class="bg-popup bgAlumno"></div> -->
<!-- <div class="popup popupAlumno"> -->
<!-- 	<table class="table table-condensed table-nohover"> -->
<!-- 	<tr> -->
<!-- 		<td>Alumno:</td> -->
<!-- 		<td> -->
<%-- 			<input name="alumno" id="alumno" type="text" maxlength="7" onKeypress="if (window.event.keyCode < 48 || window.event.keyCode > 57) window.event.returnValue = false;"/> --%>
<!-- 		</td> -->
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 		<td colspan="2" style="text-align:right;"> -->
<!-- 			<input type="button" class="btn btn-primary grabarAl" value="Grabar"/> -->
<!-- 			<input type="button" class="btn cancelAl" value="Cancelar"> -->
<!-- 		</td> -->
<!-- 	</tr> -->
<!-- </table> -->
<!-- </div> -->
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
		alert("Show");
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
	var pdf;
	
	$('.icon-user').on('click', function(){
		
		matricula = $(this).parent().parent().children('td').find('.matricula');
		nombre = $(this).parent().parent().children('td').find('.nombre');
		estado = $(this).parent().parent().children('td').find('.estado');
		pdf = $(this).parent().parent().children('td').find('.pdf');
		
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
							estado.parent().parent().find('.icon-minus').show();
							pdf.show();
							pdf.siblings('.space').hide();
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
	$('.QuitarPlaza').on('click', function(){
		var $this = $(this);
		
		$.get('existePago?programacionId='+$this.siblings('input').val(), function(re){
			if($.trim(re) == 'borrar'){
				
				if(confirm('¿Esta seguro que desea eliminar esta plaza?') == true){
					$.get('eliminarProgramacion?programacionId='+$this.siblings('input').val(), function(r){
						if($.trim(r) == 'borro'){
							$this.parent().parent().remove();
						}else{
							alert('ocurrio un error al borrar la plaza');
						}
					});
				}
				
			}else{
				if( confirm('El alumno de esta plaza ya tiene pago, ¿Esta seguro que desea eliminar la plaza todos modos?') == true){
					
					$.get('eliminarProgramacion?programacionId='+$this.siblings('input').val(), function(r){
						if($.trim(r) == 'borro'){
							$this.parent().parent().remove();
						}else{
							alert('ocurrio un error al borrar la plaza');
						}
					});
					
				}
			}
			
		});
		
	});
	//Eliminar alumno de la rotacion
	$('#table').on('click', '.icon-minus', function(){
		$this = $(this);
		
		$.get('existePago?programacionId='+$this.siblings('input').val(), function(re){
			if($.trim(re) == 'borrar'){
				
				if(confirm('¿Esta seguro que desea borrar el alumno de esta plaza?') == true){
					matricula = $this.parent().parent().children('td').find('.matricula');
					nombre = $this.parent().parent().children('td').find('.nombre');
					estado = $this.parent().parent().children('td').find('.estado');
					pdf    = $this.parent().parent().children('td').find('.pdf');
					
					$.get('eliminarAlumno?programacionId='+$this.siblings('input').val(), function(r){
						if($.trim(r) == 'borro'){
							$this.hide();
							matricula.html('&nbsp;');
							nombre.html('&nbsp;');
							estado.html('Vacia');
							pdf.hide();
							pdf.siblings('.space').show();
						}else{
							alert('ocurrio un error al eliminar al alumno');
						}
					});
				}
				
			}else{
				if( confirm('Este alumno ya tiene pago, ¿Esta seguro que desea desasingar el alumno de esta plaza de todos modos?') == true){
					matricula = $this.parent().parent().children('td').find('.matricula');
					nombre = $this.parent().parent().children('td').find('.nombre');
					estado = $this.parent().parent().children('td').find('.estado');
					pdf    = $this.parent().parent().children('td').find('.pdf');
					
					$.get('eliminarAlumno?programacionId='+$this.siblings('input').val(), function(r){
						if($.trim(r) == 'borro'){
							$this.hide();
							matricula.html('&nbsp;');
							nombre.html('&nbsp;');
							estado.html('Vacia');
							pdf.hide();
							pdf.siblings('.space').show();
						}else{
							alert('ocurrio un error al eliminar al alumno');
						}
					});
				}
			}
		});
	});
})(jQuery);

	jquery("#forma").hide();

	function mostrarAgregarPlaza(){
		$("#forma").show();
	};
	
	function ocultarAgregarPlaza(){
		$("#forma").hide();
	}

</script>
</div>
</body>