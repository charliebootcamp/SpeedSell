app.controller('HomeController',
    function($scope, $interval, lotsService){
        var ob = this;
        ob.lots = [];
        ob.timer = 0;
        $scope.viewby = 3;
        $scope.currentPage = 1;
        $scope.totalItems = 0;
        ob.limitLots = [];
        ob.premlots = [];
        $scope.maxSize = 5;
        $scope.itemPerPage = $scope.viewby;
        $scope.startItems = (( $scope.currentPage-1)* $scope.itemPerPage);
        
       
        var timerFunc = function(){
          for(i=0;i<ob.limitLots.length;i++){
            ob.limitLots[i].timeLeft = getTimeLeft(ob.limitLots[i]);
          }
        };
        ob.timer = $interval(timerFunc,1000);


        $scope.setItemsPerPage = function(num) {


           $scope.itemPerPage = num;
           $scope.currentPage = 1; //reset to first paghe
           $scope.changePage();
         }


        lotsService.getLots().then(function(d){
            ob.lots = d.data;
            $scope.totalItems = ob.lots.length;
        });

        lotsService.getPremium().then(function(i){

        			ob.premlots = i.data;
        			for(i=0;i<ob.premlots.length;i++){
                if(ob.premlots[i].bestBid != null){
                  ob.premlots[i].currentPrice = ob.premlots[i].bestBid.amount;
                }else{
                  ob.premlots[i].currentPrice = ob.premlots[i].startPrice;
                }
               }
        	});

         $scope.changePage = function () {
        	 $scope.startItems = (( $scope.currentPage-1)* $scope.itemPerPage);
        	 lotsService.getLimitedLots( $scope.startItems,  $scope.itemPerPage).then(function(l){
        			ob.limitLots = l.data;

              timerFunc();
             })
        }



        lotsService.getLimitedLots( $scope.startItems,  $scope.itemPerPage).then(function(l){

         ob.limitLots = l.data;

          timerFunc();
        })

        $scope.$on('$destroy',function(){
            if(ob.timer)
                $interval.cancel(ob.timer);
            });
     });