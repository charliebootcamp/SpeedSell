
<div class="container-fluid" ng-controller="SearchingController as searchingCtrl">
  <div class="row"  >
    <div class="col-sm-3">
      <aside class="container-fluid">
      <div class="row container-fluid ">
            <div class="col-sm-12">
              <h3 class="white">Parameters</h3>
            </div>
          </div>
      <ul class="list-group params">
            <li class="list-group-item" >
              <h5 class="list-group-item-heading">Price</h5>
              <p class="list-group-item-text">
                <input type="number" id="from" placeholder=" from" ng-model="searchingCtrl.fromPrice" max="{{searchingCtrl.toPrice}}" min ="0" ng-blur="searchingCtrl.updateFromPrice(searchingCtrl.fromPrice)">
                -
                <input type="number" id="to" placeholder=" to" ng-model="searchingCtrl.toPrice" min="{{searchingCtrl.fromPrice}}" ng-blur="searchingCtrl.updateToPrice(searchingCtrl.toPrice)">
              </p>
              </li>
              <li class="list-group-item">
                <h5 class="list-group-item-heading">Type of purchase</h5>
                <p class="list-group-item-text">
                  <input type="checkbox" ng-model="searchingCtrl.buyNow" ng-click="searchingCtrl.updateRedemption($event, searchingCtrl.buyNow)"> Buy now<br>
                </p>
              </li>
            </ul>
       	<div class="row container-fluid ">
            <div class="col-sm-12">
              <h3 class="white">Choose categories: <button type="button" class="btn btn-primary" ng-click="searchingCtrl.changeCategories()">Add all/clean all</button></h3>
            </div>
        </div>
        <ul class="list-group">
          <li class="list-group-item">
            <div class="panel-group">
              <div class="panel " ng-repeat="cat in searchingCtrl.categories">
                <div class="panel-heading">
                  <p class="panel-title">
                    <a class="text-capitalize" data-toggle="collapse in" target="_self" href="#collapse{{cat.id}}"> {{cat.name}}</a>
                  </p>
                </div>
                <div id="collapse{{cat.id}}" class="panel-collapse collapsein list-group">

                	<label class="list-group-item text-capitalize" ng-repeat="subcategory in cat.subcategories">
                		<input type="checkbox" ng-click="searchingCtrl.updateSelection($event, subcategory.id, subcategory)" data-checklist-model="cat.subcategories"  data-checklist-value="subcategory.id" ng-checked="searchingCtrl.Check(subcategory.id)">{{subcategory.name}}
                	</label>

                </div>
              </div>
            </div></li>
          </ul>
          </aside>
        </div>
        <div class="col-sm-9" >
          <div class="container-fluid main" infinite-scroll="searchingCtrl.getPageLots()">
          	<div ng-show="searchingCtrl.lotsEmpty"><h3 class="white"><br/><br/><br/>No lots for this search request.</h3>
          	</div>
             <div ng-if="!searchingCtrl.lotsEmpty" class="col-sm-4" ng-repeat="lot in searchingCtrl.lots">
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
                     <div>
                       Current price: <span ng-if="!lot.bestBid.amount">{{lot.startPrice}}</span>
                       <span class="lotBestBid" ng-if="lot.bestBid.amount">{{lot.bestBid.amount}}</span>$
                     </div>
                     <div>
                       Time left: {{lot.timeLeft.t}} <span
                       class="label label-danger"> <span
                       ng-if="lot.timeLeft.days" ng-bind="lot.timeLeft.days">
                     </span><span ng-if="lot.timeLeft.days"> day(s) </span> <span
                     ng-bind="lot.timeLeft.hours"> </span>:<span
                     ng-bind="lot.timeLeft.minutes"> </span>:<span
                     ng-bind="lot.timeLeft.seconds"> </span>
                   </span>
                 </div>
               </div>
             </div>
           </div>
          </div>
        </div>
      </div>
    </div>
