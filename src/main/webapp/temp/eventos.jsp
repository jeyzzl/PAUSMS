<%@ page import= "aca.plan.MapaCurso"%>

<%@ include file= "../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="convSU"  class="aca.conva.ConvSolicitudUtil" scope="page"/>
<jsp:useBean id="convS"  class="aca.conva.ConvSolicitud" scope="page"/>
<jsp:useBean id="convEvento"  class="aca.conva.ConvEvento" scope="page"/>
<jsp:useBean id="convEventoU"  class="aca.conva.ConvEventoUtil" scope="page"/>

<script type="text/javascript" src ="../../validacion.js"> </script>

<%//Traspaso de datos desde CONV_SOLICITUD hasta CONV_EVENTO
	ArrayList<aca.conva.ConvSolicitud> lisSolicitud = convSU.getListDistinct(conEnoc,"ORDER BY CODIGO_PERSONAL");
	for(int i = 0; i < lisSolicitud.size(); i++){
		convS = (aca.conva.ConvSolicitud) lisSolicitud.get(i);
		if(!convEventoU.existeMatricula(conEnoc,convS.getCodigoPersonal())){
			System.out.println("1");
			convEvento.setUsuario(convS.getUsuario());
			System.out.println("2"+convS.getUsuario());
			convEvento.setPlanId(convS.getCursoId().substring(0,8));
			System.out.println("3"+convS.getCursoId().substring(0,8));
			convEvento.setFecha(convS.getFecha());
			System.out.println("4"+convS.getFecha());
			convEvento.setEstado("S");
			System.out.println("5"+convEvento.getEstado());
			convEvento.setConvalidacionId(String.valueOf(convEventoU.getMaxReg(conEnoc)));
			System.out.println("6"+String.valueOf(convEventoU.getMaxReg(conEnoc)));
			convEvento.setComentario(" ");
			System.out.println("7"+convEvento.getComentario());
			convEvento.setCodigoPersonal(convS.getCodigoPersonal());
			System.out.println(convS.getCodigoPersonal());
			convEventoU.insertReg(conEnoc, convEvento);
			System.out.println("9");
		}
	}
%>

<%@ include file= "../../cierra_enoc.jsp" %> 