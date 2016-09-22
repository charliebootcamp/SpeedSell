app.controller("CreateLotController", function($scope, $localStorage, $http, lotService, uActivitiesService, categoryService){
	var l = this;
	l.name;
	l.info;
	l.subcat;
	l.startprice;
	l.duration;
	l.redemption;
	l.ownerid;
	l.img;
	l.categories;
	l.myFile;
	l.cid;
	getCategories();
	function getCategories() {
		categoryService.getCategories().then(
				function(response) {
					l.categories = response.data;
				},
				function(error) {
					l.status = 'Unable to load categories data: '
						+ error.message;
				});
	}
	l.insertLot = function() {
		if($localStorage.curruser.id  != undefined){

			$http({
	    	    method: 'POST',
	    	    url: 'lotdata/images',
	    	    headers: {'Content-Type': undefined },
	    	    transformRequest: function (data) {
	    	        var formData = new FormData();

	    	        formData.append("file", data.file);
	    	        return formData;
	    	    },
	    	    data: { file: l.myFile}
	    	})
	        .success(function(response){
	        	var lot = {
						name : l.name,
						info : isEmpty(l.info)?null:l.info,
						categoryId : l.cid,
						startPrice : l.startprice,
						duration : l.duration * 3600000,//format in hours
						redemption : isEmpty(l.redemption)?null:l.redemption,
						ownerId : $localStorage.curruser.id,
						img : response.path
				};
				var uActivity = {
						personId : $localStorage.curruser.id,
						dateOf : new Date().getTime(),
						info : "User created lot = " + lot.name,
						typeId : 6
				}
				lotService.insertLot(lot).then(function(d){
				uActivitiesService.addUserActivities(uActivity);
				l.success = true;
				location.href = "#/myaccount/lots_created";
				}, function(error){
					l.status = 'Unable to create lot: ' + error;
					console.log(l.status);
				});
	        })
	        .error(function(){
	        	console.log("Unable to load image.");
	        });
		}else{
			console.log("Not logged");
		}
	};
	l.checkNum = function(num, min, max){
		if (num > max) {
			return max;
		} else if (num < min) {
			return min;
		} else {
			return num;
		}
	}
});
