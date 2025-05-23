<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="HospitalU" scope="page" class="aca.rotaciones.RotHospitalUtil"/>
<jsp:useBean id="EspecialidadU" scope="page" class="aca.rotaciones.RotEspecialidadUtil"/>
<jsp:useBean id="HospitalEspecialidadU" scope="page" class="aca.rotaciones.RotHospitalEspecialidadUtil"/>

<%
	ArrayList<aca.rotaciones.RotHospital> hospitales = HospitalU.getListAll(conEnoc, "ORDER BY 1");
	ArrayList<aca.rotaciones.RotEspecialidad> especialidades = EspecialidadU.getEspNoAsignadas(conEnoc, hospitales.get(0).getHospitalId(),"ORDER BY 1");
	
	ArrayList<aca.rotaciones.RotHospitalEspecialidad> HospEsp = HospitalEspecialidadU.getListHosp(conEnoc, hospitales.get(0).getHospitalId(),"ORDER BY 1");
%>
<style>
	.table td.active {
		border:2px solid black;
		font-weight: 600;
	}
	.table-bordered tbody:first-child tr:first-child td.active {
	  border-top: 2px solid black;
	}
	.table td.inactive {
		border:1px solid #FA5858;
	}
	.table-bordered tbody:first-child tr:first-child td.inactive {
	  border-top: 1px solid #FA5858;
	}
	.hospitales td{
		cursor:pointer;
	}
	.wait{
		float:right;
		height:15px;
	}
	.icon-circle-arrow-right{
		float:right;
	}
	i, .descripcion{cursor:pointer;}
	}
</style>
<div class="container-fluid">
	<h2>Especialidades por Hospital</h2>
	<div class="well"></div>
	<table>
	<tr>
		<td align="center">
			<div style="background:white;height: 542px;width:428px;overflow-y:auto;border:1px solid black;">
				<table style="width:410px" class="table table-condensed" style="margin:0;">
					<tr>
						<th>Hospitales</th>
					</tr>
				</table>
				<table style="width:410px" class="table table-condensed table-striped table-bordered hospitales" style="margin:0;">
					<%
					int cont = 0;
					for(aca.rotaciones.RotHospital hospi: hospitales){ %>
					<tr>
						<td class="hospital <%if(cont==0)out.print("active"); %>">
							<%=hospi.getHospitalNombre()%>
							<input type="hidden" value="<%=hospi.getHospitalId()%>" />
						</td>
					</tr>
					<%
						cont++;
					} %>
				</table>
			</div>
		</td>
		<td width="20px"></td>
		<td align="center">
			<div style="background:white;height: 542px;width:318px;overflow: auto;border:1px solid black;">
				<table style="width:300px" class="table table-condensed" style="margin:0;">
					<tr>
						<th>Especialidades Asignadas</th>
					</tr>
				</table>
				<table style="width:300px" class="table table-condensed table-striped table-bordered Asignadas" style="margin:0;">
					<%for(aca.rotaciones.RotHospitalEspecialidad hospEsp: HospEsp){ %>
					<tr>
						<td <%if(hospEsp.getEstado().equals("I"))out.print("class=inactive"); %>>
							<i title="editar especificaciones" class="fas fa-edit"></i>
							<span class="descripcion"><%=aca.rotaciones.RotEspecialidad.getNombre(conEnoc, hospEsp.getEspecialidadId())%></span>
							<i title="desasignar especialidad" class="icon-circle-arrow-right"></i>
							<input type="hidden" value="<%=hospEsp.getEspecialidadId()%>" />
							<div class="alert alert-info">
								<b>Contacto Principal:</b> <span class="cPrincipal" ><%=hospEsp.getContactoPrincipal()==null?"":hospEsp.getContactoPrincipal()%></span><br>
								<b>Puesto:</b> <span class="pPrincipal" ><%=hospEsp.getPuestoPrincipal()==null?"":hospEsp.getPuestoPrincipal() %></span><br>
								<b>Contacto Secundario:</b> <span class="cSecundario" ><%=hospEsp.getContactoSecundario()==null?"":hospEsp.getContactoSecundario() %></span><br>
								<b>Puesto:</b> <span class="pSecundario" ><%=hospEsp.getPuestoSecundario()==null?"":hospEsp.getPuestoSecundario() %></span><br>
								<b><spring:message code="aca.Estado"/>:</b> <%=hospEsp.getEstado().equals("A")?"Activo":"Inactivo" %>
								<input type="hidden" value="<%=hospEsp.getEstado() %>" class="estado">
								<br>
							</div>
						</td>
					</tr>
					<%} %>
				</table>
			</div>
		</td>
		<td width="20px"></td>
		<td align="center">
			<div style="background:white;height: 542px;width:318px;overflow: auto;border:1px solid black;">
				<table style="width:300px" class="table table-condensed" style="margin:0;">
					<tr>
						<th>Especialidades no Asignadas</th>
					</tr>
				</table>
				<table style="width:300px" class="table table-condensed table-striped table-bordered noAsignadas" style="margin:0;">
					<%for(aca.rotaciones.RotEspecialidad esp: especialidades){ %>
					<tr>
						<td>
							<i title="asignar especialidad" class="icon-circle-arrow-left"></i> 
							<%=esp.getEspecialidadNombre()%>
							<input type="hidden" value="<%=esp.getEspecialidadId()%>" />
						</td>
					</tr>
					<%} %>
				</table>
			</div>
		</td>
	</tr>
</table>
<div class="bg-popup"></div>
<div class="popup">
	<input type="hidden" id="especialidadId">
	<table class="table table-condensed table-nohover">
	<tr>
		<td>Contacto Principal:</td>
		<td><input name="contactoPrincipal" id="contactoPrincipal" type="text" maxlength="80" class="input-xlarge" /></td>
	</tr>
	<tr>
		<td>Puesto:</td>
		<td><input name="puestoPrincipal" id="puestoPrincipal" type="text" maxlength="80" class="input-xlarge" /></td>
	</tr>
	<tr>
		<td>Contacto Secundario:</td>
		<td><input name="contactoSecundario" id="contactoSecundario" type="text" maxlength="80" class="input-xlarge" /></td>
	</tr>
	<tr>
		<td>Puesto:</td>
		<td><input name="puestoSecundario" id="puestoSecundario" type="text" maxlength="80" class="input-xlarge" /></td>
	</tr>
	<tr>
		<td><spring:message code="aca.Estado"/>:</td>
		<td>
			<select name="estado" id="estado">
				<option value="A"><spring:message code='aca.Activo'/></option>
				<option value="I"><spring:message code='aca.Inactivo'/></option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="2" style="text-align:right;">
			<input type="button" class="btn btn-primary grabar" value="Grabar"/>
			<input type="button" class="btn cancel" value="Cancelar">
		</td>
	</tr>
</table>
</div>
<script>

(function($){
	var hospitales = $('.hospitales').find('td');
	var asignadas  = $('.Asignadas');
	var noAsignadas  = $('.noAsignadas');
	var hospitalActual = $('.active').find('input');
	//elegir hospital
	$('.hospital').on('click',function(){
		hospitales.removeClass('active');
		$this = $(this);
		$this.addClass('active');
		$this.append('<img src=../../imagenes/wait.gif class=wait />');
		
		var id = $this.find('input').val();		
		$.get('getEspAsignadas?hospitalId='+id, function(r){
			asignadas.html(r);
			
			$.get('getEspNoAsignadas?hospitalId='+id, function(r){
				noAsignadas.html(r);
				$this.find('.wait').remove();
			});
		});
		hospitalActual = $('.active').find('input');
	});
	//Asignar especialidad
	$('.noAsignadas').on('click','.icon-circle-arrow-left',function(){
		var $this = $(this);
		console.log($this.siblings('input'));
		$.get('asignarEsp?especialidadId='+$this.siblings('input').val()+'&hospitalId='+hospitalActual.val(), function(r){
			if($.trim(r) == 'grabo'){
				$.get('getEspAsignadas?hospitalId='+hospitalActual.val(), function(r){
					asignadas.html(r);
					$this.parent().parent().hide();
				});
			}else if($.trim(r) == 'error'){
				alert('ocurrio un error al asignar la especialidad');
			}
		})
	});
	//Desasignar especialidad
	$('.Asignadas').on('click','.icon-circle-arrow-right', function(){
		var $this = $(this);
		
		$.get('existeProgramacion?especialidadId='+$this.siblings('input').val()+'&hospitalId='+hospitalActual.val(), function(result){
			if($.trim(result) == 'existe'){
				alert('no se puede desasignar esta especialidad porque tiene rotaciones programadas');
			}else{
			
				$.get('desasignarEsp?especialidadId='+$this.siblings('input').val()+'&hospitalId='+hospitalActual.val(), function(r){
					if($.trim(r) == 'borro'){
						$.get('getEspNoAsignadas?hospitalId='+hospitalActual.val(), function(r){
							noAsignadas.html(r);
							$this.parent().parent().hide();
						});
					}else if($.trim(r) == 'error'){
						alert('ocurrio un error al asignar la especialidad');
					}
				})
				
			}
		});
	})
	//show description
	$('.Asignadas').on('click','.descripcion', function(){
		$(this).siblings('.well').slideToggle();
	});
	//editar esp asignada
	var especialidad = $('#especialidadId');
	var cPrincipal = $('#contactoPrincipal');
	var pPrincipal = $('#puestoPrincipal');
	var cSecundario = $('#contactoSecundario');
	var pSecundario = $('#puestoSecundario');
	var estado = $('#estado');
	
	$('.Asignadas').on('click','.fa-edit', function(){
		//alert("Datos:"+$(this).siblings('.well').find('.cPrincipal').html()+$(this).siblings('.well').find('.pPrincipal').html());
		especialidad.val($(this).siblings('input').val());
		cPrincipal.val($(this).siblings('.well').find('.cPrincipal').html());
		pPrincipal.val($(this).siblings('.well').find('.pPrincipal').html());
		cSecundario.val($(this).siblings('.well').find('.cSecundario').html());
		pSecundario.val($(this).siblings('.well').find('.pSecundario').html());
		var edo = $(this).siblings('.well').find('.estado').val();
		estado.find('option[value="'+edo+'"]').attr('selected', true);
		show();
	});

	var popup = $('.popup');
	var bg = $('.bg-popup');
	function show(){
		bg.show();
		popup.css({
			'left': $(window).width()/2 - popup.width()/2,
			'top' : $(window).height()/2 - popup.height()/2 - 40
		});
		popup.fadeToggle(200, function(){
			contactoPrincipal.focus();
		});
	}
	$('.cancel').on('click', close);
	bg.on('click', close);
	function close(){
		popup.fadeToggle();
		bg.fadeToggle();
	}
	//grabar modificaciones
	
	$('.grabar').on('click', function(){
		var estadoSelected = estado[0].options[estado[0].selectedIndex].value;
		$.get('grabarHospEsp?hositalId='+hospitalActual.val()+'&especialidadId='+especialidad.val()+'&cPrincipal='+cPrincipal.val()+'&pPrincipal='+pPrincipal.val()+'&cSecundario='+cSecundario.val()+'&pSecundario='+pSecundario.val()+'&estado='+estadoSelected, function(r){
			if($.trim(r) == 'actualizo'){
				close();
				$.get('getEspAsignadas?hospitalId='+hospitalActual.val(), function(r){
					asignadas.html(r);
				});
			}else{
				alert('Ocurrió un error al grabar');
			}
		})
	});
})(jQuery);
</script>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>