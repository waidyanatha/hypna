Hypna = new function() {

this.lastData = function() {
var token = $('#token').val();
var data = {"action":"lastData","token":token};
HypnaAppUtil.makeJsonRequest("GET","https://202.69.197.115:9443/hypna/transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("lastData");
}

this.getPK = function() {
var token = $('#token').val();
var data = {"action":"getPK","token":token};
HypnaAppUtil.makeJsonRequest("GET","https://202.69.197.115:9443/hypna/transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
}

this.viewData = function() {
var token = $('#token').val();
var data = {"action":"viewData","token":token};
HypnaAppUtil.makeJsonRequest("GET","https://202.69.197.115:9443/hypna/transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("viewData");
}

this.addData = function() {
var token = $('#token').val();
var data = {"action":"addData","token":token};
HypnaAppUtil.makeJsonRequestAddData("GET","https://202.69.197.115:9443/hypna/transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("addData");
}
this.addMasterData = function() {
var token = $('#token').val();
var data = {"action":"addMasterData","token":token};
HypnaAppUtil.makeJsonRequestAddData("GET","https://202.69.197.115:9443/hypna/transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("addMasterData");
}

this.verifyCard = function() {
var token = $('#token').val();
var cardnumber = $('#cardnumber').val();
var data = {"action":"verifyCard","token":token};
HypnaAppUtil.makeJsonverifyCard("GET","https://202.69.197.115:9443/hypna/transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
},cardnumber);
console.log("verifyCard"+cardnumber);
}

this.resetDB = function() {
var token = $('#token').val();
var data = {"action":"resetDB","token":token,"DBadmin":"Madhuka"};
HypnaAppUtil.makeJsonRequestAddData("GET","https://202.69.197.115:9443/hypna/transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("resetDB");
}

this.resetDBCardTable = function() {
var token = $('#token').val();
var data = {"action":"resetDBCardTable","token":token,"DBadmin":"Madhuka"};
HypnaAppUtil.makeJsonRequestAddData("GET","https://202.69.197.115:9443/hypna/transaction.jag",data,function(html) {
console.log(html);
Hypna.makeout(html);
});
console.log("resetDBCardTable");
}




this.login = function() {
var data = {"action":"login"};
HypnaAppUtil.makeJsonRequestWithHeaders("GET","https://202.69.197.115:9443/hypna/login.jag",data,function(html) {
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

}
