<div class="container" ng-controller="HomeController as homeCtrl">
  <br>
    <div id="myCarousel" class="carousel slide hidden-xs" data-ride="carousel">
      <div class="carousel-inner" role="listbox" style="background: white;">
        <div class="item" ng-class="{active: $first==true}"
          ng-repeat="plot in homeCtrl.premlots">
          <a href="#lot/{{plot.id}}"> <img class="carouselimg"
            ng-src="image/{{plot.img}}" alt="laptop"
            style="width: auto; height: 300px">
            <div class="carousel-caption">
              <h3>{{plot.name}}</h3>
              <p>{{plot.info}}</p>
              <h4>Current price: {{plot.currentPrice}} $</h4>
            </div>
          </a>
        </div>
      </div>
      <a target="_self" class="left carousel-control" href="#myCarousel"
        role="button" data-slide="prev"> <span
        class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span> <span
        class="sr-only">Previous</span>
      </a> <a target="_self" class="right carousel-control" href="#myCarousel"
      role="button" data-slide="next"> <span
      class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>

  <!--End carousel-->
  <!--Hot lots-->
  <div class="row-eq-height">
    <div class="col-sm-4" ng-repeat="limitLots in homeCtrl.limitLots">
      <div class="card">
        <a href="#lot/{{limitLots.id}}">
        	<div class="img-container">
        		<img ng-src="image/{{limitLots.img}}" class="lotimg" alt="Image lot">
        	</div>
        </a>
          <div class="card-container">
            <div class="text-center">
              <a href="#lot/{{limitLots.id}}"><strong>{{limitLots.name}}</strong></a>
            </div>
            <div>
              Current price: <span ng-if="!limitLots.bestBid.amount">{{limitLots.startPrice}}</span>
              <span class="lotBestBid" ng-if="limitLots.bestBid.amount">{{limitLots.bestBid.amount}}</span>$
            </div>
            <div>
              Time left: {{limitLots.timeLeft.t}} <span
              class="label label-danger"> <span
              ng-if="limitLots.timeLeft.days" ng-bind="limitLots.timeLeft.days">
            </span><span ng-if="limitLots.timeLeft.days"> day(s) </span> <span
            ng-bind="limitLots.timeLeft.hours"> </span>:<span
            ng-bind="limitLots.timeLeft.minutes"> </span>:<span
            ng-bind="limitLots.timeLeft.seconds"> </span>
          </span>
        </div>
      </div>
    </div>
  </div>
  <div class="col-sm-12 hidden-xs">
  <div class="row">
  <div class="col-xs-12">
     View records at a time.
  </div>
  </div>
  <div class="row">
     <div class="col-ms-2 col-sm-2">
    <select class="form-control" ng-model="viewby" ng-change="setItemsPerPage(viewby)">
    <option selected">3</option>
    <option>6</option>
    <option>9</option></select>
    </div>
    </div>
  </div>
  </div>
    <div class="text-center" >
    <div class="row">
    <div class="col-xs-12">
      <a href="#ldlimit/{{currentPage}}" class="lot-link">
        <ul uib-pagination boundary-links="true" total-items="totalItems" ng-model="currentPage" ng-change="changePage()"
          items-per-page="itemPerPage" max-size="3" class="pagination-sm" previous-text="&lsaquo;" next-text="&rsaquo;" first-text="&laquo;" last-text="&raquo;">
        </ul>
      </a>
      </div>
     </div>
    </div>
  </div>
</div>
