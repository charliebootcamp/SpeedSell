app.factory('categoryService', ['$http', function($http){
	
	var baseUrl = 'categories';
	var categoryService = {};
	
	categoryService.getCategories = function(){
		return $http.get(baseUrl);
	};
	
	categoryService.getAllCategories = function(){
		return $http.get('allcategories');
	};
	
	categoryService.getCategoryById = function(id){
		return $http.get(baseUrl + '/' + id);
	}
	
	categoryService.getCategoryParent = function(id){
		return $http.get(baseUrl + '/' + id + '/parent');
	}
	
	categoryService.insertCategory = function(cat){
		return $http.post(baseUrl, cat);
	};
	
	categoryService.deleteCategory = function (id) {
        return $http.delete(baseUrl + '/' + id);
    };
	
	return categoryService;
}]);