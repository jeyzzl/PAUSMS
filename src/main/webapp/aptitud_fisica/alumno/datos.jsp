<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.catalogo.CatCarrera"%>
<%@ page import="java.text.*" %>
<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"></jsp:useBean>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="plan" scope="page" class="aca.alumno.AlumPlan"></jsp:useBean>
<jsp:useBean id="AlumPlanU" scope="page" class="aca.alumno.PlanUtil"></jsp:useBean>
<jsp:useBean id="planUtil" scope="page" class="aca.plan.PlanUtil"></jsp:useBean>
<jsp:useBean id="catAptitud" scope="page" class="aca.catalogo.CatAptitud"></jsp:useBean>
<jsp:useBean id="estadistica" scope="page" class="aca.vista.Estadistica"></jsp:useBean>
<jsp:useBean id="alumAptitud" scope="page" class="aca.alumno.AlumAptitud"></jsp:useBean>
<jsp:useBean id="AlumAptitudU" scope="page" class="aca.alumno.AlumAptitudUtil"></jsp:useBean>
<%
	String matricula 	= (String) session.getAttribute("codigoAlumno");
	String cargaId	 	= request.getParameter("carga")==null?aca.carga.CargaUtil.getMejorCarga(conEnoc,matricula):request.getParameter("carga");
	String accion		= request.getParameter("Accion")==null?"0":request.getParameter("Accion");
	String residencia 	= aca.alumno.AcademicoUtil.getResidencia(conEnoc, matricula);
	String mensaje		= "";
	DecimalFormat getformato= new DecimalFormat("##0.0;(##0.0)");
	
	ArrayList<String> planes				= AlumPlanU.getPlanesAlumno(conEnoc, matricula);
	
	estadistica.mapeaRegId(conEnoc, matricula, cargaId);
	alumno = AlumUtil.mapeaRegId(conEnoc, matricula);
	catAptitud.mapeaRegId(conEnoc);
	if(AlumAptitudU.existeReg(conEnoc, matricula, cargaId))
		alumAptitud = AlumAptitudU.mapeaRegId(conEnoc, matricula, cargaId);
	
	if(accion.equals("1")){	//Graba
		alumAptitud.setCodigoPersonal(matricula);
		alumAptitud.setCargaId(cargaId);
		alumAptitud.setFuerza(request.getParameter("fuerza"));
		alumAptitud.setFlexibilidad(request.getParameter("flexibilidad"));
		alumAptitud.setResistencia(request.getParameter("resistencia"));
		alumAptitud.setCardio(request.getParameter("cardio"));
		alumAptitud.setPeso(request.getParameter("peso"));
		alumAptitud.setTalla(request.getParameter("talla"));
		alumAptitud.setGrasa(request.getParameter("grasa"));
		alumAptitud.setAbdomen(request.getParameter("abdomen"));
		alumAptitud.setDieta(request.getParameter("dieta"));
		
		String imc = getformato.format(Float.parseFloat(request.getParameter("imc"))).replaceAll(",",".");
		alumAptitud.setImc(imc);
		
		if(AlumAptitudU.existeReg(conEnoc, matricula, cargaId)){
			if(AlumAptitudU.updateReg(conEnoc, alumAptitud)){
				mensaje = "Se guard&oacute; correctamente";
			}else{
				mensaje = "Ocurri&oacute; un error al guardar. Int&eacute;ntelo de nuevo";
			}
		}else{
			if(AlumAptitudU.insertReg(conEnoc, alumAptitud)){
				mensaje = "Se guard&oacute; correctamente";
			}else{
				mensaje = "Ocurri&oacute; un error al guardar. Int&eacute;ntelo de nuevo";
			}
		}
	}
%>
<head>
	<script type="text/javascript">
		function verifica(){
			var x
			var inputs = document.getElementsByTagName("input")
			for(x in inputs){
				if(inputs[x].value == "" && inputs[x].name != "Accion"){
					alert("Necesita llenar todos los campos para poder guardar")
					return false;
				}else{
					if(document.forma.imc.value=="Error"){
						alert("El imc debe ser válido")
						return false;
					}
				}
			}
			document.forma.Accion.value="1";
			return true;
		}
		
		function CalcularImc(){
				var peso = document.forma.peso.value;
				var talla = document.forma.talla.value/100;
				
				var res = peso/((talla*talla));
				
				document.forma.imc.value=res;
				
				if(document.forma.imc.value=='NaN' || document.forma.imc.value=='Infinity'){
					document.forma.imc.value="Error";
					document.getElementById('txt').style.visibility = "visible";
					document.forma.diag.value="-";
				}else{
					document.getElementById('txt').style.visibility = "hidden";
					var diagnostico;
					var imc = document.forma.imc.value;
					if(imc==0){
						diagnostico="-";
					}else if(imc><%=Float.parseFloat(catAptitud.getImcMinBajom())%> && imc<=<%=Float.parseFloat(catAptitud.getImcMaxBajom())%>){
						diagnostico="Bajo Peso";
					}else if(imc>=<%=Float.parseFloat(catAptitud.getImcMinNormalm())%> && imc<=<%=Float.parseFloat(catAptitud.getImcMaxNormalm())%>){
						diagnostico="Normal";
					}else if(imc>=<%=Float.parseFloat(catAptitud.getImcMinSobrem())%> && imc<=<%=Float.parseFloat(catAptitud.getImcMaxSobrem())%>){
						diagnostico="Sobrepeso";
					}else if(imc>=<%=Float.parseFloat(catAptitud.getImcMinObeso1m())%> && imc<=<%=Float.parseFloat(catAptitud.getImcMaxObeso1m())%>){
						diagnostico="Obesidad";
					}else if(imc>=<%=Float.parseFloat(catAptitud.getImcMinObeso2m())%> && imc<=<%=Float.parseFloat(catAptitud.getImcMaxObeso2m())%>){
						diagnostico="Obesidad Mórbida";
					}else if(imc>=<%=Float.parseFloat(catAptitud.getImcMinObeso3m())%> && imc<=<%=Float.parseFloat(catAptitud.getImcMaxObeso3m())%>){
						diagnostico="Deshabilitado";
					}				
					document.forma.diag.value=diagnostico;
				}
		}
		
		function checkPoint(obj, event){
			if(event.keyCode == 46){
				alert('La talla debe estar en centimetros');
				return false;
			}
			return true;
		}
	</script>
</head>
<div class="container-fluid">
<h2>Datos Aptitud F&iacute;sica</h2>
<form id="forma" name="forma" action="datos" methdo="post">
<div class="alert alert-info">
	Carga:
				<select id="carga" name="carga" style="width:300px;" onChange="document.forma.submit();">
<%
	aca.carga.CargaUtil cargaU = new aca.carga.CargaUtil();
	ArrayList<aca.carga.Carga> lisCarga = cargaU.getListAlumno(conEnoc, matricula);
	int i=0;
	for( i=0;i<lisCarga.size();i++){
		aca.carga.Carga carga = (aca.carga.Carga) lisCarga.get(i);
		if (carga.getCargaId().equals(cargaId)){
			out.print("<option value=\""+carga.getCargaId()+"\" Selected>"+ carga.getNombreCarga()+"</option>");
		}else{
			out.print("<option value=\""+carga.getCargaId()+"\">"+ carga.getNombreCarga()+"</option>");
		}				
	}
	lisCarga 	= null;
	cargaU		= null;
 %>
				</select>
</div>
<input type="hidden" id="Accion" name="Accion"/>
	<table style="width:80%" style="background:white;">
<%
	if(!mensaje.equals("")){
%>
	  <tr>
		<td><font color="blue"><b><%=mensaje %></b></font></td>
	  </tr>
<%
	}
%>
	  <tr>
		<td>
		  <table>
			   <tr>
					<td valign="top" width="75%">
						<table>
							<tr>
								<td><font size="3"><b><spring:message code="aca.Nombre"/>:</b></font></td>
								<td><%=alumno.getNombre() %> <%=alumno.getApellidoPaterno() %> <%=alumno.getApellidoMaterno() %>&nbsp;&nbsp;&nbsp;</td>
								<td><font size="3"><b><spring:message code="aca.Matricula"/>:</b></font></td>
								<td><%=matricula %></td>
							</tr>
							<tr>
								<td><font size="3"><b><spring:message code='aca.Residencia'/>:</b></font></td>
								<td><%=residencia.equals("E")?"Externo":"Interno" %></td>
								<td><font size="3"><b><spring:message code='aca.Carrera'/>:</b></font></td>
								<td><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, estadistica.getCarreraId()) %></td>
							</tr>
							<tr>
								<td><font size="3"><b><spring:message code="aca.Edad"/>:</b></font></td>
								<td><%=aca.alumno.AlumUtil.getEdad(conEnoc, matricula) %></td>
								<td><font size="3"><b><spring:message code='aca.Genero'/>:</b></font></td>
								<td><%=alumno.getSexo().equals("M")?"Masculino":"Femenino" %></td>
							</tr>
						</table>
					</td>
					<td>
						<table>
							<tr><td colspan="10"><img src="../../imagenes/LogoAF.png" /></td></tr>
						</table>
					</td>
				</tr>
			<tr>
			  <td colspan="12">
			  </td>
			</tr>
		  </table>
		</td>
	  </tr>
	  <tr>
		<td>
		  <table style="width:80%" >
			<tr>
			  <td colspan="3">
				<table style="width:100%" class="table table-sm table-nohover">
				  <tr>
					<th colspan="6"><b>Composici&oacute;n Corporal</b></th>
				  </tr>
				  <tr>
					<td width="50%">Peso (Kg):</td>
					<td><input onchange="CalcularImc();" type="text" class="text" id="peso" name="peso" size="4" value="<%=alumAptitud.getPeso() %>" />[999.99]</td>
				  </tr>
				  <tr>
					<td>Talla (cm):</td>
					<td><input onchange="CalcularImc();" onkeypress="return checkPoint(this, event);" type="text" class="text" id="talla" name="talla" size="4" value="<%=alumAptitud.getTalla() %>" />[999]</td>
				  </tr>
				  <tr>
					<td>IMC:</td>
					<td><input type="text" class="text" id="imc" name="imc" size="4" value="<%=alumAptitud.getImc() %>" readonly />[99.9]
					  <input type="text" id="txt" name="txt" value="*Peso o Talla no validos" size=35 style="visibility:hidden;text-align:left;background-color:transparent; border-width:0; font-size:10px; font-family:Verdana; font-weight: bold;color:red;" />
					</td>
				  </tr>
				  <tr>
<%				
				String strImc = alumAptitud.getImc()==null?"0":alumAptitud.getImc();
				if(strImc.equals("") || strImc.equals("null"))strImc="0";				

				float imc = Float.parseFloat(strImc);
				String diagnostico="";
				
				if(imc==0){
					diagnostico="-";
				}else if(imc<18.5){
					diagnostico="Bajo Peso";
				}else if(imc>=18.5 && imc<25){
					diagnostico="Normal";
				}else if(imc>=25 && imc<30){
					diagnostico="Sobrepeso";
				}else if(imc>=30 && imc<40){
					diagnostico="Obesidad";
				}else if(imc>=40){
					diagnostico="Obesidad Mórbida";
				}
%>
					<td>Diagnostico:</td>
					<td><input type="text" class="text" size="20" id="diag" name="diag" value="<%=diagnostico%>" size="4" disabled />
					</td>
				  </tr>
				  <tr>
					<td>% Grasa Corporal:</td>
					<td><input type="text" class="text" id="grasa" name="grasa" size="4" value="<%=alumAptitud.getGrasa() %>" />[99.9]</td>
				  </tr>
				  <tr>
					<td>Abdomen:</td>
					<td><input type="text" class="text" id="abdomen" name="abdomen" size="4" value="<%=alumAptitud.getAbdomen() %>" />[999.99]</td>
				  </tr>
				  <tr>
					<td>Dieta:</td>
<%
					if(alumAptitud.getDieta()==null || alumAptitud.getDieta().equals("null")){
						alumAptitud.setDieta("1");
					}
%>
					<td>
						<select name="dieta" id="dieta">
						    <option value="1" <%if (alumAptitud.getDieta().equals("1")) out.println("selected");%>>No Vegetariano</option>
						    <option value="2" <%if (alumAptitud.getDieta().equals("2")) out.println("selected");%>>Vegetariano</option>
		                </select>	
					</td>
				  </tr>
				</table>
			  </td>
			</tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td><table style="width:100%" class="table table-sm table-nohover">
			<tr>
			  <th colspan="3"><b>Valores de Referencia</b></th>
			</tr>
			<tr>
			  <td>
			    <table style="width:100%" class="tabla">
				<tr>
				  <th><b>IMC (&Iacute;ndice de Masa Corporal)</b></th>
				</tr>
				<tr>
				  <td>
					<table style="width:100%">
						<tr>
							<td>&nbsp;</td>
							<th>Hombre</th>
							<th>Mujer</th>
						</tr>
						<tr>
							<td>Bajo Peso</td>
							<td><%=catAptitud.getImcMinBajom() %> < <%=catAptitud.getImcMaxBajom() %></td>
							<td><%=catAptitud.getImcMinBajof() %> < <%=catAptitud.getImcMaxBajof() %></td>
						</tr>
						<tr>
							<td>Normal</td>
							<td><%=catAptitud.getImcMinNormalm() %> - <%=catAptitud.getImcMaxNormalm() %></td>
							<td><%=catAptitud.getImcMinNormalf() %> - <%=catAptitud.getImcMaxNormalf() %></td>
						</tr>
						<tr>
							<td>Sobrepeso</td>
							<td><%=catAptitud.getImcMinSobrem() %> - <%=catAptitud.getImcMaxSobrem() %></td>
							<td><%=catAptitud.getImcMinSobref() %> - <%=catAptitud.getImcMaxSobref() %></td>
						</tr>
						<tr>
							<td>Obesidad Tipo I</td>
							<td><%=catAptitud.getImcMinObeso1m() %> - <%=catAptitud.getImcMaxObeso1m() %></td>
							<td><%=catAptitud.getImcMinObeso1f() %> - <%=catAptitud.getImcMaxObeso1f() %></td>
						</tr>
						<tr>
							<td>Obesidad Tipo II</td>
							<td><%=catAptitud.getImcMinObeso2m() %> - <%=catAptitud.getImcMaxObeso2m() %></td>
							<td><%=catAptitud.getImcMinObeso2f() %> - <%=catAptitud.getImcMaxObeso2f() %></td>
						</tr>
						<tr>
							<td>Obesidad Tipo III</td>
							<td><%=catAptitud.getImcMinObeso3m() %> - <%=catAptitud.getImcMaxObeso3m() %></td>
							<td><%=catAptitud.getImcMinObeso3f() %> - <%=catAptitud.getImcMaxObeso3f() %></td>
						</tr>
					</table>
				  </td>
				</tr>
				</table>
			  </td>
			  <td valign="top">
				<table style="width:100%" class="tabla">
				<tr>
				  <th><b>% Grasa Corporal</b></th>
				</tr>
				<tr>
				  <td>
					<table style="width:100%">
					  <tr>
						<td>&nbsp;</td>
						<th>Hombre</th>
						<th>Mujer</th>
					  </tr>
					  <tr>
						<td>Aceptable</td>
						<td><%=catAptitud.getGrasaMinAceptablem() %> - <%=catAptitud.getGrasaMaxAceptablem() %></td>
						<td><%=catAptitud.getGrasaMinAceptablef() %> - <%=catAptitud.getGrasaMaxAceptablef() %></td>
					  </tr>
					  <tr>
						<td>Sobrepeso</td>
						<td><%=catAptitud.getGrasaMinSobrem() %> - <%=catAptitud.getGrasaMaxSobrem() %></td>
						<td><%=catAptitud.getGrasaMinSobref() %> - <%=catAptitud.getGrasaMaxSobref() %></td>
					  </tr>
					  <tr>
						<td>Obesidad</td>
						<td><%=catAptitud.getGrasaMinObesom() %> - <%=catAptitud.getGrasaMaxObesom() %></td>
						<td><%=catAptitud.getGrasaMinObesof() %> - <%=catAptitud.getGrasaMaxObesof() %></td>
					  </tr>
				    </table>
				  </td>
			    </tr>
				</table>
			</td>
			<td valign="top">
			  <table style="width:100%" class="tabla">
				<tr>
					<th align="center"><b>Abdomen Riesgo</b></th>
				</tr>
				<tr>
					<td>
						<table style="width:100%">
							<tr>
								<td>Hombre</td>
								<td> >= <%=catAptitud.getAbdomenRiesgom() %></td>
							</tr>
							<tr>
								<td>Mujer</td>
								<td> >= <%=catAptitud.getAbdomenRiesgof() %></td>
							</tr>
						</table>
					</td>
				</tr>
			  </table>
			</td>
		  </tr>
		  </table>
			</td>
		</tr>
		</table></td></tr>
		<tr>
			<td>
				<table style="width:80%" class="table table-sm table-nohover">
					<tr>
						<td align="center">
							<table style="width:100%">
								<tr>
									<th colspan="2"><b>Resultados de la Prueba</b></th>
									<th colspan="2"><b>Valores de Referencia</b></th>
								</tr>
								<tr>
									<td colspan="2">&nbsp;</td>
									<th><spring:message code="aca.Hombres"/></th>
									<th><spring:message code="aca.Mujeres"/></th>
								</tr>
								<tr>
									<td>Fuerza (Lagartijas):</td>
									<td><input type="text" class="text" id="fuerza" name="fuerza" size="3" value="<%=alumAptitud.getFuerza() %>" />[99]</td>
									<td><%=catAptitud.getFuerzam() %></td>
									<td><%=catAptitud.getFuerzaf() %></td>
								</tr>
								<tr>
									<td>Flexibilidad:</td>
									<td><input type="text" class="text" id="flexibilidad" name="flexibilidad" size="3" value="<%=alumAptitud.getFlexibilidad() %>" />[99]</td>
									<td><%=catAptitud.getFlexibilidadm() %></td>
									<td><%=catAptitud.getFlexibilidadf() %></td>
								</tr>
								<tr>
									<td>Resistencia (Abdominales):</td>
									<td><input type="text" class="text" id="resistencia" name="resistencia" size="3" value="<%=alumAptitud.getResistencia() %>" />[99]</td>
									<td><%=catAptitud.getResistenciam() %></td>
									<td><%=catAptitud.getResistenciaf() %></td>
								</tr>
								<tr>
									<td>Cardiorespiratoria (Carrera):</td>
									<td><input type="text" class="text" id="cardio" name="cardio" size="3" value="<%=alumAptitud.getCardio() %>" />[99.99]</td>
									<td><%=catAptitud.getCardiom() %></td>
									<td><%=catAptitud.getCardiof() %></td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td colspan="10"><input type="submit" class="btn btn-primary" value="Guardar" onclick="return verifica();" /></td>
		</tr>
	</table>
</form>
</div>
<%@ include file= "../../cierra_enoc.jsp" %>