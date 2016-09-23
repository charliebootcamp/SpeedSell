app.controller('SendQuestionController',
		function($scope,$localStorage, personService){
			var sq = this;
			sq.title = $localStorage.title || "Question";
			sq.message;
			sq.recEmail = $localStorage.recEmail;
			sq.SSemail = "speedsellauction@gmail.com";
			sq.alertMessage;
			sq.alertTitle;
			sq.showM = false;
			sq.alertType;
			sq.senderEmail;
			sq.senderName;

			sq.showMessage = function(message, type , title){
				sq.alertMessage = message;
				sq.alertType = type;
				sq.alertTitle = title;
				sq.showM = true;
			}

			sq.sendEmail = function() {
				sq.showM = false;
				if(sq.message && sq.message.length > 10){
					var ltr = {
						title : sq.title,
						message : sq.message,
						recEmail : $localStorage.recEmail || sq.SSemail,
						senderName : sq.senderName || null,
						senderEmail : sq.senderEmail || $localStorage.curruser.email
					}
					personService.sendEmail(ltr).then(function(response) {
						sq.showMessage("Your message was successfully sended.", "success", "Success!");
						$localStorage.title = null;
						$localStorage.recEmail = null;
					},function(error){
						sq.showMessage(error.statusText, "danger", "Error!");
					});
				}else{
					sq.showMessage("Message is too short.", "danger", "Error!");
				}
			}
});
