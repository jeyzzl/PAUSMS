<%@page import="aca.por.PorDocumento"%>
<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>


<jsp:useBean id="PorSeccionU" scope="page" class="aca.por.PorSeccionUtil"/>
<jsp:useBean id="PorSeccionEmpU" scope="page" class="aca.por.PorSeccionEmpUtil"/>
<jsp:useBean id="PorSeccionEmp" scope="page" class="aca.por.PorSeccionEmp"/> 

<script type="text/javascript">
	function Borrar(){
		document.frmPortafolio.Accion.value="2";
		document.frmPortafolio.submit();
	}	
</script>
<%
	String codigoPersonal 	= request.getParameter("codigoPersonal")==null?"0":request.getParameter("codigoPersonal");

	// Trae el valor del portafolio como parametro
	String porId			= request.getParameter("porId")==null?"0":request.getParameter("porId");
	

	String rutaCarpeta		= application.getRealPath("/WEB-INF/porDocente")+"/"+codigoPersonal;
	
	// Lista de portafolios 
	ArrayList<aca.por.PorSeccion> lisSeccion	=  PorSeccionU.listPortafolio(conEnoc, porId, "'A'"," ORDER BY ORDEN, SECCION_ID");
	
	// Map de secciones capturadas
	java.util.HashMap<String, aca.por.PorSeccionEmp> mapEmpSec		= PorSeccionEmpU.mapSeccionEmp(conEnoc, porId, codigoPersonal);	
%>
<div class="container-fluid">
	<h2><spring:message code="aca.DatosEmpleados"/><small class="text-muted fs-4">(<%=aca.vista.UsuariosUtil.getNombreUsuario(conEnoc, codigoPersonal, "")%> <%=codigoPersonal%> - <%= aca.por.PorDocumento.getNombre(conEnoc, porId) %> )</small></h2>
	<form name="frmSeccion" action="seccion" method="post" >
	<input type="hidden" name="Accion">
		<div class="alert alert-info">
			<a href="listaEmp?porId=<%=porId%>&codigoPersonal=<%=codigoPersonal%>" class="btn btn-primary"><i class="fas fa-arrow-left"></i> Regresar</a>
		</div> 
	</form>
  	<table  class="table table-sm table-bordered">
  	<tr class=table-info>
		<th align="center" width="5%"><spring:message code="aca.Numero"/></th>
		<th align="left" width="10%"><spring:message code="aca.Opcion"/></th>
		<th align="left" width="40%"><spring:message code="aca.Seccion"/></th>    	
    	<th align="left" width="15%"><spring:message code="aca.Tipo"/></th>
    	<th align="left" width="30%"><spring:message code="aca.Datos"/></th>
  	</tr>
  <% 
  	int row = 0;  
	for (aca.por.PorSeccion sec : lisSeccion) {
  		row++;  		
  		  
  		String nivel = "-";
  		if (sec.getSeccionId().length()==2)
  			nivel = "<span class='badge bg-dark'>"+sec.getTitulo()+"</span>";
  		else if (sec.getSeccionId().length()==4)
			nivel = "&nbsp;&nbsp;&nbsp;&nbsp;<span class='badge bg-success'>"+sec.getTitulo()+"</span>";
		else if (sec.getSeccionId().length()==6)
			nivel = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='badge bg-info'>"+sec.getTitulo()+"</span>";
		else if (sec.getSeccionId().length()==8)
			nivel = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='badge bg-warning'>"+sec.getTitulo()+"</span>";		
	
		String tipo		= "Informativo";
		String dato 	= "-";
		
		if (sec.getTipo().equals("C")){
			tipo = "Texto";
			dato = aca.por.PorSeccionEmp.getTextoCorto(conEnoc, porId, sec.getSeccionId(), codigoPersonal);
		}else if (sec.getTipo().equals("L")){
			tipo = "Texto";
			dato = aca.por.PorSeccionEmp.getTextoLargo(conEnoc, porId, sec.getSeccionId(), codigoPersonal);
		}else if (sec.getTipo().equals("A")){
			tipo = "Archivo";
		}else if (sec.getTipo().equals("I")){
			tipo = "Imágen";
		}else if (sec.getTipo().equals("E")){
			tipo = "Enlace externo";
			dato = aca.por.PorSeccionEmp.getTextoCorto(conEnoc, porId, sec.getSeccionId(), codigoPersonal);
		}else{
			tipo = "Informativo";
		}
			
		boolean tieneSeccion = false;
		if (mapEmpSec.containsKey(sec.getSeccionId())){
			tieneSeccion = true;
		}	
		String archivoNombre = "";
		if(sec.getTipo().equals("A")){
			String	nombreArchivo 	= aca.por.PorSeccionEmp.getNombreArchivo(conEnoc, porId, sec.getSeccionId(), codigoPersonal);
			String archivo []		= nombreArchivo.split("/");
			archivoNombre			= archivo[archivo.length-1];
		}
	
		String botonVer		= "<a href='verTxt?SeccionId="+sec.getSeccionId()+"' class='btn btn-sm btn-info'></span></a>";
		//Lista de Imagenes por seccion 
		ArrayList <aca.por.PorSeccionEmp>imagenList =  PorSeccionEmpU.getListImagen(conEnoc, porId, sec.getSeccionId(), codigoPersonal, "");
%>  
	<tr>
		<td align="center"><%= row %></td>
	
		<td align="left">		
<%
			if ( tieneSeccion ){
				if (sec.getTipo().equals("C") || sec.getTipo().equals("L")){
					//out.print(botonVer+" ");
%>
					<a href='verTxt?seccionId=<%=sec.getSeccionId()%>&codigoPersonal=<%=codigoPersonal%>&porId=<%=porId%>&folio=1&seccionTipo=<%=sec.getTipo()%>' class='btn btn-sm btn-info' title="Ver datos"><i class="fas fa-search"></i></a>
<%				}
			}
			if(tieneSeccion){ 
				if(sec.getTipo().equals("E")){
%>
					<a href='verFrame?SeccionId=<%=sec.getSeccionId()%>&codigoPersonal=<%=codigoPersonal%>&porId=<%=porId%>' class='btn btn-sm btn-info' title='Ver enlace'> <i class="fas fa-folder"></i></a>
<%
				}
			}
%>		                               
		</td> 
		
		<td align="left"><%=nivel%>&nbsp;&nbsp; <%= sec.getSeccionNombre() %>
<% 
		if (sec.getTipo().equals("I") && imagenList.size()!=0){
			
			for(aca.por.PorSeccionEmp imagen : imagenList){
%>				
				<a href='verImg?SeccionId=<%=sec.getSeccionId()%>&Folio=<%=imagen.getFolio()%>&codigoPersonal=<%=codigoPersonal%>&porId=<%=porId%>' class='btn btn-sm btn-info' title="Ver imagen <%=imagen.getFolio()%>"><i class="fas fa-image"></i></a>
<%
			}        
		}
%>
		</td>
		<td align="left"><%=tipo%></td>
		<td align="left">
<%
			if (dato.length() > 59) {
				String subDato  = dato.substring(0,59);
				out.print(subDato + "...");
			} else {
				out.print(dato);
			}
%>
		</td>
	</tr>	
<%	} %>  
  </table>  
</div>
<script>
	jQuery('.seccion').addClass('active');
</script>
<%@ include file="../../cierra_enoc.jsp" %>