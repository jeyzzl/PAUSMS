<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.archivo.spring.ArchDocumentos"%>
<%@page import="aca.archivo.spring.ArchStatus"%>
<%@page import="aca.archivo.spring.ArchDocStatus"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<link rel="stylesheet" href="../../js/chosen/chosen.css" />
<%	// Declaracion de Variables
	String IdDocumento		= request.getParameter("IdDocumento")==null?"0":request.getParameter("IdDocumento");
	String documentoNombre 	= (String)request.getAttribute("documentoNombre");
	String sResultado		= "";
	
	List<ArchDocumentos> lisDocumentos			= (List<ArchDocumentos>)request.getAttribute("lisDocumentos");
	List<ArchDocStatus> lisEstadosAsignados		= (List<ArchDocStatus>) request.getAttribute("lisEstadosAsignados");
	List<ArchStatus> lisEstadosRestantes		= (List<ArchStatus>) request.getAttribute("lisEstadosRestantes");
	HashMap<String,String> mapaEstados			= (HashMap<String,String>) request.getAttribute("mapaEstados");
	HashMap<String,String> mapaUsados			= (HashMap<String,String>) request.getAttribute("mapaUsados");
%>
<!-- inicio de estructura -->
<div class="container-fluid">
	<h1>Status by Document</h1>
	<form name="frmStatus" method="post" action="elegir_documento">
	<div class="alert alert-info d-flex align-items-center">
		<strong>Select file:</strong>
		<select name="IdDocumento" class="form-select" onchange="javascript:recarga()" style="width:450px">
      		<option value="0">Select a file</option>
<%	for(ArchDocumentos doc : lisDocumentos){ %>
			<option value="<%=doc.getIdDocumento()%>" <%if(doc.getIdDocumento().equals(IdDocumento)) out.print("selected");%>><%=doc.getDescripcion()%></option>
<%	} %>  
        </select> 
	</div>
	</form>	
<%	//	Condicion para que aparezca la segunda parte!!
	if(!IdDocumento.equals("0")) {		
%> 	
<!-- inicio de estructura -->
	<table style="width:100%" class="table table-nohover table-sm table-bordered"> 
	<tr>
		<td valign="top">
			<table style="width:100%" >		  	
		  	<tr> 
		    	<td colspan="6"> <h5> <%=documentoNombre%><small class="text-muted fs-6">&nbsp;(Allowed status for the current document)</small></h5></td>
		  	</tr>
		  	<tr> 
		    	<th width="10%" height="18"><spring:message code="aca.Operacion"/></th>
		    	<th width="10%" height="18"><spring:message code="aca.Status"/></th>
		   		<th width="50%" height="18"><spring:message code='aca.Descripcion'/></th>	
		   		<th width="10%" height="18" class="right">Status</th>
		   		<th width="10%" height="18" class="right">Validity</th>
		   		<th width="10%" height="18" class="right">Used</th>
		 	</tr>			
<% 
		for (ArchDocStatus estado : lisEstadosAsignados){
			
			String estadoNombre = "-";
			if (mapaEstados.containsKey(estado.getIdStatus())){
				estadoNombre =  mapaEstados.get(estado.getIdStatus());
			}
			
			String usados = "0";
			if (mapaUsados.containsKey(IdDocumento+"-"+estado.getIdStatus())){
				usados = mapaUsados.get(IdDocumento+"-"+estado.getIdStatus());
			}
			
			String check = "";
			if (estado.getEstado().equals("A")) check = "checked"; else check = "";			
%>  
		  	<tr>
		  		<td class="text-center">
<%			if (usados.equals("0")){%>		  			
		    		<a href="borrarStatus?IdDocumento=<%=IdDocumento%>&IdStatus=<%=estado.getIdStatus()%>" onclick="javascript:alert('Are you sure you want to delete this file?);return true"><img src="../../imagenes/no.png" title="Delete" /></a>
<% 			}else{
				out.print(" ");	
			}
%>		    		
		    	</td>		    		
	    		<td class="text-center"><font color="#000000" size="2"><%=estado.getIdStatus()%></font></td>
	    		<td class="text-left"><font color="#000000" size="2"><%=estadoNombre%></font></td>
	    		<td class="text-right">
	    			<input id="CheckEstado" name="CheckEstado" type="checkbox" value=<%=estado.getEstado()%> onclick="document.location.href='grabarEstado?Estado=<%=estado.getEstado()%>&IdDocumento=<%=IdDocumento%>&IdStatus=<%=estado.getIdStatus()%>'" <%=check%>>		    		
		    	</td>
		    	<td class="text-right">
		    		<a href="grabarValido?IdDocumento=<%=IdDocumento%>&IdStatus=<%=estado.getIdStatus()%>&Valido=<%=estado.getValido()%>">
		    			<%=estado.getValido().equals("S")?"YES":"NO"%>
		    		</a>
		    	</td>
		    	<td width="10%" class="right"><font color="#000000" size="2"><%=usados%></font></td>
		  	</tr>
  	<%	}%>
			</table>
		</td>
		<td valign="top">
			<table style="width:100%"  align="center">		  		
		  		<tr> 
		    		<td colspan="3" align="center"><h5><%=documentoNombre%><small class="text-muted fs-6">&nbsp;(Denied status for the current document)</small></h5></td>
		  		</tr>						
		  		<tr> 
		    		<th width="10%" height="18"><spring:message code="aca.Operacion"/></th>
		    		<th width="10%" height="18"><spring:message code="aca.Status"/></th>
		   			<th width="80%" height="18"><spring:message code='aca.Descripcion'/></th>
		 		</tr>			
<%		for (ArchStatus estado : lisEstadosRestantes){ %>
		  		<tr> 
		    		<td width="10%" align="center"><a href="grabarStatus?IdDocumento=<%=IdDocumento%>&IdStatus=<%=estado.getIdStatus()%>"><img title="Add" src="../../imagenes/add.gif" /></a></td>
		    		<td width="10%" align="center"><font color="#000000" size="2"><%=estado.getIdStatus()%></font></td>
		    		<td width="80%" align="left"><font color="#000000" size="2"><%=estado.getDescripcion()%></font></td>
		  		</tr>
<%		} %>				
			</table>
		</td>
	</tr>
	</table>
<%	//	Cierre de la condicion if inicial	
	}
%>
</form>
</div>
<script src="../../js/chosen/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
	function recarga(){
		document.frmStatus.submit();
	}
	
	jQuery(".chosen").chosen();
</script>