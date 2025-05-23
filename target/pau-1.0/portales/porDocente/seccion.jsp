<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%@ include file= "menuPortal.jsp" %>

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
	String codigoPersonal 		= session.getAttribute("codigoPersonal").toString();

	// Trae el valor del portafolio en sesion
	String portafolioSession	=session.getAttribute("portafolioId")==null?"0":session.getAttribute("portafolioId").toString();

	// Trae el valor del portafolio como parametro
	String portafolioId			= request.getParameter("PortafolioId")==null?"0":request.getParameter("PortafolioId");
	
	// Si el portafolio como parametro tiene un nuevo valor
	if ( !portafolioId.equals("0") ){
		session.setAttribute("portafolioId",portafolioId);
	}else{
		portafolioId = portafolioSession;
	}
	
	String rutaCarpeta		= application.getRealPath("/WEB-INF/porDocente")+"/"+codigoPersonal;
	
	// Lista de portafolios 
	ArrayList<aca.por.PorSeccion> lisSeccion	=  PorSeccionU.listPortafolio(conEnoc, portafolioId, "'A'"," ORDER BY ORDEN, SECCION_ID");
	
	// Map de secciones capturadas
	java.util.HashMap<String, aca.por.PorSeccionEmp> mapEmpSec		= PorSeccionEmpU.mapSeccionEmp(conEnoc, portafolioId, codigoPersonal);
	
	// Lista de imagenes 
%>
<div class="container-fluid">
	<h2><spring:message code="aca.Secciones"/><small class="text-muted fs-4">( <%= aca.por.PorDocumento.getNombre(conEnoc, portafolioId) %> )</small></h2>
	<form name="frmSeccion" action="seccion" method="post" >
	<input type="hidden" name="Accion">
	<div class="alert alert-info"><a href="portafolio" class="btn btn-primary"><i class="icon-chevron-left icon-white"></i> Regresar</a></div> 
	</form>
	<div class="alert alert-info">
		<h4><%=codigoPersonal%> - <%=aca.vista.MaestrosUtil.getNombreCorto(conEnoc, codigoPersonal, "NOMBRE") %></h4>
	</div>	
  	<table style="width:80%"  class="table table-condensed">
  	<tr>
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
  			nivel = "<span class='label label-pill label-inverse'>"+sec.getTitulo()+"</span>";
  		else if (sec.getSeccionId().length()==4)
			nivel = "&nbsp;&nbsp;&nbsp;&nbsp;<span class='label label-pill label-success'>"+sec.getTitulo()+"</span>";
		else if (sec.getSeccionId().length()==6)
			nivel = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='label label-pill label-info'>"+sec.getTitulo()+"</span>";
		else if (sec.getSeccionId().length()==8)
			nivel = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span class='label label-pill label-warning'>"+sec.getTitulo()+"</span>";		

		String tipo		= "Informativo";
		String dato 	= "-";
		if (sec.getTipo().equals("C")){
			tipo = "Texto";
			dato = aca.por.PorSeccionEmp.getTextoCorto(conEnoc, portafolioId, sec.getSeccionId(), codigoPersonal);
		}else if (sec.getTipo().equals("L")){
			tipo = "Texto";
			dato = aca.por.PorSeccionEmp.getTextoLargo(conEnoc, portafolioId, sec.getSeccionId(), codigoPersonal);
		}else if (sec.getTipo().equals("A")){
			tipo = "Archivo";
		}else if (sec.getTipo().equals("I")){
			tipo = "Imágen";
		}else if (sec.getTipo().equals("E")){
			tipo = "Enlace externo";
			dato = aca.por.PorSeccionEmp.getTextoCorto(conEnoc, portafolioId, sec.getSeccionId(), codigoPersonal);
		}else{
			tipo = "Informativo";
		}
		
		boolean tieneSeccion = false;
		if (mapEmpSec.containsKey(sec.getSeccionId())){
			tieneSeccion = true;
		}	
		String archivoNombre = "";
		if(sec.getTipo().equals("A")){
			String	nombreArchivo 	= aca.por.PorSeccionEmp.getNombreArchivo(conEnoc, portafolioId, sec.getSeccionId(), codigoPersonal);
			String archivo []		= nombreArchivo.split("/");
			archivoNombre			= archivo[archivo.length-1];
		}
		String botonGrabar		= "<a href='seccionAccion?Accion=0&SeccionId="+sec.getSeccionId()+"' class='btn btn-sm btn-success'><i class='icon-pencil icon-white'></i></a>";
		String botonBorrarSec	= "<a href='seccionAccion?Accion=4&SeccionId="+sec.getSeccionId()+"' class='btn btn-sm btn-danger'><i class='icon-trash icon-white'></i></a>";
		String botonBorrar		= "<a href='borrarImagen?SeccionId="+sec.getSeccionId()+"' class='btn btn-sm btn-danger'><i class='icon-trash icon-white'></i></a>";		
		String botonImagen 		= "<a href='verImagen?SeccionId="+sec.getSeccionId()+"' class='btn btn-sm btn-info'><i class='icon-picture icon-white'></i></a>";
		String botonArchivo		= "<a href='bajarArchivo?SeccionId="+sec.getSeccionId()+"&archivoNombre="+archivoNombre+"' class='btn btn-sm btn-info'><i class='icon-file icon-white'></i></a>";
		String botonArchivoBorrar	= "<a href='borrarArchivo?SeccionId="+sec.getSeccionId()+"&archivoNombre="+archivoNombre+"' class='btn btn-sm btn-danger'><i class='icon-trash icon-white'></i></a>";
		String botonFrame 		= "<a href='verFrame?SeccionId="+sec.getSeccionId()+"' class='btn btn-sm btn-info'><i class='icon-file icon-white'></i></a>";
		//Lista de Imagenes por seccion 
		ArrayList <aca.por.PorSeccionEmp>imagenList =  PorSeccionEmpU.getListImagen(conEnoc, portafolioId, sec.getSeccionId(), codigoPersonal, "");
%>  
	<tr>
		<td align="center"><%= row %></td>
		<td align="left">		
<%
			if ( tieneSeccion ){
				if (sec.getTipo().equals("I") && imagenList.size()<5){
					out.print(botonGrabar+" ");
				}else if (sec.getTipo().equals("C") || sec.getTipo().equals("L")){
					out.print(botonGrabar+" ");
					out.print(botonBorrarSec);
				}else if (sec.getTipo().equals("A")){
					out.print(botonArchivo+" ");
					out.print(botonArchivoBorrar);
				}else if (sec.getTipo().equals("E")){
					out.print(botonGrabar);
					out.print(botonFrame);
					out.print(botonBorrarSec);
				}
			}else{
					if (sec.getTipo().equals("-") || sec.getTipo().equals("C") || sec.getTipo().equals("L") || sec.getTipo().equals("A") || sec.getTipo().equals("E") || sec.getTipo().equals("I")){
						out.print(botonGrabar);
					}else{
						out.print(" ");
					}	
			}
%>		
		</td> 
		<td align="left"><%=nivel%>&nbsp;&nbsp; <%= sec.getSeccionNombre() %> - 
<% 		
		if (sec.getTipo().equals("I") && imagenList.size()!=0){			
			for(aca.por.PorSeccionEmp imagen : imagenList){
%>		
				<a href='verImagen?SeccionId=<%=sec.getSeccionId()%>&Folio=<%=imagen.getFolio() %>' class='btn btn-sm btn-info'><i class='icon-picture icon-white' title="Ver imagen <%=imagen.getFolio()%>"></i></a>
				<a href='borrarImagen?SeccionId=<%=sec.getSeccionId()%>&Folio=<%=imagen.getFolio() %>' class='btn btn-sm btn-danger'><i class='icon-trash icon-white' title="Eliminar img <%=imagen.getFolio()%>"></i></a>
<%
			}
		} 	
%>
		</td>
		<td align="left"><%= tipo %></td>
		<td align="left">
			<%	
			if(dato == null){
				dato = "-";
			}
			
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