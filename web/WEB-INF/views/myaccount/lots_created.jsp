<div class="container" id="myaccount" ng-controller="AccountController as ACtrl" data-ng-init="ACtrl.loadLots(1)">

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
            <li><a href="#myaccount/lots_wfpaylot">Wait for pay</a></li>
            <li><a href="#myaccount/lots_archive">Archive</a></li>
            <li><a href="#myaccount/lots_canceled">Canceled</a></li>
            <li class="active"><a href="#myaccount/lots_created">Created</a></li>
          </ul>
        </div>
        <div class="col-sm-10">
          <div class="tab-content">
            <div id="createdlots" class="tab-pane active">
              <div class="container-fluid lot-container">
                <div ng-if="ACtrl.lots.length == 0">
                  <h2>No created lots</h2>
                </div>
                <div class="alert alert-{{ACtrl.alertType}}" ng-show="ACtrl.showMessage">
                  <a href="" class="close" ng-click="ACtrl.showMessage = false">&times;</a>
                  <strong>{{ACtrl.title}}</strong> {{ACtrl.message}}
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
                        </div>
                        <div class="panel-footer">
                        <div class="row row-eq-height">
                        <div class="col-xs-4 footer-but" >
                          <a title="Pay a commission {{lot.subcategory.commission}}$." ng-if="!lot.paidCommission" href="" data-toggle="modal" data-target="#payform" ng-click="ACtrl.cur=lot; ACtrl.f=1"  ><span class="glyphicon glyphicon-usd footer-but-inside footer-glyph"></span></a>
                          <span title="Commission paid. Waiting for admin to approve." ng-if="lot.paidCommission" class="glyphicon glyphicon-ok footer-but-inside footer-glyph"></span>
                        </div>
                        <div class="col-xs-4 footer-but" >
                          <a title="You can make your lot premium. To do it you need to pay {{lot.subcategory.commission}}$." class="footer-but-inside" ng-if="lot.premium == null" href="" data-toggle="modal" data-target="#payform" ng-click="ACtrl.cur=lot; ACtrl.f=2"  >
                          	<span class="glyphicon glyphicon-send footer-but-inside footer-glyph">
                          	</span>
                          </a>
                          <span title="Request to make a premium sended." class="glyphicon glyphicon-send footer-but-inside footer-glyph" ng-if="lot.premium == false" style="color:black;"></span>
                          <span title="Lot is premium. This lot will be at carousel After auction starts ." ng-if="lot.premium == true" class="glyphicon glyphicon-sunglasses footer-glyph footer-but-inside"></span>
                        </div>
                        <div class="col-xs-4 footer-but ">
                          <a title="Delete this lot." class="glyphicon glyphicon-trash footer-glyph footer-but-inside" href="" data-toggle="modal" data-target="#confirmDelete" ng-click="ACtrl.cur=lot"></a>
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
    <div class="modal fade" id="confirmDelete" role="dialog">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            Are you sure to delete this lot?
          </div>
          <div class="modal-body">
            <div class="row">
              <div class="col-xs-6">
                <button class="btn yes pull-left" ng-click="ACtrl.deleteLot(ACtrl.cur.id)">Yes</button>
              </div>
              <div class="col-xs-6">
                <button class="btn nope pull-right" data-dismiss="modal" aria-label="Close">No</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="payform" role="dialog">
      <div class="modal-dialog">
        <!-- Modal content-->
        <div class="modal-content">
          <div class="modal-header">
            <div class="row">
              <div class="col-xs-6">
                <div class="panel-heading">
                  <h3>Payment</h3>
                </div>
              </div>
              <div class="col-xs-3 hidden-xs">
                <img src="http://i76.imgup.net/accepted_c22e0.png">
              </div>
                <div class="col-xs-3 pull-right">
                  <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
              </div>
            </div>

            <div class="modal-body">
              <div class="panel-body">
                <div class="alert alert-success" ng-show="ACtrl.suc==true">
                  <a href="" class="close" ng-click="ACtrl.suc = false">&times;</a>
                  <strong>Thank you for pay</strong>
                </div>
                <div class="alert alert-success" ng-show="ACtrl.suc==false">
                  <a href="" class="close" ng-click="ACtrl.suc = false">&times;</a>
                  <strong>Failed to pay. Check your input and try again.</strong>
                </div>
                <form role="form" id="payment-form" ng-submit="ACtrl.pay()">
                  <div class="row">
                    <div class="col-xs-12">
                      <div class="form-group">
                        <label for="cardNumber">CARD NUMBER</label>
                        <input type="text" only-digits class="form-control" ng-model="ACtrl.cardNumb" name="cardNumber" placeholder="Valid Card Number" required autofocus />
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xs-7">
                      <div class="form-group">
                        <label for="expMonth">EXPIRATION DATE</label>
                        <div class="row">
                          <div class="col-xs-4 col-xs-offset-3">
                            <input type="text" min="0" max="12" class="form-control" ng-model="ACtrl.expMonth" name="expMonth" placeholder="MM" required  only-digits/>
                          </div>
                          <div class="col-xs-4">
                            <input type="text" min="16" max="20" class="form-control" ng-model="ACtrl.expYear" name="expYear" placeholder="YY" required only-digits />
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-xs-3">
                      <div class="form-group">
                        <label for="cvCode">CV CODE</label>
                        <input type="password" class="form-control" ng-model="ACtrl.cvc" name="cvCode" placeholder="CV" required only-digits/>
                      </div>
                    </div>
                  </div>
                  <div class="row">
                    <div class="col-xs-12">
                      <button class="btn btn-success btn-lg btn-block" type="submit" >Pay {{ACtrl.cur.subcategory.commission}}$</button>
                    </div>
                  </div>
                  <div class="row" style="display:none;">
                    <div class="col-xs-12">
                      <p class="payment-errors"></p>
                    </div>
                  </div>
                </form>
              </div>

            </div>
          </div>
        </div>
      </div>
    </div>

    <!--End of my account-->
