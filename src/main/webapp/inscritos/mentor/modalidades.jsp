<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import= "aca.catalogo.spring.CatModalidad"%>

<%@ include file= "../../headPortal.jsp" %>
<%@ include file="id.jsp"%>
<%@ include file="../../seguro.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Modalidad" scope="page" class="aca.catalogo.CatModalidad" />
<jsp:useBean id="ModalidadU" scope="page" class="aca.catalogo.ModalidadUtil" />
<jsp:useBean id="EstadisticaU" scope="page" class="aca.vista.EstadisticaUtil" />
<script type="text/javascript">	
	function Mostrar(){					
		document.forma.Accion.value="1";
		document.forma.submit();
	}
</script>
<%
	String codigoPersonal				= (String) session.getAttribute("codigoPersonal");
	String cargas						= (String)session.getAttribute("cargas") == null?"0":session.getAttribute("cargas").toString();
	String modalidades					= (String)session.getAttribute("modalidades") == null?"":session.getAttribute("modalidades").toString();
	String accion 						= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	List<CatModalidad> lisModalidad	=(List<CatModalidad>)request.getAttribute("lisModalidad");
	
	HashMap<String,String> mapaModalidadesEnCargas =(HashMap<String,String>) request.getAttribute("mapaModalidadesEnCargas");
	
	if (accion.equals("1")){
		modalidades = "";
		for(int i = 0; i < lisModalidad.size(); i++){
			CatModalidad modalidad = (CatModalidad) lisModalidad.get(i);
			if(request.getParameter(modalidad.getModalidadId()) != null ){
				if(modalidades.equals(""))
					modalidades = "'"+modalidad.getModalidadId()+"'";
				else
					modalidades += ",'"+modalidad.getModalidadId()+"'";						
			}
		}
		session.setAttribute("modalidades", modalidades);	
	}
	
	 
%>
<head></head>	
<body>
<div class="container-fluid">
	<h2>Elegir Modalidades<small class="text-muted fs-4">( Cargas:<%=cargas.replace("'","")%> )</small></h2>
	<form id="forma" name="forma" action="modalidades" method="post">
	<input name="Accion" type="hidden">
	<div class="alert alert-info">
		<a class="btn btn-primary btn-small" href="inscritos"><spring:message code="aca.Regresar"/></a>&nbsp;&nbsp;
		<input class="btn btn-primary" type="submit" value="Grabar" onclick="Mostrar();"/>
	</div>	
	<table style="width:50%" class="table table-sm">
		<tr class="table-info">
			<th width="20%">Elegir
				<a onclick="jQuery('.checkboxModalidad').prop('checked',true)" class="badge bg-success"><spring:message code='aca.Todos'/></a>&nbsp;
				<a onclick="jQuery('.checkboxModalidad').prop('checked', false)" class="badge bg-warning"><spring:message code='aca.Ninguno'/></a>	
			</th>
			<th><spring:message code="aca.Clave"/></th>
			<th><spring:message code="aca.Modalidad"/></th>
			<th class="text-end"><spring:message code="aca.Inscritos"/></th>
		</tr>			
<%
	String modalidadSelected = "";
	for(CatModalidad modalidad : lisModalidad){
		if (modalidades.contains("'" + modalidad.getModalidadId() + "'"))
			modalidadSelected = "checked";
		else
			modalidadSelected = "";
		
		String totAlumnos = "0";
		if (mapaModalidadesEnCargas.containsKey(modalidad.getModalidadId())){
			totAlumnos = mapaModalidadesEnCargas.get(modalidad.getModalidadId());
		}
		
%>
		<tr>
			<td>
				<input class="checkboxModalidad" type="checkbox" id="<%=modalidad.getModalidadId() %>" name="<%=modalidad.getModalidadId() %>" value="S"<%=modalidadSelected%>/>
			</td>
			<td>			
				<b><%=modalidad.getModalidadId() %></b>																			
			</td>
			<td>			
				<b><%=modalidad.getNombreModalidad() %></b>																
			</td>			
			<td class="text-end">			
				<b><%=totAlumnos%></b>
			</td>
		</tr>		
<%	} %>
		<tr><td colspan="4"><input class="btn btn-primary" type="submit" value="Grabar" onclick="Mostrar();"/></td></tr>
	</table>
	
	</form>
  </div>
</body>
