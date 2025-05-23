<%@ include file= "../../conectadbp.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<style>
	.procesando, .completado, .error{
		display:none;
	}
</style>

<div class="container-fluid">
	
	<h1>Student Support</h1>
	<div class="alert alert-success completado">
		<i class="icon-ok"></i> Backup Completed on Server
	</div>
	<div class="alert procesando">
		<img src="../../imagenes/wait.gif" style="width:14px;vertical-align:text-top;" /> Creating Server Backup, <strong>this operation may take a few minutes</strong>
	</div>
	<div class="alert alert-warning error">
		<i class="icon-warning-sign"></i> An error occurred
	</div>
	<div class="alert alert-info d-flex align-items-center">	
		<a href="menuRespaldos" style="text-align:center;" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
		<br><br>
		<p>
			<label><spring:message code="aca.FechaInicio"/></label>
			<input type="text" data-date-format="dd/mm/yyyy" class="fInicio" value="<%=aca.util.Fecha.getHoy() %>" />
		</p>
		<p>
			<label><spring:message code='aca.FechaFinal'/></label>
			<input type="text" data-date-format="dd/mm/yyyy" class="fFin" value="<%=aca.util.Fecha.getHoy() %>" />
		</p>
		<p>
			<a href="" class="btn btn-primary btn-large crear-respaldo"><i class="icon-folder-open icon-white"></i> New</a>
			<a href="" class="btn btn-success btn-large descargar-respaldo"><i class="icon-arrow-down icon-white"></i> Download</a>
			<a href="" class="btn btn-danger btn-large eliminar-respaldo"><i class="fas fa-trash-alt icon-white"></i> Delete</a>
		</p>
	</div>
</div>
<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>
<script>
	(function($){
		
		
		jQuery('.fInicio').datepicker();
		jQuery('.fFin').datepicker();
		
		
		
		var completado 	= $('.completed');
		var procesando 	= $('.processing');
		var error 		= $('.error');
		
		var fInicio 	= $('.fInicio');
		var fFin 		= $('.fFin');
		
		
		
		/* ********* CREAR RESPALDO ********* */
		$('.crear-respaldo').on('click', function(e){
			e.preventDefault();
			$this = $(this);
			

			var date1 = new Date(  fInicio.val().split('/')[2], fInicio.val().split('/')[1], fInicio.val().split('/')[0]  );
			var date2 = new Date(  fFin.val().split('/')[2], fFin.val().split('/')[1], fFin.val().split('/')[0] );
			var timeDiff = Math.abs(date2.getTime() - date1.getTime());
			var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
			
			/*
			if( isNaN(diffDays) ){
				alert('Fechas invalidas!');
				return;
			}else if( diffDays > 50  ){
				alert('No debe haber mas de 50 dias de diferencia entre la Fecha Inicio y la Fecha Final');
				return;
			}
			*/
			
			
			completado.hide();
			$this.prop('disabled', true);
			$this.html('<i class="icon-folder-open icon-white"></i> Supporting...');
			procesando.html('<img src="../../imagenes/wait.gif" style="width:14px;vertical-align:text-top;" /> Creating Server Backup, <strong>this operation may take a few minutes</strong>').show();
			error.hide();
			
			$.get('zipAlumnos?fechaIni='+fInicio.val()+'&fechaFin='+fFin.val(), function(r){
				completado.html('<i class="icon-ok"></i> Backup Completed on Server').show();
				procesando.hide();
				$this.prop('disabled', false);
				$this.html('<i class="icon-folder-open icon-white"></i> New');
				error.hide();
				
				if( $(r).filter('.error')[0] != undefined ){
			    	error.html('<i class="icon-warning-sign"></i> An Error Occurred While Creating Backup').show();
			    	completado.hide();
			    }
			});
		})
		
		
		
		/* ********* ELIMINAR RESPALDO ********* */
		$('.eliminar-respaldo').on('click', function(e){
			e.preventDefault();
			$this = $(this);
			
			completado.hide();
			$this.prop('disabled', true);
			$this.html('<i class="icon-folder-open icon-white"></i> Deleting...');
			procesando.html('<img src="../../imagenes/wait.gif" style="width:14px;vertical-align:text-top;" /> Deleting Server Backup</strong>').show();
			error.hide();
			
			$.get('deleteZipAlumnos.jsp', function(r){
				completado.html('<i class="icon-ok"></i> Backup Deleted').show();
				procesando.hide();
				$this.prop('disabled', false);
				$this.html('<i class="icon-folder-open icon-white"></i> Delete');
				error.hide();
				
				if( $(r).filter('.error')[0] != undefined ){
			    	error.html('<i class="icon-warning-sign"></i> An Error Occurred While Deleting Backup').show();
			    	completado.hide();
			    }
			})
		})
		
		
		
		/* ********* DESCARGAR RESPALDO ********* */
		$('.descargar-respaldo').on('click', function(e){
			e.preventDefault();
			$this = $(this);
			
			completado.hide();
			procesando.hide();
			error.hide();
			
			$.get('existeZipAlumnos', function(r){
				if( $(r).filter('.error')[0] != undefined ){
			    	error.html('<i class="icon-warning-sign"></i> No Backup File Exists on Server, Please Create It').show();
			    }else{
			    	location.href="respaldoAlumnos.zip";
			    }
			})
		})
		
		
		
	})(jQuery)
</script>