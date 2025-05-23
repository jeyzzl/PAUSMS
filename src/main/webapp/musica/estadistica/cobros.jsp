<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.musica.MusiPeriodo"%>

<jsp:useBean id="PeriodoU" scope="page" class="aca.musica.MusiPeriodoUtil"/>
<jsp:useBean id="alumnoU" scope="page" class="aca.musica.MusiAlumnoUtil"/>
<jsp:useBean id="calculoU" scope="page" class="aca.musica.MusiCalculoUtil"/>

<script type="text/javascript" src="../../js/jquery-1.5.1.min.js"></script>
<script type="text/javascript">
	
	function recarga(){
		document.forma.submit();
	}	
	  
	$(document).ready(function() {  
	     $(".botonExcel").click(function(event) {  
	     	$("#datos_a_enviar").val( $("<div>").append( $("#Exportar_a_Excel").eq(0).clone()).html());  
	     	$("#FormularioExportacion").submit();  
		});  
	});	
</script>

<% 
	// Formato de los numeros decimales
	java.text.DecimalFormat getformato	= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	java.text.SimpleDateFormat sdf		= new java.text.SimpleDateFormat("dd/MM/yyyy");

	String codigoId			= (String) session.getAttribute("CodigoId");
	String periodoId 		= request.getParameter("PeriodoId");
	if (periodoId==null || periodoId.equals("null") || periodoId.equals("")){
		periodoId 			= aca.musica.MusiPeriodo.getPeriodoActual(conEnoc);
	}
	
	String institucionId	= "0";
	String alumInstitucion	= "0";
	String tipoPago			= "X";
	String bgColor			= "";
	
	boolean pagare1			= false;
	boolean pagare2			= false;
	boolean pagare3			= false;
	
	java.util.Date hoy 	= new java.util.Date();
	// Obtiene las fechas del vencimiento de los pagares en el periodo
	String [] fechas	= aca.musica.MusiPagareUtil.getFechasPagare(conEnoc, periodoId).split(",");
	
	if(fechas.length>2){
		// Fecha de vencimiento primer pagare
		java.util.Date fecha = sdf.parse(fechas[0]);
		if (hoy.compareTo(fecha)>=0) pagare1 = true;
		
		// Fecha de vencimiento segundo pagare
		fecha = sdf.parse(fechas[1]);
		if (hoy.compareTo(fecha)>=0) pagare2 = true;
		
		// Fecha de vencimiento tercer pagare
		fecha = sdf.parse(fechas[2]);
		if (hoy.compareTo(fecha)>=0) pagare3 = true;
	}
	
	/* Llena el treeMap de telefonos*/
	java.util.TreeMap<String,aca.musica.MusiAlumno> treeTel = new java.util.TreeMap<String,aca.musica.MusiAlumno>();
	treeTel = aca.musica.MusiAlumnoUtil.getMapMusiAlum(conEnoc);
	
	ArrayList lisPeriodos	= new ArrayList();
	lisPeriodos				= PeriodoU.getListAll(conEnoc,"ORDER BY PERIODO_ID");
	
	ArrayList lisPagare	= new ArrayList();
	lisPagare			= calculoU.getListPagare(conEnoc,periodoId,"ORDER BY SOBRESUELDO, INSTITUCION_ID, MUSI_APELLIDO(CODIGO_ID)");
	
	int cont = 0;	
	double total1 = 0;
	double total2 = 0;
	double total3 = 0;
	
	double totalGeneral1	= 0;
	double totalGeneral2	= 0;
	double totalGeneral3	= 0;
	
	double saldo		= 0;
	double pagares 		= 0;
	double saldoVencido	= 0;
%>

<div class="container-fluid">
<h1>Cobros a Instituciones</h1>
<div class="alert alert-info">
	<a href="opcion" class="btn btn-primary">Regresar</a>&nbsp;&nbsp;
	<b>Seleccione el Periodo:</b>
	<form action="cobros" name="forma">
          	<select onchange='javascript:recarga()' name="PeriodoId">
			<%
				for (int i=0; i < lisPeriodos.size(); i++){
					aca.musica.MusiPeriodo periodo = (aca.musica.MusiPeriodo) lisPeriodos.get(i);
			%>
						<option <%if( periodoId.equals(periodo.getPeriodoId() ))out.print(" Selected ");%> value="<%= periodo.getPeriodoId() %>">[<%= periodo.getPeriodoId() %>] <%= periodo.getPeriodoNombre() %></option>
			<%
				}
%>
			</select>
			
</div>

<form action="../../excel" method="post" id="FormularioExportacion">
  <input type="hidden" id="datos_a_enviar" name="datos_a_enviar" />
  <input type="hidden" id="archivo" name="archivo" value="CobrosAInstituciones.xls"/>
  <table style="width:90%" >
    <tr><td><a href="#"><img src="../../imagenes/excel-chico.png" width="30px" class="botonExcel" onmouseover="this.src='../../imagenes/excel-chico-over.png'" onmouseout="this.src='../../imagenes/excel-chico.png'"/></a></td></tr>    
  </table>
</form>

<table  class="table table-fullcondensed" width="95%" id="Exportar_a_Excel"> 

<%
	for (int j=0; j<lisPagare.size(); j++){
		aca.musica.MusiCalculo calculo = (aca.musica.MusiCalculo) lisPagare.get(j);
		
		//Obtiene la institucion actual del alumno
		//alumInstitucion = aca.musica.MusiAlumno.getInstitucion(conEnoc, calculo.getCodigoId());		
		
		//Verifica si tiene pagares con cantidad mayor que cero 
		pagares = Double.valueOf(calculo.getPagare1()).doubleValue()+ Double.valueOf(calculo.getPagare2()).doubleValue()+Double.valueOf(calculo.getPagare3()).doubleValue();
		
		if(pagares>0) {
			pagares=0; saldo = 0;		
			saldo = aca.musica.MusiMovimiento.saldoAlumno(conEnoc, calculo.getCodigoId()); 
			if (pagare1==false) pagares += aca.musica.MusiCalculo.getPagare1(conEnoc, calculo.getCodigoId(), periodoId);
			if (pagare2==false) pagares += aca.musica.MusiCalculo.getPagare2(conEnoc, calculo.getCodigoId(), periodoId);
			if (pagare3==false) pagares += aca.musica.MusiCalculo.getPagare3(conEnoc, calculo.getCodigoId(), periodoId);
			
			saldoVencido = saldo + pagares;
			if (saldoVencido>0) saldoVencido = 0;			
			
			if(!calculo.getSobresueldo().equals(tipoPago)){
				if (!tipoPago.equals("X")){
					
					totalGeneral1 += total1;
					totalGeneral2 += total2;
					totalGeneral3 += total3;
					%>
					  <tr bgcolor="#7fa3d0" >
						<td style="color:#000099" colspan="8" align="center" ><b> T O T A L &nbsp; &nbsp; I N S T I T U C I O N :</b></td>
						<td style="color:#000099" align="right" ><b><%=getformato.format(total1) %></b></td>
						<td style="color:#000099" align="right" ><b><%=getformato.format(total2) %></b></td>
						<td style="color:#000099" align="right" ><b><%=getformato.format(total3) %></b></td>
						<td style="color:#000099" align="right" ><b>&nbsp;</b></td>
					  </tr>
					<%					
				}
				tipoPago 	= calculo.getSobresueldo();
				institucionId = "0";
				%>
				  <tr>
					<td style="font-size:14pt;" colspan="12" style="text-align:center;">
					  <b><br><%= tipoPago.equals("S")?"Cobros en Sobresueldo":tipoPago.equals("N")?"Cobros en Nomina":"Cobros en Efectivo"%></b>
					</td>
				  </tr>
				<%				
				
			}
			
			if ( ! institucionId.equals(calculo.getInstitucionId()) ){
				if (!institucionId.equals("0")){
					%>
					  <tr bgcolor="#7fa3d0" >
						<td style="color:#000099" colspan="8" align="center" ><b> T O T A L &nbsp; &nbsp; I N S T I T U C I O N :</b></td>
						<td style="color:#000099" align="right" ><b><%=getformato.format(total1) %></b></td>
						<td style="color:#000099" align="right" ><b><%=getformato.format(total2) %></b></td>
						<td style="color:#000099" align="right" ><b><%=getformato.format(total3) %></b></td>
						<td style="color:#000099" align="right" ><b>&nbsp;</b></td>
					  </tr>
					<%					
				}
				institucionId = calculo.getInstitucionId();
				totalGeneral1 += total1;
				totalGeneral2 += total2;
				totalGeneral3 += total3;
				
				total1 = 0; total2=0; total3=0;
				%>
				  <tr><td colspan="12"><font size="2"><b>Institución: &nbsp; <%= aca.musica.MusiInstitucion.getNombreInstitucion(conEnoc,institucionId)%></b></font></td></tr>
				  <tr> 
				    <th width="5%"><spring:message code="aca.Numero"/></th>
				    <th width="10%"><spring:message code="aca.Matricula"/></th>
				    <th width="20%">Apellidos</th>
				    <th width="20%"><spring:message code="aca.Nombre"/></th>    
				    <th width="10%">><spring:message code="aca.Telefono"/></th>
				    <th width="14%"><spring:message code="aca.Celular"/></th>
				    <th width="5%">TipoPago</th>
				    <th width="7%"><spring:message code="aca.Saldo"/></th>
				    <th width="7%">Pagare1</th>
				    <th width="7%">Pagare2</th>
				    <th width="7%">Pagare3</th>
				    <th width="7%">Vencido</th>    
				  </tr>
				<%				
			}		
			cont++;
			if(cont%2 == 0) bgColor = "bgcolor='#CCCCCC'"; else bgColor = "";
			
			total1 += Double.valueOf(calculo.getPagare1()).doubleValue();
			total2 += Double.valueOf(calculo.getPagare2()).doubleValue();
			total3 += Double.valueOf(calculo.getPagare3()).doubleValue();
			
			String[] nombre = aca.musica.MusiAlumno.getNombreDividido(conEnoc, calculo.getCodigoId()).split(",");
			
			String telefono 	= "-";
		    String celular		= "-";
		    
		    // Obttiene los datos de telefono y celular del alumno.
		    if (treeTel.containsKey(calculo.getCodigoId())){    	
		    	aca.musica.MusiAlumno alumno = treeTel.get(calculo.getCodigoId());
		    	telefono		= alumno.getTelefono();
		    	celular			= alumno.getCelular();
		    }
		%>
		  <tr class="tr2" <%=bgColor%>>
		    <td align="center"><font size="1"><%=cont%></font></td>
		    <td align="center"><font size="1"><%=calculo.getCodigoId() %></font></td> 
		    <td align="left"><font size="1"><%= nombre[1] %></font></td>
		    <td align="left"><font size="1"><%= nombre[0] %></font></td>
		    <td align="left"><font size="1">&nbsp;<%=telefono%></font></td>
		    <td align="left"><font size="1">&nbsp;<%=celular%></font></td>
		<%			String formap = "";
		    		if(calculo.getSobresueldo().equals("-")){
		    			formap = "Efectivo";    
		    		}else if(calculo.getSobresueldo().equals("S")){
		    			formap = "Sobresueldo";
		    		}else 
		    			formap = "Nomina";    
		    
		   	//String celular =aca.musica.MusiAlumno.getCelular(conEnoc, calculo.getCodigoId());
		   	
		    %>
		    <td align="center"><font size="1"><%=formap %></font></td>
		    <td align="right"><font size="1"><%=getformato.format(saldo) %></font></td>
		    <td align="right"><font size="1"><%=getformato.format(Double.valueOf(calculo.getPagare1()).doubleValue()) %></font></td>
		    <td align="right"><font size="1"><%=getformato.format(Double.valueOf(calculo.getPagare2()).doubleValue()) %></font></td>
		    <td align="right"><font size="1"><%=getformato.format(Double.valueOf(calculo.getPagare3()).doubleValue()) %></font></td>
		    <td align="right"><font size="1"><%=getformato.format(saldoVencido) %>&nbsp;</font></td>    
		  </tr>
<% 		}
	}
%>
  <tr bgcolor="#7fa3d0" >
	<td style="color:#000099" colspan="8" align="center" ><b> T O T A L &nbsp; &nbsp; I N S T I T U C I O N :</b></td>
	<td style="color:#000099" align="right" ><b><%=getformato.format(total1) %></b></td>
	<td style="color:#000099" align="right" ><b><%=getformato.format(total2) %></b></td>
	<td style="color:#000099" align="right" ><b><%=getformato.format(total3) %></b></td>
	<td style="color:#000099" align="right" >&nbsp;</td>
  </tr>
  <tr><td colspan="10">&nbsp;</td></tr>
  <tr class="th2" >
	<td colspan="8" align="center" ><b> T O T A L &nbsp; &nbsp; G E N E R A L :</b></td>
	<td align="right" ><b><%=getformato.format(totalGeneral1) %></b></td>
	<td align="right" ><b><%=getformato.format(totalGeneral2) %></b></td>
	<td align="right" ><b><%=getformato.format(totalGeneral3) %></b></td>
	<td align="right" >&nbsp;</td>
  </tr>
</table>
</div>
<%@ include file = "../../cierra_enoc.jsp"%>