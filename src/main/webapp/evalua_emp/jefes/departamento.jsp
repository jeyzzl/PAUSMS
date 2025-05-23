<%@page import="java.util.Vector"%>
<%@page import="java.util.HashSet"%>
<%@page import="aca.proyectos.EmpCCosto"%>
<%@page import="java.util.StringTokenizer"%>

<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.proyectos.OPActividad"%>
<%@page import="aca.proyectos.OPProyectos"%>
<%@page import="aca.proyectos.OPMetas"%>
<%@page import="aca.proyectos.DatosEncabezado"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page import="aca.proyectos.DatosEncabezado"%>

<%
Vector vCuentas= new Vector();
Vector vCEmplea= new Vector();
HashSet hs= new HashSet();
boolean muestra = false;
EmpCCosto cuenta = new EmpCCosto(conEnoc);

if(request.getParameter("buscar")!=null){
	
	cuenta.cuentaPersonal((String)request.getParameter("noempleado"));
	vCuentas = cuenta.cuentasGenerales();
	vCEmplea = cuenta.getVb();
	hs = cuenta.getHs();
	muestra = true;
	
}
Iterator<String> itEmpleados = cuenta.empleados().iterator();

String salida ="";
if(request.getParameter("empleadono")!=null){
	salida = cuenta.guardar(request);
    //response.sendRedirect("departamento");
	out.print("<div class='alert alert-success'><a class='btn btn-primary' href='departamento'>Departamento</a></div>");
}
%>

<html>
<head>
<title><spring:message code='aca.DocumentoSinTitulo'/></title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<SCRIPT TYPE="text/javascript">
		<!--
		function MoveOption(objSourceElement, objTargetElement)
		{
			var aryTempSourceOptions = new Array();
			var x = 0;
			
			//looping through source element to find selected options
			for (var i = 0; i < objSourceElement.length; i++) {
				if (objSourceElement.options[i].selected) {
					//need to move this option to target element
					for(var j=0; j < objTargetElement.length; j++){
//						var intTargetLen = objTargetElement.length++;
						if(objTargetElement.options[j].value == objSourceElement.options[i].value){
							objTargetElement.options[j].disabled = false;
						}
					}
				} else {
					//storing options that stay to recreate select element
					var objTempValues = new Object();
					objTempValues.text = objSourceElement.options[i].text;
					objTempValues.value = objSourceElement.options[i].value;
					aryTempSourceOptions[x] = objTempValues;
					x++;
				}
			}
			
			//resetting length of source
			objSourceElement.length = aryTempSourceOptions.length;
			//looping through temp array to recreate source select element
			for (var i = 0; i < aryTempSourceOptions.length; i++) {
				objSourceElement.options[i].text = aryTempSourceOptions[i].text;
				objSourceElement.options[i].value = aryTempSourceOptions[i].value;
				objSourceElement.options[i].selected = false;
			}
		}
		
		function agregar(objSourceElement, objTargetElement){
			for (var i = 0; i < objSourceElement.length; i++) {
				if (objSourceElement.options[i].selected) {
					var intTargetLen = objTargetElement.length++;
                	objTargetElement.options[intTargetLen].text = objSourceElement.options[i].text;
                	objTargetElement.options[intTargetLen].value = objSourceElement.options[i].value;
					objSourceElement.options[i].disabled = true;
					objSourceElement.options[i].selected = false;
				}
        	}
		}
		
		function quitar(){
		
		}
		
		function selectOptions(listaSeleccionar){
			for(var i= 0; i < listaSeleccionar.length; i++){
				listaSeleccionar.options[i].selected = true;
			}
			document.datosEmpleado.submit();
		}
		//-->
    </SCRIPT>
</head>

<body>
<div class="container-fluid">
	<h1>Lista de Departamentos</h1>
	<div class="alert alert-info">
	<form action="" method="post" name="busqEmpleado" id="busqEmpleado">
      Numero de empleado 
      <select name="noempleado" id="cuentasEmpleado" style="width: 500px">
      <%
      while(itEmpleados.hasNext()){
    	  StringTokenizer stk = new StringTokenizer(itEmpleados.next(),":");
    	  String numero = stk.nextToken();
    	  String nombre = stk.nextToken();
      %>
      <option value="<%= numero %>" <% if(request.getParameter("noempleado")!=null){ if(request.getParameter("noempleado").equals(numero)){ %> selected <% } } %>><%= nombre %></option>
      <%
      }
      %>
      </select>
      <input type="submit" name="buscar" value="Buscar">
    </form>
	</div>   
<table style="width:100%" >
   <% if(request.getParameter("buscar")!=null){ %>
  <tr>
    <td><form action="" method="post" name="datosEmpleado" id="datosEmpleado">
      <table style="width:100%"  >
        <tr>
          <td width="45%"><div align="center">
            <select name="cuentasEmpleado" size="9" multiple id="cuentasEmpleado" style="width: 100%"> 
<%			if(muestra){
				for(int i=0;i<vCEmplea.size();i++){
					StringTokenizer tka = new StringTokenizer((String)vCEmplea.elementAt(i), ",");
					String valor = (String)tka.nextElement();
					String etiqueta = (String)tka.nextElement(); %>
					<option value="<%= valor %>">(<%= valor %>) <%= etiqueta %></option>
<%				}
			}%>
            </select>
          </div></td>
          <td><div align="center">
            <input type="button" name="Submit2" value=">" onClick="MoveOption(this.form.cuentasEmpleado, this.form.cuentasGeneral)">
            <br>
            <input type="button" name="Submit3" value="<" onClick="agregar(this.form.cuentasGeneral, this.form.cuentasEmpleado)">
            <br>
          </div></td>
          <td width="45%"><div align="center">
            <select name="cuentasGeneral" size="9" multiple id="cuentasGeneral" style="width: 100%">
<%			if(muestra){
				for(int i=0;i<vCuentas.size();i++){
					StringTokenizer tka = new StringTokenizer((String)vCuentas.elementAt(i), ",");
					String valor = (String)tka.nextElement();
					String etiqueta = (String)tka.nextElement(); 
					boolean contiene = false;
					if(hs.contains(vCuentas.elementAt(i))){
						contiene = true;
					}
					%>
					<option value="<%= valor %>"<%if(contiene){%> disabled<%}%>>(<%= valor %>) <%= etiqueta %></option>
<%				}
			}%>
            </select>
          </div></td>
        </tr>
        <tr>
          <td><%if(muestra){%><input name="empleadono" type="hidden" id="empleadono" value="<%= request.getParameter("noempleado")%>"><%}%></td>
          <td><div align="center">
            <input type="button" name="Submit4" value="Enviar" onClick="selectOptions(this.form.cuentasEmpleado)">
          </div></td>
          <td><%= salida %></td>
        </tr>
      </table>
    </form></td>
  </tr>        <%
    }
        %>
</table>
	
    
</div>

<link rel="stylesheet" href="../../bootstrap/datepicker/datepicker.css" />
<script src="../../bootstrap/datepicker/datepicker.js" type="text/javascript"></script>


<%@ include file= "../../cierra_enoc.jsp" %>	