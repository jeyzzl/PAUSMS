<%@page import="aca.voto.spring.VotoCandidato"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file="../../body.jsp"%>
<%@ include file="../../idioma.jsp"%>

<jsp:useBean id="alumno" scope="page" class="aca.alumno.AlumPersonal"/>
<jsp:useBean id="AlumUtil" scope="page" class="aca.alumno.AlumUtil"/>
<jsp:useBean id="FedVotoM" scope="page" class="aca.federacion.FedVotoM"/>
<jsp:useBean id="FedMoodies" scope="page" class="aca.federacion.FedMoodies"/>
<jsp:useBean id="FedMoodiesUtil" scope="page" class="aca.federacion.FedMoodiesUtil"/>

<jsp:useBean id="votoCandidato" scope="page" class="aca.voto.spring.VotoCandidato"/>

<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css" rel="stylesheet">
<script src = "../..//js/jquery-1.7.1.min.js"></script>
<script>
	function votar(){
		if (document.formVotar.votoM.value != "" && document.formVotar.votoH.value != ""){			
			document.formVotar.submit();
		}else{
			alert("¡Elige un alumno y una alumna para éste premio!")
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
	String candidatas		= (String) request.getAttribute("candidatas");		
	boolean existe			= (boolean) request.getAttribute("existe");	
	boolean yaVoto			= (boolean) request.getAttribute("yaVoto");
	
	List<VotoCandidato> lisCandidatos 		= (List<VotoCandidato>) request.getAttribute("lisCandidatos");	
	List<String> lisElegidos 				= (List<String>) request.getAttribute("lisElegidos");
	
	HashMap<String,String> mapCandidatos 	= (HashMap<String,String>) request.getAttribute("mapa");
	HashMap<String,String> mapYaVoto 		= (HashMap<String,String>) request.getAttribute("mapaYaVoto");
	
	String [] votoHombre 			= candidatos.split(",");
	String [] votoMujer 			= candidatas.split(",");
%>

<div class="container-fluid">	
	<h2>Vota por tus candidatos<small>(<%=eventoNombre%>)</small></h2>
<!-- 	<hr /> -->
	<div class="alert alert-info">
<%
	String etiqueta = "<span class='label label-pill label-success'>"; // label-success label-warning label-inverse
	for (VotoCandidato candidato : lisCandidatos){
		if (mapYaVoto.containsKey(candidato.getCandidatoId())){
			etiqueta = "<span class='label label-pill label-inverse'>";
		}else{
			etiqueta = "<span class='label label-pill label-success'>";
		}
		out.print(etiqueta);
%>		
		<a style="color:white;" href="votoMoodie?eventoVoto=<%=eventoVoto%>&candidatoId=<%=candidato.getCandidatoId() %>"><%=candidato.getCandidatoNombre()%></a>
		</span>&nbsp;&nbsp;
<%		
	}
%>		
	</div>
	<h4><%=candidatoNombre%><small>( Mujer )</small><h4>
	<hr/>
	<form name="formVotar" action="votar?eventoVoto=<%=eventoVoto%>&candidatoId=<%=candidatoId%>" method="post">
	<div class="Mujeres">
		<div class="row">
			<div class="span14">
<%
			for (String voto : votoMujer ){
				String nombre = "-";
				if(mapCandidatos.containsKey(voto)){
					nombre = mapCandidatos.get(voto);
				}
%>		
				<div class="img-container radio" style="height: 140px; width:140px;">
					<label>
						<img width="30px" src="../../foto?Codigo=<%=voto%>" alt="" />
						<%	if(yaVoto){
								if (lisElegidos.contains(voto)){
									out.print("<span class='label label-pill label-inverse'>X</span>");
								}						
							}else{%>
						<input type="radio" name="votoM" id="votoM" value="<%=voto%>">&nbsp;&nbsp;&nbsp;&nbsp;
						<%	}%>
						<span style="font-size:9px"><%=nombre%></span>
					</label>
				</div>
<%
			}
%>			
						
			</div>
		</div>
	</div>
	<br>
	<h4><%=candidatoNombre%><small>( Hombre )</small><h4>
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
					<div class="img-container radio" style="height: 140px; width:140px;">
						<label>
							<img src="../../foto?Codigo=<%=voto%>" alt="" />
							<%	if(yaVoto){				
									if (lisElegidos.contains(voto)){
										out.print("<span class='label label-pill label-inverse'>X</span>");
									}
								}else{ %>
							<input type="radio" name="votoH" id="votoH" value="<%=voto%>">&nbsp;&nbsp;&nbsp;&nbsp;
							<%	} %>
							<span style="font-size:9px"><%=nombre%></span>
						</label>
					</div>
<%
				}
%>							
				</div>
			</div>
		</div>
<%
	if(!yaVoto){
%>			
		<div class="alert alert-info">
			<a href="javascript:votar()" class="btn btn-primary">Votar</a>
		</div>	
<%
	}
%>	
	</form>
</div>