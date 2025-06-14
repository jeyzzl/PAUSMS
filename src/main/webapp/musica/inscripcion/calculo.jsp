<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file= "../../idioma.jsp" %>

<jsp:useBean id="Movimiento" scope="page" class="aca.musica.MusiMovimiento"/>
<jsp:useBean id="MovtosU" scope="page" class="aca.musica.MusiMovimientoUtil"/>
<jsp:useBean id="CobroU" scope="page" class="aca.musica.CobroUtil"/>
<jsp:useBean id="Cobro" scope="page" class="aca.musica.MusiPeriodoCobro"/>
<jsp:useBean id="Periodo" scope="page" class="aca.musica.MusiPeriodo"/>
<jsp:useBean id="PeriodoU" scope="page" class="aca.musica.MusiPeriodoUtil"/>
<jsp:useBean id="Alumno" scope="page" class="aca.musica.MusiAlumno"/>
<jsp:useBean id="Calculo" scope="page" class="aca.musica.MusiCalculo"/>
<jsp:useBean id="CalculoDet" scope="page" class="aca.musica.MusiCalculoDet"/>
<jsp:useBean id="CalculoDetU" scope="page" class="aca.musica.MusiCalculoDetUtil"/>
<jsp:useBean id="Pagare" scope="page" class="aca.musica.MusiPagare"/>
<jsp:useBean id="InstitucionU" scope="page" class="aca.musica.MusiInstitucionUtil"/>

<head>
<script type="text/javascript">
	function recarga(){
		document.frmPeriodo.submit();
	}
	
	function inscribir( CodigoId, Periodo, CostoPagare, BecaAlumno ){
		if(confirm("Esta acci�n inscribe al alumno y realiza el cargo en su estado de cuenta: "+CodigoId+"- "+Periodo)==true){
	  		document.location="calculo?Accion=4&CodigoId="+CodigoId+"&PeriodoId="+Periodo+"&CostoPagare="+CostoPagare+"&Beca="+BecaAlumno;
	  	}
	}
	
	function desinscribir( CodigoId, Periodo ){
		if(confirm("Esta acci�n cancela la inscripci�n del alumno:"+CodigoId+"-"+Periodo+". \n � Recuerde eliminar los cargos en el estado de cuenta !")==true){
	  		document.location="calculo?Accion=5&CodigoId="+CodigoId+"&PeriodoId="+Periodo;
	  	}
	}
	
	function formato( Periodo ){		
		if(confirm("Estas seguro de realizar esta accion: "+Periodo)==true){
	  		document.location="formato?PeriodoId="+Periodo;
	  	}
	}
	
	function CambiaSobresueldo( sobresueldo ){		
		document.location="calculo?Accion=6"+"&Sobresueldo="+sobresueldo;
	}
		
</script>
</head>
<%	
	java.text.DecimalFormat getformato = new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	java.text.DecimalFormat frmtDouble = new java.text.DecimalFormat("#####0.00;-#####0.00");
	
	String codigoId		= (String) session.getAttribute("CodigoId");
	String codigo 		= request.getParameter("Codigo");
	String institucion 	= (String)session.getAttribute("institucion");
	
	if ( codigo != null){
		codigoId = codigo;
		session.setAttribute("CodigoId",codigoId);
	}
	Alumno.mapeaRegId(conEnoc, codigoId);	
	
	String periodoId 		= request.getParameter("PeriodoId");
	if (periodoId==null || periodoId.equals("null") || periodoId.equals("")){
		periodoId 			= aca.musica.MusiPeriodo.getPeriodoActual(conEnoc);
	}
	
	int numAccion 			= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	
	String alumnoEstado		= aca.musica.MusiCalculo.getEstado(conEnoc,codigoId,periodoId);
	String formaPago 		= request.getParameter("FormaPago")==null?"C":request.getParameter("FormaPago");	
	int porcentaje			= request.getParameter("Porcentaje")==null?34:Integer.parseInt(request.getParameter("Porcentaje"));
	double matricula		= request.getParameter("Matricula")==null?0:Double.valueOf(request.getParameter("Matricula")).doubleValue();
	double ensenanza		= request.getParameter("Ensenanza")==null?0:Double.valueOf(request.getParameter("Ensenanza")).doubleValue();
	double renta			= request.getParameter("Renta")==null?0:Double.valueOf(request.getParameter("Renta")).doubleValue();
	
	double pagoInicial 		= 0;
	String pagare1 			= request.getParameter("Pagare1")==null?"0":request.getParameter("Pagare1");
	String pagare2 			= request.getParameter("Pagare2")==null?"0":request.getParameter("Pagare2");
	String pagare3 			= request.getParameter("Pagare3")==null?"0":request.getParameter("Pagare3");
	String numPagare		= aca.musica.MusiPeriodo.getNumPagare(conEnoc,periodoId);
	
	if (request.getParameter("NumPagare")!=null) numPagare = request.getParameter("NumPagare");  
	
	ArrayList<aca.musica.MusiPeriodo> lisPeriodos	= PeriodoU.getListAll(conEnoc,"ORDER BY ENOC.MUSI_PERIODO.F_INICIO DESC, PERIODO_ID");
	ArrayList<aca.musica.MusiPeriodoCobro> lisDisponibles	= null;
	ArrayList<aca.musica.MusiCalculoDet> lisAsignados 		= null;	

	aca.util.Fecha fecha	= new aca.util.Fecha();
	String estado 			= "";
	String tipoCuenta		= "";
	String strBgcolor		= "";
	double manejoPagare		= 0;
	double costoTotal		= 0;
	double pagares			= 0;
	double saldo			= 0;
	String Resultado 		="";
	
	conEnoc.setAutoCommit(false);
	
	switch (numAccion){
	
		case 3: {
			
			costoTotal 	= 0;
			Calculo.setPeriodoId(periodoId);
			Calculo.setCodigoId(codigoId);
			if (Calculo.existeReg(conEnoc)){
				
				// GRABA LOS DETALLES DE LOS MOVIMIENTOS DEL CALCULO DE COBRO
				matricula=0; ensenanza=0; renta=0;
				int cont2 = 0;
				ArrayList<aca.musica.MusiCalculoDet> lisCalculoDet	= CalculoDetU.getListAsignados(conEnoc,codigoId, periodoId,"ORDER BY ENOC.MUSI_CTATIPO(CUENTA_ID), CUENTA_ID");
				for (int z=0; z< lisCalculoDet.size(); z++){
					aca.musica.MusiCalculoDet caldet = (aca.musica.MusiCalculoDet) lisCalculoDet.get(z);
					
					caldet.setCodigoId(codigoId);
					caldet.setPeriodoId(periodoId);
					caldet.setCuentaId(caldet.getCuentaId());										
					Cobro.mapeaRegId(conEnoc, periodoId, caldet.getCuentaId());
					String cantidad = frmtDouble.format( Double.parseDouble(aca.musica.MusiPeriodoCobro.getCantidad(conEnoc, periodoId,caldet.getCuentaId())) * Double.parseDouble(caldet.getFrecuencia()) );
					cantidad = cantidad.replaceAll(",",".");
					costoTotal += Double.parseDouble(cantidad);
					
					caldet.setCantidad(cantidad);
					if(caldet.updateReg(conEnoc)){
						// Separa los totales de matricula, renta y ense�anza en el calculo					
						tipoCuenta = aca.musica.MusiCuenta.getTipo(conEnoc, caldet.getCuentaId());					
						if (tipoCuenta.equals("0")){
							matricula += Double.valueOf(caldet.getCantidad());
						}else if(tipoCuenta.equals("1")){
							ensenanza += Double.valueOf(caldet.getCantidad());
						}else{
							renta += Double.valueOf(caldet.getCantidad());
						}
					}			
					cont2++;
				}
				
				Calculo.setFecha(aca.util.Fecha.getHoy());
				Calculo.setEstado("C");
				Calculo.setEmpleado(request.getParameter("Empleado"));
				Calculo.setFormaPago(formaPago);
				
				// Asigna el numero de pagares 
				if (formaPago.equals("P")){
					if (request.getParameter("NumPagare").equals("0")){
						Calculo.setNumPagare("1");
						numPagare = "1";
					}else{
						Calculo.setNumPagare(request.getParameter("NumPagare"));
					}	
					Calculo.setHayPagoIni(request.getParameter("HayPagoIni"));
				}else{
					Calculo.setNumPagare("0");
					Calculo.setHayPagoIni("S");					
				}
				matricula = Double.parseDouble(frmtDouble.format( matricula ));
				Calculo.setMatricula(String.valueOf(matricula));
				Calculo.setRenta(String.valueOf(renta));
				
				if (request.getParameter("Sobresueldo")!=null){
					Calculo.setSobresueldo(request.getParameter("Sobresueldo"));
				}else{
					Calculo.setSobresueldo("-");
				}
				
				//Determina el pago inicial 
				if (request.getParameter("HayPagoIni").equals("S")){
					if (formaPago.equals("C")){ 
						pagoInicial = costoTotal;
					}else if ( request.getParameter("PagoInicial") == null || request.getParameter("PagoInicial").equals("0.0")){
						pagoInicial = (Double.valueOf(request.getParameter("Matricula"))+(Double.valueOf(request.getParameter("Ensenanza")).doubleValue()*porcentaje/100))-Double.parseDouble(request.getParameter("inputBeca"));							
					}else{						
						pagoInicial = Double.valueOf(request.getParameter("PagoInicial"));
					}
				}else{
					pagoInicial = 0;
				}
				
				ensenanza = Double.parseDouble(frmtDouble.format( ensenanza ));
				Calculo.setEnsenanza(String.valueOf(ensenanza));
				Calculo.setPorcentaje(String.valueOf(porcentaje));
				if (formaPago.equals("P")){
					
					Calculo.setPagoInicial(frmtDouble.format(pagoInicial));
					pagares 	= costoTotal-Double.valueOf(pagoInicial).doubleValue();
					pagares		= pagares /Integer.parseInt(numPagare);
					
					// Define la cantidad a pagar en el Pagare # 1
					if (Integer.parseInt(numPagare) >= 1)
						// pagare1 = getformato.format(pagares).replaceAll(",",".");
						pagare1 = getformato.format(pagares);
					else
						pagare1 = "0";
					
					// Define la cantidad a pagar en el Pagare # 2
					if (Integer.parseInt(numPagare) >= 2) 
						pagare2 = getformato.format(pagares);
					else
						pagare2 = "0";
					
					// Define la cantidad a pagar en el Pagare # 3
					if (Integer.parseInt(numPagare) >= 3) 
						pagare3 = getformato.format(pagares);
					else
						pagare3 = "0";
					
					Calculo.setPagare1(pagare1);
					Calculo.setPagare2(pagare2);
					Calculo.setPagare3(pagare3);
				}else{
					Calculo.setPagoInicial(getformato.format(costoTotal));
					Calculo.setPagare1("0");
					Calculo.setPagare2("0");
					Calculo.setPagare3("0");
				}	
								
				Calculo.setInstitucionId(Alumno.getInstitucionId());			
				
				if (Calculo.updateReg(conEnoc)){					
					conEnoc.commit();
				}else{
					conEnoc.rollback();										
				}
			}else{
				System.out.println("No hay Calculo de cobro..!!");
			}
			costoTotal = 0;
			
			break;		
		}
		case 4: { // Inscribir
						
			int rowInsert=0, folio=0;
			conEnoc.setAutoCommit(false);
			lisAsignados 		= CalculoDetU.getListAsignados(conEnoc,codigoId, periodoId,"ORDER BY ENOC.MUSI_CTATIPO(CUENTA_ID), CUENTA_ID");	

			Movimiento.setCodigoId(codigoId);
			for(int k=0; k<lisAsignados.size(); k++){
				aca.musica.MusiCalculoDet detalle = (aca.musica.MusiCalculoDet) lisAsignados.get(k);
				Movimiento.setFolio(String.valueOf(Movimiento.maxReg(conEnoc)));
				Movimiento.setCuentaId(detalle.getCuentaId());
				Movimiento.setDescripcion(periodoId+"-"+aca.musica.MusiCuenta.getCuentaNombre(conEnoc,detalle.getCuentaId()) );
				Movimiento.setFecha(aca.util.Fecha.getHoy());
				Movimiento.setImporte(detalle.getCantidad());
				Movimiento.setTipo("D");
				if (Movimiento.insertReg(conEnoc)){	
					rowInsert++;
				}
			}
			
			if(request.getParameter("CostoPagare")!=null){
				Movimiento.setFolio(String.valueOf(Movimiento.maxReg(conEnoc)));
				Movimiento.setDescripcion(periodoId+"-Costo pagar�");
				Movimiento.setFecha(aca.util.Fecha.getHoy());
				Movimiento.setImporte(request.getParameter("CostoPagare"));
				Movimiento.setTipo("D");
				Movimiento.insertReg(conEnoc);				
			}
			Movimiento.setFolio(String.valueOf(Movimiento.maxReg(conEnoc)));
			Movimiento.setDescripcion(periodoId+"-Beca");
			Movimiento.setFecha(aca.util.Fecha.getHoy());
			Movimiento.setImporte(request.getParameter("Beca"));
			Movimiento.setTipo("C");
			Movimiento.insertReg(conEnoc);
			
			if (lisAsignados.size()==rowInsert){
				Calculo.mapeaRegId(conEnoc, codigoId, periodoId);
				Calculo.setEstado("I");
				if ( Calculo.updateReg(conEnoc) ){
					conEnoc.commit();					
				}else{
					conEnoc.rollback();
				}
			}
			conEnoc.setAutoCommit(true);
			break;
		}
		case 5: { // desinscribir
			
			Calculo.mapeaRegId(conEnoc, codigoId, periodoId);
			Calculo.setEstado("C");
			if ( Calculo.updateReg(conEnoc) ){
				conEnoc.commit();
			}else{
				conEnoc.rollback();
			}			
			break;
		}
		
		case 6: { // modifica el tipo de pago
			
			Calculo.setCodigoId(codigoId);
			Calculo.setPeriodoId(periodoId);			
			if (Calculo.existeReg(conEnoc)){
				Calculo.mapeaRegId(conEnoc, codigoId, periodoId);
				Calculo.setEstado(Calculo.getEstado());
				String tipoPago	=  request.getParameter("Sobresueldo")==null?"-":request.getParameter("Sobresueldo");			
				if (aca.musica.MusiCalculo.updateSobresueldo(conEnoc, codigoId, periodoId, tipoPago)){
					Resultado = "Cambio";
					conEnoc.commit();
				}else{
					Resultado = "No Cambi�: ";
				}				
			}			
			break;			
		}
	}
	
	conEnoc.setAutoCommit(true);
	
	Pagare.setPeriodoId(periodoId);
	if (Pagare.existeReg(conEnoc)){
		Pagare.mapeaRegId(conEnoc, periodoId);
	}
	
	lisAsignados 		= CalculoDetU.getListAsignados(conEnoc,codigoId, periodoId,"ORDER BY ENOC.MUSI_CTATIPO(CUENTA_ID), CUENTA_ID");
	
	ArrayList<aca.musica.MusiInstitucion> lisInstitucion = InstitucionU.getListAll(conEnoc,"ORDER BY 1");
%>
<body>
<div class="container-fluid">
	<h1>Inscripcion <small> ( <%=codigoId%> - <%=Alumno.getNombre()+" "+Alumno.getApellidoPaterno()+" "+Alumno.getApellidoMaterno()%> - <%= fecha.getFecha("2") %>)</small></h1>
	<form name="frmPeriodo">
	<div class="alert alert-info">
		Periodo:&nbsp;
	    <select onchange='javascript:recarga()' name="PeriodoId">
<%
		for (int i=0; i< lisPeriodos.size(); i++){
			aca.musica.MusiPeriodo periodo = (aca.musica.MusiPeriodo) lisPeriodos.get(i);
%>
			<option <%if( periodoId.equals(periodo.getPeriodoId() ))out.print(" Selected ");%> value="<%= periodo.getPeriodoId() %>">[<%= periodo.getPeriodoId() %>] <%= periodo.getPeriodoNombre() %></option>
<%
	}
%>
	    </select>&nbsp;&nbsp; 
	    <a href="buscar" class="btn btn-success">Buscar Alumno</a>
	
	</div>  
	</form>
	<div class="alert alert-success">
		<b>Datos del alumno</b> &nbsp;&nbsp;
		<i class="fas fa-long-arrow-alt-right fa-1x"></i>&nbsp;&nbsp;	  
		<b>Nacimiento:</b> <%= Alumno.getFechaNac() %> &nbsp; &nbsp;
	    <b><spring:message code="aca.Edad"/>:</b> <%= Alumno.getEdad(conEnoc) %> &nbsp; &nbsp;
	    <b><spring:message code="aca.Seguro"/>:</b> <%=Alumno.getSeguro().equals("S")?"SI":"NO"%> &nbsp; &nbsp;	    
	    <b><spring:message code="aca.Tutor"/>:</b> <%= Alumno.getTutor() %> &nbsp; &nbsp;
		<b><spring:message code="aca.Tel"/>:</b> <%= Alumno.getTelefono() %> &nbsp; &nbsp; 
  	    <b><spring:message code="aca.Cel"/>: </b> <%= Alumno.getCelular() %> &nbsp; &nbsp; 
  	    <b><spring:message code="aca.Matricula"/> UM: </b> <%= Alumno.getCodigoUM() %> &nbsp; &nbsp;
  	    <b><spring:message code="aca.Institucion"/>: </b><%= aca.musica.MusiInstitucion.getNombreInstitucion( conEnoc, aca.musica.MusiAlumno.getInstitucion(conEnoc, codigoId)) %> &nbsp; &nbsp;
  	    <b><spring:message code="aca.Saldo"/>: <%= getformato.format(saldo).replaceAll(",",".") %></b>  	    
	</div>
	    
	<form name="frmCalculo" action="calculo?PeriodoId=<%= periodoId %>&Accion=3" method="post">	
  	<table id="noayuda"  class="table table-condensed">
  	  <tr><th align="center" colspan="5" style="font-size:12pt"><b>Cursos Asignados</b></th></tr>
  	  <tr>
	    <th><spring:message code="aca.Numero"/></th>
  		<th><spring:message code="aca.Tipo"/></th>
  		<th><spring:message code="aca.Cuenta"/></th>
  		<th><spring:message code="aca.Frec"/></th>
  		<th><spring:message code="aca.Costo"/> - <spring:message code="aca.Beca"/></th>
  		<th><spring:message code="aca.Total"/></th>
	  </tr>
<% 
	matricula=0; ensenanza=0; renta=0;
	int cont=0;
	double sumaBeca = 0;
	for (int z=0; z< lisAsignados.size(); z++){
		aca.musica.MusiCalculoDet caldet = (aca.musica.MusiCalculoDet) lisAsignados.get(z);
		double costoUnico = Double.parseDouble(aca.musica.MusiPeriodoCobro.getCantidad(conEnoc, periodoId,caldet.getCuentaId()));
		double costoCuenta =   costoUnico * Double.valueOf(caldet.getFrecuencia()).doubleValue();
		
		tipoCuenta = aca.musica.MusiCuenta.getTipo(conEnoc, caldet.getCuentaId());

		if(tipoCuenta.equals("0")){
			matricula += Double.valueOf(caldet.getCantidad());
		}
		else if(tipoCuenta.equals("1")){
			ensenanza += Double.valueOf(caldet.getCantidad());
		}
		else{
			renta += Double.valueOf(caldet.getCantidad());
		}
%>
	  <tr class="tr2">
  		<td align="center"><%= z+1 %></td>
  		<td>
  		<em><%= aca.musica.MusiCuenta.getTipoNombre(conEnoc, caldet.getCuentaId()) %></em></td>
  		<td>
  		    <b><%= aca.musica.MusiCuenta.getCuentaNombre(conEnoc, caldet.getCuentaId()) %> [<%= getformato.format(costoUnico).replaceAll(",",".") %>]</b>
  		    - <img src="../../imagenes/asignar_profesor.gif">  		  
  		   (<%= aca.musica.MusiMaestro.getNombre(conEnoc, caldet.getMaestro(), "NOMBRE") %>)
  		   &nbsp; <img src="../../imagenes/nota3.jpg" width="15">
  		   <b><%= aca.musica.MusiInstrumento.getNombreInstrumento(conEnoc, caldet.getInstrumentoId()).equals("null")?"":aca.musica.MusiInstrumento.getNombreInstrumento(conEnoc, caldet.getInstrumentoId()) %></b> 
  		</td>
  		<td align="center"><b><%=caldet.getFrecuencia()%></b></td>
	<%
		double becas = costoCuenta*(Double.parseDouble(caldet.getBeca()==null ? "0" : caldet.getBeca())/100);
		double total = costoCuenta - becas;
		sumaBeca+=becas;
		
		costoTotal+=total;
	%>
  		<td align="right"><b><%= getformato.format(costoCuenta) %> - <%=getformato.format(becas) %></b>&nbsp;&nbsp;</td>
  		<td align="right"><b><%= getformato.format(total) %></b></td>
	  </tr>
<%		
		cont++;
	}
	matricula 	= Double.parseDouble(frmtDouble.format(matricula).replaceAll(",","."));
	ensenanza 	= Double.parseDouble(frmtDouble.format(ensenanza).replaceAll(",","."));
%>
	  <tr class="th2">
  		<th align="center" colspan="5"><spring:message code="aca.Costo"/> del Semestre:</th>	
  		<th align="right"><b><%= getformato.format( costoTotal ) %></b></th>
	  </tr>
	</table>
<%
	boolean existeCalculo = false;
	Calculo.setCodigoId(codigoId);
	Calculo.setPeriodoId(periodoId);
	if (Calculo.existeReg(conEnoc)){
		Calculo.mapeaRegId(conEnoc,codigoId,periodoId);
		porcentaje = Integer.parseInt(Calculo.getPorcentaje());
		existeCalculo = true;
	}
%>	
	<div class="alert alert-info">
	    <select name="FormaPago" class="input input-small">
	      <option value="C" <% if(Calculo.getFormaPago().equals("C")) out.print("selected"); %>>Contado</option>
	      <option value="P" <% if(Calculo.getFormaPago().equals("P")) out.print("selected"); %>>Pagare</option>
	    </select> 
	    &nbsp; &nbsp;
	    Num.Pag.:
	    <select name="NumPagare" class="input input-small">
	      <option value="0" <% if(Calculo.getNumPagare().equals("0")) out.print("selected"); %>>0</option>
	      <option value="1" <% if(Calculo.getNumPagare().equals("1")) out.print("selected"); %>>1</option>
	      <option value="2" <% if(Calculo.getNumPagare().equals("2")) out.print("selected"); %>>2</option>
	      <option value="3" <% if(Calculo.getNumPagare().equals("3")) out.print("selected"); %>>3</option>
	    </select>
	    &nbsp; &nbsp;
	    Pag.Ini.:	      
	    <select name="HayPagoIni" class="input input-small">
	      <option value="S" <% if(Calculo.getHayPagoIni().equals("S")) out.print("selected"); %>><spring:message code='aca.Si'/></option>
	      <option value="N" <% if(Calculo.getHayPagoIni().equals("N")) out.print("selected"); %>><spring:message code='aca.No'/></option>	        
	    </select>
	    &nbsp; &nbsp;
	    # Emp. 
	    <% 
	      String empleado = Calculo.getEmpleado()==null?"*":Calculo.getEmpleado().trim();
	      if( empleado.length() == 7 ){ 
	    %>
	    <input type="text" name="Empleado" size="5" value="<%= Calculo.getEmpleado().trim() %>" maxlength="7">
	    <%}else{ %>
	    <input type="text" name="Empleado" size="5" value="<%= Alumno.getEmpleado() %>" maxlength="7">
	    <%}%>	
	    &nbsp;	       
	    Nomina <input type="radio" name="Sobresueldo" value="N" <% if (Calculo.getSobresueldo().equals("N")) out.print("Checked"); %>  <%if (existeCalculo){ %> onClick="javascript:CambiaSobresueldo('N')" <%}%> />
	    &nbsp;
	    Efectivo <input type="radio" name="Sobresueldo" value="-" <% if (Calculo.getSobresueldo().equals("-")) out.print("Checked"); %> <%if (existeCalculo){ %>onClick="javascript:CambiaSobresueldo('-')" <%}%> />	      
	</div>	
<% 
	if (Calculo.getEstado().equals("C") || Calculo.getEstado().equals("I")){
		pagoInicial = Double.valueOf(Calculo.getPagoInicial()).doubleValue();
	} 
%>	
	<table id="noayuda" width="90%" class="table table-condensed">
	  <tr><th colspan="3" align="center" style="font-size:12pt;"><b>Detalle de Pagos</b></th></tr>
	  <tr>
	  	<th class="left" width="15%">Concepto</th>    
		<th class="left" width="10%"><spring:message code='aca.Cantidad'/></th>
		<th class="left" width="75%"><spring:message code="aca.Comentario"/></th>	    
	  </tr>
	  <tr>
	    <td align="left">Matr�cula:</td>    
	    <td align="right"><input type="text" name="Matricula" size="10" style="text-align:right;" value="<%= matricula %>"></td>
	    <td align="center">&nbsp;</td>
	  </tr>
	  <tr>
	    <td align="left">Renta:</td>	    
	    <td align="right">	      
	      <input type="text" name="Renta" size="10" style="text-align:right;" value="<%= renta %>">
	    </td>
	    <td align="center">&nbsp;</td>
	  </tr>
	  <tr>
	    <td align="left">Ense�anza:</td>
	    <td align="right">
	      <input name="Ensenanza" type="text" size="10" style="text-align:right;" value="<%= ensenanza %>">
	      <input name="porEnsenanza" type="hidden"  value="<%=(ensenanza * porcentaje) / 100%>">
	    </td>
	    <td align="left">
	      <input type="text" name="Porcentaje" size="1" style="text-align:right;" value="<%=Calculo.getPorcentaje()%>">% -- (<%=frmtDouble.format(ensenanza * porcentaje/100).replaceAll(",",".")%> )
	    </td>
	  </tr>
	  <tr>
	    <td align="left">Beca:</td>
	    <td class="left">	      
		      <input name="inputBeca" type="text" size="10" style="text-align:right;" value="<%=getformato.format(sumaBeca) %>" readonly>
		</td>    
	    <td align="center">&nbsp;</td>
	  </tr>
	  <%
	  Double pag = 0.0;
	  if(Calculo.getFormaPago().equals("P")){
		  Periodo.mapeaRegId(conEnoc, periodoId);
		  pag = Double.parseDouble(Periodo.getCostoPagare()==null?"0":Periodo.getCostoPagare());
		  
		  // Costo por manejo de pagare
		  manejoPagare = Double.parseDouble(Periodo.getCostoPagare());
		  %>
		  <tr>
		    <td bgcolor="#D8D8D8" align="left"><b><spring:message code="aca.Costo"/> Pagar�:</b></td>
		    <td bgcolor="#D8D8D8" align="left">
		    	<input name="inputCostoPagare" type="text" size="10" style="text-align:right;" value="<%= Periodo.getCostoPagare() %>" readonly>		    
		    </td>  
		    <td align="right">&nbsp;</td>
		  </tr>
	  <%}else{
			manejoPagare = 0;  
	  	}%>
	  <tr class="alert alert-info">
	    <td align="left"><b>Total:</b></td>
	    <td align="left">
	    	<input name="inputTotal" type="text" size="10" style="text-align:right;" value="<%= matricula+ensenanza+renta+pag-sumaBeca %>" readonly>	    
	    </td>
	    <td align="right">&nbsp;</td>
	  </tr>
	  <tr>
	    <td align="left">Pago Inicial:</td>  
	    <td align="right"><input type="text" name="PagoInicial" size="10" style="text-align:right;" value="<%= pagoInicial %>"></td>
	    <td align="right">( Mat. + %Enza. - Beca )</td>
	  </tr>
<% 
	if (existeCalculo ){	
		if ( (Calculo.getEstado().equals("C")||Calculo.getEstado().equals("I")) && Calculo.getFormaPago().equals("P")){
			int numPagares = 0;
			if ( Integer.parseInt(Calculo.getNumPagare()==null?"0":Calculo.getNumPagare())>0 )numPagares++;
			if ( Integer.parseInt(Calculo.getNumPagare()==null?"0":Calculo.getNumPagare())>1 )numPagares++;
			if ( Integer.parseInt(Calculo.getNumPagare()==null?"0":Calculo.getNumPagare())>2 )numPagares++;
			
			double costoPagare = Double.parseDouble(Periodo.getCostoPagare())/numPagares;

			if ( Integer.parseInt(Calculo.getNumPagare()==null?"0":Calculo.getNumPagare())>0 ){ %>
	  <tr>
	    <td align="left">Primer Pagar�:</td> 
	    <td align="right"><input type="text" name="Pagare1" size="10" style="text-align:right;" value="<%= Calculo.getPagare1()==null?costoPagare:getformato.format(Double.parseDouble(Calculo.getPagare1())+costoPagare).replaceAll(",",".") %>"></td>
	    <td align="left">(&nbsp; Vence: <%= Pagare.getPagare1()==null?"-":Pagare.getPagare1() %> )</td>
	  </tr>
<% 			} 
			if ( Integer.parseInt(Calculo.getNumPagare()==null?"0":Calculo.getNumPagare())>1 ){ 
%>	  
	  <tr>
	    <td align="left">Segundo Pagar�:</td>	    
	    <td align="right"><input type="text" name="Pagare2" size="10" style="text-align:right;" value="<%= Calculo.getPagare2()==null?costoPagare:getformato.format(Double.parseDouble(Calculo.getPagare2())+costoPagare).replaceAll(",",".") %>"></td>
	    <td align="left">(&nbsp; Vence: <%= Pagare.getPagare2()==null?"-":Pagare.getPagare2() %> )</td>
	  </tr>
<% 			}
			if ( Integer.parseInt(Calculo.getNumPagare()==null?"0":Calculo.getNumPagare())>2 ){ %>
	  <tr>
	    <td align="left">Tercer Pagar�:</td>	   
	    <td align="right"><input type="text" name="Pagare3" size="10" style="text-align:right;" value="<%= Calculo.getPagare3()==null?costoPagare:getformato.format(Double.parseDouble(Calculo.getPagare3())+costoPagare).replaceAll(",",".") %>"></td>
	    <td align="left">(&nbsp; Vence: <%= Pagare.getPagare3()==null?"-":Pagare.getPagare3() %> )</td>
	  </tr>
<%			}
		} %>  
	  <tr>
	    <td style="text-align:left;" colspan="4">
	    <% if (Calculo.getEstado().equals("A")||Calculo.getEstado().equals("C")){ %>
	  	  <input class="btn btn-primary" type="submit" name="Calcular" value="Calcular">
<% 			if (Calculo.getEstado().equals("C")){%>	  	  
	  	  <input class="btn btn-primary" type="button" onclick="javascript:inscribir('<%= codigoId %>','<%= periodoId %>','<%=manejoPagare%>','<%=sumaBeca%>')" name="Inscribir" value="Inscribir">
<% 			}%>	  	  
<% 		}else if (Calculo.getEstado().equals("I")){%>
		  <input class="btn btn-primary" type="button" onclick="javascript:desinscribir('<%= codigoId %>','<%= periodoId %>')" name="Desinscribir" value="Desinscribir">
<% 		}%>	  
		 <input class="btn btn-primary" type="button" onclick="javascript:formato('<%= periodoId %>')" name="Formato" value="Formato">
	    </td>
	  </tr>
<% 	}%>	    
	</table>
	</form>
</div>
</body>	
<%@ include file= "../../cierra_enoc.jsp" %>