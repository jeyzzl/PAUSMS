/*****************************************************************************************************
   			 	  QRtag api - programmed by Jens Eldering (C) 2009 by ATECmedia
 *****************************************************************************************************				
	version : 1.2b
	release : 09-09-2009
	license : public
	script  : JavaScript
******************************************************************************************************/
function QRtag(){

	/**************************************************************************************************
		Initialize class
	 *************************************************************************************************/
    this.initialize = function(){    
		this.qr_data = document.location.href;
		this.qr_link = "";
		this.qr_size = 100;
		this.qr_border = 5;
		this.qr_id = "";
		this.qr_class_name = "";
		this.qr_align = "";
		this.qr_target = document.body;
		this.qr_fgcolor = "000000";
		this.qr_bgcolor = "ffffff";
    }

	/**************************************************************************************************
		Input functions
	 *************************************************************************************************/    
	this.data = function(data) {
		if (data && data.length>0 && data.length<128) this.qr_data = data;
		else this.qr_data = document.location.href;
	}
    
	this.link = function(url) {
		if (url>"" && url.length>5 && url.length<128) this.qr_link = url;
		else this.qr_link = "";
	}
    
	this.target = function(target) {
		if (document.getElementById(target) != undefined) this.qr_target = document.getElementById(target);
		else this.qr_target = document.body;
	}
	
	this.size = function(size) {
		if (size<100) size = 100;
		if (size>1000) size = 1000; 
		this.qr_size = size;
	}
	
	this.border = function(border) {
		if (border<0) border = 0;
		if (border>10) border = 10; 
		this.qr_border = border;
	}
	
	this.id = function(id) {
		if (id>"" && id.length>0 && id.length<255) this.qr_id = id;
		else this.qr_id = "";
	}

	this.color = function(color) {
		color = color.replace("#","");
		if (color.length==6) this.qr_fgcolor = color;
		else this.qr_fgcolor = "000000";
	}
	
	this.bgcolor = function(color) {
		color = color.replace("#","");
		if (color.length==6) this.qr_bgcolor = color;
		else this.qr_bgcolor = "ffffff";
	}
		
	this.place = function(place) {
		// absbottom | absmiddle | baseline | bottom | right | left | none | texttop | top
		if (place>"" && place.length>0 && place.length<255) this.qr_align = place;
		else this.qr_align = "";
	}
	
	this.class_name = function(class_name) {
		if (class_name>"" && class_name.length>5 && class_name.length<255) this.qr_class_name = class_name;
		else this.qr_class_name = "";
	}
	
	this.clear = function() {
		this.initialize();
	}
	
	/**************************************************************************************************
		Output functions
	 *************************************************************************************************/	
	this.image = function(target,data,size,border,link,id,class_name,place,hide) {
		if (data) this.data(data);
		if (size) this.size(size);
		if (border) this.border(border);
		if (id) this.id(id);
		if (link) this.link(link);
		if (class_name) this.class_name(class_name);
		if (place) this.place(place);
		if (target) this.target(target);

		var filename = "http://api.qrtag.net/code";
		filename = filename + "/" + this.qr_size
		filename = filename + "/" + this.qr_border
		filename = filename + "/" + this.qr_fgcolor
		filename = filename + "/" + this.qr_bgcolor
		filename = filename + "/" + string2hex(this.qr_data) + ".png";
		
		//var filename = "http://academico.um.edu.mx/codigosQR/prueba.png";
		
		if (hide==true) {
			this.qr_target.appendChild(create_div((this.qr_size+this.qr_border),filename,this.qr_data,this.qr_data,this.qr_id,this.qr_link,this.qr_class_name,this.qr_align,true));	
		} else {
			this.qr_target.appendChild(create_image(filename,this.qr_data,this.qr_data,this.qr_id,this.qr_link,this.qr_class_name,this.qr_align));
		}
	}
	
	this.print_image = function(data,size,border,link,id,class_name,align) {
		this.image(data,size,border,link,id,class_name,align,true)
	}
   
   this.initialize();

} 
/*****************************************************************************************************
   Some handy functions :-)
 ****************************************************************************************************/
function create_image(src, alt, title, id, link, class_name, align) {
	var IE = /*@cc_on!@*/false;
	
	if (IE) var img = new Image();
	else var img = document.createElement('img');

	img.src= src;
	//img.position = "relative";
	//img.style.zIndex = 100;
	if (alt) img.alt = alt;
	if (title) img.title = title;
	if (id) img.id = id;
	if (align) img.align = align; // absbottom | absmiddle | baseline | bottom | right | left | none | texttop | top
	if (class_name) img.className = class_name;
	if (link!=null && link!="") {
		img.onclick = new Function("document.location.href='"+link+"'");
		img.style.cursor='pointer';
	}

	return img;
}


function create_div(size, src, alt, title, id, link, class_name, align, display) {
	var div = document.createElement("div");
	
	//div.setAttribute("align","center");
	//div.style.margin = "0px auto";
	div.style.width = size;
	div.style.height = size;	
	if (id) div.style.height = size;
	if (display) div.style.display = 'none';
	if (class_name) div.className = class_name;
	if (align) div.style.align = align;
	if (link!=null && link!="") {
		div.onclick = new Function("document.location.href='"+link+"'");
		div.style.cursor='pointer';
	}
	var html = '<img src="'+src+'"';
	if (alt) html = html + ' alt="'+alt+'"';
	if (title) html = html + ' title="'+title+'"';
	html = html + '>';
	div.innerHTML = html;
	
	return div;

}

function string2hex(str){
	var output = "";
	var c=0;
	var code = "";
	while (c<str.length) {
		code = str.charCodeAt(c++).toString(16);
		while (code.length<2) code = "0" + code;
		output = output + code;
	}
	return output;
}

function get_id(){
	var divEls = document.getElementsByTagName("div");
	var i = 0;
	for(i=0;i<divEls.length;i++)
	alert(divEls[i].id);
}
