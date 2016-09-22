<div class="container" ng-controller="UserFeedbacksController as FCtrl">

  <!-- Nav tabs -->
  <div class="nav-wrap">
    <ul class="nav nav-tabs" role="tablist">
      <li role="presentation" class="active"><a href="#user_feedbacks/{{FCtrl.userid}}"><span class="text-capitalize">{{FCtrl.person.name}}</span> feedbacks</a></li>
      <li role="presentation"><a href="#user_lots/{{FCtrl.userid}}"><span class="text-capitalize">{{FCtrl.person.name}}</span> items</a></li>
    </ul>
  </div>
  <!-- Tab panes -->
  <div class="tab-content panel">
    <div class="tab-pane active">
      <div class="container-fluid lot-container">
        <%-- user info --%>
        <div class="row">
            <div class="panel panel-primary">
              <div class="panel-heading">
                <div class="row" id="innerStat">
                  <div class="col-sm-4 col-xs-12" ng-repeat="stat in FCtrl.statistics">
                  <div class="panel panel-{{stat.class}}">
                    <div class="panel-heading">
                      <div class="row">
                        <div class="col-sm-6 col-xs-6">
                          <strong><div>Mark</div>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                          <div>{{stat.mark}}</div>
                        </div></strong>
                      </div>
                    </div>
                    <div class="panel-body">
                      <div class="row">
                        <div class="col-sm-6 col-xs-6">
                          <div>Feedbacks</div>
                        </div>
                        <div class="col-sm-6 col-xs-6">
                          <div><strong>{{stat.countFeedbacks}}</strong></div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                </div>
              </div>
              <div class="panel-footer">
                <div class="row">
                  <div class="col-sm-4">
                    Email: <a href="" ng-click="FCtrl.sendQuestion()">{{FCtrl.person.email}}</a>
                  </div>
                  <div class="col-sm-4">
                    Registered: <span class="DateTime">{{FCtrl.person.createDate | date:'yyyy-MM-dd HH:mm:ss'}}</span><span class="DateTime">{{feedback.submitDate | date:'yyyy-MM-dd HH:mm:ss'}}</span>
                  </div>
                  <div class="col-sm-4">
                    Last login: <span class="DateTime">{{FCtrl.person.lastLogin | date:'yyyy-MM-dd HH:mm:ss'}}</span>
                  </div>
                </div>
              </div>
            </div>
        </div>

        <div ng-if="FCtrl.feedbacks.length == 0">
          <h2>No feedbacks</h2>
        </div>
        <div class="row">
          <div ng-repeat="feedback in FCtrl.feedbacks">
            <div class="panel panel-primary" ng-class="{'border-bad': feedback.mark=='Bad', 'border-good': feedback.mark=='Good','border-dontcare': feedback.mark=='Neutral'}">
              <div class="panel-body"  >
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
                    <span>From: <a href="#user_feedbacks/{{feedback.sender.id}}"><strong>{{feedback.sender.name}}</strong></a></span>
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
