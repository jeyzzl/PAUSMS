<%@ include file= "../../con_enoc.jsp" %>
<%@ page import="java.sql.*" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"/>
<jsp:useBean id="AlumPlanU" scope="page" class="aca.alumno.PlanUtil"/>
<jsp:useBean id="alumnoUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="ArchStatusUtil" scope="page" class="aca.archivo.ArchStatusUtil"/>
<!-- inicio de estructura -->
<% 
	String matricula 			= (String) session.getAttribute("codigoAlumno");
	String codigoUsuario 		= (String) session.getAttribute("codigoPersonal");
	String IdDocumento			= request.getParameter("f_documento");	
	String IdStatus 			= request.getParameter("f_status");
	String s_status 			= "";	
	String s_cantidad 			= request.getParameter("f_cantidad");
	
	alumno = alumnoUtil.mapeaRegId(conEnoc,matricula);
	plan = AlumPlanU.mapeaRegId(conEnoc,matricula);
%>

<table align="CENTER" width="95%" >
  <tr> 
    <td class="titulo">Add File</td>
  </tr>
  <tr> 
    <td align="center"><font size="2"><a href="documentos">Student 
      Files</a></font></td>
  </tr>
  <tr> 
    <td align="center">&nbsp;</td>
  </tr>
  <tr>
    <th align="center">
			<font color="#000000" size="2" face="Verdana, Arial, Helvetica, sans-serif"><b>[<%=matricula%>] [<%=alumno.getNombre()%> <%=alumno.getApellidoPaterno()%> <%=alumno.getApellidoMaterno()%>] -- 
		[<%=plan.getPlanId()%>] [<%=alumnoUtil.getCarrera(conEnoc,matricula)%>]</b></font>
	  </th>
</table>
<form name="form1" method="post" action="add.jsp">
  <table style="width:60%"  align="center" class="table">
    <tr> 
      <td width="21%"><b><spring:message code="aca.Documento"/>:</b></td>
      <td width="79%"><%=aca.archivo.ArchDocumentosUtil.getDescripcion(conEnoc, IdDocumento)%> <font color="#000000"><b>[ <%=IdDocumento%> ]</b></font></td>
    </tr>
    <tr> 
      <td width="21%"><b>Status:</b></td>
      <td width="79%">
        <select name="f_status">
<%
	ArrayList lisDoc = new ArrayList();
		lisDoc = ArchStatusUtil.getListNow(conEnoc, IdDocumento, "ORDER BY IDSTATUS");
		for (int i=0; i< lisDoc.size(); i++){
			aca.archivo.ArchStatus doc = (aca.archivo.ArchStatus) lisDoc.get(i);
%>		
		<option value="<%=doc.getIdStatus()%>" <%if(doc.getIdStatus().equals(IdStatus)) out.print("selected");%>><%=doc.getDescripcion()%></option>
<%			}
%>  
        </select>
      </td>
    </tr>
    <tr> 
      <td width="21%"><b><spring:message code="aca.Fecha"/>:</b></td>
      <td width="79%"> 
        <input type="text" class="text" name="f_fecha" size="10" maxlength="10" value="<%=aca.util.Fecha.getHoy()%>">
        (DD/MM/YYYY)</td>
    </tr>
    <tr> 
      <td width="21%"><b>Amount:</b></td>
      <td width="79%"> 
        <input type="text" class="text" name="f_cantidad" size="3" maxlength="2" value="<%=s_cantidad%>">
      </td>
    </tr>
    <tr> 
      <td width="21%"><b><spring:message code="aca.Usuario"/>:</b></td>
      <td width="79%"><%=codigoUsuario%></td>
    </tr>
    <tr>
      <td width="21%">&nbsp;</td>
      <td width="79%"> 
        <input type="submit" class="btn btn-primary" name="Grabar" value="Grabar">
		<input type="hidden" name="f_codigo_personal" value="<%=matricula%>">
		<input type="hidden" name="f_documento" value="<%=IdDocumento%>">
		<input type="hidden" name="f_usuario" value="<%=codigoUsuario%>">
      </td>
    </tr>
  </table>
</form>
<%@ include file= "../../cierra_enoc.jsp" %>