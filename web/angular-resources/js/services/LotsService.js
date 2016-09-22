app.factory('lotsService', function($http){
	var lotsService = {};
	lotsService.getLots = function() {
		return $http.get("lotdata");
   }
	// lotsService.getLots = function() {
	// 	return $http.post("/lotdatapost");
    //  }
	
	lotsService.getLimitedLots = function(firstItems, itemPerPage){
			return $http.get("ldlimit/" + firstItems + "/" + itemPerPage);
	}
	
	lotsService.getLotsByCategory = function(catId) {
		return $http.get("lots/" + catId);
	}
	
	lotsService.getLimitedLotsByCategory = function(catId, firstItems, itemPerPage){
		return $http.get("limitlotsbycat/" + catId + "/" + firstItems + "/" + itemPerPage);

	}
	
	lotsService.getLimitedLotsBySCategory = function(catId, firstItems, itemPerPage){
		return $http.get("limitlotsbyscat/" + catId + "/" + firstItems + "/" + itemPerPage);

	}
	
	lotsService.getLotsBySearch = function(lottmplt) {
		console.log(JSON.stringify(lottmplt));
		return $http.post("search", JSON.stringify(lottmplt));
	}
	
	lotsService.getLotsByName = function(name) {
		return $http.get("search/"+name);
	}
	
	lotsService.getPremium = function() {
		return $http.get("lots/premium");
	}
	
	return lotsService;
  });
