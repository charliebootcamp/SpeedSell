app.controller('UserLotsController',function($scope, $routeParams, $interval, accountService, personService){
	var ob = this;
	ob.lots;
	ob.userid = $routeParams.userid;
	ob.person;
	ob.timer = 0;

	$scope.$on('$destroy',function(){
		if(ob.timer)
				$interval.cancel(ob.timer);
	});
	var timerFunc = function(){
		for(i=0;i<ob.lots.length;i++){
			ob.lots[i].timeLeft = getTimeLeft(ob.lots[i]);
		}
	};
	ob.timer = $interval(timerFunc,1000);
	personService.getPersonById(ob.userid).then(function(d){
		ob.person = d.data;
	})
	accountService.getAccountLots(ob.userid, 3).then(function(d){
		ob.lots = d;
		timerFunc();
	}, function(error){
		location.href = '#/';
	});
});
