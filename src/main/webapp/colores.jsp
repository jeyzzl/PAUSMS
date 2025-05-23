<%
	System.out.println("Entre a colores...");
	String sCodigoPersonal	= (String)session.getAttribute("codigoPersonal");
	aca.portal.Alumno colorDelUsuario = new aca.portal.Alumno();
	String colorTablas = colorDelUsuario.obtenColor(sCodigoPersonal);
	
	if(colorTablas.equals("default") || colorTablas.equals("")){		
		colorTablas = "#683EAD";
	}
%>
<style type="text/css">
	th{
		<%	String tmpColor = colorTablas.substring(1);
			int r = Integer.parseInt(tmpColor.substring(0,2), 16);
			int g = Integer.parseInt(tmpColor.substring(2,4), 16);
			int b = Integer.parseInt(tmpColor.substring(4,6), 16);
			
			if((r+64)>255){
				r=255;
			}else{
				r = r+64;
			}
			
			if((g+64)>255){
				g=255;
			}else{
				g = g+64;
			}
			
			if((b+64)>255){
				b=255;
			}else{
				b = b+64;
			}
	%>
			background: -webkit-gradient(linear, left top, left bottom, from(rgb(<%=r%>, <%=g%>, <%=b%>)), to(<%=colorTablas%>));
			background: -webkit-linear-gradient(rgb(<%=r%>, <%=g%>, <%=b%>), <%=colorTablas%> 60%);
			background: -moz-linear-gradient(rgb(<%=r%>, <%=g%>, <%=b%>), <%=colorTablas%> 60%);
			background: -o-linear-gradient(rgb(<%=r%>, <%=g%>, <%=b%>), <%=colorTablas%> 60%);
		  
		  <%String colorHex = Integer.toHexString(r)+Integer.toHexString(g)+Integer.toHexString(b);%>
			filter:  progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#<%=colorHex%>', endColorstr='<%=colorTablas%>'); /* IE6 & IE7 */
			-ms-filter: "progid:DXImageTransform.Microsoft.gradient(GradientType=0,startColorstr='#<%=colorHex%>', endColorstr='<%=colorTablas%>')"; /* IE8 */
			
	} 
</style>