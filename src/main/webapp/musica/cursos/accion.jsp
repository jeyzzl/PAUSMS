<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="Musi" scope="page" class="aca.musica.MusiCalculoDet"/>
<jsp:useBean id="MaestroU" scope="page" class="aca.musica.MusiMaestroUtil"/>
<jsp:useBean id="InstrumentoU" scope="page" class="aca.musica.MusiInstrumentoUtil"/>

<% 
	String codigoId			= (String) session.getAttribute("CodigoId");

	int accion				= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	String periodoId		= request.getParameter("PeriodoId");
	String cuentaId			= request.getParameter("CuentaId");
	String maestroId 		= request.getParameter("Maestro");
	String instrumentoId 	= request.getParameter("Instrumento");
	
//System.out.println("Datos:"+maestroId+":"+cuentaId+":"+periodoId);

	Musi.mapeaRegId(conEnoc, codigoId, periodoId, cuentaId);
	String cantidad 		= Musi.getCantidad();
	 
	ArrayList lisMaestro 		=  MaestroU.getListAll(conEnoc,"ORDER BY APELLIDO_PATERNO,APELLIDO_MATERNO,NOMBRE");
	ArrayList lisInstrumento 	=  InstrumentoU.getListAll(conEnoc,"ORDER BY INSTRUMENTO_NOMBRE");

     
    String Resultado      	= "";     
     
    //operaciones a realizar 
	switch (accion){
	 
		case 0: { // Consulta
	    	Resultado = "Consulta..¡¡";			
	    	Musi.mapeaRegId(conEnoc,codigoId, periodoId, cuentaId);
			break;
	    }
	       
		case 2: { // Grabar y modificar
	    	Musi.setCodigoId(codigoId);
	    	Musi.setPeriodoId(periodoId);
	    	Musi.setCuentaId(cuentaId);
	    	Musi.setCantidad(cantidad);
	    	Musi.setMaestro(request.getParameter("Maestro"));
	    	Musi.setInstrumentoId(request.getParameter("Instrumento"));	    	
	    	if (Musi.existeReg(conEnoc) == true){
				if (Musi.updateReg(conEnoc)){ 
					Resultado = "¡ Maestro Asignado ¡";					
				}else{
					Resultado = "No Cambió";
				}
			}
			break;	
			
		}	
		
	 }
%>
<body>
<div class="container-fluid">
	<h2>Asignar Maestro/instrumento<small> ( <%= aca.musica.MusiAlumno.getNombre(conEnoc,codigoId,"NOMBRE") %> - <%= aca.musica.MusiCuenta.getCuentaNombre(conEnoc,cuentaId) %> ) </small></h2>
	<div class="alert alert-info">
		<a class="btn btn-primary" href="cursos"><spring:message code="aca.Regresar"/></a>
		&nbsp;&nbsp;
		<%= Resultado %>
	</div>
	<form name="forma" method="post" action="accion?CodigoId=<%=codigoId %>&PeriodoId=<%=periodoId %>&CuentaId=<%=cuentaId %>&Accion=2">  
  	<table class="table table-condensed">            
    <tr> 
      <td width="15%"><strong>Maestro:</strong></td>
      <td width="76%">			
		<select name="Maestro">
<%
	for (int i=0; i< lisMaestro.size(); i++){
		aca.musica.MusiMaestro maestro = (aca.musica.MusiMaestro) lisMaestro.get(i);
%>
		  <option <%if( maestroId.equals( maestro.getMaestroId() )) out.print(" Selected ");%> value="<%= maestro.getMaestroId() %>"> <%= maestro.getNombre()+" "+maestro.getApellidoPaterno()+" "+maestro.getApellidoMaterno() %></option>
<%	} %>
		</select>
	  </td>			
    </tr>
    <tr> 
      <td width="15%"><strong>Instrumento:</strong></td>
      <td width="76%">			
		<select name="Instrumento">
<%
	for (int i=0; i< lisInstrumento.size(); i++){
		aca.musica.MusiInstrumento instrumento = (aca.musica.MusiInstrumento) lisInstrumento.get(i);
%>
		  <option <%if( instrumentoId.equals( instrumento.getInstrumentoId() )) out.print(" Selected ");%> value="<%= instrumento.getInstrumentoId() %>"> <%= instrumento.getInstrumentoNombre() %></option>
<%	} %>
		</select>
	  </td>			
    </tr>    
 	</table>
 	<div class="alert alert-info"><input class="btn btn-primary" type="submit" name="Guardar" value="Guardar"/></div>
	</form>
</div>	
<%@ include file= "../../cierra_enoc.jsp" %>