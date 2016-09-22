app.factory('accountService', [ '$http', function($http) {
	var accountService = {};
	accountService.getActiveOrders = function(bidderId) {
		var lots = $http.get("orders/active/" + bidderId).then(
			function(response) {
				return response.data;
			});
			return lots;
		}
	accountService.getOrders = function(personId, stateId) {
		var lots = $http.get("orders",{params: { personId: personId, stateId : stateId}}).then(
			function(response) {
				return response.data;
			});
			return lots;
	}

	accountService.getAccountLots = function(personId, stateId) {
		var lots = $http.get("account/lots",{params: { personId: personId, stateId : stateId}}).then(
			function(response) {
				return response.data;
			});
			return lots;
	}
	
	accountService.confirmLot = function(id){
		console.log(id);
		var sth = $http.put("confirmsell", id).then(function(d){
			return d;
		});
		return sth;
	}
	
	accountService.payCommission = function(payerId, lotId, cardNumb, cvc, expMonth, expYear){
		var uCard = {
				payerId: payerId,
				lotId: lotId,
				cardNumb: cardNumb,
				cvc: cvc,
				expMonth: expMonth,
				expYear: expYear
		}
		console.log(uCard);
		var sth = $http.put("payment", uCard).then(function(response){
			return response;
		});
		return sth;
	}
	
	accountService.askPremium = function(payerId, lotId, cardNumb, cvc, expMonth, expYear){
		var uCard = {
				payerId: payerId,
				lotId: lotId,
				cardNumb: cardNumb,
				cvc: cvc,
				expMonth: expMonth,
				expYear: expYear
		}
		console.log(uCard);
		var sth = $http.put("lots/premium/ask", uCard).then(function(response){
			return response;
		});
		return sth;
	}
	
	return accountService;
}]);
