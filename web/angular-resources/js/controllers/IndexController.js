app.controller('IndexController', function($scope, $route, uActivitiesService, personService, $localStorage, lotsService, searchingService, $location){
		var ic = this;
		ic.isAdmin = false;
		ic.isGuest = true;
		ic.user = null;
		
		$localStorage.curruser = null;
		personService.getCurrUser().then(
				function(d) {
					if (!isEmpty(d.data)){
//					ic.curruser = d.data;
					$localStorage.curruser = d.data;
					personService.getPersonById($localStorage.curruser.id).then(
						function(p){
							ic.user = p.data;
						}
					)
					if ($localStorage.curruser.typeId == 1){
						ic.isAdmin=true;
					}
					ic.isGuest = false;
//					if ($localStorage.curruser.typeId == 1){
//						$localStorage.isAdmin=true;
//					}
//					$localStorage.isGuest = false;
					}
				},
				function(error) {
					error.status = 'Unable to load persons data: '
					+ error.message;
				}

		);

		ic.logout = function() {
			var uActivity = {
				personId : $localStorage.curruser.id,
				dateOf : new Date().getTime(),
				info : "User logged out",
				typeId : 3
			}
			personService.logout().then(
				function(d) {
					uActivitiesService.addUserActivities(uActivity);
					ic.isGuest = true;
					ic.isAdmin = false;
					$localStorage.curruser = null;
					location.href = '#/login';
				},
				function(error) {
					error.status = 'Unable to load persons data: '
					+ error.message;
				}

		);
		};

		ic.Search = function(nameLot){
			searchingService.clearLots();
			searchingService.setName(nameLot);
			ic.searchInput = '';

			if($location.url()!=="/searching"){
					location.href = "#/searching";

			} else {
					$route.reload();
			}
		};

		ic.sendFeedback = function(){
			if($localStorage.curruser != null){
				$localStorage.recEmail = "speedsellauction@gmail.com";
				$localStorage.title = "Feedback from user";
				if($route.current.loadedTemplateUrl != "question/"){
					location.href = '#/question';
				}else{
					$route.reload();
				}
			}else{
				if(confirm("You must be logged in. Login?")){
					location.href = '#/login';
				}
			}
		}
	})
