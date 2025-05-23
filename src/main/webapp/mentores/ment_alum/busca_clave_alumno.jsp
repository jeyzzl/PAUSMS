<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import="java.util.List" %>

<%@page import="aca.alumno.spring.AlumPersonal"%>

<%	
	String nombre = (String) request.getAttribute("nombre");
	
	List<AlumPersonal> lisPersonal	= (List<AlumPersonal>) request.getAttribute("lisPersonal");
%>

<script type="text/javascript">
	function regresar(strID){
		self.opener.document.form.matricula.value = strID;
		close();
	}
</script>

<h2>Results</h2>
<table style="width:100%;" class="tabla">
  <tr>
    <th><div align="center"><font size="2"><strong>Enrollment ID</strong></font></div></th>
    <th><div align="center"><font size="2"><strong>Name</strong></font></div></th>
  </tr>
<%	for(int i = 0; i<lisPersonal.size(); i++){
		AlumPersonal personal = lisPersonal.get(i);

%>
  		<tr>
			<td><a href="javascript:regresar('<%=personal.getCodigoPersonal()%>');"><%=personal.getCodigoPersonal()%></a></td>
			<td><%=personal.getApellidoPaterno()+" "+personal.getApellidoMaterno()+" "+personal.getNombre()%></td>
  		</tr>
<%	
	}
%>
</table>