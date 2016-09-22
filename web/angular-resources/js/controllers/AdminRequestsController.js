app.controller("AdminRequestsController",
		function($scope,$route, $localStorage, adminrequestsService, lotService,  uActivitiesService) {
			var ob = this;
			ob.pendingrequests = [];
			ob.acceptedrequests = [];
			ob.deniedrequests = [];
			ob.premium = [];
			ob.state = [];

			adminrequestsService.getPendingRequests().then(function(d){
				ob.pendingrequests = d;
			});

			adminrequestsService.getAcceptedRequests().then(function(d){
				ob.acceptedrequests = d;
			});

			adminrequestsService.getDeniedRequests().then(function(d){
				ob.deniedrequests = d;
			});
			
			adminrequestsService.getPremiumRequests().then(function(d){
				ob.premium = d.data;
			});

			ob.makePremium = function(id){
				adminrequestsService.makePremium(id).then(function(d){
					var timeoutID = window.setTimeout(function(){
						$route.reload();
					}, 500);
				});
			}

			ob.denyItem = function (x) {
				var tmpindex1 = ob.pendingrequests.indexOf(x);
				var tmpindex2 = ob.acceptedrequests.indexOf(x);
				var lot = new Object();
				if (tmpindex1 > -1){

					ob.pendingrequests[tmpindex1].state.id = 0;
					lot.id = ob.pendingrequests[tmpindex1].id;
					lot.stateId = ob.pendingrequests[tmpindex1].state.id;

				}
				else if (tmpindex2 > -1){
					ob.acceptedrequests[tmpindex2].state.id = 0;
					lot.id = ob.acceptedrequests[tmpindex2].id;
					lot.stateId = ob.acceptedrequests[tmpindex2].state.id;
				}
				var uActivity = {
					personId : $localStorage.curruser.id,
					dateOf : new Date().getTime(),
					info : "Admin denied lot id = " + lot.id,
					typeId : 10
				}
				lotService.updateLot(lot).then(function(d){
					uActivitiesService.addUserActivities(uActivity);
				});
				var timeoutID = window.setTimeout(function(){
					$route.reload();
				}, 500);
			}
			ob.acceptItem = function (x) {
				var tmpindex1 = ob.pendingrequests.indexOf(x);
				var tmpindex2 = ob.deniedrequests.indexOf(x);
				var lot = new Object();
				if (tmpindex1 > -1){
					ob.pendingrequests[tmpindex1].stateId = 2;
					lot.id = ob.pendingrequests[tmpindex1].id;
					lot.stateId = ob.pendingrequests[tmpindex1].stateId;
				}
				else if (tmpindex2 > -1){
					ob.deniedrequests[tmpindex2].stateId = 2;
					lot.id = ob.deniedrequests[tmpindex2].id;
					lot.stateId = ob.deniedrequests[tmpindex2].stateId;
				}
				var uActivity = {
					personId : $localStorage.curruser.id,
					dateOf : new Date().getTime(),
					info : "Admin accepted lot id = " + lot.id,
					typeId : 9
				}
				lotService.updateLot(lot).then(function(d){
					uActivitiesService.addUserActivities(uActivity);
				})
				var timeoutID = window.setTimeout(function(){
					$route.reload();
				}, 500);
			}
		});
