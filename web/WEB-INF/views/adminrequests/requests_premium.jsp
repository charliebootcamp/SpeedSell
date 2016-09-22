<div class="container">

  <!-- Nav tabs -->
  <ul class="nav nav-tabs">
    <li role="presentation"><a href="#requests"><h4>Requests</h4></a></li>
    <li role="presentation" class="active"><a href="#premium"><h4>Premium</h4></a></li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content panel" ng-controller="AdminRequestsController as AdminrequestsCtrl">
    <div role="tabpanel" class="tab-pane fade in active" id="premium_requests">
      <div class="row">
        <div class="col-sm-3">
          <ul class="nav nav-pills nav-stacked">
            <li class="active"><a href="#premium"><h4>Premium</h4></a></li>
          </ul>
        </div>
        <div class="col-sm-9">
          <div class="tab-content">
            <div id="pendingrequests" class="tab-pane fade in active">
              <div class="container-fluid">
                <div class="row">
                  <div ng-if="AdminrequestsCtrl.premium.length == 0"><h3>No requests.</h3></div>
                  <div class="col-sm-4"  ng-repeat="x in AdminrequestsCtrl.premium">
                    <div class="card">
                      <a href="#lot/{{x.id}}">
        					<div class="img-container">
        						<img ng-src="image/{{x.img}}" class="lotimg" alt="Image lot">
        					</div>
        				</a>
                        <div class="card-container">
                          <div class="text-center">
                            <a href="#lot/{{x.id}}"><strong>{{x.name}}</strong></a>
                          </div>
                        </div>
                        <div class="panel-footer">
                          <button class="btn btn-warning" ng-click="AdminrequestsCtrl.makePremium(x.id)">Make premium <span class="glyphicon glyphicon-sunglasses"></span></button>
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
