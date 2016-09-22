app.controller('CategoryController',
		function($scope, $localStorage, categoryService, uActivitiesService) {
			var ob = this;
			ob.status;
			ob.nameInput;
			ob.categories;
			ob.allcategories;
			ob.comInput;
			ob.env = findBootstrapEnvironment();
			getCategories();
			getAllCategories();
			function getCategories() {
				categoryService.getCategories().then(
					function(response) {
						ob.categories = response.data;
					},
					function(error) {
						ob.status = 'Unable to load categories data: '
						+ error.message;
					});
				}

			function getAllCategories() {
				categoryService.getAllCategories().then(
					function(response) {
						ob.allcategories = response.data;
					},
					function(error) {
						ob.status = 'Unable to load categories data: '
						+ error.message;
					});
				}

//			for (i=0;i<ob.categories.length;i++){
//				ob.categories[i].parent;
//				getParent(i);
//			}


			ob.insertCategory = function(parId) {

				var cat = {
					      name : ob.nameInput,
					      parentId : parId,
					      commission: ob.comInput
				};
				var uActivity = {
					personId : $localStorage.curruser.id,
					dateOf : new Date().getTime(),
					info : "Admin created category = " + cat.name + " with parentId = " + cat.parentId,
					typeId : 4
				}
				categoryService.insertCategory(cat).then(function(response) {
			      ob.status = 'Inserted Category! Refreshing category list.';
						uActivitiesService.addUserActivities(uActivity);
						var timeoutID = window.setTimeout(function(){
							location.reload();
						}, 500);
			     }, function(error) {
			      ob.status = 'Unable to insert category: ' + error.message;
			     });
			    };

		ob.deleteSubcategory = function(id) {
			var uActivity = {
				personId : $localStorage.curruser.id,
				dateOf : new Date().getTime(),
				info : "Admin deleted category id = " + id,
				typeId : 5
			}
			categoryService.deleteCategory(id).then(function(response) {
				ob.status = "Deleted Category! Refreshing Category list.";
				uActivitiesService.addUserActivities(uActivity);
				var timeoutID = window.setTimeout(function(){
					location.reload();
				}, 500);
			}, function(error) {
				ob.status = 'Unable to delete category: ' + error.message;
				console.log(ob.status);
			});
		};

		ob.deleteCategory = function(id) {
			var tmpcat = {};
			categoryService.getCategoryById(id).then(function(d) {
				tmpcat = d.data;
				if (!isEmpty(tmpcat.subcategories)){
					for (i=0;i<tmpcat.subcategories.length;i++){
						ob.deleteSubcategory(tmpcat.subcategories[i].id);
					}
				}
				ob.deleteSubcategory(tmpcat.id);
			});
		}
		ob.mobcat;
		ob.catclicked = function(cat){
			ob.mobcat = cat;
		}
	});
