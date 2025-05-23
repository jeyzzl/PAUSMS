<%@page import="java.util.List"%>
<%@ page import="aca.financiero.spring.FinMaratum"%>
<%@ page import="aca.vista.spring.Inscritos"%>

<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>

<html>
<head>	
	<link rel="stylesheet" href="<%=request.getContextPath()%>/js/chosen/chosen.css" />
	<script type="text/javascript">		
		function Grabar() {
			if (document.frmMaratum.Codigo.value != ""){
				document.frmMaratum.submit();
			} else {
				alert("<spring:message code='aca.JSCompletar'/>");
			}
		}			
	</script>	
</head>
<%	
	String mensaje 					= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	FinMaratum finMaratum 			= (FinMaratum)request.getAttribute("finMaratum"); 
	List<Inscritos> lisInscritos	= (List<Inscritos>)request.getAttribute("lisInscritos");
	String llave 					= finMaratum.getCodigoPersonal()+finMaratum.getPlanId();
	String temp 					= "X";
%>
<body>
<div class="container-fluid">
	<h1>Editar</h1>		
	<div class="alert alert-info">
		<a class="btn btn-primary" href="listado"><i class="fas fa-arrow-left"></i></a>
	</div>
<% if (!mensaje.equals("-")){%>	
	<div class="alert"><%=mensaje%></div>
<% }%>	
	<form name="frmMaratum" action="grabar" method="post" enctype="multipart/form-data" style="padding-left: 10px; padding-right: 10px;">
	<div class="form-group">
		<label><strong><spring:message code="aca.Codigo"/></strong></label>
		<select name="Codigo" class="chosen" style="width:500px">
<% 	for (Inscritos alumno : lisInscritos){
		if (!temp.equals(alumno.getCodigoPersonal()+alumno.getPlanId())){
			temp = alumno.getCodigoPersonal()+alumno.getPlanId();
%>
		<option value="<%=alumno.getCodigoPersonal()+alumno.getPlanId()%>" <%=llave.equals(alumno.getCodigoPersonal()+alumno.getPlanId())?"selected":""%>>
			<%=alumno.getPlanId()%> - <%=alumno.getApellidoPaterno()+" "+alumno.getApellidoMaterno()+","+alumno.getNombre()%>
		</option>
<%		
		}
	} %>			
		</select>
		<br><br>		
		<span class="btn btn-file">
	  		<input type="file" id="archivo" name="archivo" required="required"/>
	 	</span>
	</div>
	</form>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Guardar"/></a> &nbsp;	
	</div>         
</div>
</body>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript"> 
		jQuery(".chosen").chosen(); 
</script>
</html>
