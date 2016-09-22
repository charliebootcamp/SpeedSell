<div class="container-fluid" ng-controller="LotController as LotCtrl">
	<ul class="breadcrumb text-capitalize">
		<li><a href="#/"><span class="glyphicon glyphicon-home"></span></a></li>
		<li><a  href="#/lots/{{LotCtrl.cat.id}}">{{LotCtrl.cat.name}}</a></li>
		<li><a href="#/lots/{{LotCtrl.subcat.id}}">{{LotCtrl.subcat.name}}</a></li>
	</ul>
	<h1>
		{{LotCtrl.lot.name}}
	</h1>
	<div class="panel panel-default card">
		<div class="panel-heading">
			<div class="row">
				<div class="col-sm-4 text-left">
					<h4>Auction No <span class="label label-danger">{{LotCtrl.lot.id}}</span></h4>
				</div>
				<div class="col-sm-4 text-center">
					<button class="btn btn-danger text-uppercase" ng-if="LotCtrl.lot.state.id != 3">{{LotCtrl.lot.state.name}}</button>
				</div>
				<div class="col-sm-4 text-right" ng-if="LotCtrl.lot.state.id == 3">
					<h4>Time left:
						<span class="label label-danger">
							<span ng-if="LotCtrl.lot.timeLeft.days" ng-bind="LotCtrl.lot.timeLeft.days">
							</span><span ng-if="LotCtrl.lot.timeLeft.days"> day(s)	</span>
							<span ng-bind="LotCtrl.lot.timeLeft.hours">
							</span>:<span ng-bind="LotCtrl.lot.timeLeft.minutes">
						</span>:<span ng-bind="LotCtrl.lot.timeLeft.seconds">
					</span>
				</span>
			</h4>
		</div>
	</div>
</div>
<div class="panel-body">
	<div class="col-md-4 col-sm-4">
		<div class="card">
			<img ng-src="image/{{LotCtrl.lot.img}}" class="img-responsive"
				alt="Image lot" style="width: 100%;">
			</div>
				<div class="panel panel-default card">
					<div class="panel-body">
						<h4>
							Seller:	{{LotCtrl.lot.owner.name}}
						</h4>
					</div>
					<div class="list-group">
						<a href="#/user_lots/{{LotCtrl.lot.owner.id}}" class="list-group-item">Show all seller lots</a>
						<a href="#/user_feedbacks/{{LotCtrl.lot.owner.id}}"	class="list-group-item">Show feedbacks</a>
					</div>
					<div class="panel-footer">
						<a href="" class="btn btn-info" ng-click="LotCtrl.sendQuestion()">Ask question</a>
					</div>
				</div>
		</div>
		<div class="col-md-8 col-sm-8">
			<div class="alert alert-{{LotCtrl.alertType}}" ng-show="LotCtrl.showMessage">
				<a href="" class="close" ng-click="LotCtrl.pageReload()">&times;</a>
				<strong>{{LotCtrl.title}}</strong> {{LotCtrl.message}}
			</div>
			<div class="panel-body card">
				<div class="col-sm-3">
					<label for="currPrice">Current price:</label><br/>
					<button class="btn" id="currPrice">$ <span ng-if="!LotCtrl.lot.bestBid.amount">{{LotCtrl.lot.startPrice}}</span>{{LotCtrl.lot.bestBid.amount}}</button>
				</div>
				<div ng-if="LotCtrl.lot.state.id == 3">
					<div class="col-sm-2" ng-if="LotCtrl.lot.redemption">
						<label for="buy">Buy now:</label>
						<button data-toggle="modal" data-target="#payform" type="button"
							class="btn btn-success" name="button" id="buy">$
							{{LotCtrl.lot.redemption}}</button>
						</div>
						<div class="col-sm-2 form-group ">
							<label for="bid">Your bid:</label>
							<input type="number" min="{{LotCtrl.min}}" max="{{LotCtrl.min + 99}}"
								class="form-control" id="bid" ng-model="LotCtrl.newBid">
							</div>
							<div class="col-sm-2">
								<!-- Trigger the modal with a button   data-toggle="modal" data-target="#myModal" -->
								<button type="button" class="btn btn-success btn-bids"
									ng-click="LotCtrl.verify()">Make bid</button>
								</div>
								<div class="col-sm-3">
									<button type="button" class="btn btn-info btn-bids"
										data-toggle="modal"	data-target="#showBids">Bids history
									</button>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<div class="panel card">
									<div class="panel-heading">Description:</div>
									<div class="panel-body" id="description">{{LotCtrl.lot.info}}</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--Modal start-->
			<div class="modal fade" id="showBids" role="dialog">
				<div class="modal-dialog">

					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
							<h4 class="modal-title">Bid made for this lot:</h4>
						</div>
						<div class="modal-body">
							<table class="table">
								<thead>
									<tr>
										<th width="20%">Bid</th>
										<th width="30%">Date</th>
										<th width="50%">User</th>
									</tr>
								</thead>
								<tbody>
									<tr ng-if="!LotCtrl.bids.length">
										<td colspan="3" class="text-center">No bids yet</td>
									</tr>
									<tr ng-repeat="bid in LotCtrl.bids">
										<td>{{bid.amount}} $</td>
										<td>{{bid.bidDate | date:'yyyy-MM-dd HH:mm:ss'}}</td>
										<td>{{bid.bidder.name}}</td>
									</tr>
								</tbody>
							</table>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-default" data-dismiss="modal">OK</button>
						</div>
					</div>
				</div>
			</div>
			<!--Modal end-->
			<!-- Modal -->
			<div class="modal fade" id="Alert" role="dialog">
				<div class="modal-dialog">
					<!-- Modal content-->
					<div class="modal-content">
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">&times;</button>
						</div>
						<div class="modal-body">
							<h4>{{LotCtrl.alertMessage}}</h4>
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-success"	ng-click="LotCtrl.insertBid(LotCtrl.newBid)">
								Yes
							</button>
							<button type="button" class="btn btn-danger" data-dismiss="modal">
								No
							</button>
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
								<div class="col-xs-3">
									<img src="http://i76.imgup.net/accepted_c22e0.png">
									</div>
									<div class="col-xs-3">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
									</div>
								</div>
							</div>

							<div class="modal-body">
								<div class="panel-body">
									<div class="alert alert-success" ng-show="LotCtrl.suc==true">
										<a href="" class="close" ng-click="LotCtrl.suc = false">&times;</a>
										<strong>Thank you for pay</strong>
									</div>
									<div class="alert alert-success" ng-show="LotCtrl.suc==false">
										<a href="" class="close" ng-click="LotCtrl.suc = false">&times;</a>
										<strong>Failed to pay. Check your input and try again.</strong>
									</div>
									<form role="form" id="payment-form">
										<div class="row">
											<div class="col-xs-12">
												<div class="form-group">
													<label for="cardNumber">CARD NUMBER</label>
													<input type="text" only-digits class="form-control" ng-model="LotCtrl.cardNumb" name="cardNumber" placeholder="Valid Card Number" required autofocus />
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-xs-7">
												<div class="form-group">
													<label for="expMonth">EXPIRATION DATE</label>
													<div class="row">
														<div class="col-xs-4 col-xs-offset-3">
															<input type="text" min="0" max="12" class="form-control" ng-model="LotCtrl.expMonth" name="expMonth" placeholder="MM" required  only-digits/>
														</div>
														<div class="col-xs-4">
															<input type="text" min="16" max="20" class="form-control" ng-model="LotCtrl.expYear" name="expYear" placeholder="YY" required only-digits />
														</div>
													</div>
												</div>
											</div>
											<div class="col-xs-3">
												<div class="form-group">
													<label for="cvCode">CV CODE</label>
													<input type="password" class="form-control" ng-model="LotCtrl.cvc" name="cvCode" placeholder="CV" required only-digits/>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-xs-12">
												<button class="btn btn-success btn-lg btn-block" type="submit" ng-click="LotCtrl.pay()">Pay {{lot.subcategory.redemption}}$</button>
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
				<!-- End -->
			</div>
