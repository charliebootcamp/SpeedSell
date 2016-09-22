app.service('searchingService', function(categoryService){
	var findLots = [];
	var searchName;
	var categories = [];
	var toPrice;
	var fromPrice;
	
	var redemption=false;
	
	var setName = function(name) {
		clearLots();
		searchName = name;
	}
	
	var getName = function() {
		return searchName;
	}
	
	var getToPrice = function(){
		return toPrice;
	}
	
	var setToPrice = function(newToPrice){
		clearLots();
		toPrice = newToPrice;
	}
	
	var getFromPrice = function(){
		return fromPrice;
	}
	
	var setFromPrice = function(newFromPrice){
		clearLots();
		fromPrice = newFromPrice;
	}
	
	var setCategories = function(newCategories){
		clearLots();
		categories = newCategories;
	}
	
	var getCategories = function(){
		return categories;
	}
	
	var addCategory = function(id) {
		clearLots();
		categories.push(id);
	}
	
	var removeCategory = function(id) {
		if (categories.length != 0){
			var index = categories.indexOf(id);
			if (index > -1){
				clearLots();
				categories.splice(index, 1);
			}
		}
	}
	
	var cleanCategories = function(){
		clearLots();
		categories = [];
	}
	
	var isRedemption = function(){
		clearLots();
		return redemption;
	}
	
	var setRedemption = function(newValue){
		clearLots();
		redemption = newValue;
	}
	
	var addLots = function(newListLots) {
		//findLots = newListLots;
		console.log(findLots);
		findLots = findLots.concat(newListLots);
		console.log(findLots);
	}
	var addLot = function(newLot) {
		findLots.push(newLot);
	}
	
	var clearLots = function(){
		findLots = [];
	}
	
	var getLots = function(){
		return findLots;
	}
	var count=0;
	var getPageLots = function(st,fn){
		console.log("lots: "+findLots.slice(st,fn));
		return findLots.slice(st,fn);
	}
	
	var getLotsLength = function(){
		return findLots.length;
	}
	
	return {
		setName: setName,
		getName: getName,
		getToPrice: getToPrice,
		setToPrice: setToPrice,
		getFromPrice: getFromPrice,
		setFromPrice: setFromPrice,
		setCategories: setCategories,
		getCategories: getCategories,
		addCategory: addCategory,
		removeCategory: removeCategory,
		cleanCategories: cleanCategories,
		isRedemption: isRedemption,
		setRedemption: setRedemption,
		addLots: addLots,
		addLot: addLot,
		clearLots: clearLots,
		getLots: getLots,
		getPageLots : getPageLots,
		getLotsLength : getLotsLength
	};
  });