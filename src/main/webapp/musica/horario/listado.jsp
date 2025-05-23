<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<%@page import="aca.musica.MusiHorarioUtil"%>
<%@page import="aca.musica.MusiHorario"%>
<%@page import="aca.musica.MusiMaestro" %>
<%@page import="aca.musica.MusiMaestroUtil" %>

<jsp:useBean id="Horario" scope="page" class="aca.musica.MusiHorario"/>
<jsp:useBean id="HorarioU" scope="page" class="aca.musica.MusiHorarioUtil"/>
<jsp:useBean id="SalonU" scope="page" class="aca.musica.MusiSalonUtil"/>
<jsp:useBean id="MaestroU" scope="page" class="aca.musica.MusiMaestroUtil"/>
<jsp:useBean id="Maestro" scope="page" class="aca.musica.MusiMaestro"/>

<head>
  <script type="text/javascript">	
	
	function borrar(){
		if(document.fmrHorario.HorarioId.value!=""){
			if(confirm("Estas seguro de eliminar el registro!")==true){
	  			document.fmrHorario.Accion.value="3";
				document.fmrHorario.submit();
			}			
		}else{
			alert("Escriba la Clave !");
			document.fmrHorario.MaestroId.focus(); 
	  	}
	}		
</script>
  
</head>
<% 
	String periodoId 		= request.getParameter("PeriodoId");
	String maestroId 		= request.getParameter("MaestroId");
	String accion 			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");		
	int numAccion			= Integer.parseInt(accion);	
    String sResultado       = "";
    int cont1 = 0;
    
    
	if ( numAccion==1){	
		Horario.setHorarioId(Horario.maximoReg(conEnoc, periodoId));
		
	}else{
		Horario.setHorarioId(request.getParameter("HorarioId"));
		
	}
	
	ArrayList lisHorario = null;
	
//operaciones a realizar	
    switch (numAccion){

	    case 1: { // Nuevo			
	    	sResultado = "Llene el formulario correctamente ..¡¡";
				break;
		}		
		
		case 2: { // Grabar
			int hI = Integer.parseInt(request.getParameter("HoraInicio"));
			int hF = Integer.parseInt(request.getParameter("HoraFinal"));
			int mI = Integer.parseInt(request.getParameter("MinInicio"));
			int mF = Integer.parseInt(request.getParameter("MinFinal"));
			
			Horario.setHorarioId(request.getParameter("HorarioId"));
			Horario.setMaestroId(request.getParameter("MaestroId"));
			Horario.setHoraInicio(request.getParameter("HoraInicio"));
			Horario.setHoraFinal(request.getParameter("HoraFinal"));
			Horario.setMinInicio(request.getParameter("MinInicio"));
			Horario.setMinFinal(request.getParameter("MinFinal"));
			Horario.setCupo(request.getParameter("Cupo"));
			Horario.setSalonId(request.getParameter("SalonId"));
			Horario.setDia(request.getParameter("Dia"));
			Horario.setEstado("S");
			
			
			if (Horario.existeReg(conEnoc) == false){
				
				if(hI <= hF){					
					if(hI == hF && mI < mF || hI < hF){
						if (Horario.insertReg(conEnoc)){
							sResultado = "Grabado: "+Horario.getHorarioId();							
						}else{
							sResultado = "No Grabó: "+Horario.getHorarioId();
						}
					}else{
						sResultado = "Horario Incorrecto";						
					}					
				}				
			}else{				
				if (Horario.updateReg(conEnoc)){
					sResultado = "Modificado: "+Horario.getHorarioId();					
				}else{
					sResultado = "No Cambió: "+Horario.getHorarioId();
				}
			}
			
			break;			
		}
		case 3:{//borrar
			
			if (Horario.existeReg(conEnoc) == true){
				if (Horario.deleteReg(conEnoc)){
					sResultado = "Borrado: "+Horario.getHorarioId();					
				}else{
					sResultado = "No Borró: "+Horario.getHorarioId();
				}	
			}else{
					sResultado = "No existe: "+Horario.getHorarioId();
			}
			break;
		}
		
		case 4:{ //Consultar
			
			if (Horario.existeReg(conEnoc) == true){
				Horario.mapeaRegId(conEnoc,Horario.getHorarioId());
			}else{
				sResultado = "No existe: "+Horario.getHorarioId();
			}
			break;
		}
		
						
			
	}  
	lisHorario = HorarioU.getListAll(conEnoc, "ORDER BY HORARIO_ID");
	ArrayList<aca.musica.MusiSalon> lisSalon	= SalonU.getListAll(conEnoc,"SALON_ID");
	ArrayList<MusiHorario> lisMaestro	= HorarioU.getListMaestro(conEnoc, maestroId, "ORDER BY DIA");
%>
<body>
<div class="container-fluid">
	<h2>Datos Horario</h2>
	<form action="accion?PeriodoId=<%= periodoId %>&MaestroId=<%= maestroId %>" method="post" name="fmrHorario" target="_self">
	<input type="hidden" name="Accion">
	<div class="alert alert-info">	
		<a class="btn btn-primary" href="horario"><spring:message code="aca.Regresar"/></a> 
	</div>
	<table style="width:50%" class="table">
  	<tr><td align="center" colspan="4" style="font-size:10pt"><b>Maestro: <%= Maestro.getNombre(conEnoc, maestroId, "ORDER BY NOMBRE")%></b></td></tr>
  	<tr>
    	<th width="5%"><spring:message code="aca.Operacion"/></th>
    	<th width="8%">Hora</th>
    	<th width="8%">Cupo</th>
    	<th width="32%"><spring:message code="aca.Alumno"/></th>
     
  	</tr>
<%  for(int i=0; i<lisMaestro.size(); i++){
		aca.musica.MusiHorario maestro = (aca.musica.MusiHorario) lisMaestro.get(i);
		int cont = Integer.parseInt(maestro.getDia());
		
		if(cont!= cont1){
			cont1 = cont;
			%><tr>
			<td align="center" colspan = "4"><font size="3"><b><%= maestro.getNombreDia(maestro.getDia()) %></b></font></td>
			</tr>
			<%
		}
%>
	<tr>
    	<td align="center"> <a href="accion?Accion=3&HorarioId=<%= maestro.getHorarioId()%>&MaestroId=<%=maestro.getMaestroId()%>&PeriodoId=<%= periodoId%>"> <img src="../../imagenes/no.png" alt="Eliminar" > 
      	</a> </td>    
    	<td align="center"><%= maestro.getHoraInicio()+":"+maestro.getMinInicio()+ "  a  "+maestro.getHoraFinal()+":"+maestro.getMinFinal()%></td>
    	<td align="center"><%= maestro.getCupo()%></td>
  	</tr>   
<% }%> 
	</table>
	</form>
</div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>