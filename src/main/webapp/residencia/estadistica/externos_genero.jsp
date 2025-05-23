<%@ page import= "java.util.List"%>
<%@ page import= "java.util.HashMap"%>
<%@ page import="aca.residencia.spring.ResDatos"%>
<%@ page import="aca.residencia.spring.ResComentario"%>
<%@ page import="aca.vista.spring.Inscritos"%>
<%@ page import= "aca.catalogo.spring.CatCarrera"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript">
	function Mostrar(){		
		document.forma.submit();
	}
</script>

<link rel="stylesheet" href="../../js/datepicker/datepicker.css" />

<%	
	String fechaIni			= request.getParameter("FechaIni")==null?(String) session.getAttribute("fechaIni"):request.getParameter("FechaIni");
	String fechaFin			= request.getParameter("FechaFin")==null?(String) session.getAttribute("fechaFin"):request.getParameter("FechaFin");
	String accion 			= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	
	if (accion.equals("1")){		
		session.setAttribute("fechaIni", fechaIni);
		session.setAttribute("fechaFin", fechaFin);
	}

	String sexo					= "X";
	int cont					= 0;
	int contRegistrados			= 0;
	int contMujeres				= 0;
	String modalidades 			= (String) session.getAttribute("modalidadesReportes");
	int contHombres				= 0;
	
	List<Inscritos> lisInscritos 	= (List<Inscritos>)request.getAttribute("lisInscritos");
	List<ResDatos> lisDatos			= (List<ResDatos>)request.getAttribute("lisDatos");
	
	// Map de carreras
	HashMap<String,CatCarrera> mapaCarreras 	= (HashMap<String,CatCarrera>) request.getAttribute("mapaCarreras");
	
	//Map razon
	HashMap<String, String>	mapaRazon =(HashMap<String, String>) request.getAttribute("mapaRazon");
	HashMap<String,ResComentario> mapaComentarios	= (HashMap<String,ResComentario>) request.getAttribute("mapaComentarios");
%>

<div class="container-fluid">

	<h2>Off-Campus Students by Gender</h2>
	<form name="forma" action="externos_genero?Accion=1" method="post">
		<div class="alert alert-info"><b>Note:</b> Students in red have not been registered. </div>
		<div class="alert alert-info">
			<a href="menu" class="btn btn-primary"><i class="fas fa-arrow-left"></i></a>&nbsp;&nbsp;
			Start Date: <input data-format="hh:mm:ss" id="FechaIni" name="FechaIni" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaIni%>" maxlength="10"/>&nbsp;&nbsp;
			<span class="add-on">
     		 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   			 </span>
			End Date: <input data-format="hh:mm:ss" id="FechaFin" name="FechaFin" type="text" data-date-format="dd/mm/yyyy" value="<%=fechaFin%>" maxlength="10"/>&nbsp;&nbsp;
			<span class="add-on">
     		 	<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
   			 </span>
			<a href="javascript:Mostrar();" class="btn btn-primary btn-sm"><i class="fas fa-sync-alt"></i></a>	
		</div>
	</form>	
<%
	boolean registrado = false;
 	ResDatos resDatos = new ResDatos();
    for(Inscritos inscritos: lisInscritos){
    	registrado = false;
    	for(int j = 0; j < lisDatos.size(); j++){
    		 resDatos = (ResDatos) lisDatos.get(j);
    		if(resDatos.getMatricula().equals(inscritos.getCodigoPersonal())){
    			registrado = true;
    			j = lisDatos.size();
    		}
    	}
    	if(registrado){
			contRegistrados++;
    	}else{
    		resDatos = new ResDatos();
    	}
	
	  	if(inscritos.getSexo().equals("F")){contMujeres++;}else{contHombres++; }
 	   	if(!inscritos.getSexo().equals(sexo)){
			sexo = inscritos.getSexo();
			cont = 0;
			if (!sexo.equals("X")){ out.print("</table>");}
%>	
	<div class="alert alert-success"><h3>Gender: [ <%=sexo.equals("M")?"Male":"Female" %> ]</h3></div>  
	<table id="table" class="table table-sm table-bordered">
	<thead class="table-info"> 
		<tr>
		  	<th><spring:message code="aca.Numero"/></th>
		    <th><spring:message code="aca.Matricula"/></th>
		    <th><spring:message code="aca.Nombre"/></th>
		    <th>Reason</th>
		    <th><spring:message code="aca.Tutor"/></th>
		    <th>Neighborhood | Street</th>
		    <th><spring:message code="aca.Telefono"/></th>
		    <th><spring:message code="aca.Carrera"/></th>
			<th><spring:message code="aca.Comentario"/></th>
		    <th><spring:message code="aca.FechaInsc"/></th>
		</tr>
	</thead>
<%
		} 	   	
		cont++;
			
			// Busca el nombre de la carrera
		String nombreCarrera = "";
		if (mapaCarreras.containsKey( inscritos.getCarreraId() )){
			nombreCarrera = mapaCarreras.get( inscritos.getCarreraId() ).getNombreCarrera(); 
		}
			// Busca el nombre de la razon
		String razon		= "-";
		if(mapaRazon.containsKey(resDatos.getRazon())){
			razon = mapaRazon.get(resDatos.getRazon());
		}

		String comentario = "";
		if(mapaComentarios.containsKey(inscritos.getCodigoPersonal())){
			comentario = mapaComentarios.get(inscritos.getCodigoPersonal()).getComentario();
		}
%>
		<tr>
		  	<td><font <%=registrado?"":" color=\"red\"" %>><%=cont %></font></td>
		    <td><font <%=registrado?"":" color=\"red\"" %>><%=inscritos.getCodigoPersonal() %></font></td>
		    <td><font <%=registrado?"":" color=\"red\"" %>><%=inscritos.getApellidoPaterno() %> <%=inscritos.getApellidoMaterno() %> <%=inscritos.getNombre() %></font></td>
		    <td><font <%=registrado?"":" color=\"red\"" %>><%=razon %></font></td>
		    <td><font <%=registrado?"":" color=\"red\"" %>><%=resDatos.getNombreTut() %> <%=resDatos.getApellidoTut() %></font></td>
		    <td><font <%=registrado?"":" color=\"red\"" %>><b><%=resDatos.getColonia() %></b> | <%=resDatos.getCalle() %></font></td>
		    <td><font <%=registrado?"":" color=\"red\"" %>>(<%=resDatos.getTelArea() %>) <%=resDatos.getTelNum() %></font></td>
		    <td><font <%=registrado?"":" color=\"red\"" %>><%=nombreCarrera%></font></td>
		    <td><%=comentario%></td>
			<td><%=inscritos.getfInscripcion()%></td>
	    </tr>
<%	
	} // fin del for
%>
	</table>
	<div class="alert alert-success">Totals &nbsp;&nbsp;&nbsp;
		<b>Males:</b> <%=contHombres %>
		&nbsp;&nbsp;&nbsp;		
		<b>Females:</b> <%=contMujeres %>
		&nbsp;&nbsp;&nbsp;	
		<b>Registered Off-campus:</b> <%=contRegistrados %>
		&nbsp;&nbsp;&nbsp;		
		<b>Unregistered Off-campus:</b> <%=lisInscritos.size()-contRegistrados %>
		&nbsp;&nbsp;&nbsp;		
		<b>Enrolled Off-campus:</b> <%=lisInscritos.size()%>	
	</div>	
<%
	if(lisInscritos.size() == 0){
		out.print("<strong>No students enrolled in current blocks</strong>");
	}else{
		lisInscritos = null;
		lisDatos = null;
	}
%>
	  <br>
</div>
<script type="text/javascript" src="../../js/datepicker/datepicker.js"></script>
<script>	
	jQuery('#FechaIni').datepicker();
	jQuery('#FechaFin').datepicker();	
</script>