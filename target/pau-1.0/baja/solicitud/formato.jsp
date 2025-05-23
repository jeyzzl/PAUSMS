<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="aca.util.Fecha"%>

<%@page import="aca.baja.spring.BajaPaso"%>
<%@page import="aca.baja.spring.BajaAlumno"%>
<%@page import="aca.vista.spring.AlumnoCurso"%>
<%@page import="aca.acceso.spring.Acceso"%>

<%@ include file= "id.jsp"%>
<%@ include file= "../../seguro.jsp"%>
<%@ include file= "../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<script>
	function grabaCarga(){
		document.forma.submit();
	}
	
	function grabaBloque(){
		document.forma.submit();
	}	
</script>
<%
	String codigoPersonal		= (String) session.getAttribute("codigoPersonal");
	String codigoAlumno 		= (String) session.getAttribute("codigoAlumno");
	String institucion 			= (String) session.getAttribute("institucion");
	String alumnoNombre			= (String) request.getAttribute("alumnoNombre");
	String carreraNombre		= (String) request.getAttribute("carreraNombre");
	Acceso acceso 				= (Acceso) request.getAttribute("acceso");
	BajaAlumno bajaAlumno		= (BajaAlumno) request.getAttribute("bajaAlumno");
	boolean paso1				= (boolean) request.getAttribute("paso1");
	boolean paso2				= (boolean) request.getAttribute("paso2");
	boolean paso3				= (boolean) request.getAttribute("paso3");
	boolean paso4				= (boolean) request.getAttribute("paso4");
	boolean paso5				= (boolean) request.getAttribute("paso5");
	boolean paso6				= (boolean) request.getAttribute("paso6");
	boolean paso7				= (boolean) request.getAttribute("paso7");	
	
	//System.out.println("Datos:"+paso1+":"+paso2+":"+paso3+":"+paso4+":"+paso5+":"+paso6+":"+paso7);	
	List<AlumnoCurso> lisCursos 			= (List<AlumnoCurso>) request.getAttribute("lisCursos");
	List<BajaPaso> lisPasos 				= (List<BajaPaso>) request.getAttribute("lisPasos");
	HashMap<String,String> mapaMaestros		= (HashMap<String,String>) request.getAttribute("mapaMaestros");
	HashMap<String,BajaPaso> mapaPasos		= (HashMap<String,BajaPaso>) request.getAttribute("mapaPasos");
	
	int i = 0, contador = 1;
	
	if(acceso.getAccesos().indexOf(bajaAlumno.getCarreraId()) != -1 | Boolean.parseBoolean(session.getAttribute("admin")+"")){
%>
<head>
	<link href="../../academico.css" rel="STYLESHEET" type="text/css">	
	<STYLE TYPE="text/css">
		.tabbox
			{
				font-family: Arial, Helvetica, sans-serif;
				background: #eeeeee;
			}
		.Estilo1 {font-size: 7pt}
		.Estilo3 {color: #000000}
		
		.img {
		  width:100%;
		  height:900px;
		  background-repeat: no-repeat;
		  background-position: 445px 150px; 
		  background-size: 400px 400px;
		  background-image: url("../../imagenes/logo_fondo.png");
		}	
	</STYLE>
	
	<script type="text/javascript">
		function inicio(){
			var img = document.getElementById("fondo");
			var theWidth, theHeight;
			if (window.innerWidth){
			  theWidth = window.innerWidth 
			  theHeight = window.innerHeight 
			} 
			else if (document.documentElement && document.documentElement.clientWidth){
			  theWidth = document.documentElement.clientWidth 
			  theHeight = document.documentElement.clientHeight 
			} 
			else if (document.body){
			  theWidth = document.body.clientWidth 
			  theHeight = document.body.clientHeight 
			}
			img.style.left = (((theWidth)/2)-(img.offsetWidth/2))+"px";
			img.style.top = ((((theHeight/2)-(img.offsetHeight/2))<0)?0:((theHeight/2)-(img.offsetHeight/2)))+"px";
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
	<div class="container-fluid" >				
		<table>
				<tr>
					<td colspan="4" align="center" ><img alt=""  src="../../imagenes/encabezado.png" width="530" height="100"><br><br><br><br></td>
				</tr>
			</table>
			<h3>Baja Total <small class="text-muted fs-6">( <%=codigoAlumno %> - <%=alumnoNombre%> | <%=Fecha.getHoy()%> )</small></h4> 
			<br>
			<div>					
				Plan de estudios: <%=bajaAlumno.getPlanId()%> - <%=carreraNombre%>
			</div>
			<br>
<!-- 			<div class="img"> -->
				<table class="table table-sm table-bordered" style="width:950px">
					<tr> 	
 						<td colspan="4"><h1>Materias que se darán de Baja</h1></td> 	
	 				</tr>	
					<tr>
						<th width="5%"><h6>#</h6></td>
						<th width="40%" align="left"><h6><spring:message code='aca.Nombre'/></h6></th>
						<th width="45%" align="left"><h6><spring:message code='aca.Maestro'/></h6></th>
						<th width="10%" align="center"><h6><spring:message code='aca.Tipo'/></h6></th>
					</tr>
<%
				int row=0;
				for(AlumnoCurso alumnoCurso : lisCursos){
					row++;
					
					String maestroNombre = "-";
					if (mapaMaestros.containsKey(alumnoCurso.getMaestro() )){
						maestroNombre = mapaMaestros.get(alumnoCurso.getMaestro());
					}
%>
					<tr>
						<td width="5%"><%=row%></td>
						<td width="35px" align="left"><%=alumnoCurso.getNombreCurso() %></td>
						<td width="35px" align="left"><%=maestroNombre%></td>
						<td width="35px" align="center"><%=alumnoCurso.getTipoCalId() %></td>
					</tr>			
<%
				}
%>
				</table><br>
				<table style="width:950px">	
<%
			
			int cont = 0;
					if (paso1){
						//Retencion y Mentoria
						cont++;
						if(cont == 1 || cont == 4){
%>
							<tr>
<%
						}
%>
								<td align="center">
								<br><br><br>
									<br>______________________________ <br>
									 <b>Firma <%=mapaPasos.get("1").getPasoNombre()%></b>
									<br><br><br><br>
<%						
						contador++;
%>						
								</td>
<%		
						if(cont == 3 || cont == 6){
%>
							</tr>
<%
						}
					}
					if (paso2){
						//Firma del coordinador	
						cont++;
						if(cont == 1 || cont == 4){
%>
							<tr>
<%
						}
%>						
								<td align="center">
									<br>______________________________ <br>
									 <b>Firma <%= mapaPasos.get("2").getPasoNombre()%></b>
								</td>
<%						
						contador++;	
						if(cont == 3 || cont == 6){
%>
							</tr>
<%
						}
					}
					if (paso3){
						//Comedor
						
						cont++;
						if(cont == 1 || cont == 4){
%>
							<tr>
<%
						}
%>
								<td align="center">
									<br>______________________________ <br>
									 <b>Firma <%=mapaPasos.get("3").getPasoNombre()%></b>
								</td>
<%							
						contador++;
						if(cont == 3 || cont == 6){
%>
							</tr>
<%	
						}
					}
					if (paso4){
						//Dormitorio
						
							cont++;
							if(cont == 1 || cont == 4){
%>
								<tr>
<%
							}
%>
									<td align="center">
										<br>______________________________ <br>
										<b>Firma <%= mapaPasos.get("4").getPasoNombre()%></b>
									</td>

<%							
							contador++;
							if(cont == 3 || cont == 6){
%>
								</tr>
<%
							}
						
					}
					if (paso5){
						//Direccion Juridica						
							cont++;
							if(cont == 1 || cont == 4){
%>
								<tr>
<%
							}
%>
									<td align="center">
										<br>______________________________ <br>
										 <b>Firma <%=mapaPasos.get("5").getPasoNombre() %></b>
									</td>		
<%							
							contador++;
							if(cont == 3 || cont == 6){
%>
								</tr>
<%
							}
						
					}
					if (paso6){
						//Finanzas Estudiantiles
						cont++;
						if(cont == 1 || cont == 4){
%>
							<tr>
<%
						}
%>
								<td align="center">
									<br>______________________________ <br>
									<b>Firma <%=mapaPasos.get("6").getPasoNombre()%></b>
								</td>
<%					
						contador++;
						if(cont == 3 || cont == 6){
%>
							</tr>
<%
						}
					}
					if (paso7){
						cont++;
						if(cont == 1 || cont == 4){
%>
							<tr>
<%
						}
%>
								<td align="center">
									<br>______________________________ <br>
									 <b>Firma <%=mapaPasos.get("7").getPasoNombre()%></b>
								</td>
<%						
						if(cont == 3 || cont == 6){
%>
							</tr>
<%
						}
					}				
%>
			</table>
			<br><br><br>
			<table style="width:950px">
			<tr>				
				<td align="center">
					<br>______________________________________ <br>					
					<span><b><%=alumnoNombre%></b></span><br>Alumno
				</td>
			</tr>			
			</table>
	</div>
<%
	}else{
%>
	<table>
		<tr>
			<td><font color="red" size="3"><b>Este alumno no pertenece a su carrera!!!</b></font></td>
		</tr>
	</table>
<%	} %>
</body>