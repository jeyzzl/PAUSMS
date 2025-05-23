<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.catalogo.CatCarrera"%>
<!-- bootstrap -->
  
  <link rel="stylesheet" href="../../bootstrap/css/bootstrap.min.css" type="text/css" media="screen" />
  
<!-- end bootstrap -->
<jsp:useBean id="PlazaU" scope="page" class="aca.afe.AfePlazaUtil"/>
<%
	java.text.DecimalFormat getFormato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");

	String matricula 		= (String) session.getAttribute("codigoAlumno");
	String colorPortal 		= (String)session.getAttribute("colorPortal");
	String strOpcion 		= request.getParameter("Opcion")==null?"1":request.getParameter("Opcion");
	
	String colportorId		= aca.alumno.AlumUtil.esColportor(conEnoc,matricula);
	
	String ccostoTemp		= "x";
	String bgColor			= "";
	String imagen 			= "";
	String alumno 			= "";
	String mensaje			= "";
	
	int row = 0;
	double precio = 0, hrsPrepa = 0, hrsUniv = 0, totPrepa = 0, totUniv = 0;
	
	// Llenar las listas
	ArrayList<aca.afe.AfePlaza> lisPlaza 		= null;
	if (strOpcion.equals("1")) 
		lisPlaza = PlazaU.getListVacantes(conEnoc,"ORDER BY CCOSTO_ID, PUESTO_ID");
	else if (strOpcion.equals("2"))
		lisPlaza = PlazaU.getListAsignados(conEnoc,"ORDER BY CCOSTO_ID, PUESTO_ID");
	else
		lisPlaza = PlazaU.getListAll(conEnoc,"ORDER BY CCOSTO_ID, PUESTO_ID");
	
%>
<head><link href="css/portalAlumno.css" rel="STYLESHEET" type="text/css"></head>
<form name="frmBecas" action="becas" method='post'>
  <table style="width:90%" align="center" class="tabla" >
    <tr>
	  <td style="text-align:center;"><h3><spring:message code="aca.Alumno"/></h3></td>
	</tr>
	<tr>
	  <td align="center"> 
	    Matricula: [ <%= matricula %> ] <%= aca.alumno.AlumUtil.getNombreAlumno(conEnoc,matricula,"NOMBRE") %>&nbsp; &nbsp;
	    <a href="https://academico.um.edu.mx/financiero/hojaAlumnoFromAcademico.html?matricula=<%=matricula%>">[ Datos Autofinanciamiento ]</a>
	  </td>	  
	</tr>      
	<tr>
	  <td style="text-align:center;"><h3>Autofinanciamiento Estudiantil</h3></td>
	</tr>
	<tr>
	  <td align="center">
  		<table style="width:80%" class="table table-fullcondensed">
  		  <tr><th style="font-size:10pt;" colspan="2"><b> F o r m a t o s &nbsp; &nbsp; d e &nbsp; &nbsp; B e c a </b></th></tr>
		  <tr>  		
	  		<td style="font-size:9pt;"><a href="Solicitud_beca_basica.pdf"><b>Beca Básica:</b></a></td>
	  		<td style="font-size:9pt;"><a href="Solicitud_beca_basica.pdf">
	  		Podrá recibir desde $4,800 hasta $11,200 pesos semestrales dependiendo del tipo de servicio becario que realice.
	  		</a></td>
		  </tr>
		  <tr>	  		
	  		<td style="font-size:9pt;"><a href="Solicitud_beca_adicional.pdf"><b>Beca Adicional</b></a></td>
	  		<td style="font-size:9pt;"><a href="Solicitud_beca_adicional.pdf">
	  		Podrá recibir hasta un 34% de su aportación al proyecto educativo.
	  		</a></td>
		  </tr>
		  <tr>	  		
	  		<td style="font-size:9pt; Se hará una estimación según el proyecto. Recibirá de acuerdo a los resultados financieros finales reales.
Beca Institucional 	Recibirá el 100% de la enseñanza y de internado durante los dos primeros años de sus estudios.
Puestos de Trabajo "><a href="Solicitud_beca_proyecto.pdf"><b>Beca Proyecto</b></a></td>
	  		<td style="font-size:9pt;"><a href="Solicitud_beca_proyecto.pdf">
	  		Se hará una estimación según el proyecto. Recibirá de acuerdo a los resultados financieros finales reales.
	  		</a></td>
		  </tr>
		  <tr>	  		
	  		<td style="font-size:9pt;"><a href="Solicitud_beca_institucional.pdf"><b>Beca Institucional</b></a></td>
	  		<td style="font-size:9pt;"><a href="Solicitud_beca_institucional.pdf">
	  		Recibirá el 100% de la enseñanza y de internado durante los dos primeros años de sus estudios.
	  		</a></td>
		  </tr>
  		</table>
	  </td>
	</tr>
	<tr><td style="text-align:center;" ><h3>Colportores</h3></td></tr>  	
	<tr>
	  <td align="center">
	  <%if (colportorId.equals("A")){
	    	out.println("¡...No estas registrado como colportor...!");
	 	}else{
%>
		 <a href="https://academico.um.edu.mx/financiero/consultaDocumentosFiltrados.html?matricula=<%=matricula%>">Registra tus Documentos</a> &nbsp; &nbsp;
		 <a href="https://academico.um.edu.mx/financiero/clientesColportor.html?matricula=<%=matricula%>">Registra tus clientes</a> &nbsp; &nbsp;
		 <a href="https://academico.um.edu.mx/financiero/informeColportors.html?matricula=<%=matricula%>">Informes</a>
	 	<% }%>    
	  </td>
	</tr>
	<tr>
	  <td align="center" >
	    <font size="3"><b>Puestos de Trabajo </b></font>
	    <select name="Opcion" onchange="document.frmBecas.submit()">
	      <option value='1' <%if(strOpcion.equals("1"))out.print("Selected");%>>Ver puestos vacantes</option>
	      <option value='2' <%if(strOpcion.equals("2"))out.print("Selected");%>>Ver puestos Asignados</option>
	      <option value='3' <%if(strOpcion.equals("3"))out.print("Selected");%>>Ver todos</option>
	    </select>
	  </td>
	</tr>		
  </table>
  
  </form>
  
	  <table style="width:90%" align="center" class="table table-condensed table-bordered">
<%
	for (int i=0; i< lisPlaza.size(); i++){		
	 	aca.afe.AfePlaza plaza = (aca.afe.AfePlaza) lisPlaza.get(i);	 	
	 	if (!ccostoTemp.equals(plaza.getCcostoId())){
	 		ccostoTemp = plaza.getCcostoId();
	 		row=0;
%>
		  <tr><td colspan="16" align= "left"> &nbsp; </td></tr>
		  <tr>
		    <td colspan="16" align= "left" style="font-size:12pt;">
		      <b><%= plaza.getCcostoId() %> - <%= aca.afe.AfeCCostoPuestoUtil.getCCostoNombre(conEnoc,plaza.getEjercicioId(),plaza.getCcostoId()) %></b>
		    </td>
		  </tr> 		  
		  <tr>
  		    <th><spring:message code="aca.Numero"/></th>
  		    <th><spring:message code="aca.Alumno"/></th>	    
  		    <th>Puesto</th>
  		    <th>Turno</th>
  		    <th>D</th>
  		    <th>L</th>
  		    <th>M</th>
  		    <th>M</th>
  		    <th>J</th>
  		    <th>V</th>
  		    <th>S</th>  		    
  		    <th width="40%">Requisitos</th>
  		    <th>email</th>
  		    <th>Precio</th>
  		    <th>Hrs.Prepa</th>  		    
  		    <th>Tot. Prepa</th>
  		    <th>Hrs.Univ.</th>
 		    <th>Tot. Univ</th>
  		  </tr>
<%	 	}
	 	row++;
	 	precio 		= Double.valueOf(aca.afe.AfeCCostoPuestoUtil.getPrecio(conEnoc, plaza.getPuestoId())).doubleValue();
	 	hrsPrepa	= Double.valueOf(plaza.getMaximoHorasBach()).doubleValue();
	 	totPrepa 	= precio * hrsPrepa;
	 	hrsUniv		= Double.valueOf(plaza.getMaximoHorasUniv()).doubleValue();
	 	totUniv 	= precio * hrsUniv;	 	
	 	alumno 		= aca.afe.AfeContratoAlumnoUtil.getAlumnoPuesto( conEnoc,plaza.getId());
	 		
	 	if ((row % 2) == 0 ) bgColor = "bgcolor = '#dddddd'"; else bgColor = "";
%>
  		  <tr <%= bgColor %> >
  		    <td><%= row %></td>
<% 			if (!alumno.equals("Vacante")){ %>
				<td align="center" class="ayuda alumno <%=alumno%>"><b><%= alumno %></b></td>
<%			}else{ %>
				<td align="center"><em><%= alumno %></em></td>
<%			} %>  		    
  		    <td><%= aca.afe.AfeCCostoPuestoUtil.getPuestoNombre(conEnoc,plaza.getPuestoId()) %></td>
  		    <td><%= plaza.getTurno().equals("M")?"Matutino":"Vespertino" %></td>
  		    <td><img src='<%= plaza.getDias().substring(0,1).equals("1")?"img/checked.ico":"img/unchecked.ico"%>' width="15"></td>
  		    <td><img src='<%= plaza.getDias().substring(1,2).equals("1")?"img/checked.ico":"img/unchecked.ico"%>' width="15"></td>
  		    <td><img src='<%= plaza.getDias().substring(2,3).equals("1")?"img/checked.ico":"img/unchecked.ico"%>' width="15"></td>
  		    <td><img src='<%= plaza.getDias().substring(3,4).equals("1")?"img/checked.ico":"img/unchecked.ico"%>' width="15"></td>
  		    <td><img src='<%= plaza.getDias().substring(4,5).equals("1")?"img/checked.ico":"img/unchecked.ico"%>' width="15"></td>
  		    <td><img src='<%= plaza.getDias().substring(5,6).equals("1")?"img/checked.ico":"img/unchecked.ico"%>' width="15"></td>
  		    <td><img src='<%= plaza.getDias().substring(6,7).equals("1")?"img/checked.ico":"img/unchecked.ico"%>' width="15"></td>
  		    <td><%= plaza.getRequisitos() %></td>
  		    <td><%= plaza.getEmail() %></td>
  		    <td align="right"><%= getFormato.format(precio) %></td>
  		    <td align="right"><%= plaza.getMaximoHorasBach() %></td>  		    
  		    <td align="right"><%= getFormato.format(totPrepa) %></td>
  		    <td align="right"><%= plaza.getMaximoHorasUniv() %></td>
  		    <td align="right"><%= getFormato.format(totUniv) %></td>
  		  </tr>
<%	} %>		  
	</table>	   
   	
<%@ include file= "../../cierra_enoc.jsp" %>