<%@ page import= "aca.parametros.spring.Parametros"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../idioma.jsp" %>


<script type="text/javascript">	
	function Grabar(){	
		if (document.forma.Institucion.value != "") {			
			document.forma.submit();
		}
	}	
</script>
<%
	// Declaracion de variables	
	String Id 				= request.getParameter("Id")==null?"1":request.getParameter("Id");
	Parametros param		= (Parametros) request.getAttribute("parametros");
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
%>
<body>
<div class="container-fluid">
	<h1>General Parameters</h1>
	<form name="forma" action="grabar" method="post">	
	<table class="table table-bordered">
    <tr> 
    	<td width="15%"><strong>Id:</strong></td>
        <td width="76%"><input name="Id" size="1" value="<%=param.getId()%>" readonly></td>			
    </tr>
    <tr> 
    	<td><strong>Institution:</strong></td>
        <td><input name="Institucion" type="text" class="text" id="Institucion" size="40" maxlength="40" value="<%=param.getInstitucion() %>"></td>
	</tr>
    <tr> 
    	<td><strong>Certificates:</strong></td>
    	<td><input name="Certificados" type="text" class="text" id="Certificados" size="40" maxlength="40" value="<%=param.getCertificados() %>"></td>
    </tr>
    <tr> 
    	<td><strong>Records:</strong></td>
    	<td><input name="Constancias" type="text" class="text" id="Constancias" size="40" maxlength="40" value="<%=param.getConstancias()  %>"></td>
    </tr>
    <tr> 
    	<td><strong>Transcript:</strong></td>
    	<td><input name="Cardex" type="text" class="text" id="Cardex" size="40" maxlength="40" value="<%=param.getCardex() %>"></td>
    </tr>        
<%	if (!mensaje.equals("-")){%>    
    <tr> 
    	<td colspan="2" align="center"><%=mensaje%></td>
    </tr>
<%	} %>    
    <tr>
    	<th colspan="2" align="center"> 
			<a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Grabar"/></a> &nbsp;
		</th>
    </tr>     
    </table>
	</form>
</div>
</body>