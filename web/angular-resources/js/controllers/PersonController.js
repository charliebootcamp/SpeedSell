app.controller('PersonController',
function($scope, $route, personService, $localStorage, uActivitiesService){
	var p = this;
	p.persons;//
	p.username;//
	p.email;//
	p.phone;//
	p.pass;//
	p.passConfirm;
	p.fullname;//
	p.address;//
	p.success = false;//
	p.success2 = false;
	p.showAlert = false;
	p.newPass;
	p.h;
	p.person;
	p.alertType = "danger";
	p.title = "";
	p.valid = false;
	p.validEmail = false;
	p.validPhone = false;
	p.validUsername = false;

	p.settingsInit = function () {
		if($localStorage.curruser != null){
			if($localStorage.curruser.id != undefined){
				personService.getPersonById($localStorage.curruser.id).then(function(d){
					p.person = d.data;
				})
			}
		}else{
			console.log("Not logged");
			location.href = '#/login';
		}
	}

	p.sendMessage = function(message) {
		p.message = message;
		p.alertType = "danger";
		p.title = "Error!";
		p.showMessage = true;
	}

	p.sendInfo = function(message) {
		p.message = message;
		p.alertType = "info";
		p.title = "Attention!";
		p.showMessage = true;
	}

	p.sendOk = function(message) {
		p.message = message;
		p.alertType = "success";
		p.title = "Success!";
		p.showMessage = true;
	}

	p.updatePerson = function() {
		p.checkPassword();
		p.checkEmail();
		p.checkPhone();
		if(p.valid){
			var per = {
				id : $localStorage.curruser.id,
				email : p.email,
				passwordHash : p.newPass,
				phone : p.phone,
				homeAddress : p.address,
				fullName : p.fullname
			};
			personService.updatePerson(per).then(function(response){
				p.sendOk("Successfully updated your data");
				if(p.email != null){
					var timer = 5;
					p.sendInfo("Your email was changed. We sent you letter on this new email. Please verify it. You will be automatically logged out in " + timer + " sec.");
					//click on element on index page to logout
					var count = window.setTimeout(function(){
						while(timer != 0){
							timer--;
						}
					}, 1000);
					var timeoutID = window.setTimeout(function(){
						angular.element("#Logout").click();
					}, 5000);
				};
			}, function(error){
				p.sendMessage(error.statusText);
			});
		}
	};
	
	p.pageReload = function() {
		var timeoutID = window.setTimeout(function(){
			$route.reload;
		}, 1000);
	}

	p.checkUsername = function() {
		if (p.username != undefined && p.username != null && p.username != ""){
			var per = {
					name : p.username
				};
				personService.checkUsername(per).then(
					function(response){
						p.valid = true;
						p.validUsername = true;	
						p.showMessage = false;		
					},
					function(error){
						p.valid = false;
						p.sendMessage(error.data.message);
						p.validUsername = false;		
					});
		}else{
			p.username = null;		
			p.validUsername = false;	
		}
		
	};

	p.comparePass = function(pass1, pass2){
		if(pass1 != pass2){
			p.sendMessage("Passwords do not match.");
			p.valid = false;			
		}
		else{
			p.valid = true;			
		}
	};

	p.checkEmail = function() {
		if (p.email != undefined && p.email != null && p.email != ""){
			var per = {
				email : p.email
			};
			personService.checkEmail(per).then(
				function(response){
					p.valid = true;
					p.validEmail = true;
					p.showMessage = false;					
				},
				function(error){
					p.valid = false;
					p.sendMessage(error.data.message);
					p.validEmail = false;
				});
		}else{
			p.email = null;
			p.validEmail = false;
		}
	};

	p.checkPhone = function() {
		if (p.phone != undefined && p.phone != null && p.phone != ""){
		var per = {
			phone : p.phone
		};
		personService.checkPhone(per).then(
			function(response){
				p.valid = true;
				p.validPhone = true;
				p.showMessage = false;
			},
			function(error){
				p.sendMessage(error.data.message);
				p.valid = false;
				p.validPhone = false;
			});
		}
		else{
			p.phone = null;
			p.validPhone = false;
		}
	};

	p.checkPassword = function() {
		if (p.pass != undefined && p.pass != null && p.pass != ""){
		var per = {
			id : $localStorage.curruser.id,
			passwordHash : p.pass
		};
		personService.checkPassword(per).then(
			function(response){
				p.valid = true;				
			},
			function(error){
				p.sendMessage(error.data.message);
				p.valid = false;				
			});
		}else {
			p.pass = null;			
		}
	}

	function getPersons() {
		personService.getPersons().then(
			function(response) {
				p.persons = response.data;
			},
			function(error) {
				p.status = 'Unable to load persons data: '
				+ error.message + error.statusText;
			});
		}

	p.insertPerson = function() {
		p.checkEmail();
		p.checkPhone();
		p.checkUsername();
		if(p.validUsername && p.validPhone && p.validEmail){		
			var per = {
					name : p.username,
					email : p.email,
					passwordHash : p.pass,
					phone : p.phone,
					fullName: p.fullname,
				};
				personService.insertPerson(per).then(function(response){
					p.success = true;
				}, function(error){

				});
		}
		else{
			console.log(p.validUsername);
			console.log(p.validPhone);
			console.log(p.validEmail);
		}
		
	};

	p.resetPassword = function(h){
		if(p.valid){
			var s = {
				passwordChangeCode : h,
				passwordHash : p.newPass
			};
			personService.resetPassword(s).then(function(response){
				location.href = '#/login';
			}, function(error){
				p.sendMessage("Passwords are not same");
				console.log(error);
			});
		}else{
			p.newPass = null;
			p.passConfirm = null;
			p.sendMessage("Passwords are not same.");
		}
	};

	p.resetPswdReq = function(){
		var ch = {
			email : p.email
		};

		personService.resetPswdReq(ch).then(function(response){
			p.success2 = true;
		}, function(error){
		});
	};

	p.login = function(){
		var user = {
			email : p.email,
			passwordHash : p.pass
		};

		personService.login(user).then(function(response) {
			location.href = '#/myaccount/orders_active';
			location.reload();
		}, function(error) {
			p.sendMessage("Wrong username/password");
			console.log(error);
		});

	};

});
