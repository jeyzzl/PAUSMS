<%@ page import="java.util.Arrays"%>
<%@ page import="java.util.Collections"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatFacultad"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>
<%@ page import= "aca.admision.spring.AdmRequisito"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>
<html>
<head>
	<script type="text/javascript">
		function seleccionarTodos(checkAll, size){
			for(i=0; i<size; i++){
				if(checkAll.name == "CheckAllAgregar"){
					var chkA = document.getElementById("chkAgregar"+i);
					if(chkA!=null) chkA.checked = checkAll.checked;
				}
				else{
					document.getElementById("chkQuitar"+i).checked = checkAll.checked;
				}
			}
		}
		
		function Grabar(){			
			document.frmAgregar.submit();	
		}		
	</script>
</head>	
<%

	String facultad					= request.getParameter("Facultad")==null?"0":request.getParameter("Facultad");
	String carrera					= request.getParameter("Carrera")==null?"0":request.getParameter("Carrera");
	String documento				= request.getParameter("Documento")==null?"0":request.getParameter("Documento");
	String facultadNombre			= (String)request.getAttribute("facultadNombre");
	String carreraNombre			= (String)request.getAttribute("carreraNombre");
	String documentoNombre			= (String)request.getAttribute("documentoNombre");
	AdmRequisito admRequisito		= (AdmRequisito)request.getAttribute("admRequisito");

	List<CatModalidad> lisModalidades 				= (List<CatModalidad>) request.getAttribute("lisModalidades");
	HashMap<String,CatModalidad> mapaModalidades 	= (HashMap<String,CatModalidad>) request.getAttribute("mapaModalidades");
	
	String [] arrRequisito = new String[0];
	if(!admRequisito.getModalidades().equals("")) arrRequisito = admRequisito.getModalidades().split("-");
	
	ArrayList<String> lisRequisito = new ArrayList<String>(Arrays.asList(arrRequisito));	
%>
<body>
<div class="container-fluid">
	<h2>Allowed Modalities <small class="text-muted fs-4">( <%=carreraNombre%> - <%=documentoNombre%> )</small></h2>
	
	<!-- Form frmAgregar -->
	<form name="frmAgregar" action="grabar?Facultad=<%=facultad%>&Carrera=<%=carrera%>&Documento=<%=documento%>" method="post" >
	<div class="alert alert-info  d-flex align-items-center" >
		<a href="documentos?Facultad=<%=facultad %>&Carrera=<%=carrera %>" class="btn btn-primary" style="width:120px"><spring:message code='aca.Regresar'/></a>
		&nbsp;&nbsp;&nbsp;&nbsp;
		<b><spring:message code='aca.AutorizadoPorCoordinador'/></b>&nbsp;
		<select name="Autorizar" id="Autorizar" class="form-select" style="width:120px"> 
			<option <%if(admRequisito.getAutorizar().equals("A")) out.print(" Selected ");%> value="A"><spring:message code='aca.No'/></option>
			<option <%if(admRequisito.getAutorizar().equals("C")) out.print(" Selected ");%> value="C"><spring:message code='aca.Si'/></option>
		</select>		
		&nbsp;&nbsp;&nbsp;&nbsp;
		<b><spring:message code='aca.Requerido'/></b>
		<select name="Requerido" id="Requerido" class="form-select" style="width:120px">
			<option <%if(admRequisito.getRequerido().equals("N")) out.print(" Selected ");%> value="N"><spring:message code='aca.No'/></option>
			<option <%if(admRequisito.getRequerido().equals("S")) out.print(" Selected ");%> value="S"><spring:message code='aca.Si'/></option>
		</select>
		&nbsp;&nbsp;
		<a href="javascript:Grabar();" class="btn btn-primary"><spring:message code="aca.Grabar"/></a>
	</div>	
	<table class="table table-condensed" style="width:50%">
		<tr>
			<td colspan="3">
				<h3>Modalities</h3>
			</td>
		</tr>	  		
		 <tr>
			<th style="text-align: center"><input name="CheckAllAgregar" type="checkbox" value="S" onclick="javascript:seleccionarTodos(this, '<%=lisModalidades.size()%>')"></th>
		   	<th style="text-align: center"><spring:message code="aca.Numero"/></th>
		   	<th style="text-aling: center"><spring:message code="aca.Modalidad"/></th>
		</tr>
<%	
	int row = 0;
	for(CatModalidad modalidad : lisModalidades){
		String seleccionar = "";
		if(lisRequisito.contains(modalidad.getModalidadId())){
			seleccionar = "checked";
		}				
%>
		<tr>
			<td align="center">
				<input type="checkbox" id="chkAgregar<%=row%>" name="chkAgregar<%=row+1%>" <%=seleccionar%>/>
		  	</td>
			<td align="center"><strong><%=row+1%></strong></td>
			<td><strong><%=modalidad.getNombreModalidad() %></strong></td>
		</tr>
		
<%	
		row++;
	} 
%>
	</table>
	</form>
</div>
</body>
</html>