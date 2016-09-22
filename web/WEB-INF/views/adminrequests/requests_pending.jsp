<div class="container">

  <!-- Nav tabs -->
  <ul class="nav nav-tabs">
    <li role="presentation" class="active"><a href="#requests"><h4>Requests</h4></a></li>
    <li role="presentation"><a href="#premium"><h4>Premium</h4></a></li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content panel" ng-controller="AdminRequestsController as AdminrequestsCtrl">
    <div role="tabpanel" class="tab-pane fade in active" id="pending_requests">
      <div class="row">
        <div class="col-sm-3">
          <ul class="nav nav-pills nav-stacked">
            <li class="active"><a href="#requests/pending"><h4>Pending requests <span class="badge">{{AdminrequestsCtrl.pendingrequests.length}}</span></h4></a></li>
            <li><a href="#requests/accepted"><h4>Accepted requests <span class="badge">{{AdminrequestsCtrl.acceptedrequests.length}}</span></h4></a></li>
            <li><a href="#requests/denied"><h4>Denied requests <span class="badge">{{AdminrequestsCtrl.deniedrequests.length}}</span></h4></a></li>
          </ul>
        </div>
        <div class="col-sm-9">
          <div class="tab-content">
            <div id="pendingrequests" class="tab-pane fade in active">
              <div class="container-fluid">
                <div class="row">
                  <div ng-if="AdminrequestsCtrl.pendingrequests.length == 0"><h3>No requests.</h3></div>
                  <div class="col-sm-4"  ng-repeat="x in AdminrequestsCtrl.pendingrequests">
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
                          <button class="btn btn-success" ng-click="AdminrequestsCtrl.acceptItem(x)">Accept <span class="glyphicon glyphicon-ok"></span></button>
                          <button class="btn btn-danger" ng-click="AdminrequestsCtrl.denyItem(x)">Deny <span class="glyphicon glyphicon-remove"></span></button>
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
