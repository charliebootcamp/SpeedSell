<div class="container" id="myaccount" ng-controller="AccountController as ACtrl" data-ng-init="ACtrl.loadActiveOrders()">

  <!-- Nav tabs -->
  <div class="nav-wrap">
    <ul class="nav nav-tabs" role="tablist">
      <li role="presentation" class="active"><a href="#myaccount/orders_active">Orders</a></li>
      <li role="presentation"><a href="#myaccount/lots_active">Lots</a></li>
      <li role="presentation"><a href="#myaccount/feedbacks">Feedbacks</a></li>
      <li role="presentation"><a href="#myaccount/settings">Settings</a></li>
    </ul>
  </div>

  <!-- Tab panes -->
  <div class="tab-content panel">
    <div role="tabpanel" class="tab-pane active" id="my_orders">
      <div class="row">
        <div class="col-sm-2">
          <ul class="nav nav-pills nav-stacked">
            <li class="active"><a href="#myaccount/orders_active">Active</a></li>
            <li><a href="#myaccount/orders_wfpay">Wait for pay</a></li>
            <li><a href="#myaccount/orders_bought">Bought</a></li>
          </ul>
        </div>
        <div class="col-sm-10">
          <div class="tab-content">
            <div id="orders_active" class="tab-pane active">
              <div class="container-fluid lot-container">
                <div ng-if="ACtrl.lots.length == 0">
                  <h2>No lots</h2>
                </div>
                <div class="row-eq-height">
                  <div class="col-sm-4" ng-repeat="lot in ACtrl.lots">
                  	<div class="card">
                    <a href="#lot/{{lot.id}}">
        					<div class="img-container">
        						<img ng-src="image/{{lot.img}}" class="lotimg" alt="Image lot">
        					</div>
        				</a>
                        <div class="card-container">
                          <div class="text-center">
                            <a href="#lot/{{lot.id}}"><strong>{{lot.name}}</strong></a>
                          </div>
                          <div>
                            Current price: <span ng-if="!lot.bestBid.amount">{{lot.startPrice}}</span>
                            <span class="lotBestBid" ng-if="lot.bestBid.amount">{{lot.bestBid.amount}}</span>$
                          </div>
                          <div>Your bid: <span class="Bid">{{lot.lastUserBid.amount}}</span>$</div>
                          <div>
                            Time left: {{lot.timeLeft.t}} <span
                            class="label label-danger"> <span
                            ng-if="lot.timeLeft.days" ng-bind="lot.timeLeft.days">
                          </span><span ng-if="lot.timeLeft.days"> day(s) </span> <span
                          ng-bind="lot.timeLeft.hours"> </span>:<span
                          ng-bind="lot.timeLeft.minutes"> </span>:<span
                          ng-bind="lot.timeLeft.seconds"> </span>
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
</div>

<!--End of my account-->
