$.extend($.fn.dataTableExt.oStdClasses, {
	"sWrapper" : "dataTables_wrapper form-inline"
});

$.validator.addMethod("intValidate", function(value, element) {
	var elementValue = $(element).val();
	return elementValue >= 0 && elementValue.indexOf(".") == -1
			&& elementValue[0] != "-" || elementValue == null;
}, "Please enter a valid number.");

$.validator.addMethod('confirm_password', function() {
	return $('#password').val() === $('#confirmPassword').val();
}, jQuery.validator.messages.equalTo);

$.validator.addMethod('passwordMinLength', function() {
	return $('#password').val().length >= 5;
}, "Password cannot have less than 5 chars");

$.validator.addMethod('maxLength', function(value, element) {
	return value.length < 256;
}, "Value must be less than 256 symbols");

$.validator.addMethod('maxLength128', function(value, element) {
	return value.length < 129;
}, "Value must be less than 129 symbols");

$.validator.addMethod('maxLength64', function(value, element) {
	return value.length < 65;
}, "Value must be less than 65 symbols");

$.validator.addMethod('maxLength10', function(value, element) {
	return value.length <= 10;
}, "Value must be less than 11 symbols");

$.validator.addMethod('maxLength2', function(value, element) {
  return value.length <= 2;
}, "Value must be less than 3 symbols");

$.validator.addMethod('maxLength23', function(value, element) {
	return value.length <= 23;
}, "Value must be less than 24 symbols");
$.validator.addMethod('maxLength500', function(value, element) {
	return value.length <= 500;
}, "Value must be less than 501 symbols");

$.validator.addMethod("inputimage", getFileValidation(new Array('gif', 'png', 'jpg', 'jpeg')), 
	"File extension should be {'gif','png','jpg','jpeg'} ");

function getFileValidation(extentions) {
	return function(file, element) {
		try {
			var file = file;
			if (file=='') {
				return true;
			}
			var ext = file.split('.').pop().toLowerCase();
			var allow = extentions;
			if (jQuery.inArray(ext, allow) == -1) {
				return false;
			}
			return true;
		} catch (err) {
			return this.optional(element) || false;
		}
	}	
}

$.validator.addMethod("uploadfile", function(file, element) {
	try {
		var file = file;
		if (file == "") {return false;}
		return true;
	} catch (err) {
		return this.optional(element) || false;
	}
}, "please choose a file");

$.validator.addMethod("fileNameLength255", function(file, element) {
	try {
		var file = file;
		if (file.length>206) {return false;}
		return true;
	} catch (err) {
		return this.optional(element) || false;
	}
}, "File Name must be less than 207 symbols");


$.validator.addMethod('email', function(value, element) {
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    return re.test(value);
}, "Value must be email");

$.validator.addMethod('Decimal', function(value, element) {
	return this.optional(element) || /^[+-]?\d+(\.\d+)?([eE][+-]?\d+)?$/.test(value); 
}, "Please enter a correct number, format xxxx.xxxxx");

$.validator.addMethod('dayofmonth', function(value, element) {
	return Number(value) > 0 && Number(value) <=28;
}, "Day of month must be from 1 to 28.<br/>");

$.validator.addMethod('day', function(value, element) {
	return Number(value) > 0 && Number(value) <=365;
}, "Day must be from 1 to 365.<br/>");

$.validator.addMethod('hundredyears', function(value, element) {
	return Number(value) > 0 && Number(value) <=36500;
}, "Day must be from 1 to 36500 (100 years).<br/>");

$.validator.addMethod('integer', function(value, element) {
	return Number(value) > 0;
}, "Field must be numeric and greater 0.<br/>");
$.validator.addMethod('integer0', function(value, element) {
	return Number(value) >= 0;
}, "Field must be numeric and equal or greater than 0.<br/>");

$.validator.addMethod('integertimeout', function(value, element) {
	var n = Number(value);
	return (n >= 15 && n <= 120);
}, "Timeout must be numeric and between 15 and 120.<br/>");

$.validator.addMethod('periodDateTime', function(value, element) {
	var parent = $(element).closest('.control-group').parent();
	var start = new Date(parent.find('.startDateTime.datepckr').val() +" "+ parent.find('.startDateTime.timepckr').val()).getTime();
	var end = new Date(parent.find('.endDateTime.datepckr').val() +" "+ parent.find('.endDateTime.timepckr').val()).getTime();
	
	return start<end;
}, "End Date must be greater than Start Date.<br/>");

$.validator.addMethod('startBeforeNow', function(value, element) {
	var parent = $(element).closest('.control-group').parent();
	var start = new Date(parent.find('.startDateTime.datepckr').val() +" "+ parent.find('.startDateTime.timepckr').val()).getTime();
	return start<new Date().getTime();
}, "Start Date must be less than current date.<br/>");

//dayofmonth

$.validator.addMethod("postalcode",	function(postalcode, element) {
	return this.optional(element) || postalcode
			.match(/(^\d{5}(-\d{4})?$)|(^[ABCEGHJKLMNPRSTVXYabceghjklmnpstvxy]{1}\d{1}[A-Za-z]{1} ?\d{1}[A-Za-z]{1}\d{1})$/);
}, "Please specify a valid postal/zip code");


$.validator.addMethod("dateValue", function(dateValue, element) {
	try {
		var date = $.datepicker.parseDate('mm/dd/yy', dateValue);
		return true;
	} catch (err) {
		return this.optional(element) || false;
	}
}, "Please specify a valid date, date format mm/dd/yyyy");

/* API method to get paging information */
$.fn.dataTableExt.oApi.fnPagingInfo = function(oSettings) {
	return {
		"iStart" : oSettings._iDisplayStart,
		"iEnd" : oSettings.fnDisplayEnd(),
		"iLength" : oSettings._iDisplayLength,
		"iTotal" : oSettings.fnRecordsTotal(),
		"iFilteredTotal" : oSettings.fnRecordsDisplay(),
		"iPage" : Math.ceil(oSettings._iDisplayStart
				/ oSettings._iDisplayLength),
		"iTotalPages" : Math.ceil(oSettings.fnRecordsDisplay()
				/ oSettings._iDisplayLength)
	};
};

/* Bootstrap style pagination control */
$.extend(
				$.fn.dataTableExt.oPagination,
				{
					"bootstrap" : {
						"fnInit" : function(oSettings, nPaging, fnDraw) {
							var oLang = oSettings.oLanguage.oPaginate;
							var fnClickHandler = function(e) {
								e.preventDefault();
								if (oSettings.oApi._fnPageChange(oSettings,
										e.data.action)) {
									fnDraw(oSettings);
								}
							};

							$(nPaging)
									.addClass('pagination')
									.append(
											'<ul>'
													+ '<li class="prev disabled"><a href="#">&larr; '
													+ oLang.sPrevious
													+ '</a></li>'
													+ '<li class="next disabled"><a href="#">'
													+ oLang.sNext
													+ ' &rarr; </a></li>'
													+ '</ul>');
							var els = $('a', nPaging);
							$(els[0]).bind('click.DT', {
								action : "previous"
							}, fnClickHandler);
							$(els[1]).bind('click.DT', {
								action : "next"
							}, fnClickHandler);
						},

						"fnUpdate" : function(oSettings, fnDraw) {
							var iListLength = 5;
							var oPaging = oSettings.oInstance.fnPagingInfo();
							var an = oSettings.aanFeatures.p;
							var i, j, sClass, iStart, iEnd, iHalf = Math
									.floor(iListLength / 2);

							if (oPaging.iTotalPages < iListLength) {
								iStart = 1;
								iEnd = oPaging.iTotalPages;
							} else if (oPaging.iPage <= iHalf) {
								iStart = 1;
								iEnd = iListLength;
							} else if (oPaging.iPage >= (oPaging.iTotalPages - iHalf)) {
								iStart = oPaging.iTotalPages - iListLength + 1;
								iEnd = oPaging.iTotalPages;
							} else {
								iStart = oPaging.iPage - iHalf + 1;
								iEnd = iStart + iListLength - 1;
							}

							for (i = 0, iLen = an.length; i < iLen; i++) {
								// Remove the middle elements
								$('li:gt(0)', an[i]).filter(':not(:last)')
										.remove();

								// Add the new list items and their event
								// handlers
								for (j = iStart; j <= iEnd; j++) {
									sClass = (j == oPaging.iPage + 1) ? 'class="active"'
											: '';
									$(
											'<li ' + sClass + '><a href="#">'
													+ j + '</a></li>')
											.insertBefore(
													$('li:last', an[i])[0])
											.bind(
													'click',
													function(e) {
														e.preventDefault();
														oSettings._iDisplayStart = (parseInt(
																$('a', this)
																		.text(),
																10) - 1)
																* oPaging.iLength;
														fnDraw(oSettings);
													});
								}

								// Add / remove disabled classes from the static
								// elements
								if (oPaging.iPage === 0) {
									$('li:first', an[i]).addClass('disabled');
								} else {
									$('li:first', an[i])
											.removeClass('disabled');
								}

								if (oPaging.iPage === oPaging.iTotalPages - 1
										|| oPaging.iTotalPages === 0) {
									$('li:last', an[i]).addClass('disabled');
								} else {
									$('li:last', an[i]).removeClass('disabled');
								}
							}
						}
					}
				});

$.extend($.fn.dataTable.defaults, {
	"sDom" : "<'row'<'span6'l><'span6'f>r>t<'row'<'span6'i><'span6'p>>",
	"sPaginationType" : "bootstrap",
	"aLengthMenu" : [ [ 20, 50, 100, -1 ], [ 20, 50, 100, "All" ] ],
	"iDisplayLength" : 50
});

jQuery.validator.setDefaults({
	onfocusout: injectTrim($.validator.defaults.onfocusout),
	errorElement : 'span',
	errorClass : 'help-inline',
	errorPlacement: function(error, element) {
		if (element.is('.filter_value_by_type')) {
	    	var p = element.parent().find('.filter-error-container');
	    	p.append(error);
	    } else if (element.is('.datetime')) {
				var p = element.parent().parent();
				p.find('.error-container').empty().append(error);
				return;
						
		} else if (element.is('.datepckr, .value-control, .timepckr')) {
			error.insertAfter(element.parent());
			error.css('position','relative');
			//error.css('left','15px');
			return;
		} else if (element.is('.inputwithrightlabel, .inputwithrightlabel')) {
			var c = element.closest('.controls, .control-group');
			c.find('.error-container').append(error);
			return;
		} else if (element.is(':file')) {
			if ($.browser.msie) {
				element.parent().parent().append(error);
			} else {
				error.insertAfter(element.parent().parent());
			}
			return;
		} else if (element.is('.withCombo')) {
			element.parent().append(error);
			return;
		} else if (element.is('.j_salesforce_select, .j_outputTo')) {
			var p = element.parent().parent();
			if (p.parent().is('.controls-group')) {
				error.addClass('errorInGroup');
				error.attr('title',error.html());
			} 
			
			p.append(error);
			return;
			
		} else if (element.is('j_phone_item_field')) {
			var parent = element.closest('j_ui_item_container');
			parent.append(error);
			return;
		} else if (element.is('.select_brackets')) { 
			$('.error-container').empty().append(error);
			
		} else {
			error.insertAfter(element);
		};
	},
	highlight : function(label) {
		$(label).closest('.control-group').addClass('error');
	},
	invalidHandler: function(form, validator) {
		if (!validator.numberOfInvalids()) {
			return;
		};

		$('html, body').animate({
            scrollTop: $(validator.errorList[0].element).parent().parent().offset().top-120
        }, 500);
		
	},
	success : function(label) {
		if ($(label).closest('.filter-fields .error-container').length > 0) {
			$('.j_filteritems_container .error').each(function() {
				$this = $(this).parent();
				$(this).removeClass('error');
			});
		};
		if ($(label).closest('.j_filter_exclude_items').length > 0) {
			$('.j_filter_exclude_items .error').each(function() {
				$this = $(this).parent();
				$(this).removeClass('error');
			});
		}		
		else {
			$(label).closest('.control-group').removeClass('error');
		}
	}
});


function injectTrim(handler) {
	  return function (element, event) {
	    if (element.tagName === "TEXTAREA" || (element.tagName === "INPUT" && element.type !== "password"
	      && element.type !== "file")) {
	      element.value = $.trim(element.value);
	    }
	    return handler.call(this, element, event);
	  };
}


function millisToDateString(millis) {
	function _withZeros(n) {
		return Math.floor(n / 10).toString() + (n % 10).toString();
	}
	var dt = new Date(millis);
	return _withZeros(dt.getDate()) + "/" + _withZeros(1 + dt.getMonth()) + "/"
			+ dt.getFullYear();
}

function getCurrentDate() {
	var currentTime = new Date()
	var month = currentTime.getMonth() + 1
	var day = currentTime.getDate()
	var year = currentTime.getFullYear()
	return month + "/" + day + "/" + year;
}

(function($) {
	$.fn.fadingAlert = function(msg) {
		var oldtmId = this.data('tm-id');
		if (oldtmId)
			window.clearTimeout(oldtmId);

		var html = "<div class='alert";
		if (arguments.length > 1) {
			html += " ";
			html += arguments[1];
		}
		html += "'>";
		html += msg;
		html += "</div>";
		this.html(html).fadeIn();
		var _this = this;
		var tmId = setTimeout(function() {
			_this.fadeOut();
		}, 5000);
		this.data('tm-id', tmId);
	};
	
	String.prototype.capitalize = function() {
	    return this.replace(/(?:^|\s)\S/g, function(a) { return a.toUpperCase(); });
	};	

})(jQuery);

function initDatePickers() {
	$('.datepicker').each(function () {
		var config = { format: "mm/dd/yyyy",autoclose: true};
		$(this).datepicker(config);
	});
};

Date.prototype.yyyymmdd = function() {
	  var yyyy = this.getFullYear().toString();
	  var mm = (this.getMonth()+1).toString(); // getMonth() is zero-based
	  var dd  = this.getDate().toString();
	  return (mm[1]?mm:"0"+mm[0]) + "/" + (dd[1]?dd:"0"+dd[0]) +"/"+ yyyy; 
};

Date.prototype.hhmm = function() {
	  var hh = this.getHours().toString();
	  var mi = this.getMinutes().toString();
	  return (hh[1]?hh:"0"+hh[0]) + ":" + (mi[1]?mi:"0"+mi[0]); 
};

function htmlEscape1(str) {
	return String(str)
            .replace(/&/g, '&amp;')
            .replace(/'/g, '&#39;')
            .replace(/</g, '&lt;')
            .replace(/>/g, '&gt;');
};

function htmlEscape(str) {
	var r = htmlEscape1(encodeObjectProp(htmlEscape1(str)));
	return r;
}

function encodeObjectProp(value) {
	var o = $('<div></div>');
	o.html(value);
	return o.text();
};