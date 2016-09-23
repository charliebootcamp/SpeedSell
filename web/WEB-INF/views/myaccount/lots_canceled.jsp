<div class="container" id="myaccount" ng-controller="AccountController as ACtrl" data-ng-init="ACtrl.loadLots(0)">

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
            <li class="active"><a href="#myaccount/lots_canceled">Canceled</a></li>
            <li><a href="#myaccount/lots_created">Created</a></li>
          </ul>
        </div>
        <div class="col-sm-10">
          <div class="tab-content">
            <div id="canceled" class="tab-pane active">
              <div class="container-fluid lot-container">
                <div ng-if="ACtrl.lots.length == 0">
                  <h2>No lots</h2>
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
                        <div class="row">
                        <div class="col-xs-6">
                          <a title="Edit lot." href="" ng-click="ACtrl.showModalEdit(lot)">
                            <span class="glyphicon glyphicon-pencil footer-glyph footer-but-inside"></span>
                          </a>
                        </div>
                        <div class="col-xs-6 footer-but ">
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
      <div class="modal fade" id="editLot" role="dialog">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" id="CloseModal">&times;</button>
              <h4 class="modal-title">Edit lot:</h4>
            </div>
            <div class="modal-body">

              <form id="createLot-form" class="form-horizontal" role="form">
                <div class="form-group">
                  <label class="control-label col-sm-3" for="lotname">Lot name:</label>
                  <div class="col-sm-9">
                    <input type="text" name="lot" id="lotname" ng-model="ACtrl.editLot.name"
                      class="form-control" value="" required>
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="control-label col-sm-3" for="text">Description:</label>
                    <div class="col-sm-9">
                      <textarea tabindex="1" class="form-control" rows="5" id="lotdescription" maxlength="1024"
                        ng-model="ACtrl.editLot.info"></textarea>
                      </div>
                    </div>
                    <div class="form-group" >
                      <label class="control-label col-sm-3" for="categoryparent">Category:</label>
                      <div class="col-sm-9">
                        <select ng-model="selectedCategory" ng-options="x.name for x in ACtrl.categories"
                          id="categoryselect" class="form-control text-capitalize">
                        </select>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="control-label col-sm-3" for="subcategoryparent">Subcategory:</label>
                      <div class="col-sm-9">
                        <div>
                          <select ng-model="selectedSubcategory"
                            ng-options="x.name for x in selectedCategory.subcategories"
                            id="subcategoryselect" class="form-control text-capitalize" required>
                          </select>
                        </div>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="control-label col-sm-3 " for="image">Image:</label>
                      <div class="col-sm-9 ">
                        <label  class="btn-file form-control">
                          <input id="image" type="file" file-model="ACtrl.myFile" />
                        </label>
                      </div>
                    </div>

                    <div class="form-group">
                      <label class="control-label col-sm-3" for="lotduration">Duration (hours):</label>
                      <div class="col-sm-9">
                        <input class="form-control" type="text" name="lotduration" id="lotduration"
                          ng-model="ACtrl.editLot.duration" class="form-control"
                          only-digits ng-blur="ACtrl.editLot.duration = ACtrl.checkNum(ACtrl.editLot.duration)" value="">
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-sm-3" for="lotredemption">Buy now price($):</label>
                        <div class="col-sm-9">
                          <input type="text" only-digits min="0" name="lotredemption" id="lotredemption"
                            ng-model="ACtrl.editLot.redemption" class="form-control" value="" >
                          </div>
                        </div>
                        <div class="form-group">
                          <label class="control-label col-sm-3" for="lotredemption">Start price($):</label>
                          <div class="col-sm-9">
                            <input type="text" only-digits min="0" name="lotstartprice" id="lotstartprice"
                              ng-model="ACtrl.editLot.startPrice" class="form-control" value="" >
                            </div>
                          </div>
                        </form>
                      </div>
                      <div class="modal-footer">
                        <button type="button" class="btn btn-success"
                          ng-click="ACtrl.sendEditedLot(selectedSubcategory.id)">Send</button>
                          <button type="button" class="btn btn-default"
                            data-dismiss="modal">Cancel</button>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!--End of my account-->
