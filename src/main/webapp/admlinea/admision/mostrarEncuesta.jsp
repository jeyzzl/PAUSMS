<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Arrays"%>
<%@page import="aca.admision.spring.AdmEncuesta"%>
<%@page import="aca.admision.spring.AdmRecomienda"%>

<%@ include file= "id.jsp" %>
<%@ include file= "../../seguro.jsp" %>
<%@ include file= "../../headPortal.jsp" %>
<%@ include file="../../idioma.jsp"%>
<head>
</head>
<% 
	String folio				= (String)request.getAttribute("folio");
	AdmEncuesta encuesta 		= (AdmEncuesta)request.getAttribute("admEncuesta");
	
	HashMap<String,AdmRecomienda> mapaEncuestas = (HashMap<String,AdmRecomienda>)request.getAttribute("mapaEncuestas");
	
	String dias 	= "";
	String meses 	= "";
	String anos 	= "";
	
	String tiempo[];
	if(!encuesta.getTiempo().equals("")){
			tiempo = encuesta.getTiempo().split(",");
			dias	= tiempo[0]== null? "0":tiempo[0];
			meses 	= tiempo[1]== null? "0":tiempo[1];
			anos 	= tiempo[2]== null? "0":tiempo[2];
	}

	
	String nombre 	= "-";
	String puesto	= "-";
	if (mapaEncuestas.containsKey(encuesta.getFolio()+encuesta.getRecomendacionId())) {
		nombre = mapaEncuestas.get(encuesta.getFolio()+encuesta.getRecomendacionId()).getNombre();
		puesto = mapaEncuestas.get(encuesta.getFolio()+encuesta.getRecomendacionId()).getPuesto();			
	}
	
	String opinion = encuesta.getOpinion();
	if(opinion.equals("1")){
		opinion = "Highly Recommended";		
	}else if(opinion.equals("2")){
		opinion = "Recommended with reservations";
	}else if(opinion.equals("3")){
		opinion = "Recommended";
	}else{
		opinion = "Not recommended";
	}
%>
	
<body>	
<div class="container-fluid">
	<h4>REFEREE: <small class="text-muted fs-5"><%=nombre%></small> OCUPATION: <small class="text-muted fs-5"><%=puesto%></small> OPINION: <small class="text-muted fs-5"><%=opinion%></small></h4>	
	<table class="table table-sm table-bordered border-dark">
	<thead class="table-dark">
  		<tr>
  			<th class="text-center">#</th>
		    <th class="text-center">CHARACTERISTIC</th>
		    <th class="text-center">1</th>
		    <th class="text-center">2</th>
		    <th class="text-center">3</th>
		    <th class="text-center">4</th>
		    <th class="text-center">5</th>
	  	</tr>
	</thead>
	<tbody>  	
	  	<tr>
	  		<td style="background-color:#eeeeee">1</td>
		    <td style="background-color:#eeeeee">Motivation and perseverance</td>
		    <td <%=encuesta.getR1().equals("1")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR1().equals("1")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Below Average</td>
		    <td <%=encuesta.getR1().equals("2")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR1().equals("2")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Avergage</td>
		    <td <%=encuesta.getR1().equals("3")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR1().equals("3")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Above Average</td>
		    <td <%=encuesta.getR1().equals("4")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR1().equals("4")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Superior</td>
		    <td <%=encuesta.getR1().equals("5")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR1().equals("5")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>I do not have enough information</td>
	  	</tr>
	  	<tr>
	  		<td style="background-color:#eeeeee">2</td>
		    <td style="background-color:#eeeeee">Reliability (does his job conscientiously and responsibly)</td>
		    <td <%=encuesta.getR2().equals("1")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR2().equals("1")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>A menudo es irresponsable</td>
		    <td <%=encuesta.getR2().equals("2")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR2().equals("2")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Must be supervised</td>
		    <td <%=encuesta.getR2().equals("3")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR2().equals("3")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Frequently reliable</td>
		    <td <%=encuesta.getR2().equals("4")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR2().equals("4")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Completely reliable</td>
		    <td <%=encuesta.getR2().equals("5")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR2().equals("5")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>I do not have enough information</td>
	  	</tr>
	  	<tr>
	  		<td style="background-color:#eeeeee">3</td>
		    <td style="background-color:#eeeeee">Intellectual Ability</td>
		    <td <%=encuesta.getR3().equals("1")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR3().equals("1")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Below Average</td>
		    <td <%=encuesta.getR3().equals("2")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR3().equals("2")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Avergage</td>
		    <td <%=encuesta.getR3().equals("3")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR3().equals("3")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Above Average</td>
		    <td <%=encuesta.getR3().equals("4")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR3().equals("4")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Superior</td>
		    <td <%=encuesta.getR3().equals("5")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR3().equals("5")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>I do not have enough information</td>
	  	</tr>
	  	<tr>
	  		<td style="background-color:#eeeeee">4</td>
		    <td style="background-color:#eeeeee">Integrity</td>
		    <td <%=encuesta.getR4().equals("1")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR4().equals("1")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Frequently dishonest: steals or copies</td>
		    <td <%=encuesta.getR4().equals("2")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR4().equals("2")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Sometimes questionable</td>
		    <td <%=encuesta.getR4().equals("3")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR4().equals("3")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Basically honest</td>
		    <td <%=encuesta.getR4().equals("4")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR4().equals("4")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>Consistently honest and truthful person</td>
		    <td <%=encuesta.getR4().equals("5")?"style='background-color:#F5F57D'":""%>><%=encuesta.getR4().equals("5")?"<i style='color: green;' class='far fa-check-circle'></i>&nbsp;":""%>I do not have enough information</td>
	  	</tr>
	  	<tr>
	  		<td style="background-color:#eeeeee">5</td>
		    <td style="background-color:#eeeeee">Emotional stability</td>
		    <td <%=encuesta.getR5().equals("1")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR5().equals("1")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Tense, excitable often loses control</td>
		    <td <%=encuesta.getR5().equals("2")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR5().equals("2")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Occasionally affected by feelings, changeable temperament</td>
		    <td <%=encuesta.getR5().equals("3")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR5().equals("3")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Generally well balanced</td>
		    <td <%=encuesta.getR5().equals("4")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR5().equals("4")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Self-controlled, serene, cheerful</td>
		    <td <%=encuesta.getR5().equals("5")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR5().equals("5")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>I do not have enough information</td>
	  	</tr>
	  	<tr>
	  		<td style="background-color:#eeeeee">6</td>
		    <td style="background-color:#eeeeee">Empathy: shows sensitivity to the needs of others, consideration and tact</td>
		    <td <%=encuesta.getR6().equals("4")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR6().equals("4")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Always</td>
		    <td <%=encuesta.getR6().equals("3")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR6().equals("3")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Frequently</td>
		    <td <%=encuesta.getR6().equals("2")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR6().equals("2")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Occasionally</td>
		    <td <%=encuesta.getR6().equals("1")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR6().equals("1")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Never</td>
		    <td <%=encuesta.getR6().equals("5")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR6().equals("5")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>I do not have enough information</td>
	  	</tr>
	  	<tr>
	  		<td style="background-color:#eeeeee">7</td>
		    <td style="background-color:#eeeeee">Consistency of academic performance</td>
		    <td <%=encuesta.getR7().equals("1")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR7().equals("1")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Below Average</td>
		    <td <%=encuesta.getR7().equals("2")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR7().equals("2")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Avergage</td>
		    <td <%=encuesta.getR7().equals("3")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR7().equals("3")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Above Average</td>
		    <td <%=encuesta.getR7().equals("4")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR7().equals("4")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Superior</td>
		    <td <%=encuesta.getR7().equals("5")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR7().equals("5")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>I do not have enough information</td>
	  	</tr>
	  	<tr>
	  		<td style="background-color:#eeeeee">8</td>
		    <td style="background-color:#eeeeee">Maturity (balanced personality, ability to cope with daily life situations)</td>
		    <td <%=encuesta.getR8().equals("1")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR8().equals("1")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Below Average</td>
		    <td <%=encuesta.getR8().equals("2")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR8().equals("2")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Avergage</td>
		    <td <%=encuesta.getR8().equals("3")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR8().equals("3")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Above Average</td>
		    <td <%=encuesta.getR8().equals("4")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR8().equals("4")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Superior</td>
		    <td <%=encuesta.getR8().equals("5")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR8().equals("5")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>I do not have enough information</td>
	  	</tr>
	  	<tr>
	  		<td style="background-color:#eeeeee">9</td>
		    <td style="background-color:#eeeeee">Willingness to follow rules</td>
		    <td <%=encuesta.getR9().equals("1")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR9().equals("1")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Null </td>
		    <td <%=encuesta.getR9().equals("2")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR9().equals("2")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Regular</td>
		    <td <%=encuesta.getR9().equals("3")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR9().equals("3")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Good</td>
		    <td <%=encuesta.getR9().equals("4")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR9().equals("4")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Excellent</td>
		    <td <%=encuesta.getR9().equals("5")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR9().equals("5")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>I do not have enough information</td>
	  	</tr>
	 	<tr>  
	 		<td style="background-color:#eeeeee">10</td>
		    <td style="background-color:#eeeeee">The friends he makes are people of good reputation and moral principles.</td>
		    <td <%=encuesta.getR10().equals("4")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR10().equals("4")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Always</td>
		    <td <%=encuesta.getR10().equals("3")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR10().equals("3")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Frequently</td>
		    <td <%=encuesta.getR10().equals("2")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR10().equals("2")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Seldom</td>
		    <td <%=encuesta.getR10().equals("1")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR10().equals("1")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Never</td>
		    <td <%=encuesta.getR10().equals("5")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR10().equals("5")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>I do not have enough information</td>
	  	</tr>
	  	<tr>  
	  		<td style="background-color:#eeeeee">11</td>
		    <td style="background-color:#eeeeee">Personal appearance (in both sexes evaluate neatness and cleanliness, in the female sex also modesty and decency in their clothing).</td>
		    <td <%=encuesta.getR11().equals("1")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR11().equals("1")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Undesirable</td>
		    <td <%=encuesta.getR11().equals("2")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR11().equals("2")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Careless</td>
		    <td <%=encuesta.getR11().equals("3")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR11().equals("3")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Good</td>
		    <td <%=encuesta.getR11().equals("4")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR11().equals("4")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Excellent</td>
		    <td <%=encuesta.getR11().equals("5")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR11().equals("5")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>I do not have enough information</td>
	  	</tr>
	   	<tr>  
	   		<td style="background-color:#eeeeee">12</td>
	  		<td style="background-color:#eeeeee">Social relationships</td>
	    	<td <%=encuesta.getR12().equals("1")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR12().equals("1")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Withdrawn and shy person</td>
		    <td <%=encuesta.getR12().equals("2")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR12().equals("2")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>He/She only has a small circle of friends</td>
		    <td <%=encuesta.getR12().equals("3")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR12().equals("3")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>He/She generally has a small circle of friends</td>
		    <td <%=encuesta.getR12().equals("4")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR12().equals("4")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Exceptional ease for social relationships</td>
		    <td <%=encuesta.getR12().equals("5")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR12().equals("5")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>I do not have enough information</td>
	  	</tr>
	  	<tr>  
	  		<td style="background-color:#eeeeee">13</td>
		    <td style="background-color:#eeeeee">Interest in the spiritual aspect</td>
		    <td <%=encuesta.getR13().equals("1")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR13().equals("1")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Negative</td>
		    <td <%=encuesta.getR13().equals("2")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR13().equals("2")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Passive</td>
		    <td <%=encuesta.getR13().equals("3")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR13().equals("3")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Participates</td>
		    <td <%=encuesta.getR13().equals("4")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR13().equals("4")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Shows active leadership</td>
		    <td <%=encuesta.getR13().equals("5")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR13().equals("5")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>I do not have enough information</td>
	  	</tr>
	  	<tr>  
	  		<td style="background-color:#eeeeee">14</td>
		    <td style="background-color:#eeeeee">Involvement in spiritual activities</td>
		    <td <%=encuesta.getR14().equals("1")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR14().equals("1")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Never</td>
		    <td <%=encuesta.getR14().equals("2")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR14().equals("2")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Seldom</td>
		    <td <%=encuesta.getR14().equals("3")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR14().equals("3")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Regularly</td>
		    <td <%=encuesta.getR14().equals("4")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR14().equals("4")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>Always</td>
		    <td <%=encuesta.getR14().equals("5")?"style='background-color:#F5F57D'":""%>><% if(encuesta.getR14().equals("5")){ %> <i style='color: green;' class='far fa-check-circle'></i>&nbsp; <% }%>I do not have enough information</td>
	  	</tr>
	</tbody>  	  
	</table>
	<table class="table" style="width:98%; margin: 0 auto;">
  		<tr>
	    	<td colspan="3">&nbsp;</td>
	  	</tr>
	  	<tr>
		    <td colspan="3">How long have you known the applicant? (this information is very important):<br>
		    	<%=dias.equals("0")?"":"<b>"+dias+"&nbsp;days</b>"%>
		    	<%=meses.equals("0")?"":"<b>"+meses+"&nbsp;months</b>"%>		    	
		    	<%=anos.equals("0")?"":"<b>"+anos+"&nbsp;years</b>"%>		    	
		    </td>
	  	</tr>
	  	<tr>
	  		<td colspan="3">How well do you think you know him?<br>		    
		    	<%=encuesta.getConocer().equals("1")?"<i class='far fa-check-square'></i>":"<i class='far fa-square'></i>"%>&nbsp;<b>Bit</b>&nbsp;&nbsp;
				<%=encuesta.getConocer().equals("2")?"<i class='far fa-check-square'></i>":"<i class='far fa-checksquare'></i>"%>&nbsp;<b>Good</b>&nbsp;&nbsp;
				<%=encuesta.getConocer().equals("3")?"<i class='far fa-check-square'></i>":"<i class='far fa-square'></i>"%>&nbsp;<b>Very good</b>
	    	</td>
	  	</tr>
	  	<tr>
		    <td colspan="3">What is your relationship?<br>		    
			    <%=encuesta.getRelacion().equals("1")?"<i class='far fa-check-square'></i>&nbsp;":"<i class='far fa-square'></i>"%>&nbsp;<b>lecturer/academic/mentor</b>&nbsp;&nbsp;
			    <%=encuesta.getRelacion().equals("2")?"<i class='far fa-check-square'></i>&nbsp;":"<i class='far fa-square'></i>"%>&nbsp;<b>school administrator</b>&nbsp;&nbsp;
			    <%=encuesta.getRelacion().equals("3")?"<i class='far fa-check-square'></i>&nbsp;":"<i class='far fa-square'></i>"%>&nbsp;<b>manager/supervisor</b>&nbsp;&nbsp;
			    <%=encuesta.getRelacion().equals("4")?"<i class='far fa-check-square'></i>&nbsp;":"<i class='far fa-square'></i>"%>&nbsp;<b>other(indicate): <%=encuesta.getOtra()%></b>	    
		    </td>
	  	</tr>
	  	<tr>
		    <td colspan="3">Do you know of any disciplinary action, censure, suspension or expulsion that has been taken against the applicant?<br>		    
		    	<%=encuesta.getCensura().equals("S")?"<i class='far fa-check-square'></i>&nbsp;":"<i class='far fa-square'></i>&nbsp;"%><b>YES</b>&nbsp;&nbsp;
		    	<%=encuesta.getCensura().equals("N")?"<i class='far fa-check-square'></i>&nbsp;":"<i class='far fa-square'></i>&nbsp;"%><b>NO</b>&nbsp;&nbsp; 
		    	<%=encuesta.getConducta()==null?"":encuesta.getConducta()%>
		    </td>	        
	  	</tr>
	  	<tr>
		    <td colspan="3">Based on the evaluated characteristics, the candidate is:<br>		    
		    	<%=encuesta.getOpinion().equals("1")?"<i class='far fa-check-square'></i>&nbsp;":"<i class='far fa-square'></i>&nbsp;"%><b>Highly recommended</b>&nbsp;&nbsp;
		    	<%=encuesta.getOpinion().equals("3")?"<i class='far fa-check-square'></i>&nbsp;":"<i class='far fa-square'></i>&nbsp;"%><b>Recommended with reservations</b>&nbsp;&nbsp;
		    	<%=encuesta.getOpinion().equals("2")?"<i class='far fa-check-square'></i>&nbsp;":"<i class='far fa-square'></i>&nbsp;"%><b>Recommended</b>&nbsp;&nbsp;		    	
	        	<%=encuesta.getOpinion().equals("4")?"<i class='far fa-check-square'></i>&nbsp;":"<i class='far fa-square'></i>&nbsp;"%><b>Not recommended</b>
	        </td>
	  	</tr>
	  	<tr>
	    	<td colspan= "3">
	    		Comentario adicional:<br>
	    		<%=encuesta.getAdicional()==null?"<b>None</b>":encuesta.getAdicional()%>
	    	</td>
	  	</tr>	  	
	  	<tr>
	    	<td colspan="3">&nbsp;</td>
	  	</tr>
	</table>
</div>
</body>
