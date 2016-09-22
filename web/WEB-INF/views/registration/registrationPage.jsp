<!-- Start autorization form ======================================-->

<div class="container" ng-controller="PersonController as personCtrl">
	<div class="row">
		<div class="col-md-8 col-md-offset-2">
			<div class="panel panel-login card" ng-hide="personCtrl.success">
				<div class="panel-heading">
					<div class="row">
						<div class="col-xs-6">
							<a href="#login">Login</a>
						</div>
						<div class="col-xs-6">
							<a href="#registration" class="active">Register</a>
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
									ng-click="personCtrl.showMessage = false">&times;</a>
									<strong>{{personCtrl.title}}</strong>{{personCtrl.message}}
							</div>
							<form id="register-form" name="myForm" class="form-horizontal"
								ng-submit="personCtrl.insertPerson()" role="form">
								<p class='col-sm-offset-1' style='color: #959595;'>Please
									complete the form below. Fields marked with * are required.</p>
								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-2"
										for="username">Username*</label>
									<div class="col-sm-9">
										<input type="text" name="username"
											ng-blur="personCtrl.checkUsername()" tabindex="1"
											id="reg-username" ng-model="personCtrl.username" maxlength="255"
											class="form-control" placeholder="Username" value="" required>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-2"
										for="fullname">Fullname*</label>
									<div class="col-sm-9">
										<input tabindex="2" type="text" name="fullname"
											id="reg-fullname" ng-model="personCtrl.fullname" maxlength="255"
											class="form-control" placeholder="Fullname" value="" required>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-2"
										for="email">Email*</label>
									<div class="col-sm-9">
										<input type="email" name="email"
											ng-blur="personCtrl.checkEmail()" id="new_email" tabindex="3"
											ng-model="personCtrl.email" class="form-control" maxlength="255"
											placeholder="Email Address" value="" required>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-2"
										for="password">Password*</label>
									<div class="col-sm-9">
										<input type="password" name="password" minlength="6" maxlength="128"
											ng-model="personCtrl.pass" id="reg-password" tabindex="4"
											class="form-control" placeholder="Password" required>
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-2"
										for="confirm-password">Confirm*</label>
									<div class="col-sm-9">
										<input type="password" name="confirm-password"
											id="confirm-password" tabindex="5" class="form-control"
											placeholder="Confirm Password" ng-model="personCtrl.passConfirm"
											ng-blur="personCtrl.comparePass(personCtrl.pass, personCtrl.passConfirm)"
											required maxlength="128" minlength="6">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-2" for="tel">Phone*</label>
									<div class="col-sm-9">
										<input type="text" name="tel" id="tel"
											ng-blur="personCtrl.checkPhone()" tabindex="6"
											ng-model="personCtrl.phone" class="form-control"
											placeholder="Phone number" value="" maxlength="10">
									</div>
								</div>
								<div class="form-group">
									<label class="control-label col-sm-offset-1 col-sm-2"
										for="address">Address</label>
									<div class="col-sm-9">
										<input type="text" name="address" id="address"
											ng-model="personCtrl.address" tabindex="7" maxlength="255"
											class="form-control" placeholder="Home address" value="">
									</div>
								</div>
								<div class="form-group">
									<div class="row">
										<div class="col-sm-6 col-sm-offset-3 col-xs-6 col-xs-offset-3">
											<input type="submit" name="register-submit"
												id="register-submit" tabindex="8"
												class="form-control btn btn-register" value="Register Now">
										</div>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<div class="alert alert-success" ng-if="personCtrl.success">
				Letter with confirmation link was sent to your email. Please click
				that link to verify your email.</div>
		</div>
	</div>
</div>
<!--End autorization form ==========================-->
