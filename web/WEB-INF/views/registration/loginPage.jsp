<!-- Start autorization form ======================================-->

<div class="container" ng-controller="PersonController as personCtrl">
	<div class="row">
		<div class="col-md-8 col-md-offset-2">
			<div class="panel panel-login card" ng-hide="personCtrl.success2">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-6">
							<a href="#login" class="active">Login</a>
						</div>
						<div class="col-xs-6">
							<a href="#registration">Register</a>
						</div>
					</div>
					<hr />
				</div>
				<div class="panel-body">
					<div class="row">
						<div class="col-lg-12">
							<div class="alert alert-{{personCtrl.alertType}}"
								ng-show="personCtrl.showMessage">
								<a href="" class="close"
									ng-click="personCtrl.showMessage = false">&times;</a> <strong>{{personCtrl.title}}</strong>
								{{personCtrl.message}}
							</div>
							<!-- # replace http://phpoll.com/login/process -->
							<form id="login-form" class="form-horizontal"
								method="post" role="form" ng-submit="personCtrl.login()">
								<div class="form-group">
									<label class="control-label col-sm-2" for="email">Email:</label>
									<div class="col-sm-10">
										<input type="text" name="email" ng-model="personCtrl.email"
											id="email" tabindex="1" class="form-control"
											placeholder="Email" value="" required maxlength="255">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-2" for="username">Password:</label>
									<div class="col-sm-10">
										<input type="password" name="password"
											ng-model="personCtrl.pass" id="password" tabindex="2"
											class="form-control" placeholder="Password" required>
									</div>
								</div>
								<div class="form-group">
									<div class="row">
										<div class="col-sm-4 col-sm-offset-4 col-xs-4 col-xs-offset-4 ">
											<input type="submit"
												name="login-submit" id="login-submit" tabindex="4"
												class="form-control btn btn-login" value="Log In">
										</div>
									</div>
								</div>
								<div class="form-group">
									<div class="row">
										<div class="col-lg-12">
											<div class="text-center">
												<!-- # replace http://phpoll.com/recover -->
												<a data-target="#collapseOne" tabindex="5"
													class="forgot-password" data-toggle="collapse" href="">Forgot
													Password?</a>
												<!-- <button type="button" class="btn btn-info" data-toggle="collapse"
												data-target="#resetForm">Simple collapsible</button> -->
											</div>
										</div>
									</div>
								</div>
							</form>


							<div id="collapseOne" class="collapse">
								<form id="resetForm" class="form-horizontal" ng-submit="personCtrl.resetPswdReq()"
									method="post" style="display: block;">
									<div class="row">
										<div class="col-sm-5 col-sm-offset-4 text-center">
											<h3 class="text-danger">Reset your password</h3>
										</div>

									</div>
									<div class="form-group">
										<label class="control-label col-sm-2" for="email">Email:</label>
										<div class="col-sm-10">
											<input type="text" name="email" ng-model="personCtrl.email"
												id="email" tabindex="6" class="form-control"
												placeholder="Email" value="" required maxlength="255">
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-4 col-sm-offset-4 col-xs-4 col-xs-offset-4">
												<input type="submit"
													name="reset-submit" id="reset-submit" tabindex="7"
													class="form-control btn btn-login" value="Reset">
											</div>
										</div>
									</div>

								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="alert alert-success" ng-if="personCtrl.success2">
				Your password reset email should arrive shortly. If you don't see
				it, please check your spam folder, sometimes it can end up there!</div>
		</div>
	</div>
</div>
<!--End autorization form ==========================-->
