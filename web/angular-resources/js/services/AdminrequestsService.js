app.factory('adminrequestsService', function($http){
	var baseUrl = "state";
	var canceled = "0";
	var accepted = "2";
  var adminrequestsService = {};
  adminrequestsService.getPendingRequests = function() {
      var promise = $http.get("lotsrequests").then(function(response){
        return response.data;
      });
      return promise;
    }

  adminrequestsService.getAcceptedRequests = function() {
      var promise = $http.get(baseUrl + "/" + accepted).then(function(response){
        return response.data;
      });
      return promise;
    }

  adminrequestsService.getDeniedRequests = function() {
	  var promise = $http.get(baseUrl + "/" + canceled).then(function(response){
		  return response.data;
	  });
	  return promise;
  }
  
    adminrequestsService.getPremiumRequests = function() {
      return $http.get("premium/requests");
    }
    
    adminrequestsService.makePremium = function(id) {
    	console.log(id);
    	return $http.put("premium", id);
    }


  return adminrequestsService;
});
