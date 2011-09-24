function load_animations(){
		
			if (!$.browser.msie) {
				$('#header_images').css({height: '592px', opacity:'0'})
				$('#overlay_bg').css({height:'592px'});							
				$('#header_controls_left').animate({opacity:'1'});
				$('#header_controls_right').animate({opacity:'1'});
				$('#header_images').stop().animate({opacity:'1'},400,'easeOutQuad');
				$('#header_images > .header_image:first-child').stop().animate({opacity: '1'},400,'easeOutQuad');				
				var header_count = $("#header_images > .header_image").size();
				
			}
			else{
				$('#header_images').css({height: '592px'});		
				$('#overlay_bg').css({height:'592px'});
				$('#header_images img').wrap('<div />');
				$('#header_images div').stop().animate({height: '0px'},0);
				$('#header_images div:first-child').stop().animate({height: '592px'},0);
				var header_count = $("#header_images > div").size();
			}
    
		/// end animation in ///		
		var current_project = 1;
 		
		if (!$.browser.msie) {
			$('#overlay_bg')
			.click(function(event){
			window.location=($('#header_images > img:nth-child('+current_project+')').attr('link'));
			});
		}
		else{
			$('#overlay_bg')
			.click(function(event){
			window.location=($('#header_images > div:nth-child('+current_project+')').children().attr('link'));
			});
		}
		$('#slider_outer')
		.hover(
		function(event){
		$('#header_controls_left').show();
		$('#header_controls_right').show();
		if (!$.browser.msie) {
			$('#header_controls_left').stop().animate({left:'0px'},200,'easeOutQuad');
			$('#header_controls_right').stop().animate({right:'0px'},200,'easeOutQuad');
		}},
		function(event){
		$('#header_controls_left').hide();
		$('#header_controls_right').hide();
		if (!$.browser.msie) {
			$('#header_controls_left').stop().animate({left:'5px'},300,'easeOutQuad');
			$('#header_controls_right').stop().animate({right:'5px'},300,'easeOutQuad');
		}})
		
		$('#header_controls_right').click(function(event){animate_header('right',0);clearInterval(interval_header);})	
		$('#header_controls_left').click(function(event){animate_header('left',0);clearInterval(interval_header);})
		
		function animate_header(direction,project){
		if (!$.browser.msie) {
			$('#header_images > .header_image:nth-child('+current_project+')').stop().animate({opacity:'0',marginLeft:'-100px',marginTop:'-50px',width:'1200px',height:'740px'},250,'easeInQuad', function(){
			$(this).css({marginLeft:'0px',marginTop:'0px',width:'960px',height:'592px'})				
						
			if(direction == 'logo'){current_project = project};
			if(direction == 'left'){current_project--};
			if(direction == 'right'){current_project++};
			if(current_project>header_count){current_project=1};
			if(current_project<1){current_project=header_count};
								
			$('#header_images > .header_image:nth-child('+current_project+')').css({marginLeft:'100px',marginTop:'50px',width:'760px',height:'469px'});
			$('#header_images > .header_image:nth-child('+current_project+')').stop().animate({opacity: '1',marginLeft:'0',marginTop:'0',width:'960px',height:'592'},250,'easeOutQuad');
			});
		}
		else{
			$('#header_images > div:nth-child('+current_project+')').stop().animate({height: '0px'},100,'easeInQuad', function(){

			if(direction == 'logo'){current_project = project};
			if(direction == 'left'){current_project--};
			if(direction == 'right'){current_project++};
			if(current_project>header_count){current_project=1};
			if(current_project<1){current_project=header_count};
			
			$('#header_images > div:nth-child('+current_project+')').stop().animate({height: '592px'},250,'easeInQuad');
			});
		}
		}

		var interval_header = setInterval(timerFunction, 4000);
		
		function timerFunction(){
		animate_header('right',0);
		}
}