<div class="container" id="myaccount" ng-controller="AccountController as ACtrl" data-ng-init="ACtrl.loadLots(3)">

  <!-- Nav tabs -->
  <div class="nav-wrap">
    <ul class="nav nav-tabs" role="tablist">
      <li role="presentation"><a href="#myaccount/orders_active">Orders</a></li>
      <li role="presentation" class="active"><a href="#myaccount/lots_active">Lots</a></li>
      <li role="presentation"><a href="#myaccount/feedbacks">Feedbacks</a></li>
      <li role="presentation"><a href="#myaccount/settings">Settings</a></li>
    </ul>
  </div>
  <!-- Tab panes -->
  <div class="tab-content panel">
    <div role="tabpanel" class="tab-pane active" id="my_lots">
      <div class="row">
        <div class="col-sm-2">
          <ul class="nav nav-pills nav-stacked">
            <li class="active"><a href="#myaccount/lots_active">Active</a></li>
            <li><a href="#myaccount/lots_sold">Sold</a></li>
            <li><a href="#myaccount/lots_wfpaylot">Wait for pay</a></li>
            <li><a href="#myaccount/lots_archive">Archive</a></li>
            <li><a href="#myaccount/lots_canceled">Canceled</a></li>
            <li><a href="#myaccount/lots_created">Created</a></li>
          </ul>
        </div>
        <div class="col-sm-10">
          <div class="tab-content">
            <div id="lots_active" class="tab-pane active">
              <div class="container-fluid lot-container">
                <div ng-if="ACtrl.lots.length == 0">
                  <h2>No lots</h2>
                </div>
                <div class="row">
                  <div class="col-sm-4" ng-repeat="lots in ACtrl.lots">
                    <div class="card">
                      	<a href="#lot/{{lots.id}}">
        					<div class="img-container">
        						<img ng-src="image/{{lots.img}}" class="lotimg" alt="Image lot">
        					</div>
        				</a>
                        <div class="card-container">
                          <div class="text-center">
                            <a href="#lot/{{lots.id}}"><strong>{{lots.name}}</strong></a>
                          </div>
                          <div>
                            Current price: <span ng-if="!lots.bestBid.amount">{{lots.startPrice}}</span>
                            <span class="lotBestBid" ng-if="lots.bestBid.amount">{{lots.bestBid.amount}}</span>$
                          </div>
                          <div>
                            Time left: {{lots.timeLeft.t}} <span
                            class="label label-danger"> <span
                            ng-if="lots.timeLeft.days" ng-bind="lots.timeLeft.days">
                          </span><span ng-if="lots.timeLeft.days"> day(s) </span> <span
                          ng-bind="lots.timeLeft.hours"> </span>:<span
                          ng-bind="lots.timeLeft.minutes"> </span>:<span
                          ng-bind="lots.timeLeft.seconds"> </span>
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
