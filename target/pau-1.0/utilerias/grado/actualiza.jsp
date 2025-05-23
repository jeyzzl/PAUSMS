<%@ include file= "../../con_enoc.jsp" %>
<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../body.jsp" %>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="CargaU" scope="page" class="aca.carga.CargaUtil"/>

<script type="text/javascript">
	function Carga(){
		document.frmActualiza.Accion.value="2";
		document.frmActualiza.submit();
	}
</script>
<%	
	String codigoPersonal	= (String) session.getAttribute("codigoEmpleado");
	String cargaId 			= (String) session.getAttribute("cargaId");
	String accion 			= request.getParameter("Accion")==null?"1":request.getParameter("Accion");
	int nAccion				= Integer.parseInt(accion);
		
	String sResultado		= "";	
	int i=0;
	// Fecha y Hora
	java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
	java.util.Date date 			= new java.util.Date();
	 
	ArrayList lisCarga		= CargaU.getListPeriodoActual(conEnoc,"ORDER BY CARGA_ID");
	
	// Operaciones a realizar en la pantalla
	switch (nAccion){
		case 1: { // Lista de Cursos
			sResultado = "Bienvenido Portal ..¡¡";			
			break;
		}		
		case 2: { // Cerrar
			cargaId = request.getParameter("CargaId");
			session.setAttribute("cargaId", cargaId );		
			break;
		}		
	}
	
%>
<div class="container-fluid">
	<h1>Actualiza Grado y Ciclo</h1>
	<form action="actualiza" method="post" name="frmActualiza">
	<input type="hidden" name="Accion">
	<div class="alert alert-info">	
		Carga: 
		<select name="CargaId" id="CargaId" onChange="Carga()" class="input input-xlarge">
<%					
		// Buscando la carga					
		for( i=0;i<lisCarga.size();i++){
			aca.carga.Carga carga = (aca.carga.Carga) lisCarga.get(i);
			if	 	(	 lisCarga.size()==1 ){	cargaId = carga.getCargaId(); 	}						
			if (carga.getCargaId().equals(cargaId)){
			out.print(" <option value='"+carga.getCargaId()+"' Selected>"+ carga.getNombreCarga()+"</option>");
			}else{
			out.print(" <option value='"+carga.getCargaId()+"'>"+ carga.getNombreCarga()+"</option>");
		}				
	}					
 %>
      	</select>   	
	</div>		
	</form>
<%
	// Actualizar los grados y ciclos
	boolean updateCiclo = false;
	boolean updateCicloPostgrado = false;
	
	if ( aca.alumno.PlanUtil.actualizaCiclo(conEnoc,"'"+cargaId+"'") ){
		updateCiclo = true;
		System.out.println("Actualize el ciclo Nivel 1,2,5");
	}
	
	if ( aca.alumno.PlanUtil.actualizaCicloPostgrado(conEnoc,"'"+cargaId+"'") ){
		updateCicloPostgrado = true;
		System.out.println("Actualize el ciclo Postgrado");
	}
	
	if (updateCiclo || updateCicloPostgrado){
		if (aca.alumno.PlanUtil.actualizaGrado(conEnoc,"'"+cargaId+"'")){  
			System.out.println("Actualize el grado");
		}else{
			conEnoc.rollback();
			System.out.println("No actualizó el grado");
		}
	}	
%>
	<table style="width:60%" >
		<td style="text-align:center">&nbsp;<b>¡Datos Actualizados!</b></td>
	   	<br><br>
	   	<td style="text-align:center"><b>Carga:</b><%=cargaId%> &nbsp; <b>Fecha y Hora:</b> <%=dateFormat.format(date)%></td>					  
	</table>

</div>
<%@ include file= "../../cierra_enoc.jsp" %>