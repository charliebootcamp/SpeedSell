app.controller('FeedbacksController',
		function($scope,$localStorage, feedbacksService){
		if($localStorage.curruser.id  != undefined){
				var personId = $localStorage.curruser.id;//change when session will work
				var ob = this;
				ob.feedbacks = [];
				feedbacksService.getFeedbacks(personId).then(function(d){
					ob.feedbacks = d.data;
					for (i=0;i<ob.feedbacks.length;i++){
						ob.feedbacks[i].mark = getMark(ob.feedbacks[i].markId);
					}
				});
			}else{
				console.log("Not logged");
			}
});
