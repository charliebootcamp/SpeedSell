<section>
  <div class="container" ng-controller="CreateLotController as newLotCtrl">
    <div class="row">
      <div class="col-md-8 col-md-offset-2">
        <div class="panel panel-login card">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-3"></div>
              <div class="col-xs-6">
                <h4>Create a lot</h4>
              </div>
              <div class="col-xs-3"></div>
            </div>
            <hr/>
          </div>
          <div class="panel-body">
            <div class="row">
              <div class="col-sm-12">
                <form id="createLot-form" class="form-horizontal" role="form" ng-submit="newLotCtrl.insertLot()">
                  <div class="form-group">
                    <label class="control-label col-sm-3" for="lotname">Lot name:</label>
                    <div class="col-sm-9">
                      <input tabindex="1" type="text" name="lot" id="lotname" ng-model="newLotCtrl.name" class="form-control" placeholder="Enter lot name" value="" required>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="control-label col-sm-3" for="text">Description:</label>
                      <div class="col-sm-9">
                        <textarea tabindex="2" class="form-control" rows="5" id="lotdescription" maxlength="1024"
                          ng-model="newLotCtrl.info"  placeholder="Enter lot description"></textarea>
                        </div>
                      </div>
                      <div class="form-group" >
                        <label class="control-label col-sm-3" for="categoryparent">Category:</label>
                        <div class="col-sm-9">
                          <select tabindex="3" required ng-model="selectedCategory"  ng-options="x.name for x in newLotCtrl.categories" id="categoryselect" class="form-control text-capitalize" >
                          <option value="" disabled selected>Select category</option>
                          <option>
                          </option>
                          </select>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-sm-3" for="subcategoryparent">Subcategory:</label>
                        <div class="col-sm-9">
                          <div>
                            <select tabindex="4" ng-model="selectedSubcategory" ng-selected="newLotCtrl.cid = selectedSubcategory.id" ng-options="x.name for x in selectedCategory.subcategories" id="subcategoryselect" class="form-control text-capitalize" required>
	                          <option value="" disabled selected>Select subcategory</option>
                            <option>
                            </option>
                            </select>
                          </div>
                        </div>
                      </div>
                      <div class="form-group">
                        <label class="control-label col-sm-3 " for="image">Image:</label>
                        <div class="col-sm-9 ">
                          <label tabindex="5" class="btn-file form-control">
                            <input id="image" type="file" file-model="newLotCtrl.myFile" required/>
                          </label>
                        </div>
                      </div>

                      <div class="form-group">
                        <label class="control-label col-sm-3" for="lotduration">Duration (hours):</label>
                        <div class="col-sm-9">
                          <input class="form-control" type="text" maxlength="2"
                            name="lotduration" id="lotduration" ng-model="newLotCtrl.duration"
                            class="form-control" placeholder="Enter lot duration from 24 to 72" only-digits
                            ng-blur="newLotCtrl.duration = newLotCtrl.checkNum(newLotCtrl.duration, 24, 72)" tabindex="6"
                            required>
                          </div>
                        </div>
                        <div class="form-group">
                          <label class="control-label col-sm-3" for="lotredemption">Buy now price($):</label>
                          <div class="col-sm-9">
                            <input tabindex="7" type="text" only-digits maxlength="12" name="lotredemption" id="lotredemption"
                              ng-model="newLotCtrl.redemption" class="form-control" placeholder="Enter price for buy now option.">
                            </div>
                          </div>
                          <div class="form-group">
                            <label class="control-label col-sm-3" for="lotredemption">Start price($):</label>
                            <div class="col-sm-9">
                              <input tabindex="8" type="text" only-digits maxlength="12" name="lotstartprice" id="lotstartprice"
                                ng-model="newLotCtrl.startprice" class="form-control" placeholder="Enter start price for lot" required>
                              </div>
                            </div>
                            <div class="form-group">
                              <div class="row">
                                <div class="col-sm-6 col-sm-offset-3 col-xs-6 col-xs-offset-3">
                                  <label></label>
                                  <input  type="submit"
                                    name="createcategory-submit" id="createcategory-submit" tabindex="9"
                                    class="form-control btn btn-register" value="Create">
                                  </div>
                                </div>
                              </div>
                            </form>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </section>
