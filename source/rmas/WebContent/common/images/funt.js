// JavaScript Document
$(document).ready(function () {
	bdwhset();
	$(window).resize(bdwhset);
	$(".leftfrm").children("ul").children("li").click(function(){
		$(".leftfrm").children("ul").children("li").removeClass("sel");
		$(this).addClass("sel");
	});
	$(".leftfrm").children("ul").children("li").children("ul").children("li").click(function(){
		$(".leftfrm").children("ul").children("li").children("ul").children("li").removeClass("sel");
		$(this).addClass("sel");
	});
});
function bdwhset(){
	$(".leftfrm").height($(window).height()-94);
	$(".rightfrm").height($(window).height()-94);
	$(".rightfrm").width($(window).width()-193);
	}