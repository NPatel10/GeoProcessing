//alert("Hello");
//for side panels
var left_toggle = 0;
var right_toggle = 0;
$(document).ready(function(){
	// for left panel
	$("#left_slideout").on("click", function(){
		if(left_toggle == 0){
			$("#left_slideout").css("left", "260px");
			$("#left_slideout").css("z-index", "10");
			$("#left_slideout_inner").css("z-index", "10");
			$("#left_slideout_inner").css("left", "0px");
			$("#left_icon").attr("class", "glyphicon glyphicon-menu-left");
			left_toggle = 1;
		}
		else if (left_toggle == 1){
			$("#left_slideout").css("left", "0px");
			$("#left_slideout").css("z-index", "0");
			$("#left_slideout_inner").css("z-index", "0");
			$("#left_slideout_inner").css("left", "-260px");
			$("#left_icon").attr("class", "glyphicon glyphicon-menu-right");
			left_toggle = 0;
		}
	});

	// for right panel
	$("#right_slideout").on("click", function(){
		if(right_toggle == 0){
			$("#right_slideout").css("right", "260px");
			$("#right_slideout_inner").css("right", "0px");
			$("#right_icon").attr("class", "glyphicon glyphicon-menu-right");
			right_toggle = 1;
		}
		else if (right_toggle == 1){
			$("#right_slideout").css("right", "0px");
			$("#right_slideout_inner").css("right", "-260px");
			$("#right_icon").attr("class", "glyphicon glyphicon-menu-left");
			right_toggle = 0;
		}
	});
});



// for login and signup form validation.
$(document).ready(function(){
	// for login form
	$("#login_login").on("click", function(){
		// for password
		var login_pass = $("#login_pass").val();
		if(login_pass == ""){
			$('#login_pass').popover("show");
			$("#login_pass_div").attr("class", "form-group has-error has-feedback");
		}else{
			$('#login_pass').popover("hide");
			$("#login_pass_div").attr("class", "form-group");
		}

		// for email
		var login_email = $("#login_email").val();
		if(login_email == ""){
			$('#login_email').popover("show");
			$("#login_email_div").attr("class", "form-group has-error has-feedback");
		}
		else if(!validateEmail(login_email)){
			$('#login_email').popover("show");
			$("#login_email_div").attr("class", "form-group has-error has-feedback");
		}else{
			$('#login_email').popover("hide");
			$("#login_email_div").attr("class", "form-group");
		}
	});
	// for sign up form
	$("#signup_signup").on("click", function(){
		// for username
		var signup_uname = $("#signup_uname").val();
		if(signup_uname == "" || signup_uname.length < 3 || signup_uname.length > 10){
			$('#signup_uname').popover("show");
			$("#signup_uname_div").attr("class", "form-group has-error has-feedback");
		}else{
			$('#signup_uname').popover("hide");
			$("#signup_uname_div").attr("class", "form-group");
		}
		// for email
		var signup_email = $("#signup_email").val();
		if(signup_email == ""){
			$('#signup_email').popover("show");
			$("#signup_email_div").attr("class", "form-group has-error has-feedback");
		}
		else if(!validateEmail(signup_email)){
			$('#signup_email').popover("show");
			$("#signup_email_div").attr("class", "form-group has-error has-feedback");
		}else{
			$('#signup_email').popover("hide");
			$("#signup_email_div").attr("class", "form-group");
		}

		// for password and re entered password
		var signup_pass = $("#signup_pass").val();
		var signup_repass = $("#signup_repass").val();
		if(signup_pass == ""){
			// $('#signup_pass').popover({trigger: "manual"});
			$('#signup_pass').popover("show");
			$("#signup_pass_div").attr("class", "form-group has-error has-feedback");
		}else if(signup_repass == ""){
			$("#signup_repass").attr("data-content", "Enter Password.");
			$('#signup_repass').popover("show");
			$("#signup_repass_div").attr("class", "form-group has-error has-feedback");
		}else if(signup_pass != signup_repass){
			$("#signup_repass").attr("data-content", "Passwords are not Matching.");
			$('#signup_repass').popover("show");
			$("#signup_pass_div").attr("class", "form-group has-error has-feedback");
			$("#signup_repass_div").attr("class", "form-group has-error has-feedback");
		}else if(signup_pass != ""){
			$('#signup_pass').popover("hide");
			$("#signup_pass_div").attr("class", "form-group");
		}else if(signup_repass != ""){
			$('#signup_repass').popover("hide");
			$("#signup_repass_div").attr("class", "form-group");
		}
	});
});
// emial validation function.
function validateEmail(email) {
	var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return re.test(email);
}