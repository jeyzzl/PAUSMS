<%@ include file= "../../con_enoc.jsp"%>
<%@ include file= "id.jsp" %>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file= "../../seguro.jsp" %>

<jsp:useBean id="Titulo" scope="page" class="aca.kardex.KrdxAlumnoTitulo"/>
<jsp:useBean id="Alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="CursoAct" scope="page" class="aca.kardex.KrdxCursoAct"/>
<jsp:useBean id="Curso" scope="page" class="aca.plan.MapaCurso"/>
<jsp:useBean id="MapaCursoU" scope="page" class="aca.plan.CursoUtil"/>
<% 
	String codigoPersonal 			= (String) session.getAttribute("codigoPersonal");
	boolean esAdmin			 		= Boolean.parseBoolean(session.getAttribute("admin")+"");
	int numAccion 					= request.getParameter("Accion")==null?0:Integer.parseInt(request.getParameter("Accion"));
	
	String codigoAlumno 			= (String) session.getAttribute("codigoAlumno");
	String cursoCargaId 			= request.getParameter("CursoCargaId");
	String cursoId 					= request.getParameter("CursoId");	
	String planId 					= aca.plan.CursoUtil.getPlanId(conEnoc,cursoId);
	String institucion 				= (String)session.getAttribute("institucion");

	Titulo.setCodigoPersonal(codigoAlumno);
	Titulo.setCursoCargaId(cursoCargaId);
	if (Titulo.existeReg(conEnoc)){
		Titulo.mapeaRegId(conEnoc,codigoAlumno,cursoCargaId);
		Titulo.getPresidente();		
	}
	
	Alumno = AlumUtil.mapeaRegId(conEnoc, codigoAlumno);	
	String s_fecha 					= aca.util.Fecha.getHoy();
	String mensaje					= "";
	
	if (numAccion == 1){
		Curso = MapaCursoU.mapeaRegId(conEnoc,cursoId);
		if ( Integer.parseInt(Titulo.getNota()) >= Integer.parseInt(Curso.getNotaAprobatoria()) ){
			CursoAct.setTipoCalId("1");
		}else{
			CursoAct.setTipoCalId("2");
		}
		CursoAct.setCodigoPersonal(codigoAlumno);
		CursoAct.setCursoCargaId(cursoCargaId);
		CursoAct.setCursoId(cursoId);
		CursoAct.setTitulo("S");
		CursoAct.setFTitulo(aca.util.Fecha.getHoy());
		CursoAct.setNota(Titulo.getNota());
		//System.out.println("Datos:"+CursoAct.getCodigoPersonal()+":"+CursoAct.getCursoCargaId()+":"+CursoAct.getCursoId()+":"+CursoAct.getTipoCalId()+":"+CursoAct.getNota());
		if (CursoAct.updateTitulo(conEnoc)){			
			mensaje = "¡ La nota fue traspasada !";
		}else{
			mensaje = "¡ Error al modificar la nota de titulo suficiencia !";
		}
	}
%>
<head>
  <style type="text/css">

	.etiqueta{
		font-size:9pt;		
	}	
  </style>
</head>
<head>
  <script type="text/javascript">
	function confirmar( CursoCargaId, CursoId ){
		alert("Hola...");
		if(confirm("Ésta operación registra la solicitud de Titulo Suficiencia en el cardex del alumno. \n ¿Estás de acuerdo?" )==true){
	  		document.location.href = "formato?CursoCargaId="+CursoCargaId+"&CursoId="+CursoId+"&Accion=1";
	  	}
	}	 
  </script>	   
</head>  
<head>
	<script type="text/javascript" language="JavaScript1.2">
		mes	= <%=Integer.parseInt(s_fecha.substring(3,5))%>;
		switch(mes)
        {
          case 1:
                  f_mes = "Enero";
                  break;
          case 2:
                  f_mes = "Febrero";
                  break;
          case 3:
                  f_mes = "Marzo";
                  break;
          case 4:
                  f_mes = "Abril";
                  break;
          case 5:
                  f_mes = "Mayo";
                  break;
          case 6:
                  f_mes = "Junio";
                  break;
          case 7:
                  f_mes = "Julio";
                  break;
          case 8:
                  f_mes = "Agosto";
                  break;
          case 9:
                  f_mes = "Septiembre";
                  break;
          case 10:
                  f_mes = "Octubre";
                  break;
          case 11:
                  f_mes = "Noviembre";
                  break;
          case 12:
                  f_mes = "Diciembre";
                  break;
		}

	</script>
</head>
<body>
<table border="3" align="center" class="tabla"> 
  <tr>
	<td>
	  <div style="margin:40px 10px 40px 30px"> 
      <table style="width:90%" align="center"  cellpadding="5">
	   	<tr>
	      <td>
	        <a href="javascript:window.print()"><img src="../../imagenes/logo_small.jpg"  alt="Logo" align="left" alt="Imprimir"></a>
	        <div style="margin:40px 10px 40px 10px"><font size="3"><b><%=institucion.toUpperCase()%></b></font>
	        <br><font size="2"><b>Acta de Examen a Título de Suficiencia</b></font></div>
	      </td> 
	    </tr>
		  <table style="margin: 0 auto;  width:90%"  cellpadding="3">   
		  <tr>
			<td class="etiqueta"><b>Carrera:</b>&nbsp;<u><%=aca.catalogo.CatCarreraUtil.getNombreCarrera(conEnoc, Titulo.getCarreraId()) %></u><td>
		  </tr>
		  <tr>
	      	<td class="etiqueta"><b><spring:message code="aca.Nombre"/> de la Materia:</b>&nbsp;<u><%= aca.plan.CursoUtil.getMateria(conEnoc,cursoId)  %></u></td>
	      </tr> 
	      <br>
		  <tr> 
			<td class="etiqueta">En la Ciudad de Montemorelos, Nuevo Leon, Mexico, siendo el d&iacute;a&nbsp;<u><%=s_fecha.substring(0,2)%></u> del mes de&nbsp;<u><script>document.write(f_mes)</script></u> 
			                 del año&nbsp;<u><%=s_fecha.substring(6,10)%></u>. Se levantó la presente acta en donde se consignan la(s) calificacion (es) obtenidas por:<br></td>
		  </tr>
		  <br>
		  <tr>
			<td class="etiqueta"><b>Estudiante:</b>&nbsp;<u><%= Alumno.getNombre()+" " + Alumno.getApellidoPaterno()+" "+Alumno.getApellidoMaterno() %></u></td>
		  </tr>
		  <tr>
			<td class="etiqueta"><b>No. de Matricula:</b>&nbsp;<u><%= codigoAlumno %></u></td>
		  </tr>
		  <tr>
			<td class="etiqueta"><b>Grado:</b>&nbsp;<u><%= aca.alumno.PlanUtil.getGrado(conEnoc,codigoAlumno,planId) %></u></td>
		  </tr>
		  <br>
		  <tr>
			<td align="center"><b>I N T E G R A N T E S &nbsp; D E L &nbsp; J U R A D O</b></td>
		  </tr>
		   <tr>
		    <td>&nbsp;</td>
		  </tr>	 
		  <tr>
			<td class="etiqueta" align="center">Presidente:&nbsp;<u><%= Titulo.getPresidente() %></u></td>
		  </tr> 
		  <tr>
			<td class="etiqueta" align="center">Secretario:&nbsp;<u><%= Titulo.getSecretario() %></u></td>
		  </tr>
		  <tr>
			<td class="etiqueta" align="center">Miembro:&nbsp;<u><%=Titulo.getMiembro() %></u></td>
		  </tr>
		  <tr>
		    <td>&nbsp;</td>
		  </tr>	 
			<table style="margin: 0 auto;  width:90%" border="1"cellpadding="2" cellspacing="2">
  	   		<tbody align="center">
    	  	  <tr align="center">
      			<td><span style="font-weight: bold;">Comentarios del Jurado</span></td>
    	  	  </tr>
    	  	  <tr align="center">
      			<td></td>
    	  	  </tr>
    	  	  <tr>
      	    	<td>      	    	
      	    	<br>
      	    	<%=Titulo.getComentario()%>
      	    	<br>
      	    	<br>      	          	    
				<b>Calificaci&oacute;n:</b>&nbsp;<u><%= Titulo.getNota()%></u>
				&nbsp; &nbsp;  
				Firma del Presidente_________________			
				&nbsp; &nbsp; 
				Firma del Coordinador_________________				
      	    	<br>
				</td>
    	  	  </tr>
  	    	</tbody>  	
	  		</table>
	    </table>
   	  </table>
      </div>
   </td>
   </tr>  
  </table>
  <table align="center">
    <tr><td align="center"><a href="javascript:confirmar('<%= cursoCargaId%>','<%= cursoId %>');" class="btn btn-primary" >Confirmar</a></td></tr>
  </table>
  <div class="alert alert info"><% if (numAccion==1) out.println(mensaje);%></div>
</body>
<%@ include file= "../../cierra_enoc.jsp" %>