<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="aca.plan.spring.MapaPlan"%>
<%@ page import="aca.alumno.spring.AlumDocumento"%>
<%@ page import="aca.alumno.spring.AlumPersonal"%>
<%@ page import="aca.bec.spring.BecSolicitud"%>
<%@ page import="aca.bec.spring.BecSolPeriodo"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../idioma.jsp" %>
<%@ include file= "../alumno/menu.jsp"%>
<%
	String codigoPersonal 		= (String) session.getAttribute("codigoPersonal");
	String codigoEmpleado 		= (String) session.getAttribute("codigoEmpleado");

	String mensaje 				= (String) request.getAttribute("mensaje");
	String matricula	 		= (String) request.getAttribute("matricula");
	boolean acepto 				= (boolean) request.getAttribute("acepto");
	AlumPersonal alumPersonal 	= (AlumPersonal) request.getAttribute("alumPersonal");

	List<String> lisPlanes 							= (List<String>) request.getAttribute("lisPlanes");
	List<AlumDocumento> lisAlumDocumento 			= (List<AlumDocumento>) request.getAttribute("lisAlumDocumento");
	List<BecSolPeriodo> lisPeriodos 				= (List<BecSolPeriodo>) request.getAttribute("lisPeriodos");

	HashMap<String, MapaPlan> mapaPlanes 			= (HashMap<String, MapaPlan>) request.getAttribute("mapaPlanes");
	HashMap<String, BecSolicitud> mapaSolicitudes	= (HashMap<String, BecSolicitud>) request.getAttribute("mapaSolicitudes");
	
	String nombrePlan = "";
%>
<head>
	<script type="text/javascript">
		function borrar(matricula,folio){
			if(confirm("¿Está seguro que desea borrar éste documento?")){
				location.href = "borrarDocumento?Matricula="+matricula+"&Folio="+folio;
			}
		}
	</script>
</head>
<body>
<div class="container-fluid mt-1">	
	<div class="alert alert-success">
		<a href="previos"><i class="far fa-arrow-alt-circle-left fa-lg"></i></a>&nbsp;
		<span class="badge rounded-pill bg-dark">1E</span>
		<spring:message code="portal.alumno.solicitudBeca.AsuntosPreviosBecas"/><small class="text-muted"> ( <%=matricula%> - <%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%>)</small>
	</div>	
</div>
<div class="container-fluid">	
<% if(mensaje.equals("1")){%>
	<div class="aler alert-success">
		Archivo guardado
	</div>
<% }else if(mensaje.equals("2")){%>
	<div class="aler alert-success">
		Archivo borrado
	</div>
<% }%>
<% if(acepto){%>
 	<a class="btn btn-success" data-bs-toggle="collapse" href="#collapseRequisitos" role="button" aria-expanded="false" aria-controls="collapseRequisitos">
   		Ver requisitos
  	</a>&nbsp;
<% }%>
<% if(acepto){%>
  	<div class="collapse " id="collapseRequisitos">
<% }%>
	 	<table>
 		<tr>
 			<th><h3>I. REQUISITOS</h3></th>
 		</tr>
 		<tr>
 			<td>
				- Llenar y enviar la solicitud de beca.
 			</td>
 		</tr>
 		<tr>
 			<td>
				- Los estudiantes de primer ingreso recibirán una llamada de su coordinador para una entrevista. 
 			</td>
 		</tr>
 		<tr>
 			<td>
				- Estar inscrito en EMPRENDUM (Colportaje Estudiantil) antes del 8 de agosto 2022. Ver CONDICIONES PARA MANTENER LA BECA. 1 Septiembre - 30 Noviembre
 			</td>
 		</tr>
	 	</table><br>
	 	<table>
 		<tr>
 			<th><h3>II. INFORMACIÓN GENERAL</h3></th>
		</tr>
 		<tr>
 			<td>
				- Para ser tomada en cuenta la solicitud debe venir con la información completa y ser enviada antes del 8 de agosto de 2022 y no tener deuda.
 			</td>
 		</tr>
 		<tr>
 			<td>
				- La beca otorgada no se puede transferir entre personas. No es acumulable con otro tipo de beca.
 			</td>
 		</tr>
 		<tr>
 			<td>
				- La resolución será notificada a través del mismo sistema académico (portal del estudiante) o por su coordinador. El porcentaje autorizado en su caso se reflejará al momento de inscribirse, en su carga académica con los cálculos financieros allí señalados.
 			</td>
 		</tr>
 		<tr>
 			<td>
				- La beca es solo para cubrir parte de la enseñanza. No se otorgan becas para matrícula, internado, ni deuda anterior.
 			</td>
 		</tr>
 		<tr>
 			<td>
				- No tener asignaturas reprobadas en el período escolar anterior.
 			</td>
 		</tr>
	 	</table><br>
	 	<table>
 		<tr>
 			<th>
				<h3>III.  CONDICIONES PARA ACREDITAR LA BECA</h3>
 			</th>
 		</tr>
 		<tr>
 			<td>
				Para acreditar la beca en el período intersemestral, el alumno deberá:
 			</td>
 		</tr>		
 		<tr>
 			<td>
				- Haber participado satisfactoriamente en el programa EMPRENDUM (colportaje estudiantil) intersemestral depositando la cantidad de $8,000.00 para los estudiantes en programas universitarios y $4,000.00 en caso de programas de preparatoria cumpliendo todos los &nbsp;&nbsp;requisitos señalados por programa EMPRENDUM dentro del periodo intersemestral del 1
				  de septiembre al 30 de noviembre. Deberán inscribirse en línea en la oficina de Emprendum por correo electrónico a emprendum@um.edu.mx o por WhatsApp al celular &nbsp;&nbsp;8261275453. Para continuar con el proceso de atender la solicitud se verificará con la oficina de EMPRENDUM si ya está inscrito. Si no está inscrito se detendrá el proceso de estudio de la solicitud. 
 			</td>
 		</tr>
 			<tr>
 			<td>
				-No tener saldos pendientes al momento de aplicar la beca al
				 final del semestre, en caso de tener bonificaciones pendientes
				 deberá de enviar su hoja de bonificación al correo de
				 becas@um.edu.mx 		 			
				</td>
 		</tr>
	 	</table><br>
	 	<table>
 		<tr>
 			<th>
				<h3>IV. NOTIFICACIÓN( OPCIONAL )</h3>
 			</th>
 		</tr>
 		<tr>
 			<td>
				Si ya tienes una beca autorizada y vas a cumplir con tu beca Diamante por medio de EmprendUM, mándanos un WhatsApp al +528261960340 con tu nombre y número de matrícula mencionandonos que estarás con EmprendUM para realizar tu convenio.
 			</td>
 		</tr>
	 	</table><br>
 <% if(acepto){%>
	 </div>
<% }%>
<% 	if(!acepto){%>
		<a class="btn btn-primary" href="aceptoRequisitos">Aceptar</a>&nbsp;
<% 	}
	if (acepto && codigoPersonal.equals(matricula)){%>
		<a class="btn btn-primary" href="http://becas.um.edu.mx" target="_blank">Llenar formulario Diamante</a>&nbsp;
		<form name="frmDocumento" id="frmDocumento" enctype="multipart/form-data" action="subirArchivoBeca" method="post">
			<input type="hidden" name="Matricula" value="<%=matricula%>">
			<br>
			<label>Elige el plan de estudios donde solicitarás la beca y el archivo de la solicitud que llenaste</label>			
			<select class="form-select" name="PlanId" style="width:700px">
<% 				for(String plan : lisPlanes){
					if(mapaPlanes.containsKey(plan)){
						nombrePlan = mapaPlanes.get(plan).getNombrePlan();
					}
%>
					<option value="<%=plan%>"><%=plan%> - <%=nombrePlan%></option>
<% 					nombrePlan = "";
				}%>
			</select>					
			<label class="col-sm-4 col-form-label">Período beca</label>
	    	<div class="col-sm-8">
				<select class="form-select" name="PeriodoId" style="width:250px">
<% 				for(BecSolPeriodo periodo : lisPeriodos){%>
					<option value="<%=periodo.getPeriodoId()%>"><%=periodo.getPeriodoNombre()%></option>
<% 				}%>
				</select>    			   				
			</div>
			<br>
		  	<span class="btn btn-file">
			  	<input type="file" id="archivo" name="archivo" required="required"/>
		  	</span>		  	
			<button type="submit" id="btnGuardar" class="btn btn-warning"><i class="fas fa-save"></i> Grabar</button>
		</form>
<% 	} //Si acepto y es el alumno el que esta en el portal%>		
	<table class="table">
		<tr>
			<th>Folio</th>
			<th>Plan</th>
			<th>Carrera</th>
			<th>Fecha</th>
			<th>Documento</th>
			<th>Estado</th>
			<th>% Com</th>
		</tr>
<% 		
	for(AlumDocumento documento : lisAlumDocumento){
		
		String planNombre = "-";
		if (mapaPlanes.containsKey(documento.getPlanId())){
			planNombre = mapaPlanes.get(documento.getPlanId()).getCarreraSe();
		}
		
		String porcentajeCom = "-";
		if(mapaSolicitudes.containsKey(documento.getCodigoPersonal()+documento.getFolio())){
			porcentajeCom = mapaSolicitudes.get(documento.getCodigoPersonal()+documento.getFolio()).getPorComision();
		}
		
		String estado = "Solicitado";
		if(documento.getEstado().equals("A")){
			estado = "Autorizado";
		}else if(documento.getEstado().equals("I")){
			estado = "Inactivo";
		}
%>
		<tr>
			<td>
				<%=documento.getFolio()%>&nbsp;&nbsp;&nbsp;
<%		if (codigoPersonal.equals("9800308") || (codigoPersonal.equals(matricula) && (documento.getEstado().equals("S") || documento.getEstado().equals("I")))){%>			
				<a class="btn btn-danger btn-sm" href="javascript:borrar('<%=documento.getCodigoPersonal()%>','<%=documento.getFolio()%>');"><i class="fas fa-times"></i></a>
<% 		}%>				
			</td>
			<td><%=documento.getPlanId()%></td>
			<td><%=planNombre%></td>
			<td><%=documento.getFecha()%></td>
			<td><%=documento.getNombre()%></td>
			<td><%=estado%></td>
			<td><%=porcentajeCom%></td>
		</tr>
<% 	}%>
	</table>	
</div>
</body>