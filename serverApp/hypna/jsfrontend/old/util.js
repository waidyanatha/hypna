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

this.makeJsonRequestWithHeaders = function(type, u, d, callback, usern, pass) {
$.ajax({
type: type,
url: u,
data: d,
dataType: "json",
beforeSend: function(xhr) {
xhr.setRequestHeader("username", usern);
xhr.setRequestHeader("password", pass);
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
}
