Hypna = new function() {

this.lastData = function() {
var token = $('#token').val();
var data = {"action":"lastData","token":token};
HypnaAppUtil.makeJsonRequest("GET","transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("lastData");
}

this.getPK = function() {
var token = $('#token').val();
var data = {"action":"getPK","token":token};
HypnaAppUtil.makeJsonRequest("GET","transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
}

this.viewData = function() {
var token = $('#token').val();
var data = {"action":"viewData","token":token};
HypnaAppUtil.makeJsonRequest("GET","transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("viewData");
}

this.viewSearchData = function() {
var token = $('#token').val();
var tfromv = $('#tfrom').val();
var ttov = $('#tto').val();
var data = {"action":"viewSearchData","token":token};
HypnaAppUtil.makeJsonRequestForTranSearch("GET","transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
},tfromv,ttov);
console.log("viewSearchData");
}

this.addData = function() {
var token = $('#token').val();
var data = {"action":"addData","token":token};
HypnaAppUtil.makeJsonRequestAddData("GET","transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("addData");
}
this.addMasterData = function() {
var token = $('#token').val();
var data = {"action":"addMasterData","token":token};
HypnaAppUtil.makeJsonRequestAddData("GET","transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("addMasterData");
}
this.addMasterDataLtr = function() {
var token = $('#token').val();
var tto = $('#tto').val();
var data = {"action":"addMasterDataLtr","token":token};
HypnaAppUtil.makeJsonRequestAddDataLtr("GET","transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
},tto);
console.log("addMasterDataLtr");
}

this.addMasterDataWithLocation = function() {
var token = $('#token').val();
var data = {"action":"addMasterDataWithLocation","token":token};
HypnaAppUtil.makeJsonRequestAddDataWithLocaton("GET","transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("addMasterDataWithLocation");
}

this.verifyCard = function() {
var token = $('#token').val();
var cardnumber = $('#cardnumber').val();
var data = {"action":"verifyCard","token":token};
HypnaAppUtil.makeJsonverifyCard("GET","transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
},cardnumber);
console.log("verifyCard"+cardnumber);
}

this.resetDB = function() {
var token = $('#token').val();
var data = {"action":"resetDB","token":token,"DBadmin":"Madhuka"};
HypnaAppUtil.makeJsonRequestAddData("GET","transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("resetDB");
}

this.resetDBCardTable = function() {
var token = $('#token').val();
var data = {"action":"resetDBCardTable","token":token,"DBadmin":"Madhuka"};
HypnaAppUtil.makeJsonRequestAddData("GET","transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("resetDBCardTable");
}


this.updateLocData = function() {
var token = $('#token').val();
var tUUID = $('#tUUID').val();
var lUUID = $('#lUUID').val();
var data = {"action":"updateLocData","token":token};
HypnaAppUtil.makeJsonUpdateLocation("GET","transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
},tUUID,lUUID);
console.log("updateLocData"+cardnumber);
}

this.updateLocNameData = function() {
var token = $('#token').val();
var tUUID = $('#tUUID').val();
var locnames = $('#locname').val();
var data = {"action":"updateLocData","token":token};
HypnaAppUtil.makeJsonUpdateLocationName("GET","transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
},tUUID,locnames);
console.log("updateLocNameData"+cardnumber);
}


this.login = function() {
var data = {"action":"login"};
HypnaAppUtil.makeJsonRequestWithHeaders("GET","login.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
$('#token').val(html.sessionId);
});
console.log("testing");
}

this.testjs = function() {
console.log("testing");
}

this.makeout = function(html) {
$("#out").val(JSON.stringify(html));
}

/*
 * 
 * Lcoation API
 */

this.listlocationNames = function() {
var token = $('#token').val();
var data = {"action":"listlocationNames","token":token};
HypnaAppUtil.makeRequest("GET","location.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("listlocationNames");
}

this.listlocations = function() {
	console.log("listlocationsxx");
var token = $('#token').val();
var data = {"action":"listlocations","token":token};
HypnaAppUtil.makeRequest("GET","location.jag",data,function(html) {
	console.log("html");
console.log(html);
Hypna.makeout(html);
});
console.log("listlocations");
}


this.getshops = function() {
var token = $('#token').val();
var longitudeN = $('#longitude').val();
var data = {"action":"getshops","token":token};
HypnaAppUtil.makeJsonRequestGetshops("GET","location.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
},longitudeN);
console.log("getshops");
}

this.getLocation = function() {
var token = $('#token').val();
var locuuid = $('#locuuid').val();
var data = {"action":"getLocation","token":token};
HypnaAppUtil.makeJsonRequestGetLocation("GET","location.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
},locuuid);
console.log("getLocation");
}

this.lastLocData = function() {
	console.log("lastLocDatax");
var token = $('#token').val();
var data = {"action":"lastData","token":token};
HypnaAppUtil.makeJsonRequest("GET","location.jag",data,function(html) {
	console.log("lastLocDataxx");
console.log(html);
Hypna.makeout(html);
});
console.log("lastData");
}

this.getLocPK = function() {
var token = $('#token').val();
var data = {"action":"getPK","token":token};
HypnaAppUtil.makeJsonRequest("GET","location.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
}

this.addLocationData = function() {
var token = $('#token').val();
var locname = $('#locname').val();
var longitude = $('#longitude').val();
var latitude = $('#latitude').val();
var data = {"action":"addLocation","token":token};
HypnaAppUtil.makeJsonAddLocationRequest("GET","location.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
},locname,longitude,latitude);
}



}