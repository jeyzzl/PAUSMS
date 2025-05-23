<%@page import="aca.alumno.AlumDescuento"%>
<%@ include file= "../../con_enoc.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<jsp:useBean id="Descuento" class="aca.alumno.AlumDescuento" scope="page" />
<jsp:useBean id="DescuentoU" class="aca.alumno.AlumDescuentoUtil" scope="page" />
<%
	String codigoPersonal 	= (String)session.getAttribute("codigoPersonal"); 
	String cargaId 		  	= request.getParameter("cargaId")==null?"0":request.getParameter("cargaId");
	String	maximo		 	= DescuentoU.maximoReg(conEnoc,cargaId);
	String descuentoId		= request.getParameter("descuentoId");
	int accionFmt		  	= 0;	  
	String msj;
	Descuento.setCodigoPersonal(request.getParameter("matricula"));
	Descuento.setCargaId(request.getParameter("cargaId"));
	Descuento.setDescuentoId(descuentoId);
	Descuento.setFecha(request.getParameter("Fecha")==null?aca.util.Fecha.getHoy():request.getParameter("Fecha"));
	Descuento.setMatricula(request.getParameter("dMatricula"));
	Descuento.setTipoMatricula(request.getParameter("tipoMatricula"));
	Descuento.setEnsenanza(request.getParameter("dEnsenanza"));
	Descuento.setTipoEnsenanza(request.getParameter("tipoEnsenanza"));
	Descuento.setInternado(request.getParameter("dInternado"));
	Descuento.setTipoInternado(request.getParameter("tipoInternado"));
	Descuento.setTotal(request.getParameter("total"));
	Descuento.setObservaciones(request.getParameter("observaciones").equals("")?"-":request.getParameter("observaciones"));
	Descuento.setUsuario(request.getParameter("codigoUsuario"));
	Descuento.setAplicado(request.getParameter("aplicado"));
	if(!DescuentoU.existeReg(conEnoc, request.getParameter("matricula"), request.getParameter("cargaId"), descuentoId)){
		if(DescuentoU.insertReg(conEnoc, Descuento)) {
			accionFmt = 1;
			msj = "<div class='alert alert-success'>Registro Guardado</div>";
		}else{
			accionFmt = 2;
			msj = "<div class='alert alert-danger'>Ocurrió un error al guardar el registro</div>";
		}
	}else{
		if(DescuentoU.updateReg(conEnoc, Descuento)){
			accionFmt = 3;
			msj = "<div class='alert alert-success'>Registro Actualizado</div>";
		}else{
			accionFmt = 4;
			msj = "<div class='alert alert-danger'>Ocurrió un error al actualizar el registro</div>";
		}
	}
	out.print(msj);
%>

<%@ include file= "../../cierra_enoc2.jsf" %>