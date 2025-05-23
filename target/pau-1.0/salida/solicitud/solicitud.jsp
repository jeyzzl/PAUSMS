<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="aca.salida.spring.SalAuto"%>
<%@page import="aca.salida.spring.SalAlumno"%>
<%@page import="aca.salida.spring.SalInforme"%>
<%@page import="aca.salida.spring.SalConsejero"%>
<%@page import="aca.salida.spring.SalSolicitud"%>
<%@page import="aca.salida.spring.SalGrupo"%>
<%@page import="aca.vista.spring.Maestros"%>

<%@ include file="id.jsp"%>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../seguro.jsp"%>
<%@ include file= "../../idioma.jsp" %>
<%
	String codigoPersonal 			= (String)request.getAttribute("CodigoPersonal");
	String periodo 					= (String)request.getAttribute("Periodo");
	String accion 					= request.getParameter("Accion")!=null?request.getParameter("Accion"):"0";
	String fechaHoy 				= aca.util.Fecha.getHoy();
	boolean registroLlegada 		= (boolean)request.getAttribute("registroLlegada");
	
	SimpleDateFormat sdf 			= new SimpleDateFormat("dd/MM/yyyy");
    Date firstDate   				= sdf.parse(fechaHoy);
    Date secondDate 				= new Date();
    Date hoy 						= sdf.parse(fechaHoy);	
    
    
    List<SalAlumno> lisAlumnos 					= (List<SalAlumno>)request.getAttribute("lisAlumnos");    
	List<SalSolicitud> lisSolicitud 			= (List<SalSolicitud>)request.getAttribute("lisSolicitud");
	List<SalGrupo> lisGrupo 					= (List<SalGrupo>)request.getAttribute("lisGrupo");
	List<Maestros> lisMaestros 					= (List<Maestros>)request.getAttribute("lisMaestros");
	HashMap<String,String> mapaGrupoNombre 		= (HashMap<String,String>)request.getAttribute("mapaGrupoNombre");
	HashMap<String,String> mapaMaestros 		= (HashMap<String,String>)request.getAttribute("mapaMaestros");
	HashMap<String,SalInforme> mapaInformes 	= (HashMap<String,SalInforme>)request.getAttribute("mapaInformes");
	HashMap<String,String> mapaAlumnos		 	= (HashMap<String,String>)request.getAttribute("mapaAlumnos");
	HashMap<String,String> mapaEmpleados	 	= (HashMap<String,String>)request.getAttribute("mapaEmpleados");
	HashMap<String,String> mapaAutos		 	= (HashMap<String,String>)request.getAttribute("mapaAutos");
	
	int dias 						= 0;
	int diasImplicados 				= 0;
	boolean pernoctar 				= false;
	boolean solicitudCorrecta 		= false;								
%>
<body>
<div class="container-fluid">
	<h2>Solicitudes de salidas</h2>
	<div class="alert alert-info">
<% 	if(registroLlegada){%>
		<a class="btn btn-primary" href="accion?Accion=1"><i class="fas fa-plus icon-white"></i></i>&nbsp;<spring:message code="aca.Anadir"/></a>
<% }else{%>
		<a class="btn btn-danger" href="" title="No has registrado alguna llegada"><i class="fas fa-plus icon-white"></i>&nbsp;<spring:message code="aca.Anadir"/></a>
<% }%>
		&nbsp;&nbsp;
		<a class="btn btn-primary" href="crearGrupo">Grupos</a>
	</div>
	<table style="width:100%" class="table table-sm">
		<thead class="table-light">
		<tr>
			<th width="2%" style="text-align:center"><spring:message code="aca.Opcion"/></th>
			<th width="1%" style="text-align:center">#</th>
			<th width="1%" style="text-align:center">Folio</th>
			<th width="7%" style="text-align:center">Fecha Salida</th>
			<th width="7%" style="text-align:center">Hora Salida</th>
			<th width="7%" style="text-align:center">Fecha Llegada</th>
			<th width="7%" style="text-align:center">Hora Llegada</th>						
			<th width="7%" style="text-align:left">Grupo</th>				
			<th width="7%" style="text-align:left">Lugar</th>
			<th width="14%" style="text-align:left">Responsable</th>
			<th width="5%" style="text-align:left">Registro</th>
			<th width="15%" style="text-align:left">Comentario</th>
			<th width="3%" style="text-align:center">Autos</th>
			<th width="3%" style="text-align:center">Alum</th>
			<th width="3%" style="text-align:center">Emp</th>
			<th width="3%" style="text-align:center">Inv</th>
			<th width="3%" style="text-align:center">Enviar</th>
			<th width="4%" style="text-align:center">PDF</th>				
			<th width="5%" style="text-align:center">Autorizó</th>						
			<th width="5%" style="text-align:center">Informe</th>
		</tr>
		</thead>
		
<%
	for (int i = 0; i < lisSolicitud.size(); i++){
		SalSolicitud sol = (SalSolicitud) lisSolicitud.get(i);
		
		String fechaSalida 		= sol.getFechaSalida().substring(0, sol.getFechaSalida().indexOf(" "));
		String horaSalida 		= sol.getFechaSalida().substring(sol.getFechaSalida().indexOf(" "), sol.getFechaSalida().lastIndexOf(":"));
		String fechaLlegada 	= sol.getFechaLlegada().substring(0, sol.getFechaLlegada().indexOf(" "));
		String horaLlegada 		= sol.getFechaLlegada().substring(sol.getFechaLlegada().indexOf(" "), sol.getFechaLlegada().lastIndexOf(":"));
		
		Date salidaTmp	 		= sdf.parse(fechaSalida);
		Date llegadaTmp	 		= sdf.parse(fechaLlegada);				
		int diasLlegada	 		= new Long((llegadaTmp.getTime()-salidaTmp.getTime())/1000/60/60/24).intValue();
		dias 					= new Long((salidaTmp.getTime()-hoy.getTime())/1000/60/60/24).intValue();
		
		if(diasLlegada > 0){
			pernoctar = true;
		}else{
			pernoctar = false;
		}
		
		if(sol.getPaisId().equals("91") && sol.getEstadoId().equals("19")){
			if(pernoctar){
				diasImplicados = 13;
			}else{
				diasImplicados = 3;
			}
		}else if(sol.getPaisId().equals("91")){
			if(pernoctar){
				diasImplicados = 13;
			}else{
				diasImplicados = 6;
			}
		}else {
			if(pernoctar){
				diasImplicados = 20;
			}else{
				diasImplicados = 6;
			}
		}
		//System.out.println("Datos:"+sol.getSalidaId()+":"+diasImplicados+":"+dias+":"+diasLlegada+":"+pernoctar+":"+sol.getPaisId()+":"+sol.getEstadoId());
		if(diasImplicados <= dias){
			solicitudCorrecta = true;
		}
		
		String grupoNombre = "";			
		if(mapaGrupoNombre.containsKey(sol.getGrupoId())){
			grupoNombre = mapaGrupoNombre.get(sol.getGrupoId());
		}
			
		String maestroNombre = "";
		if (mapaMaestros.containsKey(sol.getResponsable())){
			maestroNombre = mapaMaestros.get(sol.getResponsable());
		}
		
		boolean tieneAuto = false;
		int totAutos = 0;
		if (mapaAutos.containsKey(sol.getSalidaId())){
			totAutos = Integer.parseInt(mapaAutos.get(sol.getSalidaId()));
			if (totAutos > 0) tieneAuto = true;
		}		
		
		boolean tieneAlumno = false;
		int totAlumnos = 0;
		if (mapaAlumnos.containsKey(sol.getSalidaId())){
			totAlumnos = Integer.parseInt(mapaAlumnos.get(sol.getSalidaId()));
			if (totAlumnos > 0) tieneAlumno = true;
		}
		
		
		boolean tieneEmpleado 	= false;
		int totEmpleados 		= 0;
		if (mapaEmpleados.containsKey(sol.getSalidaId())){
			totEmpleados = Integer.parseInt(mapaEmpleados.get(sol.getSalidaId()));
			if (totEmpleados > 0) tieneEmpleado = true;
		}	
		
		String color = "";
		secondDate = sdf.parse(fechaSalida);
		int daysApart = (int)((secondDate.getTime() - firstDate.getTime()) / (1000*60*60*24l));
	    if (Math.abs(daysApart) >= 6){
	    	//System.out.println("7+ days apart.");
	    }else{
	    	color = "class='danger'";
	    	//System.out.println("Less than 10 days apart.");
	    }
	    if(secondDate.getTime() < firstDate.getTime()){
	    	color = "class='success'";
	    }
	    
	    String existeInforme = "<span class='badge bg-warning'><a href='editarInforme?SalidaId="+sol.getSalidaId()+"' style='color:white'>NO</a></span>";
	    if (mapaInformes.containsKey(sol.getSalidaId())){
	    	if(llegadaTmp.after(hoy)){
	    		existeInforme = "<span class='badge bg-info'>SI</span>";
	   		}else{
	   			existeInforme = "<span class='badge bg-info'><a href='editarInforme?SalidaId="+sol.getSalidaId()+"' style='color:white'>SI</a></span>";
	   		}
	    }else if (hoy.after(llegadaTmp) || hoy.equals(llegadaTmp)){
	    	existeInforme = "<span class='badge bg-warning'><a href='editarInforme?SalidaId="+sol.getSalidaId()+"' style='color:white'>NO</a></span>";
	    }else{
	    	existeInforme = "<span class='badge bg-warning' data-bs-toggle='tooltip' data-bs-placement='left' title='¡El informe se puede realizar hasta el día de la fecha de regreso o despúes!'>NO</span>";
	    }
	    String noCumple = "No cumple con el plazo requerido para hacer la solicitud. Salidas en Nuevo León que implican pernoctar fuera del campus son 14 días de anticipacion, sin pernoctar son 3 días."
				+" Destinos nacionales que implican pernoctar fuera del campus son 14 días de anticipacion, sin pernoctar son 7 días."
				+" Destinos Internacionales que implican pernoctar fuera del campus son 20 días de anticipacion, sin pernoctar son 7 días.";
%>
		<tr <%=color%>>
			<td style="text-align: center">
<%		if (sol.getAutorizo() == null || sol.getAutorizo().equals("0")){%>
				<a href="accion?salida=<%=sol.getSalidaId()%>" title="Modificar"><i class="fas fa-edit"></i></a>
<%			if(tieneAuto==false && tieneAlumno==false && tieneEmpleado==false){ %>					
				<a href="javascript:Borrar('<%=sol.getSalidaId()%>');" class="far fa-trash-alt" title="Eliminar"></a>&nbsp;
<%			}		
		}%>
			</td>			
			<td style="text-align:center"><%=i+1%></td>	
			<td style="text-align:center"><span class="badge bg-dark"><%=sol.getSalidaId()%></span></td>			
			<td style="text-align:center"><%=fechaSalida%></td>
			<td style="text-align:center"><%=horaSalida%></td>
			<td style="text-align:center"><%=fechaLlegada%></td>
			<td style="text-align:center"><%=horaLlegada%></td>
			<td ><%=grupoNombre%></td>
			<td><%=sol.getLugar()%></td>
			<td><%=maestroNombre%></td>
			<td><%=sol.getUsuario()%></td>
			<td><%=sol.getComentario()%></td>
<%		if (sol.getAutorizo() == null || sol.getAutorizo().equals("0")){ %>
			<td style="text-align:center;">
				<a class="btn <%=tieneAuto?"btn-success":"btn-secondary"%>" href="autos?SalidaId=<%=sol.getSalidaId()%>"><i class="fas fa-plus-square"></i></a>
			</td>
			<td style="text-align:center;">
				<a class="btn <%=tieneAlumno?"btn-success":"btn-secondary"%>" href="alumnos?salida=<%=sol.getSalidaId()%>"><i class="fas fa-plus-square"></i></a>
			</td>
			<td style="text-align:center;">	
				<a class="btn <%=tieneEmpleado?"btn-success":"btn-secondary"%>"" href="consejero?salida=<%=sol.getSalidaId()%>"><i class="fas fa-plus-square"></i></a>
			</td>
			<td style="text-align:center;">	
				<a class="btn btn-success" href="invitados?salida=<%=sol.getSalidaId()%>"><i class="fas fa-plus-square"></i></a>
			</td>
<%			if (sol.getEstado().equals("S") && tieneAuto && tieneAlumno && tieneEmpleado){ %>	
			<td style="text-align:center;">	
<%				if (solicitudCorrecta || sol.getPermiso().equals("S")){	%>		
				<a class="btn btn-success" href="enviar?SalidaId=<%=sol.getSalidaId()%>"><i class="far fa-paper-plane"></i></a>
<%				}else { %>		
				<span class="btn btn-secondary" data-bs-toggle="tooltip" data-bs-placement="right" title="<%=noCumple%>"><i class="far fa-paper-plane"></i></span>
<%				}%>		
			</td>
<%			}else if (sol.getEstado().equals("S")){%>
			<td style="text-align:center;">	
				<span class="badge bg-warning">Solicitud</span>
			</td>
<%			}else if (sol.getEstado().equals("E")){%>
			<td style="text-align:center;">	
				<span class="badge bg-success">Enviada</span>
			</td>
<%			}%>		
			<td style="text-align:center;">
				<a class="btn btn-info disabled"  href="pdf?salida=<%=sol.getSalidaId()%>">PDF</a>
			</td>
<%		}else{ %>
			<td style="text-align:center;">
				<a class="btn btn-primary disabled" href=""><i class="fas fa-plus-square"></i></a>
			</td>
			<td style="text-align:center;">
				<a class="btn btn-primary disabled" href=""><i class="fas fa-plus-square"></i></a>
			</td>
			<td style="text-align:center;">	
				<a class="btn btn-primary disabled" href=""><i class="fas fa-plus-square"></i></a>
			</td>
			<td style="text-align:center;">	
				<a class="btn btn-primary disabled" href=""><i class="fas fa-plus-square"></i></a>
			</td>
			<td style="text-align:center;">	
				<span class="badge bg-success">Enviada</span>
			</td>
			<td style="text-align:center;">
				<a class="btn btn-info" href="pdf?salida=<%=sol.getSalidaId()%>">PDF</a>
			</td>
<% 		} %>				
			<td style="text-align:center;">
<%		if (sol.getAutorizo()!=null && !sol.getAutorizo().equals("0")){
			out.println( "["+sol.getAutorizo()+"]");
		}
		if (sol.getFolio() != null && !sol.getFolio().equals("0")){
			out.println("<span class='badge bg-info'>  "+sol.getFolio()+" </span>");
		}%>		
			</td>	
			<td style="text-align:center;"><%=existeInforme%></td>
		</tr>
<%	}%>
	</table>
</div>
</body>
<script type="text/javascript">
	function Borrar(salidaId) {
		if (confirm("Estás seguro de eliminar la solicitud de salida?") == true) {
			document.location.href = "borrar?salida="+salidaId;
		}
	}

	function diasInvalidos(){
		alert("Entre...");
	/*	
		alert("No cumple con el plazo requerido para hacer la solicitud. Salidas en Nuevo León que implican pernoctar fuera del campus son 14 días de anticipacion, sin pernoctar son 3 días."
			+"Destinos nacionales que implican pernoctar fuera del campus son 14 días de anticipacion, sin pernoctar son 7 días."
			+"Destinos Internacionales que implican pernoctar fuera del campus son 20 días de anticipacion, sin pernoctar son 7 días.");
	*/					
	}
</script>

<script>	
	$(function () {
  		$('[data-bs-toggle="tooltip"]').tooltip();
	});
</script>