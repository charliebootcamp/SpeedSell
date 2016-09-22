<!-- Start reset password form ======================================-->

<div class="container" ng-controller="PersonController as personCtrl">
  <div class="row">
    <div class="col-md-8 col-md-offset-2">
      <div class="panel panel-login card">
        <div class="panel-heading">
          <div class="row">
            <div class="col-xs-offset-4 col-xs-5">
              <a href="#resetPassword">Reset password</a>
            </div>
          </div>
          <hr/>
        </div>
        <div class="panel-body">
          <div class="row">
            <div class="col-lg-12">
              <div class="alert alert-{{personCtrl.alertType}}" ng-show="personCtrl.showMessage">
                 <a href="" class="close" ng-click="personCtrl.showMessage = false">&times;</a>
                <strong>{{personCtrl.title}}</strong> {{personCtrl.message}}
              </div>
              <form id="login-form" ng-submit="personCtrl.resetPassword('${h}')" class="form-horizontal"  role="form">
                <input type="hidden" id="h" name="h"  value="${h}">
                <div class="form-group">
                  <label class="control-label col-sm-3" for="newPass">New password:</label>
                  <div class="col-sm-9">
                    <input type="password" name="newPass" ng-model="personCtrl.newPass" id="newPass" tabindex="1" class="form-control" placeholder="New password" value="" required>
                  </div>
                </div>
                <div class="form-group">
                  <label class="control-label col-sm-3" for="newPassConfirm">Again:</label>
                  <div class="col-sm-9">
                    <input type="password" name="newPassConfirm" ng-model="personCtrl.passConfirm" ng-blur="personCtrl.comparePass(personCtrl.newPass, personCtrl.passConfirm)" id="newPassConfirm" tabindex="2" class="form-control" placeholder="One more time" required>
                  </div>
                </div>
                <div class="form-group">
                  <div class="row">
                    <div class="col-sm-4 col-sm-offset-4 col-xs-4 col-xs-offset-4">
                      <input type="submit"  name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="Reset">
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
<!--End reset password form ==========================-->
