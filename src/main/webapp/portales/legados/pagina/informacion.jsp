<%@ include file= "../../../con_enoc.jsp" %>
<%@ include file= "../id.jsp" %>
<%@ include file= "../../../seguro.jsp" %>
<%@ include file="../../../body.jsp"%>
<%@ include file= "../../../idioma.jsp" %>
<%@ page import="java.util.Date,java.text.SimpleDateFormat" %>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="academico" scope="page" class="aca.alumno.AlumAcademico"/>
<jsp:useBean  id="CompImage" class="aca.cultural.CompEventoImagenUtil" scope="page" />
<jsp:useBean  id="CompEvento" class="aca.cultural.CompEvento" scope="page" />
<jsp:useBean  id="CompRegistro" class="aca.cultural.CompRegistro" scope="page" />
<jsp:useBean  id="CompRegistroU" class="aca.cultural.CompRegistroUtil" scope="page" />
<link rel="stylesheet" href="../../../bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />
<link rel="stylesheet" href="../../../js/chosen/chosen.css" />
<link rel="stylesheet" href="../css/cssyify2.css" />
<link rel="stylesheet" href="../bootstrap new/css/bootstrap.css" />

<%

		String horaActual = aca.util.Fecha.getHoraDelDia();
		String minutos = aca.util.Fecha.getMinutos();
		int largom = minutos.length();
		if(largom==1){
			minutos = "0"+aca.util.Fecha.getMinutos();
		}
		horaActual = horaActual+minutos;
		int largo = horaActual.length();
		if(largo==3){
			horaActual="0"+horaActual;
		}
		String fechaDia = aca.util.Fecha.getHoy();
		
		String direccion 		= request.getRequestURL().toString();
		boolean virtual 		= false;
		if(direccion.contains("academico")){
			virtual = true;
		}
		
		if(!session.getAttribute("codigoAlumno").equals(session.getAttribute("codigoPersonal")) && !session.getAttribute("codigoPersonal").equals("9800308")){
			virtual=false;
		}
		String accion		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
		int nAccion			= Integer.parseInt(accion);
		int accionFmt		= 0;	
		
		//Codigo del usuario que se firmó
		String matricula 	= (String) session.getAttribute("codigoAlumno");
		String eventoId 	= request.getParameter("eventoId")==null?"":request.getParameter("eventoId");
		String colorPortal 	= (String) session.getAttribute("colorPortal")==null?"":(String)session.getAttribute("colorPortal");
		String facultadId	= CompRegistroU.facultadAlumno(conEnoc, matricula);
		String carreraId	= CompRegistroU.carreraAlumno(conEnoc, matricula);
		ArrayList<aca.cultural.CompEventoImagen> imagenes = CompImage.getImagenEvento(conEnoc, eventoId, "ORDER BY EVENTO_ID");
		
		CompEvento.mapeaRegId(conEnoc, eventoId);
		alumno = AlumUtil.mapeaRegId(conEnoc,matricula);	
		
		
		switch(nAccion){
		case 2 : //Grabar
			CompRegistro.setEventoId(eventoId);	
			CompRegistro.setEstado("R");
			CompRegistro.setCodigoPersonal(matricula);
			CompRegistro.setFacultadId(facultadId);
			CompRegistro.setCarreraId(carreraId);
			CompRegistro.setFechaRegistro(fechaDia);
			if(CompRegistroU.existeReg(conEnoc, eventoId)==false){
				if(CompRegistroU.insertRegByte(conEnoc, CompRegistro)){
					accionFmt = 1;
				}
			}
			break;
		case 3 ://Elimina
			CompRegistro.setEventoId(eventoId);
			CompRegistro.setEstado("R");
			CompRegistro.setCodigoPersonal(matricula);
			if(CompRegistroU.existeReg(conEnoc, eventoId)){
				if(CompRegistroU.deleteReg(conEnoc, eventoId, matricula)){
					accionFmt = 5;
				}
			}
			break;
		case 4 ://Elimina
			CompRegistro.setEventoId(eventoId);
			CompRegistro.setEstado("R");
			CompRegistro.setCodigoPersonal(matricula);
			if(CompRegistroU.existeReg(conEnoc, eventoId)){
				if(CompRegistroU.deleteReg(conEnoc, eventoId, matricula)){					
					//response.sendRedirect("principal");
				}
			}
			break;	
	}
		
		CompRegistro.mapeaRegId(conEnoc, eventoId, matricula);
		String descripcion = "";
		if(CompEvento.getDescripcion()==null){
		descripcion = "";
		}else{
			descripcion = CompEvento.getDescripcion();
		}
%>
<style>
	body{
		background:white;
	}
	.puestosAlum td, .puestosAlum th{
		background: white !important;
	}
	
	.puestosAlum th{
		color: black !important;
		border: 1px solid #DCDEDE !important;
	}
	
</style>
<div class="alert alert-info" background="black">
<h2 align ="center">Información del evento </h2>
</div>
&nbsp;&nbsp;&nbsp;&nbsp;<a href="principal" class="btn btn-primary"><i class="icon-arrow-left icon-white"></i><spring:message code='aca.Regresar'/></a>
<br>
<br>
<%  if(accionFmt==1){%>
		<div class="alert alert-info"><h4> <%if(alumno.getSexo().equals("M")) out.print("Joven");else out.print("Señorita");%> <%=alumno.getNombre()%>, su reservación a sido realizada satisfactoriamente, nos gustaría que regresara pronto. ¡Gracias! </h4></div>
<%}	if(accionFmt==5){%>
		<div class="alert alert-info"><h4>¡Nos da lástima que haya cancelado el evento!</h4></div>
<%}%>

<form name="frmReservar" action="informacion">
<input name="Accion" type="hidden" value="<%=accion%>"/>		
<input name="eventoId" type="hidden" value="<%=eventoId%>"/>	
<input name="codigoAlumno" type="hidden" value="<%=matricula%>"/>	 
<div id="wrapper">
        <div class="full-width">
        <br><br>
            <div id="movie-wrapper">
                <div class="movie-content">
              <%
              	for(aca.cultural.CompEventoImagen imagen: imagenes){ %>
                    <div class="movie-img"><img src="../../../fotoEvento?eventoId=<%=imagen.getEventoId() %>&imagenId=<%=imagen.getImagenId() %>" style="width: 100%;" disable/></div>
               <%}%>
                    <div class="movie-info">
                        <h1><%=CompEvento.getEventoNombre() %></h1>
                        <p></p>
                        <p><b>Lugar:</b> <%=CompEvento.getLugar() %></p>
                        <p><b>Capacidad:</b> <%=CompEvento.getCapacidad() %></p>
                        <p><b>Disponibles:</b> <%=Integer.parseInt((CompEvento.getCapacidad()))-Integer.parseInt((aca.cultural.CompRegistroUtil.getCantidadReservados(conEnoc, eventoId))) %></p>
                        <p><b><spring:message code="aca.Fecha"/>:</b> <%=CompEvento.getFecha().substring(0, 10) %></p>
                        <p><b>Hora:</b> <%=CompEvento.getFecha().substring(11, 16) %></p>
                        <p><b><spring:message code='aca.Descripcion'/></b> <%=descripcion %></p>
                        <%
                        String horaEvento = CompEvento.getFecha().substring(11, 13)+""+CompEvento.getFecha().substring(14, 16);
						int disponibles = Integer.parseInt(CompEvento.getCapacidad())-Integer.parseInt(aca.cultural.CompRegistroUtil.getCantidadReservados(conEnoc, eventoId));
						boolean fecha = aca.util.Fecha.getFechaMayorIgualQueHoy(CompEvento.getFecha().substring(0, 10));
                        if((fecha == false)&&(Integer.parseInt(horaActual)>=Integer.parseInt(horaEvento))){
	                    }else{
	                    	 if(!CompRegistroU.existeReg(conEnoc, CompEvento.getEventoId())){
		                        	if(disponibles>0){%>
										<a class="btn btn-primary btn-large" onclick="javascript:Guardar();"><i class="icon-arrow-up icon-white" ></i> Reservar</a>		                       		 	
		                        	<%}else{%>
		                        	<%}
		                        }else{ %>
		                        <a class="btn btn-primary btn-large" onclick="javascript:Eliminar();"><i class="fas fa-trash-alt icon-white"></i> Cancelar</a>
		                        <%} 
	                     }%>
	                        
                        <a class="btn btn-primary btn-large" href="amigos?eventoId=<%=eventoId%>"><i class="icon-user icon-white"></i> ¿Quien irá al evento?</a>
                        
                    </div>
                   
                </div>
            </div>
        </div>
</div>
</form>
<script type="text/javascript">
function Guardar() {
		document.frmReservar.Accion.value = "2";
		document.frmReservar.submit();
}
function Eliminar(){
		if (confirm("¿Estás seguro que deseas cancelar tu reservación?") == true) {
			document.frmReservar.Accion.value = "3";
			document.frmReservar.submit();
		}
}
</script>
<%@ include file= "../../../cierra_enoc.jsp" %>