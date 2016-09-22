app.controller("LotsController", function(lotsService, $interval, categoryService, $scope, $routeParams) {
				var ob = this;
				ob.lots = [];
				ob.limitedLots = [];
				$scope.currentPage = 1;
			    $scope.totalItems = 0;
			    $scope.maxSize = 5;
			    $scope.itemPerPage = 9;
			    $scope.startItems = (( $scope.currentPage-1)* $scope.itemPerPage);
				ob.cat = {};
				ob.subcat = {};
				ob.iscat;
				ob.timer = 0;

				$scope.$on('$destroy',function(){
					if(ob.timer)
							$interval.cancel(ob.timer);
				});
				var timerFunc = function(){
					for(i=0;i<ob.limitedLots.length;i++){
						ob.limitedLots[i].timeLeft = getTimeLeft(ob.limitedLots[i]);
					}
				};
				ob.timer = $interval(timerFunc,1000);

				
				
				
				function getSubLot(){
					categoryService.getCategoryById($routeParams.cat).then(function(d){
						ob.subcat = d.data;
						categoryService.getCategoryParent($routeParams.cat).then(function(d){
							ob.cat = d.data;
							ob.iscat = !isEmpty(ob.cat);
							categoryService.getCategoryById($routeParams.cat).then(function(d){
								//need for lot.jsp
								ob.subcat = d.data;
							});
							if (typeof ob.subcat.subcategories !== 'undefined'){
							for (var i=0;i<ob.subcat.subcategories.length;i++){
								lotsService.getLotsByCategory(ob.subcat.subcategories[i].id).then(function(d){
									var tmp = d.data;
									ob.lots = ob.lots.concat(tmp);
									$scope.totalItems = ob.lots.length;
									});	

                            }
							}

				})})}
				getSubLot();
				$scope.changePage = function (){
			    $scope.startItems = (( $scope.currentPage-1)* $scope.itemPerPage);
				categoryService.getCategoryById($routeParams.cat).then(function(d){
						ob.subcat = d.data;
						categoryService.getCategoryParent($routeParams.cat).then(function(d){
							ob.cat = d.data;
							ob.iscat = !isEmpty(ob.cat);
							categoryService.getCategoryById($routeParams.cat).then(function(d){
								//need for lot.jsp
								ob.subcat = d.data;
							});
						
							if (!ob.iscat && ob.subcat.subcategories !== undefined){
								for (var i=0;i<ob.subcat.subcategories.length;i++){
                                   // getSubLot(i);	

                                }
								//$scope.totalItems = ob.lots.length;
								lotsService.getLimitedLotsBySCategory($routeParams.cat, $scope.startItems,  $scope.itemPerPage).then(function(l){
									ob.limitedLots = l.data;
									timerFunc();
								});
								/*
								for (var i=0;i<ob.subcat.subcategories.length;i++){
									getSubLot(i);											
								}*/
								
								/*
								function getSubLimitedLot(i){
									lotsService.getLimitedLotsByCategory(ob.subcat.subcategories[i].id, $scope.startItems,  $scope.itemPerPage).then(function(l){
										var tmp = l.data;
										ob.limitedLots = ob.limitedLots.concat(tmp);
										timerFunc();
									});
								}*/

							}
							else {
								lotsService.getLotsByCategory($routeParams.cat).then(function(d){
									ob.lots = d.data;
									$scope.totalItems = ob.lots.length;
									});
								lotsService.getLimitedLotsByCategory($routeParams.cat, $scope.startItems,  $scope.itemPerPage).then(function(l){
									ob.limitedLots = l.data;
									timerFunc();
								});
							}
						});
				}, function(error){
					location.href = '#/';
				});
			};
			$scope.changePage();
		});
