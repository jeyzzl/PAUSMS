<%@page import="aca.admision.spring.AdmSolicitud"%>
<%@page import="aca.admision.spring.AdmBanco"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>
		
</head>
<%	
	String folio 				= request.getParameter("Folio")==null?"0":request.getParameter("Folio");

	AdmSolicitud admSolicitud 	= (AdmSolicitud) request.getAttribute("admSolicitud");	
	AdmBanco admBanco			= (AdmBanco) request.getAttribute("admBanco");
%>
<body>
<div class="container-fluid">
	<h2><%=admSolicitud.getNombre()+" "+(admSolicitud.getApellidoMaterno()==null?"":admSolicitud.getApellidoMaterno())+" "+admSolicitud.getApellidoPaterno() %> Personal Details	</h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="mostrarProceso?Folio=<%=folio %>"><i class="fas fa-arrow-left"></i></a>
	</div>
	
    <table class="table table-condensed">
        <tr>
            <td>&nbsp;<b>Bank:</b></td>
            <td><input readonly type="text" id="Banco" name="Banco" value="<%=admBanco.getBanco() %>" size="30" maxlength="70" /></td>
        </tr>
        <tr>
            <td>&nbsp;<b>Branch:</b></td>
            <td><input readonly type="text" id="BancoRama" name="BancoRama" value="<%=admBanco.getBancoRama() %>" size="30" maxlength="70" /></td>
        </tr>
        <tr>
            <td>&nbsp;<b>Name of Account:</b></td>
            <td><input readonly type="text" id="CuentaNombre" name="CuentaNombre" value="<%=admBanco.getCuentaNombre() %>" size="30" maxlength="70" /></td>
        </tr>
        
        <tr>
            <td>&nbsp;<b>Account Number:</b></td>
            <td><input readonly type="text" id="CuentaNumero" name="CuentaNumero" value="<%=admBanco.getCuentaNumero() %>" size="30" maxlength="70" /></td>
        </tr>
        
        <tr>
            <td>&nbsp;<b>BBS Number:</b></td>
            <td><input readonly type="text" id="BBS" name="BBS" value="<%= admBanco.getNumeroBbs() %>" size="30" maxlength="10"/></td>
        </tr>
        
        <tr>
            <td>&nbsp;<b>Account Type:</b></td>
            <td><input readonly type="text" id="cuentaTipo" name="cuentaTipo" value="<%=admBanco.getCuentaTipo() %>" maxlength="20" /></td>
        </tr>

        <tr>
            <td>&nbsp;<b>Swift Code:</b></td>
            <td><input readonly type="text" id="CodigoSwift" name="CodigoSwift" value="<%=admBanco.getCodigoSwift() %>" maxlength="20" /></td>
        </tr>
        
    </table>
</div>
</body>
