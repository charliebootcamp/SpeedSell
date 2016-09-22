<div class="container" id="myaccount" ng-controller="AccountController as ACtrl" data-ng-init="ACtrl.loadLots(6)">

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
            <li class="active"><a href="#myaccount/lots_sold">Sold</a></li>
            <li><a href="#myaccount/lots_wfpaylot">Wait for pay</a></li>
            <li><a href="#myaccount/lots_archive">Archive</a></li>
            <li><a href="#myaccount/lots_canceled">Canceled</a></li>
            <li><a href="#myaccount/lots_created">Created</a></li>
          </ul>
        </div>
        <div class="col-sm-10">
          <div class="tab-content">
            <div id="sold" class="tab-pane active">
              <div class="container-fluid lot-container">
                <div ng-if="ACtrl.lots.length == 0">
                  <h2>No lots</h2>
                </div>
                <div class="modal fade" id="sendFeedback" role="dialog">
                  <div class="modal-dialog">

                    <div class="modal-content">
                      <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" id="CloseModal">&times;</button>
                        <h4 class="modal-title">Send feedback:</h4>
                      </div>
                      <div class="modal-body">
                        <div class="alert alert-{{ACtrl.alertType}}" ng-show="ACtrl.showMessage">
                          <a href="" class="close" ng-click="ACtrl.showMessage = false">&times;</a>
                          <strong>{{ACtrl.title}}</strong> {{ACtrl.message}}
                        </div>
                        <form id="feedback-form" class="form-horizontal" role="form">
                          <div class="form-group">
                            <label class="control-label col-sm-3" for="categoryname">Title:</label>
                            <div class="col-sm-7">
                              <input tabindex="1" type="text" name="title" ng-model="ACtrl.feedback.title" class="form-control"
                                placeholder="Enter feedback title" maxlength="255" required>
                              </div>
                            </div>
                            <div class="form-group">
                              <label for="sel1" class="control-label col-sm-3">Mark:</label>
                              <div class="col-sm-7">
                                <select tabindex="2" selected="2" class="form-control col-sm-3" ng-model="ACtrl.feedback.markId" required>
                                  <option value="1">Good</option>
                                  <option value="2">Neutral</option>
                                  <option value="0">Bad</option>
                                </select>
                              </div>
                            </div>
                            <div>
                              <div class="form-group">
                                <label class="control-label col-sm-3" for="categoryparent">Feedback:</label>
                                <div class="col-sm-7">
                                  <textarea tabindex="3" class="form-control" rows="5" id="comment" maxlength="255" ng-model="ACtrl.feedback.feedback"
                                    placeholder="Enter feedback"></textarea>
                                  </div>
                                </div>
                              </div>
                            </form>
                          </div>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-success"
                              ng-click="ACtrl.createFeedback(ACtrl.feedback)" tabindex="4">Send</button>
                              <button type="button" class="btn btn-default"
                                data-dismiss="modal">Cancel</button>
                              </div>
                            </div>
                          </div>
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
                                  <div>Current price: <span ng-if="!lot.bestBid.amount">{{lot.startPrice}}</span>
                                  <span class="lotBestBid">{{lot.bestBid.amount}}</span>$
                                </div>
                                <div>Sold: <span class="DateTime">{{lot.startDate + lot.duration | date:'yyyy-MM-dd HH:mm:ss'}}</span></div>
                                <div>Winner: <a href="#/user_feedbacks/{{lot.bestBid.bidder.id}}">{{lot.bestBid.bidder.name}}</a></div>
                              </div>
                              <div class="panel-footer">
                                <div class="row">
								<div class="col-xs-6">
                      				<a title="Contact with buyer." href=""
                        				ng-click="ACtrl.sendQuestion(lot.bestBid.bidder.email, lot.name)">
                        				<span class="glyphicon glyphicon-question-sign footer-glyph footer-but-inside"></span>
                      				</a>
								</div>
								<div class="col-xs-6">
                      				<a title="Send feedback." href="" ng-click="ACtrl.showModalFeedback( lot.id, lot.owner.id, lot.bestBid.bidder.id)">
                        				<span class="glyphicon glyphicon-heart-empty footer-glyph footer-but-inside"></span>
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
        </div>

        <!--End of my account-->
