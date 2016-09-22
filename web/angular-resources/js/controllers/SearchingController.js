app.controller("SearchingController", function(lotsService, categoryService, searchingService, $scope, $interval, $routeParams, $route) {
	var sc = this;
	sc.lots=[];
	sc.categories=[];
	sc.lotsEmpty;
	sc.timer = 0;
	sc.fromPrice= searchingService.getFromPrice();;
	sc.toPrice= searchingService.getToPrice();
	sc.buyNow= searchingService.isRedemption();
	var count = 0;
	var pageSlicer = 6;


	$scope.$on('$destroy',function(){
		if(sc.timer)
				$interval.cancel(sc.timer);
	});

	categoryService.getCategories().then(function(d){
		sc.categories=d.data;
		searchingService.cleanCategories();
		for (var i=0;i<sc.categories.length;i++){
			for (var j=0;j<sc.categories[i].subcategories.length;j++){
				searchingService.addCategory(sc.categories[i].subcategories[j].id);
			}
		}
		searchLots();
	});

	sc.Check = function(id){
		if (searchingService.getCategories().indexOf(id) > -1){
			return true;
		}
		return false;
	}

function searchLots(){
	sc.buyNow = searchingService.isRedemption();
	if (isEmpty(searchingService.getName())){
		searchingService.setName(null);
	}


	var lottmplt = {
			name: searchingService.getName(),
			categoryIds: searchingService.getCategories(),
			priceFrom: searchingService.getFromPrice(),
			priceTo: searchingService.getToPrice(),
			isRedemption: sc.buyNow ? 1 : 0
	}

	lotsService.getLotsBySearch(lottmplt).then(function(d){
		count=0;
		pageSlicer = 6;
		searchingService.clearLots();
		sc.lots=[];
		loaded=false;
		searchingService.addLots(d.data);
		sc.getPageLots();
		sc.lotsEmpty = LotsEmpty();
		timerFunc();
	},function(error) {
		sc.lotsEmpty = true;
	});

	var timerFunc = function(){
		for(i=0;i<sc.lots.length;i++){
			sc.lots[i].timeLeft = getTimeLeft(sc.lots[i]);
		}
	};
	sc.timer = $interval(timerFunc,1000);

}
var loaded=false;
sc.getPageLots = function(){
		if(loaded==true){
			return;
		}
		var length = searchingService.getLotsLength();
		if(length== 0) {
			sc.lotsEmpty = true;
			return;
		}
		if (length <= count){
			loaded = true;
			return;
		}
		else if (length < count+pageSlicer){
			sc.lots = sc.lots.concat(searchingService.getPageLots(count,length));
			sc.lotsEmpty = LotsEmpty();
			loaded = true;
		}else{
			sc.lots = sc.lots.concat(searchingService.getPageLots(count,count+pageSlicer));
			sc.lotsEmpty = LotsEmpty();
			count +=pageSlicer;
		}
	}

function LotsEmpty(){
	return isEmpty(searchingService.getLotsLength());
}

	sc.changeCategories = function(){
		var sum=0;
		for (var k=0;k<sc.categories.length;k++){
			sum+=sc.categories[k].subcategories.length;
		}
		var categoriesIds = searchingService.getCategories();
		if (sum > categoriesIds.length){
			searchingService.cleanCategories();
			var subcat = [];
			for (var i=0;i<sc.categories.length;i++){
				for (var j=0;j<sc.categories[i].subcategories.length;j++){
					searchingService.addCategory(sc.categories[i].subcategories[j].id);
				}
			}
			sc.check = true;
		} else {
			searchingService.cleanCategories();
			sc.check = false;
		}
		
		searchLots();
	}

	sc.updateSelected = function(action, id){
		if (action==="add" ){
			searchingService.addCategory(id);
		}
		if (action==="remove"){
			searchingService.removeCategory(id);
		}
	}

	sc.updateSelection = function($event, id, model){
		var checkbox = $event.target;
		var action = (checkbox.checked ? "add" : "remove");
		sc.updateSelected(action, id);
		searchingService.clearLots();
		searchLots();
	}
	sc.updateRedemption = function($event, model){
		var checkbox = $event.target;
		searchingService.setRedemption(checkbox.checked);
		searchingService.clearLots();
		searchLots();
	}

	sc.updateToPrice = function(toPrice){
		searchingService.setToPrice(toPrice);
		searchingService.clearLots();
		searchLots();
	}

	sc.updateFromPrice = function(fromPrice){
		searchingService.setFromPrice(fromPrice);
		searchingService.clearLots();
		searchLots();
	}


});
