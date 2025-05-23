<%@ page import= "aca.alumno.spring.AlumPersonal"%>
<%@ page import= "aca.alumno.spring.AlumAcademico"%>
<%@ page import= "aca.internado.spring.IntDatosAlumno"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<% idJsp="070_p";%>
<%@ include file= "../../idioma.jsp" %>

<script type="text/javascript">

	function Modificar(){
		document.frmpersonal.Accion.value="3";
		document.frmpersonal.submit();
	}	

	function Consultar(){
		document.frmpersonal.Accion.value="5";
		document.frmpersonal.submit();		
	}	
</script>
<% 	 
	// Declaracion de variables	
	String codigoAlumno = (String) session.getAttribute("codigoAlumno");
	String planId 		= (String) request.getAttribute("planId");	
	String accion 		= request.getParameter("Accion")==null?"5":request.getParameter("Accion");	
	int numAccion		= Integer.parseInt(accion);
	int i 				= 0;
	String sResultado	= "";
	
	AlumPersonal alumPersonal 		= (AlumPersonal)request.getAttribute("alumPersonal");
	AlumAcademico alumAcademico 	= (AlumAcademico)request.getAttribute("alumAcademico");
	IntDatosAlumno datos		 	= (IntDatosAlumno)request.getAttribute("datos");
	String paisNombre				= (String)request.getAttribute("paisNombre");
	String estadoNombre				= (String)request.getAttribute("estadoNombre");
	String nacionalidad				= (String)request.getAttribute("nacionalidad");
	String institucionNombre		= (String)request.getAttribute("institucionNombre");
	int edad						= (int)request.getAttribute("edad");
	boolean esInscrito				= (boolean)request.getAttribute("esInscrito");
	String carreraNombre			= (String)request.getAttribute("carreraNombre");	
	String grado					= (String)request.getAttribute("grado");
	String ciclo					= (String)request.getAttribute("ciclo");
	String tipoAlumno				= (String)request.getAttribute("tipoAlumno");
	String religionNombre			= (String)request.getAttribute("religionNombre");	
%>
<div class="container-fluid">
<form action="datosPersonales" method="post" name="frmpersonal" target="_self">
<input type="hidden" name="Accion">
<input type="hidden" name="CodigoAlumno" value="<%=codigoAlumno%>">
<table class="tabbox" style="width:100%; height:99%; margin: 0 auto;">
	<tr>
		<td width='100%' align = 'left'>
				<legend>Personal Data:</legend> 
				<table class="fieldbox" style="width:100%" >
					<tr valign='top'>
						<td width='10%' align='center'>
							<img src="../../foto?Codigo=<%=codigoAlumno%>&Tipo=O" width="150"></td>
						</td>
						<td width='40%'>
							<table class="table table-condensed table-bordered" style="width:100%" cellspacing='4'>
								<tr>
									<td><spring:message code="aca.Matricula"/>:</td>
									<td><b><%=alumPersonal.getCodigoPersonal()%></b></td>
								</tr>
								<tr>
									<td><spring:message code="aca.Nombre"/>:</td>
									<td><b><%=alumPersonal.getNombre()%> <%=alumPersonal.getApellidoPaterno()%> <%=alumPersonal.getApellidoMaterno()%></b></td>
								</tr>
								<tr>
							      	<td><spring:message code='aca.Genero'/>:</td>
							       	<td><b><% if(alumPersonal.getSexo().equals("M"))out.print("Male"); else out.print("Female");%></b></td>
								</tr>
								<tr>
							    	<td>Marital Status:</td>
							    	<td><b>
<%										if(alumPersonal.getEstadoCivil().equals("S")) out.print("Single");
							    		else if(alumPersonal.getEstadoCivil().equals("C")) out.print("Married");
										else if(alumPersonal.getEstadoCivil().equals("D")) out.print("Divorced");
										else out.print("Widowed");
%>									
										</b>
									</td>
								</tr>
								<tr>
							    	<td>Birth date:</td>
							        <td><b><%=alumPersonal.getFNacimiento()%> - <%=edad%> years</b></td>
								</tr>
							  	<tr>
							    	<td>Country:</td>
							    	<td><b><%=paisNombre%></b></td>
							  	</tr>
							  	<tr>
							    	<td>Province:</td>
							    	<td><b><%=estadoNombre%></b></td>
							  	</tr>
							  	<tr>
						            <td><spring:message code="aca.Nacionalidad"/>:</td>
						            <td><b><%=nacionalidad%></b></td>
							  	</tr>
							  	<tr>
							    	<td>Enrolled:</td>
							    	<td><b><%=esInscrito?"ENROLLED":"NOT ENROLLED"%></b></td>
							  	</tr>
							  	<tr>
							    	<td><spring:message code='aca.Residencia'/>:</td>
							    	<td><b><% if(alumAcademico.getResidenciaId().equals("E"))out.print("Day Student");else out.print("Dormitory");%></b></td>
							  	</tr>							  	
							</table>
						</td>
						<td width='40%'>
							<table class="table table-condensed table-bordered" style="width:100%"  cellspacing='6'>
							  	<tr>
							    	<td><spring:message code='aca.Carrera'/>:</td>
							    	<td><b><%=carreraNombre%></b></td>
							  	</tr>
							  	<tr>
							    	<td>Level:</td>
							    	<td><b>
							    	  Year <%=grado%>, 
							    	  Semester <%=ciclo%></b>
							    	</td>
							  	</tr>
							  	<tr>
							    	<td>Modality:</td>
							    	<td><b><%=alumAcademico.getModalidadId().equals("1")?"Face-to-Face":"Online"%></b></td>
							  	</tr>
							  	<tr>
							    	<td><spring:message code="aca.Tipo"/>:</td>
							    	<td><b><%=tipoAlumno%></b></td>
							  	</tr>
							  	<%-- <tr>
							    	<td><spring:message code="aca.Institucion"/>:</td>
							    	<td><b><%=institucionNombre%></b></td>
							  	</tr>							  	 --%>
							  	<%-- <tr>
							    	<td>Financial Type:</td>
							    	<td><b><% if(alumAcademico.getClasFin().equals("1"))out.print("ACFE");else out.print("NO ACFE");%></b></td>
							  	</tr> --%>
								<tr>
									<td><spring:message code="aca.Religion"/>:</td>
									<td><b><%=religionNombre%></b></td>
							  	</tr>
							  	<tr>
							    	<td><spring:message code="aca.Bautizado"/>:</td>
							    	<td><b><% if(alumPersonal.getBautizado().equals("S"))out.print("YES");else out.print("NO");%></b></td>
							  	</tr>
							  	<tr>
							    	<td><spring:message code="aca.Telefono"/>:</td>
							    	<td><b><%=alumPersonal.getTelefono().equals("-")?"N/A":alumPersonal.getTelefono()%></b></td>
							  	</tr>
							  	<tr>
							    	<td><spring:message code="aca.Email"/>:</td>
							    	<td><b><%=alumPersonal.getTelefono().equals("-")?"N/A":alumPersonal.getEmail()%></b></td>
							  	</tr>
							</table>
						</td>
					</tr>
				</table>
		</td>
	</tr>
				<%-- <legend>Personal Data:</legend>  --%>
				<%-- <table class="fieldbox" >
					<tr valign='top'>
						<td width='100%'>
							<table width='100%'  >
								<tr>
									<td><b>Device:</b></td>
									<td>
										<select  class="form-select" style="width:300px" name = "Computadora">											
											<option value='N' <% if(datos.getComputadora().equals("N")) out.print("selected");%>>None</option>
											<option value='P' <% if(datos.getComputadora().equals("P")) out.print("selected");%>>Laptop</option>
											<option value='E' <% if(datos.getComputadora().equals("E")) out.print("selected");%>>Desktop</option>
										</select>
									</td>
								</tr>
								<tr>
									<td><b>Medical Treatment:</b></td>
									<td>
										<select class="form-select" style="width:300px" name ="Tratamiento">
											<option value='N' <% if(datos.getTratamiento().equals("N")) out.print("selected");%>><spring:message code='aca.No'/></option>
											<option value='S' <% if(datos.getTratamiento().equals("S")) out.print("selected");%>><spring:message code='aca.Si'/></option>
										</select>
									</td>
								</tr>
								<tr>
									<td><b>Reason:</b></td>
									<td><input type='text'  class="form-control" style="width:300px"name = "Motivo" size='30' maxlength='100' value='<%=datos.getMotivo()%>'></td>
								</tr>
								<tr>
									<td><b>Blood type:</b></td>
									<td>
										<input type='text' name ="TipoSangre" class="form-control" style="width:300px" size='20' maxlength='20' value='<%=datos.getTipoSangre()%>'>
									</td>
								</tr>
								<tr>
									<td width='120'><b>Phone:</b></td>
									<td>
										<input type='text' name ="Celular" class="form-control" style="width:300px" size='20' maxlength='20' value='<%=datos.getCelular()%>'>							
									</td>
								</tr>
								<tr>
									<td width='120'><b>Email:</b></td>
									<td>
										<input type='text' name ="Correo" class="form-control" style="width:300px" size='20' maxlength='70' value='<%=datos.getCorreo()%>'>							
									</td>
								</tr>								
								<tr>
									<td width='120'><b>Parents Phone:</b></td>
									<td>
										<input type='text' name ="Telefono" class="form-control" style="width:300px" size='20' maxlength='70' value='<%=datos.getTelefono()%>'>							
									</td>
								</tr>
								<tr>
									<td><b>Instruments Played:</b></td>
									<td><input type='text' name ="Instrumentos" class="form-control" style="width:300px" size='47' maxlength='100'  value='<%=datos.getInstrumentos()%>'></td>
								</tr>
							</table>							
						</td>
					</tr>
				</table> --%>
			<%-- </fieldset>
		</td>
	</tr> --%>
	<%-- <tr>
		<td>
			<input type='button' onclick='Modificar()' class="btn btn-primary" value = 'Save'>
		</td>
	</tr> --%>
	<%-- </table> --%>
	</table>
</form>
</div>