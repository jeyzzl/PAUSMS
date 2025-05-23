<%@ page import="java.util.List"%>
<%@ page import="aca.emp.spring.Empleado"%>
<%@ page import="aca.emp.spring.EmpDatos"%>
<%@ page import="aca.emp.spring.EmpleadoDependientes"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<script type="text/javascript" src="../../js/qrTag.js"></script>
<script type="text/javascript">

	function Valida_graba(){
		if((form1.f_empleado.value!="") && (form1.f_nombre.value!="") && (form1.nombreCredencial.value!="") && (form1.f_apellidoP.value!="") && (form1.f_apellidoM.value!="") && (form1.f_activo.value != "") && (form1.f_cotejado.value!="" )){			
			form1.Accion.value = 1;
			document.form1.submit();
		}else{
			alert("Complete el formulario Correctamente ..! ");
		}
	}
	
	function Valida_modifica(){
		if((form1.f_empleado.value!="") && (form1.f_nombre.value!="") && (form1.f_apellido.value!="") && (form1.f_activo.value != "") && (form1.f_cotejado.value!="" )){			
			form1.Accion.value = 2;
			document.form1.submit();
		}else{
			alert("Complete el formulario Correctamente ..! ");
		}
	}
	
	function credencial(){
		abrirVentana("credencial",405,285,0,0,"no","yes","no","no","no","credencial.jsp");
	}
		
	function credencialDependiente(matricula,nombre,fnac){
		abrirVentana("credencial",405,285,0,0,"no","yes","no","no","no","credencialDependiente.jsp?matricula="+matricula+"&nombre="+nombre+"&fnac="+fnac);
		
	}
	
	function camara(){
		location.href="tomarfoto.jsp";
	}
	
	function borrarFoto( codigoEmp ){
		if (confirm("¿Estas seguro de borrar la fotografia del empleado?")){
			location.href="borrar?Codigo="+codigoEmp+"&Folio=0";
		}	
	}
	
	function abrirVentana(strName,iW,iH,TOP,LEFT,R,S,SC,T,TB,URL){
		var sF="";
		TOP = (screen.height - iH)/2-50
		LEFT = (screen.width - iW)/2
		sF+=iW?'width='+iW+',':'';
		sF+=iH?'height='+iH+',':'';
		sF+=R?'resizable='+R+',':'';
		sF+=S?'status='+S+',':'';
		sF+=SC?'scrollbars='+SC+',':'';
		sF+=T?'titlebar='+T+',':'';
		sF+=TB?'toolbar='+TB+',':'';
		sF+=TB?'menubar='+TB+',':'';
		sF+=TOP?'top='+TOP+',':'';
		sF+=LEFT?'left='+LEFT+',':'';
		newwindow = window.open(URL,strName?strName:'',sF)
		newwindow.focus();
	}	
</script>

<style type="text/css">
	#sombra {
		float:left;
		padding:0 5px 5px 0; /*Esta es la profundidad de nuestra sombra, sí haces más grandes estos valores, el efecto de sombra es mayor también */
		background: url(../../imagenes/sombra.gif) no-repeat bottom right; /*Aquí es donde ponemos la imagen como fondo colocando su ubicación*/
		
		position:relative;
		top:0px;
		left: 0px;
	} 
	
	#sombra img {
		display:block;
		position:relative;
		top: 0px; /* Desfasamos la imagen hacia arriba */
		left:-2px; /*Desfasamos la imagen hacia la izquierda */
		padding:5px;
		background:#FFFFFF; /*Definimos un color de fondo */
		border:1px solid;
		border-radius:.4em;
		border-color: #CCCCCC #666666 #666666 #CCCCCC Creamos un marco para acentuar el efecto */
	}
</style>
<!-- inicio de estructura -->
<%	
	String codigoEmpleado		= (String) session.getAttribute("codigoEmpleado");
	EmpDatos empDatos			= (EmpDatos) request.getAttribute("empDatos");
	Empleado empleado			= (Empleado) request.getAttribute("empleado");
	String departamento			= (String) request.getAttribute("departamento");
	String puesto				= (String) request.getAttribute("puesto");
	String mensaje				= (String) request.getAttribute("mensaje");
	boolean emp_academico 		= (boolean) request.getAttribute("existe");
	boolean tieneFoto 			= (boolean) request.getAttribute("tieneFoto");
	
	List<EmpleadoDependientes> lisDependientes 		= (List<EmpleadoDependientes>) request.getAttribute("lisDependientes");
	//System.out.println("Lista:"+lisDependientes.size()+":"+empDatos.getId());
	
	String s_empleado		= "";
	String s_folio			= "";
	String s_nombre 		= "x";
	String s_apellidoP 		= "x";
	String s_apellidoM		= "x";
	String s_puesto 		= "x";
	String s_depto 			= "x";
	String s_puestoRH		= "x";
	String s_deptoRH		= "x";
	String s_status			= "x";
	String s_coteja			= "N";
	String s_tipocred		= "x";
	String s_check1			= "";
	String s_check2			= "";
	String s_check3			= "";
	String nombreCredencial = "";

	String sE 				= "";
    int NumArch			    = 1;
	boolean Salir 			= false;	
	
	emp_academico 		= true;
	s_puesto 			= empDatos.getPuesto();
	s_depto				= empDatos.getDepartamento();
	s_status			= empDatos.getStatus();
	s_coteja			= empDatos.getCotejado();
	nombreCredencial	= empDatos.getNombre();
	s_tipocred			= empDatos.getTipocred();
		
	s_empleado			= empleado.getId();
	s_nombre 			= empleado.getNombre();
	s_apellidoP 		= empleado.getAppaterno();
	s_apellidoM			= empleado.getApmaterno();
	s_puestoRH			= puesto;
	s_deptoRH			= departamento;			
	if (emp_academico == false){
		s_puesto 			= s_puestoRH;
		s_depto				= s_deptoRH;
	}
	
	if (s_status.equals("A")){ 
		s_check1 = "checked"; s_check2 = ""; s_check3="";
	}else if (s_status.equals("J")||codigoEmpleado.substring(0,3).equals("988")){
		s_check1 = ""; s_check2 = ""; s_check3="checked";
	}else{ 
		s_check1 = ""; s_check2 = "checked"; s_check3="";
	}
	
	session.setAttribute("matricula",codigoEmpleado);
	session.setAttribute("nombre",s_nombre);
	session.setAttribute("apellidos",s_apellidoP+" "+s_apellidoM);
	session.setAttribute("puesto",s_puesto);
	session.setAttribute("depto",s_depto);		
	
	String textoQR = codigoEmpleado+", "+s_nombre+" "+s_apellidoP+" "+s_apellidoM+", "+s_depto+", "+s_puesto;
%>
<div class="container-fluid">
	<h2>Datos del Empleado</h2>
	<hr>
<% 	if(mensaje.equals("1")){%>
	<div class="alert alert-success">
		Los Datos Fueron Grabados
	</div>
<%  }else if(mensaje.equals("2")){%>
	<div class="alert alert-success">
		Los Datos Fueron Modificados
	</div>
<%  }%>
	<form name="form1" method="post" action="add">
	<input name="Accion" type="hidden">
	<table width="100%" class="tabla">
	<tr align="CENTER" valign="TOP">
		<th colspan="4" align="CENTER"></th>
	</tr>
	<tr align="CENTER" valign="TOP">
		<td width="92" height="260" align="LEFT">
			<%session.setAttribute("mat",codigoEmpleado);%>
			<div id="sombra">
				<img src="../../empFoto?Codigo=<%=codigoEmpleado%>&Folio=0" width="250" border="1">
			</div> 
			<a href="tomarfoto" title="Tomar la Foto"><img src='../../imagenes/camaraweb.png' width="20" ></a>&nbsp;
			<%if (!tieneFoto){%>
			<a href="javascript:camara()" title="Tomar la Foto">
				<img src='../../imagenes/camaraweb.png' width="20">
			</a>&nbsp; 
			<a href="subir" title="Subir Foto de un archivo">
				<img src='../../imagenes/upload.png' width="30">
			</a>&nbsp; 
			<%}%>
			<%if (tieneFoto){%>
			<a href="../../empFotoBajar?Codigo=<%=codigoEmpleado%>&Folio=0" title="Descargar la Foto">
				<img src='../../imagenes/descargar.png' width="30">
			</a>&nbsp; 
			<a href="javascript:borrarFoto('<%=codigoEmpleado%>')" title="Borrar la Foto">
				<img src='../../imagenes/borrar2.gif' width="25">
			</a> 
			<%}%>
		</td>
		<td align="LEFT" width="900"><br>
			<table align="LEFT" width="100%" class="table table-sm">
				<tr>
					<td width="142" align="LEFT"><b><font size="2" face="Arial, Helvetica, sans-serif">N° Empleado</font></b></td>
					<td width="298" align="LEFT">
						<input name="f_empleado" type="text" class="text" id="f_empleado" value="<%=codigoEmpleado%>" size="8" maxlength="7" readonly>
						<input name="f_id" type="hidden" value="<%=s_empleado%>">
					</td>
				</tr>
				<tr>
					<td height="27" align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b><spring:message code="aca.Nombre" />: </b></font></td>
					<td align="LEFT">
						<input name="f_nombre" type="text" class="text" id="f_nombre" value="<%=s_nombre%>" size="60" maxlength="35">
					</td>
				</tr>
				<tr>
					<td height="27" align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b><spring:message code="aca.Nombre" /> Credencial:</b></font></td>
					<td align="LEFT">
						<input name="nombreCredencial" type="text" class="text" id="nombreCredencial" value="<%=nombreCredencial%>" size="15" maxlength="13"> &nbsp; (Máximo 13 caracteres)
					</td>
				</tr>
				<tr>
					<td height="28" align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b>Ap. Paterno: </b></font></td>
					<td align="LEFT">
						<input name="f_apellidoP" type="text" class="text" id="f_apellidoP" value="<%=s_apellidoP%>" size="60" maxlength="35">
					</td>
				</tr>
				<tr>
					<td height="28" align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b>Ap. Materno: </b></font></td>
					<td align="LEFT">
						<input name="f_apellidoM" type="text" class="text" id="f_apellidoM" value="<%=s_apellidoM%>" size="60" maxlength="35">
					</td>
				</tr>
				<tr>
					<td align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b>Puesto:</b></font></td>
					<td align="LEFT">
						<input name="f_puesto" type="text" class="text" id="f_puesto" value="<%=s_puesto%>" size="30" maxlength="35"><font size="1">&nbsp;R.H.&nbsp; <%=s_puestoRH%></font>
					</td>
				</tr>
				<tr>
					<td align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b>Departamento:</b></font></td>
					<td align="LEFT"><input name="f_depto" type="text" class="text" id="f_depto" value="<%=s_depto%>" size="30" maxlength="60"><font size="1">&nbsp;R.H.&nbsp; <%=s_deptoRH%></font>
					</td>
				</tr>
				<tr>
					<td align="LEFT"><font size="2"
						face="Arial, Helvetica, sans-serif"><b>Status : </b></font></td>
					<td align="LEFT">Activo <input name="f_activo" type="radio" value="A" <%=s_check1%>> &nbsp;&nbsp;&nbsp;&nbsp; Jubilado <input name="f_activo" type="radio" value="J"
						<%=s_check3%>> &nbsp;&nbsp;&nbsp;&nbsp; Inactivo <input name="f_activo" type="radio" value="I" <%=s_check2%>>
					</td>
				</tr>
				<tr>
					<td align="LEFT"><strong>Cotejado:</strong></td>
					<td><select name="f_cotejado" class="input-small">
							<option value="N" <%=s_coteja.equals("N")?" selected":""%>>NO</option>
							<option value="S" <%=s_coteja.equals("S")?" selected":""%>>SI</option>
					</select></td>
				</tr>
				<tr>
					<td align="LEFT"><strong>Misionero:</strong></td>
					<td><select id="f_tipocred" name="f_tipocred"
						class="input input-xlarge">
							<option value="Licencia misionera"
								<%=s_tipocred.equals("Licencia misionera")?"selected":""%>>Licencia misionera</option>
							<option value="Licencia magisterial"
								<%=s_tipocred.equals("Licencia magisterial")?"selected":""%>>Licencia magisterial</option>
							<option value="Licencia ministerial"
								<%=s_tipocred.equals("Licencia ministerial")?"selected":""%>>Licencia ministerial</option>
							<option value="Credencial misionera"
								<%=s_tipocred.equals("Credencial misionera")?"selected":""%>>Credencial misionera</option>
							<option value="Credencial magisterial"
								<%=s_tipocred.equals("Credencial magisterial")?"selected":""%>>Credencial magisterial</option>
							<option value="Credencial ministerial"
								<%=s_tipocred.equals("Credencial ministerial")?"selected":""%>>Credencial ministerial</option>
							<option value="Credencial honoraria misionera"
								<%=s_tipocred.equals("Credencial honoraria misionera")?"selected":""%>>Credencial honoraria misionera</option>
							<option value="Credencial honoraria magisterial"
								<%=s_tipocred.equals("Credencial honoraria magisterial")?"selected":""%>>Credencial honoraria magisterial</option>
							<option value="Credencial honoraria ministerial"
								<%=s_tipocred.equals("Credencial honoraria ministerial")?"selected":""%>>Credencial honoraria ministerial</option>
					</select></td>
				</tr>
				<tr>
					<td align="CENTER" colspan="2">
						<input class="btn btn-primary" type="button" value="Grabar" onclick="Valida_graba();" />
					</td>
				</tr>
			</table>
		</td>
		<td>&nbsp;&nbsp;</td>
		<td align="LEFT" width="400">
			<table align="LEFT" width="70%" class="tabla">
			<tr>
				<td>
<!-- 				<div id="qrtag_div" style="width: 220px; height: 220px; background: white; text-align: center;"></div> -->
<!-- 				<script type="text/javascript"> -->
<!-- //     			var qrtag = new QRtag(); -->
<%-- 	    			qrtag.image("qrtag_div","<%=textoQR%>",200,20,"http://www.um.edu.mx"); --%>
<!-- 				</script> -->
					<img src="qr" style="width: 220px; height: 220px; background: white; text-align: center;"/>
				</td>
			</tr>
		</table>
	</td>
	</tr>
	</table>
	</form>
	<table style="width: 70%" class="table table-sm">
		<tr align="center">
			<td colspan="5"><h4>Dependientes del empleado</h4></td>
		</tr>
		<tr align="center">
			<th width="8%"><spring:message code="aca.Folio" /></th>
			<th width="30%"><spring:message code="aca.Nombre" /></th>
			<th width="13%">Relación</th>
			<th width="11%">F. Nac.</th>
		</tr>
		<%
	for (EmpleadoDependientes dependiente : lisDependientes){
		s_folio= dependiente.getId();
%>
		<tr align="LEFT">
			<td align="center"><%=s_folio%></td>
			<td><a href="dato_dep?f_empleado=<%=s_empleado%>&f_folio=<%=s_folio%>"><%=dependiente.getNombre()%></a></td>
			<td><%=dependiente.getRelacionId().equals("1") ? "Esposo(a)" : "Hijo(a)"%></td>
			<td><%= dependiente.getBday()%></td>
		</tr>
		<%	}%>
	</table>
	<!-- fin de estructura -->
</div>