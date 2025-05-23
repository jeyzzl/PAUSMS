<%@page import="aca.voto.spring.VotoCandidato"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ page import="java.util.Date" %>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../headPortal.jsp"%>
<%@ include file="../../idioma.jsp"%>

<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
<script src = "../..//js/jquery-1.7.1.min.js"></script>
<script>
	function votar(){
		if (document.formVotar.voto.value != ""){
			document.formVotar.submit();
		}else{
			alert("?Elige un alumno votar!")
		}				
	}
</script>

<style>
	body{
		padding: 0 20px;
	}	
	.img-container{
		width: 180px;
		margin: 0 20px 0 0 !important;
		float: left;
	}	
	input[type=radio]{
		margin: 2px 10px 0 0 !important;
	}	
	.candidatos{
		overflow: hidden;
		margin-bottom: 20px;	
	}	
	img{
		width: 100%;
		border: 3px solid gray;
		display: block;
	}
	#pacman{
		width: 200px;
		height: 200px;
		border: 3px solid gray;
		display: block;
	}	
	.graficas{
		width: 50%;
		padding: 30px;
		float: left;
		border: 1px solid #BDBDBD;
	}
</style>
<%	
	String matricula 		= (String) session.getAttribute("codigoPersonal");
	String eventoVoto		= (String) request.getAttribute("eventoVoto");
	String eventoNombre		= (String) request.getAttribute("eventoNombre");
	String candidatoId		= (String) request.getAttribute("candidatoId");	
	String candidatoNombre	= (String) request.getAttribute("candidatoNombre");
	String candidatos		= (String) request.getAttribute("candidatos");			
	boolean existe			= (boolean) request.getAttribute("existe");	
	boolean yaVoto			= (boolean) request.getAttribute("yaVoto");
	boolean esInscrito		= (boolean) request.getAttribute("esInscrito");
	String fechaFin			= (String) request.getAttribute("fechaFin");
	String estado 			= (String) request.getAttribute("estado");
	String mensaje			= request.getParameter("Mensaje")==null?"-":request.getParameter("Mensaje");

	VotoCandidato votoCandidato = new VotoCandidato();
	
	List<VotoCandidato> lisCandidatos 		= (List<VotoCandidato>) request.getAttribute("lisCandidatos");	
	List<String> lisElegidos 				= (List<String>) request.getAttribute("lisElegidos");
	
	HashMap<String,String> mapCandidatos 	= (HashMap<String,String>) request.getAttribute("mapa");
	HashMap<String,String> mapVoto 			= (HashMap<String,String>) request.getAttribute("mapaVoto");
	HashMap<String,String> mapYaVoto 		= (HashMap<String,String>) request.getAttribute("mapaYaVoto");	
	String [] votoHombre 					= candidatos.split(",");	
	
	java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Date dateEvento = sdf.parse(fechaFin);
	Date dateHoy 	= new Date();
%>

<div class="container-fluid">	
	<h2>Votaciones<small class="text-muted fs-4"> (<%=eventoNombre%> Finaliza el: <%=fechaFin%>) </small>  </h2>
<!-- 	<hr /> -->
	<div class="alert alert-info">
<%
	String etiqueta = "<span class='badge bg-success'>"; // label-success label-warning label-inverse
	for (VotoCandidato candidato : lisCandidatos){
		if (mapYaVoto.containsKey(candidato.getCandidatoId())){
			etiqueta = "<span class='badge bg-dark'>";
		}else{
			etiqueta = "<span class='badge bg-success'>";
		}
		out.print(etiqueta);
%>		
		<a style="color:white; font-size:10px;" href="votoManana?eventoVoto=<%=eventoVoto%>&candidatoId=<%=candidato.getCandidatoId() %>"><%=candidato.getCandidatoNombre()%></a>
		</span>&nbsp;&nbsp;
<%		
	}
%>		
	</div>	
	<form name="formVotar" action="votarMan?eventoVoto=<%=eventoVoto%>&candidatoId=<%=candidatoId%>" method="post">	
	<h4><%=candidatoNombre%><small class="text-muted fs-6"></small></h4>
<!-- 	<ul class="list-group"> -->
<!--   		<li class="list-group-item">Ptr. F?lix Hadid Cort?s, de origen mexicano, catedr?tico en la Universidad Andrews.</li> -->
<!--   		<li class="list-group-item">Ptr. Andr?s Peralta, de origen dominicano, director asociado de los Ministerios Juveniles en la Asociaci?n General.</li> -->
<!--   		<li class="list-group-item">Ptr. Jonathan Jejel, de origen espa?ol, ex director mundial de Conquistadores y director asociado de los Ministerios Juveniles.</li> -->
<!--  	 	<li class="list-group-item">Ptr. Dwight Nelson, de origen estadounidense, pastor principal de la iglesia de la Universidad Andrews.</li> -->
<!-- 	</ul> -->
	<hr/>
	<div class="Hombres">
		<div class="row">
			<div class="span14">
<%
				for (String voto : votoHombre ){
					String nombre = "-";
					if(mapCandidatos.containsKey(voto)){
						nombre = mapCandidatos.get(voto);
					}
%>		
				<div class="img-container radio" style="height: 200px; width:200px;">
					<label>
<%						if (yaVoto){
							String votos = "0";
							if (mapVoto.containsKey(candidatoId+voto)){
								votos = mapVoto.get(candidatoId+voto);
							}
							out.print("<span style='font-size:14px' class='left badge bg-success'>"+votos+"</span><br>");
						}
%>
						<img src="../../foto?Codigo=<%=voto%>" alt="" />
							<%	if(yaVoto){				
									if (lisElegidos.contains(voto)){
										out.print("<span class='badge bg-dark'>X</span>");
									}
								}else{ %>
						<input type="radio" name="voto" id="voto" value="<%=voto%>">&nbsp;&nbsp;&nbsp;&nbsp;
							<%	} %>
						<span style="font-size:9px"><%=nombre%></span>
					</label>
				</div>
<%				} %>							
			</div>
		</div>
	</div>
	<br><br><br>
	<div class="alert alert-info">
<%	  
	if( !yaVoto && esInscrito){
		if(estado.equals("A") && dateHoy.before(dateEvento)) {
			out.print("<a href='javascript:votar()' class='btn btn-primary'>Votar</a>");
		}else{
			if (!mensaje.equals("-")){
				out.print(mensaje);
			}else{
				out.print("¡La votacion no esta activa!");
			}
		}
	}else if(yaVoto){
		out.print("¡Tu voto ya está registrado!");
	}else if (!esInscrito){
		out.print("¡Solo los alumnos inscritos pueden participar!");
	}
%>
	</div>
	</form>
</div>