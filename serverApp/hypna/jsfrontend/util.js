HypnaAppUtil = new function() {
this.makeRequest = function(type, u, d, callback) {
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
success: callback
});
var requestOut = "Request URL: "+ window.location.host +""+ u+" \n"+"Request Method: "+type;
if(d !=null){
requestOut += "\nRequest Data: "+d;
}
$("#request-textarea").val(requestOut);
};

this.test = function() {

};

this.makeJsonRequest = function(type, u, d, callback) {
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
success: callback
});
};

this.makeJsonRequestWithHeaders = function(type, u, d, callback) {
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
beforeSend: function(xhr) {
xhr.setRequestHeader("username", "admin");
xhr.setRequestHeader("password", "admin");
},
success: callback
});
};

this.makeJsonRequestAddData = function(type, u, d, callback) {
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
beforeSend: function(xhr) {
xhr.setRequestHeader("cardNum", "1234123456768970");
xhr.setRequestHeader("pin", "453");
xhr.setRequestHeader("expDate", "1212");
xhr.setRequestHeader("amount", "1230");
xhr.setRequestHeader("user", "Madhuka");
xhr.setRequestHeader("cardCode", "ADADK342MLKWR984FM32DKD2C,KSD902EMMK82MMC84FMC");
},
success: callback
});
};

this.makeJsonRequestAddDataLtr = function(type, u, d, callback,ltr) {
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
beforeSend: function(xhr) {
xhr.setRequestHeader("cardNum", "1234123456768970");
xhr.setRequestHeader("pin", "453");
xhr.setRequestHeader("expDate", "1212");
xhr.setRequestHeader("amount", "1230");
xhr.setRequestHeader("user", "Madhuka");
xhr.setRequestHeader("cardCode", "ADADK342MLKWR984FM32DKD2C,KSD902EMMK82MMC84FMC");
xhr.setRequestHeader("to", ltr);
},
success: callback
});
};

this.makeJsonRequestForTranSearch = function(type, u, d, callback,from,to) {
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
beforeSend: function(xhr) {
xhr.setRequestHeader("to", to);
xhr.setRequestHeader("from", from);
},
success: callback
});
};

this.makeJsonRequestAddDataWithLocaton = function(type, u, d, callback) {
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
beforeSend: function(xhr) {
xhr.setRequestHeader("cardNum", "1234123456768970");
xhr.setRequestHeader("pin", "453");
xhr.setRequestHeader("expDate", "1212");
xhr.setRequestHeader("amount", "1230");
xhr.setRequestHeader("user", "Madhuka");
xhr.setRequestHeader("cardCode", "ADADK342MLKWR984FM32DKD2C,KSD902EMMK82MMC84FMC");
xhr.setRequestHeader("locationName", "Retail-Store-Gampaha");

},
success: callback
});
};

this.makeJsonverifyCard = function(type, u, d, callback,cardNum) {
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
beforeSend: function(xhr) {
xhr.setRequestHeader("cardNum", cardNum);
xhr.setRequestHeader("cardPass", "123");
xhr.setRequestHeader("expDate", "1212");
xhr.setRequestHeader("user", "Madhuka");
xhr.setRequestHeader("cardCode", "ADADK342MLKWR984FM32DKD2C,KSD902EMMK82MMC84FMC");
},
success: callback
});
};


this.makeJsonverifyCard = function(type, u, d, callback,cardNum) {
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
beforeSend: function(xhr) {
xhr.setRequestHeader("cardNum", cardNum);
xhr.setRequestHeader("cardPass", "123");
xhr.setRequestHeader("expDate", "1212");
xhr.setRequestHeader("user", "Madhuka");
xhr.setRequestHeader("cardCode", "ADADK342MLKWR984FM32DKD2C,KSD902EMMK82MMC84FMC");
},
success: callback
});
};

this.makeJsonUpdateLocation = function(type, u, d, callback,tUUID,lUUID) {
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
beforeSend: function(xhr) {
xhr.setRequestHeader("tpk", tUUID);
xhr.setRequestHeader("lpk", lUUID);
},
success: callback
});
};

this.makeJsonUpdateLocationName = function(type, u, d, callback,tUUID,locName) {
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
beforeSend: function(xhr) {
xhr.setRequestHeader("tpk", tUUID);
xhr.setRequestHeader("locationName", locName);
},
success: callback
});
};



/*
 * location data
 */
this.makeJsonRequestGetshops = function(type, u, d, callback,longitudeN) {
	
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
beforeSend: function(xhr) {
xhr.setRequestHeader("longitude", parseInt(longitudeN));
},
success: callback
});
};

this.makeJsonRequestGetLocation = function(type, u, d, callback,locuuid) {	
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
beforeSend: function(xhr) {
xhr.setRequestHeader("locuuid", locuuid);
},
success: callback
});
};


this.makeJsonAddLocationRequest = function(type, u, d, callback,locname,longitude,latitude) {	
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
beforeSend: function(xhr) {
xhr.setRequestHeader("locname", locname);
xhr.setRequestHeader("longitude", longitude);
xhr.setRequestHeader("latitude", latitude);
},
success: callback
});
};

}