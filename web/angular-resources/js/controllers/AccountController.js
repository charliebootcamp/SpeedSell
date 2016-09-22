app.controller("AccountController", function($scope, $route, $localStorage, $interval, $http, accountService,
	bidsService, feedbacksService, uActivitiesService, lotService, categoryService) {
	var ob = this;
	ob.lots = [];
	ob.feedback = {	title :"", markId : 0, recipientId : 0, senderId : 0, lotId : 0, feedback : "", submitDate : 0};
	ob.lot = { id : 0, duration : 0};
	ob.duration;
	ob.timer = 0;
	ob.cardNumb;
	ob.cvc;
	ob.expMonth;
	ob.expYear;
	ob.cur;
	ob.f;


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

	ob.loadActiveOrders = function(){//$localStorage.curruser.id != undefined
		if($localStorage.curruser != null){
			if($localStorage.curruser.id != undefined){
				accountService.getActiveOrders($localStorage.curruser.id).then(function(d) {
					ob.lots = d;
					timerFunc();
					for(i=0;i<ob.lots.length;i++){
						ob.getBids(i);
					}
				});
			}
		}else{
			console.log("Not logged");
		}
	}

	ob.getBids = function(lotid){
		bidsService.getBidsByLotId(ob.lots[lotid].id).then(function(d){
			d.data.reverse();
			for(i=0;i<d.data.length;i++){
				if(d.data[i].bidder.id == $localStorage.curruser.id){
					ob.lots[lotid].lastUserBid = d.data[i];
					return ;
				}
			}
		})
	}

	ob.loadOrders = function(stateId){
		if($localStorage.curruser != null){
			if($localStorage.curruser.id != undefined){
				accountService.getOrders($localStorage.curruser.id,stateId).then(function(d) {
					ob.lots = d;
					timerFunc();
					for(i=0;i<ob.lots.length;i++){
						ob.getBids(i);
					}
				});
			}
		}else{
			console.log("Not logged");
		}
	}

	ob.loadLots = function(stateId){
		if($localStorage.curruser != null){
			if($localStorage.curruser.id != undefined){
				accountService.getAccountLots($localStorage.curruser.id,stateId).then(function(d){
					ob.lots = d;
					timerFunc();
					for(i=0;i<ob.lots.length;i++){
						ob.getBids(i);
					}
				});
			}
		}else{
			console.log("Not logged");
		}
	}
	ob.deleteLot = function(id){
		lotService.deleteLot(id).then(function(d){
			location.reload();
		});
	}

	ob.pay = function(){
		if (ob.f == 1){
			ob.payCommission();
		} else {
			ob.askPremium();
		}
	}
	ob.payCommission = function(){
		var lotid =ob.cur.id;
		if($localStorage.curruser != null && lotid != null){
			accountService.payCommission($localStorage.curruser.id ,lotid, ob.cardNumb, ob.cvc, ob.expMonth, ob.expYear).then(function(d){
				if (d.status == 206){
					ob.suc = true;
				} else ob.suc = false;
				angular.element('#payform').modal('hide');
				ob.pageReload();
			});
		}
	}

	ob.askPremium = function(){
		var lotid =ob.cur.id;
		if($localStorage.curruser != null && lotid != null){
			accountService.askPremium($localStorage.curruser.id ,lotid, ob.cardNumb, ob.cvc, ob.expMonth, ob.expYear).then(function(d){
				if (d.status == 200){
					ob.suc = true;
				} else ob.suc = false;
				angular.element('#payform').modal('hide');
				ob.pageReload();
			});
		}
	}

	ob.confirm = function(lotid){
		accountService.confirmLot(lotid).then(function(d){
			location.reload();
		});
	}

	ob.sendError = function(message) {
		ob.message = message;
		ob.alertType = "danger";
		ob.title = "Error!";
		ob.showMessage = true;
	}

	ob.sendOk = function(message) {
		ob.message = message;
		ob.alertType = "success";
		ob.title = "Success!";
		ob.showMessage = true;
	}

	ob.showModalFeedback = function(id,senderId,recipientId){
		if($localStorage.curruser != null){
				//calling modal window feedback
				ob.feedback.title = "";
				ob.feedback.markId = 0;
				ob.feedback.senderId = senderId;
				ob.feedback.recipientId = recipientId;
				ob.feedback.lotId = id;
				ob.feedback.feedback = "";
				ob.showMessage = false;
				angular.element('#sendFeedback').modal('show');
		}else{
			console.log("Not logged");
			location.href = '#/login';
		}
	}

	ob.showModalEdit = function(lot){
		if($localStorage.curruser != null){
			//calling modal window feedback
			categoryService.getCategories().then(
					function(response) {
						ob.categories = response.data;
					},
					function(error) {
						ob.status = 'Unable to load categories data: '
							+ error.message;
			});
			ob.editLot = {};
			ob.editLot.id = lot.id;
			ob.editLot.info = lot.info;
			ob.editLot.name = lot.name;
			ob.editLot.startPrice = lot.startPrice;
			ob.editLot.redemption = lot.redemption;
			ob.editLot.categoryId = null;
			ob.editLot.duration = 24;
			ob.myFile;
			ob.showMessage = false;
			angular.element('#editLot').modal('show');
		}else{
			console.log("Not logged");
			location.href = '#/login';
		}
	}

	ob.pageReload = function(){
		var timeoutID = window.setTimeout(function(){
			$route.reload();
		}, 2500);
	}

	ob.sendEditedLot = function (categoryId){
		angular.element('#editLot').modal('hide');
		if(categoryId != undefined && categoryId != null){
			ob.editLot.categoryId = categoryId;
		}
		if(ob.editLot.startPrice < 1){
			ob.editLot.startPrice = 1;
		}

		ob.editLot.duration = ob.editLot.duration * 3600000;
		if (!isEmpty(ob.myFile)){

		$http({
    	    method: 'POST',
    	    url: 'lotdata/images',
    	    headers: {'Content-Type': undefined },
    	    transformRequest: function (data) {
    	        var formData = new FormData();

    	        formData.append("file", data.file);
    	        return formData;
    	    },
    	    data: { file: ob.myFile}
    	})
        .success(function(response){
        	edit(response.path);
        })
        .error(function(error){
        	console.log(error);
        	ob.sendError("Error in image saving. Please try later.");
        	});
		} else edit('');
	
		function edit(path) {
			if(!isEmpty(path)) ob.editLot.img = path;
			lotService.editLot(ob.editLot).then(function successCallback(response){
		
			var uActivity = {
				personId : $localStorage.curruser.id,
				dateOf : new Date().getTime(),
				info : "User edit canceled lot = " + ob.editLot.id,
				typeId : 22
			}
			uActivitiesService.addUserActivities(uActivity);
			ob.sendOk("Your lot was successfully edited and sended dor moderation.");
			ob.pageReload();
		}, function errorCallback(response) {
			ob.sendError("Something went wrong. Please try later.");
		});
		}
	}

	ob.showModalPlaceAgain = function(id){
		if($localStorage.curruser != null){
				ob.lot.id = id;
				ob.duration = 24;
				angular.element('#placeAgain').modal('show');
		}else{
			console.log("Not logged");
			location.href = '#/login';
		}
	}

	ob.placeAgain = function(dur){
		angular.element('#placeAgain').modal('hide');
				var lot = {
					id : ob.lot.id,
					duration : dur * 3600000,
					stateId : 1
				}
				var uActivity = {
					personId : $localStorage.curruser.id,
					dateOf : new Date().getTime(),
					info : "User send request to place again lot = " + lot.id,
					typeId : 6
				}
				lotService.editLot(lot).then(function successCallback(response) {
					uActivitiesService.addUserActivities(uActivity);
					ob.sendOk("Your request was sent to administrator. Wait for confirmation.");
					ob.pageReload();
			}, function errorCallback(response) {
				ob.sendError("Wrong start Date. Value must be greater than current time.");
			});
	}

	ob.createFeedback = function(feedback){
		if(ob.feedback.feedback.length < 10){
			ob.sendError("Your feedback is too small.");
		}else if (ob.feedback.title.length < 10) {
			ob.sendError("Your title is too small.");
		}else {
			feedback.submitDate = new Date().getTime();
			var uActivity = {
				personId : feedback.senderId,
				dateOf : feedback.submitDate,
				info : "User leaved feedback for user id = " + feedback.recipientId,
				typeId : 8
			}
			feedbacksService.createFeedback(feedback).then(function successCallback(response) {
				uActivitiesService.addUserActivities(uActivity);
				ob.showOk("Your feedback was sent.");
			}, function errorCallback(response) {
				ob.sendError("You already write one feedback.");
			});
		}
	}

	ob.sendQuestion = function(email, lot){
		if($localStorage.curruser != null){
			$localStorage.recEmail = email;
			$localStorage.title = lot;
			location.href = '#/question';
		}else{
			if(confirm("You must be logged in. Login?")){
				location.href = '#/login';
			}
		}
	}

	ob.checkNum = function(num){
		if (num > 72) {
			return 72;
		} else if (num < 24) {
			return 24;
		} else {
			return num;
		}
	}
});
