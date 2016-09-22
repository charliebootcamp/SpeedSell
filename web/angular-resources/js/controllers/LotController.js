app.controller("LotController",
			function($scope, $routeParams, $route, $localStorage, $interval, lotService,
				bidsService, personService, categoryService, uActivitiesService) {
				var ob = this;
				ob.lot;
				ob.bids;
				ob.cat = {};
				ob.subcat = {};
				ob.newBid = 1;
				ob.lotId = $routeParams.lotid;
				ob.showMakeBid = false;
				ob.message = "";
				ob.alertMessage = "";
				ob.showMessage = false;
				ob.alertType = "error";
				ob.title = "Error!";
				ob.min = 1;
				ob.timer = 0;
				ob.suc;
				ob.cardNumb;
				ob.expMonth;
				ob.expYear;

				$scope.$on('$destroy',function(){
					if(ob.timer)
							$interval.cancel(ob.timer);
				});

				ob.pay = function(){
					if($localStorage.curruser != null && ob.lotId != null){
						lotService.buyLotNow($localStorage.curruser.id ,ob.lotId, ob.cardNumb, ob.cvc, ob.expMonth, ob.expYear).then(function(d){
							if (d.status == 206){
								ob.suc = true;
							} else ob.suc = false;
							var timeoutID = window.setTimeout(function(){
								location.reload();
							}, 500);
						});
					}
				}

				ob.sendMessage = function (title, type, message){
					ob.message = message;
					ob.alertType = type;
					ob.title = title;
					ob.showMessage = true;
				}

				//using for button Make Bid
				ob.verify = function(){
					if($localStorage.curruser != null){
							ob.alertMessage = "Are you sure you want to do this bid?"
							angular.element('#Alert').modal('show');
					}else{
						console.log("Not logged");
						location.href = '#/login';
					}
				}

				lotService.getLot(ob.lotId).then(function(d) {
					ob.lot = d;

					if(ob.lot.bestBid != null && ob.lot.bestBid.deleted != true){
						ob.newBid = ob.lot.bestBid.amount + 5;
						ob.min = ob.lot.bestBid.amount + 1;
					}else{
						ob.min = ob.lot.startPrice + 1;
						ob.newBid = ob.lot.startPrice + 1;
					}

					ob.lot.timeLeft = getTimeLeft(ob.lot);
					ob.timer = $interval(function(){
						if(ob.lot.timeLeft.total > 1000){
							ob.lot.timeLeft = getTimeLeft(ob.lot);
						}
					},1000);

					bidsService.getBidsByLotId(ob.lot.id).then(function(d) {
						d.data.reverse();
						ob.bids = d.data.slice(0,10);
					});

					categoryService.getCategoryById(ob.lot.subcategory.id).then(function(d){
						ob.subcat = d.data;
						categoryService.getCategoryParent(ob.lot.subcategory.id).then(function(d){
							ob.cat = d.data;
						});
					});
				}, function(error){
					location.href = '#/';
				});

				ob.pageReload = function(){
					var timeoutID = window.setTimeout(function(){
						$route.reload();
					}, 2500);
				}

				ob.insertBid = function(newBid) {
					//Preparing bid for insert to DB
					angular.element('#Alert').modal('hide');
						var bid = {
							amount : newBid,
							bidderId : $localStorage.curruser.id,
							lotId : ob.lot.id
						};
						bidsService.insertBid(bid).then(function(response) {
							ob.sendMessage("Successful!","success","You bid this lot successfully.");
							ob.pageReload();
						}, function(error) {
							ob.sendMessage("Error!","danger",error.data.message);
						});
				};

				ob.sendQuestion = function(){
					if($localStorage.curruser != null){
						$localStorage.recEmail = ob.lot.owner.email;
						$localStorage.title = ob.lot.name;
						location.href = '#/question';
					}else{
						if(confirm("You must be logged in. Login?")){
							location.href = '#/login';
						}
					}
				}

			});
