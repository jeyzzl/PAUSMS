<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Alumno" scope="page" class="aca.musica.MusiAlumno"/>
<jsp:useBean id="CalculoDetU" scope="page" class="aca.musica.MusiCalculoDetUtil"/>
<jsp:useBean id="CobroU" scope="page" class="aca.musica.CobroUtil"/>
<jsp:useBean id="Calculo" scope="page" class="aca.musica.MusiCalculo"/>
<jsp:useBean id="Pagare" scope="page" class="aca.musica.MusiPagare"/>
<jsp:useBean id="Periodo" scope="page" class="aca.musica.MusiPeriodo"/>
<head>
<style type="text/css">
.etiqueta2{
		background-color: none;
		text-align: none;
		color: black;
	}
</style>
</head>
<%
	// Declaracion de variables	
	java.text.DecimalFormat getformato= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String codigoId			= (String) session.getAttribute("CodigoId");
	String institucion 		= (String)session.getAttribute("institucion");
	String periodoId 		= request.getParameter("PeriodoId");
	aca.util.Fecha fecha	= new aca.util.Fecha();	
	String hoy 				= aca.util.Fecha.getHoy();
	String tmp="";
	
	Alumno.mapeaRegId(conEnoc,codigoId);
	
	/* Datos del calculo del alumno */
	Calculo.setCodigoId(codigoId);
	Calculo.setPeriodoId(periodoId);
	if (Calculo.existeReg(conEnoc)){
		Calculo.mapeaRegId(conEnoc,codigoId, periodoId);
		hoy = Calculo.getFecha();
	}
	
	Pagare.setPeriodoId(periodoId);
	if (Pagare.existeReg(conEnoc)){
		Pagare.mapeaRegId(conEnoc, periodoId);
	}
	
	String fechaLetra 		= fecha.getFechaTexto( hoy );	
	String strBgcolor		= "";
	//String numPagare		= aca.musica.MusiPeriodo.getNumPagare(conEnoc,periodoId);
	
	double saldo			= 0;
	double costoTotal 		= 0;
	double pagoInicial		= 0;
	double matricula		= 0;
	double renta			= 0;
	double ensenanza		= 0;
	double porEnsenanza		= 0;
	int porcentaje			= 0;	
	
	ArrayList lisAsignados 	= CalculoDetU.getListAsignados(conEnoc,codigoId, periodoId,"ORDER BY MUSI_CTATIPO(CUENTA_ID), CUENTA_ID");
	
%>

<body>
<table style="width:99%; margin:0 auto;" id="noayuda">
  <tr><td class="etiqueta2" align="center" colspan="4" style="font-size:12pt" ><b> <%=institucion%>, A. C.</b></td></tr>
  <tr><td class="etiqueta2" align="center" style="font-size:11pt">Conservatorio de Música</td></tr>
</table>
<table style="width:99%; margin:0 auto;"   >
<tr>
  <td class="etiqueta2" align="right"><spring:message code="aca.Fecha"/>: <b><u><%= fechaLetra %></u></b>&nbsp;&nbsp;</td>
</tr>  
</table>
<table style="width:99%; margin:0 auto;" id="noayuda" >
  	  <tr><td class="etiqueta2"  align="center" colspan="5" style="font-size:11pt"><b>Datos del Alumno</b></td></tr>
  	  <tr>
	    <td class="etiqueta2" width="15%"><b><spring:message code="aca.Codigo"/>:</b></td>
	    <td class="etiqueta2" align="center" style="border-bottom: 1px solid #666666;"><%= codigoId %></td>
	    <td>&nbsp;</td>
	    <td class="etiqueta2" width="15%"><b><spring:message code="aca.Nombre"/>:</b></td>	     
	    <td class="etiqueta2" style="border-bottom: 1px solid #666666;"><%= Alumno.getNombre()+" "+Alumno.getApellidoPaterno()+" "+Alumno.getApellidoMaterno()%></td>
  	  </tr>
  	  <tr>
	    <td class="etiqueta2"><b><spring:message code="aca.FechaNac."/></b>:</td>
	    <td class="etiqueta2" align="center" style="border-bottom: 1px solid #666666;"><%= Alumno.getFechaNac() %></td>
	    <td>&nbsp;</td>
	    <td class="etiqueta2"><b><spring:message code="aca.Edad"/>:</b></td>  
	    <td class="etiqueta2" style="border-bottom: 1px solid #666666;"><%= Alumno.getEdad(conEnoc) %></td>
  	  </tr>
  	  <tr>
	    <td class="etiqueta2"><b><spring:message code="aca.Seguro"/>:</b></td>
	    <td class="etiqueta2" align="center" style="border-bottom: 1px solid #666666;"><% if (Alumno.getSeguro().equals("S")) out.print("SI"); else out.print("NO"); %></td>
	    <td>&nbsp;</td>
	    <td class="etiqueta2"><b><spring:message code="aca.Tutor"/>:</b></td>	     
	    <td class="etiqueta2" style="border-bottom: 1px solid #666666;"><%= Alumno.getTutor() %></td>
  	  </tr>
<%
		saldo = aca.musica.MusiMovimiento.saldoAlumno(conEnoc,codigoId);
%>  	  
  	  <tr>
  	    <td class="etiqueta2" colspan="5">
  	      <b><spring:message code="aca.Tel"/>: </b> <%= Alumno.getTelefono() %> &nbsp; &nbsp; 
  	      <b><spring:message code="aca.Cel"/>: </b> <%= Alumno.getCelular() %> &nbsp; &nbsp; 
  	      <b><spring:message code="aca.Matricula"/> UM: </b> <%= Alumno.getCodigoUM() %>&nbsp; &nbsp;
  	      <b><spring:message code="aca.Institucion"/>: </b><%= aca.musica.MusiInstitucion.getNombreInstitucion( conEnoc, aca.musica.MusiAlumno.getInstitucion(conEnoc, codigoId)) %>&nbsp; &nbsp;
  	      <b><spring:message code="aca.Saldo"/>: <%= getformato.format(saldo) %></b>
  	    </td>
  	  </tr>
	</table>  	
  	<table style="width:99%; margin:0 auto;" id="noayuda" class="table table-fullcondensed table-nohover">
  	  <tr><td class="etiqueta2" align="center" colspan="4" style="font-size:11pt"><b>Cursos Asignados</b></td></tr>
  	  <tr>
	    <th class="etiqueta2"><spring:message code="aca.Numero"/></th>
  		<th class="etiqueta2"><spring:message code="aca.Tipo"/></th>
  		<th class="etiqueta2"><spring:message code="aca.Cuenta"/></th>
  		<th class="etiqueta2"><spring:message code="aca.Maestro"/></th>
  		<th class="etiqueta2"><spring:message code="aca.Instrumento"/></th>
  		<th class="etiqueta2"><spring:message code="aca.Frec"/></th>
  		<th class="etiqueta2"><spring:message code="aca.Costo"/> - <spring:message code="aca.Beca"/></th>
  		<th class="etiqueta2"><spring:message code="aca.Total"/></th>
	  </tr>
<%
	matricula 	= Double.valueOf(Calculo.getMatricula()).doubleValue();
	renta 		= Double.valueOf(Calculo.getRenta()).doubleValue();
	ensenanza	= Double.valueOf(Calculo.getEnsenanza()).doubleValue();
	pagoInicial = Double.valueOf(Calculo.getPagoInicial()).doubleValue();
	porcentaje	= Integer.parseInt(Calculo.getPorcentaje());	

	double sumaBeca = 0;
	for (int z=0; z< lisAsignados.size(); z++){
		aca.musica.MusiCalculoDet caldet = (aca.musica.MusiCalculoDet) lisAsignados.get(z);
		
		double costoCuenta =   Double.valueOf(caldet.getCantidad()).doubleValue();
		
		if(z%2 == 0) strBgcolor = "bgcolor='#CCCCCC'"; else strBgcolor = "";
%>
	  <tr <%= strBgcolor %>>
  		<td class="etiqueta2" align="center"><%= z+1 %></td>
  		<td class="etiqueta2"><em><%= aca.musica.MusiCuenta.getTipoNombre(conEnoc,caldet.getCuentaId()) %></em></td>
  		<td class="etiqueta2">
  		  <b><%= aca.musica.MusiCuenta.getCuentaNombre(conEnoc, caldet.getCuentaId()) %></b>
  		</td>
  		<td class="etiqueta2">
  			<%= aca.musica.MusiMaestro.getNombre(conEnoc, caldet.getMaestro(), "NOMBRE") %>
  		</td>
  		<td class="etiqueta2">
  			<%= aca.musica.MusiInstrumento.getNombreInstrumento(conEnoc, caldet.getInstrumentoId()) %>
  		</td>
  		<td class="etiqueta2">
  			<%=caldet.getFrecuencia()%>
  		</td>
	<%
		double becas = costoCuenta*(Double.parseDouble(caldet.getBeca()==null ? "0" : caldet.getBeca())/100);
		double total = costoCuenta - becas;
		sumaBeca+=becas;
		
		costoTotal+=total;
	%>
  		<td class="etiqueta2" align="right"><b><%= getformato.format(costoCuenta)+" - "+getformato.format(becas) %></b></td>
  		<td class="etiqueta2" align="right"><b><%= getformato.format(costoCuenta-becas) %></b></td>
	  </tr>
<%	} %>
	  <tr bgcolor="black">
  		<td class="etiqueta2" align="center" colspan="7" style="color:white"><spring:message code="aca.Costo"/> del Semestre:</td>	
  		<td class="etiqueta2" align="right" style="color:white"><b><%= getformato.format( costoTotal ) %></b></td>
	  </tr>
	</table>
	<br>
	<table style="width:99%; margin:0 auto;" id="noayuda" class="table table-fullcondensed">	  
	  <tr>
	    <td class="etiqueta2" align="center">
	      Forma de Pago: <b> <%= Calculo.getFormaPago().equals("C")?"Contado":"Pagare" %> </b>
	    </td>
	    <td class="etiqueta2" align="center">N° Emp. <input type="text" name="Empleado" size="10" value="<%= Calculo.getEmpleado() %>"></td>
	    <td class="etiqueta2" align="center">
	      Sobresueldo:
	      <% if (Calculo.getSobresueldo().equals("S")){ %>
	        <img src="../../imagenes/checked.ico" alt="">
	      <% }else{%>
	      	<img src="../../imagenes/unchecked.ico" alt="">
	      <% }%>&nbsp; &nbsp;
	      Nómina:
	      <% if (Calculo.getSobresueldo().equals("N")){ %>
	        <img src="../../imagenes/checked.ico" alt="">
	      <% }else{%>
	      	<img src="../../imagenes/unchecked.ico" alt="">
	      <% }%>&nbsp; &nbsp;
	      Otro:
	      <% if (Calculo.getSobresueldo().equals("-")){ %>
	        <img src="../../imagenes/checked.ico" alt="">
	      <% }else{%>
	      	<img src="../../imagenes/unchecked.ico" alt="">
	      <% }%>
	    </td>
	  </tr>
	</table>
	<table style="margin: 0 auto;" id="noayuda" class="table table-fullcondensed">
	  <tr><td class="etiqueta2" colspan="3" align="center" style="font-size:12pt;">Detalle de Pagos</td></tr>
	  <tr>
	    <th class="etiqueta2" align="center">Concepto</th>	    
	    <th class="etiqueta2" align="center"><spring:message code='aca.Cantidad'/></th>
	    <th class="etiqueta2" align="center"><spring:message code="aca.Comentario"/></th>
	  </tr>
	  <tr>
	    <td class="etiqueta2" align="left">Matrícula:</td>	    
	    <td class="etiqueta2" align="right"><%= getformato.format(matricula) %></td>
	    <td class="etiqueta2" align="center">&nbsp;</td>
	  </tr>
	  <tr>
	    <td class="etiqueta2" align="left">Renta:</td>	    
	    <td class="etiqueta2" align="right"><%= getformato.format(renta) %></td>
	    <td class="etiqueta2" align="center">&nbsp;</td>
	  </tr>
	  <tr>
	    <td class="etiqueta2" align="left">Enseñanza:</td>
	    <td class="etiqueta2" align="right">
	      <%= getformato.format(ensenanza) %>
	    </td>	    
	    <td class="etiqueta2" align="center">&nbsp;</td>
	  </tr>
	  <tr>
	    <td align="left">Beca:</td>	    
	    <td align="right">	      
	      <%=getformato.format(sumaBeca) %>&nbsp;
	      <input name="inputBeca" type="hidden"  value="<%=getformato.format(sumaBeca) %>">
	    </td>
	    <td align="center">&nbsp;</td>
	  </tr>
	  <%
	  Double pag = 0.0;
	  if(Calculo.getFormaPago().equals("P")){
		  Periodo.mapeaRegId(conEnoc, periodoId);
		  pag = Double.parseDouble(Periodo.getCostoPagare()==null?"0":Periodo.getCostoPagare());
		  %>
		  <tr>
		    <td bgcolor="#D8D8D8" align="left"><b><spring:message code="aca.Costo"/> Pagaré:</b></td>
		    <td bgcolor="#D8D8D8" align="right"><b><%=Periodo.getCostoPagare() %></b></td>  
		    <td align="right">&nbsp;</td>
		  </tr>
	  <%} %>
	  <tr bgcolor="#CCCCCC">
	    <td class="etiqueta2" align="left"><b>Total:</b></td>
	    <td align="right"><b><%=getformato.format(matricula+ensenanza+renta+pag-sumaBeca) %></b></td>
	    <td class="etiqueta2" align="center">&nbsp;</td>
	  </tr>
	  <tr>	  
	    <td class="etiqueta2" align="left">Pago Inicial:</td>	    
	    <td class="etiqueta2" align="right"><%= getformato.format(pagoInicial) %></td>
	    <td class="etiqueta2" align="right">( Mat.+ %Enza.)</td>
	  </tr>
<% 
	if ( (Calculo.getEstado().equals("C")||Calculo.getEstado().equals("I")) && Calculo.getFormaPago().equals("P")){
		int numPagares = 0;
		if ( Integer.parseInt(Calculo.getNumPagare()==null?"0":Calculo.getNumPagare())>0 )numPagares++;
		if ( Integer.parseInt(Calculo.getNumPagare()==null?"0":Calculo.getNumPagare())>1 )numPagares++;
		if ( Integer.parseInt(Calculo.getNumPagare()==null?"0":Calculo.getNumPagare())>2 )numPagares++;
		
		double costoPagare = Double.parseDouble(Periodo.getCostoPagare())/numPagares;
		
		if ( Integer.parseInt(Calculo.getNumPagare()==null?"0":Calculo.getNumPagare())>0 ){
%>
	  <tr>
	    <td class="etiqueta2" align="left">Primer Pagaré:</td>
	    <td class="etiqueta2" align="right"><%= Calculo.getPagare1()==null?costoPagare:getformato.format(Double.parseDouble(Calculo.getPagare1())+costoPagare).replaceAll(",",".") %></td>
	    <td class="etiqueta2" align="center">( &nbsp; Vence: <%= Pagare.getPagare1()==null?"-":Pagare.getPagare1() %> )</td>
	  </tr>
<% 		}%>
<% 
		if ( Integer.parseInt(Calculo.getNumPagare()==null?"0":Calculo.getNumPagare())>1 ){ 
%>
	  <tr>
	    <td class="etiqueta2" align="left">Segundo Pagaré:</td>	    
	    <td class="etiqueta2" align="right"><%= Calculo.getPagare2()==null?costoPagare:getformato.format(Double.parseDouble(Calculo.getPagare2())+costoPagare).replaceAll(",",".") %></td>
	    <td class="etiqueta2" align="center">( &nbsp; Vence: <%= Pagare.getPagare2()==null?"-":Pagare.getPagare2() %> )</td>
	  </tr>
<% 		}%>
<% 

if ( Integer.parseInt(Calculo.getNumPagare()==null?"0":Calculo.getNumPagare())>2 ){ 
%>
	  <tr>
	    <td class="etiqueta2" align="left">Tercer Pagaré:</td>	    
	    <td class="etiqueta2" align="right"><%= Calculo.getPagare3()==null?costoPagare:getformato.format(Double.parseDouble(Calculo.getPagare3())+costoPagare).replaceAll(",",".") %></td>
	    <td class="etiqueta2" align="center">( &nbsp; Vence: <%= Pagare.getPagare3()==null?"-":Pagare.getPagare3() %> )</td>
	  </tr>
<% 		}%>
<% 	}%>	  
	
	</table>
	<br><br><br>
	<table style="width:70%" align="center" border="1" cellpadding="3" >
	<tr>
	<td class="etiqueta2" align="justify">Por el presente pagaré me comprometo a pagar al Conservatorio de Música las cantidades citadas en los vencimientos señalados.
	Si no fuere cubierto a su vencimiento, causará suspensión de los servicios de enseñanza. En Montemorelos, N.L., México, a <%=fechaLetra%>	 </td>
	</tr>	
	</table>
	<br><br><br>
	<table style="width:50%" align="center"   >
	<tr>
	<td class="etiqueta2" align="center">___________________________________________________</td>
	</tr>
	<tr>
	<td class="etiqueta2" align="center">FIRMA DE COMPROMISO DEL ESTUDIANTE O TUTOR</td>
	</tr>
	<tr><td>&nbsp;</td></tr>
	<tr>
	<td class="etiqueta2" align="justify">NOta:&nbsp;<%= Pagare.getComentario()%> </td>
	</tr>	

	</table>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>