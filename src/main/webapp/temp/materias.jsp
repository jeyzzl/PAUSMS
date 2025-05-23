<%@ page import= "aca.plan.MapaCurso"%>

<%@ include file= "../con_enoc.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="convEU"  class="aca.conva.ConvEventoUtil" scope="page"/>
<jsp:useBean id="convE"  class="aca.conva.ConvEvento" scope="page"/>
<jsp:useBean id="convM"  class="aca.conva.ConvMateria" scope="page"/>
<jsp:useBean id="convMU"  class="aca.conva.ConvMateriaUtil" scope="page"/>
<jsp:useBean id="convSU"  class="aca.conva.ConvSolicitudUtil" scope="page"/>
<jsp:useBean id="convS"  class="aca.conva.ConvSolicitud" scope="page"/>
<script type="text/javascript" src ="../../validacion.js"> </script>

<%//Traspaso de datos desde CONV_SOLICITUD hasta CONV_EVENTO
	ArrayList<aca.conva.ConvEvento> lisEvento = convEU.getListAll(conEnoc,"ORDER BY CODIGO_PERSONAL");
	for(int i = 0; i < lisEvento.size(); i++){
		convE = (aca.conva.ConvEvento) lisEvento.get(i);
		ArrayList<aca.conva.ConvSolicitud> lisSolicitud = convSU.getList(conEnoc,convE.getCodigoPersonal(),"ORDER BY FECHA,CURSO_ID");
		for(int j = 0; j < lisSolicitud.size(); j++){
			convS = (aca.conva.ConvSolicitud) lisSolicitud.get(j);
			convM.setTipo(convS.getTipo());
			convM.setFecha(convS.getFecha());
			convM.setEstado("-");
			convM.setCursoId(convS.getCursoId());
			convM.setConvalidacionId(convE.getConvalidacionId());
			System.out.println(convE.getConvalidacionId()+" - "+convS.getCursoId());
			convMU.insertReg(conEnoc, convM);
		}
	}	
%>
<%@ include file= "../../cierra_enoc.jsp" %> 