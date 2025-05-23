<%@ page import= "java.util.List"%>
<%@ page import= "aca.admision.spring.AdmAsesor"%>
<%@ page import= "aca.emp.spring.EmpMaestro"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../idioma.jsp" %>


<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<script type="text/javascript">	
	
	function Grabar(){
		console.log("entered");
		if(document.frmasesor.Email.value!="" && document.frmasesor.Telefono!=""){	
			console.log("in");
			document.frmasesor.submit();			
		}else{
			alert("<spring:message code='aca.JSCompletar'/>");
		}
	}
</script>
<%
	// Declaracion de variables	
	AdmAsesor asesor			= (AdmAsesor)request.getAttribute("asesor");
	String mensaje				= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");
	
	List<EmpMaestro> lisMaestros		= (List<EmpMaestro>) request.getAttribute("lisMaestros");
%>
<div class="container-fluid">
	<h2>Add Advisor <%=mensaje.equals("-")?"":"- "+mensaje %></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="asesor"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;<%=mensaje.equals("-")?"":mensaje%>
	</div>
	<form name="frmasesor" action="grabarAsesor" method="post" target="_self">
	<div class="form-group">
    	<label for="AsesorId">Employee ID:</label><br>
	    <select name="AsesorId" class="form-select chosen" style="width:400px" required>
	    	<option value="0" <%=asesor.getAsesorId().equals("0")?"selected":""%>>Choose</option>
<%		for (EmpMaestro maestro : lisMaestros){ %>
			<option value="<%=maestro.getCodigoPersonal()%>" <%=maestro.getCodigoPersonal().equals(asesor.getAsesorId())?"selected":""%>><%=maestro.getEstado()%>-<%=maestro.getCodigoPersonal()%> - <%=maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno()+" "+maestro.getNombre()%></option>
<%		} %>	    	
	    </select>
	    <br>
        <label for="Email">Email:</label>
        <input name="Email" type="text" class="input input-xlarge form-control" id="Email" required value="<%=asesor.getCorreo()%>" size="40" maxlength="50">
        <br>
        <label for="Telefono">Phone Number:</label>
        <input name="Telefono" type="text" class="input input-xlarge form-control" id="Telefono" required value="<%=asesor.getTelefono()%>" size="40" maxlength="10">
        <br>
        <label for="Estado">Status</strong></label>
		<select class="input input-medium form-select" style="width:200px" name="Estado"  id="Estado" required>
	    	<option value="A" <% if (asesor.getEstado().equals("A")) out.print("selected"); %> >Active</option>
	    	<option value="I" <% if (asesor.getEstado().equals("I")) out.print("selected"); %> >Inactive</option>
	    </select>
    </div>
    <br>		 				
    <div class="alert alert-info">
        <a href="javascript:Grabar()" class="btn btn-primary"><spring:message code="aca.Guardar"/></a>&nbsp;  
	</div>		
	</form>
</div>

	<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script>
	jQuery(".chosen").chosen();	
</script>