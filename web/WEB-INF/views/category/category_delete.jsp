
<div class="container">
    <div class="row">
      <div class="col-md-8 col-md-offset-2">
        <div class="panel panel-login">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-2"></div>
              <div class="col-sm-3">
                <a href="#category/create"><h4>Create a category</h4></a>
              </div>
              <div class="col-sm-2"></div>
              <div class="col-sm-3">
                <a href="#category/delete" class="active"><h4>Delete a category</h4></a>
              </div>
              <div class="col-xs-2"></div>
            </div>
            <hr/>
          </div>
          <div class="panel-body">
            <div class="row">
              <div class="col-lg-12">
                <!-- # replace http://phpoll.com/register/process -->
                <form id="register-form" ng-controller="CategoryController as categoriesCtrl" class="form-horizontal" action="#" method="delete" role="form" style="display: block;">
                  <div>
                    <div class="form-group">
                      <label class="control-label col-sm-3" for="categoryparent">Category:</label>
                      <div class="col-sm-7">
                        <select ng-model="selectedCategory" ng-options="x.name for x in categoriesCtrl.allcategories" id="categoryselect" class="form-control text-capitalize">
                        </select>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="control-label col-sm-3" for="subcategoryparent">Subcategory:</label>
                      <div class="col-sm-7">
                        <div>
                          <select ng-model="selectedSubcategory" ng-options="x.name for x in selectedCategory.subcategories" id="subcategoryselect" class="form-control text-capitalize">
                          </select>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="row">
                      <div class="col-sm-6 col-sm-offset-3  col-xs-6 col-xs-offset-3 ">
                        <label></label>
                        <input ng-if="!selectedSubcategory" type="button" name="deletesubcategory-submit" id="deletesubcategory-submit" ng-click="categoriesCtrl.deleteCategory(selectedCategory.id)" tabindex="5" class="form-control btn btn-register" value="delete">
                        <input ng-if="selectedSubcategory" type="button" name="deletesubcategory-submit" id="deletesubcategory-submit" ng-click="categoriesCtrl.deleteSubcategory(selectedSubcategory.id)" tabindex="5" class="form-control btn btn-register" value="delete">
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
