app.factory('personService', ['$http', function($http){

	var baseUrl = 'persons';
	var personService = {};

	personService.getPersons = function(){
		return $http.get(baseUrl);
	};

	personService.getPersonById = function(id){
		return $http.get(baseUrl + '/' + id);
	};

	personService.insertPerson = function(per){
		return $http.post('registration', per);
	};

	personService.checkUsername = function(per){
		return $http.post('checkUsername', per);
	};

	personService.checkEmail = function(per){
		return $http.post('checkEmail', per);
	};

	personService.checkPhone = function(per){
		return $http.post('checkPhone', per);
	};

	personService.login = function(per){
		return $http.post('do-login', per);
	};
	personService.getCurrUser = function(){
		return $http.get('currentuser');
	};

	personService.resetPswdReq = function(ch){
		return $http.post('changePswdReq', ch);
	};

	personService.resetPassword = function(s){
		return $http.post('resetPass', s);
	};

	personService.logout = function(){
		return $http.get('logoutUser');
	};

	personService.updatePerson = function(person){
		return $http.put('persons/'+ person.id, person);
	}

	personService.validPerson = function(id){
		return $http.get('valid/' + id);
	}

	personService.checkPassword = function(person){
		return $http.post('check_password', person);
	}
	
	personService.sendEmail = function(ltr) {
		return $http.post('send_email', ltr);		
	}

	return personService;
}]);
