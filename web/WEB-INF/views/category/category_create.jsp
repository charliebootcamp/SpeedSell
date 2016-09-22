
<div class="container">
    <div class="row">
      <div class="col-md-8 col-md-offset-2">
        <div class="panel panel-login">
          <div class="panel-heading">
            <div class="row">
              <div class="col-xs-2"></div>
              <div class="col-sm-3">
                <a href="#category/create" class="active"><h4>Create a category</h4></a>
              </div>
              <div class="col-sm-2"></div>
              <div class="col-sm-3">
                <a href="#category/delete"><h4>Delete a category</h4></a>
              </div>
              <div class="col-xs-2"></div>
            </div>
            <hr/>
          </div>
          <div class="panel-body">
            <div class="row">
              <div class="col-lg-12">
                <!-- # replace http://phpoll.com/register/process -->
                <form id="register-form" ng-controller="CategoryController as categoriesCtrl" class="form-horizontal" action="#" method="post" role="form" style="display: block;">

                  <div class="form-group">
                    <label class="control-label col-sm-3" for="categoryname">Category name:</label>
                    <div class="col-sm-7">
                      <input type="text" name="category" ng-model="categoriesCtrl.nameInput" id="categoryname"  class="form-control" placeholder="Enter category name" required>
                    </div>
                  </div>
                  <div>
                    <div class="form-group">
                      <label class="control-label col-sm-3" for="categoryparent">Category:</label>
                      <div class="col-sm-7">
                        <select ng-model="selectedCategory" ng-options="x.name for x in categoriesCtrl.allcategories" id="categoryselect" class="form-control text-capitalize" >
                        </select>
                      </div>
                      <!-- <input type="text" name="categoryparent" id="categoryparent" tabindex="2" class="form-control" placeholder="Choose category parent"> -->
                    </div>
                  </div>
                  <div class="form-group">
                    <label class="control-label col-sm-3" for="categorycom">Category commission:</label>
                    <div class="col-sm-7">
                      <input type="text" name="categorycom" ng-model="categoriesCtrl.comInput" id="categorycom"  class="form-control" placeholder="Enter subcategory commission" only-digits>
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="row">
                      <div class="col-sm-6 col-sm-offset-3  col-xs-6 col-xs-offset-3 ">
                        <label></label>
                        <input type="button" name="createcategory-submit" id="createcategory-submit" ng-click="categoriesCtrl.insertCategory(selectedCategory.id)" tabindex="4" class="form-control btn btn-register" value="Create">
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
