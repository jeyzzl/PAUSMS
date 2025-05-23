<%@page import="aca.util.Fecha"%>
<%@ page import= "java.util.List"%>
<%@ page import= "aca.apFisica.spring.ApFisicaGrupo"%>
<%@ page import= "aca.carga.spring.Carga"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>


<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script type="text/javascript" src="../../bootstrap/datepicker/datepicker.js"></script>

<html>
	<body>
    <%
// 		String periodoId 	= (String)session.getAttribute("periodo");
		String cargaId 		= request.getParameter("CargaId")!=null?request.getParameter("CargaId"):(String)session.getAttribute("cargaId");	
		String accion   	= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		//int nAccion     	= Integer.parseInt(accion);
		String grupoId    	= request.getParameter("GrupoId")==null?"0":request.getParameter("GrupoId");
		//int accionFmt   	= 0;
	
		String fechaHoy   	= aca.util.Fecha.getHoy();
		String fechaInicio  = request.getParameter("FInicio")==null?fechaHoy:request.getParameter("FInicio");
		String fechaFinal   = request.getParameter("FFinal")==null?fechaHoy:request.getParameter("FFinal");
		String fechaCierre  = request.getParameter("FCierre")==null?fechaHoy:request.getParameter("FCierre");
		String fecha    	= request.getParameter("Fecha")==null?fechaHoy:request.getParameter("Fecha");
      
		List<Carga> lisCarga 	=  (List<Carga>) 	request.getAttribute("lisCarga");
		ApFisicaGrupo apFiGru 	= (ApFisicaGrupo) 	request.getAttribute("apFiGru"); 
		String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
		
	%>
		<div class="container-fluid">
 			<h1><%if(accion.equals("4")){out.print(apFiGru.getNombreGrupo());}else{out.print("Alta Grupo");}%></h1>
		<%if(!mensaje.equals("-")){%>
		<div class = "alert"><%=mensaje %></div>				
		<% }%>

		<div class="alert alert-info">
			<a href="grupo?Fecha=<%=fecha%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>
		</div>
    
		<form name="frmEvento" action="guardar" method="post">
			<input style="width:70px;" type="hidden" class="form-control" id="GrupoId" name="GrupoId" value="<%=apFiGru.getGrupoId()%>" width="50%" readonly/>
			<div class="row">
				<div class="span3 col">										
					<label for="NombreGrupo">Nombre Disciplina*</label>	
					<input type="text" class="form-control" id="NombreGrupo" style="width:370px;" name="NombreGrupo" maxlength="50" value="<%=apFiGru.getNombreGrupo()%>" width="50%"/>(máximo 50 caracteres)
					<br><br>
					<label for="Clave">Clave Curso*</label>			
					<select  id="Clave" name="Clave" class="form-select" style="width:370px;">                   
						<option value="FGAF133" <%if(apFiGru.getClave().equals("FGAF133"))out.print("selected");%>>1-FGAF133-Aptitud física: atención a la salud</option>
						<option value="FGAF134" <%if(apFiGru.getClave().equals("FGAF134"))out.print("selected");%>>2-FGAF134-Aptitud física: salud física</option>
						<option value="FGAF233" <%if(apFiGru.getClave().equals("FGAF233"))out.print("selected");%>>3-FGAF233-Aptitud física: educación física</option>
						<option value="FGAF234" <%if(apFiGru.getClave().equals("FGAF234"))out.print("selected");%>>4-FGAF234-Aptitud física: cuidado del cuerpo</option>
						<option value="FGAF333" <%if(apFiGru.getClave().equals("FGAF333"))out.print("selected");%>>5-FGAF333-Aptitud física: acondicionamiento físico</option>
						<option value="FGAF334" <%if(apFiGru.getClave().equals("FGAF334"))out.print("selected");%>>6-FGAF334-Aptitud física: rendimiento físico</option>												
						<option value="COSM191" <%if(apFiGru.getClave().equals("COSM191"))out.print("selected");%>>1-COSM191-Desarrollo Personal para una Cultura Universitaria</option>
						<option value="COSM192" <%if(apFiGru.getClave().equals("COSM192"))out.print("selected");%>>2-COSM192-Desarrollo Personal: Preparación para la Vida</option>
						<option value="COSM293" <%if(apFiGru.getClave().equals("COSM293"))out.print("selected");%>>3-COSM293-Desarrollo Personal: Entorno Sustentable</option>
						<option value="COSM294" <%if(apFiGru.getClave().equals("COSM294"))out.print("selected");%>>4-COSM294-Desarrollo Personal: Visión Emprendedora</option>
						<option value="COSM395" <%if(apFiGru.getClave().equals("COSM395"))out.print("selected");%>>5-COSM395-Desarrollo Personal: Gestión Comunitaria</option>
						<option value="COSM396" <%if(apFiGru.getClave().equals("COSM396"))out.print("selected");%>>6-COSM396-Desarrollo Personal: Visión de Servicio</option>						
					</select>
					<br>
					<label for="Cargas">Cargas*</label>					
					<select id="Cargas" name="Cargas" style="width:370px;" class="form-select">
<%					for (Carga carga : lisCarga){ %>
						<option <%if(apFiGru.getCargas().equals(carga.getCargaId()))out.print(" Selected ");%> value="<%=carga.getCargaId()%>">[<%=carga.getCargaId() %>] <%=carga.getNombreCarga()%></option>				
<%					} %>
					</select>
					<br>
					<label for="Lugar">Lugar*</label>
					<input type="text" style="width:370px;" class="form-control" id="Lugar" name="Lugar" maxlength="30" value="<%=apFiGru.getLugar()%>"/>(máximo 30 caracteres)
					<br><br>
					<label for="Lugar">Sexo</label>
					<select name="Sexo" class="form-select" style="width:120px;">
						<option value="T" <%=apFiGru.getSexo().equals("T")?"selected":""%>>Todos</option>
						<option value="F" <%=apFiGru.getSexo().equals("F")?"selected":""%>>Mujeres</option>
						<option value="M" <%=apFiGru.getSexo().equals("M")?"selected":""%>>Hombres</option>
					</select>					
				</div>
				<div class="span3 col">
					<label for="Instructor">Instructor*</label>
					<input type="text" style="width:350px;" class="form-control" id="Instructor" name="Instructor" maxlength="50" value="<%=apFiGru.getInstructor()%>"/>(máximo 50 caracteres)
					<br><br>
					<label for="Cupo">Cupo*</label>	
					<input style="width: 75px" type="text" class="form-control" id="Cupo" name="Cupo" maxlength="3" value="<%=apFiGru.getCupo()%>"/>(solo numeros)
<%		
					String hora = apFiGru.getHora();
					if(hora.equals("")) hora = "00:00 - 00:00";		
%>
					<br><br>
					<label for="Hora">Hora*</label>			
					<input type="text" style="width:200px" name="Hora" class="form-control" id="Hora" size="15" maxlength="15" value="<%=hora%>" />(24h/60m)
					<br><br>
					<label for="Dia">Dia*</label>					
					<select  id="Dia" name="Dia" class="form-select" style="width:300px">                   
						<option value="Domingo" <%if(apFiGru.getDia1().equals("Domingo"))out.print("selected"); %>>Domingo</option>
						<option value="Lunes"  <%if(apFiGru.getDia1().equals("Lunes"))out.print("selected"); %>>Lunes</option>
						<option value="Martes" <%if(apFiGru.getDia1().equals("Martes"))out.print("selected"); %>>Martes</option>
						<option value="Miercoles" <%if(apFiGru.getDia1().equals("Miercoles"))out.print("selected"); %>>Miercoles</option>
						<option value="Jueves" <%if(apFiGru.getDia1().equals("Jueves"))out.print("selected"); %>>Jueves</option>
						<option value="Viernes" <%if(apFiGru.getDia1().equals("Viernes"))out.print("selected"); %>>Viernes</option>
						<option value="Sabado" <%if(apFiGru.getDia1().equals("Sabado"))out.print("selected"); %>>Sabado</option>
					</select>
					<br>
					<label for="FInicio">Fecha Inicial*</label>
					<input name="FInicio" type="text" id="FInicio" style="width:200px" class="form-control" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=apFiGru.getfInicio() %>">
				</div>
				<div class="span3 col">
					<label for="FCierre">Fecha Cierre Grupo*</label>
					<input name="FCierre" type="text" id="FCierre" style="width:200px" class="form-control" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=apFiGru.getfCierre()%>">
					<br>
					<label for="FFinal">Fecha Final*</label>	
					<input name="FFinal" type="text" id="FFinal" style="width:200px" class="form-control" size="12" maxlength="10" data-date-format="dd/mm/yyyy" onfocus="focusFecha(this);" value="<%=apFiGru.getfFinal()%>">
					<br>
					<label for="Descripcion"><spring:message code='aca.Descripcion'/>*</label>			
					<textarea style="width:450px" type="text" class="form-control" maxlength="500" id="Descripcion" name="Descripcion"><%=apFiGru.getDescripcion() %></textarea>
					<br><br>
					<label for="Acceso">Acceso para alumnos:</label>					
					<select  id="Acceso" name="Acceso" class="form-select" style="width:200px">                   
						<option value="T" <%if(apFiGru.getAcceso().equals("T"))out.print("selected"); %>>Todos</option>
						<option value="N"  <%if(apFiGru.getAcceso().equals("N"))out.print("selected"); %>>Sin Registro</option>
					</select>
					<br>
					<label for="Liga">Liga:</label>			
					<textarea style="width:400px" type="text" class="form-control" maxlength="300" id="Liga" name="Liga"><%=apFiGru.getLiga()%></textarea>
				</div>
			</div>
			<br>
			<div class="alert alert-info">
				<a href="javascript:Guardar();" class="btn btn-primary"><i class="icon-ok icon-white"></i> Guardar</a>
			</div>
		</form>
		</div>
	</body>
	<link rel="stylesheet" href="../../js/maxlength/jquery.maxlength.css" />
<script src="../../js/maxlength/jquery.maxlength.min.js"></script>
	<script>
		function Guardar() {      
			if (document.frmEvento.GrupoId.value != ""
			&& document.frmEvento.Clave.value != ""
			&& document.frmEvento.Cargas.vaule != ""
			&& document.frmEvento.NombreGrupo.value != ""
			&& document.frmEvento.Lugar.value != ""
			&& document.frmEvento.Instructor.value != ""
			&& document.frmEvento.Cupo.value != ""
			&& document.frmEvento.Hora.value != ""
			&& document.frmEvento.FInicio.value != ""
			&& document.frmEvento.FFinal.value != ""
			&& document.frmEvento.Liga.value != ""
			&& document.frmEvento.Descripcion.value != "") {
				document.frmEvento.submit();
			} else {
				alert("¡Completa todos los campos!");
			}
		}
	</script>
	<script>
		jQuery('#FInicio').datepicker();
		jQuery('#FFinal').datepicker();
		jQuery('#FCierre').datepicker();	
		
		jQuery('#Descripcion').maxlength({ 
		    max: 500
		});
		
	</script>
</html>
