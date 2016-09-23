<!-- Container (Contact Section) -->
<div class="col-sm-offset-1 col-sm-10">
  <div class="panel panel-default card" ng-controller="SendQuestionController as sqc">
    <h2 class="text-center">CONTACTS</h2>
    <div class="row">
      <div class="col-sm-5">
        <div class="container">
          <p>Contact us and we'll get back to you within 24 hours.</p>
          <p><span class="glyphicon glyphicon-map-marker"></span> Lviv, Ukraine</p>
          <p><span class="glyphicon glyphicon-phone"></span> +38 066 12 34 567</p>
          <p><span class="glyphicon glyphicon-envelope"></span> speedsellauction@gmail.com</p>
        </div>
      </div>
      <div class="col-sm-7">
        <form id="contact">
          <div class="alert alert-{{sqc.alertType}}" ng-show="sqc.showM">
            <a href="" class="close" ng-click="sqc.showM = false">&times;</a>
            <strong>{{sqc.alertTitle}}</strong> {{sqc.alertMessage}}
          </div>
          <div class="row">
            <div class="col-sm-6 form-group">
              <input class="form-control" id="name" ng-model="sqc.senderName" name="name" placeholder="Your name" type="text" required>
              </div>
              <div class="col-sm-6 form-group">
                <input class="form-control" ng-model="sqc.senderEmail" id="email" name="email" placeholder="Your email" type="email" required>
                </div>
              </div>
              <textarea class="form-control" maxLength=255 ng-model="sqc.message" id="comments" name="comments" placeholder="Comment" rows="5"></textarea><br>
              <div class="row">
                <div class="col-sm-12 form-group">
                  <button class="btn btn-info pull-right" type="submit" ng-click="sqc.sendEmail()">Send</button>
                </div>
              </div>
            </form>
          </div>
        </div>
        <br>
          <div class="embed-responsitive">
            <iframe class="embed-responsitive-item" src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d2573.0271468104047!2d24.029478315569346!3d49.84194743890313!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x473add6daf2af431%3A0xd1b86b864117cb0c!2z0JvRjNCy0ZbQstGB0YzQutCwINGA0LDRgtGD0YjQsCwg0JvRjNCy0ZbQsiwg0JvRjNCy0ZbQstGB0YzQutCwINC-0LHQu9Cw0YHRgtGM!5e0!3m2!1suk!2sua!4v1470045946040" width="100%" height="300" frameborder="0" style="border:0">
            </iframe>
          </div>
        </div>

        <!--<div id="googleMap" style="height:300px;width:100%;"></div>-->

      </div>
      <!-- Add Google Maps -->
