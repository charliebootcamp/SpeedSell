app.factory('feedbacksService', function($http){
	var feedbacksService = {};
	feedbacksService.getFeedbacks = function(personId) {
		return $http.get("persons/" + personId + "/feedbacks");
   }
	 feedbacksService.createFeedback = function(feedback){
		 return $http.post("feedbackdata",feedback);
	 }
	 feedbacksService.getStatByPersonId = function(id){
		 return $http.get('stat/' + id);
	 }
	return feedbacksService;
  });
