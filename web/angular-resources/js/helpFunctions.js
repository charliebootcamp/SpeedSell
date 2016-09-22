function isEmpty(str) {
    return (!str || 0 === str.length);
}

function minmax(value, min, max)
{
    if(parseInt(value) < min || isNaN(value))
        return min;
    else if(parseInt(value) > max)
        return max;
    else return value;
}

function getTimeLeft(lot) {
	 var t = lot.startDate + lot.duration - new Date().getTime();
	    if(t < 1000) t = 0;
	    var seconds = Math.floor( (t/1000) % 60 );
	    var minutes = Math.floor( (t/1000/60) % 60 );
	    var hours = Math.floor( (t/(1000*60*60)) % 24 );
	    var days = Math.floor( t/(1000*60*60*24) );
	    if (seconds < 10) seconds = "0" + seconds;
	    if (minutes < 10) minutes = "0" + minutes;
	    if (hours < 10) hours = "0" + hours;
	    return {
	        'total': t,
	        'days': days,
	        'hours': hours,
	        'minutes': minutes,
	        'seconds': seconds
	    };
	}

	function getMark(id){
	            switch(id) {
	            case 0:
	                return "Bad";
	                    break;
	            case 1:
	                return "Good";
	                    break;
	            case 2:
	                return "Neutral";
	                    break;
	            default:
	                    return "No mark";
	    }
	}
	function findBootstrapEnvironment() {
	    var envs = ['xs', 'sm', 'md', 'lg'];

	    var $el = $('<div>');
	    $el.appendTo($('body'));

	    for (var i = envs.length - 1; i >= 0; i--) {
	        var env = envs[i];

	        $el.addClass('hidden-'+env);
	        if ($el.is(':hidden')) {
	            $el.remove();
	            return env;
	        }
	    }
	}
