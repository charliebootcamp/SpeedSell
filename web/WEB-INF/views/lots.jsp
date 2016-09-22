<div class="container" ng-controller="LotsController as ctrl" ng-init="ctrl.changePage()">
  <ul class="breadcrumb text-capitalize">
    <li><a href="#/"><span class="glyphicon glyphicon-home"></span></a></li>
    <li ng-if="ctrl.iscat"><a  href="#/lots/{{ctrl.cat.id}}">{{ctrl.cat.name}}</a></li>
    <li><a href="#/lots/{{ctrl.subcat.id}}">{{ctrl.subcat.name}}</a></li>
  </ul>
  <div ng-if="ctrl.lots.length == 0" class="white">
    <h2>No lots</h2>
  </div>
  <div class="row-eq-height">
    <div class="col-sm-4" ng-repeat="limitLots in ctrl.limitedLots">
      <div class="card">
        <a href="#lot/{{limitLots.id}}">
        					<div class="img-container">
        						<img ng-src="image/{{limitLots.img}}" class="lotimg" alt="Image lot">
        					</div>
        				</a>
          <div class="card-container">
            <div class="text-center">
              <a href="#lot/{{limitLots.id}}"><strong>{{limitLots.name}}</strong></a>
            </div>
            <div>
              Current price: <span ng-if="!limitLots.bestBid.amount">{{limitLots.startPrice}}</span>
              <span class="lotBestBid" ng-if="limitLots.bestBid.amount">{{limitLots.bestBid.amount}}</span>$
            </div>
            <div>
              Time left: {{limitLots.timeLeft.t}} <span
              class="label label-danger"> <span
              ng-if="limitLots.timeLeft.days" ng-bind="limitLots.timeLeft.days">
            </span><span ng-if="limitLots.timeLeft.days"> day(s) </span> <span
            ng-bind="limitLots.timeLeft.hours"> </span>:<span
            ng-bind="limitLots.timeLeft.minutes"> </span>:<span
            ng-bind="limitLots.timeLeft.seconds"> </span>
          </span>
        </div>
      </div>
    </div>
  </div>
</div>
  <div class="col-sm-12 white">
    <div class="text-center" >
      <a href="#ldlimit/{{currentPage}}" class="lot-link">
        <ul uib-pagination boundary-links="true" total-items="totalItems" ng-model="currentPage" ng-change="changePage()"
          items-per-page="itemPerPage" max-size="3" class="pagination-sm" previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;">
        </ul>
      </a>
    </div>
  </div>
  </div>
