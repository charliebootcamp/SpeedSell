<div class="container" id="myaccount" ng-controller="PersonController as ctrl" data-ng-init="ctrl.settingsInit()">

  <!-- Nav tabs -->
  <div class="nav-wrap">
    <ul class="nav nav-tabs" role="tablist">
      <li role="presentation"><a href="#myaccount/orders_active">Orders</a></li>
      <li role="presentation"><a href="#myaccount/lots_active">Lots</a></li>
      <li role="presentation"><a href="#myaccount/feedbacks">Feedbacks</a></li>
      <li role="presentation" class="active"><a href="#myaccount/settings">Settings</a></li>
    </ul>
  </div>

  <!-- Tab panes -->
  <div class="panel-body panel card">
          <div class="row">
            <div class="col-lg-12">
              <div class="alert alert-{{ctrl.alertType}}" ng-show="ctrl.showMessage">
                 <a href="" class="close" ng-click="ctrl.showMessage = false">&times;</a>
                <strong>{{ctrl.title}}</strong> {{ctrl.message}}
              </div>

              <form id="register-form" class="form-horizontal" role="form"
					method="post" role="form" ng-submit="ctrl.updatePerson()">

                <div class="form-group">
                  <label class="control-label col-sm-2" for="address">Full name:</label>
                  <div class="col-sm-10">
                    <input type="text" name="fullname" id="fullname" tabindex="1"
                      class="form-control" placeholder={{ctrl.person.fullName}} value=""
                      ng-model="ctrl.fullname" maxlength="255">
                  </div>
                </div>

                <div class="form-group">
                  <label class="control-label col-sm-2" for="email">Email:</label>
                  <div class="col-sm-10">
                    <input type="email" name="email" id="email" tabindex="2"
                      class="form-control" placeholder={{ctrl.person.email}} value=""
                      ng-model="ctrl.email" ng-blur="ctrl.checkEmail()" maxlength="255">
                  </div>
                </div>

                <div class="form-group">
                  <label class="control-label col-sm-2" for="password">Current password:</label>
                  <div class="col-sm-10">
                    <input type="password" name="password" maxlength="128" id="old-password"
                      tabindex="3" class="form-control" placeholder="Put current password"
                      ng-model="ctrl.pass" ng-blur="ctrl.checkPassword()" required>
                  </div>
                </div>

                <div class="form-group">
                  <label class="control-label col-sm-2" for="password">New password:</label>
                  <div class="col-sm-10">
                    <input type="password" name="password" id="new-password" maxlength="128" minlength="6"
                      tabindex="4" class="form-control" placeholder="Put new password" ng-model="ctrl.newPass">
                  </div>
                </div>

                <div class="form-group">
                  <label class="control-label col-sm-2" for="confirm-password">Confirm new password:</label>
                  <div class="col-sm-10">
                    <input type="password" name="confirm-password" id="confirm-password" maxlength="128" minlength="6"
                      tabindex="5" class="form-control" placeholder="Confirm new password" ng-model="ctrl.passConfirm" ng-blur="ctrl.comparePass(ctrl.newPass, ctrl.passConfirm)">
                  </div>
                </div>

                <div class="form-group">
                  <label class="control-label col-sm-2" for="tel">Phone:</label>
                  <div class="col-sm-10">
                    <input type="text" name="tel" id="tel" tabindex="6" class="form-control" maxlength="10"
                      placeholder={{ctrl.person.phone}} value="" ng-model="ctrl.phone" ng-blur="ctrl.checkPhone()">
                  </div>
                </div>

                <div class="form-group">
                  <label class="control-label col-sm-2" for="address">Address:</label>
                  <div class="col-sm-10">
                    <input type="text" name="homeAddress" id="address" tabindex="7" maxlength="255"
                      class="form-control" placeholder={{ctrl.person.homeAddress}} value=""
                      ng-model="ctrl.address">
                  </div>
                </div>

                <div class="form-group">
                  <div class="row">
                    <div class="col-sm-2 col-sm-offset-5 col-xs-8 col-xs-offset-2">
                      <br>
                      <input type="submit" name="register-submit" id="register-submit"
                        tabindex="8" class="form-control btn btn-register" value="Update info">
                    </div>
                  </div>
                </div>

              </form>
            </div>
          </div>
        </div>
</div>

<!--End of my account-->
