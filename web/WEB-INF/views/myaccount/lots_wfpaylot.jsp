<div class="container" id="myaccount" ng-controller="AccountController as ACtrl" data-ng-init="ACtrl.loadLots(5)">

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
    <div role="tabpanel" class="tab-pane active" id="my_orders">
      <div class="row">
        <div class="col-sm-2">
          <ul class="nav nav-pills nav-stacked">
            <li><a href="#myaccount/lots_active">Active</a></li>
            <li><a href="#myaccount/lots_sold">Sold</a></li>
            <li class="active"><a href="#myaccount/lots_wfpaylot">Wait for pay</a></li>
            <li><a href="#myaccount/lots_archive">Archive</a></li>
            <li><a href="#myaccount/lots_canceled">Canceled</a></li>
            <li><a href="#myaccount/lots_created">Created</a></li>
          </ul>
        </div>
        <div class="col-sm-10">
          <div class="tab-content">
            <div id="wfpaylot" class="tab-pane active">
              <div class="container-fluid lot-container">
                <div ng-if="ACtrl.lots.length == 0">
                  <h2>No lots</h2>
                </div>
                <div class="row">
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
                          <div>Bought: <span class="DateTime">{{lot.startDate + lot.duration | date:'yyyy-MM-dd HH:mm:ss'}}</span></div>
                          <div>Winner: {{lot.bestBid.bidder.email}}</div>
                          <div>
                            Time to pay left: {{lot.timeLeft.t}} <span
                            class="label label-danger"> <span
                            ng-if="lot.timeLeft.days" ng-bind="lot.timeLeft.days">
                          </span><span ng-if="lot.timeLeft.days"> day(s) </span> <span
                          ng-bind="lot.timeLeft.hours"> </span>:<span
                          ng-bind="lot.timeLeft.minutes"> </span>:<span
                          ng-bind="lot.timeLeft.seconds"> </span>
                        </span>
                      </div>
                    </div>
                    <div class="panel-footer">
                    <div class="row">
					<div class="col-xs-6">
                      <a title="Contact with buyer." href=""
                        ng-click="ACtrl.sendQuestion(lot.bestBid.bidder.email, lot.name)">
                        <span class="glyphicon glyphicon-envelope footer-glyph footer-but-inside"></span>
                      </a>
					</div>
					<div class="col-xs-6">
                      <a title="Confirm payment." href=""  data-toggle="modal" data-target="#Alert" ng-click="ACtrl.cur = lot.id">
                        <span class="glyphicon glyphicon-ok-circle footer-glyph footer-but-inside"></span>
                      </a>
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
  <div class="modal fade" id="Alert" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
        </div>
        <div class="modal-body">
          <h4>Are you sure you want to confirm payment for this lot?</h4>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success"	ng-click="ACtrl.confirm(ACtrl.cur)">
            Yes
          </button>
          <button type="button" class="btn btn-danger" data-dismiss="modal">
            No
          </button>
        </div>
      </div>
    </div>
  </div>
</div>

<!--End of my account-->
