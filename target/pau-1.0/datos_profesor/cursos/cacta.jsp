<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<%
 	String cursoCargaId			= request.getParameter("cursoCargaId")==null?"0":request.getParameter("cursoCargaId");
	String imp					= request.getParameter("imp")==null?"":request.getParameter("imp");	
	String str 					= "";
	if (imp.equals("1")) str="&imp=1";
%>
<div class="container-fluid">
	<h2>Print Record</h2>
	<div class="alert alert-secondary">
		<a href="cursos" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp; &nbsp; &nbsp;
		<b>Click</b> the University's <b>logo</b> to print the record
	</div>
	<table style="width:100%" height="96%">	
	<tr>
		<td>
			<iframe width="100%" height="800" src="acta?cursoCargaId=<%=cursoCargaId+str%>"></iframe>
		</td>
	</tr>
	</table>
</div>