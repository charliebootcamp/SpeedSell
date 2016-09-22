var app = angular.module('myApp', [ "ngStorage",'ngRoute','ui.bootstrap' ]).config(
	[ '$routeProvider', function($routeProvider) {
		$routeProvider.when('/', {
			templateUrl : 'home'
		}).when('/category', {
			templateUrl : 'category/category_create'
		}).when('/category/create', {
			templateUrl : 'category/category_create'
		}).when('/category/delete', {
			templateUrl : 'category/category_delete'
		}).when('/requests', {
			templateUrl : 'adminrequests/requests_pending'
		}).when('/requests/pending', {
			templateUrl : 'adminrequests/requests_pending'
		}).when('/requests/accepted', {
			templateUrl : 'adminrequests/requests_accepted'
		}).when('/requests/denied', {
			templateUrl : 'adminrequests/requests_denied'
		}).when('/premium', {
			templateUrl: 'adminrequests/requests_premium'
		}).when('/lots/:cat', {
			templateUrl : "lots"
		}).when('/lot/:lotid', {
			templateUrl : 'lot'
		}).when('/searching', {
			templateUrl : 'searching'
		}).when('/myaccount/orders_active', {
			templateUrl: 'myaccount/orders_active'
		}).when('/myaccount/orders_wfpay', {
			templateUrl: 'myaccount/orders_wfpay'
		}).when('/myaccount/orders_bought', {
			templateUrl: 'myaccount/orders_bought'
		}).when('/myaccount/lots_active', {
			templateUrl: 'myaccount/lots_active'
		}).when('/myaccount/lots_archive', {
			templateUrl: 'myaccount/lots_archive'
		}).when('/myaccount/lots_canceled', {
			templateUrl: 'myaccount/lots_canceled'
		}).when('/myaccount/lots_sold', {
			templateUrl: 'myaccount/lots_sold'
		}).when('/myaccount/lots_wfpaylot', {
			templateUrl: 'myaccount/lots_wfpaylot'
		}).when('/myaccount/lots_created', {
			templateUrl: 'myaccount/lots_created'
		}).when('/myaccount/feedbacks', {
			templateUrl: 'myaccount/feedbacks'
		}).when('/myaccount/settings', {
			templateUrl: 'myaccount/settings'
		}).when('/contacts', {
			templateUrl: 'contacts'
		}).when('/registration', {
			templateUrl: 'registration/registrationPage'
		}).when('/login', {
			templateUrl: 'registration/loginPage'
		}).when('/resetPassword/:h', {
			templateUrl: function(params){ return 'registration/resetPassword/' + params.h; }
		}).when('/createlot', {
			templateUrl: 'createlot'
		}).when('/user_lots/:userid', {
			templateUrl: 'user_lots'
		}).when('/user_feedbacks/:userid', {
			templateUrl: 'user_feedbacks'
		}).when('/errors', {
			templateUrl: 'errors/404'
		}).when('/errors/404', {
			templateUrl: 'errors/404'
		}).when('/errors/403', {
			templateUrl: 'errors/403'
		}).when('/errors/500', {
			templateUrl: 'errors/500'
		}).when('/question', {
			templateUrl: 'question/'
        }).when('/ldlimit/:currentPage', {
			templateUrl: 'home'
		}).otherwise({
			redirectTo : '/errors'
		})
	}]);