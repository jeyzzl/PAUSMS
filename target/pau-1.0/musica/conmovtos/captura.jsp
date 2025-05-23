<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@ page import= "aca.musica.MusiCuenta"%>
<%@ page import= "aca.musica.MusiCuentaUtil"%>

<jsp:useBean id="MovtosU" scope="page" class="aca.musica.MusiMovimientoUtil"/>
<jsp:useBean id="Movimiento" scope="page" class="aca.musica.MusiMovimiento"/>

<head>
<script type="text/javascript" src="../js/prototype-1.6.js"></script>
<script type="text/javascript">
	function Nuevo(){
		document.frmGraba.Accion.value ="1";
		document.frmGraba.submit();
	}
	
	function Graba(){
		document.frmGraba.Accion.value ="2";
		document.frmGraba.submit();
	}
	
	function Borrar( CodigoId, Folio ){
		if(confirm("Estas seguro de eliminar el registro: "+CodigoId+"- "+Folio)==true){
	  		document.location="captura?Accion=4&CodigoId="+CodigoId+"&Folio="+Folio;
	  	}
	}
</script>
</head>
<%	
	java.text.DecimalFormat getformato= new java.text.DecimalFormat("###,##0.00;-###,##0.00");
	
	String codigoId			= (String) session.getAttribute("CodigoId");
	String codigo 			= request.getParameter("Codigo");
	if (codigo!=null){
		codigoId = codigo;
		session.setAttribute("CodigoId",codigoId);
	}
	
	String strOpcion 		= request.getParameter("Opcion")==null?"1":request.getParameter("Opcion");
	int numAccion 			= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	double cargo			= 0;
	double credito			= 0; 
	double saldo 			= 0;
	double saldoAnt			= 0;
	String strBgcolor		= "";	
	boolean modificar		= false;
	String importe 			= "0";
	String strYear			= aca.util.Fecha.getHoy().substring(6).trim();
	String resultado 		= "";	
	
	ArrayList<aca.musica.MusiCuenta> lisCuenta 			= new ArrayList<aca.musica.MusiCuenta>();	
	MusiCuentaUtil cuentaU			= new MusiCuentaUtil();
	lisCuenta						= cuentaU.getListAll(conEnoc,"ORDER BY TIPO, CUENTA_NOMBRE");
	
	switch (numAccion){
		case 1: { // Nuevo
			if (request.getParameter("Folio")!=null){
				modificar = true;
			}
			break;
		}		
		case 2: { // Grabar
			Movimiento.setCodigoId(codigoId);
			Movimiento.setFolio(request.getParameter("Folio"));						
			Movimiento.setFecha(request.getParameter("Fecha"));
			Movimiento.setCuentaId(request.getParameter("CuentaId"));
			Movimiento.setDescripcion(request.getParameter("Descripcion"));
			Movimiento.setReferencia(request.getParameter("Referencia"));
			
			importe = request.getParameter("Importe");
			if (importe.equals("") || importe.equals("null") || importe==null) importe = "0";	
			Movimiento.setImporte(importe);
			
			if (request.getParameter("Tipo").equals("C")){
				Movimiento.setTipo("C");
			}else{				
				Movimiento.setTipo("D");
			}			
			
			if (Movimiento.existeReg(conEnoc) == false){
				Movimiento.setFolio(String.valueOf(Movimiento.maxReg(conEnoc)));				
				if (Movimiento.insertReg(conEnoc)){
					resultado = "Grabado: "+Movimiento.getFolio();					
				}else{
					resultado = "No Grabó: "+Movimiento.getFolio();
				}
			}else{
				if (Movimiento.updateReg(conEnoc)){
					resultado = "Modificado: "+Movimiento.getFolio()+"-"+Movimiento.getDescripcion();					
				}else{
					resultado = "No Modificó : "+Movimiento.getFolio()+"-"+Movimiento.getDescripcion();
				}
			}
			
			break;
		}
		
		case 4: { // Borrar
			Movimiento.setCodigoId(codigoId);
			Movimiento.setFolio(request.getParameter("Folio"));
			
			if (Movimiento.existeReg(conEnoc)){
				if (Movimiento.deleteReg(conEnoc)){
					resultado = "Borrado: "+Movimiento.getFolio();					
				}else{
					resultado = "No Grabó: "+Movimiento.getFolio();
				}
			}			
			break;
		}
	}
	
	ArrayList<aca.musica.MusiMovimiento> lisMovtos		= new ArrayList<aca.musica.MusiMovimiento>();
	if (strOpcion.equals("0")){
		lisMovtos				= MovtosU.getMovimientosAlumno(conEnoc, codigoId, "ORDER BY TO_CHAR(ENOC.MUSI_MOVIMIENTO.FECHA,'YYYY/MM/DD'),FOLIO");
	}else{
		lisMovtos				= MovtosU.getListMovAlumYear(conEnoc, codigoId, strYear, "ORDER BY TO_CHAR(ENOC.MUSI_MOVIMIENTO.FECHA,'YYYY/MM/DD'),FOLIO");
	}	
%>
<body>
<div class="container-fluid">
	<h2>Estado de Cuenta<small> ( <%= codigoId %>] - <%= aca.musica.MusiAlumno.getNombre(conEnoc,codigoId,"NOMBRE")%> ) </small></h2>
	<form name="frmGraba" method="POST" action="captura">
	<input type='hidden' name='Accion'/>
	<div class="alert alert-info">
	  <a class="btn btn-success" href="buscar">Buscar Alumno</a>&nbsp;&nbsp;	
<%	if (strOpcion.equals("1")){ %>    
      <a class="btn btn-primary" href="captura?Opcion=0"><font size="2">Mostrar Todos los años</font></a>
<%	}else{ %>
	  <a class="btn btn-primary" href="captura?Opcion=1"><font size="2">Mostrar año <%= strYear %></font></a>	
<%	} %>      
    
	</div>
	<table class="table table-condensed">  
  	<tr>
		<th width="3%"><spring:message code="aca.Operacion"/></th>
		<th width="4%"><spring:message code="aca.Numero"/></th>
		<th width="10%"><spring:message code="aca.Fecha"/></th>
		<th width="10%">Referencia</th>
		<th width="20%"><spring:message code="aca.Cuenta"/></th>
		<th width="35%"><spring:message code='aca.Descripcion'/></th>
		<th width="7%">Cargo</th>
		<th width="7%">Credito</th>
		<th width="7%"><spring:message code="aca.Saldo"/></th>
  	</tr>
<%
	if (strOpcion.equals("1")){
		saldoAnt = Double.valueOf(aca.musica.MusiMovimientoUtil.getSaldoAnterior(conEnoc, codigoId, strYear));
		saldo = saldoAnt;
%>
	<tr class="th2">
		<td align="center">&nbsp;</td>
	    <td align="center">0</td>
		<td align="center">---</td>
		<td>&nbsp;</td>
		<td>&nbsp;</td>
		<td><b> S a l d o &nbsp; &nbsp; A n t e r i o r</b></td>	    
		<td align="center"><b><% if (saldoAnt<0) out.println(getformato.format( saldoAnt )); else out.println(""); %></b></td>
		<td align="center"><b><% if ( saldoAnt>=0) out.println(getformato.format( saldoAnt )); else out.println(""); %></b></td>
		<td align="center"><b><%= getformato.format(saldo)%></b></td>
	</tr>  	
<%		
	}
	for (int i=0; i< lisMovtos.size(); i++){
		aca.musica.MusiMovimiento Movtos = (aca.musica.MusiMovimiento) lisMovtos.get(i);
		
		if (Movtos.getTipo().equals("D")){
			cargo = Double.valueOf(Movtos.getImporte()).doubleValue();
			saldo = saldo - cargo;
			credito = 0;
		}else{
			credito = Double.valueOf(Movtos.getImporte()).doubleValue();
			saldo = saldo + credito;
			cargo = 0;
		}
		if (modificar && Movtos.getFolio().equals(request.getParameter("Folio"))){
			strBgcolor = "bgcolor='black'";
		}else{
			if(i%2 == 0) strBgcolor = "bgcolor='#CCCCCC'"; else strBgcolor = "";
		}		
%>
  <tr <%= strBgcolor %> >
    <td align="center">
  	  -
	</td>
    <td align="center">	  
	 	<%=i+1%>
	</td>
	<td align="center"><%= Movtos.getFecha() %></td>
	<td><%= Movtos.getReferencia()%></td>
	<td><%= aca.musica.MusiCuenta.getCuentaNombre(conEnoc, Movtos.getCuentaId()) %></td>
	<td><%= Movtos.getDescripcion() %></td>	    
	<td align="center"><% if (Movtos.getTipo().equals("D")) out.println(getformato.format( cargo )); else out.println(""); %></td>
	<td align="center"><% if (Movtos.getTipo().equals("C")) out.println(getformato.format( credito )); else out.println(""); %></td>
	<td align="center"><%= getformato.format(saldo)%></td>	  
  </tr>
<%  		
	}
	if (numAccion==1){
		String fecha 		= "";
		String folio 		= "";		
		String cuentaId		= "";
		String descripcion	= "";
		importe				= "";
		String tipo 		= "";
		String referencia   = "";
		
		if (modificar){
			Movimiento.mapeaRegId(conEnoc,codigoId,request.getParameter("Folio"));
			fecha 			= Movimiento.getFecha();
			folio 			= Movimiento.getFolio();			
			cuentaId		= Movimiento.getCuentaId();
			descripcion 	= Movimiento.getDescripcion();
			importe	 		= Movimiento.getImporte();
			tipo 			= Movimiento.getTipo();
			referencia 		= Movimiento.getReferencia();
			
		}else{			
			folio	= String.valueOf(aca.musica.MusiMovimiento.maxReg(conEnoc,codigoId));
			fecha	= aca.util.Fecha.getHoy();			
		}
%>
	
  <tr bgcolor="#b5e1f8">
    <td align="center"><a href="captura?Accion=0">Cancelar</a></td>
    <td align="center">
      <input type="Text" class="text" name="Folio" size="3" maxlength="3" value="<%= folio %>">
    </td>
	<td align="center">
	  <input type="Text" class="text" name="Fecha" size="10" maxlength="10" value="<%= fecha %>">
	</td>
	  <td>
	  <input type="Text" class="text" name="Referencia" size="20" maxlength="20" value="<%=referencia%>">
	  <script> document.frmGraba.Referencia.focus(); </script>
	</td>
	<td>
	  <select name="CuentaId">
<%		tipo = "";	
		String color	= "";
	
		for (int j=0; j< lisCuenta.size(); j++){
			aca.musica.MusiCuenta cuenta = (aca.musica.MusiCuenta) lisCuenta.get(j);
			if ( !tipo.equals(cuenta.getTipo()) ){
				if (color.equals("#CCCCCC")) color = "white"; else color = "#CCCCCC";
				tipo = cuenta.getTipo();
			}
%>
		<option style="background-color:<%=color%>;" value="<%= cuenta.getCuentaId() %>" <% if (cuenta.getCuentaId().equals(Movimiento.getCuentaId())) out.print(" Selected ");%>>
		  <%= cuenta.getCuentaNombre() %>
		</option>
<%
		}
%>
	  </select>	    
	</td>
	<td>
	  <input type="Text" class="text" name="Descripcion" size="30" maxlength="70" value="<%=descripcion%>">
	</td>
	<td align="center">
	  <input type="Text" class="text" name="Importe" size="8" maxlength="7" value="<%=importe%>">
	</td>
	<td align="center">
	  <select name="Tipo">
	    <option <% if ( Movimiento.getTipo().equals("C") ) out.print(" Selected ");%> value="C">Credito</option>
	    <option <% if ( Movimiento.getTipo().equals("D") ) out.print(" Selected ");%> value="D">Cargo</option>
	  </select>  
	</td>
	<td align="center">
	  <input type="submit" class="button" name="Grabar" onclick="javascript:Graba()" value="Grabar">
	</td>
  </tr>  	
<%
	}else{
%>
  <tr><td align="left" colspan="10">-</td></tr>
<%		
	}
%>	
</table> 
</form>
</body>
<%
	lisMovtos 	= null;
%>
<!-- fin de estructura -->
<%@ include file= "../../cierra_enoc.jsp" %>