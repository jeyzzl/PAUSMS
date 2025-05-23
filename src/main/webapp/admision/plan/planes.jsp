<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.vista.Inscritos"%>
<%@page import= "aca.alumno.spring.AlumPlan"%>
<%@page import= "aca.acceso.spring.Acceso"%>
<%@page import= "aca.catalogo.spring.CatCarrera"%>
<%@page import= "aca.catalogo.spring.CatNivel"%>
<%@page import= "aca.plan.spring.MapaPlan"%>
<%@page import= "aca.plan.spring.MapaMayorMenor"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type = "text/javascript">	
	function Anadir(){
		document.frmdatos.submit();
	}	
	function Borrar(plan){
		if(confirm("Are you sure you want to delete this record?")==true){
  			document.location.href="borrarPlan?PlanId="+plan;
		}			
	}	
	function Activar(plan){
		document.location.href="activarPlan?PlanId="+plan;		
	}
</script>
	
<%
	String Plan 			= request.getParameter("Plan")==null?"":request.getParameter("Plan");
	String materias			= request.getParameter("Materias")==null?"":request.getParameter("Materias");
	String mensaje 			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	String codigoPersonal	= (String)request.getAttribute("codigoPersonal");
	String codigoAlumno		= (String)request.getAttribute("codigoAlumno");
	String nombreAlumno		= (String)request.getAttribute("nombreAlumno");
	String carreraId		= (String)request.getAttribute("carreraId");

	boolean esInscrito		= (boolean)request.getAttribute("esInscrito");
	boolean datosAcademicos = (boolean)request.getAttribute("datosAcademicos");
    boolean datosPersonales = (boolean)request.getAttribute("datosPersonales");
    boolean datosUbicacion 	= (boolean)request.getAttribute("datosUbicacion");
    boolean resCandado 		= (boolean)request.getAttribute("resCandado");
		
	Acceso acceso 								= (Acceso)request.getAttribute("acceso");	
	List<AlumPlan> lisPlanes 					= (List<AlumPlan>)request.getAttribute("lisPlanes");
	List<String>  lisPlanesInscritos			= (List<String>)request.getAttribute("lisPlanesInscritos");
	
	HashMap<String,MapaPlan> mapaPlanes			= (HashMap<String,MapaPlan>)request.getAttribute("mapaPlanes");
	HashMap<String,CatCarrera> mapaCarreras		= (HashMap<String,CatCarrera>)request.getAttribute("mapaCarreras");
	HashMap<String,CatNivel> mapaNiveles		= (HashMap<String,CatNivel>)request.getAttribute("mapaNiveles");
	HashMap<String,String> mapaMateriasAlumno	= (HashMap<String,String>)request.getAttribute("mapaMateriasAlumno");
	HashMap<String,MapaMayorMenor> mapMayores	= (HashMap<String,MapaMayorMenor>)request.getAttribute("mapMayores");
	HashMap<String,MapaMayorMenor> mapMenores	= (HashMap<String,MapaMayorMenor>)request.getAttribute("mapMenores");
	
%>
<div class="container-fluid">
	<h2>Assign Plan <small class="text-muted fs-5"> ( <%=codigoAlumno%>-<%=nombreAlumno%> )</small></h2>
	<div class="alert alert-info">
		<a class="btn btn-success" href="javascript:Anadir()"><spring:message code='aca.Anadir'/></a>&nbsp;		
	</div>
<%	if (!mensaje.equals("-")){%>
	<div class="alert alert-info"><%=mensaje%></div>
<%	} %>	
	<form action="planes" method="post" name="frmalumno">
	 	<input type="hidden" name="Accion">
	 	<input type="hidden" name="Plan">	
	</form>
<%	if(datosAcademicos&&datosPersonales&&datosUbicacion){ %>
	<form action="facultad" name="frmdatos" method="post">
	<input type="hidden" name="CodigoAlumno" value="<%=codigoAlumno%>">	
	<table class="table table-sm table-bordered">
	<tr class="table-info"> 
    	<td width="5%"><strong>Op.</strong></td>
        <td width="5%"><strong><spring:message code="aca.Clave"/></strong></td>
        <td width="20%"><strong><spring:message code="aca.Plan"/> <spring:message code="aca.Nombre"/></strong></td>
        <td width="2%"><strong>Year</strong></td>
        <td width="2%"><strong>Cycle</strong></td>
        <td width="5%"><strong><spring:message code="aca.Inscrito"/></strong></td>
        <td width="5%"><strong><spring:message code="aca.Materias"/></strong></td>
        <td width="8%"><strong><spring:message code="aca.Nivel"/></strong></td>
        <td width="10%" class="text-center"><strong><spring:message code="aca.FechaInicio"/></strong></td>
        <td width="8%" class="text-center"><strong><spring:message code="aca.Status"/></strong></td>
        <td width="8%" class="text-center"><strong><spring:message code="aca.Principal"/> Plan</strong></td>
        <td width="5%" class="text-center"><strong><spring:message code="aca.Escala"/></strong></td>
        <td width="7%"><strong><spring:message code="aca.Tipo"/></strong></td>
		<td width="10%" class="text-center"><strong>Major / Minor</strong></td>        
	</tr>
<%
		 for (AlumPlan plan : lisPlanes){				
				String carreraPlan		= "0";
				String nivelPlan 		= "0";
				String nivelNombre		= "-";
				String nombrePlan 		= "-";
				String planOficial		= "-";
				String grado			= "<span class='badge bg-dark rounded-pill'>"+plan.getGrado()+"</span>";
				String ciclo			= "<span class='badge bg-dark rounded-pill'>"+plan.getCiclo()+"</span>";
				boolean planInscrito 	= lisPlanesInscritos.contains(plan.getPlanId())?true:false;
				if (mapaPlanes.containsKey(plan.getPlanId())){
					carreraPlan	= mapaPlanes.get(plan.getPlanId()).getCarreraId();
					nombrePlan 	= mapaPlanes.get(plan.getPlanId()).getNombrePlan();
					planOficial = mapaPlanes.get(plan.getPlanId()).getOficial();
					if (planOficial.equals("S")) planOficial = "Official";
					if (planOficial.equals("N")) planOficial = "Unofficial";
					if (planOficial.equals("I")) planOficial = "English";
					if (mapaCarreras.containsKey(carreraPlan)){
						nivelPlan = mapaCarreras.get(carreraPlan).getNivelId();
						if (mapaNiveles.containsKey(nivelPlan)){
							nivelNombre = mapaNiveles.get(nivelPlan).getNombreNivel();
						}
					}				
				} 			
				
				String numMat = "0";
				String colorMat	= "<span class='badge bg-warning rounded-pill'>"+numMat+"</span>";
				if (mapaMateriasAlumno.containsKey(plan.getPlanId())){
					numMat 		= mapaMateriasAlumno.get(plan.getPlanId());
					colorMat	= "<span class='badge bg-dark rounded-pill'>"+numMat+"</span>";
				}

				String mayor = "", colorMayor = "bg-info";
				String menor = "", colorMenor = "bg-info";
				if(mapMayores.containsKey(plan.getMayor())){
					mayor = mapMayores.get(plan.getMayor()).getNombre();
				}else{
					mayor = "N/A";
					colorMayor = "bg-warning";
				}
				if(mapMenores.containsKey(plan.getMenor())){
					menor = mapMenores.get(plan.getMenor()).getNombre();
				}else{
					menor = "N/A";
					colorMenor = "bg-warning";
				}
		%>
	<tr> 
    	<td>
        	<div>
                <a href="javascript:Activar('<%=plan.getPlanId()%>')"><i class="fas fa-check-circle"></i></a>
		        <%	if (numMat.equals("0")){%>     
		                <a href="javascript:Borrar('<%=plan.getPlanId()%>')"><i class="fas fa-trash-alt"></i></a>
				<% 	}%>		                	
            </div>
        </td>
        <td title="<%=nombrePlan%>"><%=plan.getPlanId()%></td>
        <td><%=nombrePlan%></td>
        <td style="text-align:center">
        	<a href="editarCiclo?PlanId=<%=plan.getPlanId() %>&Ciclo=<%=plan.getCiclo()%>&Grado=<%=plan.getGrado()%>">
        		<%=grado%>
        	</a>
        </td>
        <td style="text-align:center">
	        <a href="editarCiclo?PlanId=<%=plan.getPlanId() %>&Ciclo=<%=plan.getCiclo()%>&Grado=<%=plan.getGrado()%>">
	        	<%=ciclo%>
	        </a>
        </td>
        <td><%=planInscrito?"<span class='badge bg-info rounded-pill'>YES</span>":"<span class='badge bg-secondary rounded-pill'>NO</span>"%></td>
        <td class="text-center"><%=colorMat%></td>
        <td><%=nivelPlan%>-<%=nivelNombre%></td>
        <td style="text-align:center;"><%=plan.getPrimerMatricula()%>&nbsp;<a href="editarPrimerMatricula?PlanId=<%=plan.getPlanId()%>"><span class='badge bg-info rounded-pill'><i class="fas fa-edit"></i></span></a></td>
        <td style="text-align:center;"><% if (plan.getEstado().equals("1")) out.print("<span class='badge bg-info rounded-pill'>ACTIVE</span>"); else out.print("<span class='badge bg-secondary rounded-pill'>INACTIVE</span>");%></td>
        <td class="text-center" style="text-align:center;">
        	<a href="activarPrincipal?PlanId=<%=plan.getPlanId()%>">
            	<% if (plan.getPrincipal().equals("1")) out.print("<span class='badge bg-info rounded-pill'>YES</span>"); else out.print("<span class='badge bg-secondary rounded-pill'>NO</span>");%>
            </a>
        </td>
            <% String esc = plan.getEscala()==null?"":plan.getEscala();%>
        <td class="text-center">
        	<a href="grabarEscala?PlanId=<%=plan.getPlanId()%>">
            <%=plan.getEscala()%>
            </a>
		</td>
		<td><%=planOficial%></td>	
		<td class="text-center" title="MAJOR: <%=mayor%> / MINOR: <%=menor%>">
			<a href="editarMayorMenor?PlanId=<%=plan.getPlanId()%>&Mayor=<%=plan.getMayor()%>&Menor=<%=plan.getMenor()%>" title="MAJOR: <%=mayor%> / MINOR: <%=menor%>"><span class="badge rounded-pill <%=colorMayor%>"><i class="fas fa-edit"></i></span></a>
		</td>	
	</tr>
<%		}%>		 
	</table>
			
	</form>
<%	}else{
		String faltan = "";
		if (!datosPersonales) faltan += "personal ";
		if (!datosAcademicos) faltan += "academic ";
		if (!datosUbicacion) faltan += "location ";

		String[] arr = faltan.trim().split(" ");
		faltan = "";
		for (int i = 0; i < arr.length - 1; i++) {
			faltan += arr[i] + ", ";
		}
		faltan += arr[arr.length - 1];

		if (!datosUbicacion && faltan.contains(",")) {
			int lastCommaIndex = faltan.lastIndexOf(",");
			if (lastCommaIndex != -1) {
				faltan = faltan.substring(0, lastCommaIndex) + " " + faltan.substring(lastCommaIndex + 1);
			}
		}

		if (faltan.contains(",")) {
			int lastCommaIndex = faltan.lastIndexOf(",");
			if (lastCommaIndex != -1) {
				faltan = faltan.substring(0, lastCommaIndex) + " and " + faltan.substring(lastCommaIndex + 1);
			}
		}
%>
    <div>    	
    	<font color="#AE2113" size="3"><b>Missing <%=faltan%> data</b><br><br>
    		<a class="btn btn-primary" href="../datos/alumno"><spring:message code="aca.IrADatosDelAlumno"/></a>
   		</font>		
	</div>
<%	}%>	
</div>
 