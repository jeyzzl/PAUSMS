<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.catalogo.spring.CatPeriodo"%>
<%@ page import="aca.catalogo.spring.CatCarrera"%>
<%@ page import="aca.catalogo.spring.CatFacultad"%>
<%@ page import="aca.mentores.spring.MentCarrera"%>
<%@ page import="aca.mentores.spring.MentAcceso"%>
<%@ page import="aca.mentores.spring.MentContacto"%>
<%@ page import="aca.mentores.spring.MentMotivo"%>
<%@ page import="aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	
	function Normal(){
		document.frmContacto.Accion.value = "1";	
		document.frmContacto.submit();	
	}

	function Historial(){
		document.frmContacto.Accion.value = "2";
		document.frmContacto.submit();		
	}
	
</script>
<%
    String periodo	 		= (String)session.getAttribute("ciclo");
    String carreraId		= request.getParameter("carreraId");	
	String mentorId			= request.getParameter("idMentor");
	String accion 			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");
	String facultadNombre	= (String)request.getAttribute("facultadNombre");
	String carreraNombre	= (String)request.getAttribute("carreraNombre");
	String mentorNombre		= (String)request.getAttribute("mentorNombre");
	
	String comentario		= "";
	String nombre			= "";
	String motivo			= "";
	
	List<MentContacto> lisMenContacto 			= (List<MentContacto>)request.getAttribute("lisMenContacto");	
	HashMap<String,MentMotivo> mapaMotivo  		= (HashMap<String,MentMotivo>)request.getAttribute("mapaMotivo");
	HashMap<String,String> mapaAlumnos			= (HashMap<String,String>)request.getAttribute("mapaAlumnos");	
%>
<div class="container-fluid">
	<h3><%=mentorNombre%> Interviews <small class="text-muted fs-5">( <%=facultadNombre %> - <%=carreraNombre %> )</small></h3>    
	<div class="alert alert-info">
		<a href="entra_mentor?carreraId=<%=carreraId %>" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>
	</div>	
	<form action="frm_mentor_contacto" method="post" name="frmContacto">
	<input type="hidden" name="Accion">
  	<input type="hidden" name="carreraId" value="<%= carreraId %>">
  	<input type="hidden" name="idMentor" value="<%= mentorId %>">
	<table id="table" class="table table-sm table-bordered">
  	<tr>
  		<td colspan="8">View:
  		<a href="javascript:Normal();"><strong>Degree only</strong></a>&nbsp;&nbsp;
  		<a href="javascript:Historial();"><strong>All interviews</strong></a>
  	</td>
	</tr>
	<thead class="table-info">	
	<tr> 
    	<th width="3%">#</th>
    	<th width="7%">Interview</th>
    	<th width="7%"><spring:message code="aca.Matricula"/></th>
    	<th width="20%"><spring:message code="aca.Nombre"/></th>
    	<th width="10%">Reason</th>
    	<th width="6%">Visit</th>
    	<th width="40%"><spring:message code="aca.Comentario"/></th>
    	<th width="9%"><spring:message code="aca.Fecha"/></th>
  	</tr>
  	</thead>
  	<!--  ------------------------------------------------------------------------------------------------------------------------------------------------>
<%	
	for(int i=0; i<lisMenContacto.size(); i++){
		MentContacto contacto = (MentContacto) lisMenContacto.get(i);
		//inscritos = InscritosU.mapeaRegId(conEnoc, contacto.getCodigoPersonal());
		String motivoNombre = "Other";
		if (mapaMotivo.containsKey(contacto.getMotivoId())){
			motivoNombre = mapaMotivo.get(contacto.getMotivoId()).getMotivoNombre(); 
		}
		
		String alumnoNombre = "-";
		if (mapaAlumnos.containsKey(contacto.getCodigoPersonal())){
			alumnoNombre = mapaAlumnos.get(contacto.getCodigoPersonal()); 
		}			
%>
	<tr> 
	  <td><%=i+1%></td>
	  <td><div align="center"><%=contacto.getContactoId() %></div></td>
	  <td><div align="center"><%=contacto.getCodigoPersonal() %></div></td>
	  <td><%=alumnoNombre%></td>
	  <td><%=motivoNombre%></td>
	  <td><div align="center"><%=contacto.getTipo() %></div></td>		
	  <td><div align="left"><%=contacto.getComentario() %></div></td>
	  <td><div align="center"><%=contacto.getFechaContacto() %></div></td>
	</tr>
<%			
	} 	
%>
</table>
</form>
</div>