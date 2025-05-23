<%@page import="aca.emp.spring.EmpDatos"%>
<%@page import="aca.emp.spring.Empleado"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

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
	String codigoEmpleado	= (String) session.getAttribute("codigoEmpleado");
	EmpDatos empDatos		= (EmpDatos) request.getAttribute("empDatos");
	Empleado empleado		= (Empleado) request.getAttribute("empleado");
	boolean existe			= (boolean)request.getAttribute("existe");
	boolean existeAcademico	= (boolean)request.getAttribute("existeAcademico");
	boolean existeFoto		= (boolean)request.getAttribute("existeFoto");
	String puesto			= (String)request.getAttribute("puesto");
	String depto			= (String)request.getAttribute("depto");
	
	String s_empleado		= "";
	String s_folio			= "";
	String s_nombre 		= "x";
	String s_apellidoP 		= "x";
	String s_apellidoM		= "x";
	String s_puesto 		= "x";
	String s_depto 			= "x";	
	String s_status			= "x";
	String s_coteja			= "x";
	String s_check1			= "";
	String s_check2			= "";
	String s_check3			= "";
	String nombreCredencial = "";

	String sE 				= "";
    int NumArch			    = 1;
	boolean Salir 			= false;
	boolean emp_academico 	= false;
	
	if (existe){	
		
		s_puesto 			= empDatos.getPuesto();
		s_depto				= empDatos.getDepartamento();
		s_status			= empDatos.getStatus();
		s_coteja			= empDatos.getCotejado();
		nombreCredencial	= empDatos.getNombre();
		s_empleado			= empleado.getId();
		s_nombre 			= empleado.getNombre();
		s_apellidoP 		= empleado.getAppaterno();
		s_apellidoM			= empleado.getApmaterno();	
					
		if (existeAcademico == false){
			s_puesto 			= puesto;
			s_depto				= depto;
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
			
			boolean tieneFoto 			= false;			
			// Verifica si existe la imagen	
			String dirFoto = application.getRealPath("/WEB-INF/fotos/"+codigoEmpleado+".jpg");
			java.io.File foto = new java.io.File(dirFoto);
			if (foto.exists()){
				tieneFoto = true;
			}			
			String textoQR = codigoEmpleado+", "+s_nombre+" "+s_apellidoP+" "+s_apellidoM+", "+s_depto+", "+s_puesto;
%>
<input name="Accion" type="hidden">
<div class="container-fluid">
<h1>Datos del Empleado</h1>
<table   width="90%" class="table table-condensed table-nohover">
  <tr align="CENTER" valign="TOP">		
    <th colspan="4" align="CENTER"> <b><font size="2" face="Arial, Helvetica, sans-serif"></font></b></th>
  </tr>			
  <tr align="CENTER" valign="TOP">		
    <td width="92" height="260" align="LEFT"> 
      <table align="LEFT" border ="0">
		<tr>
           <%session.setAttribute("mat",codigoEmpleado);%>
		  <td>
	  		<div id="sombra"><img src="../../foto?Codigo=<%=codigoEmpleado%>&Tipo=O" width="250" border="1"></div>
		  </td>
		</tr>
	  </table>
	</td>
    <td align="LEFT" width="900">
    <br>
      <table align="LEFT" width="100%" class="table table-condensed">
          <tr> 
            <td width="142" align="LEFT"><b><font size="2" face="Arial, Helvetica, sans-serif">N° Empleado</font></b></td>
            <td width="298" align="LEFT">
               <input name="f_empleado" type="text" class="text" id="f_empleado" value="<%=codigoEmpleado%>" size="8" maxlength="7" readonly>
               <input name="f_id" type="hidden" value="<%=s_empleado%>">
            </td>
          </tr>
          <tr> 
            <td height="27" align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b><spring:message code="aca.Nombre"/>: 
              </b></font></td>
            <td align="LEFT"> <input name="f_nombre" type="text" class="text" id="f_nombre" value="<%=s_nombre%>" size="60" maxlength="35" readonly></td>
          </tr>
          <tr> 
            <td height="27" align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b><spring:message code="aca.Nombre"/> Credencial:</b></font></td>
            <td align="LEFT"> <input name="nombreCredencial" type="text" class="text" id="nombreCredencial" readonly value="<%=nombreCredencial%>" size="15" maxlength="13"> &nbsp; (Máximo 13 caracteres)</td> 
          </tr>
          <tr> 
            <td height="28" align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b>Ap. Paterno:
              </b></font></td>
            <td align="LEFT"><input name="f_apellidoP" type="text" class="text" id="f_apellido" readonly value="<%=s_apellidoP%>" size="60" maxlength="35"></td>
          </tr>
          <tr> 
            <td height="28" align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b>Ap. Materno:
              </b></font></td>
            <td align="LEFT"><input name="f_apellidoM" type="text" class="text" id="f_apellido" readonly value="<%=s_apellidoM%>" size="60" maxlength="35"></td>
          </tr>
          <tr> 
            <td align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b>Puesto:</b></font></td>
            <td align="LEFT">
              <input name="f_puesto" type="text" class="text" id="f_puesto" readonly value="<%=s_puesto%>" size="30" maxlength="35"><font size="1">&nbsp;R.H.&nbsp; <%=puesto%></font>
            </td>
          </tr>          
          <tr> 
            <td align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b>Departamento:</b></font></td>
            <td align="LEFT">
              <input name="f_depto" type="text" class="text" id="f_depto" readonly value="<%=s_depto%>" size="30" maxlength="60"><font size="1">&nbsp;R.H.&nbsp; <%=depto%></font>
            </td>
          </tr>          
          <tr> 
            <td align="LEFT"><font size="2" face="Arial, Helvetica, sans-serif"><b>Status 
              : </b></font> </td>
            <td align="LEFT">
			<%
				if(s_check1.equals("checked")){
					out.print("Activo");
				}else if(s_check2.equals("checked")){
					out.print("Jubilado");
				}else if(s_check3.equals("checked")){
					out.print("Inactivo");
				}
			%>
	      </tr>
          <tr>
            <td align="LEFT"><strong>Cotejado:</strong></td>
			<td align="LEFT"><input name="f_cotejado" type="text" class="text" id="f_cotejado" value="<%=s_coteja%>" size="2" maxlength="1" readonly></td>
          </tr>
      </table>
	</td>
  </tr>
 </table>
<%	}else{ %>
		<div align="center">¡No existe el empleado! ¡Busca un empleado activo!</div>
<%	} %> 
<!-- fin de estructura -->
</div>