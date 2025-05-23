<%@ page import="java.util.List"%>
<%@ page import="aca.admision.spring.AdmFormato"%>
<%@ page import="aca.admision.spring.AdmDocumento"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<html>
<head>
	<script type = "text/javascript">			
		function Grabar(){
			if(document.frmDocumento.DocumentoId.value!="" && document.frmDocumento.DocumentoNombre!="" 
			    && document.frmDocumento.Tipo!="" && document.frmDocumento.Comentario!="" && document.frmDocumento.Orden!="" ){				
				document.frmDocumento.submit();			
			}else{
				alert("Fill out the entire form!");
			}
		}			
	</script>
</head>
<%
	List<AdmFormato> listaFormato = (List<AdmFormato>) request.getAttribute("listaFormato");
	AdmDocumento admDocumento		  = (AdmDocumento) request.getAttribute("admDocumento");
%>
<body>
<div class="container-fluid">
	<h2><spring:message code='aca.CatalogoDeDocumentos'/> <small class="text-muted fs-5"><%=admDocumento.getDocumentoId().equals("0")?"":"( Key: "+ admDocumento.getDocumentoId()+" )"%></small></h2>
	<div class="alert alert-info  d-flex align-items-center">
		<a class="btn btn-primary" href="documentos"><spring:message code="aca.Regresar"/></a>
	</div>
	<form name="frmDocumento" action="grabar" method="post">		
		<div class="row">
			<div class="col-6">							
				<input name="DocumentoId" type="hidden"  id="DocumentoId" value="<%=admDocumento.getDocumentoId()%>">				
				<br>
				<label for="DocumentoNombre"><b><spring:message code="aca.Nombre"/></b></label>					
				<input name="DocumentoNombre" type="text"  class="form-control" id="DocumentoNombre" size="50" maxlength="100" value="<%=admDocumento.getDocumentoNombre()%>">
				<br>
				<label for="Corto"><b><spring:message code="aca.NombreCorto"/></b></label>			
				<input name="Corto" type="text"  class="form-control" id="Corto" maxlength="20" value="<%=admDocumento.getCorto()%>">				
				<br>
				<label for="Tipo"><b><spring:message code="aca.Tipo"/></b></label>
				<br>		
				<input type="radio" name="Tipo" value="1" <%=admDocumento.getTipo().equals("1")?"Checked":""%>/>&nbsp;General
				&nbsp;
				<input type="radio" name="Tipo" value="2" <%=admDocumento.getTipo().equals("2")?"Checked":""%>/>&nbsp;<spring:message code="aca.Carrera"/>
				<br><br>
				<label for="Original"><b>Original</b></label>					
				<select name="Original" id="Original" class="form-select" style="width:100px;">
					<%	if(admDocumento.getOriginal().equals("S")){ %>
			                <option value="S" selected><spring:message code='aca.Si'/></option>
					<%	}
						else{ %>	
							<option value="S"><spring:message code='aca.Si'/></option>
					<%	} %>	
					<%	if(admDocumento.getOriginal().equals("N")){ %>
			                <option value="N" selected><spring:message code='aca.No'/></option>
					<%	}
						else{ %>	
							<option value="N"><spring:message code='aca.No'/></option>
					<%	} %>	
             		</select>
				<br>				
				<label for="Orden"><b><spring:message code='aca.Orden'/></b></label>					
				<input name="Orden" type="text" class="form-control" style="width:100px; id="Orden" size="5" maxlength="2" value="<%= admDocumento.getOrden() %>">
				<br>
<%-- 				<label for="Formato"><spring:message code='aca.Formulario'/>:</label>			 --%>
<!-- 				<select name="Formato" class="form-select" style="width:220px; id="Formato"> -->
<%--        		  			<option value="0" <%if(admDocumento.getFormatoId().equals("0")) out.print("Selected"); %>><spring:message code='aca.Ninguno'/></option> --%>
<%-- 						<%	for(AdmFormato formato : listaFormato){%> --%>
<%--   			  					<option value="<%=formato.getFormatoId() %>" <%if(admDocumento.getFormatoId().equals(formato.getFormatoId())) out.print("Selected"); %>><%=formato.getFormatoNombre() %></option> --%>
<%-- 						<% 	} %> --%>
<!--        			</select>	 -->
       			<input type="hidden" name="Formato" id="Formato" value="0">			
<!-- 				<br> -->
				<label for="Comentario"><b><spring:message code="aca.Descripcion"/></b></label>					
				<textarea name="Comentario" class="form-control" id="Comentario"  rows="3" cols="70"><%=admDocumento.getComentario()%></textarea>
			</div>							
			
		</div>
		<br>
		<div class="alert alert-info">
			<a class="btn btn-primary" href="javascript:Grabar()"><spring:message code="aca.Grabar"/></a> &nbsp;
		</div>
	</form>
</div>
</body>
</html>
