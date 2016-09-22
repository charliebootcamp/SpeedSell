app.factory('uActivitiesService', [ '$http', function($http) {
	var baseUrl = 'uact';
	var uActivitiesService = {};
	uActivitiesService.getById = function(id) {
		return $http.get(baseUrl + id);
	}
	uActivitiesService.getAllUserActivities = function(){
		return $http.get(baseUrl);
	}
	uActivitiesService.getUserActivitiesByPersonId = function(id){
		return $http.get(baseUrl + id);
	}
	uActivitiesService.addUserActivities = function (uA){
		return $http.post(baseUrl, uA);
	}
	return uActivitiesService;
}]);
