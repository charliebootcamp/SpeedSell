<section>
	<div class="container" ng-controller="SendQuestionController as sqc">
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-login">
					<div class="panel-heading">
						<div class="row">
							<div class="col-xs-3"></div>
							<div class="col-xs-6">
								<h4>Send message</h4>
							</div>
							<div class="col-xs-3"></div>
						</div>
						<hr />
					</div>
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<form id="feedback-form" class="form-horizontal" role="form" ng-submit="sqc.sendEmail()">
									<div class="alert alert-{{sqc.alertType}}"
										ng-show="sqc.showM">
										<a href="" class="close" ng-click="sqc.showM = false">&times;</a>
										<strong>{{sqc.alertTitle}}</strong> {{sqc.alertMessage}}
									</div>
									<div class="form-group" ng-if="sqc.recEmail != sqc.SSemail">
										<label class="control-label col-sm-3" for="categoryname">Title:</label>
										<div class="col-sm-7">
											<input type="text" name="title" ng-model="sqc.title"
												class="form-control" placeholder="Enter title" required
												readonly maxlength="255">
										</div>
									</div>
									<div class="form-group" ng-if="sqc.recEmail != sqc.SSemail">
										<label class="control-label col-sm-3" for="categoryname">Recipient:</label>
										<div class="col-sm-7">
											<input type="text" name="title" ng-model="sqc.recEmail"
												class="form-control" readonly maxlength="255" required>
										</div>
									</div>
									<div class="form-group">
										<label class="control-label col-sm-3" for="categoryparent">Your
											message:</label>
										<div class="col-sm-7">
											<textarea class="form-control" rows="5" maxlength="255" minlength="10"
												id="comment" ng-model="sqc.message"
												placeholder="Enter message"></textarea>
										</div>
									</div>
									<div class="form-group">
										<div class="row">
											<div class="col-sm-2 col-sm-offset-5 col-xs-4 col-xs-offset-4">
												<br> <input type="submit" name="question-submit"
													id="question-submit" tabindex="4"
													class="form-control btn btn-register" value="Send">
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
