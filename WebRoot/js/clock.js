var weekday = new Array(7);
weekday[0]=  "Sunday";
weekday[1] = "Monday";
weekday[2] = "Tuesday";
weekday[3] = "Wednesday";
weekday[4] = "Thursday";
weekday[5] = "Friday";
weekday[6] = "Saturday";

var monthArr = new Array(12);
monthArr[0] = "Jan";
monthArr[1] = "Feb";
monthArr[2] = "Mar";
monthArr[3] = "Apr";
monthArr[4] = "May";
monthArr[5] = "Jun";
monthArr[6] = "Jul";
monthArr[7] = "Aug";
monthArr[8] = "Sep";
monthArr[9] = "Oct";
monthArr[10] = "Nov";
monthArr[11] = "Dec";
    
function startTime() {
    var today = new Date();
    var day = weekday[today.getDay()];
    
    var date = today.getDate();
    var month = monthArr[today.getMonth()];
    var year = today.getFullYear();
    
    var h=today.getHours();
    var m=today.getMinutes();
    var s=today.getSeconds();
    m = checkTime(m);
    s = checkTime(s);
    document.getElementById('date').innerHTML = day+", "+date+" "+month+" "+year;
    document.getElementById('time').innerHTML = h+":"+m+":"+s;
    var t = setTimeout(function(){startTime()},500);
}

function checkTime(i) {
    if (i<10) {i = "0" + i};  // add zero in front of numbers < 10
    return i;
}
