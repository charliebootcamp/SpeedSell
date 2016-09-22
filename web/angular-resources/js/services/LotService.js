app.factory('lotService', [ '$http', function($http) {
	var baseUrl = 'lotdata';
	var lotService = {};
	lotService.getLot = function(lotId) {
			var lot = $http.get(baseUrl + "/" + lotId).then(
					function(response) {
						return response.data;
					});
			return lot;
		}
	lotService.updateLot = function (lot){
		return $http.put('update_lot', lot);
	}
	lotService.editLot = function (lot){
		return $http.put('edit_lot', lot);
	}
	lotService.getLotsByPerson = function(personId){
		return $http.get("persons/" + personId + "/lots");
	}
	lotService.insertLot = function(lot){
		console.log(lot);
		return $http.post('create_lot', lot);
	}
	lotService.deleteLot = function(id){
		return $http.delete("delete_lot/"+id);
	}
	
	lotService.buyLotNow = function(payerId, lotId, cardNumb, cvc, expMonth, expYear){
		var uCard = {
				payerId: payerId,
				lotId: lotId,
				cardNumb: cardNumb,
				cvc: cvc,
				expMonth: expMonth,
				expYear: expYear
		}
		console.log(uCard);
		return $http.put("redemption", uCard);
	}
	
	lotService.makePremium = function(lotId) {
		return $http.put("premium", lotId);
	}
	
	return lotService;
}]);
