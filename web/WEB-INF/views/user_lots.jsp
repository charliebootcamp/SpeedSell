<div class="container" ng-controller="UserLotsController as UCtrl">

  <!-- Nav tabs -->
  <div class="nav-wrap">
    <ul class="nav nav-tabs" role="tablist">
      <li role="presentation"><a href="#user_feedbacks/{{UCtrl.userid}}"><span class="text-capitalize">{{UCtrl.person.name}}</span> feedbacks</a></li>
      <li role="presentation" class="active"><a href="#user_lots/{{UCtrl.userid}}"><span class="text-capitalize">{{UCtrl.person.name}}</span> items</a></li>
    </ul>
  </div>

  <!-- Tab panes -->
  <div class="tab-content panel">
    <div role="tabpanel" class="tab-pane active" id="user_lots">
      <div class="row">
        <div class="col-sm-12">
          <div class="tab-content">
            <div id="lots_active" class="tab-pane active">
              <div class="container-fluid lot-container">
                <div ng-if="UCtrl.lots.length == 0">
                  <h2>No lots</h2>
                </div>
                <div class="row">
                  <div class="col-sm-4" ng-repeat="lot in UCtrl.lots">
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
