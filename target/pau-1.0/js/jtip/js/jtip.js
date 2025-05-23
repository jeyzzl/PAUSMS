/*
 * 
 	CREATED BY DAVID BLANCO
 * 
 */

(function($){
	$(window).load(function(){
		
		$('<div class=jpopup><div class="img" style="width:17px;height:15px;"/><span></span></div>').appendTo('body');
		$('<div class=jspopup><div class="img" style="width:17px;height:15px;"/><span></span> <div class=controls><span class=counter></span> <a class=back href=#>&#8592;</a><a class=next href=#>&#8594;</a> <a class=play href=#>&#9658;</a> <a class=close href=#>x</a> </div> </div>').appendTo('body');
		
		var jtip = {
			tip: $('div.jpopup'),
			arrow: $('div.jpopup').find('.img'),
			stip: $('div.jspopup'),
			sarrow: $('div.jspopup').find('.img'),  
			text: '',
			sequence: 1,
			seqNumber: 0,
			counter: $('.controls').find('.counter'),
			jstips: $('body').find('.jstip'),
			play: 'no',
			interval: 0,
			
			init: function(){
				var body = $('body');
				
				body.on('mouseenter','.jtip',function(){
					jtip.show.call(this,'tip','arrow');
				});
				
				
				body.on('mouseleave','.jtip',function(){ 
					jtip.hide('tip');
				});


				//Verify the sequence is right if is not, then fix it 
				var arr = '';
				var wrong = 0;
				jtip.jstips.each(function(){
					var $this = $(this);
					var seq = $this.attr('data-sequence');
					
					var split = arr.split(',');

					for(var i=0; i<split.length; i++){
						if((seq)==split[i]){
							wrong = 1;
						}
					}

					if(!seq){
						wrong = 1;
					}

					arr += ','+seq;
				});

				if(wrong){
					cont = 1;

					jtip.jstips.each(function(){
						$(this).attr('data-sequence',cont++);
					});

				}

				//sequencial tip

				//activate on load
				jtip.seqNumber = jtip.jstips.length;

				jtip.jstips.each(function(){
					$this = $(this);
					if($this.attr('data-sequence') == '1'){
						jtip.show.call($this,'stip','sarrow');
					}

				});

				//activate when click 
				$('.activateSequential').on('click', function(){
						jtip.jstips.each(function(){
							$this = $(this);
							if($this.attr('data-sequence') == '1'){
								jtip.show.call($this,'stip','sarrow');
							}

						});
				});


				jtip.counter.text(jtip.sequence+'/'+jtip.seqNumber);

				//controls go back
				$('.controls').find('.back').on('click', function(evt){
					evt.preventDefault();

					if(jtip.sequence=='1'){
						jtip.sequence = jtip.seqNumber;
					}else{
						jtip.sequence--;
					}

					jtip.counter.text(jtip.sequence+'/'+jtip.seqNumber);


					jtip.jstips.each(function(){
						$this = $(this);
						if($this.attr('data-sequence') == jtip.sequence){
							$('html, body').animate({scrollTop: $this.offset().top-50}, 800);

							jtip.show.call($this,'stip','sarrow');

						}

					});

				});

				//controls go next
				$('.controls').find('.next').on('click', function(evt){
					evt.preventDefault();

					if(jtip.sequence==jtip.seqNumber){
						jtip.sequence = 1;
					}else{
						jtip.sequence++;
					}

					jtip.counter.text(jtip.sequence+'/'+jtip.seqNumber);


					jtip.jstips.each(function(){
						$this = $(this);
						if($this.attr('data-sequence') == jtip.sequence){
							$('html, body').animate({scrollTop: $this.offset().top-50}, 800);

							jtip.show.call($this,'stip','sarrow');
						}

					});

				});

				//controls close
				$('.controls').find('.close').on('click', function(evt){
					evt.preventDefault();
					jtip.hide('stip');
				});

				//controls play
				$('.controls').find('.play').on('click', function(evt){
					evt.preventDefault();
					$this = $(this);
					
					if(jtip.play == 'no'){
						jtip.play = 'yes';
						$this.html('&#9632;');

						jtip.interval = setInterval(jtip.playing, 3000);
					}else{
						jtip.play = 'no';
						$this.html('&#9658;');

						jtip.interval = window.clearInterval(jtip.interval);
					}
		
				});


			},

			playing: function(){
				if(jtip.sequence==jtip.seqNumber){
						jtip.sequence = 1;
				}else{
					jtip.sequence++;
				}

				jtip.counter.text(jtip.sequence+'/'+jtip.seqNumber);

				jtip.jstips.each(function(){
					$this = $(this);
					if($this.attr('data-sequence') == jtip.sequence){
						$('html, body').animate({scrollTop: $this.offset().top-50}, 800);

						jtip.show.call($this,'stip','sarrow');
					}

				});
			},
			
			show: function(tip,arrow){

				var $this = $(this);
				var position = $this.data('position');
				var text = $this.data('text');
				if(!text){
					text = 'Please specify the text that goes here <br> with the attribute: data-text="example" ';
				}

				jtip[tip].promise().done(function(){
					jtip[tip].find('span').first().html( text );

					if(!position){
						position = 'right';
					}
					if(position === 'right'){
						jtip.right.call($this, tip, arrow);
					}else if(position === 'left'){
						jtip.left.call($this, tip, arrow);
					}else if(position === 'top'){
						jtip.top.call($this, tip, arrow);
					}else if(position === 'bottom'){
						jtip.bottom.call($this, tip, arrow);
					}else if(position === 'auto'){
						
					}
				});
				
			},
			
			right: function(tip, arrow){				
				var paddingTop = parseInt(this.css("padding-top")) - parseInt(jtip[tip].css("padding-top"));
				
				jtip[arrow]
						.addClass('arrowLeft')
						.css({
							'left':-15,
							'top': jtip[tip].css("padding-top")//back to normal
						});
				
				var top = this.offset().top + paddingTop;
				var left = this.offset().left + this.outerWidth() + 20 
				
				jtip[tip]
					.show()
					.css({
						'opacity': 0,
						'top': top,
						'left': left-6
					})
					.animate({
						'opacity': 1,
						'left': left
					},200);
				
	
			},
			
			left: function(tip, arrow){
				var paddingTop = parseInt(this.css("padding-top")) - parseInt(jtip[tip].css("padding-top")),
					tipWidth = jtip[tip].outerWidth();
				
				jtip[arrow]
						.addClass('arrowRight')
						.css({
							'left': tipWidth - 2,
							'top': jtip[tip].css("padding-top")//back to normal
						});
				
				var top = this.offset().top + paddingTop;
				var left = this.offset().left - tipWidth - 20
				
				jtip[tip]
					.show()
					.css({
						'opacity': 0,
						'top': top,
						'left': left + 6
					})
					.animate({
						'opacity': 1,
						'left': left
					},200);
			},
			
			top: function(tip, arrow){
				var paddingLeft = parseInt(this.css("padding-left")) - parseInt(jtip[tip].css("padding-left")),
					tipHeight = jtip[tip].outerHeight();
				
				
				jtip[arrow]
						.addClass('arrowBottom')
						.css({
							'top': tipHeight - 2,
							'left': jtip[tip].css("padding-left")//back to normal
						});
				
				var top = this.offset().top - tipHeight - 20;
				var left = this.offset().left + paddingLeft - 5;
				
				jtip[tip]
					.show()
					.css({
						'opacity': 0,
						'top': top + 6,
						'left': left
					})
					.animate({
						'opacity': 1,
						'top': top
					},200);
			},
			
			bottom: function(tip, arrow){
				var paddingLeft = parseInt(this.css("padding-left")) - parseInt(jtip[tip].css("padding-left"));
				
				jtip[arrow]
						.addClass('arrowTop')
						.css({
							'top': -parseInt(jtip[tip].css("padding-top")) - 5,
							'left': jtip[tip].css("padding-left")//back to normal
						});
				
				var top = this.offset().top + this.outerHeight() + 20;
				var left = this.offset().left + paddingLeft - 5;
				
				jtip[tip]
					.show()
					.css({
						'opacity': 0,
						'top': top - 6,
						'left': left
					})
					.animate({
						'opacity': 1,
						'top': top
					},200);
			},
			
			hide: function(tip){
				
				this[tip].promise().done(function(){
					$(this).stop().hide();
				});
				
			}
			
		};
		
		jtip.init();
	
	});
})(jQuery);