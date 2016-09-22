<div class="container" id="myaccount" ng-controller="FeedbacksController as FCtrl">

  <!-- Nav tabs -->
  <div class="nav-wrap">
    <ul class="nav nav-tabs" role="tablist">
      <li role="presentation"><a href="#myaccount/orders_active">Orders</a></li>
      <li role="presentation"><a href="#myaccount/lots_active">Lots</a></li>
      <li role="presentation" class="active"><a href="#myaccount/feedbacks">Feedbacks</a></li>
      <li role="presentation"><a href="#myaccount/settings">Settings</a></li>
    </ul>
  </div>

  <!-- Tab panes -->
  <div class="tab-content panel">
    <div class="tab-pane active">
      <div class="container-fluid lot-container">
        <div ng-if="FCtrl.feedbacks.length == 0">
          <h2>No feedbacks</h2>
        </div>
        <div class="row">
          <div ng-repeat="feedback in FCtrl.feedbacks">
            <div class="panel panel-primary">
              <div class="panel-body">
                <div class="row">
                  <div class="col-sm-4 col-xs-12">
                    <span>Title: <strong>{{feedback.title}}</strong></span>
                    <br>
                      <div>{{feedback.feedback}}</div>
                    </div>
                    <div class="col-sm-2 col-xs-12">
                      <div>Mark: <span id="mark">{{feedback.mark}}</span></div>
                    </div>
                    <div class="col-sm-3 col-xs-12">
                      <span>From: <a href="#/user_lots/{{feedback.sender.id}}"<strong>{{feedback.sender.name}}</strong></a></span>
                    </div>
                    <div class="col-sm-3 col-xs-12">
                      <a href="#lot/{{feedback.lot.id}}">{{feedback.lot.name}}</a>
                    </div>
                  </div>
                </div>
                <div class="panel-footer">
                  <div class="text-muted text-right">Submited: <span class="DateTime">{{feedback.submitDate | date:'yyyy-MM-dd HH:mm:ss'}}</span></div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>

  <!--End of my account-->
