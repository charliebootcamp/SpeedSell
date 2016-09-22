app.controller('UserFeedbacksController', function($scope, $routeParams,
		$localStorage, feedbacksService, personService) {

	var ob = this;
	ob.userid = $routeParams.userid;
	ob.feedbacks = [];
	ob.person;
	ob.statistics = [];
	feedbacksService.getFeedbacks(ob.userid).then(function(d) {
		ob.feedbacks = d.data;
		for (i = 0; i < ob.feedbacks.length; i++) {
			ob.feedbacks[i].mark = getMark(ob.feedbacks[i].markId);
		}
	}, function(error){
		location.href = '#/';
	});
	personService.getPersonById(ob.userid).then(function(d) {
		ob.person = d.data;
	})
	feedbacksService.getStatByPersonId(ob.userid).then(function(s) {
		ob.statistics = s.data;
		ob.statistics[0].class = "danger";
		ob.statistics[1].class = "success";
		ob.statistics[2].class = "warning";
	})

	ob.sendQuestion = function() {
		if ($localStorage.curruser != null) {
			$localStorage.recEmail = ob.person.email;
			$localStorage.title = "Question from user";
			location.href = '#/question';
		} else {
			if (confirm("You must be logged in. Login?")) {
				location.href = '#/login';
			}
		}
	}
});
