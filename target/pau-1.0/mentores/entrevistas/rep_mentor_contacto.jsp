<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="contactoU" scope="page" class="aca.mentores.MentContactoUtil"/>
<jsp:useBean id="MentMotivo" scope="page" class="aca.mentores.MentMotivo"/>
<jsp:useBean id="inscritos" scope="page" class="aca.vista.Inscritos"/>
<jsp:useBean id="InscritosU" scope="page" class="aca.vista.InscritosUtil"/>

<%  String sModulo		= request.getParameter("moduloId"); 
	String sCarpeta     = request.getParameter("carpeta");
	String sMentorId	= request.getParameter("idMentor");
	String sCarrera		= request.getParameter("carreraId");
	String sFacultad	= sCarrera.substring(0,3);	
	String sAccion		= request.getParameter("Accion");
	String sComentario			= "";
	String sNombre				= "";
	String sMotivo				= "";
	
	ArrayList lisMenContacto 		= new ArrayList();
	if(sAccion.equals("2")){
		lisMenContacto= contactoU.getLista(conEnoc, sMentorId, "ORDER BY 1");
	}else{
		lisMenContacto= contactoU.getHistorial(conEnoc, sMentorId, "ORDER BY 1");
	}
%>

<table style="width:60%; margin:0 auto;" id="noayuda">
  <tr> 
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
  </tr>
  <tr> 
    <td><div align="center"><strong><font size="4">
	<%=aca.catalogo.CatFacultadUtil.getNombreFacultad(conEnoc, sFacultad) %>
	</font></strong></div></td>
  </tr>
  <tr> 
    <td><div align="center"><font size="2"><strong>
    <%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, sCarrera) %> Degree
	</strong></font></div></td>
  </tr>
  <tr> 
    <th><div align="center">MENTOR: 
    [<%=sMentorId %>]&nbsp;&nbsp; 
	<%= aca.vista.MaestrosUtil.getNombreMaestro(conEnoc, sMentorId, "NOMBRE") %>
	</div></th>
  </tr>
  <tr> 
    <td><div align="center">
    <a href="frm_mentor_contacto.jsp?Accion=<%=sAccion %>&idMentor=<%=sMentorId %>&carreraId=<%=sCarrera %>&moduloId=<%=sModulo %>&carpeta=<%=sCarpeta %>">
    Return</a></div></th>
  </tr>
</table>
<br>
<table style="width:50%" align="center" class="tabla">
   <tr> 
     <td colspan="3" align="center"><strong><font size="2">Interview Students</font></strong></td>
  </tr>
  <tr> 
    <th>Interview</th>
    <th><spring:message code="aca.Matricula"/></th>
    <th align="center"><spring:message code="aca.Nombre"/></th>
  </tr>
  <%  	for(int i=0; i<lisMenContacto.size(); i++){
		aca.mentores.MentContacto contacto = (aca.mentores.MentContacto) lisMenContacto.get(i);
		inscritos = InscritosU.mapeaRegId(conEnoc, contacto.getCodigoPersonal());
		if(InscritosU.existeReg(conEnoc, contacto.getCodigoPersonal())==true){
			MentMotivo.setMotivoId(contacto.getMotivoId());
			if(MentMotivo.existeReg(conEnoc)==true){
				MentMotivo.mapeaRegId(conEnoc, MentMotivo.getMotivoId());
				sNombre = aca.alumno.AlumUtil.getNombreAlumno(conEnoc, contacto.getCodigoPersonal(), "NOMBRE");
				sMotivo = MentMotivo.getMotivoNombre();
			}			
%>
  <tr> 
    <td><div align="center"><%=contacto.getContactoId() %></div></td>
    <td><div align="center"><%=contacto.getCodigoPersonal() %></div></td>
    <td><%=sNombre %></td>
  </tr>
 <%		}
 	}
	lisMenContacto = null;
%>
</table>

<%@ include file= "../../cierra_enoc.jsp" %> 