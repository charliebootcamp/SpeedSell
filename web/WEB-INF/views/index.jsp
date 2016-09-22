<!DOCTYPE html>
<!-- ANGULAR JS -->
<html lang="en-US">
  <head>
    <title>SpeedSell Auction</title>
    <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet"	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css"	href="${pageContext.request.contextPath}/angular-resources/css/style.css" />
          <script	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
          <script	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
		  <script	src="${pageContext.request.contextPath}/angular-resources/js/lib/angular.min.js"></script>
		  <script	src="${pageContext.request.contextPath}/angular-resources/js/lib/angular-resource.min.js"></script>
		 <script	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.2.26/angular-route.min.js"></script>
        </head>
        <body  ng-app="myApp" class="ng-cloak">
          <div>
            <nav class="navbar navbar-default">
              <div class="container-fluid" ng-controller="IndexController as indexCtrl">
                <div class="navbar-header visible-xs ">
                <div class="row-eq-height">
                <div class="col-xs-1 home">
                  <a href="#/" data-toggle="collapse" data-target=".collapse.in"><span class="glyphicon glyphicon-home white-glyph big-glyph"></span></a>
                </div>
                <div class="col-xs-1 home" ng-if="!indexCtrl.isGuest">
                  <button type="button" class="navbar-toggle " data-toggle="collapse"
                    data-target="#myNavbar" >
                    <span class="glyphicon glyphicon-list white-glyph big-glyph"></span>
                  </button>
                </div>
                <div class="col-xs-1 home">
                <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#categories">
                   <span class="glyphicon glyphicon-th white-glyph big-glyph"></span>
                  </button>
                </div>
                <div class="col-xs-1 home">
                  <a data-toggle="collapse" data-target=".collapse.in" ng-hide="indexCtrl.isGuest" href="#/user_feedbacks/{{indexCtrl.user.id}}"><span class="glyphicon glyphicon-user white-glyph big-glyph"></span></a>
                </div>
                <div class="col-xs-1 home mob-index-right" ng-if="!indexCtrl.isGuest">
                  <a data-toggle="collapse" data-target=".collapse.in"  ng-click="indexCtrl.logout()" id="Logout" ng-href="#logout" ><span class="glyphicon glyphicon-log-out white-glyph big-glyph"></span></a>
                </div>
                <div class="col-xs-1 home mob-index-right" ng-if="indexCtrl.isGuest">
                  <a data-toggle="collapse" data-target=".collapse.in"  href="#login" ><span class="glyphicon glyphicon-log-in white-glyph big-glyph"></span></a>
                </div>
                <div class="col-xs-1 home mob-index-right">
                  <a data-toggle="collapse" data-target=".collapse.in" href="#contacts"><span class="glyphicon glyphicon-envelope white-glyph big-glyph"></span></a>
                </div>
                </div>
                </div>
                <div class="row-eq-height">
                  <div class="col-sm-2 hidden-xs">
                    <a href="#/"><img id="logoimg" class="img-responsive" src="${pageContext.request.contextPath}/r/images/logo.png" alt="SpeedSell logo"></a>
                  </div>
                  <div class=" col-sm-10">
                    <div class="collapse navbar-collapse" id="myNavbar">
                      <div class="col-sm-7">
                        <ul class="nav navbar-nav" ng-if="!indexCtrl.isGuest">
                          <li data-toggle="collapse" data-target=".collapse.in"><a href="#createlot">Create a lot</a></li>
                          <li data-toggle="collapse" data-target=".collapse.in"><a href="#myaccount/orders_active">My account</a></li>
                          <li data-toggle="collapse" data-target=".collapse.in" ng-if="indexCtrl.isAdmin"><a href="#category">Create/delete a category</a></li>
                          <li data-toggle="collapse" data-target=".collapse.in" ng-if="indexCtrl.isAdmin"><a href="#requests">Requests</a></li>
                        </ul>
                      </div>
                      <div class="col-sm-5">
                        <ul class="nav navbar-nav navbar-right">
                          <li class="hidden-xs" data-toggle="collapse" data-target=".collapse.in" ng-hide="indexCtrl.isGuest" id="greeting"><a href="#/user_feedbacks/{{indexCtrl.user.id}}">{{indexCtrl.user.name}}</a></li>
                          <li class="hidden-xs" data-toggle="collapse" data-target=".collapse.in" ng-if="!indexCtrl.isGuest" ng-click="indexCtrl.logout()" id="Logout"><a ng-href="#logout" >Sign Out</a></li>
                          <li class="hidden-xs" data-toggle="collapse" data-target=".collapse.in" ng-if="indexCtrl.isGuest"><a href="#login" >Sign In</a></li>
                          <li class="hidden-xs" data-toggle="collapse" data-target=".collapse.in" ng-if="indexCtrl.isGuest"><a href="#registration">Register</a></li>
                          <li class="hidden-xs" data-toggle="collapse" data-target=".collapse.in"><a href="#contacts">Contacts</a></li>
                        </ul>
                      </div>
                      <form id="search" class="col-sm-12 hidden-xs" ng-submit="searchCtrl.Search(searchCtrl.searchInput)" ng-controller="IndexController as searchCtrl">
                        <div class="col-sm-9" >
                          <label for="inputsearch"></label>
                          <input maxlength="255" ng-model="searchCtrl.searchInput" class="form-control" id="inputsearch" type="text" placeholder="Input text search">
                          </div>
                          <div class="col-sm-3">
                            <label for="btnsearch"></label>
                            <input type="submit" id="btnsearch" class="form-control btn btn-default" value='Search'>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>
              </nav>
            </div>

              <nav class="navbar navbar-default"  >
              <div class="row row-eq-height  collapse navbar-collapse " id="categories"
              ng-controller="CategoryController as categoriesCtrl">
              
                <div class="col-sm-3 drowdown hidden-xs"
                  ng-repeat="category in categoriesCtrl.categories">
                  <button class="btn drowdown-toggle text-capitalize" type="button"
                   data-toggle="dropdown">
                    {{category.name}} <span class="caret hidden-xs"></span>
                  </button>
                 
                  <ul class="dropdown-menu text-capitalize" 
                    role="menu" aria-labelledby="electronic">
                    <li role="presentation"><a role="menuitem" tabindex="1"
                      href="#lots/{{subcategory.id}}"
                      ng-repeat="subcategory in category.subcategories">
                      {{subcategory.name}} </a></li>
                    </ul>
                </div>
                <div class="row visible-xs mob-cats">
                 <div class="col-xs-4 vertline">
                 	<div class="row " ng-repeat="category in categoriesCtrl.categories">
                 		<div class="text-capitalize pull-left">
                    		<h5><a ng-click="categoriesCtrl.catclicked(category)" href="">{{category.name}}</a></h5>
                  		</div>
                 	</div>
                 </div>
                 <div class="col-xs-8" ng-if="!isEmpty(categoriesCtrl.mobcat)">
	             	<div class="row mob-subcats" ng-repeat="subcategory in categoriesCtrl.mobcat.subcategories" >
	             		<div class="text-capitalize pull-left" data-toggle="collapse" data-target=".collapse.in">
                    		<h5><a href="#lots/{{subcategory.id}}">{{subcategory.name}}</a></h5>
                  		</div>
	             	</div>
                 </div>
                </div>
                </div>
                </nav>

              <div class="view">

                <div ng-view></div>

              </div>


	<footer>
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-5 col-sm-offset-2">
					<h5> <span class="glyphicon glyphicon-copyright-mark"></span> 2016 SpeedSell by Charlie team</h5>
				</div>
				<div class="col-sm-2" ng-controller="IndexController as indexCtrl">
					<a href="" class="btn btn-info" ng-click="indexCtrl.sendFeedback()">Feedback</a>
				</div>
			</div>
		</div>
	</footer>
	<script type="text/javascript" src="https://cdn.jsdelivr.net/ngstorage/0.3.6/ngStorage.min.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/app.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/helpFunctions.js"></script>
	<script type='text/javascript' src="${pageContext.request.contextPath}/angular-resources/js/directives/WhenScrollEndsDirective.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/directives/UploadFileDirective.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/directives/OnlyNumbersDirective.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/IndexController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/HomeController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/PersonController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/CategoryController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/LotController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/AdminRequestsController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/AccountController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/LotsController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/FeedbacksController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/UserLotsController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/UserFeedbacksController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/CreateLotController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/SearchingController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/controllers/SendQuestionController.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/services/SearchingService.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/services/CategoryService.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/services/LotService.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/services/LotsService.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/services/BidsService.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/services/PersonService.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/services/AdminrequestsService.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/services/AccountService.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/services/FeedbacksService.js"></script>
	<script	src="${pageContext.request.contextPath}/angular-resources/js/services/UserActivitiesService.js"></script>

    <!--  -->
 	<script	src="${pageContext.request.contextPath}/angular-resources/js/lib/ui-bootstrap-custom-2.1.3.js"></script>
  <script	src="${pageContext.request.contextPath}/angular-resources/js/lib/ui-bootstrap-custom-2.1.3.min.js"></script>
  <script	src="${pageContext.request.contextPath}/angular-resources/js/lib/ui-bootstrap-custom-tpls-2.1.3.js"></script>
  <script	src="${pageContext.request.contextPath}/angular-resources/js/lib/ui-bootstrap-custom-tpls-2.1.3.min.js"></script>
</body>
</html>
