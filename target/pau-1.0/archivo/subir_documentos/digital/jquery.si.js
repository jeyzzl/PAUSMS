jQuery.noConflict();
jQuery.fn.si = function() {
	jQuery.support = {
		opacity: !(jQuery.browser.msie && /MSIE 6.0/.test(navigator.userAgent))
	};
	if (jQuery.support.opacity) {
		jQuery(this).each(function(i) {
			if (jQuery(this).is(":file")) {
				var jQueryinput = jQuery(this);
				jQuery(this).wrap('<label class="cabinet" id="cabinet'+i+'"></label>');
				jQuery("label#cabinet"+i)
					.wrap('<div class="si"></div>')
					.after('<div class="uploadButton"><div>Select</div></div><label class="selectedFile"></label>')
					.live("mousemove", function(e) {
					if (typeof e == 'undefined') e = window.event;
					if (typeof e.pageY == 'undefined' &&  typeof e.clientX == 'number' && document.documentElement)
					{
						e.pageX = e.clientX + document.documentElement.scrollLeft;
						e.pageY = e.clientY + document.documentElement.scrollTop;
					};
					
					var ox = oy = 0;
					var elem = this;
					if (elem.offsetParent)
					{
						ox = elem.offsetLeft;
						oy = elem.offsetTop;
						while (elem = elem.offsetParent)
						{
							ox += elem.offsetLeft;
							oy += elem.offsetTop;
						};
					};
		
					var x = e.pageX - ox;
					var y = e.pageY - oy;
					var w = this.offsetWidth;
					var h = this.offsetHeight;
		
					jQueryinput.css("top", y - (h / 2)  + 'px');
					jQueryinput.css("left", x - (w + 30) + 'px');
				});
				
				jQuery(this).change(function() {
					jQuerycontainer = jQuery(this).closest("div.si");
					jQuery("label.selectedFile", jQuerycontainer).html(jQuery(this).val().split("\\").pop());
				})
			}
		});
	}
};