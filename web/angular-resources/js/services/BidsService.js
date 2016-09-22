//return bids array by lotid
app.factory('bidsService', [ '$http', function($http) {
	var baseUrl = 'bid';
	var bidsService = {};
	bidsService.getBidsByLotId = function(lotId) {
		return $http.get(baseUrl + '/lotId/' + lotId);
	}
	bidsService.insertBid = function (bid){	
		return $http.post('create_bid', bid);
	}
	return bidsService;
}]);
