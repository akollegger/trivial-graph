$(document).ready(function() {

 		$.preloadCssImages({ 'imgDir': 'images' });

		$(".dropdown li").hover(function(){
			var dropDown = $(this).children("ul");
			var ulWidth = dropDown.width();
			var liWidth = $(this).width();
			var posLeft = (liWidth - ulWidth)/2 + 10;
			dropDown.css("left",posLeft);		
		});	
		
		$(".dropdown ul").parent("li").addClass("parent");
		$(".dropdown li:first-child").addClass("first");
		$(".dropdown li:last-child").addClass("last");
		$(".dropdown li:only-child").removeClass("last").addClass("only");		

		$("ul.tabs").tabs("> .tabcontent", {
			tabs: 'li', 
			effect: 'fade'
		});
		
	$(".recent_posts li:odd").addClass("odd");
	$(".popular_posts li:odd").addClass("odd");
	$(".widget-container li:even").addClass("even");

// cols
	$(".row .col:first-child").addClass("alpha");
	$(".row .col:last-child").addClass("omega"); 	

// toggle content
	$(".toggle_content").hide(); 
	
	$(".toggle").toggle(function(){
		$(this).addClass("active");
		}, function () {
		$(this).removeClass("active");
	});
	
	$(".toggle").click(function(){
		$(this).next(".toggle_content").slideToggle(300,'easeInQuad');
	});
	
	$(".table-price tr:even").addClass("even");

// gallery
	$(".gl_col_2 .gallery-item::nth-child(2n)").addClass("nomargin");
	
// pricing
	$(".table-pricing td.table-row-title:even").addClass("even");

// buttons	
	if (!$.browser.msie) {
		$(".button_styled, .button_link, .btn-submit, .button_large").hover(function(){
			$(this).stop().animate({"opacity": 0.8});
		},function(){
			$(this).stop().animate({"opacity": 1});
		});
	}	

// scroll to anchor	
	$(".topmenu a[href^='#']").click(function(event){
		event.preventDefault();
		
		var full_url = this.href;
		var parts = full_url.split("#");
		var trgt = parts[1];
		
		var target_offset = $("#"+trgt).offset();
		var target_top = target_offset.top - 94;
		
		$('html, body').animate({scrollTop:target_top}, 700);
	});

});

$(function () {  
     $(window).scroll(function () {  
         if ($(this).scrollTop() != 0) {  
             $('.link-top').fadeIn();  
         } else {  
             $('.link-top').fadeOut();  
         }  
     });  
     $('.link-top').click(function () {  
         $('body,html').animate({  
             scrollTop: 0  
         },  
         1500);  
     });  
 });
 
