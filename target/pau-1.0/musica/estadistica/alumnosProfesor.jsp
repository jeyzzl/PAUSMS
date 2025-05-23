<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.musica.MusiPeriodo"%>
<%@page import="aca.musica.MusiAlumno"%>

<jsp:useBean id="PeriodoU" scope="page" class="aca.musica.MusiPeriodoUtil"/>
<jsp:useBean id="alumnoU" scope="page" class="aca.musica.MusiAlumnoUtil"/>
<jsp:useBean id="calculoDetU" scope="page" class="aca.musica.MusiCalculoDetUtil"/>
<jsp:useBean id="MaestrosU" scope="page" class="aca.musica.MusiMaestroUtil"/>

<script type="text/javascript" src="../../js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">

function recarga(){
	document.forma.submit();
}	
  
$(document).ready(function() {  
     $(".botonExcel").click(function(event) {  
     	$("#datos_a_enviar").val( $("<div>").append( $("#Exportar_a_Excel").eq(0).clone()).html());  
     	$("#FormularioExportacion").submit();  
	});  
});	
</script>
<% 
	String codigoId			= (String) session.getAttribute("CodigoId");

	String periodoId 		= request.getParameter("PeriodoId");	
	if (periodoId==null || periodoId.equals("null") || periodoId.equals("")){
		periodoId 			= aca.musica.MusiPeriodo.getPeriodoActual(conEnoc);
	}
	String bgColor			= "";

	ArrayList lisPeriodos	= new ArrayList();
	lisPeriodos				= PeriodoU.getListAll(conEnoc,"ORDER BY PERIODO_ID");
	
	ArrayList lisInscritos	= new ArrayList();
	lisInscritos			= alumnoU.getListInscrito(conEnoc, periodoId, " ORDER BY APELLIDO_PATERNO, APELLIDO_MATERNO, NOMBRE ");
	
	int cont = 0;
	
	//Nombres de Maestros
	ArrayList<aca.musica.MusiMaestro> Profesores 		= MaestrosU.getListMaestros(conEnoc, periodoId, "ORDER BY NOMBRE");
	
	String maestroNombre = "";
			
%> 

<div class="container-fluid">
<h1>Alumnos Inscritos</h1>
<div class="alert alert-info">
	<a href="opcion" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;
	<b>Seleccione el Periodo:</b>
	<form action="alumnosProfesor" name="forma">
          <select onchange='javascript:recarga()' name="PeriodoId">
<%
	for (int i=0; i< lisPeriodos.size(); i++){
		aca.musica.MusiPeriodo periodo = (aca.musica.MusiPeriodo) lisPeriodos.get(i);
%>
			<option <%if( periodoId.equals(periodo.getPeriodoId() ))out.print(" Selected ");%> value="<%= periodo.getPeriodoId() %>">[<%= periodo.getPeriodoId() %>] <%= periodo.getPeriodoNombre() %></option>
<%
	}
%>
          </select>
	</form>
</div>
<%
		for(aca.musica.MusiMaestro  maestros: Profesores){
			maestroNombre = maestros.getNombre() +" "+ maestros.getApellidoPaterno() +" "+ maestros.getApellidoMaterno();
%>
			<div class="alert alert-info" style="margin-bottom:3px;">
				<h3><%=maestroNombre%></h3>
			</div> 
			
			<table class="table table-striped">
						<tr>
							<th width="4%"><h4><spring:message code="aca.Numero"/></h4></th>
						    <th width="7%"><h4><spring:message code="aca.Matricula"/></h4></th>
						    <th width="20%"><h4><spring:message code="aca.Nombre"/> </h4></th>
						    <th width="20%"><h4>Apellido</h4></th>
						    <th width="10%"><h4>F.Nac.</h4></th>
						    <th width="4%"><h4><spring:message code="aca.Edad"/></h4></th>
						    <th width="7%"><h4><spring:message code="aca.Seguro"/></h4></th>
						    <th width="7%"><h4>><spring:message code="aca.Telefono"/></h4></th>
						    <th width="7%"><h4><spring:message code="aca.Instrumento"/></h4></th>
						</tr>
						
						<%
						for (int j=0; j<lisInscritos.size(); j++){
						aca.musica.MusiAlumno alumno = (aca.musica.MusiAlumno) lisInscritos.get(j);
						String CodigoId = alumno.getCodigoId();
						String telefono = alumno.getTelefono();
						if(telefono==null){
							telefono = "-";
						}else if(telefono.equals("null")){
							telefono = "-";
						}
						String instrumento = "0";
						String maestro ="";
						if(instrumento.equals("null") || instrumento.equals(null))instrumento="-";
						ArrayList lista = calculoDetU.getListAsignados(conEnoc, CodigoId, periodoId, "ORDER BY 1");
						for (int k=0; k<lista.size(); k++){
							aca.musica.MusiCalculoDet alum = (aca.musica.MusiCalculoDet) lista.get(k);
							if(!alum.getMaestro().equals("0"))maestro=alum.getMaestro();
							if(alum.getInstrumentoId()!=null)
								if(!alum.getInstrumentoId().equals("0"))instrumento=alum.getInstrumentoId();
						}
						instrumento = aca.musica.MusiInstrumento.getNombreInstrumento(conEnoc, instrumento);
						String nombreMaestro = aca.musica.MusiMaestro.getNombre(conEnoc, maestro, "NOMBRE");
						if(maestros.getMaestroId().equals(maestro)){cont++;
						%>
						 <tr > 
						    <td align="center"><font size="1"><%=cont%></font></td>
						    <td align="center"><font size="1"><%=CodigoId%></font></td> 
						    <td align="left"><font size="1"><%=alumno.getNombre()%></font></td>
						    <td align="left"><font size="1"><%=alumno.getApellidoPaterno()%>&nbsp;&nbsp;<%=alumno.getApellidoMaterno()%></font></td>
						    <td align="center"><font size="1"><%=alumno.getFechaNac()%></font></td>
						    <td align="center"><font size="1"><%=alumno.getEdad(conEnoc) %></font></td> 
						    <td align="center"><font size="1"><%=alumno.getSeguro()%></font></td>
						    <td align="center"><font size="1"><%=telefono %></font></td>    
			    			<td align="center"><font size="1"><%=instrumento  %></font></td>           
						  </tr>
						  <%
						  }
						}cont=0; %>
						</table>
	</div>
<%
			}
			
%>

<%@ include file = "../../cierra_enoc.jsp"%>